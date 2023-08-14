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
    {:logo [:img {:height "24px" :src (u/public-src "/suitkin/img/aidbox-logo.svg")}]
     :submenu
     {:items
      [{:title "Documentation"
        :href  "https://docs.aidbox.app/"
        :img   "/suitkin/img/icon/ic-doc-book-16.svg"
        :target "_blank"}
       {:title "Support"
        :img   "/suitkin/img/icon/ic-headphones-16.svg"}
       {:title "Logout"
        :img   "/suitkin/img/icon/ic-logout-16.svg"}]}
     :menu
     {:items
      [{:title "Notebooks"
        :img   "/suitkin/img/icon/ic-notebook-play-16.svg"}
       {:title "Resources"
        :img   "/suitkin/img/icon/ic-workflow-16.svg"}
       {:title "APIs"
        :img   "/suitkin/img/icon/ic-cloud-cog-16.svg"
        :items [{:title "Operations"    :img "/suitkin/img/icon/ic-operations2-16.svg"}
                {:title "REST Console" :img "/suitkin/img/icon/ic-terminal-16.svg" :active true}
                {:title "GraphQL" :img "/suitkin/img/icon/ic-graphql-16.svg"}]}
       {:title "Auth"
        :img   "/suitkin/img/icon/ic-lock-16.svg"
        :items [{:title "Clients" :img "/suitkin/img/icon/ic-bot-16.svg"}
                {:title "Users" :img "/suitkin/img/icon/ic-users-16.svg"}
                {:title "Access Control" :img "/suitkin/img/icon/ic-shield-shield-check-16.svg"}
                {:title "Sandbox" :img "/suitkin/img/icon/ic-auth-sandbox-16.svg"}]}
       {:title "Profiles"
        :img   "/suitkin/img/icon/ic-worflow-2-16.svg"}
       {:title "Terminology"
        :img   "/suitkin/img/icon/ic-terminology-16.svg"
        :items [{:title "Code Systems" :img "/suitkin/img/icon/ic-books-16.svg"}
                {:title "Concepts" :img "/suitkin/img/icon/ic-whole-word-16.svg"}
                {:title "Value Sets" :img "/suitkin/img/icon/ic-value-set-16.svg"}]}
       {:title "Database"
        :img   "/suitkin/img/icon/ic-database-16.svg"
        :items [{:title "SQL" :img "/suitkin/img/icon/ic-database-code-16.svg"}
                {:title "Tables" :img "/suitkin/img/icon/ic-table-16.svg"}
                {:title "Running Queries" :img "/suitkin/img/icon/ic-database-query-16.svg"}
                {:title "Attrs Stats" :img "/suitkin/img/icon/ic-stats-16.svg"}]}
       {:title "Analytics"
        :img   "/suitkin/img/icon/ic-analytics-16.svg"
        :items [{:title "SQL" :img "/suitkin/img/icon/ic-database-code-16.svg"}]}
       {:title "Workflow Engine"
        :img   "/suitkin/img/icon/ic-workflow2-16.svg"
        :items [{:title "Workflows" :img "/suitkin/img/icon/ic-workflow2-16.svg"}
                {:title "Tasks" :img "/suitkin/img/icon/ic-checkbox-16.svg"}]}
       {:divider true}
       {:title "Aidbox Forms"
        :img   "/suitkin/img/icon/ic-form-16.svg"}
       {:title "Apps"
        :img   "/suitkin/img/icon/ic-apps-16.svg"}
       {:title "HL7 v2"
        :img   "/suitkin/img/icon/ic-integration-16.svg"
        :items [{:title "Messages"
                 :img "/suitkin/img/icon/ic-database-messages-16.svg"}
                {:title "Mappings"
                 :img "/suitkin/img/icon/ic-mappings-16.svg"}]}
       {:space true}
       ]}}]])
