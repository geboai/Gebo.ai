# Euro-Office Workspace sandbox (Nextcloud + Euro-Office DS + Gebo.ai + Keycloak)

Twin of `../onlyoffice-workspace`, using the **Euro-Office Document Server fork**
(`ghcr.io/euro-office/documentserver`) instead of upstream ONLYOFFICE. Same goals:
test the **Gebo AI Assistant** plugin in a real Nextcloud editing session, and
the Gebo.ai **WebDAV data-source pipeline** against Nextcloud's WebDAV, all under
one Keycloak realm with unified SSO.

Read `../onlyoffice-workspace/README.md` for the full architecture and the
same-site-over-`localtest.me` SSO trick — everything applies here, only the
Document Server image, its web root, the JWT secret, the realm and the ports
differ.

## Pieces

| Service | Browser URL | Role |
|---|---|---|
| Nextcloud | http://nextcloud.localtest.me:8091/ | Workspace: files, WebDAV, OIDC login, ONLYOFFICE connector |
| Euro-Office DS (+ oauth2-proxy) | http://office.localtest.me:4380/ | Editor hosting the plugin, SSO-gated |
| Keycloak | http://keycloak.localtest.me:8083/ | IdP, realm `euro-office-workspace-dev` (admin/admin) |
| Gebo.ai | http://gebo.localtest.me:14999/ | Backend, OAuth2 resource server, WebDAV ingestion target |
| mongo / qdrant / neo4j / opensearch | — | Platform infrastructure |

## Differences from the onlyoffice-workspace twin

- Document Server image `ghcr.io/euro-office/documentserver:latest`, web root
  `/var/www/euro-office/documentserver` (the plugin mounts land there, not under
  `.../onlyoffice/...`).
- JWT secret `232dfab767ecb811e9009b4256cf80c2b10526c65e7e7d82` (matches the
  euro-office image and the Nextcloud ONLYOFFICE connector `jwt_secret`).
- Keycloak realm `euro-office-workspace-dev`, editor client
  `euro-office-plugin-dev`.
- Port block: Nextcloud 8091, editor proxy 4380, Keycloak 8083, Gebo 14999,
  mongo 27019, qdrant 6337/6338, neo4j 7476/7689.

## Prerequisites, bring-up, accounts and the two tests

Identical to the onlyoffice-workspace twin — see
`../onlyoffice-workspace/README.md`. Substitute the URLs/ports from the table
above. In particular:

- Build the plugin first: `cd ../../../Gebo.ai-offices-plugin && npm ci && npm run build`.
- `docker compose up -d`, then watch `docker compose logs -f nextcloud | grep gebo-provision`.
- WebDAV data source to register in Gebo (internal URL the backend resolves):
  `http://nextcloud/remote.php/dav/files/webdav/`, user `webdav`,
  password `webdav-sandbox-2026`.

This stack is fully independent (its own network
`gebo-euro-office-workspace-dev`, its own volumes), so it can run alongside the
onlyoffice-workspace stack — though both together is very heavy.
