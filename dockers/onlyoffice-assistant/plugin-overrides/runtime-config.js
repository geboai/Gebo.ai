/*
 * Sandbox runtime configuration for the Angular "Gebo AI Assistant" plugin
 * running inside the ONLYOFFICE document server behind Keycloak+oauth2-proxy.
 *
 * It sets the backend to resolve ("gebo-ai" -> assets/backends.json), the API
 * origin the auth headers may be attached to, and an authProvider that reads the
 * logged-in user's Keycloak access token from oauth2-proxy same-origin
 * (GET /oauth2/auth, credentials included; oauth2-proxy echoes the token back in
 * the X-Auth-Request-Access-Token response header - it is configured with
 * pass_access_token=true and set_xauthrequest=true). Never store a token here.
 */
(function () {
  function readJwtExp(token) {
    try {
      var payload = JSON.parse(atob(token.split('.')[1].replace(/-/g, '+').replace(/_/g, '/')));
      return typeof payload.exp === 'number' ? payload.exp * 1000 : undefined;
    } catch (e) {
      return undefined;
    }
  }

  async function keycloakAuthProvider() {
    // Same-origin against oauth2-proxy (the plugin is served through :4180).
    const res = await fetch('/oauth2/auth', { credentials: 'include' });
    if (!res.ok) {
      throw new Error('Not authenticated with the SSO gate (/oauth2/auth ' + res.status + ')');
    }
    const token = res.headers.get('X-Auth-Request-Access-Token');
    if (!token) {
      throw new Error('oauth2-proxy did not return an access token');
    }
    return {
      accessToken: token,
      tokenType: 'Bearer',
      expiresAt: readJwtExp(token),
      provider: 'keycloak',
    };
  }

  window.OfficePluginConfig = Object.assign(
    {
      backendName: 'gebo-ai',
      platform: 'onlyoffice',
      // The plugin's office interceptor only attaches the raw host token to
      // requests whose origin is listed here; keep it aligned with backends.json.
      apiOrigins: ['http://localhost:12999'],
      authProvider: keycloakAuthProvider,
    },
    window.OfficePluginConfig || {},
  );
})();
