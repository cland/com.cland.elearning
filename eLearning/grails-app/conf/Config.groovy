// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [
    all:           '*/*',
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false
//attachments
grails.attachmentable.maxInMemorySize = 1024
grails.attachmentable.maxUploadSize = 1024000000
//grails.attachmentable.uploadDir = "C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\temp" //YOUR_USER_HOME/APP_NAME
grails.attachmentable.poster.evaluator = { "unknown" }
environments {
    development {
        grails.logging.jul.usebridge = true
		grails.attachmentable.uploadDir = "C:\\Users\\Cland\\temp"
    }
    production {
        grails.logging.jul.usebridge = false
        // TODO: grails.serverURL = "http://www.changeme.com"
		grails.attachmentable.uploadDir = "C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\temp" //YOUR_USER_HOME/APP_NAME
//		if (System.properties["os.name"] == "Linux") {
//			grails.config.locations = ["file:/var/grails/app-conf/${appName}-config.groovy"]
//		}else{
//			grails.config.locations = ["file:C:\\grails\\app-conf\\${appName}-Config.groovy"]
//		}
    }
}

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error  'org.codehaus.groovy.grails.web.servlet',        // controllers
           'org.codehaus.groovy.grails.web.pages',          // GSP
           'org.codehaus.groovy.grails.web.sitemesh',       // layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping',        // URL mapping
           'org.codehaus.groovy.grails.commons',            // core / classloading
           'org.codehaus.groovy.grails.plugins',            // plugins
           'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'
}

// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'com.cland.elearning.Person'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'com.cland.elearning.PersonRole'
grails.plugins.springsecurity.authority.className = 'com.cland.elearning.Role'
grails.plugins.springsecurity.ui.encodePassword = false //added manually, jd - 19/05/2013

//grails.plugins.springsecurity.securityConfigType = "Annotation"
//grails.plugins.springsecurity.rejectIfNoRule = true

/** ORDERING: REALLY-SECURE FIRST to LESS SECURE **/
grails.plugins.springsecurity.controllerAnnotations.staticRules=[
	//'/**': ['IS_AUTHENTICATED_FULLY'],
	//** ADMIN ONLY
	'/subModule/**': ["hasRole('ADMIN')",'IS_AUTHENTICATED_FULLY'],
	 '/exam/**': ["hasRole('ADMIN')",'IS_AUTHENTICATED_FULLY'],
	 '/person/create/**': ["hasRole('ADMIN')",'IS_AUTHENTICATED_FULLY'],
	 '/person/edit/**': ["hasRole('ADMIN')",'IS_AUTHENTICATED_FULLY'],
	 '/course/create/**': ["hasRole('ADMIN')",'IS_AUTHENTICATED_FULLY'],
	  '/course/edit/**': ["hasRole('ADMIN')",'IS_AUTHENTICATED_FULLY'],
	'/admin/**': ["hasRole('ADMIN')",'IS_AUTHENTICATED_FULLY'],
	'/exam/**': ["hasRole('ADMIN')",'IS_AUTHENTICATED_FULLY'],
	'/personRole/**': ["hasRole('ADMIN')",'IS_AUTHENTICATED_FULLY'],
	'/registration/create/**': ["hasRole('ADMIN')",'IS_AUTHENTICATED_FULLY'],
	'/registration/edit/**': ["hasRole('ADMIN')",'IS_AUTHENTICATED_FULLY'],
	'/role/**': ["hasRole('ADMIN')",'IS_AUTHENTICATED_FULLY'],
	'/user/**': ["hasRole('ADMIN')",'IS_AUTHENTICATED_FULLY'],
	'/venue/**': ["hasRole('ADMIN')",'IS_AUTHENTICATED_FULLY'],
	'/eventResult/**': ["hasRole('ADMIN')",'IS_AUTHENTICATED_FULLY'],
	'/ResultSummary/create/**': ["hasRole('ADMIN')",'IS_AUTHENTICATED_FULLY'],
	'/ResultSummary/edit/**': ["hasRole('ADMIN')",'IS_AUTHENTICATED_FULLY'],
	'/module/create/**': ["hasRole('ADMIN')",'IS_AUTHENTICATED_FULLY'],
	'/module/edit/**': ["hasRole('ADMIN')",'IS_AUTHENTICATED_FULLY'],
	
	//** GENERAL AUTHENTICATED USER
	'/home/**': ['IS_AUTHENTICATED_FULLY'],
	'/course/**': ['IS_AUTHENTICATED_FULLY'],	
	'/ResultSummary/**': ['IS_AUTHENTICATED_FULLY'],	
	'/courseEvent/**': ['IS_AUTHENTICATED_FULLY'],
	'/module/**': ['IS_AUTHENTICATED_FULLY'],
	'/ExamResult/**': ['IS_AUTHENTICATED_FULLY'],
	'/organisation/**': ['IS_AUTHENTICATED_FULLY'],	
	'/login/**': ['IS_AUTHENTICATED_ANONYMOUSLY'],
	'/logout/**': ['IS_AUTHENTICATED_ANONYMOUSLY']
]
