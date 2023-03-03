(ns suitkin.toolkit.textarea
  (:require [stylo.core :refer [c]]
            [suitkin.zf.util :refer [class-names]]
            [suitkin.zf.form :as f]
            [suitkin.zf :as zf]
            [clojure.string :as str]
            #?(:cljs [reagent.core :as r])
            #?(:cljs [reagent.dom :as dom])))


(def base-class
  (c :inline-flex
     :w-full
     [:items-center]
     [:overflow-hidden]
     [:space-x 2]
     [:leading-relaxed]
     [:border :portal-fg-fill]
     :rounded
     [:bg :portal-fg-fill]
     :transition-all [:duration 200] :ease-in-out
     [:focus-within :outline-none [:border :portal-primary-red]]
     [:hover [:border :portal-primary-red]]))

(def disabled-class
  (c [:text :gray-500]
     [:bg :gray-200]
     [:border :gray-400]
     :cursor-not-allowed
     [:hover [:text :gray-500] [:border :gray-400]]))

(def textarea-class
  (c [:py 1]
     [:px 2]
     [:leading-relaxed]
     :flex-auto
     [:h-min 11]
     [:w-min 0]
     :resize-none
     [:focus :outline-none]
     [:bg :transparent]
     [:disabled :cursor-not-allowed]))

(defn textarea-auto-height [props]
  #?(:cljs
     (r/create-class
      {:reagent-render (fn [props] [:textarea props])
       :component-did-mount (fn [this]
                              (let [node (dom/dom-node this)]
                                (set! (.. node -style -height) "0")
                                (set! (.. node -style -height) (str (. node -scrollHeight) "px"))))
       :component-did-update (fn [this]
                               (let [node (dom/dom-node this)]
                                 (set! (.. node -style -height) "0")
                                 (set! (.. node -style -height) (str (. node -scrollHeight) "px"))))})))

(defn textarea
  [props]
  (let [textarea-props (merge (dissoc props :class :textarea-class :prefix :suffix :auto-height)
                              {:class [textarea-class (class-names (:textarea-class props))]})]
    [:<>
     [:span {:class (c :font-normal)
             :dangerouslySetInnerHTML {:__html (:label props)}}]
     [:div {:class [base-class
                    (when (:disabled props) disabled-class)
                    (class-names (:class props))]}
      (:prefix props)

      (if (:auto-height props)
        [textarea-auto-height textarea-props (:value props)]
        [:textarea textarea-props])
      (:suffix props)]]))

(defn zf-textarea
  [props]
  (let [o (:opts props)]
    [textarea
     (merge (dissoc props :opts :on-change :on-focus)
            (when o
              {:value     (or @(zf/subscribe [:zf/value o]) "")
               :id        (suitkin.zf.form/get-id o)
               :on-focus (:on-focus props)
               :on-change (fn [e]
                            (when-let [f (:on-change props)]
                              (f e))
                            (zf/dispatch [:zf.form/set-value (assoc o
                                                                    :value (.. e -target -value)
                                                                    :auto-commit false)]))
               :on-blur   #(zf/dispatch [:zf/commit-value o])}))]))
