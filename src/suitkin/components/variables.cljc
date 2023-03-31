(ns suitkin.components.variables
  (:require #?(:cljs reagent.dom.client)
            [suitkin.zf.form :as f]
            [stylo.tailwind.color]
            [re-frame.core :as rf]
            [ui.pages :as pages :refer [reg-page]]
            [clojure.string :as str]
            #?(:cljs [reagent.core])
            [stylo.core :refer [c]]))

(defn change-css-var
  [varname value]
  #?(:cljs (let [r (js/document.querySelector ":root")
                 s (.-style r)]
             (.setProperty s varname value))))

(defn get-css-var
  [varname]
  #?(:cljs
     (.getPropertyValue (js/getComputedStyle js/document.documentElement) varname)))

(defn variable-changer
  [varname description default-variables]
  (let [current-color #?(:cljs (reagent.core/atom (get-css-var varname)) :clj  (atom {}))]
    (fn [& _]
      [:div {:class (c [:py 4])}
       (get-css-var varname)
       [:div {:title description :class (c [:text :black] [:mr 2])} varname
        [:code {:class (c [:text :gray-600] [:pl 2])} @current-color]]
       [:div {:class (c [:text :gray-600] [:mr 2])} description]
       [:div {:class (c :flex [:space-x 1] :items-center [:pt 2])}
        [:input {:id    varname
                 :type "color"
                 :value @current-color
                 :class (c :border-r [:pr 3] {:background-color "inherit"})
                 :on-change (fn [e] #?(:cljs (change-css-var varname (.. e -target -value))))}]
        [:div {:class (c [:pl 2] :flex [:space-x 2])}
         (for [default-var default-variables] ^{:key default-var}
           [:div {:style    {:background-color default-var}
                  :class    (c [:w-min "20px"] [:h-min "20px"] :cursor-pointer [:hover {:outline "2px solid black" :outline-offset "1px"}]
                               :rounded)
                  :on-click (fn [e] #?(:cljs
                                       (do (reset! current-color default-var)
                                           (change-css-var varname default-var))))}])]]])))

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


(defn select-colors
  [styles number & [ks]]
  (->> (filter (fn [[k _]] (or (contains? ks k) (clojure.string/ends-with? (str k) (str number)))) styles)
       (map last)
       (sort)))

(defn main
  []
  [:<>
   [suitkin.core/header "Variables"]

   [variable-changer "--suitkin-body-bg" "Specifies background color for body." (select-colors stylo.tailwind.color/colors 500 #{:black :white})]
   [:hr]
   [variable-changer "--suitkin-body-color" "Specifies text color for body." (select-colors stylo.tailwind.color/colors 500 #{:black :white})]
   [:hr]
   [variable-changer "--suitkin-primary-color" "Specifies background color for primary buttons." (select-colors stylo.tailwind.color/colors 500)]
   [:hr]
   [variable-changer "--suitkin-secondary-color" "Specifies background color for secondary buttons." (select-colors stylo.tailwind.color/colors 300)]])

(rf/reg-event-fx
 ::init
 (fn [{_db :db} [_ _params]]))

(pages/reg-page ::init main)
