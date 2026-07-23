#!/usr/bin/env bash
#
# publish-multiplatform.sh — Push gebo.ai Docker images to Docker Hub for
# linux/amd64 + linux/arm64, with SBOM attestation (publish phase)
#
# Reruns the same docker buildx definitions as build-multiplatform.sh but
# with push=true. If the builder's cache from the build phase is still warm
# (same builder, nothing changed), this only pushes — it doesn't rebuild.
#
# Usage:
#   ./dockers/publish-multiplatform.sh                      # push all 3 images
#   ./dockers/publish-multiplatform.sh platform              # push only geboai/platform
#   ./dockers/publish-multiplatform.sh gebo.ai              # push only geboai/gebo.ai
#   ./dockers/publish-multiplatform.sh easyinstall          # push only geboai/easyinstall.gebo.ai
#   --platforms  Override platforms (default: linux/amd64,linux/arm64)
#
# Prerequisites:
#   - docker buildx enabled (docker buildx create --use --name multiarch if needed)
#   - Logged in to Docker Hub:  docker login
#   - The monolith bootable jar built:
#       mvn -f gebo.apps.parent/gebo.ai.app/pom.xml -P bootables package -DskipTests
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

run_target true "$TARGET"

echo "=== Done ==="
echo ""
echo "Inspect SBOM attestations with:"
echo "  docker buildx imagetools inspect geboai/gebo.ai:${VERSION} --format json"
