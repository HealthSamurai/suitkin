clear:
	rm -rf .shadow-cljs node_modules package-lock.json resources/public/js

init:
	npm install

dev:
	rm -rf .shadow-cljs && clj -M:development:shadow-cljs watch components

run-test:
	clj -M:development -m kaocha.runner

release:
	clj -M:shadow-cljs release components

gh-pages:
	make init && cp -r resources/public _site && clojure -M:shadow-cljs release gh-pages
