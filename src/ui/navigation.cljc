(ns ui.navigation
  (:require
   #?(:cljs [portfolio.data :as data])
   [suitkin.toolkit.navigation]
   [portfolio.reagent-18 :refer-macros [defscene]]
   [suitkin.utils :as u]
   [stylo.core :refer [c]]))

(data/register-collection! :navigation {:title "Navigation"})

(defscene sidebar
  :collection :navigation
  :title "Sidebar"
  []
  [:div {:class (c {:height "100%"})}
   [suitkin.toolkit.navigation/sidebar
    {:logo  "/assets/img/icons/aidbox-logo-gray.svg"
     :brand [:img {:src (u/img-src "/assets/img/icons/aidbox-name.svg")}]
     :submenu
     {:items
      [{:title "Documentation"
        :href  "https://docs.aidbox.app/"
        :img   "/assets/img/icons/doc-book.svg"
        :new-tab? true}
       {:title "Support"
        :img   "/assets/img/icons/headphones.svg"}
       {:title "Logout"
        :img   "/assets/img/icons/logout.svg"}]}
     :menu
     {:items
      [{:title "Notebooks"
        :img   "/assets/img/icons/icon-notebook.svg"}
       {:title "FHIR Database"
        :img   "/assets/img/icons/database.svg"
        :items [{:title "REST Console" :img "/assets/img/icons/terminal.svg"}
                {:title "DB Console"   :img "/assets/img/icons/database-code.svg"}
                {:title "DB Tables"    :img "/assets/img/icons/table.svg"}
                {:title "DB Queries"   :img "/assets/img/icons/database-query.svg"}
                {:title "Attrs Stats"  :img "/assets/img/icons/stats.svg"}]}
       {:title "APIs"
        :img   "/assets/img/icons/cloud-cog.svg"
        :items [{:title "GraphQL" :img "/assets/img/icons/graphsql.svg"}
                {:title "Apps"    :img "/assets/img/icons/apps.svg"}]}
       {:title "FHIR Profiling"
        :img   "/assets/img/icons/profiling.svg"
        :items [{:title "Profiles" :img "/assets/img/icons/profile.svg"}
                {:title "Resources"    :img "/assets/img/icons/database-resource.svg"}
                {:title "Operations"    :img "/assets/img/icons/database-operations.svg"}]}
       {:title "Access control"
        :img   "/assets/img/icons/keys.svg"
        :items [{:title "Auth Clients" :img "/assets/img/icons/shield-check.svg"}
                {:title "Users"    :img "/assets/img/icons/users.svg"}
                {:title "Access Control"    :img "/assets/img/icons/database.svg"}
                {:title "Auth Sandbox"    :img "/assets/img/icons/auth-sandbox.svg"}]}
       {:title "Terminology"
        :img   "/assets/img/icons/terminology.svg"
        :items [{:title "Code Systems" :img "/assets/img/icons/tag.svg"}
                {:title "Value Sets"    :img "/assets/img/icons/tag.svg"}
                {:title "Concepts"    :img "/assets/img/icons/tag.svg"}]}
       {:title "Modules"
        :img   "/assets/img/icons/box.svg"
        :items [{:title "Aidbox Forms" :img "/assets/img/icons/form.svg"}
                {:title "Workflow Engine"    :img "/assets/img/icons/workflow.svg"}
                {:title "Task"    :img "/assets/img/icons/repeat.svg"}]}
       {:title "Integrations"
        :img   "/assets/img/icons/puzzle.svg"
        :items [{:title "Mappings" :img "/assets/img/icons/mappings.svg"}
                {:title "HL7 v2"   :img "/assets/img/icons/integration.svg"}]}]}}]])
