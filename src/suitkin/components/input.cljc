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
    [input/zf-input {:disabled true :placeholder "disabled"}]
    "[suitkin.toolkit.input/zf-input {:disabled true
                                 :placeholder \"disabled\"}]"]

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
    "[suitkin.toolkit.input/zf-input {:tooltip \"Tooltip text\"
                                 :label \"Label\"}]"]

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
    [input/zf-input {:text-left "text-left" :text-right "text-right"}]
    "[suitkin.toolkit.input/zf-input {:text-left \"text-left\"
                                 :text-right \"text-right\"}]"]

   [suitkin.core/component
    [input/zf-input {:icon-left "search.svg"}]
    "[suitkin.toolkit.input/zf-input {:icon-left \"search.svg\"}]"]

   [suitkin.core/component
    [input/zf-input {:icon-right "eye.svg"}]
    "[suitkin.toolkit.input/zf-input {:icon-right \"eye.svg\"}]"]

   [suitkin.core/component
    [input/zf-input {:icon-right "credit_card.svg"
                     :text-right "will be skipped"}]
    "[suitkin.toolkit.input/zf-input {:icon-right \"credit_card.svg\"
                                 :text-right \"will be skipped\"}]"]

   [suitkin.core/component
    [input/zf-input {
                     :label "Label"
                     :tooltip "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer malesuada hendrerit dui, non dapibus tellus vulputate a. Sed feugiat nulla eu nisi convallis pharetra. Aliquam in metus ut elit blandit euismod vel ut ipsum. Nulla facilisi. Sed euismod erat risus, a efficitur velit malesuada ut. Praesent vel urna odio. In dapibus, metus ut feugiat ultricies, sapien tortor semper dolor, sed efficitur ex ante quis velit. "
                     :text-left "https://"
                     :text-right ".com"
                     :placeholder "placeholder"
                     :description "Description text"
                     }]
    "[suitkin.toolkit.input/zf-input {:label \"Label\"
                                 :tooltip \"Tooltip Lorem ipsum ...\"
                                 :text-left \"https://\"
                                 :text-right \".com\"
                                 :placeholder \"placeholder\"
                                 :description \"Description text\"}]"]

   [suitkin.core/component
    [input/zf-input {:opts {:zf/root [::form :path] :zf/path [:input-with-error]}}]
    "[suitkin.toolkit.input/zf-input {:opts {:zf/root [::form :path] :zf/path [:input-with-error]}}]"]
   ])

(rf/reg-event-fx
 ::init
 (fn [{_db :db} [_ _params]]))

(pages/reg-page ::init main)
