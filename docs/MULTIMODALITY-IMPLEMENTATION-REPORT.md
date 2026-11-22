# Multimodality — what branch `feature/moltimodality` changed

Report written on 2026-11-20 from the net difference between the branch and `develop` (`git diff origin/develop...HEAD`), OSS head `bfb7eef85`, Pro head `a931e69`. It describes what is on the branch now; code that was added and later removed on the branch is not described. The analysis the work follows is `docs/MULTIMODAL-MODELS-GAPS.md`, whose section 13 keeps the status step by step.

Size of the change in the OSS repository: 964 files, of which about 140 are hand-written (Java, tests, YAML, Angular) and the rest is regenerated client code (19 Angular stub files, 807 files of the Java REST client, almost all a changed generation date). The Pro repository has 3 changed files.

The report has two levels. Sections 1 to 4 are the functional view of the whole branch. Parts A, B and C then go module by module: first what the module does now, then every changed class.

---

## 1. What the product does now

### 1.1 A chat model receives files, not only text

Before the branch every model received text only: a picture uploaded in a chat was either turned into text by a reader or kept for the tools. Now a file reaches a model as it is when two things agree:

A third condition was added on 2026-11-22: the installation must send that kind of file to its models. Each kind (images, recordings, videos, other documents) has its own switch, **off by default**, and its own low limits (section 2). With every switch off no file reaches a model, a picture upload is accepted or refused exactly as before the branch, and no context room is reserved: the standard path is unchanged. The reason is that no provider is asked what a file costs in the context, so what an installation sends is what it chose to.

- the configuration of the chat model declares the kind of the file among its input modalities (text, image, audio, video, document);
- the integration of the provider can deliver that MIME type (each provider module states it, see the table in Part A).

The decision is taken in one class, `GModelMediaGuard`, and always has a reason. A configuration that declares nothing stays text only; an integration that states nothing carries nothing.

The administrator sees this in three places: the chat model editor (which file types are really sent, and which declared kinds the provider's integration cannot deliver), the list of configured chat models (a new "Accepts" column, and one button that gives the configurations declaring nothing what their model's metadata know), and the setup wizard (what the chosen model accepts). The models libraries bundled with the providers know the input modalities of more models, and a library rule can state them for a whole family of models by pattern.

### 1.2 The files of a conversation travel with the requests

- An upload that no reader turns into text is accepted when the chat's model takes its type. The upload box tells the user which types those are.
- On every request the candidates are gathered: such uploads, plus the pictures, recordings and videos generated earlier in the same conversation. Only the kinds switched on are gathered, each cut by the limits of its kind, and room is kept for them in the context budget.
- Only the model that answers receives them. The calls that work on the text of the request (query rewriting, routing, the prompt of an image generation) are made without files.
- Files can be uploaded as the first action of a new chat (two new endpoints create the chat and take the files in one request).

### 1.3 Files that arrive from tools and agents are kept, and can be shown to the model

- A read-only files location named `generated` holds, per conversation, what the models generated and what arrived from outside: the picture an MCP tool returned, the file a remote A2A agent sent back. The user finds these files in the conversation and the file tools can open them.
- A tool can show a file to the model that called it: the model receives it in a message right after the tool results of that round (only the kinds switched on, each within the limits of its kind per round). The new agent tool `viewFile` uses this to let an agent look at a picture of its working directory or of another files location; the Pro tools `extractImages` and `extractSlideImages` show the pictures they extract.

### 1.4 Gebo.ai exchanges files with other systems

- **MCP client:** pictures, recordings and binary resources in a tool result are kept in the conversation instead of being flattened to a placeholder.
- **MCP server:** a network exported as a tool can be given a chat profile; it then runs in a conversation and answers with the files of its run as image, audio or embedded-resource content.
- **A2A server:** an exported network answers with the files its models generated and its tools wrote, declares the input types it takes, tells the caller what it did not take (`notTaken`), and fetches a file named by a link only from hosts the administrator listed.
- **A2A client:** the conversation's files are sent to a remote agent when its card says it takes them and `ai.gebo.a2a.clients.send-files-enabled` is on (off by default); a file the remote agent returns is kept, and one named by an address is fetched only from the agent's own address.

### 1.5 Images, recordings and videos in the knowledge base

This is the three-step design agreed on 2026-11-20. Everything is off by default, and no service calls another service that it did not call before.

1. **Ingestion** (`ai.gebo.images.ingestion-enabled`, `ai.gebo.recordings.ingestion-enabled`, `ai.gebo.videos.ingestion-enabled`): the readers hand over each image as a Spring AI `Document` of its own holding the image as media, with the metadata of the texts of its document. Sources are image files, pictures inside Word, PowerPoint, Excel, OpenDocument and HTML files, and PDF pages without text (rendered as pictures). Recording and video files become one media document each. No reader calls a model.
2. **Chunking:** the chunker chunks text as before and returns the media whole, each as a chunk of its kind (`IMAGE`, `AUDIO`, `VIDEO`) in its place among the text chunks, only for the kinds asked for. It holds no model and needs none.
3. **Vectorization** (`ai.gebo.vectorizator.embed-images`, `embed-recordings`, `embed-videos`): the vectorizator, which already has the models, describes an image with a chat model that reads pictures and transcribes a recording or video with the transcription model, then embeds that text with the unchanged embedding code. The vector ids are recorded with the document's other vector ids in its vectorized-content entry. The monolith runs the same code.

Full-text indexing and graph extraction take text chunks only. In a chat, image, recording and video uploads do not go through these readers; they follow the path of section 1.2.

Two readers were added independently of the switches: PowerPoint (`.ppt`, `.pptx`) and RTF.

### 1.6 The compliance register

The data-flow register names the new movements of data: the files sent as they are to the responder model of a chat profile, to the models of a direct chat (a new entry), to the models of the agents of a network, and the models that images, recordings and videos leave the installation for during vectorization.

---

## 2. Settings added

| Property | Default | Meaning |
|---|---|---|
| `ai.gebo.images.chat-enabled` | `false` | Images go to the chat models that take them, with a request and when a tool shows one. |
| `ai.gebo.images.chat-max-images` | `2` | Images carried by one request, or shown in one round of tool calls, at most. |
| `ai.gebo.images.chat-max-image-mb` | `2` | Largest image carried. |
| `ai.gebo.images.chat-max-total-mb` | `4` | All images of one request or round together. |
| `ai.gebo.images.chat-estimated-tokens-per-image` | `1500` | Context room kept per image. |
| `ai.gebo.recordings.chat-enabled` | `false` | The same switch for recordings. |
| `ai.gebo.recordings.chat-max-recordings` / `chat-max-recording-mb` / `chat-max-total-mb` / `chat-estimated-tokens-per-recording` | `1` / `5` / `5` / `5000` | The limits of recordings. |
| `ai.gebo.videos.chat-enabled` | `false` | The same switch for videos. |
| `ai.gebo.videos.chat-max-videos` / `chat-max-video-mb` / `chat-max-total-mb` / `chat-estimated-tokens-per-video` | `1` / `10` / `10` / `10000` | The limits of videos. |
| `ai.gebo.documents.chat-enabled` | `false` | The same switch for any other file a model takes as it is. |
| `ai.gebo.documents.chat-max-documents` / `chat-max-document-mb` / `chat-max-total-mb` / `chat-estimated-tokens-per-document` | `1` / `2` / `2` / `5000` | The limits of those files. |
| `ai.gebo.a2a.clients.send-files-enabled` | `false` | The conversation's files are sent to the remote A2A agents whose card takes them. |
| `ai.gebo.a2a.clients.send-max-file-mb` / `send-max-total-mb` | `2` / `4` | Largest file, and all files of one message, sent to a remote agent. |
| `ai.gebo.a2a.server.linked-files-hosts` | empty | Hosts from which a file an A2A caller names by a link is fetched; empty means none. |
| `ai.gebo.images.ingestion-enabled` | `false` | The readers give images as media documents. |
| `ai.gebo.images.max-images-per-document` | `30` | Images taken of one document at most. |
| `ai.gebo.images.min-image-bytes` | `2048` | A smaller image is passed over as a decoration. |
| `ai.gebo.images.max-image-mb` | `20` | A larger image is passed over. |
| `ai.gebo.recordings.ingestion-enabled` | `false` | Recording files are read as media documents. |
| `ai.gebo.recordings.max-recording-mb` | `20` | Largest recording taken. |
| `ai.gebo.videos.ingestion-enabled` | `false` | Video files are read as media documents. |
| `ai.gebo.videos.max-video-mb` | `20` | Largest video taken. |
| `ai.gebo.vectorizator.embed-images` | `false` | Describe images and embed the descriptions. |
| `ai.gebo.vectorizator.embed-recordings` | `false` | Transcribe recordings and embed the transcripts. |
| `ai.gebo.vectorizator.embed-videos` | `false` | Transcribe videos and embed the transcripts. |
| `ai.gebo.vectorizator.image-model-code` | empty | The chat model that describes images; empty means the default chat model when it reads pictures, else the first configured one that does. |
| `ai.gebo.vectorizator.max-media-mb` | `20` | A larger media is not given to a model. |

Fixed in code, not settings: 20 MB per file and 40 MB per answer for the files of a run sent over A2A and MCP (`ChatRunFiles`), 20 MB for a linked file fetched over A2A, 20 MB for a file shown by `viewFile`, 200 PDF pages rendered at 110 DPI, 8 MB of media per chunk set.

---

## 3. What was checked and what was not

Checked: the unit tests of the changed modules (each listed in its part below), a build of the whole application, compilation of the chunker, vectorizator, full-text and graph microservices, and 29 integration tests of the application (agents, chat life cycle, budget, uploads) run before the last merges of `develop`. After merging `develop` only `ToolsRoomTest` (OSS) and the Pro document libraries module (345 tests) were run again.

Not checked: no request was sent to a real provider; nothing was tried in the browser; nothing was run on a microservices stack; no ingestion with images, recordings or videos was run end to end, since the integration tests keep the switches off; the microservices' own generated stubs were not refreshed; the full application suite and the Pro suite beyond the document libraries module were not run.

---

## 4. Open points

- **Direct image embedding** (embedding the image itself when the embedding model can) is agreed for later; the embedding model contract has no declared capability for it yet.
- **Image editing** is not built: the Spring AI image model takes text only.
- **Step 12 of the roadmap** (Office add-ins in other repositories, pictures of web pages named by an address, multimodal embeddings) is not built.
- Agent tools exported over MCP (other than networks with a chat profile) and exported Gebo tools still answer with text only.
- `it.json` carries the reworded texts and the new labels that were looked at; the new keys were not all checked one by one.
- No pull request is open on either repository.

---

# Part A — The models layer

How a chat model is told what it accepts besides text, how a file of a request reaches a model, how a tool shows a file to the model that called it, and what each provider integration can deliver.

## gebo.architecture.ai

### What it does now (functional view)

This module only gains two small contracts that other modules implement or call. They let two things happen without the modules involved knowing each other:

- A file that arrives in a conversation from outside the tools' own file locations (the picture an MCP tool returned, the file another agent sent back) can be kept in the conversation, in a read-only files location named `generated`, so that the user finds it among the conversation's files, the file tools can open it, and a model that takes such files sees it on the following requests.
- A tool can show a file to the model that called it (a picture it extracted, drew or received), so that the model looks at the file and does not only read its name.

There is no setting in this module.

### Class by class

#### `IGConversationGeneratedFilesStore` (new) — gebo.architecture.parent/gebo.architecture.ai/src/main/java/ai/gebo/architecture/ai/files/IGConversationGeneratedFilesStore.java

The contract for keeping a file in a conversation. The constant `LOCATION_ID` is `"generated"`, the identifier of the read-only files location that holds the files kept this way. The method `store(chatSessionCode, name, mimeType, data)` keeps one file and returns it as a `GLibraryFile`, the form in which the file tools find it in that location. The static helper `extensionOf(mimeType)` derives a file extension from the subtype of a MIME type: it drops the parameters after a semicolon, maps `jpeg` to `jpg`, `mpeg` to `mp3` for audio (and `mpeg` otherwise), `svg+xml` to `svg`, `plain` to `txt`, `x-wav` and `wave` to `wav`, and answers `.bin` when there is no type or when the subtype is not one to eight lowercase letters or digits. The interface has no implementation in the modules of this part.

#### `IGToolShownFiles` (new) — gebo.architecture.parent/gebo.architecture.ai/src/main/java/ai/gebo/architecture/ai/files/IGToolShownFiles.java

The contract a tool uses to show a file to the model. The constant `TOOLS_CONTEXT_KEY` is `"geboToolShownFiles"`, the key under which an implementation is placed in the Spring AI tools context. The instance method `show(name, mimeType, data)` answers `true` when the model will see the file and `false` when the model does not take files of that type or the round of tool calls already carries as much as it may. The static method `show(ToolContext, name, mimeType, data)` is what a tool calls: it looks for the implementation in the tools context and answers `null` when the call has no way to show files, otherwise the instance answer. The implementation is `GToolShownFiles` in the models abstraction layer (below).

---

## gebo.architecture.llms.abstraction.layer

### What it does now (functional view)

**A model is described by what it accepts as input.** The configuration of a chat model already carried `inputModalities` (text, image, audio, video, document). On this branch that declaration takes effect: a file of a request goes to a model as it is only when two things agree, namely that the configuration declares the modality of the file, and that the integration of the provider can deliver the MIME type of the file. A configuration that declares nothing is text only. An integration that states nothing carries nothing, so no file reaches a provider nobody checked. The decision is taken in one place (`GModelMediaGuard`) and always has a reason: sent, the model does not read it, the integration does not carry it, or the type is unknown.

**The models library can say what a family of models accepts.** A parameter rule of the library may now state `inputModalities` for the models it covers by pattern. This serves the models that have no entry of their own (the dated variants of a model, such as `gpt-5.2-2025-12-11`) and the providers whose models API tells nothing about inputs. A rule that states only the input modalities is ignored when the request parameters of a model are looked up, so it never hides the rule after it. What a provider's API or the model's own entry said always wins over a rule. The library also records, where the provider's API tells it, what a model answers with besides text (`outputModalities`).

**New chat model configurations take the modalities automatically.** When a chat model configuration is inserted through the administration controllers without input modalities, it receives what the metadata of the chosen model know. An update keeps what the form sends.

**The files of a request are attached to the answering model's last user message.** The request context can now carry candidate files (`getMedia()`). When the last user message is built, each candidate is checked by the guard; the files that pass are read and attached, the others are logged at debug level with the reason and never fail the call. A file that cannot be read is logged as an error and the request goes without it. The calls that work on the text of the request only can ask for the context without its files (`withoutMedia()`), so that a picture is sent, and paid for, once.

**A tool can show a file to the model that called it.** In the calls where Gebo runs the tool loop itself, where there are tools, and where the model takes files at all, a collector is placed in the tools context. What the tools show during a round reaches the model in a user message of its own, right after the results of that round. A collector exists only while the installation sends some kind of file the model takes, and a round shows each kind within the limits of that kind (`GModelFilesDelivery`).

There is no new configuration property in this module.

### Class by class

#### `AbstractBaseChatModelsConfigurationCRUDController` (modified) — gebo.architecture.parent/gebo.architecture.llms.abstraction.layer/src/main/java/ai/gebo/llms/abstraction/layer/controllers/AbstractBaseChatModelsConfigurationCRUDController.java

The base class of the controllers that create and change chat model configurations. In `insertInternal`, before the configuration is stored, a configuration whose `inputModalities` is `null` receives `IGChatModelConfigurationSupportService.inputModalitiesOf(config.getChoosedModel())`, that is, what the metadata of the chosen model know. The comment states that this matches what the setup wizards already do and that an update keeps what the form sends.

#### `ForwardingChatRequestContext` (modified) — .../llms/abstraction/layer/model/ForwardingChatRequestContext.java

The wrapper used to build views of a request context. It gains `getMedia()`, which forwards to the wrapped context, so that every view of a context keeps its files unless it overrides the method.

#### `GBaseChatModelConfig` (modified) — .../llms/abstraction/layer/model/GBaseChatModelConfig.java

Only the documentation of the field `inputModalities` changes. It no longer says that Gebo gives the models nothing but text; it says that a configuration made before the field existed stays text only until it is edited or given its model's metadata, and that a file goes to the model only when its modality is declared here and the provider's integration carries its type.

