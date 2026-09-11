# Declaring the knowledge base hierarchy in `application.yml`

Every content system and data source Gebo.ai can ingest from is normally created by an admin in
the UI and stored in Mongo. A deployment can instead **declare** them in its own
`application.yml`, so that an install comes up already wired to the systems it is meant to read —
no click-through, no per-environment manual step, and the whole configuration under review in
version control alongside the rest of the deployment.

This document covers the whole hierarchy — knowledge bases, projects, secrets, content systems and
data sources — how they reference each other, and a complete worked example for every handler that
supports it.

For the shipped defaults and the rest of the file, see
[`APPLICATION-YML-ADMIN-MANUAL.md`](APPLICATION-YML-ADMIN-MANUAL.md).

---

## 1. The five layers and how they reference each other

```
ai.gebo.knowledgebases[].code  ◄──────────────────┐
                                                  │  referenced by
ai.gebo.projects[].rootKnowledgeBaseCode  ────────┘
                 │ .code  ◄───────────────────────┐
                                                  │  referenced by
ai.gebo.secrets.config.<type>[].code  ─────┐      │
                                           │      │
ai.gebo.<handler>.systems[].secretCode ◄───┘      │
                        │ .code  ──────────┐      │
                                           │      │
ai.gebo.<handler>.datasources[].systemCode ┘      │
                            │ .parentProjectCode ─┘
```

Five keys, each referencing another **by code**, so a deployment can describe the whole hierarchy:

| Layer | Key | What it holds |
|---|---|---|
| **Knowledge base** | `ai.gebo.knowledgebases` | the top of the hierarchy; what a project belongs to |
| **Project** | `ai.gebo.projects` | what a data source feeds, inside a knowledge base |
| **Secret** | `ai.gebo.secrets.config.<type>` | the credential itself |
| **System** | `ai.gebo.<handler>.systems` | the connection — a server, a bucket, a tenant — plus the *code* of the secret it authenticates with |
| **Data source** | `ai.gebo.<handler>.datasources` | a scoped ingestion against a system: which paths, into which project |

Here is the whole chain for one WebDAV share, end to end:

```yaml
# 0. where the documents land
ai.gebo:
  knowledgebases:
    - code: COMPANY-KB
      description: Company knowledge base
      accessibleToAll: true
  projects:
    - code: COMPANY-DOCS                    # ◄── referenced below
      description: Corporate documents
      rootKnowledgeBaseCode: COMPANY-KB     # ──► the knowledge base above
      accessibleToAll: true

# 1. the credential
ai.gebo.secrets.config:
  username-password:
    - code: webdav-service-account          # ◄── referenced below
      description: WebDAV ingestion account
      context-code: SYSTEMS
      secret:
        username: gebo
        password: ${WEBDAV_PASSWORD}

# 2. the connection, authenticating with that secret
ai.gebo.webdav:
  systems:
    - code: corporate-dav                   # ◄── referenced below
      description: Corporate WebDAV share
      baseUri: https://dav.example.com/remote.php/dav
      webdavAuthType: BASIC
      secretCode: webdav-service-account    # ──► the secret above

# 3. what to ingest through that connection, and into which project
  datasources:
    - code: corporate-policies
      description: Policy library
      systemCode: corporate-dav             # ──► the system above
      parentProjectCode: COMPANY-DOCS       # ──► the project above
      synchPeriodically: true
      paths:
        - path: https://dav.example.com/remote.php/dav/files/admin/Policies
          folder: true
```

**The references need not stay inside the file.** A declared system may name a secret an admin
created in the UI, and a data source created in the UI may name a declared system. The codes are
resolved through the same services either way; nothing distinguishes a declared code from a stored
one at the point of use.

**A reference is resolved when it is used, not at startup.** A `secretCode` that names nothing is
found when the handler opens a connection, and surfaces as
`Unkown secret with code=>…`, not as a boot failure. A `systemCode` naming no system is found at
the first ingestion. Only the *shape* of a declaration is checked at startup — see §11. Verify a
new chain by pressing **Test** or **Publish** on it once, rather than by a clean boot.

