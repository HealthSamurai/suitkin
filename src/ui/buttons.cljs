(ns ui.buttons
  (:require
   [suitkin.core]
   [ui.properties]
   [reagent.ratom :as ra]
   [portfolio.data :as data]
   [portfolio.reagent-18 :refer-macros [defscene]]))

(data/register-collection! :buttons {:title "Buttons"})

(defscene button-primary-default
  :title "Component"
  :collection :buttons
  :params (ra/atom {:use "primary" :theme "default" :icon "/suitkin/image/icons/plus.svg" :title "Execute"})
  [p portfolio-opts]
  [:div
   [suitkin.core/button @p (:title @p)]
   (ui.properties/component
     p
     [[:use      {:p/type :select :items  ["primary" "secondary" "tertiary"]}]
      [:theme    {:p/type :select :items  ["default" "dangerous"]}]
      [:size     {:p/type :select :items  ["default" "narrow"]}]
      [:disabled {:p/type :boolean :items  [true false]}]
      [:icon     {:p/type :select :items  ["/suitkin/image/icons/plus.svg"] :clear? true}]
      [:title    {:p/type :text}]])])
