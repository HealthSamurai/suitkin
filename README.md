# Suitkin

Suitkin is a Samurai UI toolkit and component library for building user interfaces in ClojureScript. It is inspires by the desing principles of Heath Samurai Team and provides a collection of reusable UI components that follow a consistent design system.

## Features

Suitkin provides a range of customizable and composable components, such as buttons, forms, modals, tables, and more. It is a work in progress, and the full list of components can be found on the [Demo page](https://healthsamurai.github.io/suitkin/)


## Development

`make init && make dev`

## Usage

To use Suitkin components in your project, you need to require Suitkin components in your ClojureScript code. For example, to use the Button component:

```clojure
(ns my-app.core
  (:require [suitkin.core :as sk]
            [suitkin.button :as button]))

(defn my-view []
  [:div
   [button/button {:label "Click me!"}]])
```

## Mascote
![](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRvBRJa8s3w--l6YRqgjWXmfJCn1zaM5Z74Gw&usqp=CAU)
