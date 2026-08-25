![Gebo.ai image logo](https://raw.githubusercontent.com/geboai/Gebo.ai/develop/gebo.ui/projects/gebo-ai-reusable-ui/src/assets/Gebo-1000.png)

# Gebo.ai, The open source Enterprise AI vendor agnostic platform (visit [https://gebo.ai](https://gebo.ai))

### This image is suitable to be installed with docker-compose

This software is an **open source enterprise retrieve augmented generation platform** that can be installed in every company to take the most out from their documentation and informations using modern large language models.
It's a "No AI vendor lock-in" alternative to cloud vendors platform, it can work with almost all cloud or on premise AI infrastructures and connects to widely used enterprise systems.

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

## How to run Gebo.ai

Use the following docker-compose.yml file

```docker
version: '3.1'
#================================================================
# This compose file creates a service for gebo.ai enterprise retrive augmented generation system
# and its required mongodb (database) and qdrant (vector database) services
# once runned simply open the address http://<your host address:12999/ with your browser
# it will be required to create the administrative account and you'll be ready to setup and
# start immediately to share the application with your company or team.
services:
  # =======================
  # MongoDB
  # =======================
  # Bound only on loopback interface for security reasons
  mongo:
    image: mongo
    restart: always
    ports:
      - "127.0.0.1:27017:27017"
    environment:
      MONGO_INITDB_ROOT_USERNAME: mongoroot
      MONGO_INITDB_ROOT_PASSWORD: mongopwd
    command: ["mongod", "--wiredTigerCacheSizeGB=1"]

  # =======================
  # Qdrant
  # =======================
  # Bound only on loopback interface for security reasons
  qdrant:
    image: qdrant/qdrant
    restart: always
    ports:
      - "127.0.0.1:6333:6333"
      - "127.0.0.1:6334:6334"
    environment:
      QDRANT__SERVICE__API_KEY: ce7c85bc-f037-4c64-91d8-70335638329d
      QDRANT__STORAGE__OPTIMIZE_INDEXING: "true"
      QDRANT__STORAGE__RAM_DISK_SIZE: "256MB"
      QDRANT__STORAGE__MAX_SEGMENT_SIZE: "1000000"

  neo4j:
     image: neo4j:5
     restart: always
     ports:
      - "127.0.0.1:7474:7474"
      - "127.0.0.1:7687:7687"
     environment:
      NEO4J_AUTH: "neo4j/neo4jmaster"
      NEO4J_server_memory_heap_initial__size: 1G
      NEO4J_server_memory_heap_max__size: 2G
      NEO4J_server_memory_pagecache_size: 512M
      NEO4JLABS_PLUGINS: '["apoc"]'
      NEO4J_apoc_export_file_enabled: "true"
      NEO4J_apoc_import_file_enabled: "true"
      NEO4J_apoc_import_file_use__neo4j__config: "true"
  opensearch:
    image: opensearchproject/opensearch:latest
    container_name: opensearch
    environment:
      - discovery.type=single-node
      - OPENSEARCH_INITIAL_ADMIN_PASSWORD=dothesearch1973-Advanced
      - plugins.security.ssl.http.enabled=true
      - plugins.security.ssl.transport.enabled=true
      # consigliato in docker (evita bootstrap checks su memlock)
      - bootstrap.memory_lock=true
      # heap: regola in base alla RAM della macchina
      - OPENSEARCH_JAVA_OPTS=-Xms1g -Xmx1g
    ulimits:
      memlock:
        soft: -1
        hard: -1
      nofile:
        soft: 65536
        hard: 65536
    ports:
      - "9200:9200"
      - "9600:9600"

  # =======================
  # gebo.ai
  # =======================
  # Bound to all host interface
  gebo.ai:
    image: geboai/gebo.ai
    restart: always
    ports:
      - "12999:12999"
    depends_on:
      - mongo
      - qdrant
      - neo4j
      - opensearch
    deploy:
      resources:
        limits:
          # Limit memory usage to 4GB
          memory: 4G

    environment:
      #SPRING_PROFILES_ACTIVE: "prod"
      #Jvm settings
      JAVA_TOOL_OPTIONS:
        --enable-native-access=ALL-UNNAMED
        -Dsun.jnu.encoding=UTF-8
        -Dfile.encoding=UTF-8
        -Dai.gebo.neo4j.enabled=true
        -XX:+AggressiveHeap
        -XX:+PrintGCDetails
        -Xlog:gc:/opt/gebo.ai/logs/gc.log
        -Xms2g
        -Xmx4g
        -XX:MaxMetaspaceSize=512m
        --add-opens=java.base/java.nio.charset=ALL-UNNAMED
```

- **for detailed setup use the following documentation** [Gebo.ai documentation](https://gebo.ai/documents/)

### Post install configuration procedure:

After you've installed with docker go to [http://your-server-ip:12999/](http://your-server-ip:12999/) and configure your used llms provider, enterprise rag system account & setup your system with all company tools integrations (like sharepoint/jira/confluence, google searches ecc...). **Follow these steps**:
[Setting up Gebo.ai](https://gebo.ai/first-gebo-ai-server-first-startup-step-creating-admin-account/)

### What the docker-compose file installs

The docker-compose file installs the required

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

The platform can run either as a single **bootable jar** (monolith, this image) or be deployed as a set of **20 distributed microservices** with **Eureka** service discovery and **Hazelcast** clustering for horizontal scalability and high availability.

| Target | What | Where |
|---|---|---|
| Monolith (this image) | One bootable jar + external infra via docker-compose | `dockers/gebo.ai/docker-compose.yml` |
| Easy install | All-in-one single container (embedded infra) | `docker run -p 12999:12999 geboai/easyinstall.gebo.ai` |
| Docker Compose microservices | 20 containerized microservices + infra | `dockers/gebo.microservices/docker-compose.yml` |
| Kubernetes (Helm) | Microservices stack as Helm charts with per-service install toggles & observability | `deploy/helm/gebo-microservices` |

### How to build the software:

- Use Maven 3.8+
- Use JDK 20+
- Run "mvn clean package -P bootables,angular-ui"
- The Bootable jar file will be generated in: /gebo.ai.app/target
