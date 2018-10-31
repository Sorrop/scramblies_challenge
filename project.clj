(defproject scramblies-challenge "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.238"]
                 [hiccup "1.0.5"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]
                 [ring-middleware-format "0.7.2"]
                 [ring "1.7.1"]
                 [com.stuartsierra/component "0.3.2"]
                 [reagent "0.7.0"]
                 [re-frame "0.10.5"]
                 [cljs-ajax "0.7.5"]
                 [ring/ring-mock "0.3.2"]
                 [org.clojure/test.check "0.10.0-alpha3"]]
  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-sass "0.4.0"]]
  :source-paths ["src/clj" "src/cljs"]

  :main scramblies-challenge.core

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :figwheel {:css-dirs ["resources/public/css"]}

  :repl-options {:nrepl-middleware [cider.piggieback/wrap-cljs-repl]}
  :profiles {:dev {:main user
                   :source-paths ["src/" "dev/"]
                   :dependencies [[org.clojure/tools.namespace "0.2.11"]
                                  [com.stuartsierra/component.repl "0.2.0"]
                                  [org.clojure/tools.nrepl "0.2.13"]
                                  [binaryage/devtools "0.9.10"]
                                  [figwheel-sidecar "0.5.16"]
                                  [cider/piggieback "0.3.5"]
                                  [cljs-ajax "0.7.5"]]}
             :uberjar {:aot :all
                       :prep-tasks ["compile" ["cljsbuild" "once" "min"]]}}
  :sass {:src "resources/public/sass"
         :output-directory "resources/public/css"}
  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src/cljs"]
     :figwheel     {:on-jsload "scramblies-challenge.core/mount-root"}
     :compiler     {:main                 scramblies-challenge.core
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :asset-path           "js/compiled/out"
                    :source-map-timestamp true
                    :preloads             [devtools.preload]
                    :external-config      {:devtools/config {:features-to-install :all}}
                    }}

    {:id           "min"
     :source-paths ["src/cljs"]
     :compiler     {:main            scramblies-challenge.core
                    :output-to       "resources/public/js/compiled/app.js"
                    :optimizations   :advanced
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}


    ]})
