![Gebo.ai image logo](https://raw.githubusercontent.com/geboai/Gebo.ai/develop/images/gebo-ai-readme-header-home-logo.svg)

# Gebo.ai, The open source Enterprise AI vendor agnostic platform (visit https://gebo.ai)

This software is an open source enterprise AI and retrieve augmented generation platform that can be installed in every company
to take the most out from their documentation and informations using modern large language models.
It's a "No AI vendor lock-in"  alternative to cloud vendors platform, it can work with almost all cloud or on premise AI infrastructures and connects to widely used enterprise systems.

## `geboai/easyinstall.gebo.ai`

The whole Gebo.ai platform in one container: nothing else to install, just pull and run.

## How to run

```bash
docker run -d --name gebo-ai -p 12999:12999 geboai/easyinstall.gebo.ai
```

Then open:

```text
http://<your-server-ip>:12999/
```

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
 - Configure tools & functions that each llm configuration can use, including **web search** (**Google Programmable Search**, **Tavily**, **Brave Search**, **SerpApi** or a self-hosted **SearXNG**) — see [Web search & deep search](#web-search--deep-search) below
 - Configure additional AI model types besides chat & embedding models:
    - **Image generation** models (OpenAI, AWS Bedrock, Regolo.ai, OpenRouter.ai & OpenAI-compatible providers)
    - **Text to speech** models (OpenAI, AWS Bedrock, Regolo.ai, OpenRouter.ai)
    - **Speech to text / transcription** models (OpenAI, AWS Bedrock, Regolo.ai, OpenRouter.ai)
    - **Reranking** models to improve retrieve augmented generation relevance (AWS Bedrock, Regolo.ai, OpenRouter.ai, vLLM & OpenAI-compatible providers)
 - Most providers support **guided fast-setup** with ready-to-use model presets and automatic models lookup
 - Connect to **Model Context Protocol (MCP)** servers to give your chatbots extra tools & data sources, or expose Gebo.ai itself as an **MCP server**
 - **Agent-to-agent interoperability** — through the open **Agent2Agent (A2A) protocol**, Gebo.ai plugs into the wider agentic ecosystem in **both directions**: **connect external A2A agents** — whatever framework or vendor built them — and put them to work as tools and participants inside your own agent networks, or **publish your Gebo.ai agents, a single agent or an entire network of agents, as standards-compliant A2A agents** with their own Agent Card, ready to be consumed by any A2A-capable platform. It is **secure by default**: every import/export is admin-enabled, off and invisible until you switch it on, inbound calls are validated by the platform security chain (self-issued JWT / API key or corporate OAuth2) and run under the caller's own identity, and outbound credentials are handled by the platform secrets vault with OAuth2 token relay. The **"no AI vendor lock-in"** promise now reaches the agents themselves.
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
 	- **AWS IAM Identity Center** (ex AWS SSO)
 	- **KeyCloak**, with its own dedicated single sign on settings
 	- Any other standard **oauth2/OpenID Connect** identity provider, through the **generic oauth2** connector
 - Configure **GraphRag** features (experimental)
 	- The software can use cheap models provided (on premise or in cloud) to export knowledge graphs persisted with neo4j. 	
 - Create knowledge bases collectioning documents from the previus mentioned system.  
 - Schedule document updates for AI reindexing (embedding) on updates.
 - Monitor embedding batch job.
 - Monitor **LLM usage** with built-in dashboards (admin: every user; user: own usage only) — drill down by provider, model, model type (chat/embedding/image/reranking/TTS/transcription), user and month to track calls/tokens over time.
 - **NIS2-oriented security audit logging**: login/logout, LLM configuration changes, LLM invocations, secrets/API-key/3rd-party-integration changes and user administration are all traced to a dedicated, append-only, **Wazuh-compatible JSON** audit trail — see [Security & compliance](https://github.com/geboai/Gebo.ai#security--compliance) below.
 - **Compliance / data-flow register** (GDPR Art. 30 · NIS2 Art. 21): a live **record of processing activities** built from the components actually running — data sources, transformation engines, retaining stores and external providers, with per-source personal-data scope, retention & erasure per store, an interactive data-flow graph and CSV export, plus the Wazuh/SIEM security-audit-logging status — see [Security & compliance](https://github.com/geboai/Gebo.ai#security--compliance) below.
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
 - Run a **deep search** from the chat, choosing which sources to work on — company knowledge bases, Confluence, Jira, SharePoint/OneDrive, Google Drive and the web — see [Web search & deep search](#web-search--deep-search) below.
 - Voice interface (speech to text & text to speech) working with OpenAI provider.   

### Web search & deep search
 - Choose the **web search provider** the chatbots will use, from a guided wizard:
    - **Google Programmable Search**
    - **Tavily**
    - **Brave Search**
    - **SerpApi** (real Google/Bing/DuckDuckGo results pages)
    - **SearXNG**, self-hosted, for companies that do not want their queries handled by a search vendor
 - Only one provider is active at a time: switching provider is just entering the new key, nothing else to reconfigure. Keys are stored as protected secrets and every change to them is traced in the security audit log.
 - The AI does not just send keywords: depending on the chosen provider it also decides **how** to search — how recent the results must be (last day/week/month/year), whether to look at news or general content, which country or language to favour, the safe-search level, or which underlying search engine to use.
 - The configured provider is available in three different ways:
    - as a **tool the chatbot can call** during a normal conversation, to check something on the internet;
    - as one of the **deep search** sources;
    - as a dedicated **searching agent** gathering evidences when the chatbot works as a network of agents.
 - **Deep search** answers a question by working on it instead of replying in one shot: it breaks the question into several searches, runs them **in parallel on all the sources the user is entitled to**, opens and reads what it found, drops what is irrelevant and writes a final analysis with the references it used. Sources are:
    - the company **knowledge bases**
    - **Atlassian Confluence** — searched by space, labels, page title & text, authors
    - **Atlassian Jira** — searched by project, issue type, status, priority, affected/fix versions, labels, people
    - **Microsoft SharePoint/OneDrive** — searched by site, folder path, document type, title & text, people
    - **Google Workspaces/Drives**
    - the **web**, through the configured provider
 - The user picks which sources to use for each question, and the administrator decides, per data source and per user/group, who is allowed to use them at all.
 - Web searches leave the company installation, so each active provider is listed among the external providers of the compliance data-flow register — see [Security & compliance](https://github.com/geboai/Gebo.ai#security--compliance).

## What this installation contains

- **Gebo.ai**
- **MongoDB**
- **Qdrant**
- **Neo4j**
- **OpenSearch**

All of them run inside the same container, so there is nothing else to deploy. Docker keeps the data in volumes of its own; when you later want an installation you can back up and upgrade, name those volumes as described in [dockers/PERSISTENCE.md](https://github.com/geboai/Gebo.ai/blob/develop/dockers/PERSISTENCE.md).

For a structured deployment where the infrastructure services run as separate containers, use [`geboai/gebo.ai`](https://hub.docker.com/r/geboai/gebo.ai) with the official Docker Compose stack.

## Gebo.ai licence

The open source version is available under a variation of the Mozilla Public License Version 2.0 (MPL-2.0),
an enterprise version with more feature and support is also available.

- [Click here to see the licence](https://github.com/geboai/Gebo.ai/blob/develop/LICENCE.md)
- [Click here the ORIGIN declaration](https://github.com/geboai/Gebo.ai/blob/develop/ORIGIN.md)
