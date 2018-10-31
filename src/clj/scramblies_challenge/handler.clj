(ns scramblies-challenge.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.format :refer [wrap-restful-format]]
            [ring.middleware.defaults :refer [wrap-defaults
                                              site-defaults
                                              api-defaults]]
            [ring.util.response :refer [response]]
            [scramblies-challenge.views :as views]
            [scramblies-challenge.api :refer [valid? scramble]]))


(defn serve-scramble [request]
  (let [{:keys [left-string right-string]} (get request :params)
        both-valid? (and (valid? left-string)
                         (valid? right-string))]
    (if both-valid?
      (-> (scramble left-string right-string)
          response)
      (response :invalid))))

(defroutes site-routes
  (GET "/" [] (views/main))
  (route/not-found "Not Found"))

(defroutes api-routes
  (context "/api" []
           (GET "/scramble" request (serve-scramble request))
           (ANY "*" []
                (route/not-found "Not Found."))))


(def app-handler
  (routes (-> (wrap-defaults api-routes api-defaults)
              (wrap-restful-format :formats [:edn]))
          (-> site-routes
              (wrap-defaults site-defaults))))
