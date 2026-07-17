"""Reusable Gebo.ai session: logs in once, renews the jwt before it expires, and
exposes get/post plus the fast-setup helpers. Used by the driver scripts so none of
them re-implements auth or fights the ~2 minute token lifetime."""
import json
import os
import time
import urllib.request
import urllib.error

# Credentials come from the environment so no secret is committed. Set them before use:
#   GEBO_BASE  (default http://localhost:12999)
#   GEBO_USER  (admin email)
#   GEBO_PWD   (admin password)
BASE = os.environ.get("GEBO_BASE", "http://localhost:12999")
USER = os.environ.get("GEBO_USER", "")
PWD = os.environ.get("GEBO_PWD", "")


class Session:
    def __init__(self):
        self.token = None
        self.token_at = 0
        self.login()

    def _raw(self, method, path, token=None, body=None, binary=None):
        data = binary if binary is not None else (
            json.dumps(body).encode() if body is not None else None)
        req = urllib.request.Request(BASE + path, data=data, method=method)
        if binary is not None:
            req.add_header("Content-Type", "application/octet-stream")
        elif data is not None:
            req.add_header("Content-Type", "application/json")
        if token:
            req.add_header("Authorization", "Bearer " + token)
        try:
            with urllib.request.urlopen(req, timeout=300) as r:
                raw = r.read()
                return r.status, raw
        except urllib.error.HTTPError as e:
            return e.code, e.read()

    def login(self):
        st, raw = self._raw("POST", "/auth/login", body={"username": USER, "password": PWD})
        d = json.loads(raw)
        self.token = d["result"]["securityHeaderData"]["token"]
        self.token_at = time.time()

    def _fresh(self):
        # tokens live ~2 min; renew proactively past 60s, fall back to a full login
        if time.time() - self.token_at < 60:
            return
        st, raw = self._raw("GET", "/api/users/TokenRenewController/renew", token=self.token)
        renewed = None
        if st == 200 and raw:
            try:
                d = json.loads(raw)
                renewed = (d.get("result", {}) or {}).get("securityHeaderData", {}).get("token") \
                    if isinstance(d, dict) else None
                if not renewed and isinstance(d, dict):
                    renewed = d.get("token")
            except Exception:
                renewed = raw.decode().strip() or None
        if renewed:
            self.token = renewed
            self.token_at = time.time()
        else:
            self.login()

    def get(self, path):
        self._fresh()
        st, raw = self._raw("GET", path, token=self.token)
        return st, (json.loads(raw) if raw else None)

    def post(self, path, body):
        self._fresh()
        st, raw = self._raw("POST", path, token=self.token, body=body)
        return st, (json.loads(raw) if raw else None)

    def post_binary_get_bytes(self, path, body):
        self._fresh()
        st, raw = self._raw("POST", path, token=self.token, body=body)
        return st, raw

    def post_bytes(self, path, data):
        self._fresh()
        st, raw = self._raw("POST", path, token=self.token, binary=data)
        return st, (json.loads(raw) if raw else None)
