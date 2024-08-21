(ns suitkin.components.navigation.view
  (:require [stylo.core :refer [c]]))

(defn tabs
  [options]
  [:div (merge {:class [(c :flex
                           {:border-bottom  "1px solid var(--basic-gray-2,#BFC1C7)"
                            :font-size "14px"
                            :font-weight "500"
                            :font-family "Inter"
                            :color  "#212636"})
                        (or (:space-x-class options)
                            (c [:space-x "16px"]))
                        (:class options)]}
               (dissoc options :class :items :space-x-class))
   (for [item (:items options)]
     (if (:header item)
       ^{:key (:title item)}
       [(if (:href item) :a :div)
        (merge {:class [(c {:display "flex"
                            :margin-bottom "2px"
                            :padding "7px 4px 6px 4px"
                            :line-height "20px"
                            :justify-content "center"
                            :align-items "center"})
                        (:class item)]}
               (dissoc item :class :title))
        (:header item)]
       ^{:key (:title item)}
       [(if (:href item) :a :div)
        (merge {:class [(if (:active item)
                          (c {:margin-bottom "0px"
                              :border-bottom "1px solid #212636"})
                          (c {:border-bottom "1px solid transparent"
                              :color "#83868E"}
                             [:hover {:margin-bottom "0px"
                                      :color  "#212636"}]))
                        (c {:display "flex"
                            :cursor "pointer"
                            :padding "7px 4px 6px 4px"
                            :line-height "20px"
                            :justify-content "center"
                            :align-items "center"})
                        (:class item)]}
               (dissoc item :class :title))
        (:title item)]))])
