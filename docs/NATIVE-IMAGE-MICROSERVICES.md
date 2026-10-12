# GraalVM native images for Gebo.ai microservices

This document records which Gebo.ai services can realistically be shipped as
**GraalVM native images** (fast startup, low memory footprint) and what it takes
to get there. It exists because the agent framework generates classes at
runtime, which raised the question of whether native compilation is possible at
all.

## Background: what breaks native compilation

GraalVM native image uses a **closed-world assumption** — everything reachable
must be known at build time. Two patterns in this codebase are incompatible with
that:

1. **Runtime class generation.** The agent framework uses ByteBuddy
   (`new ByteBuddy().subclass(...).make().load(...)`) to reify generic types that
   are only known from runtime configuration, so Spring AI can build JSON tool /
   routing schemas. This happens in two places:
   - `gebo.architecture.parent/gebo.architecture.agents.standard/.../NativeSearchServiceWrapperTool.java`
   - `gebo.architecture.parent/gebo.architecture.agents.abstraction.layer/.../GBaseRoutingNetworkAgentService.java`

   Defining a class at runtime is a **hard blocker** for native image.

2. **JNI native libraries.** Local embedding models pull
   `org.springframework.ai:spring-ai-transformers`, which brings DJL and ONNX
   Runtime (`com.microsoft.onnxruntime:onnxruntime`). These load native libraries
   via JNI and do their own reflection — a well-known hard spot for native image.

Everything else people worry about here (JDK/dynamic proxies, `Class.forName`,
Jackson polymorphism, Spring Data reflection) is **configurable** with
reachability metadata — it is not a blocker.

> The **monolith** is out of scope: it aggregates everything (agents + ONNX + a
> ~500-module component-scan surface), so it inherits both blockers.

## Verdict: per-service classification

Classification was derived from each app's resolved Maven dependency tree
(`mvn -o dependency:tree`). Note: `net.bytebuddy:byte-buddy` appears on every
service but is **test-scope only** (via Mockito) — it is only a runtime concern
in `brain`.

### Native-candidates (17)

No agents module, no `spring-ai-transformers` / ONNX / DJL, plain Spring Boot jar
(no `gebo.boot` launcher), no runtime devtools:

`aws-s3`, `chunker`, `confluence`, `eureka`, `filesystem`, `gateway`, `git`,
`googledrive`, `heimdall`, `integration`, `jira`, `mcpclient`, `sharepoint`,
`tyr`, `uploads`, `userspace`, `webdav`

Most still pull the **non-JNI** slice of Spring AI (`spring-ai-commons`,
`spring-ai-model`, `spring-ai-client-chat`, `spring-ai-template-st`,
`spring-ai-tika-document-reader`) plus MongoDB and RabbitMQ; `gateway` and
`eureka` pull no Spring AI, and `eureka` no persistence. These are
reachability-metadata surface, **not** blockers.

### Blocked (5)

| Service | Reason |
|---|---|
| `brain` | **Both** — depends on both agents modules (ByteBuddy runtime codegen) **and** `spring-ai-transformers` → DJL + ONNX JNI |
| `vectorizator` | ONNX/DJL JNI (`spring-ai-transformers`) |
| `graphicator` | ONNX/DJL JNI (`spring-ai-transformers`) |
| `fulltextor` | ONNX/DJL JNI (`spring-ai-transformers`) |

`fulltextor`, `graphicator`, and `vectorizator` do **not** use the agents module
(their ByteBuddy stays test-scope); ONNX/DJL is their only blocker.

## Current build reality

- **No GraalVM/AOT wiring exists.** The only `native` Maven profile is a no-op in
  the root `pom.xml` that just sets `skipTests=true`, and — because its id is
  `native` — it *shadows* Spring Boot's stock `native` profile. There are no uses
  of `native-maven-plugin`, `org.graalvm.buildtools`, or `process-aot` anywhere.
- **Images are JVM-only.** Microservice containers are built with
  `jib-maven-plugin` (goal `dockerBuild`) under the `-P docker` profile in
  `gebo.apps.parent/gebo.microservices.apps.parent/pom.xml`, onto the JRE base
  `geboai/platform:2.5`, with JVM flags. A static native binary cannot reuse this
  flow.
- **No devtools obstacle** — devtools is not on the runtime classpath of the
  candidates.
- Stack: Spring Boot **4.1.1** / Spring 7 / Java 21 / Spring AI 2.0.1 /
  **Jackson 3** (`tools.jackson.*`).

## How to ship a candidate as native (recommended approach)

