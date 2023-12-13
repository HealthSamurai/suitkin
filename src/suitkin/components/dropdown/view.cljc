(ns suitkin.components.dropdown.view
  (:require
   [suitkin.components.input.view :as input]
   [suitkin.utils :as u]
   [suitkin.components.sidebar.view]
   [suitkin.components.dropdown.styles :as s]))

(defn component
  [properties]
  (let [open? (u/ratom (:open? properties false))]
    (fn [properties]
      [:div {:class s/root}
       [input/component
        (-> (:search properties)
            (assoc :type "search")
            (assoc :s/right [:img {:src (u/public-src "/suitkin/img/icon/ic-chevron-down-16.svg")}])
            (update :on-click
                    (fn [callback]
                      (fn [event]
                        (reset! open? true)
                        (when callback (callback event)))))
            (update :on-blur
                    (fn [callback]
                      (fn [event]
                        (reset! open? false)
                        (when callback (callback event))))))]
       (when @open?
         [:div {:class s/menu-items}
          (if-let [items (seq (get-in properties [:menu :items]))]
            (for [item items] ^{:key (:value item)}
              [:div {:class s/menu-item
                     :on-mouse-down (fn [event]
                                      (when-let [callback (get-in properties [:menu :on-select])]
                                        (callback event item)))}
               (:title item)])
            [:div {:class s/menu-item-empty}
             (get-in properties [:menu :not-found] "No items found")])])])))
