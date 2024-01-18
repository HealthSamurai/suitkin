(ns suitkin.core
  (:require
   [suitkin.components.button]
   [suitkin.components.input.view]
   [suitkin.components.sidebar.view]
   [suitkin.components.dropdown.view]
   [suitkin.components.segment-control.view]
   [suitkin.components.monaco.view]
   [suitkin.components.typography]))

(def button #'suitkin.components.button/component)
(def sidebar #'suitkin.components.sidebar.view/component)
(def input #'suitkin.components.input.view/component)
(def monaco #'suitkin.components.monaco.view/component)
(def segment-control #'suitkin.components.segment-control.view/component)
(def dropdown #'suitkin.components.dropdown.view/component)
(def h1 #'suitkin.components.typography/h1)
(def label #'suitkin.components.typography/label)
(def span #'suitkin.components.typography/span)
(def expandeable-text #'suitkin.components.typography/expandeable-text)
(def kv-list #'suitkin.components.typography/kv-list)
(def assistive-text #'suitkin.components.typography/assistive-text)
