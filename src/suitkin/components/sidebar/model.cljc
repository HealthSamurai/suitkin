(ns suitkin.components.sidebar.model
  (:require [suitkin.utils :as utils]))

(defn open-menu-item?
  [item]
  (some :active (:items item)))

(defn on-open-menu
  [item]
  (utils/set-storage-item (:title item) true))

(defn on-close-menu
  [item]
  (utils/remove-storage-item (:title item)))


(defn open-before?
  [item]
  (utils/get-storage-item (:title item)))
