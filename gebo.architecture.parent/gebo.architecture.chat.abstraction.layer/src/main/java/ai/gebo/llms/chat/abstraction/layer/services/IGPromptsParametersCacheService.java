package ai.gebo.llms.chat.abstraction.layer.services;

import java.util.Map;
import java.util.function.Supplier;

public interface IGPromptsParametersCacheService {
	public Map<String, Object> lookupCache(String promptUse, String userChatContext, String contextKey, String langCode,
			long ttl, Supplier<Map<String, Object>> parametersSupplier);

	public default Map<String, Object> lookupCache(String promptUse, String userChatContext, String contextKey,
			long ttl, Supplier<Map<String, Object>> parametersSupplier) {
		return this.lookupCache(promptUse, userChatContext, contextKey, "en", ttl, parametersSupplier);
	}
}
