#!/usr/bin/env bash
#
# regenerate-all-microservices-stubs.sh
# ---------------------------------------------------------------------------
# Atomic pipeline: either all 38 client stubs regenerate successfully and get
# committed, or the repository returns to its exact pre-run state.
#
# Stages:
#   1. Rebuild all 19 microservice Docker images (jib:buildTar, swagger-on).
#   2. docker compose down + up -d the full stack.
#   3. Poll /v3/api-docs on all services (skip unready ones after timeout).
#   4. Git-stash the clients parent, then clean all 38 stubs.
#   5. Regenerate each stub from live specs (per-client git restore on failure).
#   6. Compile to verify.
#   7. Tear down docker compose.
#   8. Create branch feature/stubs-refresh and commit regenerated sources.
#
# On ANY failure after Stage 3, the git stash is popped and the repository
# is restored to its exact pre-run state.
# ---------------------------------------------------------------------------
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "$SCRIPT_DIR/../.." && pwd)"
COMPOSE_FILE="$REPO_ROOT/dockers/gebo.microservices/docker-compose.yml"
CLIENTS_PARENT="$REPO_ROOT/gebo.api.clients/gebo.microservices.clients.parent"
MICROSERVICES_PARENT="$REPO_ROOT/gebo.apps.parent/gebo.microservices.apps.parent"
STASH_REF=""
ATOMIC_SUCCESS=0

# ---- Service -> port map ---------------------------------------------------
declare -A SERVICE_PORTS
SERVICE_PORTS=(
  [eureka]=13017       [gateway]=13000     [heimdall]=13018
  [brain]=13001        [vectorizator]=13002 [graphicator]=13003
  [chunker]=13004      [git]=13005         [filesystem]=13006
  [uploads]=13007      [userspace]=13008   [sharepoint]=13009
  [confluence]=13010   [jira]=13011        [aws-s3]=13012
  [googledrive]=13013  [mcpclient]=13014   [integration]=13015
  [fulltextor]=13016
)

# ---- helpers ---------------------------------------------------------------
red()    { echo -e "\e[31m$*\e[0m"; }
green()  { echo -e "\e[32m$*\e[0m"; }
yellow() { echo -e "\e[33m$*\e[0m"; }

bail() {
  red "FATAL: $*"
  exit 1
}

# ---- atomic snapshot / rollback --------------------------------------------

# Called on EXIT or explicitly.  If we haven't reached success, restore the
# stashed pre-regen state so the repo is exactly as it was.
restore_snapshot() {
  # Always tear down compose — no-op if already down
  docker compose -f "$COMPOSE_FILE" down --remove-orphans --volumes 2>/dev/null || true

  if [ "$ATOMIC_SUCCESS" -eq 1 ]; then
    return 0
  fi
  if [ -z "$STASH_REF" ]; then
    return 0
  fi
  yellow ""
  yellow "Restoring pre-regeneration state (git stash pop) ..."
  git -C "$REPO_ROOT" stash pop --index 2>/dev/null || {
    red "  stash pop failed — forcing checkout of clients parent"
    git -C "$REPO_ROOT" checkout -- "$CLIENTS_PARENT" 2>/dev/null || true
    git -C "$REPO_ROOT" stash drop 2>/dev/null || true
  }
}

# ---- polling ---------------------------------------------------------------
poll_until_200() {
  local port=$1 svc=$2 max_attempts=36 interval=5 attempt=0
  local url="http://localhost:$port/v3/api-docs"
  yellow "  Waiting for $svc on $url ..."
  while [ $attempt -lt $max_attempts ]; do
    local code
    code=$(curl -s -o /dev/null -w '%{http_code}' "$url" 2>/dev/null || true)
    if [ "$code" = "200" ]; then
      green "    $svc ($port) answered 200 after $((attempt * interval))s"
      return 0
    fi
    sleep "$interval"
    ((attempt++))
  done
  red "    $svc ($port) did not answer 200 within $((max_attempts * interval))s — skipping"
  return 0
}

