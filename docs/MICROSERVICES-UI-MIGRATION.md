# Angular UI Migration — Monolithic Stub → Per-Microservice Clients

**Status:** planning • **Branch:** `feature/microservices-ui` • **Scope:** `gebo.ui/` Angular apps only (no backend changes)

## 0. What this document is

A file-by-file plan to move the Gebo.ai Angular UI off the single monolithic
REST stub (`@Gebo.ai/gebo-ai-rest-api`) and onto the 21 generated
per-microservice client libraries under
`gebo.api.clients/gebo.microservices.clients.parent`, wired through
`gebo.api.clients/gebo.microservices.clients.parent/gebo.microservices.clients.angular.module`
(`@Gebo.ai/microservices-clients`) in the host `AppModule`.

Every mapping is **grounded in the current Angular and client code**: the reverse
index is computed from the `*.service.ts` files actually present in each client
library, and every target is assigned from the services a `.ts` file actually
imports and how it calls them. Where the code alone cannot decide (a controller
that several services expose), the rule used is stated.

### Numbers (measured)

| | count |
|---|---|
| Monolithic stub services (`gebo-ai-rest-api/src/lib/api/*.service.ts`) | 138 |
| Target microservice client libraries | 21 |
| UI `.ts` files importing `@Gebo.ai/gebo-ai-rest-api` | 216 |
| …importing ≥1 **service** class (need retargeting) | 165 |
| …importing **only models / infra** (no service) | 51 |
| Distinct service references across the UI | 330 |
| Files touching **>1** microservice after the split | 61 |
| Monolith services with exactly **one** microservice home (clean 1:1) | 130 |
| Monolith services exposed by **several** microservices (rule needed) | 4 |
| Monolith services with **no** microservice client (orphans) | 4 |

---

## 1. The mechanism — how a client resolves its address

Each generated library hardcodes the address its spec was scraped from
(`http://localhost:13001/brain`, …) and exposes its **own** `BASE_PATH`
injection token to override it — 21 distinct tokens that merely share a
description string, which is exactly what lets them coexist in one injector.

`MicroservicesClientsModule.forRoot({ baseUrl })`
(`@Gebo.ai/microservices-clients`) imports all 21 `ApiModule`s, then provides
all 21 `BASE_PATH` tokens from a single source: an app initializer reads
`GET <baseUrl>/public/ClientsTopologyProviderController` and hands each library
the web-context that installation publishes for it (`/brain`, `/heimdall`, … on
a gateway deployment; the bare root on a monolith). **The app knows one URL;
nothing else changes between a monolithic and a microservices target** — the
same UI build runs against both.

Consequences that shape this migration:

- **Services keep their class name.** `ChatModelsControllerService` is still
  `ChatModelsControllerService`; only the package it is imported from changes
  (`@Gebo.ai/gebo-ai-rest-api` → `@Gebo.ai/brain`). Most edits are a one-line
  import-source change; the constructor injection is untouched.
- **The single `BASE_PATH` token is gone.** Files that inject the monolith’s
  `BASE_PATH` (URL builders, the auth interceptor) must move to
  `GeboClientsTopologyService.baseUrl` — see §5.
- **Models are per-library and nominally distinct.** `@Gebo.ai/brain` and
  `@Gebo.ai/heimdall` each ship their own copy of a shared model
  (`MCPClientConfig`, `SecretInfo`, …). Structurally identical, but a different
  TypeScript type — see §6.

---

## 2. Root wiring change — host `AppModule`

`gebo.ui/src/app/app.module.ts` today:

```ts
import { BASE_PATH, ApiModule as GeboAiChatApiModule } from '@Gebo.ai/gebo-ai-rest-api';
// ...
imports: [ /* ... */ GeboAiChatApiModule, /* ... */ ],
providers: [ /* ... */ { provide: BASE_PATH, useFactory: getBaseUrl }, /* ... */ ],
```

After:

```ts
import { MicroservicesClientsModule } from '@Gebo.ai/microservices-clients';
// ...
imports: [ /* ... */ MicroservicesClientsModule.forRoot({ baseUrl: getBaseUrl() }), /* ... */ ],
// remove the GeboAiChatApiModule import and the { provide: BASE_PATH, useFactory: getBaseUrl } provider
```

Notes grounded in `app.module.ts`:
- `getBaseUrl()` stays as-is (it already computes the installation origin,
  swapping `:4200` → `:12999` for `ng serve`); it now feeds `forRoot` instead of
  the `BASE_PATH` provider.
- `provideHttpClient(withInterceptorsFromDi())` and the `AuthInterceptor`
  (`HTTP_INTERCEPTORS`, `multi:true`) stay — HttpClient interceptors are
  client-wide, so auth keeps covering all 21 clients unchanged. Only the
  interceptor’s `@Inject(BASE_PATH)` needs a new source (§5).
- The 21 `@Gebo.ai/*` client packages and `@Gebo.ai/microservices-clients` must
  be added as dependencies / tsconfig `paths`, the same way
  `@Gebo.ai/gebo-ai-rest-api` is resolved today.

---

## 3. Target model — how each service maps

Computed by intersecting each UI file’s imported service classes with the
`*.service.ts` inventory of every client library.

### 3.1 Clean 1:1 (130 services) — mechanical import-source change

Where a controller exists in exactly one library, the target is unambiguous.
Distribution of the **references actually made by the UI**:

| target library | service references |
|---|---|
| `@Gebo.ai/brain` | 151 |
| `@Gebo.ai/heimdall` | 79 |
| `@Gebo.ai/filesystem` | 8 |
| `@Gebo.ai/confluence` | 7 |
| `@Gebo.ai/googledrive` | 7 |
| `@Gebo.ai/aws-s3` | 6 |
| `@Gebo.ai/webdav` | 6 |
| `@Gebo.ai/jira` | 6 |
| `@Gebo.ai/sharepoint` | 6 |
| `@Gebo.ai/tyr` | 5 |
| `@Gebo.ai/userspace` | 5 |
| `@Gebo.ai/uploads` | 4 |
| `@Gebo.ai/mcpclient` | 3 |
| `@Gebo.ai/git` | 3 |
| `@Gebo.ai/graphicator` | 3 |
| `@Gebo.ai/vectorizator` | 2 |

`brain` and `heimdall` carry the overwhelming majority — the LLM/agent/model/
knowledge-base admin surface (`brain`) and the auth/user/secret/setup surface
(`heimdall`).

### 3.2 Duplicated content-handler controllers (rule needed)

Four controllers are shipped by **every** content-handler library because they
come from the shared content-handler starter, and each instance operates on
**that handler’s** content:

`ContentsResetControllerService`, `DocumentContentStreamerControllerService`,
`GenericalPublisherControllerService`, `JobLauncherControllerService`.

