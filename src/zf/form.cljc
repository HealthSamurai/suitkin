(ns zf.form
  (:require [zf :as zf]
            [re-frame.core :as rf]
            [re-frame.registrar]
            [clojure.string :as str]
            [zf.util]))

(defmulti init-field (fn [k opts params] k))

(defmethod init-field :default
  [k opts params]
  {:zen/error {:message (str ::not-implemented k params)}})


(defn find-by-pattern [pattern xs]
  (or (first (keep-indexed (fn [idx x]
                             (when (= (select-keys x (keys pattern)) pattern)
                               idx)) xs))
      (count xs)))

(defn assoc-in*
  [m [k & ks] v]
  (letfn [(assoc* [m k v]
             (cond (and (int? k)
                        (or (nil? m) (vector? m))
                        (>= k (count m)))
                   (assoc (into (or m []) (repeat (- k (count m)) nil)) k v)
                   (and (map? k) (or (vector? m) (nil? m)))
                   (assoc (or m []) (find-by-pattern k m) (merge v k))
                   :else
                   (assoc m k v)))]
    (if ks
      (assoc* m k (assoc-in* (get m k) ks v))
      (assoc* m k v))))

(defn get-in*
  [m [k & ks]]
  (letfn [(get* [m k]
            (if (and (map? k) (or (vector? m) (nil? m)))
              (get m (find-by-pattern k m))
              (get m k)))]
    (if ks
      (get-in* (get* m k) ks)
      (get* m k))))

(defn update-in*
   [m ks f & args]
   (assoc-in* m ks (apply f (get-in* m ks) args)))


(defn get-id** [opts & subpath]
  (->> (concat (:zf/root opts) (:zf/path opts) subpath)
       (map (fn [kw] (if (number? kw) (keyword (str kw)) kw)))
       (mapv (fn [kw] (cond->> (name kw)
                        (namespace kw) (str (namespace kw) \/))))
       (str/join \.)))

(def get-id (memoize get-id**))


(defn with-path
  [opts & args]
  (apply update opts :zf/path into args))


(defn- state-path
  ([{:zf/keys [root path]}]
   (concat root [:state] path))
  ([{:zf/keys [root path]} inner-path]
   (concat root [:state] path inner-path)))

(defn- schema-path
  ([{:zf/keys [root path]}]
   (concat root [:schema] path))
  ([{:zf/keys [root path]} inner-path]
   (concat root [:schema] path inner-path)))

(defn- value-path [{:zf/keys [root path]}]
  (concat root [:value] path))

(defn- data-path [{:zf/keys [root path]}]
  (concat root [:data] path [:data]))

(defn- error-path [{:zf/keys [root path]} & [k]]
  (let [path (or path [])]
    (concat root [:error] (if k (conj path k) path))))

(defn set-schema
  [db opts schema & [inner-path]]
  (assoc-in* db (schema-path opts inner-path) schema))

(rf/reg-event-db
 :zf/set-schema
 (fn [db [_ opts schema]]
   (set-schema db opts schema)))


(defn update-value
  [db opts f & args]
  (let [value (get-in* db (value-path opts))]
    (assoc-in* db (value-path opts)
               (apply f value args))))

(defn set-error
  [db opts k error]
  (assoc-in* db (error-path opts k) error))

(defn clear-error
  [db opts k]
  (-> (update-in* db (error-path opts) (fn [errs] (dissoc errs k)))
      (update-in* (conj (:zf/root opts) :error) zf.util/sanitize-obj)
      (update-in* (:zf/root opts) (fn [form] (if (seq (:error form))
                                               form
                                               (dissoc form :error))))))

;; Sometimes we don't need to sanitize root error object,
;; because we need to keep nils in vectors with errors
;; to preserve index of error
(defn clear-error-without-sanitize
  [db opts k]
  (-> (update-in* db (error-path opts) (fn [errs] (dissoc errs k)))
      (update-in* (:zf/root opts) (fn [form] (if (seq (:error form))
                                               form
                                               (dissoc form :error))))))


(rf/reg-event-fx
 :zf/set-error
 (fn [{db :db} [_ opts k error]]
   {:db (set-error db opts k error)}))

(rf/reg-event-fx
 :zf/clear-error
 (fn [{db :db} [_ opts k]]
   {:db (clear-error db opts k)}))

