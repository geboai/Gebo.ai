# Gebo.ai — Production Hardening Manual

**Version:** 1.0  
**Audience:** System administrators / DevOps engineers deploying Gebo.ai in production  
**Covers:** Monolithic deployment + Microservices deployment  
**Last updated:** July 2026

---

## Table of Contents

1. [Architecture Overview](#1-architecture-overview)
2. [Part A — Enabling HTTPS (SSL/TLS)](#2-part-a--enabling-https-ssltls)
   - [2.1 Monolithic Deployment](#21-monolithic-deployment)
   - [2.2 Microservices Deployment](#22-microservices-deployment)
   - [2.3 Certificate Generation Reference](#23-certificate-generation-reference)
3. [Part B — Externalising the Encryption Keystore](#3-part-b--externalising-the-encryption-keystore)
   - [3.1 Current State — Bundled Keystore](#31-current-state--bundled-keystore)
   - [3.2 Generating a Custom Keystore](#32-generating-a-custom-keystore)
   - [3.3 Configuring the External Keystore — Monolith](#33-configuring-the-external-keystore--monolith)
   - [3.4 Configuring the External Keystore — Microservices](#34-configuring-the-external-keystore--microservices)
4. [Part C — Secrets Rotation Checklist](#4-part-c--secrets-rotation-checklist)
5. [Part D — Network & Infrastructure Hardening](#5-part-d--network--infrastructure-hardening)
6. [References](#6-references)

---

## 1. Architecture Overview

Gebo.ai runs as a **Spring Boot** application and supports two deployment architectures:

### Monolithic (`gebo.ai.app`)

A single Spring Boot process (JAR / Docker image `geboai/gebo.ai`) that bundles all modules.
It listens on **port 12999** (HTTP) and connects to co-located infrastructure databases.

```
Browser ── HTTP :12999 ──→ gebo.ai.app
                              ├── MongoDB  (127.0.0.1:27017)
                              ├── Qdrant   (127.0.0.1:6333/6334)
                              ├── Neo4j    (127.0.0.1:7474/7687)
                              └── OpenSearch (9200)
```

**Docker Compose:** `dockers/gebo.ai/docker-compose.yml`

### Microservices (20 distinct services)

Each module runs as its own Spring Boot service on a dedicated port (13000–13020).
The **gateway** (port 13000) is the single entry point for browsers. All internal
service-to-service calls resolve through **Eureka** (service registry, port 13017) with
client-side load balancing.

```
Browser ── HTTP :13000 ──→ gateway
                              ├── lb://brain-gebo-ai
                              ├── lb://vectorizator-gebo-ai
                              ├── lb://graphicator-gebo-ai
                              ├── ... (+ 12 other services)
                              ├── lb://heimdall-gebo-ai  (AuthN/AuthZ/Secrets)
                              └── lb://tyr-gebo-ai       (Workflows/Jobs)
```

**Docker Compose:** `dockers/gebo.microservices/docker-compose.yml`

### Shared Infrastructure (both deployments)

| Component | Default Ports | Internal Auth |
|-----------|--------------|---------------|
| MongoDB | 27017 | `mongoroot / mongopwd` |
| Qdrant | 6333, 6334 | API key `ce7c85bc-...` |
| Neo4j | 7474 (HTTP), 7687 (Bolt) | `neo4j / neo4jmaster` |
| OpenSearch | 9200, 9600 | `admin / dothesearch1973-Advanced` |
| RabbitMQ (microservices only) | 5672, 15672 (mgmt) | `guest / guest` |

> ⚠️ **All default credentials are shipped as placeholder values and must be changed
> before production deployment.** See [Part C — Secrets Rotation Checklist](#4-part-c--secrets-rotation-checklist).

---

## 2. Part A — Enabling HTTPS (SSL/TLS)

Gebo.ai is a standard **Spring Boot 3.x** application. It currently listens on
plain HTTP. Enabling HTTPS is a standard Spring Boot `server.ssl.*` configuration.
The same approach applies to every service in the stack because they all share the
same embedded web server (Tomcat or Netty/WebFlux).

> **Spring Boot Reference:**
> [Configure SSL](https://docs.spring.io/spring-boot/reference/web/graceful-shutdown.html#web.ssl)
> in the official Spring Boot documentation.

### 2.1 Monolithic Deployment

The monolith exposes **port 12999** on all host interfaces. You harden it by:

1. generating a keystore,
2. mounting it into the container,
3. adding `server.ssl.*` properties to the config file.

#### Step 1 — Generate a PKCS12 Keystore

Use the Java `keytool` (bundled with any JDK) or `openssl`:

```bash
# Using keytool (recommended for Spring Boot)
keytool -genkeypair \
  -alias gebo-ssl \
  -keyalg RSA \
  -keysize 2048 \
  -storetype PKCS12 \
  -keystore /home/gebo.ai/secrets/gebo-ssl.p12 \
  -validity 3650 \
  -dname "CN=gebo.example.com, OU=IT, O=MyOrg, L=City, S=State, C=IT"
```

You will be prompted for a keystore password and a key password. Keep these secure.

> For production, obtain a certificate signed by a trusted CA (Let's Encrypt, DigiCert,
> etc.) and import it into the keystore instead of using a self-signed one.
> See [Section 2.3](#23-certificate-generation-reference) for Let's Encrypt integration.

#### Step 2 — Mount the Keystore in Docker

Edit `dockers/gebo.ai/docker-compose.yml` and add a volume mount to the `gebo.ai`
service:

```yaml
gebo.ai:
  image: geboai/gebo.ai
  restart: always
  ports:
    - "12999:12999"            # ← change to HTTPS port, e.g. "8443:12999"
  volumes:
    - /home/gebo.ai/work:/opt/gebo.ai/work
    - /home/gebo.ai/home:/opt/gebo.ai/home
    - /home/gebo.ai/logs:/opt/gebo.ai/logs
    - /mnt:/opt/gebo.ai/shares
    - /home/gebo.ai/secrets/gebo-ssl.p12:/opt/gebo.ai/secrets/gebo-ssl.p12:ro  # ← NEW
```

#### Step 3 — Add SSL Properties

Edit `dockers/gebo.ai/config/application.yml`. Replace the existing `server:` block:

```yaml
server:
  port: 12999
  ssl:
    enabled: true
    key-store-type: PKCS12
    key-store: file:/opt/gebo.ai/secrets/gebo-ssl.p12
    key-store-password: ${GEBO_SSL_KEYSTORE_PASSWORD}
    key-alias: gebo-ssl
  compression:
    enabled: true
    mime-types: text/html,text/xml,text/plain,text/css,text/javascript,...
    min-response-size: 1024
  http2:
    enabled: true
```

The password is injected via an environment variable so it never sits in plain text
in the config file. Set it in the `environment:` block of the compose file:

```yaml
gebo.ai:
  environment:
    GEBO_SSL_KEYSTORE_PASSWORD: "your-strong-password-here"
```

#### Step 4 — Redirect HTTP to HTTPS (Optional)

To redirect `http://gebo.example.com:12999` → `https://gebo.example.com:12999`,
add a second embedded connector. In the main application class or a
`@Configuration` class, add:

```java
@Bean
public ServletWebServerFactory servletContainer() {
    TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
        @Override
        protected void postProcessContext(Context context) {
            SecurityConstraint constraint = new SecurityConstraint();
            constraint.setUserConstraint("CONFIDENTIAL");
            SecurityCollection collection = new SecurityCollection();
            collection.addPattern("/*");
            constraint.addCollection(collection);
            context.addConstraint(constraint);
        }
    };
    tomcat.addAdditionalTomcatConnectors(httpToHttpsRedirectConnector());
    return tomcat;
}

private Connector httpToHttpsRedirectConnector() {
    Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
    connector.setScheme("http");
    connector.setPort(12999);
    connector.setSecure(false);
    connector.setRedirectPort(12999); // same port, but https:// scheme
    return connector;
}
```

> **Reference:** Spring Boot docs —
> [How-to: Enable multiple connectors](https://docs.spring.io/spring-boot/how-to/webserver.html#howto.webserver.configure-ssl).

#### Step 5 — Update CORS Configuration

In the same `application.yml`, update the CORS origins from `http://` to `https://`:

```yaml
ai.gebo.security:
  cors:
    allowedOrigins: https://gebo.example.com:12999,http://localhost:4200
```

#### Step 6 — Restart

```bash
docker compose down
docker compose up -d
```

The application is now reachable at `https://gebo.example.com:12999`.

---

### 2.2 Microservices Deployment

The microservices stack has **20 services** listening on ports 13000–13020. The
**gateway** (port 13000) is the only browser-facing service. The recommended approach
is **TLS termination at the gateway only**, with internal service-to-service
communication staying on HTTP inside the Docker network.

#### Option A — TLS at the Gateway Only (Recommended)

Only the gateway needs a keystore. The other services remain on HTTP inside the
internal Docker bridge network (`gebo-net`).

##### Step 1 — Generate the Keystore

Same as monolithic — use `keytool` to create a PKCS12 keystore.

##### Step 2 — Mount the Keystore for the Gateway

Edit `dockers/gebo.microservices/docker-compose.yml`:

```yaml
gateway:
  image: geboai/gateway.gebo.ai:${GEBO_VERSION:-1.0.2.0-SNAPSHOT}
  restart: unless-stopped
  depends_on: [eureka]
  environment:
    EUREKA_CLIENT_SERVICEURL_DEFAULTZONE: http://eureka:13017/eureka/
    GEBO_SSL_KEYSTORE_PASSWORD: "your-strong-password-here"    # ← NEW
  ports:
    - "8443:13000"    # ← external HTTPS on 8443 → internal 13000
  volumes:
    - /home/gebo.ai/secrets/gebo-ssl.p12:/opt/gebo.ai/secrets/gebo-ssl.p12:ro  # ← NEW
  networks: [gebo-net]
```

##### Step 3 — Add SSL Properties to the Gateway

The gateway's own application.yml is at
`gebo.apps.parent/gebo.microservices.apps.parent/gateway.gebo.ai/src/main/resources/application.yml`.

Add a `server.ssl` block:

```yaml
server:
  port: 13000
  ssl:
    enabled: true
    key-store-type: PKCS12
    key-store: file:/opt/gebo.ai/secrets/gebo-ssl.p12
    key-store-password: ${GEBO_SSL_KEYSTORE_PASSWORD}
    key-alias: gebo-ssl
```

> ⚠️ **Do NOT put `server.ssl` settings into the shared config file**
> (`dockers/gebo.microservices/config/application.yml`). That file is mounted at
> `/opt/gebo.ai/config` and loaded as an additional location by **all 20 services**.
> Adding `server.ssl` there would apply TLS to every microservice and break
> internal service-to-service communication, because Eureka registration and
> `lb://` load-balanced calls would switch to HTTPS without corresponding
> certificates on each service.

You must rebuild the gateway image after changing its baked-in `application.yml`:

```bash
cd gebo.apps.parent/gebo.microservices.apps.parent
mvn -pl gateway.gebo.ai -am package -DskipTests
docker compose -f dockers/gebo.microservices/docker-compose.yml up -d --build gateway
```

##### Step 4 — Update CORS

Update `allowedOrigins` in the shared config or gateway config:

```yaml
ai.gebo.security:
  cors:
    allowedOrigins: https://gebo.example.com:8443,http://localhost:4200
```

---

#### Option B — Full mTLS / HTTPS Between All Services (Enterprise)

For high-security environments where every service-to-service call must be encrypted:

1. Generate a **CA certificate** and import it into a **truststore**.
2. Generate a **unique server certificate** (signed by the CA) for **each** of the
   20 services.
3. Add `server.ssl.*` to **each service's own** `application.yml` (not the shared one).
4. Register each service in Eureka with `secure-virtual-host-name`:

```yaml
eureka:
  instance:
    non-secure-port-enabled: false
    secure-port-enabled: true
    secure-port: ${server.port}
    status-page-url-path: ${server.servlet.context-path}/actuator/info
    health-check-url-path: ${server.servlet.context-path}/actuator/health
    home-page-url-path: ${server.servlet.context-path}/
```

5. Configure Spring Cloud Gateway routes with `lb:https://service-id` instead of `lb:http://service-id`.

This is a significant operational burden and is only recommended when a compliance
framework (PCI-DSS, FedRAMP, etc.) mandates it.

> **Reference:** Spring Cloud documentation —
> [TLS/SSL in Spring Cloud](https://cloud.spring.io/spring-cloud-static/spring-cloud.html#_tls_and_ssl).

---

### 2.3 Certificate Generation Reference

| Method | Command | Notes |
|--------|---------|-------|
| **Self-signed (keytool)** | `keytool -genkeypair -alias gebo-ssl -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore gebo-ssl.p12 -validity 3650` | Interactive; suitable for internal/staging use only |
| **Self-signed (openssl)** | `openssl req -x509 -newkey rsa:4096 -keyout key.pem -out cert.pem -days 3650 -nodes && openssl pkcs12 -export -in cert.pem -inkey key.pem -out gebo-ssl.p12 -name gebo-ssl` | Non-interactive; suitable for scripting |
| **Let's Encrypt (Certbot)** | `certbot certonly --standalone -d gebo.example.com && openssl pkcs12 -export -in /etc/letsencrypt/live/gebo.example.com/fullchain.pem -inkey /etc/letsencrypt/live/gebo.example.com/privkey.pem -out gebo-ssl.p12 -name gebo-ssl` | Requires a public domain name; cert auto-renews |
| **Internal CA** | Follow your organisation's PKI procedure; import the signed cert with `keytool -importcert` | Recommended for enterprise |

For Let's Encrypt auto-renewal, set up a cron job that regenerates the PKCS12 file and
copies it to `/home/gebo.ai/secrets/gebo-ssl.p12`, then signals the Docker container
to load the updated keystore.

> **Reference:** Spring Boot docs —
> [How-to: Configure SSL](https://docs.spring.io/spring-boot/how-to/webserver.html#howto.webserver.configure-ssl).

---

## 3. Part B — Externalising the Encryption Keystore

### 3.1 Current State — Bundled Keystore

Gebo.ai ships with a **bundled PKCS12 keystore** inside the JAR at
`/keystore/bundled.gebo.ai.ks`. This keystore contains an **AES** symmetric key used to
encrypt/decrypt all sensitive secrets at rest in MongoDB — JWT signing secrets,
OAuth2 client secrets, API keys for LLM providers, database passwords, and more.

**Relevant source files:**

| File | Purpose |
|------|---------|
| `gebo.architecture.crypting/.../GeboCryptingServiceImpl.java:87–91` | Loads the bundled keystore; hardcoded password `TheFunkyHeadHunter1969Ad` |
| `gebo.architecture.crypting/.../KeyStoreConfig.java:21–39` | External keystore configuration model |
| `gebo.architecture.crypting/.../GeboCryptingConfig.java` | Binds to `ai.gebo.crypting.*` properties |

The initialisation logic (from `GeboCryptingServiceImpl.java:230–292`) is:

```
IF ai.gebo.crypting.keystoreConfig.keystore-path IS SET
    → Load the external keystore from that path
    → Read the key using configured alias and passwords
    → usedBundled = false
ELSE
    → Load /keystore/bundled.gebo.ai.ks from the classpath
    → Use hardcoded password and key alias
    → usedBundled = true
```

> ⚠️ **The bundled keystore password is hardcoded in Java source code** and the
> keystore file is accessible to anyone who can extract the JAR.
> **For any production deployment, you must configure an external keystore.**

### 3.2 Generating a Custom Keystore

Use the Java `keytool` to generate a PKCS12 keystore with an AES key:

```bash
keytool -genseckey \
  -alias gebo-prod-key \
  -keyalg AES \
  -keysize 256 \
  -storetype PKCS12 \
  -keystore /home/gebo.ai/secrets/gebo-crypto.p12 \
  -storepass "your-keystore-password" \
  -keypass "your-key-password"
```

> **Note on AES key length:** AES-256 requires the **Java Cryptography Extension (JCE)
> Unlimited Strength Jurisdiction Policy** to be installed. For OpenJDK 11+, this is
> enabled by default. For older JDKs you may need to install the policy files.

Verify the keystore contents:

```bash
keytool -list -v -keystore /home/gebo.ai/secrets/gebo-crypto.p12 -storepass "your-keystore-password"
```

Expected output shows one `SecretKeyEntry` with alias `gebo-prod-key` and algorithm `AES`.

**Generating with openssl (alternative):**

```bash
# Generate a random 256-bit AES key
openssl rand -hex 32 > /tmp/aes-key.txt

# Create a PKCS12 keystore programmatically via a small Java tool:
#   keytool cannot directly import a raw AES key from openssl.
#   Use the keytool method above.
```

### 3.3 Configuring the External Keystore — Monolith

The external keystore is configured via the `ai.gebo.crypting.keystoreConfig.*`
properties. These properties are **not** set in the shipped `application.yml` and
must be **added** explicitly.

#### Step 1 — Mount the Keystore

Edit `dockers/gebo.ai/docker-compose.yml`:

```yaml
gebo.ai:
  volumes:
    - /home/gebo.ai/secrets/gebo-crypto.p12:/opt/gebo.ai/secrets/gebo-crypto.p12:ro  # ← NEW
```

#### Step 2 — Add the Configuration

Edit `dockers/gebo.ai/config/application.yml` and add the following block:

```yaml
ai:
  gebo:
    crypting:
      keystoreConfig:
        key-store-type: PKCS12
        key-store-path: /opt/gebo.ai/secrets/gebo-crypto.p12
        key-algorithm: AES
        key-store-password: ${GEBO_CRYPTO_KEYSTORE_PASSWORD}
        key-password: ${GEBO_CRYPTO_KEY_PASSWORD}
        key-name: gebo-prod-key
```

Pass the passwords via environment variables:

```yaml
gebo.ai:
  environment:
    GEBO_CRYPTO_KEYSTORE_PASSWORD: "your-keystore-password"
    GEBO_CRYPTO_KEY_PASSWORD: "your-key-password"
```

#### Step 3 — Restart and Verify

```bash
docker compose down && docker compose up -d
docker logs gebo.ai 2>&1 | grep -i "custom keystore"
```

Successful output:

```
Reading custom keystore security data structures
Security custom keystore data structures read!!
```

If the keystore cannot be loaded, the application will **fail to start** with a
`RuntimeException` — this is a deliberate fail-closed design.

> **Important:** Once an external keystore is configured, any secrets previously
> encrypted with the bundled keystore will **no longer be decryptable**. You must
> re-encrypt all secrets with the new key. In practice this means:
> - Re-entering LLM API keys in the admin UI
> - Re-entering OAuth2 client secrets
> - Re-entering any other stored credentials
>
> Plan this as a fresh-start migration or schedule a maintenance window.

---

### 3.4 Configuring the External Keystore — Microservices

In the microservices architecture, **every service that encrypts/decrypts secrets**
carries the `gebo.architecture.crypting` module and therefore needs access to the
same keystore. This includes:

- `brain` (LLM API keys, prompt secrets)
- `vectorizator` (embedding provider keys)
- `gateway` (JWT secret verification)
- `heimdall` (the central secrets service — itself uses the keystore for storage)
- All content-handler services if they store credentials (SharePoint, Confluence, Jira, etc.)

#### Step 1 — Generate and Place the Keystore

```bash
keytool -genseckey -alias gebo-prod-key -keyalg AES -keysize 256 \
  -storetype PKCS12 -keystore /home/gebo.ai/secrets/gebo-crypto.p12
```

#### Step 2 — Mount the Keystore into All Microservices

Add a volume mount to the shared `x-microservice:` anchor in
`dockers/gebo.microservices/docker-compose.yml`:

```yaml
x-microservice: &microservice
  restart: unless-stopped
  depends_on: [eureka, rabbit, mongo]
  environment:
    <<: *microservice-env
    GEBO_CRYPTO_KEYSTORE_PASSWORD: "your-keystore-password"   # ← NEW (shared env)
    GEBO_CRYPTO_KEY_PASSWORD: "your-key-password"             # ← NEW (shared env)
  volumes:
    - ./config:/opt/gebo.ai/config:ro
    - /home/gebo.ai/secrets/gebo-crypto.p12:/opt/gebo.ai/secrets/gebo-crypto.p12:ro  # ← NEW
    - /opt/gebo.ai/home
    - /opt/gebo.ai/work
  networks: [gebo-net]
```

#### Step 3 — Add Keystore Config to the Shared Configuration

Edit `dockers/gebo.microservices/config/application.yml` and add:

```yaml
ai:
  gebo:
    crypting:
      keystoreConfig:
        key-store-type: PKCS12
        key-store-path: /opt/gebo.ai/secrets/gebo-crypto.p12
        key-algorithm: AES
        key-store-password: ${GEBO_CRYPTO_KEYSTORE_PASSWORD}
        key-password: ${GEBO_CRYPTO_KEY_PASSWORD}
        key-name: gebo-prod-key
```

> ⚠️ **This IS safe to put in the shared config** — unlike `server.ssl`, the
> keystore configuration is needed by every service that carries the crypting
> module (which is all services), and it is the same keystore everywhere. The
> `gcloudd` comment in the docker-compose about heimdall's secrets-cluster
> properties should NOT go here refers to heimdall-specific cluster endpoint
> settings, not to the shared crypto keystore.

#### Step 4 — Restart All Services

```bash
docker compose -f dockers/gebo.microservices/docker-compose.yml down
docker compose -f dockers/gebo.microservices/docker-compose.yml up -d
```

#### Key Rotation for Microservices

The same caveat applies as for the monolith: **changing the keystore invalidates
all previously encrypted secrets**. In a microservices deployment this is
especially critical because:

- **heimdall** stores encrypted secrets in its own MongoDB database (`heimdall-gebo`).
  These secrets are served to other services as stored ciphertext; each caller
  decrypts locally. All callers must have the **same** keystore as heimdall.

- If you rotate the keystore, you must either:
  1. **Re-seed all secrets** through heimdall's admin API with the new key, or
  2. Accept a clean start with fresh secrets.

> The `hei''mdall` service never decrypts on a caller's behalf — it hands back the
> **stored ciphertext** and the caller decrypts locally. No secret is ever in the
> clear on the network. This architecture is documented at
> `dockers/gebo.microservices/docker-compose.yml:173–176`.

---

## 4. Part C — Secrets Rotation Checklist

The following secrets are shipped with placeholder values and **must be rotated**
before production:

| Secret | Configuration Property | How to Rotate |
|--------|----------------------|---------------|
| **JWT signing secret** | `ai.gebo.security.auth.tokenSecret` | Generate with `openssl rand -hex 64`; update in `application.yml`. All existing tokens are immediately invalidated — users must re-authenticate. |
| **MongoDB root password** | `MONGO_INITDB_ROOT_PASSWORD` (Docker env) + `ai.gebo.mongodb.connectionString` | Change docker-compose env, update connection string. |
| **Qdrant API key** | `QDRANT__SERVICE__API_KEY` (Docker env) + `ai.gebo.vectorstore.qdrant.apiKey` | Generate new UUID; update both places. |
| **Neo4j password** | `NEO4J_AUTH` (Docker env) + `spring.neo4j.authentication.password` | Change docker-compose env, update app config. |
| **OpenSearch admin password** | `OPENSEARCH_INITIAL_ADMIN_PASSWORD` (Docker env) + `ai.gebo.opensearch.password` | Change docker-compose env, update app config. |
| **RabbitMQ password** | `spring.rabbitmq.password` + `ai.gebo.messaging.rabbitmq.connection.password` (microservices) | Change in shared config and RabbitMQ config. |
| **OAuth2 client secrets** | `ai.gebo.security.oauth2configs[].client.secret` | Rotate in the OAuth2 provider's admin console, then update in Gebo. |
| **Mail server credentials** | `ai.gebo.userflows.mail-user-name`, `mail-password` | Update SMTP server credentials. |
| **Encryption keystore password** | `ai.gebo.crypting.keystoreConfig.key-store-password` | See [Section 3](#3-part-b--externalising-the-encryption-keystore). |
| **TLS keystore password** | `server.ssl.key-store-password` | See [Section 2](#2-part-a--enabling-https-ssltls). |

### JWT Secret Rotation Procedure

```bash
# Generate a new 512-bit hex secret
NEW_SECRET=$(openssl rand -hex 64)
echo "New JWT secret: $NEW_SECRET"

# Update both monolith and microservices config files
# Replace the value of ai.gebo.security.auth.tokenSecret with $NEW_SECRET
```

### Environment Variable Injection (Recommended)

Instead of hardcoding secrets in YAML files, use environment variable placeholders:

```yaml
ai.gebo.security.auth:
  tokenSecret: ${GEBO_JWT_SECRET}
```

Set the values in a `.env` file (excluded from version control) or via your
container orchestration's secret management (Docker Secrets, Kubernetes Secrets,
HashiCorp Vault, etc.).

> **Reference:** Spring Boot documentation —
> [Externalized Configuration](https://docs.spring.io/spring-boot/reference/features/external-config.html).

---

## 5. Part D — Network & Infrastructure Hardening

### 5.1 Docker Network Binding

By default, the monolith compose file exposes infrastructure databases on
`127.0.0.1` (loopback only), which is correct. Verify:

```yaml
mongo:
  ports:
    - "127.0.0.1:27017:27017"   # ✅ loopback only
qdrant:
  ports:
    - "127.0.0.1:6333:6333"     # ✅ loopback only
```

In the microservices compose, infrastructure databases have **no ports published**
at all — they are reachable only within the internal Docker network `gebo-net`.
This is the most secure posture.

### 5.2 Microservices — Cluster Participant Restriction

The **heimdall** security service admits callers only from addresses currently
registered in Eureka. This is configured in heimdall's `application.yml`:

```yaml
ai.gebo.cluster.participants:
  extra-service-ids: []
  additional-allowed-addresses: []
  cache-ttl: 30s
```

- **`extra-service-ids`** — Additional Eureka service IDs to admit. Keep empty.
- **`additional-allowed-addresses`** — Static IP bypasses. **Keep empty in production.**
- **`cache-ttl`** — How long a removed participant stays in the allow-list cache.

Also verify the **gateway** is NOT in the allow-list — see the comment at
`heimdall.gebo.ai/.../application.yml:53–63`. The gateway route only exposes
heimdall's admin API (`/heimdall/api/admin/**`), never its cluster secrets surface
(`/heimdall/api/cluster/**`).

### 5.3 OpenSearch — Internal SSL

OpenSearch already runs with internal SSL enabled:

```yaml
- plugins.security.ssl.http.enabled=true
- plugins.security.ssl.transport.enabled=true
```

The `OPENSEARCH_SSL_VERIFICATIONMODE=none` setting in the OpenSearch Composer stack
is a **developer convenience** — consider enabling certificate verification in
production.

### 5.4 Qdrant TLS

Qdrant currently has `tls: false`. To enable it in production:

1. Generate a TLS certificate for Qdrant.
2. Mount it and configure Qdrant's TLS settings.
3. Update `ai.gebo.vectorstore.qdrant.tls: true` in the application config.

### 5.5 Firewall Rules

| Service | Port | Public Access | Recommendation |
|---------|------|---------------|----------------|
| Monolith (HTTP) | 12999 | Yes (if hardened with HTTPS) | Allow only from trusted networks |
| Gateway (HTTP) | 13000 | Yes (if hardened with HTTPS) | Allow only from trusted networks |
| Eureka | 13017 | No | Block at firewall; internal only |
| All other microservices | 13001–13020 | No | Block at firewall; internal only |
| MongoDB | 27017 | No | Block at firewall |
| Qdrant | 6333, 6334 | No | Block at firewall |
| Neo4j | 7474, 7687 | No | Block at firewall |
| OpenSearch | 9200 | No | Block at firewall |
| RabbitMQ | 5672, 15672 | No | Block at firewall |
| Grafana | 3000 | No | Expose through authenticated reverse proxy |
| Prometheus | 9090 | No | Block at firewall |

---

## 6. References

| Reference | URL |
|-----------|-----|
| Spring Boot — Configure SSL | https://docs.spring.io/spring-boot/how-to/webserver.html#howto.webserver.configure-ssl |
| Spring Boot — Externalized Configuration | https://docs.spring.io/spring-boot/reference/features/external-config.html |
| Spring Boot — Graceful Shutdown / SSL | https://docs.spring.io/spring-boot/reference/web/graceful-shutdown.html |
| Spring Cloud — TLS and SSL | https://cloud.spring.io/spring-cloud-static/spring-cloud.html#_tls_and_ssl |
| Spring Boot — Enable Multiple Connectors | https://docs.spring.io/spring-boot/how-to/webserver.html#howto.webserver.configure-ssl |
| Java keytool Reference | https://docs.oracle.com/en/java/javase/17/docs/specs/man/keytool.html |
| Let's Encrypt / Certbot | https://certbot.eff.org/ |
| Bouncy Castle (used by Gebo.ai crypting module) | https://www.bouncycastle.org/ |
| Gebo.ai — APPLICATION-YML-ADMIN-MANUAL.md | `docs/APPLICATION-YML-ADMIN-MANUAL.md` (included in this repository) |
| Gebo.ai — Crypto service implementation | `gebo.architecture.parent/gebo.architecture.crypting/src/main/java/ai/gebo/crypting/services/impl/GeboCryptingServiceImpl.java` |
| Gebo.ai — KeyStore configuration model | `gebo.architecture.parent/gebo.architecture.crypting/src/main/java/ai/gebo/crypting/config/KeyStoreConfig.java` |

---

> **Document maintained by:** Gebo.ai Engineering Team
> **License:** MPL-2.0 with Data Protection Clauses
> **Copyright:** 2025+ Gebo.ai
