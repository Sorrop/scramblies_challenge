(ns scramblies-challenge.api
  (:require [clojure.set :refer [subset?]]))


(defn valid? [s]
  (if (re-matches #"[a-z]+" s)
    true
    false))

(defn scramble-search [a-freqs b]
  (loop [freqs a-freqs
         letters-b b]
    (if (empty? letters-b)
      true
      (let [c (first letters-b)
            freq (get freqs c)]
        (cond
          (or (nil? freq)
              (zero? freq)) false
          :else (recur (update freqs c dec)
                       (rest letters-b)))))))

(defn scramble [#^String a #^String b]
  (if (> (count b) (count a))
    false
    (scramble-search (frequencies a)
                     b)))
