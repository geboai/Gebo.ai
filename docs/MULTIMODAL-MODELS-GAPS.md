# Multimodal models: gap analysis

Where Gebo.ai could hand pictures, audio, video and documents to the models that accept them, what stands in the way today, and an order in which to build it.

**Status:** analysis only. Nothing described here as missing has been built.
**Date:** 2026-11-18.
**Code read:** community `develop` at `88917c3f5`, pro `develop` at `1289b8a`, and the Spring AI `2.1.0-RC2` jars.

---

## 1. Scope and method

**In scope:** every path by which content reaches a model:
- chat requests and their uploads;
- the chat history;
- the agents and their tools and libraries;
- MCP and A2A;
- knowledge base ingestion, including graph RAG extraction and embeddings;
- voice input and speech;
- image generation;
- the Office add-ins;
- web search, deep search and the web crawler.

**Method:**
- **Source reading** of both repositories (`src/main` only; generated REST clients left out).
- **Bytecode reading** of the Spring AI 2.1.0-RC2 jars in the local Maven repository. This was done for each provider's chat model, for the message part types and for the tool-calling classes, to see what each provider does with media in a message. When grepping class files, search them as text (`grep -a`): a plain `grep -l` misses matches inside them.

**Not done:** no request was run against a real provider. "Supported" in the provider table means the Spring AI code converts that media type into the provider's request format. Whether a given model then reads it depends on the model, and is what `inputModalities` records.

**References:** classes are cited by name, with the module in parentheses the first time. Section 10 gives the full paths. Line numbers are those of the commits above; methods are named as well, so a reference survives small edits.

---

## 2. Summary

- **Gebo gives every model text and nothing else.**
  - Every prompt is built in one method, which returns a text-only `UserMessage`.
  - Pictures uploaded to a chat are kept for the agents' tools, and the models never see them.
  - Pictures inside ingested documents are dropped.
  - The MCP client replaces images with a placeholder, and the A2A client drops file parts.
  - Audio is transcribed to text before any model sees it.
- **The code says so.** `GBaseChatModelConfig.inputModalities` is documented with: "It characterizes the model; Gebo does not yet give the models anything but text."
- **The foundations are in place:**
  - Each chat model declares its input modalities (TEXT, IMAGE, AUDIO, VIDEO, DOCUMENT). They are filled at setup and editable in the model editor.
  - All prompts are built in `GAbstractConfigurableChatModel.prepareCall`, which makes a single point of change.
  - Spring AI 2.1 puts `Media` on a user message. Seven of the eight provider integrations Gebo uses convert at least images.
  - Chat uploads of pictures are already stored, and already reach the agents' tools.
- **Where the value is, in order:**
  1. Pictures and PDFs uploaded to a chat, sent to models that read them.
  2. Agents looking at pictures they were given or made.
  3. Knowledge bases learning from slides, pictures and scans.
  4. Files exchanged over MCP and A2A, kept as conversation files instead of lost.
- **Constraints to design around:**
  - Spring AI 2.1 carries tool results as text only.
  - DeepSeek drops media without an error.
  - Anthropic raises an error for anything other than images and PDF.
  - The OpenAI Responses API raises an error for audio.
  - Media isn't counted in any of Gebo's token budgets.

---

## 3. Foundations already in place

### 3.1 Model characterization

**`inputModalities`** is declared on `GBaseChatModelConfig` (gebo.architecture.llms.abstraction.layer) as `List<GModelInputModality>`. Null or empty means text only.

**Where it is filled:**
- `IGChatModelConfigurationSupportService.insertAndConfigureModel`: when the field is null, it is filled from the chosen model's metadata through the static `inputModalitiesOf()`, which reads `ModelMetaInfo.knownInputModalities()`. That method falls back to `supportsVision`, giving TEXT plus IMAGE.
- `GeboLLMSSetupService` (gebo.llms.setup) copies the preset value in `createChatModel`, `applyPresetDefaults` and `createLLMS`.
- The providers' model lookups set it from their metadata: Mistral, DeepSeek, Ollama, Bedrock, OpenRouter and Anthropic.

**Where it is read:**
- At setup, by the code above.
- In the admin model editors: the "Input modalities" multiselect of `standard-chat-model-settings`, with "use these" from the metadata, plus a form control in each provider's chat model editor.
- **Nowhere at request time.** No chat service, pipeline step, agent or UI component reads it while a request runs.

**`features` (`ChatModelFeatures`, including `MULTIMEDIA`)** is declared on `GBaseChatModelConfig` and has no reader anywhere. It is superseded by `inputModalities` and can be removed.

**`ModelProviderCapabilities`** is what the chat UI receives as a model's capabilities: transcript, speech, structured output, function calls and tools. It is built in `GChatServiceImpl` and has no modality field.

### 3.2 How a request becomes a prompt

There is one path for the pipelines and one for direct chat calls. Both end in the same method.

1. `ChatPipelineServiceImpl.streamingExecute` hands over to `ChatPipelinesExecutorImpl` (gebo.architecture.chat.abstraction.layer).
2. `ChatPipelinesExecutorImpl` calls `IGChatSessionLifeCycleService.startRequest`, which returns the request's `LLMChatRequestResources`.
   - `GChatSessionLifeCycleServiceImpl.startRequest` ingests the forced documents and the request's uploads into the chat state as **text** documents.
   - `budgetedResources` then sizes them to the model's context.
3. `LLMChatRequestResources.createChatRequestContext()` produces the `IChatRequestContext` the models read:
   - `getInteractions()`: the history, as user and assistant strings;
   - `getDocuments()`: the chat-with, retrieved, uploaded and generated documents, all as Spring AI `Document` text;
   - `getActualUserRequest()`: the question, as a string.
4. The agents route hands that context to the network (`ReactiveChatAgentsNetworkStreamingOutputChatPipelineService`).
   - The agentic loop agent (`AgenticLoopReactiveAgentServiceImpl`, gebo.architecture.agents.standard) calls the model through `ReportWriterReactiveAgentServiceImpl`, which calls `chatModel.streamAnswer(prompt, params, context, toolCalling)`.
   - Agents of a network may wrap the context in `ForwardingChatRequestContext` (gebo.architecture.llms.abstraction.layer).
