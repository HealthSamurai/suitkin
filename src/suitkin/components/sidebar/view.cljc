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
       (do
         (set! (.-ontoggle ^js/Object element)
               (fn [^js/Object event]
                 (if (= "open" (.-newState event))
                   (m/on-open-menu item)
                   (m/on-close-menu item))))
         (when (or (:open item) (m/open-before? item))
           (set! (.-open element) true))))))

(defn menu-item
  [item]
  [:a (merge {:class [s/menu-item
                      (when (:active item) "item-active")
                      (when-not (:title item)
                        (c :flex :justify-center))]}
             (dissoc item :items :img :open :title))
   (when (:img item)
     [:img {:src (u/public-src (:img item)) :width "18"}])
   (when (:active item)
     [:data {:hidden true :data-key :active} (:active item)])
   (when (:title item)
     [:span {:data-key :label :class (c :w-full :truncate {:color "var(--basic-gray-7)"})} (:title item)])
   (when (:items item)
     [:img.chevron {:src (u/public-src "/suitkin/img/icon/ic-chevron-right-16.svg")}])])

(defn menu-items
  [node]
  [:ul {:class s/content-items :data-array :items}
   (for [item (:items node)]
     [:li {:class s/content-item :key (or (:title item) (hash item))}
      (cond
        (:items item)
        [:details {:ref #(details-constructor % item)}
         [:summary
          (when (or (:open item) (m/open-before? item))
            [:data {:hidden true :data-key :open} (:open item)])
          [menu-item item]]
         [:div.content [menu-items item]]]
        (:divider item)
        [:hr {:class s/divider}]
        (:space item)
        [:hr {:class (c [:pb "4px"])}]
        :else [menu-item item])])])

(defn component
  [properties]
  [:aside {:data-object ::component :class [s/root (:class properties)]}
   [:div {:class [s/header (:class-header properties)]}  (:logo properties)]
   [:div {:data-object :menu :class s/content} [menu-items (:menu properties)]]
   (when (:submenu properties)
     [:div {:class s/submenu :data-object :submenu} [menu-items (:submenu properties)]])])
