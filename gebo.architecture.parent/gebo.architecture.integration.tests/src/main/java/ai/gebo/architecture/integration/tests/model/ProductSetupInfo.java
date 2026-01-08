package ai.gebo.architecture.integration.tests.model;

import ai.gebo.monolithic.api.client.model.GeboGoogleJsonSecretContent;
import ai.gebo.monolithic.api.client.model.GeboOauth2SecretContent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductSetupInfo {
	public static enum Product {
		GOOGLE_SEARCH, CONFLUENCE_ONPREMISE, CONFLUENCE_CLOUD, SHAREPOINT, GOOGLE_WORKSPACE, JIRA_CLOUD
	};

	@NotNull
	Product product = null;
	String user = null;
	String id = null;
	@NotNull
	String apiKey = null;
	String basePath = null;
	GeboOauth2SecretContent oauth2Credentials = null;
	GeboGoogleJsonSecretContent googleJsonCredentials = null;
}
