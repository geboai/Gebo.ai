package ai.gebo.architecture.documents.cache.service.impl;

import java.util.Date;
import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractCachedEntryRepository<Type extends AbstractCachedEntry> extends MongoRepository<Type, String> {
	public Stream<Type> findByLastAccessedLessThan(Date date);

	public void deleteByLastAccessedLessThan(Date date);
}
