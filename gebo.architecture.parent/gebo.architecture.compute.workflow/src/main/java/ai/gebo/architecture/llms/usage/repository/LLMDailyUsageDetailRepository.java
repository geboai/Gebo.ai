package ai.gebo.architecture.llms.usage.repository;

import ai.gebo.core.messages.LLMCallOutcome;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.gebo.architecture.llms.usage.model.LLMDailyUsageDetail;
import ai.gebo.model.ModelType;

public interface LLMDailyUsageDetailRepository extends MongoRepository<LLMDailyUsageDetail, String> {

	Optional<LLMDailyUsageDetail> findByProviderIdAndUsernameAndModelAndCallerStackAndModelTypeAndOutcomeAndYearAndMonthAndDay(
			String providerId, String username, String model, String callerStack, ModelType modelType, LLMCallOutcome outcome, int year,
			int month, int day);

	Optional<LLMDailyUsageDetail> findByProviderIdAndUsernameAndModelAndCallerStackAndModelTypeAndOutcomeAndApiSecretCodeAndYearAndMonthAndDay(
			String providerId, String username, String model, String callerStack, ModelType modelType,
			LLMCallOutcome outcome, String apiSecretCode, int year, int month, int day);

	/** The daily usage of the days from dayFrom to dayTo, both included, of a month. */
	Stream<LLMDailyUsageDetail> findByYearAndMonthAndDayGreaterThanEqualAndDayLessThanEqual(int year, int month,
			int dayFrom, int dayTo);

}