## 2. Why systems and data sources behave differently

A **system** is served straight from the configuration. Its handler's DAO merges the declared list
with the stored records, and every read path — ingestion, browsing, the admin list — goes through
that DAO.

A **data source** is *written into the module's endpoint repository at startup*. It has to be:
publishing, the central scheduler and `JobLauncherController` all resolve an endpoint through
`IGPersistentObjectManager`, and none of those paths knows about a handler DAO. A data source that
existed only in memory would list and browse, then fail the moment anything tried to ingest it.
Seeding makes it a real record, and every path works on it unchanged.

So: **editing a declared system takes effect on restart; editing a declared data source takes
effect on restart and rewrites its stored record.**

## 3. Rules that apply to everything declared

**The declaration wins.** A code named in the file is the deployment's answer for that code. A
declared secret is resolved before the vault and before Mongo. A declared system displaces a
stored record carrying the same code. A declared data source overwrites the stored record — but
only if that record is itself `readonly`, i.e. one the seeder wrote on an earlier boot. If an
admin created a data source with that code through the UI, **the startup fails** naming it, rather
than throwing away work nobody asked to lose.

**Everything declared is read-only.** Save and delete are disabled on it in the admin UI, and the
backend refuses insert, update and delete regardless of the client. Publish stays enabled — see
§9. To change a declared entry, edit the file and restart.

**A broken declaration fails the boot, not the first ingestion** — as far as its shape can be
checked without connecting to anything. See §11.

**Prefer `${ENV_VAR}` to literals** for anything secret. The value is read by Spring's normal
property resolution, so a password can come from the environment, a mounted file, or any other
property source, and the file itself stays safe to commit.

---

## 4. The hierarchy — `ai.gebo.knowledgebases` and `ai.gebo.projects`

Both are written into Mongo at startup, exactly like data sources and for the same reason:
a knowledge base and a project are resolved by code through `IGPersistentObjectManager` from
everywhere — ingestion, the scheduler, browsing, the ACL resolver — and none of those paths
consults a configuration bean.

They are seeded **top down**: knowledge bases, then projects, then data sources, since each level
names the one above it. A declared project may name a knowledge base an admin created, and a
declared data source may name a project an admin created; the ordering only guarantees that what
is declared *in the same file* already exists when the level below it is written.

| Key | Fields |
|---|---|
| `ai.gebo.knowledgebases` | `code` (required), `description`, `accessibleToAll`, `accessibleUsers`, `accessibleGroups`, `parentKnowledgebaseCode`, `knowledgeBaseReferences`, `projectsReferences`, `embeddingModelReferences`, `objectSpaceType` |
| `ai.gebo.projects` | `code` (required), `description`, `rootKnowledgeBaseCode`, `parentProjectCode`, `accessibleToAll`, `accessibleUsers`, `accessibleGroups`, `objectSpaceType` |

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

Both carry `readonly` and follow the rules of §3: an admin-created record of the same code fails
the startup rather than being overwritten, and a record whose declaration is removed has its
marker cleared rather than being deleted — deleting a project or knowledge base means disposing of
everything beneath it, which a repository write at startup cannot do.

## 5. Declaring secrets — `ai.gebo.secrets.config`

One list per secret type. Each entry carries the metadata a stored secret keeps on its record —
`code`, `description`, `context-code` — plus the credential itself nested under `secret`.

| Entry field | Meaning |
|---|---|
| `code` | **required**, unique across every list; what a `secretCode` references |
| `description` | shown in the admin surface |
| `context-code` | a free-form grouping label, not a fixed vocabulary; `SYSTEMS` is the convention for content-system credentials |
| `secret` | **required**; the fields depend on the list it is under, below |

### 5.1 The types and their fields

