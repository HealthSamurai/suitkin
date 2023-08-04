(ns suitkin.components.sidebar.styles
  (:require [stylo.core :refer [c]]))


(def root
  (c [:w "240px"]
     [:h "100vh"]
     {:color "var(--basic-gray-7)"
      :background-color "white"
      :font-size "14px"
      :display "flex"
      :flex-flow "column"
      :letter-spacing "0.33px"
      :word-spacing   "-1px"
      :line-height "20px"
      :border-right "1px solid var(--basic-gray-2,red)"}))

(def submenu
  (c
   [:p "4px"]
   [:mb "16px"]
   {:border-top "1px solid var(--basic-gray-2,red)"}))

(def header
  (c :font-extrabold
     :flex
     :items-center
     :h-auto
     [:space-x "4px"]
     [:py "14px"]
     [:px "16px"]
     {:font-size "16px" :letter-spacing "0.48px" :color "#353B50"}))

(def content-items
  (c :w-full
     [[:details
       {:width "100%"}]
      ["a.item-active"
       {:background-color "var(--basic-gray-1)"}]
      ["a:hover"
       {:color "inherit"}]
      ["a:not(.item-active):hover"
       {:background-color "var(--basic-gray-0)" :color "inherit"}]
      #_["a:active"
       {:background-color "var(--basic-gray-1)!important"}]
      [:a {:width "100%" :border-radius "4px" :cursor "pointer"}]]))

(def menu-item
  (c :flex :truncate :justify-between
     {:padding "6px 16px 6px 16px"
      :border-radius "4px"}
     [[:img {:display "inline-block"}]]))

(def content-item
  (c :flex
     :select-none
     :cursor-pointer
     [[:.chevron
       {:transition ".15s transform ease"}]
      ["details[open]"
       [:.chevron {:transform "rotate(90deg)"}]]]))

(def content
  (c :flex [:mx "4px"] [:mt "4px"] {:overflow-y "auto" :height "100%"}))
