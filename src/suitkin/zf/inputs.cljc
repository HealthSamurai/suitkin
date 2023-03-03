(ns suitkin.zf.inputs
  (:require
   [stylo.core :refer [c color c?]]
   [zf :as zf]
   [zf.form :as f]))

(def disabled-class
  (c [:text :gray-500]
     [:bg :gray-200]
     [:border :gray-400]
     :cursor-not-allowed
     [:hover [:text :gray-500] [:border :gray-400]]))

(def common-class
  (c :border
     :rounded
     :w-full
     :transition-all [:duration 200] :ease-in-out
     [:bg :white]
     [:disabled :cursor-not-allowed]
     [:bg :gray-100]
     [:px 2]
     [:py 1]
     [:space-x 2]
     [:focus :outline-none]))

(def input-class
  (c
   [:focus [:border :blue-500] [:input-shadow :blue-400]]
   [:hover  [:input-shadow :blue-400]]))

(def error-class
  (c [:border :red-600]
     [:focus [:border :red-600] [:input-shadow :red-500]]
     [:hover [:input-shadow :red-500]]))

(defn errors [errs]
  [:div {:class (c :flex [:space-x 3] :text-sm [:text :red-500])}
   (for [[k err] errs]
     [:div {:key k} err])])

(defn input
  [props]
  (let [value       (zf/subscribe [:zf/value props])
        error       (zf/subscribe [:zf/error props])
        on-key-down (:on-key-down props)
        on-change   #(let [v (.. % -target -value)
                           p (assoc props :value v :auto-commit false)]
                       ;; TODO: think about on-change 
                       (when-let [oc (:on-change props)]
                         (oc v))
                       (f/set-value p))
        on-blur     (comp (or (:on-blur props) identity) #(do (zf/dispatch [:zf/commit-value props]) (zf/dispatch [:zf/blur props])))
        on-focus    (comp (or (:on-focus props) identity) #(zf/dispatch [:zf/focus props]))
        ui-id       (f/get-id props)
        attrs (cond-> {:id  ui-id
                       :auto-complete "off"
                       :placeholder (:placeholder props)
                       :on-change   on-change
                       :on-blur     on-blur
                       :on-focus    on-focus}
                on-key-down (assoc :on-key-down on-key-down))]
    (fn [_]
      [:div
       [:input (assoc attrs
                      :value (or @value "")
                      :class [common-class (if @error error-class input-class)])]
       (when-let [errs @error]
         (errors errs))])))


