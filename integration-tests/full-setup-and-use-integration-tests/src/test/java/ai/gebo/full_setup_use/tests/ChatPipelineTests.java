package ai.gebo.full_setup_use.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import ai.gebo.architecture.integration.tests.AbstractVendorSetupAndUseTest;
import ai.gebo.architecture.integration.tests.model.TestGeboSystemInfo;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.monolithic.api.client.api.GeboChatControllerApi;
import ai.gebo.monolithic.api.client.api.GeboChatPipelinesControllerApi;
import ai.gebo.monolithic.api.client.api.GeboChatProfileLookupControllerApi;
import ai.gebo.monolithic.api.client.api.GeboRagChatControllerApi;
import ai.gebo.monolithic.api.client.api.GeboUserChatsControllerApi;
import ai.gebo.monolithic.api.client.invoker.ApiClient;
import ai.gebo.monolithic.api.client.model.DataPage;
import ai.gebo.monolithic.api.client.model.GChatProfileConfiguration;
import ai.gebo.monolithic.api.client.model.GUserChatInfo;
import ai.gebo.monolithic.api.client.model.GeboChatRequest;
import ai.gebo.monolithic.api.client.model.GeboChatResponse;
import ai.gebo.monolithic.api.client.model.PageGLookupEntry;
import ai.gebo.monolithic.app.Main;

@SpringBootTest(classes = Main.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class ChatPipelineTests extends AbstractVendorSetupAndUseTest {
	@Autowired
	IGRuntimeBinder runtimeBinder;

	@Test
	public void runChatPipelineTest() throws IOException, InterruptedException {
		TestGeboSystemInfo systemInfo = executeSystemSetupBySecret();
		ApiClient apiClient = createApiClient(systemInfo.getHost(), systemInfo.getPort(),
				systemInfo.getSecurityHeader());
		Thread.currentThread().sleep(30000);
		renew(apiClient);
		// looking up chat profiles
		DataPage page = new DataPage();
		page.setPage(0);
		page.setPageSize(10);
		page.setNumrecords(0);
		page.setSort(List.of());

		GeboRagChatControllerApi ragChatControllerApi = new GeboRagChatControllerApi(apiClient);
		List<GChatProfileConfiguration> profiles = ragChatControllerApi.getChatProfiles();

		GeboUserChatsControllerApi userChatControllerApi = new GeboUserChatsControllerApi(apiClient);
		GUserChatInfo chatInfo = userChatControllerApi.createCleanChatByChatProfileCode(profiles.get(0).getCode());
		renew(apiClient);
		GeboChatPipelinesControllerApi chatPipelineControllerApi = new GeboChatPipelinesControllerApi(apiClient);
		List<GeboChatRequest> requests = this.loadChatRequests("/chats-playbook/chat-playbook.json");
		for (GeboChatRequest request : requests) {
			renew(apiClient);
			request.setUserChatContextCode(chatInfo.getCode());
			long time = System.currentTimeMillis();
			LOGGER.info("Running request: " + request.getQuery());
			GeboChatResponse response = chatPipelineControllerApi.executeDefaultChatPipeline(request);
			LOGGER.info("Response received in:" + (System.currentTimeMillis() - time) + " ms");
			assertNotNull(response, "The response cannot be null");
			assertNotNull(response.getQueryResponse(), "The response content cannot be null");
			LOGGER.info("Routing decision: " + response.getPipelineRouterDecisionCode());
			LOGGER.info("Response: " + response.getQueryResponse());
		}

	}

}
