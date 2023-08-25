(ns ui.segment-control
  (:require
   [suitkin.core]
   [stylo.core :refer [c]]
   [portfolio.reagent-18 :refer-macros [defscene]]
   #?(:cljs [portfolio.data :as data])))

#?(:cljs
   (data/register-collection! :segment-control {:title "Segment control"}))

(defscene segment-control
  :collection :segment-control
  :title "Segment control"
  []
  [:div {:class (c [:p 3])}
   [suitkin.core/segment-control
    {:id "my-segment-control"
     :items
     [{:title "Custom" :active true}
      {:title "JSON"}
      {:title "YAML"}]}]])