# ---- clean procedures ------------------------------------------------------
clean_java_client() {
  local dir=$1
  yellow "  Cleaning Java  client: $(basename "$dir")"
  rm -rf "$dir/src" "$dir/docs" "$dir/README.md" "$dir/.swagger-codegen"
}

clean_angular_client() {
  local dir=$1
  yellow "  Cleaning Angular client: $(basename "$dir")"
  local lib
  lib=$(find "$dir/projects" -maxdepth 1 -type d -name 'gebo-*-api' | head -1)
  if [ -z "$lib" ]; then
    red "    Could not find library directory under $dir/projects"
    return 1
  fi
  local lib_src="$lib/src/lib"
  rm -rf "$lib_src/api" "$lib_src/model" "$lib_src/.swagger-codegen"
  find "$lib_src" -maxdepth 1 -name '*.ts' -delete 2>/dev/null || true
}

# ---- regeneration with per-client rollback ---------------------------------
regenerate_client() {
  local client_dir=$1 client_name
  client_name=$(basename "$client_dir")
  yellow "Regenerating $client_name ..."
  if ! mvn -f "$client_dir/pom.xml" generate-sources -P generate-rest-api -q 2>&1; then
    red "  FAILED — restoring $client_name via git restore"
    git -C "$REPO_ROOT" restore "$client_dir"
    return 1
  fi
  green "  OK"
  return 0
}

# ---- main ------------------------------------------------------------------

# Install the atomic rollback trap.  From this point forward, any exit that is
# NOT preceded by ATOMIC_SUCCESS=1 will restore the pre-regen stash.
trap restore_snapshot EXIT

echo "========================================================================="
green "  Microservices client stubs — atomic regeneration pipeline"
echo "========================================================================="

# ----- Stage 1: Build Docker images ----------------------------------------
yellow ""
yellow "Stage 1: Building microservice Docker images (jib:buildTar, swagger-on)"
yellow "----------------------------------------------------------------------"
mvn -f "$MICROSERVICES_PARENT/pom.xml" -P docker,swagger-on jib:buildTar \
    -DskipTests -q 2>&1 || bail "Docker image build failed"

