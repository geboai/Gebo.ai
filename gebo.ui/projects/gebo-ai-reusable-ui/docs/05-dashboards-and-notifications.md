# Dashboards & notifications

## Notifications (toasts) — `GeboAINotificationsModule`

Import with `.forRoot()` **exactly once**, at the app root — it provides PrimeNG's `MessageService`
singleton:
```ts
imports: [GeboAINotificationsModule.forRoot()]
```
Feature modules elsewhere just import the plain `GeboAINotificationsModule` (no `forRoot()`) if
they need the components below.

Place a sink once in the app shell (global layer) and once per modal host if you open dialogs
above which toasts still need to render:
```html
<gebo-ai-display-messages [layer]="'GLOBAL'"></gebo-ai-display-messages>
<!-- inside a dialog template: -->
<gebo-ai-display-messages [layer]="'DIALOG'"></gebo-ai-display-messages>
```
`NotificationLayerEnum = 'GLOBAL' | 'DIALOG'` — `DIALOG` toasts get a computed z-index above any
currently-open PrimeNG dialog/overlay (handled internally by `ToastZIndexService`).

Most feature components in this library expose a `userMessages: ToastMessageOptions[]` (or
`GUserMessage[]`) array — wire it to the forwarder:
```html
<gebo-ai-notifications [messages]="userMessages" [layer]="'GLOBAL'"></gebo-ai-notifications>
```
`<gebo-ai-notifications>` is headless (no visible template) — it just diffs `messages` on change and
forwards new entries to `GeboAIRootNotificationService.addMessages(...)`, which translates them
(via `GeboAITranslationService`) before they reach `<gebo-ai-display-messages>`.

To push a toast programmatically instead:
```ts
constructor(private notify: GeboAIRootNotificationService) {}
this.notify.addMessage('MyModule', 'MyEntity', { severity: 'success', summary: 'Saved' });
```

## System messages — `<gebo-ai-system-messages>`

Persistent notifications published by backend modules, as opposed to the transient toasts above.
The backend (`gebo.architecture.system.messages`) routes them by role: administrators receive the
messages for `ADMINS` and `ALL`, the other users those for `USERS` and `ALL`.

The component is part of `GeboAINotificationsModule` and is already placed in the application shell
(`<gebo-ai-desktop>`) for logged-in users. It loads the messages right after login and then every
`refreshSeconds` (default 300), shows them most severe first, and offers a dismiss button on the
dismissible ones. A dismissed message comes back when its publisher changes its content.

```html
<gebo-ai-system-messages [refreshSeconds]="300"></gebo-ai-system-messages>
```

A backend module publishes through `IGSystemMessagesService`; publishing again under the same source
and key replaces the message, so a status that changes over time stays one message:

```java
systemMessagesService.publish(new SystemMessagePublication("backup", "last-run",
        MsgServerity.warn, "Last backup failed", detail, SystemMessageAudience.ADMINS,
        true /* dismissible */, null /* until retracted */));
systemMessagesService.retract("backup", "last-run");
```

## LLM usage dashboards — `GeboAILLMSUsageDashboardModule`

Two components, no inputs on either — scoping (which user's data) is determined entirely by which
component you use, not by a bound `@Input`:

```html
<!-- sees ALL users' LLM traffic; filter dropdown includes a "username" dimension -->
<gebo-ai-llms-usage-admin-dashboard></gebo-ai-llms-usage-admin-dashboard>

<!-- implicitly scoped to the current authenticated user by the backend -->
<gebo-ai-llms-usage-user-dashboard></gebo-ai-llms-usage-user-dashboard>
```

Each renders two tabs ("This month, daily" and "Monthly history") with Chart.js token-consumption
and latency charts, plus per-tab dropdown filters (`providerId`, `username` [admin only], `model`,
`callerStack`, `modelType`, `year`, `month`) whose options come pre-computed from the backend
response — there is no free-form date-range picker.

## Workflow stats dashboard — `GeboAIWorkflowStatsDashboardModule`

Only an admin variant exists (no user-level component/service in this library):
```html
<gebo-ai-workflow-stats-admin-dashboard></gebo-ai-workflow-stats-admin-dashboard>
```
Same two-tab/filter shape as the LLM dashboard, but dimensions are
`knowledgeBaseReference`, `projectReference`, `projectEndpointReference`, `workflowType`,
`workflowId`, `workflowStepId`, `year`, `month`, and charts are "Documents" (input/processed/sent/
discarded/errors) and "Chunks & Tokens" processed.

Both dashboard families share one abstract base (`BaseLLMSUsageDashboardComponent` /
`BaseWorkflowStatsDashboardComponent`, both `@Directive()`) that does all chart-building/filtering;
the concrete components only differ in which backend controller they call
(`LlmsUsageAdminLevelControllerService` vs `LlmsUsageUserLevelControllerService` vs
`WorkflowStatsAdminLevelControllerService`). If you need a dashboard scoped some other way, subclass
the relevant base and implement `executeDrillDown(filter)`.
