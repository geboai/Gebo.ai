/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.security.services.impl;

import static ai.gebo.security.services.impl.GMicrosoftAccountsIdTokenValidatorTest.CONTOSO;
import static ai.gebo.security.services.impl.GMicrosoftAccountsIdTokenValidatorTest.PERSONAL;
import static ai.gebo.security.services.impl.GMicrosoftAccountsIdTokenValidatorTest.idToken;
import static ai.gebo.security.services.impl.GMicrosoftAccountsIdTokenValidatorTest.registration;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedHashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

import ai.gebo.secrets.model.GeboOauth2SecretContent;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.oauth2.GeboOauth2Exception;
import ai.gebo.security.model.oauth2.Oauth2ClientRegistration;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import ai.gebo.security.services.IGOauth2ConfigurationService;

/**
 * Which check the id token of a login goes through, by the configuration it logs
 * in with.
 */
class GOidcIdTokenValidatorFactoryTest {

    private IGOauth2ConfigurationService configurations;
    private GOidcIdTokenValidatorFactory factory;

    @BeforeEach
    void setUp() {
        configurations = mock(IGOauth2ConfigurationService.class);
        factory = new GOidcIdTokenValidatorFactory(configurations);
    }

    private static Oauth2ClientRegistration configuration(AuthProvider provider, String allowedTenants) {
        Oauth2RuntimeConfiguration runtime = new Oauth2RuntimeConfiguration();
        runtime.setRegistrationId("microsoft-test");
        runtime.setProvider(provider);
        GeboOauth2SecretContent secret = new GeboOauth2SecretContent();
        LinkedHashMap<String, String> attributes = new LinkedHashMap<>();
        if (allowedTenants != null) {
            attributes.put(GMicrosoftAccountsIdTokenValidator.ALLOWED_TENANTS, allowedTenants);
        }
        secret.setCustomAttributes(attributes);
        return new Oauth2ClientRegistration(secret, runtime);
    }

    @Test
    void theMicrosoftAccountsConfigurationAdmitsTheTenantsItLists() throws Exception {
        when(configurations.findOauth2ClientRegistrationByRegistrationId("microsoft-test"))
                .thenReturn(configuration(AuthProvider.microsoft, "consumers, " + CONTOSO));

        assertThat(factory.apply(registration()).validate(idToken(CONTOSO).build()).getErrors()).isEmpty();
        assertThat(factory.apply(registration()).validate(idToken(PERSONAL).build()).getErrors()).isEmpty();
    }

    /**
     * Any other configuration is checked as Spring checks it: an issuer that is not
     * the registration's is refused, whatever tenant the token names.
     */
    @Test
    void anyOtherConfigurationKeepsSpringsCheck() throws Exception {
        when(configurations.findOauth2ClientRegistrationByRegistrationId("microsoft-test"))
                .thenReturn(configuration(AuthProvider.microsoft_multitenant, "consumers, " + CONTOSO));

        assertThat(factory.apply(registration()).validate(idToken(PERSONAL).build()).getErrors())
                .as("the registration's own issuer").isEmpty();
        assertThat(factory.apply(registration()).validate(idToken(CONTOSO).build()).hasErrors())
                .as("another issuer").isTrue();
    }

    @Test
    void anUnknownConfigurationKeepsSpringsCheck() throws Exception {
        when(configurations.findOauth2ClientRegistrationByRegistrationId("microsoft-test")).thenReturn(null);

        assertThat(factory.apply(registration()).validate(idToken(CONTOSO).build()).hasErrors()).isTrue();
    }

    /** Spring asks for a validator on each login, so an edit applies to the next. */
    @Test
    void anEditOfTheTenantsAppliesToTheNextLogin() throws Exception {
        when(configurations.findOauth2ClientRegistrationByRegistrationId("microsoft-test"))
                .thenReturn(configuration(AuthProvider.microsoft, null))
                .thenReturn(configuration(AuthProvider.microsoft, "consumers, " + CONTOSO));

        assertThat(factory.apply(registration()).validate(idToken(CONTOSO).build()).hasErrors()).isTrue();
        assertThat(factory.apply(registration()).validate(idToken(CONTOSO).build()).getErrors()).isEmpty();
    }

    /**
     * When the configuration cannot be read the login fails, rather than falling
     * back to a check that does not know which tenants are admitted.
     */
    @Test
    void aConfigurationThatCannotBeReadFailsTheLogin() throws Exception {
        when(configurations.findOauth2ClientRegistrationByRegistrationId("microsoft-test"))
                .thenThrow(new GeboOauth2Exception("the secret storage is down"));
        ClientRegistration registration = registration();

        assertThatThrownBy(() -> factory.apply(registration)).isInstanceOf(OAuth2AuthenticationException.class);
    }
}
