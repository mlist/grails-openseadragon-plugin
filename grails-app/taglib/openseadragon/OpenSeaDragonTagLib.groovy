package openseadragon

class OpenSeaDragonTagLib {

    def grailsApplication
    def imageProcessingService

    def openSeaDragon = { attrs, body ->

        def filePath = attrs.file
        def target = attrs.target

        //check if filePath and target div are provided
        if(!filePath){
            out << "file path required"
            return
        }

        if(!target){
            out << "target required"
            return
        }

        //check if file is being processed right now
        if(imageProcessingService.isBeingProcessed(filePath)){
            out << "<div class='message'>Image file is still being processed. It will be...wait for it...</div>"
        } //check if file has been processed
        else if(!imageProcessingService.isProcessed(filePath, true)){
            out << "<div class='message'>Image file is being processed in the background. Refresh this page in a few seconds.</div>"
            return
        }

        //extract XML description of deep image
        def deepImageDescriptor = new XmlParser().parse(imageProcessingService.getDescriptorFilePath(filePath))

        def tileSourceUrl = g.createLink(controller: "img", action:"getTiles") + "/" + imageProcessingService.getModifiedFilePath(filePath) + "/"
        def imageWidth = deepImageDescriptor.Size.@Width.first()
        def imageHeight = deepImageDescriptor.Size.@Height.first()
        def format = deepImageDescriptor.@Format
        def schema = deepImageDescriptor.@xmnls
        def overlap = deepImageDescriptor.@Overlap
        def tileSize = deepImageDescriptor.@TileSize

        //js output
         out << """
            <script src="${g.resource(dir: 'js', file: 'openseadragon.min.js', plugin:'open-sea-dragon')}" type="text/javascript"></script>
            <script type="text/javascript">
            jQuery(document).ready(function(){
                var viewer = OpenSeadragon({
                    id: "zoomable",
                    showNavigator: ${attrs.showNavigator?:true},
                    visibilityRatio: ${attrs.visibilityRatio?:0.5},
                    defaultZoomLevel: ${attrs.defaultZoomLevel?:1},
                    minZoomLevel: ${attrs.minZoomLevel?:1},
                    maxZoomLevel: ${attrs.maxZoomLevel?:7},
                    prefixUrl: "${g.resource(dir:'images', plugin:'open-sea-dragon')}/",
                    tileSources: {
                        Image: {
                            xmlns:    "${schema}",
                            Url:      "${tileSourceUrl}",
                            Format:   "${format}",
                            Overlap:  "${overlap}",
                            TileSize: "${tileSize}",
                            Size: {
                                Height: "${imageHeight}",
                                Width:  "${imageWidth}"
                            }
                        }
                    }
                });

            });
            </script>
        """
    }
}
