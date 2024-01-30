(ns ui.input
  (:require
   [suitkin.core]
   [suitkin.utils :as u]
   [stylo.core :refer [c]]
   [portfolio.reagent-18 :refer-macros [defscene]]
   #?(:cljs [portfolio.data :as data])))

#?(:cljs (data/register-collection! :input {:title "Input"}))

(defscene input-1
  :collection :input
  :title "Default"
  [suitkin.core/input {:placeholder "Placeholder"}])

(defscene input-2
  :collection :input
  :title "Default invalid"
  [suitkin.core/input {:placeholder "Placeholder" :s/invalid? true}])

(defscene input-3
  :collection :input
  :title "Default disabled"
  [suitkin.core/input {:placeholder "Placeholder" :disabled true}])

(defscene input-4
  :collection :input
  :title "Default narrow"
  [suitkin.core/input {:placeholder "Placeholder" :s/size "narrow"}])

(defscene input-5
  :collection :input
  :title "Default narrow invalid"
  [suitkin.core/input {:placeholder "Placeholder" :s/size "narrow" :s/invalid? true}])

(defscene input-6
  :collection :input
  :title "Default narrow disabled"
  [suitkin.core/input {:placeholder "Placeholder" :s/size "narrow" :disabled true}])

(defscene input-7
  :collection :input
  :title "Icon"
  [suitkin.core/input {:placeholder "Search..." :s/left [:img {:src (u/public-src "/suitkin/img/icon/ic-search-16.svg")}]}])

(defscene input-8
  :collection :input
  :title "Icon invalid"
  [suitkin.core/input {:placeholder "Search..." :s/left [:img {:src (u/public-src "/suitkin/img/icon/ic-search-16.svg")}] :s/invalid? true}])

(defscene input-9
  :collection :input
  :title "Icon disabled"
  [suitkin.core/input {:placeholder "Search..." :s/left [:img {:src (u/public-src "/suitkin/img/icon/ic-search-16.svg")}] :disabled true}])


(defscene input-10
  :collection :input
  :title "Icon narrow"
  [suitkin.core/input {:placeholder "Search..." :s/left [:img {:src (u/public-src "/suitkin/img/icon/ic-search-16.svg")}]  :s/size "narrow"}])

(defscene input-11
  :collection :input
  :title "Icon invalid narrow"
  [suitkin.core/input {:placeholder "Search..." :s/left [:img {:src (u/public-src "/suitkin/img/icon/ic-search-16.svg")}] :s/invalid? true  :s/size "narrow"}])

(defscene input-12
  :collection :input
  :title "Icon disabled narrow"
  [suitkin.core/input {:placeholder "Search..." :s/left [:img {:src (u/public-src "/suitkin/img/icon/ic-search-16.svg")}] :disabled true  :s/size "narrow"}])
