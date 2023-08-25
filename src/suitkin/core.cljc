(ns suitkin.core
  (:require
   [suitkin.components.button]
   [suitkin.components.search]
   [suitkin.components.sidebar.view]
   [suitkin.components.segment-control.view]))

(def button  #'suitkin.components.button/component)
(def sidebar #'suitkin.components.sidebar.view/component)

;; Inputs
(def search  #'suitkin.components.search/component)
(def segment-control #'suitkin.components.segment-control.view/component)
