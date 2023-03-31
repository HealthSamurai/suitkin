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
     :rounded
     [:items-center]
     [:px "12px"]
     [:py "4px"]
     [:overflow-hidden]
     [:space-x 2]
     [:leading-relaxed]
     ;;[:border :portal-fg-fill]
     {:outline "1px solid #DADCE0"}
     {:transition "outline 0.3s ease-out"}
     [:bg :white]
     {:letter-spacing "1px"}
     [:focus-within {:outline "2px solid #105FE1"
     :transition "outline 0.3s ease-out"}]
     [:focus :outline-none :shadow-none #_[:border :portal-primary-red]]
     ))

(def disabled-class
  (c [:text :gray-500]
     [:bg :gray-200]
     [:border :gray-400]
     :cursor-not-allowed
     [:hover [:text :gray-500] [:border :gray-400]]))

(def input-class
  (c [:py 1]
     [:leading-relaxed]
     {:font-family "Inter"
      :font-size "16px"
      :font-weight "400"
      :letter-spacing "0.1px"}
     :flex-auto
     :w-full
     [:w-min 0]
     ;; [:text :black]
     [:focus :outline-none]
     [:bg :transparent]
     [:disabled :cursor-not-allowed]))

(def label-class
  ;;TODO: Oleg finish
  ;;this one without star
  (c ))

(def label-required-class
  ;;TODO: Oleg finish
  ;;this one with star
  (c ))

(def tooltip-icon-class
  ;;TODO "works on hover with text class"
  (c ))


(def label-wrapper-class
  (c [:py 1]
     :flex-auto
     [:w-min 0]
     ;; [:text :black]
     [:bg :transparent]
     [:disabled :cursor-not-allowed]))

(def description-wrapper-class
  (c [:py 1]
     :flex-auto
     [:w-min 0]
     ;; [:text :black]
     [:bg :transparent]
     [:disabled :cursor-not-allowed]))

(comment

  ;;We decide not to parametrize with icon
  #_[:div.icon
   [:div.popup [:div.arrow-left] [:div.text (:text i)]]
   [:div {:class (str "navigation-" (:icon i) "-icon")}]])

(def tooltip-class
  (c :cursor-pointer
     [:hover [[".tooltip" {:display :inline-block

                           :font-family "Inter";
                           :font-size "12px";
                           :line-height "15px";
                           :letter-spacing "0.001em";
                           :color "#FFFFFF"
                           :padding "10px"

                           :margin-top "-54px"
                           :border-radius "4px"
                           :background "rgba(0, 0, 0, .8)"
                           :position "absolute"}]]]))

(def tooltip-text-class
  (c [:hidden]))

(def description-class
  (c))

(defn static-path
  [img-name]
  (str "../../assets/img/" img-name))


(defn tooltip-icon
  [tooltip-text]
  [:div {:class [tooltip-class]}
   [:div {:class [ tooltip-icon-class]} "?"] ;;TODO: hardcoded
   [:div {:class ["tooltip" tooltip-text-class]} tooltip-text]])

(defn input-label
  [label-text required]
  [:div {:class (c :inline-flex)} [:div {:class (c [:mr 2]) } label-text]
   (when required [:div {:class (c [:text :red-600])} "*"])])

(defn lable-area
  [label label-required tooltip]
  [:div (cond (and label tooltip)
              [:div {:class (c :inline-flex)}
               [input-label label false]
               [tooltip-icon tooltip]]

              (and label-required tooltip)
              [:div {:class (c :inline-flex)}
               [input-label label-required true]
               [tooltip-icon tooltip]]

              label-required
              [input-label label-required true]
              label
              [input-label label false]
              tooltip
              [tooltip-icon tooltip])])

(defn description-area
  [description]
  [:div {:class [description-wrapper-class]}
   [:div {:class [description-class]} description]])

(def total-input-class
  (c [:flex-inline]))

(defn icon
  [img-name]
  (when img-name
    [:div [:img {:src #_"https://fastly.picsum.photos/id/72/200/300.jpg?hmac=8tyK7lgBqIQNIGPVnmsVP3SL5bYCsSDmdZtnIJNQv3o"
                 (static-path img-name)
                 :class (c [:ml "20px"]
                           {:max-width "none"
                            :width "12px"
                            :height "12px"})}]]))


(defn left-input-area
  [text-left icon-left]
  (cond icon-left [icon icon-left]
        :else (when text-left [:div {:class (c {:border-right "1px solid"
                                                :width "20%"
                                                :white-space "nowrap" ;
                                                :overflow "hidden"
                                                :text-overflow "ellipsis"})
                          :title text-left}
                    text-left])))

(defn right-input-area
  [text-right icon-right]
  (cond icon-right [icon icon-right]
        :else (when text-right [:div {:class (c {:border-left "1px solid"
                                                 :padding-left "2px"
                                                 :width "20%"
                                                 :white-space "nowrap" ;
                                                 :overflow "hidden"
                                                 :text-overflow "ellipsis"})
                          :title text-right}
                    text-right])))

(defn input-with-text-area
  [props text-left text-right icon-left icon-right]
  ;;TODO: should left and right text be added to the input value?
  [:div {:class (c :w-full
                   {:display "flex"
                    :flex-direction "row"})}
   [left-input-area text-left icon-left]
   [:input (merge (dissoc props :class :input-class :prefix :suffix :base-class)
                  {:class [input-class (class-names (:input-class props))]})]
   [right-input-area text-right icon-right]])

(defn input
  [{:keys [label label-required tooltip
           icon-left icon-right
           description text-left text-right] :as props}]
  [:div {:class [description-wrapper-class]}
   [:div {:class [label-wrapper-class]}
    [lable-area label label-required tooltip]
    [:div {:class [(or (:base-class props) base-class)
                   (when (:disabled props) disabled-class)
                   (class-names (:class props))]}
     (:prefix props)
     [input-with-text-area props text-left
      text-right icon-left icon-right]
     (when (:suffix props)
       [:div {:class (c :absolute :h-full [:right 0] :flex :items-center :justify-center
                        {:border-left "1px solid #DADCE0"})}
        (:suffix props)])]]
   [description-area description]])

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
