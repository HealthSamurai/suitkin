(ns ui-toolkit.components.button-test
  (:require
   [ui-toolkit.components.button :as sut]
   [clojure.test                 :refer [deftest testing]]
   [matcho.core                  :refer [match]]))

(deftest core-test
  (match true true)
  (match true false)
  )