#### `GModelCarriedMediaTypes` (new) — .../llms/abstraction/layer/model/GModelCarriedMediaTypes.java

Describes what the integration of a provider can deliver to its models besides text: four lists of MIME types, `image`, `audio`, `video` and `document`. A type is either exact (`application/pdf`) or a whole family (`image/*`). It describes the integration, not the model. `none()` creates an integration that carries nothing; the fluent methods `images(...)`, `audio(...)`, `video(...)` and `documents(...)` set each list. `typesOf(modality)` returns the list of a modality (empty for text and for an unknown one). `carries(mimeType)` says whether a file of that type is carried, using the list of the modality the type belongs to. `carriesNothing()` says whether all four lists are empty. The static `essence(mimeType)` reduces a MIME type to lowercase without parameters (`Image/PNG; q=1` becomes `image/png`) and answers `null` when the value is not a type/subtype pair. The static `matchesAny(types, mimeType)` checks a type against a collection in which `family/*` stands for a family. The class has plain getters and setters so that it serializes as JSON for the administration interface.

#### `GModelInputModality` (modified) — .../llms/abstraction/layer/model/GModelInputModality.java

The enumeration `TEXT, IMAGE, AUDIO, VIDEO, DOCUMENT`. It gains the static `ofMimeType(mimeType)`, which gives the modality of a file: `IMAGE`, `AUDIO` and `VIDEO` by the family of the type, `DOCUMENT` for any other file, and `null` when there is no type. The class documentation is updated in the same sense as `GBaseChatModelConfig`.

#### `GModelFilesDelivery` (new) — .../llms/abstraction/layer/model/GModelFilesDelivery.java

Whether, and how much, files go to the chat models as they are: one switch and one set of limits per kind of file. The record `Limits(enabled, maxFiles, maxFileBytes, maxTotalBytes, estimatedTokensPerFile)` describes a kind; `Limits.off()` is a kind switched off and `Limits.of(..)` builds one from megabytes. The current limits are a static map, empty (every kind off) until `set(..)` is called; `get()` returns a copy, used by tests to set them back. `of(kind)` and `ofMimeType(mimeType)` give the limits of a kind, never null. `delivers(mimeType)` says whether files of that kind go to the models; `deliversAnything()` whether any kind does; `delivered(mimeTypes)` keeps, of the MIME types a model takes, those of the kinds switched on; `estimatedTokens(mimeType)` is the context room of one file of that kind. The nested class `Admission` counts the files of one request or one round of tool calls: `admit(mimeType, size)` accepts a file when its kind is on and it stays within the number of files, the size of one file and the total bytes of its kind.

#### `GModelMediaGuard` (new) — .../llms/abstraction/layer/model/GModelMediaGuard.java

The single place that decides whether a file may go to a model as media. The nested enumeration `Outcome` has four values: `SENT`, `MODEL_DOES_NOT_READ_IT` (the configuration does not declare the modality), `INTEGRATION_DOES_NOT_CARRY_IT` (the modality is declared but the provider's integration does not carry the type) and `UNKNOWN_TYPE`. `decide(declared, carried, mimeType)` applies those checks in that order. `sends(...)` is the boolean form. `effectiveMimeTypes(declared, carried)` lists the MIME types a model effectively takes, that is, the carried types of the declared modalities, without duplicates; it is empty for a text-only model and for an integration that carries nothing. `declaredNotCarried(declared, carried)` lists the declared modalities, other than text, for which the integration carries nothing, which is what a configuration promises and Gebo cannot deliver through that provider.

#### `IChatRequestContext` (modified) — .../llms/abstraction/layer/model/IChatRequestContext.java

The request context gains two default methods. `getMedia()` returns the files of the request that may go to the model as they are (pictures, PDF files, recordings that no content handler turned into text); it returns an empty list by default, and the entries are candidates only. `withoutMedia()` returns the same context without its files (a `ForwardingChatRequestContext` whose `getMedia()` is empty), or the context itself when it has none; the documentation names the router, the query rewriter and the image prompt writer as the calls it is meant for.

#### `IChatRequestMedia` (new) — .../llms/abstraction/layer/model/IChatRequestMedia.java

One candidate file of a request: `getName()`, `getMimeType()`, `getSize()` (minus one when unknown) and `readBytes()`. The bytes are read only when the file is actually attached to a model call.

#### `ClientChatCallUtil` (modified) — .../llms/abstraction/layer/services/ClientChatCallUtil.java

Four static methods are removed: `createPromptAndContext(Prompt, IChatRequestContext)`, `createHistoryFragment(IChatRequestContext)`, `createPromptContextHistory(Prompt, IChatRequestContext)` and `createLastUserMessage(GPromptTemplateConfig, Map, IChatRequestContext)`. The last one was a stub that returned `null`. Nothing is added to the class.

#### `ModelFilesDeliveryConfig` (new) — .../llms/abstraction/layer/services/config/ModelFilesDeliveryConfig.java

A `@Configuration` whose `@Value` fields read the switch and the limits of each kind (`ai.gebo.images.chat-*`, `ai.gebo.recordings.chat-*`, `ai.gebo.videos.chat-*`, `ai.gebo.documents.chat-*`; defaults in section 2). Its `@PostConstruct` method `apply()` builds the four `Limits` and hands them to `GModelFilesDelivery.set(..)`, logging them at start. It follows the pattern of `TokensBudgetConfig` in the same package.

#### `GAbstractConfigurableChatModel` (modified) — .../llms/abstraction/layer/services/GAbstractConfigurableChatModel.java

The common base of every configured chat model. Three changes:

- In `prepareCall`, when the call has its own tool loop (`toolCalling` is not null), has tools, and `getEffectiveInputMimeTypes()` is not empty, a `GToolShownFiles` is created with `this::acceptsMedia` as its test, is put in a copy of the tools context under `IGToolShownFiles.TOOLS_CONTEXT_KEY`, and is passed to `toolCalling.advisor(observationRegistry, shownFiles)`.
- `createLastUserMessage` now calls `mediaToAttach(chatContext)` and, when the result is not empty, builds the message with `UserMessage.builder().text(content).media(media).build()` instead of a plain text message.
- The new protected method `mediaToAttach(chatContext)` walks the candidates of `chatContext.getMedia()`, asks `GModelMediaGuard.decide` with the model's `getInputModalities()` and `getCarriedMediaTypes()`, skips (with a debug log naming the outcome) every file that is not `SENT`, reads the bytes of the others and builds a Spring AI `Media` for each. Empty files are skipped. An `IOException` or runtime exception while reading is logged as an error and the request goes without that file.

Two package-private static helpers support it. `sentMimeType(mimeType)` returns the type without parameters and turns `audio/mpeg` into `audio/mp3`; the comment explains that Spring AI's OpenAI chat model declares an audio file as MP3 only when its type contains "mp3" and as WAV otherwise. `mediaName(name, position)` keeps only letters, digits, spaces, hyphens, parentheses and square brackets in a file name (the comment cites Bedrock's rule for document names) and answers `file-<position+1>` when nothing is left.

Since 2026-11-22 the kinds of file the installation sends are checked too. In `prepareCall` the collector of the files shown by tools is created only when `GModelFilesDelivery.delivered(getEffectiveInputMimeTypes())` is not empty. In `mediaToAttach` a candidate whose kind is off is skipped before it is read, and a `GModelFilesDelivery.Admission` applies the limits of each kind after the bytes are read, so the last gate before the provider holds whoever gathered the candidates.

#### `IGChatModelConfigurationSupportService` (modified) — .../llms/abstraction/layer/services/IGChatModelConfigurationSupportService.java

The contract each provider integration implements. It gains the default method `carriedMediaTypes(modelCode)`, which states what the integration can deliver to a model besides text. It may depend on the model when a provider calls its models through different APIs; `modelCode` may be `null` when no model is chosen yet. The default is `GModelCarriedMediaTypes.none()`.

#### `IGConfigurableChatModel` (modified) — .../llms/abstraction/layer/services/IGConfigurableChatModel.java

The contract of a configured chat model gains four default methods. `getInputModalities()` returns what the configuration declares, or `[TEXT]` when it declares nothing. `getCarriedMediaTypes()` returns `GModelCarriedMediaTypes.none()` unless the provider's model class overrides it. `getEffectiveInputMimeTypes()` and `acceptsMedia(mimeType)` delegate to `GModelMediaGuard.effectiveMimeTypes` and `GModelMediaGuard.sends`.

#### `GModelChoiceMetaInfoEnricherServiceImpl` (modified) — .../llms/abstraction/layer/services/impl/GModelChoiceMetaInfoEnricherServiceImpl.java

The service that completes the metadata of a model choice from the library. `enrichChatModelMetaInfos` now also calls the new private method `fillInputModalitiesByRule(providerId, choice)`. That method does nothing when the choice already knows its input modalities (`knownInputModalities()` is not `null`); otherwise it asks the library `findInputModalities(providerId, choice.getCode())` and, when a rule answers, sets them on the metadata, creating a `ModelMetaInfo` marked as a chat model when the choice had none.

#### `GToolShownFiles` (new) — .../llms/abstraction/layer/services/toolcalling/GToolShownFiles.java

The implementation of `IGToolShownFiles` for one model call. It is built with a predicate saying whether the model takes a MIME type. `show(name, mimeType, data)` refuses (returns `false`) a file with no readable type, with no bytes, of a type the model does not take, or one whose kind is switched off or that is beyond the limits of its kind for the round (`GModelFilesDelivery.Admission`); otherwise it keeps the file as a `Media` (with `audio/mpeg` sent as `audio/mp3` and the name cleaned the same way as `mediaName`) and returns `true`. `takeMessage()` returns a `UserMessage` whose text is "These are the files the tools just showed you:" followed by the names, with the files attached, and then empties the collector; it returns `null` when nothing was shown. Both methods are synchronized.

#### `GeboToolCalling` (modified) — .../llms/abstraction/layer/services/toolcalling/GeboToolCalling.java

Gains the overload `advisor(observationRegistry, shownFiles)`, which creates the advisor of a call whose tools can show files.

#### `GeboToolCallingAdvisor` (modified) — .../llms/abstraction/layer/services/toolcalling/GeboToolCallingAdvisor.java

The advisor that runs Gebo's tool loop. It gains the field `shownFiles` and a second constructor that receives it (the existing constructor passes `null`). The private method `withShownFiles(next)` appends the message returned by `shownFiles.takeMessage()` to the messages of the next round when there is one. Both `doGetNextInstructionsForToolCall` and `doGetNextInstructionsForToolCallStream` now wrap the parent's result with it, so the behaviour is the same for blocking and streaming calls.

#### `IGModelsLibraryDao` (modified) — gebo.architecture.parent/gebo.architecture.llms.abstraction.layer/src/main/java/ai/gebo/llms/models/metainfos/IGModelsLibraryDao.java

The contract of the models library gains two default methods. `findInputModalities(providerId, modelId)` returns the input modalities of the first rule covering the model that states them, or `null` (the default) when no rule does. `knowsAsChatModel(providerId, modelId)` says whether the library knows a model as a chat model, either through an entry of its own with `chatModel` true or through a rule that states its inputs. Its purpose, per the comment, is to keep in the chat model lists a model whose identifier carries a marker such as "audio" or "image", because a chat model that listens or draws is named that way too.

#### `ModelMetaInfo` (modified) — .../llms/models/metainfos/ModelMetaInfo.java

