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
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.client.oidc.authentication.OidcIdTokenValidator;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.yaml.snakeyaml.Yaml;

import ai.gebo.secrets.model.GeboOauth2SecretContent;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.services.impl.GMicrosoftAccountsIdTokenValidator;

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

    /** The tenant every personal Microsoft account belongs to. */
    private static final String MICROSOFT_PERSONAL_ACCOUNTS_TENANT = "9188040d-6c67-4c5b-b112-36a304b66dad";

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

    /**
     * The OIDC login builds its user with the registration's user name attribute and
     * fails when the claims lack it, before Gebo.ai sees the user. Only two claims can
     * be counted on: {@code sub}, which every id token carries, and {@code email},
     * without which the login policy refuses the user anyway.
     */
    @Test
    void everyLibraryUserNameAttributeIsAClaimTheLoginAlwaysHas() {
        for (AuthProvider.AuthProviderDto dto : AuthProvider.getOauth2Providers()) {
            Map<String, Object> entry = entryOf(dto.getProvider());
            if (entry == null || entry.get("userNameAttribute") == null) {
                continue;
            }
            assertThat(entry.get("userNameAttribute")).as("%s.userNameAttribute", dto.getProvider())
                    .isIn("sub", "email");
        }
    }

    /**
     * Microsoft Entra ID with the claims Microsoft documents for a v2.0 id token and
     * for https://graph.microsoft.com/oidc/userinfo, given to the same
     * {@link DefaultOidcUser} constructor Spring's OIDC login calls with the
     * registration's user name attribute: the user must come out, named by its
     * subject.
     */
    @Test
    void entraLoginBuildsItsUserFromMicrosoftClaims() {
        Map<String, Object> entry = entryOf(AuthProvider.microsoft_multitenant);
        assertThat(entry).as("the microsoft_multitenant entry of the provider library").isNotNull();
        String tenant = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee";
        String subject = "OLu859SGc2Sr9ZsqbkG-QbeLgJlb41KcdiPoLYNpSFA";
        Instant now = Instant.now();
        OidcIdToken idToken = OidcIdToken.withTokenValue("id-token").issuer("https://login.microsoftonline.com/" + tenant + "/v2.0")
                .subject(subject).audience(List.of("11111111-2222-3333-4444-555555555555")).issuedAt(now)
                .expiresAt(now.plusSeconds(3600)).claim("oid", "00000000-0000-0000-66f3-3332eca7ea81")
                .claim("tid", tenant).claim("preferred_username", "anna.bianchi@demo-company.example")
                .claim("name", "Anna Bianchi").claim("ver", "2.0").build();
        OidcUserInfo userInfo = OidcUserInfo.builder().subject(subject).name("Anna Bianchi").givenName("Anna")
                .familyName("Bianchi").email("anna.bianchi@demo-company.example").build();

        DefaultOidcUser user = new DefaultOidcUser(Set.of(), idToken, userInfo,
                (String) entry.get("userNameAttribute"));

        assertThat(user.getName()).isEqualTo(subject);
        // the claim the login policy looks the Gebo.ai user up by
        assertThat((String) user.getAttribute("email")).isEqualTo("anna.bianchi@demo-company.example");
    }

    /**
     * The Microsoft accounts entry against what Microsoft publishes at
     * https://login.microsoftonline.com/common/v2.0/.well-known/openid-configuration,
     * except the issuer, which "common" does not have: it is the one of personal
     * accounts, published for "consumers". The user info has to be the OIDC one:
     * Spring's OIDC login refuses a user info response without the id token's
     * {@code sub}, and Graph's /v1.0/me has none.
     */
    @Test
    void microsoftAccountsUseTheCommonEndpoints() {
        Map<String, Object> entry = entryOf(AuthProvider.microsoft);
        assertThat(entry).as("the microsoft entry of the provider library").isNotNull();

        assertThat(entry.get("authorizationUri"))
                .isEqualTo("https://login.microsoftonline.com/common/oauth2/v2.0/authorize");
        assertThat(entry.get("tokenUri")).isEqualTo("https://login.microsoftonline.com/common/oauth2/v2.0/token");
        assertThat(entry.get("jwkSetUri")).isEqualTo("https://login.microsoftonline.com/common/discovery/v2.0/keys");
        assertThat(entry.get("userInfoUri")).isEqualTo("https://graph.microsoft.com/oidc/userinfo");
        assertThat(entry.get("issuerUri"))
                .isEqualTo("https://login.microsoftonline.com/" + MICROSOFT_PERSONAL_ACCOUNTS_TENANT + "/v2.0");
    }

    /**
     * The checks an id token goes through, on a registration built from the Microsoft
     * accounts entry that admits personal accounts and one organization. Spring's own
     * check passes only personal accounts, whose issuer is the one of the entry: an
     * organization's tokens carry the issuer of its tenant. Gebo's check passes both
     * the admitted ones, and refuses an organization that is not admitted.
     */
    @Test
    void microsoftAccountsTokensPassTheLoginCheckOfTheirTenant() {
        Map<String, Object> entry = entryOf(AuthProvider.microsoft);
        assertThat(entry).as("the microsoft entry of the provider library").isNotNull();
        String admitted = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee";
        String other = "ffffffff-bbbb-cccc-dddd-eeeeeeeeeeee";

        Oauth2ProviderConfig providerConfig = new Oauth2ProviderConfig();
        providerConfig.setProvider(AuthProvider.microsoft);
        providerConfig.setAuthorizationUri((String) entry.get("authorizationUri"));
        providerConfig.setTokenUri((String) entry.get("tokenUri"));
        providerConfig.setUserInfoUri((String) entry.get("userInfoUri"));
        providerConfig.setIssuerUri((String) entry.get("issuerUri"));
        providerConfig.setJwkSetUri((String) entry.get("jwkSetUri"));
        providerConfig.setUserNameAttribute((String) entry.get("userNameAttribute"));

        Oauth2RuntimeConfiguration runtime = new Oauth2RuntimeConfiguration();
        runtime.setRegistrationId("microsoft-test");
        runtime.setProvider(AuthProvider.microsoft);
        runtime.setConfigurationType(Oauth2ConfigurationType.AUTHENTICATION);
        runtime.setProviderConfig(providerConfig);

        String clientId = "11111111-2222-3333-4444-555555555555";
        GeboOauth2SecretContent secret = new GeboOauth2SecretContent();
        secret.setClientId(clientId);
        secret.setSecret("a-client-secret");
        LinkedHashMap<String, String> attributes = new LinkedHashMap<>();
        attributes.put(GMicrosoftAccountsIdTokenValidator.ALLOWED_TENANTS, "consumers, " + admitted);
        secret.setCustomAttributes(attributes);

        ClientRegistration registration = Oauth2ClientRegistration
                .toClientRegistration(new Oauth2ClientRegistration(secret, runtime));
        OidcIdTokenValidator spring = new OidcIdTokenValidator(registration);
        GMicrosoftAccountsIdTokenValidator gebo = new GMicrosoftAccountsIdTokenValidator(registration,
                GMicrosoftAccountsIdTokenValidator.admittedTenants(attributes));

        assertThat(spring.validate(microsoftIdToken(MICROSOFT_PERSONAL_ACCOUNTS_TENANT, clientId)).getErrors())
                .as("Spring's check of a personal account's id token").isEmpty();
        assertThat(spring.validate(microsoftIdToken(admitted, clientId)).hasErrors())
                .as("Spring's check of an organization account's id token").isTrue();

        assertThat(gebo.validate(microsoftIdToken(MICROSOFT_PERSONAL_ACCOUNTS_TENANT, clientId)).getErrors())
                .as("a personal account's id token").isEmpty();
        assertThat(gebo.validate(microsoftIdToken(admitted, clientId)).getErrors())
                .as("the id token of an account of the admitted organization").isEmpty();
        assertThat(gebo.validate(microsoftIdToken(other, clientId)).hasErrors())
                .as("the id token of an account of another organization").isTrue();
    }

    /**
     * An id token as Microsoft's v2.0 endpoints issue it to the given client for a
     * person of the given tenant.
     */
    private static Jwt microsoftIdToken(String tenant, String clientId) {
        Instant now = Instant.now();
        return Jwt.withTokenValue("id-token").header("alg", "RS256")
                .issuer("https://login.microsoftonline.com/" + tenant + "/v2.0")
                .subject("AAAAAAAAAAAAAAAAAAAAAIkzqFVrSaSaFHy782bbtaQ").audience(List.of(clientId)).issuedAt(now)
                .expiresAt(now.plusSeconds(3600)).claim("tid", tenant)
                .claim("preferred_username", "anna.bianchi@outlook.com").claim("email", "anna.bianchi@outlook.com")
                .claim("name", "Anna Bianchi").claim("ver", "2.0").build();
    }
}
