/**
 * Bridges this plugin to the Keycloak SSO session that already gates access
 * to this document-server sandbox (see ../../oauth2-proxy/oauth2-proxy.cfg
 * and ../../README.md).
 *
 * The plugin's iframe is served same-origin with oauth2-proxy (both under
 * http://localhost:4180), and that proxy is configured with
 * `set_xauthrequest = true` + `pass_access_token = true`, so a plain
 * same-origin, cookie-authenticated GET to /oauth2/auth echoes the current
 * user's Keycloak access token back in the X-Auth-Request-Access-Token
 * response header - no separate login step or manual token entry needed
 * inside the plugin itself.
 *
 * ASSUMPTION for real deployments: this only lets the plugin call the real
 * Gebo.ai installation's API as the logged-in user if that installation
 * trusts the SAME Keycloak realm/issuer this sandbox's Keycloak represents -
 * i.e. the sandbox's realm needs to be (or be federated with) whatever
 * Keycloak/OIDC issuer Gebo.ai's own `ai.gebo.security.oauth2configs` is
 * configured to accept (see the `keycloakClient`/`MainRealm` test config in
 * gebo.apps.parent/gebo.ai.app's application.yml). This sandbox ships its
 * own standalone realm (onlyoffice-dev) for local plugin development; point
 * it at your real IdP for anything beyond that.
 */
window.GeboAuthBridge = (function () {
  async function getAccessToken() {
    const res = await fetch("/oauth2/auth", { credentials: "include" });
    if (!res.ok) {
      throw new Error(
        "Not authenticated with the SSO gate (HTTP " + res.status + "). " +
        "Log in at the sandbox root (e.g. http://localhost:4180/welcome/) first."
      );
    }
    const token = res.headers.get("X-Auth-Request-Access-Token");
    if (!token) {
      throw new Error(
        "The SSO gate didn't expose an access token. Check that " +
        "set_xauthrequest and pass_access_token are both true in oauth2-proxy.cfg."
      );
    }
    return token;
  }

  return { getAccessToken: getAccessToken };
})();
