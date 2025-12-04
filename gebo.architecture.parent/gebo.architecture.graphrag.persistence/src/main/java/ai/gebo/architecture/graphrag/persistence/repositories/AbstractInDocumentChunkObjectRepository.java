package ai.gebo.architecture.graphrag.persistence.repositories;

import org.springframework.data.repository.NoRepositoryBean;

import ai.gebo.architecture.graphrag.persistence.model.AbstractInDocumentChunkObject;

@NoRepositoryBean
public interface AbstractInDocumentChunkObjectRepository<Type extends AbstractInDocumentChunkObject>
		extends AbstractGraphObjectRepository<Type> {
	public void deleteByDocumentChunkDocumentCode(String code);
}
