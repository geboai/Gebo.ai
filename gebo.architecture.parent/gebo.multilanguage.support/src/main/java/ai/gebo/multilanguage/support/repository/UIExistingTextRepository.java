package ai.gebo.multilanguage.support.repository;

import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.gebo.multilanguage.support.model.UIExistingText;

public interface UIExistingTextRepository extends MongoRepository<UIExistingText, String> {
	
}
