(ns ui.buttons
  (:require
   [toolkit.button]
   [portfolio.reagent-18 :refer-macros [defscene]]))

(defscene button
  :params {:title "Button"}
  [params portfolio-opts]
  [toolkit.button/component {} "Execute"])
