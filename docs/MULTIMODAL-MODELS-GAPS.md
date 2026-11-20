# Multimodal models: gap analysis

Where Gebo.ai could hand pictures, audio, video and documents to the models that accept them, what stands in the way today, how the model presets, the models library and the model configurations have to carry it, and an order in which to build it.

**Status:** analysis only. Nothing described here as missing has been built.
**Date:** 2026-11-18 (second edition: every statement re-read against the code, and section 4 added).
**Code read:** community `develop` at `88917c3f5`, pro `develop` at `1289b8a`, the three Office add-in repositories, and the Spring AI `2.1.0-RC2` jars.

---

## 1. Scope and method

**In scope:**
- how a model is characterized: the provider lookups, the models library, the setup presets, the model configurations and the APIs that list them (section 4);
- every path by which content reaches a model: chat requests and uploads, the chat history, the agents and their tools, MCP, A2A, knowledge base ingestion, voice, image generation, the Office add-ins, web search and the crawler (section 5).

**Method:**
- **Source reading** of `src/main` in both repositories. Generated REST clients were left out.
- **Bytecode reading** (`javap -p -c`) of the Spring AI 2.1.0-RC2 jars in the local Maven repository, for each provider's chat model, the message part types and the tool-calling classes. Class files have to be searched as text (`grep -a`).
- **Second pass.** Every factual sentence of the first edition was checked again by opening the cited class. What turned out wrong is corrected here, and section 11 lists the corrections.

**Not done:**
- No request was run against a real provider. "Carried" in the provider table means that the Spring AI code converts that media type into the provider's request format. Whether a given model then reads it depends on the model.
- Whether the providers' reported prompt tokens include image input was not checked.
- The bodies of `ConversationFilesRenderer`, `GConversationFilesText` and `GMediaModelsDataFlowComponent` were not re-read.

**References:** classes are cited by name; section 12 gives the paths. Line numbers are those of the commits above.

---

## 2. Summary

- **Gebo gives every model text and nothing else.**
  - The chat, pipeline and agent calls build their prompt in one method, which returns a text-only `UserMessage`.
  - Pictures uploaded to a chat are kept for the agents' tools; the models never see them.
  - Pictures inside ingested documents are dropped. The MCP client replaces images with a placeholder, and the A2A client drops file parts.
  - Audio is transcribed to text before any model sees it.
- **The code says so.** `GBaseChatModelConfig.inputModalities` is documented with: "It characterizes the model; Gebo does not yet give the models anything but text."
- **What a model reads is already recorded**, as `inputModalities` on each chat model configuration, filled from four sources (section 4). The record has holes that matter once it starts deciding what is sent:
  - it says what the **model** reads, never what the **Spring AI integration** can carry to it;
  - the models library matches a model by exact id, so a dated variant of a listed model is text only;
  - no preset declares modalities, so the providers whose API tells nothing (Groq, NVIDIA, vLLM and the flat-rate gateways) create text-only models;
  - a model created in the admin editor is text only until the administrator clicks "use these";
  - the chat UI receives nothing about modalities.
- **The providers behave very differently with a file they can't take** (section 3.5): Anthropic and Bedrock raise an error, DeepSeek drops it silently, Mistral and Ollama send any file as if it were a picture, and OpenAI chat completions pastes an unsupported file into the prompt as base64 text.
- **Where the value is, in order:**
  1. Pictures and PDFs uploaded to a chat, sent to models that read them.
  2. Agents looking at pictures they were given or made.
  3. Knowledge bases learning from slides, pictures and scans.
  4. Files exchanged over MCP and A2A, kept as conversation files instead of lost.
- **The first step is the model record**, not the upload: declared modalities completed in the library and presets, a per-provider statement of what can be carried, and both exposed by the listing APIs.

---

## 3. Foundations already in place

### 3.1 Where a model's inputs are declared

- **`GBaseChatModelConfig.inputModalities`** (:78) is a `List<GModelInputModality>`: `TEXT`, `IMAGE`, `AUDIO`, `VIDEO`, `DOCUMENT`. Null or empty means text only.
- **`GBaseChatModelConfig.features`** (:68, `ChatModelFeatures`, which includes `MULTIMEDIA`) has no reader in Java or in the UI. It is persisted and exposed by REST, so removing it changes the generated clients.
- **`ModelMetaInfo`** (the metadata of a model choice) has `supportsVision` (:61) and `inputModalities` (:65). `knownInputModalities()` (:376) answers the list when given, otherwise text plus images by `supportsVision`, otherwise null (unknown).
- **Nothing reads `inputModalities` while a request runs.** Section 4 covers how it is filled and listed.
- **`ModelProviderCapabilities`** is what the chat UI receives about its model. It is declared in `IGGenericalChatService` (:38-81) and built in `GChatServiceImpl` (:200-211) and `GRagChatServiceImpl` (:238-252). Its fields are `configurationCode`, `supportsTranscript`, `supportsSpeech`, `supportsStructuredOutput`, `supportsFunctionCalls` and `functionsTreeList`. The two voice flags say whether a default transcription or speech model exists; they aren't properties of the chat model.

### 3.2 How a request becomes a prompt

1. `ChatPipelineServiceImpl.chat` (:89) and `streamingChat` (:119) call `ChatPipelinesExecutorImpl.streamingExecute` (:259).
2. The executor calls `IGChatSessionLifeCycleService.startRequest` (:160, :478).
   - `GChatSessionLifeCycleServiceImpl.startRequest` (:353-473) ingests the forced documents and the request's uploads (:445-456) into the chat state as **text** documents.
   - `budgetedResources` (:566) sizes them to the answering model's context (`getTokensBudget(targetChatModel)`, :458).
   - Other callers of `startRequest`: `GChatServiceImpl` (:128, :245), `GRagChatServiceImpl` (:167, :329), `ChatPipelineServiceImpl` (:263) and `GChatEnvironmentNetworkRunner` (:283, :460).
3. `LLMChatRequestResources.createChatRequestContext()` (:343) produces the `IChatRequestContext` the models read:
   - `getInteractions()` (:259): the history, as user and assistant strings;
   - `getDocuments()` (:273): the chat-with, retrieved, uploaded and generated documents (`allDocuments()`, :347), as Spring AI `Document` text;
   - `getActualUserRequest()` (:279): the question, as a string.
4. **`IChatRequestContext` has three implementations:**
   - `LLMChatRequestResources.NestedChatRequestContext` (:214), the request's own;
   - `ForwardingChatRequestContext` (abstract), created by every `with…` default of the interface and by `ToolsContextSharing`;
   - `IChatRequestContext.ChatRequestContextImpl` (:196), made by `IChatRequestContext.of(...)`, `MinimalChatContext.createChatRequestContext()` and `GUserChatSession.createChatRequestContext()`.
5. **Who calls the model with which context:**
   - The agents route hands the request's context to the network (`ReactiveChatAgentsNetworkStreamingOutputChatPipelineService` :259). `AgenticLoopReactiveAgentServiceImpl` extends `ReportWriterReactiveAgentServiceImpl` and inherits its call, `chatModel.streamAnswer(prompt, params, context, toolCalling)` (:367-369).
   - **The query rewriter and the router call the service model with the same full request context** (`DefaultRoutingChatPipelineStepServiceImpl` :218 and :390). So does the image-prompt writer (`DefaultImageGenerationStreamingOutputChatPipelineServiceImpl` :236).
   - Text-only contexts are used by the title (`GChatSessionLifeCycleServiceImpl` :1458), the assisted retrieve (`InternalKnowledgeLLMAssistedRetrieveServiceImpl` :58) and the history shrinker.
6. `streamAnswer`, `answer`, `response`, `structuredResponse` and `textResponse` all go through **`GAbstractConfigurableChatModel.prepareCall`** (:770-849), which builds:
   - the system message (`createSystemMessage`, `new SystemMessage(content)` at :1188);
   - the history, when the prompt asks for it (:785): `new UserMessage(String)` and `new AssistantMessage(String)` (:1003, :1008, and the compressed form at :1043-1064);
   - **the last user message** (`createLastUserMessage`, :1072-1097). The documents are rendered as text between `BEGIN_CONTEXT` and `END_CONTEXT` into the user template, and the method returns `new UserMessage(content)` (:1096).
7. No provider subclass overrides `prepareCall` or `createLastUserMessage`. `GenericOpenAIAPIChatModelConfigurationSupportService` has only a commented-out override (:313-328).

**Calls that do not go through `prepareCall`:**
- Graph RAG extraction (`GraphDataExtractionServiceImpl` :117, :142, :536-540, :564) builds its own messages through `doWithChatClient` and `doWithChatModel`.
- The pro content workflows (`AbstractContentLLMPromptRobotStepOperatorImpl` :114, :139, :156) call `doWithChatModel`.
- The model test call (`GAbstractConfigurableChatModel` :537).

**Consequence:** media read by `createLastUserMessage` from the request context would reach every chat, pipeline and agent call with no change in the providers. It would also reach the rewriter, the router and the image-prompt writer, which share that context, unless they are given a context without it (section 6.2).

### 3.3 Media today: output only

Spring AI `Media` appears in main code only on the way **out** of a model.

| Where | What it does |
|---|---|
| `GChatAnswerChunk` (:112-113) | collects `generation.getOutput().getMedia()` from a streamed answer |
| `AbstractChatService` (:461-469) | saves each output media with `chatStorageAreaService.addMedia` and lists it in `response.generatedResources`; it is the only reader of `chunk.media()` |
| `GChatStorageAreaServiceImpl.addMedia` (:305-323) | writes the bytes into the session folder and inserts an `LLMGeneratedResource`, shown in the chat as "AI generated files" |
| `DefaultImageGenerationStreamingOutputChatPipelineServiceImpl` (:207-210) | wraps the image model's result into a PNG `Media` and saves it through `addMedia` |
| `GDocumentTranslatorImpl.fromChatResponse` | reads output media into `GeboDocument.tryAddMedia`, an empty method; its caller is the pro content workflows (`AbstractContentLLMPromptRobotStepOperatorImpl` :166) |

- **On the agents route, output media is dropped.** The agents' stream keeps `chunk.answer()` only (`ReportWriterReactiveAgentServiceImpl` :389-393, `AgenticLoopReactiveAgentServiceImpl` :2056-2060).
- **On the input side** there is no `UserMessage.builder().media(...)`, no `new Media(...)` and no `MediaPart` in main code of either repository.

### 3.4 What Spring AI 2.1.0-RC2 offers

Read from the `spring-ai-model` and `spring-ai-client-chat` jars.

- **User messages.** `UserMessage` implements `MediaContent` and carries a list of `Media`. A `Media` holds a MIME type and its data, which is a `byte[]` or a `String` (a URI is stored as its string). `SystemMessage` has no media.
- **Message parts:** `TextPart`, `MediaPart`, `ReasoningPart`, `ToolCallPart`, `ToolResultPart` and `UnknownPart`.
- **Tool results carry text only, in practice.**
  - `ToolCallback.call` and `ToolCallResultConverter.convert` return a `String`.
  - `DefaultToolCallingManager` builds only `new ToolResponse(id, name, String)`.
  - `DefaultToolCallResultConverter` turns a returned `RenderedImage` into a JSON string with `"mimeType":"image/png"` and base64 data. That is still text.
  - A `ToolResponseMessage` can be built with `ToolResultPart`s holding a `MediaPart` (its builder has `result`, `results` and `parts`). No provider reads them: every provider converts tool messages through `getResponses()` and `responseData()`, and `ToolResultPart.toToolResponse()` joins text parts only.
- **Consequence:** in 2.1 a tool can't hand a picture to the model as its result. A picture has to travel in a user message.
- **Media on assistant messages of the history:** Google replays them. OpenAI chat completions sends text and tool calls only. The OpenAI Responses API, Anthropic, Mistral and Bedrock skip them with a debug log. Ollama and DeepSeek ignore them.
- **Media-related options Gebo doesn't set:** `OpenAiChatOptions.outputModalities` and `outputAudio` (audio output), and `AnthropicChatOptions.citationDocuments`.

