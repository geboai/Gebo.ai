---
name: refresh-models-library
description: Refresh the per-provider models library (bundle-models/<provider>.yml of each gebo.llms.* module) from the providers' online documentation - models still served and new ones, context and output limits, input modalities, reasoning, deprecation and retirement dates, and the parameter rules (sampling, reasoning efforts, api, temperature/top_p ranges) the chat integrations and the editors read. Use when asked to update/renew/refresh the models library, the models meta-informations, the context windows, the reasoning efforts or the parameter rules of one or every provider, or to add the models a provider released.
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

# Refresh the models library from the providers' docs

The models library is what Gebo knows of a model that its provider's models api does
not say. It lives in one YAML file per provider, shipped by the module implementing the
provider, and it is read at run time by `GModelsLibraryDaoImpl`
(`gebo.architecture.llms.abstraction.layer`, package `ai.gebo.llms.models.metainfos`):

- the chat models' prompt budgets start from `contextLength` (8,192 when nothing is known);
- `parameterRules` decide what a request carries (temperature, top_p, reasoning effort,
  the OpenAI api) and what the chat model and agent editors offer
  (`ChatModelsController.getChatModelParametersSupport`);
- the fast setup presets (`gebo.llms.setup/src/main/resources/llms-fast-setup-library/library.yml`)
  name models that must still be served.

A wrong value is either a failed request (a parameter the provider refuses) or a silent
loss (a prompt cut to 8,192 tokens, a thinking choice ignored). So **every value comes
from a page read in this run, quoted, with its URL and date; never from memory.**

## 1. The files

| Provider id | File (under `gebo.llms.parent/`) |
|---|---|
| openai | `gebo.llms.openai/src/main/resources/bundle-models/openai.yml` |
| anthropic | `gebo.llms.anthropic3/.../bundle-models/anthropic.yml` |
| google-vertex | `gebo.llms.google_vertex/.../bundle-models/google-vertex.yml` |
| mistralai | `gebo.llms.mistral/.../bundle-models/mistralai.yml` |
| deepseek | `gebo.llms.deepseek/.../bundle-models/deepseek.yml` |
| ollama | `gebo.llms.ollama/.../bundle-models/ollama.yml` (rules only) |
| aws-bedrock | `gebo.llms.aws-bedrock/.../bundle-models/aws-bedrock.yml` |
| onnx-transformers | `gebo.llms.onxx-embeddings/.../bundle-models/onnx-transformers.yml` |
| groq, xai, nvidia, openrouter.ai, regolo.ai, llmax, profundoai, awanllm, cheapestinference, subconscious, vllm, generic-openai | `gebo.llms.generic-openai-compatible/.../bundle-models/<id>.yml` |

Every entry and rule already carries an `informativeUrl` or a `source:` naming the page it
was read on: start from those. An installation can add or override entries and rules in
its own configuration (`ai.gebo.models.library`, `ModelsLibraryConfig`), read first.

Print what the library makes of each model before and after editing:

```bash
python3 .claude/skills/refresh-models-library/scripts/effective_rules.py <file.yml>
python3 .claude/skills/refresh-models-library/scripts/effective_rules.py openai.yml --model gpt-5.5-pro-2026-12-01   # an id without an entry
```

## 2. What each field means (read the code if in doubt)

