(ns ui.build
  (:require [optimus.assets :as assets]
            [optimus.export :as export]))

(def assets
  (assets/load-assets
   "public"
   [#"/portfolio/prism.js"
    #"/portfolio/styles/portfolio.css"
    #"/portfolio/canvas.html"]))

(defn export
  {:shadow.build/stage :flush}
  [build-state]
  (export/save-assets assets "_site")
  build-state)
