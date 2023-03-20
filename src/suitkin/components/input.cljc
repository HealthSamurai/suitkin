(ns suitkin.components.input
  (:require #?(:cljs reagent.dom.client)
            [suitkin.core]
            [suitkin.toolkit.input :as input]
            [suitkin.zf.form :as f]
            [stylo.tailwind.color]
            [re-frame.core :as rf]
            [ui.pages :as pages :refer [reg-page]]
            [clojure.string :as str]
            #?(:cljs [reagent.core])
            [stylo.core :refer [c]]))



(defn main
  []
  [:<>
   [suitkin.core/header "Inputs" "zf-input"]

   [suitkin.core/component
    [input/zf-input {}]
    "[suitkin.toolkit.input/zf-input {}]"]

   [suitkin.core/component
    [input/zf-input {:placeholder "placeholder"}]
    "[suitkin.toolkit.input/zf-input {:placeholder \"placeholder\"}]"]

   [suitkin.core/component
    [input/zf-input {:disabled true}]
    "[suitkin.toolkit.input/zf-input {:disabled true}]"]

   [suitkin.core/component
    [input/zf-input {:opts {:zf/root [::form :path] :zf/path [:input-with-error]}}]
    "[suitkin.toolkit.input/zf-input {:opts {:zf/root [::form :path] :zf/path [:input-with-error]}}]"]])

(rf/reg-event-fx
 ::init
 (fn [{_db :db} [_ _params]]))

(pages/reg-page ::init main)
