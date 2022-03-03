#!/usr/bin/env bash

git checkout gh-pages

git rm -rf target

git checkout main

mvn clean package

git checkout gh-pages


