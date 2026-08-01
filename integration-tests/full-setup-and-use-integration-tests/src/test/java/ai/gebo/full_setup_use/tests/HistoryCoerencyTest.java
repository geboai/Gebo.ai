package ai.gebo.full_setup_use.tests;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.DatabindException;

import ai.gebo.architecture.integration.tests.AbstractVendorSetupAndUseTest;
import ai.gebo.architecture.integration.tests.model.TestGeboSystemInfo;
import ai.gebo.monolithic.api.client.invoker.ApiClient;
import ai.gebo.monolithic.app.Main;

@SpringBootTest(classes = Main.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class HistoryCoerencyTest extends AbstractVendorSetupAndUseTest {

	public void historyCoerencyTest() throws DatabindException, JacksonException, InterruptedException {
		TestGeboSystemInfo systemInfo = executeSystemSetupBySecret();
		ApiClient apiClient = createApiClient(systemInfo.getHost(), systemInfo.getPort(),
				systemInfo.getSecurityHeader());
		Thread.currentThread().sleep(30000);
	}

}
