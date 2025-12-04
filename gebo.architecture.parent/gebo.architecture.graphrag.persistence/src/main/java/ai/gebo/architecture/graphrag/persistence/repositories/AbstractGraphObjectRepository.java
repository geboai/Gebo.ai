package ai.gebo.architecture.graphrag.persistence.repositories;

import java.util.stream.Stream;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.NoRepositoryBean;

import ai.gebo.architecture.graphrag.persistence.model.AbstractGraphObject;

@NoRepositoryBean
public interface AbstractGraphObjectRepository<Type extends AbstractGraphObject> extends Neo4jRepository<Type, String> {
	public Stream<Type> findByType(String type);
}
