package ai.gebo.architecture.llms.usage.service.impl;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.GAbstractMessageReceiverFactory;
import ai.gebo.application.messaging.IGMessageReceiver;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.llms.usage.model.LLMUsageDetail;
import ai.gebo.architecture.llms.usage.repository.LLMUsageDetailRepository;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.core.messages.LLMUsageDetailPayload;

/**
 * Receives {@link LLMUsageDetailPayload} emitted by every LLM-hosting
 * microservice's {@code LLMSUsageCrudServiceImpl} and persists it - the raw
 * insert previously done synchronously in that emitter, now offloaded to this
 * receiver's own dedicated thread (pool cardinality 1), mirroring
 * {@code GComputeEndOfWorkflowReceiverFactory} in the sibling
 * {@code ai.gebo.workflows.compute.service.impl} package.
 */
@Component
@Scope("singleton")
public class LLMUsageConcentratorReceiverFactory extends GAbstractMessageReceiverFactory {
	private final IGRuntimeBinder runtimeBinder;
	public static final MessageReceiverFactoryConfig factoryConfig = new MessageReceiverFactoryConfig();

	static {
		factoryConfig.setUseSenderThread(false);
		factoryConfig.setPoolCardinality(1);
	}

	class UsageNestedReceiver extends GNestedMessageReceiver {

		@Override
		public void accept(GMessageEnvelope t) {
			if (t.getPayload() instanceof LLMUsageDetailPayload payload) {
				LLMUsageDetailRepository usageRepo = runtimeBinder.getImplementationOf(LLMUsageDetailRepository.class);
				usageRepo.insert(toEntity(payload));
			} else {
				throw new IllegalStateException("This receiver cannot handle payload type:" + t.getPayloadType());
			}
		}

		private LLMUsageDetail toEntity(LLMUsageDetailPayload payload) {
			LLMUsageDetail detail = new LLMUsageDetail();
			detail.setProviderId(payload.getProviderId());
			detail.setUsername(payload.getUsername());
			detail.setModel(payload.getModel());
			detail.setCallerStack(payload.getCallerStack());
			detail.setModelType(payload.getModelType());
			detail.setLatency(payload.getLatency());
			detail.setInputToken(payload.getInputToken());
			detail.setOutputToken(payload.getOutputToken());
			detail.setTotalToken(payload.getTotalToken());
			detail.setTimestamp(payload.getUsageTimestamp());
			return detail;
		}
	}

	public LLMUsageConcentratorReceiverFactory(IGRuntimeBinder runtimeBinder) {
		super(factoryConfig);
		this.runtimeBinder = runtimeBinder;
	}

	@Override
	public List<String> getAcceptedPayloadTypes() {
		return List.of(LLMUsageDetailPayload.class.getName());
	}

	@Override
	public boolean isAcceptEveryPayloadType() {
		return false;
	}

	@Override
	public IGMessageReceiver create() {
		return new UsageNestedReceiver();
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

}
