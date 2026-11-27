---
name: refresh-llms-setup-presets
description: Refresh the fast setup presets of every LLM vendor (gebo.llms.setup llms-fast-setup-library/library.yml) from the providers' online documentation - replace preset models their provider deprecates or retires within six months, add the provider's new current models, and keep each preset's thinking and context window to the platform rules. Use when asked to update/refresh/check the setup presets, the fast setup / autoconfigure models, the default models of a vendor, or the presets' obsolescence.
user-invocable: true
allowed-tools:
  - Read
  - Edit
  - Write
  - Glob
  - Grep
  - Bash
  - Agent
  - WebFetch
  - WebSearch
---

# Refresh the LLM setup presets from the providers' docs

The fast setup (module `gebo.llms.parent/gebo.llms.setup`) creates a working platform
from a vendor key with no choice left to the user: for each vendor of
`src/main/resources/llms-fast-setup-library/library.yml` it creates the preset models of
each kind (CHAT, EMBEDDING, RANKING, IMAGESGEN, TTS, TRANSCRIPT, DECISION), the
`defaultChoice` of each being the one the easy wizard proposes. So the presets must stay
models the provider serves, with the settings the platform wants.

**Every model status, date and capability comes from a provider page read in this run,
quoted with its URL and date; never from memory.**

## The rules a preset follows (stated at the top of library.yml)

1. **Not obsolete in any way the provider declares:** a preset model is not one its
   provider announces deprecated with a shutdown, or retiring, within **six months** of
   today (a Vertex date "X or later" is the earliest one: it counts), and not one the
   provider's pages declare obsolete otherwise. Read **the model's own page**, not only the
   deprecations list. Each of these counts:
   - a deprecation scoped to a region, a data residency (EU above all: Gebo.ai serves
     European installations) or a service tier, e.g. OpenAI's "GPT-5.2, GPT-5.4, and
     GPT-5.4 mini with EU data residency and `service_tier: fast` or `priority`", which
     shut down 2027-01-11;
   - "Legacy", "Previous generation", "Previous flagship model";
   - "Preview", "not for production", "may be discontinued at short notice";
   - "We recommend using X", "use X instead", "X is the successor to".
   When the provider names a replacement, prefer it. Never keep a flagged model on your own
   judgement: replacing a vendor's `defaultChoice`, or keeping a flagged one for a reason
   (tool calling, cost), is the user's call, so ask.
2. **Callable by its integration:** a replacement must be one the module's handler can call
   (endpoint, api, batching). Check the model page's endpoints against the handler before
   presetting it - e.g. OpenAI's TTS replacement `gpt-realtime-2.1-mini` takes only
   `v1/realtime`, which `openai-tts` (`/v1/audio/speech`) cannot call. When no callable
   replacement exists, say so: keep or drop the preset by the user's decision, and report
   the integration work it needs (as Vertex's `gemini-embedding-001`, one text per request,
   needed `SingleTextEmbeddingModel` in `embedding-google-vertex`).
3. **Thinking:** a chat model (`uses: CHAT`) `thinking: MEDIUM_THINKING` where the model
   takes a thinking level; a service model (`uses: INTERNAL_SERVICES`) `NO_THINKING` where
   the model can stop thinking, else `LOW_THINKING` (`internalServicesThinking` for a model
   serving both). Leave it unset where a level would not reach the model or the provider
   documents none (the handler would send it as asked and could be refused). A preset
   without one gets the same rule from `GeboLLMSSetupService.applyThinkingDefault`, when
   the model's integration tells what it takes (`parametersSupport`).
