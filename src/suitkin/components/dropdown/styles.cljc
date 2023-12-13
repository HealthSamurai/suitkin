(ns suitkin.components.dropdown.styles
  (:require [stylo.core :refer [c]]))

(def root
  (c {:width "fit-content"}))

(def menu-items
  (c {:border-radius "4px"
      :margin-top    "5px"
      :padding       "4px"
      :outline       "1px solid var(--basic-gray-1)"
      :font-family   "Inter"
      :font-size     "14px"
      :font-weight   "500"
      :line-height   "20px"
      :box-shadow    "8px 12px 24px 2px rgba(0, 0, 0, 0.15)"}
     [:space-y "2px"]))

(def menu-item
  (c {:padding "6px 15px"
      :border-radius "4px"}
     [:hover {:background "var(--basic-gray-0)"
              :cursor     "pointer"}]))

(def menu-item-empty
  (c {:padding "6px 15px"
      :border-radius "4px"
      :color "var(--basic-gray-4)"
      :cursor "not-allowed"}))