**Gebo's own tool-calling classes** add nothing for media:
- `ToolCallbackDeclarationUtil` declares the tools with a null result converter (:135-136, :196-197, :217), so Spring AI's default applies.
- `GeboToolCallingManager` delegates to Spring AI and changes only `returnDirect` (:83-89, :97-107).
- `GeboToolCallingAdvisor` extends Spring AI's `ToolCallingAdvisor`. Its `doGetNextInstructionsForToolCall` (:86-91) and `doGetNextInstructionsForToolCallStream` (:125-131) build the messages of the next round. They are the hook section 6.3 uses.

### 3.5 Media in a user message, by provider

What each provider's Spring AI chat model does with `Media` on a `UserMessage`, from its bytecode. "Other" is any type the row doesn't name.

| Gebo module | Spring AI chat model, created at | Images | PDF | Audio | Video | Other types |
|---|---|---|---|---|---|---|
| `gebo.llms.openai`, chat completions | `OpenAiChatModel`, `OpenAIChatModelConfigurationSupportService` :209 | carried | carried as a file part | carried; declared MP3 when the type contains "mp3", **WAV otherwise**; needs bytes | **pasted into the prompt as base64 text** | **pasted into the prompt as base64 text** |
| `gebo.llms.openai`, Responses API | `OpenAiResponsesChatModel` in `ReasoningSummariesFallbackChatModel`, :256, :262 | carried | carried as an input file | **raises an error** | carried as an input file | carried as an input file, no type check |
| `gebo.llms.generic-openai-compatible` | `OpenAiChatModel`, `GenericOpenAIAPIChatModelConfigurationSupportService` :275 | as OpenAI chat completions | same | same | same | same |
| `gebo.llms.anthropic3` | `AnthropicChatModel`, `AnthropicChatModelConfigurationSupportService` :231 | png, jpeg, gif, webp; other image types raise an error | carried | **raises an error** | **raises an error** | **raises an error** |
| `gebo.llms.google_vertex` | `GoogleGenAiChatModel`, `GoogleVertexChatModelConfigurationSupportService` :181 | carried | carried | carried | carried | carried, no type check |
| `gebo.llms.mistral` | `MistralAiChatModel`, `MistralChatModelConfigurationSupportService` :216 | carried as image URL chunks | **sent as if it were an image** | **sent as if it were an image** | **sent as if it were an image** | **sent as if it were an image** |
| `gebo.llms.ollama` | `OllamaChatModel`, `OllamaChatModelConfigurationSupportService` :240 | carried as base64 images | **sent as if it were an image** | **sent as if it were an image** | **sent as if it were an image** | **sent as if it were an image** |
| `gebo.llms.aws-bedrock` | `BedrockProxyChatModel`, `BedrockChatModelConfigurationSupportService` :137 | jpeg, png, gif, webp | pdf, csv, doc, docx, xls, xlsx, html, txt, md | **raises an error** | mkv, mov, mp4, webm, flv, mpeg, wmv, 3gp | **raises an error** |
| `gebo.llms.deepseek` | `DeepSeekChatModel`, `DeepseekChatModelConfigurationSupportService` :247 | **dropped silently** | dropped | dropped | dropped | dropped |
| pro `gebo.provided.llms` | none: `create()` raises "not implemented yet" (:119-120) | n/a | n/a | n/a | n/a | n/a |

Notes:
- **Two OpenAI routes.** `OpenAiApiRouting.usesResponsesApi` (:37) reads the first number in the model id; 6 or more goes to the Responses API. Only `gebo.llms.openai` calls it (:201). The OpenAI-compatible providers always use chat completions.
- **Four different failures.** An error fails the whole call. A silent drop leaves the model without the file. A file sent as an image, or pasted as base64 text, reaches the provider as something it isn't, and the base64 text also costs a large number of tokens. Each has to be stopped before the call (section 6.1).
- **OpenAI-compatible backends** get what OpenAI chat completions would get. Whether they read it depends on the backend and the model.
- **Exact messages:** Anthropic raises "Unsupported media type: … Supported types are: images (image/*) and PDF documents (application/pdf)"; the Responses API "Audio input is not supported by the OpenAI Responses API. Use OpenAiChatModel for audio input."; Bedrock "Unsupported media format: …".

---

## 4. Models, presets and configurations

How Gebo knows what a model reads, how that reaches a configuration, what the listing APIs return, and how each of them should be treated for multimodality.

### 4.1 Four sources and their order

A chat model's input modalities come from four places.

| Source | Where | What it gives |
|---|---|---|
| 1. The provider's models API | each module's lookup (section 4.2) | `supportsVision` and `inputModalities` on the choice's `ModelMetaInfo`, when the API tells them |
| 2. The models library | `bundle-models/<provider>.yml` in each provider module, plus the installation's `ai.gebo.models.library` | `modelsInfo` entries with `inputModalities`, matched by provider and model id |
| 3. The setup presets | `llms-fast-setup-library/library.yml` in `gebo.llms.setup` | `inputModalities` on a preset choice (`LLMModelPresetChoice` :37) |
| 4. The configuration | `GBaseChatModelConfig.inputModalities` | what the administrator set, or what was copied in from 1 to 3 |

**How 1 and 2 merge.** `GModelChoiceMetaInfoEnricherServiceImpl.enrichMetaInfos` (:70):
- looks the model up in the library by provider and id, then by id alone;
- when the lookup gave metadata, fills only its missing values from the library (`fillMissingFrom`, :78), so the API wins;
- when it gave none, takes a copy of the library entry (:84).

**How the library is searched.** `GModelsLibraryDaoImpl.all` (:67) puts the installation's entries before the providers'. `findByProviderIdAndModelId` (:90) and `findByModelId` (:85) compare the model id with `equals`. The parameter rules next to them (`findParameterRules`, :96) match with `*` wildcards.

**How 1 to 3 reach the configuration.**
- `IGChatModelConfigurationSupportService.insertAndConfigureModel` (:47) lists the provider's models, finds the requested one, and sets `inputModalities` from its metadata when the configuration has none (:56, through `inputModalitiesOf`, :71).
- A preset's or a caller's value is set before that (`GeboLLMSSetupService.createLLMS` :1189-1190), so it wins over the metadata.

**Resulting order at setup:** caller or preset, then the provider's API, then the library entry, then `supportsVision`. With none of them the configuration has no modalities, which means text only.

### 4.2 What each provider module tells

Every module under `gebo.llms.parent`, with what its lookup puts in the metadata and what its library file holds.

| Module (model type) | Lookup | Modalities from the API | Library file |
|---|---|---|---|
| `gebo.llms.openai` (`chatgpt-OpenAI`) | `GOpenAIApiUtilImpl.getChatModels` (:219), from `GET /v1/models` | none: `liveMetaInfos` (:377) reads only the shutdown date, the context window and the output limit | `openai.yml`: 12 of its 15 chat entries have `[TEXT, IMAGE]`; `gpt-6-astra`, `gpt-6-sol` and `gpt-5.5` have none ("a model whose page was not checked has none, which means text only") |
| `gebo.llms.generic-openai-compatible`, Regolo.ai | `RegoloAIModelsListProviderService.metaInfos` (:223) | `supportsVision` from `supports_vision` (:233); no list | `regolo.ai.yml`: no entry |
| same, OpenRouter | `OpenRouterModelsListProviderService.metaInfos` (:196) | `supportsVision` and the full list from `architecture.input_modalities` (:212-214) | `openrouter.ai.yml`: rules only |
| same, xAI | `XaiEmbeddingModelsListProviderService` (:162-168) | `supportsVision` from `input_modalities` (:167); the list itself isn't kept | `xai.yml`: six models, no modalities |
| same, Groq, NVIDIA, vLLM, generic, Subconscious, Awan LLM, llmax, CheapestInference, Profundo AI | `GOpenAIApiUtilImpl.getChatModels` | none | `nvidia.yml` has four models without modalities; the others have no model entry |
| `gebo.llms.anthropic3` (`chat-anthropic`) | `AnthropicModelInfoMapper.toChoice` (:58) | image and PDF from the capabilities `imageInput` and `pdfInput` (:76-82) | `anthropic.yml`: rules only |
| `gebo.llms.google_vertex` (`chatmodel-google-vertex`) | `GoogleVertexChatModelConfigurationSupportService.toChoice` (:279) | none: the Vertex models API gives the limits and the thinking support | `google-vertex.yml`: six models with all five modalities; `gemini-3.6-flash` and `gemini-3.7-flash` have none |
| `gebo.llms.mistral` (`chatmodel-mistral`) | `MistralModelsLookupService` (:161-178) | image from `vision`, audio from `audio` | `mistralai.yml`: rules only |
| `gebo.llms.ollama` (`chat-ollama`) | `OllamaModelsLookupService.metaInfos` (:347) | image from the `vision` capability of `/api/show` (:361-362) | `ollama.yml`: no entry |
| `gebo.llms.aws-bedrock` (`chat-aws-bedrock`) | `BedrockFoundationModelsLookupService.choice` (:146) | the model summary's `inputModalities` (:156-158); an inference profile takes those of the model it routes to (:111-117) | `aws-bedrock.yml`: rules only |
| `gebo.llms.deepseek` (`chat-deepseek`) | `DeepseekModelsLookupService.toChoice` (:186) | the list from `input_modalities` (:204-206) | `deepseek.yml`: no entry |
| `gebo.llms.onxx-embeddings` | embeddings only | n/a | n/a |

`GModelInputModality.fromProvider` (:29) maps the providers' names: `image`, `images`, `vision` to IMAGE; `audio`, `speech`, `voice` to AUDIO; `video` to VIDEO; `file`, `files`, `pdf`, `document`, `documents` to DOCUMENT. A name it doesn't know is left out.

### 4.3 The presets

`library.yml` has presets for 17 vendors. A preset names a model type (`CHAT`, `EMBEDDING`, `RANKING`, `IMAGESGEN`, `TRANSCRIPT`, `TTS`, `DECISION`), its service handler, whether to look the models up, and its choices.

- **A chat choice can carry** `contextWindow`, `uses`, `maxGeneratedTokens`, `thinking`, `internalServicesThinking`, `temperature` and `inputModalities`.
- **No choice in `library.yml` sets `inputModalities`.** The field exists (`LLMModelPresetChoice` :37) and is copied in three places of `GeboLLMSSetupService`: `createChatModel` (:918-919), `applyPresetDefaults` (:1136-1137) and `createLLMS` (:1189-1190).
- **Its documented purpose** is "for a model whose provider's metadata and the models library do not tell it; unset otherwise, the model's metadata decides". In the code a set value wins over the metadata (section 4.1).
- **Every chat preset has `doModelsLookup: true`**, so each preset chat model goes through `insertAndConfigureModel` and gets its metadata's modalities. The presets without lookup are a decision model, two speech and two transcription presets.
- **`createLLMS` :1191-1194 has no effect.** It reads the modalities of `configuration.getChoosedModel()`, which `createBaseConfiguration` has just created with a code and a description and no metadata. The value is filled later, in `insertAndConfigureModel`.

**What the presets give today, by vendor:**

| Vendor | Preset chat models get their modalities from |
|---|---|
| OpenAI | the library: `gpt-5.2` and `gpt-5.4-mini` are `[TEXT, IMAGE]` |
| Anthropic, Mistral, DeepSeek, OpenRouter, Bedrock, Ollama | the provider's API |
| Regolo.ai, xAI | the API's vision flag: text, or text and images |
| Google Vertex | the library: the three preset models have all five |
| Groq, NVIDIA, llmax, Profundo AI, vLLM, generic OpenAI | nothing: text only |

### 4.4 How a configuration gets its modalities, by path

| Path | Code | `inputModalities` of the new configuration |
|---|---|---|
| Setup wizards: autoconfigure, easy wizard, "Change this model" | `GeboFastLLMSSetupController.createLLMByAutoconfigure` and `createLLMS`, then `GeboLLMSSetupService.createLLMS` and `configureModelValidating` (:1066) | the preset's, else the metadata's. The wizards never send a value of their own |
| Admin model editor, insert | each provider's `insert…ChatModelConfig`, then `AbstractBaseChatModelsConfigurationCRUDController.insert` (:93-133) | what the form sent. Nothing is filled on the server |
| Admin model editor, update | `update` (:154-179) | what the form sent |
| A configuration made before the field existed | none | null, text only, until someone edits it |

