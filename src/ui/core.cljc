(ns ui.core
  (:require
   #?(:cljs [reagent.dom.client])
   #?(:cljs [cljsjs.react])
   #?(:cljs [ui.index])
   [ui.pages :as pages]
   [re-frame.core :as rf]
   [clojure.string :as str]
   #_[reagent.dom.client :as reagent]
   [suitkin.zf.routing]
   [ui.routes :as routes]
   [suitkin.demo]
   [stylo.core :refer [c]]))

(defn main
  []
  )
