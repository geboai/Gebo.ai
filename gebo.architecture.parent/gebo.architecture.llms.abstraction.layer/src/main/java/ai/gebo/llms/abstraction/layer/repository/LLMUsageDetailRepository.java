package ai.gebo.llms.abstraction.layer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.gebo.llms.abstraction.layer.model.LLMUsageDetail;

public interface LLMUsageDetailRepository extends MongoRepository<LLMUsageDetail, String> {

}
