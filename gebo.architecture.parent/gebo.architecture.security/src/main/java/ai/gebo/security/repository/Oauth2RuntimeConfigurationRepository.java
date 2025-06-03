package ai.gebo.security.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.gebo.security.model.Oauth2RuntimeConfiguration;

public interface Oauth2RuntimeConfigurationRepository extends MongoRepository<Oauth2RuntimeConfiguration, String> {

}
