(ns suitkin.core
  (:require #?(:cljs [react :as react])
            [stylo.core :refer [c]]))

(def main-header-class
  (c :text-3xl
     :font-bold
     [:p "40px 0 60px 0"]))

(def second-header-class
  (c :text-2xl
     [:p "8px 6px"]))

(def third-header-class
  (c :text-l
     {:color "gray"}
     [:p "10px 6px"]))

(defn header
  [title & [subtitle]]
  [:header
   [:h1 {:class main-header-class} title]
   (when subtitle
     [:h2 {:class second-header-class} subtitle])])

(def code-block-class
  (c :block
     [:p "14px 20px"]
     {:font-size "12px"
      :font-family "JetBrains Mono"}))

(def component-wrapper-class
  (c [:bg :white]
     {:border "1px solid #EEE"
      :border-radius "12px"}
     [:m "0 0 50px 0"]))

(def component-code-wrapper-class
  (c [:p "14px 20px"]
     {:display "flex"
      :flex-direction "row-reverse"
      :justify-content "space-between"
      :gap "10px"
      :border-radius "0 0 11px 11px"
      :border-top-width "1px"}))

(def component-class
  (c [:m 10]
     [:space-y 5]
     [:w "360px"]
     :mx-auto
     {:font-family "Inter"
      :display "flex"
      :justify-content "center"}))

(def code-copy-btn-class
  (c [:p 1.5]
     {:display "grid"
      :place-items "center"
      :font-size "18px"}))

(defn component
  [component component-code]
  (fn [component component-code]
    (let [code-el-ref (react/useRef nil)

          [copy-clicked? set-copy-clicked?]
          (react/useState false)]
      (react/useEffect (fn []
                         (let [code-el (.-current code-el-ref)
                               highlight-fn #(.highlightElement js/hljs code-el)]
                           (when code-el
                             (try (if (.includes (.listLanguages js/hljs) "clojure")
                                    (highlight-fn)
                                    (js/setTimeout highlight-fn 500))
                                  (catch js/Error e (js/setTimeout highlight-fn 500))))
                           js/undefined))
                       (array component-code))
      [:div {:class component-wrapper-class}
       [:div {:class component-class}
        component]
       [:div {:class component-code-wrapper-class}
        [:button {:class code-copy-btn-class
                  :on-click (fn []
                              (.writeText (.-clipboard js/navigator) component-code)
                              (set-copy-clicked? true)
                              (js/setTimeout #(set-copy-clicked? false) 1500))}
         (if copy-clicked? [:i.fa-solid.fa-copy] [:i.fa-light.fa-copy])]
        [:pre [:code.language-clojure {:ref code-el-ref
                                       :class code-block-class}
               component-code]]]])))
