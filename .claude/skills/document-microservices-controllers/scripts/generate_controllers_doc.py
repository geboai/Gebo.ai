#!/usr/bin/env python3
"""Generate docs/MICROSERVICES-CONTROLLERS.md from each running microservice's live
/v3/api-docs. Auto-detects whether the checked-out branch serves each backend at its
root (no server.servlet.context-path) or under a short context prefix (e.g. /brain) -
see SKILL.md step 3. Run only after every service in the port map below answers 200 on
its api-docs URL (poll first, this script does not wait).
"""
import json
import re
import sys
import urllib.request
import urllib.error
from pathlib import Path

REPO_ROOT = Path(__file__).resolve().parents[4]
OUT_PATH = REPO_ROOT / "docs" / "MICROSERVICES-CONTROLLERS.md"

# name -> (port, eureka appname or None for gateway/eureka)
SERVICES = [
    ("gateway", 13000, "gateway-gebo-ai"),
    ("brain", 13001, "brain-gebo-ai"),
    ("vectorizator", 13002, "vectorizator-gebo-ai"),
    ("graphicator", 13003, "graphicator-gebo-ai"),
    ("chunker", 13004, "chunker-gebo-ai"),
    ("git", 13005, "git-gebo-ai"),
    ("filesystem", 13006, "filesystem-gebo-ai"),
    ("uploads", 13007, "uploads-gebo-ai"),
    ("userspace", 13008, "userspace-gebo-ai"),
    ("sharepoint", 13009, "sharepoint-gebo-ai"),
    ("confluence", 13010, "confluence-gebo-ai"),
    ("jira", 13011, "jira-gebo-ai"),
    ("aws-s3", 13012, "aws-s3-gebo-ai"),
    ("googledrive", 13013, "googledrive-gebo-ai"),
    ("mcpclient", 13014, "mcpclient-gebo-ai"),
    ("integration", 13015, "integration-gebo-ai"),
    ("fulltextor", 13016, "fulltextor-gebo-ai"),
    ("eureka", 13017, None),
    ("heimdall", 13018, "heimdall-gebo-ai"),
    ("tyr", 13019, "tyr-gebo-ai"),
    ("webdav", 13020, "webdav-gebo-ai"),
]
NO_CONTROLLER_SERVICES = {"gateway", "eureka", "fulltextor"}
NOTES_NO_CONTROLLERS = {
    "gateway": "Gateway routes to backends via `lb://`; it hosts no controllers of its own — "
               "its own `/v3/api-docs` is empty by design (it proxies/aggregates the backends' "
               "specs at `/api-docs/<service>` when `swagger-on` is active).",
    "eureka": "The Eureka **registry** itself; it is not a `swagger-on` service and exposes no "
              "`/v3/api-docs` — this is the registry dashboard/REST API (`/eureka/apps`), not a "
              "Gebo controller.",
    "fulltextor": "A pure message-driven worker: confirmed by grepping its entire dependency "
                  "tree for `@RestController` (none exist anywhere in it) and by its clean "
                  "startup log, which registers `IGMessageReceiver`/`IGMessageEmitter` for "
                  "`fulltext-module.fulltext-indexing-component` and connects to OpenSearch — "
                  "it consumes chunk-availability messages, downloads the chunk via the "
                  "documents-cache client, and writes it to OpenSearch, with no REST admin "
                  "surface of its own. Its previously-shown 5 controllers were entirely the "
                  "`contentsystems.abstraction.layer` leak (see the top-of-file note); 0 is "
                  "the correct steady state, not a failure.",
}


def fetch(url, timeout=20):
    try:
        with urllib.request.urlopen(url, timeout=timeout) as resp:
            if resp.status != 200:
                return None
            return json.loads(resp.read())
    except (urllib.error.URLError, TimeoutError, json.JSONDecodeError, ConnectionError):
        return None


def detect_context_path(name, port):
    """Try root first (no context-path branch), then /<name> (context-path branch).
    Returns (spec_dict_or_None, context_path_or_None)."""
    if name in NO_CONTROLLER_SERVICES:
        d = fetch(f"http://localhost:{port}/v3/api-docs")
        return d, None
    d = fetch(f"http://localhost:{port}/v3/api-docs")
    if d is not None and d.get("paths"):
        return d, None
    d2 = fetch(f"http://localhost:{port}/{name}/v3/api-docs")
    if d2 is not None:
        return d2, f"/{name}"
    # Root answered but with an empty/degenerate spec (shouldn't normally happen for
    # a real backend) - prefer it over nothing.
    return d, None


