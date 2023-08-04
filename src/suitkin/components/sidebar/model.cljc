(ns suitkin.components.sidebar.model)

(defn open-menu-item?
  [item]
  (some :active (:items item)))