Introduce a reusable native build + shared reachability hints, prove it on one
pilot service, then repeat. Keep the JVM/Jib flow untouched for the 5 blocked
services.

### 1. Add the GraalVM/AOT build wiring
- Rename the no-op root `native` profile (e.g. to `skip-tests`) so it stops
  shadowing Spring Boot's `native` profile.
- Add a real native/AOT profile in the microservices aggregator pom
  (`gebo.apps.parent/gebo.microservices.apps.parent/pom.xml`) so all candidate
  apps inherit it, activated per service with `-pl <app> -am`. It declares
  `org.graalvm.buildtools:native-maven-plugin` (version managed by the Boot
  parent) and binds `spring-boot-maven-plugin:process-aot`.
- The build host needs a **GraalVM JDK 21** (Liberica NIK or GraalVM CE) plus a
  native toolchain (gcc, zlib).

### 2. Supply reachability metadata once, in shared modules
The candidates are ordinary Spring beans but still reflect over a few things:
- **RabbitMQ payloads (the sharpest edge).** `GMessageEnvelopeCodec.deserialize`
  resolves message payloads by
  `Class.forName(payloadType)` + `objectMapper.treeToValue(...)`
  (`gebo.microservices.architecture.parent/gebo.architecture.messages.rabbitmq/.../codec/GMessageEnvelopeCodec.java`).
  Add a `RuntimeHintsRegistrar` **in that same module** that registers every
  `IGMessagePayloadType` implementation for reflection and Jackson binding, wired
  via `META-INF/spring/aot.factories`. One registrar covers **all** messaging
  services.
- **Jackson-3 polymorphism** (`@JsonTypeInfo` / `@JsonSubTypes`): rely on Spring
  AOT; add `@RegisterReflectionForBinding` for subtypes not statically reachable.
- **Mongo `@Document` / Neo4j `@Node`**: Spring Data AOT covers these — verify per
  service.
- **Complement with the GraalVM tracing agent**: run the pilot on the JVM with
  `-agentlib:native-image-agent=config-output-dir=...`, exercise its endpoints and
  a message round-trip, and commit the generated `META-INF/native-image/...`
  metadata (shared bits into shared modules, service-specific into the app).

### 3. Pilot on one representative connector
- **`git`** is a good pilot — the typical connector shape (Mongo + RabbitMQ +
  non-JNI Spring AI) that exercises the message codec, so it validates the shared
  template for the majority. (`heimdall` is a fine alternative.)
- Treat **`eureka`** (Netflix Eureka server) and **`gateway`** (Spring Cloud
  Gateway) as **separate** pilots — both have their own native-image quirks.

### 4. Containerize the native binary separately
Leave the existing `-P docker` (Jib) flow for JVM images and blocked services. Add
a `docker-native` path, either:
- Spring Boot buildpacks (`spring-boot:build-image` with `BP_NATIVE_IMAGE=true`), or
- `native-maven-plugin` binary + a minimal `Dockerfile.native`
  (`COPY target/<app> /app`) on a distroless/ubuntu base — dropping the JRE base
  and JVM flags.

### 5. Roll out
Apply the inherited profile to the remaining candidates, capturing any
service-specific hints via the tracing agent. Keep `brain`, `fulltextor`,
`graphicator`, and `vectorizator` on the JVM/Jib flow.

## Verifying a native pilot end-to-end
1. Build the binary: `mvn -Pnative -pl gebo.apps.parent/gebo.microservices.apps.parent/git.gebo.ai -am -DskipTests native:compile`.
2. Bring up the microservices infra + Eureka and run the native binary.
3. Confirm it starts, **registers in Eureka**, serves `/<service>/v3/api-docs`
   (200), a functional endpoint works, and a RabbitMQ message **round-trips
   through `GMessageEnvelopeCodec`** without a `ClassNotFoundException` or
   missing-reflection error.
4. Measure startup time and RSS versus the JVM image (the payoff).
5. On any missing-reflection/resource failure, add the hint (or re-run the tracing
   agent) and rebuild.

## Non-goals and future work
- This covers only the **17 native-candidate** microservices — not the monolith and
  not the 5 blocked services.
- **Unblocking `brain`** would require refactoring the two ByteBuddy sites to
  build-time-known JSON schemas (or a bounded, pre-reified type registry) **and**
  solving ONNX/DJL JNI — a separate, larger effort.
- Introducing the toolchain and proving it on a pilot is the hard part; converting
  the remaining candidates is mechanical repetition afterward.
