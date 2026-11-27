#!/usr/bin/env python3
"""
Checks the fast setup presets (gebo.llms.setup/src/main/resources/llms-fast-setup-library/library.yml)
against the models libraries (bundle-models/<provider>.yml of every gebo.llms.* module) and
the platform's preset rules:

- every preset model has a library entry, and is not retiring within --months (default 6);
- a context window is preset only for the providers whose models list gives none
  (STATIC_CONTEXT below); elsewhere the chosen model carries the live one;
- a chat preset (uses CHAT) thinks MEDIUM_THINKING or keeps the provider default; a
  service preset (uses INTERNAL_SERVICES) NO_THINKING or LOW_THINKING;
- what each preset thinking is sent as, for the providers whose requests take the
  nearest reasoning effort the library lists (OpenAI-style reasoning_effort).

Run from the repository root:  python3 .claude/skills/refresh-llms-setup-presets/scripts/check_presets.py [--months 6] [--today YYYY-MM-DD]
Prints one line per preset, WARN lines for what breaks a rule. Needs PyYAML.
"""
import argparse
import datetime
import glob
import re
import sys

import yaml

PRESETS = "gebo.llms.parent/gebo.llms.setup/src/main/resources/llms-fast-setup-library/library.yml"
LIBRARIES = "gebo.llms.parent/*/src/main/resources/bundle-models/*.yml"
# setup vendor id -> models library provider id, where they differ
PROVIDER = {"mistral.ai": "mistralai"}
# providers whose models list gives no context length to the lookup: a preset contextWindow
# is the only context the configuration gets (keep this list in sync with the lookups:
# GOpenAIApiUtilImpl.liveMetaInfos reads context_window / max_model_len, OpenRouter
# context_length, Regolo max_input_tokens; Anthropic, DeepSeek, Vertex, Mistral and Ollama
# have their own lookups reading it; Bedrock's lookup reads none)
STATIC_CONTEXT = {"openai", "aws-bedrock", "xai", "nvidia", "llmax", "profundoai"}
# providers whose chat requests send reasoning_effort through GAbstractConfigurableChatModel.reasoningEffortFor
NEAREST_EFFORT = {"openai", "aws-bedrock", "regolo.ai", "groq", "xai", "nvidia", "openrouter.ai", "llmax",
                  "profundoai", "awanllm", "cheapestinference", "subconscious", "vllm", "generic-openai"}
SCALE = ["none", "minimal", "low", "medium", "high", "xhigh", "max"]


def library_of(document):
    if isinstance(document, dict):
        if "modelsInfo" in document or "parameterRules" in document:
            return document
        for value in document.values():
            found = library_of(value)
            if found is not None:
                return found
    return None


def matches(pattern, model_id):
    if "*" not in pattern:
        return pattern == model_id
    return re.fullmatch(".*".join(re.escape(p) for p in pattern.split("*")), model_id) is not None


def parameter_rule(rules, provider_id, model_id):
    for rule in rules:
        if rule.get("providerId") != provider_id:
            continue
        if not (rule.get("sampling") or rule.get("reasoningEfforts") or rule.get("queryInputType")
                or rule.get("documentInputType") or rule.get("speechFormat")):
            continue
        if any(matches(p, model_id) for p in rule.get("models") or []):
            return rule
    return None


def nearest(option, rule):
    asked = {"NO_THINKING": "none", "LOW_THINKING": "low", "MEDIUM_THINKING": "medium",
             "HIGH_THINKING": "high"}.get(option)
    if asked is None:
        return None
    efforts = (rule or {}).get("reasoningEfforts") or []
    if not efforts:
        return None if option == "NO_THINKING" else asked + " (efforts unknown: sent as asked)"
    listed = [e for e in SCALE if e in efforts]
    if option == "NO_THINKING":
        return listed[0] if listed else None
    reasoning = [e for e in listed if e != "none"]
    if option == "HIGH_THINKING":
        return reasoning[-1] if reasoning else None
    target, best, distance = SCALE.index(asked), None, 99
    for effort in reasoning:
        d = abs(SCALE.index(effort) - target)
        if d < distance:
            best, distance = effort, d
    return best


def uses_of(choice):
    uses = choice.get("uses")
    if uses is None:
        return []
    if isinstance(uses, str):
        return [u.strip() for u in uses.split(",") if u.strip()]
    return list(uses)


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--months", type=int, default=6)
    parser.add_argument("--today", default=datetime.date.today().isoformat())
    args = parser.parse_args()
    today = datetime.date.fromisoformat(args.today)
    month = today.month - 1 + args.months
    limit = datetime.date(today.year + month // 12, month % 12 + 1, min(today.day, 28)).isoformat()

    entries, rules = {}, []
    for path in glob.glob(LIBRARIES):
        library = library_of(yaml.safe_load(open(path, encoding="utf-8"))) or {}
        for entry in library.get("modelsInfo") or []:
            entries.setdefault(entry["providerId"], {})[entry["modelId"]] = entry
        rules.extend(library.get("parameterRules") or [])

    presets = yaml.safe_load(open(PRESETS, encoding="utf-8"))["ai.gebo.llms.vendors.setup.config"]["vendors"]
    warnings = 0
    print("retiring before %s counts as within %d months" % (limit, args.months))
    for vendor in presets:
        vendor_id = vendor["vendorInfo"]["vendorId"]
        provider_id = PROVIDER.get(vendor_id, vendor_id)
        for preset in vendor.get("presets") or []:
            for choice in preset.get("choices") or []:
                code, notes, warn = choice["code"], [], []
                entry = entries.get(provider_id, {}).get(code)
                if entry is None:
                    notes.append("no library entry (check the provider's lifecycle page)")
                else:
                    retirement = entry.get("retirementDate")
                    if retirement and str(retirement) <= limit:
                        warn.append("retires %s -> %s" % (retirement, entry.get("replacementModel")))
                    elif entry.get("deprecated"):
                        warn.append("deprecated")
                if preset["type"] == "CHAT":
                    uses = uses_of(choice)
                    context = choice.get("contextWindow")
                    if context and provider_id not in STATIC_CONTEXT:
                        warn.append("contextWindow %s preset where the models list gives the context" % context)
                    if not context and provider_id in STATIC_CONTEXT:
                        library_context = entry.get("contextLength") if entry else None
                        notes.append("no contextWindow: the library's %s applies" % library_context)
                    if context and entry and entry.get("contextLength") and entry["contextLength"] != context:
                        notes.append("contextWindow %s, library %s" % (context, entry["contextLength"]))
                    thinking = choice.get("thinking")
                    service = choice.get("internalServicesThinking") or thinking
                    if "INTERNAL_SERVICES" in uses and service not in (None, "NO_THINKING", "LOW_THINKING"):
                        warn.append("service model thinking %s" % service)
                    if uses == ["CHAT"] and thinking not in (None, "MEDIUM_THINKING"):
                        warn.append("chat model thinking %s" % thinking)
                    if provider_id in NEAREST_EFFORT:
                        rule = parameter_rule(rules, provider_id, code)
                        for option in {thinking, choice.get("internalServicesThinking")} - {None}:
                            notes.append("%s sent as %s" % (option, nearest(option, rule)))
                warnings += len(warn)
                line = "%-14s %-10s %-42s %s" % (vendor_id, preset["type"], code,
                                                 "DEFAULT" if choice.get("defaultChoice") else "")
                print(line + ("  " + "; ".join(notes) if notes else ""))
                for w in warn:
                    print("  WARN " + w)
    print("%d warning(s)" % warnings)
    sys.exit(1 if warnings else 0)


if __name__ == "__main__":
    main()
