package ai.gebo.architecture.opensearch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(value = "ai.gebo.opensearch")
@Data
public class OpenSearchConfig {
	private boolean enabled = false;

	public static enum Protocol {
		http, https
	}

	private Protocol protocol = Protocol.http;
	private String host = "localhost";
	private int port = 9200;
	private String username = null;
	private String password = null;

	/**
	 * When true (the default), the OpenSearch transport uses
	 * {@code NoopHostnameVerifier.INSTANCE}, i.e. it skips TLS hostname (SAN)
	 * verification. This is what lets the client reach an OpenSearch reached by a
	 * service name (e.g. {@code https://opensearch:9200} in docker-compose) whose
	 * bundled demo certificate has no matching subject-alternative-name - without
	 * it the handshake fails with "No subject alternative DNS name matching
	 * opensearch found". Set to false in production, where OpenSearch presents a
	 * certificate whose SAN actually matches the configured host.
	 */
	private boolean noopHostnameVerifier = true;

}