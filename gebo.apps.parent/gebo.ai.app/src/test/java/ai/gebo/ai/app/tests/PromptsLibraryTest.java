package ai.gebo.ai.app.tests;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.model.GPromptUseInfo;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGPromptUseInfoDao;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
public class PromptsLibraryTest extends AbstractBaseTestLLmsIntegrationTests {
	@Autowired IGPromptConfigDao promptsDao;
	@Autowired IGPromptUseInfoDao promptUseInfoDao;
	@Test
	public void testLibraryConsistency() {
		for(String promptUse:GeboPromptsLibrary.ALL_PROMPT_CODES) {
			LOGGER.info("Check if prompt with use:"+promptUse+" exists");
			GPromptTemplateConfig prompt = promptsDao.findByPromptUse(promptUse);
			assertNotNull(prompt, "The prompt cannot be null!");
			LOGGER.info("OK!! prompt with use:"+promptUse+" exists!!");
		}
	}

	/**
	 * Every promptUse code declared by any static prompts provider (across every
	 * module) must also have a matching {@link GPromptUseInfo} catalog entry, so the
	 * admin-facing catalog never silently drifts out of sync with the templates it
	 * documents.
	 */
	@Test
	public void testUseInfoCatalogCompleteness() {
		Set<String> declaredPromptUses = new LinkedHashSet<>();
		for (GPromptTemplateConfig config : promptsDao.getConfigurations()) {
			declaredPromptUses.add(config.getPromptUse());
		}
		for (String promptUse : declaredPromptUses) {
			LOGGER.info("Check if a GPromptUseInfo catalog entry exists for promptUse:" + promptUse);
			GPromptUseInfo info = promptUseInfoDao.findByCode(promptUse);
			assertNotNull(info, "Missing GPromptUseInfo catalog entry for promptUse:" + promptUse);
			LOGGER.info("OK!! GPromptUseInfo catalog entry for promptUse:" + promptUse + " exists!!");
		}
	}

}
