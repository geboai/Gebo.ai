---
name: office-plugin-dev
description: Architecture reference and operating runbook for the gebo-ai-assistant ONLYOFFICE/Euro-Office plugin and its Keycloak+oauth2-proxy sandboxes (dockers/onlyoffice, dockers/euro-office). Use when asked to modify, test, or debug that plugin, work on the document-editor SSO sandboxes, or understand how Gebo.ai plugs into an embedded document editor.
user-invocable: true
allowed-tools:
  - Read
  - Edit
  - Write
  - Glob
  - Grep
  - Bash
  - PowerShell
---

# Gebo.ai + Keycloak + ONLYOFFICE/Euro-Office plugin architecture

Reference + runbook for `dockers/onlyoffice` and `dockers/euro-office`: two near-identical
local sandboxes that let the `gebo-ai-assistant` document-editor plugin be developed and
tested against a real SSO-gated editor, and against a real Gebo.ai backend (standalone
`brain` microservice or the monolith). Written from a full session of building, debugging,
and live-testing this stack end to end — every gotcha below was hit for real.

## 1. The moving pieces, and how they connect

```
Browser
  └─ oauth2-proxy (Keycloak-gated reverse proxy in front of the whole document server)
       └─ ONLYOFFICE/Euro-Office Document Server (nginx + docservice + converter)
            └─ sdkjs-plugins/gebo-ai-assistant/  (our plugin: index.html + app.js, iframe'd in)
                 ├─ auth.js: same-origin GET /oauth2/auth -> reads the logged-in user's
                 │   Keycloak access token back from oauth2-proxy's
                 │   X-Auth-Request-Access-Token response header
                 └─ app.js: calls the Gebo.ai backend directly as that user
                      (brain-ai-js-client stub, bundled as vendor/brain-client.bundle.js)
                           └─ Gebo.ai backend (brain microservice OR monolith),
                                configured as an OAuth2 **resource server** trusting
                                the SAME Keycloak realm that gates the sandbox
```

Three independent trust/config surfaces have to line up for any of this to work:
1. **oauth2-proxy → Keycloak**: the sandbox's own login gate (`oauth2-proxy.cfg`).
2. **auth.js → oauth2-proxy**: same-origin cookie read, no separate login inside the plugin.
3. **Gebo.ai backend → Keycloak**: the backend independently trusts the *same* realm as an
   OAuth2 resource server (`ai.gebo.security.oauth2configs`) — this is what makes the
   token auth.js hands the plugin actually authenticate against Gebo.ai.

## 2. Directory map

```
dockers/onlyoffice/                  dockers/euro-office/            (near-identical twins)
  docker-compose.yml                   docker-compose.yml            keycloak+oauth2-proxy+documentserver
  keycloak/realm-export.json           keycloak/realm-export.json    realm "onlyoffice-dev" / "euro-office-dev"
  oauth2-proxy/oauth2-proxy.cfg        oauth2-proxy/oauth2-proxy.cfg
  plugin/hello-world/                  plugin/hello-world/           minimal working example plugin
  plugin/gebo-ai-assistant/            plugin/gebo-ai-assistant/     our plugin (see §4)
  README.md                            README.md

gebo.js-plugins/brain/brain-ai-js-client/   generated plain-JS API client (swagger-codegen,
                                             same mechanism as gebo.ui's Angular stubs) -
                                             `npm run build` in the plugin folder bundles this
                                             into plugin/gebo-ai-assistant/vendor/brain-client.bundle.js
                                             (a build artifact, not checked in — build it before
                                             the sandbox will have anything to load)

gebo.ui/projects/gebo-ai-chat-ui/.../gebo-ai-chat-section-component/
                                             the REAL Angular chat control this plugin's
                                             "Knowledge base" tab is modeled on — when unsure
                                             what endpoint/pattern to use, read this first
                                             rather than guessing (see §5)

gebo.ui/projects/gebo-ai-reusable-ui/src/assets/   Gebo logo/icon source assets (see §7)
```

## 3. Bringing a sandbox up

```
cd dockers/onlyoffice   # or dockers/euro-office
docker compose up -d
```

