(ns suitkin.toolkit.radio-blocks
  (:require [zf]
            [zf.form :as f]
            [zf.util :refer [class-names]]
            [stylo.core :refer [c]]
            [clojure.string :as str]))


(def radio-block-base
  (c :items-center [:py 3] [:px 15]
     [:border :portal-fg-fill]
     :align-middle :text-center :cursor-pointer
     :rounded [:bg :portal-fg-fill]
     :transition-all [:duration 200] :ease-in-out
     [:hover [:bg :portal-fg-fill-dimmed]]))


(def radio-block-checked
  (c [:border :portal-primary-red]))

(defn radio-block
  [props]
  (let [i [:input (merge (dissoc props :class :label-class)
                         {:class (c :hidden)
                          :type  "radio"})]]
    (if (:label props)
      [:label {:class [(:label-class props)
                       radio-block-base
                       (when (:disabled props)
                         (c [:text :portal-fg-fill-dimmed2] [:hover :cursor-not-allowed [:bg :portal-fg-fill]]))
                       (when (:checked props)
                         radio-block-checked)
                       (when (:checked props)
                         (c [:text :black] [:bg :portal-fg-fill] [:hover [:bg :portal-fg-fill]]))
                       ]}
       i [:span (:label props)]]
      i)))

(defn radio-group
  [props]
  (into [:div {:id (:id props)
               :class [(:class props) (c :flex {:gap "20px"})]}]
        (for [option (:options props)]
          ^{:key (:value option)}
          [radio-block (merge (dissoc props :opts :options :value :class :radio-class)
                              (dissoc option :class)
                              {:id (->> (concat (get-in props [:opts :zf/root])
                                                (get-in props [:opts :zf/path])
                                                [(:value option)])
                                        (map name)
                                        (str/join \.))
                               :class     [(class-names (:radio-class props)) (class-names (:class option))]
                               :checked   (when (:value props) (= (:value option) (:value props)))
                               :on-change (fn [& _]
                                            (when-let [on-change (:on-change props)]
                                              (on-change (:value option))))})])))


(defn zf-radio
  [props]
  [radio-block
   (merge
    (dissoc props :opts)
    {:checked   (or @(zf/subscribe [:zf/value (:opts props)]) false)
     :on-change #(zf/dispatch [:zf.form/set-value (:opts props) (.. % -target -checked)])})])


(defn zf-radio-group
  [props]
  [radio-group
   (merge
    props
    {:value     (or @(zf/subscribe [:zf/value (:opts props)]) [])
     :on-change #(zf/dispatch [:zf.form/set-value (assoc (:opts props) :value %)])
     :options   (:options @(zf/subscribe [:zf/schema (:opts props)]))})])
