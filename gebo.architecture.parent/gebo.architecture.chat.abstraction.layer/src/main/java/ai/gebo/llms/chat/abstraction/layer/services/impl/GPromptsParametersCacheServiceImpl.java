package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.util.Date;
import java.util.Map;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsParametersCacheConfig;
import ai.gebo.llms.chat.abstraction.layer.model.PromptsParametersCache;
import ai.gebo.llms.chat.abstraction.layer.repository.PromptsParametersCacheRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGPromptsParametersCacheService;
import ai.gebo.security.services.IGSecurityService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GPromptsParametersCacheServiceImpl implements IGPromptsParametersCacheService {
	private final PromptsParametersCacheRepository repo;
	private final IGSecurityService securityService;
	private final GeboPromptsParametersCacheConfig cacheConfig;

	private static final Logger LOGGER = LoggerFactory.getLogger(GPromptsParametersCacheServiceImpl.class);

	@Override
	public Map<String, Object> lookupCache(String promptUse, String userChatContext, String contextKey, String langCode,
			long ttl, Supplier<Map<String, Object>> parametersSupplier) {
		String username = securityService.getCurrentUser().getUsername();
		PromptsParametersCache data = repo.findBy(promptUse, userChatContext, contextKey, username, langCode);
		boolean recreate = false;
		if (data == null) {
			recreate = true;
			data=new PromptsParametersCache();
			data.setPromptUse(promptUse);
			data.setContextKey(contextKey);
			data.setUserChatContext(userChatContext);
			data.setUsername(username);
			data.recalculateCode();
		} else {
			recreate = data.getCreationDateTime().getTime() < System.currentTimeMillis() - ttl;
		}
		if (recreate) {
			Map<String, Object> map = parametersSupplier.get();
			data.setPromptsParameters(map);
			data.setCreationDateTime(new Date());
			data.setLastHitDateTime(new Date());
			repo.save(data);
		} else {
			data.setLastHitDateTime(new Date());
			repo.save(data);
		}
		return data.getPromptsParameters();
	}

	@Override
	public void evictStaleEntries(long idleTtlMillis) {
		Date threshold = new Date(System.currentTimeMillis() - idleTtlMillis);
		repo.deleteByLastHitDateTimeBefore(threshold);
	}

	@Scheduled(initialDelay = 60000, fixedRate = 300000)
	public void scheduledEviction() {
		try {
			evictStaleEntries(cacheConfig.getIdleTtlMinutes() * 60 * 1000);
		} catch (Throwable th) {
			LOGGER.error("Error in prompts cache scheduled eviction", th);
		}
	}

}
