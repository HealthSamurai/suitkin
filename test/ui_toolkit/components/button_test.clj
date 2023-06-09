(ns ui-toolkit.components.button-test
  (:require
   [suitkin.toolkit.button :as sut]
   [clojure.test           :refer [deftest testing]]
   [matcho.core            :refer [match]]
   [retest.core            :refer [click datafy]]))

(deftest zf-button-test
  (testing "click"
    (def counter (atom 0))
    (click [sut/zf-button {:id ::id :on-click (fn [_] (swap! counter inc))} "text"] ::id)
    (match @counter 1))

  (testing "loading"
    (def counter (atom 0))
    (click [sut/zf-button {:id ::id :loading true :on-click (fn [_] (swap! counter inc))} "text"] ::id)
    (match @counter 0))

  (testing "disabled"
    (def counter (atom 0))
    (click [sut/zf-button {:id ::id :disabled true :on-click (fn [_] (swap! counter inc))} "text"] ::id)
    (match @counter 0))

  (testing "label"
    (match
     (datafy [sut/zf-button {} [:span {:itemProp ::label} "text"]])
     {::label "text"})))