| List key | `secret` fields | Used by |
|---|---|---|
| `username-password` | `username`, `password` | Confluence on-premise, WebDAV basic/digest/NTLM, Git |
| `token` | `user`, `token` | Confluence Cloud, Jira, WebDAV bearer |
| `ssh-key` | `email`, `key`, `pub`, `passphrase` | Git over SSH |
| `oauth2-standard` | `providerName`, `clientId`, `secret`, `scopes`, `customAttributes` | SharePoint / OneDrive |
| `oauth2-google` | `uid`, `token`, `location`, `projectId`, `scopes` | Google OAuth2 flows |
| `google-cloud-json-credentials` | `jsonContent`, `delegatedUser` | Google Drive |
| `aws-connection` | `accessKeyId`, `secretAccessKey`, `region` | AWS S3 |
| `custom-secret` | `customContentDescription`, `content`, `contentType` | anything else |

Every field marked required on the underlying model is validated at startup, so a
`username-password` declared without a password stops the boot rather than authenticating with
half a credential.

```yaml
ai.gebo.secrets.config:
  username-password:
    - code: webdav-service-account
      description: WebDAV ingestion account
      context-code: SYSTEMS
      secret:
        username: gebo
        password: ${WEBDAV_PASSWORD}
  token:
    - code: jira-api-token
      context-code: SYSTEMS
      secret:
        user: integration@example.com
        token: ${JIRA_API_TOKEN}
  aws-connection:
    - code: aws-ingestion-account
      context-code: SYSTEMS
      secret:
        accessKeyId: ${AWS_ACCESS_KEY_ID}
        secretAccessKey: ${AWS_SECRET_ACCESS_KEY}
        region: EU_SOUTH_1
```

### 5.2 Resolution order, and what it means for you

A code is resolved in this order, first hit wins:

1. **the declarations** in this file;
2. the **external vault**, when one is active;
3. the **Mongo** secrets store — the secrets created in the admin UI.

Two consequences worth knowing. A declared code cannot be displaced by anything written later —
that is the point. And **when an external vault is active the Mongo store is not consulted at
all**, so in a vault deployment a credential is either declared here or held in the vault.

### 5.3 What cannot be declared

`OAUTH2_AUTHORIZED_CLIENT` has no list. It holds the access and refresh tokens the server obtains
and renews on a user's behalf — a runtime artefact, not a deployment setting — and a read-only
declaration of one could never work, because the first refresh would be refused.

### 5.4 Read-only, for real

A declared secret is refused by every write path — create, update and delete alike. Rotation means
editing this file and restarting, not a UI action. §6.2 of the admin manual covers the cluster
behaviour and the migration rules in full.

---

## 6. Fields every system entry accepts

From `GBaseObject` and `GContentManagementSystem`, in addition to the handler-specific fields
listed per handler below:

| Field | Type | Meaning |
|---|---|---|
| `code` | String | **required**, unique per handler; what a `systemCode` references |
| `description` | String | shown in the admin surface |
| `baseUri` | String | the server root, where the handler connects by URL |
| `contentManagementSystemType` | String | implied by the handler; only Git needs it |
| `readonly` | boolean | forced to `true` for declared systems; do not set it |
| `usedCapabilities` | List | capability roles, rarely set by hand |

`contentManagementSystemType` can be omitted because a handler serving exactly one type applies
it. Only Git, which registers several, requires each entry to name its own.

---

## 7. Content systems, handler by handler

### 7.1 Confluence — `ai.gebo.confluence.systems`

Implemented type `ATLASSIAN-CONFLUENCE`. Handler-specific fields: `confluenceVersion` (`CLOUD` or
`ONPREMISE7X`) and `secretCode`.

**The secret type depends on the version**, because the two APIs authenticate differently: `CLOUD`
reads a `token` secret (`user` = the Atlassian account e-mail, `token` = an API token),
`ONPREMISE7X` reads a `username-password` secret.

