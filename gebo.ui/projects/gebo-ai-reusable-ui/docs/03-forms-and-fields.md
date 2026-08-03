# Reusable form controls & the i18n field system

Almost every control here implements Angular's `ControlValueAccessor` — use them with
`formControlName`/`[(ngModel)]`, not raw `@Input`/`@Output` pairs, unless stated otherwise.

## Field translation container — the i18n wrapper used everywhere

`GeboAIFieldTranslationContainerModule` (import with `.forRoot()` **once**, at the app root — see
[00-overview.md](./00-overview.md)) provides the label/help/placeholder translation plumbing that
wraps nearly every form field in the app.

```html
<div [formGroup]="formGroup">
  <gebo-ai-field id="code" label="Code" help="Unique identifier">
    <input gebo-ai-field-element pInputText formControlName="code" />
  </gebo-ai-field>
</div>
```
`<gebo-ai-field>` (`gebo-ai-field`) requires `[id]` and `[label]` (both `@Input({required:true})`),
optional `[placeholder]`, `[help]`, `[required]`. It must sit inside a `[formGroup]`/`FormGroupDirective`
context. The `gebo-ai-field-element` directive on the inner input auto-wires `id`/`aria-describedby`/
`required`/`aria-invalid` from the field container + the input's own `NgControl`.

For plain (non-form) translated text, use the directives directly:
```html
<label gebo-ai-label>Some label</label>
<span gebo-ai-text>Some UI text</span>
<p-button gebo-ai-label label="Save"></p-button> <!-- 22 PrimeNG components have adapter support -->
```
`[gebo-ai-label]` works on plain DOM elements' `label`/`legend`/`header`/`title`/`placeholder`
attributes, and on 22 specific PrimeNG components (button, dialog, panel, card, chip, tag, checkbox,
radio, select, multiselect, listbox, calendar, slider, etc.) via dedicated adapter directives,
because those components' "label" isn't a plain DOM attribute.

Every component that hosts `<gebo-ai-field>`/`[gebo-ai-label]`/`[gebo-ai-text]` children must
provide two DI tokens so those children can resolve translation lookup keys:
```ts
providers: [
  { provide: GEBO_AI_MODULE, useValue: "MyFeatureModule" },   // arbitrary but stable module id
  { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => MyComponent) }, // or fieldHostComponentName("MyEntity")
]
```
`fieldHostComponentName(entityName: string)` is a shortcut when you don't need dynamic host
resolution: `{ provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("MyEntity") }`.

Language switcher: `<gebo-ai-language-choice-component>` (CVA, value = language code string).
App-header variant: `<gebo-ai-main-language-choice [menuItem]="langMenuItem">` (persists to
`localStorage['gebo.ai.lang']`, defaults to browser language if supported).

## Editable listbox / relation list — pick-or-create-new widgets

