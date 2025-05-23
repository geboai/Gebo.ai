/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.patterns.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.architecture.patterns.IGRuntimeModuleComponent;
import ai.gebo.architecture.patterns.IGRuntimeModuleComponentsDao;
import ai.gebo.architecture.patterns.model.GModuleUseInfo;

/**
 * Gebo.ai comment agent
 * Implementation of the IGRuntimeModuleComponentsDao interface, providing
 * runtime configuration management for module components.
 * This class extends GAbstractRuntimeConfigurationDao to leverage shared 
 * configuration management logic.
 */
@Service
public class GRuntimeModuleComponentsDaoImpl extends GAbstractRuntimeConfigurationDao<IGRuntimeModuleComponent>
		implements IGRuntimeModuleComponentsDao {

	/**
	 * Constructs a new GRuntimeModuleComponentsDaoImpl with an optional 
	 * list of static configurations.
	 * @param staticConfigs optional list of IGRuntimeModuleComponent instances
	 * to be managed by this DAO.
	 */
	public GRuntimeModuleComponentsDaoImpl(@Autowired(required = false) List<IGRuntimeModuleComponent> staticConfigs) {
		super(staticConfigs, null);

	}

	/**
	 * Finds a runtime module component by its code.
	 * @param code the code of the IGRuntimeModuleComponent to find.
	 * @return the IGRuntimeModuleComponent instance matching the code, or null if not found.
	 */
	@Override
	public IGRuntimeModuleComponent findByCode(String code) {
		// Uses a predicate to search within the managed components by ID.
		return this.findByPredicate(x -> x.getId().equals(code));
	}

	/**
	 * Retrieves usage information for all module components.
	 * @return a list of GModuleUseInfo representing usage statistics for each managed component.
	 */
	@Override
	public List<GModuleUseInfo> getModuleUseInfo() {
		// Initializes a new list to gather usage statistics.
		List<GModuleUseInfo> useStats = new ArrayList<GModuleUseInfo>();
		for (IGRuntimeModuleComponent igModuleComponent : staticConfigs) {
			// Aggregates module use information from each component.
			useStats.addAll(igModuleComponent.getModuleUseInfo());
		}
		return useStats;
	}

}