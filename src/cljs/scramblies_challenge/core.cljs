(ns scramblies-challenge.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   #_[scramblies-challenge.events :as events]
   [scramblies-challenge.views :as views]
   #_[scramblies-challenge.config :as config]))

(enable-console-print!)

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (mount-root))
