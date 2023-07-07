(ns ui.navigation
  (:require
   #?(:cljs [portfolio.data :as data])
   [suitkin.toolkit.navigation]
   [portfolio.reagent-18 :refer-macros [defscene]]
   [stylo.core :refer [c]]))

(data/register-collection! :navigation {:title "Navigation"})

(defscene navigation
  :collection :navigation
  :title "Menu items"
  :params {:title "Items"}
  [params portfolio-opts]
  [:div {:style {:height "500px"}}
   [suitkin.toolkit.navigation/component
    {:title "Abc"
     :children [{:content "Buttons"
                 :icon "code"
                 :id :buttons
                 :children [{:content "Primary"
                             :id :buttons.primary
                             :link "/?id=buttons.primary"}
                            {:content "Secondary"
                             :id :buttons.secondary
                             :link "/?id=buttons.secondary"}
                            {:content "Tertiary"
                             :id :buttons.tertiary
                             :link "/?id=buttons.tertiary"}]}
                {:content "Navigation"
                 :icon "code"
                 :link "/?id=ui.navigation"
                 :id :execute}]}]])
