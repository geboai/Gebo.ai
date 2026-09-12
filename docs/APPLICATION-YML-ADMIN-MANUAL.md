# Gebo.ai — `application.yml` Admin Configuration Manual

Reference for every property Gebo.ai's **monolith** (`gebo.ai.app`) reads from `application.yml`
(or the equivalent `application.properties` / environment-variable form). Applies to the
`geboai/gebo.ai` Docker image and on-premise/binary installs. It complements, and does not
replace, `docs/gebo-ai-manual-tech-configuration.pdf` (installation/infrastructure setup); this
document is the exhaustive property-by-property reference that manual doesn't fully cover.

Every property below was verified against the `@ConfigurationProperties` class (or `@Value`
binding) that actually reads it — not just copied from a sample file. Where the shipped sample
config contains a key that no Java code binds to, it's called out explicitly in
[§22 Dead / legacy keys](#22-dead--legacy-keys-in-the-shipped-file) instead of documented as if it worked.

---

## 1. Where to edit configuration

| Deployment | File | Notes |
|---|---|---|
| Docker / Docker Compose | `/opt/gebo.ai/config/application.yml` inside the container | Baked in from `dockers/gebo.ai/config/application.yml` at image build time (`Dockerfile`: `COPY config/application.yml /opt/gebo.ai/config`), but the directory is declared `VOLUME /opt/gebo.ai/config` — bind-mount your own file over it to override without rebuilding the image. |
| Linux binary / on-premise | `/etc/gebo-ai/application.properties` | Same keys, `.properties` dotted syntax (`key=value`) instead of YAML. |
| Windows binary / on-premise | `C:\Program Files\GeboAI\app\instance\config\application.properties` | Same as above. |
| Built-in defaults | `gebo.apps.parent/gebo.ai.app/src/main/resources/application.yml` (compiled into the jar) | The lowest-priority layer — any key **not** set in your external file falls back to this. Several properties (e.g. all of `management.*`, `springdoc.api-docs.version`) only exist here and are *not* present in the shipped Docker config; add them to your external file to override. |

**Precedence** (highest wins): environment variable → external `application.yml`/`.properties`
next to the jar or in the mounted `/opt/gebo.ai/config` → built-in defaults compiled into the jar.
Any property can also be set as an environment variable using Spring Boot's relaxed-binding rules
(uppercase, `.`/`-` → `_`) — e.g. `management.otlp.tracing.endpoint` becomes
`MANAGEMENT_OTLP_TRACING_ENDPOINT`, exactly as `dockers/docker-compose-deploy/docker-compose.observability.yml` already does.

**A configuration change requires a restart** of the `gebo.ai` process/container — nothing in this
document is hot-reloaded. Two standalone settings are supplied separately, not through
`application.yml`: the `GEBO_HOME` and `GEBO_WORK_DIRECTORY` environment variables (application
home / working-file storage — see the PDF manual, §"Gebo.ai detailed configuration").

---

## 2. How to read the tables

- **Property** — the exact YAML key path as it appears in `application.yml`.
- **Type** — Java type behind it.
- **Shipped default** — the value in `dockers/gebo.ai/config/application.yml` (the Docker image's
  external config) if set there; otherwise the built-in jar default, marked *(baked-in only)*.
- 🔒 marks a value that **must be rotated before production use** (secret/credential/dummy key).

---

## 3. Server & HTTP

| Property | Type | Shipped default | Description |
|---|---|---|---|
| `server.port` | int | `12999` | HTTP listen port. |
| `server.servlet.contextPath` | String | `/` | Base path the app is served under. |
| `server.compression.enabled` | boolean | `true` | Enables gzip response compression. |
| `server.compression.mime-types` | csv | `text/html,text/xml,text/plain,text/css,text/javascript,application/javascript,application/json` | MIME types eligible for compression. |
| `server.compression.min-response-size` | bytes | `1024` | Minimum response size before compressing. |
| `server.http2.enabled` | boolean | `true` | Enables HTTP/2. |
| `spring.servlet.multipart.enabled` | boolean | `true` | Enables multipart (file upload) request handling. |
| `spring.servlet.multipart.maxFileSize` | size | `100MB` | Max size of a single uploaded file (chat/doc uploads). |
| `spring.servlet.multipart.maxRequestSize` | size | `100MB` | Max total size of a multipart request. |
| `spring.application.name` | String | `gebo-ai-monolith` *(baked-in only)* | Service name reported to tracing/metrics. |
| `springdoc.api-docs.version` | String | `OPENAPI_3_0` *(baked-in only)* | OpenAPI spec version emitted by Swagger (only relevant with the `swagger-on` build profile). |

## 4. Logging

| Property | Type | Shipped default | Description |
|---|---|---|---|
| `logging.level.root` | level | `INFO` | Root log level. |
| `logging.level.org.springframework` | level | `INFO` | Spring framework log level. |
| `logging.level.org.springframework.web` | level | `INFO` | Spring MVC/WebFlux log level. |
| `logging.level.org.springframework.core` | level | `INFO` | Spring core log level. |
| `logging.level.org.springframework.core.codec` | level | `INFO` | Request/response codec log level (verbose at DEBUG — large payload bodies get logged). |

Any other package can be added the same way, e.g. `logging.level.ai.gebo: DEBUG`.

## 5. Observability — Actuator, Micrometer, Tracing

*(Not present in the shipped Docker `config/application.yml` — these are built-in jar defaults;
add them to your external file to change. See the README's "Observability & monitoring" section
for how this plugs into the bundled Grafana/Prometheus/Tempo stack.)*

| Property | Type | Default | Description |
|---|---|---|---|
| `management.endpoints.web.exposure.include` | csv | `health,prometheus,metrics` | Which Actuator endpoints are exposed over HTTP. |
| `management.prometheus.metrics.export.enabled` | boolean | `true` | Enables the `/actuator/prometheus` scrape endpoint. |
| `management.tracing.sampling.probability` | double 0–1 | `1.0` (env-overridable via `MANAGEMENT_TRACING_SAMPLING_PROBABILITY`) | Fraction of requests traced. Lower this in high-traffic production (e.g. `0.1`). |
| `management.otlp.tracing.endpoint` | URL | `http://localhost:4318/v1/traces` (env-overridable via `MANAGEMENT_OTLP_TRACING_ENDPOINT`) | Where OTLP traces are exported — the bundled `otel-collector` in Docker Compose. |

## 6. Security — authentication, tokens, CORS, SSO

Bound by `GeboSecurityConfig` (`ai.gebo.security`) except `cors.allowedOrigins`, which is a
separate, **required** `@Value` binding (`WebMvcConfig`) — the app fails to start without it.

| Property | Type | Shipped default | Description |
|---|---|---|---|
| `ai.gebo.security.auth.tokenSecret` | String | 🔒 shipped with a placeholder value | HMAC secret signing local JWTs. **Must be changed** to a unique high-entropy value before production. |
| `ai.gebo.security.auth.tokenExpirationMsec` | long (ms) | `1800000` (30 min) | User session JWT lifetime. |
| `ai.gebo.security.cors.allowedOrigins` | String\[] (comma-separated) | `http://localhost:12999,http://localhost:4200` | Allowed CORS origins for the Angular front end / any external client. **Required** — startup fails if unset. |
| `ai.gebo.security.systemUser.username` | String | `heimdall@bifrost.gebo.ai` *(baked-in only)* | Identity used for background/no-user-thread calls (scheduler jobs, LLM client init, MCP reconnects). |
| `ai.gebo.security.systemUser.roles` | List\<String> | `[SYSTEM, ADMIN]` *(baked-in only)* | Roles granted to the system identity. |
| `ai.gebo.security.systemUser.tokenExpirationMsec` | long (ms) | `300000` *(baked-in only)* | Lifetime of the short-lived system-identity token. |
| `ai.gebo.security.loginPolicy` | enum | `REQUIRE_INVITATION` *(baked-in only)* | `TRUST_EVERY_OAUTH_IDENTITY` \| `REQUIRE_INVITATION` \| `USER_SELF_REGISTERS` — controls whether a successful OAuth2/OIDC login alone is enough to create an account. |
| `ai.gebo.security.oauth2UISetupEnabled` | boolean | `true` *(baked-in only)* | Shows/hides OAuth2 provider setup in the admin UI. |
| `ai.gebo.security.oauth2LoginEnabled` | boolean | `true` *(baked-in only)* | Master switch for OAuth2/OIDC login. |
| `ai.gebo.security.oauth2ResourceServerEnabled` | boolean | `true` *(baked-in only)* | Enables validating bearer tokens issued by external OIDC providers (resource-server mode). |
| `ai.gebo.security.useAcl` | boolean | `false` *(baked-in only)* | Enables fine-grained ACL enforcement (chatbot/knowledge-base per-user/group access grants). |

### 6.1 `ai.gebo.security.oauth2configs` — SSO provider registrations (list)

Each entry registers one SSO provider (Microsoft Entra, Google, AWS Cognito, KeyCloak/generic
OIDC, LDAP, or a local account). This is the design-time equivalent of what the admin UI's OAuth2
provider setup screen writes; **most installs configure this through the UI instead of YAML**, but
it can be pre-seeded here.

| Field | Type | Description |
|---|---|---|
| `registrationId` | String | Unique ID for this provider registration. |
| `description` | String | Admin-facing label. |
| `provider` | enum (`AuthProvider`) | `local` \| `google` \| `microsoft` \| `microsoft_multitenant` \| `aws_cognito` \| `oauth2_generic` \| `ldap`. |
| `client.clientId` / `client.secret` 🔒 | String | OAuth2 client credentials. |
| `client.scopes` | List\<String> | Requested OAuth2 scopes. |
| `client.customAttributes` | Map\<String,String> | Provider-specific extras (e.g. `tenantId` for Entra multi-tenant — there is **no** dedicated `tenantId` field, it goes here). |
| `configurationType` | enum (`Oauth2ConfigurationType`) | `AUTHENTICATION` \| `INTEGRATION`. ⚠️ Note the **singular** field name — see §22. |
| `clientAuthMethod` | enum | Token-endpoint auth method. |
| `authGrantType` | enum | Defaults to `AUTHORIZATION_CODE`. |
| `providerConfig.*` | object | Only used when `provider: oauth2_generic` — `authorizationUri`, `tokenUri`, `userInfoUri`, `introspectionUri`, `issuerUri`, `jwkSetUri`, `userNameAttribute`. |

### 6.2 `ai.gebo.secrets.config` — declaratively configured read-only secrets (lists)

> For how these are referenced by a declared content system, and which type each connector
> expects, see
> [`APPLICATION-YML-SYSTEMS-DATASOURCES-CONFIGURATION.md`](APPLICATION-YML-SYSTEMS-DATASOURCES-CONFIGURATION.md).

Secrets normally live encrypted in the store (Mongo, or an external vault) and are created through
the admin UI. This section is the alternative for a deployment that wants a credential to come from
its own configuration — a Kubernetes secret projected into `application.yml`, an env var, a mounted
config file — instead of being typed into the UI and stored.

One **list per secret type**, keyed by the `GeboSecretType` in dashed form. Each entry carries the
metadata a stored secret would have on its record (`code`, `description`, `context-code`) plus the
content itself, nested under `secret`:

```yaml
ai.gebo.secrets.config:
  username-password:
    - code: nightly-ingestion-account      # what the rest of the configuration references
      description: Service account of the nightly ingestion
      context-code: SYSTEMS
      secret:
        username: ingestion
        password: ${INGESTION_PASSWORD}    # env var / projected k8s secret
  token:
    - code: my-llm-api-key
      context-code: LLMS
      secret:
        token: ${SOME_API_KEY}
        user: gebo
  aws-connection:
    - code: aws-main
      context-code: SYSTEMS
      secret:
        access-key-id: ${AWS_ACCESS_KEY_ID}
        secret-access-key: ${AWS_SECRET_ACCESS_KEY}
        region: eu-central-1
```

| Key | Content type | Content fields |
|---|---|---|
| `username-password` | `GeboUsernamePasswordContent` | `username`, `password` (both required) |
| `token` | `GeboTokenContent` | `token`, `user` (both required) |
| `ssh-key` | `GeboSshKeySecretContent` | `email`, `key` (private key), `pub`, `passphrase` |
| `custom-secret` | `GeboCustomSecretContent` | `content`, `content-type`, `custom-content-description` |
| `oauth2-standard` | `GeboOauth2SecretContent` | `provider-name`, `client-id`, `secret` (required), `scopes`, `custom-attributes` |
| `oauth2-google` | `GeboGoogleOauth2SecretContent` | `uid`, `token`, `location`, `project-id`, `scopes` |
| `google-cloud-json-credentials` | `GeboGoogleJsonSecretContent` | `json-content`, `delegated-user` |
| `aws-connection` | `GeboAwsConnectionCredentials` | `access-key-id`, `secret-access-key`, `region` (all required; region as the AWS code, e.g. `eu-central-1`) |

Every entry field: `code` is **required and unique across all the lists**; `description` and
`context-code` are optional but `context-code` is what the admin UI and the connectors filter on, so
a secret declared without one will not appear in any per-context picker.

**What "read-only" means.** A declared secret is resolved **before** the external vault and before
Mongo, so a declared `code` shadows any stored secret of the same code, and the configuration always
wins. In exchange every write path refuses it — create, update and delete alike — with
`A readOnly secret can only being read and not updated/deleted in the implementation of
IGeboSecretsAccessService`. Practical consequences:

- the admin UI can list a declared secret but cannot edit or delete it; rotation means changing the
  configuration and restarting, not a UI action;
- an OAuth2 client secret declared here cannot be rotated by the OAuth2 admin screens either;
- store/vault **migration skips** declared secrets in both directions — they are never copied into
  the vault;
- `OAUTH2_AUTHORIZED_CLIENT` is intentionally not configurable: it holds runtime OAuth2 tokens the
  server refreshes for itself, which a read-only secret could not support.

**Where to put it.** Only the service that owns the secrets store reads this section: the monolith,
or `heimdall.gebo.ai` in a microservices deployment — and in the latter case it belongs in
*heimdall's own* configuration, not the shared one, so the plaintext stays on the one service that
already holds the crypting keys. The other services see declared secrets transparently through the
cluster secrets endpoints, encrypted on the wire exactly like stored ones.

**Misconfiguration fails the startup.** A missing or duplicated `code`, a missing `secret` block, or
a content that violates its own required fields (a `username-password` with no `password`, say) stops
the application from starting. One gap to be aware of: a *mistyped* content property with no required
constraint behind it (`tokenn:` instead of `token:`) binds to nothing and is only reported when the
secret is read — Spring Boot's unknown-property check does not reach inside list elements.

🔒 Everything under this section is a credential in plaintext at rest in your configuration. Prefer
`${ENV_VAR}` placeholders over literals, and treat the file itself as secret material.

## 7. Async execution

| Property | Type | Shipped default | Description |
|---|---|---|---|
| `reactor.schedulers.defaultPoolSize` | int | `16` | Default size of Reactor's parallel scheduler pool (reactive LLM/IO calls). |
| `spring.task.execution.pool.max-size` | int | `16` | Max threads for Spring's general async task executor. |
| `spring.task.execution.pool.queue-capacity` | int | `100` | Queue depth before new tasks are rejected. |
| `spring.task.execution.pool.keep-alive` | duration | `10s` | Idle-thread keep-alive before pool shrinks. |

## 8. LLM providers

Bound by `GeboLLMSConfig` (`ai.gebo.llms.config`) for some flags; the rest are read directly via
`@ConditionalOnProperty` on each provider's auto-configuration (confirmed working even though not
declared as Java fields on the config class — Spring reads them straight from the environment).

