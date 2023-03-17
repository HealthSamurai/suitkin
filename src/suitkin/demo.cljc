(ns suitkin.demo
  (:require #?(:cljs reagent.dom.client)
            [suitkin.toolkit.input :as input]
            [suitkin.toolkit.select :as select]
            [suitkin.toolkit.button :as button]
            [suitkin.toolkit.textarea :as textarea]
            [suitkin.zf.form :as f]
            [stylo.tailwind.color]
            [suitkin.toolkit.dropdown-button :as dropdown-button]
            [re-frame.core :as rf]
            #?(:cljs [reagent.core])
            [stylo.core :refer [c]]))


(defn change-css-var
  [varname value]
  #?(:cljs (let [r (js/document.querySelector ":root")
                 s (.-style r)]
             (.setProperty s varname value))))

(defn variable-changer
  [varname description default-variables]
  (let [current-color #?(:cljs (reagent.core/atom "black") :clj  (atom {}))]
    (fn [& _]
      [:div
       [:div {:title description :class (c [:text :black] [:mr 2])} varname]
       [:div {:class (c [:text :gray-600] [:mr 2])} description]
       [:div {:class (c :flex [:space-x 1] :items-center [:pt 2])}
        [:input {:id    varname
                 :type "color"
                 :value @current-color
                 :class (c :border-r [:pr 3] {:background-color "inherit"})
                 :on-change (fn [e] (prn "##") #?(:cljs (change-css-var varname (.. e -target -value))))}]
        [:div {:class (c [:pl 2] :flex [:space-x 2])}
         (for [default-var default-variables] ^{:key default-var}
           [:div {:style    {:background-color default-var}
                  :class    (c [:w-min "20px"] [:h-min "20px"] :cursor-pointer [:hover {:outline "2px solid black" :outline-offset "1px"}]
                               :rounded)
                  :on-click (fn [e] #?(:cljs
                                       (do (reset! current-color default-var)
                                           (change-css-var varname default-var))))}])]]])))

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

(defn select-colors
  [styles number & [ks]]
  (->> (filter (fn [[k _]] (or (contains? ks k) (clojure.string/ends-with? (str k) (str number)))) styles)
       (map last)
       (sort)))

(defn main
  []
  [:div {:style {:background-color "var(--suitkin-body-bg)"
                 :min-height       "100vh"
                 :color            "var(--suitkin-body-color)"}} 
   [:div {:class (c  [:space-y 5] [:w "500px"] :mx-auto {:font-family "Inter"})}
    [:h1 {:class (c :text-3xl :font-bold)} "Variables"]
    [variable-changer "--suitkin-body-bg" "Specifies background color for body." (select-colors stylo.tailwind.color/colors 500 #{:black :white})]
    [variable-changer "--suitkin-body-color" "Specifies text color for body." (select-colors stylo.tailwind.color/colors 500 #{:black :white})]
    [variable-changer "--suitkin-primary-color" "Specifies background color for primary buttons." (select-colors stylo.tailwind.color/colors 500)]
    [:hr]
    [variable-changer "--suitkin-secondary-color" "Specifies background color for secondary buttons." (select-colors stylo.tailwind.color/colors 300)]
    [:h1 {:class (c [:mt 15] :text-3xl :font-medium)} "Inputs"]
    [input/zf-input {}]
    [:h1 {:class (c :text-3xl :font-medium)} "Dropdowns"]
    [select/zf-select {:render-value  :label
                       :render-option :label
                       :opts          {:zf/root [::form :path]
                                       :zf/path [:foo]}}]
    [:h1 {:class (c :text-3xl :font-medium)} "Buttons"]
    [:h1 {:class (c :text-xl :font-medium)} "Primary button"]
    [button/view {} "My button"]
    [:h1 {:class (c :text-xl :font-medium)} "Secondary button"]
    [button/view {:variant "gray"} "My button"]
    ]])

(defn ^:dev/after-load initialize
  []
  #?(:cljs (reagent.dom.client/render root-component [main])))
