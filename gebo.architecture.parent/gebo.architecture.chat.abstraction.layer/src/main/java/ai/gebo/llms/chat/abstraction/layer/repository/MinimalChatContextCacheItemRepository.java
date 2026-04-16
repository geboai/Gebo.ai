package ai.gebo.llms.chat.abstraction.layer.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.gebo.llms.chat.abstraction.layer.model.MinimalChatContextCacheItem;

public interface MinimalChatContextCacheItemRepository extends MongoRepository<MinimalChatContextCacheItem, String> {
	public void deleteByUserChatContextCode(String userChatContextCode);
	public List<MinimalChatContextCacheItem> findByUserChatContextCode(String userChatContextCode);
	public List<MinimalChatContextCacheItem> findByUserChatContextCodeAndLastRequestIdAndTokensBudgetLessThanEqual(
			String sessionCode, String lastInteractionId, Integer tokensBudget);
}
