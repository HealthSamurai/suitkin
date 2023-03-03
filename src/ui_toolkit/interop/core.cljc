(ns ui-toolkit.interop.core
  #?(:cljs (:require reagent.dom.client)))

(defn create-reagent-root
  [element]
  #?(:cljs (reagent.dom.client/create-root element)))

(defn get-element-by-id
  [id]
  #?(:cljs (js/document.getElementById id)))

(defn reagent-dom-render
  [element component]
  #?(:cljs (reagent.dom.client/render element component)))

;; document.documentElement.style.setProperty('--your-variable', '#YOURCOLOR');
