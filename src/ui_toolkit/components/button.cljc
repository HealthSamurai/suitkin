(ns ui-toolkit.components.button
  (:require
   [re-frame.core :as rf]
   [stylo.core    :refer [c]]))

(defn view
  [attributes content]
  [:button (merge {:class (c :border [:p 2])} attributes)
   content])