Then build the plugin's vendor bundle once (skip if `vendor/brain-client.bundle.js` already
exists and nothing in `brain-ai-js-client` changed):

```
cd plugin/gebo-ai-assistant
npm install && npm run build
```

Login: `developer` / `developer` at `http://localhost:4180/welcome/` (onlyoffice) or
`http://localhost:4181/welcome/` (euro-office) — realms `onlyoffice-dev` /
`euro-office-dev`. Keycloak admin console on `keycloak.localtest.me:8081` / `:8082`
(`admin`/`admin`). Ports are deliberately offset so both sandboxes can run at once.

**Known bug, already fixed once — check before assuming a fresh sandbox works:**
`dockers/euro-office/docker-compose.yml`'s plugin volume mounts were copy-pasted from the
onlyoffice compose file and pointed at `/var/www/onlyoffice/documentserver/sdkjs-plugins/...`
— but the Euro-Office image serves from `/var/www/euro-office/documentserver/...` (confirmed
via its nginx config: the `sdkjs-plugins` location block aliases to
`/var/www/euro-office/documentserver/$2$3`). Mounting under the wrong prefix means the files
land somewhere nginx never serves from: the container starts cleanly, `docker compose ps`
looks fine, and every plugin asset 404s. If euro-office plugin assets 404 with the container
otherwise healthy, `docker exec <container> sh -c "ls /var/www/euro-office/documentserver/sdkjs-plugins/"`
is the first thing to check. (The `Data`/log volumes have the same `onlyoffice`→`euro-office`
prefix trap.)

## 4. Plugin anatomy (`plugin/gebo-ai-assistant/`)

- **`config.json`** — ONLYOFFICE plugin manifest. `variations[0].url` is the entry HTML
  page (normally just `"index.html"`). `variations[0].icons` is `["resources/icon.png",
  "resources/icon@2x.png"]` — see §7 for how those were generated.
  **Never put a query string in `url`**: ONLYOFFICE's plugin loader appends its own
  `?lang=en-EN&theme-type=light` with a bare `?`, not `&`, with no check for an existing
  `?` — `index.html?geboBaseUrl=...` becomes `index.html?geboBaseUrl=...?lang=en-EN&...`
  and everything after the embedded `?` gets silently absorbed into the `geboBaseUrl`
  value. If you need to override config at load time, change `app.js`'s hardcoded default
  instead (temporarily, for testing) — never encode it into `config.json`'s `url`.
