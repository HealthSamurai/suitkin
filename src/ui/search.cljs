(ns ui.search
  (:require
   [suitkin.core]
   [ui.properties]
   [reagent.ratom :as ra]
   [portfolio.data :as data]
   [portfolio.reagent-18 :refer-macros [defscene]]))

(data/register-collection! :search {:title "Search"})

(defscene search-primary-default
  :title "Component"
  :collection :search
  :params (ra/atom {:use "primary" :theme "default" :icon "/suitkin/img/icon/plus.svg" :title "Execute"})
  [p portfolio-opts]
  [:div
   [suitkin.core/search [:root] [:form]]])
