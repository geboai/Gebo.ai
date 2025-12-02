package ai.gebo.monolithic.api.client.connectivity.test;

import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.monolithic.api.client.api.AuthControllerApi;
import ai.gebo.monolithic.api.client.api.ChatModelsControllerApi;
import ai.gebo.monolithic.api.client.api.GeboAdminChatProfilesConfigurationControllerApi;
import ai.gebo.monolithic.api.client.invoker.ApiClient;
import ai.gebo.monolithic.api.client.model.GChatModelType;
import ai.gebo.monolithic.api.client.model.LoginRequest;
import ai.gebo.monolithic.api.client.model.OperationStatusAuthResponse;

public class ApiWorkingTest {
	static final Logger LOGGER = LoggerFactory.getLogger(ApiWorkingTest.class);
	
	//@Test
	public void testConnectivity() {
		ApiClient apiClient = new ApiClient();
		AuthControllerApi controllerApi = new AuthControllerApi(apiClient);

		LoginRequest login = new LoginRequest();
		login.setUsername("put here my user name");
		login.setPassword("put here my password");
		OperationStatusAuthResponse result = controllerApi.authenticateUser(login);
		result.getMessages().forEach(x -> {
			LOGGER.info(x.getSummary() + " - " + x.getDetail());
		});
		assertFalse("Login cannot be with errors", result.isHasErrorMessages());
		String token = result.getResult().getSecurityHeaderData().getToken();
		ApiClient authApiClient = new ApiClient();
		authApiClient.setApiKey(token);
		authApiClient.setApiKeyPrefix("Bearer");
		ChatModelsControllerApi chatModelsControllerApi = new ChatModelsControllerApi(authApiClient);
		List<GChatModelType> models = chatModelsControllerApi.getChatModelTypes();
		for (GChatModelType gChatModelType : models) {
			LOGGER.info("Chat model type:" + gChatModelType.getCode());
		}
	}

}
