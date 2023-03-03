(ns ui-toolkit.components.demo
  (:require
   [ui-toolkit.interop.core      :as interop]
   [ui-toolkit.components.button :as button]
   [stylo.core                   :refer [c]]))


(defonce root-component
  (-> "root"
      interop/get-element-by-id
      interop/create-reagent-root))

(defn main
  []
  [:div {:class (c [:m 12])}
   [:h1 {:class (c :text-3xl :font-medium [:mb 4])} "Buttons"]
   [button/view {} "My button"]])


(defn ^:dev/after-load initialize
  []
  (interop/reagent-dom-render root-component [main]))
