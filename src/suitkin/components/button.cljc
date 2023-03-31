(ns suitkin.components.button
  (:require #?(:cljs reagent.dom.client)
            [suitkin.core]
            [suitkin.toolkit.button :as button]
            [suitkin.zf.form :as f]
            [stylo.tailwind.color]
            [re-frame.core :as rf]
            [ui.pages :as pages :refer [reg-page]]
            [clojure.string :as str]
            #?(:cljs [reagent.core])
            [stylo.core :refer [c]]
            [suitkin.toolkit.dropdown-button :as dropdown-button]))

(defn main
  []
  [:<>
   [suitkin.core/header "Buttons" "button/view"]

   [suitkin.core/component
    [button/view {} "My button"]
    "[suitkin.toolkit.button/view {} \"My button\"]"]
    (for [button-props [{:type "primary-2"} {:type "sdc"} {:type "sdc-small"} {:type "sdc-outline"} {:type "danger"} {:type "link"} {:type "text"}]]
      ^{:key (:type button-props)}
      [suitkin.core/component
       [button/button button-props "My button"]
       (str "[suitkin.toolkit.button/button " button-props " \"My button\"]")])])

(rf/reg-event-fx
 ::init
 (fn [{_db :db} [_ _params]]))

(pages/reg-page ::init main)