`EditableListboxModule`:
```html
<geboai-editable-listbox-component
  formControlName="chosenCode"
  [optionsObservable]="options$"
  [createNewRecordRequest]="newRecordRequest">
</geboai-editable-listbox-component>
```
Value is the item's `code` (`string`). `createNewRecordRequest?: GeboUIActionRequest` — when set, an
extra "Create new…" option appears that fires this request through
[the action-routing pipeline](./01-architecture.md#4-firing-an-action-request). Sibling
`<geboai-editable-listbox-bound-object-component>` (`EditableListboxBoundObjectAdapterComponent`)
does the same but binds/emits the **full `{code,description}` object** instead of just the code.

`GeboAiRelationListModule`: `<gebo-ai-relation-list formControlName="relatedCodes" [allOptionsObservable]="all$" [remainingObjectsObservable]="remaining$">` — chip-list editor for a many-to-many
relation; value is `string[]` of codes.

## Content selection filter — `GeboAIContentSelectionFilterModule`

`<gebo-ai-content-selection-filter-component formControlName="filter" title="File selection criterias">`
— editable list of ingestion-scoping rules (mime types, extensions, name filter+operator, max file
size/token size/modification age). Value: `GContentSelectionFilter = { criterias: GContentSelectionFilterCriteria[] }`.
Implements `Validator` as well as CVA — invalid rows surface as form errors automatically.

## API key / secret picker — `GeboAIApiKeyModule`

```html
<gebo-ai-api-key-component
  formControlName="secretCode"
  [contextCode]="'git-endpoint-123'"
  [apiKeyDescription]="'Git access token'"
  [backendValidation]="validateFn">
</gebo-ai-api-key-component>
```
`contextCode` and `apiKeyDescription` are `@Input({required:true})`. `backendValidation?: (credentials: SecretInfo) => Observable<IOperationStatus<any>>`
— if the newly-entered secret fails this check, the component deletes the credential server-side and
surfaces the validation error. Value: secret `code: string | undefined`.

## OAuth2 secret editor — `GeboOauth2SecretModule`

`<gebo-ui-oauth2-secret-component formControlName="oauthSecret" [oauth2ChoosableScopes]="scopes">` —
`clientId`/`secret`/`scopes` plus dynamically-generated custom attribute fields per selected provider.
`[hideProviderChoice]`, `[forcedProviderName]` to pin a single provider. Implements both CVA and
`Validator` (`notValidated`/`required`/`requiredFields` errors while provider metadata is loading).

## Choose LLM tool-calling functions — `GeboAIChooseLLMFunctionsModule`

`<gebo-ai-choose-llm-functions-component formControlName="enabledFunctions" [knowledgeBaseContext]="true">`
— `p-treeSelect` over the backend's tool/function catalog. Value: `string[]` (leaf function names).

## Chat model "used for" flags — `GeboAIChatModelUseModule`

`<gebo-ai-chat-model-use-component formControlName="forUses">` — checkbox group, value:
`GBaseChatModelConfig.ForUsesEnum[]` (`CHAT` / `INTERNAL_SERVICES`).

## Prompt template editor — `PromptEditingModule`

`<gebo-ai-prompt-editing-component formControlName="promptText" [rows]="8" [placeHolders]="[{code:'{{query}}', description:'User query', required:true}]">`
— Monaco-backed editor for prompt templates; value is the raw `string`. (Note: the sibling
"AI-assisted prompt generation" wizard dialog exists in the same folder but its generation call
isn't wired up yet — see [00-overview.md](./00-overview.md#known-rough-edges).)

## Reindex schedule editor — `GeboAIContentReindexModule`

`<gebo-ai-content-reindex-scheduler-component formControlName="schedule" [frequencyTypes]="['DAILY','WEEKLY']">`
— add/edit/remove UI for a set of `ReindexingProgrammedTable` rules. Value: `ReindexingProgrammedTable[]`.

## Base entity-editing infrastructure

See [01-architecture.md §3](./01-architecture.md#3-writing-an-entity-editing-component) for
`BaseEntityEditingComponent<T>` / `BaseEntityEditingComponentAutoDeleteCheck<T>` — the CRUD base
classes most "entity edit form" components in this library (and in `gebo-ai-admin-ui`) extend.

## Pluggable project-endpoint modules (extension point)

If you're adding a new data-source type (a new "Git"/"SharePoint"-like integration), implement
`GeboAIPluggableProjectEndpointModuleService` and register it:
```ts
{
  provide: GEBO_AI_PLUGGABLE_MODULE_UI_CONFIG,
  useValue: {
    moduleId: 'MY_MODULE',
    projecteEndpointClassName: 'ai.myorg.MyProjectEndpoint',
    addProjectEndpointicon: 'pi pi-cloud',
    addProjectEndpointLabel: 'My Source',
    addProjectEndpointTitle: 'Add My Source',
    service: MyProjectEndpointModuleService,
  } as GeboAIPluggableProjectEndpointModule,
  multi: true,
}
```
`GeboAIPluggableProjectEndpointsService` aggregates every registered module's
`findByProjectEndpoints`/`byProjectCreateAction` so the generic knowledge-base tree UI
(`ProjectAddContextMenuModule`, see [02-chat-and-knowledge-base.md](./02-chat-and-knowledge-base.md))
picks it up automatically without core-library changes.
