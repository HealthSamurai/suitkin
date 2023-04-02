(ns suitkin.components.select
  (:require #?(:cljs reagent.dom.client)
            [suitkin.core]
            [suitkin.toolkit.select :as select]
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
   [suitkin.core/header "Selects" "zf-select"]

   [suitkin.core/component
    [select/zf-select {:render-value  :label
                       :render-option :label
                       :opts          {:zf/root [::form :path]
                                       :zf/path [:foo]}}]
"[suitkin.toolkit.select/zf-select { :render-value  :label
                                    :render-option :label
                                    :opts {:zf/root [::form :path]
                                    :zf/path [:foo]}}]"]])

(def settings-form
  {:zf/root   [::form :path]
   :schema    {:foo {:id-fn   :value
                     :options [{:label "Option 1" :value "a"}
                               {:label "Option 2" :value "b"}]}}})

(rf/reg-event-fx
 ::init
 (fn [{_db :db} [_ _params]]
   {:dispatch [::f/form-init settings-form]}))

(pages/reg-page ::init main)