5. Every chat call (`streamAnswer`, `answer`, `response`, `structuredResponse`, `textResponse`) goes through **`GAbstractConfigurableChatModel.prepareCall`** (:770-849), which builds:
   - the system message (`createSystemMessage`, `new SystemMessage(String)`);
   - the history (`createHistoryMessages`, `new UserMessage(String)` and `new AssistantMessage(String)`);
   - **the last user message** (`createLastUserMessage`, :1072-1097). The documents are rendered as text between `BEGIN_CONTEXT` and `END_CONTEXT` into the user template, and the method returns `new UserMessage(content)`.
6. No provider subclass overrides `prepareCall` or `createLastUserMessage`. `GenericOpenAI` has only a commented-out override.

**Consequence:** media added to `IChatRequestContext`, and read by `createLastUserMessage`, would reach every chat model call. That includes the agentic loop and the chat-with-files step, with no change in the providers.

**Unused helpers on this path** (candidates for removal): `ClientChatCallUtil.getChatHistory` has no callers, and `ClientChatCallUtil.createLastUserMessage` is a stub returning null.

### 3.3 Media today: output only

Spring AI `Media` appears in main code only on the way **out** of a model:

| Where | What it does |
|---|---|
| `GChatAnswerChunk` | collects `generation.getOutput().getMedia()` from a streamed answer |
| `AbstractChatService` (:461-469) | saves each output media of an answer with `chatStorageAreaService.addMedia` and lists it in `response.generatedResources` |
| `GChatStorageAreaServiceImpl.addMedia` (:305-323) | writes the bytes into the session folder and inserts an `LLMGeneratedResource`, shown in the chat as "AI generated files" |
| `DefaultImageGenerationStreamingOutputChatPipelineServiceImpl` | wraps the image model's PNG into a `Media` and saves it through `addMedia` |
| `GDocumentTranslatorImpl.fromChatResponse` | reads output media into `GeboDocument.tryAddMedia`, which is an empty method; `fromChatResponse` itself has no callers |

**On the input side:** there is no `UserMessage.builder().media(...)`, no `new Media(...)` and no `MediaPart` in main code of either repository.

### 3.4 What Spring AI 2.1.0-RC2 offers

Read from the `spring-ai-model` jar:

- **User messages.** `UserMessage` carries a list of `Media` (MIME type plus bytes or a URI). Messages are built from parts: `TextPart`, `MediaPart`, `ReasoningPart`, `ToolCallPart`, `ToolResultPart` and `UnknownPart`.
- **Tool result parts.** `ToolResultPart(id, name, List<MessagePart> content, isError, …)` could carry a `MediaPart`. Nothing produces or sends one, though:
  - `ToolCallback.call(String[, ToolContext])` returns a `String`, and `ToolCallResultConverter.convert` returns a `String`.
  - `DefaultToolCallingManager` builds only `new ToolResponse(id, name, String)` into a `ToolResponseMessage`.
  - `DefaultToolCallResultConverter` turns a returned `java.awt.image.RenderedImage` into a JSON string with `"mimeType":"image/png"` and base64 data. That is still text.
  - Every provider converts tool messages through `ToolResponseMessage.getResponses()` and `responseData()`, which is a string. Where `ToolResultPart` appears in their bytecode, it only raises "Tool results belong in a tool response message".
- **Consequence:** in 2.1 a tool can't hand a picture to the model as its result. A picture has to travel in a user message.

**Gebo's own tool-calling classes** add nothing for media:
- `ToolCallbackDeclarationUtil` declares the tools with a null result converter, so Spring AI's default applies.
- `GeboToolCallingManager` delegates to Spring AI and changes only `returnDirect`.
- `GeboToolCallingAdvisor` extends Spring AI's `ToolCallingAdvisor`. Its `doGetNextInstructionsForToolCall` / `doGetNextInstructionsForToolCallStream` build the messages of the next round, and are the hook section 5.3 uses.

### 3.5 Media in a user message, by provider

What each provider's Spring AI chat model does with `Media` on a `UserMessage`, from its bytecode.

| Gebo module (model type) | Spring AI chat model, created in | Images | PDF / documents | Audio | Video |
|---|---|---|---|---|---|
| `gebo.llms.openai` (`chatgpt-OpenAI`) | `OpenAiChatModel`, `OpenAIChatModelConfigurationSupportService` | yes | PDF as a file part | yes | converted to text |
| `gebo.llms.openai`, models that `OpenAiApiRouting.usesResponsesApi` sends to the Responses API | `OpenAiResponsesChatModel` in `ReasoningSummariesFallbackChatModel` | yes | as input file | **raises an error** ("Audio input is not supported by the OpenAI Responses API") | as input file |
| `gebo.llms.generic-openai-compatible` (Regolo.ai, OpenRouter, Groq, NVIDIA, vLLM, xAI and the others in `providers.yml`) | `OpenAiChatModel`, `GenericOpenAIAPIChatModelConfigurationSupportService` | yes | yes | yes | converted to text |
| `gebo.llms.anthropic3` (`chat-anthropic`) | `AnthropicChatModel`, `AnthropicChatModelConfigurationSupportService` | png, jpeg, gif, webp | PDF | **raises an error** | **raises an error** |
| `gebo.llms.google_vertex` (`chatmodel-google-vertex`) | `GoogleGenAiChatModel`, `GoogleVertexChatModelConfigurationSupportService` | yes | yes | yes | yes (any MIME type, inline or by URI) |
| `gebo.llms.mistral` (`chatmodel-mistral`) | `MistralAiChatModel`, `MistralChatModelConfigurationSupportService` | yes (image URL chunks) | no | no | no |
| `gebo.llms.ollama` (`chat-ollama`) | `OllamaChatModel`, `OllamaChatModelConfigurationSupportService` | yes (base64 images) | no | no | no |
| `gebo.llms.aws-bedrock` (`chat-aws-bedrock`) | `BedrockProxyChatModel`, `BedrockChatModelConfigurationSupportService` | yes | yes | no | yes (s3 and http(s) URLs fetched through `MediaFetcher`, which guards against SSRF) |
| `gebo.llms.deepseek` (`chat-deepseek`) | `DeepSeekChatModel`, `DeepseekChatModelConfigurationSupportService` | **dropped silently** | dropped | dropped | dropped |
| pro `gebo.provided.llms` (`geboai-provided-chat`) | none: `create()` raises "not implemented yet" | n/a | n/a | n/a | n/a |

