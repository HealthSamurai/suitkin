(ns suitkin.components.dropdown.model
  (:require [clojure.string :as str]
            [re-frame.core :as rf]))

(defn build-package-filter-fn [search-string]
  (let [string-parts (str/split search-string #" ")
        preds (map (fn [string-part] #(str/includes? (str/lower-case %) string-part)) string-parts)]
    (apply every-pred preds)))

(rf/reg-sub
 ::open?
 (fn [db [_ id]]
   (boolean (get-in db [::state id]))))

(rf/reg-event-db
 ::open
 (fn [db [_ id]]
   (assoc-in db [::state id] true)))

(rf/reg-event-db
 ::close
 (fn [db [_ id]]
   (assoc-in db [::state id] false)))
