(ns suitkin.components.monaco.view
  #?(:cljs (:require ["@monaco-editor/react" :default Editor])
     :clj  (:require [suitkin.utils])))

(defn set-json-defaults
  [monaco-instance urls & [default-path]]
  #?(:cljs
     (when urls
       (.setDiagnosticsOptions (.-jsonDefaults (.-json (.-languages ^js/Object monaco-instance)))
                               (clj->js {:validate true
                                         :allowComments true
                                         :enableSchemaRequest true
                                         :schemas (mapv (fn [url] {:uri url :fileMatch [(or default-path "*")]})
                                                        urls)})))
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
       :defaultPath (:defaultPath properties)
       :defaultValue (:defaultValue properties)
       :language "json"
       :automaticLayout true
       :options  (merge {:minimap              {:enabled false}
                         "bracketPairColorization.enabled" false
                         :fontSize             "14px"
                         :automaticLayout true
                         :fontStyle            "normal"
                         :scrollbar {:verticalScrollbarSize 6
                                     :horizontalScrollbarSize 6}
                         :padding {:top 16}
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
                         :lineDecorationsWidth 0
                         :tabSize              2
                         ;; :lineNumbersMinChars  0
                         }
                        (:options properties))
       :onMount
       (fn [editor instance]
         (when-let [on-mount-fn (:on-mount-fn properties)]
           (on-mount-fn editor instance)))
       :beforeMount
       (fn [instance]
         (when-let [before-mount-fn (:before-mount-fn properties)]
           (before-mount-fn instance))
         (when (:schemas properties)
           (set-json-defaults instance (:schemas properties) (:defaultPath properties)))
         #?(:cljs 
            (.defineTheme (.-editor ^js/Object instance)
                          "suitkin-theme"
                          (clj->js {:base    "vs"
                                    :inherit true
                                    :rules   [{:token "string.key.json" :foreground "#EA4A35"}
                                              {:token "string.value.json" :foreground "#405CBF"}
                                              {:token "string" :foreground "#405CBF"}
                                              {:token "number" :foreground "#00A984"}
                                              {:token "key.json" :foreground "#00A984"}]
                                    :colors  {"editor.background" "#F9F9F9"
                                              "editorLineNumber.foreground" "#616471"
                                              "editorLineNumber.activeForeground" "#616471"
                                              "scrollbar.shadow"  "#ffffff00"
                                              "scrollbarSlider.background" "#83868E"
                                              "scrollbarSlider.activeBackground" "#212636"
                                              "scrollbarSlider.hoverBackground" "#212636"
                                              "widget.shadow"     "#ffffff00"}}))
            :clj nil)
         instance)}
      (merge (dissoc properties :options)))]))