**`modelsInfo`** (one entry per model id, matched on the **exact** id; a dated snapshot
the provider serves needs its own entry, a copy of its model's):

- `contextLength`: the most a request may carry **as input** - the page's "maximum input
  tokens" where it states one (OpenAI GPT-6 family: 922,000 of a 1,050,000 window), else
  the context window. Not the window when the page gives both.
- `maxOutputToken`, `inputModalities` (TEXT, IMAGE, AUDIO, VIDEO, DOCUMENT), `chatModel`,
  `embeddingModel`, `supportsReasoning` (false only where the page says the model does
  not reason: the OpenAI integration then sends no reasoning effort and the editors
  disable thinking), `supportsFunctionCalls`.
- `deprecated`, `deprecationDate`, `retirementDate`, `replacementModel`: as the provider
  announces them (ISO dates). A model already shut down is removed, not kept deprecated.

What a provider's models api returns wins: the library fills only the values left null
(`ModelMetaInfo.fillMissingFrom`), so a `0` from an api beats the library. The lookups
reading the context live: Anthropic (`max_input_tokens`), DeepSeek, Vertex
(`inputTokenLimit`), Mistral (`max_context_length`), Ollama (`/api/show`), OpenRouter,
Regolo, the generic OpenAI compatible lookup (`context_window` or `max_model_len`, not
`context_length`). `BedrockFoundationModelsLookupService` does not read the library at all:
`aws-bedrock.yml` only matters to the fast setup presets.

**`parameterRules`** (patterns, `*` = any characters; one provider only; **order matters**):

- The parameter rule of a model is the **first** rule covering it that states
  `sampling`, `reasoningEfforts`, `queryInputType`, `documentInputType` or `speechFormat`.
  A specific rule goes before the family rule that would catch the model
  (`gpt-5.5-pro*` before `gpt-5.5*`, `gpt-6.1-terra*` before `gpt-6*`).
- `sampling`: `ACCEPTED`, `REJECTED` (a request carrying temperature/top_p fails),
  `WITHOUT_REASONING` (only at effort `none`), `PROVIDER_DEFAULT` (taken, but an empty
  one is left to the provider instead of Gebo's deterministic 0), `IGNORED` (taken and
  ignored: not sent). Where a page says nothing about sampling on a reasoning model, keep
  `REJECTED` (a refused parameter fails every request; a withheld one only loses
  determinism).
- `reasoningEfforts`: exactly the values the page lists, spelled as the provider spells
  them. `reasoningEffortFor` sends the nearest listed value on
  none < minimal < low < medium < high < xhigh < max: no thinking = `none` or the least
  listed, high = the most listed. An empty list means "unknown": low/medium/high are
  then sent as asked, which fails on a model that lacks one of them - so list the efforts
  of every reasoning model whose page gives them (the pro models take medium/high/xhigh
  or high only).
- Read apart from the parameters, each from the first rule covering the model that
  tells it, so they never hide a parameter rule: `inputModalities` (models without an
  entry of their own, e.g. dated variants `gpt-5.2-20*`), `api` (OpenAI: `responses` for
  models whose page marks Chat Completions "Not supported", read by `OpenAiApiRouting`
  before its version rule), `temperatureRange` / `topPRange` (`{min, max}`; a `models: ["*"]`
  rule placed last gives the provider's range from its api reference).
- Every rule ends with `source: <page>, <yyyy-mm-dd>`; a YAML comment above a rule
  quotes the sentence that justifies it.

## 3. Procedure

1. **Scope.** Which providers. For each, the model list to check: the provider's
   models index page (or its public models endpoint, e.g. OpenRouter's), plus every id
   already in the file and every id the fast setup presets name.
2. **Read the pages in parallel.** Fan out `web-fetch` agents in one message, about 10
   model pages each, asking for verbatim quotes of: maximum input tokens / context
   window, max output tokens, input modalities, endpoints (OpenAI: Chat Completions and
   Responses supported or not), reasoning effort values and default, anything on
   temperature/top_p, reasoning summaries, function calling caveats, deprecation and
   shutdown dates and replacement. Require "say explicitly when a page states nothing,
   fails to load or redirects" and "no values from memory". For ranges, also read the
   provider's api reference for the chat endpoint. Do not re-send a URL that failed.
3. **Diff.** Run `effective_rules.py` and compare, model by model, with the quotes:
   missing models, values that changed, efforts the rule does not list, rules a model
   falls under by accident (a family pattern catching a pro model), snapshots without
   entries, retired models still listed, fast setup presets naming retired models.
4. **Decide what is not stated.** When a page leaves a value out, keep the field
   unknown (null / no rule) rather than guessing; tell the user each judgment call you
   make (e.g. "128K" read as 128,000 or 131,072; sampling kept refused).
5. **Edit** the YAML in place: keep the existing comments, add a dated source to each
   new or changed rule, keep entries grouped as the file groups them. Validate:
   `python3 -c "import yaml,sys; yaml.safe_load(open(sys.argv[1]))" <file>`.
6. **Tests.** Update the segment tests pinning the values you changed, add assertions
   for what you added, and run the modules' tests (`mvn -o install -pl <module>`):
   - openai: `OpenAIModelsLibrarySegmentTest`, `OpenAIParametersSupportTest`, `OpenAiApiRoutingTest`
   - anthropic: `AnthropicModelsLibrarySegmentTest`, `AnthropicParametersSupportTest`
   - google-vertex: `GoogleVertexModelsLibrarySegmentTest`, `GoogleVertexParametersSupportTest`
   - mistralai: `MistralModelsLibrarySegmentTest`, `MistralParametersSupportTest`
   - ollama: `OllamaParametersSupportTest`; aws-bedrock: `BedrockModelsLibrarySegmentTest`
   - compatible providers: `CompatibleModelsLibrarySegmentsTest`, `ProviderModelsLibrariesWiringTest`
   - the library itself: `ProviderModelsLibrariesTest`, `ModelParameterRulesTest`
     (`gebo.architecture.llms.abstraction.layer`)
   A segment test asserting an old value is updated with the new quote's value, never
   loosened to pass.
7. **Fast setup presets.** Check `library.yml`: every preset names a model still served,
   and its `contextWindow` matches the library's `contextLength` for that model.
8. **Report** per provider: models added, changed (old -> new, with the quote), removed,
   rules added or reordered, judgment calls, pages that failed, and what was not checked
   live (nothing in this procedure calls a provider). Commit on a branch per the repo's
   conventions; the user opens or merges the PR.

## Gotchas

- Library entries do not reach saved configurations: a configuration keeps the
  `choosedModel` (with its `contextLength`) it was saved with until the model is picked
  again in its editor.
- A rule's `providerId` is the provider id of the model type (`openai`, `aws-bedrock`,
  `google-vertex`, `regolo.ai`...), not the module name.
- Pattern pitfalls: `gpt-5*` catches `gpt-5.5-pro`; `gpt-5-20*` is the dated variants of
  `gpt-5` only; `gemini-3*` catches 3.6+ (which ignore sampling: `IGNORED` rule first).
- OpenAI routing reads a version from the id (`gpt-oss-120b` reads 120): name the
  Responses-only models in the `api: responses` rule instead of relying on it.
