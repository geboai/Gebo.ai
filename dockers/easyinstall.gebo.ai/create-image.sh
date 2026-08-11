#!/usr/bin/env bash
#
# create-image.sh — Build geboai/easyinstall.gebo.ai all-in-one image locally
#
# Copies the freshly built bootable jar and Maven SBOM into the build context,
# then builds the all-in-one image (MongoDB, Qdrant, Neo4j, OpenSearch + the
# Gebo.ai app) locally and loads into the Docker daemon (single-platform).
# The SBOM is COPY'd into the image at /opt/gebo.ai/sbom.cdx.json.
# The BuildKit SBOM attestation (--sbom) is only generated on publish
# (build-multiplatform.sh + publish-multiplatform.sh).
#
# Prerequisites:
#   mvn -f gebo.apps.parent/gebo.ai.app/pom.xml -P bootables package -DskipTests
#
set -euo pipefail
cd "$(dirname "$0")"
REPO_ROOT="$(cd ../.. && pwd)"
VERSION="1.0.2.4-SNAPSHOT"
JAR="$REPO_ROOT/gebo.apps.parent/gebo.ai.app/target/gebo.ai.app-${VERSION}-bootable.jar"
SBOM="$REPO_ROOT/gebo.apps.parent/gebo.ai.app/target/classes/META-INF/sbom/application.cdx.json"

if [ ! -f "$JAR" ]; then
  echo "ERROR: Bootable jar not found at $JAR"
  echo "Build it first:  mvn -f gebo.apps.parent/gebo.ai.app/pom.xml -P bootables package -DskipTests"
  exit 1
fi
if [ ! -f "$SBOM" ]; then
  echo "ERROR: SBOM not found at $SBOM"
  exit 1
fi

cp "$JAR" .
cp "$SBOM" .

docker image rm geboai/easyinstall.gebo.ai --force 2>/dev/null || true
docker buildx build \
  --network=host \
  --platform linux/amd64 \
  --load \
  --build-arg JAVA_EXTRA_SECURITY_DIR=/opt/gebo.ai \
  -t geboai/easyinstall.gebo.ai \
  -t geboai/easyinstall.gebo.ai:${VERSION} \
  .
