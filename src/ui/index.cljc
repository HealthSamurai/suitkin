(ns ui.index
 #?(:cljs
    (:require
     [ui.buttons]
     [ui.navigation]
     [ui.segment-control]
     [ui.input]
     [ui.dropdown]
     [portfolio.ui :as ui])))

(goog-define GH-PAGES false)

#?(:cljs (defonce app
           (ui/start!
               {:config
                {:viewport/defaults {:viewport/padding [16]
                                     :viewport/height  "800px"}
                                     
                 :css-paths
                 (if GH-PAGES
                   ["/suitkin/suitkin/css/stylo.css"
                    "/suitkin/suitkin/css/styles.css"
                    "/suitkin/suitkin/font/Inter/inter.css"]
                   ["/suitkin/css/stylo.css"
                    "/suitkin/css/styles.css"
                    "/suitkin/font/Inter/inter.css"])
                 :canvas-path "portfolio/canvas.html"}})))