(rf/reg-event-fx
 :zf/clear-error-without-sanitize
 (fn [{db :db} [_ opts k]]
   {:db (clear-error-without-sanitize db opts k)}))

(defn merge-state
  [db {root :zf/root path :zf/path} value]
  (update-in* db (concat root [:state] path) merge value))

(rf/reg-event-db
  :zf/merge-state
  (fn [db [_ opts value]]
    (merge-state db opts value)))

(defn set-state
  ([db opts value]
   (assoc-in* db (state-path opts) value))
  ([db opts inner-path value]
   (assoc-in* db (state-path opts inner-path) value)))

(rf/reg-event-db
  :zf/set-state
  (fn [db [_ opts inner-path value]]
    (set-state db opts inner-path value)))


(defn schema
  [db opts]
  (get-in* db (schema-path opts)))

(rf/reg-sub
  :zf/schema
  (fn [db [_ opts]]
    (schema db opts)))

(defn value
  [db opts]
  (get-in* db (value-path opts)))

(rf/reg-sub
  :zf/value
  (fn [db [_ opts]]
    (value db opts)))

(defn data
  [db opts]
  (get-in* db (data-path opts)))

(rf/reg-sub
  :zf/data
  (fn [db [_ opts]]
    (data db opts)))

(defn error
  [db opts]
  (get-in* db (error-path opts)))

(rf/reg-sub
  :zf/error
  (fn [db [_ opts]]
    (error db opts)))

(defn clear-empty [m]
  (if (map? m)
    (reduce (fn [res [k v]]
              (if-let [v' (clear-empty v)]
                (assoc res k v')
                res)) nil m)
    m))

(rf/reg-sub :zf/form-errors
 (fn [db [_ opts]]
   (clear-empty
    (:error (get-in db (:zf/root opts))))))

(defn state
  ([db opts]
   (get-in* db (state-path opts)))
  ([db opts inner-path]
   (get-in* db (state-path opts inner-path))))

(defn dirty? [db opts]
  (get (state db opts) :dirty? false))

(rf/reg-sub
  :zf/state
  (fn [db [_ opts]]
    (state db opts)))

(zf/defe :dom/select
  [{:keys [id start end]}]
  #?(:cljs
     (when-let [el (js/document.getElementById id)]
       (set! (.-selectionStart el) start)
       (set! (.-selectionEnd el) end))))


(zf/defe :dom/focus
  [id]
  #?(:cljs
     (when-let [el (js/document.getElementById id)]
       (.focus el))))


(zf/defe :dom/blur
  [id]
  #?(:cljs
     (if id
       (some-> (js/document.getElementById id) .blur)
       (some-> js/document.activeElement .blur))))

(zf/defe :dom/scroll-to-bottom
  [id]
  #?(:cljs
     (when-let [element (js/document.getElementById id)]
       (set! (.-scrollTop element) (.-scrollHeight element)))))


(zf/defe :dom/scroll-into
  [id]
  #?(:cljs
     (when-let [el (js/document.getElementById id)]
       (.scrollIntoView el false))))


(rf/reg-event-fx
 :zf/focus
 (fn [{db :db} [_ opts]]
   (let [event (:on-focus (get-in* db (:zf/root opts)))]
     {:db (-> db
              (assoc-in* (state-path opts [:zf/pre-commit-value]) (value db opts))
              (assoc-in* (state-path opts [:zf/focused]) true))
      :dispatch-n [(when event
                     (conj (if (vector? event) event [event]) opts))]})))

(rf/reg-event-fx
  :zf/blur
  (fn [{db :db} [_ opts]]
    (let [event (:on-blur (get-in* db (:zf/root opts)))]
      {:db (-> db
               (assoc-in* (state-path opts [:dirty?]) true)
               (assoc-in* (state-path opts [:zf/focused]) false)
               )
       :dispatch-n [(when event
                      (conj (if (vector? event) event [event]) opts))]})))

(rf/reg-event-fx
  :zf/commit-value
  (fn [{db :db} [_ opts _]]
    (let [form-opts (dissoc opts :zf/path)
          event (:on-commit (get-in* db (:zf/root form-opts)))]
      {:db (assoc-in* db (state-path form-opts [:zf/committed-value]) (value db form-opts))
       :dispatch-n [(when (and event (not= (value db opts)
                                           (do (assert (contains? (state db opts) :zf/pre-commit-value))
                                               (state db opts [:zf/pre-commit-value]))))
                      (conj (if (vector? event) event [event])
                            opts
                            (value db opts)
                            (state db opts [:zf/pre-commit-value])))]})))

