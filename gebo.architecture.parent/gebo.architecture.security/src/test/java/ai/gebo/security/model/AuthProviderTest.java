/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.security.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Which providers an administrator is offered when configuring single sign-on.
 */
class AuthProviderTest {

    private List<AuthProvider> offered() {
        return AuthProvider.getOauth2Providers().stream().map(dto -> dto.getProvider()).toList();
    }

    @Test
    void onlyOauth2ProvidersAreOffered() {
        assertThat(offered()).doesNotContain(AuthProvider.local, AuthProvider.ldap);
        assertThat(offered()).contains(AuthProvider.google, AuthProvider.keycloak, AuthProvider.oauth2_generic);
    }

    /**
     * Microsoft's shared endpoints sign each id token under the issuer of the tenant
     * the person belongs to, never under a shared one, and the login compares that
     * issuer exactly. A configuration made against them could therefore never log
     * anybody in, so it is not offered; the organization entry, which takes the
     * tenant id, is the one that works.
     */
    @Test
    void theSharedMicrosoftEndpointsAreNotOffered() {
        assertThat(offered()).doesNotContain(AuthProvider.microsoft);
        assertThat(offered()).contains(AuthProvider.microsoft_multitenant);
    }

    /**
     * The constant itself has to stay: installations that saved a configuration
     * against it still read it back, and an enum that no longer has the name simply
     * fails to load them.
     */
    @Test
    void theConstantStaysSoOldConfigurationsStillLoad() {
        assertThat(AuthProvider.valueOf("microsoft")).isEqualTo(AuthProvider.microsoft);
        assertThat(AuthProvider.getAllProviders().stream().map(dto -> dto.getProvider()))
                .contains(AuthProvider.microsoft);
    }
}
