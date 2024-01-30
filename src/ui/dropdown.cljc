(ns ui.dropdown
  (:require
   [suitkin.core]
   [stylo.core :refer [c]]
   [portfolio.reagent-18 :refer-macros [defscene]]
   #?(:cljs [portfolio.data :as data])))

#?(:cljs (data/register-collection! :dropdown {:title "Segment control"}))

(defscene dropdown-1
  "The ':search' key support all 'Input' arguments"
  :collection :dropdown
  :title "Dropdown empty"
  [suitkin.core/dropdown {:search {} :menu {:not-found "My not found text"}}])

(defscene dropdown-2
  "The ':search' key support all 'Input' arguments"
  :collection :dropdown
  :title "Dropdown items"
  [suitkin.core/dropdown {:search {}
                          :value  {:value "active" :title "Active"}
                          :menu   {:items [{:value "draft" :title "Draft"}
                                           {:value "active" :title "Active"}
                                           {:value "retired" :title "Retired"}
                                           {:value "unknown" :title "Unknown"}]}}])
