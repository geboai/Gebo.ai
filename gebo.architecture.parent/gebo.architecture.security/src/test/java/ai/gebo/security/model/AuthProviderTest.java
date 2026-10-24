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

import ai.gebo.security.services.impl.GMicrosoftAccountsIdTokenValidator;

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
     * Both kinds of Microsoft configuration can be made: the Microsoft accounts one,
     * for personal accounts and the organizations it admits, and the one for the
     * accounts of a single organization, by its tenant id.
     */
    @Test
    void bothKindsOfMicrosoftAccountAreOffered() {
        assertThat(offered()).contains(AuthProvider.microsoft, AuthProvider.microsoft_multitenant);
    }

    /**
     * The administration form asks for the attributes a provider declares, and the
     * login of the Microsoft accounts configuration reads the tenants it admits from
     * one of them: the two names must be the same, or the list typed in the form would
     * never be read.
     */
    @Test
    void theMicrosoftAccountsProviderAsksForTheTenantsItAdmits() {
        assertThat(AuthProvider.microsoft.getAuthProviderDto().getCustomAttributes().stream()
                .map(attribute -> attribute.getAttributeName()))
                .containsExactly(GMicrosoftAccountsIdTokenValidator.ALLOWED_TENANTS);
    }

    /**
     * The constant for personal accounts was first written against Microsoft's shared
     * endpoints and is kept under that name: installations that saved a configuration
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
