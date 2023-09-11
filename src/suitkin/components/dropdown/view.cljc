(ns suitkin.components.dropdown.view
  (:require
   [suitkin.components.input.view :as input]
   [suitkin.utils :as u]
   [suitkin.components.sidebar.view]
   [suitkin.components.dropdown.styles :as s]))

(defn component
  [properties]
  [:div {:class s/root}
   [input/component
    {:placeholder "Choose weekday"
     :s/right [:img {:src (u/public-src "/suitkin/img/icon/ic-chevron-down-16.svg")}]}]
   [:div {:class s/menu-items}
    (for [item (:items properties)] ^{:key (:value item)}
      [:div {:class s/menu-item} (:title item)])]])
