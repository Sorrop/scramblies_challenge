(ns scramblies-challenge.views
  (:require [reagent.ratom :refer [atom]]
            [clojure.string :refer [blank?]]
            [scramblies-challenge.utils :refer [clog validate-inputs remote-call]]))


(defn result-msg [result]
  (str "The result is " result))

(defn input-cnt [cnt]
  [:input {:type "text"
           :value @cnt
           :on-change (fn [e]
                        (let [content (-> e
                                          .-target
                                          .-value)]
                          (reset! cnt content)))}])

(defn button-click-handler [l-str r-str result]
  (fn [_]
    (let [a-str @l-str
          b-str @r-str
          [valid? msg] (validate-inputs a-str b-str)]
      (reset! result nil)
      (cond
        (not valid?) (js/alert msg)
        :else (remote-call {:left-string a-str
                            :right-string b-str}
                           #(reset! result %))))))

(defn main-panel []
  (let [l-str (atom "")
        r-str (atom "")
        result (atom nil)]
    (fn []
      [:div#scramble
       [:fieldset
        [:legend "Provide inputs for scrambling"]
        [:div#inputs
         [:div.input
          [:label "First String"]
          (input-cnt l-str)]
         [:div.input
          [:label "Second String"]
          (input-cnt r-str)]
         [:button {:on-click (button-click-handler l-str r-str result)}
          "Scramble"]]]
       (when @result
         [:p (result-msg @result)])])))
