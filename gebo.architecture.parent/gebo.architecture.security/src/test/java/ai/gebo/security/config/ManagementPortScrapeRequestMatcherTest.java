/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * The anonymous scrape of the actuator happens on the management port only, for
 * health and prometheus only, and never when no separate management port exists.
 */
public class ManagementPortScrapeRequestMatcherTest {

	private static MockHttpServletRequest request(int localPort, String uri) {
		MockHttpServletRequest request = new MockHttpServletRequest("GET", uri);
		request.setLocalPort(localPort);
		return request;
	}

	private static MockEnvironment environment(String managementPort, String serverPort) {
		MockEnvironment environment = new MockEnvironment();
		if (managementPort != null) {
			environment.setProperty("management.server.port", managementPort);
		}
		if (serverPort != null) {
			environment.setProperty("server.port", serverPort);
		}
		return environment;
	}

	@Test
	public void scrapeAndHealthMatchOnTheManagementPort() {
		ManagementPortScrapeRequestMatcher matcher = new ManagementPortScrapeRequestMatcher(
				environment("12998", "12999"));
		assertTrue(matcher.matches(request(12998, "/actuator/prometheus")));
		assertTrue(matcher.matches(request(12998, "/actuator/health")));
		assertTrue(matcher.matches(request(12998, "/actuator/health/liveness")));
	}

	@Test
	public void otherActuatorEndpointsNeverMatch() {
		ManagementPortScrapeRequestMatcher matcher = new ManagementPortScrapeRequestMatcher(
				environment("12998", "12999"));
		assertFalse(matcher.matches(request(12998, "/actuator/metrics")));
		assertFalse(matcher.matches(request(12998, "/actuator/env")));
		assertFalse(matcher.matches(request(12998, "/actuator/prometheus/../env")));
		assertFalse(matcher.matches(request(12998, "/actuator/health/../env")));
		assertFalse(matcher.matches(request(12998, "/actuator/health;x=1")));
		assertFalse(matcher.matches(request(12998, "/api/admin/anything")));
	}

	@Test
	public void theApplicationPortNeverMatches() {
		ManagementPortScrapeRequestMatcher matcher = new ManagementPortScrapeRequestMatcher(
				environment("12998", "12999"));
		assertFalse(matcher.matches(request(12999, "/actuator/prometheus")));
		assertFalse(matcher.matches(request(12999, "/actuator/health")));
	}

	@Test
	public void nothingMatchesWithoutASeparateManagementPort() {
		assertFalse(new ManagementPortScrapeRequestMatcher(environment(null, "12999"))
				.matches(request(12999, "/actuator/prometheus")));
		// the same port as the application: the actuator is the application's, behind auth
		assertFalse(new ManagementPortScrapeRequestMatcher(environment("12999", "12999"))
				.matches(request(12999, "/actuator/prometheus")));
		assertFalse(new ManagementPortScrapeRequestMatcher(environment("0", null))
				.matches(request(0, "/actuator/prometheus")));
	}

	@Test
	public void theActualPortsWinOverTheConfiguredOnes() {
		// management.server.port=0 picks a random port, known as local.management.port
		MockEnvironment environment = environment("0", "0");
		environment.setProperty("local.management.port", "41234");
		environment.setProperty("local.server.port", "41233");
		ManagementPortScrapeRequestMatcher matcher = new ManagementPortScrapeRequestMatcher(environment);
		assertTrue(matcher.matches(request(41234, "/actuator/prometheus")));
		assertFalse(matcher.matches(request(41233, "/actuator/prometheus")));
	}

	@Test
	public void theBasePathsAreHonoured() {
		MockEnvironment environment = environment("12998", "12999");
		environment.setProperty("management.server.base-path", "/mgmt/");
		environment.setProperty("management.endpoints.web.base-path", "/ops");
		ManagementPortScrapeRequestMatcher matcher = new ManagementPortScrapeRequestMatcher(environment);
		assertEquals("/mgmt/ops", matcher.basePath());
		assertTrue(matcher.matches(request(12998, "/mgmt/ops/prometheus")));
		assertFalse(matcher.matches(request(12998, "/actuator/prometheus")));
	}
}
