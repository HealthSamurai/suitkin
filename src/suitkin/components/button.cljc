(ns suitkin.components.button
  (:require
   [suitkin.utils :as u]
   [stylo.core :refer [c]]))


(def primary-default
  (c {:background-color "var(--basic-gray-4)" :color "var(--white)"}
     [:hover {:background-color "var(--basic-gray-5)"}]
     [:active {:background-color "var(--basic-gray-6)"}]
     [:disabled {:background-color "var(--basic-gray-2)" :color "var(--basic-gray-4)" :cursor "not-allowed"}
      [[:.button-icon {:filter "invert(55%) sepia(9%) saturate(277%) hue-rotate(186deg) brightness(95%) contrast(86%)"}]]]
     [[:.button-icon {:filter "invert(100%) sepia(17%) saturate(2%) hue-rotate(233deg) brightness(108%) contrast(101%)"}]]))

(def secondary-default
  (c {:background-color "var(--basic-gray-0)" :color "var(--basic-gray-7)" :border "1px solid var(--basic-gray-4)"}
     [:hover {:background-color "var(--basic-gray-1)"}]
     [:active {:background-color "var(--basic-gray-2)"}]
     [:disabled {:background-color "rgba(219, 221, 227, 0.15)" :color "var(--basic-gray-3)" :border "1px solid var(--basic-gray-3)"}
      [[:.button-icon {:filter "invert(96%) sepia(2%) saturate(881%) hue-rotate(190deg) brightness(84%) contrast(83%)"}]]]
     [[:.button-icon {:filter "invert(12%) sepia(21%) saturate(974%) hue-rotate(187deg) brightness(93%) contrast(91%)"}]]))

(def tertiary-default
  (c {:background-color "inherit" :color "var(--basic-gray-7)"}
     [:hover {:background-color "var(--basic-gray-1)"}]
     [:active {:background-color "var(--basic-gray-2)"}]
     [:disabled {:background-color "inherit" :color "var(--basic-gray-3)"}
      [[:.button-icon {:filter "invert(96%) sepia(2%) saturate(881%) hue-rotate(190deg) brightness(84%) contrast(83%)"}]]]
     [[:.button-icon {:filter "invert(12%) sepia(21%) saturate(974%) hue-rotate(187deg) brightness(93%) contrast(91%)"}]]))

(def primary-dangerous
  (c {:background-color "var(--basic-red-5)" :color "var(--white)"}
     [:hover {:background-color "var(--basic-red-7)"}]
     [:active {:background-color "var(--basic-red-8)"}]
     [:disabled {:background-color "var(--basic-gray-2)" :color "var(--basic-gray-4)"}
      [[:.button-icon {:filter "invert(55%) sepia(9%) saturate(277%) hue-rotate(186deg) brightness(95%) contrast(86%)"}]]]
     [[:.button-icon {:filter "invert(100%) sepia(17%) saturate(2%) hue-rotate(233deg) brightness(108%) contrast(101%)"}]]))

(def secondary-dangerous
  (c {:background-color "rgba(254, 231, 228, 0.15)" :color "var(--basic-red-6)" :border "1px solid var(--basic-red-5)"}
     [:hover {:background-color "rgba(254, 231, 228, 0.40)"}]
     [:active {:background-color "rgba(254, 231, 228, 0.80)"}]
     [:disabled {:background-color "rgba(219, 221, 227, 0.15)" :color "var(--basic-gray-3)" :border "1px solid var(--basic-gray-3)"}
      [[:.button-icon {:filter "invert(96%) sepia(2%) saturate(881%) hue-rotate(190deg) brightness(84%) contrast(83%)"}]]]
     [[:.button-icon {:filter "invert(16%) sepia(78%) saturate(3482%) hue-rotate(357deg) brightness(106%) contrast(94%)"}]]))

(def tertiary-dangerous
  (c {:background-color "inherit" :color "var(--basic-red-6)"}
     [:hover {:background-color "rgba(254, 231, 228, 0.40)"}]
     [:active {:background-color "rgba(254, 231, 228, 0.80)"}]
     [:disabled {:background-color "inherit" :color "var(--basic-gray-3)"}
      [[:.button-icon {:filter "invert(96%) sepia(2%) saturate(881%) hue-rotate(190deg) brightness(84%) contrast(83%)"}]]]
     [[:.button-icon {:filter "invert(16%) sepia(78%) saturate(3482%) hue-rotate(357deg) brightness(106%) contrast(94%)"}]]))

(def button-size-narrow  (c [:px "16px"] [:py "6px"]))
(def button-size-default (c [:px "20px"] [:py "10px"]))

(def base-button-class
  (c [:rounded 4]
     :inline-flex
     :items-center
     [:disabled {:cursor "not-allowed"}]
     [:focus :outline-none]
     {:font-size "14px" :font-family "Inter" :font-weight "400" :line-height "20px"}))

(defn icon
  [src]
  [:img.button-icon {:widht "16px" :height "16px" :class (c [:mr "8px"]) :src (u/public-src src)}])

;; TODO: merge properties
(defn component
  [properties body]
  [:button {:id       (:id properties)
            :disabled (:disabled properties)
            :on-click (:on-click properties)
            :class    [base-button-class
                       (case [(:use properties) (:theme properties)]
                         ["primary" "default"]     primary-default
                         ["primary" nil]     primary-default
                         ["primary" "dangerous"]   primary-dangerous
                         ["secondary" nil]   secondary-default
                         ["secondary" "default"]   secondary-default
                         ["secondary" "dangerous"] secondary-dangerous
                         ["tertiary" nil]    tertiary-default
                         ["tertiary" "default"]    tertiary-default
                         ["tertiary" "dangerous"]  tertiary-dangerous
                         primary-default)
                       (case (:size properties)
                         "narrow" button-size-narrow
                         button-size-default)
                       (:class properties)]
            :title (:title properties)}
   (when (:icon properties)
     [icon (:icon properties)])
   body])
