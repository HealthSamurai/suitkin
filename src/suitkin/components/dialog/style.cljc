(ns suitkin.components.dialog.style
  (:require [stylo.core :refer [c]]))

(def root-class
  (c [:z 10]
     [:inset 0]
     [:rounded :lg]
     [:p 0]
     [:pseudo "[open]" {:animation "modal-open-scale 0.25s ease-out"}]
     [:backdrop {:background-color "rgb(107 114 128)"
                 :opacity   "0.75"
                 :animation "modal-opacity 0.25s ease-out"}]))

(def dangerous-root-class
  (c [:w-max "32rem"]  [:pb "16px"]))

(def dangerous-title
  (c {:font-size "22px"
      :font-family "Metropolis"
      :line-height "24px"
      :border-bottom "1px solid #E5E7EB"}
     [:py "20px"] [:px "24px"]))

(def dangerous-content
  (c [:mt "0.5rem"]
     [:p "24px"]
     [:h-min "3rem"]
     {:color "var(--basic-gray-5)"
      :font-weight "400"
      :line-height "21px"}))

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
  (c :flex  [:px "24px"] [:space-x 3] [:mt "1rem"]))

(def discard-button
  (c [:ml "auto"]
     {:border "1px solid #E5E7EB"
      :background-color "inherit"
      :color "#DF351F"}
     [:hover {:background-color "#E5E7EB"
              :color "#DF351F"}]))

(def cancel-button
  (c {:background-color "inherit"
      :color "#83868E"
      :border "0px"}))
