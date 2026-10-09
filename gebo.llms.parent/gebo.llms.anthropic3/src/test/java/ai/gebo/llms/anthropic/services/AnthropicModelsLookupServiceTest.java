package ai.gebo.llms.anthropic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.anthropic.client.AnthropicClient;
import com.anthropic.core.AutoPager;
import com.anthropic.core.JsonValue;
import com.anthropic.core.http.Headers;
import com.anthropic.errors.UnauthorizedException;
import com.anthropic.models.beta.models.BetaModelInfo;
import com.anthropic.models.beta.models.ModelListParams;

import ai.gebo.llms.abstraction.layer.services.IGModelChoiceMetaInfoEnricherService;
import ai.gebo.llms.anthropic.model.GAnthropicChatModelChoice;
import ai.gebo.llms.anthropic.model.GAnthropicChatModelConfig;
import ai.gebo.model.GUserMessage.MsgServerity;
import ai.gebo.model.OperationStatus;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;

class AnthropicModelsLookupServiceTest {

	IGeboSecretsAccessService secrets;
	AnthropicClient client;
	AtomicInteger clientsCreated;
	AnthropicModelsLookupService service;

	@BeforeEach
	void setUp() throws Exception {
		secrets = mock(IGeboSecretsAccessService.class);
		GeboTokenContent token = new GeboTokenContent();
		token.setToken("sk-ant-test");
		when(secrets.getSecretContentById("anthropic-key")).thenReturn(token);
		client = mock(AnthropicClient.class, RETURNS_DEEP_STUBS);
		clientsCreated = new AtomicInteger();
		service = new AnthropicModelsLookupService(mock(IGModelChoiceMetaInfoEnricherService.class), secrets, null,
				null) {
			@Override
			protected AnthropicClient newClient(String apiKey, String baseUrl) {
				assertEquals("sk-ant-test", apiKey);
				clientsCreated.incrementAndGet();
				return client;
			}
		};
	}

	static GAnthropicChatModelConfig configWithKey() {
		GAnthropicChatModelConfig config = new GAnthropicChatModelConfig();
		config.setApiSecretCode("anthropic-key");
		return config;
	}

	@SuppressWarnings("unchecked")
	void apiLists(BetaModelInfo... models) {
		AutoPager<BetaModelInfo> pager = mock(AutoPager.class);
		when(pager.iterator()).thenAnswer(invocation -> List.of(models).iterator());
		when(client.beta().models().list(any(ModelListParams.class)).autoPager()).thenReturn(pager);
	}

	static BetaModelInfo opus() throws Exception {
		return AnthropicModelInfoMapperTest.parse(AnthropicModelInfoMapperTest.model("claude-opus-5-5",
				"Claude Opus 5.5", "active", null,
				AnthropicModelInfoMapperTest.capabilities(true, false, true, true, true, true)));
	}

	@Test
	void withoutAKeyTheCurrentLineupIsOfferedWithAWarning() {
		OperationStatus<List<GAnthropicChatModelChoice>> status = service.getChatModels(new GAnthropicChatModelConfig());

		assertFalse(status.isHasErrorMessages());
		assertEquals(MsgServerity.warn, status.getMessages().get(0).getSeverity());
		assertEquals(AnthropicModelsLookupService.NO_KEY_MODELS,
				status.getResult().stream().map(GAnthropicChatModelChoice::getCode).toList());
		assertEquals(0, clientsCreated.get());
	}

	@Test
	void withAKeyTheModelsComeFromTheApi() throws Exception {
		apiLists(opus());

		OperationStatus<List<GAnthropicChatModelChoice>> status = service.getChatModels(configWithKey());

		assertFalse(status.isHasErrorMessages());
		assertEquals(1, status.getResult().size());
		assertEquals("Claude Opus 5.5", status.getResult().get(0).getDescription());
		assertEquals(1000000, status.getResult().get(0).getContextLength());
	}

	@Test
	void aRefusedKeyIsAnErrorNotAFallback() {
		UnauthorizedException refused = UnauthorizedException.builder().headers(Headers.builder().build())
				.body(JsonValue.from(java.util.Map.of("error", "invalid x-api-key"))).build();
		when(client.beta().models().list(any(ModelListParams.class))).thenThrow(refused);

		OperationStatus<List<GAnthropicChatModelChoice>> status = service.getChatModels(configWithKey());

		assertTrue(status.isHasErrorMessages());
		assertNull(status.getResult());
	}

	@Test
	void theListFillsTheThinkingSupportCache() throws Exception {
		apiLists(opus());
		service.getChatModels(configWithKey());

		assertNotNull(service.getThinkingSupport("sk-ant-test", null, "claude-opus-5-5"));
		verify(client.beta().models(), never()).retrieve(anyString());
	}

	@Test
	void anUnknownModelIsRetrievedOnceAndAFailureIsRemembered() throws Exception {
		BetaModelInfo opus = opus();
		when(client.beta().models().retrieve("claude-opus-5-5")).thenReturn(opus);
		when(client.beta().models().retrieve("claude-gone")).thenThrow(new RuntimeException("not found"));

		assertTrue(service.getThinkingSupport("sk-ant-test", null, "claude-opus-5-5").adaptive());
		assertTrue(service.getThinkingSupport("sk-ant-test", null, "claude-opus-5-5").adaptive());
		assertNull(service.getThinkingSupport("sk-ant-test", null, "claude-gone"));
		assertNull(service.getThinkingSupport("sk-ant-test", null, "claude-gone"));

		verify(client.beta().models(), times(1)).retrieve("claude-opus-5-5");
		verify(client.beta().models(), times(1)).retrieve("claude-gone");
	}
}
