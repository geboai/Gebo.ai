package ai.gebo.llms.chat.abstraction.layer.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;

import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableRankerModel;
import ai.gebo.llms.abstraction.layer.services.IGRankerModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboRagSearchConfig;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.ranker.model.RankerModel;
import ai.gebo.ranker.model.RankingInput;
import ai.gebo.ranker.model.RankingOutput;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;

/**
 * Pins the two relevance stages of the ranker service: rank(...) only orders with the
 * ranker model, rankAndRemoveIrrelevant(...) also submits the ranked fragments to the
 * irrelevance filter.
 */
class GRankerServiceImplTest {

	private IGRankerModelRuntimeConfigurationDao rankerModelDao;
	private IGChatModelRuntimeConfigurationDao chatModelsDao;
	private IGPromptConfigDao promptsDao;
	private RankerModel rankerModel;
	private GRankerServiceImpl service;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@BeforeEach
	void rankerReversingTheFragments() {
		rankerModelDao = mock(IGRankerModelRuntimeConfigurationDao.class);
		chatModelsDao = mock(IGChatModelRuntimeConfigurationDao.class);
		promptsDao = mock(IGPromptConfigDao.class);
		rankerModel = mock(RankerModel.class);
		when(rankerModel.call(any(RankingInput.class))).thenAnswer(invocation -> {
			List<Document> input = new ArrayList<>(((RankingInput) invocation.getArgument(0)).getDocuments());
			java.util.Collections.reverse(input);
			List<RankingOutput.RankingItem> ranked = new ArrayList<>();
			for (Document document : input) {
				ranked.add(new RankingOutput.RankingItem(document, 1.0d));
			}
			return new RankingOutput(ranked);
		});
		IGConfigurableRankerModel configured = mock(IGConfigurableRankerModel.class);
		when(configured.getRankerModel()).thenReturn(rankerModel);
		when(configured.getCode()).thenReturn("test-ranker");
		when(rankerModelDao.defaultHandler()).thenReturn(configured);
		IGSecurityAuditLoggerService audit = mock(IGSecurityAuditLoggerService.class);
		when(audit.newSecurityEvent()).thenAnswer(invocation -> {
			SecurityEvent event = mock(SecurityEvent.class);
			Map<String, Object> details = new HashMap<>();
			when(event.getDetails()).thenReturn(details);
			return event;
		});
		// the irrelevance filter enabled, from 5 fragments on (the defaults)
		GeboRagSearchConfig ragSearchConfig = new GeboRagSearchConfig(null, null, null, null, null, null, null, null,
				null);
		service = new GRankerServiceImpl(rankerModelDao, chatModelsDao, promptsDao, ragSearchConfig, audit);
	}

	private static List<Document> fragments(int count) {
		List<Document> fragments = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			fragments.add(Document.builder().id("f" + i).text("fragment " + i)
					.metadata(Map.of(DocumentMetaInfos.CONTENT_CODE, "doc-" + i)).build());
		}
		return fragments;
	}

	private static List<String> ids(List<Document> documents) {
		return documents.stream().map(Document::getId).toList();
	}

	@Test
	void rankOnlyOrdersWithTheRankerModel() throws Exception {
		List<Document> ranked = service.rank(fragments(6), "query", 6);

		assertEquals(List.of("f5", "f4", "f3", "f2", "f1", "f0"), ids(ranked));
		verify(rankerModel, times(1)).call(any(RankingInput.class));
		verify(chatModelsDao, never()).findByUsesOrGetDefault(any(ChatModelsUses[].class));
		verify(promptsDao, never()).findByPromptUse(any());
	}

	@Test
	void rankOnADocumentsSetOnlyOrdersWithTheRankerModel() throws Exception {
		AIDocumentsSet ranked = service.rank(AIDocumentsSet.from(fragments(6)), "query", 6);

		assertEquals(6, ranked.countFragments());
		verify(rankerModel, times(1)).call(any(RankingInput.class));
		verify(chatModelsDao, never()).findByUsesOrGetDefault(any(ChatModelsUses[].class));
	}

	@Test
	void rankAndRemoveIrrelevantAlsoRunsTheIrrelevanceFilter() throws Exception {
		// no internal services model: the filter fails open, the ranked list is kept
		List<Document> ranked = service.rankAndRemoveIrrelevant(fragments(6), "query", 6);

		assertEquals(List.of("f5", "f4", "f3", "f2", "f1", "f0"), ids(ranked));
		verify(chatModelsDao, times(1)).findByUsesOrGetDefault(any(ChatModelsUses[].class));

		service.rankAndRemoveIrrelevant(AIDocumentsSet.from(fragments(6)), "query", 6);
		verify(chatModelsDao, times(2)).findByUsesOrGetDefault(any(ChatModelsUses[].class));
	}

	@Test
	void nothingToRankIsReturnedAsItIs() throws Exception {
		List<Document> none = List.of();

		assertSame(none, service.rank(none, "query", 6));
		assertSame(none, service.rankAndRemoveIrrelevant(none, "query", 6));
		verify(rankerModel, never()).call(any(RankingInput.class));
	}

	@Test
	void noRankerModelFailsEveryEntryPoint() {
		when(rankerModelDao.defaultHandler()).thenReturn(null);

		assertThrows(LLMConfigException.class, () -> service.rank(fragments(2), "query", 2));
		assertThrows(LLMConfigException.class, () -> service.rankAndRemoveIrrelevant(fragments(2), "query", 2));
	}
}
