#!/usr/bin/env python3
"""
Prints what the Gebo models library makes of every model of a provider library file
(bundle-models/<provider>.yml), as GModelsLibraryDaoImpl reads it:

- the model entry (exact model id match): context, max output, reasoning, retirement;
- the parameter rule (first rule covering the model that states parameters:
  sampling, reasoningEfforts, queryInputType, documentInputType or speechFormat);
- the api, the temperature and top_p ranges and the input modalities, each from the
  first rule covering the model that tells it (read apart from the parameters).

Usage:
  effective_rules.py <provider.yml> [--model ID ...] [--provider PROVIDER_ID]

--model evaluates ids that have no entry (dated snapshots, new models): the rules
only, since an entry is matched on the exact id. Needs PyYAML.
"""
import argparse
import re
import sys

import yaml


def library_of(document):
    """The mapping holding modelsInfo / parameterRules, wherever the prefix puts it."""
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


def covers(rule, provider_id, model_id):
    return rule.get("providerId") == provider_id and any(matches(p, model_id) for p in rule.get("models") or [])


def states_parameters(rule):
    return bool(rule.get("sampling") or rule.get("reasoningEfforts") or rule.get("queryInputType")
                or rule.get("documentInputType") or rule.get("speechFormat"))


def first(rules, provider_id, model_id, telling):
    return next((r for r in rules if telling(r) and covers(r, provider_id, model_id)), None)


def describe(rules, provider_id, model_id, entry):
    parameters = first(rules, provider_id, model_id, states_parameters)
    api = first(rules, provider_id, model_id, lambda r: bool(r.get("api")))
    temperature = first(rules, provider_id, model_id, lambda r: bool(r.get("temperatureRange")))
    top_p = first(rules, provider_id, model_id, lambda r: bool(r.get("topPRange")))
    modalities = first(rules, provider_id, model_id, lambda r: bool(r.get("inputModalities")))
    out = [model_id]
    if entry is not None:
        out.append("ctx=%s out=%s reasons=%s" % (entry.get("contextLength"), entry.get("maxOutputToken"),
                                                  entry.get("supportsReasoning")))
        if entry.get("deprecated") or entry.get("retirementDate"):
            out.append("retires=%s->%s" % (entry.get("retirementDate"), entry.get("replacementModel")))
        if entry.get("inputModalities"):
            out.append("inputs=%s" % ",".join(entry["inputModalities"]))
    else:
        out.append("(no entry of its own)")
    if parameters:
        out.append("rule=%s sampling=%s efforts=%s" % (parameters["models"][0], parameters.get("sampling"),
                                                        parameters.get("reasoningEfforts")))
    else:
        out.append("rule=none")
    if api:
        out.append("api=%s" % api["api"])
    if temperature:
        out.append("temperature=%s" % temperature["temperatureRange"])
    if top_p:
        out.append("top_p=%s" % top_p["topPRange"])
    if entry is None and modalities:
        out.append("inputs(rule)=%s" % ",".join(modalities["inputModalities"]))
    return "  ".join(out)


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("yml")
    parser.add_argument("--model", action="append", default=[])
    parser.add_argument("--provider")
    parser.add_argument("--missing-modalities", action="store_true",
                        help="list only the chat entries with no input modalities, of their own or by a rule")
    args = parser.parse_args()
    library = library_of(yaml.safe_load(open(args.yml, encoding="utf-8"))) or {}
    entries = library.get("modelsInfo") or []
    rules = library.get("parameterRules") or []
    provider_id = args.provider or next((e.get("providerId") for e in entries + rules if e.get("providerId")), None)
    if provider_id is None:
        sys.exit("no providerId found: pass --provider")
    print("provider %s: %d entries, %d rules" % (provider_id, len(entries), len(rules)))
    by_id = {e.get("modelId"): e for e in entries if e.get("providerId") == provider_id}
    if args.missing_modalities:
        missing = [m for m, e in by_id.items() if e.get("chatModel") and not e.get("inputModalities")
                   and not first(rules, provider_id, m, lambda r: bool(r.get("inputModalities")))]
        print("%d chat entries without input modalities: %s" % (len(missing), ", ".join(missing)))
        return
    for model_id in (args.model or list(by_id)):
        print(describe(rules, provider_id, model_id, by_id.get(model_id)))


if __name__ == "__main__":
    main()
