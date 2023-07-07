(ns ui.buttons
  (:require
   [toolkit.button]
   [portfolio.data :as data]
   [ui.properties]
   [reagent.ratom :as ra]
   [portfolio.reagent-18 :refer-macros [defscene]]))

(data/register-collection! :buttons {:title "Buttons"})

(defscene button-primary-default
  :title "Component"
  :collection :buttons
  :params (ra/atom {:use "primary" :theme "default" :icon "/assets/img/icons/ic-plus-16.svg" :title "Execute"})
  [p portfolio-opts]
  [:div
   [toolkit.button/component @p (:title @p)]
   (ui.properties/component
     p
     [[:use      {:p/type :select :items  ["primary" "secondary" "tertiary"]}]
      [:theme    {:p/type :select :items  ["default" "dangerous"]}]
      [:size     {:p/type :select :items  ["default" "narrow"]}]
      [:disabled {:p/type :boolean :items  [true false]}]
      [:icon     {:p/type :select :items  ["/assets/img/icons/ic-plus-16.svg"] :clear? true}]
      [:title    {:p/type :text}]])])

;; (defscene button-primary-dangerous
;;   :collection :buttons.primary
;;   :params (ra/atom {:use "primary" :theme "dangerous" :icon "/assets/img/icons/ic-plus-16.svg" :title "Execute"})
;;   :title "Dangerous"
;;   [p portfolio-opts]
;;   [:div
;;    [toolkit.button/component @p (:title @p)]
;;    (ui.properties/component
;;      p
;;      [[:size     {:p/type :select :items  ["narrow"]}]
;;       [:disabled {:p/type :boolean :items  [true false]}]
;;       [:icon     {:p/type :select :items  ["/assets/img/icons/ic-plus-16.svg"]}]
;;       [:title    {:p/type :text}]])])

;; (defscene button-secondary-default
;;   :collection :buttons.secondary
;;   :params (ra/atom {:use "secondary" :theme "default" :icon "/assets/img/icons/ic-plus-16.svg" :title "Execute"})
;;   :title "Default"
;;   [p portfolio-opts]
;;   [:div
;;    [toolkit.button/component @p (:title @p)]
;;    (ui.properties/component
;;      p
;;      [[:size     {:p/type :select :items  ["narrow"]}]
;;       [:disabled {:p/type :boolean :items  [true false]}]
;;       [:icon     {:p/type :select :items  ["/assets/img/icons/ic-plus-16.svg"]}]
;;       [:title    {:p/type :text}]])])

;; (defscene button-secondary-dangerous
;;   :collection :buttons.secondary
;;   :params (ra/atom {:use "secondary" :theme "dangerous" :icon "/assets/img/icons/ic-plus-16.svg" :title "Execute"})
;;   :title "Dangerous"
;;   [p portfolio-opts]
;;   [:div
;;    [toolkit.button/component @p (:title @p)]
;;    (ui.properties/component
;;      p
;;      [[:size     {:p/type :select :items  ["narrow"]}]
;;       [:disabled {:p/type :boolean :items  [true false]}]
;;       [:icon     {:p/type :select :items  ["/assets/img/icons/ic-plus-16.svg"]}]
;;       [:title    {:p/type :text}]])])

;; (defscene button-tertiary-default
;;   :collection :buttons.tertiary
;;   :params (ra/atom {:use "tertiary" :theme "default" :icon "/assets/img/icons/ic-plus-16.svg" :title "Execute"})
;;   :title "Default"
;;   [p portfolio-opts]
;;   [:div
;;    [toolkit.button/component @p (:title @p)]
;;    (ui.properties/component
;;      p
;;      [[:size     {:p/type :select :items  ["narrow"]}]
;;       [:disabled {:p/type :boolean :items  [true false]}]
;;       [:icon     {:p/type :select :items  ["/assets/img/icons/ic-plus-16.svg"]}]
;;       [:title    {:p/type :text}]])])

;; (defscene button-tertiary-dangerous
;;   :collection :buttons.tertiary
;;   :params (ra/atom {:use "tertiary" :theme "dangerous" :icon "/assets/img/icons/ic-plus-16.svg" :title "Execute"})
;;   :title "Dangerous"
;;   [p portfolio-opts]
;;   [:div
;;    [toolkit.button/component @p (:title @p)]
;;    (ui.properties/component
;;      p
;;      [[:size     {:p/type :select :items  ["narrow"]}]
;;       [:disabled {:p/type :boolean :items  [true false]}]
;;       [:icon     {:p/type :select :items  ["/assets/img/icons/ic-plus-16.svg"]}]
;;       [:title    {:p/type :text}]])])
