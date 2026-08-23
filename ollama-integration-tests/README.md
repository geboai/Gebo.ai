# ollama-integration-tests — the local-inference suite

These tests need a **reachable ollama server with the models already pulled**.
That is a machine-local, GPU-sized prerequisite no hosted CI runner provides, so
this tree is kept out of the pipeline suite: everything that a Jenkins / GitHub
Actions job can run unattended lives in
[`../integration-tests`](../integration-tests) instead.

Like that suite, this pom is **not** in the repository root `<modules>`, so a
plain root build never descends into it.

## Running

Prerequisites:

* **ollama** serving on `OLLAMA_URL` with the chat and embedding models pulled;
* a **docker daemon** — Mongo, Neo4j, OpenSearch and Qdrant come up as
  Testcontainers.

```bash
ollama pull qwen3:14b
ollama pull mxbai-embed-large:latest

mvn install                                   # repository root, once
mvn -f ollama-integration-tests/pom.xml test  # then this suite
```

## Environment overrides

Defaults live in `ollama-integration-tests/src/test/resources/application.yml`.

| Variable | Default | Purpose |
| --- | --- | --- |
| `OLLAMA_URL` | `http://localhost:11434` | Where the ollama server is |
| `OLLAMA_CHAT_MODEL` | `qwen3:14b` | Chat model to autoconfigure as default |
| `OLLAMA_EMBEDDING_MODEL` | `mxbai-embed-large:latest` | Embedding model to autoconfigure as default |
| `GEBO_ADMIN_USERNAME` | `mymail@gmail.com` | Admin account the monolith self-registers at boot and the tests log in with |
| `GEBO_ADMIN_PASSWORD` | `mypassword` | as above |

Unlike the cloud suite, the admin account and the LLM provider are set up
declaratively here (`ai.gebo.sysinit.admin.config` /
`ai.gebo.sysinit.llms.config`) rather than through a `FullSetupSecret`: there is
no vendor API key to keep out of the repository.

## What it covers

`OllamaSetupAndIntegrationTest` exercises chat-session state against a real
local model: ingesting documents into a session, then checking that the full
session state keeps the retrieved documents and that the shrinked session state
is consolidated (both from real chat traffic and from a synthetic, pre-injected
history).
