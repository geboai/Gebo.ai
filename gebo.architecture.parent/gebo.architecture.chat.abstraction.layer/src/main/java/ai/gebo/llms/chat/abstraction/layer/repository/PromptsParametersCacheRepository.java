package ai.gebo.llms.chat.abstraction.layer.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.gebo.llms.chat.abstraction.layer.model.PromptsParametersCache;

public interface PromptsParametersCacheRepository extends MongoRepository<PromptsParametersCache, String> {

	public default PromptsParametersCache findBy(String promptUse, String userChatContext, String contextKey,
			String username, String langCode) {
		String code = PromptsParametersCache.calculateCode(promptUse, userChatContext, contextKey, username, langCode);
		Optional<PromptsParametersCache> op = findById(code);
		return op.isPresent() ? op.get() : null;
	}

	void deleteByLastHitDateTimeBefore(Date threshold);
}
