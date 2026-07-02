package ai.gebo.llms.abstraction.layer.services.impl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.model.LLMUsageDetail;
import ai.gebo.llms.abstraction.layer.repository.LLMUsageDetailRepository;
import ai.gebo.llms.abstraction.layer.services.ILLMSUsageCrudService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LLMSUsageCrudServiceImpl implements ILLMSUsageCrudService {
	private final LLMUsageDetailRepository usageRepo;

	@Override
	@Async
	public void enqueueUsage(LLMUsageDetail usage) {
		usageRepo.insert(usage);
	}

}