**The editor** (`standard-chat-model-settings`):
- shows a multiselect labelled "Accepts as input";
- shows under it "The model's metadata says: …" with a "use these" link, computed from the chosen model's `metaInfos` (`notifyChoosedModelChange`, :125-130);
- sets the value only when the administrator clicks the link (`useModelInputModalities`, :109-116). Its comment gives the reason: "so that a configuration made before this field existed stays text only until someone edits it";
- its help text says "Gebo does not send the models anything but text yet".

**A configuration keeps its model's metadata.** `GBaseModelConfig.choosedModel` is stored with the configuration, with the `metaInfos` it had when it was chosen. An existing configuration without modalities often still holds what its model accepts.

### 4.5 The listing APIs

| API | Returns | Modalities in the answer |
|---|---|---|
| Each provider's model choices, for example `getOpenAIChatModels`, `getAnthropicModels`, `getGenericOpenAIAPIChatModels` (admin) | `OperationStatus<List<…ChatModelChoice>>` from `getModelChoices` | yes, in `metaInfos`: what the API and the library know |
| `GeboFastLLMSSetupController.verifyVendorCredentialsAndDownloadModels` and `verifyCredentialsAndDownloadModels` (admin) | `List<GBaseModelChoice>` from the same `getModelChoices` | yes, in `metaInfos` |
| `GeboFastLLMSSetupController.getActualLLMSConfiguration` (admin) | the vendors with their presets (`LLMSModelsPresets`) and the existing configurations | the presets' `inputModalities` (none today) |
| `ChatModelsController.getRuntimeConfiguredChatModels` (admin, :79) | the whole configurations | yes: the configured `inputModalities` |
| `ChatModelsLookupController.getRuntimeConfiguredChatModelsLookup` and `getDefaultChatModel` (users, :79, :108) | `GLookupEntryRef`: code, description and a reference | **no** |
| `GeboChatController` / `GeboRagChatController`: `getChatModelMetaInfos`, `getChatProfileModelMetaInfos` (users) | the configuration's `choosedModel` (`GRagChatServiceImpl` :221-228) | the model's metadata, **not** the configured value |
| the same controllers: `getProviderCapabilities`, `getProfileProviderModelCapabilities` (users) | `ModelProviderCapabilities` | **no** |
| The other kinds: `EmbeddingModelsControllers`, `ImageModelsController`, `TranscriptModelsController`, `TextToSpeechModelsController`, `RankerModelsController`, `DecisionModelsController` | model types and configured models | no modality field exists for these kinds |

- **The chat UI** stores the answer of `getChatModelMetaInfos` in `modelMetaInfos` (`gebo-ai-reusable-chat.component.ts` :1163), and nothing reads it. It uses `capabilities` only to offer voice (`chat-input-shell.component.html` :36).
- **So the chat UI has no knowledge, during a chat, of what its model accepts.**

### 4.6 The lists leave some multimodal models out

The chat model lists are filtered by model id, because several models APIs return every kind of model with no type.

| Where | Ids left out of the chat list |
|---|---|
| `OpenAIChatModelConfigurationSupportService.isChatModel` (:320-329) | containing `tts`, `whisper`, `transcribe`, `dall-e`, `image`, `moderation`, `realtime`, `audio`, `embedding`, `babbage`, `davinci`, `sora`, `gpt-live`, `translate` |
| `GoogleVertexChatModelConfigurationSupportService.toChoice` (:285-289) | not starting with `gemini-`, or containing `embedding`, `image`, `live`, `tts`, `audio` |
| `NonChatModelsTypeFiltrer` (:44-47), for Groq and NVIDIA | containing `embed`, `whisper`, `orpheus`, `-tts`, `guard`, `content-safety`, `-reward`, `nemotron-parse`, `nvclip`, `video-detector` |
| `LLMTypeFiltrerImpl` (:33), the default | containing `embed` |

The markers `audio` and `image` also leave out chat models that take audio or answer with pictures, since their ids carry those words. OpenRouter and Regolo.ai don't need the markers: they split by the provider's own type (`listModelsByType`, and the `mode` of `model_info`).

### 4.7 The other model kinds

| Kind | Interface | Input | Output | Modality field |
|---|---|---|---|---|
| Transcription | `IGConfigurableTranscriptModel.call(InputStream)` | audio | text | none |
| Speech | `IGConfigurableTextToSpeechModel.call(String)` | text | audio | none; `ModelParameterRules.speechFormat` names the one format a model answers in |
| Image generation | `IGConfigurableImageModel.getImageModel()` and `call(ImagePrompt)` | text | image | none |
| Embedding | `IGConfigurableEmbeddingModel.getEmbeddingModel()` | text | vector | none (`GBaseEmbeddingModelConfig` has `tokenizationThreshold` only) |
| Ranker, decision | `IGConfigurableRankerModel`, `IGConfigurableDecisionModel` | text | scores, a decision | none |

**Which model serves a use.** `GBaseChatModelConfig.forUses` holds `ChatModelsUses` (`CHAT`, `INTERNAL_SERVICES`). `IGChatModelRuntimeConfigurationDao.findByUses` and `findByUsesOrGetDefault` (:44-45) pick a model by use, and `ModelRuntimeConfigureHandler.handleSystemChatModels` (:101) keeps one default per use.

### 4.8 Gaps in the model record

1. **Nothing says what can be carried.** `inputModalities` describes the model. Section 3.5 describes the integration. They disagree in known cases:
   - **DeepSeek:** the API can declare image input, and Spring AI drops every file.
   - **Mistral:** the API can declare audio, and Spring AI would send the audio as an image.
   - **Bedrock:** the API can declare IMAGE, and never DOCUMENT, although the integration carries nine document types.
   - **OpenAI:** a model on the Responses API can't take audio whatever it declares.
   - **Anthropic:** only four image types pass.
2. **Exact-id library matching.** `gpt-5.2-2025-12-11` is not `gpt-5.2` for `findByProviderIdAndModelId`, so it gets no modalities. The parameter rules of the same file already name both ids (`models: ["gpt-5.2", "gpt-5.2-2025-12-11"]`) or a family (`gpt-5*`).
3. **Library entries without modalities:** `gpt-6-astra`, `gpt-6-sol`, `gpt-5.5`, `gemini-3.6-flash`, `gemini-3.7-flash`, and every model of `xai.yml` and `nvidia.yml`.
4. **OpenAI's library says IMAGE only.** The integration carries PDFs to OpenAI on both routes, and no OpenAI entry declares DOCUMENT.
5. **xAI keeps the vision flag and drops the list** its API gives (`XaiEmbeddingModelsListProviderService` :167).
6. **Providers with no source at all:** Groq, NVIDIA, vLLM, generic OpenAI and the five flat-rate gateways. Their models are text only unless the administrator sets the field by hand. The preset field made for this case is unused.
7. **Unknown and text only look the same.** A null list means both "nobody knows" and "text only". The editor can't tell an administrator which models still need a look.
8. **The admin editor doesn't fill the value** on insert, so a model created there is text only unless the administrator clicks "use these". A model created by the wizard is filled.
9. **Old configurations stay text only** until edited, by design, although most hold their model's metadata (section 4.4).
10. **The users' APIs don't carry it** (section 4.5), so the chat can't adapt its upload box or its hints.
11. **Output modalities aren't recorded.** `ModelMetaInfo` has no such field. OpenRouter gives `architecture.output_modalities` (`ModelArchitecture`), and Bedrock gives `outputModalities`, which the lookup uses only to tell chat from embedding models (:154-155).
12. **The id filters hide audio and image chat models** (section 4.6).
13. **No use names a model for reading media.** A knowledge base that wants pictures described (section 5.6) has no way to say which model does it.
14. **Statements that will become false:** the javadoc of `GModelInputModality` and of `GBaseChatModelConfig.inputModalities`, and the editor's help text, all say Gebo sends text only.

### 4.9 Proposed treatment

**In `gebo.architecture.llms.abstraction.layer`:**

1. **Keep `inputModalities` as the single declared value** of a configuration, with the meaning it has. Add `getInputModalities()` to `IGConfigurableChatModel`, read from the configuration as `isSupportsFunctionsCall()` reads the choice (`GAbstractConfigurableChatModel` :598).
2. **Add what the integration carries**, as a hook on `GAbstractConfigurableChatModel` that each provider's model class overrides, the way `liveParameterRules` (:329) is overridden today.
   - It answers, for the configuration, the MIME types that may be attached, grouped by modality, and whether the data must be bytes.
   - Its default answers nothing, so a provider that says nothing never receives a file.
   - Section 4.10 gives each provider's answer.
3. **Compute the effective inputs** in one place: a type is sent only when the configuration declares its modality **and** the hook carries it. This is the media guard of section 6.1.
4. **Let the library declare modalities by pattern.** Add `inputModalities` to `ModelParameterRules`, next to `sampling` and `reasoningEfforts`.
   - The rules already match by wildcard, already merge a live and a library rule (`ModelParameterRules.merge`), and are already overridable per installation through `ai.gebo.models.library.parameterRules`.
   - `GModelChoiceMetaInfoEnricherServiceImpl` fills a choice's `inputModalities` from the covering rule when the API and the `modelsInfo` entry gave none. Every listing then carries the value, and `inputModalitiesOf` needs no change.
   - One rule such as `gpt-5.2*` then covers the dated variants.
5. **Keep unknown apart from text only** in the metadata: `knownInputModalities()` already answers null for unknown. Carry that to the listings (point 9), and leave the configuration's rule as it is (null means text only at request time).
6. **Record output modalities** on `ModelMetaInfo` where the provider gives them (OpenRouter, Bedrock). It costs little, and it is what a later "chat models that answer with pictures or audio" step needs. Not needed for input.
7. **Correct the javadoc** of `GModelInputModality` and `GBaseChatModelConfig.inputModalities` when the first media is sent.

**In `gebo.llms.setup` and the presets:**

8. **Use the preset field for the vendors with no source**, and only for them, as its javadoc says: the preset chat models of Groq, NVIDIA, llmax and Profundo AI, each from the model's own page with the page and date in a comment, as the file does for context windows. Where a vendor's models are known as a family, a library rule (point 4) is the better place, since it also covers models outside the presets.
   - Remove the lines of `createLLMS` (:1191-1194) that have no effect.
   - Add to `ChatModelSetupInputModalitiesTest` a case per vendor so a preset model can't silently lose its modalities.

**In the listing and configuration APIs:**

9. **Model choices** (`getModelChoices` of every provider, and the setup lookups): already carry `metaInfos`. With point 4 they are complete. Add to each choice what its provider carries (point 2), so the editor and the wizard can show both.
10. **Insert in the admin editor:** fill `inputModalities` from the chosen model's metadata when the request carries none, in `AbstractBaseChatModelsConfigurationCRUDController.insert`, as the wizard path does. An update keeps what the form sends.
11. **Existing configurations:** offer the administrator the model's own metadata, which the configuration already stores. Two ways, to choose between (section 9): a "use the model's metadata" action on the models list for all configurations at once, or a one-time fill at startup.
12. **The users' APIs:** add the configuration's declared modalities and the effective MIME types to `ModelProviderCapabilities`. The chat UI already fetches it for its model or its profile, and both chat services already build it from the configuration.
13. **The other kinds** need no field for the roadmap of section 8. If audio and video files are transcribed into knowledge bases (section 5.6, step 3), the transcription models' accepted audio types become a per-provider statement like point 2.
14. **A model for reading media:** add a `ChatModelsUses` value for it, so `findByUsesOrGetDefault` can answer the model that describes pictures at ingestion or for a chat model that can't read them. The alternative is a model chosen per knowledge base. This is a decision (section 9).
15. **The id filters** (section 4.6): once a model's modalities are known from the library, a chat model can be kept in the list even when its id contains `audio` or `image`. Where the provider tells the model's type, use it, as OpenRouter and Regolo.ai do.
16. **Generated clients:** points 9 and 12 change REST models, so the Angular stubs and the Java client have to be regenerated.

**In the UI:**

17. **Model editor:** show the provider's carried types next to "Accepts as input", and mark a declared modality the integration can't carry ("DeepSeek: pictures are not sent"). Replace the help text.
18. **Models list:** a column with the declared modalities, and a mark on the models whose metadata is unknown.
19. **Wizards:** show what a preset model accepts on the step that offers it.

