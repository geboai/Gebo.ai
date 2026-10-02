package ai.gebo.architecture.integration.tests.preconditions;

import java.util.Optional;

import org.testcontainers.DockerClientFactory;

import com.github.dockerjava.api.exception.NotFoundException;

/**
 * Asks Testcontainers about the Docker daemon, so the check honours the same
 * configuration (DOCKER_HOST, ~/.testcontainers.properties, rootless sockets)
 * the containers will be started with. Testcontainers is only referenced from
 * the nested class, which is loaded when a Docker requirement is evaluated;
 * suites without it on the classpath get a message instead of a linkage error.
 */
final class DockerProbe {

	private DockerProbe() {
	}

	static Optional<String> unavailableReason() {
		try {
			return Testcontainers.dockerAvailable() ? Optional.empty()
					: Optional.of("Docker is not available: Testcontainers could not reach a Docker daemon"
							+ " (start Docker, or check DOCKER_HOST / ~/.testcontainers.properties)");
		} catch (LinkageError e) {
			return Optional.of("Docker cannot be checked: Testcontainers is not on the test classpath");
		}
	}

	static Optional<String> missingImageReason(String image, String hint) {
		try {
			return Testcontainers.hasLocalImage(image) ? Optional.empty()
					: Optional.of("Docker image " + image + " is not present locally"
							+ (hint.isBlank() ? "" : ": " + hint));
		} catch (LinkageError e) {
			return Optional.of("Docker image " + image + " cannot be checked: Testcontainers is not on the test classpath");
		} catch (RuntimeException e) {
			return Optional.of("Docker image " + image + " could not be inspected: " + e.getMessage());
		}
	}

	private static final class Testcontainers {

		static boolean dockerAvailable() {
			return DockerClientFactory.instance().isDockerAvailable();
		}

		static boolean hasLocalImage(String image) {
			try {
				DockerClientFactory.instance().client().inspectImageCmd(image).exec();
				return true;
			} catch (NotFoundException e) {
				return false;
			}
		}
	}
}
