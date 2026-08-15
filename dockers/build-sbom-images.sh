#!/usr/bin/env bash
#
# build-sbom-images.sh — Build gebo.ai Docker images locally
#
# Builds all 3 images (or a single one) and loads them into the local Docker
# daemon (single-platform linux/amd64). The Maven SBOM is COPY'd into each
# image. The BuildKit SBOM attestation (--sbom) is NOT used here because the
# local Docker exporter cannot load manifest-list attestations — use
# build-multiplatform.sh + publish-multiplatform.sh for that (multi-platform,
# --sbom=true).
#
# Usage:
#   ./dockers/build-sbom-images.sh                    # build all 3 images
#   ./dockers/build-sbom-images.sh platform           # build only geboai/platform
#   ./dockers/build-sbom-images.sh gebo.ai            # build only geboai/gebo.ai
#   ./dockers/build-sbom-images.sh easyinstall        # build only geboai/easyinstall.gebo.ai
#
# Prerequisites:
#   - The monolith bootable jar built:
#       mvn -f gebo.apps.parent/gebo.ai.app/pom.xml -P bootables package -DskipTests
#
set -euo pipefail

REPO_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
# Read the project's own <version> from the root pom.xml header, stopping at
# <parent> so we don't accidentally match spring-boot-starter-parent's version
# instead. Keeps this script aligned with whatever the actual build produces —
# this was hardcoded, and went stale as the project version moved on.
VERSION="$(sed -n '/<parent>/q;p' "$REPO_ROOT/pom.xml" | grep -oP '(?<=<version>)[^<]+(?=</version>)' | head -1)"
if [ -z "$VERSION" ]; then
  echo "ERROR: Could not read project version from $REPO_ROOT/pom.xml"
  exit 1
fi
JAR="$REPO_ROOT/gebo.apps.parent/gebo.ai.app/target/gebo.ai.app-${VERSION}-bootable.jar"
SBOM="$REPO_ROOT/gebo.apps.parent/gebo.ai.app/target/classes/META-INF/sbom/application.cdx.json"

TARGET="${1:-all}"

check_artifacts() {
  if [ ! -f "$JAR" ]; then
    echo "ERROR: Bootable jar not found at $JAR"
    echo "Build it first:  mvn -f gebo.apps.parent/gebo.ai.app/pom.xml -P bootables package -DskipTests"
    exit 1
  fi
  if [ ! -f "$SBOM" ]; then
    echo "ERROR: SBOM not found at $SBOM"
    exit 1
  fi
}

build_platform() {
  echo "=== Building geboai/platform:2.5 (local) ==="
  docker buildx build \
    --network=host \
    --platform linux/amd64 \
    --load \
    -t geboai/platform:2.5 \
    "$REPO_ROOT/dockers/gebo.ai.platform"
}

build_gebo_ai() {
  check_artifacts
  echo "=== Building geboai/gebo.ai (local) ==="
  cp "$JAR" "$REPO_ROOT/dockers/gebo.ai/"
  cp "$SBOM" "$REPO_ROOT/dockers/gebo.ai/"
  docker buildx build \
    --network=host \
    --platform linux/amd64 \
    --load \
    -t geboai/gebo.ai \
    -t geboai/gebo.ai:${VERSION} \
    "$REPO_ROOT/dockers/gebo.ai"
}

build_easyinstall() {
  check_artifacts
  echo "=== Building geboai/easyinstall.gebo.ai (local) ==="
  cp "$JAR" "$REPO_ROOT/dockers/easyinstall.gebo.ai/"
  cp "$SBOM" "$REPO_ROOT/dockers/easyinstall.gebo.ai/"
  docker buildx build \
    --network=host \
    --platform linux/amd64 \
    --load \
    -t geboai/easyinstall.gebo.ai \
    -t geboai/easyinstall.gebo.ai:${VERSION} \
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
