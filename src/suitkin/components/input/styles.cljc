(ns suitkin.components.input.styles
  (:require [stylo.core :refer [c]]))

(def root
  (c {:outline       "1px solid var(--basic-gray-3)"
      :font-size     "14px"
      :font-family   "Inter"
      :line-height   "20px"
      :font-weight   "500"
      :min-width     "238px"
      :display       "block"
      :border-radius "4px"}
     [:placeholder
      {:color "var(--basic-gray-3)"}]
     [[:&:focus
       {:outline "2px solid var(--basic-blue-3)"}]
      [:&:invalid
       {:outline "2px solid var(--basic-red-5)"}]
      [:&:disabled
       {:background "var(--bg-disabled)"
        :outline    "1px solid var(--basic-gray-2)"
        :cursor     "not-allowed"}]]))

(def narrow
  (c {:padding "5px 11px"}))

(def default
  (c {:padding "9px 11px"}))

(def icon-wrapper
  (c {:position "relative"
      :width    "max-content"}
     [[".left + input"
       {:padding-left "35px"}]
      [".left"
       {:position "absolute"
        :opacity "0.6"
        :top "50%"
        :transform "translate(0, -50%)"
        :left "11px"}]
      ["input:has(+ .right):focus::-webkit-search-cancel-button"
       {:margin-right "26px"
        :padding-right "10px"
        :border-right "1px solid var(--basic-gray-3)"
        }]
      [".right"
       {:position "absolute"
        :display  "flex"
        :align-items "center"
        :right    "11px"
        :padding-left "10px"
        :opacity "0.6"
        :top "50%"
        :transform "translate(0, -50%)"}
       [:img {:height "16px"}]]]))
