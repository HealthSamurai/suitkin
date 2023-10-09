(ns suitkin.utils)

#?(:cljs (goog-define CLASSPATH "")
   :clj  (def CLASSPATH ""))

(defonce ^:export public-prefix nil)

(defn public-src
  [src]
  (str public-prefix CLASSPATH src))
