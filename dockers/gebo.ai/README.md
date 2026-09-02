![Gebo.ai image logo](https://raw.githubusercontent.com/geboai/Gebo.ai/develop/gebo.ui/projects/gebo-ai-reusable-ui/src/assets/Gebo-1000.png)

# Gebo.ai, The open source Enterprise AI vendor agnostic platform (visit https://gebo.ai)

**Enterprise RAG · AI Agents · A2A & MCP Interoperability · Deep Search · Cloud or On-Premise**

Gebo.ai is an open-source Enterprise AI and retrieval-augmented generation platform designed to connect company knowledge, collaboration systems and business information to modern Large Language Models.

It is a **No AI vendor lock-in** alternative to single-vendor AI platforms: Gebo.ai can work with cloud AI services, European AI providers, privately hosted models and OpenAI-compatible inference servers, while connecting to widely used enterprise systems.

- **Website:** https://gebo.ai
- **Documentation:** https://gebo.ai/documents/
- **GitHub:** https://github.com/geboai/Gebo.ai
- **YouTube:** https://www.youtube.com/@GeboSystem
- **Downloads:** https://gebo.ai/downloads/

---

## About this Docker image

`geboai/gebo.ai` is the main Gebo.ai application image.

It is intended for **Docker Compose and structured deployments**, where Gebo.ai and the required infrastructure services run as separate containers.

For the fastest all-in-one installation, with MongoDB, Qdrant, Neo4j and OpenSearch bundled in a single container, use:

```text
geboai/easyinstall.gebo.ai
```

| Image | Recommended use |
|---|---|
| `geboai/gebo.ai` | Docker Compose, persistent installations and structured deployments |
| `geboai/easyinstall.gebo.ai` | Fast evaluation and compact all-in-one installations |

---

# Quick start with Docker Compose

Use the **canonical Docker Compose configuration maintained in the Gebo.ai GitHub repository**.

Do not copy an old Compose file from documentation or previous releases: the repository version contains the current persistence, infrastructure and observability configuration.

## Linux

You do not need to clone the whole repository. Create a folder, download the current `docker-compose.yml` into it, and start the stack:

```bash
mkdir gebo.ai && cd gebo.ai
curl -O https://raw.githubusercontent.com/geboai/Gebo.ai/develop/dockers/gebo.ai/docker-compose.yml
docker compose pull
docker compose up -d
```

Then open:

```text
http://<your-server-ip>:12999/
```

## Docker Desktop for Windows

A Windows-specific Compose configuration is maintained in the repository. As on Linux, you do not need to clone it — create a folder, download the current Windows `docker-compose.yml` into it, and start the stack:

```powershell
mkdir gebo.ai; cd gebo.ai
curl.exe -O https://raw.githubusercontent.com/geboai/Gebo.ai/develop/dockers/gebo.ai/windows/docker-compose.yml
docker compose pull
docker compose up -d
```

Then open `http://localhost:12999/`.

For complete installation options visit:

https://gebo.ai/downloads/

On the first startup, create the local administrative account and then configure AI providers, enterprise data sources, users, groups and chatbots from the Gebo.ai administration interface.

First-start documentation:

https://gebo.ai/first-gebo-ai-server-first-startup-step-creating-admin-account/

---

# What the Docker Compose deployment includes

The current monolithic Docker Compose deployment includes:

- **Gebo.ai**
- **MongoDB** — application state, users, configuration and chat history
- **Qdrant** — vector database
- **Neo4j** — GraphRAG / knowledge graph storage
- **OpenSearch** — full-text search
- **OpenTelemetry Collector** — telemetry collection
- **Prometheus** — metrics
- **Grafana Tempo** — distributed traces
- **Grafana** — dashboards

The official Compose configuration uses **Docker named volumes** for persistent application and infrastructure data.

---

# Enterprise AI without vendor lock-in

Gebo.ai lets organizations choose the AI infrastructure that best fits each workload instead of tying company knowledge to a single AI provider.

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

- chat models
- embedding models
- reranking models
- image-generation models
- speech-to-text / transcription models
- text-to-speech models

This makes it possible to combine public cloud APIs, private cloud infrastructure, European providers and fully on-premise models in the same platform.

---

# Enterprise RAG and company knowledge

Gebo.ai can build AI-searchable knowledge bases from the systems your organization already uses.

Supported information sources include:

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

Administrators can create multiple knowledge bases, schedule document discovery and AI re-indexing/embedding jobs, monitor ingestion activity and control access per user or group.

---

# AI Agents

Gebo.ai is not limited to conventional RAG chat.

Chatbots can use configurable **tools and functions** and can work with specialized AI agents to combine enterprise knowledge with external capabilities.

Typical capabilities include:

- enterprise knowledge retrieval
- web search
- Deep Search
- MCP tools (call external MCP servers, or expose Gebo.ai as one)
- **A2A agent interoperability** — import external Agent2Agent agents, and export your own agents or entire agent networks
- document analysis
- multimodal interaction
- specialized searching agents
- multi-source information gathering

Through the open **Agent2Agent (A2A) protocol**, Gebo.ai interoperates with other agent platforms in **both directions**: it can consume external A2A agents as tools and network participants, and publish its own agents — a single agent or a whole network — as standards-compliant, opaque A2A agents with their own Agent Card. A2A interoperability is **admin-enabled and secure by default**, so nothing is exposed until you choose to expose it.

Access to chatbots and knowledge bases can be granted individually to users and groups.

---

# Model Context Protocol — MCP

Gebo.ai supports **Model Context Protocol (MCP)** in both directions.

It can:

- connect to external **MCP servers**
- expose MCP tools to configured chatbots and agents
- use MCP tools together with RAG and enterprise knowledge
- expose Gebo.ai itself as an **MCP server**

This allows Gebo.ai to operate as an integration layer between enterprise information, AI applications and the wider MCP ecosystem.

---

# Enterprise Deep Search

Deep Search works on a complex question through multiple research steps instead of relying on a single retrieval.

A Deep Search can:

1. break the question into multiple searches;
2. execute searches in parallel;
3. query multiple sources the user is authorized to access;
4. open and analyze the retrieved information;
5. discard irrelevant evidence;
6. consolidate the findings;
7. produce a final answer with references.

Available sources include:

- Gebo.ai company knowledge bases
- **Atlassian Confluence**
- **Atlassian Jira**
- **Microsoft SharePoint / OneDrive**
- **Google Workspace / Drive**
- the **Web**

The user chooses which available sources participate in each search, while the administrator controls which data sources each user or group is allowed to use.

---

# Web Search

A web-search provider can be configured from the Gebo.ai administration interface.

Supported providers include:

- **Google Programmable Search**
- **Tavily**
- **Brave Search**
- **SerpApi**
- **SearXNG**

A self-hosted SearXNG instance can be used by organizations that prefer not to send search queries to an external search vendor.

The configured web-search capability is available:

- as a tool callable during normal chat;
- as a Deep Search source;
- to specialized searching agents.

Depending on the selected provider, AI-generated searches can also control parameters such as recency, content type, country, language, safe-search level and underlying search engine.

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

# Authentication and access control

Gebo.ai supports local users/groups and enterprise Single Sign-On.

OAuth2 / OpenID Connect integrations include:

- **Microsoft Entra**
- **Google**
- **AWS Cognito**
- **Keycloak / generic OAuth2**

Administrators can independently grant users and groups access to chatbots, knowledge bases and enterprise information sources.

---

# GraphRAG

Gebo.ai includes **experimental GraphRAG capabilities**.

Configured language models can extract knowledge graphs from enterprise information and persist graph data in **Neo4j**, allowing graph-based knowledge representation to complement conventional vector retrieval.

---

# LLM usage monitoring

Gebo.ai includes built-in dashboards for AI usage monitoring.

Usage can be analyzed by:

- provider
- model
- model type
- user
- month
- number of calls
- token consumption

Tracked model types include chat, embedding, image generation, reranking, text-to-speech and transcription.

Administrators can inspect organization-wide activity while users can inspect their own usage.

---

# Security & compliance capabilities

Gebo.ai includes built-in capabilities designed to assist enterprise security, traceability and compliance workflows.

## Security audit logging — Wazuh / SIEM compatible

Security-relevant events are written to a dedicated append-only JSON Lines audit trail.

Audited activity includes:

- local and SSO login/logout attempts;
- LLM configuration changes;
- LLM invocation metadata;
- API-key and secret lifecycle changes;
- third-party integration changes;
- user administration.

LLM audit events record invocation metadata such as model, provider, outcome and latency — **not prompt or response content**.

The audit trail is designed for **Wazuh / SIEM integration**.

## Records of processing and data-flow register

The administration interface includes a live records-of-processing and data-flow register designed to assist with **GDPR and NIS2-related governance workflows**.

It can represent:

- data sources;
- transformation components;
- retaining stores;
- external providers;
- personal-data scope;
- retention and erasure information;
- flows between components;
- security-audit status.

The register includes an interactive data-flow graph and CSV export.

These capabilities assist compliance activities; using Gebo.ai does not by itself constitute legal or regulatory compliance.

Documentation:

- Security & compliance: https://github.com/geboai/Gebo.ai/blob/develop/docs/security-and-compliance.md
- Wazuh integration: https://github.com/geboai/Gebo.ai/blob/develop/docs/wazuh-integration.md

---

# Observability

The official Docker Compose deployment includes an observability stack based on:

- **OpenTelemetry Collector**
- **Prometheus**
- **Grafana Tempo**
- **Grafana**

Gebo.ai uses **Micrometer** and **Spring Boot Actuator** for metrics and **OpenTelemetry / OTLP** for distributed tracing.

Application and JVM metrics include areas such as:

- JVM health and memory;
- HTTP traffic;
- application/service activity;
- message-routing metrics in distributed deployments.

Grafana is pre-provisioned with Prometheus and Tempo data sources and a starter Gebo.ai dashboard.

With the current monolithic Compose configuration, Grafana is bound to the Docker host loopback interface on port `3000`.

```text
http://localhost:3000/
```

Expose or proxy it explicitly if remote access is required.

Infrastructure observability is independent from the built-in Gebo.ai **LLM usage dashboards**.

---

# Persistence and backups

The official monolithic Docker Compose configuration uses **named volumes** for the application and infrastructure state that must survive container recreation and upgrades.

Persistent data includes:

- users and application configuration;
- knowledge-base metadata;
- chat history;
- uploaded/session documents;
- local content mirrors;
- document caches and chunks;
- OAuth tokens;
- vector embeddings;
- GraphRAG data;
- OpenSearch indexes;
- security audit logs;
- observability history.

## Critical backup rule

`GEBO_WORK_DIRECTORY` and MongoDB form **one logical backup unit**.

The work directory contains durable files and documents while MongoDB contains references and indexes pointing to those files.

> **Always back up and restore the Gebo.ai work volume and MongoDB together.**

For the current volume layout, backup procedures, migration from older anonymous volumes and upgrade instructions, read:

https://github.com/geboai/Gebo.ai/blob/develop/dockers/PERSISTENCE.md

## Updating the Compose deployment

Normally:

```bash
docker compose pull
docker compose up -d
```

Named volumes survive container recreation.

> **Never run `docker compose down -v` on a live installation unless you intentionally want to delete its persistent Docker volumes and data.**

---

# Deployment options

Gebo.ai can be deployed in several forms:

| Deployment | Intended scenario |
|---|---|
| `easyinstall.gebo.ai` | Single-container evaluation / compact installation |
| `gebo.ai` + Docker Compose | Persistent monolithic deployment |
| Docker Compose microservices | Distributed service architecture |
| Kubernetes / Helm | Orchestrated microservices deployment |

The distributed architecture separates capabilities into independently deployable services for AI orchestration, vectorization, GraphRAG, document processing and enterprise connectors, together with service discovery, gateway and messaging infrastructure.

Current deployment definitions:

- Monolith Docker Compose: https://github.com/geboai/Gebo.ai/tree/develop/dockers/gebo.ai
- Microservices Docker Compose: https://github.com/geboai/Gebo.ai/tree/develop/dockers/gebo.microservices
- Kubernetes / Helm: https://github.com/geboai/Gebo.ai/tree/develop/deploy/helm/gebo-microservices

For Kubernetes and microservices deployments, follow the current repository documentation and persistence notes rather than assuming the monolithic Docker Compose storage model applies unchanged.

---

# For developers, software architects and software companies

Gebo.ai is also an open-source foundation for companies building Enterprise AI solutions.

The project is built with technologies including:

- **Java**
- **Spring Boot**
- **Spring AI**
- **Angular**
- **PrimeNG**
- MongoDB
- Qdrant
- Neo4j
- OpenSearch
- OpenTelemetry

The same codebase can operate as a monolithic application or as a distributed microservices architecture.

Source code and build information:

https://github.com/geboai/Gebo.ai

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
- **Docker Hub — main image:** https://hub.docker.com/r/geboai/gebo.ai
- **Docker Hub — Easy Install:** https://hub.docker.com/r/geboai/easyinstall.gebo.ai
- **YouTube:** https://www.youtube.com/@GeboSystem

---

**Own your data. Choose your models. Connect your enterprise knowledge.**
