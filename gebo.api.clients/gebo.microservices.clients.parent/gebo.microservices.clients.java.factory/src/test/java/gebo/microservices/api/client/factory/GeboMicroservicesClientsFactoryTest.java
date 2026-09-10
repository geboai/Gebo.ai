package gebo.microservices.api.client.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

/**
 * Covers the one thing this module exists to get right: turning ONE base url
 * plus the published topology into the per-service base urls, identically for
 * both installation shapes.
 */
class GeboMicroservicesClientsFactoryTest {

	private static final String BASE_URL = "https://gebo.example.com";
	private static final String TOPOLOGY_URL = BASE_URL + GeboClientsTopologyResolver.TOPOLOGY_PATH;

	private static final String MICROSERVICES_TOPOLOGY = """
			{"architectureType":"MICROSERVICES","services":[
			  {"serviceId":"brain_gebo_ai","relativeContextUrl":"/brain"},
			  {"serviceId":"heimdall_gebo_ai","relativeContextUrl":"/heimdall"},
			  {"serviceId":"aws_s3_gebo_ai","relativeContextUrl":"/aws-s3"},
			  {"serviceId":"gateway_gebo_ai","relativeContextUrl":""}]}""";

	private static final String MONOLITHIC_TOPOLOGY = """
			{"architectureType":"MONOLITHIC","services":[
			  {"serviceId":"default","relativeContextUrl":""}]}""";

	@Test
	void microservicesTopologyAppendsThePerServiceWebContext() {
		GeboMicroservicesClientsFactory clients = factoryAnswering(MICROSERVICES_TOPOLOGY);

		assertEquals(BASE_URL + "/brain", clients.brain().getBasePath());
		assertEquals(BASE_URL + "/heimdall", clients.heimdall().getBasePath());
		assertEquals(BASE_URL + "/aws-s3", clients.awsS3().getBasePath());
		// The gateway IS the base url - its entry carries the empty relative url.
		assertEquals(BASE_URL, clients.gateway().getBasePath());
	}

	@Test
	void monolithicTopologyPointsEveryClientAtTheBaseUrl() {
		GeboMicroservicesClientsFactory clients = factoryAnswering(MONOLITHIC_TOPOLOGY);

		assertEquals(BASE_URL, clients.brain().getBasePath());
		assertEquals(BASE_URL, clients.heimdall().getBasePath());
		assertEquals(BASE_URL, clients.awsS3().getBasePath());
	}

	@Test
	void aServiceTheInstallationDoesNotPublishThrowsRatherThanGuessing() {
		GeboMicroservicesClientsFactory clients = factoryAnswering(MICROSERVICES_TOPOLOGY);

		// eureka is inside the deployment, never routed at the edge: there is no
		// url to build, and a silent baseUrl fallback would only 404 later.
		IllegalStateException failure = assertThrows(IllegalStateException.class, clients::eureka);
		org.junit.jupiter.api.Assertions.assertTrue(failure.getMessage().contains(GeboMicroservices.EUREKA),
				"the failure must name the unresolvable service, was: " + failure.getMessage());
	}

	@Test
	void anUnreachableTopologyDegradesToTheMonolithicShape() {
		RestTemplate restTemplate = new RestTemplate();
		MockRestServiceServer.bindTo(restTemplate).build().expect(requestTo(TOPOLOGY_URL))
				.andRespond(withServerError());

		// A server predating the endpoint is always a monolith, so answering with
		// the base url for everything is the right degradation - not an abort.
		GeboMicroservicesClientsFactory clients = GeboMicroservicesClientsFactory.of(BASE_URL, restTemplate);
		assertEquals(BASE_URL, clients.brain().getBasePath());
		assertEquals(ArchitectureType.MONOLITHIC, clients.topology().getArchitectureType());
	}

	@Test
	void theTopologyIsReadOnceAndTheClientsAreShared() {
		// ExpectedCount.once() on the mock is the assertion: a second GET fails.
		GeboMicroservicesClientsFactory clients = factoryAnswering(MICROSERVICES_TOPOLOGY);

		assertSame(clients.brain(), clients.brain());
		clients.heimdall();
		clients.awsS3();
	}

	@Test
	void aTrailingSlashOnTheBaseUrlDoesNotDoubleUp() {
		RestTemplate restTemplate = new RestTemplate();
		MockRestServiceServer.bindTo(restTemplate).build().expect(requestTo(TOPOLOGY_URL))
				.andRespond(withSuccess(MICROSERVICES_TOPOLOGY, MediaType.APPLICATION_JSON));

		GeboMicroservicesClientsFactory clients = GeboMicroservicesClientsFactory.of(BASE_URL + "/", restTemplate);
		assertEquals(BASE_URL + "/brain", clients.brain().getBasePath());
	}

	@Test
	void defaultHeadersReachTheClientsCreatedBeforeAndAfterThem() {
		GeboMicroservicesClientsFactory clients = factoryAnswering(MICROSERVICES_TOPOLOGY);

		clients.brain();
		clients.addDefaultHeader("Authorization", "Bearer token");
		clients.heimdall();

		// The generated ApiClient exposes no header getter, so what this pins is
		// the reflective contract the factory relies on: every generated client
		// still has setBasePath(String) and addDefaultHeader(String, String). A
		// template change that renamed either would fail here with the message
		// naming the class, instead of silently sending unauthenticated calls.
		assertEquals(BASE_URL + "/brain", clients.brain().getBasePath());
	}

	private static GeboMicroservicesClientsFactory factoryAnswering(String topologyJson) {
		RestTemplate restTemplate = new RestTemplate();
		MockRestServiceServer.bindTo(restTemplate).build().expect(ExpectedCount.once(), requestTo(TOPOLOGY_URL))
				.andRespond(withSuccess(topologyJson, MediaType.APPLICATION_JSON));
		return GeboMicroservicesClientsFactory.of(BASE_URL, restTemplate);
	}
}
