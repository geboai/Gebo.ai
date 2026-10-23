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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import ai.gebo.secrets.model.GeboOauth2SecretContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.oauth2.Oauth2ConfigurationType;
import ai.gebo.security.model.oauth2.Oauth2ProviderConfig;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import ai.gebo.security.services.IGOauth2ProvidersLibraryDao;
import ai.gebo.security.services.IGOauth2RuntimeConfigurationDao;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;

/**
 * The description an administrator types when creating an OAUTH2 configuration is
 * the only human-readable name it ever gets: it names the configuration in the
 * setup list and it is the label of the provider's button on the login page. It
 * used to be accepted by the controller and then dropped on the way to the store,
 * so a freshly created configuration came back with a null description and an
 * unlabelled login button until someone edited and saved it again.
 */
class GOauth2ConfigurationServiceImplInsertTest {

    private GOauth2ConfigurationServiceImpl service;
    private ArgumentCaptor<Oauth2RuntimeConfiguration> inserted;

    @BeforeEach
    void setUp() throws Exception {
        service = new GOauth2ConfigurationServiceImpl();
        service.secretService = mock(IGeboSecretsAccessService.class);
        service.repository = mock(IGOauth2RuntimeConfigurationDao.class);
        service.providersLibraryDao = mock(IGOauth2ProvidersLibraryDao.class);
        service.securityAuditLoggerService = mock(IGSecurityAuditLoggerService.class);
        when(service.securityAuditLoggerService.newSecurityEvent()).thenReturn(mock(SecurityEvent.class));
        when(service.secretService.storeSecret(any(), anyString(), anyString())).thenReturn("a-secret-id");
        inserted = ArgumentCaptor.forClass(Oauth2RuntimeConfiguration.class);
    }

    private static GeboOauth2SecretContent client() {
        GeboOauth2SecretContent content = new GeboOauth2SecretContent();
        content.setClientId("gebo-ai");
        content.setSecret("a-client-secret");
        return content;
    }

    @Test
    void theDescriptionSurvivesTheGenericProviderInsert() throws Exception {
        Oauth2ProviderConfig providerConfig = new Oauth2ProviderConfig();
        providerConfig.setProvider(AuthProvider.oauth2_generic);
        providerConfig.setIssuerUri("https://sso.example.com/realms/company");

        service.insertOauth2Configuration(providerConfig, client(), null, null, Oauth2ConfigurationType.AUTHENTICATION,
                "Company single sign-on");

        org.mockito.Mockito.verify(service.repository).insert(inserted.capture());
        assertThat(inserted.getValue().getDescription()).isEqualTo("Company single sign-on");
    }

    @Test
    void theDescriptionSurvivesTheLibraryProviderInsert() throws Exception {
        service.insertOauth2Configuration(AuthProvider.keycloak, client(), null, null,
                Oauth2ConfigurationType.AUTHENTICATION, "Keycloak company realm");

        org.mockito.Mockito.verify(service.repository).insert(inserted.capture());
        assertThat(inserted.getValue().getDescription()).isEqualTo("Keycloak company realm");
        assertThat(inserted.getValue().getProvider()).isEqualTo(AuthProvider.keycloak);
    }
}
