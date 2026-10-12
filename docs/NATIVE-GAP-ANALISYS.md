# Native-image gap analysis — LLM / agent microservices

Companion to [`NATIVE-IMAGE-MICROSERVICES.md`](./NATIVE-IMAGE-MICROSERVICES.md),
which classified 17 services as native-candidates and 5 as blocked. This document
analyses **what it would take to unblock the 5**: `brain`, `vectorizator`,
`graphicator`, `fulltextor` (the LLM/agent/embedding services).

## TL;DR

The picture is more favourable than the raw dependency scan suggested. There are
only **two genuine hard blockers**, and each affects fewer services than it first
appears:

| Service | ByteBuddy (agents) | In-process ONNX/DJL | Real situation |
|---|---|---|---|
| `vectorizator` | no | **yes — it hosts the model** | The one true JNI blocker. Keep it on the JVM. |
| `graphicator` | no | no (delegates to `vectorizator`) | ONNX/DJL is **transitive dead weight** → excludable → native-eligible after metadata work |
| `fulltextor` | no | no (delegates to `vectorizator`) | Same — the easiest of the four |
| `brain` | **yes** | no (delegates via REST) | Blocked by the **agent codegen**, not by ONNX |

So the two gaps to solve are: **(1)** the agents' runtime class generation
(affects `brain`), and **(2)** in-process ONNX/DJL, which is real for **only
`vectorizator`** — for the others it is a transitive dependency they never invoke
and can exclude.

---

## Gap 1 — Agent framework generates classes at runtime (ByteBuddy)

**Affects:** `brain` (the only service that pulls the agents modules at compile
scope).

### What it does
Two sites define brand-new classes at runtime:

- `gebo.architecture.agents.standard/.../standardtools/NativeSearchServiceWrapperTool.java`
  — `toTool()` builds two subclasses via
  `new ByteBuddy().subclass(parameterizedType(...)).make().load(classLoader)`,
  reifying `WrappedNativeSearcher<X>` and `NativeSearchParam<X>` where
  `X = wrapped.getNativeSearchDataStructureType()` comes from the configured
  native-search backend.
- `gebo.architecture.agents.abstraction.layer/.../GBaseRoutingNetworkAgentService.java`
  — for each coordinated peer, builds a subclass reifying
  `TargetAgentEnvelope<peerInputType>`, where `peerInputType` comes from the
  agents-network config/DAO.

### Why it's needed (and why it's a hard blocker)
The generated class exists only to carry a **non-erased generic type** through
Java's erasure. Spring AI's tool-callback path (`ToolCallbackDeclarationUtil` →
`org.springframework.ai.util.json.schema.JsonSchemaGenerator.generateForType(...)`
and `FunctionToolCallback`) needs a concrete `Class` to **(a)** generate the tool's
JSON input schema and **(b)** deserialize the LLM's tool-call arguments back into
the parameter object. A raw `NativeSearchParam.class` loses the concrete `X`; the
reified subclass restores it via `getGenericSuperclass()`.

Because `X` / `peerInputType` come from runtime config and a **new class is
defined at runtime**, this violates GraalVM's closed-world assumption. It cannot be
"configured away" with reachability metadata — the class must exist at build time.

### Options to fix (in order of preference)

1. **Build-time reification (recommended).** Keep the exact runtime path
   (`FunctionToolCallback` + `JsonSchemaGenerator`) but move class generation from
   runtime to **build time**: enumerate every possible type argument (all
   `INativeSearchService` data-structure types, all agent input types — these are
   compiled classes discoverable at build) and generate the reified subclasses
   during the build (ByteBuddy can `saveIn(...)`/`toJar(...)` `.class` files, or an
   annotation processor / Maven codegen step). At runtime, look the pre-generated
   class up from a registry keyed by the type instead of calling `.load()`.
   - Prerequisite: the set of type arguments must be **closed and enumerable at
     build time**. It appears to be (search backends and agent input types are
     concrete registered classes), but this must be confirmed.
