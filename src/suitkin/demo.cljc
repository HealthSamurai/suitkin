(ns suitkin.demo
  (:require #?(:cljs reagent.dom.client)
            [suitkin.toolkit.input :as input]
            [suitkin.toolkit.select :as select]
            [suitkin.toolkit.button :as button]
            [suitkin.toolkit.textarea :as textarea]
            [suitkin.zf.form :as f]
            [suitkin.toolkit.dropdown-button :as dropdown-button]
            [re-frame.core :as rf]
            [stylo.core :refer [c]]))

(def settings-form
  {:zf/root   [::form :path]
   :schema    {:foo {:id-fn   :value
                     :options [{:label "Option 1" :value "a"}
                               {:label "Option 2" :value "b"}]}}})

(defonce root-component
  #?(:cljs (-> "root" js/document.getElementById reagent.dom.client/create-root)
     :clj  nil))

(defonce form-init
  (rf/dispatch [::f/form-init settings-form]))

(defn main
  []
  [:div {:class (c [:m 12] [:space-y 5] [:w "500px"] :mx-auto {:font-family "Inter"})}
   [:h1 {:class (c :text-3xl :font-medium)} "Inputs"]
   [input/zf-input {}]
   [:h1 {:class (c :text-3xl :font-medium)} "Dropdowns"]
   [select/zf-select {:render-value  :label
                      :render-option :label
                      :opts          {:zf/root [::form :path]
                                      :zf/path [:foo]}}]
   [:h1 {:class (c :text-3xl :font-medium)} "Buttons"]
   [button/view {} "My button"]])

(defn ^:dev/after-load initialize
  []
  #?(:cljs (reagent.dom.client/render root-component [main])))
