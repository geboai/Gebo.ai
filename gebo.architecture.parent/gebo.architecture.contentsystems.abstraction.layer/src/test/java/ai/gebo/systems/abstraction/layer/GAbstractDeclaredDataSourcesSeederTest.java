/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.systems.abstraction.layer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import ai.gebo.knlowledgebase.model.projects.GVirtualFilesystemProjectEndpoint;
import ai.gebo.knowledgebase.repositories.IGBaseMongoDBProjectEndpointRepository;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSource;
import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSourcePath;

/**
 * Covers what
 * {@link GAbstractDeclaredDataSourcesSeeder} guarantees: the declarations become
 * stored records carrying the {@code readonly} marker, an admin-created record
 * of the same code is never overwritten, a record whose declaration is gone is
 * handed back rather than deleted, and a broken declaration fails before
 * anything is written.
 *
 * Gebo.ai comment agent
 */
class GAbstractDeclaredDataSourcesSeederTest {

	/** A module endpoint, standing in for a concrete handler's own type. */
	public static class TestEndpoint extends GVirtualFilesystemProjectEndpoint {
		private static final long serialVersionUID = 1L;
		private String systemCode = null;
	}

	/**
	 * An in-memory stand-in for the module's Mongo repository: only findById, save
	 * and findAll are exercised by the seeder.
	 */
	private static class InMemoryRepository implements IGBaseMongoDBProjectEndpointRepository<TestEndpoint> {
		private final Map<String, TestEndpoint> rows = new LinkedHashMap<String, TestEndpoint>();

		@Override
		public Optional<TestEndpoint> findById(String id) {
			return Optional.ofNullable(rows.get(id));
		}

		@Override
		public <S extends TestEndpoint> S save(S entity) {
			rows.put(entity.getCode(), entity);
			return entity;
		}

		@Override
		public List<TestEndpoint> findAll() {
			return new ArrayList<TestEndpoint>(rows.values());
		}

		@Override
		public Class<TestEndpoint> getManagedType() {
			return TestEndpoint.class;
		}

		@Override
		public List<TestEndpoint> findByParentProjectCode(String parentProjectCode) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void deleteByParentProjectCode(String code) {
			throw new UnsupportedOperationException();
		}

		@Override
		public List<TestEndpoint> findByUserCreated(String username) {
			throw new UnsupportedOperationException();
		}

		@Override
		public org.springframework.data.domain.Page<TestEndpoint> findByUserCreated(String username,
				org.springframework.data.domain.Pageable pageable) {
			throw new UnsupportedOperationException();
		}

		@Override
		public <S extends TestEndpoint> List<S> saveAll(Iterable<S> entities) {
			throw new UnsupportedOperationException();
		}

		@Override
		public List<TestEndpoint> findAll(org.springframework.data.domain.Sort sort) {
			throw new UnsupportedOperationException();
		}

		@Override
		public org.springframework.data.domain.Page<TestEndpoint> findAll(
				org.springframework.data.domain.Pageable pageable) {
			throw new UnsupportedOperationException();
		}

		@Override
		public List<TestEndpoint> findAllById(Iterable<String> ids) {
			throw new UnsupportedOperationException();
		}

		@Override
		public long count() {
			return rows.size();
		}

		@Override
		public void deleteById(String id) {
			rows.remove(id);
		}

		@Override
		public void delete(TestEndpoint entity) {
			rows.remove(entity.getCode());
		}

