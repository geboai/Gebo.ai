/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.io.InputStream;

import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableTextToSpeechModel;
import ai.gebo.llms.abstraction.layer.services.IGTextToSpeechModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.services.IGTextToSpeechService;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.SecurityAuditTaxonomy;
import lombok.AllArgsConstructor;

/**
 * Gebo.ai comment agent
 *
 * Default implementation of {@link IGTextToSpeechService}. It resolves the
 * default text to speech model via the runtime configuration DAO and delegates
 * the call to it.
 */
@Service
@AllArgsConstructor
public class GTextToSpeechServiceImpl implements IGTextToSpeechService {

	final IGTextToSpeechModelRuntimeConfigurationDao ttsModelsDao;
	final IGSecurityAuditLoggerService securityAuditLoggerService;

	@Override
	public boolean isEnabled() {
		return ttsModelsDao.defaultHandler() != null;
	}

	@Override
	public InputStream speech(String text) throws LLMConfigException {
		// Metadata-only: model/provider/outcome/latency, never the text being
		// synthesized.
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		long startMillis = System.currentTimeMillis();
		event.setEventType(SecurityAuditTaxonomy.EventType.LLM_INVOCATION);
		event.setCategory(SecurityAuditTaxonomy.Category.LLM_INVOCATION);
		event.setAction(SecurityAuditTaxonomy.Action.LLM_INVOKE_TTS);
		try {
			IGConfigurableTextToSpeechModel model = ttsModelsDao.defaultHandler();
			if (model == null) {
				throw new LLMConfigException("No default text to speech model configured");
			}
			event.setResourceId(model.getCode());
			if (model.getType() != null) {
				event.getDetails().put("provider", model.getType().getCode());
			}
			InputStream result = model.call(text);
			event.getDetails().put("latencyMs", System.currentTimeMillis() - startMillis);
			event.setOutcome(SecurityAuditTaxonomy.Outcome.SUCCESS);
			securityAuditLoggerService.log(event);
			return result;
		} catch (RuntimeException | LLMConfigException e) {
			event.getDetails().put("latencyMs", System.currentTimeMillis() - startMillis);
			event.setOutcome(SecurityAuditTaxonomy.Outcome.FAILURE);
			securityAuditLoggerService.log(event);
			throw e;
		}
	}
}
