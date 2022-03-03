#!/usr/bin/env bash

set -eu

GITHUB_WORKSPACE="."
GITHUB_ACTOR="marc"
GITHUB_TOKEN="sdlkfj"
GITHUB_REPOSITORY="sdlkfj"

repo_uri="https://x-access-token:${GITHUB_TOKEN}@github.com/${GITHUB_REPOSITORY}.git"
remote_name="origin"
main_branch="main"
target_branch="gh-pages"
build_dir="dist"


cd "$GITHUB_WORKSPACE"

git config user.name "$GITHUB_ACTOR"
git config user.email "${GITHUB_ACTOR}@bots.github.com"

git checkout "$target_branch"
git rebase "${remote_name}/${main_branch}"

mvn clean package

git add "$build_dir"

git commit -m "updated GitHub Pages"
if [ $? -ne 0 ]; then
    echo "nothing to commit"
    exit 0
fi

git remote set-url "$remote_name" "$repo_uri" # includes access token
#git push --force-with-lease "$remote_name" "$target_branch"