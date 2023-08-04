(ns suitkin.components.sidebar.view
  (:require
   [suitkin.components.sidebar.model  :as m]
   [suitkin.components.sidebar.styles :as s]
   [stylo.core :refer [c]]
   [suitkin.utils                     :as u]))

(defn details-animation
  [element]
  (when element
    #?(:cljs (new js/Accordion element))))

(defn menu-item
  [item & [padding]]
  [:div {:class s/menu-item :style {:padding-left (str padding "px")}}
   [:a (select-keys item [:on-click :href :title])
    [:img {:src (u/public-src (:img item)) :class (c [:pr "8px"])}]
    [:span (:title item)]]
   (when (:items item)
     [:img.chevron {:src (u/public-src "/suitkin/img/icon/chevron.svg")}])])

(defn menu-items
  [node & [padding]]
  (let [padding (or padding 16)]
    [:ul {:class s/content-items}
     (for [item (:items node)]
       [:li {:class s/content-item :key (:title item)}
        (if (:items item)
          [:details {:ref details-animation :open (m/open-menu-item? item)}
           [:summary [menu-item item padding]]
           [:div.content [menu-items item (+ padding 24)]]]
          [:a {:class [(when (:active item) "item-active")]
               :href  (:href item)
               :target (when (:new-tab? item) "_blank")}
           [menu-item item padding]])])]))

(defn component
  [properties]
  [:aside {:class s/root}
   [:div {:class s/header} (:logo properties) (:brand properties)]
   [:div {:class s/content} [menu-items (:menu properties)]]
   [:div {:class s/submenu} [menu-items (:submenu properties)]]])
