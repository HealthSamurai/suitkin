(ns suitkin.core
  (:require [stylo.core :refer [c]]))

(def main-header-class
  (c :text-3xl
     :font-bold
     [:p "40px 0 60px 0"]))

(def second-header-class
  (c :text-2xl
     [:p "8px 6px"]))

(def third-header-class
  (c :text-l
     {:color "gray"}
     [:p "10px 6px"]))

(defn header
  [title & [subtitle]]
  [:header
   [:h1 {:class main-header-class} title]
   (when subtitle
     [:h2 {:class second-header-class} subtitle])])

(def code-block-class
  (c [:bg :gray-100]
     {:border-radius "0 0 8px 8px"}
     :block
     [:p "14px 20px"]
     ;;[:m "10px 0px 10px"]
     {:font-size "12px"
      :font-family "JetBrains Mono"
      :color "gray"
      :white-space "pre"}))

(def component-wrapper-class
  (c [:bg :white]
     {:border "1px solid #EEE"
      :border-radius "12px"}
     [:m "0 0 50px 0"]))

(def component-class
  (c [:m 10]
     [:space-y 5]
     [:w "360px"]
     :mx-auto
     {:font-family "Inter"}))

(defn component
  [component component-code]
  ;;[:h3 {:class third-header} ":placeholder"]
  [:div {:class component-wrapper-class}
   [:div {:class component-class}
    component]
   [:code {:class code-block-class}
    component-code]])