```yaml
ai.gebo.secrets.config:
  token:
    - code: confluence-cloud-token
      context-code: SYSTEMS
      secret:
        user: integration@example.com
        token: ${CONFLUENCE_API_TOKEN}

ai.gebo.confluence:
  systems:
    - code: corporate-wiki
      description: Corporate Confluence Cloud
      baseUri: https://example.atlassian.net/wiki
      confluenceVersion: CLOUD
      secretCode: confluence-cloud-token
```

On-premise, with the matching secret type:

```yaml
ai.gebo.secrets.config:
  username-password:
    - code: confluence-onprem-account
      context-code: SYSTEMS
      secret:
        username: gebo-integration
        password: ${CONFLUENCE_PASSWORD}

ai.gebo.confluence:
  systems:
    - code: legacy-wiki
      description: Confluence Data Center 7.x
      baseUri: https://wiki.internal.example.com
      confluenceVersion: ONPREMISE7X
      secretCode: confluence-onprem-account
```

### 7.2 Jira — `ai.gebo.jira.systems`

Implemented type `ATLASSIAN-JIRA`. Handler-specific field: `secretCode`, always a `token` secret.

```yaml
ai.gebo.secrets.config:
  token:
    - code: jira-api-token
      context-code: SYSTEMS
      secret:
        user: integration@example.com
        token: ${JIRA_API_TOKEN}

ai.gebo.jira:
  systems:
    - code: corporate-jira
      description: Corporate Jira
      baseUri: https://example.atlassian.net
      secretCode: jira-api-token
```

### 7.3 SharePoint and OneDrive — `ai.gebo.sharepoint.systems`

Implemented type `sharepoint-module`. Handler-specific fields: `sharepointVersion`
(`CLOUD_VERSION` or `ONPREMISE2019`) and `secretCode` — **both required**, enforced at startup, so
a half-written entry stops the boot rather than failing at the first connection.

The secret must be `oauth2-standard`: the Microsoft Graph client factory refuses anything whose
type is not `OAUTH2_STANDARD`. **The Azure tenant id goes in `customAttributes.tenantId`** — the
factory reads it from there, and a secret without it cannot build a credential.

```yaml
ai.gebo.secrets.config:
  oauth2-standard:
    - code: msgraph-application
      context-code: SYSTEMS
      secret:
        providerName: azure
        clientId: ${AZURE_CLIENT_ID}
        secret: ${AZURE_CLIENT_SECRET}
        customAttributes:
          tenantId: ${AZURE_TENANT_ID}

ai.gebo.sharepoint:
  systems:
    - code: corporate-sharepoint
      description: Corporate SharePoint Online
      baseUri: https://example.sharepoint.com
      sharepointVersion: CLOUD_VERSION
      secretCode: msgraph-application
```

### 7.4 WebDAV — `ai.gebo.webdav.systems`

Implemented type `WEBDAB-CMS`. Handler-specific fields: `webdavAuthType` (`NONE`, `BASIC`,
`DIGEST`, `NTLM`, `BEARER_TOKEN`) and `secretCode`.

**The secret type follows the auth type**: `BASIC`, `DIGEST` and `NTLM` read a `username-password`
secret; `BEARER_TOKEN` reads a `token` secret; `NONE` needs no secret at all.

```yaml
ai.gebo.secrets.config:
  username-password:
    - code: webdav-service-account
      context-code: SYSTEMS
      secret:
        username: gebo
        password: ${WEBDAV_PASSWORD}

ai.gebo.webdav:
  systems:
    - code: corporate-dav
      description: Corporate WebDAV share
      baseUri: https://dav.example.com/remote.php/dav
      webdavAuthType: BASIC
      secretCode: webdav-service-account
```

### 7.5 AWS S3 — `ai.gebo.awss3.systems`

