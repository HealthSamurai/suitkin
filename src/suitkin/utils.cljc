(ns suitkin.utils
  (:require [clojure.walk]))


(defn gen-uuid []
  (str #?(:clj (java.util.UUID/randomUUID)
          :cljs (random-uuid))))

(defn log [& [args]]
  #?(:cljs (.log js/console (clj->js args))
     :clj (println args)))

(defn deep-merge
  "efficient deep merge"
  [a b]
  (loop [[[k v :as i] & ks] b
         acc a]
    (if (nil? i)
      acc
      (let [av (get a k)]
        (if (= v av)
          (recur ks acc)
          (recur ks
                 (cond
                   (and (map? v) (map? av)) (assoc acc k (deep-merge av v))
                   (and (nil? v) (map? av)) (assoc acc k av)
                   :else (assoc acc k v))))))))


(defn assoc-when-kv
  ([pred m k v]
   (cond-> m (pred [k v]) (assoc k v)))
  ([pred m k v & kvs]
   {:pre [(even? (count kvs))]}
   (reduce (partial apply assoc-when-kv pred)
           (assoc-when-kv pred m k v)
           (partition 2 kvs))))


(defn assoc-when-key
  ([pred m k v]
   (cond-> m (pred k) (assoc k v)))
  ([pred m k v & kvs]
   {:pre [(even? (count kvs))]}
   (reduce (partial apply assoc-when-key pred)
           (assoc-when-key pred m k v)
           (partition 2 kvs))))


(defn assoc-when
  ([pred m k v]
   (cond-> m (pred v) (assoc k v)))
  ([pred m k v & kvs]
   {:pre [(even? (count kvs))]}
   (reduce (partial apply assoc-when pred)
           (assoc-when pred m k v)
           (partition 2 kvs))))


(defn assoc-some [m k v & kvs]
  (apply assoc-when some? m k v kvs))


(defn dissoc-when-kv
  ([pred m k]
   (cond-> m
     (and (contains? m k)
          (pred [k (get m k)]))
     (dissoc k)))
  ([pred m k & ks]
   (reduce (partial dissoc-when-kv pred)
           (dissoc-when-kv pred m k)
           ks)))


(defn dissoc-when-key
  ([pred m k]
   (cond-> m
     (and (contains? m k)
          (pred k))
     (dissoc k)))
  ([pred m k & ks]
   (reduce (partial dissoc-when-key pred)
           (dissoc-when-key pred m k)
           ks)))


(defn dissoc-when
  ([pred m k]
   (cond-> m
     (and (contains? m k)
          (pred (get m k)))
     (dissoc k)))
  ([pred m k & ks]
   (reduce (partial dissoc-when pred)
           (dissoc-when pred m k)
           ks)))


(defn dissoc-nil [m k & ks]
  (apply dissoc-when nil? m k ks))


(defn strip-when-key [pred m]
  (if-let [ks (seq (keys m))]
    (apply dissoc-when-key pred m ks)
    m))


(defn strip-when-kv [pred m]
  (if-let [ks (seq (keys m))]
    (apply dissoc-when-kv pred m ks)
    m))


(defn strip-when [pred m]
  (if-let [ks (seq (keys m))]
    (apply dissoc-when pred m ks)
    m))


(defn strip-nils [m]
  (strip-when nil? m))

(defn remove-empty-vals [m]
  (let [f (fn [x]
            (if (map? x)
              (let [kvs (filter (comp not #(or (nil? %) (= "" %)) second) x)]
                (if (empty? kvs) nil (into {} kvs)))
              x))]
    (clojure.walk/postwalk f m)))

(defn remove-at-index [coll idx]
  (->> coll
       (map-indexed (fn [i item]
                      (if (= idx i)
                        nil
                        item)))
       (remove nil?)
       vec))

(def special-chars
  #"[\u0000-\u001f\u007f-\u009f\u00ad\u061c\u200d\u200b\u200e\u200f\u2028\u2029\u202d\u202e\u2066\u2067\u2069\ufeff\ufff9-\ufffc]")

(defn special-char-placeholder
  [char-string]
  #?(:cljs
     (let [placeholder-element (js/document.createElement "span")
           placeholder-style   (.-style placeholder-element)]
       (set! (.-innerHTML placeholder-element) "⚠️")
       (set! (.-title placeholder-element) (str "Special char '\\u" (.toString (.charCodeAt char-string 0) 16) "'"))
       (set! (.-backgroundColor placeholder-style) "rgb(255 240 185)")
       (set! (.-padding placeholder-style) "1px 2px")
       (set! (.-borderRadius placeholder-style) "4px")
       placeholder-element)
     :clj char-string))

(goog-define GH-PAGES false)
(defn img-src
  [src]
  (if GH-PAGES (str "/suitkin" src) src))
