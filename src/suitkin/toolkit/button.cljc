(ns suitkin.toolkit.button
  (:require
   [suitkin.toolkit.spinner :refer [spin]]
   [stylo.core :refer [c c?]]
   [suitkin.zf :as zf]
   [suitkin.zf.util :refer [class-names]]))


(def base-class
  (c [:px 4] [:py 2]
     :inline-flex
     :items-center
     :cursor-pointer
     [:leading-relaxed]
     :border
     :rounded
     :whitespace-no-wrap
     [:bg :white]
     [:space-x 1]
     :transition-all [:duration 200] :ease-in-out
     [:focus :outline-none :shadow-outline]
     [:pseudo ":not(:disabled)"
      [:hover [:text :blue-500] [:border :blue-500]]
      [:active [:text :blue-800] [:border :blue-800]]]
     [:disabled [:text :gray-500] [:bg :gray-200] [:border :gray-400] :cursor-not-allowed]))

(def primary-class
  (c [:bg :blue-500]
     [:border :transparent]
     [:text :white]
     [:pseudo ":not(:disabled)"
      [:hover [:text :white] [:bg :blue-400] [:border :transparent]]
      [:active [:text :white] [:bg :blue-600] [:border :transparent]]]))

(def primary-2-class
  (c [:bg :portal-primary-red]
     [:border :transparent]
     [:text :white]
     [:pseudo ":not(:disabled)"
      [:hover [:text :white] [:bg :portal-primary-red-dimmed] [:border :transparent]]
      [:active [:text :white] [:bg :portal-primary-red-tinted] [:border :transparent]]]))

