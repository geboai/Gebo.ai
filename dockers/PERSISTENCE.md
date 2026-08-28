# Gebo.ai container persistence

What has to survive an update/upgrade in every containerised deployment, where it
lives, and how to move data when you change deployments.

The rule this document exists to enforce: **an anonymous volume is not
persistence.** Docker Compose carries an anonymous volume across a container
*recreate*, but `docker compose down` — the first half of every upgrade — leaves
it dangling, the next `up` creates a fresh empty one, and a later
`docker system prune --volumes` deletes it for good. Every persistent path in
this repository is now a **named volume or a host bind**.

---

## 1. The two directories the application owns

Both come from environment variables read by
`ai.gebo.architecture.environment.EnvironmentHolder`, which every Spring Boot
entry point checks at startup (`Main.java`, and each microservice's
`<Service>Application.java` — they `System.exit(-1)` when `GEBO_HOME` is unset).

### `GEBO_WORK_DIRECTORY` — `/opt/gebo.ai/work`

**The single most important directory to back up.** It holds durable user data
that Mongo only *indexes*:

| Contents | Written by |
|---|---|
| `SESSIONS_AREA/<session>/` — chat-session attachments, LLM-generated resources | `GChatStorageAreaServiceImpl` |
| Per-endpoint content mirrors (`<systemType>/<endpointCode>/`) | `GLocalPersistentFolderDiscoveryServiceImpl` |
| `DOCUMENTSCACHE/` — cached source documents and their chunks | `DocumentsCacheServiceImpl`, `DocumentsChunkServiceImpl` |
| Upload staging and handshakes | `UploadsSystemsManagementServiceImpl` |
| Google Workspace OAuth **tokens** | `GeboGoogleWorkspaceFlowSessionFactory` |

Losing it does **not** produce a clean empty system — it produces a Mongo
database full of references to files that no longer exist. **Mongo and the work
directory are one backup unit: snapshot and restore them together.**

`GLocalPersistentFolderDiscoveryServiceImpl` also *throws* if the directory does
not exist, which is why `dockers/gebo.ai.platform/Dockerfile` creates the whole
`/opt/gebo.ai` layout — the Jib-built microservice images cannot run a `mkdir` of
their own.

### `GEBO_HOME` — `/opt/gebo.ai/home`

The application's own home area. Currently a hard startup gate that is otherwise
only read back, but it is a declared persistent surface (`README.md`) and is
mounted everywhere so it stays one.

---

## 2. The other persistent paths

| Path | What | Lose it and… |
|---|---|---|
| `/opt/gebo.ai/logs` | `security-log.jsonl` — the append-only **GDPR Art. 32 / NIS2 Art. 21 audit trail** — plus `gc.log` | the compliance evidence is gone, and it is gone *silently* |
| `/opt/gebo.ai/shares` | host content the filesystem handler **ingests** | nothing of Gebo.ai's own; mount read-only (no code path writes here) |
| `/opt/gebo.ai/config` | Spring's additional config location | — see the warning below |

> **`/opt/gebo.ai/config` is a MOUNT, never a volume.**
> It used to carry `VOLUME` in `dockers/gebo.ai/Dockerfile` and
> `dockers/easyinstall.gebo.ai/Dockerfile`. That is an upgrade hazard, not
> persistence, and it reproduces: Docker seeds an anonymous volume there from the
> image on first run, Compose carries that same volume across the container
> recreate an upgrade performs, and the container goes on serving the **first**
> image's `application.yml` however many times you upgrade. Bind your own
> directory over it, or take the baked default — which now tracks the image,
> because nothing shadows it.

Infrastructure, in every deployment:

| Service | Path | Rebuildable? |
|---|---|---|
| MongoDB | `/data/db` | **No** — users, LLM/KB config, chat history, the index of `work/` |
| Qdrant | `/qdrant/storage` | Only by re-embedding the whole corpus — which costs real money at the LLM provider |
| Neo4j | `/data` | Only by re-running GraphRAG extraction |
| OpenSearch | `/usr/share/opensearch/data` | Only by re-ingesting |
| RabbitMQ | `/var/lib/rabbitmq` | Durable queue/exchange/binding definitions, users, unacked persistent messages — losing it drops ingestion work still in flight |
| Prometheus / Grafana / Tempo | `/prometheus`, `/var/lib/grafana`, `/var/tempo` | History only; nothing functional |

---

## 3. Per deployment

### Monolith, Linux — `dockers/gebo.ai/docker-compose.yml`

**Named volumes throughout** (project name `gebo-monolith`, so the real names are
`gebo-monolith_<key>`): `gebo-mongo`, `gebo-qdrant`, `gebo-neo4j-data`,
`gebo-neo4j-logs`, `gebo-os`, and the application's `gebo-work`, `gebo-home`,
`gebo-logs`, `gebo-shares`, plus `prometheus-data` / `grafana-data` /
`tempo-data`. No host tree to pre-create; Docker creates the volumes on first
`up`.

The only binds are read-only **config files** (`./config`, `./prometheus.yml`,
`./tempo-config.yaml`, `./otel-collector-config.yaml`, `./grafana/provisioning`)
— configuration, not data.

`gebo-shares` (the read-only ingest area, container `/opt/gebo.ai/shares`) is a
named volume so the image's `VOLUME` does not become anonymous; to expose real
host content to the filesystem handler, replace that one line with a bind, e.g.
`- /srv/company-shares:/opt/gebo.ai/shares:ro`.

