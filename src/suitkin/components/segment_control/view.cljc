(ns suitkin.components.segment-control.view
  (:require [suitkin.components.segment-control.styles :as s]))

(defn component
  [properties]
  [:div {:class s/root}
   (for [item (:items properties)] ^{:key (hash (:title item))}
     [:label {:class s/item}
      (:title item)
      [:input {:type           "radio"
               :name           (:id properties)
               :defaultChecked (:active item)}]])])
