package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.util.Date;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

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
			repo.save(data);
		}
		return data.getPromptsParameters();
	}

}
