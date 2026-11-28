/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.persistence.config;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.IndexDefinition;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.index.IndexResolver;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

/**
 * Creates in MongoDB the indexes the documents of the platform declare (@Indexed,
 * @HashIndexed, @TextIndexed, @CompoundIndex...), as Spring Data resolves them, once
 * the application is ready and on a thread of its own: the large collections are
 * indexed without holding the start. The documents are the types annotated
 * {@code @Document} and the domain types of the MongoDB repositories, which may have
 * no annotation.
 * <p>
 * Each index is created on its own. A hashed index an embedded type brings onto a path
 * through a list is never created: MongoDB hashes no array. One MongoDB refuses (an
 * index conflicting with one already there) is told and left out, and the others are
 * created all the same. An index already there as declared is left as it is.
 * <p>
 * Turned off by {@code ai.gebo.mongodb.autoIndexCreation: false}.
 */
@Component
@ConditionalOnProperty(prefix = "ai.gebo.mongodb", name = "enabled", havingValue = "true")
public class MongoDeclaredIndexes {
	private static final Logger LOGGER = LoggerFactory.getLogger(MongoDeclaredIndexes.class);
	/** The package the documents of the platform, and of the applications built on it, are in. */
	static final String DOCUMENTS_PACKAGE = "ai.gebo";
	private final MongoConfig config;
	private final ObjectProvider<MongoTemplate> mongo;
	private final ListableBeanFactory applicationContext;

	public MongoDeclaredIndexes(MongoConfig config, ObjectProvider<MongoTemplate> mongo,
			ListableBeanFactory applicationContext) {
		this.config = config;
		this.mongo = mongo;
		this.applicationContext = applicationContext;
	}

	/** What the creation did: the indexes created or found as declared, and the ones refused. */
	public record Outcome(int types, int indexes, int refused) {
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		if (!config.isAutoIndexCreation()) {
			LOGGER.info("The indexes the MongoDB documents declare are not created (ai.gebo.mongodb.autoIndexCreation: false)");
			return;
		}
		final Thread thread = new Thread(() -> {
			try {
				createAll();
			} catch (RuntimeException e) {
				LOGGER.error("The indexes the MongoDB documents declare cannot be created", e);
			}
		}, "mongo-declared-indexes");
		thread.setDaemon(true);
		thread.start();
	}

	/** Creates the indexes the documents declare, each on its own. */
	public Outcome createAll() {
		final MongoTemplate template = mongo.getIfAvailable();
		if (template == null) {
			LOGGER.warn("No MongoDB template: the indexes the documents declare are not created");
			return new Outcome(0, 0, 0);
		}
		final MappingContext<? extends MongoPersistentEntity<?>, MongoPersistentProperty> mappingContext = template
				.getConverter().getMappingContext();
		final IndexResolver resolver = IndexResolver.create(mappingContext);
		final long start = System.currentTimeMillis();
		int types = 0;
		int indexes = 0;
		int refused = 0;
		int skipped = 0;
		for (Class<?> type : documentTypes(mappingContext)) {
			final Iterable<? extends IndexDefinition> declared;
			try {
				declared = resolver.resolveIndexFor(type);
			} catch (RuntimeException e) {
				LOGGER.warn("Cannot read the indexes " + type.getName() + " declares: " + e.getMessage());
				continue;
			}
			types++;
			final MongoPersistentEntity<?> entity = mappingContext.getPersistentEntity(type);
			IndexOperations operations = null;
			for (IndexDefinition index : declared) {
				final String throughList = hashedThroughList(entity, index, mappingContext);
				if (throughList != null) {
					skipped++;
					LOGGER.info("Left out the hashed index on " + throughList + " " + type.getName()
							+ " gets from an embedded type: the path goes through a list, and MongoDB hashes no array");
					continue;
				}
				try {
					if (operations == null) {
						operations = template.indexOps(type);
					}
					operations.createIndex(index);
					indexes++;
				} catch (RuntimeException e) {
					refused++;
					LOGGER.warn("MongoDB refuses the index " + index.getIndexKeys().toJson() + " " + type.getName()
							+ " declares, left out: " + e.getMessage());
				}
			}
		}
		final Outcome outcome = new Outcome(types, indexes, refused);
		LOGGER.info("Indexes the MongoDB documents declare: " + indexes + " created or already there, " + refused
				+ " refused, " + skipped + " hashed through a list left out, of " + types + " document type(s), in "
				+ (System.currentTimeMillis() - start) + " ms");
		return outcome;
	}

