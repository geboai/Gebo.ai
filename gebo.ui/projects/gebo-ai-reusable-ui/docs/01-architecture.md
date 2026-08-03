# Architecture: actions, modals, entity forms, wizards

Read [00-overview.md](./00-overview.md) first for the module install/wiring. This doc covers the
decoupled "request an action → route it → open a modal/form" pipeline that most editing UI in
this library (and in apps that consume it) is built on, plus the setup-wizard framework.

## 1. The core problem this solves

Any component (a tree node, a table row, a menu item) may need to say "open the editor for this
entity" or "create a new X" — without knowing which component renders that editor, or where it
lives in the DOM. This library solves it with a small pub/sub bus:

```
component fires a GeboUIActionRequest
        │
        ▼
GeboUIActionRoutingService.routeEvent(request)   // finds a registered listener by targetType
        │
        ▼
GeboUIModalOpenerComponent.handleAction(request)  // one instance per entity type, self-registered
        │
        ▼
opens <gebo-ui-modal-wrapper> which dynamically instantiates a BaseEntityEditingComponent subclass
```

`GeboUIEntityFormsLauncherComponent` (selector `gebo-ui-entities-forms-launcher-component`) is a
bootstrap component you place once in your app shell. On init it reads every registered
`GeboUIEntityFormConfig` and materializes one `GeboUIModalOpenerComponent` per entity type — so
you never place `<gebo-ui-modal-opener>` manually.

## 2. Registering an entity editor

```ts
import { GEBO_UI_ENTITY_FORM_TOKEN, GeboUIEntityFormConfig } from "@Gebo.ai/reusable-ui";

@NgModule({
  providers: [
    {
      provide: GEBO_UI_ENTITY_FORM_TOKEN,
      useValue: {
        entityName: 'GGitSystem',
        // alternate names the backend may send (e.g. fully-qualified Java class names)
        entityAliases: ['GGitContentManagementSystem', 'ai.gebo.git.content.handler.GGitContentManagementSystem'],
        entityUI: GeboAiGitSystemAdminComponent,
      } as GeboUIEntityFormConfig,
      multi: true,
    },
  ],
})
export class MyFeatureModule {}
```

This is the real pattern used throughout `gebo-ai-admin-ui` (one `{provide: GEBO_UI_ENTITY_FORM_TOKEN, ...}`
entry per editable entity type — dozens of them in that module). `entityUI` must be a component
class that extends `BaseEntityEditingComponent<T>` (see §3).

`GeboUIEntityFormsLauncherService` is the abstraction that supplies the list of configs to the
launcher; the default (and only) implementation, `GeboUIEntityFormsLauncherByInjectionService`,
just returns everything injected via `GEBO_UI_ENTITY_FORM_TOKEN`. Register it once at the app root:

```ts
{ provide: GeboUIEntityFormsLauncherService, useClass: GeboUIEntityFormsLauncherByInjectionService }
```

## 3. Writing an entity-editing component

Extend `BaseEntityEditingComponent<RecordType extends {code?, description?}>`. Real, working
example from this repo (`gebo-deep-search-admin.component.ts`, trimmed):

```ts
import { Component, forwardRef, Injector } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import {
  BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE,
  GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService,
} from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { Observable, of, map } from "rxjs";

@Component({
  selector: "gebo-ai-deep-search-admin-component",
  templateUrl: "gebo-deep-search-admin.component.html",
  standalone: false,
  providers: [
    { provide: GEBO_AI_MODULE, useValue: "GeboAIDeepSearchConfigAdminModule" },
    { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIDeepSearchConfigAdminComponent) },
  ],
})
export class GeboAIDeepSearchConfigAdminComponent extends BaseEntityEditingComponent<DeepSearchConfig> {
  protected override entityName = "DeepSearchConfig";
  override formGroup = new FormGroup({
    code: new FormControl(),
    description: new FormControl(),
    // ...rest of the entity's fields
  });

  constructor(
    injector: Injector,
    geboFormGroupsService: GeboFormGroupsService,
    confirmationService: ConfirmationService,
    geboUIActionRoutingService: GeboUIActionRoutingService,
    outputForwardingService: GeboUIOutputForwardingService,
    private deepSearchConfigService: GeboDeepSearchAdminControllerService, // your own extra deps go after
  ) {
    super(injector, geboFormGroupsService, confirmationService, geboUIActionRoutingService, outputForwardingService);
  }

  override findByCode(code: string): Observable<DeepSearchConfig | null> {
    return this.deepSearchConfigService.getDeepSearchDefaultConfig();
  }
  override save(value: DeepSearchConfig): Observable<DeepSearchConfig> {
    return this.deepSearchConfigService.updateDeepSearchConfig(value);
  }
  override insert(value: DeepSearchConfig): Observable<DeepSearchConfig> {
    return this.deepSearchConfigService.insertDeepSearchConfig(value);
  }
  override delete(value: DeepSearchConfig): Observable<boolean> {
    return this.deepSearchConfigService.deleteDeepSearchConfig(value).pipe(map(() => true));
  }
  override canBeDeleted(value: DeepSearchConfig): Observable<{ canBeDeleted: boolean; message: string }> {
    return of({ canBeDeleted: true, message: "" });
  }
}
```