Notes:
- **OpenAI-compatible backends** get what OpenAI would get; whether they read it depends on the backend and the model.
- **"Raises an error":** Spring AI throws, so the whole call fails. Gebo must filter these types out before the call.
- **"Dropped silently":** the request goes out without the media and without an error. Gebo must not count on the model having seen anything.
- **Two OpenAI routes:** a model's route depends on `OpenAiApiRouting`, so one provider can accept audio for one model and reject it for another.

---

## 4. Gaps by area

Each area has the same layout: **today**, with evidence; **gap**; **proposed design**; **effort and risks**; **how to test**.

### 4.1 Chat uploads

**Today:**
- The upload box (`upload-chat-document.component`, gebo-ai-reusable-ui) posts to `GeboUserChatUploadsController.chatSessionUpload`. That reaches `GChatStorageAreaServiceImpl.addUploadedFile` (:104-171), which saves the file and runs the ingestion handler:
  - **A file a content handler reads:** its text chunks are written to `<code>-ingest.json` and reach the prompt as text documents (section 3.2). Pictures inside it are dropped (section 4.6).
  - **A file no handler reads, such as a picture** (`isUnmanagedContent`, :128): it is refused and deleted, unless its extension is one the chat's agents' libraries work on (`toolsFileTypesOfSession`, :131). If kept, it is stored with `tokensCount` 0 and no ingested text, "for the agents' tools only".
- At request time, `startRequest` asks for each upload's ingested contents. A tools-only upload has none, so **the model gets nothing of it**.
- The tools reach the file through `ChatUploadsFilesProvider`, the read-only `uploads` location.
- The box's accepted types come from `GChatUploadFileTypesServiceImpl.acceptedFileTypes` (:113): the content handlers' extensions plus the extensions of the libraries of the chat's agents. It takes no model input.
- The box tells the user that tools-only files "are not read into the chat, and the models do not see pictures".
- **Routing:** a turn carrying any upload counts as carrying documents (`DefaultRoutingChatPipelineStepServiceImpl`). It goes to the chat-with-files step unless the user chose the agents. That step's prompt is built by the same `prepareCall`.
- **Scanned PDFs** are read by the PDF handler, whose text comes out empty (section 4.6). They reach the model as an empty document.

**Gap:** when the chat's model declares IMAGE, an uploaded picture could go to it as media. With DOCUMENT, a PDF could too (including a scanned one), and with AUDIO an audio file. Nothing today connects the upload to the model.

**Proposed design:**
1. **Carry the media on the request context.**
   - Add `default List<Media> getMedia()` (empty) to `IChatRequestContext`. Implement it in `LLMChatRequestResources`'s nested context from the request's uploads, and forward it in `ForwardingChatRequestContext`.
   - `MinimalChatContext` (the router's and service steps' context) stays without media.
2. **Attach it where the prompt is built.** In `createLastUserMessage`, build the message as `UserMessage.builder().text(content).media(chatContext.getMedia())` when the list isn't empty.
3. **Send only what the model and its provider read.** Add a `media guard` used by step 1.
   - It keeps a `Media` only when the model's `inputModalities` declare its modality **and** the provider's chat model converts that MIME type (section 3.5).
   - The provider side belongs with each provider's `IGChatModelConfigurationSupportService`, for example a default `acceptedInputMediaTypes()` that answers empty, so a provider that never declares any (DeepSeek) never gets media.
   - The OpenAI handler answers per model, because of the Responses API routing.
   - Files that don't pass stay where they are today: kept for the tools.
4. **Budget it.**
   - Media isn't counted anywhere: `budgetedResources` sizes text, and the media branch of `GContentDispatchingEvaluatorImpl` (:179-181) is empty.
   - Add a per-request cap: number of files, bytes per file, total bytes. Add an estimate of their tokens to the context budget. A fixed per-image estimate per provider is enough to start; providers report the real figure in their usage.
5. **Decide the later turns** (see section 8). The history (`CSSSimplefiedInteraction`) has no media field. The simplest rule is to send a picture only on the turn it was uploaded, and to keep its file name in the history text, as for tools-only files today. The alternative is to re-send it on later turns.
6. **The upload box follows the model.**
   - `acceptedFileTypes` also takes the chat's model, or the profile's, and adds the types the media guard lets through to the chat extensions.
   - The "models do not see pictures" hint is shown only when that is true for the chat's model.
7. **Data protection register.** Pictures, PDFs and audio would leave for the provider as content, not as extracted text. Add the flow to the compliance and data-flow descriptions, as `GMediaModelsDataFlowComponent` does for transcription and speech.

**Effort and risks:**
- **Effort:** small in the backend (one context method, one prompt-building change, one guard, a budget). Small in the UI (accepted types and hint).
- **Risk:** a provider raising an error for a type the guard let through. Keep the guard conservative and test it per provider.
- **Risk:** cost. A picture costs tokens on every turn it is sent, which is why the later-turn rule matters.

**How to test:**
- In the fake-model integration tests, the `TestChatModel` prompt observer receives the whole `Prompt`. A test can assert that the last `UserMessage` carries the uploaded picture's `Media` when the model declares IMAGE, and carries none when it doesn't. The office libraries integration test (pro `integration-tests/office-libraries-integration-tests`) already uploads a picture to a free chat.
- Unit-test the guard against the provider table.

### 4.2 Chat history and generated pictures

**Today:**
- The history shown in the UI keeps each answer whole, including its `generatedResources` and `writtenFiles`.
- The history the model reads (`CSSSimplefiedInteraction`) keeps text only: user and assistant text, documents, listed document names and written files.
- Generated pictures are offered to ingestion at the end of the request (`GChatSessionLifeCycleServiceImpl.endRequest`). For a picture that gives nothing, so it is never read again by a model.
- `addLLMGenerated` has no callers.

**Gap:** a follow-up question about a picture the chat generated ("make the sky darker", "what does the chart show") reaches a model that never saw it.

**Proposed design:** once section 4.1 exists, the guard can attach the previous turn's generated pictures to the next request, under the same later-turn rule. Editing a picture also needs an image-edit call to the image models (section 4.8).

