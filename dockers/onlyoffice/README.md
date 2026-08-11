# ONLYOFFICE + Keycloak SSO plugin-development sandbox

Stands up ONLYOFFICE Document Server behind Keycloak-backed SSO, for developing
and testing an ONLYOFFICE JavaScript editor plugin locally.

## Why a reverse proxy in front of the document server

ONLYOFFICE Document Server has no built-in OIDC/SSO login screen of its own —
it's an embeddable editor secured by a shared `JWT_SECRET` signed between a
calling application and the server, not user-facing authentication. To make
"the document server authenticates with the SSO" concrete, [oauth2-proxy]
sits in front of it as an OIDC gate backed by Keycloak: reaching the editor
sandbox requires logging in via Keycloak first. `JWT_SECRET` is a **separate**
concern from that login — it protects editor-config integrity between caller
and document server, independent of who is allowed to reach the sandbox.

[oauth2-proxy]: https://oauth2-proxy.github.io/oauth2-proxy/

## Usage

```
docker compose up -d
```

Then open **http://localhost:4180/welcome/** — you'll be redirected to
Keycloak to log in:

- realm: `onlyoffice-dev`
- user: `developer` / password: `developer`

Keycloak's own admin console is at **http://keycloak.localtest.me:8081/**
(`admin` / `admin`). `keycloak.localtest.me` is a public wildcard DNS entry
that always resolves to `127.0.0.1` — used here (with a matching Docker
network alias on the `keycloak` service) so Keycloak's hostname/issuer
resolves identically for your browser and for the `oauth2-proxy` container,
at the same port (8081, no NAT offset). That's what keeps Keycloak's
issuer/token validation consistent from both sides — see oauth2-proxy's own
[Keycloak example][oauth2-proxy-keycloak-example] for the upstream pattern
this follows.

[oauth2-proxy-keycloak-example]: https://github.com/oauth2-proxy/oauth2-proxy/blob/master/contrib/local-environment/docker-compose-keycloak.yaml

The document server itself is not published to the host — every request goes
through `oauth2-proxy` on port 4180.

## Developing a plugin

`plugin/hello-world/` is bind-mounted read-only into the document server's
`sdkjs-plugins/hello-world/` folder and is auto-discovered — it's a minimal
working example (adds a button that inserts text) to use as a starting point.

1. Open a document through the sandbox, then the editor's **Plugins** tab —
   "Hello World" should be listed.
2. Edit `plugin/hello-world/index.html` (and add more files alongside it as
   your plugin grows).
3. `docker compose restart documentserver` to pick up changes — the document
   server caches plugin discovery, so a brand-new plugin folder (not just
   edits to an existing one) needs this restart to show up.

To add a **second, independent plugin**, don't drop it into `plugin/` and
expect it to appear — Docker bind-mounts are per-path. Give it its own
`plugin/<name>/` folder and add a matching volume line in
`docker-compose.yml`:

```yaml
- ./plugin/<name>:/var/www/onlyoffice/documentserver/sdkjs-plugins/<name>:ro
```

Never bind-mount `sdkjs-plugins` itself (rather than a subfolder of it) — that
would obscure the server's own built-in plugins that already live there.

Plugin structure/config.json reference:
https://api.onlyoffice.com/docs/plugin-and-macros/structure/configuration/

## Cleanup

```
docker compose down -v
```

The `-v` also drops the document-server data/log volumes and the Keycloak
dev database (Keycloak runs in-memory `start-dev` mode here — nothing to
drop there, but the flag is harmless).

## Note on secrets

Every secret in this stack (`JWT_SECRET`, the Keycloak client secret, the
oauth2-proxy cookie secret, the `developer` user's password) is a
dev-only placeholder committed in plaintext, scoped to `localhost` only —
consistent with the other local dev sandboxes in this repo (e.g.
`dockers/dex-oauth2`). Do not reuse any of these values outside this sandbox.
