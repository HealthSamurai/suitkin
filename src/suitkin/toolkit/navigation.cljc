(ns suitkin.toolkit.navigation
  (:require [stylo.core :refer [c]]
            [re-frame.core :as rf]))


(def default-item-content-class
  (c {#_#_:all "unset"
      :cursor "pointer"
      :display "flex"
      :flex-direction "row"
      :align-items "center"
      :top "20px"
      :left "19px"
      :padding "8px 16px 8px 16px"
      :border-radius "4px"
      :gap "8px"}
     [:hover {:background "#F7F7F8"}]
     [:focus-within {:background "#EDEEF1"}]
     [:focus {:background "#EDEEF1"}]
     ))


(rf/reg-event-db
 ::update-item-state
 (fn [db [_ item-path value]]
   (update-in db (vec (concat [::state] item-path [::item-state])) merge value)))


(rf/reg-sub
 ::state
 (fn [db _]
   (::state db)))


(rf/reg-sub
 ::item-state
 :<- [::state]
 (fn [state [_ path]]
   (get-in state (conj path ::item-state))))


(def default-item-content-wrapper-class
  (c {:flex-grow "1"}))


(def default-item-collapsed-class
  (c {:display none}))


(def default-item-inner-class
  (c {:padding-left "16px"}))


(defn icon
  [icon-name]
  [:img {:src (str "/assets/img/icons/ic-" icon-name "-16.svg")}])


(declare item-wrapper)


(defn item-content
  [{icon-name :icon
    :keys [content children parent-path link id]
    :as content-item}]
  (let [path ((fnil conj []) parent-path id)
        _ (rf/dispatch [::update-item-state path (select-keys content-item [:expanded?])])

        item-state (rf/subscribe [::item-state path])]
    (fn []
      (let [{:keys [expanded?]} @item-state]
        [:div
         [:div {:class default-item-content-class
                :tabindex 0
                :on-click (when children #(rf/dispatch [::update-item-state path {:expanded? (not expanded?)}]))}
          (when icon-name [icon icon-name])
          [:div {:class default-item-content-wrapper-class} content]
          (when children [icon (if expanded? "chevron-down" "chevron-right")])]
         (when children
           [:ul {:class [default-item-inner-class (when-not expanded? default-item-collapsed-class)]}
            (for [child children]
              ^{:key (:id child)}
              [item-wrapper (assoc child :parent-path path)])])]))))


(defn item-wrapper
  [{icon-name :icon
    :keys [children link]
    :as item}]
  (let [item-to-render [item-content item]]
    (if (and link (not children))
      [:a {:href link} item-to-render]
      item-to-render)))


(defn root
  [{:keys [children title] :as _content}]
  [:div
   [:ul {:style {:width "232px"}}
    (for [child children]
      ^{:key (:id child)}
      [item-wrapper child])]])


(defn component
  [content-items]
  [:nav [root content-items]])
