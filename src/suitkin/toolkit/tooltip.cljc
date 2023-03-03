(ns suitkin.toolkit.tooltip
  (:require
   #?(:cljs [reagent.core :as r])
   [stylo.core :refer [c]]))

(def wrapper-tooltip-class
  (c :absolute
     [:z 3]
     [:bottom "100%"]
     [:left "-1px"]
     [:bg :sdc-gray-bg] [:text :sdc-gray-fg] :shadow-md
     [:py 1] [:px 2] :rounded
     [:w-min 50]
     [:w-max 200]
     :text-sm
     [:mb 2]
     [:hover :cursor-pointer]
     :whitespace-no-wrap
     :overflow-x-auto
     :overflow-y-hidden
     [:before :absolute [:w 0] [:h 0] :border-solid [:top "100%"] [:left 2]
      {:content      "''"
       :border-width "4px 4px 0 4px"
       :border-color "#F0F0F0 transparent"}]))

(defn tooltip [content child]
  (let [open? #?(:cljs (r/atom false)
                 :clj (atom false))]
    (fn []
      [:div {:class (c :relative)
             :on-click (fn [e]
                         (swap! open? not))}
       (when @open?
         [:div {:class ["tooltip-text" wrapper-tooltip-class]}
          content])
       child])))
