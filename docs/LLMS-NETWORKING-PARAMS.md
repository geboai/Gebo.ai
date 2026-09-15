# Gebo.ai — LLM Client Networking Parameters (timeout / retry) Review

> **Audience:** this document is written to be read by both humans and coding agents.
> It reflects the **implementation as built**. It records which model services honour
> the configured networking parameters, which do not, and why.
>
> **Read this before adding a new model service or changing a client builder.**

---

## 1. Fact Sheet (machine-readable)

| Key | Value |
| --- | --- |
| Config prefix | `ai.gebo.llms.default.clients.config` — `GeboDefaultLlmsServiceClientsProviderConfig` |
| `web-client-config.connect-timeout` | `30000` ms |
| `web-client-config.response-timeout` | `80000` ms — used as **both** read and write timeout |
| `retry-config.max-attempts` | `5` |
| `retry-config.backoff-interval` | `5000` ms |
| `retry-config.retry-timeout` | `80000` ms |
| Provider | `GDefaultLlmsServiceClientsProviderImpl` |
| Provider factory | `GLlmsServiceClientsProviderFactoryImpl.get(providerId)` — **always returns the default provider**, there is no per-vendor override today |
| Transports in use | Reactor Netty (`RestClient`/`WebClient`), OkHttp (OpenAI + Anthropic SDKs), AWS SDK (Bedrock), Google SDK (Vertex) |
| Model services reviewed | 26 |

The provider exposes five things; a correctly wired service uses the ones that fit
its transport:

| Accessor | Carries |
| --- | --- |
| `getRestClientBuilder()` | Reactor Netty `responseTimeout` |
| `getWebClientBuilder()` | Reactor Netty `responseTimeout` |
| `getRetryTemplate()` | `maxAttempts` + fixed backoff |
| `getCoreRetryTemplate()` | same, Spring Core `RetryPolicy` |
| `getClientConfig()` / `getOkHttpRetryInterceptor()` | OkHttp timeouts + retry interceptor |

---

## 2. Review result

| Module | Service | Transport | Timeout | Retry |
| --- | --- | --- | --- | --- |
| generic-openai-compatible | Chat, Embedding, Image, TextToSpeech, Transcript | OkHttp (OpenAI SDK) | **fixed** (§3) | interceptor |
| generic-openai-compatible | Ranker | Reactor Netty | ok | `RetryTemplate` |
| openai | Chat, Embedding, Image, TextToSpeech, Transcript | OkHttp (OpenAI SDK) | **fixed** (§3) | interceptor |
| anthropic3 | Chat | OkHttp (Anthropic SDK) | ok (§4) | interceptor |
| deepseek | Chat | Reactor Netty | ok | `RetryTemplate` |
| mistral | Chat, Embedding | Reactor Netty | ok | `RetryTemplate` |
| ollama | Chat, Embedding | Reactor Netty | ok | `RetryTemplate` |
| google_vertex | Chat, Embedding | Google GenAI SDK | **fixed** (§5) | **fixed** (§5) |
| aws-bedrock | Chat, Embedding, Image, Ranker, TextToSpeech, Transcript | AWS SDK | **fixed** (§5) | **fixed** (§5) |
| onxx-embeddings | Embedding | in-process | n/a | n/a |

Before this review, **0 of 26** services set a timeout on their model *options*
builder. For the Reactor Netty services that is correct — the timeout rides on the
client. For the ten OpenAI-SDK services it was a live defect.

---

## 3. The OpenAI-SDK defect (fixed here)

`OpenAiClientCustomizer.from(provider)` sets `.timeout(readTimeoutMs)` on the OkHttp
client builder, so the configuration looked applied. It was not, because Spring AI
feeds **two** timeouts into the client factory and the second one wins:

```
OpenAiChatModel$Builder.build()
  -> OpenAiSetup.setupAsyncClient(..., options.getTimeout(), maxRetries, ..., httpClientCustomizers)
       1. SpringAiOpenAiHttpClient$Builder.timeout(options.getTimeout())   // 60s
       2. OpenAiHttpClientBuilderCustomizer.customize(builder)             // our 80s wins here
       3. ClientOptions$Builder.timeout(duration)                          // 60s again, AFTER the customizer
```

