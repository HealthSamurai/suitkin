(ns suitkin.utils
  #?(:cljs (:require [reagent.core])
     :clj  (:import java.net.URLEncoder
                    java.nio.charset.StandardCharsets)))

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

(defn ratom
  [value]
  #?(:cljs (reagent.core/atom value)
     :clj  (atom value)))

(defn target-value
  [event]
  #?(:cljs (.. event -target -value)
     :clj  (-> event :target :value)))

(defn encode-uri
  [value]
  #?(:cljs (js/encodeURI value)
     :clj  (java.net.URLEncoder/encode value java.nio.charset.StandardCharsets/UTF_8)))

(defn decode-uri
  [value]
  #?(:cljs (js/decodeURI value)
     :clj  nil))

(defn edn->json-pretty
  [edn]
  #?(:cljs (js/JSON.stringify (clj->js edn) nil 2)
     :clj  nil))

(defn json-string->edn
  [json-string]
  #?(:cljs (try (js->clj (js/JSON.parse json-string)
                         :keywordize-keys true)
                (catch js/Error e
                  (prn "error" ::json-string->edn)
                  {}))
     :clj nil))
