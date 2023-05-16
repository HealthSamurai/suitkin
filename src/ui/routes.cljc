(ns ui.routes
  (:require
   [re-frame.db :as db]
   [clojure.string :as str]
   [re-frame.core :as rf]
   [suitkin.zf.window-location :as window-location]
   [route-map.core :as route-map]
   [suitkin.components.variables]
   [suitkin.components.input]
   [suitkin.components.button]
   [suitkin.components.select]
   #?(:cljs [suitkin.components.colors])))

(def routes-info
  [ {:path "input"
    :component :suitkin.components.input/init
    :display "Inputs"}
   {:path "button"
    :component :suitkin.components.button/init
    :display "Buttons"}
   {:path "select"
    :component :suitkin.components.select/init
    :display "Selects"}
   {:path "colors"
    :component :suitkin.components.colors/init
    :display "Colors"}
   {:path "variables"
    :component :suitkin.components.variables/init
    :display "Variables"}])

(def routes
  (into {:. {:. :suitkin.demo/init}}
        (map (fn [info]
               [(:path info) {:. (:component info)}])
             routes-info)))

(defn get-url-alias
  [url]
  (->> (route-map.core/match [:. url] routes)
       (:path)
       (mapv (fn [path] (if (keyword? path) [path] path)))
       (get-in routes)
       (:alias)))


(defn to-query-params [params]
  (->> params
       (map (fn [[k v]] (str (name k) "=" v)))
       (str/join "&")))


(defn href [& parts]
  (let [params (if (map? (last parts)) (last parts) nil)
        parts (if params (butlast parts) parts)
        url (str "/" (str/join "/" (map (fn [x] (if (keyword? x) (name x) (str x))) parts)))]
    (when-not  (route-map/match [:. url] routes)
      (println (str url " is not matches routes")))
    (str "#" url (when (seq (filter second params))
                   (window-location/gen-query-string (into {} (filter second params)))))))


(defn back [fallback]
  {:href (or @(rf/subscribe [:pop-route]) fallback)})
