package ai.gebo.full_setup_use.tests;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.DatabindException;

import ai.gebo.architecture.integration.tests.AbstractVendorSetupAndUseTest;
import ai.gebo.architecture.integration.tests.model.TestGeboSystemInfo;
import ai.gebo.monolithic.api.client.invoker.ApiClient;
import ai.gebo.monolithic.app.Main;
import ai.gebo.architecture.integration.tests.preconditions.RequiresConfig;

@SpringBootTest(classes = Main.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@RequiresConfig(value = AbstractVendorSetupAndUseTest.FULL_SETUP_ENVIRONMENT_JSON_STRING,
		description = "JSON with the admin account, the LLM vendor and its API key")
public class HistoryCoerencyTest extends AbstractVendorSetupAndUseTest {

	public void historyCoerencyTest() throws DatabindException, JacksonException, InterruptedException {
		TestGeboSystemInfo systemInfo = executeSystemSetupBySecret();
		ApiClient apiClient = createApiClient(systemInfo.getHost(), systemInfo.getPort(),
				systemInfo.getSecurityHeader());
		Thread.currentThread().sleep(30000);
	}

}
