#!/usr/bin/env bash
#
# push-sbom-images.sh — Push gebo.ai Docker images to a registry (multiplatform)
#
# Builds all 3 images (or a single one) for linux/amd64,linux/arm64 with SBOM
# attestation enabled and pushes them to Docker Hub. This is the only script
# that pushes — the create-image.sh and build-sbom-images.sh scripts are
# local-only (--load).
#
# Usage:
#   ./dockers/push-sbom-images.sh                      # push all 3 images
#   ./dockers/push-sbom-images.sh platform              # push only geboai/platform
#   ./dockers/push-sbom-images.sh gebo.ai              # push only geboai/gebo.ai
#   ./dockers/push-sbom-images.sh easyinstall          # push only geboai/easyinstall.gebo.ai
#   --platforms  Override platforms (default: linux/amd64,linux/arm64)
#
# Prerequisites:
#   - docker buildx enabled (docker buildx create --use --name sbom-builder)
#   - Logged in to Docker Hub:  docker login
#   - The monolith bootable+swagger jar built:
#       mvn -f gebo.apps.parent/gebo.ai.app/pom.xml -P bootables package -DskipTests
#
set -euo pipefail

REPO_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
VERSION="1.0.2.1-SNAPSHOT"
PLATFORMS="linux/amd64,linux/arm64"
JAR="$REPO_ROOT/gebo.apps.parent/gebo.ai.app/target/gebo.ai.app-${VERSION}-bootable.jar"
SBOM="$REPO_ROOT/gebo.apps.parent/gebo.ai.app/target/classes/META-INF/sbom/application.cdx.json"

TARGET="${1:-all}"
shift || true

# Parse remaining args
while [[ $# -gt 0 ]]; do
  case "$1" in
    --platforms) PLATFORMS="$2"; shift 2 ;;
    *) shift ;;
  esac
done

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

push_platform() {
  echo "=== Pushing geboai/platform:2.5 with SBOM ($PLATFORMS) ==="
  docker buildx build \
    --platform "$PLATFORMS" \
    --sbom=true \
    --push \
    -t geboai/platform:2.5 \
    "$REPO_ROOT/dockers/gebo.ai.platform"
}

push_gebo_ai() {
  check_artifacts
  echo "=== Pushing geboai/gebo.ai with SBOM ($PLATFORMS) ==="
  cp "$JAR" "$REPO_ROOT/dockers/gebo.ai/"
  cp "$SBOM" "$REPO_ROOT/dockers/gebo.ai/"
  docker buildx build \
    --platform "$PLATFORMS" \
    --sbom=true \
    --push \
    -t geboai/gebo.ai \
    -t geboai/gebo.ai:${VERSION} \
    "$REPO_ROOT/dockers/gebo.ai"
}

push_easyinstall() {
  check_artifacts
  echo "=== Pushing geboai/easyinstall.gebo.ai with SBOM ($PLATFORMS) ==="
  cp "$JAR" "$REPO_ROOT/dockers/easyinstall.gebo.ai/"
  cp "$SBOM" "$REPO_ROOT/dockers/easyinstall.gebo.ai/"
  docker buildx build \
    --platform "$PLATFORMS" \
    --sbom=true \
    --push \
    -t geboai/easyinstall.gebo.ai \
    -t geboai/easyinstall.gebo.ai:${VERSION} \
    "$REPO_ROOT/dockers/easyinstall.gebo.ai"
}

case "$TARGET" in
  platform)    push_platform ;;
  gebo.ai)     push_gebo_ai ;;
  easyinstall) push_easyinstall ;;
  all)
    push_platform
    push_gebo_ai
    push_easyinstall
    ;;
  *)
    echo "Unknown target: $TARGET (use: platform, gebo.ai, easyinstall, or all)"
    exit 1
    ;;
esac

echo "=== Done ==="
echo ""
echo "Inspect SBOM attestations with:"
echo "  docker buildx imagetools inspect geboai/gebo.ai:${VERSION} --format json"
