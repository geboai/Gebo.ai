/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.persistence;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;

import ai.gebo.model.base.GBaseObject;

/**
 * Writes the records a deployment declares in its own {@code application.yml}
 * into a Mongo repository at startup, so that the whole knowledge base
 * hierarchy - knowledge bases, projects, and the data sources under them - can
 * be described by the configuration instead of assembled by hand in the admin
 * UI.
 *
 * <h2>Why these are stored rather than served from the configuration</h2>
 * <p>
 * A content management system can be served straight from its handler's DAO,
 * because that DAO is the only thing that ever resolves one. A knowledge base, a
 * project and a project endpoint are not: they are reached through
 * {@link IGPersistentObjectManager} by code from every corner of the
 * application - ingestion, the scheduler, the browsing services, the ACL
 * resolver - and none of those paths consults a configuration bean. A declared
 * record that existed only in memory would list and then fail the first time
 * anything tried to use it, so it is written to the repository and every path
 * works on it unchanged.
 * </p>
 *
 * <h2>What the seeding may and may not overwrite</h2>
 * <p>
 * A declared code may only take over a record that is itself {@code readonly} -
 * one this seeder wrote on an earlier boot. A record an admin created carries no
 * such marker, and overwriting it would throw away work nobody asked to lose, so
 * that fails the startup instead, naming the code.
 * </p>
 *
 * <p>
 * A {@code readonly} record whose declaration has since been removed is
 * <b>not</b> deleted: deleting a project or a knowledge base means disposing of
 * everything beneath it, which a repository write cannot do. Its marker is
 * cleared instead, which hands it back to the admin UI - save and delete stop
 * being disabled - and it is logged as a warning so the change is not silent.
 * </p>
 *
 * <h2>Ordering</h2>
 * <p>
 * The hierarchy is seeded top down, because each level references the one above
 * it by code: knowledge bases, then projects, then data sources. Concrete
 * seeders express that through {@link #getOrder()}; see
 * {@link #KNOWLEDGE_BASE_ORDER} and the constants beside it.
 * </p>
 *
 * Gebo.ai comment agent
 *
 * @param <EntityType>     the declared record type
 * @param <RepositoryType> the repository that stores it
 */