(def sdc-class
  (c {:background-color "rgb(var(--button__accent-color, var(--main-color, 120, 38, 245)))"}
     [:border :transparent]
     [:text :white]
     [:rounded 5]
     [:focus :outline-none :shadow-none]
     :tracking-wider
     :font-medium
     :text-sm
     [:space-x 0]
     [:px 6] [:py 2.5]
     [:disabled [:bg :sdc-disabled-bg] [:text :sdc-disabled-fg] :border-none]
     [:pseudo ":not(:disabled)"
      [:hover [:text :white]
       {:background-color "rgba(var(--button__accent-color, var(--main-color, 120, 38, 245)), 0.6)"}
       [:border :transparent]]
      #_[:active [:text :white] {:background-color "rgba(98, 31, 199, 1)"} [:border :transparent]]]))

(def sdc-small-class
  (c {:background-color "rgba(var(--button__accent-color, var(--main-color)), 0.15)"}
     [:border :transparent]
     {:color "rgba(var(--button__accent-color, var(--main-color)), 1)"}
     [:rounded 5]
     [:focus :outline-none :shadow-none]
     :tracking-wider
     :font-medium
     :text-xs
     [:space-x 0]
     [:px 3] [:py 1]
     [:disabled [:bg :sdc-disabled-bg] [:text :sdc-disabled-fg] :border-none]
     [:pseudo ":not(:disabled)"
      [:hover
       {:background-color "rgba(var(--button__accent-color, var(--main-color)), 1)"
        :color "rgb(var(--button__text-color, 255, 255, 255))"}
       [:border :transparent]]
      #_[:active [:text :white] {:background-color "rgba(98, 31, 199, 1)"} [:border :transparent]]]))

(def sdc-outline-class
  (c [:border 2 :sdc-purple-2]
     [:bg :transparent]
     [:text :sdc-purple-2]
     [:rounded 8]
     [:focus :outline-none :shadow-none]
     :tracking-wider
     :font-medium
     {:font-size "0.9rem"}
     :cursor-pointer
     [:px 3] [:py 1]
     [:pseudo ":not(:disabled)"
      [:hover [:text :white] [:bg :sdc-purple-2] [:border 2 :sdc-purple-2]]
      [:active [:text :white] {:background-color "rgba(98, 31, 199, 1)"} [:border 2 :sdc-purple-2]]]))

(def danger-class
  (c [:bg :red-500]
     [:border :transparent]
     [:text :white]
     [:pseudo ":not(:disabled)"
      [:hover [:text :white] [:bg :red-400] [:border :transparent]]
      [:active [:text :white] [:bg :red-600] [:border :transparent]]]))

(def link-class
  (c [:bg :transparent]
     [:border :transparent]
     [:text :blue-600]
     [:focus :outline-none :shadow-none]
     [:pseudo ":not(:disabled)"
      [:hover [:text :blue-500] [:bg :transparent] [:border :transparent]]
      [:active [:text :blue-800] [:bg :transparent] [:border :transparent]]]
     [:disabled [:text :gray-500] [:bg :transparent] [:border :transparent] :cursor-not-allowed]))

(def text-class
  (c [:bg :transparent]
     [:border :transparent]
     [:pseudo ":not(:disabled)"
      [:hover [:text :sdc-purple-2] [:bg :sdc-hover-bg] [:border :transparent]]
      [:active [:text :inherit] [:bg :gray-200] [:border :transparent]]]
     [:disabled [:text :gray-500] [:bg :transparent] [:border :transparent] :cursor-not-allowed]
     [:px 2] [:py 1]
     ))

(def small-class
  (c [:px 3] [:py 0.25] :text-xs))

(def group-class
  (c :relative
     [:rounded 0]
     [:first-child :rounded-l]
     [:last-child :rounded-r]
     [:pseudo ":not(:first-child)" [:ml "-1px"]]
     [:focus [:z 2]] [:hover [:z 1]]))


(defn button
  [props & children]
  (into [:button (merge (dissoc props :class :type :loading :size :on-click :btn-type)
                        {:class    [base-class
                                    (case (:type props)
                                      "primary" primary-class
                                      "primary-2" primary-2-class
                                      "sdc" sdc-class
                                      "sdc-small" sdc-small-class
                                      "sdc-outline" sdc-outline-class
                                      "danger" danger-class
                                      "link" link-class
                                      "text" text-class
                                      nil)
                                    (case (:size props)
                                      "small" small-class
                                      nil)
                                    (class-names (:class props))]
                         :on-click (when-not (:loading props)
                                     (:on-click props))
                         :type (or (:btn-type props) "button")})]

        (cond-> children
          (:loading props)
          (conj [spin {:class (c [:mr 2])}]))))

(defn zf-button
  [props & children]
  (into [button (merge (dissoc props :on-click)
                       (when-let [e (:on-click props)]
                         (if (vector? e)
                           {:on-click #(zf/dispatch e)}
                           {:on-click e})))]
        children))

(defn demo
  []
  [button {} "Default"]
  [button {:size "small"} "Small"]
  [button {:disabled true} "Default Disabled"]
  [button {:loading true} "Default Loading"]

  [button {:type "primary"} "Primary"]
  [button {:type "primary" :disabled true} "Primary Disabled"]
  [button {:type "primary" :loading true} "Primary Loading"]

  [button {:type "danger"} "Danger"]
  [button {:type "danger" :disabled true} "Danger Disabled"]
  [button {:type "danger" :loading true} "Danger Loading"]

  [button {:type "text"} "Text"]
  [button {:type "text" :disabled true} "Text Disabled"]
  [button {:type "text" :loading true} "Text Loading"]

  [button {:type "link"} "Link"]
  [button {:type "link" :disabled true} "Link Disabled"]
  [button {:type "link" :loading true} "Link Loading"]

  [:span
   [button {:class group-class} "1"]
   [button {:class group-class} "2"]
   [button {:class group-class} "3"]]

  [button {:class (c :block [:flex-grow 1])} "Block"]
  [zf-button {:on-click [::hello]} "Dispatch"])


(defn view
  [attrs content]
  [(if (contains? attrs :href) :a :button)
   (merge {:class [(c :rounded
                      [:w-min "160px"]
                      [:py "13px"]
                      [:px "25px"]
                      :cursor-pointer
                      :text-center
                      {:letter-spacing "0.4px"
                       :font-size "13px"
                       :font-weight "500"
                       :font-family "Inter"})
                   (case (:variant attrs)
                     "gray" (c [:bg :portal-strokes-tinted] [:text :black] [:hover [:bg :portal-strokes]])
                     (c [:bg :black] [:text :white] [:hover {:opacity "0.8"}]))]}
          (dissoc attrs :variant))
   content])
