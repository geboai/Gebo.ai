package ai.gebo.architecture.integration.tests.model;

import ai.gebo.monolithic.api.client.model.SecurityHeaderData;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TestGeboSystemInfo {
	private final String host;
	private final int port;
	private final String username;
	private final String password;
	private final SecurityHeaderData securityHeader;
}
