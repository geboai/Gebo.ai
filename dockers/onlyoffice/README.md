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

## gebo-ai-assistant: the real plugin, built on the brain JS stub

`plugin/gebo-ai-assistant/` is a working plugin - not just a scaffold - built
directly on the generated `brain-ai-js-client` stub (see
`gebo.js-plugins/brain/`), reproducing what
`gebo.ui/projects/gebo-ai-reusable-ui/src/lib/controls/chat-control`
(`gebo-ai-reusable-chat.component.ts`) does, adapted to the plugin sandbox:

- **This document** tab (in document context): reads the current selection
  via `Asc.plugin.executeMethod("GetSelectedText", ...)`, semantically
  searches the knowledge base for related content
  (`GeboUserKnowledgeBaseSemanticSearchController.semanticSearch`), or asks
  the model to elaborate/rewrite it (`GeboDirectModelChatController.chat`)
  and pastes the result back in via `PasteText`.
- **Knowledge base** tab (out of document context): an ordinary chat against
  the document-sharing system, independent of what's open in the editor.
  Routed through `GeboChatPipelinesController.executeDefaultChatPipeline`
  (not the simpler `GeboChatController.ragChat`) - matching how
  `gebo-ai-reusable-chat.component.ts`'s `callReactiveChat()` actually
  behaves: it calls `streamAgenticChat` (→ `streamChatPipeline`) for
  `ragsystem` chat, with `streamRagChat` (→ `streamRagResponse`) present in
  the file but explicitly left unused.

It uses the non-streaming `chat`/`executeDefaultChatPipeline`/
`semanticSearch` methods rather than their `stream*` counterparts: the
generated stub's `ApiClient` buffers the whole `superagent` response before
resolving (no incremental SSE chunk parsing the way the Angular reference
control's `reactive-chat.service.ts` does with a hand-rolled
`fetch`+`ReadableStream` reader), so the tradeoff is no token-by-token
streaming display - functionally equivalent, just not incremental.

### The current user's token, via the SSO gate already in place

Rather than asking the plugin's own UI to manage a login or a pasted token,
it reads the **same Keycloak session that already gates this sandbox**
(see the top of this README) - `auth.js` does a same-origin
`GET /oauth2/auth` and reads the access token back from oauth2-proxy's
`X-Auth-Request-Access-Token` response header (`oauth2-proxy.cfg` sets
`set_xauthrequest=true` + `pass_access_token=true` for exactly this). No
separate login step inside the plugin.

**Assumption for real deployments**: this only lets the plugin call the real
Gebo.ai installation's API as the logged-in user if that installation trusts
the *same* Keycloak realm/issuer this sandbox's Keycloak represents - i.e.
the sandbox's realm needs to be (or be federated with) whatever
Keycloak/OIDC issuer Gebo.ai's own `ai.gebo.security.oauth2configs` is
configured to accept (see the `keycloakClient`/`MainRealm` test config in
`gebo.apps.parent/gebo.ai.app`'s `application.yml`). This sandbox ships its
own standalone realm (`onlyoffice-dev`) for local plugin development only.

### Building and pointing it at a real Gebo.ai / brain

```
cd plugin/gebo-ai-assistant
npm install
npm run build          # bundles brain-ai-js-client into vendor/brain-client.bundle.js
```

`gebo.js-plugins/brain/brain-ai-js-client` must already be generated (see
`gebo.js-plugins/README.md`) - the build step bundles directly from that
generated source, it doesn't vendor a copy.

By default the plugin calls `http://localhost:13001/brain` (the brain
microservice's own local dev address - this sandbox does **not** run brain
itself, only the document-server/Keycloak/oauth2-proxy pieces). Point it at
a different installation with a `?geboBaseUrl=` query param on however this
plugin's URL is reached by the browser, or edit the default in `app.js`.

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
