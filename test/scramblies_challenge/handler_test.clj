(ns scramblies-challenge.handler-test
  (:require [clojure.edn :as edn]
            [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [clojure.test :refer :all]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :refer [defspec]]
            [scramblies-challenge.handler :refer :all]
            [scramblies-challenge.api :refer [scramble]]
            [scramblies-challenge.views :refer [main]]))

(deftest test-app-routes
  (testing "main route"
    (let [response (app-handler (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) (main)))))

  (testing "not-found route"
    (let [response (app-handler (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))


(defn lower-case? [c]
  (and (>= (int c) (int \a))
       (<= (int c) (int \z))))

(def gen-lower-case (gen/such-that lower-case? gen/char-alpha 100))

(def scramble-true-cases
  (prop/for-all [a-chars (gen/such-that (comp not empty?)
                                        (gen/vector gen-lower-case))]
                (let [a-str (apply str a-chars)
                      b-chars (random-sample 0.5 a-chars)
                      b-str (apply str b-chars)]
                  (= (scramble a-str b-str) true))))

(defspec scramble-true-cases-test 5000 scramble-true-cases)

(defn freqs->str [fs]
  (->> (for [[ch fr] fs]
         (repeat fr ch))
       (apply concat)
       (apply str)))

(defn mess-with-freqs [fs]
  (let [part-fs (select-keys fs (random-sample 0.5 (keys fs)))]
    (zipmap (keys part-fs) (map inc (vals part-fs)))))

(def scramble-false-cases
  (prop/for-all [a-chars (gen/such-that (comp not empty?)
                                        (gen/vector gen-lower-case))]
                (let [a-str (apply str a-chars)
                      a-freqs (frequencies a-str)
                      b-freqs (mess-with-freqs a-freqs)
                      b-str (freqs->str b-freqs)]
                  (or (clojure.string/blank? b-str)
                      (= (scramble a-str b-str) false)))))

(defspec scramble-false-cases-test 5000 scramble-false-cases)

(def scramble-api-route
  (prop/for-all [a-valid (gen/vector gen-lower-case)
                 b-valid (gen/vector gen-lower-case)
                 b-invalid (gen/such-that (comp not empty?) (gen/vector gen/char))]
                (let [a-str (apply str a-valid)
                      b-str (->> (rand-nth [b-valid b-invalid])
                                 (apply str))
                      mock-request (mock/request :get "/api/scramble"
                                                 {:left-string a-str
                                                  :right-string b-str})
                      response (app-handler mock-request)
                      result (-> response
                                 :body
                                 slurp
                                 (edn/read-string))]
                  (or (= result :invalid)
                      (= result true)
                      (= result false)))))

(defspec scramble-api-route-test 2000 scramble-api-route)
