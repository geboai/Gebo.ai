package ai.gebo.llms.abstraction.layer.repository;

import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.gebo.llms.abstraction.layer.model.LLMUsageDetail;

public interface LLMUsageDetailRepository extends MongoRepository<LLMUsageDetail, String> {
	Stream<LLMUsageDetail> findByTimestampGreaterThanEqualAndTimestampLessThanEqual(long ts1, long ts2);

	void deleteByTimestampLessThanEqual(long ts1);
}
