package org.nanocan.io

import org.apache.commons.io.FilenameUtils

class ImageProcessingService {

    def grailsApplication

    def getOutputFolder(){
        grailsApplication?.config?.openseadragon?.tilesFolder?:"web-apps/"
    }

    String getDescriptorFilePath(String filePath){
        def tilesFolder = getOutputFolder()
        def modifiedFilePath = getModifiedFilePath(filePath)
        "${tilesFolder}/${modifiedFilePath}.xml"
    }

    private String getModifiedFilePath(String filePath) {
        FilenameUtils.removeExtension(FilenameUtils.getName(filePath))
    }

    private File getLockFile(inputFile){
        return new File(getOutputFolder() + "/" + getModifiedFilePath(inputFile) + ".lock")
    }

    def convertToDeepZoom(inputFile) {

        //start by creating a lock file to avoid that this process is being restarted several times
        def lockFile = getLockFile(inputFile)

        //check if lock file exists
        if(lockFile.exists())
        {
            log.error "stopping deep image conversion of ${inputFile} due to a lock file."
            return
        }

        //save lock file
        log.info "writing lock file for ${inputFile}"
        def output = new FileWriter(lockFile)
        output.close()

        //get conversion properties
        def tileOverlap = grailsApplication?.config?.openseadragon?.tileOverlap?:1
        def tileSize = grailsApplication?.config?.openseadragon?.tileSize?:256

        //do the conversion
        log.info "converting ${inputFile} with overlap ${tileOverlap} and tile size ${tileSize}"
        try{
            DeepZoomConverter.processImageFile(new File(inputFile), new File(getOutputFolder()), tileOverlap, tileSize)
        }catch(Exception e)
        {
            log.info "deleting lock file for ${inputFile}"
            lockFile.delete()
            log.error e.getMessage()
            log.error e.getStackTrace()
        }

        //delete lock file
        log.info "deleting lock file for ${inputFile}"
        lockFile.delete()
    }

    boolean isBeingProcessed(inputFile)
    {
        return(getLockFile(inputFile).exists())
    }

    boolean isProcessed(String filePath, boolean ifNotCreate)
    {
        def descriptorFilePath = getDescriptorFilePath(filePath)

        //check if descriptor file exists
        def descriptorFile = new File(descriptorFilePath.toString())
        if(!descriptorFile.exists() && ifNotCreate)
        {
            //try to create tiles for this file first
            Thread.start{
                convertToDeepZoom(filePath)
            }
        }

        return(descriptorFile.exists())
    }
}
