![Gebo.ai image logo](https://raw.githubusercontent.com/geboai/Gebo.ai/develop/gebo.ui/projects/gebo-ai-reusable-ui/src/assets/Gebo-1000.png)

# Gebo.ai, The open source Enterprise AI vendor agnostic platform (visit https://gebo.ai)

**Enterprise RAG · AI Agents · Deep Search · MCP · Cloud or On-Premise**

## Easy Install — the complete Gebo.ai platform in one Docker container

`geboai/easyinstall.gebo.ai` packages Gebo.ai together with its required data services into a single all-in-one Docker image.

It is the fastest way to evaluate Gebo.ai or run a compact self-hosted installation without separately deploying MongoDB, Qdrant, Neo4j and OpenSearch.

- **Website:** https://gebo.ai
- **Documentation:** https://gebo.ai/documents/
- **GitHub:** https://github.com/geboai/Gebo.ai
- **YouTube:** https://www.youtube.com/@GeboSystem
- **Downloads:** https://gebo.ai/downloads/

---

# Start in seconds

For a quick evaluation:

```bash
docker run -d \
  --name gebo-ai \
  -p 12999:12999 \
  geboai/easyinstall.gebo.ai
```

Then open:

```text
http://localhost:12999/
```

or, from another computer:

```text
http://<your-server-ip>:12999/
```

Create the first administrative account and configure your AI providers, enterprise information sources, users, groups and chatbots from the Gebo.ai administration interface.

First-start documentation:

https://gebo.ai/first-gebo-ai-server-first-startup-step-creating-admin-account/

> The command above is ideal for evaluation. Docker creates anonymous volumes for the image's persistent paths. For an installation you intend to keep and upgrade, use named volumes as shown below.

---

# Persistent installation

For a durable installation, explicitly name the Docker volumes used by the all-in-one image:

```bash
docker run -d \
  --name gebo-ai \
  -p 12999:12999 \
  -v gebo-home:/opt/gebo.ai/home \
  -v gebo-work:/opt/gebo.ai/work \
  -v gebo-app-logs:/opt/gebo.ai/logs \
  -v gebo-shares:/opt/gebo.ai/shares \
  -v gebo-security-logs:/var/log/gebo.ai \
  -v gebo-mongo:/data/db \
  -v gebo-qdrant:/var/lib/qdrant \
  -v gebo-neo4j:/var/lib/neo4j \
  -v gebo-neo4j-logs:/var/log/neo4j \
  -v gebo-opensearch:/opt/opensearch/data \
  geboai/easyinstall.gebo.ai
```

Named volumes make the installation's durable state explicit and allow the container itself to be replaced during upgrades without intentionally deleting the stored data.

The image declares persistent surfaces for:

### Gebo.ai application data

- `/opt/gebo.ai/home`
- `/opt/gebo.ai/work`
- `/opt/gebo.ai/logs`
- `/opt/gebo.ai/shares`
- `/var/log/gebo.ai`

### Bundled infrastructure

- `/data/db` — MongoDB
- `/var/lib/qdrant` — Qdrant
- `/var/lib/neo4j` — Neo4j data
- `/var/log/neo4j` — Neo4j logs
- `/opt/opensearch/data` — OpenSearch

Complete persistence, backup and migration documentation:

https://github.com/geboai/Gebo.ai/blob/develop/dockers/PERSISTENCE.md

---

# What is included

The Easy Install image contains:

- **Gebo.ai**
- **MongoDB**
- **Qdrant**
- **Neo4j**
- **OpenSearch**

All services run inside the same container, providing the simplest possible installation experience.

Easy Install is designed for:

- product evaluation;
- proof-of-concept environments;
- demonstrations;
- development and testing;
- compact self-hosted installations;
- users who want to start with the smallest operational footprint.

For a structured deployment where infrastructure services run as separate containers, use the main image with the official Docker Compose stack:

```text
geboai/gebo.ai
```

| Image | Recommended use |
|---|---|
| `geboai/easyinstall.gebo.ai` | Fastest all-in-one installation |
| `geboai/gebo.ai` | Docker Compose, persistent and structured deployments |

Main Docker image:

https://hub.docker.com/r/geboai/gebo.ai

---

# Enterprise AI without vendor lock-in

Gebo.ai is an open-source Enterprise AI and retrieval-augmented generation platform designed to connect company knowledge and business systems to modern Large Language Models.

It is a **No AI vendor lock-in** alternative to single-vendor cloud AI platforms.

Supported providers and inference infrastructures include:

- **OpenAI**
- **Anthropic Claude**
- **AWS Bedrock**
- **Google Vertex AI / Gemini** *(experimental / disabled by default where indicated by the application)*
- **xAI Grok**
- **NVIDIA AI**
- **Groq**
- **DeepSeek**
- **Mistral AI**
- **Regolo.ai**
- **OpenRouter**
- **Ollama**
- **vLLM**
- other **OpenAI API-compatible** providers and local inference servers

Most providers support guided configuration, ready-to-use model presets and/or automatic model lookup.

Gebo.ai supports several model roles, including:

- chat models;
- embedding models;
- reranking models;
- image-generation models;
- speech-to-text / transcription models;
- text-to-speech models.

Cloud models, European providers and privately hosted models can coexist inside the same platform.

---

# Enterprise RAG and company knowledge

Create AI-searchable company knowledge bases from the information systems your organization already uses.

Supported sources include:

- **Microsoft OneDrive / SharePoint**
- **Atlassian Confluence**
- **Atlassian Jira**
- **Google Workspace / Google Drive**
- **GitHub**
- **Bitbucket**
- other **Git-compatible** repositories
- company shared filesystems
- **Amazon S3**
- **WebDAV-compatible** repositories

WebDAV support can be used with platforms such as:

- Nextcloud
- ownCloud
- OpenCloud
- Pydio Cells
- Seafile / SeafDAV
- ONLYOFFICE Workspace
- Synology DSM WebDAV Server

Administrators can create multiple knowledge bases, schedule document updates and AI re-indexing/embedding jobs, monitor ingestion activity and control knowledge access per user or group.

---

# AI Agents

Gebo.ai chatbots can use configurable **tools and functions** and can work with specialized AI agents.

This allows assistants to combine:

- enterprise knowledge;
- web search;
- Deep Search;
- MCP tools;
- document analysis;
- multimodal capabilities;
- specialized searching agents;
- multiple internal and external information sources.

Gebo.ai can therefore be used for more than conventional one-shot RAG chat.

---

# Model Context Protocol — MCP

Gebo.ai supports **Model Context Protocol (MCP)** in both directions.

It can:

- connect to external **MCP servers**;
- expose MCP tools to chatbots and agents;
- combine MCP tools with RAG and enterprise knowledge;
- expose Gebo.ai itself as an **MCP server**.

---

# Enterprise Deep Search

Deep Search answers complex questions through multiple research steps.

A Deep Search can:

1. break a question into several searches;
2. execute them in parallel;
3. search multiple sources the user is authorized to access;
4. open and analyze retrieved information;
5. discard irrelevant evidence;
6. consolidate the findings;
7. produce a final answer with references.

Available Deep Search sources include:

- Gebo.ai company knowledge bases
- **Atlassian Confluence**
- **Atlassian Jira**
- **Microsoft SharePoint / OneDrive**
- **Google Workspace / Drive**
- the **Web**

The user chooses which available sources participate in each search, while administrators control source permissions per user and group.

---

# Web Search

A web-search provider can be configured through the Gebo.ai administration interface.

Supported providers include:

- **Google Programmable Search**
- **Tavily**
- **Brave Search**
- **SerpApi**
- **SearXNG**

A self-hosted SearXNG deployment can be used when an organization prefers not to send search queries to a commercial search provider.

Web search can be used:

- as a chatbot tool;
- as a Deep Search source;
- by specialized searching agents.

Depending on the selected provider, AI-generated searches can control parameters such as recency, content type, country, language, safe-search level and underlying search engine.

---

# End-user capabilities

Depending on administrator configuration and permissions, users can:

- chat with general-purpose AI assistants;
- use Enterprise RAG chatbots;
- work with documents uploaded directly into a chat session;
- browse authorized company knowledge bases;
- run Deep Searches;
- use tools and MCP capabilities;
- generate images with configured image models;
- use speech-to-text and text-to-speech capabilities.

Gebo.ai is **multi-user** and its administration and chat interfaces are **multilanguage**.

---

# Users, groups and SSO

Gebo.ai supports configurable users and groups together with enterprise Single Sign-On.

OAuth2 / OpenID Connect integrations include:

- **Microsoft Entra**
- **Google**
- **AWS Cognito**
- **Keycloak / generic OAuth2**

Administrators can control access to individual:

- chatbots;
- knowledge bases;
- enterprise information sources.

---

# GraphRAG

Gebo.ai includes **experimental GraphRAG capabilities**.

Configured AI models can extract knowledge graphs from enterprise information and persist them using **Neo4j**, allowing graph-based knowledge representation to complement conventional vector retrieval.

---

# LLM usage monitoring

Built-in dashboards allow AI usage to be analyzed by:

- provider;
- model;
- model type;
- user;
- month;
- number of calls;
- token consumption.

Administrators can monitor organization-wide usage while users can inspect their own activity.

---

# Security & compliance capabilities

Gebo.ai includes built-in capabilities designed to assist enterprise security, traceability and compliance workflows.

## Security audit logging — Wazuh / SIEM compatible

Security-sensitive activity is recorded in an append-only JSON Lines audit trail.

Audited events include:

- local and SSO login/logout attempts;
- LLM configuration changes;
- LLM invocation metadata;
- API-key and secret lifecycle changes;
- third-party integration changes;
- user administration.

LLM audit events contain invocation metadata such as model, provider, outcome and latency — **not prompt or response content**.

The audit trail is designed for **Wazuh / SIEM integration**.

## Records of processing and data-flow register

The administration interface includes a live records-of-processing and data-flow register designed to assist **GDPR and NIS2-related governance workflows**.

It can represent:

- data sources;
- transformation components;
- retaining stores;
- external providers;
- personal-data scope;
- retention and erasure information;
- data flows between components;
- security-audit status.

The interface includes an interactive data-flow graph and CSV export.

These capabilities assist compliance activities; using Gebo.ai does not by itself constitute legal or regulatory compliance.

Documentation:

- Security & compliance: https://github.com/geboai/Gebo.ai/blob/develop/docs/security-and-compliance.md
- Wazuh integration: https://github.com/geboai/Gebo.ai/blob/develop/docs/wazuh-integration.md

---

# Persistence and backups

The all-in-one container contains several different kinds of durable state.

Persistent data includes:

- users and application configuration;
- knowledge-base metadata;
- chat history;
- chat-session attachments;
- local content mirrors;
- document cache and chunks;
- OAuth tokens;
- security audit logs;
- MongoDB data;
- Qdrant embeddings;
- Neo4j graphs;
- OpenSearch indexes.

## Critical backup rule

MongoDB and the Gebo.ai work directory form **one logical backup unit**.

`/opt/gebo.ai/work` contains durable documents and files while MongoDB contains references and indexes pointing to them.

> **Always back up and restore `gebo-work` and `gebo-mongo` together.**

Losing only the work directory does not create a clean empty installation: it can leave MongoDB with references to files that no longer exist.

For authoritative persistence, migration and backup procedures:

https://github.com/geboai/Gebo.ai/blob/develop/dockers/PERSISTENCE.md

---

# Updating Easy Install

When you use named volumes, the application container can be replaced while the persistent data remains in those volumes.

A typical image refresh is:

```bash
docker pull geboai/easyinstall.gebo.ai
docker stop gebo-ai
docker rm gebo-ai
```

Then recreate the container using **the same named-volume arguments** shown in the persistent installation command above.

Before significant upgrades, back up the persistent data according to the persistence documentation.

> Do not delete the named volumes unless you intentionally want to delete the installation's data.

---

# When should I use `geboai/gebo.ai` instead?

Easy Install deliberately puts the application and databases into one container.

Use the standard `geboai/gebo.ai` Docker Compose deployment when you want:

- MongoDB, Qdrant, Neo4j and OpenSearch as separate services;
- independent service lifecycle and resource management;
- the standard OpenTelemetry / Prometheus / Tempo / Grafana observability stack;
- a more structured deployment topology;
- easier infrastructure-level administration;
- a deployment model closer to distributed production environments.

Official monolithic Docker Compose files:

https://github.com/geboai/Gebo.ai/tree/develop/dockers/gebo.ai

Gebo.ai also provides distributed microservices and Kubernetes / Helm deployment definitions.

---

# Gebo.ai licence

The community/open-source edition is distributed under the **Gebo.ai community license based on Mozilla Public License 2.0 (MPL-2.0) with Data Protection Clauses**.

Please review the canonical licensing documents before redistribution or production use:

- Licence: https://github.com/geboai/Gebo.ai/blob/develop/LICENCE.md
- Origin declaration: https://github.com/geboai/Gebo.ai/blob/develop/ORIGIN.md
- Licence page: https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/

An Enterprise edition with additional capabilities and commercial support is also available.

---

# Resources

- **Website:** https://gebo.ai
- **Documentation:** https://gebo.ai/documents/
- **Downloads:** https://gebo.ai/downloads/
- **GitHub:** https://github.com/geboai/Gebo.ai
- **Docker Hub — Easy Install:** https://hub.docker.com/r/geboai/easyinstall.gebo.ai
- **Docker Hub — main image:** https://hub.docker.com/r/geboai/gebo.ai
- **YouTube:** https://www.youtube.com/@GeboSystem

---

**One container. Your infrastructure. Your models. Your company knowledge.**
