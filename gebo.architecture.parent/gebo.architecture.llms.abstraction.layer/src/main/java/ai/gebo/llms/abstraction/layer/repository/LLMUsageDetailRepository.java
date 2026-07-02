package ai.gebo.llms.abstraction.layer.repository;

import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import ai.gebo.llms.abstraction.layer.model.LLMUsageDetail;

public interface LLMUsageDetailRepository extends MongoRepository<LLMUsageDetail, String> {
	/**
	 * Explicit query: a derived {@code GreaterThanEqual ... AndTimestampLessThanEqual}
	 * would emit two separate {@code timestamp} criteria, which Spring Data cannot
	 * merge into a single {@link org.bson.Document}. The {@code $gte}/{@code $lte}
	 * bounds are therefore expressed in one timestamp expression.
	 */
	@Query("{ 'timestamp' : { $gte : ?0, $lte : ?1 } }")
	Stream<LLMUsageDetail> findByTimestampGreaterThanEqualAndTimestampLessThanEqual(long ts1, long ts2);

	void deleteByTimestampLessThanEqual(long ts1);
}
