package org.nanocan.io

class ImgController {
    def springSecurityService
    def grailsApplication

    def getTiles(String imageFile, String imageFolder, String imageLevel) {

        if(!springSecurityService?.isLoggedIn()){
            response.status = 403
            return
        }
        String imageZoomFolder = grailsApplication?.config?.openseadragon?.tilesFolder?: "web-apps/"

        String pathToTiles = "$imageZoomFolder/$imageFolder/$imageLevel/$imageFile"

        log.info "Trying to read tile for $imageFile from $pathToTiles"

        File imgFile = new File(pathToTiles)

        if (!imgFile.exists()) {
            response.status = 404
            return
        }

        response.contentType = "image/jpg"
        response.outputStream << imgFile.newInputStream()
    }
}
