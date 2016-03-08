grails.project.work.dir = 'target'
grails.project.dependency.resolver = "maven"

grails.project.dependency.resolution = {

    inherits 'global'
    log 'warn'

    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
        mavenRepo "http://maven.geo-solutions.it"
        mavenRepo "http://download.osgeo.org/webdav/geotools/"
        mavenRepo "http://repo.grails.org/grails/repo/"
    }
    dependencies {
        build 'it.geosolutions.imageio-ext:imageio-ext-tiff:1.1.7'
    }

    plugins {
        build(":release:3.1.2", ":rest-client-builder:2.1.1") { export = false }
    }
}
