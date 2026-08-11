# Euro-Office + Keycloak SSO plugin-development sandbox

Stands up Euro-Office Document Server behind Keycloak-backed SSO, for
developing and testing a JavaScript editor plugin locally. This mirrors
`dockers/onlyoffice/` — see that folder's README for the detailed rationale
behind the oauth2-proxy/Keycloak wiring.

## What Euro-Office is

[Euro-Office] is a fork of ONLYOFFICE Document Server maintained by a
consortium of European companies (IONOS, Nextcloud, Proton, OpenProject,
XWiki, and others) for digital sovereignty. It shares ONLYOFFICE's Docker
interface (`JWT_ENABLED`/`JWT_SECRET` env vars, same `sdkjs-plugins` plugin
system), published as `ghcr.io/euro-office/documentserver`.

[Euro-Office]: https://euro-office.github.io/documentation/introduction/overview/

## Why a reverse proxy in front of the document server

Like ONLYOFFICE, Euro-Office Document Server has no built-in OIDC/SSO login
screen of its own — it's an embeddable editor secured by a shared
`JWT_SECRET` signed between a calling application and the server, not
user-facing authentication. To make "the document server authenticates with
the SSO" concrete, [oauth2-proxy] sits in front of it as an OIDC gate backed
by Keycloak: reaching the editor sandbox requires logging in via Keycloak
first. `JWT_SECRET` is a **separate** concern from that login — it protects
editor-config integrity between caller and document server, independent of
who is allowed to reach the sandbox.

[oauth2-proxy]: https://oauth2-proxy.github.io/oauth2-proxy/

## Usage

```
docker compose up -d
```

Then open **http://localhost:4181/welcome/** — you'll be redirected to
Keycloak to log in:

- realm: `euro-office-dev`
- user: `developer` / password: `developer`

Keycloak's own admin console is at **http://keycloak.localtest.me:8082/**
(`admin` / `admin`). Ports here (8082, 4181) are offset from the
`dockers/onlyoffice/` sandbox's (8081, 4180) so both stacks can run at the
same time without colliding — see that folder's README for why
`keycloak.localtest.me` and the matching port (no NAT offset) are needed for
Keycloak's issuer/token validation to stay consistent between the browser and
the `oauth2-proxy` container.

The document server itself is not published to the host — every request goes
through `oauth2-proxy` on port 4181.

## Developing a plugin

`plugin/hello-world/` is bind-mounted read-only into the document server's
`sdkjs-plugins/hello-world/` folder and is auto-discovered — it's a minimal
working example (adds a button that inserts text) to use as a starting point.
It loads the plugin runtime from ONLYOFFICE's own CDN
(`onlyoffice.github.io/sdkjs-plugins`) since Euro-Office's plugin API is
inherited unchanged from the ONLYOFFICE codebase it forks; swap this for a
self-hosted copy if you need a fully offline sandbox.

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

## Cleanup

```
docker compose down -v
```

## Note on secrets

Every secret in this stack (`JWT_SECRET`, the Keycloak client secret, the
oauth2-proxy cookie secret, the `developer` user's password) is a
dev-only placeholder committed in plaintext, scoped to `localhost` only —
consistent with the other local dev sandboxes in this repo (e.g.
`dockers/dex-oauth2`). Do not reuse any of these values outside this sandbox.
