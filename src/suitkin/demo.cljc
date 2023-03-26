(ns suitkin.demo
  (:require #?(:cljs reagent.dom.client)
            [suitkin.toolkit.input :as input]
            [suitkin.toolkit.select :as select]
            [suitkin.toolkit.button :as button]
            [suitkin.toolkit.textarea :as textarea]
            [suitkin.toolkit.dropdown-button :as dropdown-button]
            [stylo.tailwind.color]
            [suitkin.zf.form :as f]
            [re-frame.core :as rf]
            [ui.pages :as pages :refer [reg-page]]
            [clojure.string :as str]
            #?(:cljs [reagent.core])
            [stylo.core :refer [c]]))

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
