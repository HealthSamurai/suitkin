(ns ui.properties
  (:require
   [clojure.string :as str]
   [stylo.core :refer [c]]))

(defn component
  [state properties]
  [:div {:class (c [:mt 4] :border-t [:pt 4])}
   [:b "Properties"]
   [:div {:class (c [:mt 2] [:space-y 1])}
    (for [[property options :as p] properties] ^{:key (hash p)}
      [:div
       [:span {:class (c {:opacity "0.5"} :inline-flex [:w "100px"])} (str/capitalize (name property)) ": "]
       (case (:p/type options)
         :select
         [:select {:class (c :border [:p 1] :rounded)
                   :value (or  (get @state property) "default")
                   :on-change #(swap! state assoc property (let [v (.. % -target -value)]
                                                             (if (= v "default") nil v)))}
          (for [i (if (:clear? options)
                    (cons "default" (:items options))
                    (:items options))] ^{:key (hash i)}
            [:option {:value i} i])]
         :boolean
         [:input {:type "checkbox" :value (get @state property)
                  :on-change #(swap! state assoc property (.. % -target -checked))}]
         :text
         [:input {:class (c :border [:p 1] :rounded)
                  :value (get @state property)
                  :on-change #(swap! state assoc property
                                     (.. % -target -value))}]
         nil)])]])