**Effort:** small once section 4.1 exists.

### 4.3 The agents' tools and the files they handle

**Today:**
- **Pro text documents and presentations libraries:**
  - They copy pictures out of documents and slides into a files location (`extractImages`, `extractSlideImages`) and put pictures into documents and slides.
  - The model only gets the files' names and sizes back (`ImagesExtracted(location, files)`).
  - `GImage` keeps its bytes out of JSON, so a document read gives the model a picture's name, type, size and alt text.
  - The text documents library's agent rules say: "A picture the user uploaded is in uploads … you know it by its name and size, you do not see it."
  - Slides are never rendered to images.
- **Working directory library** (`WorkingDirectoryLibrarySource`): reads and writes text files only (`Files.readString` / `writeString`).
- **Pro sandbox library:** `readFile` reads a text file.
- **Files written in the conversation** reach the agents as a text outline (`ConversationFilesRenderer`, `GConversationFilesText`).
- **No tool generates pictures or charts.** Image generation is a pipeline step (section 4.8).

**Gap:** an agent can't look at a picture it was given or made: a chart in an upload, the layout of a slide it built, a picture an MCP tool returned.

**Proposed design:**
- **A "view picture" tool**, in the working directory library or a small library of its own. It takes a files location and a path, and checks that the file is a picture the model's guard accepts.
- **The picture travels in a user message.** Spring AI 2.1 can't put it in a tool result (section 3.4). So the tool returns a short text ("shown to you below"), and `GeboToolCallingAdvisor.doGetNextInstructionsForToolCall` appends a `UserMessage` carrying the picture to the next round's messages.
- **This is a hypothesis to try first.** Each provider has its own rules on the order of tool messages. Anthropic wants the tool results right after the tool use, and Spring AI turns a `ToolResponseMessage` into a user message of tool-result blocks. A first PR should try it on OpenAI, Anthropic and Google before it is offered generally.
- **Slide previews:** rendering slides to PNG (POI can draw XSLF slides onto a `Graphics2D`) would let an agent check a presentation's layout through the same tool.

**Effort and risks:**
- **Effort:** medium.
- **Risk:** the provider message-order rules above.
- **Risk:** the size of pictures: downscale before sending.

**How to test:**
- A fake-model test calls the tool and checks that the next prompt carries the picture.
- One live run per provider.

### 4.4 MCP

**Today:**
- **Client tools.** `McpRemoteToolCallback.renderResult` (:92-110) keeps `TextContent` and replaces any other content with a placeholder such as `[ImageContent content]`. That includes images, audio, embedded resources and resource links. `structuredContent` is never read. This is Gebo's only MCP tool callback; Spring AI's `SyncMcpToolCallback` is not used.
- **Client resources ingested into knowledge bases.** `GMCPClientRemoteVirtualFilesystemConsumingServiceImpl` decodes blob resources and passes them to ingestion, which reads text formats only (section 4.6).
- **Server tools.** `GeboMcpToolsProvider` and the agent and network tool providers register tools through `McpToolUtils.toSyncToolSpecification`, which answers `TextContent` unless a MIME type is given (Gebo gives none).
- **Server resources.** `GeboMcpResourcesProvider` returns text for textual types and base64 blobs for the rest, up to `maxDocumentBytes`.
- **Server prompts.** `GeboMcpPromptsProvider` returns text only.
- **Not supported:** MCP sampling and elicitation.

**Gap:** a picture or a file returned by an MCP tool is lost: the agent reads a placeholder.

**Proposed design:**
- Keep non-text tool content as files of the conversation: a picture as an image file, an embedded resource as its file, in the working directory or a location of its own.
- Return a text that names them, so the "Files written" row and the panel show them and the tools can open them.
- With section 4.3, the agent can then look at a picture.
- Read `structuredContent` as JSON text when present.

**Effort:** small for keeping the files; the viewing comes with section 4.3.

### 4.5 A2A

**Today:**
- **Server, inbound** (`A2AInboundResources`):
  - Text parts become the question.
  - A file part given as bytes (`FileWithBytes`, :126) becomes a chat upload, so the agents get its extracted text, and a picture gets nothing (section 4.1).
  - A file part given by URI (`FileWithUri`) isn't fetched.
  - Data parts are read only for the `knowledgeBases` and `documents` keys.
- **Server, outbound** (`A2AResultAdapter`):
  - The answer goes out as a text part, with data parts for citations (`gebo.citations`) and run information (`gebo.runInfo`).
  - Generated pictures and written files are never sent, as file parts or as artifacts.
  - The agent card advertises `text/plain` only (`A2AAgentCardBuilder`, :44).
  - `A2AServerBuilder.renderParts` has no callers.
- **Client** (`A2AClientConnector`, `RemoteA2ANetworkAgentService`):
  - It sends text parts only.
  - Of the answer, it keeps only the text parts of the message, artifacts, status and history (`renderParts`, :185-191). File and data parts are dropped without notice.
  - The remote skills' input and output modes are stored but never read.

**Gap:** files exchanged with other agents are lost both ways.

**Proposed design:**
- **Inbound pictures** follow section 4.1 once it exists: they become uploads and reach the model through the guard.
- **Outbound:** return generated pictures and written files as file parts (or artifacts), and advertise their MIME types on the card.
- **Client:** keep received file parts as conversation files (as in section 4.4). Read the remote skills' modes, so Gebo knows what a remote agent accepts before sending it a file.

**Effort:** small to medium. The A2A SDK has the part types; the work is mapping them.

### 4.6 Knowledge base ingestion

**Today:**
- **Readers.** The only file-type registry is `gebo.system.ingestion/src/main/resources/bundle-readers/readers.yml`:

  | Reader (`readers.yml` id) | Types |
  |---|---|
  | `HtmlIngestionHandler` | .htm, .html |
  | `MSDocumentsIngestionHandler` (class `MSWordIngestionHandler`) | .doc, .docx |
  | `MsExcellIngestionHandler` | .xls, .xlsx |
  | `OdtIngestionHandler`, `OdpIngestionHandler`, `OdsIngestionHandler` (classes `GOdt…`/`GOdp…`/`GOdsIngestionHandlerImpl`) | .odt, .odp, .ods |
  | `PdfIngestionHandler` | .pdf |
  | `XmlIngestionHandler`, `JsonIntestionHandler` | .xml, .json |
  | `TextPlainIngestionHandler` | .txt, .md and source code types |

  The pro repository adds no reader.
