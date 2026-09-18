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

import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.architecture.patterns.IGDynamicConfigurationSource;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;

/**
 * Implementation parent of the per-module content management system
 * configuration DAOs: it combines the systems a deployment declares in its own
 * {@code application.yml}, under {@code ai.gebo.<content handler>.systems}, with
 * the ones an admin created through the UI and the module's Mongo repository
 * holds.
 *
 * <p>
 * {@link GAbstractRuntimeConfigurationDao} already merges a static list with a
 * dynamic source, and that merge is exactly what is wanted here - the
 * declarations are the static half, the repository-backed
 * {@link IGDynamicConfigurationSource} the dynamic one. What it does not do is
 * anything about the two halves naming the SAME system, and that is what this
 * class adds, in the shape the declarative secrets of
 * {@code ai.gebo.secrets.config} already established:
 * </p>
 *
 * <ul>
 * <li><b>The declaration wins.</b> A code declared in the configuration is the
 * deployment's answer for that code: a stored record carrying it is dropped from
 * {@link #getConfigurations()} and never returned by
 * {@link #findByCode(String)}, instead of both surfacing and the admin list
 * showing the same system twice. Nothing written later can displace what the
 * file says.</li>
 * <li><b>The handled type is implied.</b> A module that handles exactly one
 * {@code GContentManagementSystemType} passes its code here, and a declared
 * system that does not name a {@code contentManagementSystemType} is given it -
 * so the deployment does not have to repeat a constant that has only one
 * possible value, and a declared system is dispatchable exactly like a stored
 * one. Modules with more than one handled type (Git) pass {@code null} and
 * require the entry to name its own.</li>
 * <li><b>A broken declaration fails the startup</b>, rather than a system
 * quietly missing at the first ingestion: a blank code, or the same code
 * declared twice.</li>
 * <li><b>A declared system is marked {@code readonly}.</b> The marker travels to
 * the admin UI, where the editing component disables save and delete on it, and
 * the write paths of {@code GAbstractSystemsArchitectureController} refuse it
 * outright - so the store stays free of records the read chain could never
 * reach. The filesystem and MCP handlers already use the flag this way for their
 * own non-editable singleton system.</li>
 * </ul>
 *
 * <p>
 * Systems are declared, not secrets: an entry carries the {@code secretCode} of
 * a credential that lives in the secrets management layer - declared under
 * {@code ai.gebo.secrets.config} or created in the admin UI - never the
 * credential itself.
 * </p>
 *
 * Gebo.ai comment agent
 *
 * @param <SystemType> the concrete content management system type of the module
 */
public abstract class GAbstractContentManagementSystemConfigurationDao<SystemType extends GContentManagementSystem>
		extends GAbstractRuntimeConfigurationDao<SystemType>
		implements IGContentManagementSystemConfigurationDao<SystemType> {

	/**
	 * Constructs the DAO over the declared systems and the module's dynamic
	 * (repository-backed) source.
	 *
	 * @param declaredSystems       the systems declared under
	 *                              {@code ai.gebo.<content handler>.systems}; may
	 *                              be {@code null} or empty when the deployment
	 *                              declares none.
	 * @param dynamic               the repository-backed source of the systems
	 *                              created through the admin UI; may be
	 *                              {@code null}.
	 * @param handledSystemTypeCode the code of the single content management system
	 *                              type the module handles, applied to a declared
	 *                              system that does not name one, or {@code null}
	 *                              for a module handling several.
	 */
	protected GAbstractContentManagementSystemConfigurationDao(List<SystemType> declaredSystems,
			IGDynamicConfigurationSource<SystemType> dynamic, String handledSystemTypeCode) {
		super(validated(declaredSystems, handledSystemTypeCode), dynamic);
	}

	/**
	 * Applies the implied handled type and refuses a declaration that could not be
	 * resolved later on.
	 *
	 * @param declaredSystems       the declared systems, possibly {@code null}.
	 * @param handledSystemTypeCode the implied type code, possibly {@code null}.
	 * @param <SystemType>          the concrete content management system type.
	 * @return the declared systems, never {@code null}.
	 */
	private static <SystemType extends GContentManagementSystem> List<SystemType> validated(
			List<SystemType> declaredSystems, String handledSystemTypeCode) {
		List<SystemType> returned = new ArrayList<SystemType>();
		if (declaredSystems == null) {
			return returned;
		}
		Map<String, SystemType> byCode = new LinkedHashMap<String, SystemType>();
		for (SystemType system : declaredSystems) {
			if (system == null) {
				continue;
			}
			String code = system.getCode();
			if (code == null || code.trim().length() == 0) {
				throw new IllegalStateException(
						"A content management system declared in the configuration has no code: every entry of the systems list must carry the code the rest of the configuration references");
			}
			if (byCode.put(code.toLowerCase(), system) != null) {
				throw new IllegalStateException("The content management system code '" + code
						+ "' is declared more than once in the configuration: a code identifies exactly one system");
			}
			if (handledSystemTypeCode != null && (system.getContentManagementSystemType() == null
					|| system.getContentManagementSystemType().trim().length() == 0)) {
				system.setContentManagementSystemType(handledSystemTypeCode);
			}
			// The configuration owns this system: the marker is what disables save and
			// delete in the admin UI and what the controller write paths refuse on. It is
			// the same use the filesystem and MCP handlers already make of the flag for
			// their own non-editable singleton system.
			system.setReadonly(true);
			returned.add(system);
		}
		return returned;
	}

	/**
	 * Tells whether the given code is one the configuration declares - in which case
	 * it is read from the file and no stored record can answer for it.
	 *
	 * @param code the system code to check.
	 * @return {@code true} when the code is declared in the configuration.
	 */
	public boolean isDeclaredInConfiguration(String code) {
		return findDeclaredByCode(code) != null;
	}

	/**
	 * Finds a declared system by code, without consulting the repository.
	 *
	 * @param code the system code to look for.
	 * @return the declared system, or {@code null} when the code is not declared.
	 */
	protected SystemType findDeclaredByCode(String code) {
		if (code == null || staticConfigs == null) {
			return null;
		}
		for (SystemType system : staticConfigs) {
			if (code.equalsIgnoreCase(system.getCode())) {
				return system;
			}
		}
		return null;
	}

	/**
	 * The declared systems followed by the stored ones, with a stored system whose
	 * code is declared left out: the configuration is the answer for the codes it
	 * names.
	 *
	 * @return every system this module knows about.
	 */
	@Override
	public List<SystemType> getConfigurations() {
		List<SystemType> returned = new ArrayList<SystemType>();
		if (staticConfigs != null) {
			returned.addAll(staticConfigs);
		}
		List<SystemType> stored = getDynamicConfigs();
		if (stored != null) {
			for (SystemType system : stored) {
				if (system != null && !isDeclaredInConfiguration(system.getCode())) {
					returned.add(system);
				}
			}
		}
		return returned;
	}

	/**
	 * Resolves a system by code, the declared ones first.
	 *
	 * @param code the system code.
	 * @return the matching system, or {@code null} when there is none.
	 */
	@Override
	public SystemType findByCode(String code) {
		SystemType declared = findDeclaredByCode(code);
		if (declared != null) {
			return declared;
		}
		return findByPredicate((x) -> {
			return code != null && x.getCode() != null && x.getCode().equalsIgnoreCase(code);
		});
	}
}
