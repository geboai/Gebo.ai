![Gebo.ai image logo](./gebo.ui/projects/gebo-ai-reusable-ui/src/assets/Gebo-1000.png) 
# Gebo.ai, The open source Enterprise AI vendor agnostic platform (visit https://gebo.ai)
This software is an open source enterprise AI and retrieve augmented generation platform that can be installed in every company
to take the most out from their documentation and informations using modern large language models.
It's a "No AI vendor lock-in"  alternative to cloud vendors platform, it can work with almost all cloud or on premise AI infrastructures and connects to widely used enterprise systems. 
## Gebo.ai licence:

The open source version is available under a variation of the Mozilla Public License Version 2.0 (MPL-2.0), 
an enterprise version with more feature and support is also available.  
 
 - [Click here to see the licence](./LICENCE.md)
 - [Click here the ORIGIN declaration](./ORIGIN.md) 
 
## Gebo.ai features:
### Administrative features
 The admin, chat,rag chat,graphrag chat user interfaces are **fully multilanguage** and the application is **fully multiuser**.
 All the following features are fully configurable using the administrative user interface.
 - Configure the large language models to use like:
      - **OpenAI** chatgpt
      - **Anthropic** Claude
      - **AWS Bedrock** (Claude, Amazon Nova, Llama, Mistral & more)
      - **Google** Vertex AI / Gemini (experimental, disabled by default)
      - **XaI** Grok
      - **Nvidia** AI provider
      - **Groq**
      - **Deepseek**
      - **MistralAI**
      - **Regolo.ai** (Italian/European)
      - **OpenRouter.ai** (multi-model router)
      - Almost every local large language model using **Ollama** or **vLLM**       
      - Every provider/local server compatible with **OpenAi API**
 - Configure tools & functions that each llm configuration can use, including **web search** — **Google Programmable Search**, **Tavily**, **Brave Search**, **SerpApi** or a self-hosted **SearXNG** instance, set up from a guided wizard and usable as an LLM tool, as a deep-search data source and as a searching agent — see [Web search, deep search & agents](#web-search-deep-search--agents) below
 - Configure additional AI model types besides chat & embedding models:
    - **Image generation** models (OpenAI, AWS Bedrock, Regolo.ai, OpenRouter.ai & OpenAI-compatible providers)
    - **Text to speech** models (OpenAI, AWS Bedrock, Regolo.ai, OpenRouter.ai)
    - **Speech to text / transcription** models (OpenAI, AWS Bedrock, Regolo.ai, OpenRouter.ai)
    - **Reranking** models to improve retrieve augmented generation relevance (AWS Bedrock, Regolo.ai, OpenRouter.ai, vLLM & OpenAI-compatible providers)
 - Most providers support **guided fast-setup** with ready-to-use model presets and automatic models lookup
 - Connect to **Model Context Protocol (MCP)** servers to give your chatbots extra tools & data sources, or expose Gebo.ai itself as an **MCP server**
 - Configure gebo.ai rag system to access several company documents repository and information sharing tools such as:
    - **Microsoft Onedrive/Sharepoint**
    - **Atlassian Confluence**
    - **Atlassian Jira**
    - **Google Workspaces/Drives** 
    - **GitHub/GIT/Bitbucket** or other **git** compatible servers
    - Company shared filesystems
     - **Amazon AWS S3** buckets
     - **WebDAV** compatible servers (Nextcloud, ownCloud, OpenCloud, Pydio Cells, Seafile/SeafDAV, ONLYOFFICE Workspace, Synology DSM WebDAV Server...)
 - Configure **company single sign** on (SSO) using one of the following oauth2 providers:
 	- **Microsoft Entra**
 	- **Google auth**   
 	- **AWS Cognito**
 	- **KeyCloak** (as Generic oauth2)
 - Configure **GraphRag** features (experimental)
 	- The software can use cheap models provided (on premise or in cloud) to export knowledge graphs persisted with neo4j. 	
 - Create knowledge bases collectioning documents from the previus mentioned system.  
 - Schedule document updates for AI reindexing (embedding) on updates.
 - Monitor embedding batch job.
 - Monitor **LLM usage** with built-in dashboards (admin: every user; user: own usage only) — drill down by provider, model, model type (chat/embedding/image/reranking/TTS/transcription), user and month to track calls/tokens over time.
 - **NIS2-oriented security audit logging**: login/logout, LLM configuration changes, LLM invocations, secrets/API-key/3rd-party-integration changes and user administration are all traced to a dedicated, append-only, **Wazuh-compatible JSON** audit trail — see [Security & compliance](#security--compliance) below.
 - **Compliance / data-flow register** (GDPR Art. 30 · NIS2 Art. 21): a live **record of processing activities** built from the components actually running — data sources, transformation engines, retaining stores and external providers, with per-source personal-data scope, retention & erasure per store, an interactive data-flow graph and CSV export, plus the Wazuh/SIEM security-audit-logging status — see [Security & compliance](#security--compliance) below.
 - Configure company users and groups.      
 - Organize multiple specific Retrieve augmented generation chats for specific company tasks:
    - Examples:
       - Customer support **chatbots to support customer support employees or directly the customers**
       - Tech/Production **productivity chatbots to support employee on mananging internal technical documentation**.
 - Chatbot access can be granted individually to users/groups
 - Knowledge bases can be granted individually to users/groups



### Users features        
 - Chat using chatbots without retrieve augmented generation according to admin config.
 - Chat using chatbots with retrieve augmented generation  according to admin config.
 - Chat with uploaded documents/user documents uploaded in chat session (rag or normal chat sessions).
 - Browse company knowledge bases to select  documents to chat/work with  according to admin config.  
 - Generate **images** directly in chat using configured image generation models.
 - Run a **deep search** from the chat: the platform plans several queries, searches the company knowledge bases **and the web** (through the configured web-search provider), fetches and reads the found pages/documents and streams back a referenced analysis — see [Web search, deep search & agents](#web-search-deep-search--agents).
 - Voice interface (speech to text & text to speech) working with OpenAI provider.   

## How to install Gebo.ai 

You can use docker, docker-compose, download an already configured appliance or install a Ubuntu or windows package, 
visit [https://gebo.ai/downloads/](https://gebo.ai/downloads/)

For the monolith via Docker Compose, use [`dockers/gebo.ai/docker-compose.yml`](./dockers/gebo.ai/docker-compose.yml) on Linux hosts and [`dockers/gebo.ai/windows/docker-compose.yml`](./dockers/gebo.ai/windows/docker-compose.yml) on Docker Desktop for Windows — both keep all data on named volumes so it survives updates/upgrades (see [dockers/PERSISTENCE.md](./dockers/PERSISTENCE.md)).

### Post install configuration procedure:

After you've installed with docker go to http://<your server ip>:12999/ and configure your enterprise rag system account & setup your system.

### What the docker-compose file installs  
The docker-compose file installs the required 
- MongoDB
- Qdrant Vector Database
- Neo4J Graph Database 
- OpenSearch
- geboai/gebo.ai open source version software https://hub.docker.com/r/geboai/gebo.ai  
- An **observability stack** — OpenTelemetry Collector, Prometheus, Tempo and Grafana (pre-provisioned with datasources and a starter dashboard) — see [Observability & monitoring](#observability--monitoring) below

## For devs/software architects/software companies 

This software is build with latest spring boot technologies and the new spring-ai framework, with UI developed in Angular 19+PRIMENG
Is made to accelerate professionals/companies that invested in these technologies to accelerate your own business opportunities 
start building having this as base.

The platform can run either as a single **bootable jar** (monolith) or be deployed as a set of **distributed microservices** (content-handler, brain, vectorizator & graphicator apps) with **Eureka** service discovery and **Hazelcast** clustering for horizontal scalability and high availability.

### Delivery & deployment

| Target | What | Where |
|---|---|---|
| Monolith (single jar) | One bootable Spring Boot fat jar, all controllers in one process | `gebo.apps.parent/gebo.ai.app` |
| Docker Compose microservices | 20 containerized microservices + infra (Mongo, Rabbit, Qdrant, Neo4j, OpenSearch, Eureka, gateway) | `dockers/gebo.microservices/docker-compose.yml` |
| Kubernetes (Helm) | The same microservices stack as Helm charts with per-service install toggles, topology-synced ConfigMaps, and optional in-cluster observability | `deploy/helm/gebo-microservices` |

**Microservices** — the full set of 20+ services (brain, vectorizator, graphicator, chunker, git, filesystem, uploads, userspace, sharepoint, confluence, jira, aws-s3, googledrive, webdav, mcpclient, integration, fulltextor, heimdall, tyr, gateway, eureka) is built with `mvn -P docker,swagger-on clean package jib:buildTar` and deployed via Docker Compose or Helm. Each service exposes its REST surface under a context-path (e.g. `/brain`); per-service Java and Angular client stubs are auto-generated from live OpenAPI specs under `gebo.api.clients/gebo.microservices.clients.parent`.

**Kubernetes / Helm chart** — a production-ready Helm chart at `deploy/helm/gebo-microservices` deploys the entire microservices platform to any Kubernetes cluster. It mirrors the Docker Compose stack and adds:
- **Per-service optional/mandatory install toggles** — disable connectors you don't need (e.g. `graphicator`, `fulltextor`, `jira`); mandatory services (heimdall, brain, tyr, chunker, vectorizator, eureka, gateway) are always rendered.
- **Topology-synced ConfigMaps** — the messaging topology `gebo.microservices.topology` is rendered to match the deployed footprint, so declared topology never drifts from what is deployed.
- **Stateful dependencies** — MongoDB, RabbitMQ, Qdrant, Neo4j, OpenSearch are deployed in-cluster by default, or pointed at external/managed instances.
- **Optional observability** — OpenTelemetry collector, Tempo (traces), Prometheus (metrics), and Grafana (dashboards) can be enabled with `observability.enabled: true`.

```bash
helm install gebo deploy/helm/gebo-microservices \
  --namespace gebo --create-namespace \
  --set image.registry=myregistry.example.com/ \
  --set image.tag=1.0.2.1-SNAPSHOT
```

### Observability & monitoring

Every service (the monolith and each microservice) ships with **Micrometer** + **Spring Boot Actuator**, built once in the shared `gebo.architecture.telemetry` module and pulled in by every starter (`gebo.apps.monolithic.starter`, `gebo.microservices.starter`, `gateway.gebo.ai`):
- `micrometer-registry-prometheus` exposes `/actuator/prometheus` metrics (JVM, HTTP, message-broker routing rate/latency).
- `micrometer-tracing-bridge-otel` + `opentelemetry-exporter-otlp` export distributed traces (including message-broker hops) over OTLP.

**Docker Compose** — both `dockers/gebo.ai/docker-compose.yml` (monolith) and `dockers/gebo.microservices/docker-compose.yml` bundle the full stack out of the box: an `otel-collector`, `prometheus` (scraping every service's `/actuator/prometheus`), `tempo` (trace storage) and `grafana` — provisioned with Prometheus/Tempo datasources and a starter "Gebo overview" dashboard (services up, HTTP request rate/latency, message-broker throughput, JVM memory). Reach it at `http://<host>:3000` after `docker compose up`.

**Kubernetes / Helm** — the same stack is available as an opt-in add-on (`observability.enabled: true`), see [deploy/helm/gebo-microservices/README.md](./deploy/helm/gebo-microservices/README.md#observability-optional).

**LLM usage dashboards** — independent of the infra metrics above, Gebo.ai also tracks per-call LLM usage (tokens, provider, model, model type, calling module, user) and exposes it through dedicated admin/user REST APIs and Angular dashboards — see [Administrative features](#geboai-features) above.

### Web search, deep search & agents

Web search is a first-class connector family, not a hardcoded call to one engine. Every provider is a module under `gebo.systems.parent` extending the same base, `AbstractWebSearchServiceImpl<N extends INativeQueryObject>` (`gebo.architecture.parent/gebo.architecture.search.abstraction.layer`), so all of them return the same `SearchResult`/`SearchResultReference` model, share result aggregation and share the page download & content-type guessing used to actually read what was found.

| Provider | Module | Endpoint called | Options the LLM can steer | Credentials |
|---|---|---|---|---|
| **Google Programmable Search** | `gebo.googlesearch.handler` | `https://www.googleapis.com/customsearch/v1` | query strings only | API key + Programmable Search engine id |
| **Tavily** | `gebo.tavilysearch.handler` | `POST https://api.tavily.com/search` | `search_depth` (basic/advanced), `topic` (general/news), `time_range` | API key (bearer) |
| **Brave Search** | `gebo.bravesearch.handler` | `https://api.search.brave.com/res/v1/web/search` | `freshness` (pd/pw/pm/py), `country`, `safesearch` | subscription token (`X-Subscription-Token`) |
| **SearXNG** (self-hosted) | `gebo.searxngsearch.handler` | your own instance, `format=json` | `categories`, `time_range`, `language` | instance URL + optional bearer token |
| **SerpApi** | `gebo.serpapisearch.handler` | `https://serpapi.com/search.json` | `engine` (google/bing/duckduckgo), `gl`, `hl`, `tbs` | API key |

Providers that expose engine-specific options declare their own **native query type** (`TavilyNativeSearchQuery`, `BraveNativeSearchQuery`, `SearxngNativeSearchQuery`, `SerpapiNativeSearchQuery`): the LLM fills it with **structured output**, so the model chooses recency, topic, country or search depth itself instead of receiving a flat keyword list. Plain providers keep using `WebSearchQueryObject`. A legacy `gebo.bingsearch.handler` module is still built and shipped in `brain`, but it has no configuration controller and no wizard entry — it is not configurable from the UI.

**Setup — exactly one active provider.** The admin **Web search** wizard (`gebo.ui/projects/gebo-ai-admin-ui/src/lib/setup-wizard/web-search-wizard.component.ts`) lists the five providers, and saving one clears the credentials of all the others: the active provider is simply whichever one holds stored credentials, so there is always exactly one web-search tool mounted on the LLMs. Each provider has its own `*SearchConfigurationController` (7 endpoints: status, fast-insert, CRUD, delete), hosted by `brain` in the microservices topology — see [docs/MICROSERVICES-CONTROLLERS.md](./docs/MICROSERVICES-CONTROLLERS.md). The API key is never written in the credentials document: it only carries a `secretCode` pointing into the Gebo secrets store, and every change to it is traced in the security audit log.

The configured provider is then used in **three** distinct ways:

- **As an LLM tool (function calling)** — each handler publishes a `ToolCallback` in the `INTERNET_BROWSING` tools category (`*FunctionCallbackWrapperSourceImpl`), exposed to the models as `searchWebWithGoogle` / `searchWebWithTavily` / `searchWebWithBrave` / `searchWebWithSearxng` / `searchWebWithSerpapi`, and enabled per chat-model configuration like any other tool.
- **As a deep-search data source** — `ReactiveDeepSearchDataSourceServiceWrapper` turns any enabled search service into a deep-search source, so the reactive worker (`FullReactiveDeepsearchWorker`, `gebo.architecture.chat.abstraction.layer/.../llms/deepsearch`) plans the queries, runs the web searches side by side with the internal knowledge bases, downloads and chunks the retrieved pages, and streams the consolidated analysis with its document references back into the chat. Who may use which external source is decided by `GExternalSearchSecurityServiceImpl` against the admin `DeepSearchConfig` — per data source, per user/group.
- **As a searching agent** — with the standard agents network enabled (`ai.gebo.agents.standard.enabled`, on by default in the monolith), every enabled search service is registered automatically as an `EVIDENCES_SEARCHER_AGENT`: providers with a native query become a `<product>NativeSearcherAgent` (`NativeDocumentsSearchNetworkAgentService`) driven by the provider's own native prompt template, and `NativeSearchServiceWrapperTool` exposes their `<product>NativeSearch` function to the agents network. The same registration path serves the enterprise-systems searchers (Confluence, Jira, SharePoint, Google Drive), so a web-search provider behaves exactly like an internal source inside the network.

**Compliance note** — because these queries leave the installation, every enabled web-search provider is published in the data-flow register as a `WEB_SEARCH` endpoint with locality `EXTERNAL_PROVIDER` (`GStandardChatPipelineDataFlowComponent`), linked to the deep-search query endpoint, so the GDPR Art. 30 record shows the transfer. A self-hosted SearXNG is the local-deployment exception and is deliberately reported the same way, erring towards flagging the transfer.

**Testing** — the full-setup integration harness configures the active provider through the admin API (`GOOGLE_SEARCH`, `TAVILY_SEARCH`, `BRAVE_SEARCH`, `SEARXNG_SEARCH`, `SERPAPI_SEARCH` entries in the `FullSetupSecret` subsystems array); declaring more than one fails the run on purpose. See [integration-tests/README.md](./integration-tests/README.md).

### Security & compliance

Gebo.ai ships with two always-on capabilities for **GDPR / NIS2** obligations, both surfaced in the admin **Compliance** screen:

- **Security audit logging — Wazuh / SIEM compatible** (GDPR Art. 32 · NIS2 Art. 21): every security-relevant action (logins, LLM config changes, LLM invocations — *metadata only*, secret/API-key & integration changes, user administration) is written to a dedicated, append-only `security-log.jsonl` audit trail in Wazuh-compatible JSON Lines — verified against a real Wazuh manager on every build.
- **Records of processing & data-flow register** (GDPR Art. 30 · NIS2 Art. 21): a live record of processing activities built from the components *actually running* — data sources, transformation engines, retaining stores and external providers — with per-source personal-data scope, retention & erasure per store, an interactive data-flow graph and CSV export.

**Feature detail: [docs/security-and-compliance.md](./docs/security-and-compliance.md)** · **Wazuh setup: [docs/wazuh-integration.md](./docs/wazuh-integration.md)**

### How to build the software:

- Use Maven 3.8+
- Use JDK 20+ 
- Run "mvn clean package -P bootables,angular-ui" 
- The Bootable jar file will be generated in:   /gebo.ai.app/target 

### Environment variables to run the software:

To run the software uoy have to put 2 variables in your environment (with set on windows and export on bash/linux) 
 - GEBO_HOME ==> it points to the home directory the software it uses to allocate its own 
 - GEBO_WORK_DIRECTORY ==> it points to a local filesystem area that the software uses to archive informations/files (please back it up)

In a container deployment both are mounted on persistent volumes. **What has to
survive an update/upgrade, in every image and every compose file - and how to
back it up or migrate it - is documented in
[dockers/PERSISTENCE.md](./dockers/PERSISTENCE.md).** The short version: the work
directory and the MongoDB database are ONE backup unit, because Mongo only
*indexes* the files that live in the work directory.
 
       
