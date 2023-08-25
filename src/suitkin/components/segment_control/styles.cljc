(ns suitkin.components.segment-control.styles
  (:require [stylo.core :refer [c]]))

(def root
  (c {:border        "1px solid var(--basic-gray-2)"
      :width         "fit-content"
      :display       "flex"
      :align-items   "center"
      :border-radius "4px"
      :padding       "1px"}
     [:space-x "1px"]
     [["input"
       {:opacity "0"
        :position "absolute"}]
      ["label:has(> input:checked)"
       {:background "var(--basic-gray-1)"
        :color      "var(--text-default)"}]]))

(def item
  (c {:line-height   "20px"
      :font-family   "Inter"
      :font-weight   "500"
      :font-size     "14px"
      :text-align    "center"
      :padding       "4px 16px"
      :cursor        "pointer"
      :border-radius "2px"
      :color         "var(--basic-gray-5)"
      :position      "relative"}
     [:hover
      {:background "var(--basic-gray-0)"}]))
