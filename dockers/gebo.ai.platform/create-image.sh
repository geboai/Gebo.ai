#!/usr/bin/env bash
#
# create-image.sh — Build geboai/platform base image with SBOM attestation
#
# Usage:
#   ./create-image.sh              (local, single-platform, --load)
#   ./create-image.sh --push      (push to Docker Hub, multiplatform)
#
set -euo pipefail
cd "$(dirname "$0")"

ACTION="--load"
PLATFORMS="linux/amd64"

if [ "${1:-}" = "--push" ]; then
  ACTION="--push"
  PLATFORMS="linux/amd64,linux/arm64"
fi

docker image rm geboai/platform:2.5 --force 2>/dev/null || true
docker buildx build \
  --platform "$PLATFORMS" \
  --sbom=true \
  -t geboai/platform:2.5 \
  $ACTION \
  .
