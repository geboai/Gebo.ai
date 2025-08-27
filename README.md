![Gebo.ai image logo](./gebo.ui/src/assets/Gebo-1000.png) 
# Gebo.ai, the open source enterprise retrieve augmented generation platform (visit https://gebo.ai)
This software is an open source enterprise retrieve augmented generation platform that can be installed in every company
to take the most out from their documentation and informations using modern large language models.
## Gebo.ai licence:

The open source version is available under a variation of the Mozilla Public License Version 2.0 (MPL-2.0), 
an enterprise version with more feature and support is also available.  
 
 - [Click here to see the licence](./LICENCE.md)
 - [Click here the ORIGIN declaration](./ORIGIN.md) 
 
## Gebo.ai features:
### Administrative features
 - Configure the large language models to use like:
      - OpenAI chatgpt
      - Anthropic Claude
      - XaI Grok
      - Nvidia AI provider
      - Groq
      - Deepseek
      - MistralAI
      - Regolo.ai (Italian/European)
      - Almost every local large language model using Ollama 
 - Configure tools & functions that each llm configuration can use      
 - Configure gebo.ai rag system to access several company documents repository and information sharing tools such as:
    - Microsoft Onedrive/Sharepoint
    - Atlassian Confluence
    - Atlassian Jira
    - Google Workspaces/Drives (under development, coming soon)
    - GitHub/GIT/Bitbucket or other git compatible servers
    - Company shared filesystems
 - Create knowledge bases collectioning documents from the previus mentioned system.  
 - Schedule document updates for AI reindexing (embedding) on updates.
 - Monitor embedding batch job.
 - Configure company users and groups.      
 - Organize multiple specific Retrieve augmented generation chats for specific company tasks:
    - Examples:
       - Customer support chats to support customer support employees or directly the customers
       - Tech/Production productivity chats to support employee on mananging internal technical documentation.
 - Chatbot access can be granted individually to users/groups
 - Knowledge bases can be granted individually to users/groups



### Users features        
 - Chat using chatbots without retrieve augmented generation according to admin config.
 - Chat using chatbots with retrieve augmented generation  according to admin config.
 - Browse company knowledge bases to select  documents to chat/work with  according to admin config.     

## How to install Gebo.ai 

You can install Gebo.ai on linux/windows/mac using docker-compose immediately 
  - [docker compose file for linux](./dockers/gebo.ai/docker-compose.yml)
  - [docker compose file for windows](./dockers/gebo.ai/windows/docker-compose.yml)
  - [installation manual ](./docs/gebo-ai-manual-tech-configuration.pdf)

### Post install configuration procedure:

After you've installed with docker go to http://<your server ip>:12999/ and configure your enterprise rag system account & setup your system.

### What the docker-compose file installs  
The docker-compose file installs the required 
- MongoDB
- Qdrant Vector Database
- geboai/gebo.ai open source version software https://hub.docker.com/r/geboai/gebo.ai  

## For devs/software architects/software companies 

This software is build with latest spring boot technologies and the new spring-ai framework, with UI developed in Angular 19+PRIMENG
Is made to accelerate professionals/companies that invested in these technologies to accelerate your own business opportunities 
start building having this as base.

### How to build the software:

- Use Maven 3.8+
- Use JDK 20+ 
- Run "mvn clean package -P bootables,angular-ui" 
- The Bootable jar file will be generated in:   /gebo.ai.app/target 

### Environment variables to run the software:

To run the software uoy have to put 2 variables in your environment (with set on windows and export on bash/linux) 
 - GEBO_HOME ==> it points to the home directory the software it uses to allocate its own 
 - GEBO_WORK_DIRECTORY ==> it points to a local filesystem area that the software uses to archive informations/files (please back it up)
 
       
