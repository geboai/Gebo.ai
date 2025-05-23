/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.fastsetup.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.config.repositories.MongoGeboInstallationRepository;
import ai.gebo.architecture.patterns.IGRuntimeModuleComponentsDao;
import ai.gebo.architecture.patterns.model.GModuleUseInfo;
import ai.gebo.architecture.patterns.model.GModuleUseInfo.MInfoType;
import ai.gebo.architecture.patterns.model.GModuleUseInfo.ModuleType;
import ai.gebo.config.GeboConfig;
import ai.gebo.config.model.MongoGeboInstallation;
import ai.gebo.fastsetup.model.ComponentSetupStatus;
import ai.gebo.fastsetup.model.GeboAdvancedSetupStatus;
import ai.gebo.knowledgebase.repositories.KnowledgeBaseRepository;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;

/**
 * Service class providing methods to check the setup status for various components
 * of the Gebo application, focusing on installation configurations and runtime
 * environment readiness.
 * AI generated comments
 */
@Service
public class GeboAdvancedSetupStatusService {
	
	@Autowired
	MongoGeboInstallationRepository mongoGeboInstallationRepository;
	
	@Autowired
	GeboConfig geboConfig;
	
	@Autowired
	IGChatModelRuntimeConfigurationDao chatModelsConfigDao;
	
	@Autowired
	IGEmbeddingModelRuntimeConfigurationDao embeddingModelsConfigDao;
	
	@Autowired
	IGRuntimeModuleComponentsDao runtimeModuleComponentsDao;
	
	@Autowired
	KnowledgeBaseRepository knowledgeBaseRepository;

	/**
	 * Default constructor for GeboAdvancedSetupStatusService.
	 */
	public GeboAdvancedSetupStatusService() {

	}

	/**
	 * Checks whether the first knowledge base has been set up.
	 * 
	 * @return a ComponentSetupStatus indicating the setup status.
	 */
	public ComponentSetupStatus getFirstKnowledgeBaseSetupStatus() {
		ComponentSetupStatus status = new ComponentSetupStatus();
		// Determines setup by checking if there are any entries in the knowledge base
		status.isSetup = knowledgeBaseRepository.count() > 0l;
		return status;
	}

	/**
	 * Checks whether the minimal content setup has been completed for the modules.
	 * 
	 * @return a ComponentSetupStatus indicating if minimal contents are set up.
	 */
	public ComponentSetupStatus getMinimalContentsSetupStatus() {
		ComponentSetupStatus status = new ComponentSetupStatus();
		
		// Retrieve module use information
		List<GModuleUseInfo> modulesUserInfo = runtimeModuleComponentsDao.getModuleUseInfo();
		
		// Check for content type modules that are set up with configurations
		status.isSetup = modulesUserInfo.stream().filter(x -> {
			boolean thereIsContentSetup = x.getModuleType() == ModuleType.CONTENT;
			thereIsContentSetup = thereIsContentSetup && x.getInfoType() == MInfoType.SETUP;
			thereIsContentSetup = thereIsContentSetup && x.getConfigNumbers() > 0;
			return thereIsContentSetup;
		}).findAny().isPresent();
		
		return status;
	}

	/**
	 * Gathers all setup status components for the Gebo installation.
	 * 
	 * @return a GeboAdvancedSetupStatus containing various flags indicating what has been completed in the setup.
	 * @throws SQLException to indicate a database access error.
	 */
	public GeboAdvancedSetupStatus getSetupStatus() throws SQLException {
		GeboAdvancedSetupStatus status = new GeboAdvancedSetupStatus();
		
		Optional<MongoGeboInstallation> geboInstallationOpt = mongoGeboInstallationRepository
				.findById(MongoGeboInstallation.ENTRY);
		MongoGeboInstallation geboInstallation = geboInstallationOpt.isPresent() ? geboInstallationOpt.get() : null;
		
		// Check if the working directory needs to be set
		if (geboConfig.getSetupConfiguresWorkdir() != null && geboConfig.getSetupConfiguresWorkdir()) {
			status.mustSetWorkDirectory = geboInstallation != null
					&& geboInstallation.getGeboWorkdirectoryPath() != null
					&& geboInstallation.getGeboWorkdirectoryPath().trim().length() > 0;
		} else {
			status.mustSetWorkDirectory = false;
		}
		
		// Checks if the initial setup has been completed
		if (geboInstallation != null) {
			status.firstSetupDone = geboInstallation.getFirstSetupDone() != null
					&& geboInstallation.getFirstSetupDone();
		}
		
		// Verify chat model and embedding model configurations
		status.chatModelSetup = !chatModelsConfigDao.getConfigurations().isEmpty();
		status.embeddedModelSetup = !embeddingModelsConfigDao.getConfigurations().isEmpty();
		status.llmsSetup = status.chatModelSetup && status.embeddedModelSetup;
		
		List<GModuleUseInfo> modulesUserInfo = runtimeModuleComponentsDao.getModuleUseInfo();
		
		// Check if the knowledge base setup is complete
		status.firstKnowledgeBaseSetup = knowledgeBaseRepository.count() > 0l;
		
		// Verify content modules setup with documented configurations
		status.hasContentModulesSetup = modulesUserInfo.stream().filter(x -> {
			boolean thereIsContentSetup = x.getModuleType() == ModuleType.CONTENT;
			thereIsContentSetup = thereIsContentSetup && x.getInfoType() == MInfoType.SETUP;
			thereIsContentSetup = thereIsContentSetup && x.getConfigNumbers() > 0;
			return thereIsContentSetup;
		}).findAny().isPresent();
		
		return status;
	}
}