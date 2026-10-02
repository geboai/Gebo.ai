package ai.gebo.architecture.integration.tests.preconditions;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import ai.gebo.architecture.integration.tests.preconditions.IntegrationTestConfig.ResolvedText;

class IntegrationTestConfigTest {

	private static final String PROPERTY = "gebo.preconditions.test.config.value";
	private static final String UNSET = "GEBO_PRECONDITIONS_TEST_NEVER_SET";

	@AfterEach
	void clearProperties() {
		System.clearProperty(PROPERTY);
		System.clearProperty(IntegrationTestConfig.TEST_WHAT_IS_CONFIGURED);
	}

	@Test
	void environmentNamesFollowSpringRelaxedBinding() {
		assertThat(IntegrationTestConfig.environmentNameCandidates("gebo.cluster.heimdall.url")).containsExactly(
				"gebo.cluster.heimdall.url", "gebo_cluster_heimdall_url", "GEBO.CLUSTER.HEIMDALL.URL",
				"GEBO_CLUSTER_HEIMDALL_URL");
		assertThat(IntegrationTestConfig.environmentNameCandidates("FullSetupSecret"))
				.containsExactly("FullSetupSecret", "FULLSETUPSECRET");
	}

	@Test
	void blankValuesCountAsMissing() {
		System.setProperty(PROPERTY, "   ");
		assertThat(IntegrationTestConfig.isSet(PROPERTY)).isFalse();
		assertThat(IntegrationTestConfig.get(PROPERTY, "fallback")).isEqualTo("fallback");
		System.setProperty(PROPERTY, "value");
		assertThat(IntegrationTestConfig.get(PROPERTY, "fallback")).isEqualTo("value");
	}

	@Test
	void placeholderDefaultsMayContainColons() {
		ResolvedText resolved = IntegrationTestConfig.resolvePlaceholders("model ${" + UNSET + ":qwen3:14b}");
		assertThat(resolved.value()).isEqualTo("model qwen3:14b");
		assertThat(resolved.unresolved()).isEmpty();
	}

	@Test
	void placeholdersWithoutDefaultAreReportedWhenUnset() {
		ResolvedText resolved = IntegrationTestConfig.resolvePlaceholders("image:${" + UNSET + "}");
		assertThat(resolved.unresolved()).containsExactly(UNSET);
		System.setProperty(PROPERTY, "1.0");
		assertThat(IntegrationTestConfig.resolvePlaceholders("image:${" + PROPERTY + "}").value()).isEqualTo("image:1.0");
	}

	@Test
	void testWhatIsConfiguredDefaultsToTrue() {
		assertThat(IntegrationTestConfig.testWhatIsConfigured()).isTrue();
		System.setProperty(IntegrationTestConfig.TEST_WHAT_IS_CONFIGURED, "FALSE");
		assertThat(IntegrationTestConfig.testWhatIsConfigured()).isFalse();
		System.setProperty(IntegrationTestConfig.TEST_WHAT_IS_CONFIGURED, "nonsense");
		assertThat(IntegrationTestConfig.testWhatIsConfigured()).isTrue();
	}

	@Test
	void howToSetOnlySuggestsExportableEnvironmentNames() {
		assertThat(IntegrationTestConfig.howToSet("gebo.cluster.heimdall.url")).isEqualTo(
				"set the environment variable GEBO_CLUSTER_HEIMDALL_URL or pass -Dgebo.cluster.heimdall.url=...");
		assertThat(IntegrationTestConfig.howToSet("FullSetupSecret")).isEqualTo(
				"set the environment variable FullSetupSecret (or FULLSETUPSECRET) or pass -DFullSetupSecret=...");
		assertThat(IntegrationTestConfig.howToSet("OPENAI_API_KEY"))
				.isEqualTo("set the environment variable OPENAI_API_KEY or pass -DOPENAI_API_KEY=...");
	}
}
