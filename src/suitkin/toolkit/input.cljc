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
     [:py "4px"]
     [:items-center]
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
  (c {:color "#989A9E"
      :background "#F9FAFA"
      :border "#DADCE0"}
     :cursor-not-allowed))

(def input-class
  (c [:px "12px"]
     [:py "4px"]
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
     [:disabled :cursor-not-allowed]
     [:pseudo "::placeholder" {:color "#989A9E"
                               :font-weight "300"
                               :font-size "15px"}]
     ))

(def label-class
  (c {:display "flex"
      :font-family "Product Sans"
      :font-size "12px"
      :font-weight "500"
      :letter-spacing "0.2px"
      :color "#6D737C"
      :margin-bottom "3px"
      :padding "5px"
      :cursor "default"}))

(def label-required-class
  (c :flex
     [:text :red-600]
     [:pl 1]))

(def tooltip-icon-class
  (c {:border "1.5px solid black"
      :width "14px"
      :height "14px"
      :border-radius "50%"
      :font-size "11px"
      :font-weight "500"
      :text-align "center"
      :box-sizing "content-box"
      :margin-bottom "6px"
      :line-height "baseline"}))

(def label-wrapper-class
  (c [:py 1]
     :flex-auto
     [:w-min 0]
     [:bg :transparent]
     [:disabled :cursor-not-allowed]))

(def description-wrapper-class
  (c [:py 1]
     :flex-auto
     [:w-min 0]
     [:bg :transparent]
     [:disabled :cursor-not-allowed]))

(def text-left-class
  (c
   {:display "flex"
    :align-items "center"
    :border-right "1px solid #DADCE0"
    :white-space "nowrap"
    :margin "-4px 0"
    :padding "10px"
    :letter-spacing "0.1px"
    :font-size "14px"
    :background "#F9FAFA"
    }))

(def text-right-class
  (c
   {:display "flex"
    :align-items "center"
    :border-left "1px solid #DADCE0"
    :white-space "nowrap"
    :margin "-4px 0"
    :padding "10px"
    :letter-spacing "0.1px"
    :font-size "14px"
    :background "#F9FAFA"
    ;;:overflow "hidden"
    ;;:width "20%"
    ;;:text-overflow "ellipsis"
    }))

(def text-right-class-modified
  (c
   {:extend text-right-class
    :background "red"}))


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
                           :line-height "18px";
                           :letter-spacing "0.001em";
                           :color "#FFFFFF"
                           :padding "10px"
                           :z-index "100"
                           :max-width "400px"

                           :margin-top "-66px"
                           :margin-left "20px"
                           :border-radius "4px"
                           :background "rgba(0, 0, 0, .8)"
                           :position "absolute"}]]]))

(def tooltip-text-class
  (c [:hidden]))

(def description-class
  (c {:display "flex"
      :font-family "Product Sans"
      :font-size "12px"
      :font-weight "400"
      :letter-spacing "0.2px"
      :margin-left "5px"
      :color "#6D737C"
      :cursor "default"}))

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
  [:div {:class label-class} [:div label-text]
   (when required [:div {:class label-required-class} "*"])])

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


(defn icon-left
  [img-name]
  (when img-name
    [:div {:class (c {:margin "7px 0 7px 12px"})}[:img {:src #_"https://fastly.picsum.photos/id/72/200/300.jpg?hmac=8tyK7lgBqIQNIGPVnmsVP3SL5bYCsSDmdZtnIJNQv3o"
                                                        (static-path img-name)
                                                        :class (c {:width "18px"
                                                                   :height "18px"})}]]))
(defn icon-right
  [img-name]
  (when img-name
    [:div {:class (c {:margin "7px 12px 7px 0"})}[:img {:src #_"https://fastly.picsum.photos/id/72/200/300.jpg?hmac=8tyK7lgBqIQNIGPVnmsVP3SL5bYCsSDmdZtnIJNQv3o"
                                                        (static-path img-name)
                                                        :class (c {:width "18px"
                                                                   :height "18px"})}]]))

(defn left-input-area
  [text-left icon-l]
  (cond icon-l [icon-left icon-l]
        :else (when text-left [:div {:class [text-left-class]
                          :title text-left}
                    text-left])))

(defn right-input-area
  [text-right icon-r]
  (cond icon-r [icon-right icon-r]
        :else (when text-right [:div {:class [text-right-class]
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
