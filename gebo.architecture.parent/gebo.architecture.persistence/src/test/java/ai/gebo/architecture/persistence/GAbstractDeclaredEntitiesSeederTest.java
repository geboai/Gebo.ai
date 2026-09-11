/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import ai.gebo.model.base.GBaseObject;

/**
 * Covers what
 * {@link GAbstractDeclaredEntitiesSeeder} guarantees for a declared knowledge
 * base or project: the record is written carrying the {@code readonly} marker,
 * an admin-created record of the same code is never overwritten, a record whose
 * declaration is gone is handed back rather than deleted, and a broken
 * declaration fails before anything is written.
 *
 * Gebo.ai comment agent
 */
class GAbstractDeclaredEntitiesSeederTest {

	/** A declarable record, standing in for a project or a knowledge base. */
	public static class TestEntity extends GBaseObject {
		private static final long serialVersionUID = 1L;
		private Boolean readonly = null;
	}

	/** In-memory stand-in for the Mongo repository. */
	private static class InMemoryRepository extends AbstractTestRepository<TestEntity> {
		@Override
		public Class<TestEntity> getManagedType() {
			return TestEntity.class;
		}
	}

	private static class TestSeeder extends GAbstractDeclaredEntitiesSeeder<TestEntity, InMemoryRepository> {
		private final List<TestEntity> declarations;

		TestSeeder(List<TestEntity> declarations, InMemoryRepository repository) {
			super(repository, null);
			this.declarations = declarations;
		}

		@Override
		protected List<TestEntity> getDeclarations() {
			return declarations;
		}

		@Override
		protected void setReadonly(TestEntity entity, Boolean value) {
			entity.readonly = value;
		}

		@Override
		protected Boolean getReadonly(TestEntity entity) {
			return entity.readonly;
		}

		@Override
		protected String describeKind() {
			return "test record";
		}

		@Override
		public int getOrder() {
			return PROJECT_ORDER;
		}
	}

	private static TestEntity entity(String code, Boolean readonly) {
		TestEntity e = new TestEntity();
		e.setCode(code);
		e.setDescription("declared " + code);
		e.readonly = readonly;
		return e;
	}

	@Test
	void aDeclarationIsWrittenCarryingTheReadonlyMarker() {
		InMemoryRepository repository = new InMemoryRepository();

		new TestSeeder(List.of(entity("COMPANY-KB", null)), repository).seed();

		TestEntity stored = repository.findById("COMPANY-KB").orElseThrow();
		assertEquals(Boolean.TRUE, stored.readonly);
		assertEquals("declared COMPANY-KB", stored.getDescription());
	}

	@Test
	void anEarlierSeededRecordIsOverwritten() {
		InMemoryRepository repository = new InMemoryRepository();
		repository.save(entity("COMPANY-KB", Boolean.TRUE));

		TestEntity declared = entity("COMPANY-KB", null);
		declared.setDescription("a new description");
		new TestSeeder(List.of(declared), repository).seed();

		assertEquals("a new description", repository.findById("COMPANY-KB").orElseThrow().getDescription());
	}

	@Test
	void anAdminCreatedRecordIsNeverOverwritten() {
		InMemoryRepository repository = new InMemoryRepository();
		repository.save(entity("COMPANY-KB", null));
		TestSeeder seeder = new TestSeeder(List.of(entity("COMPANY-KB", null)), repository);

		IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> seeder.seed());

