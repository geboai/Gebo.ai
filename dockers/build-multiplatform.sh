#!/usr/bin/env bash
#
# build-multiplatform.sh — Build gebo.ai Docker images for linux/amd64 +
# linux/arm64, WITHOUT pushing (build phase)
#
# Builds all 3 images (or a single one) with SBOM attestation enabled but
# push=false, so the multi-platform result stays in BuildKit's build cache —
# nothing is sent to Docker Hub. Multi-platform results can't be --load'ed
# into the local Docker daemon either (the docker exporter doesn't support
# manifest lists), so this phase is for validating the build succeeds for
# every platform and warming the cache, not for running the image locally.
#
# Usage:
#   ./dockers/build-multiplatform.sh                    # build all 3 images
#   ./dockers/build-multiplatform.sh platform           # build only the base platform image
#   ./dockers/build-multiplatform.sh gebo.ai            # build only geboai/gebo.ai
#   ./dockers/build-multiplatform.sh easyinstall        # build only geboai/easyinstall.gebo.ai
#   --platforms  Override platforms (default: linux/amd64,linux/arm64)
#
# Prerequisites:
#   - docker buildx enabled (docker buildx create --use --name multiarch if needed)
#   - The monolith bootable jar built:
#       mvn -f gebo.apps.parent/gebo.ai.app/pom.xml -P bootables package -DskipTests
#
# To publish what this just built, run (same builder so its cache is reused —
# publish will only push, not rebuild):
#   ./dockers/publish-multiplatform.sh [target]
#
set -euo pipefail
cd "$(dirname "${BASH_SOURCE[0]}")"

TARGET="${1:-all}"
shift || true
while [[ $# -gt 0 ]]; do
  case "$1" in
    --platforms) export PLATFORMS="$2"; shift 2 ;;
    *) shift ;;
  esac
done

source ./lib-multiplatform-images.sh

run_target false "$TARGET"

echo "=== Done (built, not pushed) ==="