Implemented type `aws-s3-handler`. Handler-specific fields: `awsEndpoint` and `s3SecretCode`,
which must name an `aws-connection` secret — the connection factory checks the type explicitly.

`awsEndpoint` is what points the client at a non-AWS S3 implementation (MinIO, Ceph); leave it
unset for AWS itself and let the `region` in the secret decide.

```yaml
ai.gebo.secrets.config:
  aws-connection:
    - code: aws-ingestion-account
      context-code: SYSTEMS
      secret:
        accessKeyId: ${AWS_ACCESS_KEY_ID}
        secretAccessKey: ${AWS_SECRET_ACCESS_KEY}
        region: EU_SOUTH_1

ai.gebo.awss3:
  systems:
    - code: corporate-buckets
      description: Corporate S3 buckets
      s3SecretCode: aws-ingestion-account
```

### 7.6 Google Drive — `ai.gebo.googleworkspace.systems`

Implemented type `google-drive-handler`. Handler-specific field: `driveAccessSecret`, which must
name a `google-cloud-json-credentials` secret — the credentials factory refuses any other type.

That secret carries the service account JSON **and** the `delegatedUser` it impersonates; without
domain-wide delegation the service account sees nothing.

```yaml
ai.gebo.secrets.config:
  google-cloud-json-credentials:
    - code: google-workspace-service-account
      context-code: SYSTEMS
      secret:
        delegatedUser: ingestion@example.com
        jsonContent: ${GOOGLE_SERVICE_ACCOUNT_JSON}

ai.gebo.googleworkspace:
  systems:
    - code: corporate-drive
      description: Corporate Google Drive
      driveAccessSecret: google-workspace-service-account
```

### 7.7 Git — `ai.gebo.git.config.systems`

Note the different key: Git had this capability before the others and keeps its original prefix
for backward compatibility.

Git is also **the one handler that registers several content types**, so every entry must name its
own `contentManagementSystemType`. Handler-specific fields: `publicAccess` and
`defaultIdentityCode`, the latter being the secret used by any endpoint of this system that does
not name its own identity. A Git identity may be `username-password` or `ssh-key`.

```yaml
ai.gebo.secrets.config:
  ssh-key:
    - code: git-deploy-key
      context-code: SYSTEMS
      secret:
        email: ingestion@example.com
        key: ${GIT_SSH_PRIVATE_KEY}
        pub: ${GIT_SSH_PUBLIC_KEY}
        passphrase: ${GIT_SSH_PASSPHRASE}

ai.gebo.git.config:
  systems:
    - code: DEFAULT_GIT
      contentManagementSystemType: DEFAULT.GIT.CONTENT.HANDLER
      description: Git/Bitbucket/GitHub contents handler
      publicAccess: false
      defaultIdentityCode: git-deploy-key
```

---

## 8. Data sources, handler by handler

Four handlers accept data sources: **WebDAV, AWS S3, SharePoint (OneDrive drives only) and Google
Drive**. They are the ones whose endpoints extend `GVirtualFilesystemProjectEndpoint` and
therefore have a list of paths for a declaration to fill.

### 8.1 Fields every data source entry accepts

| Field | Type | Meaning |
|---|---|---|
| `code` | String | **required**, unique per handler |
| `description` | String | shown in the admin surface |
| `systemCode` | String | **required**; a system from §7 or one created in the UI |
| `parentProjectCode` | String | the knowledge base project this source feeds |
| `paths` | List | **required**, at least one; see below |
| `published` | boolean | default `true` |
| `synchPeriodically` | boolean | a marker only; what schedules a source is `programmedTables` |
| `programmedTables` | List | when the source is re-ingested — see below |
| `openZips` | boolean | walk into archives found in the source |
| `personalData` | boolean | marks the source as carrying personal data, for the GDPR data-flow register |
| `vectorizeOnlyExtensions` | List | restrict vectorization to these extensions |

`programmedTables` is what the central scheduler actually reads. Each entry is a `frequency` and
a list of `times`, and the shape of a time's `timeComponent` follows the frequency:

