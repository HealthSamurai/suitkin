(ns suitkin.components.monaco.view
  #?(:cljs (:require ["@monaco-editor/react" :default Editor])
     :clj  (:require [suitkin.utils])))

(defn set-json-defaults
  [monaco-instance urls]
  #?(:cljs
     (.setDiagnosticsOptions (.-jsonDefaults (.-json (.-languages ^js/Object monaco-instance)))
                             (clj->js {:validate true
                                       :enableSchemaRequest true
                                       :schemas (mapv (fn [url] {:uri url :fileMatch ["*"]})
                                                      urls)}))
     :clj nil))

(defn component
  [properties]
  (fn [properties]
    [:> #?(:cljs Editor
           :clj [:input {:id (:id properties)
                         :value (:value properties)
                         :on-change (fn [event]
                                      ((:onChange properties)
                                       (suitkin.utils/target-value event)))}])
     (-> 
      {:key (:id properties)
       :theme    (:theme properties "suitkin-theme")
       :language "json"
       :options  (merge {:minimap              {:enabled false}
                         :fontSize             "14px"
                         :fontStyle            "normal"
                         :lineHeight           "1.5"
                         :letterSpacing        "0.2em"
                         :fontWeight           "300"
                         :fontFamily           "JetBrains Mono"
                         :overviewRulerLanes   0
                         ;; :lineNumbers          "off"
                         :glyphMargin          false
                         :renderLineHighlight  "none"
                         :folding              true
                         :renderIndentGuides   false
                         :lineDecorationsWidth 15
                         :tabSize              2
                         ;; :lineNumbersMinChars  0
                         }
                        (:options properties))
       :beforeMount
       (fn [insance]
         (when (:schemas properties)
           (set-json-defaults insance (:schemas properties)))
         #?(:cljs 
            (.defineTheme (.-editor ^js/Object insance)
                          "suitkin-theme"
                          (clj->js {:base    "vs"
                                    :inherit true
                                    :rules   [{:token "string.key.json" :foreground "#EA4A35"}
                                              {:token "string.value.json" :foreground "#405CBF"}
                                              {:token "string" :foreground "#405CBF"}
                                              {:token "number" :foreground "#00A984"}]
                                    :colors  {"editor.background" "#F8FAFC"
                                              "editorLineNumber.foreground" "#616471"
                                              "editorLineNumber.activeForeground" "#616471"
                                              "scrollbar.shadow"  "#ffffff00"
                                              "widget.shadow"     "#ffffff00"}}))
            :clj nil)
         insance)}
      (merge (dissoc properties :options)))]))
