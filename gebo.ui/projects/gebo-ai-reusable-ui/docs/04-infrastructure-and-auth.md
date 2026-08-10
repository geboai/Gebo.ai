# Login, setup, users & auth infrastructure

## First-run admin setup — `FastSetupModule`

`<gebo-ai-fast-setup>` — creates the first admin account + accepts the licence agreement. Routed
internally at `ui/setup`. No inputs; self-contained.

## Login — `LoginModule`

Import once at the app root. Registers routes `ui/login`, `ui/oauth2-land`, `ui/logged`,
`ui/logout`, `ui/reloader`; exports only `<gebo-ai-login-component>` (`gebo-ai-login-component`) for
direct use, the rest are internal route targets.

```ts
import { LoginModule } from "@Gebo.ai/reusable-ui";
@NgModule({ imports: [LoginModule] })
export class AppModule {}
```

`LoginService` (`providedIn: 'root'`) is the service backing it — inject it wherever you need
current-session state:
```ts
constructor(private loginService: LoginService) {
  this.loginService.logged.subscribe(userInfo => { /* userInfo: UserInfo | undefined */ });
}
```
Key methods: `login({username, password})`, `logout()`, `loadUserProfile()`, `changePassword(...)`,
`getOauth2LoginOptions()`, `loginWithOauth2(...)`. It also self-schedules a JWT-renewal poll every
60s while credentials exist — you don't need to call anything to keep the session alive.

## Auth wiring (required for any authenticated backend call)

```ts
import { AuthInterceptor, GeboBackendListService } from "@Gebo.ai/reusable-ui";
import { HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi } from "@angular/common/http";

providers: [
  GeboBackendListService,                 // registers your BASE_PATH as "a Gebo backend" automatically
  { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
  provideHttpClient(withInterceptorsFromDi()),
]
```
`AuthInterceptor` attaches the stored auth header to any request whose URL is a known Gebo backend
(per `GeboBackendListService.isGeboBackend`) and isn't a registered "public" URL; on `401` it clears
auth and redirects to `/ui/login`; on `500` it pops a PrimeNG confirm dialog pointing users to
`assistence@gebo.ai`.

`gebo-credentials.ts` is the low-level storage layer underneath `LoginService`/`AuthInterceptor` —
`getAuth()`, `getAuthHeader()`, `saveAuth(value)`, `resetAuth()` — persisted to `localStorage` under
key `gebo.ai.credentials`. Use it directly only if you're writing a custom HTTP call outside the
generated REST clients and need the same bearer header.

If a feature module talks to a **second** Gebo microservice base URL, register it so the
interceptor still attaches auth headers:
```ts
constructor(backends: GeboBackendListService) {
  backends.addBackendServicesBaseUrl('https://my-other-service.internal');
  backends.addBackendServicesPublicUrl('/api/public/health'); // relative, no-auth endpoint
}
```

## Current user profile — `GeboAIUserProfileModule`

`<gebo-ai-current-user-profile>` (`gebo-ai-current-user-profile`) — displays the logged-in user's
profile, includes a "change password" dialog internally. No inputs; loads via `LoginService.loadUserProfile()`.

## Self-service activation / forgot-password — `GeboAIUserWorkflowsModule`

Lazy-load the whole module as a route (used exactly this way in this repo's `app.module.ts`):
```ts
{ path: 'ui/user-workflows', loadChildren: () => import('@Gebo.ai/reusable-ui').then(m => m.GeboAIUserWorkflowsModule) }
```
Or use the two exported components directly:
- `<gebo-ai-user-start-workflows>` — request form (email + type: `ACTIVATION` | `FORGOTPASSWORD`), gated by server config (`UserWorkflowsControllerService.getUserWorkflowsConfig()`).
- `<gebo-ai-user-land-workflows>` — consumes the emailed ticket (from route/query param `ticket`), sets the new password, auto-redirects to `/ui/login` after success.

## User integrations (self-service API keys / MCP) — `GeboAIUserIntegrationsModule`

`<gebo-ai-user-integrations>` — lets a logged-in user see which MCP servers they can access and
manage their own generated API keys (create with expiration preset, list, delete). No inputs;
gated internally by two feature-flag checks. Routed at `ui/user-integrations`.

## App shell — `GeboUIArchitectureModule` → `<gebo-ai-desktop>`

`GeboAIDesktopComponent` (`gebo-ai-desktop`) is the top app-chrome: mega-menu + auth state. Menu
content is supplied by your own implementation of the abstract `ApplicationMenuProviderService`:
```ts
@Injectable()
export class AppMenuProviderService extends ApplicationMenuProviderService {
  getMenuItems(userInfo?: UserInfo): Observable<MegaMenuItem[]> { /* return your nav */ }
}
// providers: [{ provide: ApplicationMenuProviderService, useClass: AppMenuProviderService }]
```
```html
<gebo-ai-desktop [version]="'1.0.0'"></gebo-ai-desktop>
```
It's `@Optional()`-injected inside `GeboAIDesktopComponent`, so omitting the provider just yields an
empty menu rather than an error.

## Setup wizard panel

Covered in [01-architecture.md §6](./01-architecture.md#6-setup-wizard-framework) — this is where
first-run/ongoing configuration sections (LLM providers, integrations, etc.) get registered and rendered.
