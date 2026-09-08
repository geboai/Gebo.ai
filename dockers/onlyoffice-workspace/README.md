# ONLYOFFICE Workspace sandbox (Nextcloud + ONLYOFFICE + Gebo.ai + Keycloak)

A self-contained stack for testing **both**:

1. the Angular **Gebo AI Assistant** plugin inside a real Nextcloud-driven
   ONLYOFFICE editing session, and
2. the Gebo.ai **WebDAV data-source pipeline** ingesting from Nextcloud's native
   WebDAV endpoint —

behind **one Keycloak realm with unified SSO**.

For the euro-office Document Server variant of this same stack, see
`../euro-office-workspace`.

## Pieces

| Service | Browser URL | Role |
|---|---|---|
| Nextcloud | http://nextcloud.localtest.me:8090/ | The workspace: files, WebDAV, OIDC login, ONLYOFFICE connector |
| Document Server (+ oauth2-proxy) | http://office.localtest.me:4280/ | ONLYOFFICE hosting the plugin, SSO-gated |
| Keycloak | http://keycloak.localtest.me:8082/ | IdP, realm `onlyoffice-workspace-dev` (admin/admin) |
| Gebo.ai | http://gebo.localtest.me:13999/ | Backend, OAuth2 resource server, WebDAV ingestion target |
| mongo / qdrant / neo4j / opensearch | — | Platform infrastructure |

`*.localtest.me` all resolve to `127.0.0.1` via public wildcard DNS — no hosts
file editing needed.

### Why localtest.me (the SSO-over-HTTP trick)

Nextcloud embeds the ONLYOFFICE editor in an `<iframe>`. If Nextcloud and the
editor were on unrelated hosts, the oauth2-proxy session cookie would be a
third-party cookie in that iframe and `SameSite=Lax` would drop it over plain
HTTP — the plugin would never see a token. By putting Nextcloud
(`nextcloud.localtest.me`) and the office proxy (`office.localtest.me`) under the
**same registrable domain** (`localtest.me`), the iframe is **same-site**, so the
cookie is sent and the plugin's existing `/oauth2/auth` handoff works with no TLS.

## Prerequisites

Build the plugin first (produces the `dist/office-plugin/browser` mounted into
the Document Server):

```
cd ../../../Gebo.ai-offices-plugin && npm ci && npm run build
```

## Bring it up

```
docker compose up -d
```

First start is slow: Gebo initialises a fresh DB, and the Nextcloud
`post-installation` hook installs the `onlyoffice` + `user_oidc` apps from the
app store (needs internet), configures them, and seeds a `webdav` account with
sample documents.

Watch the provisioning:

```
docker compose logs -f nextcloud | grep gebo-provision
```

## Accounts

| Where | User | Password |
|---|---|---|
| Keycloak SSO (browser login everywhere) | `developer` | `developer` |
| Nextcloud local admin | `ncadmin` | `ncadmin-sandbox-2026` |
| Nextcloud WebDAV data source | `webdav` | `webdav-sandbox-2026` |
| Keycloak admin console | `admin` | `admin` |
| Gebo.ai platform admin | `admin@gebo.ai` | `Adm1n-sandbox-2026` |

## Test 1 — the plugin in a Nextcloud editing session

1. Open http://nextcloud.localtest.me:8090/ and click **Log in with Keycloak**
   (the user_oidc button). Sign in as `developer` / `developer`.
2. Create or upload a `.docx`, open it — Nextcloud opens it in ONLYOFFICE.
3. In the editor's **Plugins** tab, open **Gebo AI Assistant**. Because you are
   already signed in through Keycloak, the editor gate authenticates silently
   (unified SSO) and the plugin talks to Gebo as the `developer` identity.

## Test 2 — the WebDAV data-source pipeline

The `webdav` account already holds the sample docs under `GeboSamples/`. Register
that WebDAV endpoint as a Gebo data source, then ingest.

- **WebDAV base URL (from inside the network, which is what Gebo uses):**
  `http://nextcloud/remote.php/dav/files/webdav/`
- **User / password:** `webdav` / `webdav-sandbox-2026`

Register it either through the admin UI (**WebDAV client admin** /
`gebo-ai-webdav-endpoint` editor) or the REST API
(`WebdavSystemsController` — `POST api/.../WebdavSystemsController` /
`fastWebdavSystemInsert`). Then browse/ingest via `WebdavBrowsingController`.
The two sample markdown files under `GeboSamples/` should appear and ingest.

> The browser can reach the same WebDAV at
> `http://nextcloud.localtest.me:8090/remote.php/dav/files/webdav/` for manual
> inspection, but register the **internal** `http://nextcloud/...` URL in Gebo
> (that is the address the backend container resolves).

## Prime the office session once per browser (important)

The editor is embedded by Nextcloud, and Nextcloud loads the ONLYOFFICE
`api.js` as a cross-site (but same-site — `localtest.me`) subresource from the
office origin. oauth2-proxy can 302 a *navigation* to Keycloak for silent SSO,
but it cannot complete that interactive redirect for a `<script>`/subresource
load — so the very first editor open hangs unless the office-origin session
cookie already exists.

Fix (one time per browser session, right after logging into Nextcloud via
Keycloak): visit **http://office.localtest.me:4280/** once. It silently SSOs
against the active Keycloak session and sets the office cookie; because office
and Nextcloud are same-site, every later editor subresource load then carries
it. After that, open documents from Nextcloud normally.

## Notes & gotchas

- **user_oidc requires HTTPS** for the OIDC flow unless the dev override
  `allow_insecure_http` is set on the app — the provisioning hook sets it, since
  this sandbox is HTTP-only. Never set it in production; put real TLS in front.
- **oauth2-proxy on the office gate** uses `skip_provider_button=true` (302
  straight to Keycloak so the embedded iframe authenticates silently) and must
  NOT set `cookie_domains` — oauth2-proxy matches that against the host
  *including the port*, never matches `office.localtest.me:4280`, and then 403s
  every request. A host-only cookie is correct here.

- **Plugin rebuilds** are picked up by recreating just the Document Server:
  `docker compose up -d --force-recreate --no-deps documentserver`.
  (The plugin `dist` is a read-only bind mount.)
- **"Connection lost" in the editor** after recreating `documentserver` is
  expected — reload the Nextcloud editor page.
- **ONLYOFFICE connectivity check** (Nextcloud → DS) uses the *internal* URL
  `http://documentserver/` and the shared `jwt_secret`; the browser uses
  `http://office.localtest.me:4280/`. Both are set by the provisioning hook.
- **`allow_local_remote_servers`** is turned on so Nextcloud will talk to the
  Document Server and receive callbacks over the private docker network. Sandbox
  only — never do this in production.
- Ports are offset from `../onlyoffice-assistant`, but running multiple full
  stacks at once is heavy (each is ~11 containers incl. OpenSearch + Nextcloud).
