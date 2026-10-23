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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Where a login is allowed to end. The address is a parameter of the request that
 * started it, so anyone can hand out a link that starts a login on this
 * installation and finishes somewhere else, under its name, and the page it is
 * written into is the one that carries the session token.
 */
class SafeRedirectUriTest {

    private static final String FALLBACK = "/ui/chat";

    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
        when(request.getServerName()).thenReturn("gebo.example.com");
    }

    @Test
    void aPageOfThisInstallationIsFollowed() {
        assertThat(SafeRedirectUri.resolve("/ui/oauth2-land?status=TRANSIENT", request, FALLBACK))
                .isEqualTo("/ui/oauth2-land?status=TRANSIENT");
        assertThat(SafeRedirectUri.resolve("https://gebo.example.com/ui/chat", request, FALLBACK))
                .isEqualTo("https://gebo.example.com/ui/chat");
    }

    @Test
    void anotherSiteIsNot() {
        assertThat(SafeRedirectUri.resolve("https://elsewhere.example/landing", request, FALLBACK))
                .isEqualTo(FALLBACK);
        // a complete address in disguise: the browser reads it as one, a naive check does not
        assertThat(SafeRedirectUri.resolve("//elsewhere.example/landing", request, FALLBACK)).isEqualTo(FALLBACK);
        assertThat(SafeRedirectUri.resolve("/\\elsewhere.example/landing", request, FALLBACK)).isEqualTo(FALLBACK);
    }

    @Test
    void nothingAtAllGoesToThePageTheCallerChose() {
        assertThat(SafeRedirectUri.resolve(null, request, FALLBACK)).isEqualTo(FALLBACK);
        assertThat(SafeRedirectUri.resolve("   ", request, FALLBACK)).isEqualTo(FALLBACK);
        assertThat(SafeRedirectUri.resolve("http://a b c", request, FALLBACK)).isEqualTo(FALLBACK);
    }

    @Test
    void aStringWrittenIntoAScriptCannotEndIt() {
        String escaped = SafeRedirectUri.forScriptString("/ui/chat\";</script><script>alert(1)</script>");

        assertThat(escaped).doesNotContain("</script>").doesNotContain("\"");
        assertThat(escaped).contains("\\u003c").contains("\\u0022");
    }

    @Test
    void aJsonLiteralStaysValidJsonAndStillCannotEndTheScript() {
        String escaped = SafeRedirectUri.forScriptJson("{\"title\":\"</script><script>alert(1)</script>\"}");

        // the quotes json needs are untouched, so the literal still parses
        assertThat(escaped).startsWith("{\"title\":\"").endsWith("\"}");
        assertThat(escaped).doesNotContain("</script>");
        assertThat(escaped).contains("\\u003c");
    }
}
