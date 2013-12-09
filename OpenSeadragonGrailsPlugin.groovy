class OpenSeadragonGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.0 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def title = "Open Seadragon Plugin" // Headline display name of the plugin
    def author = "Markus List"
    def authorEmail = "itisalist@gmail.com"
    def description = '''\
This plugin wraps the openseadragon javascript library. openseadragon is a pure javascript library that allows you
to display, pan, and zoom large images. To this end, images need to be split into tiles. openseadragon supports a number of
formats. In order to include an all java solution here, I produce tiles for the microsoft deep image format using modified code
from https://code.google.com/p/deepjzoom/. Have a look at github for a more detailled description.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/open-seadragon"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "GPL3"

    // Details of company behind the plugin (if there is one)
    def organization = [ name: "University of Southern Denmark", url: "http://nanocan.org/" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
    def issueManagement = [ system: "GIT", url: "https://github.com/mlist/grails-openseadragon-plugin/issues" ]

    // Online location of the plugin's browseable source code.
    def scm = [ url: "https://github.com/mlist/grails-openseadragon-plugin" ]

    def doWithWebDescriptor = { xml ->
    }

    def doWithSpring = {
    }

    def doWithDynamicMethods = { ctx ->
    }

    def doWithApplicationContext = { applicationContext ->
    }

    def onChange = { event ->
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event ->
    }
}
