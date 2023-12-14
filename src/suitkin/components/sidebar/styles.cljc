(ns suitkin.components.sidebar.styles
  (:require [stylo.core :refer [c]]))


(def root
  (c [:w "240px"]
     [:h "100vh"]
     :flex
     {:color            "var(--basic-gray-7)"
      :background-color "white"
      :font-family      "Inter"
      :font-size        "14px"
      :font-weight      "400"
      :line-height      "20px"
      :flex-flow        "column"
      :border-right     "1px solid var(--basic-gray-2)"}))

(def submenu
  (c
   [:p "4px"]
   [:pb "0px"]
   [:mb "16px"]
   {:border-top "1px solid var(--basic-gray-2)"}))

(def header
  (c :flex
     :class
     :items-center
     [:space-x "4px"]
     [:py "16px"]
     [:px "17px"]))

(def content-items
  (c :w-full
     [[:details {:width "100%"}]
      ["ul.root > li:last-child" {:margin-bottom "100px"}]
      ["a.item-active" {:background-color "var(--basic-gray-1)"}]
      ["a:hover" {:color "inherit"}]
      ["a:not(.item-active):hover" {:background-color "var(--basic-gray-0)" :color "inherit"}]
      ["li a" {:padding-left "16px"}]
      ["li li a" {:padding-left "40px"}]
      ["li li li a" {:padding-left "64px"}]
      [:a {:width "100%" :border-radius "4px" :cursor "pointer"}]]))

(def menu-item
  (c :flex
     :w-full
     :items-center
     [:space-x "8px"]
     {:padding "6px 16px 6px 0px"
      :border-radius "4px"}
     [:hover
      {:text-decoration "none"}]))

(def divider
  (c [:my "8px"] [:mx "6px"] {:color "var(--basic-gray-2)"} :w-full))

(def content-item
  (c :flex
     :select-none
     :cursor-pointer
     [[:.chevron {:transition ".15s transform ease"
                  :filter "invert(44%) sepia(8%) saturate(656%) hue-rotate(191deg) brightness(85%) contrast(88%)"}]
      ["details[open]" [:.chevron {:transform "rotate(90deg)"}]]]))

(def content
  (c :flex
     [:ml "4px"]
     [:mr "4px"]
     [:mt "4px"]
     {:overflow-y "auto" :height "100%"}))
