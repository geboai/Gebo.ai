package ai.gebo.llms.abstraction.layer.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.IMessageEnvelopeFactory;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.core.messages.LLMUsageDetailPayload;
import ai.gebo.llms.abstraction.layer.dto.LLMUsageDetailDto;
import ai.gebo.llms.abstraction.layer.services.ILLMSUsageCrudService;
import lombok.AllArgsConstructor;

/**
 * Emits usage events to the {@code LLMS-USAGE-MONITOR}/{@code USAGE-CONCENTRATOR}
 * receiver hosted in {@code gebo.architecture.compute.workflow} (package
 * {@code ai.gebo.architecture.llms.usage}), rather than persisting directly - the
 * receiver's own dedicated thread does the Mongo write and the periodic
 * consolidation, keeping both off the calling chat-completion thread.
 */
@Service
@AllArgsConstructor
public class LLMSUsageCrudServiceImpl implements ILLMSUsageCrudService, IGMessageEmitter {
	private final IGMessageBroker broker;
	private final IMessageEnvelopeFactory envelopeFactory;

	@Override
	public void enqueueUsage(LLMUsageDetailDto usage) {
		LLMUsageDetailPayload payload = new LLMUsageDetailPayload();
		payload.setProviderId(usage.getProviderId());
		payload.setUsername(usage.getUsername());
		payload.setModel(usage.getModel());
		payload.setCallerStack(usage.getCallerStack());
		payload.setModelType(usage.getModelType());
		payload.setLatency(usage.getLatency());
		payload.setInputToken(usage.getInputToken());
		payload.setOutputToken(usage.getOutputToken());
		payload.setTotalToken(usage.getTotalToken());
		payload.setUsageTimestamp(System.currentTimeMillis());

		GMessageEnvelope<LLMUsageDetailPayload> envelope = envelopeFactory.newMessageFrom(this, payload);
		envelope.setTargetModule(GStandardModulesConstraints.LLMS_USAGE_MONITOR);
		envelope.setTargetComponent(GStandardModulesConstraints.USAGE_CONCENTRATOR);
		broker.accept(envelope);
	}

	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.LLMS_USAGE_MONITOR;
	}

	@Override
	public String getMessagingSystemId() {
		return GStandardModulesConstraints.USAGE_CONCENTRATOR;
	}

	@Override
	public SystemComponentType getComponentType() {
		return SystemComponentType.APPLICATION_COMPONENT;
	}

	@Override
	public List<String> getEmittedPayloadTypes() {
		return List.of(LLMUsageDetailPayload.class.getName());
	}
}
