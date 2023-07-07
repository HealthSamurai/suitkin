(ns ui.navigation
  (:require
   [suitkin.toolkit.navigation]
   [portfolio.reagent-18 :refer-macros [defscene]]
   [stylo.core :refer [c]]))

(defscene navigation
  :params {:title "Navigation"}
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
