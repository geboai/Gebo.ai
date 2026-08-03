# Chat, knowledge base & content

All modules below export a single top-level component; internal sub-components exist in the same
folder but aren't part of the public API surface unless noted.

## Chat widget — `GeboAIReusableChatModule` / `<gebo-ai-reusable-chat-component>`

The full RAG/plain chat UI: send/stream messages, chat history, rename/delete, TTS playback,
speech-to-text, export to PDF/DOCX, agentic pipeline routing.

```ts
import { GeboAIReusableChatModule } from "@Gebo.ai/reusable-ui";
@NgModule({ imports: [GeboAIReusableChatModule] })
export class MyChatFeatureModule {}
```

```html
<gebo-ai-reusable-chat-component
  [ragsystem]="true"
  [chatInfo]="currentChat"
  [title]="currentChat?.description"
  (updatedChatAction)="onUpdatedChat($event)">
</gebo-ai-reusable-chat-component>
```
(real usage from `gebo-ai-chat-ui`'s `gebo-ai-rag-chat-section.component.html`)

| Input | Type | Notes |
|---|---|---|
| `chatInfo` | `GUserChatInfo` | Drives history load. Omit/leave undefined to start a fresh unsaved chat. |
| `title` / `subtitle` | `string` | Header text. |
| `modelName` | `string` | Display label for which model is answering. |
| `ragsystem` | `boolean` | `true` = retrieval-augmented (searches knowledge bases); `false` = plain model chat. |
| `maxDisplayedDocs` | `number` (default 3) | Cap on referenced-document chips shown per response. |
| `streamingTimeout` | `number` ms (default 600000) | |
| `useRestOnly` | `boolean` | Disable SSE streaming, fall back to plain REST round-trip. |

| Output | Payload |
|---|---|
| `addedChatAction` | `GUserChatInfo` — fired when a new chat session is created (e.g. first message sent without a prior `chatInfo`) |
| `updatedChatAction` | `GUserChatInfo` — description renamed etc. |
| `deleteChatAction` | `GUserChatInfo` |
| `cancelAction` | `boolean` |

Minimal "test this model" usage (also real, from `gebo-ai-admin-ui`):
```html
<gebo-ai-reusable-chat-component [chatInfo]="{chatModelCode: chatModelCode}" [modelName]="chatModelCode">
</gebo-ai-reusable-chat-component>
```

Underlying streaming service if you need to talk to the same endpoints directly:
`ReactiveRagChatService` (`streamChat`, `streamRagChat`, `streamAgenticChat` — all POST + SSE-style
line-delimited JSON via `fetch`, built on the library's internal `GeboAIBaseStreamingService`).

## Deep search — `GeboAIDeepSearchModule`

Two components, both exported:
- `<gebo-ai-deep-search-component>` (`GeboAIDeepSearchComponent`) — the deep-search mode panel inside the chat composer (`query`, `knowledgeBases`, `deepSearchDataSources` fields). `[currentChatRequest]`, `[nextRequestMode]="'standard-chat'|'deep-search'"`, `[chatProfileCode]`; `(skipDeepSearchEvent)`.
- `<gebo-ai-deep-search-sources-choice>` (`GeboAIDeepSearchSoucesChoiceComponent`) — modal to pick which data sources/knowledge bases to search before firing a deep-search request; auto-skips itself if only one choice exists.
  ```html
  <gebo-ai-deep-search-sources-choice
    [(visible)]="showSourcesDialog"
    [(deepSearchDataSources)]="chosenSources"
    [(knowledgeBases)]="chosenKbs"
    [chatProfileCode]="profileCode"
    (onChoosed)="startDeepSearch($event)"
    (onSkip)="startDeepSearch()">
  </gebo-ai-deep-search-sources-choice>
  ```

## Document viewer — `GeboAIContentViewerModule`

Universal content viewer: source-code (Monaco), plain text, PDF, browsable HTML, or download-only
fallback, chosen automatically from resolved file type.

```html
<gebo-ai-content-viewer
  [code]="documentCode"
  [activate]="viewerOpen"
  (close)="viewerOpen = false">
</gebo-ai-content-viewer>
```
`[code]` is a content-controller code (for knowledge-base documents); alternatively pass
`[userUploadedContent]: EnrichedUserUploadedContentView` or `[generatedContent]: EnrichedLLMGeneratedResource`
for the other two content sources this viewer understands.

Also exported from this module:
- `<gebo-ai-code-editor>` (`CodeEditorWrapperComponent`) — standalone Monaco wrapper bound to a `ContentObject`: `[language]`, `[componentHeight]="'100%'"`, `[contentObject]`.
- `<gebo-ui-document-opener-button>` (`GeboUIDocumentOpenerButton`) — a button/link that opens the viewer above for you: `[document]: EnrichedDocumentReferenceView` / `[uploadedContent]` / `[generatedContent]`.
- `EnrichedDocumentReferenceViewRetrieveService` — inject this to resolve/enrich document/upload/generated-resource DTOs with file-type + icon before handing them to the components above (`searchByDocumentName`, `findDocumentReferenceViewByCode`, `enrichUserUploadedContent`, `enrichLLMGeneratedResource`).

## Choose documents panel (chat "attached documents") — `GeboAIChooseDocumentsPanelModule`

`<gebo-ai-choose-documents-panel-component>` — the attached-documents chip list used in the chat
composer; implements `ControlValueAccessor` over `string[]` document codes.

```html
<gebo-ai-choose-documents-panel-component
  formControlName="attachedDocs"
  [knowledgeBaseCodes]="['kb-1']"
  [maxDisplayedDocuments]="5"
  (successfullDocumentChosen)="onDocsChosen()">
</gebo-ai-choose-documents-panel-component>
```

Also exported: `<gebo-ai-search-documents-component>` (the search dialog content: semantic search
+ filename search + directory browse, `[knowledgeBaseCodes]`, emits `confirmed`/`closeMe`) and
`<gebo-ai-documents-list-panel-component>` (dumb picklist over an already-fetched
`DocumentReferenceView[]`, `[documentsList]`, emits `documentsSelection`).

Upload-into-chat widget is a **separate module**, `GeboAIUploadChatDocumentModule`:
```html
<gebo-ai-upload-chat-documents-files
  [(showUpload)]="uploadOpen"
  [ragChat]="true"
  [userSessionCode]="currentChat?.code"
  (successfullUploadDone)="onUploaded()"
  (newSessionCreatedOnUpload)="onNewSession($event)">
</gebo-ai-upload-chat-documents-files>
```

## Virtual filesystem browser — `VFilesystemSelectorModule`

Generic tree browser/picker over *any* backend "virtual filesystem" (git, SharePoint, Google Drive,
uploads, userspace, knowledge-base trees). You supply the data-fetching callbacks; the component
only renders the PrimeNG tree + selection + edit-dialog UI.

```html
<gebo-ai-vfilesystem-selector-component
  formControlName="chosenPath"
  [selectionMode]="'single'"
  [canChooseFolders]="true"
  [canChooseFiles]="true"
  [loadRootsObservable]="loadRoots"
  [browsePathObservable]="browsePath">
</gebo-ai-vfilesystem-selector-component>
```
```ts
loadRoots = () => this.myService.getRoots();          // Observable<VFilesystemRootsResponse>
browsePath = (param: BrowseParam) => this.myService.list(param); // Observable<VFilesystemListPathInfo>
```
Value type (via `ControlValueAccessor`) is `VFilesystemReference | VFilesystemReference[]`
(`{ root: GVirtualFilesystemRoot, path?: PathInfo }`).

## Browse external content — `BrowseContentModule`

`<gebo-ai-browse-content-component>` — plain `<iframe [src]="url">` wrapper: `[title]`, `[url]`.
`<gebo-ai-html-viewer-component>` (`GeboAIViewHtmlComponent`) — injects a raw HTML string via `[htmlCode]`.

## Userspace files ("attach my personal files") — `GeboAIUserspaceFilesModule`

`<gebo-ai-userspace-files-component>` — picker for a user's personal uploaded files, implementing
`ControlValueAccessor` over `string[]` file codes.
```html
<gebo-ai-userspace-files-component formControlName="attachedUserFiles" [knowledgeBaseCodes]="['kb-1']">
</gebo-ai-userspace-files-component>
```
This module also self-registers editors for `UserspaceFolderDto` and `UserspaceKnowledgebaseDto`
into the [entity-forms-launcher](./01-architecture.md) registry (via `GeboUIEntityFormsLauncherService`),
so "new folder"/"new personal knowledge base" actions fired anywhere in the app resolve automatically.

## Audio recorder — `GeboAIAudioRecorderModule`

`<gebo-ai-audio-component>` — mic recorder + player (handles Safari/iOS MIME fallbacks internally).
```html
<gebo-ai-audio-component [disabled]="false" (onAudioTrack)="onRecorded($event)"></gebo-ai-audio-component>
```
`errorReason` (when `errorState` is true) is one of
`'permission-denied' | 'no-device' | 'device-busy' | 'insecure-context' | 'unsupported' | 'unknown'`.

## Adding a new project data-source type — `ProjectAddContextMenuModule`

`<project-add-context-menu>` (`ProjectAddContextMenuComponent`) renders the "+ add" menu (buttons,
menu items, or full page) for adding sub-projects / data sources / modules to a `GProject`, sourced
from whatever [pluggable project-endpoint modules](./03-forms-and-fields.md) are installed.
```html
<project-add-context-menu [data]="project" [showAsButtons]="true" (refreshUIEvent)="reload()">
</project-add-context-menu>
```
Also exported from the same module: `<gebo-ui-choose-data-source-type-component>`
(`GeboAIChooseDataSourceTypeComponent`) — the first step of that "add data source" wizard, an
entity-editing component (extends `BaseEntityEditingComponent`) capturing which source type was chosen.
