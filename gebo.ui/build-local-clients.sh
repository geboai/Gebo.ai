#!/usr/bin/env bash
# ===========================================================================
# build-local-clients.sh
#
# Builds each generated microservice Angular client library and the
# MicroservicesClientsModule wiring library, packs an installable .tgz from
# each, and stages every archive gebo.ui depends on under gebo.ui/dependencies/.
#
# gebo.ui/package.json references those local archives as `file:dependencies/*.tgz`,
# so the UI resolves the per-microservice clients without a registry - the same
# way it is built in CI. Re-run this after regenerating any stub.
#
#   ./gebo.ui/build-local-clients.sh
#
# (The Maven build of gebo.api.clients/gebo.microservices.clients.parent runs the
#  same per-library `npm run build-lib` via the frontend plugin; this script is
#  the standalone equivalent that also collects the archives for the UI.)
# ===========================================================================
set -euo pipefail

REPO="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
MS="$REPO/gebo.api.clients/gebo.microservices.clients.parent"
DEPS="$REPO/gebo.ui/dependencies"
NODE_DIR="$REPO/gebo.ui/node"
NPM="$NODE_DIR/npm"
export PATH="$NODE_DIR:$PATH"

# Version the reactor produces (matches the root pom project.version).
VER="1.0.4.0-SNAPSHOT"

# The 21 per-microservice client workspaces + the wiring module workspace.
WORKSPACES=( "$MS"/*.angular.client "$MS/gebo.microservices.clients.angular.module" )

echo "== building + packing $(( ${#WORKSPACES[@]} )) client libraries =="
for ws in "${WORKSPACES[@]}"; do
  [ -d "$ws" ] || continue
  echo ">>> $(basename "$ws")"
  ( cd "$ws"
    [ -d node_modules ] || "$NPM" install --no-audit --no-fund
    "$NPM" run build-lib     # ng build <lib>  &&  npm pack ./dist/<lib>
  )
done

echo "== staging archives under gebo.ui/dependencies =="
mkdir -p "$DEPS"
rm -f "$DEPS"/*.tgz
for ws in "${WORKSPACES[@]}"; do
  [ -d "$ws" ] || continue
  for t in "$ws"/*-"$VER".tgz; do
    [ -f "$t" ] && cp "$t" "$DEPS/"
  done
done
echo "staged $(ls "$DEPS"/*.tgz 2>/dev/null | wc -l) archives:"
ls "$DEPS"/*.tgz | sed 's|.*/|  |'