`options.getTimeout()` is 60s because
`org.springframework.ai.openai.AbstractOpenAiOptions.DEFAULT_TIMEOUT` is
`Duration.ofSeconds(60)`, every OpenAI options class inherits it
(`OpenAiEmbeddingOptions`, `OpenAiImageOptions`, `OpenAiAudioSpeechOptions` and
`OpenAiAudioTranscriptionOptions` extend `AbstractOpenAiOptions`; `OpenAiChatOptions`
assigns the same constant), and no gebo service ever called `.timeout(..)` on an
options builder.

**Observed consequence.** A five-cycle `ANALISYS` request cut at exactly **60.026 s**
(`14:11:30,963` -> `14:12:30,989`):

```
OpenAIIoException: Stream failed
  Caused by: InterruptedIOException: timeout  (okhttp3 RealCall.timeoutExit)
  Caused by: StreamResetException: stream was reset: CANCEL
```

The report writer then logged `Recording an empty interaction`, the network logged
`Agent ReportWriterNetworkAgentService failed; continuing network` and kept `the
already composed chat envelope` — so the user got a report that stopped mid-sentence
after 15,120 streamed characters, with no visible error. Nothing trims the answer: the
`cumulated=` counter is only the running length logged per chunk, and the one truncation
helper in the agents layer (`GAbstractGenericalAgentService.truncateToTokens`) applies to
prompt inputs and appends a visible `...(truncated content)` marker, which this answer
did not carry. The generation simply stopped where the cancelled stream left it. Neither retry layer can help: an
OkHttp interceptor and Spring AI's `RetryTemplate` cannot replay an SSE stream whose
tokens have already been delivered downstream.

**Fix.** `OpenAiClientCustomizer.requestTimeout(provider)` returns the configured
duration, and all ten services now pass it to their options builder so both layers
carry the same value:

```java
builder.timeout(OpenAiClientCustomizer.requestTimeout(clientsProvider));
```

**Verified at runtime.** Launching with `response-timeout=15000` and repeating the
request, the call died at **15.128 s** (`15:08:01,790` -> `15:08:16,918`) instead of the
old fixed 60 s, i.e. the configured value now governs:

| Configured | Actual cut | Governed by |
| --- | --- | --- |
| 80000 ms (before the fix) | 60.026 s | Spring AI's `DEFAULT_TIMEOUT` |
| 15000 ms (after the fix) | 15.128 s | the configuration |

**`maxRetries` is deliberately left at Spring AI's default of 3.** Retries are already
applied at the transport level by `getOkHttpRetryInterceptor()` with
`maxAttempts = 5`; also setting the SDK's `maxRetries` would multiply the two layers
(up to 15 attempts) rather than add to them. If SDK-level retry is ever wanted, the
interceptor should be removed at the same time.

---

## 4. Anthropic is not affected

`AnthropicChatOptions` has **no** `timeout` or `maxRetries` field at all, so there is
nothing to override `AnthropicClientCustomizer`'s value. Its OkHttp timeout stands.

---

## 5. AWS Bedrock and Google Vertex

Neither family took an `IGLlmsServiceClientsProvider` at all, so
`ai.gebo.llms.default.clients.config` never reached them and both ran on vendor SDK
defaults. Both now receive the factory (all the services are `@Service`
`@AllArgsConstructor`, so a `final` field is injected) and map the configuration onto
the options each SDK actually exposes.

### 5.1 Bedrock

`BedrockClientCustomizer` (new, in `ai.gebo.llms.aws_bedrock.http`) is the Bedrock
analogue of `OpenAiClientCustomizer`.

`BedrockProxyChatModel.Builder` exposes five durations, and its defaults were the
problem — `asyncReadTimeout` and `socketTimeout` default to **30 seconds**, tighter even
than the OpenAI 60s, and they bite exactly on long streamed generations:

| Builder method | Default | Now |
| --- | --- | --- |
| `timeout` (api call) | 5 min | `response-timeout` |
| `asyncReadTimeout` | **30 s** | `response-timeout` |
| `socketTimeout` | **30 s** | `response-timeout` |
| `connectionTimeout` | 5 s | `connect-timeout` |
| `connectionAcquisitionTimeout` | 30 s | `connect-timeout` |

