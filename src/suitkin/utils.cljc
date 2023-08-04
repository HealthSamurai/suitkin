(ns suitkin.utils)

#?(:cljs (goog-define CLASSPATH "")
   :clj  (def CLASSPATH ""))

(defn public-src
  [src]
  (str CLASSPATH src))
