# gebo.js-plugins/brain

Plain-JavaScript client stub for the `brain` microservice, generated with the same
`swagger-codegen-maven-plugin` setup used across this repo (see the sibling
`gebo.js-plugins` module and `gebo.api.clients/gebo.microservices.clients.parent`).

## Regenerating

The brain microservice must be running standalone with springdoc enabled and its
live spec reachable at `http://localhost:13001/brain/v3/api-docs` (build it with
the `swagger-on` Maven profile from
`gebo.apps.parent/gebo.microservices.apps.parent/brain.gebo.ai`). Then:

```
mvn generate-sources -P generate-rest-api
```

This overwrites `brain-ai-js-client/`.

## Passing the OAuth2 / JWT token

Every brain endpoint requires an authenticated caller
(`GeboSecurityConfig`/`GeboAISecurityConfig` gate everything but the swagger/allowed
URLs), but the OpenAPI spec brain publishes has no `securitySchemes` entry, so
swagger-codegen does not wire up any auth plumbing automatically — `authNames` is
always `[]` in every generated `*ControllerApi.js` method.

The reliable way to attach a bearer token is the client's `defaultHeaders`, which
`ApiClient.callApi()` always applies to every outgoing request regardless of
`authNames`:

```js
import { ApiClient, KnowledgeBaseControllerApi } from './brain-ai-js-client/src/index.js';

// set once, applies to every subsequent call made through this ApiClient instance
ApiClient.instance.defaultHeaders['Authorization'] = `Bearer ${currentOAuth2OrJwtToken}`;

const api = new KnowledgeBaseControllerApi();
const knowledgeBases = await api.getKnowledgeBases();
```

If you need per-call tokens (e.g. multiple concurrent users on one server process),
construct a dedicated `ApiClient` per caller instead of using the shared
`ApiClient.instance` singleton:

```js
import { ApiClient, KnowledgeBaseControllerApi } from './brain-ai-js-client/src/index.js';

const apiClient = new ApiClient();
apiClient.defaultHeaders['Authorization'] = `Bearer ${token}`;

const api = new KnowledgeBaseControllerApi(apiClient);
```