The four plain AWS SDK clients — `BedrockRuntimeClient` (image),
`BedrockAgentRuntimeClient` (ranker), `PollyClient` (text-to-speech) and
`TranscribeStreamingAsyncClient` (transcript) — take
`BedrockClientCustomizer.overrideConfiguration(..)`, which sets
`apiCallAttemptTimeout` and a `retryStrategy` with the configured `maxAttempts`.
`apiCallTimeout` is deliberately **not** set: making the overall budget equal to a
single attempt would silently defeat the retry strategy.

The embedding service's hard-coded `API_TIMEOUT = Duration.ofMinutes(2)` is replaced by
the configured value on both the Cohere and the Titan API objects.

### 5.2 Vertex

`GoogleGenAiChatModel.Builder` accepts a Spring Core
`retryTemplate(org.springframework.core.retry.RetryTemplate)` — exactly what
`getCoreRetryTemplate()` already returns — and nothing was being passed, so no retry
policy applied at all. It is now wired.

Timeouts ride on the `com.google.genai.Client`, through `HttpOptions`.
`VertexAIConfigurator` built `HttpOptions` **only** when a custom base URL was
configured, and overwrote the whole object, so no timeout or retry option ever reached
the client. A single `httpOptions(baseUrl)` helper now always sets
`timeout(int millis)` and `retryOptions(HttpRetryOptions.attempts(..))`, adding the base
URL when present.

Neither vendor is exercised by the default local stack, so these are compile-verified
and grounded in the shipped SDK APIs, but not runtime-verified the way the OpenAI path
in §3 was.

---

## 6. Invariants — do not break these

1. **A timeout on the HTTP client is not enough for OpenAI-SDK models.** Always set it
   on the options builder too, via `OpenAiClientCustomizer.requestTimeout(..)`.
   Spring AI re-applies `options.getTimeout()` after the customizer runs.
2. **Do not set `maxRetries` on OpenAI options while
   `getOkHttpRetryInterceptor()` is installed** — the two multiply. Pick one layer.
3. **New model services must take `IGLlmsServiceClientsProviderFactory`** and use the
   accessors in §1 that match their transport, rather than SDK defaults or a
   hard-coded `Duration`.
4. **`GLlmsServiceClientsProviderFactoryImpl.get(providerId)` ignores its argument**
   today. Anything that needs per-vendor timeouts must change that method rather than
   hard-code a value at the call site.

---

## 7. File Index

| Path | Role |
| --- | --- |
| `gebo.architecture.parent/gebo.architecture.llms.abstraction.layer/src/main/java/ai/gebo/llms/abstraction/layer/services/config/GeboDefaultLlmsServiceClientsProviderConfig.java` | the configured values |
| `…/services/impl/GDefaultLlmsServiceClientsProviderImpl.java` | builds RestClient/WebClient/RetryTemplate/OkHttp config |
| `…/services/impl/GLlmsServiceClientsProviderFactoryImpl.java` | provider lookup (returns the default for every id) |
| `gebo.llms.parent/gebo.llms.openai.api.utils/src/main/java/ai/gebo/llms/openai/http/OpenAiClientCustomizer.java` | OkHttp customizer + `requestTimeout(..)` |
| `gebo.llms.parent/gebo.llms.anthropic3/src/main/java/ai/gebo/llms/anthropic/http/AnthropicClientCustomizer.java` | Anthropic equivalent |
| `gebo.llms.parent/gebo.llms.openai/src/main/java/ai/gebo/llms/openai/services/` | the five OpenAI services |
| `gebo.llms.parent/gebo.llms.generic-openai-compatible/src/main/java/ai/gebo/llms/openai_compat/services/` | the six OpenAI-compatible services |
| `gebo.architecture.parent/gebo.architecture.llms.abstraction.layer/src/main/java/ai/gebo/llms/abstraction/layer/services/GAbstractConfigurableRankerModel.java` | ranker base, Reactor Netty path |
