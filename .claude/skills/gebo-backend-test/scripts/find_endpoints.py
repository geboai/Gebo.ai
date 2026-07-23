import json
import os
import sys

p = os.path.join(os.path.dirname(os.path.abspath(__file__)), "apidocs.json")
d = json.load(open(p, encoding="utf-8"))
needle = sys.argv[1] if len(sys.argv) > 1 else "Chat"
for path, ops in sorted(d["paths"].items()):
    if needle.lower() in path.lower():
        for m, o in ops.items():
            if m in ("get", "post", "put", "delete", "patch"):
                params = [pp.get("name") for pp in (o.get("parameters") or [])]
                print("%-6s %s   params=%s" % (m.upper(), path, params))