- **What no reader covers:**
  - images, audio and video;
  - **.pptx, .ppt and .rtf**, even though the pro office libraries read and write all three.
- **Unread files are dropped.** A file no reader takes is marked unmanaged (`GDocumentReferenceIngestionHandlerImpl`) and discarded at discovery.
- **Pictures inside documents are dropped:**
  - **PDF:** text is extracted page by page, and the image callback is empty (`ColumnAwarePdfTextExtractor.renderImage`, :130). A scanned PDF gives no text at all.
  - **Word, Excel:** only the extractors' text and the cell tables are read (`MSWordIngestionHandler` :142); drawings and pictures are ignored.
  - **ODT and ODP:** text elements and text boxes only.
  - **HTML:** `body.text()`, so `<img>` and its alt text are dropped.
- **There is no OCR and no captioning.** The only OCR-related code filters OCR models out of the chat model lists (Mistral, Regolo.ai). Tika's OCR parser is on the classpath only as a transitive dependency.
- **The Tika fallback handler is unused.** `GTikaFailBackContentHandler` would map every Tika type and keep media documents, but its `@Service` is commented out (:54).
- **`GeboDocument` has unused media fields.** It has lists for images, audio, video and media, but ingestion streams only its texts. In any case no content handler ingests through `GeboDocument` (`isIngestedAsGeboDocument` is false everywhere).
- **The only model at ingestion is graph RAG extraction, and it reads text.** `GraphDataExtractionServiceImpl` sends each chunk's text, in the JSON or CSV format, to the configured extraction model. No other ingestion step calls a model: cataloguing (`GAIDocumentCatalogingEnricherImpl`) builds a text header without one.
- **Embeddings are text only** for every provider: OpenAI, OpenAI-compatible, Ollama, Mistral, Vertex text embeddings, Bedrock Titan/Cohere and ONNX transformers. `GBaseEmbeddingModelConfig` has no modality field.

**Gap:** a knowledge base learns nothing from charts, diagrams, photos, scans, slides, recordings or videos. A scanned PDF or a slide deck adds nothing to it.

**Proposed design, in steps:**
1. **Text readers for .pptx/.ppt and .rtf.** No model is needed. The pro office libraries' document models already parse them, and a community reader can use POI (`XMLSlideShow`, `HSLFSlideShow`) and the RTF reader. This fixes a text gap that predates multimodality.
2. **Descriptions of pictures and scanned pages by a vision model, opt-in per knowledge base.**
   - A step after text extraction sends each picture, and each PDF page without text, to a configured model that declares IMAGE (or DOCUMENT for whole PDF pages).
   - The model's description or transcription becomes text of the document, marked as such in the chunk metadata.
   - Chunking, embedding and graph RAG extraction then work unchanged.
   - Settings: on/off, model, maximum pictures per document, minimum picture size (to skip icons), and a cost estimate shown before a run.
3. **Audio and video files** transcribed with the transcription models Gebo already has (`IGConfigurableTranscriptModel`), with the transcript as the document's text.
4. **Multimodal embeddings** (one vector space for pictures and text) would be a separate project: no embedding model Gebo configures takes images.

**Effort and risks:**
- **Effort:** step 1 is small. Step 2 is medium: an ingestion step, settings and a UI page. Step 3 is small to medium.
- **Risk in step 2:** cost and time on large corpora, hence opt-in, limits and the estimate. The description model must be one the installation's data policy allows for document content.

**How to test:**
- Step 1: reader unit tests on sample decks and RTF files.
- Step 2: a fake-model ingestion test, where the test model answers a fixed description for an image prompt and the test checks that the description is embedded and found by search.

### 4.7 Voice input, speech, audio and video

**Today:**
- **Voice input:** the chat UI records audio and posts it to `GeboTranscriptController.transcriptText`. `GTranscriptServiceImpl` turns it into text with the default transcription model. The text fills the question box and goes out as an ordinary message; the audio isn't stored and never reaches the chat model.
- **Speech:** `GTextToSpeechServiceImpl` turns an answer's text into audio for the browser, and the audio isn't stored.
- **Neither is used in ingestion.**

**Gap:** only where the sound itself matters (tone, several speakers, music, noise). Transcription already serves the common case with every model.

**Proposed design:** none for now. Sending audio directly becomes possible through section 4.1 for models that declare AUDIO (OpenAI chat completions, Google). Section 4.6 step 3 covers audio files in knowledge bases.

### 4.8 Image generation

**Today:**
- `DefaultImageGenerationStreamingOutputChatPipelineServiceImpl` is a pipeline step, not a tool. A service model writes the image prompt, the image model is called with text only (`new ImagePrompt(text)`), and the result is saved through `addMedia`.
- `IGConfigurableImageModel` offers only `call(ImagePrompt)`.

**Gap:** no "change this picture" from an upload, and follow-up questions about a generated picture (section 4.2).

**Proposed design:**
- **Follow-ups:** come with sections 4.1 and 4.2.
- **Editing:** an image-edit call on the image models that offer one (for example OpenAI's image edit endpoint), exposed as a pipeline option or a tool.

### 4.9 Office add-ins (pro)

**Today:**
- **What the add-ins send.** The Microsoft Office add-in reads the selection with `CoercionType.Text` and sends it as `text/plain`. ONLYOFFICE does the same, and LibreOffice sends `text/plain` by default.
- **The data model is text:** `AdditionalContent { contentType, name, content: String }`.
- **The pipeline handles text only** (`gebo.architecture.office-plugin-pipeline`):
  - `OfficeFragments.normalize` caps each fragment at 200,000 characters and defaults its type to `text/markdown`.
  - The fragments enter the prompts as text (`OfficeChatRuntimeDataQueryAdapterAgentService`, `GOfficeFragmentsPromptContextProvider`).
  - The answers come back as markdown documents.

**Gap:** pictures in the user's selection, and the look of the document, are never seen.

**Proposed design:** after section 4.1, let the add-ins send selected pictures as additional contents with an image type and base64 content. Map those to `Media` through the guard. Low priority.

### 4.10 Web search, deep search and the web crawler

**Today:**
- **Search results.** `SearchResult` and `SearchResultReference` have no image fields, and the search providers keep none.
- **Fetched pages are reduced to text:**
  - the search tools and deep search go through `SearchResultsChunker`, which takes text documents only;
  - the crawl function (`CrawlFunctionCallbackWrapperSource`) keeps `body().text()`.
- **The pro web crawler** (`GWebCrawlingService`) follows only `<a href>` links and never downloads `<img>` sources. The pages it saves are ingested as HTML text.

**Gap:** pictures in web pages.

**Proposed design:** none on its own. A crawled page could keep its pictures' addresses (or download them under the crawler's limits), and section 4.6 step 2 would describe them. Low priority.