2. **Custom tool callback with a Jackson `JavaType`.** Replace `FunctionToolCallback`
   with a small custom `ToolCallback` that (a) generates the schema from a
   hand-built `java.lang.reflect.ParameterizedType` (`JsonSchemaGenerator.generateForType`
   accepts a `Type`) and (b) deserializes arguments with a constructed Jackson
   `JavaType` instead of a `Class`. Removes ByteBuddy entirely; moderate refactor
   of the tool/routing wiring.
3. **Concrete DTOs instead of generics.** Replace `NativeSearchParam<X>` /
   `TargetAgentEnvelope<X>` with non-generic per-backend param classes. Most
   invasive; removes the erasure problem at the source.

**Effort:** medium. Option 1 is the least behaviourally risky; option 2 is the
cleanest long-term.

---

## Gap 2 — In-process ONNX / DJL embeddings (JNI)

**Genuinely affects only `vectorizator`.** The embedding computation lives in
`gebo.ragsystem.parent/gebo.ragsystem.content.vectorizator` (`GEmbedderImpl`,
`IGEmbedder`, the `GEmbedding*MessageReceiver` beans). `vectorizator` pulls
`org.springframework.ai:spring-ai-transformers` → DJL (`ai.djl:*`, HuggingFace
tokenizers, **PyTorch engine**) → ONNX Runtime (`onnxruntime`).

### Why it's native-hostile
- DJL **discovers its engine at runtime** and **extracts/downloads native
  libraries** on first use (libtorch is hundreds of MB) — heavy runtime dynamism
  and file materialization, incompatible with a closed native image.
- The engines use **JNI**: native image can support JNI, but each library needs
  `jni-config`, the `.so` bundled next to the binary, and the library's own
  reflection/resource metadata. For DJL + PyTorch this is effectively
  intractable; pure ONNX Runtime alone is *conceivable* but still costly.

### The key finding: only the model host actually uses it
`graphicator` (`gebo.ragsystem.content.graphrag_processor`) and `fulltextor`
(`gebo.ragsystem.content.fulltext.processor`) have **no in-process `EmbeddingModel`
/ ONNX / DJL usage** — they delegate embedding work to `vectorizator` over
RabbitMQ. `brain` consumes the RAG system through `gebo.ragsystem.client.rest`
(remote), so it too delegates. For all three, `spring-ai-transformers` / DJL /
ONNX arrives **transitively** (not declared in their poms) and is never invoked.

### Options
1. **Contain, don't convert (recommended).** Keep **`vectorizator`** — the single
   model host — on the JVM/Jib flow. Do **not** try to native-compile it. It is the
   right place to isolate the JNI/model runtime.
2. **Exclude the transitive dep from the delegating services.** Add Maven
   exclusions so `graphicator` / `fulltextor` / `brain` drop
   `spring-ai-transformers` + `ai.djl:*` + `onnxruntime` from their classpath.
   Since they never call it, this removes the JNI surface entirely and turns
   `graphicator` / `fulltextor` into native-candidates. **Verify** afterwards that
   no shared auto-configuration tries to instantiate a `TransformersEmbeddingModel`
   bean in these services (delegating services shouldn't wire it, but confirm with
   a build + boot).
3. **Remote embedding model (product change).** Replace local ONNX embeddings with
   a hosted embedding API (via the non-JNI Spring AI clients). Removes JNI from
   `vectorizator` too, but drops offline/local-model capability — a product
   decision, out of scope here.

**Effort:** low for the exclusion (options 2); `vectorizator` itself stays JVM.

### Dead code: delete the `gebo.llms.onxx-embeddings` modules

Two modules under `gebo.llms.parent` are an **unused** ONNX-embedding
implementation and can be **removed outright** (no runtime scenario uses them):

- `gebo.llms.parent/gebo.llms.onxx-embeddings` — declares
  `org.springframework.ai:spring-ai-transformers` (→ DJL + ONNX Runtime), i.e. it
  is one of the two sources of the JNI dependency (the other being
  `gebo.ragsystem.parent`).
- `gebo.llms.parent/gebo.llms.onxx-embeddings.controllers` — its REST controllers.

