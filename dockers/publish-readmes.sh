#!/usr/bin/env bash
#
# publish-readmes.sh — update ONLY the Docker Hub repo READMEs (full_description),
# without building or pushing any image. Use this when the READMEs changed but the
# images did not. The same update also runs automatically as part of
# publish-multiplatform.sh when images are pushed.
#
# Usage:
#   ./dockers/publish-readmes.sh                 # both: gebo.ai + easyinstall.gebo.ai
#   ./dockers/publish-readmes.sh gebo.ai         # only geboai/gebo.ai
#   ./dockers/publish-readmes.sh easyinstall     # only geboai/easyinstall.gebo.ai
#
# Credentials: DOCKERHUB_USERNAME + DOCKERHUB_TOKEN (a Personal Access Token
# works), else the username:password stored by `docker login` in
# ~/.docker/config.json. See dockers/publish-readme.py.
#
set -euo pipefail
cd "$(dirname "${BASH_SOURCE[0]}")"
REPO_ROOT="$(cd .. && pwd)"

update() { python3 "$REPO_ROOT/dockers/publish-readme.py" "$1" "$2"; }

TARGET="${1:-all}"
case "$TARGET" in
  gebo.ai)     update geboai/gebo.ai             "$REPO_ROOT/dockers/gebo.ai/README.md" ;;
  easyinstall) update geboai/easyinstall.gebo.ai "$REPO_ROOT/dockers/easyinstall.gebo.ai/README.md" ;;
  all)
    update geboai/gebo.ai             "$REPO_ROOT/dockers/gebo.ai/README.md"
    update geboai/easyinstall.gebo.ai "$REPO_ROOT/dockers/easyinstall.gebo.ai/README.md"
    ;;
  *)
    echo "Unknown target: $TARGET (use: gebo.ai, easyinstall, or all)"; exit 1 ;;
esac

echo "=== Done ==="
