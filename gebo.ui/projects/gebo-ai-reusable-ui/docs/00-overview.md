# @Gebo.ai/reusable-ui — Overview

Angular 21 library of components/services powering the Gebo.ai web app (chat, knowledge-base
browsing/ingestion, admin entity forms, login/setup, usage dashboards). It is consumed by three
sibling libraries in the same monorepo (`gebo-ai-chat-ui`, `gebo-ai-admin-ui`) and by the shell
app (`gebo.ui/src/app`), and is also published as an installable npm package for third-party apps
that embed Gebo.ai UI.

This `docs/` folder is written for a coding assistant/agent that needs to wire up or extend
components from this library without reading every source file first. Each doc gives real
selectors, `@Input()`/`@Output()` names, and copy-pasteable wiring taken from this repo's own
usage of the library — not idealized examples.

## Doc map

| File | Covers |
|---|---|
| [01-architecture.md](./01-architecture.md) | The action-routing → modal → entity-form pipeline, dynamic form groups, the setup wizard framework, notifications. Read this first — almost everything else depends on it. |
| [02-chat-and-knowledge-base.md](./02-chat-and-knowledge-base.md) | The chat widget, deep search, document viewer/picker, virtual-filesystem browser, userspace files, audio recorder. |
| [03-forms-and-fields.md](./03-forms-and-fields.md) | Reusable form controls (`ControlValueAccessor` widgets): editable listbox, relation list, API-key picker, OAuth2 secret, content-selection filter, prompt editor, etc. Plus the i18n/field-translation system. |
| [04-infrastructure-and-auth.md](./04-infrastructure-and-auth.md) | Login, fast-setup (first-run admin account), user profile, self-service user workflows (activation/forgot-password), user integrations (API keys/MCP), auth interceptor, credentials storage. |
| [05-dashboards-and-notifications.md](./05-dashboards-and-notifications.md) | LLM usage and workflow-stats dashboards, the toast/notification system. |

## Install

```bash
npm install @Gebo.ai/reusable-ui
```

Peer dependencies (must already be present in the consuming app — see `package.json`):

```json
{
  "@angular/common": "^21.2.18",
  "@angular/core": "^21.2.18",
  "primeng": "^21.1.9",
  "primeicons": "^7.0.0",
  "primeflex": "^4.0.0",
  "@primeng/themes": "^21.0.4",
  "ng-diagram": "^1.2.4"
}
```

This is an Angular **module-based** (non-standalone-by-default) library: every feature is an
`NgModule` you import, most declaring their inner components as internal and exporting only the
top-level one. Import the specific feature module(s) you need — importing `GeboUIArchitectureModule`
alone does not pull in login, chat, dashboards, etc.

## Minimal app wiring

This is the real `AppModule` wiring from this repo (`gebo.ui/src/app/app.module.ts`), trimmed to
the parts that come from `@Gebo.ai/reusable-ui`:

```ts
import { HTTP_INTERCEPTORS, HttpClient, provideHttpClient, withInterceptorsFromDi } from "@angular/common/http";
import { BASE_PATH } from '@Gebo.ai/gebo-ai-rest-api';
import {
  AuthInterceptor, GeboAIFieldTranslationContainerModule, GeboAIModulesModule,
  GeboAINotificationsModule, GeboUIArchitectureModule, ApplicationMenuProviderService,
  GeboUIEntityFormsLauncherService, GeboUIEntityFormsLauncherByInjectionService,
  GeboUIActionRoutingService, LoginModule, FastSetupModule,
  GeboAIUserProfileModule, GeboAIUserIntegrationsModule, GeboBackendListService
} from "@Gebo.ai/reusable-ui";

@NgModule({
  imports: [
    LoginModule,
    FastSetupModule,
    GeboUIArchitectureModule,          // action routing + modal + entity-forms-launcher + desktop shell
    GeboAIUserProfileModule,
    GeboAIUserIntegrationsModule,
    GeboAINotificationsModule.forRoot(), // MUST be forRoot() exactly once, at the app root
    GeboAIModulesModule.forRoot(),       // MUST be forRoot() exactly once, at the app root
    GeboAIFieldTranslationContainerModule.forRoot(), // MUST be forRoot() exactly once, at the app root
  ],
  providers: [
    GeboBackendListService,
    GeboUIActionRoutingService,
    { provide: BASE_PATH, useFactory: getBaseUrl }, // your backend base URL
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: ApplicationMenuProviderService, useClass: AppMenuProviderService }, // your own top-nav menu
    { provide: GeboUIEntityFormsLauncherService, useClass: GeboUIEntityFormsLauncherByInjectionService },
    provideHttpClient(withInterceptorsFromDi()),
  ]
})
export class AppModule {}
```

Then, once in the root template (typically inside `GeboAIDesktopComponent`'s own template, or
next to it):

```html
<gebo-ui-entities-forms-launcher-component></gebo-ui-entities-forms-launcher-component>
<gebo-ai-display-messages [layer]="'GLOBAL'"></gebo-ai-display-messages>
```

See [01-architecture.md](./01-architecture.md) for what these two lines actually do.

## Known rough edges worth knowing before you rely on something

- `GeboAIStringListComponent` (`gebo-ai-strings-list-component`) has an unfinished `confirmValue()` — it reads the form array but never propagates the value out via `ControlValueAccessor`. Treat as WIP.
- `GeboAIPromptWizardComponent`'s `doGeneratePrompt()` body is empty — "AI-assisted prompt generation" isn't wired to a backend call yet.
- `GaboAIAclSettingsComponent` (`acl-settings-component/`) is a stub with an empty `ngOnInit()`, and — like `editable-multiselect-component`, `view-table`, `web-viewer` — is **not re-exported from `public-api.ts`**, so it isn't importable from the published package even though the source exists.
- `GeboAIModulesService.getConfig().sharepointModuleEnabled` is derived from the wrong module-id constant internally (reads `SHARED_FILESYSTEM_MODULE` instead of `SHAREPOINT_MODULE`) — don't rely on that one flag.
- `GeboAITranslableDirective` (`[gebo-translable]`) has no functional implementation yet.