### 4.10 Per provider module

What each module under `gebo.llms.parent` needs, in the order of the points above.

| Module | Carried types (point 2) | Library or lookup (points 4, 8) |
|---|---|---|
| `gebo.llms.openai` | Chat completions: `image/*`, `application/pdf`, audio as mp3 or wav bytes. Responses API: `image/*` and `application/pdf`; no audio. The answer depends on `OpenAiApiRouting.usesResponsesApi` for the configured model | `openai.yml`: rules by family in place of per-id entries; add DOCUMENT where the model page says the model reads files; fill `gpt-6-astra`, `gpt-6-sol`, `gpt-5.5` |
| `gebo.llms.generic-openai-compatible` | As OpenAI chat completions. The backend decides what it reads, so the declared modalities do the narrowing | xAI: keep the API's list (`GModelInputModality.fromProvider(m.input_modalities)`). Regolo.ai: nothing beyond the vision flag is published. Groq, NVIDIA and the gateways: rules or presets |
| `gebo.llms.anthropic3` | `image/png`, `image/jpeg`, `image/gif`, `image/webp`, `application/pdf` | none needed: the API tells image and PDF |
| `gebo.llms.google_vertex` | every type; bytes or a URI | `google-vertex.yml`: one rule for the Gemini family in place of per-id entries; fill `gemini-3.6-flash` and `gemini-3.7-flash` |
| `gebo.llms.mistral` | `image/*` only | none needed for images. The API's `audio` flag stays declared and is not carried |
| `gebo.llms.ollama` | `image/*` only | none: `/api/show` tells vision |
| `gebo.llms.aws-bedrock` | the three format lists of section 3.5 | a rule adding DOCUMENT for the model families whose pages say they read documents; the API never tells it |
| `gebo.llms.deepseek` | nothing | none. A declared IMAGE is shown as not carried |
| `gebo.llms.onxx-embeddings`, `gebo.llms.standard.functions`, `gebo.llms.openai.api.utils`, the starters | n/a | n/a |
| `gebo.llms.tests.services` | `TestChatModel`: every type, so the fake-model tests can assert what was attached | a test configuration with settable modalities |

Each `*.controllers` module needs only the regenerated models of point 16; their endpoints don't change.

---

## 5. Gaps by area

Each area has the same layout: **today**, with evidence; **gap**; **proposed design**; **effort and risks**; **how to test**.

### 5.1 Chat uploads

**Today:**
- The upload box (`upload-chat-document.component`, hosted by `chat-input-shell.component.html` :15-20) posts to `GeboUserChatUploadsController.chatSessionUpload` (:55-60). That reaches `GUserUploadContentHandlerImpl` (:40) and `GChatStorageAreaServiceImpl.addUploadedFile` (:104-171), which saves the file and runs the ingestion handler.
  - **A file a content handler reads:** its text chunks are written to `<code>-ingest.json` (:147-166) and reach the prompt as text documents. Pictures inside it are dropped (section 5.6).
  - **A file no handler reads, such as a picture** (:128-143): it is refused and deleted, unless its extension is one the chat's agents' libraries work on (`toolsFileTypesOfSession`). If kept, it is stored with `tokensCount` 0 and no ingested text.
- At request time, `getIngestedContentsOf` answers null for such an upload (:229-244), so **the model gets nothing of it**, not even its name. It learns of the file only by listing the `uploads` location with a library tool (`ChatUploadsFilesProvider`, read-only).
- The box's accepted types come from `GChatUploadFileTypesServiceImpl.acceptedFileTypes` (:113-133): the content handlers' extensions plus those of the libraries of the chat's agents. Its parameters are the session and the chat profile; it takes no model.
- The box tells the user that such files "are not read into the chat, and the models do not see pictures".
- **Routing:** a turn carrying an upload counts as carrying documents (`DefaultRoutingChatPipelineStepServiceImpl` :789-797). It goes to the chat-with-files step unless the user chose the agents and a network answers (`answeredOverTheDocuments`, :810-820). That step's prompt is built by the same `prepareCall`.
- **Scanned PDFs** are read by the PDF handler, which gives one empty document per page (section 5.6).
- **Attachments from other callers** (A2A, run-as-tool) become chat uploads through `GChatEnvironmentNetworkRunner` (:384-395, :719-735), so they follow the same rules.
- **Sizes:** the box allows 10 MB a file and labels it "/ 1Mb"; the server allows 100MB (`application.yml` :42).

**Gap:** when the chat's model declares IMAGE and its integration carries pictures, an uploaded picture could go to it. The same holds for a PDF with DOCUMENT (a scanned one included) and an audio file with AUDIO. Nothing connects the upload to the model.

**Proposed design:**
1. **Carry the media on the request context.** Add `default List<Media> getMedia()` (empty) to `IChatRequestContext`. Implement it in `NestedChatRequestContext` from the request's uploads and forward it in `ForwardingChatRequestContext`. `ChatRequestContextImpl` keeps the default.
2. **Attach it where the prompt is built.** In `createLastUserMessage`, build the message with `UserMessage.builder().text(content).media(...)` when the list isn't empty.
3. **Send only what the model declares and the integration carries**, through the guard of section 6.1.
4. **Give the service calls a context without media.** The rewriter, the router and the image-prompt writer share the request's context (section 3.2). They should read a view of it whose `getMedia()` is empty.
5. **Budget it.** Nothing counts media: `budgetedResources` sizes text, and the media branch of `GContentDispatchingEvaluatorImpl.computeTokensCount` (:179-181) is empty. Add a per-request limit (files, bytes per file, total bytes) and a token estimate per file.
6. **Decide the later turns** (section 9). The history (`CSSSimplefiedInteraction`) has no media field and no mention of a tools-only upload.
7. **The upload box follows the model.** `acceptedFileTypes` also takes the chat's model or profile and adds the types the guard lets through. The hint is shown only when it is true for that model. The UI gets both from `ModelProviderCapabilities` (section 4.9, point 12).
8. **Data protection register.** Pictures, PDFs and audio would leave for the provider as content. Add the flow to the compliance descriptions, next to `GMediaModelsDataFlowComponent`.

**Effort and risks:**
- **Effort:** small in the backend once section 4.9 points 1 to 3 exist. Small in the UI.
- **Risk:** a provider failing on a type the guard let through. Section 3.5 is from bytecode; one live request per provider and type settles it.
- **Risk:** cost. A picture costs tokens on every turn it is sent.

**How to test:**
- In the fake-model integration tests, the `TestChatModel` prompt observer receives the whole `Prompt`. A test asserts that the last `UserMessage` carries the uploaded picture's `Media` when the model declares IMAGE and carries none otherwise, and that the router's and the rewriter's prompts carry none. The pro office libraries integration test already uploads a picture to a chat.
- Unit-test the guard against the provider table.

### 5.2 Chat history and generated pictures

**Today:**
- The history shown in the UI keeps each answer whole, with its `generatedResources` and `writtenFiles` (`GeboTemplatedChatResponse` :53, :57).
- The history the model reads keeps text only (`CSSSimplefiedInteraction` :17-36, read through `getUser()` and `getAssistant()`).
- Generated resources are offered to ingestion at the end of the request (`GChatSessionLifeCycleServiceImpl.endRequest` :901-921). One a content handler reads becomes a text document of later turns. A picture gives nothing.
- **A generated picture isn't a file the tools can open.** The only files providers are `ChatUploadsFilesProvider` and `KnowledgeBaseFilesProvider`.
- A generated image the provider returns as a link is fetched by the server (`DefaultImageGenerationStreamingOutputChatPipelineServiceImpl` :219-220) and stored as `image/png` whatever it is (:207).
- `addLLMGenerated` has no callers.

**Gap:** a follow-up question about a picture the chat generated reaches a model that never saw it, and an agent can't open it.

**Proposed design:**
- Once section 5.1 exists, the guard can attach the previous turn's generated pictures to the next request, under the same later-turn rule.
- Offer the generated resources as a read-only files location, as the uploads are, so the tools can work on them.
- Keep the output media of the agents route, which is dropped today (section 3.3).

**Effort:** small once section 5.1 exists.

### 5.3 The agents' tools and the files they handle

**Today:**
- **Pro text documents and presentations libraries:**
  - `extractImages` (`TextDocumentsLibrarySource` :1447-1488) and `extractSlideImages` (`PresentationsLibrarySource` :1350-1393) copy pictures out of documents and slides into a files location. The model gets back `ImagesExtracted(location, files)`: the location and the file names.
  - `GImage` keeps its bytes out of JSON (:36-37), so a document read gives the model a picture's name, type, size and alt text.
  - The text documents library's agent rules say (:162): "A picture the user uploaded is in uploads … you know it by its name and size, you do not see it."
  - Slides are never rendered to images.
- **Working directory library:** reads and writes text files only (`WorkingDirectoryLibrarySource` :542, :567).
- **Pro sandbox library:** `readFile` reads a text file (`OpenSandboxLibrarySource` :541-544).
- **Files written in the conversation** reach the agents as a text outline (`ConversationFilesRenderer`, `GConversationFilesText`).
- **No tool generates pictures.** Image generation is a pipeline step (section 5.8).

**Gap:** an agent can't look at a picture it was given or made: a chart in an upload, the layout of a slide it built, a picture an MCP tool returned.

**Proposed design:**
- **A "view picture" tool**, in the working directory library or a small library of its own. It takes a files location and a path, and checks through the guard that the model takes the picture.
- **The picture travels in a user message** (section 3.4). The tool returns a short text, and `GeboToolCallingAdvisor.doGetNextInstructionsForToolCall` appends a `UserMessage` carrying the picture to the next round's messages.
- **This is a hypothesis to try first.** Each provider has its own rules on the order of tool messages. A first pull request should try it on OpenAI, Anthropic and Google before it is offered generally.
- **Slide previews:** rendering slides to PNG would let an agent check a presentation's layout through the same tool.

**Effort and risks:**
- **Effort:** medium.
- **Risk:** the providers' message-order rules, and the size of pictures.

**How to test:** a fake-model test calls the tool and checks that the next prompt carries the picture; one live run per provider.

### 5.4 MCP

**Today:**
- **Client tools.** `McpRemoteToolCallback.renderResult` (:92-110) keeps `TextContent` and replaces any other content with a placeholder such as `[ImageContent content]`. `structuredContent` is never read. Spring AI's `SyncMcpToolCallback` is not used.
- **Client resources ingested into knowledge bases.** `GMCPClientRemoteVirtualFilesystemConsumingServiceImpl` (:329-335) decodes blob resources and passes them to ingestion, which reads text formats only.
- **Server tools.** `GeboMcpToolsProvider` (:68), `GeboMCPAgentAsToolProvider` (:70) and `GeboMCPAgentsNetworkAsToolsProvider` (:69) register tools through the one-argument `McpToolUtils.toSyncToolSpecification`, which answers `TextContent`.
- **Server resources.** `GeboMcpResourcesProvider` (:935-957) returns text for textual types and base64 blobs for the rest, and raises an error above `maxDocumentBytes`.
- **Server prompts** are text only (`GeboMcpPromptsProvider` :77).
- MCP sampling and elicitation appear nowhere in the three MCP modules.

**Gap:** a picture or a file returned by an MCP tool is lost: the agent reads a placeholder.

**Proposed design:**
- Keep non-text tool content as files of the conversation, and return a text that names them, so the "Files written" row and the panel show them and the tools can open them.
- With section 5.3, the agent can then look at a picture.
- Read `structuredContent` as JSON text when present.

**Effort:** small for keeping the files; the viewing comes with section 5.3.

### 5.5 A2A

**Today:**
- **Server, inbound** (`A2AInboundResources`):
  - Text parts become the question (:98-102).
  - A file part given as bytes becomes a chat upload (:124-138, handed on in `A2AChatEnvironmentRunner` :55), so it follows section 5.1.
  - A file part given by URI isn't fetched (:139-142). It is added to a `notTaken` list that `A2AServerBuilder` logs (:103-104). The caller isn't told.
  - Data parts are read only for the `knowledgeBases` and `documents` keys (:149-157).
