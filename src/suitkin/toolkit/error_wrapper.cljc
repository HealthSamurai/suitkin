(ns suitkin.toolkit.error-wrapper
  (:require
   [stylo.core :refer [c c?]]
   [zf]
   [zf.util :refer [class-names]]
   [zf.form :as f]))

(def wrapper-tooltip-class
  (c :absolute
     [:z 3]
     [:bottom "100%"]
     :hidden
     [:left "-1px"]
     :text-sm :font-normal
     [:bg :portal-primary-red] [:text :white] :shadow-sm
     [:py 1] [:px 2] :rounded
     [:mb 2]
     [:before :absolute [:w 0] [:h 0] :border-solid [:top "100%"] [:left 2]
      {:content      "''"
       :border-width "4px 4px 0 4px"
       :border-color "#EA4A35 transparent transparent transparent"}]))

(defn error-messages
  [error]
  (if (map? error)
    (->> (vals error)
         (map error-messages))
    (str error)))

(defn wrapper
  [props & _]
  (let [error (zf/subscribe [:zf/error (:opts props)])
        schema (zf/subscribe [:zf/schema (:opts props)])]
    (fn [props & children]
      (let [error @error
            required? (:zf/required @schema)]
        [:div {:class [(class-names (:class props))
                       (when error (c [:text :portal-primary-red]))
                       (c [:hover [[".wrapper-error" {:display "block"}]]])]}
         (when error
           [:div {:class (c :relative)}
            [:div {:class ["wrapper-error" wrapper-tooltip-class]}
             (for [e (-> error error-messages flatten)] ^{:key (hash e)}
               [:span {:class (c :block)} e])]])
         ;;children
         (into [:<>] children)
         (when required? [:span {:class (c [:ml 0.5] [:text :portal-primary-red] )} "*"])]))))
