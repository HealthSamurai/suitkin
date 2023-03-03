(ns suitkin.toolkit.select
  (:require [stylo.core :refer [c]]
            [suitkin.zf.util :refer [class-names]]
            [suitkin.zf.form :as f]
            [suitkin.zf :as zf]
            [clojure.string :as str]))

(def default-id-fn identity)


(defmulti search-fn
  (fn [k options term] k))

(defmethod search-fn :default
  [_ options term]
  (->> options
       (keep
        (fn [option]
          (when-let [index (str/index-of
                            (some-> (str option) (str/lower-case))
                            (some-> term (str/lower-case)))]
            [option index])))
       (sort-by second)
       (mapv first)))


(defmulti value-fn
  (fn [k option] k))

(defn scroll-into-view
  [node]
  #?(:cljs (-> node (.scrollIntoView #js {:block "nearest"}))))


(def base-class
  (c :relative
     :inline-flex
     :w-max-full
     :w-full
     [:leading-relaxed]
     [:py "7px"]
     [:pr 7]
     {:border "1px solid #DADCE0"}
     {:border-radius "4px"}
     :items-center
     [:focus-within {:outline "2px solid #3067CB"}]
     [:pseudo "[disabled]" [:bg :gray-200] [:border :gray-400] [:text :gray-500] :cursor-not-allowed]))


(def icon-class
  (c [:text :gray-400]
     [:w 7]
     :absolute
     [:right 0]
     [:top 0]
     [:bottom 0]
     {:border-left "1px solid #DADCE0"}
     [:px "28px"]
     :justify-center
     :flex
     :items-center
     [:text :black]
     [:before [:mt 0.5]]
     {:border-radius "0 4px 4px 0"}
     [:hover {:background-color "#F8F8F9" :cursor "pointer"}]
     [:pseudo ":[disabled]" :cursor-not-allowed]
     #_[:pseudo ":not([disabled])" [:hover [:text :gray-500]]]))


(def value-class
  (c :absolute
     :truncate
     [:left "18px"]
     [:right 6]
     {:font-family "Inter" :font-size "16px" :font-weight "500"}
     :pointer-events-none))


(def search-class
  (c [:bg :transparent]
     [:pl "18px"]
     :w-full
     :flex-1
     :cursor-default
     [:placeholder {:font-size "12px" :color "#6D737C" :font-family "Inter" :font-weight "500"}]
     [:disabled :cursor-not-allowed]
     [:focus :outline-none :cursor-text]))


(def dropdown-class
  (c :absolute
     :rounded
     :leading-relaxed
     :shadow-lg #_{:box-shadow "0 3px 6px -4px rgba(0,0,0,.12), 0 6px 16px 0 rgba(0,0,0,.08), 0 9px 28px 8px rgba(0,0,0,.05)"}
     :overflow-x-hidden
     :overflow-y-auto
     [:h-max 60]
     {:background-color "white"}
     #_[:bg :portal-fg-fill]
     {:border "1px solid #DADCE0"}
     [:z 100]
     [:mt "8px"]
     [:top "100%"]
     [:left "-1px"]
     [:right "-1px"]))


(def option-class
  (c [:px "4px"] [:py "4px"]
     :truncate
     [:hover {:background-color "#F8F8F9"}]
     :cursor-default))


(def active-option-class
  (c [:bg :portal-fg-fill-dimmed]))


(def selected-option-class
  (c))


(def empty-class
  (c [:px 2] [:py 1]
     :truncate
     :cursor-default
     :text-center
     ))

(def check-base-class
  (c :appearance-none
     :inline-block
     :align-middle
     :select-none
     [:h "22px"]
     [:w "22px"]
     {:color "rgb(109, 115, 124)"}
     [:bg :white]
     {:border "2px solid #DADCE0" :border-radius "4px"}
     
     :flex-none
     [:mr 2]
     :cursor-pointer
     [:focus :outline-none]
     [:checked
      [:border :transparent]
      [:bg :current]
      :bg-center
      :bg-no-repeat
      {:background-image "url(\"data:image/svg+xml,%3csvg viewBox='0 0 16 16' fill='white' xmlns='http://www.w3.org/2000/svg'%3e%3cpath d='M5.707 7.293a1 1 0 0 0-1.414 1.414l2 2a1 1 0 0 0 1.414 0l4-4a1 1 0 0 0-1.414-1.414L7 8.586 5.707 7.293z'/%3e%3c/svg%3e\")"
       :background-size "100% 100%"}]))

(def check-checked-class
  (c [:border :transparent]
     [:bg :current]
     :bg-center
     :bg-no-repeat
     [:h "22px"]
     [:w "22px"]
     {:background-color "black"}
     #_{:background-image "url(\"data:image/svg+xml,%3csvg viewBox='0 0 16 16' fill='white' xmlns='http://www.w3.org/2000/svg'%3e%3cpath d='M5.707 7.293a1 1 0 0 0-1.414 1.414l2 2a1 1 0 0 0 1.414 0l4-4a1 1 0 0 0-1.414-1.414L7 8.586 5.707 7.293z'/%3e%3c/svg%3e\")"
      :background-size "100% 100%"}))



(defn find-option
  [id-fn options value]
  (->> options
       (filter #(= (id-fn %) (id-fn value)))
       (first)))


(defn find-sibling-option
  [id-fn options value distance]
  (or (some (fn [[index option]]
              (if (= (id-fn option) (id-fn value))
                (nth options (mod (+ index distance) (count options)))))
            (map-indexed vector options))
      (first options)))


(zf/reg-sub
 search-value
 (fn [[_ opts] _]
   (zf/subscribe [:zf/state opts]))
 (fn [state _]
   (or (get state :search-value) "")))


(zf/reg-sub
 loading
 (fn [[_ opts] _]
   (zf/subscribe [:zf/state opts]))
 (fn [state _]
   (get state :loading)))


(zf/reg-sub
 opened
 (fn [[_ opts] _]
   (zf/subscribe [:zf/state opts]))
 (fn [state _]
   (get state :opened)))


(zf/reg-sub
 active
 (fn [[_ opts] _]
   (zf/subscribe [:zf/state opts]))
 (fn [state _]
   (get state :active)))


(zf/reg-sub
 should-scroll
 (fn [[_ opts] _]
   (zf/subscribe [:zf/state opts]))
 (fn [state _]
   (get state :should-scroll)))


(zf/reg-sub
 options
 (fn [[_ opts] _]
   (zf/subscribe [:zf/schema opts]))
 (fn [schema _]
   (get schema :options)))


(zf/reg-sub
 loaded-options
 (fn [[_ opts] _]
   [(zf/subscribe [:zf/schema opts])
    (zf/subscribe [:zf/state opts])])
 (fn [[schema state] _]
   (when (or (:http schema) (:rpc schema))
     (vec (get-in state [:options])))))


(zf/reg-sub
 current-options
 (fn [[_ opts] _]
   [(zf/subscribe [:zf/schema opts])
    (zf/subscribe [loaded-options opts])
    (zf/subscribe [options opts])
    (zf/subscribe [search-value opts])])
 (fn [[schema loaded-options options search-value] _]
   (let [{:keys [http rpc search-fn-key]} schema]
     (if (or http rpc)
       loaded-options
       (search-fn search-fn-key options search-value)))))

(def inject-current-options
  (zf/inject-cofx
   :sub
   (fn [[_ opts] & _]
     ^:ignore-dispose [current-options opts])))

(zf/reg-event-fx
 set-should-scroll
 (fn [{:keys [db]} [_ opts bool]]
   {:db (f/set-state db opts [:should-scroll] bool)}))

(zf/reg-event-fx
 activate
 (fn [{:keys [db]} [_ opts value]]
   {:db         (f/set-state db opts [:active] value)
    :dispatch-n [[::set-should-scroll opts true]]}))

(zf/reg-event-fx
 reset-active
 [inject-current-options]
 (fn [{:keys [db] ::keys [current-options]} [_ opts]]
   {:dispatch-n [[::activate opts (first current-options)]]}))

(zf/reg-event-fx
 load-options-ok
 (fn [{:keys [db]} [_ {:keys [opts data]}]]
   {:db (-> db
            (f/set-state opts [:loading] false)
            (f/set-state opts [:options] data))
    :dispatch-n [[::activate opts (first data)]]}))


(zf/reg-event-fx
 load-options-error
 (fn [{:keys [db]} [_ {:keys [opts]}]]
   {:db (f/set-state db opts [:loading] false)}))


(zf/reg-event-fx
 load-options
 (fn [{:keys [db]} [_ opts search-value]]
   (cond
     (:http (f/schema db opts))
     (let [http (:http (f/schema db opts))]
       (println "HTTP: " http)
       {:db         (f/set-state db opts [:loading] true)
        :json/fetch (let [aurl (get-in http [:params :alternative_url])]
                      (if (and (nil? search-value) (not (nil? aurl)))
                        {:uri aurl
                         :unbundle true
                         :success  {:event load-options-ok :opts opts}
                         :error    {:event load-options-error :opts opts}}
                        (merge (update http :params merge (cond-> {}
                                                            search-value
                                                            (assoc (:q http :q) search-value)))
                               {:unbundle true
                                :success  {:event load-options-ok :opts opts}
                                :error    {:event load-options-error :opts opts}})))})

     (:rpc (f/schema db opts))
     (let [rpc (:rpc (f/schema db opts))]
       {:db (f/set-state db opts [:loading] true)
        :zen/rpc {:method (:method rpc)
                  :params #_(get rpc :params) (if (or (nil? search-value)
                                                      (and (string? search-value)
                                                           (str/blank? search-value)))
                                                (get rpc :params)
                                                (merge (get rpc :params) {(get rpc :q :q) search-value}))
                  :success  {:event load-options-ok :opts opts}
                  :error    {:event load-options-error :opts opts}}}))))


(zf/reg-event-fx
 load-options-debounce
 (fn [_ [_ opts search-value]]
   {:dispatch-debounce
    {:delay 300
     :key   [::load-options opts]
     :event [::load-options opts search-value]}}))


(zf/reg-event-fx
 open
 (fn [{:keys [db]} [_ opts & [not-load]]]
   {:db         (f/set-state db opts [:opened] true)
    :dom/focus  (f/get-id opts)
    :dispatch-n (cond-> [[:zf/focus opts]
                         [::reset-active opts]
                         (when-not not-load [::load-options opts nil])
                         [::set-should-scroll opts true]])}))


(zf/reg-event-fx
 close
 (fn [{:keys [db]} [_ opts]]
   {:db (-> db
            (f/set-state opts [:opened] false)
            (f/set-state opts [:search-value] nil))
    :dispatch-n [[:zf/blur opts]]}))

(zf/reg-event-fx
 select
 (fn [{:keys [db]} [_ opts value multiselected?]]
   (let [schema (f/schema db opts)
         old-value  (f/value db opts)
         new-value* (if-let [vfn (:value-fn schema)]
                      (value-fn vfn value)
                      value)
         new-value  (if multiselected? [new-value*] new-value*)]
     {:dom/focus  (f/get-id opts :input)
      :dispatch-n [(when-not multiselected? [::close opts])
                   [:suitkin.zf.form/set-value (assoc opts :value  (if multiselected?
                                                             (if ((set old-value) new-value*)
                                                               (vec (remove #{new-value*} old-value))
                                                               (into (vec old-value) new-value))
                                                             new-value))]]})))

(zf/reg-event-fx
 search
 (fn [{:keys [db]} [_ opts value]]
   (let [{:keys [opened]} (f/state db opts)]
     {:db (f/set-state db opts [:search-value] value)
      :dispatch-n [[::reset-active opts]
                   [::load-options-debounce opts value]
                   (when-not opened [::open opts true])]})))


(zf/reg-event-fx
 select-active
 (fn [{:keys [db]} [_ opts]]
   (let [{:keys [active]} (f/state db opts)]
     {:dispatch-n [(when active [::select opts active])]})))


(zf/reg-event-fx
 activate-sibling
 [inject-current-options]
 (fn [{:keys [db] ::keys [current-options]} [_ opts distance]]
   (let [{:keys [opened active]} (f/state db opts)
         {:keys [id-fn] :or {id-fn default-id-fn}} (f/schema db opts)]
     {:dispatch-n [(when (and opened active)
                     [::activate opts (find-sibling-option id-fn current-options active distance)])
                   (when (not opened)
                     [::open opts])]})))

(defn zf-select
  [{:keys [opts multiselect?]}]
  (let [should-scroll (zf/subscribe [should-scroll opts])
        search-value            (zf/subscribe [search-value opts])
        opened                  (zf/subscribe [opened opts])
        value                   (zf/subscribe [:zf/value opts])
        schema                  (zf/subscribe [:zf/schema opts])
        active                  (zf/subscribe [active opts])
        current-options         (zf/subscribe [current-options opts])
        errors                  (zf/subscribe [:zf/error opts])
        loading                 (zf/subscribe [loading opts])
        on-container-mouse-down (fn [event]
                                  #?(:cljs (.preventDefault event))
                                  (when (not @opened) (zf/dispatch [::open opts])))
        on-container-key-down   (fn [event]
                                  (case (.-key event)
                                    "Escape"    (do (zf/dispatch [::close opts]) (.preventDefault event))
                                    "Enter"     (do (zf/dispatch [::select-active opts]) (.preventDefault event))
                                    "ArrowUp"   (do (zf/dispatch [::activate-sibling opts -1]) (.preventDefault event))
                                    "ArrowDown" (do (zf/dispatch [::activate-sibling opts +1]) (.preventDefault event)) nil))
        on-clear-mouse-down     (fn [event]
                                  (.stopPropagation event)
                                  (.preventDefault event)
                                  (zf/dispatch [::select opts nil]))
        on-active-option-ref    (fn [ref]
                                  (when (and ref @should-scroll)
                                    (scroll-into-view ref)
                                    (zf/dispatch [::set-should-scroll opts false])))
        on-option-mouse-down    (fn [option event]
                                  #?(:cljs (.preventDefault event))
                                  (zf/dispatch [::select opts option multiselect?]))
        on-option-mouse-move    (fn [option _]
                                  (zf/dispatch [::activate opts option]))]
    (fn [props]
      (let [search-value @search-value
            opened          @opened
            value           @value
            active          @active
            current-options @current-options
            loading         @loading
            clearable       (:clearable props true)
            disabled        (boolean (:disabled props))
            searchable      (boolean (:searchable props true))

            {:keys [id-fn] :or {id-fn default-id-fn}} @schema

            render-value    (:render-value props str)
            render-option   (:render-option props render-value)]
        [:div {:class [base-class (class-names (:class props))
                       (when (seq @errors) (c {:outline "2px solid #dc2626"}))]
               :id    (str (f/get-id opts) "_parent")
               :disabled      disabled
               :on-mouse-down (when-not disabled on-container-mouse-down)
               :on-key-down   (when-not disabled on-container-key-down)}

         (when (or (not searchable) (not opened))
           [:div {:class [value-class
                          (when (seq search-value) (c [:invisible]))
                          (when (empty? value) (c [:text :gray-500]))]}
            (if (and multiselect? searchable)
              (:placeholder props)
              (when value (render-value value)))])
         [:input {:class           [search-class
                                    (class-names (:search-class props))
                                    (c {:font-family "Inter" :font-size "16px" :font-weight "500" :color "black"})
                                    (when-not opened (c [:opacity 0]))]
                  :name (:name props)
                  :id              (f/get-id opts)
                  :disabled        disabled
                  :type            "search"
                  :value           search-value
                  :read-only       (not searchable)
                  :on-change       #(zf/dispatch [::search opts #?(:cljs (.. % -target -value)
                                                                   :clj  (-> % :target :value))])
                  :on-blur         #(zf/dispatch [::close opts])
                  :placeholder     (when (and opened searchable) "Search...")
                  :auto-capitalize "none"
                  :auto-complete   "off"
                  :auto-correct    "off"
                  :spell-check     "false"}]

         (cond
           loading
           [:div {:class icon-class :disabled disabled}
            [:i.far.fa-spinner-third.fa-spin {:class (c {:font-size "1rem"})}]]

           (and clearable (some? value) (not disabled))
           [:div {:class [icon-class (c :cursor-pointer {:background-color "#F8F8F9"})] :disabled disabled :on-mouse-down on-clear-mouse-down}
            [:img {:src "/assets/img/portal-cross.svg" :class (c {:max-width "none" :width "12px" :height "12px"})}]
            #_[:i.fal.fa-times {:class (c {:font-size "1rem"} :font-extrabold)}]]

           :else
           [:div {:class icon-class :disabled disabled}
            [:i.far.fa-chevron-down {:class (c {:font-size "1rem"} :font-extrabold)}]])

         (when opened
           [:div {:class [dropdown-class (class-names (:dropdown-class props))]}
            (if (seq current-options)
              (for [option (remove (or (:exclude-opts-fn opts)
                                       (constantly false))
                                   current-options)
                    :let [active? (= (id-fn option) (id-fn active))
                          selected? (= (id-fn option) (id-fn value)) ;; todo: merge with the line below
                          multi-selected? (some #(= (id-fn %) (id-fn option)) value)]]
                [:div {:id            (str (f/get-id opts) "_" (str (id-fn option)))
                       :class         [(c [:cursor-pointer])
                                       option-class
                                       (when selected? selected-option-class)
                                       (when active? active-option-class)]
                       :key           (str (id-fn option))
                       :ref           (when active? on-active-option-ref)
                       :on-mouse-down (partial on-option-mouse-down option)
                       :on-mouse-move (when-not active? (partial on-option-mouse-move option))}
                 [:div {:class (c :flex :items-center [:p "4px"] [:py "8px"])}
                  (when multiselect?
                    [:button {:class [(c [:ml "6px"] [:mr "12px"] {:border-radius "4px"} :text-center) #_#_check-base-class (when multi-selected? check-checked-class)]}
                     (if multi-selected?
                       [:img {:src "/assets/img/checkbox-marked.svg"}]
                       [:img {:src "/assets/img/checkbox-blank-outline.svg"}])])
                  [:div {:class (c [:pt "2px"])}
                   (render-option option)]]])
              [:div {:class empty-class} "No option"])])]))))

(defmethod value-fn
  ::state-resource
  [_ option]
  {:id           (:value option)
   :resourceType "State"
   :display      (:display option)})


(defmethod value-fn
  :default
  [f option]
  (cond
    (fn? f)
    (f option)

    (keyword? f)
    (get option f)

    (and (vector? f)
         (every? #(or (keyword? %) (integer? %))
                 f))
    (get-in option f)))
