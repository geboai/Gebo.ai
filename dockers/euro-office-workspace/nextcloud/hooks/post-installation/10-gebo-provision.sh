#!/bin/bash
# ---------------------------------------------------------------------------
# One-time Nextcloud provisioning for the Gebo ONLYOFFICE workspace sandbox.
# Runs (as the container root entrypoint) the first time Nextcloud is installed
# on a fresh data volume. Everything here is also safe to re-run.
#
# It wires:
#   - the ONLYOFFICE connector app  -> the SSO-gated Document Server
#   - the user_oidc app             -> Keycloak (unified SSO login)
#   - a local "webdav" account + sample docs -> the Gebo WebDAV data source
# ---------------------------------------------------------------------------
set -euo pipefail

OCC() { php /var/www/html/occ "$@"; }

log() { echo "[gebo-provision] $*"; }

# occ must run as the web user.
if [ "$(id -u)" = "0" ]; then
  exec su -p www-data -s /bin/bash "$0" "$@"
fi

log "waiting for Keycloak discovery to be reachable..."
for i in $(seq 1 60); do
  if curl -fsS "$OIDC_DISCOVERY_URL" >/dev/null 2>&1; then
    log "Keycloak is up."
    break
  fi
  sleep 3
done

# Nextcloud blocks server-side requests to local/private addresses by default
# (SSRF guard). The ONLYOFFICE connector must reach the Document Server and
# receive callbacks over the internal docker network, so allow it in this
# sandbox only.
OCC config:system:set allow_local_remote_servers --type boolean --value true

# --------------------------- ONLYOFFICE connector ---------------------------
log "installing + configuring the onlyoffice connector app"
OCC app:install onlyoffice || OCC app:enable onlyoffice
OCC config:app:set onlyoffice DocumentServerUrl         --value "$ONLYOFFICE_DS_BROWSER_URL"
OCC config:app:set onlyoffice DocumentServerInternalUrl --value "$ONLYOFFICE_DS_INTERNAL_URL"
OCC config:app:set onlyoffice StorageUrl                --value "$ONLYOFFICE_STORAGE_URL"
OCC config:app:set onlyoffice jwt_secret                --value "$ONLYOFFICE_JWT_SECRET"
OCC config:app:set onlyoffice jwt_header                --value "Authorization"

# --------------------------- Keycloak SSO (user_oidc) ---------------------------
log "installing + configuring the user_oidc app"
OCC app:install user_oidc || OCC app:enable user_oidc
# Recreate the provider idempotently (delete-if-exists then add).
OCC user_oidc:provider --list 2>/dev/null | grep -qi 'Keycloak' && OCC user_oidc:provider:delete Keycloak || true
OCC user_oidc:provider Keycloak \
  --clientid="$OIDC_CLIENT_ID" \
  --clientsecret="$OIDC_CLIENT_SECRET" \
  --discoveryuri="$OIDC_DISCOVERY_URL" \
  --scope="openid email profile" \
  --mapping-uid="preferred_username" \
  --mapping-email="email" \
  --mapping-display-name="name" \
  --unique-uid=0
# Keep the local-login form available too (so ncadmin can still get in).
OCC config:app:set user_oidc allow_multiple_user_backends --value 1

# --------------------------- WebDAV data source seed ---------------------------
log "creating the 'webdav' account + sample documents for the Gebo WebDAV pipeline"
if ! OCC user:info "$WEBDAV_USER" >/dev/null 2>&1; then
  OC_PASS="$WEBDAV_PASSWORD" OCC user:add --password-from-env --display-name "WebDAV Ingest" "$WEBDAV_USER"
fi
SAMPLE_DEST="/var/www/html/data/${WEBDAV_USER}/files/GeboSamples"
if [ -d /gebo-sample-docs ]; then
  mkdir -p "$SAMPLE_DEST"
  cp -f /gebo-sample-docs/* "$SAMPLE_DEST"/ 2>/dev/null || true
  OCC files:scan "$WEBDAV_USER"
fi

log "provisioning complete."
