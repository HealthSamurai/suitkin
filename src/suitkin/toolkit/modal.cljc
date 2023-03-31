(ns suitkin.toolkit.modal
  (:require #?(:cljs [reagent.core :as r])
            [stylo.core :refer [c]]
            [suitkin.toolkit.button]
            [zf.form]
            [zf :as zf]))

(def opened-modal (c :overflow-hidden))

(zf/defx show-modal
  [{db :db} content-id]
  #?(:cljs (.add (.. js/document -body -classList) (name opened-modal)))
  {:db (-> db
           (zf.form/set-state {:zf/root [::db] :zf/path []} nil)
           (zf.form/set-state {:zf/root [::db] :zf/path [content-id :shown]} true))})


(zf/defx hide-modal [{db :db}]
  #?(:cljs (.remove (.. js/document -body -classList) (name opened-modal)))
  {:db (zf.form/set-state db {:zf/root [::db] :zf/path []} nil)})


(zf/defs modal-state
  [db [_ content-id]]
  (zf.form/state db {:zf/root [::db] :zf/path [content-id :shown]})
  )

(def custom-scrollbar (c [:pseudo "::-webkit-scrollbar" {:background "none"}
                          [:w "8px"]
                          :overflow-hidden]
                         [:pseudo "::-webkit-scrollbar-thumb" {:background "rgba(0, 0, 0, 0.1)"}
                          [:rounded :full]]
                         [:pseudo "::-webkit-scrollbar-track" {:background "none"}
                          [:w "10px"]]))

(defn modal [content-id title annotation content]
  (let [shown (zf/subscribe [::modal-state content-id])]
    (fn []
      (when @shown
        [:div {:class (c :fixed [:top "0"] [:left "0"] [:right "0"] [:bottom "0"]
                         {:background "rgba(0,0,0,0.3)"
                          :backdrop-filter "blur(11.5px)"}
                         [:z 2] :overflow-hidden
                         :flex :flex-col :justify-center :items-center)
               :on-click (fn [e]
                           (.preventDefault e)
                           (.stopPropagation e)
                           (zf/dispatch [::hide-modal]))}
         [:div {:class [(c [:h-max "90%"]
                           [:bg :white] [:rounded 16]
                           :overflow-hidden
                           )
                        custom-scrollbar]
                :on-click (fn [e] (.stopPropagation e))}
          [:div {:class (c :flex :justify-between :items-center [:px 6] [:pt 4] [:py 4] :border-b)}
           [:span {:class (c :text-xl [[:text :tgray-900]:text :tgray-900] :font-semibold :tracking-wide)} title]

           [:div {:class (c [:rounded :full] [:py 2] [:px 3] [:text :sdc-gray-fg]
                            :leading-none
                            [:border 2 :sdc-gray-fg] [:bg :white]
                            :cursor-pointer
                            :transition-all [:duration 150] :ease-in-out
                            [:hover [:border 2 :sdc-purple-2] [:text :sdc-purple-2]]
                            [:focus [:border 2 :sdc-purple-2] [:text :sdc-purple-2]])
                  :on-click #(zf/dispatch [::hide-modal])}
            [:i.fas.fa-close {:class (c :text-xl)}]]]
          #_[toolkit.button/button {:type "text"
                                    :class (c :absolute [:top 2] [:right 1])
                                    :on-click #(zf/dispatch [::hide-modal])} [:i.fas.fa-xmark]]
          [:div {:class [(c :overflow-y-scroll
                            [:h "calc(100% - 72.5px)"])
                         custom-scrollbar]}
           content]]
         ])
      ))
  )
