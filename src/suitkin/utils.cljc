(ns suitkin.utils)

#?(:cljs (goog-define CLASSPATH "")
   :clj  (def CLASSPATH ""))

(defn public-src
  [src]
  (str #?(:cljs js/suitkin_public_prefix
          :clj  "") CLASSPATH src))
