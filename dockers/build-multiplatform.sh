#!/usr/bin/env bash
#
# build-multiplatform.sh — Build gebo.ai Docker images for linux/amd64 + linux/arm64
#
# Usage:
#   ./dockers/build-multiplatform.sh                    # build all 3 images
#   ./dockers/build-multiplatform.sh platform           # build only the base platform image
#   ./dockers/build-multiplatform.sh gebo.ai            # build only geboai/gebo.ai
#   ./dockers/build-multiplatform.sh easyinstall        # build only geboai/easyinstall.gebo.ai
#
# Prerequisites:
#   - docker buildx enabled (docker buildx create --use --name multiarch if needed)
#   - To --push you need a Docker Hub account with push access to geboai/*
#
set -euo pipefail

REPO_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
VERSION="1.0.2.1-SNAPSHOT"
PLATFORMS="linux/amd64,linux/arm64"
JAR="$REPO_ROOT/gebo.apps.parent/gebo.ai.app/target/gebo.ai.app-${VERSION}-bootable.jar"
SBOM="$REPO_ROOT/gebo.apps.parent/gebo.ai.app/target/classes/META-INF/sbom/application.cdx.json"

# First positional arg = which image; default = all
TARGET="${1:-all}"

build_platform() {
  echo "=== Building geboai/platform:2.5 (multiplatform: $PLATFORMS) ==="
  docker buildx build \
    --platform "$PLATFORMS" \
    -t geboai/platform:2.5 \
    --push \
    "$REPO_ROOT/dockers/gebo.ai.platform"
}

build_gebo_ai() {
  echo "=== Building geboai/gebo.ai (multiplatform: $PLATFORMS) ==="
  # Copy artifacts into build context
  cp "$JAR" "$REPO_ROOT/dockers/gebo.ai/"
  cp "$SBOM" "$REPO_ROOT/dockers/gebo.ai/"
  docker buildx build \
    --platform "$PLATFORMS" \
    -t geboai/gebo.ai \
    -t geboai/gebo.ai:${VERSION} \
    --push \
    "$REPO_ROOT/dockers/gebo.ai"
}

build_easyinstall() {
  echo "=== Building geboai/easyinstall.gebo.ai (multiplatform: $PLATFORMS) ==="
  # Copy artifacts into build context
  cp "$JAR" "$REPO_ROOT/dockers/easyinstall.gebo.ai/"
  cp "$SBOM" "$REPO_ROOT/dockers/easyinstall.gebo.ai/"
  docker buildx build \
    --platform "$PLATFORMS" \
    -t geboai/easyinstall.gebo.ai \
    -t geboai/easyinstall.gebo.ai:${VERSION} \
    --push \
    "$REPO_ROOT/dockers/easyinstall.gebo.ai"
}

case "$TARGET" in
  platform)    build_platform ;;
  gebo.ai)     build_gebo_ai ;;
  easyinstall) build_easyinstall ;;
  all)
    build_platform
    build_gebo_ai
    build_easyinstall
    ;;
  *)
    echo "Unknown target: $TARGET (use: platform, gebo.ai, easyinstall, or all)"
    exit 1
    ;;
esac

echo "=== Done ==="
