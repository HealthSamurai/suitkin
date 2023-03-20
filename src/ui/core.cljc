(ns ui.core
  (:require
   #?(:cljs [reagent.dom.client])
   #?(:cljs [cljsjs.react])
   [ui.pages :as pages]
   [re-frame.core :as rf]
   [clojure.string :as str]
   #_[reagent.dom.client :as reagent]
   [suitkin.zf.routing]
   [ui.routes :as routes]
   [suitkin.demo]
   [stylo.core :refer [c]]))


(def main-wrapper-class
  (c {:display "grid"
      :grid-template-columns "20% 80%"}))

(def aside-class
  (c {:padding "40px 20px"}))

(defn page-wrapper [fragment fragment-params]
  [:main {:class main-wrapper-class}
   [:aside {:class aside-class}
    [:ul
     (for [route-info routes/routes-info]
       [:li {:key (:path route-info)}
        [:a {:href (str "#" (:path route-info))}
         (:display route-info)]])]]
   [:div {:class (c :w-full [:bg :white] [:p "0 0 400px 0"])
          :style {:background-color "var(--suitkin-body-bg)"
                  :min-height       "100vh"
                  :color            "var(--suitkin-body-color)"}}
    [:div {:class (c [:w "800px"] :mx-auto)}
     [fragment fragment-params]]]])

(defn current-page []
  (let [{page :match params :params href :href}
        @(rf/subscribe [:route-map/current-route])
        cmp (get @pages/pages page)
        route-error @(rf/subscribe [:route-map/error])
        params (assoc params
                      :href href
                      :context {}
                      :route page
                      :route-ns (when page (namespace page)))]
    (cond
      (and page cmp) (page-wrapper cmp params)
      (and page (not cmp)) [:div.not-found (str "Component not found: " page)]
      (= route-error :not-found) [:div.not-found (str "Route not found")])))

(rf/reg-event-fx
  ::initialize
  (fn [{db :db} _]
    (let [#_#_location-hash (clojure-course.interop/get-location-hash)]
      {:db {:route-map/routes routes/routes}
       :route-map/start {}})))


(defn mount-root []
  (rf/clear-subscription-cache!)
  #?(:cljs (reagent.dom.client/render
            (-> "root" js/document.getElementById reagent.dom.client/create-root)
            [current-page])))

(defn ^:export main []
  (rf/dispatch-sync [::initialize])
  (mount-root))