- **Server, outbound** (`A2AResultAdapter` :45-58, :115-170):
  - The answer goes out as a text part, with data parts for citations and run information.
  - Generated pictures and written files are never sent.
  - The agent card advertises `text/plain` only (`A2AAgentCardBuilder` :44).
- **Client:**
  - It sends text parts only (`A2AClientConnector` :219).
  - Of the answer it keeps only the text parts (`RemoteA2ANetworkAgentService.renderParts`, :216-230). File and data parts are dropped with no log.
  - The remote skills' input and output modes are stored (`A2AClientManagementServiceImpl` :241-242) and never read.

**Gap:** files exchanged with other agents are lost both ways.

**Proposed design:**
- **Inbound pictures** follow section 5.1 once it exists. Tell the caller what was not taken, from the `notTaken` list.
- **Outbound:** return generated pictures and written files as file parts, and advertise their MIME types on the card.
- **Client:** keep received file parts as conversation files. Read the remote skills' modes, so Gebo knows what a remote agent accepts before sending it a file.

**Effort:** small to medium.

### 5.6 Knowledge base ingestion

**Today:**
- **Readers.** The only registry is `bundle-readers/readers.yml`:

  | Reader (`readers.yml` id) | Types |
  |---|---|
  | `HtmlIngestionHandler` | .htm, .html |
  | `MSDocumentsIngestionHandler` | .doc, .docx |
  | `MsExcellIngestionHandler` | .xls, .xlsx |
  | `OdtIngestionHandler`, `OdpIngestionHandler`, `OdsIngestionHandler` | .odt, .odp, .ods |
  | `PdfIngestionHandler` | .pdf |
  | `XmlIngestionHandler`, `JsonIntestionHandler` | .xml, .json |
  | `TextPlainIngestionHandler` | .txt, .text, .md and source code types |

  The pro repository adds no reader.
- **What no reader covers:** images, audio, video, and **.pptx, .ppt and .rtf**, although the pro office libraries read and write all three.
- **A file no reader takes is flagged and gives nothing.** `GDocumentReferenceIngestionHandlerImpl` (:196, :399) and `GContentDispatchingEvaluatorImpl` (:254-255, :342-344) set it as unmanaged content when its content is read, and it yields no fragments.
- **Pictures inside documents are dropped:**
  - **PDF:** text is extracted page by page, and the image callback is empty (`ColumnAwarePdfTextExtractor.renderImage`, :129-130). One document per page is always emitted (`PdfIngestionHandler` :138-145), so a scanned PDF gives as many empty documents as it has pages.
  - **Word:** the extractor's text only (`MSWordIngestionHandler` :139-143). **Excel:** no picture or drawing is read.
  - **ODT and ODP:** text elements and text boxes only.
  - **HTML:** `body.text()` (`HtmlIngestionHandler` :80), so `<img>` and its alt text are dropped.
- **There is no OCR and no captioning.** The word appears only where OCR models are kept out of chat model lists (Mistral, Regolo.ai). Tika's OCR parser is on the classpath as a transitive dependency.
- **The Tika fallback handler is off.** `GTikaFailBackContentHandler` has its `@Service` commented out (:53-54).
- **`GeboDocument` has unused media fields** (:90-102). Ingestion streams only its texts (`GDocumentReferenceIngestionHandlerImpl` :372), and no content handler ingests through it.
- **The only model at ingestion is graph RAG extraction, and it reads text** (`GraphDataExtractionServiceImpl` :78, :116-150). Cataloguing (`GAIDocumentCatalogingEnricherImpl`) calls no model.
- **Embeddings are text only** for every provider: `OpenAiEmbeddingModel` (OpenAI and the compatible providers), `VertexAiTextEmbeddingModel`, `MistralAiEmbeddingModel`, `OllamaEmbeddingModel`, `BedrockCohereEmbeddingModel` or `BedrockTitanEmbeddingModel`, and `TransformersEmbeddingModel`.

**Gap:** a knowledge base learns nothing from charts, diagrams, photos, scans, slides, recordings or videos.

**Proposed design, in steps:**
1. **Text readers for .pptx/.ppt and .rtf.** No model is needed. This fixes a text gap that predates multimodality.
2. **Descriptions of pictures and scanned pages by a model that reads them, opt-in per knowledge base.**
   - A step after text extraction sends each picture, and each PDF page without text, to a model that declares IMAGE (or DOCUMENT) and whose integration carries it. Section 4.9 point 14 says how that model is named.
   - The description becomes text of the document, marked as such in the chunk metadata. Chunking, embedding and graph RAG extraction then work unchanged.
   - Settings: on or off, the model, the number of pictures per document, the smallest picture worth describing, and a cost estimate shown before a run.
   - This call doesn't go through `prepareCall` (as graph RAG extraction doesn't), so it attaches its media itself, through the same guard.
3. **Audio and video files** transcribed with the transcription models Gebo already has, with the transcript as the document's text.
4. **Multimodal embeddings** would be a separate project: no embedding model Gebo configures takes images.

**Effort and risks:**
- **Effort:** step 1 is small. Step 2 is medium. Step 3 is small to medium.
- **Risk in step 2:** cost and time on large corpora, hence opt-in, limits and the estimate.

**How to test:**
- Step 1: reader unit tests on sample decks and RTF files.
- Step 2: a fake-model ingestion test, where the test model answers a fixed description and the test checks that it is embedded and found by search.

### 5.7 Voice input and speech

**Today:**
- **Voice input:** the chat UI posts the recording to `GeboTranscriptController` (:38). `GTranscriptServiceImpl` (:55-63) turns it into text with the default transcription model. The text goes out as an ordinary message; the audio isn't stored and never reaches the chat model.
- **Speech:** `GTextToSpeechServiceImpl` (:62) turns an answer's text into audio for the browser, and the audio isn't stored.
- **Transcription models:** OpenAI and the compatible providers (`OpenAiAudioTranscriptionModel`), and AWS Transcribe through the AWS SDK. **Speech models:** OpenAI and the compatible providers (`OpenAiAudioSpeechModel`), and AWS Polly.
- Neither is used in ingestion.

**Gap:** only where the sound itself matters (tone, several speakers, music). Transcription already serves the common case with every model.

**Proposed design:** none for now. Sending audio directly becomes possible through section 5.1 for the models that declare AUDIO on an integration that carries it: OpenAI chat completions (mp3 and wav) and Google.

### 5.8 Image generation

**Today:**
- `DefaultImageGenerationStreamingOutputChatPipelineServiceImpl` is a pipeline step, not a tool. A service model writes the image prompt, the image model is called with text only (`new ImagePrompt(imagePrompt)`, :197), and the result is saved through `addMedia`.
- Image models: `OpenAiImageModel` (OpenAI and the compatible providers) and Gebo's own `BedrockImageModel`. All take the text of an `ImagePrompt`.
- `ImageModelsController` lists image model types and configured models. No endpoint generates an image outside the pipeline.

**Gap:** no "change this picture" from an upload, and follow-up questions about a generated picture (section 5.2).

**Proposed design:**
- **Follow-ups** come with sections 5.1 and 5.2.
- **Editing:** an image-edit call on the image models that offer one, as a pipeline option or a tool.

### 5.9 Office add-ins

**Today:**
- **What the add-ins send.** The Microsoft Office add-in reads the selection with `CoercionType.Text` and sends it as `text/plain` (`office-methods.service.ts` :22, `app.component.ts` :145). ONLYOFFICE does the same (`app.component.ts` :143), and LibreOffice sends `text/plain` by default (`DocumentSelection.java` :22, :48). They are three repositories of their own.
- **The data model is text:** `AdditionalContent { contentType, name, content: String }` (:22-26).
- **The pro pipeline handles text only:**
  - `OfficeFragments.normalize` (:34-56) caps each fragment at 200,000 characters, and the type defaults to `text/markdown`.
  - The fragments enter the prompts as text (`OfficeChatRuntimeDataQueryAdapterAgentService` :100-119, `GOfficeFragmentsPromptContextProvider` :40-52).
  - The answers come back as `AdditionalContent` documents split from the model's stream (`OfficeDocumentStreamSplitter`), markdown by default; the add-ins also handle HTML.

**Gap:** pictures in the user's selection, and the look of the document, are never seen.

**Proposed design:** after section 5.1, let the add-ins send selected pictures as additional contents with an image type and base64 content, mapped to `Media` through the guard. Low priority.

### 5.10 Web search, deep search and the web crawler

**Today:**
- **Search results.** `SearchResult` (:18-27) and `SearchResultReference` (:9-15) have no image fields.
- **Fetched pages are reduced to text:** `SearchResultsChunker` builds text documents (:540), and the crawl function keeps `body().text()` (`CrawlFunctionCallbackWrapperSource` :149-150).
- **The pro web crawler** (`GWebCrawlingService`) follows `<a href>` links (:250-251) and feed entries, never `<img>` sources. It saves any content type it reaches (`saveContent`, :441-470), so a picture behind a link is saved, and ingestion then finds no reader for it.
- `LinkTypeGuesser` (:25-40) already maps image, audio, video and pptx extensions to MIME types for search links.

**Gap:** pictures in web pages.

**Proposed design:** none on its own. Section 5.6 step 2 would describe the pictures the crawler saves. Low priority.

### 5.11 Usage and cost accounting

**Today:**
- **What is recorded.** `LLMUsageDetailDto` (:30-60) holds `inputToken`, `outputToken` and `totalToken`, with the cost, the times and the outcome. `LLMUsageRecorder` (:412-413) and `UsageAdvisorFactoryImpl` (:188-190, :209-211) read only `getPromptTokens()`, `getCompletionTokens()` and `getTotalTokens()`. The provider's native usage is never read.
- **Nothing is specific to a modality.** There are no image, audio, cached or reasoning token fields.
- **Prices.** `GModelPricingConditions` (:39-65) has a price per million input and output tokens, a price per request and a monthly flat cost. There is no price per image, per second or per character.
- **Transcription and speech** record a call with zero tokens (`UsageRecordingTranscriptModel` :36-47). **Image generation** records the usage the response carries, when it carries one (`UsageRecordingImageModel` :53-68).
- **Budgets count text only:** `TokensBudgetConfig` is one factor, `KnowledgeBaseSearchBudget` is arithmetic on the context length, and `TokensBudgetFluxCoordinator` counts what its callers give it, which are text documents.

**Gap:** the budgets ignore media. The recorded usage includes media only if the provider folds it into its prompt tokens, and that hasn't been checked.

**Proposed design:**
- Section 5.1's per-file estimate feeds the context budget.
- Once media is sent, check on each provider that the reported prompt tokens include it.
- A provider deal priced per image or per second of audio has no field today. Add one when a provider Gebo supports prices that way for chat input.

---

## 6. Cross-cutting design

### 6.1 One media guard

**One component decides which media goes to a model call.** Every area uses it: uploads, generated pictures, viewed pictures, inbound A2A files, Office pictures and ingestion descriptions.

**Its input:**
- the candidate files (MIME type, size, bytes);
- the configuration's declared `inputModalities` (section 4);
- what the provider's integration carries for that configuration (section 4.9 point 2, section 4.10).

**Its output:**
- the media to attach, as bytes;
- for the rest, a reason that can be shown or logged ("chart.png kept for the tools: this model does not read pictures").

**What it must never let through**, from section 3.5:
- a type the integration rejects (Anthropic, Bedrock, the Responses API with audio);
- a type the integration drops (everything on DeepSeek);
- a non-image file to Mistral or Ollama;
- anything but images, PDF and mp3 or wav audio to OpenAI chat completions and the compatible providers.

**Mapping MIME types to modalities:**

| MIME type | Modality |
|---|---|
| `image/*` | IMAGE |
| `audio/*` | AUDIO |
| `video/*` | VIDEO |
| `application/pdf` and office types | DOCUMENT |

### 6.2 Where the media travels

- `IChatRequestContext.getMedia()` (a default that answers empty) carries the turn's media. `ForwardingChatRequestContext` forwards it, and `createLastUserMessage` attaches it.
- No provider code changes for the conversion: Spring AI does it.
- The rewriter, the router and the image-prompt writer get a view of the context without media (section 5.1 point 4), so the cost stays on the answering model.
- The calls outside `prepareCall` (graph RAG extraction, the pro content workflows) are unaffected. An ingestion description call attaches its media itself, through the guard.

