package ai.gebo.knowledgebase.repositories;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.knlowledgebase.model.contents.GDocumentAttributeListValue;

public interface DocumentAttributeListValueRepository extends IGBaseMongoDBRepository<GDocumentAttributeListValue> {
	@Override
	default Class<GDocumentAttributeListValue> getManagedType() {
		return GDocumentAttributeListValue.class;
	}
}