| `frequency` | `timeComponent` |
|---|---|
| `HOURLY` | `[minutes]` |
| `DAILY` | `[hour, minutes]` |
| `WEEKLY` | `[dayOfWeek, hour, minutes]` |
| `MONTHLY` | `[weekOfMonth, dayOfWeek, hour, minutes]` |

```yaml
      # every night at 02:30
      programmedTables:
        - frequency: DAILY
          times:
            - timeComponent: [2, 30]
```

Each entry of `paths` is:

| Field | Type | Meaning |
|---|---|---|
| `path` | String | **required**, in the handler's own addressing — see each handler below |
| `folder` | boolean | `true` to walk a folder's contents, `false` (default) for a single file |

### 8.2 The path is written in the handler's own addressing

A path is **not** a syntax invented for this file: it is the string the handler itself uses, the
same one the browsing UI stores when an admin assembles a source by clicking. A translation layer
that pretended every remote system had the same notion of "where" could only have expressed the
intersection of all of them.

For WebDAV that is a path under the system's `baseUri`, and for S3 a `bucket/key`. **For Google
Drive and OneDrive it is a pair of opaque ids**, because both address items by id and have no
server-side path at all — a folder *name* will
be refused at startup rather than silently matching nothing. The ids are the ones visible in the
item's URL, and the ones a source built in the UI already carries.

`folder` cannot be derived from the string — no remote call is made while reading the
configuration — so it is declared. What it does with it varies by handler, and it is worth knowing
which:

- **AWS S3** — it selects the encoding, `S3_FOLDER:` against `S3_RESOURCE:`, and those take
  different paths: a prefix is listed, an object is described. Declaring it wrongly therefore
  fails that path, or reads nothing.
- **WebDAV and Google Drive** — the server's own answer decides whether the node is a collection,
  so a wrong flag is largely absorbed.
- **SharePoint / OneDrive** — the navigation carries a cross-check that refuses a node whose kind
  disagrees with the declaration.

That cross-check only runs where the handler attaches a path to its native node, which today is
SharePoint alone; the others log `does not have an associated path` and skip it. So do not rely on
a wrong `folder` flag being reported — get it right, and treat an empty ingestion as the symptom.

### 8.3 WebDAV — `ai.gebo.webdav.datasources`

**Path syntax:** a path **relative to the system's `baseUri`** — the connection belongs to the
system, the data source only says where inside it. A full href is also accepted, for a share that
lives outside that base. An href with nothing above it — the server origin — means the whole share
and must be `folder: true`.

```yaml
ai.gebo.webdav:
  systems:
    - code: corporate-dav
      baseUri: https://dav.example.com/remote.php/dav     # ◄── the paths below resolve against this
      webdavAuthType: BASIC
      secretCode: webdav-service-account
  datasources:
    - code: corporate-policies
      description: Policy library
      systemCode: corporate-dav
      parentProjectCode: COMPANY-KB
      synchPeriodically: true
      paths:
        - path: /files/admin/Policies
          folder: true
        - path: /files/admin/handbook.pdf
          folder: false
```

For a Nextcloud server the disk of an account is `/files/<account>`, so declaring that one path
with `folder: true` ingests everything the account can see.

### 8.4 AWS S3 — `ai.gebo.awss3.datasources`

**Path syntax:** `<bucket>/<key>`, or a bare `<bucket>` for the whole bucket (`folder: true`).

A prefix is given the trailing `/` S3 uses to mean "everything under here" if you leave it out —
without it the listing would also match sibling keys that merely start with the same characters.
Conversely a key ending in `/` declared `folder: false` is refused, since that is a prefix and not
an object.

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

### 8.5 SharePoint — OneDrive drives only — `ai.gebo.sharepoint.datasources`

**Path syntax:** `<driveId>/<itemId>`, or a bare `<driveId>` for the whole drive (`folder: true`).
These are Microsoft Graph ids.