Notes:
- The `GEBO_AI_MODULE` / `GEBO_AI_FIELD_HOST` providers are boilerplate needed by every field that
  uses `<gebo-ai-field>`/`[gebo-ai-label]`/`[gebo-ai-text]` inside the template — see
  [03-forms-and-fields.md](./03-forms-and-fields.md#field-translation-container). Copy them verbatim,
  changing only the module-id string and the component's own class name.
- Constructor param order for the base class is fixed: `injector, geboFormGroupsService, confirmationService, geboUIActionRoutingService, outputForwardingService`. Extra deps go after.
- `formGroup` only needs the fields you know about at compile time — `GeboFormGroupsService` will
  auto-add any additional `FormControl`s the backend declares for `entityName` via metadata (custom/
  pluggable fields) at runtime, so you don't get "unknown control" errors on those.
- If deletability is just "call this metadata-check endpoint" rather than custom logic, extend
  `BaseEntityEditingComponentAutoDeleteCheck<T>` instead — it implements `canBeDeleted` for you via
  `GeboAngularFormGroupMetaInfoControllerService.checkDeletableBySimpleObjectRef`.
- Multi-step editors: pass `wizardStepsConfigurations: GeboAIEntitiesSettingWizardConfiguration[]` describing
  next/previous step navigation (label, icon, and a callback building the next `GeboUIActionRequest`);
  the base class exposes `nextStep()`/`previusStep()` and the modal wrapper renders the step breadcrumb automatically.

## 4. Firing an action request

From anywhere with `GeboUIActionRoutingService` injected:

```ts
import { GeboActionType, GeboUIActionRequest } from "@Gebo.ai/reusable-ui";

const request: GeboUIActionRequest = {
  contextType: "GProject",
  context: someProject,
  actionType: GeboActionType.OPEN_OR_NEW,   // NEW | OPEN | OPEN_OR_NEW
  targetType: "GGitSystem",                  // must match a registered entityName/entityAliases
  target: undefined,                         // existing entity when actionType is OPEN
  onActionPerformed: (event) => {
    // event.actionType: SAVED | DELETED | INSERTED | CLOSING_WINDOW
  },
};
this.geboUIActionRoutingService.routeEvent(request);
```

## 5. Generic (non-entity) modal

For a plain modal that isn't part of the action-routing pipeline, use `<gebo-ai-modal>` directly:

```html
<gebo-ai-modal [(visible)]="dialogOpen" header="Confirm" [modal]="true">
  <p>Are you sure?</p>
</gebo-ai-modal>
```

## 6. Setup wizard framework

Used for the guided post-install configuration flow (LLM setup, integrations, etc.). Register
sections via the `WIZARD_SECTION` multi-provider token:

```ts
import { WIZARD_SECTION, SetupWizardsSection, AlwaysTrueStatusService } from "@Gebo.ai/reusable-ui";

const mySection: SetupWizardsSection = {
  orderEntry: 10,
  wizardSectionId: "my-integration",
  label: "My Integration",
  description: "Configure My Integration",
  enabledService: AlwaysTrueStatusService,       // or your own AbstractStatusService
  setupCompletedService: MyCompletionCheckService, // an AbstractStatusService you implement
  wizardComponent: MyWizardStepComponent,          // extends BaseWizardSectionComponent
  mandatory: true,
};

@NgModule({ providers: [{ provide: WIZARD_SECTION, useValue: mySection, multi: true }] })
export class MyModule {}
```

Your step component:

```ts
@Component({ selector: "my-wizard-step", template: "..." })
export class MyWizardStepComponent extends BaseWizardSectionComponent {
  constructor(setupWizardComunicationService: SetupWizardComunicationService) {
    super(setupWizardComunicationService);
  }
  reloadData(): void { /* fetch current state */ }
}
```

Drop `<gebo-setup-wizard-panel-component>` (from `SetupWizardPanelModule`) wherever the wizard UI
should render; it reads `SetupWizardService.getActualStatus()` (which resolves every registered
section's `enabledService`/`setupCompletedService`) and renders a step list + the active step's
`wizardComponent`. Call `.closeWizard()` from inside a step (already available via the base class)
to return to the section list.

## 7. Base URL validator

```ts
import { GeboAIValidators } from "@Gebo.ai/reusable-ui";
new FormControl('', GeboAIValidators.baseUrl(/* required */ true));
```

See [05-dashboards-and-notifications.md](./05-dashboards-and-notifications.md) for the toast
notification system (`GeboAINotificationsModule`), which most components above push messages
into via `userMessages`/`<gebo-ai-notifications [messages]="userMessages">`.