		@Override
		public void deleteAllById(Iterable<? extends String> ids) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void deleteAll(Iterable<? extends TestEndpoint> entities) {
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
		public <S extends TestEndpoint> S insert(S entity) {
			return save(entity);
		}

		@Override
		public <S extends TestEndpoint> List<S> insert(Iterable<S> entities) {
			throw new UnsupportedOperationException();
		}

		@Override
		public <S extends TestEndpoint> Optional<S> findOne(org.springframework.data.domain.Example<S> example) {
			throw new UnsupportedOperationException();
		}

		@Override
		public <S extends TestEndpoint> List<S> findAll(org.springframework.data.domain.Example<S> example) {
			throw new UnsupportedOperationException();
		}

		@Override
		public <S extends TestEndpoint> List<S> findAll(org.springframework.data.domain.Example<S> example,
				org.springframework.data.domain.Sort sort) {
			throw new UnsupportedOperationException();
		}

		@Override
		public <S extends TestEndpoint> org.springframework.data.domain.Page<S> findAll(
				org.springframework.data.domain.Example<S> example,
				org.springframework.data.domain.Pageable pageable) {
			throw new UnsupportedOperationException();
		}

		@Override
		public <S extends TestEndpoint> long count(org.springframework.data.domain.Example<S> example) {
			throw new UnsupportedOperationException();
		}

		@Override
		public <S extends TestEndpoint> boolean exists(org.springframework.data.domain.Example<S> example) {
			throw new UnsupportedOperationException();
		}

		@Override
		public <S extends TestEndpoint, R> R findBy(org.springframework.data.domain.Example<S> example,
				java.util.function.Function<org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
			throw new UnsupportedOperationException();
		}
	}

	/** Minimal concrete seeder: the abstract class is what is under test. */
	private static class TestSeeder extends GAbstractDeclaredDataSourcesSeeder<TestEndpoint, InMemoryRepository> {
		TestSeeder(List<GDeclaredDataSource> declarations, InMemoryRepository repository) {
			super(declarations, repository, null);
		}

		@Override
		protected TestEndpoint newEndpoint(GDeclaredDataSource declaration) {
			TestEndpoint endpoint = new TestEndpoint();
			endpoint.systemCode = declaration.getSystemCode();
			return endpoint;
		}

		@Override
		protected void setPaths(TestEndpoint endpoint, List<VFilesystemReference> references) {
			endpoint.setPaths(references);
		}

		@Override
		protected VFilesystemReference toReference(GDeclaredDataSourcePath declaredPath) {
			if (declaredPath.getPath().startsWith("!")) {
				throw new IllegalStateException("a test path may not start with !");
			}
			VFilesystemReference reference = new VFilesystemReference();
			reference.root = new GVirtualFilesystemRoot();
			reference.root.setCode("root");
			reference.path = new PathInfo();
			reference.path.absolutePath = declaredPath.getPath();
			reference.path.folder = declaredPath.isFolder();
			return reference;
		}
	}

	private static GDeclaredDataSource declaration(String code, String systemCode, String project, String path,
			boolean folder) {
		GDeclaredDataSource declared = new GDeclaredDataSource();
		declared.setCode(code);
		declared.setSystemCode(systemCode);
		declared.setParentProjectCode(project);
		GDeclaredDataSourcePath declaredPath = new GDeclaredDataSourcePath();
		declaredPath.setPath(path);
		declaredPath.setFolder(folder);
		declared.setPaths(List.of(declaredPath));
		return declared;
	}

	private static TestEndpoint row(String code, Boolean readonly) {
		TestEndpoint endpoint = new TestEndpoint();
		endpoint.setCode(code);
		endpoint.setReadonly(readonly);
		return endpoint;
	}

	@Test
	void aDeclarationIsWrittenToTheRepositoryCarryingItsPathsAndTheReadonlyMarker() {
		InMemoryRepository repository = new InMemoryRepository();
		new TestSeeder(List.of(declaration("policies", "corporate-dav", "KB", "/Policies", true)), repository).seed();

		TestEndpoint stored = repository.findById("policies").orElseThrow();
		assertEquals("corporate-dav", stored.systemCode);
		assertEquals("KB", stored.getParentProjectCode());
		assertEquals(Boolean.TRUE, stored.getReadonly(),
				"the marker is what disables save and delete in the admin UI and what a later boot overwrites on");
		assertEquals(1, stored.getPaths().size());
		assertEquals("/Policies", stored.getPaths().get(0).path.absolutePath);
		assertTrue(stored.getPaths().get(0).path.folder);
	}

