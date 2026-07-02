package ai.gebo.llms.abstraction.layer.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.gebo.llms.abstraction.layer.model.LLMDailyUsageDetail;
import ai.gebo.llms.abstraction.layer.model.ModelType;

public interface LLMDailyUsageDetailRepository extends MongoRepository<LLMDailyUsageDetail, String> {

	Optional<LLMDailyUsageDetail> findByProviderIdAndUsernameAndModelAndCallerStackAndModelTypeAndYearAndMonthAndDay(
			String providerId, String username, String model, String callerStack, ModelType modelType, int year,
			int month, int day);

}