def prefix_path(path, ctx):
    return f"{ctx}{path}" if ctx else path


def build_service_section(name, port, appname, ctx, spec):
    title = f"\n## {name}.gebo.ai — port {port}"
    if appname:
        title += f" (`{appname}`)"
    if ctx:
        title += f" — context-path `{ctx}`"
    lines = [title]

    if spec is None:
        lines.append("\n_No spec captured — service was not reachable when this doc was generated._\n")
        return lines, (0, 0)

    paths = spec.get("paths", {})
    if not paths:
        note = NOTES_NO_CONTROLLERS.get(name, "No controllers found.")
        lines.append(f"\n_{note}_\n")
        return lines, (0, 0)

    by_tag = {}
    tag_order = []
    for path, methods in paths.items():
        for method, op in methods.items():
            if method.lower() not in ("get", "post", "put", "delete", "patch", "options", "head"):
                continue
            tags = op.get("tags") or ["(untagged)"]
            for tag in tags:
                if tag not in by_tag:
                    by_tag[tag] = []
                    tag_order.append(tag)
                by_tag[tag].append((method.upper(), prefix_path(path, ctx), op.get("operationId", "")))

    tag_order_sorted = sorted(tag_order)
    n_endpoints = sum(len(v) for v in by_tag.values())
    lines.append(f"\n{len(tag_order_sorted)} controller(s), {n_endpoints} endpoint(s):\n")
    for tag in tag_order_sorted:
        ops = by_tag[tag]
        lines.append(f"\n### `{tag}`")
        lines.append("| Method | Path | Operation |")
        lines.append("|---|---|---|")
        for method, path, opid in sorted(ops, key=lambda x: (x[1], x[0])):
            lines.append(f"| {method} | `{path}` | {opid} |")
    return lines, (len(tag_order_sorted), n_endpoints)


def main():
    body_sections = []
    summary_rows = []
    any_context_path = False

    for name, port, appname in SERVICES:
        spec, ctx = detect_context_path(name, port)
        if ctx:
            any_context_path = True
        section_lines, (n_ctrl, n_ep) = build_service_section(name, port, appname, ctx, spec)
        body_sections.append(section_lines)
        summary_rows.append((name, port, ctx, n_ctrl, n_ep))

    header = [
        "# Microservices — Controllers by Service\n",
        "Generated from each running microservice's live `/v3/api-docs` (springdoc), "
        "after building every image with `-P docker,swagger-on` and bringing up "
        "`dockers/gebo.microservices/docker-compose.yml`. One section per service; "
        "controllers are grouped by their springdoc `tag`, which springdoc derives 1:1 "
        "from the `@RestController` class name (kebab-case).\n",
    ]
    if any_context_path:
        header.append(
            "**Base path note:** this checkout serves backends under a "
            "`server.servlet.context-path` (e.g. `/brain`); every path below already "
            "includes it.\n"
        )
    else:
        header.append(
            "**Base path note:** this checkout serves every backend at its own root "
            "(no `server.servlet.context-path` configured).\n"
        )

    summary = ["\n## Summary\n"]
    if any_context_path:
        summary.append("| Service | Port | Context-path | Controllers | Endpoints |")
        summary.append("|---|---|---|---|---|")
        for name, port, ctx, n_ctrl, n_ep in summary_rows:
            summary.append(f"| {name}.gebo.ai | {port} | `{ctx or '—'}` | {n_ctrl} | {n_ep} |")
    else:
        summary.append("| Service | Port | Controllers | Endpoints |")
        summary.append("|---|---|---|---|")
        for name, port, _ctx, n_ctrl, n_ep in summary_rows:
            summary.append(f"| {name}.gebo.ai | {port} | {n_ctrl} | {n_ep} |")
    summary.append("")  # ensure a trailing blank line before the next markdown block

    out_lines = header + summary
    for section in body_sections:
        out_lines.extend(section)

    OUT_PATH.parent.mkdir(parents=True, exist_ok=True)
    OUT_PATH.write_text("\n".join(out_lines) + "\n")

    print(f"wrote {OUT_PATH}")
    for name, port, ctx, n_ctrl, n_ep in summary_rows:
        status = "OK" if (n_ctrl, n_ep) != (0, 0) or name in NO_CONTROLLER_SERVICES else "EMPTY/UNREACHABLE"
        print(f"  {name:14s} port={port:<6} ctx={ctx or '-':14s} controllers={n_ctrl:<4} endpoints={n_ep:<4} {status}")


if __name__ == "__main__":
    sys.exit(main())
