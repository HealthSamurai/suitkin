(ns suitkin.toolkit.spinner
  (:require [stylo.core :refer [c]]
            [suitkin.zf.util :refer [class-names]]
            [suitkin.zf.form :as f]
            [suitkin.zf]))

(defn spin [props]
  [:i.far.fa-spinner-third.fa-spin props])
