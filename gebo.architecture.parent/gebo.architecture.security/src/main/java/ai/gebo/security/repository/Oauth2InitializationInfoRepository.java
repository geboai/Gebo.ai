package ai.gebo.security.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.gebo.security.model.oauth2.Oauth2InitializationInfo;

public interface Oauth2InitializationInfoRepository extends MongoRepository<Oauth2InitializationInfo, String> {

}
