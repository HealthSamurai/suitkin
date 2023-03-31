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
    [input/zf-input {:label "Custom Label"}]
    "[suitkin.toolkit.input/zf-input {:label \"Custom Label\"}]"]

   [suitkin.core/component
    [input/zf-input {:label-required "Custom Label Required"}]
    "[suitkin.toolkit.input/zf-input {:label-required \"Custom Label required\"}]"]

   [suitkin.core/component
    [input/zf-input {:tooltip "Tooltip text"}]
    "[suitkin.toolkit.input/zf-input {:tooltip \"Tooltip text\"}]"]

   [suitkin.core/component
    [input/zf-input {:tooltip "Tooltip text" :label "Label"}]
    "[suitkin.toolkit.input/zf-input {:tooltip \"Tooltip text\" :label \"Label\"}]"]

   [suitkin.core/component
    [input/zf-input {:description "Description text"}]
    "[suitkin.toolkit.input/zf-input {:tooltip \"Description text\"}]"]

   [suitkin.core/component
    [input/zf-input {:text-left "https://"}]
    "[suitkin.toolkit.input/zf-input {:text-left \"https://\"}]"]

   [suitkin.core/component
    [input/zf-input {:text-right ".com"}]
    "[suitkin.toolkit.input/zf-input {:text-right \".com\"}]"]

   [suitkin.core/component
    [input/zf-input {:text-left "text left" :text-right "text right"}]
    "[suitkin.toolkit.input/zf-input {:text-left \"text left\" :text-right \"text right\"}]"]

   [suitkin.core/component
    [input/zf-input {:icon-left "smartbox-logo.svg"}]
    "[suitkin.toolkit.input/zf-input {:icon-left \"smartbox-logo.svg\"}]"]

   [suitkin.core/component
    [input/zf-input {:icon-right "aidbox-logo.svg"}]
    "[suitkin.toolkit.input/zf-input {:icon-right \"aidbox-logo.svg\"}]"]

   [suitkin.core/component
    [input/zf-input {:icon-right "credit_card.svg"}]
    "[suitkin.toolkit.input/zf-input {:icon-right \"credit_card.svg\"}]"]

   [suitkin.core/component
    [input/zf-input {:icon-right "credit_card.svg"
                     :text-right "will be skipped"}]
    "[suitkin.toolkit.input/zf-input {:icon-right \"credit_card.svg\" :text-right \"will be skipped\"}]"]

   [suitkin.core/component
    [input/zf-input {:description "Description text"
                     :tooltip "Tooltip text"
                     :text-left "https://"
                     :text-right ".com"
                     :label "Label"}]
    "[suitkin.toolkit.input/zf-input {:description \"Description text\" :tooltip \"Description text\"
     :label \"Label\" :text-right \"https://\" \":text-left \".com\"}]"]

   [suitkin.core/component
    [input/zf-input {:opts {:zf/root [::form :path] :zf/path [:input-with-error]}}]
    "[suitkin.toolkit.input/zf-input {:opts {:zf/root [::form :path] :zf/path [:input-with-error]}}]"]
   ])

(rf/reg-event-fx
 ::init
 (fn [{_db :db} [_ _params]]))

(pages/reg-page ::init main)
