(ns suitkin.core
  (:require
   [suitkin.components.button]
   [suitkin.components.sidebar.view]))

(def button  #'suitkin.components.button/component)
(def sidebar #'suitkin.components.sidebar.view/component)
