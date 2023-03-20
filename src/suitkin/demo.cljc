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
            [ui.pages :as pages :refer [reg-page]]
            [clojure.string :as str]
            #?(:cljs [reagent.core])
            [stylo.core :refer [c]]))

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
     {:border-radius "0 0 11px 11px"}
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
  [:div {:class (c :w-full [:bg :white] [:p "0 0 400px 0"])
         :style {:background-color "var(--suitkin-body-bg)"
                 :min-height       "100vh"
                 :color            "var(--suitkin-body-color)"}}
   [:div {:class (c [:w "800px"] :mx-auto)}
    [:h1 {:class (c :text-3xl :font-bold)} "Getting started"]
    [:p "Content"]]])

(rf/reg-event-fx
 ::init
 (fn [{_db :db} [_ _params]]))

(pages/reg-page ::init main)
