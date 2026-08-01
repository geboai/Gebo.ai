#!/usr/bin/env bash
#
# lib-multiplatform-images.sh — shared docker buildx definitions for the 3
# gebo.ai images (platform, gebo.ai, easyinstall.gebo.ai), built for
# linux/amd64 + linux/arm64 with SBOM attestation.
#
# Sourced by build-multiplatform.sh (build phase, push=false) and
# publish-multiplatform.sh (publish phase, push=true). Defining the buildx
# commands once, here, is the point: the two phases must build with
# byte-identical flags/context so BuildKit's cache carries over from build to
# publish instead of silently rebuilding — two independent copies of these
# commands is exactly what let the SBOM attestation go missing before.
#
set -euo pipefail

REPO_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
VERSION="1.0.2.2"
PLATFORMS="${PLATFORMS:-linux/amd64,linux/arm64}"
JAR="$REPO_ROOT/gebo.apps.parent/gebo.ai.app/target/gebo.ai.app-${VERSION}-bootable.jar"
SBOM="$REPO_ROOT/gebo.apps.parent/gebo.ai.app/target/classes/META-INF/sbom/application.cdx.json"

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

# $1: push - "false" (build phase, result stays in the BuildKit build cache)
#            or "true" (publish phase, pushed to Docker Hub)
image_platform() {
  local push="$1"
  echo "=== [$([ "$push" = true ] && echo publish || echo build)] geboai/platform:2.5 ($PLATFORMS) ==="
  docker buildx build \
    --platform "$PLATFORMS" \
    --sbom=true \
    --output "type=image,push=${push}" \
    -t geboai/platform:2.5 \
    "$REPO_ROOT/dockers/gebo.ai.platform"
}

image_gebo_ai() {
  local push="$1"
  check_artifacts
  echo "=== [$([ "$push" = true ] && echo publish || echo build)] geboai/gebo.ai ($PLATFORMS) ==="
  cp "$JAR" "$REPO_ROOT/dockers/gebo.ai/"
  cp "$SBOM" "$REPO_ROOT/dockers/gebo.ai/"
  docker buildx build \
    --platform "$PLATFORMS" \
    --sbom=true \
    --output "type=image,push=${push}" \
    -t geboai/gebo.ai \
    -t geboai/gebo.ai:${VERSION} \
    "$REPO_ROOT/dockers/gebo.ai"
}

image_easyinstall() {
  local push="$1"
  check_artifacts
  echo "=== [$([ "$push" = true ] && echo publish || echo build)] geboai/easyinstall.gebo.ai ($PLATFORMS) ==="
  cp "$JAR" "$REPO_ROOT/dockers/easyinstall.gebo.ai/"
  cp "$SBOM" "$REPO_ROOT/dockers/easyinstall.gebo.ai/"
  docker buildx build \
    --platform "$PLATFORMS" \
    --sbom=true \
    --output "type=image,push=${push}" \
    -t geboai/easyinstall.gebo.ai \
    -t geboai/easyinstall.gebo.ai:${VERSION} \
    "$REPO_ROOT/dockers/easyinstall.gebo.ai"
}

# $1: push - "false" or "true"
# $2: target - platform | gebo.ai | easyinstall | all
run_target() {
  local push="$1" target="$2"
  case "$target" in
    platform)    image_platform "$push" ;;
    gebo.ai)     image_gebo_ai "$push" ;;
    easyinstall) image_easyinstall "$push" ;;
    all)
      image_platform "$push"
      image_gebo_ai "$push"
      image_easyinstall "$push"
      ;;
    *)
      echo "Unknown target: $target (use: platform, gebo.ai, easyinstall, or all)"
      exit 1
      ;;
  esac
}