### 4.11 Usage and cost accounting

**Today:**
- Model usage is recorded from what each provider reports in its responses (the LLM usage tracking).
- Gebo's own context budgets and token estimates count text only.

**Gap:** the budgets ignore media. The usage reports should include it, but that hasn't been checked.

**Proposed design:**
- Section 4.1's per-image estimate feeds the context budget.
- Once media is sent, check on each provider that the reported prompt tokens include the pictures. Providers such as OpenAI and Anthropic count image input in their prompt or input tokens, but this hasn't been verified against Gebo's recorded usage.

---

## 5. Cross-cutting design

### 5.1 One media guard

**One component decides which media goes to a model call.** Every area above uses it: uploads, generated pictures, viewed pictures, inbound A2A files and Office pictures.

**Its input:**
- the candidate media (MIME type, size);
- the model's `inputModalities`;
- the provider's accepted media types for that model (section 3.5).

**Its output:**
- the media to attach;
- for the rest, a reason that can be shown or logged ("chart.png kept for the tools: gpt-x does not read pictures").

It never lets through a type the provider would reject (Anthropic, the Responses API) or silently drop (DeepSeek).

**Mapping MIME types to modalities:**

| MIME type | Modality |
|---|---|
| `image/*` | IMAGE |
| `audio/*` | AUDIO |
| `video/*` | VIDEO |
| `application/pdf` and office types | DOCUMENT |

### 5.2 Where the media travels

- `IChatRequestContext.getMedia()` (a default that answers empty) carries the turn's media. `ForwardingChatRequestContext` forwards it, and `createLastUserMessage` attaches it.
- No provider code changes: Spring AI converts the media (section 3.5).
- The service models (router, title, query rewriter) use `MinimalChatContext` and keep text only, so the extra cost stays on the answering model.

### 5.3 Pictures for agents

A picture an agent asks to see is appended as a user message by `GeboToolCallingAdvisor`, after the tool's text result (section 4.3). The same guard applies. This is the part to try on each provider first.

### 5.4 Limits

- **Per request:** a maximum number of media and of bytes per file and in total.
- **Large pictures** are downscaled before sending.
- **A per-picture token estimate** for the context budget.
- Media is sent as bytes, never as URLs Gebo would make the provider fetch.

### 5.5 Data protection and the compliance register

- Media sent to a provider is document content leaving the installation.
- Describe the new flows in the data-flow and compliance views, alongside `GMediaModelsDataFlowComponent` and the Office plugin's data-flow component. Mark the model configurations that receive media.

### 5.6 The UI

- **Upload box:** the accepted types and the "models do not see pictures" hint follow the chat's model (section 4.1).
- **Model editor:** already shows the input modalities. It could state what they enable ("pictures uploaded to chats are sent to this model").
- **Chat answers:** could show, per request, which files were sent to the model and which were kept for the tools.

---

## 6. Defects found along the way

These are separate from multimodality; each is a small fix of its own.

1. **Upload metadata lost.** In `GChatStorageAreaServiceImpl.addUploadedFile` (:155), `newMeta.putAll(newMeta)` merges the map into itself. The ingested chunks of a chat upload keep the common metadata but lose their handler's own (titles, pages…). It should be `newMeta.putAll(doc.getMetaData())`.
2. **Generated files typed loosely.** `GChatStorageAreaServiceImpl.addMedia` (:312) stores `media.getMimeType().getType()`, for example `image` rather than `image/png`, and sets no file size or token count.
3. **Upload before the chat exists.** In that case `upload-chat-document.component.ts` posts to `GeboUserChatUploadsController/chatSessionCreateWithUpload/` or `…/ragChatSessionCreateWithUpload/` (:15-16, :113-117). The controller has neither endpoint: it has `acceptedFileTypes`, `chatSessionUpload`, `deleteSessionUploads` and `serveContent`. Not yet checked whether the UI reaches that path.
4. **MCP results flattened** (section 4.4), and **A2A file parts dropped without notice** (section 4.5). Both lose content today, before any multimodal work.
5. **No knowledge base reader for .pptx, .ppt or .rtf** (section 4.6). This is a text gap: such files given to a data source are discarded.
6. **Unused code:**
   - `ChatModelFeatures` and `GBaseChatModelConfig.features`;
   - `GTikaFailBackContentHandler` (its `@Service` is commented out);
   - `GeboDocument.tryAddMedia` (an empty method);
   - `GDocumentTranslatorImpl.fromChatResponse`;
   - `A2AServerBuilder.renderParts`;
   - `ClientChatCallUtil.getChatHistory` and `ClientChatCallUtil.createLastUserMessage`;
   - `GChatSessionLifeCycleServiceImpl.addLLMGenerated`.

---

## 7. Proposed roadmap

Each step is sized to be one pull request (or a short series), with its own tests.