	@Test
	void anEarlierSeededRecordIsOverwritten() {
		InMemoryRepository repository = new InMemoryRepository();
		repository.save(row("policies", Boolean.TRUE));

		new TestSeeder(List.of(declaration("policies", "corporate-dav", "KB", "/NewPath", true)), repository).seed();

		assertEquals("/NewPath", repository.findById("policies").orElseThrow().getPaths().get(0).path.absolutePath);
	}

	@Test
	void anAdminCreatedRecordIsNeverOverwritten() {
		InMemoryRepository repository = new InMemoryRepository();
		repository.save(row("policies", null));
		TestSeeder seeder = new TestSeeder(List.of(declaration("policies", "corporate-dav", "KB", "/Policies", true)),
				repository);

		IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> seeder.seed());

		assertTrue(thrown.getMessage().contains("policies"));
		assertNull(repository.findById("policies").orElseThrow().getPaths(), "the existing record is left untouched");
	}

	@Test
	void aRecordWhoseDeclarationIsGoneIsHandedBackToTheAdminRatherThanDeleted() {
		InMemoryRepository repository = new InMemoryRepository();
		repository.save(row("was-declared", Boolean.TRUE));

		new TestSeeder(List.of(declaration("still-declared", "corporate-dav", "KB", "/Policies", true)), repository)
				.seed();

		TestEndpoint released = repository.findById("was-declared").orElseThrow();
		assertEquals(Boolean.FALSE, released.getReadonly(),
				"deleting it here would skip the disposal of the content it produced");
	}

	@Test
	void anAdminCreatedRecordIsLeftAloneByTheHandBack() {
		InMemoryRepository repository = new InMemoryRepository();
		repository.save(row("from-the-ui", null));

		new TestSeeder(List.of(), repository).seed();

		assertNull(repository.findById("from-the-ui").orElseThrow().getReadonly());
	}

	@Test
	void aDeclarationWithoutACodeFailsBeforeAnythingIsWritten() {
		assertThrows(IllegalStateException.class,
				() -> new TestSeeder(List.of(declaration(" ", "corporate-dav", "KB", "/Policies", true)),
						new InMemoryRepository()));
	}

	@Test
	void aCodeDeclaredTwiceFailsBeforeAnythingIsWritten() {
		assertThrows(IllegalStateException.class,
				() -> new TestSeeder(List.of(declaration("twice", "corporate-dav", "KB", "/A", true),
						declaration("TWICE", "corporate-dav", "KB", "/B", true)), new InMemoryRepository()));
	}

	@Test
	void aDeclarationWithoutASystemFailsBeforeAnythingIsWritten() {
		assertThrows(IllegalStateException.class, () -> new TestSeeder(
				List.of(declaration("orphan", " ", "KB", "/Policies", true)), new InMemoryRepository()));
	}

	@Test
	void aDeclarationWithoutPathsFailsBeforeAnythingIsWritten() {
		GDeclaredDataSource declared = declaration("empty", "corporate-dav", "KB", "/Policies", true);
		declared.setPaths(List.of());

		assertThrows(IllegalStateException.class, () -> new TestSeeder(List.of(declared), new InMemoryRepository()));
	}

	@Test
	void aPathTheModuleCannotResolveFailsNamingTheSourceAndThePath() {
		IllegalStateException thrown = assertThrows(IllegalStateException.class,
				() -> new TestSeeder(List.of(declaration("broken", "corporate-dav", "KB", "!nonsense", true)),
						new InMemoryRepository()));

		assertTrue(thrown.getMessage().contains("broken"));
		assertTrue(thrown.getMessage().contains("!nonsense"));
	}

	@Test
	void noDeclarationsWritesNothing() {
		InMemoryRepository repository = new InMemoryRepository();

		new TestSeeder(null, repository).seed();

		assertEquals(0, repository.count());
	}
}