(defn form-init-actions [acc opts schema]
  (->> schema
       (reduce (fn [acc [k {init :init}]]
                 (if init
                   (do
                     (assert (:event init))
                     (into acc [[(:event init) (assoc init :opts (update opts :zf/path conj k))]]))
                   acc))
               acc)))

(zf/defx form-init
  [{db :db} {root :zf/root :as form}]
  (let [actions (form-init-actions [] {:zf/root root :zf/path []} (:schema form))]
    (cond-> {:db (assoc-in db root form)}
      (seq actions)
      (assoc :dispatch-n actions))))

(defmulti validate (fn [tp cfg value] tp))

(defmethod validate :zf/required
  [_ cfg v]
  (when (or (nil? v) (empty? v))
    (or (:msg cfg) (str "Field is required"))))

(defmethod validate :zf/min-length
  [_ {minv :value msg :msg} v]
  (when (and (string? v) (< (count v) minv))
    (or msg (str "Should be longer then " minv))))

(defmethod validate :zf/max-length
  [_ {maxv :value msg :msg} v]
  (when (and (string? v) (>= (count v) maxv))
    (or msg (str "Should be shorter then " maxv))))

(defmethod validate :zf/regex
  [_ {regx :value msg :msg} v]
  (when (and (string? v) (not (re-matches regx v)))
    (or msg (str "Should match " regx))))


(defmethod validate :default
  [k cfg v]
  (str "No validation for " k (pr-str cfg)))

(defn field-errors [errs sch val]
  (reduce (fn [errs [vk vsch]]
            (if-let [err (validate vk vsch val)]
              (assoc errs vk err)
              (dissoc errs vk)))
          errs sch))

(defn validate-value [db opts sch val]
  (let [epth (error-path opts)
        errs (get-in* db epth)
        errs' (field-errors errs sch val)]
    (if (empty? errs')
      (assoc-in* db epth nil)
      (assoc-in* db epth errs'))))

(defn *set-value
  [db opts val]
  (let [val (if (and val (string? val) (str/blank? val)) nil val)
        {val-sch :validate} (schema db opts)]
    (cond-> (assoc-in* db (value-path opts) val)
      val-sch (validate-value opts val-sch val))))

(rf/reg-event-fx
 :zf.form/set-value
 (fn [{db :db} [_ {v :value :as opts}]]
   (let [db' (-> db
                 (assoc-in* (state-path opts [:dirty?]) true)
                 (*set-value opts v)
                 (cond-> (:auto-commit opts true)
                   (set-state opts [:zf/pre-commit-value] (value db opts))))]
     {:db db'
      :dispatch-n [(when-let [event (:on-change (schema db' opts))]
                     (into (if (vector? event) event [event]) [opts v]))
                   (when-let [event (:on-change (get-in* db' (:zf/root opts)))]
                     ;; TODO why we do (value 'db)
                     (into (if (vector? event) event [event]) [(value db' (dissoc opts :zf/path)) opts v]))
                   (when (:auto-commit opts true)
                     [:zf/commit-value opts opts])]})))

(defn calculate-errors [errs schema val]
  (let [errs (->> schema
                  (reduce (fn [errs [k {sch :validate}]]
                            (let [val (get val k)
                                  verrs (field-errors (get errs k) sch val)]
                              (if (empty? verrs)
                                (dissoc errs k)
                                (assoc errs k verrs))
                              )) errs))]
    (if (empty? errs) nil errs)))

(zf/defx submit
  [{db :db} {root :zf/root :as opts}]
  (let [db (set-state db (assoc opts :zf/path [::form-submitting?]) true)
        opts (assoc opts :zf/path [])
        schema (schema db opts)
        val (value db opts)
        errs (error db opts)
        ferrs (calculate-errors errs schema val)]
    (if (empty? ferrs)
      (if-let [subm-ev (:zf/submit schema)]
        {:db (assoc-in db (error-path opts) nil)
         :dispatch [(:event subm-ev) (assoc subm-ev :value val)]}
        {:db (assoc-in db (error-path opts) nil)})
      (if (not (= errs ferrs))
        {:db (assoc-in db (error-path opts) ferrs)}
        {:db db}))))
