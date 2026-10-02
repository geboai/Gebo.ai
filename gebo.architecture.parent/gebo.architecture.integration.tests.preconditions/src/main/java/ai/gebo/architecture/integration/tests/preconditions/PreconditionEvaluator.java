package ai.gebo.architecture.integration.tests.preconditions;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

import ai.gebo.architecture.integration.tests.preconditions.IntegrationTestConfig.ResolvedText;

/**
 * Turns requirements into the list of what is missing, one human readable line
 * each. Probes of the outside world (Docker, images, endpoints) are cached for
 * the lifetime of the test JVM so suites sharing a requirement probe it once;
 * configuration lookups are cheap and always re-read.
 */
final class PreconditionEvaluator {

	private static final Map<String, List<String>> PROBE_CACHE = new ConcurrentHashMap<>();

	private PreconditionEvaluator() {
	}

	static List<String> missing(Collection<Requirement> requirements) {
		// Missing configuration values sharing a description were declared together
		// and are reported on one line, at the position of the first of them.
		List<Object> report = new ArrayList<>();
		Map<UnsetConfig, List<String>> unsetByGroup = new LinkedHashMap<>();
		boolean dockerUnavailable = false;
		for (Requirement requirement : requirements) {
			if (dockerUnavailable && requirement instanceof Requirement.LocalImage) {
				// The missing daemon is already reported; one line per image would be noise.
				continue;
			}
			if (requirement instanceof Requirement.Config config) {
				if (!IntegrationTestConfig.isSet(config.name())) {
					// Without a description nothing says the values belong together.
					UnsetConfig group = new UnsetConfig(config.description(),
							config.description().isBlank() ? config.name() : "");
					unsetByGroup.computeIfAbsent(group, key -> {
						report.add(key);
						return new ArrayList<>();
					}).add(config.name());
				}
				continue;
			}
			List<String> result = evaluate(requirement);
			if (requirement instanceof Requirement.Docker && !result.isEmpty()) {
				dockerUnavailable = true;
			}
			report.addAll(result);
		}
		List<String> missing = new ArrayList<>();
		for (Object entry : report) {
			missing.add(entry instanceof UnsetConfig unset ? unsetConfig(unsetByGroup.get(unset), unset.description())
					: (String) entry);
		}
		return missing;
	}

	private static List<String> evaluate(Requirement requirement) {
		return switch (requirement) {
		case Requirement.Docker docker -> PROBE_CACHE.computeIfAbsent("docker",
				key -> DockerProbe.unavailableReason().map(List::of).orElse(List.of()));
		case Requirement.Config config -> throw new IllegalStateException("configuration is evaluated in missing()");
		case Requirement.Endpoint endpoint -> endpoint(endpoint);
		case Requirement.LocalImage image -> localImage(image);
		case Requirement.Directories directories -> directories(directories);
		case Requirement.Custom custom -> custom(custom);
		};
	}

	private static String unsetConfig(List<String> names, String description) {
		String what = description.isBlank() ? "" : " (" + description + ")";
		if (names.size() == 1) {
			return "configuration " + names.get(0) + " is not set" + what + ": "
					+ IntegrationTestConfig.howToSet(names.get(0));
		}
		return "configuration " + String.join(", ", names) + " are not set" + what
				+ ": set them as environment variables or pass them as -D<name>=...";
	}

	/** Group key of unset configuration values; {@code name} is set only for undescribed ones. */
	private record UnsetConfig(String description, String name) {
	}

	private static List<String> endpoint(Requirement.Endpoint endpoint) {
		ResolvedText url = IntegrationTestConfig.resolvePlaceholders(endpoint.url());
		if (!url.unresolved().isEmpty()) {
			return unresolved(endpoint.name() + " URL " + endpoint.url(), url.unresolved());
		}
		return PROBE_CACHE.computeIfAbsent("endpoint|" + url.value() + "|" + endpoint.timeoutMillis(),
				key -> probeEndpoint(endpoint, url.value()));
	}

	private static List<String> probeEndpoint(Requirement.Endpoint endpoint, String url) {
		URI uri;
		try {
			uri = URI.create(url);
		} catch (IllegalArgumentException e) {
			return List.of(endpoint.name() + " URL " + url + " is not a valid URL");
		}
		if (uri.getHost() == null) {
			return List.of(endpoint.name() + " URL " + url + " has no host");
		}
		int port = uri.getPort() != -1 ? uri.getPort() : "https".equalsIgnoreCase(uri.getScheme()) ? 443 : 80;
		try (Socket socket = new Socket()) {
			socket.connect(new InetSocketAddress(uri.getHost(), port), endpoint.timeoutMillis());
			return List.of();
		} catch (IOException e) {
			List<String> settings = IntegrationTestConfig.placeholderNames(endpoint.url());
			return List.of(endpoint.name() + " is not reachable at " + url + " (nothing accepts connections on "
					+ uri.getHost() + ":" + port + "): start it"
					+ (settings.isEmpty() ? "" : ", or point " + String.join(" / ", settings) + " at a running instance"));
		}
	}

	private static List<String> localImage(Requirement.LocalImage image) {
		ResolvedText reference = IntegrationTestConfig.resolvePlaceholders(image.image());
		if (!reference.unresolved().isEmpty()) {
			return unresolved("Docker image " + image.image(), reference.unresolved());
		}
		return PROBE_CACHE.computeIfAbsent("image|" + reference.value(),
				key -> DockerProbe.missingImageReason(reference.value(), image.hint()).map(List::of).orElse(List.of()));
	}

	private static List<String> directories(Requirement.Directories directories) {
		String name = directories.configName();
		String value = IntegrationTestConfig.get(name);
		if (value == null) {
			return List.of("configuration " + name + " is not set (one or more directories separated by '"
					+ File.pathSeparator + "'): " + IntegrationTestConfig.howToSet(name));
		}
		List<String> missing = new ArrayList<>();
		StringTokenizer paths = new StringTokenizer(value, File.pathSeparator);
		if (!paths.hasMoreTokens()) {
			missing.add("configuration " + name + " lists no directory (value: '" + value + "')");
		}
		while (paths.hasMoreTokens()) {
			File directory = new File(paths.nextToken().trim());
			if (!directory.exists()) {
				missing.add("directory " + directory + " listed in " + name + " does not exist");
			} else if (!directory.isDirectory()) {
				missing.add(directory + " listed in " + name + " is not a directory");
			}
		}
		return missing;
	}

	private static List<String> custom(Requirement.Custom custom) {
		try {
			List<String> missing = custom.type().getDeclaredConstructor().newInstance().missing();
			return missing != null ? missing : List.of();
		} catch (ReflectiveOperationException | RuntimeException e) {
			return List.of("precondition " + custom.type().getName() + " could not be evaluated: " + e);
		}
	}

	private static List<String> unresolved(String what, List<String> names) {
		List<String> missing = new ArrayList<>();
		for (String name : names) {
			missing.add("configuration " + name + " is not set (needed to resolve " + what + "): "
					+ IntegrationTestConfig.howToSet(name));
		}
		return missing;
	}
}
