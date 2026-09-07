# ONLYOFFICE sandbox for the Angular "Gebo AI Assistant" plugin

Runs the **new** Angular office plugin (`../../../Gebo.ai-offices-plugin`) inside a
Keycloak‑gated ONLYOFFICE Document Server, wired to a real Gebo.ai backend — so
the whole platform + plugin flow (SSO → `complete()` → chat pipeline → document
selection in / suggestion out) can be exercised end to end.

It mirrors `dockers/onlyoffice/docker-compose.yml`, but serves the plugin's built
output instead of the plain‑JS `plugin/gebo-ai-assistant`, and layers two
sandbox files over it: `plugin-overrides/runtime-config.js` (backend + Keycloak
`authProvider`) and `plugin-overrides/backends.json` (backend URL).

## 1. Build the plugin

```
cd ../../../Gebo.ai-offices-plugin
npm ci && npm run build          # produces dist/office-plugin/browser (mounted by compose)
```

## 2. Configure a Gebo.ai backend (the "actual platform")

**This compose already wires all of the backend configuration below** into the
`gebo.ai` service via `SPRING_APPLICATION_JSON` (CORS origin for `:4180`, the
`onlyoffice-dev` OAuth2 resource-server config, `loginPolicy` and the initial
admin account) — you do **not** need to edit `dockers/gebo.ai/config/application.yml`
by hand. The section below documents *what* it sets and why, so it can be adapted
for a different backend. The one hard requirement the compose cannot supply is the
image itself (see the "stale image" gotcha).

The plugin runs in the browser at `http://localhost:4180` and calls the backend
from `backends.json` (default `http://localhost:12999`). That backend must:

1. **Allow CORS from `http://localhost:4180`** — add it to
   `ai.gebo.security.cors.allowedOrigins`.
2. **Enable the office-assistant chat pipeline.** It is `@ConditionalOnProperty`
   and it *reuses* the standard agents network, so BOTH flags are required — with
   only `officeplugin.enabled` the context fails to start (`officeAgentsNetwork…`
   has no `StandardAgentsInitialization` bean):

   ```
   ai.gebo.agents.standard.enabled: true
   ai.gebo.officeplugin.enabled:    true
   ```

3. **Trust this sandbox's Keycloak realm** as an OAuth2 resource server so the
   token the plugin sends (with `X-AuthType: OAUTH2`) authenticates. Relaxed
   binding means the flat `ai.gebo.security.*` keys in `SPRING_APPLICATION_JSON`
   bind to exactly the same properties as this nested YAML:

```yaml
ai.gebo.security:
  # optional: auto-provision an unknown identity by its email claim
  loginPolicy: TRUST_EVERY_OAUTH_IDENTITY
  cors:
    allowedOrigins: http://localhost:12999,http://localhost:4200,http://localhost:4180
  oauth2configs:
    - registrationId: keycloakOnlyofficeBearer
      description: ONLYOFFICE sandbox Keycloak resource server
      provider: keycloak                       # dedicated Keycloak provider; oauth2_generic
                                               # also validates JWTs (issuer/JWKS only) but is
                                               # a less accurate description of the IdP
      configurationType: AUTHENTICATION      # singular; "configurationTypes" binds to null
      client:
        clientId: onlyoffice-plugin-dev
        secret: 42f277af-7919-4edb-a686-a2f40ec4dc87   # keycloak/realm-export.json
      providerConfig:
        provider: keycloak
        authorizationUri: http://keycloak.localtest.me:8081/realms/onlyoffice-dev/protocol/openid-connect/auth
        tokenUri: http://keycloak.localtest.me:8081/realms/onlyoffice-dev/protocol/openid-connect/token
        userInfoUri: http://keycloak.localtest.me:8081/realms/onlyoffice-dev/protocol/openid-connect/userinfo
        issuerUri: http://keycloak.localtest.me:8081/realms/onlyoffice-dev
        userNameAttribute: email
```

Declaring this `oauth2config` also lights up an interactive "Sign in with
Keycloak" button on the Gebo.ai UI (`http://localhost:12999`), whose Spring
callback is `{baseUrl}/login/oauth2/code/{registrationId}` — here
`http://localhost:12999/login/oauth2/code/keycloakOnlyofficeBearer`. That URI
(and `http://localhost:12999`) is therefore added to the `onlyoffice-plugin-dev`
client's **Valid Redirect URIs / Web Origins** in `keycloak/realm-export.json`;
without it the graphical login fails at Keycloak with *Invalid parameter:
redirect_uri*. The plugin's own bearer-token path does not use this — it is only
for logging into the platform UI directly.

An **admin account** is provisioned on first boot from
`ai.gebo.sysinit.admin.config.{adminUsername,adminPassword}` (set in the compose
to `admin@gebo.ai` / `Adm1n-sandbox-2026`) so the platform is reachable via a
local login for setup, independently of the Keycloak SSO identities.

A real chat/embedding model must also be configured (Fast LLMs Setup) for the
pipeline to return content — see `.claude/skills/gebo-backend-test` /
`gebo-ai-local-install`. Until then `isMinimalLLMSSetupDone` returns `false` and
the chat produces no suggestion.

### Building the backend image (the "stale image" gotcha)

The `geboai/gebo.ai` image must be built from source that **contains the OAuth2
JWT resource-server auto-provisioning** (commits from 2026-09-02/03:
`Provision/sync OAuth2 users on the resource-server path…`,
`Restructure resource-server provisioning to trigger only on validated-token
unknown-user`). Without it, a validated Keycloak token whose email has no local
Gebo user is rejected with `401 "User not found with email : …"` **even when
`loginPolicy: TRUST_EVERY_OAUTH_IDENTITY` is correctly bound** — the provisioning
code simply is not there. An image predating those commits looks perfectly
healthy and fails only at this exact point. Rebuild before blaming config:

```
mvn -f gebo.apps.parent/gebo.ai.app/pom.xml -P bootables package -DskipTests
bash dockers/gebo.ai/create-image.sh
docker compose up -d --force-recreate --no-deps gebo.ai
```

Confirm provisioning fired by looking for an `oauth2IdentityProvision` /
`userAutoProvision` (`created:true`) event in
`/opt/gebo.ai/logs/security-log.jsonl` inside the container.

## 3. Bring the sandbox up

```
docker compose up -d
```

- Open `http://localhost:4180/welcome/` → Keycloak login, `developer` / `developer`
  (realm `onlyoffice-dev`).
- Open a document, then the **Plugins → Gebo AI Assistant** panel.
- The plugin reads the Keycloak token from oauth2‑proxy, exchanges it via
  `SecurityHeaderDataCompletionController.complete()`, binds a chat to the current
  document, captures the current selection automatically, and shows the
  "Assistant suggestion" tab when the pipeline returns a document part.

Do **not** run this alongside `dockers/onlyoffice` — both bind ports 4180/8081.

## Gotchas

- `config.json` changes require `docker compose restart documentserver` (the
  manifest is baked into a hashed bundle at container startup); `runtime-config.js`
  / `backends.json` / `index.html` / `main-*.js` are served live.
- After rebuilding the plugin (`npm run build`), the mounted `dist/.../browser`
  updates live, but a brand‑new hashed `main-*.js` filename can 404 until a
  `docker compose restart documentserver`.
- Keycloak access tokens default to a 5‑minute lifespan; bump
  `accessTokenLifespan` on the `onlyoffice-dev` realm for longer test sessions.
