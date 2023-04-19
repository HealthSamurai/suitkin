(ns suitkin.components.colors
  (:require (reagent.dom.client)
            [suitkin.core]
            [suitkin.zf.form :as f]
            [re-frame.core :as rf]
            [ui.pages :as pages :refer [reg-page]]
            [clojure.string :as str]
            [reagent.core]
            [stylo.tailwind.color]
            [stylo.core :refer [c]]))


  (def colors
    {:portal-background "white"
     :portal-fg-fill "#FAFAFA"
     :portal-fg-fill-dimmed "#f6f6f6"
     :portal-fg-fill-dimmed2 "#cacaca"
     :portal-primary-red "#EA4A35"
     :portal-primary-red-dimmed "#D2422F"
     :portal-primary-red-tinted "#f4a49a"
     :portal-primary-green "#00C767"
     :portal-icon "rgba(0,0,0,0.2)"
     :portal-icon-dimmed "#a3a3a3"
     :portal-strokes "rgba(0,0,0,0.1)"
     :portal-strokes-tinted "rgba(0,0,0,0.05)"
     :portal-dimmed-font "rgba(0,0,0,0.4)"
     :sdc-background "#FDFDFD"
     :sdc-stroke "#E5E5E5"
     :sdc-stroke-2 "#ADADAD"
     :sdc-text "#353B50"
     :sdc-main-bg "#F1F2F5"
     :sdc-purple-bg-3 "#D3C9D8"
     :sdc-purple-bg-light "#F0E9FE"
     :sdc-disabled-bg "#F4F4F4"
     :sdc-disabled-fg "#C5C5C5"
     :sdc-gray-bg "#F0F0F0"
     :sdc-gray-bg-2 "#F7F7F7"
     :sdc-gray-bg-3 "rgba(242, 242, 242, 1)"
     :sdc-gray-bg-4 "#969EA7"
     :sdc-gray-bg-5 "#F7F7F7"
     :sdc-gray-bg-6 "#EEEEEE"
     :sdc-gray-bg-7 "#F8F9FA"
     :sdc-purple "#B1B1FD"
     :sdc-form-editor-bg "#061E3A"
     :sdc-yellow "#FFF8DF"
     :sdc-inactive-blue-color "rgba(110, 110, 252, 1)"
     :sdc-active-yellow-color "rgba(227, 216, 129, 1)"
     :sdc-active-tab-bg "rgba(75, 75, 220, 0.1)"
     :sdc-border-color "rgba(110, 110, 252, 1)"
     :sdc-scrollbar-bg "rgba(0, 0, 0, 0.2)"
     :sdc-purple-2 "rgba(120, 38, 245, 1)"
     :sdc-gray-fg "rgba(133, 133, 133, 1)"
     :sdc-hover-bg "rgba(242, 233, 254, 1)"
     :sdc-yellow-bg "#FFEAB3"
     :sdc-yellow-fg "#856000"
     :sdc-purple-bg-2 "#F6F3FE"
     :sdc-purple-bg "#EEF0FF"
     :sdc-yellow-light-bg "#FFF2BC"
     :sdc-yellow-light-fg "#A55B1E"
     :sdc-yellow-dark-bg "#FFE4BC"
     :sdc-yellow-dark-fg "#A54F1E"
     :sdc-emerald-bg "#CFF9F9"
     :sdc-emerald-fg "#03726B"
     :sdc-green-light-bg "#CFF9E3"
     :sdc-green-light-fg "#037659"
     :sdc-red-bg "#FDE1E0"
     :sdc-red-fg "#BD111F"
     :transparent "transparent"
     :current     "currentColor"
     :inherit     "inherit"
     :background  "#f4f5f6"
     :gray-title  "#7c828c"
     :title       "#2C3645"
     :warning     "#F7FAD4"
     :error       "#FF3F3F"
     :background-gray "#f4f5f6"
     :light-gray-title "#2c364580"
     :light-bg-gray "#2c36451a"
     :black-text "#2C3645"
     :tgray-50    "#f9fafb"
     :tgray-100   "#F3F4F6"
     :tgray-200   "#E5E7EB"
     :tgray-300   "#D1D5DB"
     :tgray-400   "#9CA3AF"
     :tgray-900   "#111827"
     :black       "#000000"
     :white       "#ffffff"
     :new-gray    "#2c36450d"
     :gray-100    "#f7fafc"
     :gray-200    "#edf2f7"
     :gray-300    "#e2e8f0"
     :gray-400    "#cbd5e0"
     :gray-500    "#a0aec0"
     :gray-600    "#718096"
     :gray-700    "#4a5568"
     :gray-800    "#2d3748"
     :gray-900    "#1a202c"
     :red-100     "#fff5f5"
     :red-200     "#fed7d7"
     :red-300     "#feb2b2"
     :red-400     "#fc8181"
     :red-500     "#f56565"
     :red-600     "#e53e3e"
     :red-700     "#c53030"
     :red-800     "#9b2c2c"
     :red-900     "#742a2a"
     :orange-100  "#fffaf0"
     :orange-200  "#feebc8"
     :orange-300  "#fbd38d"
     :orange-400  "#f6ad55"
     :orange-500  "#ed8936"
     :orange-600  "#dd6b20"
     :orange-700  "#c05621"
     :orange-800  "#9c4221"
     :orange-900  "#7b341e"
     :yellow-100  "#fffff0"
     :yellow-200  "#fefcbf"
     :yellow-300  "#faf089"
     :yellow-400  "#f6e05e"
     :yellow-500  "#ecc94b"
     :yellow-600  "#d69e2e"
     :yellow-700  "#b7791f"
     :yellow-800  "#975a16"
     :yellow-900  "#744210"
     :green-100   "#f0fff4"
     :green-200   "#c6f6d5"
     :green-300   "#9ae6b4"
     :green-400   "#68d391"
     :green-500   "#48bb78"
     :green-600   "#38a169"
     :green-700   "#2f855a"
     :green-800   "#276749"
     :green-900   "#22543d"
     :teal-100    "#e6fffa"
     :teal-200    "#b2f5ea"
     :teal-300    "#81e6d9"
     :teal-400    "#4fd1c5"
     :teal-500    "#38b2ac"
     :teal-600    "#319795"
     :teal-700    "#2c7a7b"
     :teal-800    "#285e61"
     :teal-900    "#234e52"
     :blue-100    "#ebf8ff"
     :blue-200    "#bee3f8"
     :blue-300    "#90cdf4"
     :blue-400    "#63b3ed"
     :blue-500    "#4299e1"
     :blue-600    "#3182ce"
     :blue-700    "#2b6cb0"
     :blue-800    "#2c5282"
     :blue-900    "#2a4365"
     :indigo-100  "#ebf4ff"
     :indigo-200  "#c3dafe"
     :indigo-300  "#a3bffa"
     :indigo-400  "#7f9cf5"
     :indigo-500  "#667eea"
     :indigo-600  "#5a67d8"
     :indigo-700  "#4c51bf"
     :indigo-800  "#434190"
     :indigo-900  "#3c366b"
     :purple-100  "#faf5ff"
     :purple-200  "#e9d8fd"
     :purple-300  "#d6bcfa"
     :purple-400  "#b794f4"
     :purple-500  "#9f7aea"
     :purple-600  "#805ad5"
     :purple-700  "#6b46c1"
     :purple-800  "#553c9a"
     :purple-900  "#44337a"
     :pink-100    "#fff5f7"
     :pink-200    "#fed7e2"
     :pink-300    "#fbb6ce"
     :pink-400    "#f687b3"
     :pink-500    "#ed64a6"
     :pink-600    "#d53f8c"
     :pink-700    "#b83280"
     :pink-800    "#97266d"
     :pink-900    "#702459"
     :cyan-100   "#e6fffb"
     :cyan-200   "#b5f5ec"
     :cyan-300   "#87e8de"
     :cyan-400   "#5cdbd3"
     :cyan-500   "#36cfc9"
     :cyan-600   "#13c2c2"
     :cyan-700   "#08979c"
     :cyan-800   "#006d75"
     :cyan-900   "#00474f"})

(defn main
  []
  [suitkin.core/header "Colors" "stylo/tailwind/color"]

  [(defn show-colors []
     (doseq [[key color] suitkin.components.colors/colors]
       (let [div (.createElement js/document "div")]
         (aset div "style" (str "background-color: " color "; width: 50px; height: 50px; display: inline-block;"))
         (.setAttribute div "title" (name key))
         (.addEventListener div "mouseover"
                            (fn [e]
                              (let [tooltip (.createElement js/document "div")]
                                (aset tooltip "style" "position: absolute; top: 0; left: 0; background-color: #333; color: #fff; padding: 5px;")
                                (.appendChild tooltip (.createTextNode js/document (name key)))
                                (.appendChild js/document.body tooltip))))
         (.addEventListener div "mouseout"
                            (fn [e]
                              (let [tooltips (.querySelectorAll js/document "div[style*='background-color: #333']")]
                                (doseq [tooltip tooltips]
                                  (.removeChild js/document tooltip)))))
         (.appendChild js/document.body div))))]

  )


(rf/reg-event-fx
 ::init
 (fn [{_db :db} [_ _params]]))

(pages/reg-page ::init main)
