/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.security.services.impl.authmanagers;

import static ai.gebo.security.services.impl.authmanagers.MicrosoftTokenSigner.CONTOSO;
import static ai.gebo.security.services.impl.authmanagers.MicrosoftTokenSigner.FABRIKAM;
import static ai.gebo.security.services.impl.authmanagers.MicrosoftTokenSigner.PERSONAL;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.oauth2.Oauth2ProviderConfig;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;

/**
 * Which configured provider a token's issuer belongs to. The store is the authority:
 * a provider deleted by an administrator must be unknown from the next request on,
 * not after a restart, and an edit must apply from the next request on as well.
 */
class IssuerConfigCacheTest {

    private static final String ISSUER = "https://sso.example.com/realms/company";

    /** What the store holds now, by registration id. */
    private final Map<String, Oauth2RuntimeConfiguration> store = new HashMap<>();
    private final AtomicInteger scans = new AtomicInteger();
    private IssuerConfigCache cache;

    @BeforeEach
    void setUp() {
        store.clear();
        scans.set(0);
        cache = new IssuerConfigCache(store::get);
    }

    private static Oauth2RuntimeConfiguration configuration(String id, String issuer, List<String> audiences) {
        Oauth2ProviderConfig provider = new Oauth2ProviderConfig();
        provider.setIssuerUri(issuer);
        Oauth2RuntimeConfiguration configuration = new Oauth2RuntimeConfiguration();
        configuration.setRegistrationId(id);
        configuration.setProviderConfig(provider);
        configuration.setResourceServerAudiences(audiences);
        return configuration;
    }

    /** Looks the issuer up the way the resolver does: scanning the store when asked to. */
    private Oauth2RuntimeConfiguration lookup(String issuer) {
        return cache.forIssuer(issuer, () -> {
            scans.incrementAndGet();
            return IssuerConfigCache.answeringFor(List.copyOf(store.values()), issuer);
        }).orElse(null);
    }

    private static String tenantIssuer(String tenant) {
        return "https://login.microsoftonline.com/" + tenant + "/v2.0";
    }

    private static Oauth2RuntimeConfiguration microsoftAccounts(String id, String allowedTenants) {
        return MicrosoftTokenSigner.otherProvider(id, AuthProvider.microsoft, tenantIssuer(PERSONAL), allowedTenants);
    }

    @Test
    void aKnownIssuerIsNotScannedForAgain() {
        store.put("keycloak-1", configuration("keycloak-1", ISSUER, null));

        assertThat(lookup(ISSUER)).isNotNull();
        assertThat(lookup(ISSUER)).isNotNull();
        assertThat(lookup(ISSUER)).isNotNull();

        assertThat(scans.get()).isEqualTo(1);
    }

    @Test
    void aDeletedProviderIsUnknownFromTheNextRequest() {
        store.put("keycloak-1", configuration("keycloak-1", ISSUER, null));
        assertThat(lookup(ISSUER)).isNotNull();

        store.remove("keycloak-1");

        assertThat(lookup(ISSUER)).isNull();
    }

    @Test
    void anEditAppliesFromTheNextRequest() {
        store.put("keycloak-1", configuration("keycloak-1", ISSUER, null));
        assertThat(lookup(ISSUER).getResourceServerAudiences()).isNull();

        store.put("keycloak-1", configuration("keycloak-1", ISSUER, List.of("gebo-ai")));

        assertThat(lookup(ISSUER).getResourceServerAudiences()).containsExactly("gebo-ai");
    }

    @Test
    void aProviderMovedToAnotherIssuerNoLongerAnswersForTheOldOne() {
        store.put("keycloak-1", configuration("keycloak-1", ISSUER, null));
        assertThat(lookup(ISSUER)).isNotNull();

        store.put("keycloak-1", configuration("keycloak-1", "https://sso.example.com/realms/other", null));

        assertThat(lookup(ISSUER)).isNull();
    }

    @Test
    void aProviderAddedForAnIssuerSeenBeforeIsKnownAtOnce() {
        // a token from this issuer arrived while nothing was configured for it
        assertThat(lookup(ISSUER)).isNull();

        store.put("keycloak-1", configuration("keycloak-1", ISSUER, null));

        assertThat(lookup(ISSUER)).isNotNull();
    }

