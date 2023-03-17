(ns ui.core
  (:require #?(:cljs reagent.dom.client)
   [cljsjs.react]
   [ui.pages :as pages]
   [re-frame.core :as rf]
   [clojure.string :as str]
   #_[reagent.dom.client :as reagent]
   [suitkin.zf.routing]
   [ui.routes :as routes]
   [suitkin.demo]))


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
      (and page cmp) [cmp params]
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