4. **Temperature:** no preset writes one. The chat model sets temperature 0 wherever the
   model takes it (the models library's `sampling` rules); a model whose provider
   recommends its own default (`PROVIDER_DEFAULT`, Gemini 3) keeps it.
5. **Input types:** no `inputModalities` on a preset whose model's metadata or models
   library tells them (the configuration takes them from the chosen model); a preset
   gives them only for a model neither tells, from the provider's page. Missing library
   values are the `refresh-models-library` skill's (its "input / output modalities" part).
6. **Context window:** `contextWindow` only for the providers whose models list gives no
   context (today: openai, aws-bedrock, xai, nvidia, llmax, profundoai), set to the
   models library's `contextLength` of the model (the most input a request may carry).
   Where the list gives it (regolo.ai `max_input_tokens`, groq `context_window`,
   openrouter.ai `context_length`, and Anthropic, DeepSeek, Vertex, Mistral, Ollama by
   their own lookups) no `contextWindow`: a preset value would override the live one.
   Check the provider's list-models reference when a provider is added.
7. **Codes the lookup can validate:** with `doModelsLookup: true` the setup creates a model
   only if its code is in the provider's live list (`configureModelValidating`); use the
   ids the provider documents (`mistral-large-4-0`, not an alias the docs do not show).

## Procedure

1. **Current state:** run the check (from the repository root):
   ```bash
   python3 .claude/skills/refresh-llms-setup-presets/scripts/check_presets.py --months 6
   ```
   It prints every preset with its library entry, retirement, context and thinking (as
   sent, for the reasoning_effort providers) and WARNs for what breaks rules 1, 3 and 6.
   "no library entry" means the libraries cannot tell its status (most image, speech,
   transcription, ranking and some embedding models): those need the provider pages.
2. **Read the providers' lifecycle pages in parallel** (`web-fetch` agents in one message,
   a few providers each), asking for verbatim quotes of status, deprecation/shutdown or
   retirement dates and the named replacement of every preset model, the current models of
   each kind, and the endpoints of each candidate replacement. Starting points:
   - OpenAI: developers.openai.com/api/docs/deprecations, /api/docs/models (index and
     /models/all), /api/docs/models/<id> (Endpoints), the text-to-speech guide.
   - Anthropic: platform.claude.com/docs/en/about-claude/models/overview and
     /about-claude/model-deprecations; /build-with-claude/effort and /thinking.
   - Google Vertex: docs.cloud.google.com/vertex-ai/generative-ai/docs/learn/model-versions,
     /embeddings/get-text-embeddings.
   - AWS Bedrock: docs.aws.amazon.com/bedrock/latest/userguide/model-lifecycle.html,
     model-lifecycle-legacy.html, model-cards.md and each model card.
   - Mistral: docs.mistral.ai/getting-started/models (deprecated & retired table), model pages.
   - DeepSeek: api-docs.deepseek.com/quick_start/pricing and /updates.
   - Groq: console.groq.com/docs/models and /docs/deprecations.
   - xAI: docs.x.ai/docs/models.
   - OpenRouter: openrouter.ai/api/v1/models, /api/v1/embeddings/models,
     /api/v1/images/models, /api/v1/audio/speech/models (`expiration_date` where given),
     model pages openrouter.ai/<id>.
   - Regolo.ai: api.regolo.ai/models; NVIDIA: integrate.api.nvidia.com/v1/models and the
     model cards on build.nvidia.com; llmax.ai/models; profundoai.com/models.
   Each preset's YAML comment usually names the page it came from: start there too.
3. **Decide per preset:** keep, replace (by the provider's named replacement, callable by
   the handler), drop, or add (a new current model). Keep the reasons the existing
   comments give (a default picked for tool calling, cost, language coverage) unless the
   docs make them obsolete; do not change a vendor's default to another generation
   without asking.
4. **Edit library.yml** in place: keep the comments, add to each change a comment quoting
   the page with its date, apply rules 3-6 to every touched chat preset. Validate the YAML.
   If a preset model has no models library entry and its library should carry it (context,
   retirement date), run the `refresh-models-library` skill for that provider.
5. **Tests:** update `gebo.llms.setup/src/test/java/ai/gebo/llms/setup/config/PresetChoiceDefaultsTest.java`
   where it pins a changed preset (it also checks rules 3 and 4 over every preset), run
   `mvn -o install -pl gebo.llms.parent/gebo.llms.setup`, and the check script again
   (0 warnings).
6. **Integration fallbacks:** grep the provider modules for hard-coded default codes of
   retired models (e.g. a handler's fallback model when none is chosen) and report them.
7. **Report** per vendor: kept, replaced (old -> new, with the quote), dropped, added,
   the integration gaps found (rule 2), decisions left to the user, and what was not
   tried live (the setup itself calls the providers only when run with a key).
