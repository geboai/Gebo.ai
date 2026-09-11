/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.systems.abstraction.layer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;

import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.architecture.persistence.GAbstractDeclaredEntitiesSeeder;
import ai.gebo.knowledgebase.repositories.IGBaseMongoDBProjectEndpointRepository;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSource;
import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSourcePath;

/**
 * Writes the data sources a deployment declares under
 * {@code ai.gebo.<content handler>.datasources} into the module's own endpoint
 * repository at startup.
 *
 * <h2>Why the declarations are stored rather than merged at read time</h2>
 * <p>
 * The system side can serve declarations straight from the configuration,
 * because a system is only ever resolved through its handler's DAO. A project
 * endpoint is not: publishing, the central scheduler and
 * {@code JobLauncherController} all reach an endpoint through
 * {@code IGPersistentObjectManager} - by {@code GObjectRef}, or through the
 * flattened {@code GCentralizedProjectEndpoint} a reschedule carries - and none
 * of those paths knows about a handler DAO. A declared source that existed only
 * in memory would therefore list and browse, and then fail the moment anyone
 * tried to ingest it. Seeding the repository makes it a real record, and every
 * one of those paths works on it unchanged.
 * </p>
 *
 * <h2>What the seeding may and may not overwrite</h2>
 * <p>
 * A declared code may only take over a record that is itself
 * {@code readonly} - one this seeder wrote on an earlier boot. A record an admin
 * created through the UI carries no such marker, and overwriting it would throw
 * away work nobody asked to lose, so that fails the startup instead, naming the
 * code. Renaming the declaration or deleting the UI record is a decision for a
 * person, not for a boot sequence.
 * </p>
 *
 * <p>
 * The reverse case - a {@code readonly} record whose declaration has since been
 * removed from the file - is <b>not</b> deleted here. Deleting an endpoint
 * properly means replicating the removal and dispatching the disposal messages
 * that clear its documents and vectors, which
 * {@code GAbstractSystemsArchitectureController} does and a repository write
 * cannot. The marker is cleared instead, which hands the record back to the
 * admin UI: delete stops being disabled on it, and an admin can remove it the
 * way any other source is removed. It is logged as a warning so it is not a
 * silent change.
 * </p>
 *
 * Gebo.ai comment agent
 *
 * @param <EndpointType>   the concrete project endpoint type of the module
 * @param <RepositoryType> the module's endpoint repository
 */