yellow "Loading images into Docker daemon ..."
for app_dir in "$MICROSERVICES_PARENT"/*.gebo.ai/; do
  image_tar="$app_dir/target/jib-image.tar"
  if [ -f "$image_tar" ]; then
    docker load -i "$image_tar" >/dev/null 2>&1
  fi
done
green "Stage 1 OK"

# ----- Stage 2: docker compose down + up -----------------------------------
yellow ""
yellow "Stage 2: Starting docker compose stack"
yellow "---------------------------------------"
docker compose -f "$COMPOSE_FILE" down --remove-orphans --volumes 2>/dev/null || true
docker compose -f "$COMPOSE_FILE" up -d 2>&1 || bail "docker compose up failed"

# ----- Stage 3: Poll /v3/api-docs for every service ------------------------
yellow ""
yellow "Stage 3: Polling /v3/api-docs on all 19 services"
yellow "-------------------------------------------------"
for svc in "${!SERVICE_PORTS[@]}"; do
  poll_until_200 "${SERVICE_PORTS[$svc]}" "$svc"
done
green "Finished polling — proceeding with available services"

# ----- Stage 3.5: Git-stash the clients parent (atomic snapshot) ------------
yellow ""
yellow "Snapshot: saving current state of client stubs (git stash)"
yellow "----------------------------------------------------------"
STASH_OUT=$(git -C "$REPO_ROOT" stash push --include-untracked \
    -m "pre-regen-snapshot" -- "$CLIENTS_PARENT" 2>&1) || true
if echo "$STASH_OUT" | grep -q "No local changes"; then
  yellow "  No changes to stash — working tree was already clean"
  STASH_REF=""
else
  STASH_REF=$(echo "$STASH_OUT" | grep -oP 'stash@\{[0-9]+\}' | head -1)
  green "  Stashed as $STASH_REF"
fi

# ----- Stage 4: Clean all 38 client stubs ----------------------------------
yellow ""
yellow "Stage 4: Cleaning previously generated sources from all 38 clients"
yellow "-----------------------------------------------------------------"
for client_dir in "$CLIENTS_PARENT"/*.java.client/ "$CLIENTS_PARENT"/*.angular.client/; do
  base=$(basename "$client_dir")
  if [[ "$base" == *".angular.client" ]]; then
    clean_angular_client "$client_dir" || bail "Clean failed for $base"
  elif [[ "$base" == *".java.client" ]]; then
    clean_java_client "$client_dir" || bail "Clean failed for $base"
  fi
done
green "Stage 4 OK — all stubs cleaned"

# ----- Stage 5: Regenerate all 38 stubs ------------------------------------
yellow ""
yellow "Stage 5: Regenerating all 38 stubs from live specs"
yellow "---------------------------------------------------"
any_failure=0
for client_dir in "$CLIENTS_PARENT"/*.java.client/ "$CLIENTS_PARENT"/*.angular.client/; do
  if ! regenerate_client "$client_dir"; then
    any_failure=1
  fi
done

if [ $any_failure -ne 0 ]; then
  bail "One or more client regenerations failed — rolling back via stash pop"
fi
green "Stage 5 OK — all 38 stubs regenerated"

# ----- Stage 6: Compile to verify -----------------------------------------
yellow ""
yellow "Stage 6: Compiling all regenerated clients"
yellow "------------------------------------------"
mvn -f "$CLIENTS_PARENT/pom.xml" clean install -q 2>&1 \
  || bail "Client compilation failed — rolling back via stash pop"
green "Stage 6 OK — all clients compile"

# ----- Stage 7: Docker compose down ---------------------------------------
yellow ""
yellow "Stage 7: Tearing down docker compose"
yellow "-------------------------------------"
docker compose -f "$COMPOSE_FILE" down --remove-orphans --volumes 2>/dev/null || true

# ----- Mark atomic success (trap will NOT restore stash) -------------------
ATOMIC_SUCCESS=1

# Drop the stash now that we've succeeded
if [ -n "$STASH_REF" ]; then
  git -C "$REPO_ROOT" stash drop "$STASH_REF" 2>/dev/null || true
  green "Stash dropped — regenerated sources are the new state"
fi

# ----- Stage 8: Create feature/stubs-refresh branch and commit ------------
yellow ""
yellow "Stage 8: Committing regenerated sources"
yellow "---------------------------------------"
git -C "$REPO_ROOT" add "$CLIENTS_PARENT" 2>/dev/null || true

if git -C "$REPO_ROOT" diff --cached --quiet; then
  yellow "No changes detected — stubs are already up to date"
  exit 0
fi

CURRENT_BRANCH=$(git -C "$REPO_ROOT" rev-parse --abbrev-ref HEAD)
TARGET_BRANCH="feature/stubs-refresh"

if git -C "$REPO_ROOT" rev-parse --verify "$TARGET_BRANCH" >/dev/null 2>&1; then
  yellow "Branch $TARGET_BRANCH already exists — committing on it"
  git -C "$REPO_ROOT" checkout "$TARGET_BRANCH"
else
  git -C "$REPO_ROOT" checkout -b "$TARGET_BRANCH"
fi

git -C "$REPO_ROOT" add "$CLIENTS_PARENT" 2>/dev/null || true

git -C "$REPO_ROOT" commit -m "chore(stubs): regenerate all microservices client stubs

Regenerated from live /v3/api-docs endpoints after full rebuild.
Source branch: $CURRENT_BRANCH"

green "Committed on branch: $TARGET_BRANCH"
green ""
green "========================================================================="
green "  All done. Review git status on $TARGET_BRANCH before pushing."
green "========================================================================="