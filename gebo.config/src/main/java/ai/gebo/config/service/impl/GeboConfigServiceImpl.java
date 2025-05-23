/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.config.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.config.repositories.MongoGeboInstallationRepository;
import ai.gebo.architecture.environment.EnvironmentHolder;
import ai.gebo.config.GeboConfig;
import ai.gebo.config.service.IGGeboConfigService;

/**
 * Service implementation for managing Gebo configuration settings.
 * 
 * This class provides methods to access and verify configuration-related information,
 * such as the home and work directory paths for the Gebo environment.
 * 
 * Gebo.ai Commentor - AI generated comments
 */
@Service
public class GeboConfigServiceImpl implements IGGeboConfigService {

	// The Gebo configuration settings
	final GeboConfig geboConfig;

	

	// Repository for accessing MongoGeboInstallation data
	final MongoGeboInstallationRepository geboInstallationRepository;

	/**
	 * Constructs a new GeboConfigServiceImpl with specified configuration settings,
	 * configuration database service, and MongoGeboInstallation repository.
	 * 
	 * @param geboConfig               The Gebo configuration settings
	 * @param configDBService          The service for configuration database
	 * @param geboInstallationRepository The repository for MongoGeboInstallation
	 */
	public GeboConfigServiceImpl(GeboConfig geboConfig,
			MongoGeboInstallationRepository geboInstallationRepository) {
		this.geboConfig = geboConfig;
		this.geboInstallationRepository = geboInstallationRepository;
	}

	/**
	 * Returns the Gebo home directory path from the environment.
	 * 
	 * @return A string representing the Gebo home directory path.
	 */
	@Override
	public String getGeboHome() {
		return EnvironmentHolder.GEBO_HOME_ENVIRONMENT_VALUE;
	}

	/**
	 * Returns the Gebo work directory path. It first checks if work directory is user-configured;
	 * if not, it retrieves the value from the environment or throws an exception if not set.
	 * 
	 * @return A string representing the Gebo work directory path.
	 */
	@Override
	public String getGeboWorkDirectory() {
		if (geboConfig.getSetupConfiguresWorkdir() == null || !geboConfig.getSetupConfiguresWorkdir()) {
			if (EnvironmentHolder.GEBO_WORK_DIRECTORY_ENVIRONMENT_VALUE != null) {
				return EnvironmentHolder.GEBO_WORK_DIRECTORY_ENVIRONMENT_VALUE;
			} else {
				throw new RuntimeException(EnvironmentHolder.GEBO_WORK_DIRECTORY + " environment variable not set");
			}
		} else {
			return getWorkDirectory();
		}
	}

	/**
	 * Retrieves the work directory path from MongoGeboInstallation.
	 * Throws an exception if the system is not initialized.
	 * 
	 * @return A string representing the work directory path.
	 */
	private String getWorkDirectory() {
		Optional<ai.gebo.config.model.MongoGeboInstallation> mongoInfo = geboInstallationRepository.findById(ai.gebo.config.model.MongoGeboInstallation.ENTRY);
		if (mongoInfo.isEmpty())
			throw new RuntimeException("System not initialized");
		return mongoInfo.get().getGeboWorkdirectoryPath();
	}

	/**
	 * Retrieves the current Gebo configuration.
	 * 
	 * @return The GeboConfig object containing configuration information.
	 */
	@Override
	public GeboConfig getGeboConfig() {
		return geboConfig;
	}

	/**
	 * Checks whether the Gebo home directory is set in the environment.
	 * 
	 * @return true if the Gebo home directory is set; otherwise, false.
	 */
	@Override
	public boolean isGeboHomeSet() {
		return EnvironmentHolder.GEBO_HOME_ENVIRONMENT_VALUE != null
				&& EnvironmentHolder.GEBO_HOME_ENVIRONMENT_VALUE.trim().length() > 0;
	}

	/**
	 * Checks if the Gebo work directory is set, either through user configuration or environment.
	 * Returns true if set; otherwise, false.
	 * 
	 * @return A boolean indicating whether the Gebo work directory is set.
	 */
	@Override
	public boolean isGeboWorkDirectorySet() {
		boolean geboWorkDirVariableSet = (EnvironmentHolder.GEBO_WORK_DIRECTORY_ENVIRONMENT_VALUE != null
				&& EnvironmentHolder.GEBO_WORK_DIRECTORY_ENVIRONMENT_VALUE.trim().length() > 0);
		if (geboConfig != null && geboConfig.getSetupConfiguresWorkdir() != null
				&& geboConfig.getSetupConfiguresWorkdir()) {
			Optional<ai.gebo.config.model.MongoGeboInstallation> mongoInfo = geboInstallationRepository
					.findById(ai.gebo.config.model.MongoGeboInstallation.ENTRY);
			if (mongoInfo.isEmpty()) {
				return false;
			} else {
				ai.gebo.config.model.MongoGeboInstallation data = mongoInfo.get();
				return data.getGeboWorkdirectoryPath() != null && data.getGeboWorkdirectoryPath().trim().length() > 0;
			}
		} else {
			return geboWorkDirVariableSet;
		}
	}

}