**SharePoint *sites* are not declarable.** Under a site the handler walks lists, list items and
site pages, each with its own identity and its own step type, and a `path`/`folder` pair has
nothing to say about which of them is meant. Site-backed sources stay an admin UI job, where the
browser shows what is actually there. A path beginning `SHAREPOINT-SITE:` is refused at startup
with that explanation.

```yaml
ai.gebo.sharepoint:
  datasources:
    - code: corporate-onedrive-policies
      description: Policies on the corporate OneDrive
      systemCode: corporate-sharepoint
      parentProjectCode: COMPANY-KB
      paths:
        - path: b!xQ3zDriveId/01ABCDEF6Y2GOVW7725BZO354PWSELRRZ
          folder: true
```

### 8.6 Google Drive — `ai.gebo.googleworkspace.datasources`

**Path syntax:** `<driveId>/<fileId>`, or a bare `<driveId>` for the whole shared drive
(`folder: true`). The drive id is a *shared drive*; the file id is the folder or file inside it.

```yaml
ai.gebo.googleworkspace:
  datasources:
    - code: corporate-handbook
      description: Company handbook folder
      systemCode: corporate-drive
      parentProjectCode: COMPANY-KB
      paths:
        - path: 0AJv7q2Xk9mLkUk9PVA/1BxY8sQ2fN7pLmRt3KcWv
          folder: true
```

### 8.7 What is not declarable

| Handler | Systems | Data sources | Why |
|---|---|---|---|
| Confluence, Jira | yes | no | endpoints are space/project selections, not filesystem paths |
| Git | yes | no | endpoints carry a repository URI, branch and build wiring |
| Filesystem, MCP client | no | no | each serves a single system built in code, with no repository behind it |
| Uploads, userspace, integration | no | no | no stored system type of their own |

---

## 9. Scheduling and publishing a declared data source

**Nothing has to be clicked.** Every stored endpoint is rescheduled on context refresh, and the
seeders run before that happens, so a declared source is on the schedule from its first boot. Its
`programmedTables` govern when it runs — a source declared `DAILY` at `[2, 30]` is re-read every
night at 02:30 with no further action.

If its schedule says it should already have run — which is normally true the first time, since the
declaration is newer than the time it names — the scheduler notices and programs a **catch-up run
about 30 seconds out**, so the first ingestion happens shortly after startup rather than waiting
for the next window.

`synchPeriodically` does **not** schedule anything on its own; a source with no `programmedTables`
is ingested only when someone publishes it. Publish does stay enabled on a read-only source, for
when you want a run now: it queues the ingestion and skips the save, because the stored record is
already what the file says.

## 10. Removing a declared entry

**A secret or a system**: delete it from the file and restart. There was never a stored record.

**A data source**: delete it from the file and restart. Its stored record is *not* deleted —
removing an endpoint properly means replicating the removal and dispatching the disposal that
clears its documents and vectors, which a repository write at startup cannot do. Instead the seeder
clears its `readonly` marker and logs a warning:

```
The data source corporate-reports is no longer declared in the configuration: it has been
left in place and is now editable in the admin UI, where it can be deleted with the content
it produced.
```

Save and delete become available on it again, and an admin removes it the normal way.

## 11. Failures, and when they happen

### 11.1 At startup — the shape of a declaration

