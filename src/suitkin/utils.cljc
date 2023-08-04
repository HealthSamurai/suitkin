(ns suitkin.utils
  (:require [clojure.walk]))

#?(:cljs (goog-define GH-PAGES false)
   :clj  (def GH-PAGES false))

(defn public-src
  [src]
  (if GH-PAGES (str "/suitkin" src) src))
