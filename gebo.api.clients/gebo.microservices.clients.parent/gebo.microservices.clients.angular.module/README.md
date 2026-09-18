# @Gebo.ai/microservices-clients

The one Angular import that connects an app to a Gebo.ai installation, whether it
is a **monolith** or a **microservices** deployment behind the gateway.

```ts
@NgModule({
  imports: [
    BrowserModule,
    HttpClientModule,
    MicroservicesClientsModule.forRoot({ baseUrl: environment.geboBaseUrl }),
  ],
})
export class AppModule {}
```

From there every generated service is injectable and already based:

```ts
constructor(
  private chatModels: ChatModelsControllerService,   // @Gebo.ai/brain
  private users: UsersAdminControllerService,        // @Gebo.ai/heimdall
) {}
```

## Why it exists

Each generated client hardcodes the address its spec was scraped from
(`http://localhost:13001/brain`, `http://localhost:13018/heimdall`, ...) and
exposes a `BASE_PATH` injection token to override it. Which suffix a service
actually answers on is a property of the deployment, not of the client:

| installation | brain answers at | heimdall answers at |
|---|---|---|
| microservices (gateway) | `<baseUrl>/brain` | `<baseUrl>/heimdall` |
| monolithic | `<baseUrl>` | `<baseUrl>` |

So the module asks the installation itself — `GET
<baseUrl>/public/ClientsTopologyProviderController`, which both shapes publish at
the same relative url — and provides all 21 `BASE_PATH` tokens from the answer.
The app is configured with ONE url and nothing else changes between targets.

## Requirements

- `HttpClient` provided (`HttpClientModule` or `provideHttpClient()`), which the
  generated `ApiModule`s already require.
- The 21 `@Gebo.ai/*` client packages installed: they are **peer dependencies**,
  this library only wires them.
- Imported once, in the root module.

## Behaviour

- The topology is read by an app initializer, so it is in place before Angular
  constructs the first generated service (each reads its base path in its
  constructor and never again). A client injected from an initializer registered
  *before* this module's is the one case that can miss it — it falls back to the
  bare base url and warns.
- If the endpoint cannot be read the module degrades to the monolithic shape
  (every client points at the base url) and warns. A server predating the
  endpoint answers 404, and those were all monoliths — so the fallback is right
  there, and wrong only against a gateway, where the warning says so.
- Asking for a service a microservices installation does not publish throws,
  lazily, when that client is first injected — rather than handing out a url that
  would 404 later. `eureka` is the standing example: the registry sits inside the
  deployment and is never routed at the edge.

## Also exported

`GeboClientsTopologyService` — inject it to read the resolved topology
(`info`, `baseUrl`) or to resolve a base path yourself (`basePathFor(serviceId)`),
and `GeboMicroservices` for the service id constants.