| Step | What | Depends on | Tests |
|---|---|---|---|
| 0 | Fix defects 1 to 3; remove the unused code of defect 6 | none | unit tests on upload metadata and generated file types |
| 1 | Media guard and provider accepted types (sections 5.1, 3.5) | none | unit tests against the provider table |
| 2 | Chat uploads to the answering model: `IChatRequestContext.getMedia`, `createLastUserMessage`, budget, later-turn rule | 1 | fake-model integration test: the last `UserMessage` carries the picture only when the model declares IMAGE |
| 3 | Upload box and hints follow the model; compliance flows described | 2 | UI check; data-flow view |
| 4 | Knowledge base readers for .pptx/.ppt and .rtf | none | reader unit tests |
| 5 | "View picture" for agents through the tool-calling advisor, tried on OpenAI, Anthropic and Google | 1 | fake-model test of the next prompt; one live run per provider |
| 6 | MCP non-text results and A2A file parts kept as conversation files; A2A outbound file parts and card modes | none (viewing with 5) | MCP and A2A integration tests with a picture-returning stub |
| 7 | Generated pictures in follow-up turns; image editing | 2 | fake-model test |
| 8 | Opt-in picture and scan descriptions at ingestion | 1 | fake-model ingestion test |
| 9 | Audio and video files transcribed into knowledge bases | none | ingestion test with a fake transcription model |
| 10 | Office add-in pictures; web pictures; multimodal embeddings | 2, 8 | to plan when reached |

---

## 8. Decisions needed

1. **Where to start.** The recommendation is steps 1 to 3 (chat uploads). The change sits at one prompt-building point plus the upload box, and its value shows in the first chat.
2. **Pictures on later turns.** Either send a picture only on the turn it was uploaded and keep its name in the history (cheapest), or re-send it on every later turn (costs tokens each time), or keep a short description the model writes on the first turn.
3. **What a chat does with a file its model can't read.** The proposal keeps today's behaviour: the file is kept for the tools, and the user is told why. Another option is to describe it with another configured model that reads it.
4. **Ingestion descriptions.** Whether to build step 8. If so: opt-in per knowledge base, which model is allowed to see document content, and the limits.
5. **Viewing pictures in agents.** Whether to accept the user-message approach of section 4.3, given the per-provider trial it needs.

---

## 9. Glossary

