# gebo.microservices.clients.java.factory

The one entry point for a Java consumer of the Gebo.ai stubs: give it the base
url of an installation, get every generated `ApiClient` already pointing at the
right address — **monolith and microservices alike**.

```java
GeboMicroservicesClientsFactory clients = GeboMicroservicesClientsFactory.of("https://gebo.example.com");
clients.addDefaultHeader("Authorization", "Bearer " + token);

ChatModelsControllerApi chatModels = new ChatModelsControllerApi(clients.brain());
UsersAdminControllerApi users      = new UsersAdminControllerApi(clients.heimdall());
```

## Why it exists

Each generated client hardcodes the address its spec was scraped from
(`http://localhost:13001/brain`, `http://localhost:13018/heimdall`, ...). Which
suffix a service actually answers on is a property of the deployment, not of the
client:

| installation | brain answers at | heimdall answers at |
|---|---|---|
| microservices (gateway) | `<baseUrl>/brain` | `<baseUrl>/heimdall` |
| monolithic | `<baseUrl>` | `<baseUrl>` |

So the factory asks the installation itself — `GET
<baseUrl>/public/ClientsTopologyProviderController`, which both shapes publish at
the same relative url — and applies the answer to every `ApiClient`. The caller
knows ONE url and nothing else changes between targets.

## Behaviour

- The topology call happens **once**, lazily, on the first accessor, and is
  cached. `refresh()` re-reads it and re-bases every client already handed out.
- Each `ApiClient` is created once and **shared**, so mutating the instance an
  accessor returns sticks for every later caller. Pass the factory around, not
  the individual clients.
- `addDefaultHeader(name, value)` applies to every client, the ones not created
  yet included — this is where an `Authorization` bearer belongs.
- If the endpoint cannot be read the factory degrades to the monolithic shape
  (every client points at the base url) and logs a warning. A server predating
  the endpoint answers 404, and those were all monoliths — so the fallback is
  right there, and wrong only against a gateway, where the warning says so.
- Asking for a service a microservices installation does not publish throws
  `IllegalStateException` naming it, rather than handing out a url that would
  404 later. `eureka()` is the standing example: the registry sits inside the
  deployment and is never routed at the edge.

## Accessors

One per generated client, each returning that service's **own** `ApiClient`
type — they are 21 unrelated classes, one per
`gebo.microservices.api.client.<name>.invoker` package, so the compiler rejects
handing brain's client to a heimdall API:

`gateway()` `eureka()` `heimdall()` `brain()` `vectorizator()` `graphicator()`
`chunker()` `git()` `filesystem()` `uploads()` `userspace()` `sharepoint()`
`confluence()` `jira()` `awsS3()` `googledrive()` `mcpclient()` `webdav()`
`integration()` `fulltextor()` `tyr()`

Plus `baseUrlFor(serviceId)`, `topology()`, `baseUrl()` and `resolver()` for
anything the accessors do not cover.

## Not generated

Unlike the per-service `<name>.gebo.ai.java.client` modules this one is
hand-written: it has no `generate-rest-api` profile and regenerating the stubs
does not touch it. What it *does* depend on is that every generated `ApiClient`
keeps `setBasePath(String)` and `addDefaultHeader(String, String)` — the
`GeboMicroservicesClientsFactoryTest` pins exactly that.
