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
  (str #?(:cljs (try js/suitkin_public_prefix (catch js/ReferenceError _ ""))
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

(defn prevent-default
  [event]
  #?(:cljs (.preventDefault event)
     :clj  nil))

(defn ratom
  [value]
  #?(:cljs (reagent.core/atom value)
     :clj  (atom value)))

(defn get-event-target
  [event]
  #?(:cljs (.. event -target)
     :clj  (-> @event :target)))

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
                    (prn "error" ::json-string->edn)))
       :clj (cheshire.core/parse-string json-string keyword ))))

(defn stop-propagation
  [event]
  #?(:cljs (.stopPropagation event)
     :clj  nil))

(defn get-element-by-id
  [id]
  #?(:cljs (js/document.getElementById id)
     :clj  nil))

(defn show-modal
  [id]
  #?(:cljs (.showModal (get-element-by-id id))
     :clj  nil))

(defn close-modal
  [id]
  #?(:cljs (.close (get-element-by-id id))
     :clj  nil))

(defn get-component-properties
  [nodes]
  (when-let [arg1 (first nodes)]
    (when (map? arg1) arg1)))

(defn get-component-children
  [properties nodes]
  (if properties (next nodes) nodes))

(defn merge-class-property
  [class-a class-b]
  (cond
    (and (vector? class-a)
         (vector? class-b))
    (into class-a class-b)

    (vector? class-a)
    (conj class-a class-b)

    (vector? class-b)
    (cons class-a class-b)

    :else [class-a class-b]))

(defn merge-component-properties
  [properties user-properties]
  (merge
   properties
   user-properties
   {:class (merge-class-property
            (:class properties)
            (:class user-properties))}))
