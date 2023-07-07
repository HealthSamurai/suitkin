(ns toolkit.button
  (:require
   [stylo.core :refer [c]]))

(def base-button-class
  (c [:px "16px"]
     [:py "6px"]
     ))


(defn component
  [properties body]
  [:button {:class [base-button-class]} body])
