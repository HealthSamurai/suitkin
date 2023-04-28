(ns stylo.core
  (:require
   #?(:clj [garden.core])
   #?(:clj [clojure.string :as str])
   #?(:clj [stylo.rule :refer [rule join-rules]])
   #?(:clj [stylo.tailwind.preflight])
   #?(:clj [stylo.tailwind.accessibility])
   #?(:clj [stylo.tailwind.background])
   #?(:clj [stylo.tailwind.border])
   #?(:clj [stylo.tailwind.color])
   #?(:clj [stylo.tailwind.effect])
   #?(:clj [stylo.tailwind.flex])
   #?(:clj [stylo.tailwind.grid])
   #?(:clj [stylo.tailwind.interactivity])
   #?(:clj [stylo.tailwind.layout])
   #?(:clj [stylo.tailwind.sizing])
   #?(:clj [stylo.tailwind.spacing])
   #?(:clj [stylo.tailwind.svg])
   #?(:clj [stylo.tailwind.table])
   #?(:clj [stylo.tailwind.transform])
   #?(:clj [stylo.tailwind.transition])
   #?(:clj [stylo.tailwind.typography])
   #?(:clj [stylo.tailwind.variant])
   #?(:clj [stylo.tailwind.media]))
  #?(:cljs (:require-macros [stylo.core])))

#?(:clj
   (defonce styles (atom {})))

#?(:clj
   (defmacro c [& rules]
     (when rules
       (let [class (if (:name (:ns &env)) (str (str/replace (:name (:ns &env)) #"\." "_") "-" (:line &env) "-" (:column &env))
                       (str "c" (hash rules)))]
         (swap! styles assoc
                (keyword (str "." class))
                (with-meta (join-rules rules) {:location [(:name (:ns &env)) (:line &env) (:column &env)]}))
         (keyword class)))))

#?(:clj
   (defn c' [& rules]
     (when rules
       (let [class (str "c" (hash rules))]
         (swap! styles assoc
                (keyword (str "." class))
                (join-rules rules))
         (keyword class)))))

#?(:clj
   (defmacro c? [& rules]
     (garden.core/css
       (into [(keyword (str ".c" (hash rules)))] (join-rules rules)))))

#?(:clj
   (defn compile-styles [styles]
     (garden.core/css
      (concat
       stylo.tailwind.preflight/preflight
       (->> styles
            (sort-by (comp :location meta val))
            (map (fn [[k v]] (into [k] v))))))))

#?(:clj
   (defmacro mount-style
     []
     `(aset (or (.getElementById js/document "stylo")
                (let [style# (.createElement js/document "style")]
                  (.setAttribute style# "id" "stylo")
                  (.appendChild js/document.head style#)
                  style#))
            "innerHTML" ~(compile-styles @styles))))


(comment
  (reset! styles {})


  @styles

  (c? [:progress-bar [:bg :red-500]] {:font-weight "500"})

  (c? [:progress-bar [:bg :red-500]])
  (c? [:disabled [:hover [:bg :red-500]]])
  (c? [:bg :red-500] [[:.some-arbitrary-class {:bg :blue-400}]])
  (c? [:bg :red-500]
      [:hover [[:.some-arbitrary-class {:bg :blue-400}]]])
  (c? [:pseudo ":nth-child(2)" [:hover [:bg :red-500]]])
  (c? [:pseudo "last-child" [:pseudo "::after" [:bg :red-500]]])

  (c? [[:& {:color "red"}]
       [:&:target {:color "green"}]])
  (c? {:color "red"})
  (c? [:hover [:placeholder :white] [:mb 1]])
  (c? [:p 1])
  (c? [:placeholder :white])
  (c? [:divide-x 2])
  (c? :sr-only))
