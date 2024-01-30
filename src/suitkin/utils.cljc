(ns suitkin.utils
  #?(:cljs (:require [reagent.core])
     :clj  (:require [clojure.string]
                     [cheshire.core]))
  #?(:clj  (:import java.net.URLEncoder
                    java.net.URLDecoder)))

#?(:cljs (goog-define CLASSPATH "")
   :clj  (def CLASSPATH ""))

(defn public-src
  [src]
  (str #?(:cljs (try js/suitkin_public_prefix
                     (catch js/Error e ""))
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

(defn ratom
  [value]
  #?(:cljs (reagent.core/atom value)
     :clj  (atom value)))

(defn target-value
  [event]
  #?(:cljs (.. event -target -value)
     :clj  (-> @event :target :value)))

(defn encode-uri
  [value]
  (when (string? value)
    #?(:cljs (js/encodeURI value)
       :clj  (clojure.string/replace (java.net.URLEncoder/encode value "UTF-8")
                                     "+" "%20"))))

(defn decode-uri
  [value]
  (when value
    #?(:cljs (js/decodeURI value)
       :clj  (java.net.URLDecoder/decode value))))

(defn edn->json-pretty
  [edn]
  #?(:cljs (js/JSON.stringify (clj->js edn) nil 2)
     :clj  (cheshire.core/generate-string edn {:pretty true})))

(defn json-string->edn
  [json-string]
  (when (seq json-string)
    #?(:cljs (try (js->clj (js/JSON.parse json-string)
                           :keywordize-keys true)
                  (catch js/Error e
                    (prn "error" ::json-string->edn)
                    {}))
       :clj (cheshire.core/parse-string json-string keyword ))))
