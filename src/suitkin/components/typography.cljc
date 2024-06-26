(ns suitkin.components.typography
  (:require
   [suitkin.utils :as u]
   [stylo.core :refer [c]]))

(defn h1
  [options & content]
  (into 
   [:h1 (merge {:class [(c {:font-family "Inter"
                            :font-size   "28px"
                            :font-weight "700"
                            :color       "#212636"})
                        (:class options)]}
               (dissoc options :class))]
   content))

(defn label
  [& content]
  (let [options? (map? (first content))
        options  (when options? (first content))
        elements (if options? (next content) content)]
    (into 
     [:label (merge {:class [(c {:font-family "Inter"
                                 :margin-left "1px"
                                 :font-size   "14px"
                                 :font-weight "400"
                                 :line-height "20px"
                                 :margin-bottom "6px"
                                 :color         "#010205"})
                             (:class options)]}
                    (dissoc options :class))]
     elements)))

(defn span
  [& content]
  (let [properties? (map? (first content))
        properties (when properties? (first content))
        content    (if properties? (next content) content)]
    (into
     [:span {:class (into [(c {:font-family "Inter"
                               :display "inline-block"
                               :font-size "14px"
                               :font-weight "400"})]
                          (if (vector? (:class properties))
                            (:class properties)
                            [(:class properties)]))}]
     content)))

(defn kv-list
  [properties entries]
  [:div
   (for [[title value] entries
         :when (not-empty value)] ^{:key title}
     [:div {:class (c [:mb 0.5] :flex :flex-wrap [:col-gap 1.5])}
      [:span {:class [(c {:color "var(--basic-gray-6, #616471)" :font-weight "500"})
                      (:title-class properties)] } (str title ": ")]
      [:div {:data-key title} value]])])

(defn expandeable-text
  [properties & content]
  (let [open? (u/ratom false)]
    (fn [properties content]
      (if (:expandeable? properties)
        [:div {:class (into [(c {:font-family "Inter" :line-height "20px"})]
                            (if (vector? (:class properties))
                              (:class properties)
                              [(:class properties)]))}  
         [:span {:style (when-not @open?
                          {:-webkit-line-clamp (:max-lines properties 3)
                           :-webkit-box-orient "vertical"
                           :overflow           "hidden"
                           :text-overflow      "ellipsis"})
                 :class [(c {:display "-webkit-box" :border-bottom      "1px dashed #BFC1C7"})
                         (:class properties)]}
          content]
         [:div {:class (c :flex :justify-center)}
          [:div {:on-click #(swap! open? not)
                 :class (c [:px 4]
                           [:hover {:opacity "0.8"}]
                           {:cursor "pointer"
                            :width "max-content"
                            :background-color "#EDEEF1"
                            :border "1px solid #BFC1C7"
                            :border-top "none"
                            :border-radius "0 0 4px 4px"})}
           [:img {:width "16" 
                  :height "16"
                  :src (if @open?
                         (u/public-src "/suitkin/img/icon/ic-chevron-up-16.svg")
                         (u/public-src "/suitkin/img/icon/ic-chevron-down-16.svg"))}]]]]
        [:span {:class (:class properties)} content]))))

(defn assistive-text
  [& content]
  (let [options? (map? (first content))
        options  (when options? (first content))
        elements (if options? (next content) content)]
    (into 
     [:span (merge {:class [(c {:font-family "Inter"
                                :font-size   "14px"
                                :font-weight "400"
                                :line-height "20px"
                                :color "#DF351F"})
                            (:class options)]}
                   (dissoc options :class))]
     elements)))

(defn divider-with-text
  [options text]
  [:div {:class [(c :flex :justify-center :items-center) (:s/root-class options)]}
   [:hr {:class (c {:border "0.5px solid var(--basic-gray-2)"} :w-full)}]
   [:span {:class (c [:px 3] {:color "var(--basic-gray-5)"})} text]
   [:hr {:class (c {:border "0.5px solid var(--basic-gray-2)"} :w-full)}]])
