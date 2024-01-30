(ns ui.buttons
  (:require
   [suitkin.utils :as u]
   [suitkin.core]
   [ui.properties]
   [reagent.ratom :as ra]
   [portfolio.data :as data]
   [portfolio.reagent-18 :refer-macros [defscene]]))

(data/register-collection! :buttons {:title "Buttons"})

(defscene button-1
  :collection :buttons
  :title "Primary"
  [suitkin.core/button {:s/use "primary"} "Execute"])

(defscene button-1-1
  :collection :buttons
  :title "Primary icon"
  [suitkin.core/button {:s/use "primary" :s/icon "/suitkin/img/icon/ic-plus-16.svg"} "Execute"])

(defscene button-2
  :collection :buttons
  :title "Primary narrow"
  [suitkin.core/button {:s/use "primary" :s/size "narrow"} "Execute"])

(defscene button-2-1
  :collection :buttons
  :title "Primary narrow icon"
  [suitkin.core/button {:s/use "primary" :s/size "narrow" :s/icon "/suitkin/img/icon/ic-plus-16.svg"} "Execute"])

(defscene button-3
  :collection :buttons
  :title "Primary dangerous"
  [suitkin.core/button {:s/use "primary" :s/theme "dangerous"} "Execute"])

(defscene button-3-1
  :collection :buttons
  :title "Primary dangerous icon"
  [suitkin.core/button {:s/use "primary" :s/theme "dangerous" :s/icon "/suitkin/img/icon/ic-plus-16.svg"} "Execute"])

(defscene button-4
  :collection :buttons
  :title "Primary dangerous narrow"
  [suitkin.core/button {:s/use "primary" :s/theme "dangerous" :s/size "narrow"} "Execute"])

(defscene button-4-1
  :collection :buttons
  :title "Primary dangerous narrow icon"
  [suitkin.core/button {:s/use "primary" :s/theme "dangerous" :s/size "narrow" :s/icon "/suitkin/img/icon/ic-plus-16.svg"} "Execute"])

(defscene button-5
  :collection :buttons
  :title "Secondary"
  [suitkin.core/button {:s/use "secondary"} "Execute"])

(defscene button-5-1
  :collection :buttons
  :title "Secondary icon"
  [suitkin.core/button {:s/use "secondary" :s/icon "/suitkin/img/icon/ic-plus-16.svg"} "Execute"])

(defscene button-6
  :collection :buttons
  :title "Secondary narrow"
  [suitkin.core/button {:s/use "secondary" :s/size "narrow"} "Execute"])

(defscene button-6-1
  :collection :buttons
  :title "Secondary narrow"
  [suitkin.core/button {:s/use "secondary" :s/size "narrow" :s/icon "/suitkin/img/icon/ic-plus-16.svg"} "Execute"])

(defscene button-7
  :collection :buttons
  :title "Secondary dangerous"
  [suitkin.core/button {:s/use "secondary" :s/theme "dangerous"} "Execute"])

(defscene button-7-1
  :collection :buttons
  :title "Secondary dangerous icon"
  [suitkin.core/button {:s/use "secondary" :s/theme "dangerous" :s/icon "/suitkin/img/icon/ic-plus-16.svg"} "Execute"])

(defscene button-8
  :collection :buttons
  :title "Secondary dangerous narrow"
  [suitkin.core/button {:s/use "secondary" :s/theme "dangerous" :s/size "narrow"} "Execute"])

(defscene button-8-1
  :collection :buttons
  :title "Secondary dangerous narrow icon"
  [suitkin.core/button {:s/use "secondary" :s/theme "dangerous" :s/size "narrow" :s/icon "/suitkin/img/icon/ic-plus-16.svg"} "Execute"])

(defscene button-9
  :collection :buttons
  :title "Tertiary"
  [suitkin.core/button {:s/use "tertiary"} "Execute"])

(defscene button-9-1
  :collection :buttons
  :title "Tertiary icon"
  [suitkin.core/button {:s/use "tertiary" :s/icon "/suitkin/img/icon/ic-plus-16.svg"} "Execute"])

(defscene button-10
  :collection :buttons
  :title "Tertiary narrow"
  [suitkin.core/button {:s/use "tertiary" :s/size "narrow"} "Execute"])

(defscene button-10-1
  :collection :buttons
  :title "Tertiary narrow icon"
  [suitkin.core/button {:s/use "tertiary" :s/size "narrow" :s/icon "/suitkin/img/icon/ic-plus-16.svg"} "Execute"])

(defscene button-11
  :collection :buttons
  :title "Tertiary dangerous"
  [suitkin.core/button {:s/use "tertiary" :s/theme "dangerous"} "Execute"])

(defscene button-11-1
  :collection :buttons
  :title "Tertiary dangerous icon"
  [suitkin.core/button {:s/use "tertiary" :s/theme "dangerous" :s/icon "/suitkin/img/icon/ic-plus-16.svg"} "Execute"])

(defscene button-12
  :collection :buttons
  :title "Tertiary dangerous narrow"
  [suitkin.core/button {:s/use "tertiary" :s/theme "dangerous" :s/size "narrow"} "Execute"])

(defscene button-12-1
  :collection :buttons
  :title "Tertiary dangerous narrow icon"
  [suitkin.core/button {:s/use "tertiary" :s/theme "dangerous" :s/size "narrow" :s/icon "/suitkin/img/icon/ic-plus-16.svg"} "Execute"])
