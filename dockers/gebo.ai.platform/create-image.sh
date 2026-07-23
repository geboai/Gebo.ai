#!/usr/bin/env bash
#
# create-image.sh — Build geboai/platform base image locally
#
# Builds locally and loads into the Docker daemon (single-platform).
# The SBOM attestation (--sbom) is only generated on publish
# (build-multiplatform.sh + publish-multiplatform.sh) since the local Docker
# exporter cannot load manifest-list attestations.
#
set -euo pipefail
cd "$(dirname "$0")"

docker image rm geboai/platform:2.5 --force 2>/dev/null || true
docker buildx build \
  --network=host \
  --platform linux/amd64 \
  --load \
  -t geboai/platform:2.5 \
  .