| Property | Type | Shipped default | Description |
|---|---|---|---|
| `ai.gebo.llms.config.openAIEnabled` | boolean | `true` | Enables the OpenAI provider (chat, embedding, image, TTS, transcription). |
| `ai.gebo.llms.config.anthropicEnabled` | boolean | `true` | Enables Anthropic Claude. |
| `ai.gebo.llms.config.mistralAIEnabled` | boolean | `true` | Enables MistralAI. |
| `ai.gebo.llms.config.ollamaEnabled` | boolean | `true` | Enables local Ollama-served models. |
| `ai.gebo.llms.config.deepseekEnabled` | boolean | `true` | Enables DeepSeek. |
| `ai.gebo.llms.config.awsBedrockEnabled` | boolean | `false` | Enables AWS Bedrock (Claude, Nova, Llama, Mistral & more via Bedrock). |
| `ai.gebo.llms.config.googleVertexEnabled` | boolean | `false` | Enables Google Vertex AI / Gemini — **experimental**, off by default. |
| `ai.gebo.llms.config.huggingfaceEnabled` | boolean | `false` *(baked-in default `true`, shipped Docker config omits it)* | Enables Hugging Face-hosted models. |
| `ai.gebo.llms.config.azureOpenAIEnabled` | boolean | `true` in shipped config | ⚠️ **Not wired to any provider auto-configuration found in the codebase** — setting it currently has no effect. Kept in the shipped file as reserved/forward-looking. |

