(ns suitkin.components.sidebar.view
  (:require
   [suitkin.components.sidebar.model  :as m]
   [suitkin.components.sidebar.styles :as s]
   [stylo.core :refer [c]]
   [suitkin.utils                     :as u]))

(defn details-constructor
  [element item]
  (when element
    #?(:cljs
       (do (new js/Accordion element)
           (when (m/open-menu-item? item)
             (set! (.-open element) true)
             )))))

(defn menu-item
  [item]
  [:a (merge {:class [s/menu-item (when (:active item) "item-active")]} (dissoc item :items :img))
   [:img {:src (u/public-src (:img item)) :width "16px"}]
   [:span {:class (c :w-full :truncate {:color "var(--basic-gray-7)"})} (:title item)]
   (when (:items item)
     [:img.chevron {:src (u/public-src "/suitkin/img/icon/ic-chevron-right-16.svg")}])])

(defn menu-items
  [node]
  [:ul {:class s/content-items}
   (for [item (:items node)]
     [:li {:class s/content-item :key (hash item)}
      (cond
        (:items item)
        [:details {:ref #(details-constructor % item)}
         [:summary [menu-item item]]
         [:div.content [menu-items item]]]
        (:divider item)
        [:hr {:class s/divider}]
        (:space item)
        [:hr {:class (c [:pb "4px"])}]
        :else [menu-item item])])])

(defn component
  [properties]
  [:aside {:class s/root}
   [:div {:class s/header}  (:logo properties)]
   [:div {:class s/content} [menu-items (:menu properties)]]
   [:div {:class s/submenu} [menu-items (:submenu properties)]]])
