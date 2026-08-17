<!--
/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
 -->
# Wazuh integration — shipping Gebo.ai's security audit trail to your SIEM

Gebo.ai writes every security-relevant action to a dedicated, append-only
**JSON Lines** file, `security-log.jsonl`, designed to be ingested by
[Wazuh](https://wazuh.com/) (or any other line-oriented JSON SIEM) with **no
custom decoder**: point an agent at the file with `log_format json` and the
manager's built-in `json` decoder turns every line into queryable fields.

This guide covers the monolithic deployment (Docker, `.deb`/`.rpm`, `.msi`) and
the microservices deployment (Docker Compose, per-host installs), plus the
manager-side rules that turn those events into alerts.

Ready-to-install files live in **[`deploy/wazuh/`](../deploy/wazuh/)** — this
guide tells you which one goes where.

> Everything below was verified against `wazuh/wazuh-manager:4.14.7` and
> `wazuh/wazuh-agent:4.14.7`. The audit trail's Wazuh compatibility is also
> asserted on every build — see [Automated verification](#automated-verification).

---

## Table of contents

1. [What Gebo.ai writes](#1-what-geboai-writes)
2. [Where the file is, per deployment](#2-where-the-file-is-per-deployment)
3. [Manager side: rules (and why no decoder)](#3-manager-side-rules-and-why-no-decoder)
4. [Agent side: telling Wazuh to read the trail](#4-agent-side-telling-wazuh-to-read-the-trail)
   - [4a. Monolith on Docker](#4a-monolith-on-docker)
   - [4b. Monolith on-premise (.deb / .rpm)](#4b-monolith-on-premise-deb--rpm)
   - [4c. Monolith on Windows (.msi)](#4c-monolith-on-windows-msi)
   - [4d. Microservices on Docker Compose](#4d-microservices-on-docker-compose)
   - [4e. Microservices per host, and Kubernetes](#4e-microservices-per-host-and-kubernetes)
5. [Testing it](#5-testing-it)
6. [Rotation, retention and what NOT to ingest](#6-rotation-retention-and-what-not-to-ingest)
7. [Writing your own rules: field gotchas](#7-writing-your-own-rules-field-gotchas)
8. [Troubleshooting](#8-troubleshooting)
9. [Automated verification](#automated-verification)

---

## 1. What Gebo.ai writes

Every executable module — the monolith and each of the 20+ microservices — routes
security events through its own `security-log` SLF4J logger, wired
`additivity="false"` to a dedicated rolling file appender, so audit events are
never mixed into (or rotated away with) the application log. One line = one
complete JSON object, no timestamp/level prefix:

```json
{"architectureType":"MONOLITHIC","userId":"alice","sourceIp":"203.0.113.7","application":"unknown","correlationId":"1f7c...","timestamp":"2026-08-16T10:26:06.407297114Z","stackPoint":"ai.gebo.security.services.impl.GSecurityAuditLoggerServiceImpl#newSecurityEvent <- ai.gebo.secrets.services.impl.GeboSecretsAccessServiceImpl#storeSecret","httpMethod":"POST","requestUri":"/api/secrets/store","action":"secretCreate","category":"secretManagement","details":{},"environment":null,"eventType":"secretManagement","outcome":"success","resourceId":"KB-SHAREPOINT-TOKEN","resourceType":null,"severity":null,"tenantId":null}
```

| Field | Meaning |
|---|---|
| `eventType`, `category` | Event family: `authentication`, `sessionManagement`, `llmConfiguration`, `llmInvocation`, `integrationConfiguration`, `integrationDataAccess`, `secretManagement`, `apiKeyManagement`, `userAdministration` |
| `action` | The specific operation: `authLoginLocal`, `authLoginLocalFailure`, `authLoginOauth2`, `authUnauthorizedAccess`, `sessionTokenRenew`, `llmConfigInsert/Update/Delete`, `llmCredentialsCreate`, `llmInvokeChat/Tts/Transcript/Rank`, `secretCreate/Update/Delete/StorageMigrate`, `apiKeyGenerateSelf/GenerateAdmin/Delete`, `passwordChangeSelf/ChangeAdmin/ResetTicket`, `userInsert/Update/Delete`, `groupInsert/Update/Delete`, `integrationSystem*`, `integrationEndpoint*`, `integrationData*`, `oauth2ClientConfig*` |
| `outcome` | `success`, `failure` or `denied` |
| `architectureType` | `MONOLITHIC` or `MICROSERVICES` |
| `application` | `spring.application.name` of the emitting service (`brain_gebo_ai`, `tyr_gebo_ai`, …; `unknown` when unset, which is the monolith's default) |
| `userId`, `sourceIp`, `httpMethod`, `requestUri`, `correlationId` | Request context, taken from MDC. `null` for events raised outside an HTTP request (schedulers, background workflows) |
| `timestamp` | UTC ISO-8601 instant, nanosecond precision |
| `stackPoint` | Caller trace (3 frames) that raised the event — for forensic drill-down |
| `resourceId`, `resourceType`, `tenantId`, `severity`, `environment`, `details` | Optional per-event detail; `details` is a nested object, flattened by Wazuh as `details.<key>` |

LLM invocations carry **metadata only** (model, provider, outcome, latency) —
never prompt or response content.

Two Wazuh behaviours worth knowing when you query the data: a JSON `null`
arrives as the **literal string `"null"`**, and an empty object (`"details":{}`)
is dropped entirely.

## 2. Where the file is, per deployment

The log directory is `${GEBO_LOG_BASE}` (a system property or environment
variable), defaulting to `logs` **relative to the process working directory**.
Each deployment sets it differently:

Every path below was read out of the deployment procedure that produces it — the
`dockers/` files, the jpackage configuration of the installers, and the Jib
configuration of the microservice images — with the exact source given in the
last column so you can re-check it against your own version of the repo.

| Deployment | `security-log.jsonl` | Where that comes from |
|---|---|---|
| **Monolith, Docker Compose** (`dockers/gebo.ai/docker-compose.yml`) | host **`/home/gebo.ai/logs/`** (container `/opt/gebo.ai/logs/`) | that compose file mounts `- /home/gebo.ai/logs:/opt/gebo.ai/logs`; the image runs with `WORKDIR /opt/gebo.ai` and no `GEBO_LOG_BASE`, so logback's default `logs` resolves there (`dockers/gebo.ai/Dockerfile`) |
| **Monolith, Docker on Windows hosts** (`dockers/gebo.ai/windows/docker-compose.yml`) | container `/opt/gebo.ai/logs/` — **not published on the host** | that compose file declares *no* volumes for the `gebo.ai` service, so the trail stays in the image's anonymous `VOLUME /opt/gebo.ai/logs`. Add a mount (see [4a](#4a-monolith-on-docker)) |
| **Monolith, plain `docker run`** of `geboai/gebo.ai` | container `/opt/gebo.ai/logs/` | `WORKDIR /opt/gebo.ai` + `VOLUME /opt/gebo.ai/logs` in `dockers/gebo.ai/Dockerfile` |
| **All-in-one appliance** `geboai/easyinstall.gebo.ai` | container `/opt/gebo.ai/logs/` | supervisord starts it with `directory=/opt/gebo.ai` (`dockers/easyinstall.gebo.ai/supervisord.conf`), and `VOLUME /opt/gebo.ai/logs` is declared in its Dockerfile. **`/var/log/gebo.ai/` is not the audit trail** — supervisord only captures the process's stdout/stderr there |
| **Monolith, `.deb` / `.rpm`** (built with `-P package-unix-deb` / `-P package-unix-rpm`) | **`/var/log/gebo-ai/`** | the jpackage launcher config carries `-DGEBO_LOG_BASE=/var/log/gebo-ai` (`gebo.ai.app/pom.xml`), and the package's `postinst` creates and chowns that directory. The app itself installs to `/home/gebo-ai/gebo-ai/` and runs from `WorkingDirectory=/home/gebo-ai` as user `gebo-ai` |
| **Monolith, `.msi`** (built with `-P package-windows`) | **`<install dir>\app\instance\logs\`** | the launcher config carries `-DGEBO_LOG_BASE=$APPDIR\instance\logs`, and jpackage's `$APPDIR` is the `app` folder under the install directory (`src/packaging/msi/instance/…` is copied into it). The MSI is built with a directory chooser (`winDirChooser`) over the default `Program Files\GeboAI`, so **the install directory is chosen at install time** — resolve it as shown in [4c](#4c-monolith-on-windows-msi) |
| **Microservices, Docker Compose** (`dockers/gebo.microservices/docker-compose.yml`) | container `/opt/gebo.ai/logs/` per service — **not published on the host** | the Jib images run with `workingDirectory /opt/gebo.ai` (`gebo.microservices.apps.parent/pom.xml`, `docker` profile) and the compose file mounts only `config`, `home` and `work`. Publish them with the [override](#4d-microservices-on-docker-compose) |
| **Gateway, `.deb` / `.rpm`** | **`/home/gebo-ai-gateway/logs/`** | its jpackage config does *not* set `GEBO_LOG_BASE`, so the `logs` default applies under the unit's `WorkingDirectory=/home/gebo-ai-gateway` (`gateway.gebo.ai/src/packaging/deb/resources/postinst`). The app installs to `/home/gebo-ai-gateway/gebo-ai-gateway/` |
| **Kubernetes** (`deploy/helm/gebo-microservices`) | pod `/opt/gebo.ai/logs/` | same Jib images; the chart mounts `emptyDir`s for `home`/`work` only — see [4e](#4e-microservices-per-host-and-kubernetes) |
| **Bootable jar started by hand** | `<working directory>/logs/` | logback's `${GEBO_LOG_BASE:-logs}` default |

Rotated archives always sit in `<log dir>/archived/security-log-<date>.<n>.jsonl.gz`.

> **Confirm before configuring the agent** — one command, no guessing:
>
> ```bash
> # containers (monolith, appliance, any microservice)
> docker exec <container> ls -l /opt/gebo.ai/logs/security-log.jsonl
> # and where that directory actually lives on the host
> docker inspect -f '{{range .Mounts}}{{.Source}} -> {{.Destination}}{{"\n"}}{{end}}' <container>
> # package installs
> systemctl show -p WorkingDirectory,ExecStart gebo-ai
> ls -l /var/log/gebo-ai/security-log.jsonl
> ```
>
> If the file does not exist yet, nothing security-relevant has happened on that
> instance: log in once and it appears.

## 3. Manager side: rules (and why no decoder)

**No decoder to write.** Wazuh's built-in `json` decoder handles the file
because it is JSON Lines. Install the classification rules instead:

```bash
# on the Wazuh manager
cp deploy/wazuh/manager/local_rules.xml /var/ossec/etc/rules/local_rules.xml
chown wazuh:wazuh /var/ossec/etc/rules/local_rules.xml
/var/ossec/bin/wazuh-control restart
grep -iE "rule|CRITICAL" /var/ossec/logs/ossec.log | tail    # must show no rule error
```

A bad rule file **stops `wazuh-analysisd` from starting**, so always check
`ossec.log` after the restart.

[`deploy/wazuh/manager/local_rules.xml`](../deploy/wazuh/manager/local_rules.xml)
ships:

| Rule | Level | Fires on |
|---|---|---|
| 100200 | 0 | Any Gebo.ai audit event (parent rule; groups the whole trail as `gebo_ai`) |
| 100201 | 3 | Successful login |
| 100202 | 5 | Failed login |
| 100203 | 10 | **5 failed logins from the same `sourceIp` within 120 s** (brute force) |
| 100204 | 8 | Access denied (`outcome=denied`, e.g. `authUnauthorizedAccess`) |
| 100205 | 3 | Session events (token renew) |
| 100210 | 7 | Secret / API-key create, rotate, delete |
| 100211 | 5 | LLM configuration change |
| 100212 | 7 | User / group administration |
| 100213 | 3 | 3rd-party integration configuration change |
| 100214 | 0 | 3rd-party data access (high volume: archived, not alerted) |
| 100215 | 0 | LLM invocation (high volume: archived, not alerted) |
| 100216 | 6 | A 3rd-party/LLM operation failed (spot revoked or expired credentials) |

Raise or lower the levels to fit your alerting policy; `level 0` means "store
it, do not alert on it".

## 4. Agent side: telling Wazuh to read the trail

### Installing the agent (skip if the host already has one)

Standard Wazuh installation, reproduced here for convenience — always check
[Wazuh's own install docs](https://documentation.wazuh.com/current/installation-guide/wazuh-agent/index.html)
for the repository URLs of your version. Enrolling straight into the `gebo-ai`
group is what makes the centralized configuration below apply itself:

```bash
# Debian / Ubuntu (the Gebo.ai host)
curl -s https://packages.wazuh.com/key/GPG-KEY-WAZUH | sudo gpg --no-default-keyring \
  --keyring gnupg-ring:/usr/share/keyrings/wazuh.gpg --import
sudo chmod 644 /usr/share/keyrings/wazuh.gpg
echo "deb [signed-by=/usr/share/keyrings/wazuh.gpg] https://packages.wazuh.com/4.x/apt/ stable main" \
  | sudo tee /etc/apt/sources.list.d/wazuh.list
sudo apt-get update
sudo WAZUH_MANAGER="wazuh.example.internal" \
     WAZUH_AGENT_NAME="gebo-ai-prod-01" \
     WAZUH_AGENT_GROUP="gebo-ai" apt-get install -y wazuh-agent
sudo systemctl daemon-reload && sudo systemctl enable --now wazuh-agent
```

```bash
# RHEL / Rocky / Alma
sudo rpm --import https://packages.wazuh.com/key/GPG-KEY-WAZUH
sudo tee /etc/yum.repos.d/wazuh.repo >/dev/null <<'EOF'
[wazuh]
gpgcheck=1
gpgkey=https://packages.wazuh.com/key/GPG-KEY-WAZUH
enabled=1
name=Wazuh repository
baseurl=https://packages.wazuh.com/4.x/yum/
protect=1
EOF
sudo WAZUH_MANAGER="wazuh.example.internal" \
     WAZUH_AGENT_NAME="gebo-ai-prod-01" \
     WAZUH_AGENT_GROUP="gebo-ai" yum install -y wazuh-agent
sudo systemctl daemon-reload && sudo systemctl enable --now wazuh-agent
```

```powershell
# Windows (run as Administrator)
msiexec.exe /i wazuh-agent-4.x.msi /q ^
  WAZUH_MANAGER="wazuh.example.internal" ^
  WAZUH_AGENT_NAME="gebo-ai-win-01" ^
  WAZUH_AGENT_GROUP="gebo-ai"
NET START WazuhSvc
```

> The **package** installers read `WAZUH_MANAGER`; the **container** image reads
> `WAZUH_MANAGER_SERVER` (see [4a](#4a-monolith-on-docker)). Same purpose,
> different variable name — a classic hour lost.

### Pointing it at the audit trail

You need one `<localfile>` block per audit trail, with `log_format json`. Two
ways to deliver it:

- **Centralized (recommended)** — put the blocks in an agent-group config on the
  manager, and enroll the Gebo.ai hosts into that group. Nothing to edit on the
  Gebo.ai side, ever:

  ```bash
  # on the Wazuh manager
  /var/ossec/bin/agent_groups -a -g gebo-ai -q
  cp deploy/wazuh/manager/agent.conf /var/ossec/etc/shared/gebo-ai/agent.conf
  chown wazuh:wazuh /var/ossec/etc/shared/gebo-ai/agent.conf
  chmod 660 /var/ossec/etc/shared/gebo-ai/agent.conf
  /var/ossec/bin/verify-agent-conf        # MUST print OK - a bad agent.conf is silently ignored by agents
  /var/ossec/bin/wazuh-control restart
  ```

  [`deploy/wazuh/manager/agent.conf`](../deploy/wazuh/manager/agent.conf) already
  contains every layout below, split into `os="Linux"` and `os="Windows"`
  sections.

- **Per agent** — paste the matching block from
  [`deploy/wazuh/agent/localfile-blocks.xml`](../deploy/wazuh/agent/localfile-blocks.xml)
  into that agent's `ossec.conf` and restart it.

Then enroll the host into the group (`-G gebo-ai` at registration,
`<groups>gebo-ai</groups>` in its `<enrollment>`, `WAZUH_AGENT_GROUP=gebo-ai`
for the containerized agent, or from the dashboard afterwards).

### 4a. Monolith on Docker

`dockers/gebo.ai/docker-compose.yml` already publishes the log directory on the
host (`/home/gebo.ai/logs`), so an agent **on the Docker host** needs nothing
from the stack:

```xml
<localfile>
  <log_format>json</log_format>
  <location>/home/gebo.ai/logs/security-log.jsonl</location>
</localfile>
```

Prefer not to install an agent package on the host? Run the agent as a
container:

```bash
export WAZUH_MANAGER_SERVER=wazuh.example.internal   # your manager
export WAZUH_AGENT_NAME=gebo-ai-prod-01
docker compose -f deploy/wazuh/docker/docker-compose.wazuh-agent.yml up -d
docker logs -f gebo-wazuh-agent
```

[`docker-compose.wazuh-agent.yml`](../deploy/wazuh/docker/docker-compose.wazuh-agent.yml)
mounts `/home/gebo.ai/logs` **read-only** and
[`deploy/wazuh/agent/ossec.conf`](../deploy/wazuh/agent/ossec.conf) (log
shipping only — no FIM/SCA/vulnerability scanning; install a real host agent if
you want those).

Two container-agent specifics that are easy to get wrong:

- the image environment variable is **`WAZUH_MANAGER_SERVER`**, not
  `WAZUH_MANAGER`;
- the image copies your mounted `/wazuh-config-mount/etc/ossec.conf` into place
  **and then** substitutes the `CHANGE_MANAGER_IP` / `CHANGE_ENROLL_IP` /
  `CHANGE_AGENT_NAME` / `CHANGE_AGENT_GROUP` placeholders from the environment —
  so leave those placeholders in the file and set the environment variables.

#### If the trail is not published on the host

Two supported ways of running the monolith keep the audit trail inside the
container, in the image's anonymous `VOLUME /opt/gebo.ai/logs`: the
**all-in-one appliance** (`docker run -p 12999:12999 geboai/easyinstall.gebo.ai`,
as documented in `dockers/easyinstall.gebo.ai/README.md`) and the **Windows-host
compose file** (`dockers/gebo.ai/windows/docker-compose.yml`, which declares no
volumes for the `gebo.ai` service). An anonymous volume survives a restart but
not a `docker rm`, so publish it before you rely on it:

```bash
# appliance: give the audit trail (and the rest of /opt/gebo.ai/logs) a real home
docker run -d -p 12999:12999 \
  -v /home/gebo.ai/logs:/opt/gebo.ai/logs \
  geboai/easyinstall.gebo.ai
```

```yaml
# dockers/gebo.ai/windows/docker-compose.yml - add to the gebo.ai service
    volumes:
      - C:\gebo.ai\logs:/opt/gebo.ai/logs
```

Then point the agent at the host side of that mount, exactly as above. The
container-agent compose file takes the host directory from
`GEBO_MONOLITH_LOG_DIR` (default `/home/gebo.ai/logs`), so
`GEBO_MONOLITH_LOG_DIR=/home/gebo.ai/logs docker compose -f … up -d` covers both
cases without editing anything.

### 4b. Monolith on-premise (.deb / .rpm)

```xml
<localfile>
  <log_format>json</log_format>
  <location>/var/log/gebo-ai/security-log.jsonl</location>
</localfile>
```

That directory is created and chowned to `gebo-ai:gebo-ai` by the package's
`postinst`, and the launcher writes there because its jpackage config carries
`-DGEBO_LOG_BASE=/var/log/gebo-ai`. The Wazuh agent's logcollector runs as root,
so it reads the file as is — no permission change needed.

Useful when something looks off (the unit is `gebo-ai.service`, the app lives in
`/home/gebo-ai/gebo-ai/`, and its JVM options — including `GEBO_LOG_BASE` — are
in the launcher's `.cfg`):

```bash
systemctl cat gebo-ai | head -20
grep -i gebo_log_base /home/gebo-ai/gebo-ai/lib/app/gebo-ai.cfg
ls -l /var/log/gebo-ai/
```

### 4c. Monolith on Windows (.msi)

The `.msi` is built with a directory chooser over the default
`Program Files\GeboAI`, so **the install directory is whatever was picked at
install time** — read it off the installed service instead of assuming, then
append `app\instance\logs\security-log.jsonl` (the launcher sets
`GEBO_LOG_BASE=$APPDIR\instance\logs`, and `$APPDIR` is the `app` folder of the
install directory):

```powershell
# resolve the install directory from the Gebo.ai service, whatever it is called
$exe = (Get-CimInstance Win32_Service |
        Where-Object { $_.PathName -like '*gebo-ai.exe*' } |
        Select-Object -First 1).PathName.Trim('"')
$trail = Join-Path (Split-Path $exe) 'app\instance\logs\security-log.jsonl'
$trail; Test-Path $trail
```

With the default install directory that is
`C:\Program Files\GeboAI\app\instance\logs\security-log.jsonl`. Put the path the
command printed into the agent config:

```xml
<localfile>
  <log_format>json</log_format>
  <location>C:\Program Files\GeboAI\app\instance\logs\security-log.jsonl</location>
</localfile>
```

Then restart the agent: `Restart-Service -Name WazuhSvc`.

### 4d. Microservices on Docker Compose

The stock `dockers/gebo.microservices/docker-compose.yml` **keeps each service's
logs inside its container**, where no agent can read them and where they are
lost when the container is replaced. Publish them first, with the shipped
override file:

```bash
cd dockers/gebo.microservices
export GEBO_SECURITY_LOG_BASE=/var/log/gebo-ai-microservices   # or add it to .env
sudo mkdir -p "$GEBO_SECURITY_LOG_BASE"
docker compose -f docker-compose.yml \
               -f ../../deploy/wazuh/docker/docker-compose.security-logs.override.yml \
               up -d

# verify the merge did what you expect, before starting anything:
docker compose -f docker-compose.yml \
               -f ../../deploy/wazuh/docker/docker-compose.security-logs.override.yml \
               config | grep -A3 "target: /opt/gebo.ai/logs" | head
```

Each of the 21 Gebo services now writes to
`$GEBO_SECURITY_LOG_BASE/<service>/security-log.jsonl` on the host — e.g.
`/var/log/gebo-ai-microservices/heimdall/security-log.jsonl`. If you skip
`GEBO_SECURITY_LOG_BASE` the override falls back to `./security-logs`, i.e.
`dockers/gebo.microservices/security-logs/<service>/security-log.jsonl`, which is
fine for a test cluster but a poor place for an audit trail in production — set
the variable.

The service names are the compose service names (`eureka`, `gateway`, `heimdall`,
`brain`, `vectorizator`, `graphicator`, `chunker`, `fulltextor`, `git`,
`filesystem`, `uploads`, `userspace`, `sharepoint`, `confluence`, `jira`,
`aws-s3`, `googledrive`, `mcpclient`, `webdav`, `integration`, `tyr`), so one
wildcard block covers the whole cluster:

```xml
<localfile>
  <log_format>json</log_format>
  <location>/var/log/gebo-ai-microservices/*/security-log.jsonl</location>
</localfile>
```

The directory wildcard is expanded by the agent's logcollector, so services added
later are picked up without touching the agent (verified: it reports
`Analyzing file: '…/<service>/security-log.jsonl'` once per matching directory).

The events stay attributable without any per-service configuration: each carries
`architectureType: MICROSERVICES` and its own `application` field
(`brain_gebo_ai`, `heimdall_gebo_ai`, `tyr_gebo_ai`, …). Wazuh also tags each
alert with the agent that shipped it, so one agent per host is enough.

Where the security-relevant events actually come from, if you want to narrow the
set of services you watch: **heimdall** (authentication, tokens, users/groups,
secrets), **brain** (LLM configuration and invocation), **tyr** (workflows, jobs,
usage), the **gateway** (edge access), and every content-handler service
(`sharepoint`, `confluence`, `jira`, `git`, `googledrive`, `aws-s3`, `webdav`,
`mcpclient`, `filesystem`, …) for integration configuration and data access.
Watching all of them is the safe default.

### 4e. Microservices per host, and Kubernetes

Of the microservices, only the **gateway** ships as `.deb`/`.rpm`
(`gateway.gebo.ai`, profiles `package-unix-deb` / `package-unix-rpm`). Its
`postinst` installs the app in `/home/gebo-ai-gateway/gebo-ai-gateway/` and runs
`gebo-ai-gateway.service` with `WorkingDirectory=/home/gebo-ai-gateway`, and —
unlike the monolith package — its launcher does **not** set `GEBO_LOG_BASE`, so
the trail is:

```xml
<localfile>
  <log_format>json</log_format>
  <location>/home/gebo-ai-gateway/logs/security-log.jsonl</location>
</localfile>
```

Any other service started by hand from its own directory follows the same rule
(`<working directory>/logs/`); `/home/gebo-ai-*/logs/security-log.jsonl` covers a
host running several of them.

Cleaner: set `GEBO_LOG_BASE` explicitly in the unit file so the trail lands in
`/var/log` like the monolith package's does, then use that path instead:

```ini
# /etc/systemd/system/gebo-ai-gateway.service.d/override.conf
[Service]
Environment=GEBO_LOG_BASE=/var/log/gebo-ai-gateway
```

```bash
systemctl daemon-reload && systemctl restart gebo-ai-gateway
```

On **Kubernetes** (`deploy/helm/gebo-microservices`) the chart does not mount a
log volume today, so apply the same idea in your values/patches: give each pod a
volume for `/opt/gebo.ai/logs` (or set `GEBO_LOG_BASE` to a mounted path) and
either run a `wazuh-agent` sidecar reading it, or a node-level agent reading a
`hostPath`. The agent-side configuration is identical — `log_format json` on
`security-log.jsonl`.

## 5. Testing it

**Does Wazuh understand a line?** Copy one real line and feed it to the
manager's own decoder/rule tester — this is exactly what the automated test
does:

```bash
# on the manager (or: docker exec -i <manager container> ...)
head -1 /var/log/gebo-ai/security-log.jsonl | /var/ossec/bin/wazuh-logtest
```

Note that `wazuh-logtest` writes its whole report to **stderr** (add `2>&1` when
piping it into `grep`). Expected — decoder `json`, every field extracted, and one
of the Gebo rules firing:

```
**Phase 2: Completed decoding.
	name: 'json'
	action: 'secretDelete'
	architectureType: 'MONOLITHIC'
	category: 'secretManagement'
	eventType: 'secretManagement'
	outcome: 'success'
	userId: 'alice'
	...
**Phase 3: Completed filtering (rules).
	id: '100210'
	level: '7'
	description: 'Gebo.ai: credential store change (secretDelete, success) by user alice.'
```

**Is the agent actually shipping?**

```bash
# agent: is the file being tailed?
grep -i "security-log.jsonl" /var/ossec/logs/ossec.log
# manager: are alerts arriving?
tail -f /var/ossec/logs/alerts/alerts.json | grep -o '"id":"1002[0-9][0-9]"'
```

Then generate a real event — log in to Gebo.ai with a wrong password five times
and rule **100203** should fire.

In the Wazuh dashboard, `rule.groups: gebo_ai` selects the whole audit trail;
useful drill-downs are `data.eventType`, `data.action`, `data.outcome`,
`data.userId`, `data.sourceIp`, `data.application` and `data.correlationId`.

## 6. Rotation, retention and what NOT to ingest

Gebo.ai rotates `security-log.jsonl` daily and at 100 MB, gzipping archives into
`<log dir>/archived/`. Retention defaults to 365 days / 10 GB total and is
overridable per module:

```bash
SECURITY_LOG_RETENTION_DAYS=730
SECURITY_LOG_TOTAL_SIZE_CAP=50GB
```

**Never point a `<localfile>` at `archived/*.jsonl.gz`.** Those lines were
already shipped while the file was live; ingesting them again duplicates events
(and Wazuh does not read gzip anyway). The agent follows the live file across
rotation on its own.

Keep the archives regardless of Wazuh: they are the on-host copy of the audit
trail, and the retention window above is what your NIS2 evidence period rests on.

## 7. Writing your own rules: field gotchas

- **`action` cannot be matched with `<field>`.** Wazuh treats `action` as one of
  its *static* field names: a rule containing
  `<field name="action">…</field>` makes `wazuh-analysisd` refuse to load the
  whole ruleset — `ERROR: Failure to read rule <id>. Field 'action' is static.`
  The static `<action>…</action>` tag does not match the JSON-decoded value
  either. Match on `eventType`, `category` and `outcome` instead (Gebo.ai's
  taxonomy keeps `action` redundant for classification), and use `$(action)` in
  the description to show it.
- **`sourceIp` is not Wazuh's `srcip`.** Correlate with
  `<same_field>sourceIp</same_field>`, not `<same_source_ip/>` (that is what
  rule 100203 does).
- **Sibling rules under the same parent are not tried in file order.** Avoid
  broad catch-all rules competing with specific ones under the same
  `<if_sid>`; give each rule a disjoint condition (that is why the shipped rules
  are one-per-`eventType`).
- **`details` is flattened.** A nested `details.reason` in the event is matched
  as `<field name="details.reason">`.
- **Test before trusting.** Any rule you add:
  `echo '<one audit line>' | /var/ossec/bin/wazuh-logtest 2>&1` and check the
  phase-3 id/level.

## 8. Troubleshooting

| Symptom | Cause / fix |
|---|---|
| No alerts at all, agent connected | The manager has no Gebo rules (only `level 0` grouping matched, or the generic rule 1002 fired). Install [`local_rules.xml`](../deploy/wazuh/manager/local_rules.xml). |
| `wazuh-analysisd` won't start after adding rules | Rule syntax/semantics — check `/var/ossec/logs/ossec.log`. `Field 'action' is static` is the classic one, see [§7](#7-writing-your-own-rules-field-gotchas). |
| Agent log: cannot open `security-log.jsonl` | Nothing security-relevant has happened yet (the file is created on the first event), or the path is wrong for this deployment — recheck [§2](#2-where-the-file-is-per-deployment). |
| Events arrive as plain text, no fields | The `<localfile>` block is missing `<log_format>json</log_format>`, or a line is not valid JSON (see next row). |
| One line breaks parsing | A non-JSON line reached the file. The only known way this happens is a serialization failure inside the audit logger, which logs an error on the same logger — treat it as a bug and report it; the automated test asserts every line is a single complete JSON object. |
| Duplicated events | Something is also ingesting `archived/*.jsonl.gz`, or two agents watch the same path. |
| Microservices produce no trail | The stock compose file keeps logs inside the containers — apply the [override](#4d-microservices-on-docker-compose). |
| `userId` / `sourceIp` are `"null"` | Expected for events raised outside an HTTP request (schedulers, background workflows), and Wazuh renders JSON `null` as the string `"null"`. |
| Agent buffer full during ingestion | Ingestion runs burst `integrationDataAccess` events; raise `<events_per_second>` in the agent's `<client_buffer>`. |

## Automated verification

`gebo.ai.app`'s test suite ends with
[`WazuhSecurityLogCompatibilityTest`](../gebo.apps.parent/gebo.ai.app/src/test/java/ai/gebo/ai/app/tests/WazuhSecurityLogCompatibilityTest.java),
which takes the `security-log.jsonl` that all the other tests just produced,
starts a real `wazuh/wazuh-manager` container, pipes the audit lines through
`wazuh-logtest`, and asserts that Wazuh's built-in `json` decoder is selected and
that every field (and the `timestamp`/`eventType`/`category`/`action`/`outcome`
taxonomy) survives decoding unchanged. It also validates the file as JSON Lines,
line by line.

```bash
# whole suite (the Wazuh check runs last, after the tests that fill the trail)
mvn clean package

# just the compatibility check, against the trail already on disk
cd gebo.apps.parent/gebo.ai.app
mvn -o test -Dtest=WazuhSecurityLogCompatibilityTest -Dsurefire.failIfNoSpecifiedTests=false

# skip it (avoids pulling the ~900MB Wazuh image)
mvn package -Dgebo.wazuh.compatibility.test.skip=true
```

It skips itself when Docker is unavailable or the trail is empty, and
`-Dgebo.wazuh.image=wazuh/wazuh-manager:<tag>` pins a different Wazuh version —
useful for checking a manager upgrade before rolling it out.
