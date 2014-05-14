(defproject twenty48 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2202"]
                 [reagent "0.4.2"]]
  :profiles {:dev {:plugins [[lein-midje "3.1.3"]
                             [lein-cljsbuild "1.0.3"]]
                   :dependencies [[midje "1.6.3" :exclusions [org.clojure/clojure]]]
                   :cljsbuild {:builds [{:source-paths ["src"]
                                         :compiler {:output-to "out/twenty48.js"
                                                    :preamble ["reagent/react.js"]
                                                    :optimizations :whitespace
                                                    :pretty-print true}}]}}})