They are wired into the build only through the LLM starters
(`gebo.llms.starter`, `gebo.llms.controllers.starter`), not through the live
embedding path: the actual embedder (`gebo.ragsystem.content.vectorizator`'s
`GEmbedderImpl`) resolves its model through the `IGConfigurableEmbeddingModel`
abstraction, independent of these modules.

**Impact on this analysis:** deleting them removes one `spring-ai-transformers`
/ DJL / ONNX source from every service that pulls the LLM starters — shrinking
the JNI surface that Gap 2 has to exclude and confirming that only
`vectorizator` genuinely hosts the model. It also removes their REST controllers
from the OpenAPI/stub surface. (Validate by a full build + boot before relying on
it, consistent with the non-goals note below.)

---

## Gap 3 — Broader reachability surface (metadata, not blockers)

Even after the two hard gaps, these services carry a large reflective surface that
needs GraalVM reachability metadata (Spring AOT + hand-written hints + a tracing-
agent run). Not blockers, but real work — and larger here than for the simple
connectors:

- **Full Spring AI RAG stack** — chat clients, tool calling, advisors (`brain` pulls
  ~22 Spring AI artifacts). Spring AI 2.x native support is improving but not
  complete; expect gaps.
- **Vector store clients** — Qdrant (gRPC → Netty + protobuf reflection), OpenSearch,
  Redis, Mongo Atlas (`gebo.ragsystem.vectorstores`).
- **MCP / A2A SDKs** — Gson-based wire types (separate reflection metadata from
  Jackson).
- **Neo4j** (`graphicator`, `brain`) — `@Node` mapping reflection, on top of Mongo
  `@Document`.
- **Jackson-3 polymorphism** (`@JsonTypeInfo`/`@JsonSubTypes`) and the config-driven
  `Class.forName` in the RabbitMQ message codec (shared `RuntimeHintsRegistrar`, see
  the companion doc).

---

## Per-service verdict & recommended order

1. **`vectorizator` — keep on the JVM.** It is the model host; native compilation
   would require solving DJL/PyTorch/ONNX JNI, which is not worth it. It is the
   correct place to isolate that runtime. **Not a native target.**
2. **`fulltextor` — easiest to unblock.** Exclude the transitive ONNX/DJL deps
   (Gap 2, option 2); then only the reachability surface (Gap 3) remains. No
   ByteBuddy, no Neo4j. **Best first candidate among the five.**
3. **`graphicator` — medium.** Same ONNX exclusion, plus Neo4j + Qdrant reachability
   metadata (Gap 3).
4. **`brain` — hardest, do last.** Requires **Gap 1** (refactor the two ByteBuddy
   sites to build-time reification / custom callbacks) **and** the ONNX exclusion
   (Gap 2) **and** the full RAG/agents/MCP/A2A/vector-store reachability surface
   (Gap 3, the largest here). Lowest ROI; tackle only after the pattern is proven
   on the simpler services.

## Recommended sequencing
1. **Delete the unused `gebo.llms.onxx-embeddings` (+ `.controllers`) modules**, then
   solve **Gap 2 (option 2)** — the dependency exclusion — and prove `fulltextor`
   native using the pilot toolchain from the companion doc. Removing the dead
   modules eliminates one ONNX/DJL source up front; the exclusion clears the rest.
   Fast win that validates the approach on an LLM-adjacent service.
2. Then `graphicator` (adds Neo4j/Qdrant metadata).
3. Solve **Gap 1** (the ByteBuddy refactor) as a standalone change to the agents
   modules — it is independently useful and testable on the JVM before any native
   build.
4. Attempt `brain` only once 1–3 are done; expect it to remain the most effort for
   the least relative gain (it is I/O- and LLM-latency-bound, so the startup/memory
   payoff matters less than for lightweight connectors).

## Non-goals
- Making `vectorizator`'s local-embedding runtime native (keep it JVM).
- The monolith (inherits every gap).
- Exclusions and refactors above must be validated by build + boot + a functional
  round-trip before being relied upon; treat the "delegates, so excludable" claims
  as verified-by-inspection, not yet verified-by-build.
