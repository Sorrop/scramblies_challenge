(ns user
  (:require #_[com.stuartsierra.component.repl
               :refer [reset set-init start stop system]]
            [com.stuartsierra.component.repl
             :refer [set-init]]
            [com.stuartsierra.component.user-helpers :refer [dev go reset set-dev-ns]]
            [scramblies-challenge.components.app-config :as app-config]
            [scramblies-challenge.components.server :as server]
            [com.stuartsierra.component :as component]))

(clojure.tools.namespace.repl/set-refresh-dirs "dev" "src")

(set-dev-ns 'user)

(defn the-system [_]
  (component/system-map
   :app-config (app-config/->App-config)
   :web-server (component/using (server/web-server)
                                [:app-config])))

(set-init the-system)

#_(go)