(defn oselect
  [props]
  (let [value       (zf/subscribe [:zf/value props])
        error       (zf/subscribe [:zf/error props])
        on-key-down (:on-key-down props)
        on-change   #(let [p (assoc props :value (.. % -target -value) :auto-commit false)] (f/set-value p))
        on-blur     (comp (or (:on-blur props) identity) #(do (zf/dispatch [:zf/commit-value props]) (zf/dispatch [:zf/blur props])))
        on-focus    (comp (or (:on-focus props) identity) #(zf/dispatch [:zf/focus props]))
        ui-id       (f/get-id props)
        schema      (zf/subscribe [:zf/schema props])
        attrs (cond-> {
                       :id  ui-id
                       :placeholder (:placeholder props)
                       :on-change   on-change
                       :on-blur     on-blur
                       :on-focus    on-focus}
                on-key-down (assoc :on-key-down on-key-down))]
    (fn [_]
      [:div
       [:select (assoc attrs
                      :value (or @value "")
                      :class [common-class (if @error error-class input-class)])
        [:<>
         (for [opt (:options @schema)]
           [:option {:key (:value opt) :value (:value opt)} (:title opt)])]]
       (when-let [errs @error]
         (errors errs))])))

(def btn-class
  (c [:px 4] [:py 1]
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

(def btn-primary-class
  (c [:bg :blue-500]
     [:border :transparent]
     [:text :white]
     [:pseudo ":not(:disabled)"
      [:hover [:text :white] [:bg :blue-400] [:border :transparent]]
      [:active [:text :white] [:bg :blue-600] [:border :transparent]]]))


(defn submit-button [opts]
  (let [attrs {:title (:title opts)}
        errs (zf/subscribe [:zf/form-errors opts])]
    (fn []
      [:div
       (if (empty? @errs)
         [:button (assoc attrs :class [btn-class btn-primary-class]
                         :on-click #(f/submit opts))
          (:title opts)]
         [:button (assoc attrs :class [btn-class], :disabled true)
          (:title opts)])])))

(def base-class
  (c :border
     :relative
     :rounded
     :w-full
     :transition-all [:duration 200] :ease-in-out
     [:bg :white]
     [:disabled :cursor-not-allowed]
     [:bg :gray-100]
     [:space-x 2]
     [:focus :outline-none]))

(def value-class
  (c [:bg :gray-100]
     :rounded
     [:px 2]
     [:py 1]))

(def dropdown-class
  (c :absolute
     :rounded
     :leading-relaxed {:box-shadow "0 3px 6px -4px rgba(0,0,0,.12), 0 6px 16px 0 rgba(0,0,0,.08), 0 9px 28px 8px rgba(0,0,0,.05)"}
     :overflow-x-hidden
     :overflow-y-auto
     :border
     [:h-max 60]
     [:bg :white]
     [:p 0]
     [:m 0]
     [:z 100]
     [:mt "2px"]
     [:top "100%"]
     [:left "-1px"]
     [:right "-1px"]))

(zf/reg-sub is-open
            (fn [db [_ opts]]
              (f/state db opts [:popup])))

(zf/reg-sub options-sub
            (fn [db [_ opts]]
              (:options (f/schema db opts))))

(zf/reg-fx
 :outside/click
 (fn [params]
   (let [id (:id params)
         ev [(:event params) (:args params)]]
     #?(:cljs (let [hndl (atom nil)
                    h (fn [x]
                        (when-not (.contains (js/document.getElementById id) (.. x -target))
                          (zf/dispatch ev)
                          (js/document.removeEventListener "mousedown" @hndl false)
                          (js/document.removeEventListener "touchend" @hndl false)))]
                (reset! hndl h)
                (js/document.addEventListener "mousedown" h false)
                (js/document.addEventListener "touchend" h false))))))

(zf/defx close-popup [{db :db} opts]
  {:db (f/set-state db opts [:popup] false)})

(zf/defx open-popup [{db :db} opts]
  (let [iopen (f/state db opts [:popup])]
    {:db (f/set-state db opts [:popup] (not iopen))
     :outside/click {:event ::close-popup
                     :id (f/get-id opts)
                     :args opts}}))

(zf/defx select-option [{db :db} opts opt]
  {:db (f/set-state db opts [:popup] false)
   :dispatch [::f/set-value (assoc opts :value (:value opt))]})

(defn options [opts]
  (let [options (zf/subscribe [options-sub opts])]
    (fn [_]
      [:div {:class (c :divide-y)}
       (for [opt @options]
         [:div {:key (:value opt)
                :tab-index "0"
                :on-click #(select-option opts opt)
                :class (c [:py 1] [:px 2]
                          :cursor-pointer
                          [:focus [:bg :gray-100]]
                          [:hover [:bg :gray-100]])}
          (:title opt)])])))

(defn prevent-default [ev]
  #?(:cljs (.preventDefault ev)))

(defmethod f/init-field :select/load
  [k opts params]
  (println "Here" opts params)
  {:fxs {:zen/rpc [params]
         :success {:event :select/loaded :opts opts}}})


(defmulti render-value (fn [method v] method))

(defmethod render-value :default
  [method v]
  []
  (println "WARN: No render for " method v))

(defn select [{rv :render-value :as opts}]
  (let [is-open (zf/subscribe [is-open opts])
        value   (zf/subscribe [:zf/value opts])
        error   (zf/subscribe [:zf/error opts])
        do-open-popup #(open-popup opts)
        do-clear    #(do (select-option opts nil) (prevent-default %))
        on-key-down (fn [event]
                      (case (.-key event)
                        "Escape" (do-open-popup)
                        "Enter" (do-open-popup)
                        "ArrowUp" (do-open-popup)
                        "ArrowDown" (do-open-popup)
                        :nop))
        uid (f/get-id opts)
        do-render-value (if rv
                          (fn [v] (render-value rv v))
                          (fn [v] (str v)))]
    (fn [_]
      [:div 
       [:div {:class base-class :id uid}
        [:div {:class [value-class (if @error error-class input-class)]
               :tab-index "0"
               :on-key-down on-key-down}
         (if-let [v @value]
           [:div {:class (c :flex :items-baseline :rounded)}
            [:div {:class (c :flex-1)
                   :on-click do-open-popup} (do-render-value v)]
            [:div.fa.fa-times {:class (c  [:px 2] :cursor-pointer [:hover [:text :red-500]])
                               :on-click do-clear}]]
           [:div {:class (c :flex :items-baseline [:text :gray-500] :rounded)
                  :on-click do-open-popup}
            [:div {:class (c :flex-1)}
             (or (:placeholder opts) "select...")]
            [:div.fa.fa-chevron-down]])]
        (when @is-open
          [:div {:class dropdown-class}
           (when (:searchable opts)
             [:input {:class [(c :block [:m 1]) value-class base-class] :auto-focus true}])
           [options opts]])]
       (when-let [errs @error]
         (zf.inputs/errors errs))])))
