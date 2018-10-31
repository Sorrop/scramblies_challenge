(ns scramblies-challenge.views
  (:require [hiccup.page :refer [html5 include-js
                                 include-css]]))

(defn main []
  (html5 [:head
          [:title "Scramblies Challenge"]
          (include-css "css/scramblies.css")
          [:body
           [:h3 {:style "text-align:center;"} "Welcome to scramblies challenge!"]
           [:div {:id "app"}]
           (include-js "js/compiled/app.js")
           [:script "scramblies_challenge.core.init();"]]]))
