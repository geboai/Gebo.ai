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
# Read the project's own <version> from the root pom.xml header, stopping at
# <parent> so we don't accidentally match spring-boot-starter-parent's version
# instead. Keeps this script aligned with whatever the actual build produces.
VERSION="$(sed -n '/<parent>/q;p' "$REPO_ROOT/pom.xml" | grep -oP '(?<=<version>)[^<]+(?=</version>)' | head -1)"
if [ -z "$VERSION" ]; then
  echo "ERROR: Could not read project version from $REPO_ROOT/pom.xml"
  exit 1
fi
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

# Update a Docker Hub repo's README (full_description). Only runs on the publish
# phase; `docker push` never carries the README, so this is a separate API call.
# Non-fatal: a README failure must not fail an otherwise-successful image push.
# $1: push flag  $2: docker hub repo (namespace/name)  $3: README file
push_readme() {
  local push="$1" repo="$2" readme="$3"
  [ "$push" = true ] || return 0
  echo "=== [publish] README -> $repo ==="
  if ! python3 "$REPO_ROOT/dockers/publish-readme.py" "$repo" "$readme"; then
    echo "WARNING: README update for $repo failed (image push was not affected)" >&2
  fi
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
  push_readme "$push" geboai/gebo.ai "$REPO_ROOT/dockers/gebo.ai/README.md"
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
  push_readme "$push" geboai/easyinstall.gebo.ai "$REPO_ROOT/dockers/easyinstall.gebo.ai/README.md"
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
