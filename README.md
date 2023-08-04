# Suitkin

Suitkin is a Samurai UI toolkit and component library for building user interfaces in ClojureScript. It is inspires by the desing principles of Heath Samurai Team and provides a collection of reusable UI components that follow a consistent design system.

## Features

Suitkin provides a range of customizable and composable components, such as buttons, forms, modals, tables, and more. It is a work in progress, and the full list of components can be found on the [Demo page](https://healthsamurai.github.io/suitkin/)

## Setup Guide

Follow the steps below to set up the HealthSamurai/Suitkin dependency in your project.

1. Adding Dependency

Include the following dependency:

``` edn
health-samurai/suitkin {:git/url "https://github.com/HealthSamurai/suitkin"
                        :sha "57eb93352f01bdbb6419e75d3c58cbae719ceb72"}
```
2. Downloading Static File

Visit the [releases page](https://github.com/HealthSamurai/suitkin/releases) and download [suitkin.zip](https://github.com/HealthSamurai/suitkin/releases/download/0.0.4/suitkin.zip).

3. Unzipping the Static Files

After downloading the static file, extract the contents of the zip file into your project's public folder.

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

## Development

`make init && make dev`


## Mascote
![](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRvBRJa8s3w--l6YRqgjWXmfJCn1zaM5Z74Gw&usqp=CAU)
