(ns suitkin.components.sidebar
  (:require [stylo.core :refer [c]]
            [suitkin.utils :as u]))

(def sidebar-class
  (c [:w "240px"]
     [:h "100vh"]
     {:color "var(--basic-gray-7)"
      :font-size "14px"
      :display "flex"
      :flex-flow "column"
      :letter-spacing "0.33px"
      :word-spacing   "-1px"
      :line-height "20px"
      :border-right "1px solid var(--basic-gray-2,red)"}))

(def sidebar-submenu
  (c
   [:p "4px"]
   [:mb "16px"]
   {:border-top "1px solid var(--basic-gray-2,red)"}))

(def sidebar-aidbox-logo-class
  (c :font-extrabold
     :flex
     :items-center
     :h-auto
     [:space-x "4px"]
     [:py "14px"]
     [:px "16px"]
     {:font-size "16px" :letter-spacing "0.48px" :color "#353B50"}))

(def sidebar-items
  (c :w-full
     [[:summary {:padding "6px 16px 6px 16px" :border-radius "4px"}
       [:&:hover {:background-color "var(--basic-gray-0)"}]
       [:&:active {:background-color "var(--basic-gray-1)"}]]
      ["a.item-active"
       {:background-color "var(--basic-gray-1)"}]
      [:a {:padding "6px 16px 6px 16px" :border-radius "4px"}
       [:&:hover {:background-color "var(--basic-gray-0)"}]
       [:&:active {:background-color "var(--basic-gray-1)"}]]]))

(def menu-item-class
  (c :flex
     :select-none
     :cursor-pointer
     [[:.chevron
       {:transition ".15s transform ease"}]
      ["details[open]"
       [:.chevron {:transform "rotate(90deg)"}]]]))

(defn menu-item
  [item]
  [:div {:class (c :flex :truncate)}
   [:img {:src (u/public-src (:img item)) :class (c [:pr "8px"])}]
   [:span (:title item)]])

(defn menu-items
  [node & [padding]]
  (let [padding (or padding 16)]
    [:ul {:class sidebar-items}
     (for [item (:items node)] ^{:key (:title item)}
       [:li {:class menu-item-class}
        (if (:items item)
          [:details {:class (c :w-full) :ref (fn [el] (when el #?(:cljs (new js/Accordion el))))}
           [:summary {:class (c :flex :justify-between) :style {:padding-left (str padding "px")}}
            [menu-item item]
            [:img.chevron {:src (u/public-src "/suitkin/img/icon/chevron.svg")}]]
           [:div.content
            [menu-items item (+ padding 24)]]]
          [:a {:class [(c :w-full) (when (:active item) "item-active")]
               :href  (:href item)
               :target (when (:new-tab? item) "_blank")
               :style {:padding-left (str padding "px")}}
           [menu-item item]])])]))

(defn component
  [properties]
  [:aside {:class sidebar-class}
   [:div {:class sidebar-aidbox-logo-class}
    [:img {:src (u/public-src (:logo properties))}]
    [:span (:brand properties)]]
   [:div {:class (c :flex [:mx "4px"] [:mt "4px"] {:overflow-y "auto" :height "100%"})}
    [menu-items (:menu properties)]]
   [:div {:class sidebar-submenu}
    [menu-items (:submenu properties)]]])
