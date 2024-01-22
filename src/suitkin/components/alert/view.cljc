(ns suitkin.components.alert.view
  (:require [stylo.core :refer [c]]))

(def success-alert-styles
  (c {:background-color "#CFF9E3"
      :color "#037659"}
     [[".close-alert"
       {:border-color "rgba(3, 118, 89, 0.6)"
        :color "rgba(3, 118, 89, 0.6)"}
       [:&:hover
        [".close-icon"
         {:color            "#037659"
          :border-radius    "8px"
          :background-color "rgba(3, 118, 89, 0.15)"}]]]]))

(def error-alert-styles
  (c {:background-color "rgba(253, 225, 224, 1)"
      :color "rgba(189, 17, 31, 1)"}
     [[".close-alert"
       {:border-color "rgba(189, 17, 31, 0.6)"
        :color "rgba(189, 17, 31, 0.6)"}
       [:&:hover
        [".close-icon"
         {:color            "rgba(189, 17, 31, 1)"
          :border-radius    "8px"
          :background-color "rgba(189, 17, 31, 0.15)"}]]]]))

(defn component
  [options & content]
  [:div.fadeInRight
   {:class [(c :relative :font-medium [:rounded 8] {:z-index "999999"} :flex :justify-between :shadow-sm {:max-width "666px"})
            (case (:status options)
              :success success-alert-styles
              :error   error-alert-styles
              nil)]}
   [:div {:class (c :flex :items-center)}
    [:span {:class (c :text-3xl [:pl 4] [:pr 3])}
     (case (:status options)
       :success [:i.fa-solid.fa-square-check]
       :error   [:i.fa-solid.fa-square-exclamation]
       nil)]
    [:span {:class (c [:my 2] [:pr 4])} content]]
   [:div.close-alert {:class (c :flex :items-center :border-l [:my 2])
                      :on-click (:on-close options)}
    [:i.far.fa-times.close-icon {:class (c :text-xl [:mx 2] [:py 2] [:px 3] :cursor-pointer)}]]])
