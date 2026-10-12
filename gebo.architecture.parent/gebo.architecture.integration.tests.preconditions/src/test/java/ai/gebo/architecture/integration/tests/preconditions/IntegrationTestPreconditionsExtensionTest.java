package ai.gebo.architecture.integration.tests.preconditions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.ServerSocket;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.testkit.engine.EngineExecutionResults;
import org.junit.platform.testkit.engine.EngineTestKit;
import org.junit.platform.testkit.engine.Event;

class IntegrationTestPreconditionsExtensionTest {

	static final String UNSET = "GEBO_PRECONDITIONS_TEST_UNSET";
	static final String BASE_UNSET = "GEBO_PRECONDITIONS_TEST_BASE_UNSET";
	static final String COMPOSED_UNSET = "GEBO_PRECONDITIONS_TEST_COMPOSED_UNSET";
	static final String CONFIGURED = "gebo.preconditions.test.configured";
	static final String DIRECTORIES = "gebo.preconditions.test.directories";
	static final String ENDPOINT_URL = "gebo.preconditions.test.endpoint.url";

	@AfterEach
	void clearProperties() {
		for (String name : List.of(CONFIGURED, DIRECTORIES, ENDPOINT_URL,
				IntegrationTestConfig.TEST_WHAT_IS_CONFIGURED)) {
			System.clearProperty(name);
		}
	}

	@Test
	void skipsByDefaultListingWhatIsMissing() {
		EngineExecutionResults results = run(MissingConfigFixture.class);

		results.containerEvents().assertStatistics(stats -> stats.skipped(1).failed(0));
		results.testEvents().assertStatistics(stats -> stats.started(0));
		assertThat(skipReason(results))
				.startsWith(IntegrationTestPreconditionsExtension.REPORT_PREFIX + MissingConfigFixture.class.getName())
				.contains("configuration " + UNSET + " is not set (what the fixture needs): "
						+ "set the environment variable " + UNSET + " or pass -D" + UNSET + "=...");
	}

	@Test
	void failsWhenTestWhatIsConfiguredIsFalse() {
		System.setProperty(IntegrationTestConfig.TEST_WHAT_IS_CONFIGURED, "false");

		EngineExecutionResults results = run(MissingConfigFixture.class);

		results.containerEvents().assertStatistics(stats -> stats.skipped(0).failed(1));
		results.testEvents().assertStatistics(stats -> stats.started(0));
		Throwable cause = failure(results);
		while (cause != null && !(cause instanceof IntegrationTestPreconditionsNotMetException)) {
			cause = cause.getCause();
		}
		assertThat(cause).isNotNull();
		assertThat(cause.getMessage()).contains("configuration " + UNSET + " is not set")
				.contains("Failing, as -DtestWhatIsConfigured=false");
	}

	@Test
	void runsWhenEverythingIsConfigured() {
		System.setProperty(CONFIGURED, "value");

		run(ConfiguredFixture.class).testEvents().assertStatistics(stats -> stats.succeeded(1).skipped(0));
	}

	@Test
	void requirementsOfSuperclassesAndComposedAnnotationsAddUp() {
		String reason = skipReason(run(HierarchyFixture.class));

		assertThat(reason).contains("configuration " + BASE_UNSET + " is not set",
				"configuration " + COMPOSED_UNSET + " is not set", "configuration " + UNSET + " is not set");
		assertThat(reason.indexOf(BASE_UNSET)).isLessThan(reason.indexOf(UNSET + " is not set (what"));
	}

	@Test
	void subclassesWithoutAnnotationsInheritTheBaseRequirements() {
		assertThat(skipReason(run(InheritingOnlyFixture.class))).contains("configuration " + BASE_UNSET + " is not set");
	}

	@Test
	void methodLevelRequirementsOnlyAffectThatMethod() {
		run(MethodLevelFixture.class).testEvents().assertStatistics(stats -> stats.succeeded(1).skipped(1));
	}

	@Test
	void everyListedDirectoryIsChecked(@TempDir Path directory) {
		Path missing = directory.resolve("missing");
		System.setProperty(DIRECTORIES, directory + File.pathSeparator + missing);

		String reason = skipReason(run(DirectoriesFixture.class));

		assertThat(reason).contains("directory " + missing + " listed in " + DIRECTORIES + " does not exist")
				.doesNotContain("directory " + directory + " listed");
	}

