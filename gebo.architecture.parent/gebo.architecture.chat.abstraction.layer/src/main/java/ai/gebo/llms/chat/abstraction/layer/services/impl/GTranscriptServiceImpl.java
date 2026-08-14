/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableTranscriptModel;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.services.IGTranscriptService;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.SecurityAuditTaxonomy;
import lombok.AllArgsConstructor;

/**
 * Gebo.ai comment agent
 *
 * Default implementation of {@link IGTranscriptService}. It resolves the
 * default transcript model via the runtime configuration DAO and delegates the
 * call to it.
 */
@Service
@AllArgsConstructor
public class GTranscriptServiceImpl implements IGTranscriptService {

	final IGTranscriptModelRuntimeConfigurationDao transcriptModelsDao;
	final IGSecurityAuditLoggerService securityAuditLoggerService;

	@Override
	public boolean isEnabled() {
		return transcriptModelsDao.defaultHandler() != null;
	}

	@Override
	public String transcript(InputStream is) throws LLMConfigException, IOException {
		// Metadata-only: model/provider/outcome/latency, never the audio or the
		// transcribed text.
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		long startMillis = System.currentTimeMillis();
		event.setEventType(SecurityAuditTaxonomy.EventType.LLM_INVOCATION);
		event.setCategory(SecurityAuditTaxonomy.Category.LLM_INVOCATION);
		event.setAction(SecurityAuditTaxonomy.Action.LLM_INVOKE_TRANSCRIPT);
		try {
			IGConfigurableTranscriptModel model = transcriptModelsDao.defaultHandler();
			if (model == null) {
				throw new LLMConfigException("No default transcript model configured");
			}
			event.setResourceId(model.getCode());
			if (model.getType() != null) {
				event.getDetails().put("provider", model.getType().getCode());
			}
			String result = model.call(is);
			event.getDetails().put("latencyMs", System.currentTimeMillis() - startMillis);
			event.setOutcome(SecurityAuditTaxonomy.Outcome.SUCCESS);
			securityAuditLoggerService.log(event);
			return result;
		} catch (RuntimeException | LLMConfigException | IOException e) {
			event.getDetails().put("latencyMs", System.currentTimeMillis() - startMillis);
			event.setOutcome(SecurityAuditTaxonomy.Outcome.FAILURE);
			securityAuditLoggerService.log(event);
			throw e;
		}
	}
}
