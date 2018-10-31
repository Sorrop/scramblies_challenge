(ns scramblies-challenge.components.server
  (:require [scramblies-challenge.handler :refer [app-handler]]
            [ring.adapter.jetty :as jetty]
            [com.stuartsierra.component :as component]))

(defrecord Webserver [app-config]
  component/Lifecycle
  (start [system]
    (let [port (get-in app-config [:options :server :port])]
      (println (str "Starting web-server on port " port))
      (assoc system :server (jetty/run-jetty app-handler
                                             {:port port
                                              :join? false}))))

  (stop [system]
    (when-let [server (:server system)]
      (let [port (get-in system [:app-config :options :server :port])]
        (println (str "Stopping web-server on port " port))
        (.stop server)))
    (assoc system :server nil)))

(defn web-server []
  (map->Webserver {}))