		assertTrue(thrown.getMessage().contains("COMPANY-KB"));
		assertTrue(thrown.getMessage().contains("test record"));
	}

	@Test
	void aRecordWhoseDeclarationIsGoneIsHandedBackRatherThanDeleted() {
		InMemoryRepository repository = new InMemoryRepository();
		repository.save(entity("WAS-DECLARED", Boolean.TRUE));

		new TestSeeder(List.of(entity("STILL-DECLARED", null)), repository).seed();

		TestEntity released = repository.findById("WAS-DECLARED").orElseThrow();
		assertEquals(Boolean.FALSE, released.readonly,
				"deleting it here would skip the disposal of everything beneath it");
	}

	@Test
	void anAdminCreatedRecordIsLeftAloneByTheHandBack() {
		InMemoryRepository repository = new InMemoryRepository();
		repository.save(entity("FROM-THE-UI", null));

		new TestSeeder(List.of(), repository).seed();

		assertNull(repository.findById("FROM-THE-UI").orElseThrow().readonly);
	}

	@Test
	void isDeclaredInConfigurationReadsTheStoredRecord() {
		InMemoryRepository repository = new InMemoryRepository();
		TestSeeder seeder = new TestSeeder(List.of(entity("COMPANY-KB", null)), repository);
		seeder.seed();
		repository.save(entity("FROM-THE-UI", null));

		assertTrue(seeder.isDeclaredInConfiguration("COMPANY-KB"));
		assertFalse(seeder.isDeclaredInConfiguration("FROM-THE-UI"));
		assertFalse(seeder.isDeclaredInConfiguration("NEVER-HEARD-OF"));
		assertFalse(seeder.isDeclaredInConfiguration(null));
	}

	@Test
	void aBlankCodeFailsBeforeAnythingIsWritten() {
		InMemoryRepository repository = new InMemoryRepository();
		TestSeeder seeder = new TestSeeder(List.of(entity(" ", null)), repository);

		assertThrows(IllegalStateException.class, () -> seeder.seed());
		assertEquals(0, repository.count());
	}

	@Test
	void aCodeDeclaredTwiceFailsBeforeAnythingIsWritten() {
		InMemoryRepository repository = new InMemoryRepository();
		TestSeeder seeder = new TestSeeder(List.of(entity("TWICE", null), entity("twice", null)), repository);

		assertThrows(IllegalStateException.class, () -> seeder.seed());
		assertEquals(0, repository.count(), "a bad declaration must not leave the store half seeded");
	}

	@Test
	void theHierarchyIsSeededTopDown() {
		assertTrue(GAbstractDeclaredEntitiesSeeder.KNOWLEDGE_BASE_ORDER < GAbstractDeclaredEntitiesSeeder.PROJECT_ORDER);
		assertTrue(GAbstractDeclaredEntitiesSeeder.PROJECT_ORDER < GAbstractDeclaredEntitiesSeeder.DATA_SOURCE_ORDER);
	}

	/**
	 * The repository surface the seeder actually uses; everything else throws so a
	 * change of behaviour cannot pass unnoticed.
	 *
	 * @param <T> the stored type
	 */
	private abstract static class AbstractTestRepository<T extends GBaseObject> implements IGBaseMongoDBRepository<T> {
		private final Map<String, T> rows = new LinkedHashMap<String, T>();

		@Override
		public Optional<T> findById(String id) {
			return Optional.ofNullable(rows.get(id));
		}

		@Override
		public <S extends T> S save(S entity) {
			rows.put(entity.getCode(), entity);
			return entity;
		}

		@Override
		public List<T> findAll() {
			return new ArrayList<T>(rows.values());
		}

		@Override
		public long count() {
			return rows.size();
		}

		@Override
		public List<T> findByUserCreated(String username) {
			throw new UnsupportedOperationException();
		}

		@Override
		public org.springframework.data.domain.Page<T> findByUserCreated(String username,
				org.springframework.data.domain.Pageable pageable) {
			throw new UnsupportedOperationException();
		}

		@Override
		public <S extends T> List<S> saveAll(Iterable<S> entities) {
			throw new UnsupportedOperationException();
		}

		@Override
		public List<T> findAll(org.springframework.data.domain.Sort sort) {
			throw new UnsupportedOperationException();
		}

		@Override
		public org.springframework.data.domain.Page<T> findAll(org.springframework.data.domain.Pageable pageable) {
			throw new UnsupportedOperationException();
		}

		@Override
		public List<T> findAllById(Iterable<String> ids) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void deleteById(String id) {
			rows.remove(id);
		}

		@Override
		public void delete(T entity) {
			rows.remove(entity.getCode());
		}

		@Override
		public void deleteAllById(Iterable<? extends String> ids) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void deleteAll(Iterable<? extends T> entities) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void deleteAll() {
			rows.clear();
		}

		@Override
		public boolean existsById(String id) {
			return rows.containsKey(id);
		}

		@Override
		public <S extends T> S insert(S entity) {
			return save(entity);
		}

		@Override
		public <S extends T> List<S> insert(Iterable<S> entities) {
			throw new UnsupportedOperationException();
		}

		@Override
		public <S extends T> Optional<S> findOne(org.springframework.data.domain.Example<S> example) {
			throw new UnsupportedOperationException();
		}

		@Override
		public <S extends T> List<S> findAll(org.springframework.data.domain.Example<S> example) {
			throw new UnsupportedOperationException();
		}

		@Override
		public <S extends T> List<S> findAll(org.springframework.data.domain.Example<S> example,
				org.springframework.data.domain.Sort sort) {
			throw new UnsupportedOperationException();
		}

		@Override
		public <S extends T> org.springframework.data.domain.Page<S> findAll(
				org.springframework.data.domain.Example<S> example,
				org.springframework.data.domain.Pageable pageable) {
			throw new UnsupportedOperationException();
		}

		@Override
		public <S extends T> long count(org.springframework.data.domain.Example<S> example) {
			throw new UnsupportedOperationException();
		}

		@Override
		public <S extends T> boolean exists(org.springframework.data.domain.Example<S> example) {
			throw new UnsupportedOperationException();
		}

		@Override
		public <S extends T, R> R findBy(org.springframework.data.domain.Example<S> example,
				java.util.function.Function<org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
			throw new UnsupportedOperationException();
		}
	}
}