public abstract class GAbstractDeclaredEntitiesSeeder<EntityType extends GBaseObject, RepositoryType extends IGBaseMongoDBRepository<EntityType>>
		implements ApplicationListener<ContextRefreshedEvent>, Ordered {

	/** Knowledge bases come first: everything else references one. */
	public static final int KNOWLEDGE_BASE_ORDER = 100;

	/** Projects, which name the knowledge base they belong to. */
	public static final int PROJECT_ORDER = 200;

	/** Data sources, which name the project they feed. */
	public static final int DATA_SOURCE_ORDER = 300;

	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

	private final RepositoryType repository;

	/**
	 * This bean's own application context, so a {@link ContextRefreshedEvent}
	 * bubbling up from an unrelated descendant context does not re-run the seeding.
	 */
	private final ApplicationContext applicationContext;

	/**
	 * @param repository         the repository the declared records are written to.
	 * @param applicationContext this bean's own context.
	 */
	protected GAbstractDeclaredEntitiesSeeder(RepositoryType repository, ApplicationContext applicationContext) {
		this.repository = repository;
		this.applicationContext = applicationContext;
	}

	/**
	 * The records this deployment declares, as the configuration binder produced
	 * them. Read at seeding time rather than kept, so a concrete seeder does not
	 * have to resolve anything while beans are still being constructed.
	 *
	 * @return the declared records; may be empty, never {@code null}.
	 */
	protected abstract List<EntityType> getDeclarations();

	/**
	 * Sets the {@code readonly} marker on a declared record. The field is declared
	 * per type rather than on the shared base, so the setter cannot be reached
	 * generically.
	 *
	 * @param entity the record being seeded.
	 * @param value  the value to set.
	 */
	protected abstract void setReadonly(EntityType entity, Boolean value);

	/**
	 * Reads the {@code readonly} marker off a stored record.
	 *
	 * @param entity the stored record.
	 * @return the marker, possibly {@code null}.
	 */
	protected abstract Boolean getReadonly(EntityType entity);

	/**
	 * Applies the defaults a declared record should carry when the declaration
	 * left them unset. The base does nothing; a concrete seeder overrides it to
	 * open the record up - a deployment-declared knowledge base or project is
	 * visible to everyone by default, since it is part of the deployment rather
	 * than a user's private space.
	 *
	 * @param entity the record about to be written.
	 */
	protected void applyDeclarationDefaults(EntityType entity) {
	}

	/**
	 * A word for this kind of record, used in the startup messages - "knowledge
	 * base", "project".
	 *
	 * @return the noun to use in messages.
	 */
	protected abstract String describeKind();

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (applicationContext != null && event.getApplicationContext() != applicationContext) {
			return;
		}
		seed();
	}

	/**
	 * Validates every declaration, then writes them, then hands back the records
	 * whose declaration is gone. Nothing is written unless all of them are valid.
	 */
	protected void seed() {
		List<EntityType> declared = validated(getDeclarations());
		for (EntityType entity : declared) {
			Optional<EntityType> existing = repository.findById(entity.getCode());
			if (existing.isPresent() && !Boolean.TRUE.equals(getReadonly(existing.get()))) {
				throw new IllegalStateException("The " + describeKind() + " '" + entity.getCode()
						+ "' is declared in the configuration but one with that code was created through the admin UI:"
						+ " the configuration will not overwrite it. Rename the declaration, or delete the existing one first.");
			}
			setReadonly(entity, Boolean.TRUE);
			applyDeclarationDefaults(entity);
			repository.save(entity);
			LOGGER.info("The {} {} declared in the configuration is available", describeKind(), entity.getCode());
		}
		releaseUndeclared(declared);
	}

	/**
	 * Refuses a declaration that could not be resolved later on: a blank code, or
	 * the same code declared twice.
	 *
	 * @param declarations the declared records, possibly {@code null}.
	 * @return the declarations, never {@code null}.
	 */
	private List<EntityType> validated(List<EntityType> declarations) {
		List<EntityType> returned = new ArrayList<EntityType>();
		if (declarations == null) {
			return returned;
		}
		Set<String> codes = new LinkedHashSet<String>();
		for (EntityType entity : declarations) {
			if (entity == null) {
				continue;
			}
			String code = entity.getCode();
			if (code == null || code.trim().length() == 0) {
				throw new IllegalStateException("A " + describeKind()
						+ " declared in the configuration has no code: every entry must carry the code the rest of the configuration references");
			}
			if (!codes.add(code.toLowerCase())) {
				throw new IllegalStateException("The " + describeKind() + " code '" + code
						+ "' is declared more than once in the configuration: a code identifies exactly one record");
			}
			returned.add(entity);
		}
		return returned;
	}

	/**
	 * Clears the {@code readonly} marker on stored records this seeder wrote on an
	 * earlier boot and the configuration no longer declares, so an admin can remove
	 * them through the UI - with the disposal a repository write cannot perform.
	 *
	 * @param declared the records declared on this boot.
	 */
	private void releaseUndeclared(List<EntityType> declared) {
		List<String> declaredCodes = new ArrayList<String>();
		for (EntityType entity : declared) {
			declaredCodes.add(entity.getCode().toLowerCase());
		}
		for (EntityType stored : repository.findAll()) {
			if (!Boolean.TRUE.equals(getReadonly(stored)) || stored.getCode() == null
					|| declaredCodes.contains(stored.getCode().toLowerCase())) {
				continue;
			}
			setReadonly(stored, Boolean.FALSE);
			repository.save(stored);
			LOGGER.warn(
					"The {} {} is no longer declared in the configuration: it has been left in place and is now editable"
							+ " in the admin UI, where it can be deleted with everything beneath it.",
					describeKind(), stored.getCode());
		}
	}

	/**
	 * Applies a change to every stored record the configuration owns - what a
	 * concrete seeder uses when it has to touch them outside the seeding itself.
	 *
	 * @param action what to do with each configuration-owned record.
	 */
	protected void forEachDeclaredStored(Consumer<EntityType> action) {
		for (EntityType stored : repository.findAll()) {
			if (Boolean.TRUE.equals(getReadonly(stored))) {
				action.accept(stored);
			}
		}
	}

	/**
	 * Whether the stored record with this code is owned by the configuration. The
	 * STORED record decides, not an object a client sent back.
	 *
	 * @param code the record code.
	 * @return true when the stored record carries the {@code readonly} marker.
	 */
	public boolean isDeclaredInConfiguration(String code) {
		if (code == null) {
			return false;
		}
		return repository.findById(code).map(this::getReadonly).map(Boolean.TRUE::equals).orElse(false);
	}

	/**
	 * Resolves a stored record by code, for a caller that needs the record itself
	 * rather than just the marker.
	 *
	 * @param code   the record code.
	 * @param mapper what to read off it.
	 * @param <R>    the read result.
	 * @return the mapped value, or {@code null} when there is no such record.
	 */
	protected <R> R readStored(String code, Function<EntityType, R> mapper) {
		return code == null ? null : repository.findById(code).map(mapper).orElse(null);
	}
}