	@Test
	void unsetDirectoriesAreReported() {
		assertThat(skipReason(run(DirectoriesFixture.class)))
				.contains("configuration " + DIRECTORIES + " is not set (one or more directories separated by '"
						+ File.pathSeparator + "')");
	}

	@Test
	void reachableEndpointsPass() throws IOException {
		try (ServerSocket server = new ServerSocket(0)) {
			System.setProperty(ENDPOINT_URL, "http://localhost:" + server.getLocalPort() + "/service");

			run(EndpointFixture.class).testEvents().assertStatistics(stats -> stats.succeeded(1));
		}
	}

	@Test
	void unreachableEndpointsNameTheSettingToChange() throws IOException {
		int port;
		try (ServerSocket server = new ServerSocket(0)) {
			port = server.getLocalPort();
		}
		System.setProperty(ENDPOINT_URL, "http://localhost:" + port + "/service");

		assertThat(skipReason(run(EndpointFixture.class)))
				.contains("fixture-service is not reachable at http://localhost:" + port
						+ "/service (nothing accepts connections on localhost:" + port + "): start it, or point "
						+ ENDPOINT_URL + " at a running instance");
	}

	@Test
	void valuesDeclaredTogetherAreReportedOnOneLine() {
		assertThat(skipReason(run(GroupedConfigFixture.class)))
				.contains("configuration " + UNSET + ", " + BASE_UNSET + " are not set (fixture credentials): "
						+ "set them as environment variables or pass them as -D<name>=...")
				.contains("configuration " + COMPOSED_UNSET + " is not set: ");
	}

	@Test
	void customChecksContributeTheirLines() {
		assertThat(skipReason(run(CustomFixture.class))).contains("  - the custom thing is missing");
	}

	private static EngineExecutionResults run(Class<?> fixture) {
		return EngineTestKit.engine("junit-jupiter").selectors(selectClass(fixture)).execute();
	}

	private static String skipReason(EngineExecutionResults results) {
		List<Event> skipped = results.containerEvents().skipped().list();
		assertThat(skipped).as("skipped containers").hasSize(1);
		return skipped.get(0).getPayload(String.class).orElseThrow();
	}

	private static Throwable failure(EngineExecutionResults results) {
		return results.containerEvents().failed().list().get(0).getPayload(TestExecutionResult.class).orElseThrow()
				.getThrowable().orElseThrow();
	}

	@RequiresConfig(value = UNSET, description = "what the fixture needs")
	static class MissingConfigFixture {
		@Test
		void test() {
		}
	}

	@RequiresConfig(value = { UNSET, BASE_UNSET }, description = "fixture credentials")
	@RequiresConfig(COMPOSED_UNSET)
	static class GroupedConfigFixture {
		@Test
		void test() {
		}
	}

	@RequiresConfig(CONFIGURED)
	static class ConfiguredFixture {
		@Test
		void test() {
		}
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@RequiresConfig(COMPOSED_UNSET)
	@interface ComposedRequirement {
	}

	@RequiresConfig(BASE_UNSET)
	abstract static class BaseFixture {
		@Test
		void test() {
		}
	}

	@ComposedRequirement
	@RequiresConfig(value = UNSET, description = "what the subclass needs")
	static class HierarchyFixture extends BaseFixture {
	}

	static class InheritingOnlyFixture extends BaseFixture {
	}

	static class MethodLevelFixture {
		@Test
		void runs() {
		}

		@Test
		@RequiresConfig(UNSET)
		void skipped() {
		}
	}

	@RequiresDirectories(DIRECTORIES)
	static class DirectoriesFixture {
		@Test
		void test() {
		}
	}

	@RequiresEndpoint(name = "fixture-service", url = "${" + ENDPOINT_URL + "}", timeoutMillis = 500)
	static class EndpointFixture {
		@Test
		void test() {
		}
	}

	public static class MissingCustomThing implements PreconditionCheck {
		@Override
		public List<String> missing() {
			return List.of("the custom thing is missing");
		}
	}

	@RequiresCustom(MissingCustomThing.class)
	static class CustomFixture {
		@Test
		void test() {
		}
	}
}
