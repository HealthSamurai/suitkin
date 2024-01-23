(ns suitkin.components.navigation.view
  (:require [stylo.core :refer [c]]))

(defn tabs
  [options]
  [:div (merge {:class [(c :flex [:space-x "16px"]
                           
                           {:border-bottom "1px solid var(--basic-gray-3,#BFC1C7)"
                            :font-size "14px"
                            :font-weight "500"
                            :font-family "Inter"
                            :color "var(--basic-gray-6, #434959)"})
                        (:class options)]}
               (dissoc options :class :items))
   (for [item (:items options)] ^{:key (:title item)} 
     [:div (merge {:class [(if (:active item)
                            (c {:color "var(--basic-gray-8, #010205)"
                                :border-bottom "2px solid var(--basic-gray-8, #010205)"})
                            (c {:margin-bottom "2px"}
                               [:hover {:color "var(--basic-gray-8, #010205)"
                                        :margin-bottom "0px"
                                        :opacity       "0.6"
                                        :border-bottom "2px solid var(--basic-gray-8, #010205)"}]))
                          (c {:display "flex"
                              :cursor "pointer"
                              :padding "7px 4px 6px 4px"
                              :line-height "20px"
                              :justify-content "center"
                              :align-items "center"})
                           (:class item)]}
                  (dissoc item :class :title))
      (:title item)])])