| Message | Cause |
|---|---|
| `A knowledge base declared in the configuration has no code` / `A project …` | an entry of `ai.gebo.knowledgebases` / `ai.gebo.projects` has no `code` |
| `The knowledge base 'X' is declared in the configuration but one with that code was created through the admin UI` | rename the declaration, or delete the existing record first |
| `A content management system declared in the configuration has no code` | an entry of a `systems` list has no `code` |
| `The content management system code 'X' is declared more than once` | two entries share a code, case-insensitively |
| `A data source declared in the configuration has no code` | an entry of a `datasources` list has no `code` |
| `The data source code 'X' is declared more than once` | two entries share a code |
| `The data source 'X' declares no systemCode` | a data source must read through a system |
| `The data source 'X' declares no path` | a source with no path would connect and read nothing |
| `The data source 'X' declares the path 'P' which this content handler cannot resolve: …` | the path is not valid for that handler; the suffix says what was expected |
| `The data source 'X' is declared in the configuration but a data source with that code was created through the admin UI` | rename the declaration, or delete the UI record first |
| a bean validation error on `ai.gebo.<handler>.systems` or `ai.gebo.secrets.config` | the entry violates its own model constraints — a SharePoint system without `secretCode`, a `username-password` secret without a password |

### 11.2 At connection time — the references

A code that names nothing is **not** a startup failure, because resolving it means asking a
service that may legitimately answer later:

| Symptom | Cause |
|---|---|
| `Unkown secret with code=>X` | a `secretCode` / `s3SecretCode` / `driveAccessSecret` naming no declared, vaulted or stored secret |
| a class cast or "invalid credentials" on connect | the secret exists but is the wrong *type* for that handler — see §12 |
| the system resolves to nothing at ingestion | a `systemCode` on a data source naming no system |

Test a new chain by pressing **Test** or **Publish** on it, not by watching a clean boot.

### 11.3 At runtime — attempts to edit

```
The content management system 'X' is declared in this deployment's configuration and cannot
be changed from the UI: edit it in application.yml instead
```

## 12. Quick reference

| Key | Binding class | Declarable |
|---|---|---|
| `ai.gebo.knowledgebases` | `GeboKnowledgeBaseHierarchyConfig` | knowledge bases |
| `ai.gebo.projects` | `GeboKnowledgeBaseHierarchyConfig` | projects |
| `ai.gebo.secrets.config.<type>` | `GeboStaticSecretsConfig` | secrets |
| `ai.gebo.confluence.systems` | `ConfluenceSystemsConfig` | systems |
| `ai.gebo.jira.systems` | `JiraSystemsConfig` | systems |
| `ai.gebo.sharepoint.systems` | `SharepointSystemsConfig` | systems |
| `ai.gebo.webdav.systems` | `WebdavSystemsConfig` | systems |
| `ai.gebo.awss3.systems` | `AwsS3SystemsConfig` | systems |
| `ai.gebo.googleworkspace.systems` | `GoogleDriveSystemsConfig` | systems |
| `ai.gebo.git.config.systems` | `GitSystemsConfig` | systems |
| `ai.gebo.webdav.datasources` | `WebdavDataSourcesConfig` | data sources |
| `ai.gebo.awss3.datasources` | `AwsS3DataSourcesConfig` | data sources |
| `ai.gebo.sharepoint.datasources` | `SharepointDataSourcesConfig` | data sources |
| `ai.gebo.googleworkspace.datasources` | `GoogleDriveDataSourcesConfig` | data sources |

Which secret type each handler expects — getting this wrong fails at connection time, not at
startup:

| Handler | Field naming the secret | Secret list |
|---|---|---|
| Confluence `CLOUD` | `secretCode` | `token` |
| Confluence `ONPREMISE7X` | `secretCode` | `username-password` |
| Jira | `secretCode` | `token` |
| SharePoint / OneDrive | `secretCode` | `oauth2-standard` (with `customAttributes.tenantId`) |
| WebDAV `BASIC` / `DIGEST` / `NTLM` | `secretCode` | `username-password` |
| WebDAV `BEARER_TOKEN` | `secretCode` | `token` |
| WebDAV `NONE` | — | none |
| AWS S3 | `s3SecretCode` | `aws-connection` |
| Google Drive | `driveAccessSecret` | `google-cloud-json-credentials` |
| Git | `defaultIdentityCode` on the system, `identityCode` on the endpoint | `username-password` or `ssh-key` |
