(ns scramblies-challenge.core
  (:gen-class)
  (:require [com.stuartsierra.component :as component]
            [scramblies-challenge.components
             [app-config :as app-config]
             [server :as server]]
            [com.stuartsierra.component :as component]))


(defn the-system []
  (component/system-map
   :app-config (app-config/->App-config)
   :web-server (component/using (server/web-server)
                                [:app-config])))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (component/start (the-system)))
