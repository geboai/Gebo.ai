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

}