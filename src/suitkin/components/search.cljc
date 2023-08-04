(ns suitkin.components.search
  (:require
   [zf.form]
   [re-frame.core :as rf]
   [suitkin.utils :as u]
   [stylo.core :refer [c]]))

(defn icon
  [src]
  [:img.button-icon {:widht "16px" :height "16px" :class (c [:mr "8px"]) :src (u/public-src src)}])

(defn component
  [form-path path]
  (let [value @(rf/subscribe [:zf/value {:zf/root form-path :zf/path path}])]
    [:div {:class [(c :inline-flex)]}
      [icon (u/public-src "/assets/img/search.svg")]
      [:input {:placeholder "Search"
               :value value
               :on-change (fn [event]
                            (rf/dispatch [:zf.form/set-value
                                          {:zf/root form-path :zf/path path
                                           :value (.. event -target -value)}]))}]]))
