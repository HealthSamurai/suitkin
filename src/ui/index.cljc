(ns ui.index
  (:require
   [ui.pages]
   #?(:cljs [ui.buttons])
   #?(:cljs [ui.navigation])
   #?(:cljs [portfolio.reagent-18 :refer-macros [defscene]])
   #?(:cljs [portfolio.data :as data])
   #?(:cljs [portfolio.ui :as ui])))

#?(:cljs (defonce app
           (ui/start!
               {:config
                {:css-paths
                 ["/assets/css/stylo.css"
                  "/assets/css/styles.css"
                  "/assets/Inter/inter.css"]}})))
