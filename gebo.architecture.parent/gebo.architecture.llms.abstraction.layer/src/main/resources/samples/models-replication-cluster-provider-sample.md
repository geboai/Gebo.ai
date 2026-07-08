# LLM models-replication cluster — configuration

Participation is **centralized in the shared microservices topology**, not
configured per service. A running service starts the models-replication Hazelcast
cache **only when its own `spring.application.name` is in the shared participant
set**; that is when it registers the
`ai.gebo.architecture.hazelcast.IGModelsReplicationClusterTopologyProvider` bean
whose presence starts the cache.

## Where participation is defined (shared, single source of truth)

- **Built-in default** (shipped in the `gebo.microservices.topology` jar):
  `GeboStandardMicroservices.DEFAULT_MODELS_REPLICATION_PARTICIPANTS` =
  `brain_gebo_ai`, `vectorizator_gebo_ai`, `graphsearch_gebo_ai`.
- **Override for the whole deployment** via the same shared topology config used
  for the messaging topology (e.g. in the gateway's `application.yml`, which
  ships the editable topology):

```yaml
gebo:
  microservices:
    topology:
      # ... existing services map ...
      # Override who participates in the LLM models-replication cache.
      # Omit this key to use the built-in default (brain/vectorizator/graphsearch).
      models-replication-participants:
        - brain.gebo.ai
        - vectorizator.gebo.ai
        - graphsearch.gebo.ai
```

The module resolves each participant id against the shared
`GeboMicroservicesTopology` and builds the Hazelcast member list
(`host:port`), where the host defaults to the participant's dotted application
name (`brain_gebo_ai` → `brain.gebo.ai`). To join, a service simply depends on
`gebo.microservices.models.replication.cluster` (auto-configured) and is listed
in the participant set — no other wiring.

## Optional per-service tuning (`gebo.models.replication.*`)

These are **local** network/deployment knobs, all with working defaults, so a
participating service usually needs none of them. Who participates is **not**
set here.

```yaml
gebo:
  models:
    replication:
      port: 5701                 # Hazelcast bind/reach port (default 5701)
      port-auto-increment: false # default false (deterministic ports)
      cluster-name: gebo-models-cluster
      host-overrides:            # only if a participant's reachable host differs
        graphsearch_gebo_ai: graphsearch.internal.svc
```

> A non-participating service (absent from the set), or a deployable without a
> `spring.application.name` such as the monolith or the gateway, registers no
> provider bean and runs the models DAOs standalone (no-op cluster bus).
