package ai.gebo.llms.abstraction.layer.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.models.metainfos.IGModelsLibraryDao;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;

class GModelChoiceMetaInfoEnricherServiceImplTest {

	ModelMetaInfo library;
	GModelChoiceMetaInfoEnricherServiceImpl enricher;

	@BeforeEach
	void setUp() {
		library = new ModelMetaInfo();
		library.setProviderId("acme");
		library.setModelId("acme-1");
		library.setDescription("Acme one, from the library");
		library.setContextLength(64000);
		library.setMaxOutputToken(8000);
		library.setSupportsFunctionCalls(true);
		library.setSupportsStructuredOutput(true);
		library.setInformativeUrl("https://acme.example/models");
		enricher = new GModelChoiceMetaInfoEnricherServiceImpl();
		enricher.dao = new IGModelsLibraryDao() {
			@Override
			public ModelMetaInfo findByModelId(String modelId) {
				return "acme-1".equals(modelId) ? library : null;
			}

			@Override
			public ModelMetaInfo findByProviderIdAndModelId(String providerId, String modelId) {
				return "acme".equals(providerId) ? findByModelId(modelId) : null;
			}
		};
	}

	static GBaseChatModelChoice choice(String code) {
		GBaseChatModelChoice choice = new GBaseChatModelChoice();
		choice.setCode(code);
		choice.setDescription(code);
		return choice;
	}

	@Test
	void theLibraryFillsAChoiceWithoutMetaInfosIncludingStructuredOutput() {
		GBaseChatModelChoice choice = choice("acme-1");

		enricher.enrichChatModelMetaInfos("acme", choice, c -> new ModelMetaInfo());

		assertEquals("Acme one, from the library", choice.getDescription());
		assertEquals(64000, choice.getContextLength());
		assertTrue(choice.getSupportsFunctionCalls());
		assertTrue(choice.getSupportsStructuredOutput());
		// The choice gets a copy: enriching it never edits the shared library entry
		assertNotSame(library, choice.getMetaInfos());
	}

	@Test
	void liveValuesWinAndTheLibraryOnlyFillsTheGaps() {
		GBaseChatModelChoice choice = choice("acme-1");
		choice.setDescription("Acme One (live)");
		ModelMetaInfo live = new ModelMetaInfo();
		live.setContextLength(1000000);
		live.setSupportsStructuredOutput(false);
		choice.setMetaInfos(live);

		enricher.enrichChatModelMetaInfos("acme", choice, c -> new ModelMetaInfo());

		assertEquals("Acme One (live)", choice.getDescription());
		assertEquals(1000000, choice.getContextLength());
		assertEquals(false, choice.getSupportsStructuredOutput());
		assertEquals(8000, choice.getMetaInfos().getMaxOutputToken());
		assertTrue(choice.getSupportsFunctionCalls());
		assertEquals("https://acme.example/models", choice.getInformativeUrl());
		assertEquals(64000, library.getContextLength());
	}

	@Test
	void theDefaultFactoryCoversAModelTheLibraryDoesNotKnow() {
		GBaseChatModelChoice choice = choice("other-2");

		enricher.enrichChatModelMetaInfos("acme", choice, c -> {
			ModelMetaInfo meta = new ModelMetaInfo();
			meta.setInformativeUrl("https://acme.example/other");
			return meta;
		});

		assertEquals("other-2", choice.getDescription());
		assertEquals("https://acme.example/other", choice.getInformativeUrl());
	}
}