public abstract class GAbstractDeclaredDataSourcesSeeder<EndpointType extends GProjectEndpoint, RepositoryType extends IGBaseMongoDBProjectEndpointRepository<EndpointType>>
		implements ApplicationListener<ContextRefreshedEvent>, Ordered {

	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

	/** The declared sources, as the configuration binder produced them. */
	private final List<GDeclaredDataSource> declarations;

	/** The module's endpoint repository, the one the handler DAO reads. */
	private final RepositoryType repository;

	/**
	 * This bean's own application context, so a {@link ContextRefreshedEvent}
	 * bubbling up from an unrelated descendant context does not re-run the seeding
	 * - the same guard {@code MessageBrokeringAssembler} applies for the same
	 * reason.
	 */
	private final ApplicationContext applicationContext;

	/**
	 * Keeps the declarations as they were bound. Validation and translation happen
	 * in {@link #seed()}, once the context is up: a module may need to resolve the
	 * system a declaration names - WebDAV reads its {@code baseUri} to make a
	 * relative path absolute - and that is not available while beans are still
	 * being constructed.
	 *
	 * <p>
	 * Every declaration is translated before ANY of them is written, so a
	 * declaration the module cannot make sense of still fails the startup with a
	 * repository untouched, rather than leaving it half seeded.
	 * </p>
	 *
	 * @param declarations       the data sources declared under
	 *                           {@code ai.gebo.<content handler>.datasources}; may
	 *                           be {@code null} or empty.
	 * @param repository         the module's endpoint repository.
	 * @param applicationContext this bean's own context.
	 */
	protected GAbstractDeclaredDataSourcesSeeder(List<GDeclaredDataSource> declarations, RepositoryType repository,
			ApplicationContext applicationContext) {
		this.repository = repository;
		this.applicationContext = applicationContext;
		this.declarations = declarations;
	}

	/**
	 * Builds the module's concrete endpoint from a declaration, setting the
	 * module's own system-code field. The shared fields - code, description,
	 * project, paths, the {@code readonly} marker - are applied by this class
	 * around it.
	 *
	 * @param declaration the declared data source.
	 * @return a new endpoint instance carrying at least the system reference.
	 */
	protected abstract EndpointType newEndpoint(GDeclaredDataSource declaration);

	/**
	 * Translates one declared path into the reference the module's navigation
	 * understands - the same encoding the browsing UI stores for a source an admin
	 * assembles by clicking.
	 *
	 * @param declaration  the data source the path belongs to, so an implementation
	 *                     can resolve the system it names.
	 * @param declaredPath the declared path and its folder flag.
	 * @return the reference to ingest from.
	 * @throws IllegalStateException when the path is not valid for this module; the
	 *                               message is shown as a startup failure, so it
	 *                               should say what the module expected.
	 */
	protected abstract VFilesystemReference toReference(GDeclaredDataSource declaration,
			GDeclaredDataSourcePath declaredPath);

	/**
	 * Stores the translated references on the endpoint. Every endpoint this covers
	 * extends {@code GVirtualFilesystemProjectEndpoint} and so already has a
	 * {@code paths} list; the setter is left to the concrete seeder because the
	 * shared supertype here is only {@code GProjectEndpoint}.
	 *
	 * @param endpoint   the endpoint being built.
	 * @param references the translated paths.
	 */
	protected abstract void setPaths(EndpointType endpoint, List<VFilesystemReference> references);

	/**
	 * Seeds the repository once this bean's own context is up, which is the first
	 * moment the repository is usable.
	 *
	 * @param event the refresh event.
	 */
	/**
	 * Last of the hierarchy: a data source names the project it feeds, so the
	 * projects - and the knowledge bases they belong to - are written first.
	 *
	 * @return the seeding order.
	 */
	@Override
	public int getOrder() {
		return GAbstractDeclaredEntitiesSeeder.DATA_SOURCE_ORDER;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (applicationContext != null && event.getApplicationContext() != applicationContext) {
			return;
		}
		seed();
	}

	/**
	 * Writes every declared source to the repository and hands back the records
	 * whose declaration is gone.
	 */
	protected void seed() {
		List<EndpointType> declaredEndpoints = translateAll(declarations);
		for (EndpointType declared : declaredEndpoints) {
			Optional<EndpointType> existing = repository.findById(declared.getCode());
			if (existing.isPresent() && !Boolean.TRUE.equals(existing.get().getReadonly())) {
				throw new IllegalStateException("The data source '" + declared.getCode()
						+ "' is declared in the configuration but a data source with that code was created through the admin UI:"
						+ " the configuration will not overwrite it. Rename the declaration, or delete the existing data source first.");
			}
			repository.save(declared);
			LOGGER.info("Data source {} declared in the configuration is available", declared.getCode());
		}
		releaseUndeclared(declaredEndpoints);
	}

	/**
	 * Clears the {@code readonly} marker on stored sources this seeder wrote on an
	 * earlier boot and the configuration no longer declares, so an admin can delete
	 * them through the UI - with the disposal a repository write cannot perform.
	 */
	private void releaseUndeclared(List<EndpointType> declaredEndpoints) {
		List<String> declaredCodes = new ArrayList<String>();
		for (EndpointType declared : declaredEndpoints) {
			declaredCodes.add(declared.getCode().toLowerCase());
		}
		for (EndpointType stored : repository.findAll()) {
			if (!Boolean.TRUE.equals(stored.getReadonly()) || stored.getCode() == null
					|| declaredCodes.contains(stored.getCode().toLowerCase())) {
				continue;
			}
			stored.setReadonly(false);
			repository.save(stored);
			LOGGER.warn(
					"The data source {} is no longer declared in the configuration: it has been left in place and is now editable"
							+ " in the admin UI, where it can be deleted with the content it produced.",
					stored.getCode());
		}
	}

	/**
	 * Translates every declaration, refusing the ones that could not be resolved
	 * later on.
	 *
	 * @param declarations the declarations, possibly {@code null}.
	 * @return the declared endpoints, never {@code null}.
	 */
	private List<EndpointType> translateAll(List<GDeclaredDataSource> declarations) {
		List<EndpointType> returned = new ArrayList<EndpointType>();
		if (declarations == null) {
			return returned;
		}
		Map<String, GDeclaredDataSource> byCode = new LinkedHashMap<String, GDeclaredDataSource>();
		for (GDeclaredDataSource declaration : declarations) {
			if (declaration == null) {
				continue;
			}
			String code = declaration.getCode();
			if (code == null || code.trim().length() == 0) {
				throw new IllegalStateException(
						"A data source declared in the configuration has no code: every entry of the datasources list must carry the code the rest of the configuration references");
			}
			if (byCode.put(code.toLowerCase(), declaration) != null) {
				throw new IllegalStateException("The data source code '" + code
						+ "' is declared more than once in the configuration: a code identifies exactly one data source");
			}
			if (declaration.getSystemCode() == null || declaration.getSystemCode().trim().length() == 0) {
				throw new IllegalStateException("The data source '" + code
						+ "' declares no systemCode: a data source reads through a content management system");
			}
			if (declaration.getPaths() == null || declaration.getPaths().isEmpty()) {
				throw new IllegalStateException("The data source '" + code
						+ "' declares no path: a data source with no path would connect and read nothing");
			}
			returned.add(translate(declaration));
		}
		return returned;
	}

	/**
	 * Applies the declaration to a module endpoint: the shared endpoint fields, the
	 * translated paths, and the {@code readonly} marker.
	 *
	 * @param declaration the declared data source.
	 * @return the endpoint to store.
	 */
	private EndpointType translate(GDeclaredDataSource declaration) {
		EndpointType endpoint = newEndpoint(declaration);
		endpoint.setCode(declaration.getCode());
		endpoint.setDescription(declaration.getDescription());
		endpoint.setParentProjectCode(declaration.getParentProjectCode());
		endpoint.setPublished(declaration.getPublished());
		endpoint.setSynchPeriodically(declaration.getSynchPeriodically());
		endpoint.setProgrammedTables(declaration.getProgrammedTables());
		endpoint.setOpenZips(declaration.getOpenZips());
		if (declaration.getPersonalData() != null) {
			endpoint.setPersonalData(declaration.getPersonalData());
		}
		endpoint.setVectorizeOnlyExtensions(declaration.getVectorizeOnlyExtensions());
		// The configuration owns this source: the marker is what disables save and
		// delete in the admin UI, what the controller write paths refuse on, and what
		// tells a later boot that this record is the seeder's to overwrite.
		endpoint.setReadonly(true);
		setPaths(endpoint, translatePaths(declaration));
		return endpoint;
	}

	/**
	 * Translates the declared paths, naming the offending data source when a path
	 * is not valid for the module.
	 *
	 * @param declaration the declared data source.
	 * @return the references to ingest from.
	 */
	private List<VFilesystemReference> translatePaths(GDeclaredDataSource declaration) {
		List<VFilesystemReference> references = new ArrayList<VFilesystemReference>();
		for (GDeclaredDataSourcePath declaredPath : declaration.getPaths()) {
			if (declaredPath == null || declaredPath.getPath() == null
					|| declaredPath.getPath().trim().length() == 0) {
				throw new IllegalStateException(
						"The data source '" + declaration.getCode() + "' declares an entry with no path");
			}
			try {
				references.add(toReference(declaration, declaredPath));
			} catch (RuntimeException e) {
				throw new IllegalStateException("The data source '" + declaration.getCode() + "' declares the path '"
						+ declaredPath.getPath() + "' which this content handler cannot resolve: " + e.getMessage(), e);
			}
		}
		return references;
	}

}
