package ai.gebo.knowledgebase.repositories;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.knlowledgebase.model.contents.GDocumentAttributeType;

public interface DocumentAttributeTypeRepository extends IGBaseMongoDBRepository<GDocumentAttributeType> {
	@Override
	default Class<GDocumentAttributeType> getManagedType() {

		return GDocumentAttributeType.class;
	}
}
