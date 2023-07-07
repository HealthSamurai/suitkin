(ns ui.index
  #?(:cljs
     (:require
      [portfolio.reagent-18 :refer-macros [defscene]]
      [portfolio.data :as data]
      [portfolio.ui :as ui])))

#?(:cljs
   (data/register-collection!
    :buttons
    {:title "Buttons"}))

#?(:cljs
   (defscene button
     :collection :buttons
     :title      "Default"
     :params {:title "Button"}
     [params portfolio-opts]
     [:button.button (:title params)]))

#?(:cljs
   (defscene button-disabled
     :collection :buttons
     :title      "Disabled"
     :params {:title "Button"}
     [params portfolio-opts]
     [:button.button (:title params)]))

#?(:cljs
   (defonce app
     (ui/start!)))
