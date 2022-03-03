#!/usr/bin/env bash

git config user.name "$GITHUB_ACTOR"
git config user.email "${GITHUB_ACTOR}@bots.github.com"

git fetch

git checkout gh-pages

git rm -rf docs

git commit -a -m "removed old stuff"

git checkout main

mvn clean package

git checkout gh-pages

mv target/generated-docs docs

git add -f docs

git commit -a -m "new stuff"

git push origin gh-pages

