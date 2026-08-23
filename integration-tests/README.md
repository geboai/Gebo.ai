# integration-tests — the cloud-only suite

Every module here must be runnable unattended by a hosted CI runner (Jenkins,
GitHub Actions) given nothing but

* a **docker daemon** — the suite starts Mongo, Neo4j, OpenSearch and Qdrant
  itself as Testcontainers, so nothing has to be pre-installed on the runner;
* **credentials injected as environment variables / CI secrets**.

That is the scope rule: pure cloud providers and pure cloud (or otherwise
remotely reachable) systems only. Tests that need a locally installed inference
server live in [`../ollama-integration-tests`](../ollama-integration-tests) and
are never part of a pipeline run.

This tree is **not** in the repository root `<modules>`, so a plain root build
never descends into it:

```bash
mvn install                            # repository root, once
mvn -f integration-tests/pom.xml test  # then this suite
```

## Modules

| Module | What it proves | Needs |
| --- | --- | --- |
| `full-setup-and-use-integration-tests` | The whole monolith end to end: installation setup → LLM setup → knowledge base → publication job → embedding → RAG threshold autotune → chatting through the default pipeline. **This is the CI smoke test.** | `FullSetupSecret` |
| `vectorstores-integration-tests` | Advanced vector-store queries against Qdrant with real OpenAI embeddings | `OPENAI_API_KEY`, `OPENAI_USER` |
| `atlassian-confluence-integration-tests` | Confluence cloud + on-premise ingestion and search | `CONFLUENCE_CLOUD_*`, `CONFLUENCE_ONPREMISE_*` |
| `atlassian-jira-integration-tests` | Jira cloud ingestion | `JIRA_CLOUD_SPACE_URL`, `JIRA_CLOUD_USER`, `JIRA_CLOUD_API_KEY` |
| `sharepoint-integration-tests` | Sharepoint online ingestion | `SHAREPOINT_CLIENT_ID`, `SHAREPOINT_TENANT_ID`, `SHAREPOINT_SECRET_KEY`, `SHAREPOINT_BASE_URL` |
| `heavy-workload-integration-tests` | Ingestion throughput with fake LLMs — no external provider at all | `GEBO_VECTORIZABLE_FOLDERS` |

## `FullSetupSecret`

One JSON document in a single environment variable, read by
`AbstractVendorSetupAndUseTest#executeSystemSetupBySecret()`. It carries the
admin account to register, the LLM vendor to autoconfigure and the subsystems to
set up:

```json
{
  "systemSetup": {
    "username": "admin@example.com",
    "password": "<password>",
    "vendorId": "openai",
    "vendorUser": "<vendor user>",
    "vendorApiKey": "<vendor api key>",
    "host": "localhost",
    "port": 12999,
    "models": [
      { "role": "DEFAULT_CHAT",      "modelCode": "<chat model>" },
      { "role": "DEFAULT_EMBEDDING", "modelCode": "<embedding model>" },
      { "role": "INTERNAL_SERVICES", "modelCode": "<chat model>" }
    ]
  },
  "subsystems": [
    { "product": "TAVILY_SEARCH", "apiKey": "<key>" }
  ]
}
```

`models` may be omitted, in which case the vendor's own default presets are
used. `host`/`port` are overridden at runtime with the port the in-process
server actually bound to, so they never collide with another Gebo.ai instance.

### Web search: exactly one provider

Since the multi-provider refactoring the monolith exposes a provider's
web-search tool to the LLM only while that provider holds stored credentials, so
**exactly one** provider is meant to be active. The harness applies the same rule as the
admin wizard: it wipes every provider, stores the one named by the secret, and
then asserts that only that one reports itself set up. Declaring more than one
`*_SEARCH` product fails the setup on purpose.

| `product` | Fields |
| --- | --- |
| `GOOGLE_SEARCH` | `apiKey`, `id` (the Programmable Search engine id) |
| `TAVILY_SEARCH` | `apiKey` |
| `BRAVE_SEARCH` | `apiKey` (the subscription token) |
| `SEARXNG_SEARCH` | `basePath` (instance URL); `apiKey` optional |
| `SERPAPI_SEARCH` | `apiKey` |

The other subsystem products — `CONFLUENCE_CLOUD`, `CONFLUENCE_ONPREMISE`,
`JIRA_CLOUD`, `SHAREPOINT`, `GOOGLE_WORKSPACE` — can be declared alongside a
web-search one.

## What fails the full-setup run

The suite exists to answer one question: *is this monolith fully functional from
setup to embedding and chatting?* Only what breaks when the monolith is broken
is a hard assertion — the publication job must finish, the RAG threshold
autotune must have run and produced coefficients, and every chat interaction
must come back with a non-empty answer, a routing decision the pipeline actually
knows, and a chat context.

**Which** route the router picked is deliberately *not* a hard assertion. The
choice between a RAG answer, a deep search, a tool call or a pure-LLM answer is
a model decision that varies with the vendor, the model version and even between
two runs of the same model — pinning it would make the job flap without anything
being wrong. Mismatches against the routing decisions declared in the registered
session are logged as warnings instead. To turn them back into failures, when
the point of the run *is* to pin the router:

```
-Dai.gebo.tests.chatpipeline.strictRoutingDecisions=true
```

or `AI_GEBO_TESTS_CHATPIPELINE_STRICTROUTINGDECISIONS=true` in the environment.

## Other environment overrides (full-setup module)

Defaults live in `full-setup-and-use-integration-tests/src/test/resources/application.yml`.

| Variable | Default | Purpose |
| --- | --- | --- |
| `GEBO_TEST_OPENAI_ENABLED`, `GEBO_TEST_ANTHROPIC_ENABLED`, `GEBO_TEST_MISTRAL_ENABLED`, `GEBO_TEST_DEEPSEEK_ENABLED`, `GEBO_TEST_AZURE_OPENAI_ENABLED` | `true` | Load that vendor's module |
| `GEBO_TEST_GOOGLE_VERTEX_ENABLED`, `GEBO_TEST_HUGGINGFACE_ENABLED` | `false` | Load that vendor's module |
| `GEBO_TEST_OLLAMA_ENABLED` | `false` | Off here on purpose — this is the cloud-only suite |
| `GEBO_TEST_DEEPSEARCH_EXTERNAL_ENABLED` | `true` | Set to `false` when the job holds no web-search key |
| `GEBO_TEST_DEEPSEARCH_CHOOSE_SOURCES` | `true` | Let the caller choose deep-search sources |
| `GEBO_TEST_TOKEN_SECRET` | a throwaway key in the yml | JWT signing key for the in-process server |
| `MAVEN_HOME`, `JAVA_HOME` | empty | Toolchain of the declared maven build system (unused by the tests) |
