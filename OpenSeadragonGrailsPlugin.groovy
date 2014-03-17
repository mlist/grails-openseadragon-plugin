class OpenSeadragonGrailsPlugin {
    def version = "0.2"
    def grailsVersion = "2.0 > *"
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def title = "Open Seadragon Plugin"
    def description = '''\
Wraps the OpenSeadragon JavaScript library. OpenSeadragon is a pure JavaScript library that allows you
to display, pan, and zoom large images. To this end, images need to be split into tiles. OpenSeadragon supports a number of
formats. In order to include an all java solution here, I produce tiles for the Microsoft deep image format using modified code
from https://code.google.com/p/deepjzoom/. Have a look at GitHub for a more detailed description.
'''

    def documentation = "http://grails.org/plugin/open-seadragon"
    def license = "GPL3"
    def organization = [ name: "University of Southern Denmark", url: "http://nanocan.org/" ]
    def developers = [
        [name: 'Markus List', email: 'itisalist@gmail.com']
    ]

    def issueManagement = [ system: "GIT", url: "https://github.com/mlist/grails-openseadragon-plugin/issues" ]
    def scm = [ url: "https://github.com/mlist/grails-openseadragon-plugin" ]
}
