dataSource {
   	pooled = true
	driverClassName = "com.mysql.jdbc.Driver"
	dialect = org.hibernate.dialect.MySQL5InnoDBDialect
   // driverClassName = "org.h2.Driver"
   // username = "sa"
   // password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            username = "root"
			password = "Arthur43"
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:mysql://localhost:3306/elearning_dev?autoreconnect=true"
        }
    }
    test {
        dataSource {
            username = "root"
			password = "Arthur43"
            dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:mysql://localhost:3306/elearning_dev?autoreconnect=true"
        }
    }
    production {
        dataSource {
            username = "root"
			password = "Arthur43"			
			url = "jdbc:mysql://localhost:3306/elearning?autoreconnect=true"
            dbCreate = "update"            
            pooled = true
            properties {
               maxActive = 100
               minEvictableIdleTimeMillis=1800000
               timeBetweenEvictionRunsMillis=1800000
               numTestsPerEvictionRun=3
               testOnBorrow=true
               testWhileIdle=true
               testOnReturn=true
               validationQuery="SELECT now()"            
            }
        }
    }
}
