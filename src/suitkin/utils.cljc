(ns suitkin.utils)

#?(:cljs (goog-define CLASSPATH "")
   :clj  (def CLASSPATH ""))

(defn public-src
  [src]
  (str #?(:cljs js/suitkin_public_prefix
          :clj  "") CLASSPATH src))

(defn log
  [& arguments]
  #?(:cljs (apply js/console.log arguments)
     :clj  (apply println arguments)))

(defn set-storage-item
  [keyname value]
  #?(:cljs (js/localStorage.setItem keyname value)
     :clj  nil))

(defn get-storage-item
  [keyname]
  #?(:cljs (js/localStorage.getItem keyname)
     :clj  nil))

(defn remove-storage-item
  [keyname]
  #?(:cljs (js/localStorage.removeItem keyname)
     :clj  nil))