They are exposed by the 13-member set `aws-s3, brain, confluence, filesystem,
git, googledrive, integration, jira, mcpclient, sharepoint, uploads, userspace,
webdav`.

**Grounding:** in every content-handler *endpoint editor*, the handler type is
fixed by the file, and the call is scoped to it — e.g.
`gebo-ai-sharepoint-endpoint.component.ts` injects `JobLauncherControllerService`
and calls `createJob(objectReference)` / `getHasRunningJobs(...)` for a
SharePoint endpoint. So:

> **Rule H — handler-scoped:** in a content-handler’s own editor
> (`…/gebo-ai-<handler>-admin/…`, `…-<handler>-endpoint…`), these controllers
> resolve to that handler’s library (`sharepoint`, `webdav`, `aws-s3`,
> `googledrive`, `uploads`, `filesystem`, `mcpclient`, `jira`, `confluence`,
> `git`, `userspace`).

> **Rule A — aggregate/generic:** where the operation is project- or
> knowledge-base-level, or the endpoint type is only known at runtime
> (`…/gebo-ai-knowledgebase-admin/…`, `setup-wizard/knowledge-base-wizard`,
> `setup-wizard/wizards-navigation`, `logs-view`), these resolve to
> **`@Gebo.ai/brain`**, which also carries the shared starter and owns the
> project/KB aggregate. Confirmed by `gebo-ai-project-admin.component.ts` and
> `gebo-ai-knowledgebase-admin.component.ts` calling
> `ContentsResetControllerService.resetContentsIngestion({...})` at project
> scope.

**Job launch vs. job status — a real split to preserve.** `JobLauncher…`
(`createJob`, `getHasRunningJobs`) is per-handler (Rule H/A). Job **status** is
`JobStatusControllerService`, which exists **only** in `@Gebo.ai/tyr` — the
central scheduler that every handler replicates `GJobStatus` to. So a screen
that both launches and polls (e.g. the job-status viewer) legitimately talks to
a handler (or brain) **and** tyr. This is grounded, not accidental:
`gebo-ai-job-status-viewer.component.ts` → `tyr[JobStatusControllerService]` +
`brain[LogViewControllerService, CompanySystemsControllerService]`.

### 3.3 Orphans — no microservice client (4)

Four monolith services have **no** matching `*.service.ts` in any client
library. Three are referenced by the UI (below) and must be resolved before
those files compile; the fourth, `LanguageResourcesControllerService`, is an
orphan with **no** UI usage — no action:

| service | UI files | options |
|---|---|---|
| `GeboModulesConfigControllerService` | 10 | modules-config is cluster/aggregate metadata. **Decide the owning service** (likely `heimdall` or `brain`) and expose it on that client, or route via the gateway. Highest-impact orphan — used across every setup wizard and `gebo-ai-modules.service.ts` |
| `GeboAngularFormGroupMetaInfoControllerService` | 3 | form-metadata endpoint used by `gebo-form-groups.service.ts` and base editors. Pick the owning service and expose it, or keep a thin monolith-compat client |
| `UiTextResourcesControllerService` | 1 | i18n text resources (`gebo-translation.service.ts`). Likely `heimdall`/gateway static content |

> **Action required (blocking):** the 3 UI-referenced orphan controllers are not
> in scope of the generated clients today. Each needs an owner decision + a
> client that exposes it (or an explicit gateway route), otherwise the ~14 files
> using them cannot leave the monolith stub. Track these as prerequisites, not
> per-file edits.

---

## 4. The 61 multi-microservice files

61 service-bearing files import from **more than one** future package. They are
the migration’s real work — each needs several import lines split by target, and
(where a model crosses the boundary) the §6 model-identity check. The dominant,
fully-grounded shape is the **content-handler endpoint editor**:

```
brain[ProjectsControllerService]                     // the project/KB it attaches to
<handler>[<Handler>SystemsControllerService,
          <Handler>BrowsingControllerService,
          JobLauncherControllerService]              // the endpoint + its ingestion job
heimdall[SecretsControllerService]                   // its credentials
```

e.g. `gebo-ai-aws-s3-endpoint.component.ts` →
`aws-s3[AwsS3Browsing…, AwsS3Systems…, JobLauncher…]` +
`brain[ProjectsController…]` + `heimdall[SecretsController…]`. The system
editors are the two-way shape `<handler>[Systems…]` + `heimdall[Secrets…/User…]`.

---

## 5. `BASE_PATH` / infra — the cross-cutting edit

The monolith’s single `BASE_PATH` token disappears. Files that inject it must
switch to the topology service the clients module already provides:

```ts
// before
import { BASE_PATH } from '@Gebo.ai/gebo-ai-rest-api';
constructor(@Optional() @Inject(BASE_PATH) private basePath: string) {}

// after
import { GeboClientsTopologyService } from '@Gebo.ai/microservices-clients';
constructor(private topology: GeboClientsTopologyService) {}
// use this.topology.baseUrl  (installation origin), or
//     this.topology.basePathFor(serviceId)  for a specific service’s context
```

Grounded `BASE_PATH` consumers to convert (from `@Inject(BASE_PATH)` /
`import { BASE_PATH }`):
`services/auth-interceptor.service.ts` (the global `AuthInterceptor` — matches
requests to the gebo backend before attaching the token),
`services/build-gebo-url.service.ts`, `services/gebo-backends-list.service.ts`,
`infrastructure/login/login.service.ts`,
`infrastructure/login/oauth2/oauth2-login.service.ts`,
plus the components that build absolute asset/stream URLs
(`content-viewer/*`, `chat-control/*`, `userspace-files-*`, `gebo-dashboard/*`,
`agents-network-admin/*`, `oauth2-registration`, `shared-filesystems`,
`user-integrations`, `time-set`, `systems.component`, `wizards-navigation`,
`chat-session`). `Configuration` / `ApiModule` imports from the monolith are
dropped (each library has its own; the module wires them).

---

## 6. Models — nominal type identity (do not skip)

Each client library ships **only** the models its own controllers reference, as
its own generated copies (`@Gebo.ai/brain` the largest, `@Gebo.ai/heimdall`,
`@Gebo.ai/tyr`, …; the monolith’s set is the superset). Two copies of
`MCPClientConfig` (brain and mcpclient) are structurally identical but are
**distinct TypeScript types**.

Rules for the 51 model-only files and for model imports inside service files:

1. **Import a model from the same package as the service that produces/consumes
   it.** A `GProject` handed to `ProjectsControllerService` comes from
   `@Gebo.ai/brain`; a `SecretInfo` handed to `SecretsControllerService` comes
   from `@Gebo.ai/heimdall`.
