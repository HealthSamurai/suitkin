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

(reagent.core/set-default-compiler! (reagent.core/create-compiler {:function-components true}))

(def main-wrapper-class
  (c {:width "1200px"
      :margin "0 auto"
      :display "grid"
      :grid-template-columns "20% 80%"}))

(def aside-class
  (c {:padding "40px 20px"
      :margin-top "50px"
      }))

(def nav-item-class
  (c {:padding "7px"}))

(defn page-wrapper [fragment fragment-params]
  [:main {:class main-wrapper-class}
   [:aside {:class aside-class}
    [:ul
     (for [route-info routes/routes-info]
       ;; TODO add nav-item-class
       [:li {:key (:path route-info) :class nav-item-class}
        [:a {:href (str "#" (:path route-info))}
         (:display route-info)]])]]
   [:div {:class (c [:bg :white] [:p "0 0 400px 0"])
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


(def root-el #?(:cljs (-> "root" js/document.getElementById reagent.dom.client/create-root)))

(defn render []
  #?(:cljs (reagent.dom.client/render
            root-el
            [current-page])))

(defn ^:dev/after-load re-render []
  #?(:cljs (render)))

(defn mount-root []
  (rf/clear-subscription-cache!)
  (render))

(defn ^:export main []
  (rf/dispatch-sync [::initialize])
  (mount-root))
