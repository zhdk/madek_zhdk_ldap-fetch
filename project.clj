(defproject madek_zhdk_ldap-fetch "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
                 [clj-logging-config "1.9.12"]
                 [clj-yaml "0.4.0"]
                 [log4j/log4j "1.2.17" :exclusions [javax.mail/mail javax.jms/jms com.sun.jdmk/jmxtools com.sun.jmx/jmxri]]
                 [org.clojars.pntblnk/clj-ldap "0.0.9"]
                 [org.clojure/clojure "1.6.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.slf4j/slf4j-log4j12 "1.7.7"]
                 [robert/hooke "1.3.0"]
                 ]
  :aot [zhdk.ldap-fetch.main] 
  :main zhdk.ldap-fetch.main
  )
