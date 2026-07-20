package ai.gebo.llms.abstraction.layer.services;

import ai.gebo.llms.abstraction.layer.dto.LLMUsageDetailDto;

public interface ILLMSUsageCrudService {
	public void enqueueUsage(LLMUsageDetailDto usage);
}
