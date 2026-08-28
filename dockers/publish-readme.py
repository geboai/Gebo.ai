#!/usr/bin/env python3
"""
publish-readme.py — push a local README.md to a Docker Hub repository's
"full_description" (the overview shown on the repo's Docker Hub page).

Docker Hub does NOT accept a README as part of `docker push`; the repo overview
is separate metadata updated through the Hub web API. This script logs in to the
Hub API and PATCHes the repository's full_description with the file contents.

Usage:
  publish-readme.py <namespace/repo> <path-to-README.md>

Credentials (first match wins):
  1. DOCKERHUB_USERNAME + DOCKERHUB_TOKEN  (or DOCKERHUB_PASSWORD)
  2. the username:password stored for index.docker.io in ~/.docker/config.json
A Personal Access Token works in place of the password.

Exit codes: 0 = updated, 1 = failure (bad creds, HTTP error, missing file).
"""
import base64
import json
import os
import sys
import urllib.error
import urllib.request

HUB = "https://hub.docker.com/v2"
MAX_LEN = 25000  # Docker Hub full_description hard limit


def creds():
    user = os.environ.get("DOCKERHUB_USERNAME", "").strip()
    pw = (os.environ.get("DOCKERHUB_TOKEN")
          or os.environ.get("DOCKERHUB_PASSWORD") or "").strip()
    if user and pw:
        return user, pw
    # Fall back to the credential docker login already stored.
    path = os.path.expanduser("~/.docker/config.json")
    try:
        cfg = json.load(open(path))
    except Exception:
        return None, None
    auth = (cfg.get("auths", {})
               .get("https://index.docker.io/v1/", {})
               .get("auth"))
    if not auth:
        return None, None
    try:
        raw = base64.b64decode(auth).decode("utf-8")
        u, _, p = raw.partition(":")
        return u, p
    except Exception:
        return None, None


def api(method, url, token=None, payload=None):
    data = json.dumps(payload).encode("utf-8") if payload is not None else None
    req = urllib.request.Request(url, data=data, method=method)
    req.add_header("Content-Type", "application/json")
    req.add_header("Accept", "application/json")
    if token:
        req.add_header("Authorization", "JWT " + token)
    with urllib.request.urlopen(req, timeout=60) as resp:
        body = resp.read().decode("utf-8")
        return resp.status, (json.loads(body) if body else {})


def main():
    if len(sys.argv) != 3:
        print(__doc__)
        return 2
    repo, readme_path = sys.argv[1], sys.argv[2]

    if not os.path.isfile(readme_path):
        print(f"ERROR: README not found: {readme_path}", file=sys.stderr)
        return 1
    text = open(readme_path, encoding="utf-8").read()
    if len(text) > MAX_LEN:
        print(f"ERROR: {readme_path} is {len(text)} chars, over the Docker Hub "
              f"{MAX_LEN}-char full_description limit", file=sys.stderr)
        return 1

    user, pw = creds()
    if not user or not pw:
        print("ERROR: no Docker Hub credentials. Set DOCKERHUB_USERNAME and "
              "DOCKERHUB_TOKEN, or run `docker login` first.", file=sys.stderr)
        return 1

    try:
        _, tok = api("POST", f"{HUB}/users/login",
                     payload={"username": user, "password": pw})
    except urllib.error.HTTPError as e:
        print(f"ERROR: Docker Hub login failed ({e.code}). If the account uses "
              f"2FA, use a Personal Access Token as DOCKERHUB_TOKEN.",
              file=sys.stderr)
        return 1
    token = tok.get("token")
    if not token:
        print("ERROR: login returned no token", file=sys.stderr)
        return 1

    try:
        status, _ = api("PATCH", f"{HUB}/repositories/{repo}/", token=token,
                        payload={"full_description": text})
    except urllib.error.HTTPError as e:
        detail = e.read().decode("utf-8", "replace")
        print(f"ERROR: updating {repo} README failed ({e.code}): {detail}",
              file=sys.stderr)
        return 1

    print(f"Updated Docker Hub README for {repo} ({len(text)} chars, HTTP {status})")
    return 0


if __name__ == "__main__":
    sys.exit(main())
