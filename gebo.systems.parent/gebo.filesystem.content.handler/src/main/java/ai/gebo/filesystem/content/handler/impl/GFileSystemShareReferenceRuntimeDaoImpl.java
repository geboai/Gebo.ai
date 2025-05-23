/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.filesystem.content.handler.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.architecture.patterns.IGDynamicConfigurationSource;
import ai.gebo.filesystem.content.handler.GFileSystemShareReference;
import ai.gebo.filesystem.content.handler.IGFileSystemShareReferenceRuntimeDao;
import ai.gebo.filesystem.content.handler.config.GeboAiFilesystemsConfig;
import ai.gebo.filesystem.content.handler.config.GeboFileShareConfig;
import ai.gebo.model.virtualfs.VFilesystemReference;

/**
 * AI generated comments
 * Implementation of IGFileSystemShareReferenceRuntimeDao that manages filesystem share references.
 * This service provides runtime access to file system share configurations.
 */
@Service
public class GFileSystemShareReferenceRuntimeDaoImpl extends GAbstractRuntimeConfigurationDao<GFileSystemShareReference>
		implements IGFileSystemShareReferenceRuntimeDao {
	
	/**
	 * Constructor that initializes the runtime DAO with configurations.
	 * 
	 * @param config The filesystem configuration containing share definitions
	 * @param dynamic The dynamic configuration source for share references
	 */
	public GFileSystemShareReferenceRuntimeDaoImpl(GeboAiFilesystemsConfig config,
			IGDynamicConfigurationSource<GFileSystemShareReference> dynamic) {
		super(cloneShares(config.getShares()), dynamic);

	}

	/**
	 * Converts configuration share objects to GFileSystemShareReference objects.
	 * Validates that each share path exists, is a directory, and is readable.
	 * 
	 * @param list List of share configs to process
	 * @return List of valid file system share references
	 */
	private static List<GFileSystemShareReference> cloneShares(List<GeboFileShareConfig> list) {

		return list == null ? List.of() : list.stream().filter(x -> {
			boolean ok = x.getAbsolutePath() != null;
			if (ok) {
				Path path = Path.of(x.getAbsolutePath());
				// Check if path exists, is a directory and is readable
				if (Files.exists(path) && Files.isDirectory(path) && Files.isReadable(path)) {
					ok = true;
				} else {
					ok = false;
				}
			}
			return ok;
		}).map(x -> {
			// Create a new share reference from the config
			GFileSystemShareReference sharereference = new GFileSystemShareReference();
			sharereference.setCode(x.getAbsolutePath());
			sharereference.setDescription(x.getDescription());
			sharereference.setReference(VFilesystemReference.from(Path.of(x.getAbsolutePath())));
			sharereference.setMongoConfigured(false);
			return sharereference;
		}).toList();
	}

	/**
	 * Finds a share reference by its code (path).
	 * First checks the dynamic configuration source, then falls back to the predicate search.
	 * 
	 * @param code The code (absolute path) to search for
	 * @return The matching share reference or null if not found
	 */
	@Override
	public GFileSystemShareReference findByCode(String code) {
		if (code != null) {
			GFileSystemShareReference found = dynamic.findByCode(code);
			if (found != null)
				return found;
		}
		return findByPredicate(x -> {
			return x.getCode() != null && code != null && x.getCode().equals(code);
		});
	}

}