- **Modality:** a kind of input a model reads (text, image, audio, video, document), as declared in `GModelInputModality`.
- **Media:** Spring AI's `org.springframework.ai.content.Media`: a MIME type plus bytes or a URI, attached to a message.
- **Tools-only upload:** a chat upload no content handler reads, kept because the chat's agents' libraries work on its type (OSS #392). It is stored with `tokensCount` 0.
- **Media guard:** the proposed component of section 5.1.

---

## 10. File index

Community repository paths are relative to its root. Pro repository paths start with `Gebo.ai.pro/`.

**Models and prompts**
- `GBaseChatModelConfig`, `ChatModelFeatures`, `GModelInputModality`, `ForwardingChatRequestContext`, `IChatRequestContext`: `gebo.architecture.parent/gebo.architecture.llms.abstraction.layer/src/main/java/ai/gebo/llms/abstraction/layer/model/`
- `GAbstractConfigurableChatModel`, `IGChatModelConfigurationSupportService`, `ClientChatCallUtil`: `gebo.architecture.parent/gebo.architecture.llms.abstraction.layer/src/main/java/ai/gebo/llms/abstraction/layer/services/`
- `GeboToolCallingAdvisor`, `GeboToolCallingManager`: `gebo.architecture.parent/gebo.architecture.llms.abstraction.layer/src/main/java/ai/gebo/llms/abstraction/layer/services/toolcalling/`
- `ModelMetaInfo`: `gebo.architecture.parent/gebo.architecture.llms.abstraction.layer/src/main/java/ai/gebo/llms/models/metainfos/ModelMetaInfo.java`
- `IGConfigurableImageModel`, `IGConfigurableTranscriptModel`, `IGConfigurableTextToSpeechModel`: `gebo.architecture.parent/gebo.architecture.llms.abstraction.layer/src/main/java/ai/gebo/llms/abstraction/layer/services/`
- `GeboLLMSSetupService`: `gebo.llms.parent/gebo.llms.setup/src/main/java/ai/gebo/llms/setup/services/GeboLLMSSetupService.java`

**Chat**
- `ChatPipelinesExecutorImpl`, `ChatPipelineServiceImpl`: `gebo.architecture.parent/gebo.architecture.chat.abstraction.layer/src/main/java/ai/gebo/llms/chat/pipelines/service/impl/`
- Under `gebo.architecture.parent/gebo.architecture.chat.abstraction.layer/src/main/java/ai/gebo/llms/chat/abstraction/layer/services/impl/`:
  - `GChatSessionLifeCycleServiceImpl`, `GChatServiceImpl`, `AbstractChatService`;
  - `GChatStorageAreaServiceImpl`, `GChatUploadFileTypesServiceImpl`, `ChatUploadsFilesProvider`;
  - `GTranscriptServiceImpl`, `GTextToSpeechServiceImpl`, `GMediaModelsDataFlowComponent`.
- `LLMChatRequestResources`, `AdditionalContent`, `GeboTemplatedChatResponse`: `gebo.architecture.parent/gebo.architecture.chat.abstraction.layer/src/main/java/ai/gebo/llms/chat/abstraction/layer/llmexchange/model/`
- `MinimalChatContext`, `CSSSimplefiedInteraction`: `gebo.architecture.parent/gebo.architecture.chat.abstraction.layer/src/main/java/ai/gebo/llms/chat/abstraction/layer/session/model/`
- `DefaultRoutingChatPipelineStepServiceImpl`, `DefaultImageGenerationStreamingOutputChatPipelineServiceImpl`, `DefaultChatWithFilesStreamingOutputPipelineServiceImpl`: `gebo.architecture.parent/gebo.architecture.chat.abstraction.layer/src/main/java/ai/gebo/llms/chat/pipelines/service/defaultsteps/impl/`
- `SearchResultsChunker`: `gebo.architecture.parent/gebo.architecture.chat.abstraction.layer/src/main/java/ai/gebo/llms/deepsearch/service/SearchResultsChunker.java`
- `GeboUserChatUploadsController`, `GeboTranscriptController`, `GeboTextToSpeechController`, `GeboLLMGeneratedResourceController`: `gebo.ragsystem.parent/gebo.ragsystem.client.rest/src/main/java/ai/gebo/llms/chat/client/rest/controllers/`
- Upload box: `gebo.ui/projects/gebo-ai-reusable-ui/src/lib/controls/choose-documents-panel/upload-chat-document.component.ts` and `.html`
- Model editor: `gebo.ui/projects/gebo-ai-admin-ui/src/lib/admin-ui/entity-editors/controls/standard-chat-model-settings/`

**Agents, tools, MCP, A2A**
- Under `gebo.architecture.parent/gebo.architecture.agents.standard/src/main/java/ai/gebo/llms/agent/`:
  - `chat/service/impl/`: `AgenticLoopReactiveAgentServiceImpl`, `ReportWriterReactiveAgentServiceImpl`, `ReactiveChatAgentsNetworkStreamingOutputChatPipelineService`;
  - `standard/services/`: `GChatEnvironmentNetworkRunner`;
  - `standardlibraries/`: `WorkingDirectoryLibrarySource`.
- `ConversationFilesRenderer`: `gebo.architecture.parent/gebo.architecture.agents.abstraction.layer/src/main/java/ai/gebo/architecture/agents/services/ConversationFilesRenderer.java`
- `ToolCallbackDeclarationUtil`: `gebo.architecture.parent/gebo.architecture.ai/src/main/java/ai/gebo/architecture/ai/service/ToolCallbackDeclarationUtil.java`
- `McpRemoteToolCallback`: `gebo.architecture.parent/gebo.architecture.mcp-clients/src/main/java/ai/gebo/architecture/mcpclients/service/impl/McpRemoteToolCallback.java`
- `GMCPClientRemoteVirtualFilesystemConsumingServiceImpl`: `gebo.systems.parent/gebo.mcp-client.content.handler/src/main/java/ai/gebo/mcpclient/content/handler/impl/`
- `GeboMcpToolsProvider`, `GeboMcpResourcesProvider`, `GeboMcpPromptsProvider`: `gebo.architecture.parent/gebo.architecture.mcp-server/src/main/java/ai/gebo/architecture/mcpserver/runtime/`
- `A2AInboundResources`, `A2AResultAdapter`, `A2AAgentCardBuilder`, `A2AServerBuilder`: `gebo.architecture.parent/gebo.architecture.a2a-server/src/main/java/ai/gebo/architecture/a2aserver/runtime/`
- `A2AClientConnector`, `RemoteA2ANetworkAgentService`: `gebo.architecture.parent/gebo.architecture.a2a-clients/src/main/java/ai/gebo/architecture/a2aclients/service/impl/`

**Ingestion**
- Readers registry: `gebo.architecture.parent/gebo.system.ingestion/src/main/resources/bundle-readers/readers.yml`
- Under `gebo.architecture.parent/gebo.system.ingestion/src/main/java/ai/gebo/system/ingestion/`:
  - `impl/`: `GDocumentReferenceIngestionHandlerImpl`, `GTikaFailBackContentHandler`, `GAIDocumentCatalogingEnricherImpl`;
  - `pdf/impl/`: `PdfIngestionHandler`, `ColumnAwarePdfTextExtractor`;
  - `msformats/impl/`: `MSWordIngestionHandler`, `MsExcellIngestionHandler`;
  - `odformats/impl/`: `GOdtIngestionHandlerImpl`, `GOdpIngestionHandlerImpl`;
  - `text/impl/`: `HtmlIngestionHandler`.
- `GContentDispatchingEvaluatorImpl`: `gebo.architecture.parent/gebo.architecture.contentsystems.abstraction.layer/src/main/java/ai/gebo/systems/abstraction/layer/impl/`
- `GraphDataExtractionServiceImpl`: `gebo.architecture.parent/gebo.architecture.graphrag.extraction/src/main/java/ai/gebo/architecture/graphrag/extraction/services/impl/`
- `GeboDocument`: `gebo.core.parent/gebo.base.model/src/main/java/ai/gebo/document/model/GeboDocument.java`
- `GDocumentTranslatorImpl`: `gebo.architecture.parent/gebo.architecture.llms.abstraction.layer/src/main/java/ai/gebo/llms/abstraction/layer/services/impl/`
- `CrawlFunctionCallbackWrapperSource`: `gebo.llms.parent/gebo.llms.standard.functions/src/main/java/ai/gebo/llms/standard/functions/`

**Providers**
- `gebo.llms.parent/gebo.llms.openai/` (`OpenAIChatModelConfigurationSupportService`, `OpenAiApiRouting`)
- `gebo.llms.parent/gebo.llms.generic-openai-compatible/` (`GenericOpenAIAPIChatModelConfigurationSupportService`, `src/main/resources/openai-compatible-providers/providers.yml`)
- `gebo.llms.parent/gebo.llms.anthropic3/` (`AnthropicChatModelConfigurationSupportService`)
- `gebo.llms.parent/gebo.llms.google_vertex/` (`GoogleVertexChatModelConfigurationSupportService`)
- `gebo.llms.parent/gebo.llms.mistral/` (`MistralChatModelConfigurationSupportService`)
- `gebo.llms.parent/gebo.llms.ollama/` (`OllamaChatModelConfigurationSupportService`)
- `gebo.llms.parent/gebo.llms.aws-bedrock/` (`BedrockChatModelConfigurationSupportService`)
- `gebo.llms.parent/gebo.llms.deepseek/` (`DeepseekChatModelConfigurationSupportService`)
- Spring AI jars: `~/.m2/repository/org/springframework/ai/spring-ai-<provider>/2.1.0-RC2/`

**Pro**
- Office libraries, under `Gebo.ai.pro/ai.gebo.pro.architecture.parent/gebo.architecture.libraries.document-models-tools/src/main/java/ai/gebo/libraries/documentmodels/`:
  - `text/library/TextDocumentsLibrarySource.java`;
  - `presentation/library/PresentationsLibrarySource.java`;
  - `spreadsheet/library/SpreadsheetsLibrarySource.java`;
  - `text/GImage.java`.
- Office pipeline: `Gebo.ai.pro/ai.gebo.pro.architecture.parent/gebo.architecture.office-plugin-pipeline/src/main/java/ai/gebo/officeplugin/pipeline/agents/` (`OfficeFragments`, `OfficeChatRuntimeDataQueryAdapterAgentService`, `GOfficeFragmentsPromptContextProvider`)
- Web crawler: `Gebo.ai.pro/ai.gebo.pro.systems.parent/gebo.webcrawler.handler/src/main/java/ai/gebo/webcrawler/handler/impl/GWebCrawlingService.java`
- Office libraries integration test: `Gebo.ai.pro/integration-tests/office-libraries-integration-tests/`
