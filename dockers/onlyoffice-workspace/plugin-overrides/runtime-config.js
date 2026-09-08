/*
 * Sandbox runtime configuration for the Angular "Gebo AI Assistant" plugin in
 * the ONLYOFFICE workspace stack. The editor is embedded by Nextcloud, but the
 * plugin still runs on the office origin (office.localtest.me:4280, served
 * through oauth2-proxy), so the same same-origin token handoff as the
 * onlyoffice-assistant stack works: GET /oauth2/auth (credentials included),
 * token read back from the X-Auth-Request-Access-Token header. Never store a
 * token here.
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
    // Same-origin against oauth2-proxy (the plugin is served through :4280).
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
      apiOrigins: ['http://gebo.localtest.me:13999'],
      authProvider: keycloakAuthProvider,
    },
    window.OfficePluginConfig || {},
  );
})();
