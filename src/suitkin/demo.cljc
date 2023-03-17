(ns suitkin.demo
  (:require #?(:cljs reagent.dom.client)
            [suitkin.toolkit.input :as input]
            [suitkin.toolkit.select :as select]
            [suitkin.toolkit.button :as button]
            [suitkin.toolkit.textarea :as textarea]
            [suitkin.zf.form :as f]
            [suitkin.toolkit.dropdown-button :as dropdown-button]
            [re-frame.core :as rf]
            [ui.pages :as pages :refer [reg-page]]
            [stylo.core :refer [c]]))

(def settings-form
  {:zf/root   [::form :path]
   :schema    {:foo {:id-fn   :value
                     :options [{:label "Option 1" :value "a"}
                               {:label "Option 2" :value "b"}]}}})

(def main-header
  (c :text-3xl
     :font-bold
    [:p "40px 0 60px 0"]))

(def second-header
  (c :text-2xl
    [:p "8px 6px"]))

(def third-header
  (c :text-l
     {:color "gray"}
     [:p "10px 6px"]))

(def code-block
  (c [:bg :gray-100]
     {:border-radius "0 0 8px 8px"}
     :block
     [:p "14px 20px"]
     ;;[:m "10px 0px 10px"]
     {:font-size "12px"
      :font-family "JetBrains Mono"
      :color "gray"}))

(def component-wrapper
  (c [:bg :white]
     ;;[:p 2]
     {:border "1px solid #EEE"
      :border-radius "12px"}
      [:m "0 0 50px 0"]))

(def component
  (c [:m 10]
     [:space-y 5]
     [:w "360px"]
     :mx-auto
     {:font-family "Inter"}))


(defn main
  []
  [:div {:class (c :w-full [:bg :white] [:p "0 0 400px 0"])}
   [:div {:class (c [:w "800px"] :mx-auto)}
    [:h1 {:class main-header} "Inputs"]

    [:h2 {:class second-header} "zf-input"]
    [:div {:class component-wrapper}
     [:div {:class component}
      [input/zf-input {}]]
     [:code {:class code-block}
      "[input/zf-input] {}]"]]

    ;;[:h3 {:class third-header} ":placeholder"]
    [:div {:class component-wrapper}
     [:div {:class component}
      [input/zf-input {:placeholder "placeholder"}]]
     [:code {:class code-block}
      "[input/zf-input] {:placeholder \"placeholder\"}]"]]

    ;;[:h3 {:class third-header} ":disabled"]
    [:div {:class component-wrapper}
     [:div {:class component}
      [input/zf-input {:disabled true}]]
     [:code {:class code-block}
      "[input/zf-input] {:disabled true}]"]]

    ;;[:h3 {:class third-header} ":error"]
    [:div {:class component-wrapper}
     [:div {:class component}
      [input/zf-input {:opts {:zf/root [::form :path] :zf/path [:input-with-error]}}]]
     [:code {:class code-block}
      "[input/zf-input {:opts {:zf/root [::form :path] :zf/path [:input-with-error]}}]"]]

    [:h2 {:class second-header} "zf-number"]
    [:div {:class component-wrapper}
     [:div {:class component}
      [input/zf-number {:opts {:zf/root [::form :path] :zf/path [:number-input]}}]]
     [:code {:class code-block}
      "[input/zf-number {:opts {:zf/root [::form :path] :zf/path [:number-input]"]]]

   #_[:h1 {:class (c :text-3xl :font-medium)} "Dropdowns"]
   #_[select/zf-select {:render-value  :label
                        :render-option :label
                        :opts          {:zf/root [::form :path]
                                        :zf/path [:foo]}}]

   #_[:h1 {:class (c :text-3xl :font-medium)} "Buttons"]
   #_[button/view {} "My button"]

   ])

(rf/reg-event-fx
 ::init
 (fn [{_db :db} [_ _params]]
   {:dispatch [::f/form-init settings-form]}))


(pages/reg-page ::init main)
