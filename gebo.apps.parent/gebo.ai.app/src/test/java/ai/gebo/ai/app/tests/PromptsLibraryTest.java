package ai.gebo.ai.app.tests;

import org.springframework.beans.factory.annotation.Autowired;

import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.model.GPromptConfig;
import ai.gebo.llms.chat.abstraction.layer.services.IGPromptConfigDao;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
public class PromptsLibraryTest extends AbstractBaseTestLLmsIntegrationTests {
	@Autowired IGPromptConfigDao promptsDao;
	@Test
	public void testLibraryConsistency() {
		for(String promptUse:GeboPromptsLibrary.ALL_PROMPT_CODES) {
			LOGGER.info("Check if prompt with use:"+promptUse+" exists");
			GPromptConfig prompt = promptsDao.findByPromptUse(promptUse);
			assertNotNull(prompt, "The prompt cannot be null!");
			LOGGER.info("OK!! prompt with use:"+promptUse+" exists!!");
		}
	}
	

}
