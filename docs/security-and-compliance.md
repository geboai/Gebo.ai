<!--
/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
 -->
# Security & compliance

Gebo.ai ships two built-in, always-on capabilities aimed at GDPR / NIS2 obligations:
a **security audit trail** (Wazuh / SIEM compatible) and a live **records-of-processing
& data-flow register**. Both are surfaced in the admin **Compliance** screen.

---

## 1. Security audit logging — Wazuh / SIEM compatible

Oriented toward **NIS2** traceability and **GDPR Art. 32** (security of processing)
requirements. Every security-relevant action is captured as a structured event: local &
SSO login/logout attempts (success and failure), LLM configuration changes (all
providers), LLM invocations (metadata only — model, provider, outcome, latency; **never**
prompt or response content), secret and API-key creation/rotation/deletion, 3rd-party
integration (SharePoint, Confluence, Jira, GitHub/GIT, Google Drive, AWS S3, WebDAV, MCP
servers…) configuration and data access, and password/user/group administration.

- **Dedicated, append-only audit trail** — every executable module (the monolith and each
  of the 20+ microservices) routes these events through their own `security-log` logger,
  isolated from the general application log (`additivity=false`), so audit events can't be
  silently lost in application noise or rotated away with it.
- **Wazuh-compatible JSON Lines format** — each log line is a single, complete JSON object
  with no timestamp/level prefix (JSON Lines / NDJSON — the file itself is not one
  parseable JSON document), ready for Wazuh's (or any SIEM's) `json` log_format decoder:
  point your agent at `security-log.jsonl` and start ingesting. **Setup guide:
  [docs/wazuh-integration.md](./wazuh-integration.md)** — where the trail lives in each
  deployment (Docker, `.deb`/`.rpm`, `.msi`, microservices, Kubernetes), plus
  ready-to-install agent/manager configuration in [`deploy/wazuh/`](../deploy/wazuh/)
  (audit rules, agent group config, a containerized agent and the compose override that
  publishes every microservice's trail).
- **Rotation & archiving** — daily and 100MB size-triggered rotation with gzip-compressed
  archives; retention defaults to 365 days / 10GB total, both overridable via the
  `SECURITY_LOG_RETENTION_DAYS` / `SECURITY_LOG_TOTAL_SIZE_CAP` environment variables.
- **Correlated & forensic-ready** — every event carries `correlationId`, `sourceIp`,
  `userId`, `httpMethod` and `requestUri` from the originating request, plus a caller-trace
  (`stackPoint`) for drill-down.
- **Verified against a real Wazuh manager** — the SIEM compatibility claim is not just
  documented: as the last step of the `gebo.ai.app` test suite,
  `WazuhSecurityLogCompatibilityTest` takes the `security-log.jsonl` produced by all the
  other tests, feeds it to `wazuh-logtest` inside a containerized `wazuh/wazuh-manager`,
  and asserts Wazuh's built-in `json` decoder extracts every audit field (and the
  `eventType`/`category`/`action`/`outcome`/`timestamp` taxonomy) unchanged. Skippable with
  `-Dgebo.wazuh.compatibility.test.skip=true`.

The Compliance screen shows this trail as a live **"Security audit logging — Wazuh / SIEM
compatible: up & running"** status (**GDPR Art. 32 · NIS2 Art. 21**).

---

## 2. Records of processing & data-flow register (GDPR Art. 30 · NIS2 Art. 21)

The admin **Compliance** screen builds a live **record of processing activities and
data-flow register** from the components *actually running* (through the message broker),
not from a declared configuration file — the answer to a GDPR Art. 30 / NIS2 Art. 21 audit
for the deployment as it stands.

- **What it maps** — every data source, the engines that transform data on the way, and
  every retaining store (chunk cache, vector store, full-text index, **Neo4j knowledge
  graph**) and external provider (chat / embedding / reranker / transcript / image models
  and web-search providers) the content reaches. Endpoint locators are **credential-free by
  construction** — a credential is referenced only by the code of the secret that guards
  it, never by value.
- **Personal-data scope** — a `personalData` flag set per data source is propagated across
  the flow, so every store and query path fed by a personal-data source is flagged in
  scope. The propagation is computed authoritatively in the backend report, not only in the
  UI, so an export or a downstream consumer sees the same scoped result.
- **Retention & erasure** — each store reports its retention period and the component able
  to erase it, so an **Art. 17** gap (a store with no erasure wired) is visible at a glance.
  Deletion of a knowledge base / project / data source propagates to every store — chunk
  cache, vector store, full-text index and knowledge graph.
- **Graph view & export** — an interactive data-flow graph (localities colour-coded for
  **Art. 44** transfer review, distinct flows laid out as separate trees) plus a one-click
  **CSV export** of the register — one row per store/source/interface — for filing.
- **Embedded security-logging status** — the same screen surfaces the audit trail of
  section 1 as its "up & running" status.

For the design rationale and the model behind the register, see
[docs/DATA-FLOW-METAINFO-ANALISYS.md](./DATA-FLOW-METAINFO-ANALISYS.md).
