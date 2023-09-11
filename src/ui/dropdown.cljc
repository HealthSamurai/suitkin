(ns ui.dropdown
  (:require
   [suitkin.core]
   [stylo.core :refer [c]]
   [portfolio.reagent-18 :refer-macros [defscene]]
   #?(:cljs [portfolio.data :as data])))

#?(:cljs (data/register-collection! :dropdown {:title "Segment control"}))

(defscene segment-control
  :collection :dropdown
  :title "Dropdown"
  []
  [:div {:class (c [:p 3])}
   [suitkin.core/dropdown
    {:items
     [{:title "Monday"    :value "1"}
      {:title "Tuesday"   :value "2"}
      {:title "Wednesday" :value "3"}
      {:title "Thursday"  :value "4"}
      {:title "Friday"    :value "5"}
      {:title "Saturday"  :value "6"}
      {:title "Sunday"    :value "7"}]}]])
