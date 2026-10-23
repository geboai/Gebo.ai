/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.security.model.oauth2;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.yaml.snakeyaml.Yaml;

import ai.gebo.secrets.model.GeboOauth2SecretContent;
import ai.gebo.security.model.AuthProvider;

/**
 * The provider library is the only place a non-generic provider's endpoints come
 * from: a provider declared in {@link AuthProvider} but missing from the library
 * cannot be turned into a client registration at all, because the administration
 * form offers no URL fields for it.
 *
 * <p>
 * These tests read the shipped library as the application does and check both
 * halves of that contract: the entry exists for every provider that needs one, and
 * the placeholders it is written with are the custom attributes the provider
 * declares, so a saved configuration resolves to concrete URLs.
 * </p>
 */
class Oauth2ProviderLibraryTest {

    private static final String LIBRARY = "/oauth2-library/oauth2-library.yml";

    @SuppressWarnings("unchecked")
    private static Map<String, Object> entryOf(AuthProvider provider) {
        try (InputStream in = Oauth2ProviderLibraryTest.class.getResourceAsStream(LIBRARY)) {
            assertThat(in).as("the provider library resource %s", LIBRARY).isNotNull();
            Map<String, Object> root = new Yaml().load(in);
            Map<String, Object> library = (Map<String, Object>) root.get("ai.gebo.oauth2.library");
            assertThat(library).as("the ai.gebo.oauth2.library section of %s", LIBRARY).isNotNull();
            List<Map<String, Object>> providers = (List<Map<String, Object>>) library.get("providers");
            return providers.stream().filter(p -> provider.name().equals(p.get("provider"))).findFirst().orElse(null);
        } catch (Exception e) {
            throw new IllegalStateException("cannot read " + LIBRARY, e);
        }
    }

    /**
     * Every OAUTH2 provider except the generic one takes its endpoints from the
     * library: the generic one is the only one whose URLs the administrator types.
     */
    @Test
    void everyNonGenericOauth2ProviderHasALibraryEntry() {
        List<AuthProvider> missing = AuthProvider.getOauth2Providers().stream().map(dto -> dto.getProvider())
                .filter(p -> p != AuthProvider.oauth2_generic).filter(p -> entryOf(p) == null).toList();
        assertThat(missing).as("providers offered in the administration form but absent from the library").isEmpty();
    }

    /**
     * A library entry may only use placeholders the provider actually asks the
     * administrator for, otherwise the braces survive into the client registration
     * and the login fails against an unresolvable URL.
     */
    @Test
    void libraryPlaceholdersAreDeclaredCustomAttributes() {
        for (AuthProvider.AuthProviderDto dto : AuthProvider.getOauth2Providers()) {
            Map<String, Object> entry = entryOf(dto.getProvider());
            if (entry == null) {
                continue;
            }
            List<String> declared = dto.getCustomAttributes().stream().map(a -> a.getAttributeName()).toList();
            for (Map.Entry<String, Object> field : entry.entrySet()) {
                String value = String.valueOf(field.getValue());
                java.util.regex.Matcher m = java.util.regex.Pattern.compile("\\{([^}]+)\\}").matcher(value);
                while (m.find()) {
                    assertThat(declared).as("%s.%s uses {%s}, which the provider does not declare",
                            dto.getProvider(), field.getKey(), m.group(1)).contains(m.group(1));
                }
            }
        }
    }

    /**
     * The end of the chain for Keycloak, whose every endpoint hangs off a server and
     * a realm of the customer: with both attributes filled the registration must come
     * out with concrete URLs, and its issuer must be the one the realm signs with.
     */
    @Test
    void keycloakResolvesToTheRealmEndpoints() {
        Map<String, Object> entry = entryOf(AuthProvider.keycloak);
        assertThat(entry).as("the keycloak entry of the provider library").isNotNull();

        Oauth2ProviderConfig providerConfig = new Oauth2ProviderConfig();
        providerConfig.setProvider(AuthProvider.keycloak);
        providerConfig.setAuthorizationUri((String) entry.get("authorizationUri"));
        providerConfig.setTokenUri((String) entry.get("tokenUri"));
        providerConfig.setUserInfoUri((String) entry.get("userInfoUri"));
        providerConfig.setIssuerUri((String) entry.get("issuerUri"));
        providerConfig.setJwkSetUri((String) entry.get("jwkSetUri"));
        providerConfig.setUserNameAttribute((String) entry.get("userNameAttribute"));

        Oauth2RuntimeConfiguration runtime = new Oauth2RuntimeConfiguration();
        runtime.setRegistrationId("keycloak-test");
        runtime.setProvider(AuthProvider.keycloak);
        runtime.setConfigurationType(Oauth2ConfigurationType.AUTHENTICATION);
        runtime.setProviderConfig(providerConfig);

        GeboOauth2SecretContent secret = new GeboOauth2SecretContent();
        secret.setClientId("gebo-ai");
        secret.setSecret("a-client-secret");
        LinkedHashMap<String, String> attributes = new LinkedHashMap<>();
        attributes.put("serverUrl", "https://sso.example.com");
        attributes.put("realm", "company");
        secret.setCustomAttributes(attributes);

        ClientRegistration registration = Oauth2ClientRegistration
                .toClientRegistration(new Oauth2ClientRegistration(secret, runtime));

        assertThat(registration.getProviderDetails().getIssuerUri())
                .isEqualTo("https://sso.example.com/realms/company");
        assertThat(registration.getProviderDetails().getAuthorizationUri())
                .isEqualTo("https://sso.example.com/realms/company/protocol/openid-connect/auth");
        assertThat(registration.getProviderDetails().getTokenUri())
                .isEqualTo("https://sso.example.com/realms/company/protocol/openid-connect/token");
        assertThat(registration.getProviderDetails().getJwkSetUri())
                .isEqualTo("https://sso.example.com/realms/company/protocol/openid-connect/certs");
        // an authentication configuration with no scopes of its own logs in with the
        // standard OIDC set, which is what makes the id token carry the e-mail
        assertThat(registration.getScopes()).contains("openid", "email");
    }
}
