#!/bin/bash
# Re-assert the sandbox config on every start (idempotent, tolerant). Handles
# recreating the nextcloud container against a persistent data volume, where the
# one-time post-installation hook does not run again.
set -uo pipefail
if [ "$(id -u)" = "0" ]; then
  exec su -p www-data -s /bin/bash "$0" "$@"
fi
OCC() { php /var/www/html/occ "$@" 2>/dev/null || true; }

# Only act once Nextcloud is actually installed.
if ! OCC status | grep -qi 'installed: true'; then
  exit 0
fi

OCC config:system:set allow_local_remote_servers --type boolean --value true
if OCC app:list | grep -q 'onlyoffice'; then
  OCC config:app:set onlyoffice DocumentServerUrl         --value "$ONLYOFFICE_DS_BROWSER_URL"
  OCC config:app:set onlyoffice DocumentServerInternalUrl --value "$ONLYOFFICE_DS_INTERNAL_URL"
  OCC config:app:set onlyoffice StorageUrl                --value "$ONLYOFFICE_STORAGE_URL"
  OCC config:app:set onlyoffice jwt_secret                --value "$ONLYOFFICE_JWT_SECRET"
  OCC config:app:set onlyoffice jwt_header                --value "Authorization"
fi
exit 0
