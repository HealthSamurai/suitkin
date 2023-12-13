(ns suitkin.components.typography
  (:require [stylo.core :refer [c]]))

(defn h1
  [options & content]
  [:h1 {:class [(c {:font-family "Inter"
                    :font-size   "28px"
                    :font-weight "700"
                    :color       "#212636"})
                (:class options)]}
   content])

(defn label
  [options & content]
  [:label {:class [(c {:font-family "Inter"
                        :margin-left "1px"
                        :font-size   "14px"
                        :font-weight "400"
                        :line-height "20px"
                        :margin-bottom "6px"
                        :color       "#010205"})
                   (:class options)]}
   content])
