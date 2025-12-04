#!/bin/bash
rm /build-area/delivery/*
echo "Repository: $REPOSITORY"
echo "Branch:     $BRANCH"

export REPO_DIR="Gebo.ai"

cd /build-area

if [ -d "$REPO_DIR/.git" ]; then
    echo ">> fetching repo update..."
    cd "$REPO_DIR"
    git fetch --all
    git checkout "$BRANCH"
    git pull
else
    echo ">> cloning repo..."
    git clone --branch "$BRANCH" "$REPOSITORY" "$REPO_DIR"
    cd "$REPO_DIR"
fi

echo ">> Run mvn install..."
mvn clean install -P bootables,angular-ui,package-unix-deb -DskipTests
cp /build-area/Gebo.ai/gebo.apps.parent/gebo.ai.app/target/installer/* /build-area/delivery
