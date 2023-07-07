(ns ui.index
  #?(:cljs
     (:require
      [ui.buttons]
      [portfolio.reagent-18 :refer-macros [defscene]]
      [portfolio.data :as data]
      [portfolio.ui :as ui])))

#?(:cljs (defonce app (ui/start! {:config  {:css-paths ["/assets/css/stylo.css"]}})))