xAI/Grok, NVIDIA, Groq, Regolo.ai and OpenRouter.ai have **no individual `*Enabled` flag** — they're
registered generically as OpenAI-API-compatible endpoints (`GenericOpenAICompatibleProvidersConfig`,
sourced from a bundled provider catalog), and any further OpenAI-compatible server is added the same
way through the admin UI, not `application.yml`.

## 9. Vector store

Bound by `GeboAIVectorStoreConfig` (`ai.gebo.vectorstore`).

| Property | Type | Shipped default | Description |
|---|---|---|---|
| `ai.gebo.vectorstore.use` | String, one of `QDRANT`\|`MONGO`\|`REDIS`\|`TEST` | `QDRANT` | Selects the active vector-store backend. |
| `ai.gebo.vectorstore.qdrant.host` | String | `qdrant` (Docker service name) | Qdrant host. |
| `ai.gebo.vectorstore.qdrant.port` | int | `6334` | Qdrant gRPC port. |
| `ai.gebo.vectorstore.qdrant.tls` | boolean | `false` | Enables TLS to Qdrant. |
| `ai.gebo.vectorstore.qdrant.apiKey` | String | 🔒 shipped with a placeholder value | Qdrant API key — rotate before production. |
| `ai.gebo.vectorstore.redis.host` / `.port` / `.username` / `.password` 🔒 | String/int | — (not shipped; set to use Redis instead of Qdrant) | Redis vector-store connection, used only when `use: REDIS`. |

