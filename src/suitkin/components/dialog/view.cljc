(ns suitkin.components.dialog.view
  (:require
   [suitkin.components.button       :as button]
   [suitkin.utils                   :as utils]
   [suitkin.components.dialog.style :as style]))

(def show  utils/show-modal)
(def close utils/close-modal)

(defn component
  [& nodes]
  (let [properties (utils/get-component-properties nodes)
        children   (utils/get-component-children properties nodes)
        this       (utils/ratom nil)]
    [:dialog
     (utils/merge-component-properties
      {:class         style/root-class
       :ref           #(when % (reset! this %))
       :on-mouse-down #(when (identical? (utils/get-event-target %) @this)
                         (close (:id properties)))}
      properties)
     (into [:div {:on-click #(utils/stop-propagation %)}] children)]))


(defn dangerous
  [properties]
  [component
   (utils/merge-component-properties
    (dissoc properties :s/title :s/content :s/accept-text :s/accept-fn)
    {:class style/dangerous-root-class})
   [:div {:class style/dangerous-body}
    #_[:figure {:class style/dangerous-icon}
     [:img {:src (utils/public-src "/suitkin/img/icon/ic-circle-alert-16.svg")}]]
    [:div
     [:h1 {:class style/dangerous-title} (:s/title properties)]
     [:div {:class style/dangerous-content}
      (:s/content properties)]
     [:div {:class style/dangerous-button-menu}
      [button/component {:class style/cancel-button
                         :s/use "secondary" :s/size "narrow"
                         :on-click #(close (:id properties))}
       "Cancel"]
      [button/component {:class style/discard-button
                         :s/use   "primary"
                         :s/theme "dangerous"
                         :s/size  "narrow"
                         :on-click (fn [e]
                                     (when-let [accept-fn (:s/accept-fn properties)]
                                       (accept-fn))
                                     (close (:id properties)))}
       (:s/accept-text properties)]]]]])
