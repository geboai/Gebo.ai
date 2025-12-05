#!/bin/bash
rm /build-area/delivery/*
echo "Repository: $REPOSITORY"
echo "Branch:     $BRANCH"

export REPO_DIR="Gebo.ai"
cd /build-area
rm -rf "$REPO_DIR"
echo ">> cloning repo..."
git clone --branch "$BRANCH" "$REPOSITORY" "$REPO_DIR"
cd "$REPO_DIR"


echo ">> Run mvn install..."
mvn clean install -P bootables,angular-ui,package-unix-rpm -DskipTests
cp /build-area/Gebo.ai/gebo.apps.parent/gebo.ai.app/target/installer/* /build-area/delivery
