package ai.gebo.llms.chat.abstraction.layer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.gebo.llms.chat.abstraction.layer.session.model.ShrinkedChatSessionState;

public interface ShrinkedChatSessionStateRepository extends MongoRepository<ShrinkedChatSessionState, String> {

}
