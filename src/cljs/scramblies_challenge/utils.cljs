(ns scramblies-challenge.utils
  (:require [ajax.core :refer [GET]]))

(defn clog [x]
  (.log js/console x))

(defn remote-call [body-map handler]
  (GET "/api/scramble"
       {:format :edn
        :params body-map
        :handler handler}))

(defn which-invalid [which]
  (str which " string is invalid. Must contain one lower-case letter or more."))

(defn valid? [s]
  (if (re-matches #"[a-z]+" s)
    true
    false))

(defn validate-inputs [l r]
  (let [valid-l? (valid? l)
        valid-r? (valid? r)]
    (cond
      (not valid-l?) [false (which-invalid "Left")]
      (not valid-r?) [false (which-invalid "Right")]
      :else [true nil])))
