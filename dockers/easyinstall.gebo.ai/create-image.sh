#!/usr/bin/env bash
#
# create-image.sh — Build geboai/easyinstall.gebo.ai all-in-one image with SBOM
#
# Copies the freshly built bootable jar and Maven SBOM into the build context,
# then builds the all-in-one image (MongoDB, Qdrant, Neo4j, OpenSearch + the
# Gebo.ai app). The SBOM is available both as a BuildKit attestation (OS-level
# packages + the Maven SBOM) and as an in-image file at /opt/gebo.ai/sbom.cdx.json.
#
# Usage:
#   ./create-image.sh              (local, single-platform, --load)
#   ./create-image.sh --push      (push to Docker Hub, multiplatform)
#
set -euo pipefail
cd "$(dirname "$0")"
REPO_ROOT="$(cd ../.. && pwd)"
VERSION="1.0.2.1-SNAPSHOT"
JAR="$REPO_ROOT/gebo.apps.parent/gebo.ai.app/target/gebo.ai.app-${VERSION}-bootable.jar"
SBOM="$REPO_ROOT/gebo.apps.parent/gebo.ai.app/target/classes/META-INF/sbom/application.cdx.json"

ACTION="--load"
PLATFORMS="linux/amd64"

if [ "${1:-}" = "--push" ]; then
  ACTION="--push"
  PLATFORMS="linux/amd64,linux/arm64"
fi

if [ ! -f "$JAR" ]; then
  echo "ERROR: Bootable jar not found at $JAR"
  echo "Build it first:  mvn -f gebo.apps.parent/gebo.ai.app/pom.xml -P swagger-on package -DskipTests"
  exit 1
fi
if [ ! -f "$SBOM" ]; then
  echo "ERROR: SBOM not found at $SBOM"
  exit 1
fi

# Copy artifacts into build context
cp "$JAR" .
cp "$SBOM" .

docker image rm geboai/easyinstall.gebo.ai --force 2>/dev/null || true
docker buildx build \
  --platform "$PLATFORMS" \
  --sbom=true \
  --build-arg JAVA_EXTRA_SECURITY_DIR=/opt/gebo.ai \
  -t geboai/easyinstall.gebo.ai \
  -t geboai/easyinstall.gebo.ai:${VERSION} \
  $ACTION \
  .