2. **Model-only files** (no service) import from **whichever package owns the
   flow the model belongs to** — chat models from `@Gebo.ai/brain`, job/usage
   models from `@Gebo.ai/tyr`, credential models from `@Gebo.ai/heimdall`. These
   51 files carry no HTTP call, so the only risk is a type mismatch at a
   boundary (rule 3).
3. **Boundary friction:** where one component passes a shared model between two
   microservices’ services, the two copies won’t be assignable. Resolve by
   importing the model from one package and, if the compiler objects at the other
   call, a structural cast at that single seam — or, preferably, keep such a model
   flowing through one service only. The 61 multi-microservice files are where
   this can bite; treat the compiler as the checklist.

---

## 7. Per-file mapping (grounded, complete)

Every UI `.ts` importing a **service** from the monolith stub, with its target
package(s) and the exact services to move. `<br>` separates targets within one
file (a multi-microservice file). Paths are relative to each project’s
`src/lib/` (host app noted as `HOST/src/`).

#### gebo-ai-admin-ui — admin-ui

| File | Target `@Gebo.ai/<lib>` [ services ] |
|---|---|
| `admin-ui/entity-editors/controls/access-control-group/access-control-group.component.ts` | heimdall[UsersAdminControllerService] |
| `admin-ui/entity-editors/controls/advanced-settings-chatmodel-group/advanced-settings-chatmodel-group.component.ts` | brain[FunctionsLookupControllerService,PromptTemplatesControllerService] |
| `admin-ui/entity-editors/controls/build-systems-chooser/build-systems-chooser.component.ts` | brain[BuildSystemsControllerService] |
| `admin-ui/entity-editors/controls/graphrag-config/graphrag-config.component.ts` | brain[GeboNeo4jModuleSetupControllerService]<br>graphicator[GraphRagConfigurationControllerService] |
| `admin-ui/entity-editors/controls/prompt-wizard/prompt-wizard.component.ts` | brain[ChatModelsLookupControllerService] |
| `admin-ui/entity-editors/gebo-ai-a2a-client-admin/gebo-ai-a2a-client-admin.component.ts` | brain[A2AClientConfigControllerService]<br>heimdall[SecretsControllerService,UsersAdminControllerService] |
| `admin-ui/entity-editors/gebo-ai-a2a-server-admin/gebo-ai-a2a-server-admin.component.ts` | brain[GeboA2AServerAdminControllerService] |
| `admin-ui/entity-editors/gebo-ai-agents-admin/gebo-ai-agents-admin.component.ts` | brain[ChatModelsControllerService,GeboAgentAdminControllerService] |
| `admin-ui/entity-editors/gebo-ai-agents-network-admin/gebo-ai-agents-network-admin.component.ts` | brain[GeboAgentAdminControllerService,GeboAgentsNetworkAdminControllerService] |
| `admin-ui/entity-editors/gebo-ai-atlassian-admin/gebo-ai-confluence-endpoint.component.ts` | brain[ProjectsControllerService]<br>confluence[ConfluenceBrowsingControllerService,ConfluenceSystemsControllerService,JobLauncherControllerService] |
| `admin-ui/entity-editors/gebo-ai-atlassian-admin/gebo-ai-confluence-system-admin.component.ts` | confluence[ConfluenceSystemsControllerService]<br>heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-atlassian-admin/gebo-ai-confluence-system-fast.component.ts` | confluence[ConfluenceSystemsControllerService]<br>heimdall[UserControllerService] |
| `admin-ui/entity-editors/gebo-ai-atlassian-admin/gebo-ai-jira-endpoint.component.ts` | brain[ProjectsControllerService]<br>jira[JiraBrowsingControllerService,JiraSystemsControllerService,JobLauncherControllerService] |
| `admin-ui/entity-editors/gebo-ai-atlassian-admin/gebo-ai-jira-system-admin.component.ts` | heimdall[SecretsControllerService]<br>jira[JiraSystemsControllerService] |
| `admin-ui/entity-editors/gebo-ai-atlassian-admin/gebo-ai-jira-system-fast.component.ts` | heimdall[UserControllerService]<br>jira[JiraSystemsControllerService] |
| `admin-ui/entity-editors/gebo-ai-aws-s3-admin/gebo-ai-aws-s3-endpoint.component.ts` | aws-s3[AwsS3BrowsingControllerService,AwsS3SystemsControllerService,JobLauncherControllerService]<br>brain[ProjectsControllerService]<br>heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-aws-s3-admin/gebo-ai-aws-s3-system-admin.component.ts` | aws-s3[AwsS3SystemsControllerService]<br>heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-aws-s3-admin/gebo-ai-aws-s3-system-fast.component.ts` | aws-s3[AwsS3SystemsControllerService] |
| `admin-ui/entity-editors/gebo-ai-chat-profile-admin/gebo-ai-chat-profile-admin.component.ts` | brain[ChatModelsControllerService,EmbeddingModelsControllersService,GeboAdminChatProfilesConfigurationControllerService,KnowledgeBaseControllerService,PromptTemplatesControllerService] |
| `admin-ui/entity-editors/gebo-ai-filesystems-admin/gebo-ai-filesystem-endpoint.component.ts` | brain[ProjectsControllerService]<br>filesystem[FileSystemsBrowsingControllerService,FileSystemsControllerService,JobLauncherControllerService] |
| `admin-ui/entity-editors/gebo-ai-filesystems-admin/gebo-ai-filesystem-share-reference-admin.component.ts` | filesystem[FileSystemSharesSettingControllerService] |
| `admin-ui/entity-editors/gebo-ai-filesystems-admin/gebo-ai-shared-filesystems.component.ts` | filesystem[FileSystemSharesSettingControllerService] |
| `admin-ui/entity-editors/gebo-ai-git-admin/gebo-ai-git-endpoint-admin.component.ts` | brain[ProjectsControllerService]<br>git[GitSystemsControllerService,JobLauncherControllerService]<br>heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-git-admin/gebo-ai-git-system-admin.component.ts` | git[GitSystemsControllerService]<br>heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-google-search-admin/gebo-ai-google-search-account.component.ts` | brain[GoogleSearchConfigurationControllerService]<br>heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-google-workspaces-admin/gebo-ai-google-drive-admin.component.ts` | googledrive[GoogleDriveSystemsControllerService]<br>heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-google-workspaces-admin/gebo-ai-google-drive-endpoint-admin.component.ts` | brain[ProjectsControllerService]<br>googledrive[GoogleDriveBrowsingControllerService,GoogleDriveSystemsControllerService,JobLauncherControllerService] |
| `admin-ui/entity-editors/gebo-ai-google-workspaces-admin/gebo-ai-google-drive-fast.component.ts` | googledrive[GoogleDriveSystemsControllerService] |
| `admin-ui/entity-editors/gebo-ai-google-workspaces-admin/gebo-ai-google-workspace-access.component.ts` | googledrive[GoogleWorkspaceAccessHandshakeControllerService]<br>heimdall[UserControllerService] |
| `admin-ui/entity-editors/gebo-ai-job-status-viewer/gebo-ai-job-status-viewer.component.ts` | brain[CompanySystemsControllerService,LogViewControllerService]<br>tyr[JobStatusControllerService] |
| `admin-ui/entity-editors/gebo-ai-job-status-viewer/log-table.component.ts` | brain[LogViewControllerService] |
| `admin-ui/entity-editors/gebo-ai-knowledgebase-admin/gebo-ai-knowledgebase-admin.component.ts` | brain[ContentsResetControllerService,EmbeddingModelsControllersService,KnowledgeBaseControllerService] |
| `admin-ui/entity-editors/gebo-ai-knowledgebase-admin/gebo-ai-knowledgebase-tree.component.ts` | brain[IngestionFileTypesLibraryControllerService] |
| `admin-ui/entity-editors/gebo-ai-knowledgebase-admin/gebo-ai-project-admin.component.ts` | brain[ContentsResetControllerService,KnowledgeBaseControllerService,ProjectsControllerService] |
| `admin-ui/entity-editors/gebo-ai-mcp-client-admin/gebo-ai-mcp-client-admin.component.ts` | brain[McpClientConfigControllerService]<br>heimdall[SecretsControllerService,UsersAdminControllerService] |
| `admin-ui/entity-editors/gebo-ai-mcp-server-admin/gebo-ai-mcp-server-admin.component.ts` | brain[GeboMcpServerAdminControllerService] |
| `admin-ui/entity-editors/gebo-ai-mcpclient-admin/gebo-ai-mcpclient-endpoint.component.ts` | brain[McpClientConfigControllerService,ProjectsControllerService]<br>mcpclient[JobLauncherControllerService,McpClientBrowsingControllerService,McpClientSystemsControllerService] |
| `admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-anthropic-chatmodel-admin.component.ts` | brain[AnthropicChatModelsConfigurationControllerService,FunctionsLookupControllerService]<br>heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-bedrock-chatmodel-admin.component.ts` | brain[BedrockChatModelsConfigurationControllerService,FunctionsLookupControllerService]<br>heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-bedrock-embedmodel-admin.component.ts` | brain[BedrockEmbeddingModelsConfigurationControllerService]<br>heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-bedrock-image-model-admin.component.ts` | brain[BedrockImageModelsConfigurationControllerService]<br>heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-bedrock-ranker-admin.component.ts` | brain[BedrockRankerModelsConfigurationControllerService]<br>heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-bedrock-text-to-speech-model-admin.component.ts` | brain[BedrockTextToSpeechModelsConfigurationControllerService]<br>heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-bedrock-transcript-model-admin.component.ts` | brain[BedrockTranscriptModelsConfigurationControllerService]<br>heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-deepseek-chatmodel-admin.component.ts` | brain[DeepseekChatModelsConfigurationControllerService,FunctionsLookupControllerService]<br>heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-generic-openai-api-chatmodel-admin.component.ts` | brain[FunctionsLookupControllerService,GenericOpenAiapiChatModelsConfigurationControllerService]<br>heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-generic-openai-api-embedmodel-admin.component.ts` | brain[GenericOpenAiapiEmbeddingModelsConfigurationControllerService]<br>heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-generic-openai-api-image-model-admin.component.ts` | brain[GenericOpenAiapiImageModelsConfigurationControllerService]<br>heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-generic-openai-api-ranker-admin.component.ts` | brain[GenericOpenAiRankerModelsConfigurationControllerService]<br>heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-generic-openai-api-text-to-speech-model-admin.component.ts` | brain[GenericOpenAiapiTextToSpeechModelsConfigurationControllerService]<br>heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-generic-openai-api-transcript-model-admin.component.ts` | brain[GenericOpenAiapiTranscriptModelsConfigurationControllerService]<br>heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-google-vertex-chatmodel-admin.component.ts` | brain[FunctionsLookupControllerService,GoogleVertexChatModelsConfigurationControllerService]<br>heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-google-vertex-embedmodel-admin.component.ts` | brain[GoogleVertexEmbeddingModelsConfigurationControllerService]<br>heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-mistralai-chatmodel-admin.component.ts` | brain[FunctionsLookupControllerService,MistralAiChatModelsConfigurationControllerService]<br>heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-mistralai-embedmodel-admin.component.ts` | brain[MistralAiEmbeddingModelsConfigurationControllerService]<br>heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-ollama-chatmodel-admin.component.ts` | brain[OllamaChatModelsConfigurationControllerService]<br>heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-ollama-embedmodel-admin.component.ts` | brain[EmbeddingModelsControllersService,OllamaEmbeddingModelsConfigurationControllerService]<br>heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-openai-chatmodel-admin.component.ts` | brain[FunctionsLookupControllerService,OpenAiChatModelsConfigurationControllerService]<br>heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-openai-embedmodel-admin.component.ts` | brain[OpenAiEmbeddingModelsConfigurationControllerService]<br>heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-openai-image-model-admin.component.ts` | brain[OpenAiImageModelsConfigurationControllerService]<br>heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-openai-text-to-speech-model-admin.component.ts` | brain[OpenAiTextToSpeechModelsConfigurationControllerService]<br>heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-openai-transcript-model-admin.component.ts` | brain[OpenAiTranscriptModelsConfigurationControllerService]<br>heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-oauth2-admin/gebo-ai-oauth2-registration.component.ts` | heimdall[OAuth2AdminControllerService] |
| `admin-ui/entity-editors/gebo-ai-prompt-admin/gebo-ai-prompt-admin.component.ts` | brain[GeboAdminPromptsControllerService] |
| `admin-ui/entity-editors/gebo-ai-secrets-admin/gebo-ai-secrets-admin-edit.component.ts` | heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-secrets-admin/gebo-ai-secrets-admin-list.component.ts` | heimdall[SecretsControllerService] |
| `admin-ui/entity-editors/gebo-ai-sharepoint-admin/gebo-ai-sharepoint-endpoint.component.ts` | brain[ProjectsControllerService]<br>heimdall[SecretsControllerService]<br>sharepoint[JobLauncherControllerService,SharepointBrowsingControllerService,SharepointSystemsControllerService] |
| `admin-ui/entity-editors/gebo-ai-sharepoint-admin/gebo-ai-sharepoint-system-admin.component.ts` | heimdall[SecretsControllerService]<br>sharepoint[SharepointSystemsControllerService] |
| `admin-ui/entity-editors/gebo-ai-sharepoint-admin/gebo-ai-sharepoint-system-fast.component.ts` | heimdall[UserControllerService]<br>sharepoint[SharepointSystemsControllerService] |
| `admin-ui/entity-editors/gebo-ai-uploads-admin/gebo-ai-uploads-endpoint.component.ts` | brain[ProjectsControllerService]<br>uploads[FileUploadControllerService,FileUploadsControllerService,JobLauncherControllerService,UploadsBrowsingControllerService] |
| `admin-ui/entity-editors/gebo-ai-users-admin/gebo-ai-change-user-password.component.ts` | heimdall[UsersAdminControllerService] |
| `admin-ui/entity-editors/gebo-ai-users-admin/gebo-ai-group.component.ts` | heimdall[UsersAdminControllerService] |
| `admin-ui/entity-editors/gebo-ai-users-admin/gebo-ai-user.component.ts` | heimdall[AuthProvidersControllerService,UsersAdminControllerService] |
| `admin-ui/entity-editors/gebo-ai-webdav-client-admin/gebo-ai-webdav-endpoint.component.ts` | brain[ProjectsControllerService]<br>heimdall[SecretsControllerService]<br>webdav[JobLauncherControllerService,WebdavBrowsingControllerService,WebdavSystemsControllerService] |
| `admin-ui/entity-editors/gebo-ai-webdav-client-admin/gebo-ai-webdav-system-admin.component.ts` | heimdall[SecretsControllerService]<br>webdav[WebdavSystemsControllerService] |
| `admin-ui/entity-editors/gebo-ai-webdav-client-admin/gebo-ai-webdav-system-fast.component.ts` | webdav[WebdavSystemsControllerService] |
| `admin-ui/entity-editors/gebo-deep-search-admin/gebo-deep-search-admin.component.ts` | brain[ChatModelsControllerService,GeboDeepSearchAdminControllerService]<br>heimdall[UsersAdminControllerService] |
| `admin-ui/entity-editors/gebo-graph-rag-extraction-config-admin/graph-rag-extraction-config.component.ts` | brain[ChatModelsControllerService,CompanySystemsControllerService,KnowledgeBaseControllerService,ProjectsControllerService]<br>graphicator[GraphRagConfigurationControllerService] |
| `admin-ui/gebo-ai-admin.component.ts` | heimdall[UserControllerService] |
| `admin-ui/gebo-ai-standard-modules-injections.module.ts` | aws-s3[AwsS3SystemsControllerService]<br>confluence[ConfluenceSystemsControllerService]<br>filesystem[FileSystemsControllerService]<br>git[GitSystemsControllerService]<br>googledrive[GoogleDriveSystemsControllerService]<br>jira[JiraSystemsControllerService]<br>mcpclient[McpClientSystemsControllerService]<br>sharepoint[SharepointSystemsControllerService]<br>uploads[FileUploadsControllerService]<br>webdav[WebdavSystemsControllerService] |
| `admin-ui/main-panels/agent-networks/agent-networks.component.ts` | brain[GeboAgentsNetworkAdminControllerService] |
| `admin-ui/main-panels/build-packaging-systems/build-packaging-systems.component.ts` | brain[BuildSystemsControllerService] |
| `admin-ui/main-panels/chat-profiles/chat-profiles.component.ts` | brain[GeboAdminChatProfilesConfigurationControllerService] |
| `admin-ui/main-panels/company-systems/systems.component.ts` | brain[CompanySystemsControllerService]<br>filesystem[FileSystemSharesSettingControllerService] |
| `admin-ui/main-panels/compliance/compliance.component.ts` | brain[DataFlowMetaInfoControllerService] |
| `admin-ui/main-panels/gebo-dashboard/gebo-dashboard.component.ts` | vectorizator[GeboCoreAnalisysControllerService] |
| `admin-ui/main-panels/gebo-dashboard/gebo-embedding-stats-panel.component.ts` | vectorizator[GeboCoreAnalisysControllerService] |
| `admin-ui/main-panels/knowledge-bases/knowledge-bases.component.ts` | brain[KnowledgeBaseControllerService,ProjectsControllerService] |
| `admin-ui/main-panels/llms-systems/llms-systems.component.ts` | brain[ChatModelsControllerService,EmbeddingModelsControllersService,ImageModelsControllerService,RankerModelsControllerService,TextToSpeechModelsControllerService,TranscriptModelsControllerService] |
| `admin-ui/main-panels/logs-view/logs-view.component.ts` | ORPHAN[GeboAngularFormGroupMetaInfoControllerService]<br>brain[JobLauncherControllerService,LogViewControllerService] |
| `admin-ui/main-panels/prompts-panel/prompts-panel.component.ts` | brain[GeboAdminPromptsControllerService] |
| `admin-ui/main-panels/users-management/users-management.component.ts` | heimdall[UsersAdminControllerService] |

#### gebo-ai-admin-ui — setup-wizard

| File | Target `@Gebo.ai/<lib>` [ services ] |
|---|---|
| `setup-wizard/a2a-export-wizard.component.ts` | brain[GeboA2AServerAdminControllerService] |
| `setup-wizard/a2a-import-wizard.component.ts` | brain[A2AClientConfigControllerService] |
| `setup-wizard/abstract-module-installed.service.ts` | ORPHAN[GeboModulesConfigControllerService] |
| `setup-wizard/agent-setup-wizard.component.ts` | brain[GeboAgentAdminControllerService] |
| `setup-wizard/aws-s3-wizard.component.ts` | ORPHAN[GeboModulesConfigControllerService]<br>aws-s3[AwsS3SystemsControllerService] |
| `setup-wizard/chat-profile-wizard.component.ts` | brain[FunctionsLookupControllerService,GeboAdminChatProfilesConfigurationControllerService,GeboFastChatProfileStatusControllerService,KnowledgeBaseControllerService,PromptTemplatesControllerService] |
| `setup-wizard/confluence-wizard.component.ts` | ORPHAN[GeboModulesConfigControllerService]<br>confluence[ConfluenceSystemsControllerService] |
| `setup-wizard/deep-search-wizard.component.ts` | brain[GeboDeepSearchAdminControllerService] |
| `setup-wizard/gebo-ai-mcp-server-wizard.component.ts` | brain[GeboMcpServerAdminControllerService] |
| `setup-wizard/generated-admin-api-key-wizard.component.ts` | heimdall[GeneratedAdminApiKeyControllerService,UsersAdminControllerService] |
| `setup-wizard/google-search-wizard.component.ts` | brain[GoogleSearchConfigurationControllerService] |
| `setup-wizard/google-workspace-wizard.component.ts` | ORPHAN[GeboModulesConfigControllerService]<br>googledrive[GoogleDriveSystemsControllerService] |
| `setup-wizard/graphrag-wizard.component.ts` | brain[GeboNeo4jModuleSetupControllerService]<br>graphicator[GraphRagConfigurationControllerService] |
| `setup-wizard/jira-wizard.component.ts` | ORPHAN[GeboModulesConfigControllerService]<br>confluence[ConfluenceSystemsControllerService]<br>jira[JiraSystemsControllerService] |
| `setup-wizard/knowledge-base-wizard.component.ts` | brain[GeboFastKnowledgeBaseSetupControllerService,JobLauncherControllerService,KnowledgeBaseControllerService] |
| `setup-wizard/llms-easy-wizard/llms-easy-setup-wizard.component.ts` | brain[GeboFastLlmsSetupControllerService] |
| `setup-wizard/llms-easy-wizard/step-provider.component.ts` | brain[GeboFastLlmsSetupControllerService] |
| `setup-wizard/llms-setup-components/easy-vendor-configuration.component.ts` | brain[GeboFastLlmsSetupControllerService] |
| `setup-wizard/llms-setup-components/llms-vendor-configuration.component.ts` | brain[GeboFastLlmsSetupControllerService] |
| `setup-wizard/llms-setup-components/llms-vendor-modeltype.component.ts` | brain[GeboFastLlmsSetupControllerService] |
| `setup-wizard/llms-setup-wizard.component.ts` | heimdall[UserControllerService] |
| `setup-wizard/llms-setup-wizard.service.ts` | brain[GeboFastLlmsSetupControllerService] |
| `setup-wizard/mcp-server-wizard.component.ts` | brain[McpClientConfigControllerService] |
| `setup-wizard/oauth2-wizard.component.ts` | heimdall[AuthProvidersControllerService,Oauth2ModuleStatusControllerService] |
| `setup-wizard/rag-autotune-wizard.component.ts` | brain[GeboAdminRagAutotuneControllerService] |
| `setup-wizard/shared-filesystem-wizard.component.ts` | filesystem[FileSystemSharesSettingControllerService] |
| `setup-wizard/sharepoint-wizard.component.ts` | ORPHAN[GeboModulesConfigControllerService]<br>sharepoint[SharepointSystemsControllerService] |
| `setup-wizard/users-wizard.component.ts` | heimdall[UsersAdminControllerService] |
| `setup-wizard/web-search-wizard.component.ts` | brain[BraveSearchConfigurationControllerService,GoogleSearchConfigurationControllerService,SearxngSearchConfigurationControllerService,SerpapiSearchConfigurationControllerService,TavilySearchConfigurationControllerService] |
| `setup-wizard/webdav-wizard.component.ts` | ORPHAN[GeboModulesConfigControllerService]<br>webdav[WebdavSystemsControllerService] |
| `setup-wizard/wizards-navigation.ts` | brain[JobLauncherControllerService] |
| `setup-wizard/work-folder-wizard.component.ts` | filesystem[FileSystemSharesSettingControllerService]<br>heimdall[GeboFastWorkFolderSetupControllerService] |

#### gebo-ai-chat-ui — gebo-ai-chat-section-component

| File | Target `@Gebo.ai/<lib>` [ services ] |
|---|---|
| `gebo-ai-chat-section-component/gebo-ai-rag-chat-section.component.ts` | brain[ChatModelsLookupControllerService,GeboRagChatControllerService,GeboUserChatsControllerService] |

#### gebo-ai-reusable-ui — architecture

| File | Target `@Gebo.ai/<lib>` [ services ] |
|---|---|
| `architecture/gebo-form-groups.service.ts` | ORPHAN[GeboAngularFormGroupMetaInfoControllerService] |

#### gebo-ai-reusable-ui — controls

| File | Target `@Gebo.ai/<lib>` [ services ] |
|---|---|
| `controls/add-project-endpoint-component/choose-data-source-type.component.ts` | brain[ProjectsControllerService] |
| `controls/add-project-endpoint-component/project-add-context-menu.component.ts` | ORPHAN[GeboModulesConfigControllerService] |
| `controls/api-key-component/api-key.component.ts` | heimdall[SecretsControllerService] |
| `controls/base-entity-editing-component/base-entity-editing-auto-delete-check.component.ts` | ORPHAN[GeboAngularFormGroupMetaInfoControllerService] |
| `controls/chat-control/gebo-ai-reusable-chat.component.ts` | brain[GeboChatControllerService,GeboChatPipelinesControllerService,GeboRagChatControllerService,GeboTextToSpeechControllerService,GeboTranscriptControllerService,GeboUserChatsControllerService] |
| `controls/choose-documents-panel/search-documents.component.ts` | brain[UserKnowledgeBaseBrowsingControllerService] |
| `controls/choose-documents-panel/upload-chat-document.component.ts` | brain[GeboUserChatUploadsControllerService,IngestionFileTypesLibraryControllerService] |
| `controls/choose-llm-functions/choose-llm-functions.component.ts` | brain[FunctionsLookupControllerService] |
| `controls/content-reindex-schedule/content-reindex-schedule.component.ts` | tyr[ReindexingFrequencyOptionsControllerService] |
| `controls/content-selection-filter-component/content-selection-filter.component.ts` | brain[IngestionFileTypesLibraryControllerService] |
| `controls/content-viewer/enriched-document-reference-view.service.ts` | brain[ContentMetaInfosControllerService,GeboUserKnowledgeBaseSemanticSearchControllerService,IngestionFileTypesLibraryControllerService] |
| `controls/content-viewer/gebo-ai-content-viewer.component.ts` | brain[ContentMetaInfosControllerService,IngestionFileTypesLibraryControllerService] |
| `controls/deep-search-control/deep-search-sources-choice.component.ts` | brain[GeboChatControllerService,GeboDeepSearchControllerService,GeboRagChatControllerService] |
| `controls/field-translation-container/gebo-translation.service.ts` | ORPHAN[UiTextResourcesControllerService] |
| `controls/gebo-oauth2-secret-component/gebo-oauth2-secret.component.ts` | heimdall[OAuth2AdminControllerService] |
| `controls/prompt-editing-component/prompt-editing.component.ts` | brain[GeboAdminPromptsControllerService] |
| `controls/prompt-editing-component/prompt-wizard.component.ts` | brain[ChatModelsLookupControllerService] |
| `controls/userspace-files-component/user-knowledgebase.component.ts` | heimdall[UserControllerService]<br>userspace[UserspaceControllerService] |
| `controls/userspace-files-component/userspace-browse.component.ts` | userspace[UserspaceControllerService] |
| `controls/userspace-files-component/userspace-files-upload.component.ts` | brain[IngestionFileTypesLibraryControllerService] |
| `controls/userspace-files-component/userspace-files-upload.service.ts` | userspace[UserspaceControllerService] |
| `controls/userspace-files-component/userspace-files.component.ts` | userspace[UserspaceControllerService] |
| `controls/userspace-files-component/userspace-folder.component.ts` | userspace[UserspaceControllerService] |

#### gebo-ai-reusable-ui — dashboard

| File | Target `@Gebo.ai/<lib>` [ services ] |
|---|---|
| `dashboard/llms-usage-admin-dashboard.component.ts` | tyr[LlmsUsageAdminLevelControllerService] |
| `dashboard/llms-usage-user-dashboard.component.ts` | tyr[LlmsUsageUserLevelControllerService] |
| `dashboard/workflow-stats-admin-dashboard.component.ts` | tyr[WorkflowStatsAdminLevelControllerService] |

#### gebo-ai-reusable-ui — infrastructure

| File | Target `@Gebo.ai/<lib>` [ services ] |
|---|---|
| `infrastructure/fast-setup/fast-setup.component.ts` | heimdall[GeboFastInstallationSetupControllerService] |
| `infrastructure/login/login.component.ts` | heimdall[GeboFastInstallationSetupControllerService,UserWorkflowsControllerService] |
| `infrastructure/login/login.service.ts` | heimdall[AuthControllerService,AuthProvidersControllerService,TokenRenewControllerService,UserControllerService] |
| `infrastructure/login/oauth2/oauth2-login.service.ts` | heimdall[AuthControllerService] |
| `infrastructure/user-integrations/user-integrations.component.ts` | brain[GeboMcpServerUserControllerService]<br>heimdall[GeneratedUserApiKeyControllerService] |
| `infrastructure/user-workflows/check-device-activation.component.ts` | heimdall[UserWorkflowsControllerService] |
| `infrastructure/user-workflows/user-workflows-land.component.ts` | heimdall[UserWorkflowsControllerService] |
| `infrastructure/user-workflows/user-workflows-start.component.ts` | heimdall[UserWorkflowsControllerService] |

#### gebo-ai-reusable-ui — services

| File | Target `@Gebo.ai/<lib>` [ services ] |
|---|---|
| `services/filetypes.service.ts` | brain[IngestionFileTypesLibraryControllerService] |
| `services/gebo-ai-modules.service.ts` | ORPHAN[GeboModulesConfigControllerService] |
| `services/pluggable-knowledge-base-admin-tree-search.service.ts` | brain[KnowledgeBaseControllerService,ProjectsControllerService] |
| `services/pluggable-project-endpoint.ts` | ORPHAN[GeboModulesConfigControllerService] |

#### HOST

| File | Target `@Gebo.ai/<lib>` [ services ] |
|---|---|
| `HOST/src/app/app-menu-provider.service.ts` | brain[GeboMcpServerUserControllerService]<br>heimdall[GeneratedUserApiKeyControllerService] |


---

## 8. Model-only / infra files (51) — no service import

These import only models, `BASE_PATH`, or `Configuration` from the monolith
stub. No service to retarget; apply §5 (infra) and §6 (models). Listed so none
is missed:

| File (relative to project `src/`) |
|---|
| `HOST/src/app/app.module.ts` |
| `gebo-ai-admin-ui/src/lib/admin-ui/entity-editors/controls/standard-chat-model-settings/standard-chat-model-settings.component.ts` |
| `gebo-ai-admin-ui/src/lib/admin-ui/entity-editors/gebo-ai-agents-network-admin/agent-node.component.ts` |
| `gebo-ai-admin-ui/src/lib/admin-ui/entity-editors/gebo-ai-job-status-viewer/graphic-rendering.ts` |
| `gebo-ai-admin-ui/src/lib/admin-ui/entity-editors/gebo-ai-job-status-viewer/graphic-visualizer.component.ts` |
| `gebo-ai-admin-ui/src/lib/admin-ui/entity-editors/gebo-ai-knowledgebase-admin/filter-avoding-kb-Loops.ts` |
| `gebo-ai-admin-ui/src/lib/admin-ui/entity-editors/gebo-ai-sharepoint-admin/confluence-url.service.ts` |
| `gebo-ai-admin-ui/src/lib/admin-ui/entity-editors/utils/gebo-ai-create-secret-action-request-factory.ts` |
| `gebo-ai-admin-ui/src/lib/admin-ui/main-panels/gebo-dashboard/gebo-embedded-piechart.component.ts` |
| `gebo-ai-admin-ui/src/lib/admin-ui/main-panels/gebo-dashboard/graphics-data.ts` |
| `gebo-ai-admin-ui/src/lib/setup-wizard/llms-easy-wizard/model-classes.ts` |
| `gebo-ai-admin-ui/src/lib/setup-wizard/llms-easy-wizard/step-intro.component.ts` |
| `gebo-ai-admin-ui/src/lib/setup-wizard/llms-easy-wizard/step-summary.component.ts` |
| `gebo-ai-chat-ui/src/lib/gebo-ai-chat-section-component/chat-session.ts` |
| `gebo-ai-reusable-ui/src/lib/architecture/desktop/application-menu-provider.service.ts` |
| `gebo-ai-reusable-ui/src/lib/architecture/desktop/gebo-ai-desktop.component.ts` |
| `gebo-ai-reusable-ui/src/lib/controls/base-entity-editing-component/base-entity-editing.component.ts` |
| `gebo-ai-reusable-ui/src/lib/controls/base-entity-editing-component/operation-status.ts` |
| `gebo-ai-reusable-ui/src/lib/controls/chat-control/chat-info.component.ts` |
| `gebo-ai-reusable-ui/src/lib/controls/chat-control/chat-input-shell.component.ts` |
| `gebo-ai-reusable-ui/src/lib/controls/chat-control/chat-stream-events-display.component.ts` |
| `gebo-ai-reusable-ui/src/lib/controls/chat-control/document-ref.component.ts` |
| `gebo-ai-reusable-ui/src/lib/controls/chat-control/llm-generated-document-ref.component.ts` |
| `gebo-ai-reusable-ui/src/lib/controls/chat-control/reactive-chat.service.ts` |
| `gebo-ai-reusable-ui/src/lib/controls/chat-control/selected-chat-documents.component.ts` |
| `gebo-ai-reusable-ui/src/lib/controls/chat-control/uploaded-document-ref.component.ts` |
| `gebo-ai-reusable-ui/src/lib/controls/chat-model-use-component/chat-model-use.component.ts` |
| `gebo-ai-reusable-ui/src/lib/controls/choose-documents-panel/documents-list-panel.component.ts` |
| `gebo-ai-reusable-ui/src/lib/controls/content-reindex-schedule/periods-base.component.ts` |
| `gebo-ai-reusable-ui/src/lib/controls/content-reindex-schedule/time-set.component.ts` |
| `gebo-ai-reusable-ui/src/lib/controls/content-selection-filter-component/content-selection-filter-criteria.component.ts` |
| `gebo-ai-reusable-ui/src/lib/controls/content-selection-filter-component/validate-content-selection-filter-criteria.ts` |
| `gebo-ai-reusable-ui/src/lib/controls/content-viewer/code-editor-wrapper.component.ts` |
| `gebo-ai-reusable-ui/src/lib/controls/content-viewer/download-link-wrapper.component.ts` |
| `gebo-ai-reusable-ui/src/lib/controls/content-viewer/pdf-viewer-wrapper2.component.ts` |
| `gebo-ai-reusable-ui/src/lib/controls/deep-search-control/deep-search.component.ts` |
| `gebo-ai-reusable-ui/src/lib/controls/userspace-files-component/userspace-wizard.ts` |
| `gebo-ai-reusable-ui/src/lib/controls/vfilesystem-selector/vfilesystem-selector.component.ts` |
| `gebo-ai-reusable-ui/src/lib/controls/vfilesystem-selector/vfilesystem-types.ts` |
| `gebo-ai-reusable-ui/src/lib/dashboard/llms-usage-dashboard.component.ts` |
| `gebo-ai-reusable-ui/src/lib/dashboard/workflow-stats-dashboard.component.ts` |
| `gebo-ai-reusable-ui/src/lib/infrastructure/gebo-credentials.ts` |
| `gebo-ai-reusable-ui/src/lib/infrastructure/user-profile/change-password.component.ts` |
| `gebo-ai-reusable-ui/src/lib/infrastructure/user-profile/user-profile.component.ts` |
| `gebo-ai-reusable-ui/src/lib/notifications/notification.component.ts` |
| `gebo-ai-reusable-ui/src/lib/notifications/root-notification.service.ts` |
| `gebo-ai-reusable-ui/src/lib/services/auth-interceptor.service.ts` |
| `gebo-ai-reusable-ui/src/lib/services/build-gebo-url.service.ts` |
| `gebo-ai-reusable-ui/src/lib/services/enriched-child.ts` |
| `gebo-ai-reusable-ui/src/lib/services/gebo-backends-list.service.ts` |


---

## 9. Execution plan

1. **Prerequisites (blocking).** Resolve the 3 UI-referenced orphans (§3.3):
   assign an owner and expose the controller on that client, or add a gateway
   route. Add `@Gebo.ai/microservices-clients` + the 21 `@Gebo.ai/*` packages to
   the UI’s dependencies and tsconfig `paths`.
2. **Root wiring (§2).** Swap `ApiModule`+`BASE_PATH` for
   `MicroservicesClientsModule.forRoot({ baseUrl: getBaseUrl() })` in
   `app.module.ts`. App still builds against the monolith backend because the
   topology endpoint answers the same on both shapes.
3. **Infra (§5).** Convert every `BASE_PATH` consumer to
   `GeboClientsTopologyService`. Do this early — the auth interceptor and URL
   builders underlie everything.
4. **Clean 1:1 files first (§3.1).** Single-target files are pure import-source
   edits; move them per microservice, `brain` and `heimdall` first (they cover
   the bulk).
5. **Duplicated-controller files (§3.2).** Apply Rules H / A; keep the
   `JobLauncher`(handler) vs `JobStatus`(tyr) split.
6. **Multi-microservice files (§4) and models (§6).** Split imports by target;
   let `tsc` flag the model-identity seams and fix them one at a time.
7. **Delete the monolith dependency.** When no UI `.ts` imports
   `@Gebo.ai/gebo-ai-rest-api`, remove it from `package.json` / tsconfig paths.

## 10. Verification

- **Grep gate:** `grep -rl "@Gebo.ai/gebo-ai-rest-api" gebo.ui/projects gebo.ui/src`
  returns nothing (per project, as a burn-down).
- **Build gate:** `ng build` each library + the host app; the TypeScript
  compiler is the model-identity checklist (§6).
- **Runtime gate:** against a real microservices cluster
  (`dockers/gebo.microservices`), confirm each retargeted screen hits the
  expected per-service context (`/brain`, `/heimdall`, `/sharepoint`, …) via the
  topology-resolved base paths — and against the monolith image, that the same
  build still works (one topology answer, both shapes).

## Appendix — target library ↔ package ↔ microservice

| library dir | package | service id (`GeboMicroservices.*`) |
|---|---|---|
| `gebo-brain-api` | `@Gebo.ai/brain` | `brain_gebo_ai` |
| `gebo-heimdall-api` | `@Gebo.ai/heimdall` | `heimdall_gebo_ai` |
| `gebo-tyr-api` | `@Gebo.ai/tyr` | `tyr_gebo_ai` |
| `gebo-vectorizator-api` | `@Gebo.ai/vectorizator` | `vectorizator_gebo_ai` |
| `gebo-graphicator-api` | `@Gebo.ai/graphicator` | `graphicator_gebo_ai` |
| `gebo-chunker-api` | `@Gebo.ai/chunker` | `chunker_gebo_ai` |
| `gebo-filesystem-api` | `@Gebo.ai/filesystem` | `filesystem_gebo_ai` |
| `gebo-git-api` | `@Gebo.ai/git` | `git_gebo_ai` |
| `gebo-uploads-api` | `@Gebo.ai/uploads` | `uploads_gebo_ai` |
| `gebo-userspace-api` | `@Gebo.ai/userspace` | `userspace_gebo_ai` |
| `gebo-sharepoint-api` | `@Gebo.ai/sharepoint` | `sharepoint_gebo_ai` |
| `gebo-confluence-api` | `@Gebo.ai/confluence` | `confluence_gebo_ai` |
| `gebo-jira-api` | `@Gebo.ai/jira` | `jira_gebo_ai` |
| `gebo-aws-s3-api` | `@Gebo.ai/awss3` | `aws_s3_gebo_ai` |
| `gebo-googledrive-api` | `@Gebo.ai/googledrive` | `googledrive_gebo_ai` |
| `gebo-mcpclient-api` | `@Gebo.ai/mcpclient` | `mcpclient_gebo_ai` |
| `gebo-webdav-api` | `@Gebo.ai/webdav` | `webdav_gebo_ai` |
| `gebo-integration-api` | `@Gebo.ai/integration` | `integration_gebo_ai` |
| `gebo-fulltextor-api` | `@Gebo.ai/fulltextor` | `fulltextor_gebo_ai` |
| `gebo-gateway-api` | `@Gebo.ai/gateway` | `gateway_gebo_ai` |
| `gebo-eureka-api` | `@Gebo.ai/eureka` | `eureka_gebo_ai` |

> Note: the aws-s3 library dir is `gebo-aws-s3-api` but its package is
> `@Gebo.ai/awss3` (no hyphen) — import from `@Gebo.ai/awss3`.
