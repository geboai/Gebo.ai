/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import ai.gebo.core.messages.LLMCallOutcome;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.abstraction.layer.services.ILLMSUsageCrudService;
import ai.gebo.model.ModelType;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.SecurityAuditTaxonomy;

/**
 * Every chat call accounted by the usage recorder leaves one llmInvokeChat event in
 * the security audit log, with metadata only; the other model types leave none, and a
 * failing audit never fails the accounting.
 */
public class LLMUsageRecorderAuditTest {

	private static SecurityEvent eventWithUser(String userId) {
		return new SecurityEvent(null, userId, "203.0.113.7", "test", "corr", "2026-01-01T00:00:00Z", "stack", "POST",
				"/api/users/chat");
	}

	private static GBaseModelConfig chatConfig() {
		GBaseModelConfig config = mock(GBaseModelConfig.class);
		when(config.getCode()).thenReturn("chat-config-001");
		when(config.getModelTypeCode()).thenReturn("chat-test-model");
		return config;
	}

	@Test
	public void aChatCallIsAuditedWithMetadataOnly() {
		ILLMSUsageCrudService crud = mock(ILLMSUsageCrudService.class);
		IGSecurityAuditLoggerService audit = mock(IGSecurityAuditLoggerService.class);
		when(audit.newSecurityEvent()).thenReturn(eventWithUser("alice"));
		LLMUsageRecorder recorder = new LLMUsageRecorder(crud, audit);

		recorder.record(chatConfig(), "openai", ModelType.CHAT, null, "alice", "ai.gebo.caller", System.nanoTime(),
				null, 120, 30, 150, LLMCallOutcome.SUCCESS);

		ArgumentCaptor<SecurityEvent> logged = ArgumentCaptor.forClass(SecurityEvent.class);
		verify(audit, times(1)).log(logged.capture());
		SecurityEvent event = logged.getValue();
		assertEquals(SecurityAuditTaxonomy.EventType.LLM_INVOCATION, event.getEventType());
		assertEquals(SecurityAuditTaxonomy.Category.LLM_INVOCATION, event.getCategory());
		assertEquals(SecurityAuditTaxonomy.Action.LLM_INVOKE_CHAT, event.getAction());
		assertEquals("chat-config-001", event.getResourceId());
		assertEquals("chat-test-model", event.getDetails().get("modelType"));
		assertEquals(SecurityAuditTaxonomy.Outcome.SUCCESS, event.getOutcome());
		assertEquals("openai", event.getDetails().get("provider"));
		assertEquals(120L, event.getDetails().get("inputTokens"));
		assertEquals(30L, event.getDetails().get("outputTokens"));
		assertTrue(event.getDetails().containsKey("latencyMs"));
		// the user comes from the request context: not repeated in the details
		assertFalse(event.getDetails().containsKey("username"));
		// nothing that could carry a prompt or an answer
		for (Object value : event.getDetails().values()) {
			assertTrue(value instanceof String || value instanceof Number, "metadata only: " + value);
		}
		verify(crud, times(1)).enqueueUsage(any());
	}

	@Test
	public void aCallWithoutRequestContextCarriesTheSampledUserAndAFailureItsOutcome() {
		ILLMSUsageCrudService crud = mock(ILLMSUsageCrudService.class);
		IGSecurityAuditLoggerService audit = mock(IGSecurityAuditLoggerService.class);
		// a Reactor thread: no request context, no user on the event
		when(audit.newSecurityEvent()).thenReturn(eventWithUser(null));
		LLMUsageRecorder recorder = new LLMUsageRecorder(crud, audit);

		recorder.record(chatConfig(), "openai", ModelType.CHAT, null, "bob", null, System.nanoTime(), null, 0, 0, 0,
				LLMCallOutcome.CANCELLED);

		ArgumentCaptor<SecurityEvent> logged = ArgumentCaptor.forClass(SecurityEvent.class);
		verify(audit).log(logged.capture());
		SecurityEvent event = logged.getValue();
		assertNull(event.getUserId());
		assertEquals("bob", event.getDetails().get("username"));
		assertEquals(SecurityAuditTaxonomy.Outcome.FAILURE, event.getOutcome());
		assertEquals("cancelled", event.getDetails().get("reason"));
	}

	@Test
	public void theOtherModelTypesAreNotAuditedAsChatCalls() {
		ILLMSUsageCrudService crud = mock(ILLMSUsageCrudService.class);
		IGSecurityAuditLoggerService audit = mock(IGSecurityAuditLoggerService.class);
		LLMUsageRecorder recorder = new LLMUsageRecorder(crud, audit);

		recorder.record(chatConfig(), "openai", ModelType.EMBEDDING, null, "alice", null, System.nanoTime(), null, 5,
				0, 5, LLMCallOutcome.SUCCESS);

		verify(audit, never()).log(any());
		verify(crud, times(1)).enqueueUsage(any());
	}

	@Test
	public void aFailingAuditNeverFailsTheAccountingAndNoLoggerRecordsUsageOnly() {
		ILLMSUsageCrudService crud = mock(ILLMSUsageCrudService.class);
		IGSecurityAuditLoggerService audit = mock(IGSecurityAuditLoggerService.class);
		when(audit.newSecurityEvent()).thenReturn(eventWithUser("alice"));
		doThrow(new IllegalStateException("audit sink down")).when(audit).log(any());
		new LLMUsageRecorder(crud, audit).record(chatConfig(), "openai", ModelType.CHAT, null, "alice", null,
				System.nanoTime(), null, 1, 1, 2, LLMCallOutcome.SUCCESS);
		verify(crud, times(1)).enqueueUsage(any());

		ILLMSUsageCrudService crudOnly = mock(ILLMSUsageCrudService.class);
		new LLMUsageRecorder(crudOnly).record(chatConfig(), "openai", ModelType.CHAT, null, "alice", null,
				System.nanoTime(), null, 1, 1, 2, LLMCallOutcome.SUCCESS);
		verify(crudOnly, times(1)).enqueueUsage(any());
	}
}