- **`index.html` / `style.css`** — plain DOM, no framework. Two tabs: `panel-doc` ("This
  document" — read/search/elaborate/insert against the current selection) and `panel-kb`
  ("Knowledge base" — an ordinary out-of-document RAG chat).
- **`auth.js`** — `window.GeboAuthBridge.getAccessToken()`: same-origin
  `fetch('/oauth2/auth', {credentials:'include'})`, reads the token back from the
  `X-Auth-Request-Access-Token` response header (oauth2-proxy config needs
  `set_xauthrequest=true` + `pass_access_token=true`, already set in both sandboxes' cfg).
- **`app.js`** — all the logic; see §5 for the specific backend calls it makes and why.
- **`vendor/brain-client.bundle.js`** — esbuild bundle of the generated
  `brain-ai-js-client` stub, exposed as `window.BrainClient`. Build artifact (§3).

## 5. Backend calls this plugin makes, and why (don't rediscover these by guessing)

| Plugin feature | Calls | Notes |
|---|---|---|
| Load chat profile dropdown | `GeboRagChatControllerApi.getChatProfiles()` → `GET api/users/GeboChatController/profiles` | **Not** `GeboChatProfileLookupControllerApi.getAllChatProfileConfigurationLoookup` — that paginated lookup wraps results in a `Page` and 500s (`HttpMessageNotWritableException`) serializing an *empty* page, i.e. exactly the case on a fresh install. `getChatProfiles()` returns a plain `List<GChatProfileConfiguration>`, mirrors what `gebo-ai-rag-chat-section.component.ts` actually calls, and has no such bug. |
| LLM-setup-incomplete warning | `GeboUserChatsControllerApi.isMinimalLLMSSetupDone()` → `GET api/users/GeboUserChatsController/isMinimalLLMSSetupDone` | Mirrors `gebo-ai-rag-chat-section.component.ts`'s `llmsSetupDone` check. `app.js`'s `checkLlmsSetup()` shows `#llmsWarning` and disables Search/Elaborate/Send when it returns `false`. Defaults to `true` while the check is in flight (same reasoning as the Angular component: avoid flashing the warning). |
| "Elaborate" | `GeboChatControllerApi.chat()` (`GeboDirectModelChatController/chat`) | Direct model chat, not RAG — the selection + optional instructions become the query. |
| "Knowledge base" chat send | `GeboChatPipelinesControllerApi.executeDefaultChatPipeline()` | **Not** the simpler `GeboChatController/ragChat`. Matches
`gebo-ai-reusable-chat.component.ts`'s `callReactiveChat()`, which routes through
`streamChatPipeline` for ragsystem chat and leaves `streamRagChat`/`streamRagResponse`
commented out/unused. When you're unsure which of two similar-looking endpoints a UI
surface should use, grep the real Angular component first — don't assume the
simpler-looking one is right. |
| "Search related content" | `GeboUserKnowledgeBaseSemanticSearchControllerApi.semanticSearch()` | Needs `state.currentKnowledgeBaseCodes` non-empty (loaded via `getVisibleKnowledgeBasesByProfileCode`) — correctly, gracefully reports "no visible knowledge bases" rather than erroring when a profile has none. |
| Read/insert selection | `window.Asc.plugin.executeMethod("GetSelectedText" / "PasteText", ...)` | ONLYOFFICE's own plugin JS API, not a Gebo.ai call. `PasteText` inserts plain text — markdown from an LLM response (`**bold**`, `### heading`) is NOT converted to rich-text formatting, it lands as literal characters. That's an accepted current limitation, not a bug to silently "fix" without being asked. |

## 6. Auth/security config needed on the Gebo.ai backend side

For the plugin to authenticate against a *real* Gebo.ai backend (monolith or brain) using
the sandbox's Keycloak session, that backend needs an `ai.gebo.security.oauth2configs`
entry trusting the sandbox's realm:

```yaml
ai.gebo.security:
  loginPolicy: TRUST_EVERY_OAUTH_IDENTITY   # only if you want auto-provisioning; see below
  oauth2configs:
    - registrationId: keycloakBearer
      provider: oauth2_generic
      configurationType: AUTHENTICATION   # singular field - "configurationTypes" (plural) silently binds to null and gets excluded
      client:
        clientId: onlyoffice-plugin-dev          # or euro-office-plugin-dev
        secret: 42f277af-7919-4edb-a686-a2f40ec4dc87   # see keycloak/realm-export.json for the real value
      providerConfig:
        provider: oauth2_generic
        authorizationUri: http://keycloak.localtest.me:8081/realms/onlyoffice-dev/protocol/openid-connect/auth
        tokenUri: http://keycloak.localtest.me:8081/realms/onlyoffice-dev/protocol/openid-connect/token
        userInfoUri: http://keycloak.localtest.me:8081/realms/onlyoffice-dev/protocol/openid-connect/userinfo
        issuerUri: http://keycloak.localtest.me:8081/realms/onlyoffice-dev
        userNameAttribute: email
```

Plus two non-obvious requirements:

1. **`X-AuthType: OAUTH2` header on every request** — `app.js`'s `initClients()` already
   sets this. Gebo's shared security dispatch (`SecurityHeaderUtil`/
   `GHttpRequestAuthenticationManagerResolverImpl`) picks LOCAL_JWT vs external-OAuth2
   validation from this header; a Keycloak token with no header 401s as if it were a
   malformed Gebo token (or, if `AUTO` sentinel support has landed, gets sniffed from the
   token shape instead — check current `SecurityHeaderUtil` behavior before assuming).
2. **Request the token from the exact same hostname as `issuerUri`** —
   `keycloak.localtest.me`, never `localhost`, even though both resolve to 127.0.0.1.
   Keycloak stamps the `iss` claim from whatever Host header the token request used; a
   mismatch against the configured `issuerUri` fails signature/issuer validation even
   against the identical server.
3. **Auto-provisioning is opt-in and off by default.** Without
   `loginPolicy: TRUST_EVERY_OAUTH_IDENTITY`, the resource-server flow only resolves an
   *existing* Gebo user by the token's `email` claim — it never creates one, on either the
   interactive login-redirect path or the token-verification path. With it, an unknown
   identity gets auto-provisioned through `IGSecurityDirectory.createUserIfNotExists`.

## 7. Toolbar icon

Standard ONLYOFFICE plugin toolbar icon size, confirmed by inspecting a built-in plugin's
own `resources/light/icon.png` / `icon@2x.png`: **28×28** (1x) and **56×56** (2x), referenced
from `config.json` as `"icons": ["resources/icon.png", "resources/icon@2x.png"]`.

Source: `gebo.ui/projects/gebo-ai-reusable-ui/src/assets/Gebo.png` — the full lockup
(2000×500) is the circular Gebo rune mark + "Gebo.ai" wordmark side by side. **Don't use
`favicon-32x32.png`** for this — it's the same mark but tiny and soft; crop the icon-only
region out of the high-res `Gebo.png` instead (roughly the left square ~420×500 px) and
downsample *that* to 28/56px for a crisp result. `favicon-32x32.png`/`favicon.ico` (max
embedded size 48×48) are both lower quality than cropping from the full-res lockup.

## 8. Testing methodologies

Two deliberately different approaches, pick based on what you're testing:

### 8a. Lightweight — standalone plugin page, no real document

For iterating on `app.js` logic (API wiring, error handling, state management) without
editor overhead. Navigate directly to
`http://localhost:4180/sdkjs-plugins/gebo-ai-assistant/index.html?geboBaseUrl=<backend>`.

The browser aggressively caches `index.html`/`app.js`/`style.css` at this path — after
editing any of them, a plain reload often still serves the stale version. Force it:

```js
const code = await (await fetch('./app.js', {cache: 'no-store'})).text();
(0, eval)(code);
await window.main();
```

(Same pattern for `index.html` via `document.open(); document.write(html); document.close();`
if you need the DOM itself refreshed too — script tags injected this way still execute.)
This also means `window.state`, `window.main`, `window.checkLlmsSetup` etc. are directly
pokeable from `javascript_tool` for assertions/mocking (e.g. temporarily stubbing
`state.userChatsApi.isMinimalLLMSSetupDone = async () => false` to verify the warning path
without tearing down real LLM config).

**This mode cannot exercise `Asc.plugin.executeMethod`** (no real editor) — "Read
selection"/"Insert into document" need §8b.

### 8b. Full — real editor session via a same-origin JWT-signed harness

Needed for anything touching document selection/paste, the toolbar icon, or genuine
end-to-end auth flow through oauth2-proxy.

**Must be same-origin with the document server** (i.e. served through
`localhost:4180`/`4181`, not some other dev-server port). A harness page on a different
origin embedding the ONLYOFFICE editor iframe (which itself embeds the plugin iframe) makes
the oauth2-proxy session cookie **third-party** from the browser's point of view — default
`SameSite=Lax` blocks it from being sent in that nested cross-site subresource context, and
`auth.js`'s same-origin fetch silently gets no cookie, no token, and the plugin then fails
auth with `Not authenticated with the SSO gate`. Fix: write the harness file *into* the
plugin's own served folder, e.g. `plugin/gebo-ai-assistant/harness-test.html`
(gitignored/scratch — delete when done), and load it via
`http://localhost:4180/sdkjs-plugins/gebo-ai-assistant/harness-test.html`.

Harness template (`docs.api.js` + a JWT-signed editor config against a blank docx):

```html
<!DOCTYPE html><html><head><meta charset="utf-8">
<style>html,body{margin:0;padding:0;height:100%;} #placeholder{width:100%;height:100vh;}</style>
</head><body>
<div id="placeholder"></div>
<script src="/web-apps/apps/api/documents/api.js"></script>
<script>
  var config = { /* see below */ };
  window.docEditor = new DocsAPI.DocEditor('placeholder', config);
</script>
</body></html>
```

Config shape (JWT is required — both sandboxes set `JWT_ENABLED: "true"`):

```js
{
  document: {
    fileType: 'docx',
    key: 'unique-key-' + Date.now(),   // MUST change on every reload, see gotcha below
    title: 'Test.docx',
    url: 'http://host.docker.internal:8899/gebo-test.docx',   // reachable from INSIDE the container
    permissions: { edit: true, download: true, print: true }
  },
  documentType: 'word',
  editorConfig: { mode: 'edit', lang: 'en', user: { id: 'x', name: 'Tester' },
                   customization: { autosave: false, forcesave: false } },
  token: /* HS256 JWT of the whole object above (minus `token`), signed with the
            sandbox's JWT_SECRET from its docker-compose.yml */
}
```

Sign with plain Node `crypto` (no library needed) — `HS256`, header
`{alg:'HS256',typ:'JWT'}`, base64url each segment, HMAC-SHA256 over `header.payload`.
`JWT_SECRET` differs per sandbox — read it from that sandbox's `docker-compose.yml`, don't
reuse the other one's.

For a blank starting document, copy the image's own blank template out once:
`docker cp <documentserver-container>:/var/www/onlyoffice/documentserver/document-templates/new/en-US/new.docx <scratch>/test.docx`
(for euro-office, first check whether that path is `onlyoffice` or `euro-office` prefixed —
see the §3 gotcha) and serve it from a trivial local static file server reachable from
inside the container via `host.docker.internal:<port>` (Docker Desktop resolves this to the
host automatically; verify with `docker exec <container> curl host.docker.internal:<port>/...`).

**Caching/staleness gotchas specific to this flow, all hit for real this session:**

- **`config.json` changes require a `docker compose restart documentserver`.** Unlike
  `app.js`/`index.html`/`style.css` (served live, no restart needed), the plugin manifest
  gets baked into a hashed static bundle at container *startup* (`Generating js caches...`
  in the startup log) — editing `config.json` on the host has zero effect until restart.
- **A brand-new file (never seen before by nginx) can 404 even though it's visibly present
  in the container's filesystem** (`docker exec ... ls` shows it) **until the container is
  restarted.** Editing an *existing* file doesn't have this problem. Observed on both
  sandboxes; root cause not fully pinned down (likely an nginx open-file/dentry cache
  interacting with Docker Desktop's Windows bind-mount propagation) — restart is the
  reliable fix, don't spend time trying to work around it.
- **Reusing the same `document.key` across reloads reuses a server-side cached editing
  session** — including a stale/old plugin manifest reference — even after fixing
  `config.json` and restarting. Always mint a fresh `document.key` (e.g. a new
  `Date.now()` suffix) when you need a guaranteed-fresh plugin load, not just a page
  reload.
- **Open a genuinely new browser tab per test iteration, don't reload/reuse one.** Each
  full ONLYOFFICE editor session is heavy; 5+ accumulated tabs reliably produced frozen
  renderers / `Page.captureScreenshot` CDP timeouts. Closing old tabs can itself hang on an
  "unsaved changes" `beforeunload` dialog (harmless test document, not real data) — a
  `computer` `key` action with `Return` while the navigate() call is pending dismisses it;
  if a tab is still stuck, just abandon it and open a fresh one rather than fighting it.
- **Keycloak access tokens default to a 5-minute lifespan** — enough to get flagged by a
  long interactive test session. Bump it via Keycloak's admin REST API for smoother
  testing (throwaway dev realm only):
  ```bash
  ADMIN_TOKEN=$(curl -s -X POST "http://keycloak.localtest.me:8081/realms/master/protocol/openid-connect/token" \
    -d "client_id=admin-cli" -d "username=admin" -d "password=admin" -d "grant_type=password" | <extract .access_token>)
  curl -s -X PUT "http://keycloak.localtest.me:8081/admin/realms/onlyoffice-dev" \
    -H "Authorization: Bearer $ADMIN_TOKEN" -H "Content-Type: application/json" \
    -d '{"accessTokenLifespan": 3600}'
  ```
- **Recreating the Keycloak container invalidates existing oauth2-proxy sessions** — new
  container = new ephemeral signing keys (dev-mode Keycloak keeps them in-memory), so an
  old session's cached token fails signature validation against the new JWKS. Fix:
  navigate to `/oauth2/sign_out` then log back in (often silent/no-credentials-needed if
  Keycloak's own browser SSO session is still valid).

## 9. Standing up a real backend to test against

The plugin's default `geboBaseUrl` points at a standalone `brain` microservice
(`http://localhost:13001/brain`), which this sandbox does not itself run. To test against
a **monolith** instead (no `/brain` prefix, root URL): needs `GEBO_HOME` /
`GEBO_WORK_DIRECTORY` / `GEBO_LOG_BASE` set (`Main` aborts without `GEBO_HOME`; background
shells don't inherit these — pass as `-D` flags), a throwaway Mongo DB
(`ai.gebo.mongodb.databaseName`), and the `oauth2configs` block from §6. See
`.claude/skills/gebo-backend-test/SKILL.md` for the general run recipe.

Two more things worth knowing if the test goes further than auth:

- **A default RAG chat profile (`default-rag-chat-profile`) is created unconditionally**
  by `DefaultChatProfileInitializationService` (`gebo.llms.setup`), a
  `@Scheduled(initialDelay=20000)` bean that runs on every startup impersonating the
  platform's own *system* identity — **not** tied to admin/user registration at all,
  despite `SystemInitializationAdminService`'s silent local-admin bootstrap
  (`ai.gebo.sysinit.admin.config.adminUsername`/`adminPassword`, same 20s delay
  convention) running around the same time and looking related.
- **The shared local Qdrant dev container requires an API key**
  (`QDRANT__SERVICE__API_KEY` env var on the container) that the monolith's default
  `ai.gebo.vectorstore.qdrant` config doesn't set — creating an embedding model without it
  fails with `UNAUTHENTICATED: Must provide an API key or an Authorization bearer token`
  from the Qdrant gRPC client. Fix: set `ai.gebo.vectorstore.qdrant.apiKey` to match
  whatever's on the running container (`docker inspect <qdrant-container> --format
  '{{range .Config.Env}}{{println .}}{{end}}'` if unknown).

To reach the **Fast LLMs Setup wizard** UI (needed to configure a real chat+embedding
model before Elaborate/Search/KB-chat can return anything but a 500): the monolith jar
built for backend testing typically has no bundled Angular UI. Run `gebo.ui`'s own dev
server against it instead:

```bash
cd gebo.ui
npx ng serve --no-hmr --proxy-config proxy-dev.conf.json   # proxies /api/* -> localhost:12999
```

Then `http://localhost:4200/ui/login` (credentials = whatever `ai.gebo.sysinit.admin.config`
bootstrapped) → `http://localhost:4200/ui/admin-setup` → "Large language models setup".

## 10. Windows/git-bash path gotchas (bit repeatedly this session)

- `node -e "...$SCRATCH..."` where `$SCRATCH` is a git-bash-style `/c/Users/...` path:
  Node (a native Windows binary) doesn't understand that notation and prepends the cwd
  drive root, producing garbage like `C:\c\Users\...`. Use PowerShell for anything that
  needs a real Windows path, or pass paths as `C:/Users/...` explicitly.
- `docker exec <container> cat /absolute/unix/path` from git-bash: git-bash's automatic
  POSIX-path-to-Windows-path conversion mangles the argument before it reaches `docker`.
  Wrap it: `docker exec <container> sh -c "cat /absolute/unix/path"`.
- `--spring.config.additional-location=file:/c/Users/...` has the same problem — use
  `file:C:/Users/...` (forward slashes fine, but must start with the drive letter, not
  `/c/`).

## Report

When this skill informs a plugin-modification task, summarize: which sandbox(es) touched,
which testing mode used (§8a/§8b), whether a container restart was needed and why, and any
new gotcha discovered that isn't already listed above (append it here if so — this file is
meant to grow).
