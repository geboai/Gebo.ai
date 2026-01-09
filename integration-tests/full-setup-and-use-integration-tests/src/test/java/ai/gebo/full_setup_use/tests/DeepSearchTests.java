package ai.gebo.full_setup_use.tests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.env.Environment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import ai.gebo.architecture.integration.tests.AbstractVendorSetupAndUseTest;
import ai.gebo.architecture.integration.tests.model.TestGeboSystemInfo;
import ai.gebo.monolithic.app.Main;
import ai.gebo.ragsystem.vectorstores.services.GeboVectorStoreConfigurationService;

@SpringBootTest(classes = Main.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class DeepSearchTests extends AbstractVendorSetupAndUseTest {

	
	@Test
	public void runDeepSearchTest() throws JsonMappingException, JsonProcessingException {
		TestGeboSystemInfo systemInfo = executeSystemSetupBySecret();
	}
}
