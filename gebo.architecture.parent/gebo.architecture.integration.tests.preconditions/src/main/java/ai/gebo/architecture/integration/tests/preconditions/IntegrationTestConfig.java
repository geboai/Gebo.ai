package ai.gebo.architecture.integration.tests.preconditions;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Resolves integration test configuration values, used both by the
 * precondition checks and by the tests reading those values, so the two can
 * never disagree about whether something is configured.
 * <p>
 * A value is looked up as a JVM system property first, then as an environment
 * variable. Environment names are matched with the relaxed rules of Spring's
 * {@code SystemEnvironmentPropertySource} ({@code .} and {@code -} become
 * {@code _}, then the upper case form), so whatever a Spring
 * {@code Environment} would pick up from the process environment is seen here
 * too. Property files are not consulted: preconditions are evaluated before any
 * Spring context exists. Blank values count as missing.
 */
public final class IntegrationTestConfig {

	/**
	 * Switch between the two ways of handling unmet preconditions. When
	 * {@code true} (the default) a test whose preconditions are not met is
	 * skipped with a warning; when {@code false} it fails, listing what is
	 * missing.
	 */
	public static final String TEST_WHAT_IS_CONFIGURED = "testWhatIsConfigured";

	private static final Logger LOGGER = LoggerFactory.getLogger(IntegrationTestConfig.class);
	private static final Pattern PLACEHOLDER = Pattern.compile("\\$\\{([^}:]+)(?::([^}]*))?}");
	private static final Pattern ENVIRONMENT_IDENTIFIER = Pattern.compile("[A-Za-z_][A-Za-z0-9_]*");

	private IntegrationTestConfig() {
	}

	/**
	 * @return the configured value, or {@code null} when neither a system
	 *         property nor an environment variable provides a non blank one
	 */
	public static String get(String name) {
		String value = System.getProperty(name);
		if (isBlank(value)) {
			value = null;
			for (String candidate : environmentNameCandidates(name)) {
				String fromEnvironment = System.getenv(candidate);
				if (!isBlank(fromEnvironment)) {
					value = fromEnvironment;
					break;
				}
			}
		}
		return value;
	}

	/**
	 * @return the configured value, or {@code defaultValue} when it is not set
	 */
	public static String get(String name, String defaultValue) {
		String value = get(name);
		return value != null ? value : defaultValue;
	}

	public static boolean isSet(String name) {
		return get(name) != null;
	}

	/**
	 * @return {@code true} (the default) when unmet preconditions skip the test,
	 *         {@code false} when they fail it
	 */
	public static boolean testWhatIsConfigured() {
		String value = get(TEST_WHAT_IS_CONFIGURED);
		if (value == null) {
			value = get("TEST_WHAT_IS_CONFIGURED");
		}
		if (value == null || value.trim().equalsIgnoreCase("true")) {
			return true;
		}
		if (value.trim().equalsIgnoreCase("false")) {
			return false;
		}
		LOGGER.warn("Ignoring {}={}: expected true or false, keeping the default (true, unmet preconditions skip)",
				TEST_WHAT_IS_CONFIGURED, value);
		return true;
	}

	/**
	 * Environment variable names tried for {@code name}, in the order Spring's
	 * {@code SystemEnvironmentPropertySource} tries them.
	 */
	static List<String> environmentNameCandidates(String name) {
		Set<String> candidates = new LinkedHashSet<>();
		addRelaxedVariants(candidates, name);
		addRelaxedVariants(candidates, name.toUpperCase(Locale.ROOT));
		return new ArrayList<>(candidates);
	}

	private static void addRelaxedVariants(Set<String> candidates, String name) {
		String noDot = name.replace('.', '_');
		candidates.add(name);
		candidates.add(noDot);
		candidates.add(name.replace('-', '_'));
		candidates.add(noDot.replace('-', '_'));
	}

	/**
	 * Replaces {@code ${name}} and {@code ${name:default}} placeholders. The
	 * default runs up to the closing brace, so it may itself contain colons
	 * ({@code ${OLLAMA_CHAT_MODEL:qwen3:14b}}).
	 */
	static ResolvedText resolvePlaceholders(String text) {
		Matcher matcher = PLACEHOLDER.matcher(text);
		StringBuilder resolved = new StringBuilder();
		List<String> unresolved = new ArrayList<>();
		while (matcher.find()) {
			String name = matcher.group(1).trim();
			String value = get(name, matcher.group(2));
			if (value == null) {
				unresolved.add(name);
				value = matcher.group(0);
			}
			matcher.appendReplacement(resolved, Matcher.quoteReplacement(value));
		}
		matcher.appendTail(resolved);
		return new ResolvedText(resolved.toString(), unresolved);
	}

	/**
	 * @return the placeholder names appearing in {@code text}, used to tell the
	 *         reader which setting controls a value
	 */
	static List<String> placeholderNames(String text) {
		Matcher matcher = PLACEHOLDER.matcher(text);
		List<String> names = new ArrayList<>();
		while (matcher.find()) {
			names.add(matcher.group(1).trim());
		}
		return names;
	}

	/**
	 * How to provide {@code name}, worded for the messages of unmet
	 * preconditions.
	 */
	static String howToSet(String name) {
		// Only suggest names a shell can actually export: the exact one when it is
		// a valid identifier, plus the fully relaxed upper case form.
		Set<String> suggested = new LinkedHashSet<>();
		if (ENVIRONMENT_IDENTIFIER.matcher(name).matches()) {
			suggested.add(name);
		}
		suggested.add(name.replace('.', '_').replace('-', '_').toUpperCase(Locale.ROOT));
		return "set the environment variable " + String.join(" (or ", suggested) + (suggested.size() > 1 ? ")" : "")
				+ " or pass -D" + name + "=...";
	}

	private static boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}

	record ResolvedText(String value, List<String> unresolved) {
	}
}
