(ns zhdk.ldap-fetch.main
  (:require 
    [clj-ldap.client :as ldap]
    )
  )




(defn -main [& args]

  (with-open [conn (ldap/connect {:host "..."})]
    (println "Hello World!")
    )

  )