The audit trail is the named volume `gebo-monolith_gebo-logs`; the Wazuh agent
compose (`deploy/wazuh/docker/docker-compose.wazuh-agent.yml`) mounts it as an
external volume — see `docs/wazuh-integration.md` for a host agent, which needs
a host bind instead.

### Monolith, Windows — `dockers/gebo.ai/windows/docker-compose.yml`

**Named volumes throughout.** This file previously declared *no volumes at all*:
every container wrote to its throwaway layer, so one `docker compose down` — or
any image upgrade, which recreates containers — discarded the entire
installation. Named rather than bind-mounted because a Windows bind mount goes
through a translation layer whose file-locking and fsync semantics MongoDB,
Neo4j and OpenSearch all dislike.

### Microservices — `dockers/gebo.microservices/docker-compose.yml`

One `<svc>-home` / `<svc>-work` / `<svc>-logs` triple per backend, plus
`eureka-logs` and `gateway-logs` (the edge and the registry keep no durable state
of their own, but both write the audit trail).

**Each service gets its own work volume; they are deliberately not shared.**
`DocumentsCacheServiceImpl` writes `DOCUMENTSCACHE` files keyed by a Mongo entry
and deletes them on eviction, so a shared work directory would put every service
in a multi-writer race over the same files. Services exchange documents over the
content-handler seam (REST + RabbitMQ), never through a shared filesystem.

Layering `deploy/wazuh/docker/docker-compose.security-logs.override.yml` replaces
each `<svc>-logs` volume with a host bind at the *same* container path — Compose
merges volume lists by target, so the override still wins:

```bash
docker compose -f docker-compose.yml \
               -f ../../deploy/wazuh/docker/docker-compose.security-logs.override.yml \
               config | grep -A2 'target: /opt/gebo.ai/logs'
```

### All-in-one — `dockers/easyinstall.gebo.ai`

Bundles the databases, so *everything* is inside one container. Its `VOLUME` set
is declared at the **bottom** of the Dockerfile on purpose. The volumes used to
sit at the top, above the `mkdir`/`COPY` steps that populate `/opt/gebo.ai`:
BuildKit keeps those writes, but the classic builder mounts a scratch volume for
each `RUN` and discards them, so the image was one builder away from shipping an
empty layout. Declaring volumes last is portable and costs nothing.

`docker run geboai/easyinstall.gebo.ai` with no `-v` gives every one of those
paths an *anonymous* volume. For anything you intend to keep:

```bash
docker run -d -p 12999:12999 \
  -v gebo-work:/opt/gebo.ai/work   -v gebo-home:/opt/gebo.ai/home \
  -v gebo-logs:/var/log/gebo.ai    -v gebo-mongo:/data/db \
  -v gebo-qdrant:/var/lib/qdrant   -v gebo-neo4j:/var/lib/neo4j \
  -v gebo-opensearch:/opt/opensearch/data \
  geboai/easyinstall.gebo.ai
```

### Kubernetes — `deploy/helm/gebo-microservices`

Infrastructure uses `volumeClaimTemplates`, but the **backends still mount
`emptyDir` for `home` and `work`** (`templates/backends.yaml`), so a pod
reschedule loses the work directory. Converting those to PVCs is outside this
change; it needs `ReadWriteOnce` claims per service (never one shared `RWX`
claim — see the multi-writer note above).

---

## 4. Upgrading

Named volumes and host binds both survive this; nothing else does.

```bash
docker compose pull          # or rebuild: dockers/gebo.ai/create-image.sh
docker compose up -d         # recreates containers, keeps volumes
```

Never `docker compose down -v` on a live deployment — `-v` removes named volumes
too.

## 5. Migrating off anonymous volumes

The microservices compose used to give `/opt/gebo.ai/{home,work}` anonymous
volumes, and `dockers/mongodb` / `dockers/mongo-development-config` gave Mongo no
volume at all. Existing data is **orphaned, not deleted** — do this *before* the
first `up` with the new files.

Find the old anonymous volume for a container:

```bash
docker inspect <container> \
  --format '{{range .Mounts}}{{.Destination}} {{.Name}}{{"\n"}}{{end}}'
```

Copy it into the new named volume (`docker volume ls` shows the real,
project-prefixed names):

```bash
docker run --rm \
  -v <old-anonymous-volume-id>:/from \
  -v gebo-microservices_uploads-work:/to \
  alpine sh -c 'cp -a /from/. /to/'
```

For a Mongo container that had no volume at all, the data is in the container
layer:

```bash
docker cp <old-container>:/data/db ./mongo-db-backup
docker compose up -d && docker compose stop mongo
docker run --rm -v <project>_mongo-data:/data/db -v "$PWD/mongo-db-backup:/backup" \
  alpine sh -c 'cp -a /backup/. /data/db/'
docker compose start mongo
```

## 6. Backing up

Stop the stack first — a hot copy of a running WiredTiger or Lucene data
directory is not a consistent backup.

```bash
docker compose stop
for v in mongo-data uploads-work brain-work heimdall-work; do
  docker run --rm -v gebo-microservices_$v:/data -v "$PWD:/backup" \
    alpine tar czf /backup/$v.tgz -C /data .
done
docker compose start
```

**Mongo and every `*-work` volume must be captured in the same stop window** —
Mongo holds the index of the files inside them.