`spring.ai.openai.api-key` / `spring.ai.openai.chat.api-key` ship as literal `DUMMYKEY` — this is a
Spring AI auto-configuration bootstrap requirement (the bean must construct even before an admin
configures a real key through the UI), not a real credential; it's overwritten by the actual
per-provider key configured at runtime. `spring.ai.openai.embedding.options.model` (default
`text-embedding-3-large`) and `spring.ai.openai.chat.options.model` (default `gpt-3.5-turbo-16k`)
are the bootstrap default models before any admin-configured model is selected.

## 10. Chat behavior

Bound by `GeboChatConfigs` (`ai.gebo.chat`).

| Property | Type | Default | Description |
|---|---|---|---|
| `ai.gebo.chat.leaveLastInteractionsOnHistoryConsolidation` | int | `8` | How many recent turns are kept verbatim when chat history is consolidated/summarized. |
| `ai.gebo.chat.historicDocumentRelevancyThreashold` | float | `0.4` | Minimum relevance score for a previously retrieved document to still be considered in history. |
| `ai.gebo.chat.ranges` | List (advanced) | 4 built-in tiers (0 / 8192 / 16000 / 128000 token thresholds) | Context-window budget ratios per model context-length tier. Advanced/internal tuning — not normally overridden; omit to use the built-in tiers. |