### 6.3 Pictures for agents

A picture an agent asks to see is appended as a user message by `GeboToolCallingAdvisor`, after the tool's text result (section 5.3). The same guard applies. This is the part to try on each provider first.

### 6.4 Limits

- **Per request:** a number of files and of bytes per file and in total. Today the upload box allows 10 MB a file and the server 100MB.
- **Large pictures** are downscaled before sending.
- **A per-file token estimate** for the context budget.
- Media is sent as bytes, never as addresses Gebo would make the provider fetch.

### 6.5 Data protection and the compliance register

- Media sent to a provider is document content leaving the installation.
- Describe the new flows in the data-flow and compliance views, next to `GMediaModelsDataFlowComponent`. Mark the model configurations that receive media.

### 6.6 The UI

- **Upload box:** the accepted types and the hint follow the chat's model (section 5.1).
- **Model editor, models list and wizards:** section 4.9 points 17 to 19.
- **Chat answers:** could show, per request, which files were sent to the model and which were kept for the tools.

---

## 7. Defects found along the way

These are separate from multimodality; each is a small fix of its own.

1. **Upload metadata lost.** In `GChatStorageAreaServiceImpl.addUploadedFile` (:154-156), `newMeta.putAll(newMeta)` merges the map into itself. The ingested chunks of a chat upload keep the common metadata and lose their handler's own.
2. **Generated files typed loosely.** `GChatStorageAreaServiceImpl.addMedia` (:312) stores `media.getMimeType().getType()`, for example `image` in place of `image/png`, and no file size. `GeboLLMGeneratedResourceController` (:33-35) then serves the file with that value as its Content-Type.
3. **Upload from the admin "Test chat" before its first answer.**
   - With no session code, `upload-chat-document.component.ts` (:112-120) posts to `chatSessionCreateWithUpload` or `ragChatSessionCreateWithUpload`. `GeboUserChatUploadsController` has neither.
   - The chat section always creates the chat first, so it never takes that path.
   - The "Test chat" dialog of the model editor (`gebo-ai-test-chat.component.html` :14) passes only a model code, so an upload before the first answer takes it. This was read in the code and not run.
4. **MCP results flattened** (section 5.4), and **A2A file parts dropped by the client with no log** (section 5.5).
5. **No knowledge base reader for .pptx, .ppt or .rtf** (section 5.6).
6. **Setup lines without effect:** `GeboLLMSSetupService.createLLMS` :1191-1194 (section 4.3).
7. **xAI's input modalities kept only as a vision flag** (section 4.8 point 5).
8. **Unused code:**
   - `ChatModelFeatures` and `GBaseChatModelConfig.features` (a persisted and exposed field);
   - `GTikaFailBackContentHandler` (its `@Service` is commented out);
   - `A2AServerBuilder.renderParts` (:300);
   - in `ClientChatCallUtil`: `getChatHistory`, `createLastUserMessage`, `createSystemMessage`, `createPromptAndContext`, `createPromptContextHistory` and `createHistoryFragment`;
   - `GChatSessionLifeCycleServiceImpl.addLLMGenerated`;
   - the chat UI's `modelMetaInfos`, assigned and never read.

`GDocumentTranslatorImpl.fromChatResponse` and `GeboDocument.tryAddMedia` are **in use** by the pro content workflows and stay.

---

## 8. Proposed roadmap

Each step is sized to be one pull request (or a short series), with its own tests.

| Step | What | Depends on | Tests |
|---|---|---|---|
| 0 | Fix defects 1 to 3, 6 and 7; remove the unused code of defect 8 | none | unit tests on upload metadata and generated file types |
| 1 | The model record: modalities by pattern in the library, library and preset entries completed, the editor's insert filled, old configurations offered their metadata (section 4.9 points 4, 5, 8, 10, 11) | none | library segment tests per provider; `ChatModelSetupInputModalitiesTest` per vendor |
| 2 | What each integration carries, and the guard (section 4.9 points 1 to 3, section 4.10, section 6.1) | none | unit tests against the provider table; one live request per provider and type |
| 3 | Listings: choices and `ModelProviderCapabilities` carry declared and effective inputs; editor, list and wizard show them; clients regenerated (points 9, 12, 16 to 19) | 1, 2 | controller tests; UI check |
| 4 | Chat uploads to the answering model: `IChatRequestContext.getMedia`, `createLastUserMessage`, the service calls' view, budget, later-turn rule | 2 | fake-model integration test of the answering and the service prompts |
| 5 | Upload box and hints follow the model; compliance flows described | 3, 4 | UI check; data-flow view |
| 6 | Knowledge base readers for .pptx/.ppt and .rtf | none | reader unit tests |
| 7 | "View picture" for agents through the tool-calling advisor, tried on OpenAI, Anthropic and Google | 2 | fake-model test of the next prompt; one live run per provider |
| 8 | MCP non-text results and A2A file parts kept as conversation files; A2A outbound file parts and card modes | none (viewing with 7) | MCP and A2A integration tests with a picture-returning stub |
| 9 | Generated pictures in follow-up turns and as a files location; agents route output media kept; image editing | 4 | fake-model test |
| 10 | Opt-in picture and scan descriptions at ingestion, with the model named by use or per knowledge base | 2 | fake-model ingestion test |
| 11 | Audio and video files transcribed into knowledge bases | none | ingestion test with a fake transcription model |
| 12 | Office add-in pictures; web pictures; output modalities; multimodal embeddings | 4, 10 | to plan when reached |

---

## 9. Decisions needed

