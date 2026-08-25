![Gebo.ai image logo](https://raw.githubusercontent.com/geboai/Gebo.ai/develop/gebo.ui/projects/gebo-ai-reusable-ui/src/assets/Gebo-1000.png)

# Gebo.ai, the open source enterprise retrieve augmented generation and chatbots platform (visit [https://gebo.ai](https://gebo.ai))

### This image is easy to install, simply run: "docker run -p 12999:12999 geboai/easyinstall.gebo.ai" and go with your browser to [http://localhost:12999/](http://localhost:12999/)

This software is an **open source enterprise retrieve augmented generation platform** that can be installed in every company to take the most out from their documentation and informations using modern large language models.

- [GitHub reference](https://github.com/geboai/Gebo.ai/)
- [Web site](https://gebo.ai)
- [YouTube channel](https://www.youtube.com/@GeboSystem)

## Gebo.ai features:

The admin, chat, rag chat, graphrag chat user interfaces are **fully multilanguage** and the application is **fully multiuser**.
All the following features are fully configurable using the administrative user interface.

### Users features

- Agentic chat delivering rag, deep searches through company used collaboration suites like Atlassian Jira and Confluence, Microsoft Sharepoint, Google searches and others.
- Chat using chatbots without retrieve augmented generation according to admin config.
- Chat using chatbots with retrieve augmented generation according to admin config.
- Chat with uploaded documents/user documents uploaded in chat session (rag or normal chat sessions).
- Browse company knowledge bases to select documents to chat/work with according to admin config.
- Generate **images** directly in chat using configured image generation models.
- Voice interface (speech to text & text to speech) working with OpenAI provider.

### Administrative features

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
    - Every provider/local server compatible with **OpenAI API**
- Configure tools & functions that each llm configuration can use, including **web search** (Google, Tavily, Brave, SerpApi or self-hosted SearXNG)
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
- Configure company users and groups.
- Organize multiple specific Retrieve augmented generation chats for specific company tasks:
    - Examples:
        - Customer support **chatbots to support customer support employees or directly the customers**
        - Tech/Production **productivity chatbots to support employee on mananging internal technical documentation**.
- Chatbot access can be granted individually to users/groups
- Knowledge bases can be granted individually to users/groups

## How to run easyinstall.gebo.ai image

```
docker run -p 12999:12999 geboai/easyinstall.gebo.ai
```

- **for detailed setup use the following documentation** [Gebo.ai documentation](https://gebo.ai/documents/)

### Post install configuration procedure:

After you've installed with docker go to [http://your-server-ip:12999/](http://your-server-ip:12999/) and configure your enterprise rag system account & setup your system. **Follow these steps**:
[Setting up Gebo.ai](https://gebo.ai/first-gebo-ai-server-first-startup-step-creating-admin-account/)

### What the image includes

A single all-in-one docker image with:

- MongoDB
- Qdrant Vector Database
- Neo4J Graph Database
- OpenSearch
- geboai/gebo.ai open source version software https://hub.docker.com/r/geboai/gebo.ai

## Gebo.ai licence:

The open source version is available under a variation of the Mozilla Public License Version 2.0 (MPL-2.0), an enterprise version with more feature and support is also available.

- [Click here to see the licence](https://github.com/geboai/Gebo.ai/blob/develop/LICENCE.md)
- [Click here the ORIGIN declaration](https://github.com/geboai/Gebo.ai/blob/develop/ORIGIN.md)

## For devs/software architects/software companies

This software is build with latest **spring boot** technologies and the new **spring-ai framework**, with UI developed in **Angular 19+PRIMENG**.
Is made to accelerate professionals/companies that invested in these technologies to accelerate your own business opportunities start building having this as base.

The platform can run either as a single **bootable jar** (monolith) or be deployed as a set of **20 distributed microservices** with **Eureka** service discovery and **Hazelcast** clustering for horizontal scalability and high availability.

| Target | What | Where |
|---|---|---|
| Monolith (this image) | One all-in-one container with embedded infra | `docker run -p 12999:12999 geboai/easyinstall.gebo.ai` |
| Docker Compose microservices | 20 containerized microservices + infra | `dockers/gebo.microservices/docker-compose.yml` |
| Kubernetes (Helm) | Microservices stack as Helm charts with per-service install toggles & observability | `deploy/helm/gebo-microservices` |

### How to build the software:

- Use Maven 3.8+
- Use JDK 20+
- Run "mvn clean package -P bootables,angular-ui"
- The Bootable jar file will be generated in: /gebo.ai.app/target