    /**
     * The people of the Microsoft accounts configuration belong to several tenants,
     * each signing under an issuer of its own, and it answers for those it admits.
     */
    @Test
    void theMicrosoftAccountsConfigurationAnswersForTheTenantsItAdmits() {
        Oauth2RuntimeConfiguration configuration = microsoftAccounts("microsoft-1", "consumers, " + CONTOSO);

        assertThat(IssuerConfigCache.answersFor(configuration, tenantIssuer(PERSONAL))).isTrue();
        assertThat(IssuerConfigCache.answersFor(configuration, tenantIssuer(CONTOSO))).isTrue();
        assertThat(IssuerConfigCache.answersFor(configuration, tenantIssuer(CONTOSO.toUpperCase()))).isTrue();
        assertThat(IssuerConfigCache.answersFor(configuration, tenantIssuer(FABRIKAM))).isFalse();
        assertThat(IssuerConfigCache.answersFor(configuration, tenantIssuer("common"))).isFalse();
        assertThat(IssuerConfigCache.answersFor(configuration, tenantIssuer(CONTOSO + "/more"))).isFalse();
        assertThat(IssuerConfigCache.answersFor(configuration, "https://login.microsoftonline.com/v2.0")).isFalse();
        assertThat(IssuerConfigCache.answersFor(configuration, "https://sts.windows.net/" + CONTOSO + "/"))
                .isFalse();
        assertThat(IssuerConfigCache.answersFor(configuration, null)).isFalse();
    }

    /** No other provider reads the list, whatever it was saved with. */
    @Test
    void anotherProviderAnswersForItsOwnIssuerOnly() {
        Oauth2RuntimeConfiguration organization = MicrosoftTokenSigner.otherProvider("entra-1",
                AuthProvider.microsoft_multitenant, tenantIssuer(CONTOSO), FABRIKAM);

        assertThat(IssuerConfigCache.answersFor(organization, tenantIssuer(CONTOSO))).isTrue();
        assertThat(IssuerConfigCache.answersFor(organization, tenantIssuer(FABRIKAM))).isFalse();
    }

    /**
     * An organization configured on its own is meant more precisely than the
     * Microsoft accounts configuration that also admits it, whichever comes first.
     */
    @Test
    void anOrganizationConfiguredOnItsOwnIsPreferred() {
        Oauth2RuntimeConfiguration accounts = microsoftAccounts("microsoft-1", "consumers, " + CONTOSO);
        Oauth2RuntimeConfiguration organization = MicrosoftTokenSigner.otherProvider("entra-1",
                AuthProvider.microsoft_multitenant, tenantIssuer(CONTOSO), null);

        assertThat(IssuerConfigCache.answeringFor(List.of(accounts, organization), tenantIssuer(CONTOSO)))
                .isSameAs(organization);
        assertThat(IssuerConfigCache.answeringFor(List.of(organization, accounts), tenantIssuer(CONTOSO)))
                .isSameAs(organization);
        assertThat(IssuerConfigCache.answeringFor(List.of(organization, accounts), tenantIssuer(PERSONAL)))
                .isSameAs(accounts);
        assertThat(IssuerConfigCache.answeringFor(List.of(organization, accounts), tenantIssuer(FABRIKAM)))
                .isNull();
    }

    @Test
    void aTenantTakenOffTheListIsUnknownFromTheNextRequest() {
        store.put("microsoft-1", microsoftAccounts("microsoft-1", "consumers, " + CONTOSO));
        assertThat(lookup(tenantIssuer(CONTOSO))).isNotNull();
        assertThat(lookup(tenantIssuer(CONTOSO))).isNotNull();
        assertThat(scans.get()).isEqualTo(1);

        store.put("microsoft-1", microsoftAccounts("microsoft-1", "consumers"));

        assertThat(lookup(tenantIssuer(CONTOSO))).isNull();
        assertThat(lookup(tenantIssuer(PERSONAL))).isNotNull();
    }

    @Test
    void noIssuerIsNoProvider() {
        assertThat(cache.forIssuer(null, () -> {
            throw new AssertionError("nothing to look up without an issuer");
        })).isEmpty();
    }
}