1. **Where to start.** The recommendation is steps 1 to 3 (the model record and its listings), then 4 and 5 (chat uploads). Steps 1 to 3 change nothing a user sees except better-described models, and everything after them depends on that record being right.
2. **Old configurations** (section 4.9 point 11). Either an administrator action that applies the stored metadata to all configurations (recommended: it keeps today's "nothing changes until someone acts" rule and makes the act one click), or a one-time fill at startup.
3. **The admin editor's insert** (point 10). Fill the modalities from the model's metadata on the server, as the wizard does (recommended), or keep the "use these" click as the only way.
4. **Where a family's modalities live** (points 4 and 8): library rules by pattern (recommended, since they also cover models outside the presets) or values on each preset choice.
5. **Pictures on later turns.** Send a picture only on the turn it was uploaded and name it in the history (cheapest), or re-send it on every later turn (costs tokens each time), or keep a short description the model writes on the first turn.
6. **A file the chat's model can't read.** The proposal keeps today's behaviour: the file is kept for the tools, and the user is told why. Another option is to describe it with another configured model that reads it (point 14).
7. **The model that reads media for others** (point 14): a new `ChatModelsUses` value with one default, or a model chosen per knowledge base.
8. **Ingestion descriptions.** Whether to build step 10, and with which limits.
9. **Viewing pictures in agents.** Whether to accept the user-message approach of section 5.3, given the per-provider trial it needs.

---

## 10. Glossary

- **Modality:** a kind of input a model reads (text, image, audio, video, document), as declared in `GModelInputModality`.
- **Declared modalities:** `GBaseChatModelConfig.inputModalities`, what the configuration says its model reads.
- **Carried types:** the MIME types a provider's Spring AI integration converts into its request (section 3.5).
- **Effective inputs:** what is both declared and carried; the only files sent.
- **Media:** Spring AI's `org.springframework.ai.content.Media`: a MIME type plus bytes or a string address, attached to a message.
- **Models library:** the `bundle-models/*.yml` files and the installation's `ai.gebo.models.library`: per-model information (`modelsInfo`) and request rules (`parameterRules`).
- **Preset:** a vendor's suggested models in `llms-fast-setup-library/library.yml`, used by the setup wizards.
- **Tools-only upload:** a chat upload no content handler reads, kept because the chat's agents' libraries work on its type. It is stored with `tokensCount` 0.
- **Media guard:** the proposed component of section 6.1.

---

## 11. What changed from the first edition

Corrections after re-reading the code:

- **Provider table (3.5).** Mistral and Ollama don't refuse non-image files: they send any file as an image. OpenAI chat completions doesn't "convert video to text": it pastes any unsupported file into the prompt as base64. Bedrock raises an error for audio; it doesn't ignore it. Bedrock video is taken as bytes or an address, not fetched. OpenAI audio is declared WAV unless the type contains "mp3".
- **Tool results (3.4).** A `ToolResponseMessage` can be built with media parts; no provider reads them.
- **Prompt path (3.2).** Not every model call goes through `prepareCall`: graph RAG extraction and the pro content workflows don't. The method names of `ChatPipelineServiceImpl` are `chat` and `streamingChat`.
- **Service calls.** The router, the rewriter and the image-prompt writer use the full request context, not `MinimalChatContext`, so they would receive the media too unless given a view without it.
- **Tools-only uploads** are not named in the history; the model learns of them only by listing the uploads location.
- **`GDocumentTranslatorImpl.fromChatResponse`** is used by pro and is not removable.
- **Unread files** are flagged when their content is read, not discarded at discovery.
- **The crawler** saves non-HTML content it reaches through links.
- **`extractImages`** returns names, not sizes.
- **A2A client** line numbers; the A2A server logs what it didn't take.
- **Defect 3** is reached from the admin "Test chat" dialog, not from the chat section.
- **Usage accounting (5.11)** now states the recorded fields.
- **Section 4 is new.**

---

## 12. File index

Community repository paths are relative to its root. Pro paths start with `Gebo.ai.pro/`.

**Models, library and prompts** (`gebo.architecture.parent/gebo.architecture.llms.abstraction.layer/src/main/java/ai/gebo/llms/`)
- `abstraction/layer/model/`: `GBaseChatModelConfig`, `GBaseModelConfig`, `GBaseModelChoice`, `GBaseChatModelChoice`, `GModelInputModality`, `ChatModelFeatures`, `ChatModelsUses`, `GModelPricingConditions`, `IChatRequestContext`, `ForwardingChatRequestContext`, `GChatAnswerChunk`
- `abstraction/layer/services/`: `GAbstractConfigurableChatModel`, `IGConfigurableChatModel`, `IGChatModelConfigurationSupportService`, `IGModelConfigurationSupportService`, `IGChatModelRuntimeConfigurationDao`, `ModelRuntimeConfigureHandler`, `ClientChatCallUtil`, `IGConfigurableImageModel`, `IGConfigurableTranscriptModel`, `IGConfigurableTextToSpeechModel`, `KnowledgeBaseSearchBudget`, `TokensBudgetFluxCoordinator`
- `abstraction/layer/services/impl/`: `GModelChoiceMetaInfoEnricherServiceImpl`, `LLMTypeFiltrerImpl`, `LLMUsageRecorder`, `UsageAdvisorFactoryImpl`, `GDocumentTranslatorImpl`
- `abstraction/layer/services/toolcalling/`: `GeboToolCallingAdvisor`, `GeboToolCallingManager`, `GeboToolCalling`
- `abstraction/layer/controllers/`: `AbstractBaseChatModelsConfigurationCRUDController`, `BaseChatModelsConfigurationController`
- `abstraction/layer/dto/LLMUsageDetailDto.java`
- `models/metainfos/`: `ModelMetaInfo`, `ModelParameterRules`, `GProviderModelsLibrary`, `impl/GModelsLibraryDaoImpl`, `config/ModelsLibraryConfig`
- Listing controllers: `gebo.architecture.parent/gebo.architecture.llms.abstraction.layer.controllers/src/main/java/ai/gebo/llms/abstraction/layer/controllers/` (`ChatModelsController`, `ChatModelsLookupController`, `ImageModelsController` and the other kinds)

**Setup and presets** (`gebo.llms.parent/gebo.llms.setup/src/main/`)
- `java/ai/gebo/llms/setup/services/GeboLLMSSetupService.java`
- `java/ai/gebo/llms/setup/config/`: `LLMModelPresetChoice`, `LLMSModelsPresets`
- `java/ai/gebo/llms/setup/model/LLMCreateModelData.java`
- `java/ai/gebo/llms/setup/controllers/GeboFastLLMSSetupController.java`
- `resources/llms-fast-setup-library/library.yml`
- test: `src/test/java/ai/gebo/llms/setup/services/ChatModelSetupInputModalitiesTest.java`

**Providers** (`gebo.llms.parent/`; each has `src/main/resources/bundle-models/<provider>.yml` and a `*.controllers` module)
- `gebo.llms.openai/`: `OpenAIChatModelConfigurationSupportService`, `OpenAiApiRouting`, `ReasoningSummariesFallbackChatModel`; `bundle-models/openai.yml`
- `gebo.llms.openai.api.utils/`: `GOpenAIApiUtilImpl`
- `gebo.llms.generic-openai-compatible/`: `GenericOpenAIAPIChatModelConfigurationSupportService`, `ModelsListProviderProxyService`, `OpenRouterModelsListProviderService`, `RegoloAIModelsListProviderService`, `XaiEmbeddingModelsListProviderService`, `NonChatModelsTypeFiltrer`; `openai-compatible-providers/providers.yml`; `bundle-models/` (xai, nvidia, groq, openrouter.ai, regolo.ai and the others)
- `gebo.llms.anthropic3/`: `AnthropicChatModelConfigurationSupportService`, `AnthropicModelInfoMapper`, `AnthropicModelsLookupService`
- `gebo.llms.google_vertex/`: `GoogleVertexChatModelConfigurationSupportService`; `bundle-models/google-vertex.yml`
- `gebo.llms.mistral/`: `MistralChatModelConfigurationSupportService`, `MistralModelsLookupService`, `MistralModelCapabilities`
- `gebo.llms.ollama/`: `OllamaChatModelConfigurationSupportService`, `OllamaModelsLookupService`
- `gebo.llms.aws-bedrock/`: `BedrockChatModelConfigurationSupportService`, `BedrockFoundationModelsLookupService`, `BedrockImageModel`
- `gebo.llms.deepseek/`: `DeepseekChatModelConfigurationSupportService`, `DeepseekModelsLookupService`
- `gebo.llms.tests.services/`: `TestChatModel`, `TestChatModelSupportServiceImpl`
- OpenRouter client: `gebo.integrations.clients/gebo.openrouter.ai.client/src/main/java/ai/gebo/openrouter/client/model/ModelArchitecture.java`
- Spring AI jars: `~/.m2/repository/org/springframework/ai/spring-ai-<provider>/2.1.0-RC2/`

**Chat** (`gebo.architecture.parent/gebo.architecture.chat.abstraction.layer/src/main/java/ai/gebo/llms/`)
- `chat/pipelines/service/impl/`: `ChatPipelinesExecutorImpl`, `ChatPipelineServiceImpl`
- `chat/pipelines/service/defaultsteps/impl/`: `DefaultRoutingChatPipelineStepServiceImpl`, `DefaultImageGenerationStreamingOutputChatPipelineServiceImpl`, `DefaultChatWithFilesStreamingOutputPipelineServiceImpl`
- `chat/abstraction/layer/services/`: `IGGenericalChatService` (`ModelProviderCapabilities`)
- `chat/abstraction/layer/services/impl/`: `GChatSessionLifeCycleServiceImpl`, `GChatServiceImpl`, `GRagChatServiceImpl`, `AbstractChatService`, `GChatStorageAreaServiceImpl`, `GUserUploadContentHandlerImpl`, `GChatUploadFileTypesServiceImpl`, `ChatUploadsFilesProvider`, `GTranscriptServiceImpl`, `GTextToSpeechServiceImpl`, `GMediaModelsDataFlowComponent`
- `chat/abstraction/layer/llmexchange/model/`: `LLMChatRequestResources`, `AdditionalContent`, `GeboTemplatedChatResponse`
- `chat/abstraction/layer/session/model/`: `MinimalChatContext`, `CSSSimplefiedInteraction`, `GUserChatSession`
- `deepsearch/service/SearchResultsChunker.java`
- REST: `gebo.ragsystem.parent/gebo.ragsystem.client.rest/src/main/java/ai/gebo/llms/chat/client/rest/controllers/` (`GeboUserChatUploadsController`, `GeboChatController`, `GeboRagChatController`, `GeboTranscriptController`, `GeboTextToSpeechController`, `GeboLLMGeneratedResourceController`)

**UI** (`gebo.ui/projects/`)
- Upload box: `gebo-ai-reusable-ui/src/lib/controls/choose-documents-panel/upload-chat-document.component.ts` and `.html`; its host `gebo-ai-reusable-ui/src/lib/controls/chat-control/chat-input-shell.component.html`
- Chat: `gebo-ai-reusable-ui/src/lib/controls/chat-control/gebo-ai-reusable-chat.component.ts`
- Model editor: `gebo-ai-admin-ui/src/lib/admin-ui/entity-editors/controls/standard-chat-model-settings/`; the provider editors and the test chat in `gebo-ai-admin-ui/src/lib/admin-ui/entity-editors/gebo-ai-models-admin/`
- Wizards: `gebo-ai-admin-ui/src/lib/setup-wizard/llms-easy-wizard/` and `llms-setup-components/`

**Agents, tools, MCP, A2A** (`gebo.architecture.parent/`)
- `gebo.architecture.agents.standard/src/main/java/ai/gebo/llms/agent/`: `chat/service/impl/` (`AgenticLoopReactiveAgentServiceImpl`, `ReportWriterReactiveAgentServiceImpl`, `ReactiveChatAgentsNetworkStreamingOutputChatPipelineService`), `standard/services/` (`GChatEnvironmentNetworkRunner`, `GBaseToolCallingNetworkAgent`), `standardlibraries/WorkingDirectoryLibrarySource.java`, `standardtools/ToolsContextSharing.java`
- `gebo.architecture.agents.abstraction.layer/src/main/java/ai/gebo/architecture/agents/services/ConversationFilesRenderer.java`
- `gebo.architecture.ai/src/main/java/ai/gebo/architecture/ai/`: `service/ToolCallbackDeclarationUtil.java`, `files/GConversationFilesText.java`
- `gebo.architecture.mcp-clients/src/main/java/ai/gebo/architecture/mcpclients/service/impl/McpRemoteToolCallback.java`
- `gebo.architecture.mcp-server/src/main/java/ai/gebo/architecture/mcpserver/runtime/`: `GeboMcpToolsProvider`, `GeboMCPAgentAsToolProvider`, `GeboMCPAgentsNetworkAsToolsProvider`, `GeboMcpResourcesProvider`, `GeboMcpPromptsProvider`
- `gebo.systems.parent/gebo.mcp-client.content.handler/src/main/java/ai/gebo/mcpclient/content/handler/impl/GMCPClientRemoteVirtualFilesystemConsumingServiceImpl.java`
- `gebo.architecture.a2a-server/src/main/java/ai/gebo/architecture/a2aserver/runtime/`: `A2AInboundResources`, `A2AResultAdapter`, `A2AAgentCardBuilder`, `A2AServerBuilder`, `A2AChatEnvironmentRunner`
- `gebo.architecture.a2a-clients/src/main/java/ai/gebo/architecture/a2aclients/service/impl/`: `A2AClientConnector`, `RemoteA2ANetworkAgentService`, `A2AClientManagementServiceImpl`

**Ingestion and search**
- Readers registry: `gebo.architecture.parent/gebo.system.ingestion/src/main/resources/bundle-readers/readers.yml`
- `gebo.architecture.parent/gebo.system.ingestion/src/main/java/ai/gebo/system/ingestion/`: `impl/` (`GDocumentReferenceIngestionHandlerImpl`, `GTikaFailBackContentHandler`, `GAIDocumentCatalogingEnricherImpl`), `pdf/impl/` (`PdfIngestionHandler`, `ColumnAwarePdfTextExtractor`), `msformats/impl/` (`MSWordIngestionHandler`, `MsExcellIngestionHandler`), `odformats/impl/` (`GOdtIngestionHandlerImpl`, `GOdpIngestionHandlerImpl`), `text/impl/` (`HtmlIngestionHandler`, `JsonIngestionHandler`)
- `gebo.architecture.parent/gebo.architecture.contentsystems.abstraction.layer/src/main/java/ai/gebo/systems/abstraction/layer/impl/GContentDispatchingEvaluatorImpl.java`
- `gebo.architecture.parent/gebo.architecture.graphrag.extraction/src/main/java/ai/gebo/architecture/graphrag/extraction/services/impl/GraphDataExtractionServiceImpl.java`
- `gebo.core.parent/gebo.base.model/src/main/java/ai/gebo/document/model/GeboDocument.java`
- `gebo.architecture.parent/gebo.architecture.search.abstraction.layer/src/main/java/ai/gebo/architecture/search/`: `model/SearchResult.java`, `model/SearchResultReference.java`, `service/LinkTypeGuesser.java`
- `gebo.llms.parent/gebo.llms.standard.functions/src/main/java/ai/gebo/llms/standard/functions/CrawlFunctionCallbackWrapperSource.java`

**Pro and the add-ins**
- Office libraries: `Gebo.ai.pro/ai.gebo.pro.architecture.parent/gebo.architecture.libraries.document-models-tools/src/main/java/ai/gebo/libraries/documentmodels/` (`text/library/TextDocumentsLibrarySource.java`, `presentation/library/PresentationsLibrarySource.java`, `spreadsheet/library/SpreadsheetsLibrarySource.java`, `text/GImage.java`)
- Sandbox library: `Gebo.ai.pro/ai.gebo.pro.architecture.parent/gebo.architecture.libraries.sandboxes/src/main/java/ai/gebo/architecture/libraries/sandboxes/services/impl/OpenSandboxLibrarySource.java`
- Office pipeline: `Gebo.ai.pro/ai.gebo.pro.architecture.parent/gebo.architecture.office-plugin-pipeline/src/main/java/ai/gebo/officeplugin/pipeline/agents/` (`OfficeFragments`, `OfficeDocumentStreamSplitter`, `OfficeChatRuntimeDataQueryAdapterAgentService`, `GOfficeFragmentsPromptContextProvider`)
- Content workflows: `Gebo.ai.pro/ai.gebo.pro.architecture.parent/gebo.architecture.content.workflows/` (`AbstractContentLLMPromptRobotStepOperatorImpl`)
- Web crawler: `Gebo.ai.pro/ai.gebo.pro.systems.parent/gebo.webcrawler.handler/src/main/java/ai/gebo/webcrawler/handler/impl/GWebCrawlingService.java`
- Office libraries integration test: `Gebo.ai.pro/integration-tests/office-libraries-integration-tests/`
- Add-ins (their own repositories): `Gebo.ai.microsoft-office-plugin`, `Gebo.ai.only-euro-officeplugin`, `Gebo.ai.libre-open-office-plugin`

---

## 13. Implementation status

Branch `feature/moltimodality`. The decisions of section 9 were taken as recommended: an administrator action for the old configurations, the editor's insert filled on the server, a family's modalities in library rules, and the conversation's files sent on every turn within limits (as the text of an uploaded document is).

### Done

| Roadmap step | What was built | Where | Tests |
|---|---|---|---|
| 1 | Input modalities by library rule: `ModelParameterRules.inputModalities`, read apart from the parameters (`IGModelsLibraryDao.findInputModalities`), filled into the choices by the enricher when neither the api nor the model's own entry told them | `ModelParameterRules`, `GModelsLibraryDaoImpl`, `GModelChoiceMetaInfoEnricherServiceImpl` | `GModelChoiceMetaInfoEnricherServiceImplTest`, `OpenAIModelsLibrarySegmentTest`, `ProviderModelsLibrariesWiringTest` |
| 1 | Libraries completed from the vendors' pages (2026-11-19): OpenAI (`gpt-6-astra`, `gpt-6-sol`, `gpt-5.5`; PDF for the models that take images; the dated variants by rule), Gemini 3.6 and 3.7 flash, the six xAI models, Groq's two vision models, NVIDIA `adept/fuyu-8b` | `bundle-models/*.yml` | the segment tests |
| 1 | xAI keeps the api's modalities list; the editor's insert fills the modalities from the chosen model's metadata; `applyModelsMetadataInputModalities` gives the old configurations their stored metadata | `XaiEmbeddingModelsListProviderService`, `AbstractBaseChatModelsConfigurationCRUDController`, `ChatModelsController` | |
| 2 | What each integration carries (`GModelCarriedMediaTypes`, `carriedMediaTypes` on every provider's support service) and the guard (`GModelMediaGuard`) | abstraction layer, every module under `gebo.llms.parent` | `GModelMediaGuardTest` |
| 3 | Listings: `getChatModelsInputs`, `getCarriedMediaTypes`, `ModelProviderCapabilities.inputModalities` and `inputMimeTypes`, `GChatUploadFileTypes.modelMimeTypes`; Angular stubs and Java client regenerated | `ChatModelsController`, `IGGenericalChatService`, `GChatUploadFileTypesServiceImpl` | |
| 3, 5 | UI: the upload box accepts and names the files the chat's model reads; the model editor shows the file types sent and the declared modalities not delivered; the models list has an "Accepts" column and the "Use the models' metadata" action | `upload-chat-document`, `standard-chat-model-settings`, `llms-systems` | template type-check |
| 4 | Chat uploads to the answering model: `IChatRequestContext.getMedia` and `withoutMedia`, attachment in `createLastUserMessage` through the guard, the session's unread uploads as candidates within `ai.gebo.chat.media` limits (10 files, 20 MB a file, 40 MB a request), an upload the chat's model takes is accepted, the router, the rewriter and the image prompt writer get no file | `GAbstractConfigurableChatModel`, `GChatStorageAreaServiceImpl`, `LLMChatRequestResources`, `DefaultRoutingChatPipelineStepServiceImpl` | `ChatRequestMediaAttachmentTest`, `ChatUploadsForMultimodalModelsTest` |
| 9 (part) | The pictures, recordings and videos a chat generated travel with its later requests | `GChatStorageAreaServiceImpl.getSessionMedia` | `ChatUploadsForMultimodalModelsTest` |
| 6 | Knowledge base readers for .pptx and .ppt (one document per slide: title, texts, tables, speaker notes, with the slide number) and .rtf (Tika's RTF parser); a chat upload of these types is now read into the chat too | `MSPowerPointIngestionHandler`, `RtfIngestionHandler`, `readers.yml` | `MSPowerPointIngestionHandlerTest`, `RtfIngestionHandlerTest` (speaker notes of a .ppt not covered: POI can't write them) |
| 8 | Files from outside are kept in the conversation: a read-only `generated` files location (what the conversation generated or received) and `IGConversationGeneratedFilesStore`. An MCP tool's image, audio and blob contents are kept there, listed among the files the request wrote and named to the model; its structured content is given as JSON when it has no text. A remote A2A agent's file parts sent as bytes are kept the same way. The A2A server sends the pictures a run generated as file parts and advertises `image/png` | `ChatGeneratedFilesProvider`, `McpRemoteToolCallback`, `RemoteA2ANetworkAgentService`, `A2AResultAdapter`, `A2AAgentCardBuilder` | `McpRemoteToolResultFilesTest`, `A2AResultAdapterTest`, `ChatUploadsForMultimodalModelsTest` |
| 7 | Tools show files to the model that called them: `IGToolShownFiles` in the tools context of the calls whose loop Gebo runs; what a round's tools showed reaches the model in a user message after the tools' results, when the model takes that type. A `viewFile` tool in the working directory library (a file of the directory, or of `uploads`, `generated` or another location). A picture an MCP tool returns is shown at once | `GToolShownFiles`, `GeboToolCallingAdvisor`, `WorkingDirectoryLibrarySource`, `McpRemoteToolCallback` | `ToolShownFilesTest`, `WorkingDirectoryLibrarySourceTest`, `McpRemoteToolResultFilesTest` |
| 10 | Pictures and the pages without text of PDF files described by a chat model that reads pictures, off until `ai.gebo.ingestion.media.describe-pictures` is turned on; the model is the one named, else the default when it reads pictures, else the first that does; 30 pages a document at most; the text is marked `gebo_model_written` | `IGMediaTextExtractor`, `MediaIngestionHandler`, `PdfIngestionHandler`, `GMediaTextExtractorImpl` | `MediaIngestionHandlerTest`, `GMediaTextExtractorImplTest` |
| 11 | Recordings and videos (.mp3, .wav, .m4a, .mpga, .mp4, .mpeg, .webm) transcribed by the default transcription model, off until `ai.gebo.ingestion.media.transcribe-recordings` is turned on | same classes | same tests |
| 10 | The pictures inside the documents are described too, each as a document of its own marked `gebo_model_written`: Word (.docx, .doc), PowerPoint (per slide, with the slide number; layout and master pictures are not taken), Excel, OpenDocument text, presentations and spreadsheets (the package's `Pictures/`), and the pictures a web page carries in itself (`data:` addresses). Only PNG, JPEG, GIF and WebP of at least 2 KB; a picture repeated in a document is described once; at most as many as the pages described of one document | `EmbeddedPicture`, `GAbstractConfiguredHandler.describedPictures`, the handlers under `msformats`, `odformats`, `text` | `EmbeddedPicturesIngestionTest` |
| 8 | A2A, the rest: the server's answer carries the files the run's tools wrote (read from the location they were written to; 20 MB a file, 40 MB an answer) and tells in the run's account (`notTaken`) what the caller sent that was not taken in; the client fetches a file the remote agent names at an address of its own (same scheme, host and port as its card or endpoint, with the agent's credentials, no redirect, 20 MB) and keeps it in the conversation | `A2AResultAdapter`, `A2AServerBuilder`, `A2AClientConnector.fetchLinkedFile`, `RemoteA2ANetworkAgentService` | `A2AResultAdapterTest`, `A2AClientConnectorIntegrationTest` |
| 6.5 | The compliance register names the files sent as they are to each agent's chat model, and what the ingestion gives to the model describing pictures and to the transcription model when those are on | `DataFlowEndpoints.sentFileTypes`, `GAgentsNetworkDataFlowComponent`, `GMediaModelsDataFlowComponent` | `GAgentsNetworkDataFlowComponentTest`, `GMediaModelsDataFlowIngestionTest` |
| 10, 11 | Microservices: the service reading the documents (the chunker) holds no model, so it gives the pictures and the recordings to the service holding them (the brain) and asks it what it reads; where the brain can't be reached nothing is read and it is asked again a minute later | `IngestionMediaController`, `GMicroserviceMediaTextExtractorClient`, `MicroserviceDocumentsAccessClientConfiguration` | `GMicroserviceMediaTextExtractorClientTest` (a stand-in for the brain; not run on a stack) |
| 8 | A2A, outbound: the conversation's files of a type the remote agent's card takes travel to it with their bytes (20 MB a file, 40 MB a message); a network exported by Gebo says it takes pictures, recordings, videos and PDF files; a file a caller names by link is fetched from the hosts listed in `ai.gebo.a2a.server.linked-files-hosts` (none at first) | `A2AClientConnector.acceptedInputModes`, `RemoteA2ANetworkAgentService.filesFor`, `A2AAgentCardBuilder`, `A2ALinkedFilesFetcher` | `A2AClientConnectorIntegrationTest`, `A2AInboundResourcesTest` |
| 8 | MCP server: a network exported as a tool with a chat profile (`GeboMCPAgentsNetworkTool.chatProfileCode`, a field of the server's editor) runs in a new conversation of the caller and answers with the files of its run beside its text: a picture as image content, a recording as audio content, any other file as an embedded resource. Without a profile it answers as before | `GeboMCPAgentsNetworkAsToolsProvider.callInAConversation`, `ChatRunFiles` (shared with A2A) | `GeboMCPAgentToolsToolCallingTest` |
| 6.5 | The chat with a model directly has its entry in the compliance register: every configured model, with the files it takes | `GStandardChatPipelineDataFlowComponent.addDirectChat` | `GStandardChatPipelineDataFlowComponentTest` |
| 4 | The context budget keeps room for the files a request carries (`ai.gebo.chat.media.estimated-tokens-per-file`, an estimate) | `GChatSessionLifeCycleServiceImpl` | |
| 4.9 points 6, 15, 19 | Output modalities recorded where the api tells them (OpenRouter, Bedrock, DeepSeek); a chat model the library knows is kept in the OpenAI and Vertex lists although its id names another kind; the setup wizard tells what the chosen chat model accepts | `ModelMetaInfo`, `IGModelsLibraryDao.knowsAsChatModel`, `llms-vendor-modeltype` | `GoogleVertexChatModelChoicesTest` |
| 0 | Defect 3: the two endpoints the upload box posts to before a chat exists are back (`chatSessionCreateWithUpload`, `ragChatSessionCreateWithUpload`). Five methods without callers removed (`ClientChatCallUtil`, `A2AServerBuilder.renderParts`) | `GeboUserChatUploadsController` | not run in the browser |
| 0 (part) | Defect 1 (upload metadata) and defect 2 (generated files' type and size) fixed; defect 7 (xAI) fixed | `GChatStorageAreaServiceImpl` | |

### Differences from the proposal

- **Carried types are asked of the support service, not written on each choice** (section 4.9 point 9). A choice is stored with its configuration, so a value written on it would go stale; `getCarriedMediaTypes(modelTypeCode, modelCode)` answers it live.
- **`GeboLLMSSetupService.createLLMS` :1191-1194 stays.** `ChatModelSetupInputModalitiesTest` shows the lines act when a provider's base configuration carries its model's metadata.
- **Google's carried documents are PDF only**, although the integration sends any type: an office file would reach Gemini as a type it does not read.
- **mp3 audio is sent as `audio/mp3`**, since Spring AI's OpenAI chat model declares a file MP3 only when its type contains "mp3".
- **Bedrock DOCUMENT rules were not added.** AWS's page of supported features per model was replaced by per-model cards with no document row (read 2026-11-19), so nothing grounds them. An administrator can declare DOCUMENT on a Bedrock model by hand, and the integration then carries the nine document types.

### Not done yet

- **No request was sent to a real provider.** Everything that reaches a provider was tested against fake models. Owed, per provider and file type:
  - a file in the last user message (section 3.5 is from bytecode);
  - **a user message with a picture right after the tools' results** (step 7). The fake-model test proves the message is built; whether each provider accepts that order is not known. OpenAI, Anthropic and Google have to be tried before agents are given `viewFile` in production.
  - a picture description and a transcription at ingestion;
  - a remote A2A agent sending, naming and receiving files, and an A2A caller receiving the files a run wrote;
  - an MCP client calling a network exported with a chat profile and reading the picture it answers with;
  - the chunker asking the brain for a description on a microservices stack. The microservices' own client stubs were not regenerated for the brain's new controller (the chunker's client is written by hand, as its documents client is).
- **Nothing was looked at in the browser:** the upload box, the model editor, the models list, the wizard line and the upload before a chat's first message.
- **Choices taken without asking, to revisit:**
  - descriptions and transcription are switched for the whole installation, not per knowledge base, and no cost estimate is shown before a run;
  - the model that describes pictures is found by a setting or by what the models declare, not by a new model use;
  - a chat upload the chat's model takes as it is (picture, recording, video) goes to the model and is not described or transcribed, even when the readers are on;
  - the room kept for a file in the context is a fixed estimate.
- **Not built:**
  - an agent (not a network) exported as an MCP tool, and the tools of Gebo exported as they are, still answer with text only; each call of a network with a chat profile leaves a conversation of its own, since MCP has no conversation id to go on with;
  - a file a remote A2A agent names at an address that is not its own is named, not fetched;
  - image editing (step 9): Spring AI's `ImageModel` takes a text prompt only, so a picture cannot be given back to an image model through it;
  - whether each provider's reported prompt tokens include the files sent was not checked, and there is no price per image or per second of audio (section 5.11);
  - the limits of `ai.gebo.chat.media` and the switches of `ai.gebo.ingestion.media` are settings of the installation, like the other ingestion settings: no screen edits them;
  - a picture a web page names by address, and the pictures of an .xls/.xlsx drawing that are charts (not image files), are not described;
  - `ChatModelFeatures`, `GBaseChatModelConfig.features`, `GTikaFailBackContentHandler`, `addLLMGenerated` and the chat UI's `modelMetaInfos` were left: the first two are persisted and exposed, the others span interfaces;
  - step 12: Office add-in pictures (three other repositories), web pictures, multimodal embeddings.
- **Pro** (branch `feature/moltimodality` there): the text documents library's agent rules tell the agent to look at a picture with `viewFile`, and `extractImages` / `extractSlideImages` show the model the pictures they copy (`shown` in their answer).
