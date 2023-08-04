(ns suitkin.core
  (:require
   [suitkin.components.button]
   [suitkin.components.search]
   [suitkin.components.sidebar.view]))

(def button  #'suitkin.components.button/component)
(def search  #'suitkin.components.search/component)
(def sidebar #'suitkin.components.sidebar.view/component)