The metadata of a model gains the field `outputModalities` (what the model answers with besides text, where the provider's models API tells it; `null` when unknown), with getter and setter. The copy method and the merge method (which fills a `null` value from another instance) both handle the new field.

#### `ModelParameterRules` (modified) — .../llms/models/metainfos/ModelParameterRules.java

A rule of the library that covers models by pattern gains the list `inputModalities` (empty when the rule says nothing about it). `knowsInputModalities()` says whether the rule states them. `statesParameters()` says whether the rule says anything about the request parameters (sampling, reasoning efforts, query or document input type, speech format). The merge of a live rule with a library rule keeps the live rule's modalities when it has them and the library's otherwise. `toString()` includes the new field.

#### `GModelsLibraryDaoImpl` (modified) — .../llms/models/metainfos/impl/GModelsLibraryDaoImpl.java

The library implementation. `findParameterRules` now filters on `ModelParameterRules::statesParameters`, so a rule that states only the inputs is skipped. The new `findInputModalities` returns a copy of the modalities of the first rule that both states them and covers the model, with a debug log.

#### `GModelMediaGuardTest` (new) — gebo.architecture.parent/gebo.architecture.llms.abstraction.layer/src/test/java/ai/gebo/llms/abstraction/layer/model/GModelMediaGuardTest.java

Seven tests on the guard. They assert that a file is sent when declared and carried (including a MIME type with different case and parameters, and a family pattern); that an undeclared modality gives `MODEL_DOES_NOT_READ_IT`, also for a `null` declaration; that an uncarried type gives `INTEGRATION_DOES_NOT_CARRY_IT` (a TIFF picture on an integration listing four picture types, audio on an images-only integration, an integration stating nothing, a `null` integration, a Word file where only PDF is carried); that a missing or malformed type gives `UNKNOWN_TYPE`; that the effective types are the carried ones of the declared modalities; that `declaredNotCarried` names the right modalities; and that `ofMimeType` maps types to modalities (`text/csv` is a document).

#### `ChatRequestMediaAttachmentTest` (new) — .../src/test/java/ai/gebo/llms/abstraction/layer/services/ChatRequestMediaAttachmentTest.java

Seven tests on `createLastUserMessage`, using a small subclass of `GAbstractConfigurableChatModel` with chosen declared modalities and carried types. They assert that of a PNG, a PDF and a TIFF only the PNG is attached to a model declaring text and image with an integration carrying PNG, JPEG and PDF, and that the files left out are never read; that a model declaring nothing, an empty list or text only gets a plain message; that an integration carrying nothing gets nothing whatever the model declares; that a view of the context (`withRulesToFollow`) keeps the files while `withoutMedia()` removes them; and that `audio/mpeg` is sent as `audio/mp3`, with `mediaName` giving `file-1` for an empty name and `chart 1-png` for `chart 1.png`. Two more assert that with every kind off no file is attached or even read, and that a second image beyond the number allowed for its kind stays out. The tests switch every kind on before each test and set the limits back after.

#### `GModelChoiceMetaInfoEnricherServiceImplTest` (modified) — .../src/test/java/ai/gebo/llms/abstraction/layer/services/impl/GModelChoiceMetaInfoEnricherServiceImplTest.java

The test library double now answers `findInputModalities` for identifiers starting with `acme-vision`. Three tests are added: a rule gives the input modalities to a model nobody else described; what the provider's API told (`supportsVision` false, hence text only) wins over the rule; a model no rule covers stays unknown (`null`).

#### `ToolShownFilesTest` (new) — .../src/test/java/ai/gebo/llms/abstraction/layer/services/toolcalling/ToolShownFilesTest.java

Five tests run a real `ChatClient` with a fake model that calls a `viewPicture` tool once. They assert that the file the tool shows reaches the model in the second prompt as a `UserMessage` placed right after the `ToolResponseMessage`, with the PNG bytes attached and the file name in its text; that when the model does not take the file the tool is told `false` and no message is added; that a call with no collector answers `null` to the tool and adds no media; and that a round accepts exactly the number of files of its kind, hands them over once, and accepts files again in the next round. The fifth asserts that a kind switched off is not shown, that an image beyond the size or the number of its kind is refused, and that with every kind off nothing is shown. The tests switch every kind on before each test and set the limits back after.

---

## gebo.architecture.llms.abstraction.layer.controllers

### What it does now (functional view)

The administrator can see, for every configured chat model, what it takes as input from four points of view: what the configuration declares, what the stored metadata of the model know, what the provider's integration carries, and what results from the two (the MIME types a request may actually carry), together with the declared modalities the integration carries nothing for. While a model is being chosen in the editor, the interface can ask what the integration of that model type carries. One action gives every configuration that declares nothing the modalities its model's metadata know, in one pass; this is how configurations created before the feature stop being text only without editing them one by one.

### Class by class

#### `ChatModelsController` (modified) — gebo.architecture.parent/gebo.architecture.llms.abstraction.layer.controllers/src/main/java/ai/gebo/llms/abstraction/layer/controllers/ChatModelsController.java

Three endpoints are added, with an injected `IGPersistentObjectManager` and a logger.

- `GET getChatModelsInputs` returns a `ChatModelInputs` for every configured chat model that has a configuration.
- `GET getCarriedMediaTypes` takes `modelTypeCode` and an optional `modelCode`, finds the support service of that model type and returns its `carriedMediaTypes(modelCode)`, or `GModelCarriedMediaTypes.none()` when the type is unknown.
- `POST applyModelsMetadataInputModalities` goes through the configured chat models; it skips a configuration that already declares something and one whose metadata know nothing; for the others it sets the known modalities and updates the configuration. A failure on one configuration is logged and reported as an error message without stopping the others. It returns an `OperationStatus` holding the configurations changed, as they are now.

The private method `inputsOf(model)` fills a `ChatModelInputs` from the configuration and the running model, using `GModelMediaGuard.declaredNotCarried` for the last field.

#### `ChatModelInputs` (new) — gebo.architecture.parent/gebo.architecture.llms.abstraction.layer.controllers/src/main/java/ai/gebo/llms/abstraction/layer/controllers/model/ChatModelInputs.java

The answer of the endpoints above, a Lombok `@Data` class with: `code` and `description` of the configuration, `modelTypeCode`, `modelCode` (the model the requests name), `inputModalities` (what the configuration declares; `null` means text only), `metadataInputModalities` (what the stored metadata know; `null` means they know nothing, which is not the same as text only), `carriedMediaTypes`, `effectiveInputMimeTypes` and `declaredNotCarried`.

---

## gebo.llms.parent — the provider integrations

### What they do now (functional view)

Each provider integration now states which file types it can deliver to its models. The statements are grounded in how the corresponding Spring AI 2.1.0-RC2 chat model treats the media of a user message, as recorded in the comment of each method:

| Provider module | Pictures | Documents | Audio | Video |
|---|---|---|---|---|
| `gebo.llms.openai`, model called through chat completions | `image/*` | `application/pdf` | `audio/mp3`, `audio/mpeg`, `audio/wav`, `audio/x-wav`, `audio/wave` | none |
| `gebo.llms.openai`, model called through the Responses API | `image/*` | `application/pdf` | none | none |
| `gebo.llms.generic-openai-compatible` | `image/*` | `application/pdf` | same five audio types | none |
| `gebo.llms.anthropic3` | PNG, JPEG, GIF, WebP | `application/pdf` | none | none |
| `gebo.llms.google_vertex` | `image/*` | `application/pdf` | `audio/*` | `video/*` |
| `gebo.llms.aws-bedrock` | JPEG, PNG, GIF, WebP | PDF, CSV, DOC, DOCX, XLS, XLSX, HTML, TXT, Markdown | none | MKV, MOV, MP4, WebM, FLV, MPEG, WMV, 3GP |
| `gebo.llms.mistral` | `image/*` | none | none | none |
| `gebo.llms.ollama` | `image/*` | none | none | none |
| `gebo.llms.deepseek` | none | none | none | none |

In every module the inner configurable chat model class overrides `getCarriedMediaTypes()` to return the support service's `carriedMediaTypes` for the code of the chosen model.

The models libraries bundled with the providers gain input modalities for more models, each with its source and date in the YAML comments. Three lookups now record what a model answers with (`outputModalities`). The OpenAI and Google Vertex chat model lists keep a model whose identifier looks like an audio or image model when the library knows it as a chat model.

There is no new configuration property in these modules.

### Class by class

#### `AnthropicChatModelConfigurationSupportService` (modified) — gebo.llms.parent/gebo.llms.anthropic3/src/main/java/ai/gebo/llms/anthropic/services/AnthropicChatModelConfigurationSupportService.java

`carriedMediaTypes` returns the four picture types `image/png`, `image/jpeg`, `image/gif`, `image/webp` and `application/pdf`. The comment records that Spring AI's Anthropic chat model raises "Unsupported media type" on any other type. The inner `AnthropicConfigurableChatModel` overrides `getCarriedMediaTypes()`.

#### `BedrockChatModelConfigurationSupportService` (modified) — gebo.llms.parent/gebo.llms.aws-bedrock/src/main/java/ai/gebo/llms/aws_bedrock/services/BedrockChatModelConfigurationSupportService.java

`carriedMediaTypes` lists the picture, document and video types, read from the Spring AI `Media.Format` constants (the same ones Spring AI's Bedrock format maps are keyed by) through the private helper `types(MimeType...)`. The comment records that the Bedrock chat model raises "Unsupported media format" on any other type, audio included. The inner `BedrockConfigurableChatModel` overrides `getCarriedMediaTypes()`.

#### `BedrockFoundationModelsLookupService` (modified) — gebo.llms.parent/gebo.llms.aws-bedrock/src/main/java/ai/gebo/llms/aws_bedrock/services/BedrockFoundationModelsLookupService.java

When a foundation model lists output modalities that include `TEXT`, the metadata now record them through `setOutputModalities(GModelInputModality.fromProvider(...))`.

#### `DeepseekChatModelConfigurationSupportService` (modified) — gebo.llms.parent/gebo.llms.deepseek/src/main/java/ai/gebo/llms/deepseek/services/DeepseekChatModelConfigurationSupportService.java

`carriedMediaTypes` returns `GModelCarriedMediaTypes.none()` explicitly. The comment records that Spring AI's DeepSeek chat model builds its messages from their text and drops the media of a user message silently, so nothing is carried even for a model whose API declares image input. The inner `DeepseekConfigurableChatModel` overrides `getCarriedMediaTypes()`.

#### `DeepseekModelsLookupService` (modified) — gebo.llms.parent/gebo.llms.deepseek/src/main/java/ai/gebo/llms/deepseek/services/DeepseekModelsLookupService.java

When the models API gives `output_modalities`, the metadata record them as `outputModalities`.

#### `GenericOpenAIAPIChatModelConfigurationSupportService` (modified) — gebo.llms.parent/gebo.llms.generic-openai-compatible/src/main/java/ai/gebo/llms/openai_compat/services/GenericOpenAIAPIChatModelConfigurationSupportService.java

`carriedMediaTypes` returns `image/*`, `application/pdf` and the five audio types. The comment records that OpenAI-compatible providers are called through chat completions, where Spring AI's OpenAI chat model carries pictures, PDF as a file part and audio as MP3 or WAV bytes, and pastes any other type into the prompt as base64 text; what the backend actually reads is left to the configuration's input modalities. The inner `GenericOpenAIConfigurableChatModel` overrides `getCarriedMediaTypes()`.

#### `OpenRouterModelsListProviderService` (modified) — gebo.llms.parent/gebo.llms.generic-openai-compatible/src/main/java/ai/gebo/llms/openai_compat/services/OpenRouterModelsListProviderService.java

When a model's architecture lists output modalities, the metadata record them as `outputModalities`.

#### `XaiEmbeddingModelsListProviderService` (modified) — gebo.llms.parent/gebo.llms.generic-openai-compatible/src/main/java/ai/gebo/llms/openai_compat/services/XaiEmbeddingModelsListProviderService.java

For the chat models it lists, the metadata now also receive `inputModalities` from the `input_modalities` the xAI API gives, besides the existing `supportsVision`.

#### `groq.yml` (modified) — gebo.llms.parent/gebo.llms.generic-openai-compatible/src/main/resources/bundle-models/groq.yml

The existing rule for `qwen/qwen3.8-27b` gains `inputModalities: [TEXT, IMAGE]`, and a new rule that states only the input modalities declares `meta-models/muse-spark-open` as text and image. The comments name Groq's vision page and the model page as sources, read on 2026-11-19, and note that Groq's models API tells nothing about inputs.

#### `nvidia.yml` (modified) — gebo.llms.parent/gebo.llms.generic-openai-compatible/src/main/resources/bundle-models/nvidia.yml

The entry of `adept/fuyu-8b` gains `inputModalities: [TEXT, IMAGE]`, with the NVIDIA model reference page named as the source.

#### `xai.yml` (modified) — gebo.llms.parent/gebo.llms.generic-openai-compatible/src/main/resources/bundle-models/xai.yml

Six entries gain `inputModalities: [TEXT, IMAGE]`: `grok-4.7`, `grok-4.6`, `grok-4.5`, `grok-4.3`, `grok-4.20-0309-reasoning` and `grok-4.20-0309-non-reasoning`. The comment names each model's page as the source and states that the lookup's own list wins when it gives one.

#### `ProviderModelsLibrariesWiringTest` (modified) — gebo.llms.parent/gebo.llms.generic-openai-compatible/src/test/java/ai/gebo/llms/openai_compat/config/ProviderModelsLibrariesWiringTest.java

The expected number of Groq parameter rules goes from 2 to 3. New assertions check that `findInputModalities` answers text and image for the two Groq models and `null` for `openai/gpt-oss-20b`; that `findParameterRules` answers `null` for `meta-models/muse-spark-open` (its rule states inputs only); and that the library entries of `grok-4.5` (xAI) and `adept/fuyu-8b` (NVIDIA) carry the image modality.

#### `GoogleVertexChatModelConfigurationSupportService` (modified) — gebo.llms.parent/gebo.llms.google_vertex/src/main/java/ai/gebo/llms/google_vertex/services/GoogleVertexChatModelConfigurationSupportService.java

`carriedMediaTypes` returns `image/*`, `audio/*`, `video/*` and `application/pdf`. The comment records that Spring AI's Google GenAI chat model sends any media as it is with no check of its type, and that the list is what Gemini's models read. The inner `GoogleVertexConfigurableChatModel` overrides `getCarriedMediaTypes()`. The static `toChoice(model)` now delegates to a new overload `toChoice(model, knownAsChatModel)`: a Gemini model whose identifier contains "embedding", "image", "live", "tts" or "audio" is still excluded unless the predicate says the library knows it as a chat model. The live listing passes `modelsLibrary.knowsAsChatModel(providerId, id)` as that predicate. A model that is not a Gemini model stays out in every case.

#### `google-vertex.yml` (modified) — gebo.llms.parent/gebo.llms.google_vertex/src/main/resources/bundle-models/google-vertex.yml

The entries of `gemini-3.6-flash` and `gemini-3.7-flash` gain `inputModalities: [TEXT, IMAGE, AUDIO, VIDEO]`. The comment names their Vertex model pages (read 2026-11-19) and states that documents were not read there and are not declared.

#### `GoogleVertexChatModelChoicesTest` (modified) — gebo.llms.parent/gebo.llms.google_vertex/src/test/java/ai/gebo/llms/google_vertex/services/GoogleVertexChatModelChoicesTest.java

A new test asserts that `gemini-3.1-flash-lite-image` is dropped by `toChoice(model)`, kept by `toChoice(model, id -> true)`, and that `imagen-4.0-generate-001` stays out whatever the predicate says.

#### `MistralChatModelConfigurationSupportService` (modified) — gebo.llms.parent/gebo.llms.mistral/src/main/java/ai/gebo/llms/mistralai/services/MistralChatModelConfigurationSupportService.java

`carriedMediaTypes` returns `image/*` only. The comment records that Spring AI's Mistral chat model sends every media of a user message as an image URL chunk whatever its type, so an audio file or a PDF would reach Mistral as a picture. The inner `MistralConfigurableChatModel` overrides `getCarriedMediaTypes()`.

#### `OllamaChatModelConfigurationSupportService` (modified) — gebo.llms.parent/gebo.llms.ollama/src/main/java/ai/gebo/llms/ollama/services/OllamaChatModelConfigurationSupportService.java

`carriedMediaTypes` returns `image/*` only. The comment records that Spring AI's Ollama chat model sends every media of a user message in the message's images whatever its type. The inner `OllamaConfigurableChatModel` overrides `getCarriedMediaTypes()`.

#### `OpenAIChatModelConfigurationSupportService` (modified) — gebo.llms.parent/gebo.llms.openai/src/main/java/ai/gebo/llms/openai/services/OpenAIChatModelConfigurationSupportService.java

`carriedMediaTypes(modelCode)` depends on the API the model is called through, decided by `OpenAiApiRouting.usesResponsesApi(modelCode)`: for the Responses API, pictures and PDF only (the comment records that audio raises "Audio input is not supported by the OpenAI Responses API"); for chat completions, pictures, PDF and the five audio types. With no model code it answers the chat completions set. The inner `OpenAIConfigurableChatModel` overrides `getCarriedMediaTypes()`. The filter of the chat model list now keeps a model when `isChatModel(code)` is true or when `modelsLibrary.knowsAsChatModel("openai", code)` is true.

#### `openai.yml` (modified) — gebo.llms.parent/gebo.llms.openai/src/main/resources/bundle-models/openai.yml

The entries that declared `[TEXT, IMAGE]` now declare `[TEXT, IMAGE, DOCUMENT]`: `gpt-4o`, `gpt-4o-2024-05-13`, `gpt-4o-2024-08-06`, `gpt-4.1`, `gpt-4o-mini`, `gpt-4o-mini-2024-07-18`, `gpt-4.1-mini`, `gpt-6.1-sol`, `gpt-6-luna`, `gpt-5.2`, `gpt-5.4-mini` and `gpt-5`. Three entries that had none gain the same value: `gpt-6-astra`, `gpt-6-sol` and `gpt-5.5`. The header comment explains the grounds: the model pages state text and image input, and the file inputs guide states that PDF parsing requires a model that accepts image inputs, so the models that take images take PDF files too. A new parameter rule, stating input modalities only, covers the dated variants by pattern (`gpt-4o-20*`, `gpt-4o-mini-20*`, `gpt-4.1-20*`, `gpt-4.1-mini-20*`, `gpt-5-20*`, `gpt-5.2-20*`, `gpt-5.4-mini-20*`, `gpt-5.5-20*`, `gpt-6-sol-20*`, `gpt-6-luna-20*`, `gpt-6-astra-20*`, `gpt-6.1-sol-20*`) with `[TEXT, IMAGE, DOCUMENT]`.

#### `OpenAIModelsLibrarySegmentTest` (modified) — gebo.llms.parent/gebo.llms.openai/src/test/java/ai/gebo/llms/openai/services/OpenAIModelsLibrarySegmentTest.java

The existing test now expects text, image and document for ten models, including `gpt-6-sol`, `gpt-6-astra` and `gpt-5.5`; the earlier assertion that `gpt-5.5` had no modalities is removed. A new test asserts that `gpt-5.2-2025-12-11` has no entry of its own yet takes its modalities from the rule, as does `gpt-4o-2024-11-20`; that the dated variant keeps the same sampling rule as `gpt-5.2`, so the modalities rule never stands in for the parameter rules; and that `text-embedding-3-large` and `gpt-5.2-pro` get no modalities from the rules.

#### `TestChatModelSupportServiceImpl` (modified) — gebo.llms.parent/gebo.llms.tests.services/src/main/java/ai/gebo/llms/abstraction/layer/tests/TestChatModelSupportServiceImpl.java

The fake chat model used by the integration tests states that it carries every picture, audio and video type and PDF (the constant `CARRIED`), both in `carriedMediaTypes` and in the inner model's `getCarriedMediaTypes()`. The comment gives the reason: a test then sees in the prompt exactly what the configuration's input modalities let through.

---

# Part B — Chat, agents, MCP and A2A

Module paths are under `gebo.architecture.parent/` unless the heading says otherwise; file paths are relative to the module.

## gebo.architecture.chat.abstraction.layer

### What it does now (functional view)

A conversation's files can now reach the chat model as files, not only as extracted text. When a user uploads a picture, a recording, a video or any other file that no content handler turns into text, the upload is accepted if the model answering the chat declares that it takes that type; before this branch such a file was kept only when one of the chat's agent tools worked on its extension, otherwise it was refused. The model that decides is the chat profile's model, else the chat's own model, else the default model. The answer to "which files does this chat accept" now carries a third list, `modelMimeTypes`, beside the extensions read into the chat and the extensions kept for the tools.

On every request the conversation's candidate files are gathered, when the installation sends some kind of file to its models: the uploads that have no ingested text, plus the pictures, recordings and videos the models generated earlier in the same conversation. They are ordered newest first; a file is kept only when its kind is switched on and it stays within the limits of its kind (`ai.gebo.images.chat-*`, `ai.gebo.recordings.chat-*`, `ai.gebo.videos.chat-*`, `ai.gebo.documents.chat-*`; see section 2). Files beyond a limit are not attached and remain reachable by the tools. Each model call later attaches only the candidates its own model takes. Room is kept in the context budget for these files: the estimated tokens of each file's kind are added up and subtracted from the budget for the text of the request, never taking the text below half of the budget. With every kind off nothing is gathered and the budget is untouched. The upload acceptance follows the same switches: the model's MIME types reported for a chat are only those of the kinds switched on.

Image, recording and video uploads are no longer passed to the ingestion readers in a chat, even when the knowledge-base ingestion has readers for such files; they follow the "file no reader takes" path described above. Likewise the two media readers of the ingestion (`ImageIngestionHandler`, `RecordingsAndVideosIngestionHandler`) do not contribute their extensions to the list of file types that are "read into the chat".

The service calls a chat pipeline makes on the text of a request (query rewriting, routing decision, writing the prompt for an image generation) are made without the files, so the files are sent only to the model that answers.

The files a conversation generated or received (pictures drawn by image models, files returned by MCP tools or by remote agents) are now a read-only files location of the conversation, named by `IGConversationGeneratedFilesStore.LOCATION_ID`, that the file tools can list and read as they do with uploads. The same class is the place where such an arriving file is stored. Generated files are now stored with their whole MIME type (`image/png` instead of `image`) and with their size.

The capabilities the chat services report for a model now include what the model accepts as input (`inputModalities`) and the MIME types of the files a request may carry to it (`inputMimeTypes`).

The compliance data-flow register gained two things: for a chat profile whose responder model takes files, a step saying the conversation files are sent to that model as they are, naming the types; and a whole new entry for the chat with a model directly (without a profile), reaching every configured chat model, with the same "files" step for the models that take files.

A defect was also corrected in the upload path: the metadata of an ingested upload document was being replaced by the common upload metadata only (the map was merged with itself); now the handler's own metadata is kept and the upload's common metadata is laid over it.

### Class by class


#### `LLMChatRequestResources` (modified) — src/main/java/ai/gebo/llms/chat/abstraction/layer/llmexchange/model/LLMChatRequestResources.java
The object that carries the resources of one chat request gained a transient, `@JsonIgnore` field `media` of type `List<IChatRequestMedia>`: the conversation's files that may go to the model as they are. The copy constructor copies it. The inner chat request context it creates now implements `getMedia()`, returning that list or an empty list when it is null. This is how the candidates gathered by the session life cycle reach the model layer.

#### `GChatUploadFileTypes` (modified) — src/main/java/ai/gebo/llms/chat/abstraction/layer/model/GChatUploadFileTypes.java
The answer to "which files does this chat accept" gained the field `modelMimeTypes`: the MIME types (exact ones or `family/*`) that the chat's model takes as they are. Its documentation states that such a file is accepted even when no content handler reads it. A two-argument constructor `(chatExtensions, toolsExtensions)` was added so that existing callers keep working, leaving `modelMimeTypes` empty.

#### `LLMGeneratedResourceRepository` (modified) — src/main/java/ai/gebo/llms/chat/abstraction/layer/repository/LLMGeneratedResourceRepository.java
A derived query `findByUserContextCode(String)` returning a `Stream<LLMGeneratedResource>` was added: the files the models generated in one conversation. It is used by `GChatStorageAreaServiceImpl.getSessionMedia` and by `ChatGeneratedFilesProvider`.

#### `IGChatStorageAreaService` (modified) — src/main/java/ai/gebo/llms/chat/abstraction/layer/services/IGChatStorageAreaService.java
The interface gained `getSessionMedia(String userSessionCode)`, returning the conversation's files that may go to the model as they are, newest first and of the kinds switched on and within the limits of each kind. The contract says they are candidates (each model call attaches the ones its model takes) and that their bytes are read only when they are attached.

#### `IGChatUploadFileTypesService` (modified) — src/main/java/ai/gebo/llms/chat/abstraction/layer/services/IGChatUploadFileTypesService.java
The interface gained `modelMimeTypesOfSession(String userSessionCode)`: the MIME types the model of an existing chat takes as they are, empty when the model gets text only.

#### `IGGenericalChatService` (modified) — src/main/java/ai/gebo/llms/chat/abstraction/layer/services/IGGenericalChatService.java
The nested `ModelProviderCapabilities` gained two final fields, `inputModalities` (`List<GModelInputModality>`) and `inputMimeTypes` (`List<String>`). The existing constructor sets them to text only and an empty list; a new constructor takes both and falls back to the same defaults when given null.

#### `ChatGeneratedFilesProvider` (new) — src/main/java/ai/gebo/llms/chat/abstraction/layer/services/impl/ChatGeneratedFilesProvider.java
A `@Service` with `@Order(110)` implementing two interfaces of `gebo.architecture.ai`: `IGConversationFilesProvider` (it gives a files location for a conversation) and `IGConversationGeneratedFilesStore` (it keeps an arriving file). `getId()` returns `LOCATION_ID`. `filesOf(chatSessionCode)` returns an inner `GeneratedLocation`, or null for a blank code. `store(chatSessionCode, name, mimeType, data)` refuses an empty file or a missing conversation, defaults the type to `application/octet-stream`, strips any folder from the name, adds an extension derived from the type when the name has none, stores the bytes through `IGChatStorageAreaService.addMedia` (injected `@Lazy`), and returns the `GLibraryFile` under which the location now lists it. `sessionFolder` resolves the conversation's folder under the work directory and `SESSIONS_AREA`.
The inner `GeneratedLocation` is read-only (`isWritable()` false; `write` throws `UnsupportedOperationException` telling to write into a writable library). `byName()` lists the conversation's generated resources in order of creation under unique names: the file name without folders, with the stored extension appended when missing, or an extension derived from the MIME type for resources stored without one; a duplicate name becomes `name (2).ext`, `name (3).ext` and so on. `list` accepts only the root folder (the location has no folders); `describe` and `read` resolve a name through `byName()`; `read` throws an `IllegalArgumentException` that lists the existing names when the file is unknown. `fileOf` refuses a path that would leave the conversation's folder. The version string of a described file is built from size and modification time.

#### `DataFlowEndpoints` (modified) — src/main/java/ai/gebo/llms/chat/abstraction/layer/services/impl/DataFlowEndpoints.java
A static helper `sentFileTypes(IGConfigurableChatModel)` was added. It returns `model.getEffectiveInputMimeTypes()`, or an empty list for a null model, a null answer or a runtime exception. The three data-flow components (profile chats, direct chats, agents networks) use it to decide whether to register a "files sent as they are" step.

#### `GChatServiceImpl` (modified) — src/main/java/ai/gebo/llms/chat/abstraction/layer/services/impl/GChatServiceImpl.java
The `ModelProviderCapabilities` it builds for a direct chat now uses the new constructor, passing `model.getInputModalities()` and `model.getEffectiveInputMimeTypes()`.

#### `GRagChatServiceImpl` (modified) — src/main/java/ai/gebo/llms/chat/abstraction/layer/services/impl/GRagChatServiceImpl.java
The same change as in `GChatServiceImpl`, for a chat with a profile.

#### `GChatSessionLifeCycleServiceImpl` (modified) — src/main/java/ai/gebo/llms/chat/abstraction/layer/services/impl/GChatSessionLifeCycleServiceImpl.java
In `budgetedResources`, before the request's resources are sized, the candidates are read with `chatAreaStorageSession.getSessionMedia(context.getCode())`. When there are some, the token budget for the text becomes `max(budget / 2, budget − estimate)`, where the estimate is the sum of `GModelFilesDelivery.estimatedTokens(mimeType)` over the candidates, and a debug line records the reduction. After the resources are built, `resources.setMedia(media)` attaches the candidates to the request.

#### `GChatStorageAreaServiceImpl` (modified) — src/main/java/ai/gebo/llms/chat/abstraction/layer/services/impl/GChatStorageAreaServiceImpl.java
In `addUploadedFile`, the kind of the file is derived from its MIME type with `GModelInputModality.ofMimeType`; an image, audio or video file is not given to the ingestion handler at all (`data` stays null). For a file with no reader, the decision to keep it now has two grounds: `forTheModel`, when `GModelCarriedMediaTypes.matchesAny(uploadFileTypes.modelMimeTypesOfSession(...), mimeType)` holds, and `forTheTools`, the earlier rule on the extension. The file is deleted and refused only when neither holds. The metadata merge of ingested documents was corrected: the handler's own metadata first, the common upload metadata over it.
In `addMedia`, the stored content type is now the whole type (`type/subtype`), and the file size is recorded on the `LLMGeneratedResource`.
New static `mimeTypeOf(UserUploadedContent)` returns the type the upload was sent with, or, when that is missing or `application/octet-stream`, the type its file name suggests. New static `mimeTypeOf(LLMGeneratedResource)` returns the stored type when whole, else the type suggested by its extension or name (older resources stored only the family, such as `image`).
New `getSessionMedia(userSessionCode)` collects, into a private record `MediaFile(name, mimeType, size, file, created)`, the uploads with no ingested JSON path and a relative file path, and the generated resources whose modality is image, audio or video. Any failure reading them is logged and yields an empty list. The candidates are sorted newest first; each is skipped when it has no MIME type or its file is missing; its size is read from disk when unknown; a candidate whose kind is switched off, or that `GModelFilesDelivery.Admission` does not admit within the limits of its kind, is skipped with a debug line saying it stays with the tools. Each kept candidate is returned as an anonymous `IChatRequestMedia` whose `readBytes()` reads the file only when called. The method returns an empty list at once while `GModelFilesDelivery.deliversAnything()` is false.

#### `GChatUploadFileTypesServiceImpl` (modified) — src/main/java/ai/gebo/llms/chat/abstraction/layer/services/impl/GChatUploadFileTypesServiceImpl.java
The constructor takes one more dependency, `ObjectProvider<IGChatModelRuntimeConfigurationDao> chatModels`. `acceptedFileTypes` now also computes the model's MIME types (from the session's profile and model code, or from the profile code given for a chat not yet created) and returns them as the third list. New `modelMimeTypesOfSession` looks up the session and delegates. The private `modelMimeTypes(chatProfileCode, chatModelCode)` resolves the model in the order profile's model reference, chat's model code, default model, and returns its `getEffectiveInputMimeTypes()` filtered by `GModelFilesDelivery.delivered(..)`, so only the kinds the installation sends are accepted for the model; a missing DAO or a runtime exception yields an empty list (the exception is logged as a warning). `chatFileTypes()` now skips the handlers whose identifier equals `ImageIngestionHandler.CODE` or `RecordingsAndVideosIngestionHandler.CODE`, so the media readers of the knowledge-base ingestion do not declare their extensions as "read into the chat".

#### `GStandardChatPipelineDataFlowComponent` (modified) — src/main/java/ai/gebo/llms/chat/abstraction/layer/services/impl/GStandardChatPipelineDataFlowComponent.java
For each chat profile, after the "answer" step towards the responder model, a "files" step is registered when `DataFlowEndpoints.sentFileTypes(responderChatModel)` is not empty; its description names the types. A new package-visible method `addDirectChat(flow, chatModelsDao)`, called at the end of the flow's construction, registers the chat with a model directly: one input endpoint `direct-chat-query` (created on the first reachable model), then for every configured chat model a "direct-answer" step and, for models that take files, a "direct-files" step. A null DAO, an unreadable or empty list of models leaves the flow unchanged.

#### `DefaultImageGenerationStreamingOutputChatPipelineServiceImpl` (modified) — src/main/java/ai/gebo/llms/chat/pipelines/service/defaultsteps/impl/DefaultImageGenerationStreamingOutputChatPipelineServiceImpl.java
The request context used for the service call that writes the image prompt is now created with `.withoutMedia()`, so the conversation's files are not sent on that call.

#### `DefaultRoutingChatPipelineStepServiceImpl` (modified) — src/main/java/ai/gebo/llms/chat/pipelines/service/defaultsteps/impl/DefaultRoutingChatPipelineStepServiceImpl.java
The same `.withoutMedia()` change in two places: the query rewriting call and the routing decision call.

#### `GStandardChatPipelineDataFlowComponentTest` (modified) — src/test/java/ai/gebo/llms/chat/abstraction/layer/services/impl/GStandardChatPipelineDataFlowComponentTest.java
One test was added, `theChatWithAModelDirectlyReachesEveryConfiguredModelWithTheFilesItTakes`. With two configured models, one of which answers `image/*` to `getEffectiveInputMimeTypes()`, it asserts that `direct-chat-query` reaches both models, that exactly one `direct-files-` transformation exists, that it points at the model taking files, and that its description ends with `(image/*)`.

## gebo.architecture.agents.standard

### What it does now (functional view)

Agents with a working directory have a new tool, `viewFile` ("Look at a picture or a file"). An agent calls it with a path, and optionally with the name of another files location of the conversation such as the uploads or the generated files, to have the file shown to its model in the next message instead of knowing it only by name. The tool answers in words whether the file will be seen: it is when the model answering takes files of that type; otherwise the agent is told why and directed to the tools that read the file. A file larger than 20 MB is not shown.

The files a run leaves in a conversation can now be collected with their bytes by callers that cannot fetch them from Gebo. This is used by the A2A server and the MCP server (see those modules). It covers what the run's models generated and what its tools wrote, with a limit of 20 MB per file and 40 MB per answer.

The compliance data-flow register of the agents networks now says, for each member whose chat model takes files, that conversation files and the files its tools show are sent to that model as they are, naming the types.

### Class by class

#### `ChatRunFiles` (new) — src/main/java/ai/gebo/llms/agent/standard/services/ChatRunFiles.java
A `@Service` that reads the files of one run from a `GeboChatResponse`. Constants: `MAX_FILE_BYTES` (20 MB) and `MAX_FILES_BYTES` (40 MB). Record `RunFile(name, mimeType, bytes)`, whose type is never null. Two optional dependencies, each with a setter for tests: `IGChatStorageAreaService storageArea` and `IGAILibraryFilesService libraryFiles`.
`of(response)` returns an empty list for a null response, then calls `generated` and `written`. `generated` walks `response.getGeneratedResources()`: a resource whose recorded size exceeds the per-file limit is skipped with a warning; otherwise its content is streamed from the storage area, its type defaults to `application/octet-stream` when not whole, its name defaults to its code, and it is skipped when it would exceed either limit. `written` walks `response.getWrittenFiles()` (the `GConversationFile` entries of the files the run's tools wrote): it skips a file whose name was already added (a file written to the generated-files location and already present as a generated one comes once), resolves its location with `libraryFiles.locationOf(chatCode, location)`, checks the described size and then the read size against both limits, and takes the content type from the stream, else from the description, else `application/octet-stream`. Every failure is logged and the file left out.

#### `GAgentsNetworkDataFlowComponent` (modified) — src/main/java/ai/gebo/llms/agent/standard/services/GAgentsNetworkDataFlowComponent.java
For each member that calls a chat model, after the existing "agent-model" step, an "agent-files" step is registered when `DataFlowEndpoints.sentFileTypes(agentChatModel)` is not empty. Its description reads "Agent <member>: conversation files, and files its tools show, sent to its chat model as they are (<types>)".

#### `WorkingDirectoryLibrarySource` (modified) — src/main/java/ai/gebo/llms/agent/standardlibraries/WorkingDirectoryLibrarySource.java
New constant `VIEW_FILE_TOOL = "viewFile"` with its description for the model, and `MAX_VIEWED_FILE_BYTES` (20 MB). A second constructor, marked `@Autowired`, takes an `ObjectProvider<IGAILibraryFilesService> conversationFiles`; the original one-argument constructor delegates with null. The declared type lists a seventh tool reference with input class `ViewParam` (`location`, optional; `path`). The tool callback list gains the `viewFile` callback.
The method `viewFile(folder, param, context)` requires a path. With an empty location (or the working directory's own type identifier) it resolves the path inside the working directory with the existing `inside` guard, requires a regular file, refuses a file over the size limit with a sentence, and reads it under the file lock. With another location it takes the chat session code from the tool context, asks `IGAILibraryFilesService.locationOf(session, location)`, and throws an `IllegalArgumentException` when the conversation has no such location or the file is not there; it applies the same size limit. It then calls `IGToolShownFiles.show(context, name, contentType, data)` and answers according to the result: true, the file "is shown to you in the next message"; false, the model does not take files of this type or the round already shows as many as it may; null, files cannot be shown in this call.

#### `GAgentsNetworkDataFlowComponentTest` (modified) — src/test/java/ai/gebo/llms/agent/standard/services/GAgentsNetworkDataFlowComponentTest.java
The mocked chat model became a field so a test can change its answers. New test `anAgentWhoseModelTakesFilesIsReportedSendingThemAsTheyAre` asserts that no `agent-files-` transformation exists for a text-only model, and that with `image/*` and `application/pdf` one exists, points at the agent's model, is of kind `PROCESSED`, and ends with `as they are (image/*, application/pdf)`.

#### `WorkingDirectoryLibrarySourceTest` (modified) — src/test/java/ai/gebo/llms/agent/standardlibraries/WorkingDirectoryLibrarySourceTest.java
The list of declared tools expected by the existing test now ends with `viewFile`. New test `aPictureOfTheWorkingDirectoryIsShownToAModelThatTakesPictures` writes `charts/sales.png` and asserts: with a viewer that accepts, the viewer received `sales.png image/png 3` and the answer says the file is shown in the next message; with a viewer that refuses, and with no viewer in the context, the answer says it cannot be shown; a path leaving the working directory throws `IllegalArgumentException`.

## gebo.architecture.mcp-clients

### What it does now (functional view)

When a tool of a remote MCP server returns something other than text, it is no longer flattened to a placeholder. A picture, a recording or a binary file in the result is kept in the conversation the tool was called for, among the conversation's generated files, and the model reads where it is (location, name, type, size). The user finds it among the conversation's files, the file tools can open it, and it is reported among the files the request wrote. If the model answering takes files of that type, the file is also shown to it in the next message, and the text says so. Outside a conversation, or where no store exists, the model is told the kind and type only, as before. Text resources embedded in a result are read as text, resource links are named with their address, and a result that has structured content but no text is given to the model as JSON. There is no new setting.

### Class by class

#### `MCPToolsExporterImpl` (modified) — src/main/java/ai/gebo/architecture/mcpclients/service/impl/MCPToolsExporterImpl.java
A new final field, `ObjectProvider<IGConversationGeneratedFilesStore> generatedFiles`, is injected (the class uses a generated constructor) and passed to every `McpRemoteToolCallback` it creates.

#### `McpRemoteToolCallback` (modified) — src/main/java/ai/gebo/architecture/mcpclients/service/impl/McpRemoteToolCallback.java
A second constructor accepts the `generatedFiles` provider; the earlier one delegates with null. The call now passes the tool context to `renderResult(result, toolContext)`, which became package-visible. `renderResult` handles, besides `TextContent`: `ImageContent` and `AudioContent` (kept through `keep`), an `EmbeddedResource` holding `TextResourceContents` (appended as text), an `EmbeddedResource` holding `BlobResourceContents` (kept as a file, named from its address), and `ResourceLink` (rendered as `[resource name uri (type)]`). When no text was found and `structuredContent()` is present, it is serialised to JSON. The `ERROR:` prefix for failed calls is unchanged.
The private `keep(kind, uri, mimeType, base64, position, toolContext)` decodes the base64 data (a failure is logged and the placeholder returned), names the file from the last segment of the address or as `<tool name>-<kind>-<position>`, calls `IGToolShownFiles.show` so a model that takes the type sees it after this round, and, when a chat session code and a store are available, stores it with `IGConversationGeneratedFilesStore.store` and reports it with `IGConversationFilesKeeper.report`. The returned text is `[<kind> kept in this conversation as <location>:<path> (<type>, <size> bytes)]`, with `; it is shown to you in the next message` when shown.

#### `McpRemoteToolResultFilesTest` (new) — src/test/java/ai/gebo/architecture/mcpclients/service/impl/McpRemoteToolResultFilesTest.java
Four tests on `renderResult` with a recording store. The first asserts the exact rendered text for a result with text, a picture and an embedded PDF, that both files were stored for conversation `chat-1` with the right bytes, and that the keeper lists `generated:srv_chart-image-1.png` and `generated:q3.pdf`. The second asserts that without a tool context, or without a store, a picture is rendered `[image image/png content]` and nothing is stored. The third asserts that structured content alone is rendered as `{"total":42}`, that text wins when both are present, and that a failed call is prefixed `ERROR:`. The fourth asserts that a viewer accepting only images is offered both files and that only the picture's text says it is shown in the next message.

## gebo.architecture.mcp-server

### What it does now (functional view)

An agents network exported as a tool of a Gebo MCP server can now be given a chat profile. With one, each call of the tool runs the network in a new conversation of the caller, in the environment of that profile, exactly as a chat with that profile would. The tool then answers with the text and with the files the run left: a picture as image content, a recording as audio content, any other file as an embedded resource with the address `gebo://conversation/<conversation code>/<file name>`, bytes included. A run that stops to wait for an answer says so in its text, with its questions and the conversation code. Without a chat profile the export behaves as before: the network runs outside any conversation and answers with text only. The files follow the limits of `ChatRunFiles` (20 MB per file, 40 MB per answer).

### Class by class

#### `GeboMCPAgentsNetworkTool` (modified) — src/main/java/ai/gebo/architecture/mcpserver/model/GeboMCPAgentsNetworkTool.java
The export of a network as a tool gained the optional field `chatProfileCode`, null by default, with the behaviour described above in its documentation.

#### `GeboMCPAgentsNetworkAsToolsProvider` (modified) — src/main/java/ai/gebo/architecture/mcpserver/runtime/GeboMCPAgentsNetworkAsToolsProvider.java
The class annotation changed from `@AllArgsConstructor` to `@lombok.RequiredArgsConstructor`, because two optional, non-final fields were added and must not enter the constructor: `GChatEnvironmentNetworkRunner chatRunner` and `ChatRunFiles runFiles`, both `@Autowired(required = false)` with package-visible setters. New constant `FRAMES_COLLECTION_TIMEOUT` of ten minutes.
`buildTools(config)` no longer converts all callbacks in one call. It first maps the exports that name a chat profile (and can run in a conversation, see `runsInAConversation`) by tool name. For each callback it builds the plain specification; for a tool in that map it builds instead a `SyncToolSpecification` with the same tool definition and a call handler that runs, as the caller (`securitySupport.runAs` with the exchange's transport context), `callInAConversation`. `toolNameOf` gives the export's tool name or, when blank, the network code; `requestTextOf` reads the `request` argument.
`callInAConversation(config, export, toolName, text)` runs the network with `chatRunner.run(networkCode, chatRunner.requestOf(text, chatProfileCode, null), ChatRunAttachments.none(), toolCallingOf(config, toolName), GTextAgentsNetworkRunner.LOGGING_SINK)`, collects the frames (waiting up to the timeout), takes the last `GeboChatResponse` carried by a frame or else `run.getResponse()`, and uses the response's text or, when blank, the concatenated streamed chunks. A suspended run appends the rendered suspension. The files of `runFiles.of(response)` are added as `ImageContent`, `AudioContent` or `EmbeddedResource` with `BlobResourceContents`. A failure returns a result flagged as an error with the text "The network could not answer: …". The run is disposed in every case.

#### `GeboMCPAgentToolsToolCallingTest` (modified) — src/test/java/ai/gebo/architecture/mcpserver/runtime/GeboMCPAgentToolsToolCallingTest.java
New test `aNetworkWithAChatProfileAnswersWithTheFilesOfItsRun`. With a mocked conversation runner and a mocked `ChatRunFiles` returning a PNG and a Word document, it asserts that the tool is named `askSales`, that the result has three contents (the text "Here is the chart.", an image content of type `image/png` with the expected base64, an embedded resource at `gebo://conversation/chat-1/report.docx`), that the run was disposed, and that the text runner was not called. It also asserts that without the conversation runner the tool is still built, as the plain one.

## gebo.architecture.a2a-server

### What it does now (functional view)

A network or agent exported over A2A now answers with files. The files the run's models generated and the files its tools wrote travel in the answer as file parts with their bytes, within the limits of `ChatRunFiles`. The agent card says so: the output modes are `text/plain`, `image/png` and `application/octet-stream`, and an exported network declares that it takes `text/plain`, `image/*`, `audio/*`, `video/*` and `application/pdf` as input (an exported single agent still declares text input).

A caller is now told what it sent that was not taken in (for example an attachment named by a link that was not fetched): the list stands in the run's account of the answer under `notTaken`, on both the plain and the streamed answer.

An attachment named by a link, instead of sent with its bytes, can now be fetched, but only from hosts the administrator listed in `ai.gebo.a2a.server.linked-files-hosts`. The list is empty by default, so by default no link is called. The fetch is a plain GET without any credential of the installation, does not follow redirects, and refuses a file over 20 MB.

### Class by class

#### `A2AAgentCardBuilder` (modified) — src/main/java/ai/gebo/architecture/a2aserver/runtime/A2AAgentCardBuilder.java
Two constants were added: `NETWORK_INPUT_MODES` (`text/plain`, `image/*`, `audio/*`, `video/*`, `application/pdf`) and `OUTPUT_MODES` (`text/plain`, `image/png`, `application/octet-stream`). The card's default output modes, and the output modes of both kinds of skill, are now `OUTPUT_MODES`. A network skill's input modes are `NETWORK_INPUT_MODES`; an agent skill's input modes and the card's default input modes stay `TEXT_MODES`.

#### `A2AInboundResources` (modified) — src/main/java/ai/gebo/architecture/a2aserver/runtime/A2AInboundResources.java
A second factory `of(parts, linkedFiles)` was added; the earlier `of(parts)` delegates with null. `readFile` receives the fetcher. For a `FileWithUri` whose link the fetcher accepts (`linkedFiles.fetches(uri)`), the file is fetched and added as an in-memory multipart file, named by the part's name or the last path segment of the link or `a2a-attachment`, typed by the part's MIME type or the server's content type; a failed fetch is logged and added to `notTaken` with the reason. Any other link keeps the earlier behaviour: not fetched and reported in `notTaken`.

#### `A2ALinkedFilesFetcher` (new) — src/main/java/ai/gebo/architecture/a2aserver/runtime/A2ALinkedFilesFetcher.java
A `@Service` whose constructor reads `ai.gebo.a2a.server.linked-files-hosts` (empty by default) into a list of trimmed, lower-case host names. `MAX_FILE_BYTES` is 20 MB. `fetches(link)` is true only for an `http` or `https` link whose host is in the list; an unparsable link is false. `fetch(link)` returns null for an unlisted host; otherwise it sends a GET with an HTTP/1.1 client that never follows redirects (20 seconds to connect, 60 seconds for the request), throws on a non-2xx status or on more than the size limit, and returns the record `Fetched(bytes, contentType)` with the content type stripped of parameters.

#### `A2AResultAdapter` (modified) — src/main/java/ai/gebo/architecture/a2aserver/runtime/A2AResultAdapter.java
New overload `messageOf(response, text, contextId, notTaken)`; the three-argument one delegates with null. With a response, the run-information data part now includes `notTaken` when not empty, and the file parts of `filesOf(response)` are appended. Without a response but with something not taken, a data part holding only the kind and `notTaken` is added. `filesOf` turns each `ChatRunFiles.RunFile` into a `FilePart` with `FileWithBytes`. The adapter holds a `ChatRunFiles` (`@Autowired(required = false)`, a bare instance by default) and exposes `setStorageArea` and `setLibraryFiles` that configure it for tests. `MAX_FILE_BYTES` mirrors the constant of `ChatRunFiles`. `runInfoOf` takes the `notTaken` list.

#### `A2AServerBuilder` (modified) — src/main/java/ai/gebo/architecture/a2aserver/runtime/A2AServerBuilder.java
A new final field `A2ALinkedFilesFetcher linkedFiles` is injected and passed to `A2AInboundResources.of`. `received.getNotTaken()` is passed to `resultAdapter.messageOf` on the plain answer and, through a new parameter of `streamFrames`, on every streamed message including the one sent when a run produced no frame. An unused private method `renderParts` was removed.

#### `A2AInboundResourcesTest` (modified) — src/test/java/ai/gebo/architecture/a2aserver/runtime/A2AInboundResourcesTest.java
New test `aLinkedFileIsFetchedOnlyFromAListedHost` starts a local HTTP server and asserts that with `127.0.0.1` listed the linked file is taken (name `chart.png`, type `image/png`, the three bytes, nothing in `notTaken`), and that with no host listed no file is taken and `notTaken` has one entry.

#### `A2AResultAdapterTest` (modified) — src/test/java/ai/gebo/architecture/a2aserver/runtime/A2AResultAdapterTest.java
Three tests were added. `thePicturesARunGeneratedTravelAsFileParts` asserts one file part with type, name and base64 bytes of a generated picture, and none for an adapter without storage. `aFileTheToolsWroteTravelsWithTheAnswer` asserts that a written Word file is sent under its bare file name with its type and bytes, while a written file in a location the conversation no longer holds is left out. `whatWasNotTakenIsToldToTheCaller` asserts that `notTaken` appears in a data part with and without a response, and is absent when everything was taken.

## gebo.architecture.a2a-clients

### What it does now (functional view)

When a network calls a remote A2A agent, the conversation's files now travel with the message when the remote agent says it takes them. The remote agent's card is read for its default input modes and those of its skills; the files of the request (pictures, PDF files, recordings) whose type matches one of those modes, by exact type, by family (`image/*`) or by `*/*`, are sent as file parts with their bytes, up to 20 MB per file and 40 MB per message. An agent declaring text only receives no file.

In the other direction, a file the remote agent sends back is no longer dropped. A file sent with its bytes is kept in the conversation among its generated files and named in the text the network reads. A file named by an address is fetched and kept the same way only when the address is the agent's own (same scheme, host and port as its card or as the endpoint its calls go to), with the credentials the agent is called with, without following redirects and up to 20 MB; a file at any other address is named with its address and not fetched. There is no new setting.

### Class by class

#### `A2AOutboundFilesConfig` (new) — src/main/java/ai/gebo/architecture/a2aclients/service/impl/A2AOutboundFilesConfig.java
A `@Configuration` with three `@Value` fields: `ai.gebo.a2a.clients.send-files-enabled` (false), `ai.gebo.a2a.clients.send-max-file-mb` (2) and `ai.gebo.a2a.clients.send-max-total-mb` (4). Its `@PostConstruct` method `apply()` hands them to `RemoteA2ANetworkAgentService.setOutboundFiles(..)` and logs them.

#### `A2AClientConnector` (modified) — src/main/java/ai/gebo/architecture/a2aclients/service/impl/A2AClientConnector.java
New record `OutboundFile(name, mimeType, bytes)`. New overload `sendMessage(config, text, contextId, files)`; the three-argument one delegates with null. `buildParams` builds the message from a list of parts: the text part, then a `FilePart` with `FileWithBytes` for each non-empty outbound file (type defaulting to `application/octet-stream`).
`acceptedInputModes(config)` fetches the agent card and returns, lower case, its `defaultInputModes()` and each skill's `inputModes()`; the answer is cached per card address for the endpoint cache's time to live, and an unreadable card yields an empty set with a warning. The static `takes(inputModes, mimeType)` strips parameters from the type and matches it exactly, by family or against `*/*`. `invalidateEndpointCache` now also clears the cached modes.
`MAX_LINKED_FILE_BYTES` is 20 MB. Record `LinkedFile(bytes, contentType)`. `isOwnAddress(config, uri)` is true only for an `http` or `https` address whose origin equals that of the card address or of the resolved RPC endpoint. `fetchLinkedFile(config, uri)` returns null for an address that is not the agent's own; otherwise it sends a GET with the agent's authorization, throws on a non-2xx status, on a declared length over the limit or on a body over the limit, and returns the bytes with the content type stripped of parameters.

#### `RemoteA2ANetworkAgentService` (modified) — src/main/java/ai/gebo/architecture/a2aclients/service/impl/RemoteA2ANetworkAgentService.java
The call now sends `filesFor(chatRequestContext)` and reads the answer with `extractText(result, chatRequestContext)`, run as the user the network works for when `runAs` is set, so that a kept file belongs to that user's conversation. Whether files are sent at all, the largest file and the total of one message are static settings applied by `A2AOutboundFilesConfig` through `setOutboundFiles(..)`: off, 2 MB and 4 MB until the installation says otherwise.
`filesFor(context)` returns nothing while sending is off. Otherwise: `filesFor(context)` takes `context.getMedia()`, asks the connector for the remote agent's accepted modes, and for each file that the agent takes and that fits the limits reads its bytes into an `OutboundFile`; unreadable or oversized files are logged and left out.
`extractText` and `renderParts` take the request context. `renderParts` now handles `FilePart` with `FileWithBytes` (through `keep`) and with `FileWithUri` (through `fetchAndKeep`); other part kinds are logged at debug level as not read. `keep(FileWithBytes, context)` decodes the base64 bytes, falling back to a description `[file name (type)]`. `fetchAndKeep` calls `connector.fetchLinkedFile`; a null answer means the address is not the agent's own and the description with the address is returned. The final `keep(name, mimeType, data, described, context)` obtains `IGConversationGeneratedFilesStore` through the runtime binder, stores the file in the session of the request context, reports it to the `IGConversationFilesKeeper` of the tools context, and returns `[file kept in this conversation as <location>:<path> (<type>, <size> bytes)]`; any failure returns the description.

#### `A2AClientConnectorIntegrationTest` (modified) — src/test/java/ai/gebo/architecture/a2aclients/service/impl/A2AClientConnectorIntegrationTest.java
The local test server gained a `/files/chart.png` route. Test `sendsTheFilesTheAgentTakes` asserts that the test card yields the modes `text/plain`, checks `takes` for a family match with parameters, for `*/*` and for a refusal, and asserts that a sent file arrives as one file part with name, type and base64 bytes beside the text. Test `fetchesAFileOnlyFromTheAgentsOwnAddress` asserts that a file at the agent's own address is fetched (four bytes, type `image/png`), and that another host, another port of the same host and a `file:` address all return null.

## gebo.ragsystem.client.rest

### What it does now (functional view)

A user can upload files as the very first action in a new chat. Two new endpoints create the chat and upload the files to it in one request: one for a chat with a model, one for a chat with a chat profile. The answer carries both the uploads and the new chat, so the user interface can continue in it. A chat that received no file is kept.

### Class by class

#### `GeboUserChatUploadsController` (modified) — src/main/java/ai/gebo/llms/chat/client/rest/controllers/GeboUserChatUploadsController.java
A new injected field `IGChatSessionLifeCycleService sessionLifeCycle`. A nested data class `UploadsWithNewChat` with `uploads` (`List<UserUploadedContent>`) and `chatInfo` (`GUserChatInfo`). Two multipart `POST` endpoints: `chatSessionCreateWithUpload/{chatModelCode}`, which creates the chat with `createCleanChatByModelCode`, and `ragChatSessionCreateWithUpload/{chatProfileCode}`, which creates it with `createCleanChatByChatProfileCode`. Both delegate to the private `uploadedToNewChat`, which calls the existing `uploadsHandler.chatSessionUpload` on the new chat's code and returns an `OperationStatus<UploadsWithNewChat>` carrying the upload's messages.

---

# Part C — Knowledge base media, application, user interface, generated code, Pro

## gebo.system.ingestion

Path: `gebo.architecture.parent/gebo.system.ingestion`

### What it does now (functional view)

The ingestion readers gain two new document formats and an optional ability to carry the media of a document forward. No reader calls a model.

**New formats, always on.** PowerPoint presentations (`.ppt`, `.pptx`) and Rich Text Format documents (`.rtf`) are now read. A presentation gives one text per slide that has text, in slide order, with the slide number stored where a PDF page stores its page number; the slide's title comes first, then its text boxes and tables, then its speaker notes after a "Speaker notes:" line. An RTF document gives one text for the whole document, with title, subject and author taken from its information group.

**Images, off by default.** With `ai.gebo.images.ingestion-enabled: true` the readers also hand over each image as a document of its own, holding the image bytes as media and carrying the same metadata as the texts of its document, plus the page or slide number when known and the picture's name. The sources are: image files (PNG, JPEG, GIF, WebP), pictures inside Word (`.doc`, `.docx`), PowerPoint, Excel, OpenDocument text/presentation/spreadsheet files, pictures written inside a web page as `data:` URIs, and PDF pages that have no text (each rendered to a PNG). With the switch off everything behaves as before: no picture is collected, and an image file is a file no reader takes.

The limits of image ingestion are settings under `ai.gebo.images`:

| Property | Default | Meaning |
|---|---|---|
| `ai.gebo.images.ingestion-enabled` | `false` | The readers give images as media documents. |
| `ai.gebo.images.max-images-per-document` | `30` | Images taken of one document at most; the rest are passed over and a warning is logged. |
| `ai.gebo.images.min-image-bytes` | `2048` | A smaller image is taken for a decoration and passed over. |
| `ai.gebo.images.max-image-mb` | `20` | A larger image is passed over. |

Only PNG, JPEG, GIF and WebP pictures are taken (vector drawings such as EMF, WMF and SVG are passed over), and a picture repeated in the same document (a logo on every page) is taken once.

**Recordings and videos, each off by default.** `ai.gebo.recordings.ingestion-enabled` and `ai.gebo.videos.ingestion-enabled` (both `false` by default) make a recording file (`.mp3`, `.mpga`, `.wav`, `.m4a`) or a video file (`.mp4`, `.mpeg`, `.webm`) be read as one media document holding the whole file. `ai.gebo.recordings.max-recording-mb` and `ai.gebo.videos.max-video-mb` (both `20`) are the size limits; a larger or empty file is not taken and a warning is logged.

**Who sees the media.** Code that reads the ingestion result through the existing `getStream()` keeps receiving texts only; the media documents are visible only through the new `getStreamWithMedia()`, which the chunking uses.

### Class by class

#### `EmbeddedPicture` (new) — `src/main/java/ai/gebo/system/ingestion/EmbeddedPicture.java`
A record `(String name, String mimeType, byte[] data, Integer page)` for a picture found inside a document being read; `page` is the page or slide number from 1, or null. A three-argument constructor leaves `page` null. `readableType()` returns the media type when the picture is one a model reads as a picture, else null: it takes the declared type, falls back to the name's extension (`.png`, `.jpg`/`.jpeg`, `.gif`, `.webp`) when the declared type is missing or not `image/*`, normalises `image/jpg` and `image/pjpeg` to `image/jpeg` and `image/x-png` to `image/png`, and accepts only `image/png`, `image/jpeg`, `image/gif` and `image/webp`.

#### `IGDocumentReferenceIngestionHandler` (modified) — `src/main/java/ai/gebo/system/ingestion/IGDocumentReferenceIngestionHandler.java`
In the nested `IngestionHandlerData`, `getStream()` now returns the stream filtered with `Document::isText` (null when there is no stream), so existing callers get texts only. The new `getStreamWithMedia()` returns the whole stream, texts and media documents, for the caller that passes media on (the chunking).

#### `ImagesIngestionConfig` (new) — `src/main/java/ai/gebo/system/ingestion/config/ImagesIngestionConfig.java`
A `@Configuration` / `@ConfigurationProperties(prefix = "ai.gebo.images")` bean with `ingestionEnabled` (false), `maxImagesPerDocument` (30), `minImageBytes` (2048) and `maxImageMb` (20). `getMaxImageBytes()` converts the megabytes to bytes (never negative). The constant `IMAGE_NAME_METADATA = "gebo_image_name"` is the metadata key under which an image document carries the picture's name inside its document.

#### `GAbstractConfiguredHandler` (modified) — `src/main/java/ai/gebo/system/ingestion/impl/GAbstractConfiguredHandler.java`
The base class of the readers gains the shared image logic. A field `imagesConfig` is injected with `@Autowired(required = false)` (absent in a unit test) and can be set with `setImagesConfig(..)`. `ingestsImages()` is true when the configuration is present and enabled. `imageDocuments(reference, pictures, metadata)` turns a list of `EmbeddedPicture` into Spring AI `Document`s holding the image as `Media` (media name `"image"`): it returns an empty list when images are not taken; passes over a picture whose `readableType()` is null, whose bytes are missing, smaller than `minImageBytes` or larger than the maximum, or that was already seen in this document (identity is the length plus `Arrays.hashCode` of the bytes); stops taking pictures after `maxImagesPerDocument` and logs a warning with the number passed over. Each image document gets a copy of the given metadata, plus `DocumentMetaInfos.CONTENT_PAGE` when the picture has a page and `IMAGE_NAME_METADATA` when it has a name.

#### `ImageIngestionHandler` (new) — `src/main/java/ai/gebo/system/ingestion/media/impl/ImageIngestionHandler.java`
A `@Service` reader with code `ImageIngestionHandler` for image files. While images are not taken it declares nothing: `getHandledExtensions()` and `getHandledContentTypes()` return empty lists and both `canManageContent(..)` overloads return false, so an image file stays a file no reader takes. `isAutoTokenizeCapable()` is false. `handleContent(..)` enriches the metadata, reads all bytes, wraps them in one `EmbeddedPicture` named `"file" + extension` with the reference's content type, passes it through `imageDocuments(..)` (so the type and size limits apply) and removes `IMAGE_NAME_METADATA` from the result, because the file is the image and has no name inside a document. A `RuntimeException` becomes a `GeboIngestionException`; the stream is closed in every case.

#### `RecordingsAndVideosIngestionHandler` (new) — `src/main/java/ai/gebo/system/ingestion/media/impl/RecordingsAndVideosIngestionHandler.java`
A `@Service` reader with code `RecordingsAndVideosIngestionHandler`. Four `@Value` fields hold `ai.gebo.recordings.ingestion-enabled` (false), `ai.gebo.videos.ingestion-enabled` (false), `ai.gebo.recordings.max-recording-mb` (20) and `ai.gebo.videos.max-video-mb` (20); `setSwitches(recordingsEnabled, videosEnabled)` sets the two switches (used by the test). `mediaType(contentType, extension)` gives the media type from the declared `audio/*` or `video/*` type (parameters after `;` dropped), else from a fixed extension map (`.mp3`, `.mpga` → `audio/mpeg`; `.wav` → `audio/wav`; `.m4a` → `audio/mp4`; `.mp4` → `video/mp4`; `.mpeg` → `video/mpeg`; `.webm` → `video/webm`). `taken(..)` tells whether the kind of an extension or type is switched on. `getHandledExtensions()` and `getHandledContentTypes()` list only the entries whose kind is on, and both `canManageContent(..)` overloads require the kind to be on. `handleContent(..)` reads at most the limit plus one byte, gives nothing (with a warning) for an empty or oversized file, and otherwise returns one `Document` holding the file as `Media` (name `"media"`) with a copy of the metadata.

#### `MSPowerPointIngestionHandler` (new) — `src/main/java/ai/gebo/system/ingestion/msformats/impl/MSPowerPointIngestionHandler.java`
A `@Service` reader with code `MSPowerPointIngestionHandler` for `.ppt` (POI `HSLFSlideShow`) and `.pptx` (POI `XMLSlideShow`). `textOf(slide)` builds a slide's text: title shapes first (`isTitle` looks at the text placeholder, the placeholder, and for `.ppt` the run type of the first paragraph), then the other shapes in order through `addShapes` (text shapes, tables through `addTable` as one line per row with cells joined by `" | "`, groups recursively), then the speaker notes after the `NOTES_MARKER` line `"Speaker notes:"`. Slide number, date, header and footer placeholders are skipped (`isBoilerplate`). `slidesOf(..)` returns one `Document` per slide with text, with `DocumentMetaInfos.CONTENT_PAGE` set to the slide number, and, when a picture list is given, collects the slide's pictures with `picturesOf(..)` (picture shapes and those inside groups, each with the slide number; an unreadable picture is skipped). `enrichPptxMetadata` and `enrichPptMetadata` take title, subject and author from the file properties, and `enrichFromFirstSlide` uses the first slide's title when the properties give none. `isPptx` / `isPpt` decide by extension, falling back to the content type when the extension is missing. `handleContent(..)` concatenates the slide texts with `imageDocuments(..)` of the collected pictures (the list is created only when `ingestsImages()` is true) and wraps POI's unchecked exceptions in `GeboIngestionException`.

#### `MSWordIngestionHandler` (modified) — `src/main/java/ai/gebo/system/ingestion/msformats/impl/MSWordIngestionHandler.java`
`streamDOCXContent` and `streamDOCContent` take an extra `pictures` list. When it is not null, the DOCX path adds every `XWPFPictureData` of `document.getAllPictures()` (file name, package part content type, data) and the DOC path adds every `Picture` of the pictures table (`suggestFullFileName()`, `getMimeType()`, `getContent()`). `handleContent(..)` creates the list only when `ingestsImages()` is true and returns the text stream concatenated with `imageDocuments(..)`.

#### `MsExcellIngestionHandler` (modified) — `src/main/java/ai/gebo/system/ingestion/msformats/impl/MsExcellIngestionHandler.java`
`readExcelTables(..)` takes an extra `pictures` list and, when it is not null, adds every picture of `workbook.getAllPictures()` (no name, its MIME type, its data); a `RuntimeException` there is ignored so the sheets are read all the same. `handleContent(..)` appends `imageDocuments(..)` to the stream of table documents.

#### `RtfIngestionHandler` (new) — `src/main/java/ai/gebo/system/ingestion/msformats/impl/RtfIngestionHandler.java`
A `@Service` reader with code `RtfIngestionHandler`. `handleContent(..)` parses the stream with Apache Tika's `RTFParser` into a `BodyContentHandler(-1)` (no character limit), copies title, subject and author from the Tika metadata through `enrichRtfMetadata`, and returns one `Document` with the trimmed text, or none when the text is blank. Tika, SAX and runtime exceptions become a `GeboIngestionException`. Pictures are not read by this handler.

#### `GOdpIngestionHandlerImpl` (modified) — `src/main/java/ai/gebo/system/ingestion/odformats/impl/GOdpIngestionHandlerImpl.java`
`streamContent(..)` takes a `pictures` list and calls `OdfPictures.collect(document, pictures)` after loading the presentation; `handleContent(..)` creates the list when `ingestsImages()` is true and appends `imageDocuments(..)`.

#### `GOdsIngestionHandlerImpl` (modified) — `src/main/java/ai/gebo/system/ingestion/odformats/impl/GOdsIngestionHandlerImpl.java`
`readTables(..)` takes a `pictures` list and calls `OdfPictures.collect(..)` on the loaded spreadsheet; `handleContent(..)` appends `imageDocuments(..)` after the table documents.

#### `GOdtIngestionHandlerImpl` (modified) — `src/main/java/ai/gebo/system/ingestion/odformats/impl/GOdtIngestionHandlerImpl.java`
After loading the text document the handler collects its pictures with `OdfPictures.collect(..)` (list created only when `ingestsImages()` is true) and adds `imageDocuments(..)` to the documents it returns.

#### `OdfPictures` (new) — `src/main/java/ai/gebo/system/ingestion/odformats/impl/OdfPictures.java`
A package-private helper. `collect(document, pictures)` does nothing when the list or the document is null; otherwise it walks the package's file paths in sorted order, takes each entry under `Pictures/` that is not a folder, and adds an `EmbeddedPicture` with the entry's name (without the folder), the package's media type string and the bytes. A `RuntimeException` is swallowed so the text is read all the same.

#### `PdfIngestionHandler` (modified) — `src/main/java/ai/gebo/system/ingestion/pdf/impl/PdfIngestionHandler.java`
Two constants are added: `PAGE_AS_IMAGE_DPI = 110f` and `MAX_RENDERED_PAGES = 200`. While images are taken, `handleContent(..)` reads the whole PDF into memory and opens the existing `PdfReader` on the bytes; for each page whose extracted text is null or blank (up to 200 pages) it loads the PDF once with PDFBox (`Loader.loadPDF`), renders the page with `PDFRenderer.renderImageWithDPI` at 110 DPI, encodes it as PNG and records an `EmbeddedPicture` with the page number. A page that cannot be rendered is logged as a warning. The rendered pages go through `imageDocuments(..)` (so the per-document limit applies after) and are appended to the page texts; the PDFBox document is closed in the `finally` block. With images not taken the handler reads the stream as before.

#### `HtmlIngestionHandler` (modified) — `src/main/java/ai/gebo/system/ingestion/text/impl/HtmlIngestionHandler.java`
While images are taken, after adding the page's text the handler selects `img[src^=data:image/]`, keeps only `data:` URIs whose header ends with `;base64`, decodes them with the MIME Base64 decoder and records each as an `EmbeddedPicture` named by its `alt` text (null when blank) with the type from the header; an undecodable one is skipped. The results of `imageDocuments(..)` are appended. Pictures referenced by an address are not fetched.

#### `readers.yml` (modified) — `src/main/resources/bundle-readers/readers.yml`
Four reader entries are added to `ai.gebo.contents.reading.library`: `MSPowerPointIngestionHandler` (file type id `powerpoint`; `.ppt` with `application/vnd.ms-powerpoint`, `.pptx` with the presentationml content type; `treatAs: word`), `RtfIngestionHandler` (file type id `rtf`; `.rtf` with `application/rtf` and `text/rtf`; `treatAs: word`), `ImageIngestionHandler` (file type id `image`; PNG, JPEG, GIF, WebP content types and `.png`, `.jpg`, `.jpeg`, `.gif`, `.webp`; `treatAs: picture`) and `RecordingsAndVideosIngestionHandler` (file type id `recording`; one `treatAs: recording` type with `audio/mpeg`, `audio/mp3`, `audio/wav`, `audio/x-wav`, `audio/mp4` and `.mp3`, `.mpga`, `.wav`, `.m4a`; one `treatAs: video` type with `video/mp4`, `video/mpeg`, `video/webm` and `.mp4`, `.mpeg`, `.webm`). All are `uiViewable: false`. Comments state that the two media readers take files only while their switches are on.

#### `EmbeddedPicturesIngestionTest` (new) — `src/test/java/ai/gebo/system/ingestion/EmbeddedPicturesIngestionTest.java`
Unit tests of the image and media path, with a hand-built `ImagesIngestionConfig`. They assert that: a Word document with a repeated picture and a tiny one gives its text plus two image documents, each PNG with the bytes unchanged, the text's metadata and the image-name metadata; with images not taken (or no configuration at all) the document gives one document as before; no more images than `maxImagesPerDocument` are given; a picture on a slide carries its slide number in `CONTENT_PAGE`; an OpenDocument text gives its picture named `chart.png`; a web page gives the picture written inside it and not the one named by an address; an image file is refused with the switch off (no handled extensions) and read as one media document with the switch on; a recording and a video are each taken only by their own switch (`setSwitches(true, false)` gives `.mp3` only, as `audio/mpeg` with the bytes unchanged); `getStream()` gives one document where `getStreamWithMedia()` gives three; `readableType()` accepts JPEG and PNG by name or type and rejects EMF, SVG and unknown; and a workbook reports its picture's MIME type as `image/png`.

#### `MSPowerPointIngestionHandlerTest` (new) — `src/test/java/ai/gebo/system/ingestion/msformats/impl/MSPowerPointIngestionHandlerTest.java`
Asserts that a `.pptx` gives one document per slide with text, in order, with the expected text layout (title first, table rows joined by ` | `) and slide numbers `1` and `3` (the slide without text gives nothing), and with title and author from the properties; that without properties the first slide's title becomes the presentation title; that a `.pptx` without extension is read by its content type; that a `.ppt` gives the same shape of result; that a file that is not a presentation raises an exception; and that `readers.yml` declares the handler with its extensions and content types.

#### `RtfIngestionHandlerTest` (new) — `src/test/java/ai/gebo/system/ingestion/msformats/impl/RtfIngestionHandlerTest.java`
Asserts that an RTF document gives one document with its paragraphs in order, with accented characters and the euro sign decoded and no RTF control words or font names in the text, and with title, subtitle and author from its information group; that a document without information has no title and one without text gives no document; and that `readers.yml` declares `.rtf` with `application/rtf` and `text/rtf`.

---

## gebo.architecture.documents.cache

Path: `gebo.architecture.parent/gebo.architecture.documents.cache`

### What it does now (functional view)

The chunk model can now hold a whole media file as a chunk, beside text chunks. A chunk has one of four kinds — text, image, audio, video — and a media chunk carries the file's bytes (base64) and MIME type with the same metadata as the document's texts. The default chunking parameters used by ingestion ask for the media kinds that the installation switches turn on (`ai.gebo.images.ingestion-enabled`, `ai.gebo.recordings.ingestion-enabled`, `ai.gebo.videos.ingestion-enabled`, all `false` by default); with all three off the parameters are exactly the previous defaults.

### Class by class

#### `DocumentChunkType` (modified) — `src/main/java/ai/gebo/architecture/documents/cache/model/DocumentChunkType.java`
The enum grows from `TEXT, IMAGE` to `TEXT, IMAGE, AUDIO, VIDEO`. `isMedia()` is true for every kind but `TEXT`. The static `ofMediaMimeType(mimeType)` maps `image/*`, `audio/*` and `video/*` to their kind and returns null for anything else.

#### `DocumentChunk` (modified) — `src/main/java/ai/gebo/architecture/documents/cache/model/DocumentChunk.java`
The static factory `ofMedia(originalDocumentCode, mimeType, data, metaData)` builds a media chunk: a random id, the kind from `DocumentChunkType.ofMediaMimeType`, a copy of the metadata, the bytes base64-encoded into `chunkData`, and the MIME type; it returns null for an unknown type or null data. `holdsText()` is true for a `TEXT` chunk and for a chunk with no kind (one written before the kinds existed). `mediaBytes()` decodes the base64 data of a media chunk and returns null for a text chunk.

#### `ChunkingParametersProviderImpl` (modified) — `src/main/java/ai/gebo/architecture/documents/cache/service/ChunkingParametersProviderImpl.java`
Three `@Value` fields read the ingestion switches. `provideChunkingParams(reference)` returns `ChunkingParams.defaultParams` when all are off; otherwise it returns a new `ChunkingParams` equal to the defaults except that its chunking specs also contain an `AbstractChunkingSpecs` of kind `IMAGE`, `AUDIO` and/or `VIDEO` for each switch that is on. This is how the ingestion asks the chunking for media.

---

## gebo.architecture.documents.cache.impl

Path: `gebo.architecture.parent/gebo.architecture.documents.cache.impl`

### What it does now (functional view)

The chunking service returns the media of a document as they are, whole, each as a chunk of its kind in its place among the text chunks — but only for the kinds the caller asked for. It calls no model and does not split or alter the media. A request with text specs only gets text chunks only, as before. A sample of a document and a keyword-filtered chunking are always text only. A request for image chunks no longer fails with "The images chunking is not yet implemented".

### Class by class

#### `DocumentsChunkServiceImpl` (modified) — `src/main/java/ai/gebo/architecture/documents/cache/service/impl/DocumentsChunkServiceImpl.java`
The former check that threw `MethodNotFoundException` for an `IMAGE` spec is removed. A set `mediaAsked` is built from the chunking specs whose kind `isMedia()`, only when the call is not in sampling mode and the policy is null or `ChunkingPolicy.SPLIT_CHUNKS`. The service reads `content.getStreamWithMedia()` when media were asked and `content.getStream()` otherwise. For each non-text document in the stream it resolves the kind from the media's MIME type and skips it when that kind was not asked; otherwise it builds a chunk with `DocumentChunk.ofMedia(..)`, adds `mediaType:length:hash` of the bytes to the content hash (so a changed image changes the document), sets the next chunk position, the byte size, a token size of 0, the chunking session id and `DocumentMetaInfos.GEBO_BYTES_LENGTH`, updates the operation's totals, and appends the chunk to the current chunk set (creating one when needed). A chunk set holding media is written to the chunk cache directory and a new one begun once it exceeds the new constant `MEDIA_BYTES_PER_CHUNK_SET` (8 MB). An exception while taking a media is logged and recorded among the operation's exceptions.

#### `MediaChunksTest` (new) — `src/test/java/ai/gebo/architecture/documents/cache/service/impl/MediaChunksTest.java`
Builds the service with mocks and an ingestion result of text, image, text. It asserts that with text and image specs and `SPLIT_CHUNKS` three chunks come back, the middle one being an `IMAGE` chunk with MIME type `image/png`, the bytes unchanged through `mediaBytes()`, the text's metadata and page, position 2, token size 0 and a chunks count of 3; that with text specs only the chunks are the two texts at positions 1 and 2; and that with `ONLY_MATCHING_CHUNKS` every chunk holds text even when images were asked.

---

## gebo.ragsystem.content.vectorizator

Path: `gebo.ragsystem.parent/gebo.ragsystem.content.vectorizator`

### What it does now (functional view)

The vectorization step can turn the media chunks of a document into text with the models this service already has, and then embeds that text with the unchanged embedding code, so the resulting vector ids are recorded with the document's other vectors. An image is described by a chat model that reads pictures; a recording or a video is transcribed by the default transcription model. Each costs a model call at every vectorization of the document. Everything is off by default, and a media kind that is not read is dropped before its bytes travel further.

| Property | Default | Meaning |
|---|---|---|
| `ai.gebo.vectorizator.embed-images` | `false` | Describe images and embed the descriptions. |
| `ai.gebo.vectorizator.embed-recordings` | `false` | Transcribe recordings and embed the transcripts. |
| `ai.gebo.vectorizator.embed-videos` | `false` | Transcribe videos and embed the transcripts. |
| `ai.gebo.vectorizator.image-model-code` | unset | The chat model configuration that describes images; unset means the default chat model when it reads pictures, else the first configured one that does. |
| `ai.gebo.vectorizator.max-media-mb` | `20` | A larger media is not given to a model. |

A kind is read only when its switch is on and a model for it is configured. A media that cannot be read (too large, empty, a type the model does not take, a failing model call, an empty answer) is logged and left out; the document's texts are vectorized all the same. The generated text carries the media's metadata plus `gebo_model_written` set to `description` or `transcript`. The compliance data-flow description of the vectorization now also names the model that the images, recordings or videos leave the installation for, while that reading is active.

### Class by class

#### `pom.xml` (modified) — `pom.xml`
Adds `spring-boot-starter-test` with test scope, needed by the new unit test.

#### `VectorizatorMediaConfig` (new) — `src/main/java/ai/gebo/ragsystem/content/vectorizator/config/VectorizatorMediaConfig.java`
A `@Configuration` / `@ConfigurationProperties(prefix = "ai.gebo.vectorizator")` bean with `embedImages`, `embedRecordings`, `embedVideos` (all false), `imageModelCode` (null) and `maxMediaMb` (20). `maxMediaBytes()` converts the limit to bytes (never negative).

#### `VectorizatorMediaReader` (new) — `src/main/java/ai/gebo/ragsystem/content/vectorizator/impl/VectorizatorMediaReader.java`
The `@Service` that turns media documents into text documents. It receives the configuration and `ObjectProvider`s of `IGChatModelRuntimeConfigurationDao` and `IGTranscriptModelRuntimeConfigurationDao`. `imageModel()` returns the model named by `imageModelCode` when it accepts `image/png` (null if it does not), else the default chat model when it accepts `image/png`, else the first configured model that does, else null. `transcriptModel()` returns the default transcription model or null. `reads(kind)` is true for `IMAGE` when `embedImages` is on and an image model exists, and for `AUDIO` / `VIDEO` when their switch is on and a transcription model exists. `withMediaAsText(documentName, documents)` returns the same list object when it holds no media; otherwise a new list in which each media document is replaced, in place, by the text documents made of it (text documents are the same objects, untouched). `textOf(documentName, mediaDocument)` does the work: it rejects an unread kind, an empty media or one larger than `maxMediaBytes()`; for an image it checks that the model accepts the actual type, then calls the chat model with the system prompt `DESCRIBE_SYSTEM_PROMPT` (read every text in the picture in reading order, tables row by row, then what the picture shows; nothing else; nothing at all for an empty or unreadable picture) and a user message carrying the picture; for a recording or video it calls `transcriptModel().call(..)` on the bytes. A blank answer gives nothing. The text gets a copy of the media's metadata plus `MODEL_WRITTEN_METADATA` (`"gebo_model_written"`) set to `"description"` or `"transcript"`, is split with Spring AI's `TokenTextSplitter`, and each piece gets `GEBO_BYTES_LENGTH` and an estimated `GEBO_TOKEN_LENGTH`. Any exception is logged and the media left out.

#### `GDocumentChunkServiceAccessorImpl` (modified) — `src/main/java/ai/gebo/ragsystem/content/vectorizator/impl/GDocumentChunkServiceAccessorImpl.java`
When turning the chunks read from the chunk service into Spring AI documents, a chunk that `holdsText()` is mapped as before; a media chunk goes through the new `mediaDocument(chunk)`, and null results are filtered out. `mediaDocument` returns null when no `VectorizatorMediaReader` is present, when the reader does not read that kind, when the chunk has no MIME type or no bytes, or on a `RuntimeException` (logged as a warning); otherwise it returns a `Document` with the chunk's id, the bytes as `Media` and the chunk's metadata. The reader is injected with `@Autowired(required = false)` and `setMediaReader(..)` exists for tests.

#### `GEmbedderImpl` (modified) — `src/main/java/ai/gebo/ragsystem/content/vectorizator/impl/GEmbedderImpl.java`
A field `mediaReader` is injected with `@Autowired(required = false)`. At the start of `embed(..)`, for every message with documents, the payload's documents are replaced by `mediaReader.withMediaAsText(name, documents)` when that returns a different list. From there the existing code runs unchanged on texts only, so the ids of the generated texts are collected and recorded exactly as the ids of the document's own texts.

#### `GContentVectorizationMessagesReceiverFactoryComponent` (modified) — `src/main/java/ai/gebo/ragsystem/content/vectorizator/impl/GContentVectorizationMessagesReceiverFactoryComponent.java`
The data-flow description built by this component now ends with a call to the new `addMediaReading(flow)`. With a `VectorizatorMediaReader` present, it adds the image-describing chat model when `reads(IMAGE)` is true, and the transcription model when `reads(AUDIO)` or `reads(VIDEO)` is true, each through `addMediaModel(..)`. `addMediaModel` adds a `DataEndpoint` of type `LLM_ENDPOINT` (id, label with the model code, model type code as product, base URL, secret reference when the configuration has one, locality local or external from the URL) plus an engine and a transformation from the chunk cache to that endpoint, with a description saying which media leave the installation for that model. A `RuntimeException` while describing is logged at debug level.

#### `VectorizatorMediaReaderTest` (new) — `src/test/java/ai/gebo/ragsystem/content/vectorizator/impl/VectorizatorMediaReaderTest.java`
With mocked models it asserts that an image between two texts becomes one text document in its place holding the model's answer, with the image's metadata, `gebo_model_written` = `description` and a non-blank id, while the two texts are the same objects; that with the switch off the image is left out and the model is never called, that with a model that accepts no media `reads(IMAGE)` is false and the image is left out, and that `reads(AUDIO)` is false when only images are on; and that a list of texts is returned as the same object.

---

## gebo.ragsystem.content.fulltext.processor

Path: `gebo.ragsystem.parent/gebo.ragsystem.content.fulltext.processor`

### What it does now (functional view)

The full-text indexing ignores media chunks: only chunks holding text are indexed, so a document ingested with images, recordings or videos is indexed in full text exactly by its texts.

### Class by class

#### `FullTextIndexingBatchMessageReceiver` (modified) — `src/main/java/ai/gebo/ragsystem/content/fulltext/processor/impl/FullTextIndexingBatchMessageReceiver.java`
The chunks of each chunk set are filtered with `DocumentChunk::holdsText` before being indexed.

---

## gebo.ragsystem.content.graphrag_processor

Path: `gebo.ragsystem.parent/gebo.ragsystem.content.graphrag_processor`

### What it does now (functional view)

The graph extraction ignores media chunks: only chunks holding text are given to the extraction.

### Class by class

#### `GraphextractionProcessorBatchReceiver` (modified) — `src/main/java/ai/gebo/ragsystem/content/graphrag_processor/impl/GraphextractionProcessorBatchReceiver.java`
The stream of chunks of each chunk set is filtered with `DocumentChunk::holdsText` before the chunks are turned into documents for the extraction.

---

## gebo.ai.app

Path: `gebo.apps.parent/gebo.ai.app`

### What it does now (functional view)

The monolith's `application.yml` lists, with comments, the new settings of this branch and their shipped defaults, so an administrator can find and change them:

- `ai.gebo.a2a.server.linked-files-hosts:` (empty) — the hosts from which a file that an A2A caller names by a link is fetched; none means no link is fetched.
- Ingestion of media: `ai.gebo.images.ingestion-enabled: false`, `ai.gebo.images.max-images-per-document: 30`, `ai.gebo.images.max-image-mb: 20`, `ai.gebo.images.min-image-bytes: 2048`, `ai.gebo.recordings.ingestion-enabled: false`, `ai.gebo.recordings.max-recording-mb: 20`, `ai.gebo.videos.ingestion-enabled: false`, `ai.gebo.videos.max-video-mb: 20`.
- Vectorization of media: `ai.gebo.vectorizator.embed-images: false`, `ai.gebo.vectorizator.embed-recordings: false`, `ai.gebo.vectorizator.embed-videos: false`, `ai.gebo.vectorizator.image-model-code:` (empty), `ai.gebo.vectorizator.max-media-mb: 20`.
- Files sent to the chat models as they are, one switch and one set of limits per kind, all off: `ai.gebo.images.chat-*`, `ai.gebo.recordings.chat-*`, `ai.gebo.videos.chat-*`, `ai.gebo.documents.chat-*` (values in section 2).
- Conversation files sent to remote A2A agents: `ai.gebo.a2a.clients.send-files-enabled: false`, `send-max-file-mb: 2`, `send-max-total-mb: 4`.

A new integration test covers chat uploads for models that take files.

### Class by class

#### `application.yml` (modified) — `src/main/resources/application.yml`
Adds the properties listed above in two places: the A2A, ingestion and vectorization properties after `ai.gebo.ingestion.pdf.column-aware-extraction`, and the per-kind chat settings and the A2A client settings at the end of the file. The comments explain the two steps (ingestion gives the media as they are and calls no model; vectorization turns them into text with a model and embeds the text) and that every switch is off when not set.

#### `ChatUploadsForMultimodalModelsTest` (new) — `src/test/java/ai/gebo/ai/app/tests/ChatUploadsForMultimodalModelsTest.java`
An integration test extending `AbstractBaseTestLLmsIntegrationTests`, observing the prompts the test chat model receives. `aPictureReachesTheModelThatDeclaresPictures` declares the default model as taking text and images, then asserts that the accepted file types of the chat report `image/*` as model MIME types, that a PNG upload is accepted, that the request context carries the picture as media, that the model receives it in its last user message with the bytes unchanged, that a call made with `context.withoutMedia()` carries no media, and that the same request to a model declaring text only carries none. `aTextOnlyModelsChatRefusesAPictureNobodyReads` asserts that with a text-only model the upload of a PNG gives no result. `aPictureTheChatGeneratedTravelsWithTheNextRequests` asserts that a picture stored with `storageArea.addMedia(..)` has its full content type and a size, and is returned by `getSessionMedia(chat)` with the bytes unchanged. `aFileAToolOrAnAgentReturnedIsKeptAndFoundAmongTheConversationsFiles` asserts that `IGConversationGeneratedFilesStore.store(..)` keeps a file in the `generated` location named with the extension of its type, keeps two files of one name apart (`chart.png`, `chart (2).png`), that the location is read-only and lists and reads them, and that both appear among the session media. The tests switch every kind of file on before each test and set the shipped limits back after. `asShippedNoPictureReachesAModelThatDeclaresPictures` tries the shipped state: for a model declaring images it asserts that `application.yml` ships every kind off, that no file type is accepted for the model, that a PNG upload is refused, and that a picture already in the conversation no longer travels with the requests.

---

## gebo.ui — gebo-ai-admin-ui (hand-written)

Path: `gebo.ui/projects/gebo-ai-admin-ui`

### What it does now (functional view)

**Chat model editor.** Under "Accepts as input" the help text no longer says that only text is sent; it now says a file goes to the model as it is when the model accepts its kind and the provider's integration can deliver its type. Two new lines appear when they apply: "Files sent to this model as they are:" with the MIME types actually delivered for the declared modalities, and "Not delivered through this provider's integration:" with the declared modalities the integration carries nothing for.

**Configured chat models list.** A new "Accepts" column shows what each configuration declares (text when it declares nothing) and, beneath it, what the model's stored metadata know beyond text when the configuration declares nothing. When at least one such model exists, a note "Some models are set to text only although their metadata say they accept more." appears with a button "Use the models' metadata" that applies the metadata to those configurations.

**Setup wizard.** Under the chosen suggested chat model a line "Accepts as input:" shows what that model takes, when known.

**MCP server editor.** The table of networks of agents exported as tools has a new "Chat profile" column, and the add row has a "Chat profile" field. A network given a chat profile runs in a conversation of the caller and answers with the files it produced as well as its text; left empty, it runs outside any conversation and answers with text.

### Class by class

#### `standard-chat-model-settings.component.html` (modified) — `src/lib/admin-ui/entity-editors/controls/standard-chat-model-settings/standard-chat-model-settings.component.html`
Changes the `help` text of the `inputModalities` field and adds two conditional `<small>` lines: `SentFileTypesLabel` followed by `sentFileTypes.join(', ')` and `DeclaredNotCarriedLabel` followed by `declaredNotCarriedText`.

#### `standard-chat-model-settings.component.ts` (modified) — `src/lib/admin-ui/entity-editors/controls/standard-chat-model-settings/standard-chat-model-settings.component.ts`
`GeboAIStandardChatModelSettings` now injects `ChatModelsControllerService` and `ChangeDetectorRef`. The field `carriedMediaTypes` (`GModelCarriedMediaTypes`) holds what the provider's integration delivers by modality. The getter `sentFileTypes` returns the carried MIME types of the modalities the form declares (`IMAGE`, `AUDIO`, `VIDEO`, `DOCUMENT`); the getter `declaredNotCarriedText` returns the labels of the declared non-text modalities with no carried type. `loadCarriedMediaTypes(modelCode)` calls `getCarriedMediaTypes(modelTypeCode, modelCode)` once per model type and model pair and marks the view for check; on error it clears the value. `notifyChoosedModelChange(..)` now also receives the model `code` and triggers that load.

#### `gebo-ai-mcp-server-admin.component.html` (modified) — `src/lib/admin-ui/entity-editors/gebo-ai-mcp-server-admin/gebo-ai-mcp-server-admin.component.html`
The description paragraph of the network tools gains the sentence on chat profiles; the table gets the header `NetworkToolChatProfileHeader` and a cell showing `item.chatProfileCode`; the empty-message cell spans four columns; the add row is re-proportioned (columns 4, 3, 3, 2) to fit a new `gebo-ai-field` `newNetworkToolChatProfile` bound to `newNetworkTool.chatProfileCode`, with a help text explaining the behaviour.

#### `gebo-ai-mcp-server-admin.component.ts` (modified) — `src/lib/admin-ui/entity-editors/gebo-ai-mcp-server-admin/gebo-ai-mcp-server-admin.component.ts`
In `addNetworkTool()` the entry's `chatProfileCode` is trimmed and set to `undefined` when blank, so a network runs in a conversation only when a profile is named.

#### `llms-systems.component.html` (modified) — `src/lib/admin-ui/main-panels/llms-systems/llms-systems.component.html`
Adds the note and the `ApplyModelsMetadataInputsBtn` button above the chat models table (shown when `someModelsCanTakeTheirMetadataInputs`), the `AcceptsHeader` column, and a cell showing `declaredInputs(code)` in lower case with, when present, a `metadata:` line listing `undeclaredMetadataInputs(code)`.

#### `llms-systems.component.ts` (modified) — `src/lib/admin-ui/main-panels/llms-systems/llms-systems.component.ts`
`LlmsSystemsComponent` keeps `chatModelsInputs`, a map of `ChatModelInputs` by configuration code, loaded by `loadChatModelsInputs()` (calling `getChatModelsInputs()`) after the chat models are loaded. `declaredInputs(code)` returns the declared modalities or `["TEXT"]`. `undeclaredMetadataInputs(code)` returns the metadata modalities other than text for a configuration that declares nothing. `someModelsCanTakeTheirMetadataInputs` is true when any configuration has such modalities. `applyModelsMetadataInputs()` calls `applyModelsMetadataInputModalities()` and reloads the inputs.

#### `llms-vendor-modeltype.component.html` (modified) — `src/lib/setup-wizard/llms-setup-components/llms-vendor-modeltype.component.html`
Adds, under the list of suggested chat models, a line `ChosenChatModelInputsLabel` followed by `chosenChatModelInputs` when it is not empty.

#### `llms-vendor-modeltype.component.ts` (modified) — `src/lib/setup-wizard/llms-setup-components/llms-vendor-modeltype.component.ts`
Adds the getter `chosenChatModelInputs` on `GeboAILlmsVendorModelTypeConfig`: for the chosen preset code it takes the preset's `inputModalities`, else the looked-up model's `metaInfos.inputModalities`, else derives text plus image or text only from `metaInfos.supportsVision`; it returns the list in lower case, or an empty string when nothing is known.

---

## gebo.ui — gebo-ai-reusable-ui (hand-written)

Path: `gebo.ui/projects/gebo-ai-reusable-ui`

### What it does now (functional view)

The chat upload box now tells the user which files the chat's model reads as they are ("This chat's model also reads these files as they are:" followed by the MIME types) and lets the file picker accept those types. The note on files accepted for the agents' tools no longer says that the models do not see pictures. The size bar's caption reads "10Mb" instead of "1Mb".

### Class by class

#### `upload-chat-document.component.html` (modified) — `src/lib/controls/choose-documents-panel/upload-chat-document.component.html`
Shortens the `ToolsOnlyFilesNote` text, adds a card with `ModelFilesNote` and `modelMimeTypes.join(', ')` shown when the list is not empty, and changes the caption of the progress bar from `/ 1Mb` to `/ 10Mb` (the caption only; no limit is changed in this file).

#### `upload-chat-document.component.ts` (modified) — `src/lib/controls/choose-documents-panel/upload-chat-document.component.ts`
`GeboAIUploadChatDocumentComponent` gains the field `modelMimeTypes`, filled from `value.modelMimeTypes` of the accepted file types answer, and adds those types to `filesExtensionsFlatList`, the list the file picker accepts.

#### `en.json` (modified) — `src/assets/i18n/en.json`
Adds the labels `AcceptsHeader`, `MetadataInputsLabel`, `ApplyModelsMetadataInputsNote`, `ApplyModelsMetadataInputsBtn`, `SentFileTypesLabel`, `DeclaredNotCarriedLabel`, `ChosenChatModelInputsLabel` and `ModelFilesNote`, and changes the `inputModalities` help and the `ToolsOnlyFilesNote` label to the new wording. Under `GeboAIMcpServerAdminModule` / `GeboMCPServerConfig` it adds the labels and help texts of the MCP server editor's two tables of exported tools (agents and networks of agents), the "Chat profile" column and field included; the same entries are in `it.json` in Italian.

#### `it.json` (modified) — `src/assets/i18n/it.json`
The file is a single line, so the diff shows one changed line; within it the Italian `inputModalities` help and `ToolsOnlyFilesNote` label are reworded to match, and Italian texts for the new labels are present (seen: `AcceptsHeader` "Accetta", `SentFileTypesLabel`, `ModelFilesNote`). The remaining new keys were not checked one by one in the Italian file.

---

## Generated code (Angular stubs and Java REST client)

Paths: `gebo.ui/projects/gebo-ai-rest-api` and `gebo.api.clients/gebo.monolithic.api.resttemplate.client`

### What it does now (functional view)

Both generated clients were regenerated from the backend's OpenAPI description and now expose the new endpoints and fields of this branch. Nothing here is hand-written.

### What changed

**New model files (both clients; the Java client also adds a `docs/*.md` page for each):** `ChatModelInputs`, `GModelCarriedMediaTypes`, `OperationStatusListChatModelInputs`, `UploadsWithNewChat`, `OperationStatusUploadsWithNewChat`, `ChatSessionCreateWithUploadChatModelCodeBody`, `RagChatSessionCreateWithUploadChatProfileCodeBody`. No file was deleted.

- `ChatModelInputs`: `code`, `description`, `modelTypeCode`, `modelCode`, `inputModalities`, `metadataInputModalities`, `carriedMediaTypes`, `effectiveInputMimeTypes`, `declaredNotCarried` (modalities are `TEXT`, `IMAGE`, `AUDIO`, `VIDEO`, `DOCUMENT`).
- `GModelCarriedMediaTypes`: `image`, `audio`, `video`, `document` (lists of MIME types).
- `UploadsWithNewChat`: `uploads` (list of `UserUploadedContent`) and `chatInfo` (`GUserChatInfo`).

**New operations:**

- `ChatModelsController`: `getChatModelsInputs` (GET `/api/admin/ChatModelsController/getChatModelsInputs`), `getCarriedMediaTypes(modelTypeCode, modelCode)` (GET `.../getCarriedMediaTypes`), `applyModelsMetadataInputModalities` (POST `.../applyModelsMetadataInputModalities`).
- `GeboUserChatUploadsController`: `chatSessionCreateWithUpload(chatModelCode, files)` and `ragChatSessionCreateWithUpload(chatProfileCode, files)` (POST under `/api/users/GeboUserChatUploadsController/`), both answering `OperationStatusUploadsWithNewChat`.

**New properties on existing models:** `GChatUploadFileTypes.modelMimeTypes`; `GeboMCPAgentsNetworkTool.chatProfileCode`; `ModelMetaInfo.outputModalities`; `ModelProviderCapabilities.inputModalities` and `inputMimeTypes`.

**Churn.** In the Angular stubs the other modified models (`DocumentReferenceView`, `GJobStatusItem`, `GResponseDocumentRef`, `GUserChatInfo`, `UserInfos`) show only a reordering of their existing properties. In the Java client 807 files changed; of the 779 modified `.java` files, 766 differ only in the `@javax.annotation.Generated` date line (counted over all of them), and the sampled remainder (`GJobStatusItem`) shows property reordering.

---

## Gebo.ai.pro — gebo.architecture.libraries.document-models-tools

Path: `/home/zava/GitHub/Gebo.ai.pro/ai.gebo.pro.architecture.parent/gebo.architecture.libraries.document-models-tools` (branch `feature/moltimodality`, 3 files against Pro `develop`)

### What it does now (functional view)

When an agent whose model takes pictures calls the tools that extract the pictures of a text document (`extractImages`) or of a presentation (`extractSlideImages`), the extracted pictures are now also shown to that model right after the tool's answer, and the answer says how many were shown. For a model that does not take pictures, or a call with nobody to show to, the tools behave and answer as before. The tool descriptions and the text-documents usage rules tell the model about this and about `viewFile` for looking at a picture.

### Class by class

#### `TextDocumentsLibrarySource` (modified) — `src/main/java/ai/gebo/libraries/documentmodels/text/library/TextDocumentsLibrarySource.java`
`EXTRACT_IMAGES_DESCRIPTION` gains a sentence saying the copied pictures are shown when the model takes pictures. The usage rules replace "you do not see it" with guidance to call `viewFile`, when it is among the tools, to look at a picture (an upload, an extracted one, one in `generated`). The record `ImagesExtracted` gains a third component `Integer shown` (described for the model, omitted from JSON when null through `@JsonInclude(NON_NULL)`) and keeps a two-argument constructor. `extractImages(places, param)` now delegates to the new `extractImages(places, param, context)`, which, after writing the files, calls `IGToolShownFiles.show(context, name, mediaType, data)` for each picture (media type from the image or detected with `GImages.mediaTypeOf`), counts the ones taken, and returns `shown` only when a shower was present. The tool declaration passes the `ToolContext` through.

#### `PresentationsLibrarySource` (modified) — `src/main/java/ai/gebo/libraries/documentmodels/presentation/library/PresentationsLibrarySource.java`
The same change for presentations: `EXTRACT_SLIDE_IMAGES_DESCRIPTION` gains the sentence, `ImagesExtracted` gains `shown`, `extractSlideImages(places, param)` delegates to a new overload taking the `ToolContext` that shows each extracted picture through `IGToolShownFiles.show(..)` and reports the count, and the tool declaration passes the context.

#### `TextDocumentsLibrarySourceTest` (modified) — `src/test/java/ai/gebo/libraries/documentmodels/text/library/TextDocumentsLibrarySourceTest.java`
The existing extraction test now also asserts that the answer has no `shown` field when nobody is there to show the pictures to, and that with an `IGToolShownFiles` in the `ToolContext` the answer contains `"shown":1` and the shower received one `image/png` picture of the chart's size.
