(ns suitkin.components.sidebar.view
  (:require
   [re-frame.core :as rf]
   [stylo.core :refer [c]]
   [suitkin.components.sidebar.model :as m]
   [suitkin.components.sidebar.styles :as s]
   [suitkin.utils :as u]))

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
                        (c :flex :justify-center))
                      (:class item)]}
             (dissoc item :items :img :open :active :title :class))
   (when (:img item)
     [:img {:src (u/public-src (:img item)) :width "18"}])
   (when (:active item)
     [:data {:hidden true :data-key :active} (:active item)])
   (when (:title item)
     [:span {:data-key :label :class (c :w-full :truncate {:color "var(--basic-gray-7)"})} (:title item)])
   (when (:items item)
     [:img.chevron {:src (u/public-src "/suitkin/img/icon/ic-chevron-right-16.svg")}])])

(defn menu-items
  [node reset-inside-items-event]
  [:ul {:class s/content-items :data-array :items}
   (for [item (:items node)]
     [:li {:class s/content-item :key (or (:id item) (:title item) (hash item))}
      (cond
        (:items item)
        (do
          (when (:open item)
            (rf/dispatch [reset-inside-items-event (:items item)]))
          [menu-item
           (assoc
             item
             :on-click
             (fn [_]
               (rf/dispatch [reset-inside-items-event (:items item)])))])

        (:divider item)
        [:hr {:class s/divider}]

        (:space item)
        [:hr {:class (c [:pb "4px"])}]

        :else
        [menu-item
         (assoc
           item
           :on-click
           (fn [_]
             (rf/dispatch [reset-inside-items-event nil])))])])])

(defn component
  [properties inside-menu reset-inside-menu-event]
    [:<>
     [:aside {:data-object ::component :class [s/root (:class properties)]}
      [:div {:class [s/header (:class-header properties)]} (:logo properties)]
      [:div {:data-object :menu :class s/content}
       [menu-items (:menu properties) reset-inside-menu-event]]
      (when (:submenu properties)
        [:div {:class s/submenu :data-object :submenu}
         [menu-items (:submenu properties) reset-inside-menu-event]])]
     (when (some? inside-menu)
       [:aside {:class [s/root (:class properties) (c {:margin-right "20px"})]}
        [menu-items {:items inside-menu} reset-inside-menu-event]])])
