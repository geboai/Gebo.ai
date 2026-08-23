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
# Wazuh integration files

Ready-to-use configuration for shipping Gebo.ai's security audit trail
(`security-log.jsonl`) into Wazuh. **The step-by-step guide is
[docs/wazuh-integration.md](../../docs/wazuh-integration.md)** — this directory
only holds the files that guide tells you to install.

| File | Goes on | What it does |
|---|---|---|
| [`manager/local_rules.xml`](manager/local_rules.xml) | Wazuh **manager**, as `/var/ossec/etc/rules/local_rules.xml` | Classifies Gebo.ai audit events (logins, credential changes, LLM config, user admin, brute-force correlation). No custom decoder needed — Wazuh's built-in `json` decoder already parses the file. |
| [`manager/agent.conf`](manager/agent.conf) | Wazuh **manager**, as `/var/ossec/etc/shared/gebo-ai/agent.conf` | Centralized `<localfile>` config for the `gebo-ai` agent group: every install layout (deb/rpm, docker, msi, microservices) in one place, so agents need no local edits. |
| [`agent/localfile-blocks.xml`](agent/localfile-blocks.xml) | Wazuh **agent**, pasted into its `ossec.conf` | The same `<localfile>` blocks for agents you configure individually instead of by group. |
| [`agent/ossec.conf`](agent/ossec.conf) | the **containerized** agent | Minimal agent config (log shipping only, no FIM/SCA), mounted by the compose file below. |
| [`docker/docker-compose.wazuh-agent.yml`](docker/docker-compose.wazuh-agent.yml) | the **Docker host** | Runs the Wazuh agent as a container, reading the audit trail read-only, instead of installing an agent package. |
| [`docker/docker-compose.security-logs.override.yml`](docker/docker-compose.security-logs.override.yml) | the **microservices** stack | Bind-mounts each microservice's `/opt/gebo.ai/logs` onto the host — the stock compose file keeps those logs inside the containers, where no agent can reach them. |

Every file in here was verified against a real `wazuh/wazuh-manager:4.14.7` +
`wazuh/wazuh-agent:4.14.7` pair: rules load and fire, `verify-agent-conf`
passes, and the agent enrolls and ships real Gebo.ai audit lines that come out
as alerts on the manager. The audit trail's own Wazuh compatibility is checked
on every build by
`gebo.apps.parent/gebo.ai.app/src/test/java/ai/gebo/ai/app/tests/WazuhSecurityLogCompatibilityTest.java`.
