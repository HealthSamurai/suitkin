(ns suitkin.components.dialog.style
  (:require [stylo.core :refer [c]]))

(def root-class
  (c [:z 10]
     [:inset 0]
     [:rounded :lg]
     [:p 0]
     [:backdrop {:background-color "rgb(107 114 128)"} [:opacity 75]]))

(def dangerous-root-class
  (c [:w-max "32rem"] [:p "1.5rem"]))

(def dangerous-title
  (c :text-base :font-semibold {:font-family "Inter"}))

(def dangerous-content
  (c [:mt "0.5rem"]
     [:h-min "3rem"]
     {:color "var(--basic-gray-5)"}))

(def dangerous-body
  (c :flex 
     [:space-x 4]))

(def dangerous-icon
  (c [:w-min "2.5rem"]
     [:h "2.5rem"]
     [:p 2]
     [:rounded :full]
     :flex
     :justify-center
     {:background-color "var(--basic-red-1)"}
     [[:img {:filter "brightness(0) saturate(100%) invert(62%) sepia(78%) saturate(6160%) hue-rotate(343deg) brightness(95%) contrast(93%)"}]]))

(def dangerous-button-menu
  (c :flex :justify-end [:space-x 3] [:mt "1rem"]))
