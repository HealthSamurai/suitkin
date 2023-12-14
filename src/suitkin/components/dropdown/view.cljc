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
      [:div {:class [s/root (:class properties)]}
       [input/component
        (merge  
         (when (and (:value properties)
                    (not (get-in properties [:search :on-change])))
           {:readOnly true
            :style {:cursor "pointer"}
            :value (get-in properties [:value :title])})
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
                         (when callback (callback event)))))))]
       (when @open?
         (let [menu (:menu properties)]
           [:div {:class s/menu-items}
            (if-let [items (seq (:items menu))]
              (for [item items] ^{:key (:value item)}
                [:div {:class [s/menu-item (when (or (and (sequential? (:value properties))
                                                          (some #(= (:value %) (:value item))
                                                                (:value properties)))
                                                     (and (map? (:value properties))
                                                          (= (:value item) (get-in properties [:value :value]))))
                                             s/menu-item-selected)]
                       :on-mouse-down (fn [event]
                                        #?(:cljs (.preventDefault event))
                                        (when-let [callback (:on-select menu)]
                                          (callback event item))
                                        (when-not (:multiselect? menu)
                                          (reset! open? false)))}
                 (:title item)])
              [:div {:class s/menu-item-empty}
               (get-in properties [:menu :not-found] "No items found")])]))])))