	/**
	 * The path of a hashed key of the index that goes through a list (an array in
	 * MongoDB), or null when there is none. An embedded type hashing one of its fields
	 * gets the index wherever it is held, a list too. MongoDB refuses such an index when
	 * some document has the list, and when none has it yet it creates the index and then
	 * refuses every write of a document with the list: it is never created.
	 */
	static String hashedThroughList(MongoPersistentEntity<?> entity, IndexDefinition index,
			MappingContext<? extends MongoPersistentEntity<?>, MongoPersistentProperty> mappingContext) {
		if (entity == null) {
			return null;
		}
		for (Map.Entry<String, Object> key : index.getIndexKeys().entrySet()) {
			if ("hashed".equals(key.getValue()) && throughList(entity, key.getKey(), mappingContext)) {
				return key.getKey();
			}
		}
		return null;
	}

	/** Whether the dotted path, of MongoDB field names, goes through a list or an array. */
	private static boolean throughList(MongoPersistentEntity<?> entity, String path,
			MappingContext<? extends MongoPersistentEntity<?>, MongoPersistentProperty> mappingContext) {
		MongoPersistentEntity<?> current = entity;
		for (String field : path.split("\\.")) {
			if (current == null) {
				return false;
			}
			MongoPersistentProperty property = null;
			for (MongoPersistentProperty candidate : current) {
				if (field.equals(candidate.getFieldName())) {
					property = candidate;
					break;
				}
			}
			if (property == null) {
				return false;
			}
			if (property.isCollectionLike() || property.isArray()) {
				return true;
			}
			current = property.isEntity() ? mappingContext.getPersistentEntity(property) : null;
		}
		return false;
	}

	/**
	 * The document types of the platform: the domain types of the MongoDB repositories,
	 * which may have no {@code @Document} (a collection only a repository is bound to,
	 * such as the virtual folders), and the classes annotated, or inheriting,
	 * {@code @Document}. Embedded types are neither: their indexes come with the
	 * documents holding them.
	 */
	Set<Class<?>> documentTypes(
			MappingContext<? extends MongoPersistentEntity<?>, MongoPersistentProperty> mappingContext) {
		final Set<Class<?>> types = new LinkedHashSet<>();
		if (applicationContext != null) {
			final Repositories repositories = new Repositories(applicationContext);
			final Set<String> notAnnotated = new LinkedHashSet<>();
			for (Class<?> domainType : repositories) {
				// the MongoDB repositories only, not the JPA ones an application may have
				if (domainType.getName().startsWith(DOCUMENTS_PACKAGE + ".")
						&& repositories.getPersistentEntity(domainType) instanceof MongoPersistentEntity) {
					types.add(domainType);
					if (!AnnotatedElementUtils.hasAnnotation(domainType, Document.class)) {
						notAnnotated.add(domainType.getName());
					}
				}
			}
			if (!notAnnotated.isEmpty()) {
				LOGGER.info("MongoDB repository types with no @Document, indexed all the same: " + notAnnotated);
			}
		}
		final ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(
				false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(Document.class));
		final ClassLoader loader = MongoDeclaredIndexes.class.getClassLoader();
		for (BeanDefinition candidate : scanner.findCandidateComponents(DOCUMENTS_PACKAGE)) {
			try {
				types.add(ClassUtils.forName(candidate.getBeanClassName(), loader));
			} catch (ClassNotFoundException | LinkageError e) {
				LOGGER.warn("Cannot load the document type " + candidate.getBeanClassName() + ": " + e.getMessage());
			}
		}
		return types;
	}
}
