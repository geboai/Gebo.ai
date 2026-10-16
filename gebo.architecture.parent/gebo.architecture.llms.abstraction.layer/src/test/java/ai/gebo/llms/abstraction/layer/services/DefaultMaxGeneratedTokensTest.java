package ai.gebo.llms.abstraction.layer.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;

class DefaultMaxGeneratedTokensTest {

	@Test
	void theDefaultIsAShareOfTheContextWindowBySteps() {
		assertNull(GAbstractConfigurableChatModel.defaultMaxGeneratedTokens(null));
		assertEquals(4096, GAbstractConfigurableChatModel.defaultMaxGeneratedTokens(8192));
		assertEquals(8192, GAbstractConfigurableChatModel.defaultMaxGeneratedTokens(32000));
		assertEquals(8192, GAbstractConfigurableChatModel.defaultMaxGeneratedTokens(120000));
		assertEquals(10240, GAbstractConfigurableChatModel.defaultMaxGeneratedTokens(1000000));
	}

	@Test
	void aConfiguredValueWinsElseTheModelsContextWindowDecides() {
		GBaseChatModelConfig<GBaseChatModelChoice> config = new GBaseChatModelConfig<>();
		GBaseChatModelChoice choice = new GBaseChatModelChoice();
		ModelMetaInfo meta = new ModelMetaInfo();
		meta.setContextLength(1000000);
		choice.setMetaInfos(meta);
		config.setChoosedModel(choice);
		assertEquals(10240, GAbstractConfigurableChatModel.maxGeneratedTokensOf(config));

		config.setContextLength(64000);
		assertEquals(8192, GAbstractConfigurableChatModel.maxGeneratedTokensOf(config));

		config.setMaxGeneratedTokens(32000);
		assertEquals(32000, GAbstractConfigurableChatModel.maxGeneratedTokensOf(config));

		assertNull(GAbstractConfigurableChatModel.maxGeneratedTokensOf(new GBaseChatModelConfig<>()));
	}

	@Test
	void theBudgetsReserveWhatTheRequestsReserveForTheGeneration() {
		GBaseChatModelConfig<GBaseChatModelChoice> config = new GBaseChatModelConfig<>();
		config.setContextLength(1000000);
		// no configured cap: the default of the window is reserved, as the requests send it
		assertEquals(1000000 - 10240, GAbstractConfigurableChatModel.usableContextWindow(1000000, config));
		config.setMaxGeneratedTokens(32000);
		assertEquals(1000000 - 32000, GAbstractConfigurableChatModel.usableContextWindow(1000000, config));
		// a cap not smaller than the window leaves the window as it is
		config.setMaxGeneratedTokens(2000000);
		assertEquals(1000000, GAbstractConfigurableChatModel.usableContextWindow(1000000, config));
		// no window known: nothing to size
		assertNull(GAbstractConfigurableChatModel.usableContextWindow(null, config));
		assertNull(GAbstractConfigurableChatModel.usableContextWindow(0, new GBaseChatModelConfig<>()));
	}
}
