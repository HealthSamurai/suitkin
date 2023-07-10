(ns ui.index
  (:require
   [ui.pages]
   #?(:cljs [ui.buttons])
   #?(:cljs [ui.navigation])
   #?(:cljs [portfolio.reagent-18 :refer-macros [defscene]])
   #?(:cljs [portfolio.data :as data])
   #?(:cljs [portfolio.ui :as ui])))

(goog-define GH-PAGES false)

#?(:cljs (defonce app
           (ui/start!
               {:config
                {:viewport/defaults {:viewport/padding [0 0 0 0]
                                     :viewport/height  "800px"
                                     }
                 :css-paths
                 (if GH-PAGES
                   ["/suitkin/assets/css/stylo.css"
                    "/suitkin/assets/css/styles.css"
                    "/suitkin/assets/Inter/inter.css"]
                   ["/assets/css/stylo.css"
                    "/assets/css/styles.css"
                    "/assets/Inter/inter.css"])
                 :canvas-path "portfolio/canvas.html"}})))