**System prompts are not configured under `ai.gebo.chat`** — see [§22 Dead / legacy keys](#22-dead--legacy-keys-in-the-shipped-file):
the real mechanism is `ai.gebo.prompts.library[]` / `ai.gebo.overridden.prompts.library[]`
(`GeboPromptsLibrary`), each entry keyed by a `promptUse` code (many more than
`standard-chat-prompt`/`standard-rag-prompt` are recognized — chat/RAG output, routing-decision,
query-rewriting, chat-agent, history-consolidation, deep-search, Google-search-query-extraction,
and more). In practice, prompts are managed through the admin UI rather than hand-edited in YAML.

## 11. Setup / installation flags

Bound by `GeboConfig` (`ai.gebo.config`).

| Property | Type | Shipped default | Description |
|---|---|---|---|
| `ai.gebo.config.setup` | boolean | `true` | Once the instance has completed first-run setup, the fast-setup wizard REST endpoints are blocked. Leave `true` for a fresh install; the app flips related setup state internally once configured. |
| `ai.gebo.config.setupConfiguresWorkdir` | boolean | `false` | If `true`, the working directory is provisioned/selected interactively via the admin UI (persisted to MongoDB) instead of `GEBO_WORK_DIRECTORY`. |
| `ai.gebo.config.enableCommunityModules` | boolean | `true` | Includes community-tier modules in the effective module set. |
| `ai.gebo.config.customKeyStore` | boolean | `false` | Reserved — no code currently reads this beyond the getter/setter. |
| `ai.gebo.config.clustered` | boolean | `false` *(baked-in only)* | Marks the instance as running in a clustered (microservices/Hazelcast) topology. Not applicable to the monolith. |

## 12. MongoDB

Bound by `MongoConfig` (`ai.gebo.mongodb`) — gates the whole persistence layer.

| Property | Type | Shipped default | Description |
|---|---|---|---|
| `ai.gebo.mongodb.enabled` | boolean | `true` | Required `true` for the app to start. |
| `ai.gebo.mongodb.databaseName` | String | `gebo-ai` | MongoDB database name. |
| `ai.gebo.mongodb.connectionString` | String 🔒 | `mongodb://mongoroot:mongopwd@mongo:27017/gebo-ai?authSource=admin` | Full MongoDB connection string (standard MongoDB URI grammar). Contains the DB password — rotate for production and treat this whole value as a secret. |

## 13. Neo4j (GraphRAG, experimental)

| Property | Type | Shipped default | Description |
|---|---|---|---|
| `ai.gebo.neo4j.enabled` | boolean | `true` (set via `-Dai.gebo.neo4j.enabled=true` JVM flag in Docker Compose) | Master switch for the GraphRAG knowledge-graph feature. |
| `spring.neo4j.uri` | String | `bolt://neo4j:7687` | Neo4j Bolt connection URI. |
| `spring.neo4j.authentication.username` | String | `neo4j` | Neo4j username. |
| `spring.neo4j.authentication.password` | String 🔒 | `neo4jmaster` | Neo4j password — rotate for production. |

## 14. OpenSearch (full-text index)

Bound by `OpenSearchConfig` (`ai.gebo.opensearch`).

| Property | Type | Shipped default | Description |
|---|---|---|---|
| `ai.gebo.opensearch.enabled` | boolean | `true` | Enables full-text indexing/retrieval. |
| `ai.gebo.opensearch.protocol` | enum `http`\|`https` | `https` | Connection protocol. |
| `ai.gebo.opensearch.host` | String | `opensearch` (Docker service name) | OpenSearch host. |
| `ai.gebo.opensearch.port` | int | `9200` | OpenSearch port. |
| `ai.gebo.opensearch.username` | String | `admin` | OpenSearch username. |
| `ai.gebo.opensearch.password` | String 🔒 | shipped with a placeholder value | OpenSearch password — rotate for production. |

## 15. Filesystem connector

Bound by `GeboAiFilesystemsConfig` (`ai.gebo.filesystem`).

| Property | Type | Shipped default | Description |
|---|---|---|---|
| `ai.gebo.filesystem.allowFilesystemSharesUI` | boolean | `false` | Lets admins add/manage filesystem shares from the UI (in addition to/instead of YAML). |
| `ai.gebo.filesystem.shares` | List | one entry: `/opt/gebo.ai/shares`, "Server shared document folder" | Filesystem shares exposed as a document source. Each entry: `absolutePath` (String), `description` (String). *(Note: a `code` key appears in the shipped sample but the binding class has no such field — it's ignored.)* |

## 16. Build & content-management systems

| Property (list) | Fields | Description |
|---|---|---|
| `ai.gebo.node.buildsystems.systems` (`NodesBuildSystemsConfiguration`) | `code`, `buildSystemTypeCode`, `description` | Registers Node/npm-based build pipelines. Implemented type code: `NODE.BUILD.SYSTEM`. |
| `ai.gebo.maven.buildsystems.systems` (`MavenBuildSystemsConfiguration`) | `code`, `buildSystemTypeCode`, `description`, `config.mavenHome`, `config.javaHome` | Registers Maven build pipelines. Implemented type code: `MAVEN.BUILD.SYSTEM`. `mavenHome`/`javaHome` must point to valid local install paths on the host running the build. |
| `ai.gebo.git.config.systems` (`GitSystemsConfig`) | `code`, `contentManagementSystemType`, `description`, `publicAccess`, `defaultIdentityCode` | Registers Git/GitHub/Bitbucket content handlers. Implemented type: `DEFAULT.GIT.CONTENT.HANDLER`. |

These are advanced/integration features (source-code repository ingestion & CI build awareness),
not required for a standard RAG deployment.

> **Full guide with a worked example per handler** — knowledge bases, projects, secrets, systems
> and data sources, how they reference each other, and which secret type each connector expects:
> [`APPLICATION-YML-SYSTEMS-DATASOURCES-CONFIGURATION.md`](APPLICATION-YML-SYSTEMS-DATASOURCES-CONFIGURATION.md).
> The three sections below are the reference tables.

### 16.1 `ai.gebo.<content handler>.systems` — content management systems declared by the configuration

Every content handler that keeps its systems in Mongo also accepts them from this file, under its
own `systems` list. A declared system is the same object the admin screen edits, so it appears in
the admin list beside the stored ones and every read path — ingestion, browsing, search — resolves
it exactly like one created in the UI.

The two sources are combined by `GAbstractContentManagementSystemConfigurationDao`, the
implementation parent of each handler's DAO, which settles what happens when both name the same
system: **the declaration wins**. A stored record whose code is declared here is dropped from the
list and never resolved, so what the file says cannot be displaced by anything written later. A
blank code, or the same code declared twice, fails the startup.

In exchange, the admin write paths refuse a declared code — insert, update and delete alike. A
record written under a code the file declares could only ever be persisted and then ignored, so
changing a declared system means editing this file and restarting, not a UI action.

Credentials are never written here. Each entry carries the *code* of a secret the secrets
management layer holds — declared under `ai.gebo.secrets.config` (§6.2) or created in the admin
UI. `contentManagementSystemType` is implied by the handler and can be omitted; only `ai.gebo.git`,
which registers several handler types, requires it.

Every entry also accepts the fields `GContentManagementSystem` carries: `code` (required),
`description`, `baseUri`, `readonly`, `usedCapabilities`.

| Property (list) | Binding class | Handler-specific fields | Implemented type |
|---|---|---|---|
| `ai.gebo.confluence.systems` | `ConfluenceSystemsConfig` | `confluenceVersion` (`ONPREMISE7X`, `CLOUD`), `secretCode` | `ATLASSIAN-CONFLUENCE` |
| `ai.gebo.jira.systems` | `JiraSystemsConfig` | `secretCode` | `ATLASSIAN-JIRA` |
| `ai.gebo.sharepoint.systems` | `SharepointSystemsConfig` | `sharepointVersion` (`CLOUD_VERSION`, `ONPREMISE2019`, required), `secretCode` (required) | `sharepoint-module` |
| `ai.gebo.webdav.systems` | `WebdavSystemsConfig` | `webdavAuthType` (`NONE`, `BASIC`, `DIGEST`, `NTLM`, `BEARER_TOKEN`), `secretCode` | `WEBDAB-CMS` |
| `ai.gebo.awss3.systems` | `AwsS3SystemsConfig` | `awsEndpoint`, `s3SecretCode` | `aws-s3-handler` |
| `ai.gebo.googleworkspace.systems` | `GoogleDriveSystemsConfig` | `driveAccessSecret` | `google-drive-handler` |

Each list is `@Validated`: a declaration that violates its own model's constraints — a SharePoint
system with no `secretCode` or no `sharepointVersion`, say — fails the startup rather than the
first connection.

```yaml
ai.gebo.sharepoint:
  systems:
    - code: corporate-sharepoint
      description: Corporate SharePoint Online
      baseUri: https://example.sharepoint.com
      sharepointVersion: CLOUD_VERSION
      secretCode: msgraph-application
```

### 16.2 `ai.gebo.<content handler>.datasources` — data sources declared by the configuration

Four handlers also accept their **data sources** (project endpoints) from this file: WebDAV,
AWS S3, SharePoint — OneDrive drives only — and Google Drive. A declared data source is the same
object the admin screen edits, appears in the project's source list beside the stored ones, and is
resolved by every read path through the same DAO.

Unlike the systems of §16.1, which are served straight from the configuration, a declared data
source is **written into the module's endpoint repository at startup**. It has to be: publishing,
the central scheduler and `JobLauncherController` all resolve an endpoint through
`IGPersistentObjectManager` — by `GObjectRef`, or through the flattened
`GCentralizedProjectEndpoint` a reschedule carries — and none of those paths knows about a handler
DAO. A source that existed only in memory would list and browse, then fail the moment anyone tried
to ingest it. The handler's endpoint DAO is therefore built over the repository alone, so there is
exactly one place a data source can come from.

What the seeding may overwrite is limited on purpose:

- a record that is itself `readonly` — one the seeder wrote on an earlier boot — is replaced;
- a record an admin created through the UI carries no such marker, and **the startup fails**
  naming the code rather than throwing away work nobody asked to lose. Rename the declaration or
  delete the UI record first;
- a `readonly` record whose declaration has since been removed from the file is **not deleted** —
  deleting an endpoint properly means replicating the removal and dispatching the disposal that
  clears its documents and vectors, which a repository write cannot do. Its marker is cleared
  instead, which hands it back to the admin UI so it can be removed there the normal way. It is
  logged as a warning.

A broken declaration fails the startup before anything is written, and the admin write paths
refuse a source the configuration owns.

| Property (list) | Binding class | Path syntax |
|---|---|---|
| `ai.gebo.webdav.datasources` | `WebdavDataSourcesConfig` | the resource's full href, `https://host/dav/path/To/Folder` |
| `ai.gebo.awss3.datasources` | `AwsS3DataSourcesConfig` | `<bucket>/<key>`, or a bare `<bucket>` for the whole bucket |
| `ai.gebo.sharepoint.datasources` | `SharepointDataSourcesConfig` | `<driveId>/<itemId>`, or a bare `<driveId>` for the whole drive |
| `ai.gebo.googleworkspace.datasources` | `GoogleDriveDataSourcesConfig` | `<driveId>/<fileId>`, or a bare `<driveId>` for the whole shared drive |

Each entry takes: `code` (required, unique), `description`, `systemCode` (required — a system from
§16.1 or one created in the UI), `parentProjectCode` (the knowledge base project it feeds),
`published`, `synchPeriodically`, `openZips`, `personalData`, `vectorizeOnlyExtensions`,
`programmedTables` (when it is re-ingested — `frequency` plus `times`, where a `DAILY`
`timeComponent` is `[hour, minutes]`), and `paths` (required, at least one). Each path is `path` plus a `folder` flag — `true` for a folder
whose contents are walked, `false` (the default) for a single file.

```yaml
ai.gebo.awss3:
  datasources:
    - code: corporate-reports
      description: Published reports
      systemCode: corporate-buckets
      parentProjectCode: COMPANY-KB
      paths:
        - path: corporate-docs/reports/2026/
          folder: true
        - path: corporate-docs/reports/summary.pdf
          folder: false
```

**The path is written in the handler's own addressing, not in a syntax invented for this file.**
That is what a remote system's notion of "where" actually is, and it is the string the browsing UI
already stores for a source an admin clicks together. For WebDAV and S3 that reads like a path.
For Google Drive and OneDrive it is a pair of **opaque ids** — both address items by id and have no
server-side path — so a folder name will be refused at startup rather than silently matching
nothing. The ids are the ones in the item's URL, and the ones a source built in the UI already
carries.

`folder` cannot be derived from the string (no remote call is made while reading the
configuration), so it is declared. Declaring it wrongly is caught at ingestion, where the
navigation refuses a node whose kind disagrees.

**SharePoint sites are not declarable** — only OneDrive drives. Under a site the module walks
lists, list items and site pages, each with its own identity and step type, and a `path`/`folder`
pair has nothing to say about which is meant. Build site-backed sources in the admin UI.

**Scheduling.** Nothing has to be clicked: every stored endpoint is rescheduled on context
refresh and the seeders run before that, so a declared source is on the schedule from its first
boot, governed by its `programmedTables`. When its schedule says it should already have run — the
normal case on a first boot — the scheduler programs a catch-up run about 30 seconds out. Publish
stays enabled on a read-only source for when you want a run immediately; it queues the ingestion
and skips the save.

### 16.3 `ai.gebo.knowledgebases` / `ai.gebo.projects` — the hierarchy above them

The knowledge base and the project a data source feeds can be declared too, so a deployment comes
up with the whole hierarchy in place rather than needing an admin to build the top of it by hand.

| Property (list) | Binding class | Fields |
|---|---|---|
| `ai.gebo.knowledgebases` | `GeboKnowledgeBaseHierarchyConfig` | `code` (required), `description`, `accessibleToAll`, `accessibleUsers`, `accessibleGroups`, `parentKnowledgebaseCode`, `knowledgeBaseReferences`, `projectsReferences`, `embeddingModelReferences`, `objectSpaceType` |
| `ai.gebo.projects` | `GeboKnowledgeBaseHierarchyConfig` | `code` (required), `description`, `rootKnowledgeBaseCode`, `parentProjectCode`, `accessibleToAll`, `accessibleUsers`, `accessibleGroups`, `objectSpaceType` |

```yaml
ai.gebo:
  knowledgebases:
    - code: COMPANY-KB
      description: Company knowledge base
      accessibleToAll: true
  projects:
    - code: COMPANY-DOCS
      description: Corporate documents
      rootKnowledgeBaseCode: COMPANY-KB
      accessibleToAll: true
```

Like the data sources of §16.2 — and for the same reason, since both are resolved by code through
`IGPersistentObjectManager` from everywhere — these are **written into Mongo at startup** rather
than served from the configuration. They are seeded top down: knowledge bases, then projects, then
data sources, so each level exists before the one that names it.

The overwrite rules of §16.2 apply unchanged: a record an admin created under the same code fails
the startup rather than being replaced, and a record whose declaration is removed has its
`readonly` marker cleared rather than being deleted — deleting a project or knowledge base means
disposing of everything beneath it, which a repository write cannot do.

### 16.4 What `readonly` means for a declared system, data source, project or knowledge base

Everything declared in §16.1, §16.2 and §16.3 carries `readonly: true`. The admin UI reads that marker in
`BaseEntityEditingComponent` and disables **save and delete**, with a message naming the file;
`GAbstractSystemsArchitectureController` refuses insert, update and delete regardless of the
client. Publish is deliberately left enabled — it writes nothing on a read-only entity.

For a data source the guard reads the marker off the **stored record**, not off the object the
client sent back: the record's marker was written by the seeder, while the incoming one is
whatever the caller chose to send. Editing a declared source would in any case not survive the
next restart, when the declaration is written back over it.

The filesystem and MCP handlers already used this flag for their own non-editable singleton
system, so no new concept was introduced for it.

Declared records are also opened to everyone by default, since they belong to the deployment: a
knowledge base and project get `accessibleToAll: true` (the knowledge base an everyone-read ACL
entry too), and a data source gets the everyone-read ACL alias its documents inherit — each only
when the declaration left visibility unset, so an explicit restriction in the file is kept.

## 17. Ingestion pipeline tuning — chunking, embedding, GraphRAG

Advanced performance/throughput tuning. Defaults are sane for most installs; only touch these for
large-scale ingestion tuning.

| Property | Type | Default | Description |
|---|---|---|---|
| `ai.gebo.vectorizator.config.maximumMessagesCumulatedBytesThreshold` | long (bytes) | `2097152` (2MB shipped; 1MB baked-in) | Byte threshold that forces a chunking/embedding batch to flush. |
| `ai.gebo.vectorizator.config.documentChunkerReceiverConfig.poolCardinality` | int | `1` | Worker-thread pool size for the chunking receiver. ⚠️ The shipped sample's `disposerConfig`/`vectorizatorReceiverConfig` keys are **not bound to any field** — see §22 Dead keys. Use `documentChunkerReceiverConfig` instead. |
| `ai.gebo.vectorizator.config.documentChunkerReceiverConfig.useSenderThread` | boolean | `true` | If `true`, messages are processed inline on the sending thread instead of the pool. |
| `ai.gebo.vectorizator.config.documentChunkerReceiverConfig.flushThreshold` | int | `10` | Message count that triggers a batch flush. |
| `ai.gebo.vectorizator.config.documentChunkerReceiverConfig.timeout` | long (ms) | `5000` | Max wait before a forced flush regardless of threshold. |
| `ai.gebo.graphrag.processor.discardedExtensions` | List\<String> | `[.xls, .xlsx, .ods]` | File extensions skipped by GraphRAG extraction. |
| `ai.gebo.graphrag.processor.maximumMessagesCumulatedBytesThreshold` | long (bytes) | `2097152` | Same semantics as the vectorizator equivalent, for graph extraction. |
| `ai.gebo.graphrag.processor.graphRagProcessorReceiverConfig.poolCardinality` | int | `2` | Worker pool size for graph extraction. |
| `ai.gebo.graphrag.processor.graphRagProcessorReceiverConfig.flushThreshold` | int | `6` | Batch flush size. |
| `ai.gebo.graphrag.processor.graphRagProcessorReceiverConfig.useSenderThread` | boolean | `true` | Inline vs. pooled processing. |
| `ai.gebo.graphrag.processor.graphRagProcessorReceiverConfig.timeout` | long (ms) | `10000` | Max wait before forced flush. |
| `ai.gebo.graphrag.processor.graphRagProcessorReceiverConfig.minimumDelayBetweenRequests` | long (ms) | `-1` (disabled) | Optional throttling between graph-extraction LLM calls. |
| `ai.gebo.graphrag.processor.graphRagProcessorReceiverConfig.concurrentGraphExtractionWorkers` | int | `2` | Concurrent graph-extraction workers. |
| `ai.gebo.core.config.mongoDisposerConfig.*` | (same shape) | `poolCardinality=1, useSenderThread=true, flushThreshold=6, timeout=10000` | Batch-write tuning for the generic Mongo persistence disposer. |
| `ai.gebo.core.config.userMessagesReceiverConfig.*` | (same shape) | `poolCardinality=1, useSenderThread=true, flushThreshold=10, timeout=10000` | Batch tuning for inbound user-message processing. |

## 18. RAG relevance & retrieval tuning

| Property | Type | Shipped default | Description |
|---|---|---|---|
| `ai.gebo.rag-threashold-autotune.config.enabled` | boolean | `true` | Periodically re-computes optimal RAG similarity thresholds per vector store / embedding model / knowledge base, by sampling document fragments, generating synthetic questions, and rating match quality. |
| `ai.gebo.agents.standard.enabled` | boolean | `false` | Enables the built-in standard document-search agents. |
| `ai.gebo.chatpipes.defaultPipelineStepIsChatAgent` | boolean | `false` | Whether the default chat pipeline routes straight to the agentic chat-agent flow instead of the LLM-based routing/decision step. |

## 19. Web search tool (Google Custom Search)

```yaml
# ai.gebo.googlesearch:
#   enabled: true
#   apiKey: <put here your api key>
#   customSearchEngineId: <put here your search engine id>
```

Shipped **commented out**, and for good reason beyond being optional: `enabled`/`apiKey`/
`customSearchEngineId` are **not bound to any `application.yml` property** in the current
codebase — Google Search credentials are configured through the admin UI and persisted to
MongoDB (`GoogleSearchConfigDaoImpl`), not through this file. Leave this block commented; editing
it has no effect either way.

## 20. User workflows — activation, password reset, outbound mail

Bound by `GeboUserWorkflowsConfig` (`ai.gebo.userflows`). **Not present in the shipped Docker
config** — production installs that need self-service activation/password-reset email should add
this block.

| Property | Type | Baked-in default | Description |
|---|---|---|---|
| `ai.gebo.userflows.activation-workflow-enabled` | boolean | `true` | Enables email-based account activation. |
| `ai.gebo.userflows.forgot-password-workflow-enabled` | boolean | `true` | Enables self-service password reset. |
| `ai.gebo.userflows.ticket-validity-timeout-ms` | int (ms) | `600000` (10 min) | Validity window for activation/reset links. |
| `ai.gebo.userflows.mail-server` | String | `localhost` | SMTP host. |
| `ai.gebo.userflows.mail-port` | int | `1025` | SMTP port. |
| `ai.gebo.userflows.mail-user-name` / `mail-password` 🔒 | String | *(unset)* | SMTP credentials. |
| `ai.gebo.userflows.mail-sender` | String | `no-reply@gebo.ai` | From-address for outbound mail. |
| `ai.gebo.userflows.gebo-reachable-base-address` | String | `http://localhost:12999` | Public base URL embedded in activation/reset links — **must be set to your real external URL** in any non-localhost deployment. |

## 21. Miscellaneous

| Property | Type | Shipped default | Description |
|---|---|---|---|
| `ai.gebo.multilanguage.config.classPathMode` | boolean | `false` | Loads UI/backend translation bundles from the classpath (`true`) vs. an external folder (`false`). |
| `ai.gebo.multilanguage.config.folderPrefix` | String | *(empty)* | Filesystem path prefix for translation bundles when `classPathMode: false`. |
| `spring.jackson.date-format` | String | `yyyy-MM-dd HH:mm:ss` | JSON date serialization format. |
| `spring.jackson.serialization.INDENT_OUTPUT` | boolean | `true` | Pretty-prints JSON responses. |

---

## 22. Dead / legacy keys in the shipped file

These keys appear in the shipped `dockers/gebo.ai/config/application.yml` (or in commented
examples) but are **not bound to any effective property** — setting them has no effect. Listed so
you don't spend time debugging "why doesn't this setting do anything."

| Shipped key | Status | What to use instead |
|---|---|---|
| `ai.gebo.lucene.config.enabled` | No binding class anywhere in the codebase. Lucene is not an implemented vector-store backend despite being mentioned in code comments as aspirational. | N/A — ignore. |
| `ai.gebo.chat.promptDefaults[].promptUse` / `.prompt` | `GeboChatConfigs` has no `promptDefaults` field; verified with a full-repo search — this key is not referenced anywhere else either. | `ai.gebo.prompts.library[]` (`GeboPromptsLibrary`), or manage prompts via the admin UI. |
| `ai.gebo.security.oauth2.authorizedRedirectUris` | No `authorizedRedirectUris` field exists anywhere in the codebase. | Not needed — the SPA/redirect flow doesn't use a configured allow-list this way. |
| `ai.gebo.security.oauth2configs[].configurationTypes` (plural) | The Java field is `configurationType` (**singular**) — relaxed binding does not match `configurationTypes`, so this key is silently dropped. | Use `configurationType:` (singular) in your YAML. |
| `ai.gebo.vectorizator.config.disposerConfig.*` / `.vectorizatorReceiverConfig.*` | `GeboDocumentsCacheConfig` only has a `documentChunkerReceiverConfig` field; these two are not bound. | `ai.gebo.vectorizator.config.documentChunkerReceiverConfig.*` (see §17). |
| `ai.gebo.config.security` | `GeboConfig` has no `security` field. | N/A — security is controlled entirely under `ai.gebo.security.*` (§6). |
| `ai.gebo.googlesearch.enabled` / `.apiKey` / `.customSearchEngineId` | Not bound by `GoogleSearchHandlerConfig` (which only holds a prompt template) or anywhere else. | Configure Google Search credentials via the admin UI (§19). |
| `security.provider.1: org.bouncycastle.jce.provider.BouncyCastleProvider` | Written in `java.security`-file provider-registration syntax, but no Gebo code and no Spring Boot mechanism reads a `security.provider.*` property. BouncyCastle is registered programmatically at startup instead. | N/A — inert, safe to remove. |
| `ai.gebo.llms.config.azureOpenAIEnabled` | No `@ConditionalOnProperty` or other read site found for this flag. | N/A currently — reserved for a future Azure OpenAI integration. |

---

## Appendix: secrets to rotate before production

Every value below ships with a non-secret placeholder in the Docker image and **must** be changed
for any deployment reachable outside your own machine:

- `ai.gebo.security.auth.tokenSecret`
- Every value under `ai.gebo.secrets.config.*` (see §6.2) — plaintext credentials in the config file
- `ai.gebo.mongodb.connectionString` (embedded Mongo password)
- `ai.gebo.vectorstore.qdrant.apiKey`
- `ai.gebo.opensearch.password`
- `spring.neo4j.authentication.password`
- Any `ai.gebo.security.oauth2configs[].client.secret` you add
- `ai.gebo.userflows.mail-password`, if SMTP auth is configured
