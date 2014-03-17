package org.nanocan.io

import org.apache.commons.io.FilenameUtils

class ImageProcessingService {

    def grailsApplication

    def getOutputFolder(){
        grailsApplication.config.openseadragon.tilesFolder?:"web-apps/"
    }

    def getDescriptorFilePath(def filePath){
        def tilesFolder = grailsApplication.config.openseadragon.tilesFolder?:"web-apps/"
        def modifiedFilePath = getModifiedFilePath(filePath)
        def descriptorFilePath = tilesFolder+ "/" + modifiedFilePath  + ".xml"

        return(descriptorFilePath)
    }

    private String getModifiedFilePath(def filePath)
    {
        FilenameUtils.removeExtension(FilenameUtils.getName(filePath))
    }

    private File getLockFile(def inputFile){
        return(new File(getOutputFolder() + "/" + inputFile + ".lock"))
    }

    def convertToDeepZoom(def inputFile) {

        //start by creating a lock file to avoid that this process is being restarted several times
        def lockFile = getLockFile()

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
        def tileOverlap = grailsApplication.config.openseadragon.tileOverlap?:1
        def tileSize = grailsApplication.config.openseadragon.tileSize?:256

        //check if it's a tiff file

        //do the conversion
        log.info "converting ${inputFile} with overlap ${tileOverlap} and tile size ${tileSize}"
        try{
            DeepZoomConverter.processImageFile(new File(inputFile), new File(getOutputFolder()), tileOverlap, tileSize)
        }catch(Exception e)
        {
            log.info "deleting lock file for ${inputFile}"
            lockFile.delete()
            throw e
        }

        //delete lock file
        log.info "deleting lock file for ${inputFile}"
        lockFile.delete()

        return
    }

    def isBeingProcessed(def inputFile)
    {
        return(getLockFile(inputFile).exists())
    }

    def isProcessed(def filePath, boolean ifNotCreate)
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
