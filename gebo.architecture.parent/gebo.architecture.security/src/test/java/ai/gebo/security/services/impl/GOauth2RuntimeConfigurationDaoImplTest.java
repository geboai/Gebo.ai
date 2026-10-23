/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.security.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedHashMap;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.secrets.model.GeboOauth2SecretContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.security.config.GeboSecurityConfig;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.oauth2.Oauth2ConfigurationType;
import ai.gebo.security.model.oauth2.Oauth2ProviderConfig;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import ai.gebo.security.repository.Oauth2RuntimeConfigurationRepository;
import ai.gebo.security.services.IGOauth2ProvidersLibraryDao;

/**
 * A stored oauth2 configuration is deliberately partial: a provider that has a
 * library entry keeps no endpoints of its own, those endpoints are templates shared
 * by every configuration of that provider, and the values that fill them live with
 * the client in the secret store.
 *
 * <p>
 * Everything that reads configurations from this dao used to get that partial form
 * and work with it as if it were complete, which is why a bearer token of an Entra
 * organization, a Cognito pool or an Identity Center instance could never be
 * accepted: the issuer it was compared against still read {@code {tenantId}}, and
 * introspection had no client to ask with.
 * </p>
 */
class GOauth2RuntimeConfigurationDaoImplTest {

    private static final String REGISTRATION = "microsoft_multitenant-1";
    private static final String SECRET_ID = "the-secret-of-this-configuration";
    private static final String TENANT = "11111111-2222-3333-4444-555555555555";
    private static final String TEMPLATE = "https://login.microsoftonline.com/{tenantId}/v2.0";

    private Oauth2RuntimeConfigurationRepository repository;
    private IGOauth2ProvidersLibraryDao library;
    private IGeboSecretsAccessService secrets;
    private GOauth2RuntimeConfigurationDaoImpl dao;

    @BeforeEach
    void setUp() {
        repository = mock(Oauth2RuntimeConfigurationRepository.class);
        library = mock(IGOauth2ProvidersLibraryDao.class);
        secrets = mock(IGeboSecretsAccessService.class);
        dao = new GOauth2RuntimeConfigurationDaoImpl(new GeboSecurityConfig(), repository, library, secrets);

        Oauth2ProviderConfig template = new Oauth2ProviderConfig();
        template.setProvider(AuthProvider.microsoft_multitenant);
        template.setIssuerUri(TEMPLATE);
        template.setAuthorizationUri("https://login.microsoftonline.com/{tenantId}/oauth2/v2.0/authorize");
        template.setIntrospectionUri("https://login.microsoftonline.com/{tenantId}/oauth2/v2.0/introspect");
        when(library.findByCode(AuthProvider.microsoft_multitenant.name())).thenReturn(template);
    }

    /** What the store holds: no endpoints of its own, and only a pointer to its client. */
    private static Oauth2RuntimeConfiguration stored() {
        Oauth2RuntimeConfiguration configuration = new Oauth2RuntimeConfiguration();
        configuration.setRegistrationId(REGISTRATION);
        configuration.setProvider(AuthProvider.microsoft_multitenant);
        configuration.setConfigurationType(Oauth2ConfigurationType.AUTHENTICATION);
        configuration.setClientSecretId(SECRET_ID);
        return configuration;
    }

    private static GeboOauth2SecretContent storedClient() {
        GeboOauth2SecretContent client = new GeboOauth2SecretContent();
        client.setClientId("gebo-ai");
        client.setSecret("a-client-secret");
        LinkedHashMap<String, String> attributes = new LinkedHashMap<>();
        attributes.put("tenantId", TENANT);
        client.setCustomAttributes(attributes);
        return client;
    }

    @Test
    void aConfigurationComesBackWithItsEndpointsResolvedAndItsClient() throws Exception {
        Oauth2RuntimeConfiguration storedConfiguration = stored();
        when(repository.findById(REGISTRATION)).thenReturn(Optional.of(storedConfiguration));
        when(secrets.getSecretContentById(SECRET_ID)).thenReturn(storedClient());

        Oauth2RuntimeConfiguration read = dao.findByCode(REGISTRATION);

        assertThat(read.getProviderConfig().getIssuerUri())
                .isEqualTo("https://login.microsoftonline.com/" + TENANT + "/v2.0");
        assertThat(read.getProviderConfig().getAuthorizationUri()).doesNotContain("{");
        assertThat(read.getProviderConfig().getIntrospectionUri()).doesNotContain("{");
        assertThat(read.getClient()).isNotNull();
        assertThat(read.getClient().getClientId()).isEqualTo("gebo-ai");
        // the stored object is left exactly as it was, and so is the shared library entry
        assertThat(storedConfiguration.getProviderConfig()).isNull();
        assertThat(storedConfiguration.getClient()).isNull();
        assertThat(library.findByCode(AuthProvider.microsoft_multitenant.name()).getIssuerUri()).isEqualTo(TEMPLATE);
    }

    @Test
    void aConfigurationWhoseClientCannotBeReadIsStillReturned() throws Exception {
        when(repository.findById(REGISTRATION)).thenReturn(Optional.of(stored()));
        when(secrets.getSecretContentById(SECRET_ID)).thenThrow(new GeboCryptSecretException("vault unavailable"));

        Oauth2RuntimeConfiguration read = dao.findByCode(REGISTRATION);

        // one unreadable configuration must not take the others, or authentication, down
        assertThat(read).isNotNull();
        assertThat(read.getRegistrationId()).isEqualTo(REGISTRATION);
        assertThat(read.getClient()).isNull();
    }

    @Test
    void whatIsWrittenBackCarriesNeitherTheClientNorTheResolvedEndpoints() throws Exception {
        when(repository.findById(REGISTRATION)).thenReturn(Optional.of(stored()));
        when(secrets.getSecretContentById(SECRET_ID)).thenReturn(storedClient());
        Oauth2RuntimeConfiguration read = dao.findByCode(REGISTRATION);

        dao.save(read);

        ArgumentCaptor<Oauth2RuntimeConfiguration> written = ArgumentCaptor
                .forClass(Oauth2RuntimeConfiguration.class);
        verify(repository).save(written.capture());
        // the client belongs to the secret store and the endpoints to the library
        assertThat(written.getValue().getClient()).isNull();
        assertThat(written.getValue().getProviderConfig()).isNull();
        assertThat(written.getValue().getRegistrationId()).isEqualTo(REGISTRATION);
    }

    @Test
    void aGenericProviderKeepsTheEndpointsItWasGiven() throws Exception {
        Oauth2ProviderConfig own = new Oauth2ProviderConfig();
        own.setProvider(AuthProvider.oauth2_generic);
        own.setIssuerUri("https://sso.example.com/realms/company");
        Oauth2RuntimeConfiguration configuration = new Oauth2RuntimeConfiguration();
        configuration.setRegistrationId("generic-1");
        configuration.setProvider(AuthProvider.oauth2_generic);
        configuration.setProviderConfig(own);

        dao.insert(configuration);

        ArgumentCaptor<Oauth2RuntimeConfiguration> written = ArgumentCaptor
                .forClass(Oauth2RuntimeConfiguration.class);
        verify(repository).insert(written.capture());
        // nothing else holds these, so they are the one place the addresses live
        assertThat(written.getValue().getProviderConfig()).isNotNull();
        assertThat(written.getValue().getProviderConfig().getIssuerUri())
                .isEqualTo("https://sso.example.com/realms/company");
        assertThat(written.getValue().getClient()).isNull();
        verify(secrets, org.mockito.Mockito.never()).getSecretContentById(anyString());
    }
}
