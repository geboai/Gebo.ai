/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.buildsystems.abstraction.layer;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.knlowledgebase.model.contents.GDependencyTree;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.knlowledgebase.model.systems.GAbstractBuildSystemConfig;
import ai.gebo.knlowledgebase.model.systems.GBuildSystem;
import ai.gebo.knlowledgebase.model.systems.GBuildSystemType;

/**
 * Gebo.ai comment agent
 * 
 * Interface representing a build system handler that operates on a specific type 
 * of build system configuration. Provides methods for configuring, handling, 
 * and retrieving metadata from build systems.
 */
public interface IGBuildSystemHandler<ConfigType extends GAbstractBuildSystemConfig> {
	
	/**
	 * Returns the type of build system that this handler is capable of processing.
	 *
	 * @return the type of build system handled by this handler.
	 */
	public GBuildSystemType getHandledSystemType();

	/**
	 * Automatically configures a build system based on a given file path.
	 *
	 * @param path the file path from which to autoconfigure the build system.
	 * @return a configured build system.
	 * @throws GeboContentHandlerSystemException if an error occurs during configuration.
	 */
	public GBuildSystem<ConfigType> autoconfigure(File path) throws GeboContentHandlerSystemException;

	/**
	 * Extracts metadata information regarding deliverables from a build system configuration.
	 *
	 * @param buildSystemConfig the build system configuration.
	 * @param item the virtual folder item representing the source of deliverables.
	 * @param path the file path to process.
	 * @return a list of dependency trees representing the deliverables' metadata.
	 * @throws GeboContentHandlerSystemException if an error occurs during metadata extraction.
	 */
	public List<GDependencyTree> extractDeliverablesMetaInfos(GBuildSystem<ConfigType> buildSystemConfig,
			GVirtualFolder item, Path path) throws GeboContentHandlerSystemException;

	/**
	 * Consumes additional custom metadata information from a build system configuration.
	 *
	 * @param buildSystemConfig the build system configuration.
	 * @param item the virtual folder item containing additional metadata.
	 * @param path the file path associated with the metadata.
	 * @param consumer the content consumer to process the additional metadata.
	 * @throws GeboContentHandlerSystemException if an error occurs during metadata consumption.
	 */
	public void consumeCustomAdditionalMetaInformations(GBuildSystem<ConfigType> buildSystemConfig, GVirtualFolder item,
			Path path, IGContentConsumer consumer) throws GeboContentHandlerSystemException;

	/**
	 * Finds a build system configuration by its unique code.
	 *
	 * @param code the unique code of the build system configuration.
	 * @return the build system configuration corresponding to the given code, or null if not found.
	 */
	public GBuildSystem<ConfigType> findConfigurationByCode(String code);

	/**
	 * Retrieves all available build system configurations handled by this handler.
	 *
	 * @return a list of all build system configurations.
	 */
	public List<GBuildSystem<ConfigType>> getConfigurations();
}