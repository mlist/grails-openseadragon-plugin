package org.nanocan.io

import org.apache.commons.io.FilenameUtils

class ImgController {
    def grailsApplication
    def springSecurityService

    def getTiles(){

        if(!springSecurityService?.isLoggedIn()){
            response.status =  403
            return
        }

        def imageZoomFolder = "/upload/tiles"//grailsApplication.config.imagezoom.folder

        def fileName = params.imageFile
        def pathToTiles = imageZoomFolder.toString() + "/" + params.imageFolder + "/" + params.imageLevel + "/" + fileName

        log.info "Trying to read tile for ${fileName} from ${pathToTiles}"

        File imgFile = new File(pathToTiles)

        if (imgFile.exists()) {
            response.contentType="image/jpg"
            response.outputStream << imgFile.newInputStream()
        }

        else {
            response.status = 404
        }

    }
}
