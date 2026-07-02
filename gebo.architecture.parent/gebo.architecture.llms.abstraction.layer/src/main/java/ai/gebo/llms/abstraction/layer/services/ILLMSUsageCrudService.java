package ai.gebo.llms.abstraction.layer.services;

import org.springframework.scheduling.annotation.Async;

import ai.gebo.llms.abstraction.layer.model.LLMUsageDetail;

public interface ILLMSUsageCrudService {
	@Async
	public void enqueueUsage(LLMUsageDetail usage);
}
