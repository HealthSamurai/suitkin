(ns suitkin.toolkit.dropdown-button
  (:require [suitkin.toolkit.button]
            [suitkin.utils :as utils]
            [stylo.core :refer [c]]
            [suitkin.zf.form]
            [suitkin.zf :as zf])
  )


(zf/defx toggle-dropdown [{db :db} id]
  {:db (update-in db [::db :state id :opened?] not)})

(zf/defs opened? [db [_ id]]
  (println "opened sub: " (suitkin.zf.form/state db {:zf/root [::db] :zf/path [id :opened?]}))
  (suitkin.zf.form/state db {:zf/root [::db] :zf/path [id :opened?]}))

(zf/defs schema [db [_ id]]
  (suitkin.zf.form/schema db {:zf/root [::db] :zf/path [id]}))

(defn dropdown-button [{opts :opts :as props} & children]
  (let [id (utils/gen-uuid)
        opened? (zf/subscribe [::opened? id])
        schema (zf/subscribe [:zf/schema opts])]
    (fn []
      (println "opened? " @schema)
      [:div {:class (c :relative :inline-block :self-center)}
       (into [suitkin.toolkit.button/zf-button {:type "sdc"

                                        :on-click [::toggle-dropdown id]}]
             children)
       (when @opened?
         [:div {:class [(c :absolute :leading-relaxed [:z 100]
                           [:mt 2]
                           [:top "100%"] [:left "-1px"] [:w-min 40]
                           [:bg :white] [:rounded 16] :overflow-hidden
                           {:box-shadow "0px -4px 105px rgba(0, 0, 0, 0.05), 0px -0.677245px 23.4531px rgba(0, 0, 0, 0.0298054), 0px -0.154328px 6.98261px rgba(0, 0, 0, 0.0201946);"})]}
          [:div {:class (c :flex :flex-col :divide-y )}
           (for [option (:options @schema)]
             ^{:key (:title option)}
             [:div {:class (c [:py 5] [:px 6] :cursor-pointer [:hover {:background-color "rgba(242, 233, 254, 1)"}]
                              :whitespace-no-wrap [:text :tgray-900] :tracking-wide)
                    :on-click (fn []
                                (when (:on-click option)
                                  (zf/dispatch [::toggle-dropdown id])
                                  (zf/dispatch (:on-click option))))} (:title option)]
             )]
          ]
         )
       ])))
