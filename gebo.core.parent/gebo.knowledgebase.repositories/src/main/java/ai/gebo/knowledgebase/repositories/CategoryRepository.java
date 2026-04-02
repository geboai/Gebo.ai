package ai.gebo.knowledgebase.repositories;

import java.util.stream.Stream;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.knlowledgebase.model.contents.GCategory;

public interface CategoryRepository extends IGBaseMongoDBRepository<GCategory> {
	@Override
	default Class<GCategory> getManagedType() {
		return GCategory.class;
	}
}
