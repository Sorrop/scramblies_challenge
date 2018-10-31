(ns scramblies-challenge.components.app-config
  (:require [com.stuartsierra.component :as component]
            [clojure.edn :as edn]))

(defrecord App-config []
  ;; Implement the Lifecycle protocol
  component/Lifecycle
  (start [system]
    (println (str "Reading app config" ))
    (let [config (edn/read-string (slurp "config.edn"))]
      (assoc system :options config)))

  (stop [system]
    (assoc system :options nil)))

(defn app-config []
  (map->App-config {}))
