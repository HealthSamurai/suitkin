(ns suitkin.components.dropdown.model
  (:require [clojure.string :as str]))

(defn build-package-filter-fn [search-string]
  (let [string-parts (str/split search-string #" ")
        preds (map (fn [string-part] #(str/includes? (str/lower-case %) string-part)) string-parts)]
    (apply every-pred preds)))
