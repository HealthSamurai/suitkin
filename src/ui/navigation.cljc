(ns ui.navigation
  (:require
   #?(:cljs [portfolio.data :as data])
   [suitkin.core]
   [portfolio.reagent-18 :refer-macros [defscene]]
   [suitkin.utils :as u]
   [stylo.core :refer [c]]))

(data/register-collection! :navigation {:title "Navigation"})

(defscene sidebar
  :collection :navigation
  :title "Sidebar"
  []
  [:div {:class (c {:height "100%"})}
   [suitkin.core/sidebar
    {:logo  [:img {:src (u/public-src "/suitkin/img/icon/aidbox-logo-gray.svg")}]
     :brand [:img {:src (u/public-src "/suitkin/img/icon/aidbox-name.svg")}]
     :submenu
     {:items
      [{:title "Documentation"
        :href  "https://docs.aidbox.app/"
        :img   "/suitkin/img/icon/doc-book.svg"
        :new-tab? true}
       {:title "Support"
        :img   "/suitkin/img/icon/headphones.svg"}
       {:title "Logout"
        :img   "/suitkin/img/icon/logout.svg"}]}
     :menu
     {:items
      [{:title "Notebooks"
        :img   "/suitkin/img/icon/notebook.svg"}
       {:title "FHIR Database"
        :img   "/suitkin/img/icon/database.svg"
        :items [{:title "REST Console" :img "/suitkin/img/icon/terminal.svg"}
                {:title "DB Console"   :img "/suitkin/img/icon/database-code.svg"}
                {:title "DB Tables"    :img "/suitkin/img/icon/table.svg"}
                {:title "DB Queries"   :img "/suitkin/img/icon/database-query.svg"}
                {:title "Attrs Stats"  :img "/suitkin/img/icon/stats.svg"}]}
       {:title "APIs"
        :img   "/suitkin/img/icon/cloud-cog.svg"
        :items [{:title "GraphQL" :img "/suitkin/img/icon/graphsql.svg"}
                {:title "Apps"    :img "/suitkin/img/icon/apps.svg"}]}
       {:title "FHIR Profiling"
        :img   "/suitkin/img/icon/profiling.svg"
        :items [{:title "Profiles" :img "/suitkin/img/icon/profile.svg"}
                {:title "Resources"    :img "/suitkin/img/icon/database-resource.svg"}
                {:title "Operations"    :img "/suitkin/img/icon/database-operations.svg"}]}
       {:title "Access control"
        :img   "/suitkin/img/icon/keys.svg"
        :items [{:title "Auth Clients" :img "/suitkin/img/icon/shield-check.svg"}
                {:title "Users"    :img "/suitkin/img/icon/users.svg"}
                {:title "Access Control"    :img "/suitkin/img/icon/database.svg"}
                {:title "Auth Sandbox"    :img "/suitkin/img/icon/auth-sandbox.svg"}]}
       {:title "Terminology"
        :img   "/suitkin/img/icon/terminology.svg"
        :items [{:title "Code Systems" :img "/suitkin/img/icon/tag.svg"}
                {:title "Value Sets"    :img "/suitkin/img/icon/tag.svg"}
                {:title "Concepts"    :img "/suitkin/img/icon/tag.svg"}]}
       {:title "Modules"
        :img   "/suitkin/img/icon/box.svg"
        :items [{:title "Aidbox Forms" :img "/suitkin/img/icon/form.svg"}
                {:title "Workflow Engine"    :img "/suitkin/img/icon/workflow.svg"}
                {:title "Task"    :img "/suitkin/img/icon/repeat.svg"}]}
       {:title "Integrations"
        :img   "/suitkin/img/icon/puzzle.svg"
        :items [{:title "Mappings" :img "/suitkin/img/icon/mappings.svg"}
                {:title "HL7 v2"   :img "/suitkin/img/icon/integration.svg"}]}]}}]])
