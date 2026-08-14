package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.llms.abstraction.layer.model.GBaseRankerModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableRankerModel;
import ai.gebo.llms.abstraction.layer.services.IGRankerModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.services.IGRankerService;
import ai.gebo.ranker.model.RankingInput;
import ai.gebo.ranker.model.RankingOutput;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.SecurityAuditTaxonomy;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GRankerServiceImpl implements IGRankerService {
	private final IGRankerModelRuntimeConfigurationDao rankerModelDao;
	private final IGSecurityAuditLoggerService securityAuditLoggerService;

	// Takes an already-created SecurityEvent (never calls newSecurityEvent()
	// itself) so newSecurityEvent()'s caller-stack capture points at the two
	// call(...) overloads - the real invocation entry points - not at this
	// shared helper. Metadata-only: model/provider/outcome/latency, never the
	// documents or query text being ranked.
	private void logRankerEvent(SecurityEvent event, IGConfigurableRankerModel rankerModel, long startMillis,
			String outcome) {
		event.setEventType(SecurityAuditTaxonomy.EventType.LLM_INVOCATION);
		event.setCategory(SecurityAuditTaxonomy.Category.LLM_INVOCATION);
		event.setAction(SecurityAuditTaxonomy.Action.LLM_INVOKE_RANK);
		event.setResourceId(rankerModel != null ? rankerModel.getCode() : null);
		if (rankerModel != null && rankerModel.getType() != null) {
			event.getDetails().put("provider", rankerModel.getType().getCode());
		}
		event.getDetails().put("latencyMs", System.currentTimeMillis() - startMillis);
		event.setOutcome(outcome);
		securityAuditLoggerService.log(event);
	}

	@Override
	public AIDocumentsSet call(AIDocumentsSet input, String query, int topK) throws LLMConfigException {
		final int nFragments = input.countFragments();
		if (nFragments <= 0)
			return input;

		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		long startMillis = System.currentTimeMillis();
		IGConfigurableRankerModel rankerModel = rankerModelDao.defaultHandler();
		try {
			if (rankerModel == null)
				throw new LLMConfigException(
						"No ranker model configured, call first isRankerConfigured() to check if there is one");
			RankingInput _input = new RankingInput(query, input.aiDocumentsList(), topK);
			RankingOutput out = rankerModel.getRankerModel().call(_input);
			List<Document> documents = out.getRanked().stream().map(x -> x.getDocument()).toList();
			logRankerEvent(event, rankerModel, startMillis, SecurityAuditTaxonomy.Outcome.SUCCESS);
			return AIDocumentsSet.from(documents);
		} catch (RuntimeException | LLMConfigException e) {
			logRankerEvent(event, rankerModel, startMillis, SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		}
	}

	@Override
	public boolean isRankerConfigured() {

		return rankerModelDao.defaultHandler() != null;
	}

	@Override
	public List<Document> call(List<Document> input, String query, int topK) throws LLMConfigException {
		final int nFragments = input.size();
		if (nFragments <= 0)
			return input;

		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		long startMillis = System.currentTimeMillis();
		IGConfigurableRankerModel rankerModel = rankerModelDao.defaultHandler();
		try {
			if (rankerModel == null)
				throw new LLMConfigException(
						"No ranker model configured, call first isRankerConfigured() to check if there is one");
			RankingInput _input = new RankingInput(query, input, topK);
			RankingOutput out = rankerModel.getRankerModel().call(_input);
			List<Document> documents = out.getRanked().stream().map(x -> x.getDocument()).toList();
			logRankerEvent(event, rankerModel, startMillis, SecurityAuditTaxonomy.Outcome.SUCCESS);
			return documents;
		} catch (RuntimeException | LLMConfigException e) {
			logRankerEvent(event, rankerModel, startMillis, SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		}
	}

	@Override
	public int getRankerConfiguredChunkSize() {
		IGConfigurableRankerModel rankerModel = rankerModelDao.defaultHandler();
		if (rankerModel == null)
			return 512;
		Integer documentTokens = ((GBaseRankerModelConfig) rankerModel.getConfig()).getMaxDocumentTokens();
		return documentTokens == null ? 512 : documentTokens.intValue();
	}

}
