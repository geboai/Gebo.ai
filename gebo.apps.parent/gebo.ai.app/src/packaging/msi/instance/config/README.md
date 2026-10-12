# Gebo.ai installation configuration

`application.properties` in this folder is **active** — the application reads it, and
it is already configured for a working installation.

## What you need installed

**MongoDB, and nothing else.**

MongoDB ships a Windows installer and runs as a Windows service on port 27017. A default
installation has access control disabled, which is exactly what `application.properties`
expects, so there is normally nothing to change.

The other components a Gebo.ai deployment can use are switched off:

| Component | State | Why |
|---|---|---|
| Vector store | Embedded (`use: LOCAL`) | Keeps vectors in-process under the instance data folder — no service to install |
| Neo4j / GraphRAG | Off | No graph bean is registered, so nothing contacts `bolt://localhost:7687` |
| OpenSearch / full text | Off | No full-text bean is registered; semantic retrieval is unaffected |

## After installing

Start the service and open <http://localhost:12999/>. The first page asks you to create
the administrative account.

## If MongoDB is not on the defaults

Only this line usually needs editing in `application.properties`:

    ai.gebo.mongodb.connectionString=mongodb://localhost:27017/gebo-ai

Add credentials if you enabled authentication:

    ai.gebo.mongodb.connectionString=mongodb://user:password@localhost:27017/gebo-ai?authSource=admin

## Before exposing the installation beyond this machine

The JWT signing secret in the application defaults is a published value, the same in every
installation. Set `ai.gebo.security.auth.tokenSecret` to your own long random string first.

## Adding server-backed components later

`example-application.properties` (not read by the application) lists the optional
settings — Qdrant, Neo4j, OpenSearch, embedded-store tuning, MongoDB authentication and
replica sets. Copy the lines you want into `application.properties`.

Note that changing vector store does not migrate anything: the new store starts empty and
the knowledge bases have to be ingested again.
