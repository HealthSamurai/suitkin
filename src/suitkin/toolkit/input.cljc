(ns suitkin.toolkit.input
  (:require [stylo.core :refer [c]]
            [suitkin.zf.util :refer [class-names]]
            [suitkin.zf.form :as f]
            [suitkin.zf]
            [re-frame.core :as rf]
            [suitkin.zf :as zf]))


(def base-class
  (c :inline-flex
     :w-full
     :relative
     [:items-center]
     [:px "18px"]
     [:py "3px"]
     [:overflow-hidden]
     [:space-x 2]
     [:leading-relaxed]
     [:border :portal-fg-fill]
     {:border "1px solid #DADCE0"}
     :rounded
     [:focus-within :outline-none :shadow-none {:outline "2px solid #3067CB"}]
     [:focus :outline-none :shadow-none #_[:border :portal-primary-red]]
     ))

(def disabled-class
  (c [:text :gray-500]
     [:bg :gray-200]
     [:border :gray-400]
     :cursor-not-allowed
     [:hover [:text :gray-500] [:border :gray-400]]))

(def input-class
  (c [:py 1] [:leading-relaxed]
     {:font-family "Inter" :font-size "16px" :font-weight "500"}
     :flex-auto
     [:w-min 0]
     [:text :black]
     [:focus :outline-none]
     [:bg :transparent]
     [:disabled :cursor-not-allowed]))

(defn input
  [props]
  [:div {:class [(or (:base-class props) base-class)
                 (when (:disabled props) disabled-class)
                 (class-names (:class props))]}
   (:prefix props)
   [:input (merge (dissoc props :class :input-class :prefix :suffix :base-class)
                  {:class [input-class (class-names (:input-class props))]})]
   (when (:suffix props)
     [:div {:class (c :absolute :h-full [:right 0] :flex :items-center :justify-center [:w "225px"] {:border-left "1px solid #DADCE0"})}
      (:suffix props)])])

(defn zf-input
  [props]
  (let [opts        (:opts props)
        value       (zf/subscribe [:zf/value opts])
        error       (zf/subscribe [:zf/error opts])
        placeholder (when-let [path (:zf/placeholder opts)]
                      (zf/subscribe [:zf/value (assoc opts :zf/path path)]))
        on-key-down (:on-key-down props)
        on-change   #(zf/dispatch
                      [:zf.form/set-value
                       (assoc opts
                              :value #?(:cljs (.. % -target -value)
                                        :clj  (-> % :target :value))
                              :auto-commit false)])
        on-blur     (comp (or (:on-blur props) identity) #(do (zf/dispatch [:zf/commit-value opts])
                                                              (zf/dispatch [:zf/blur opts])))
        on-focus    (comp (or (:on-focus props) identity) #(zf/dispatch [:zf/focus opts]))]
    (fn [props]
      [input
       (merge (dissoc props :opts)
              (when opts
                (cond-> {:value       (or @value "")
                         :id          (f/get-id opts)
                         :placeholder (or (and placeholder @placeholder) (:placeholder props))
                         :on-change   on-change
                         :on-blur     on-blur
                         :on-focus    on-focus}
                  on-key-down
                  (assoc :on-key-down on-key-down)
                  (not-empty @error)
                  (update :class (fnil conj []) (c {:outline "2px solid #dc2626"})))))])))

(defn parse-int [s]
  (when s
    #?(:clj (try (Integer/parseInt s) (catch Exception e nil))
       :cljs (try (js/parseInt s) (catch :default e nil)))))


(defn parse-float [s]
  (when s
    #?(:clj (try (Double/parseDouble s) (catch Exception e nil))
       :cljs (try (js/parseFloat s) (catch :default e nil)))))



(defn zf-number
  [props]
  [input
   (merge
     (dissoc props :opts :float :forbid-negative)
     (when-let [o (:opts props)]
       (let [number-parser (if (:float props) parse-float parse-int)
             forbid-negative (if (:forbid-negative props) #?(:clj (fn [e] (Math/abs e))
                                                             :cljs js/Math.abs) identity)
             comp-fns [forbid-negative number-parser]
             transform-fn (apply comp comp-fns)
             value @(rf/subscribe [:zf/value o])]
         {:value value
          :type      "number"
          :on-change (fn [e]
                       (zf/dispatch-sync [:zf.form/set-value (assoc o
                                                                    :value (some-> (.. e -target -value)
                                                                               not-empty
                                                                               transform-fn)
                                                                    :auto-commit false)]))
          :on-blur   #(do (zf/dispatch [:zf/commit-value o])
                          (zf/dispatch [:zf/blur o]))
          :on-focus  #(zf/dispatch [:zf/focus o])
          :on-wheel
          (fn [e]
            (.blur (.-currentTarget e)))})))])

(defn zf-datetime [props]
  [input
   (merge
     (dissoc props :opts :float :forbid-negative)
     (when-let [o (:opts props)]
       (let [value @(rf/subscribe [:zf/value o])]
         {:value value
          :type      "date"
          :on-change (fn [e]
                       (zf/dispatch-sync [:zf.form/set-value (assoc o
                                                                    :value (some-> (.. e -target -value))
                                                                    :auto-commit false)]))
          :on-blur   #(do (zf/dispatch [:zf/commit-value o])
                          (zf/dispatch [:zf/blur o]))
          :on-focus  #(zf/dispatch [:zf/focus o])
          :on-wheel
          (fn [e]
            (.blur (.-currentTarget e)))})))]
  )


(defn zf-date [props]
  [input
   (merge
    (dissoc props :opts :float :forbid-negative)
    (when-let [o (:opts props)]
      (let [value @(rf/subscribe [:zf/value o])]
        {:value value
         :type      "date"
         :on-change (fn [e]
                      (zf/dispatch-sync [:zf.form/set-value (assoc o
                                                                   :value (some-> (.. e -target -value))
                                                                   :auto-commit false)]))
         :on-blur   #(do (zf/dispatch [:zf/commit-value o])
                         (zf/dispatch [:zf/blur o]))
         :on-focus  #(zf/dispatch [:zf/focus o])
         :on-wheel
         (fn [e]
           (.blur (.-currentTarget e)))})))]
  )

(defn zf-time [props]
  [input
   (merge
    (dissoc props :opts :float :forbid-negative)
    (when-let [o (:opts props)]
      (let [value @(rf/subscribe [:zf/value o])]
        {:value value
         :type      "time"
         :on-change (fn [e]
                      (zf/dispatch-sync [:zf.form/set-value (assoc o
                                                                   :value (some-> (.. e -target -value))
                                                                   :auto-commit false)]))
         :on-blur   #(do (zf/dispatch [:zf/commit-value o])
                         (zf/dispatch [:zf/blur o]))
         :on-focus  #(zf/dispatch [:zf/focus o])
         :on-wheel
         (fn [e]
           (.blur (.-currentTarget e)))})))]
  )
