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

gp:
	make clear && make init && clj -M:shadow-cljs release gitpages && cp -r resources/public/js . && cp -r resources/public/css . && cp -r resources/public/images . && cp -r resources/public/assets .

