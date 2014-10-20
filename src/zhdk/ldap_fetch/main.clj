(ns zhdk.ldap-fetch.main
  (:require 
    [clj-yaml.core :as yaml]
    [clj-ldap.client :as ldap]
    [clojure.tools.logging :as logging]
    [clj-logging-config.log4j :as logging-config]
    [clojure.data.json :as json]
    )
  )


;(declare *ldap-result* *mapped-result*)

(defn fetch []
  (logging/debug fetch)
  (def ^:dynamic *ldap-result*
    (with-open [conn (ldap/connect {:host "adc2.ad.zhdk.ch"
                                    :bind-dn "CN=madeksvc,OU=Service Accounts,OU=Accounts,OU=_ZHdK manuell,DC=ad,DC=zhdk,DC=ch"
                                    :password  (or (System/getenv "MADEKSVC_PASSWORD")
                                                   (throw (IllegalStateException. "MADEKSVC_PASSWORD env var required")))
                                    })]
      (ldap/search-all conn "OU=_Distributionlists,OU=_ZHdK,DC=ad,DC=zhdk,DC=ch" 
                       { :attributes [:cn :name :extensionAttribute1 :extensionAttribute3 :displayName]}))
    ))

(defn map-results []
  (logging/debug map-results)
  (def ^:dynamic *mapped-result*
    (->> *ldap-result*
         (map (fn [row]
                (logging/debug {:row row})
                (->> row 
                     (map (fn [[k v]]
                            (let [new-key (case k
                                            :name :institutional_group_name
                                            :extensionAttribute3 :institutional_group_id
                                            :extensionAttribute1 :name
                                            :displayName :display_name
                                            k)
                                  ]

                              [new-key v])))
                     (sort)
                     (into (empty row))
                     (#(select-keys % [:name :institutional_group_id :institutional_group_name]))
                     ;(fn [row] (select-keys row [:name :institutional_group_id :institutional_group_name]))
                     )))
         (filter :institutional_group_id) 
         (filter :name)
         (sort-by  :institutional_group_name)
         )))

(defn write []
  (println
    (json/write-str
      *mapped-result*)
    ))

(defn -main [& args]
  (fetch)
  (map-results)
  (write)
  )

;(logging-config/set-logger! :level :debug)
;(logging-config/set-logger! :level :info)


