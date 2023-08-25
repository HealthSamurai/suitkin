(ns ui.input
  (:require
   [suitkin.core]
   [suitkin.utils :as u]
   [stylo.core :refer [c]]
   [portfolio.reagent-18 :refer-macros [defscene]]
   #?(:cljs [portfolio.data :as data])))

#?(:cljs (data/register-collection! :input {:title "Input"}))

(defscene input
  :collection :input
  :title "Input"
  []
  [:div {:class (c [:p 3] [:space-y 4])}
   [:p "Input default"]
   [suitkin.core/input {:placeholder "Placeholder"}]
   [suitkin.core/input {:placeholder "Placeholder" :required true}]
   [suitkin.core/input {:placeholder "Placeholder" :disabled true}]
   [:p "Input narrow"]
   [suitkin.core/input {:placeholder "Placeholder" :s/size "narrow"}]
   [suitkin.core/input {:placeholder "Placeholder" :s/size "narrow" :required true}]
   [suitkin.core/input {:placeholder "Placeholder" :s/size "narrow" :disabled true}]
   [:p "Search"]
   [suitkin.core/input {:placeholder "Placeholder" :s/size "narrow" :s/left [:img {:src (u/public-src "/suitkin/img/icon/ic-search-16.svg")}]}]
   [suitkin.core/input {:placeholder "Placeholder" :s/size "narrow" :s/left [:img {:src (u/public-src "/suitkin/img/icon/ic-search-16.svg")}] :required true}]
   [suitkin.core/input {:placeholder "Placeholder" :s/size "narrow" :s/left [:img {:src (u/public-src "/suitkin/img/icon/ic-search-16.svg")}] :disabled true}]
   [:p "Dropdown"]
   [suitkin.core/input {:placeholder "Placeholder" :s/size "narrow" :s/right [:img {:src (u/public-src "/suitkin/img/icon/ic-chevron-down-16.svg")}]}]
   [suitkin.core/input {:placeholder "Placeholder" :s/size "narrow" :required true :s/right [:img {:src (u/public-src "/suitkin/img/icon/ic-chevron-down-16.svg")}]}]
   [suitkin.core/input {:placeholder "Placeholder" :s/size "narrow" :disabled true :s/right [:img {:src (u/public-src "/suitkin/img/icon/ic-chevron-down-16.svg")}]}]
   ])
