package ai.gebo.security.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.gebo.security.model.oauth2.Oauth2DeliveryData;

public interface Oauth2DeliveryDataRepository extends MongoRepository<Oauth2DeliveryData, String> {

}
