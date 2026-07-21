# gebo-microservices Helm chart

Deploys the Gebo.ai microservices platform to Kubernetes: the Eureka registry, the
API gateway edge, and every backend microservice — with per-service **optional /
mandatory** install toggles, and a **messaging-topology ConfigMap kept in sync with
the deployed footprint**. Stateful dependencies (MongoDB, RabbitMQ, Qdrant, Neo4j,
OpenSearch) are deployed in-cluster by default, or pointed at external/managed
instances.

Mirrors `dockers/gebo.microservices/docker-compose.yml` + its `config/application.yml`.

## Prerequisites

- A Kubernetes cluster + `helm` 3.x.
- The `geboai/*.gebo.ai:<tag>` images **pushed to a registry the cluster can pull**
  (the Maven `-P docker` build produces them locally as `jib:buildTar`; for a cluster
  use `jib:build` to a registry, then set `image.registry` / `imagePullSecrets`).

## Install

```bash
helm install gebo deploy/helm/gebo-microservices \
  --namespace gebo --create-namespace \
  --set image.registry=myregistry.example.com/ \
  --set image.tag=1.0.2.0-SNAPSHOT
```

## Optional / mandatory services

`backends.<svc>.enabled` toggles optional services. **Mandatory** services
(`heimdall`, `brain`, `tyr`, `chunker`, `vectorizator`, plus `eureka` and `gateway`)
are always rendered; setting `enabled: false` on one **fails the install** by design.

```yaml
backends:
  graphicator: { enabled: false }   # drop graphrag (no neo4j needed)
  fulltextor:  { enabled: false }   # drop full-text (no opensearch needed)
  jira:        { enabled: false }   # don't install the Jira connector
```

Whatever you enable is **also** what gets declared in
`gebo.microservices.topology` (rendered with `include-defaults: false`), so the
declared topology never drifts from what is deployed — which is what the tyr
topology coordinator and the workflow-step enablement rely on. If you disable a
connector, its content-handler is simply not installed and not declared; disable
`graphicator`/`fulltextor` and the corresponding workflow step (GRAPHEXTRACTION /
FULLTEXT_INDEXING) is off cluster-wide.

> The topology `services:` map in `templates/configmap-shared.yaml` mirrors
> `GeboStandardMicroservices.DEFAULTS`. If that Java source gains/renames modules,
> update the ConfigMap to match.

## Stateful dependencies

In-cluster by default; flip any to external:

```yaml
infra:
  mongodb:
    enabled: false
    external: { connectionString: "mongodb://user:pass@mongo.prod:27017/?authSource=admin" }
  opensearch:
    enabled: false
    external: { protocol: https, host: opensearch.prod, port: 9200, username: admin }
```

The in-cluster StatefulSets are **dev-grade** (single replica). For production prefer
managed services or dedicated operators.

## Secrets

Dev defaults ship under `secrets:` — **override them**, or bring your own with
`secrets.existingSecret: my-secret` (which must expose the same keys:
`GEBO_TOKEN_SECRET`, `MONGO_USERNAME`, `MONGO_PASSWORD`, `RABBITMQ_PASSWORD`,
`QDRANT_API_KEY`, `NEO4J_PASSWORD`, `OPENSEARCH_PASSWORD`, `OPENAI_API_KEY`,
`ANTHROPIC_API_KEY`). They are consumed as env vars and referenced from the shared
`application.yml` as `${...}` placeholders.

## Discovery

Keeps **Eureka** + client-side load balancing (topology `url.strategy: LOAD_BALANCER`),
matching the compose deployment — no application changes. Instances register by pod
IP (`eureka.instance.prefer-ip-address=true`) so cross-pod discovery works in-cluster.

## Not yet included

- The observability stack (otel-collector / Prometheus / Tempo / Grafana) — point
  `common.otlpTracingEndpoint` at your own collector, or add it later.
- Production hardening of the stateful StatefulSets (HA, backups, node tuning such as
  `vm.max_map_count` for OpenSearch).

## Validate before applying

This chart has not been rendered against a live cluster. Before use:

```bash
helm lint deploy/helm/gebo-microservices
helm template gebo deploy/helm/gebo-microservices | kubectl apply --dry-run=client -f -
```
