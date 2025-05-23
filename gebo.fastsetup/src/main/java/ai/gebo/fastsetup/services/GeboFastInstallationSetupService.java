/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.fastsetup.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.crypting.services.IGeboCryptingService;
import ai.gebo.fastsetup.model.FastInstallationSetupData;
import ai.gebo.knlowledgebase.model.licence.GeboLicence;
import ai.gebo.knlowledgebase.model.licence.GeboLicence.GeboLicenceType;
import ai.gebo.knowledgebase.repositories.GeboLicenceRepository;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.config.GeboAISecurityConfig;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.User;
import ai.gebo.security.repository.UserRepository;

/**
 * GeboFastInstallationSetupService provides services to set up
 * a fast installation, including checking for admin user presence
 * and configuring an admin user.
 * 
 * AI generated comments
 */
@Service
public class GeboFastInstallationSetupService {

    // Services and repositories used for cryptography, user management, licence management, and persistence
	@Autowired
	IGeboCryptingService cryptService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	GeboLicenceRepository licenceRepository;

	@Autowired
	IGPersistentObjectManager persistenceManager;

    /**
     * Constructor for GeboFastInstallationSetupService.
     */
	public GeboFastInstallationSetupService() {

	}

    /**
     * Checks the presence of an administrator user in the system.
     * 
     * @return an OperationStatus containing a Boolean to indicate if an admin user is present.
     */
	public OperationStatus<Boolean> getInstallationStatus() {
		OperationStatus<Boolean> admin = checkAdminUserPresence();
		return admin;
	}

    /**
     * Creates a setup with the provided installation data. If admin exists, returns the status; otherwise, configures a new admin user.
     * 
     * @param data The FastInstallationSetupData containing setup information.
     * @return an OperationStatus indicating the success or failure of the setup creation.
     */
	public OperationStatus<Boolean> createSetup(FastInstallationSetupData data) {
		OperationStatus<Boolean> status = getInstallationStatus();
		if (status.getResult())
			return status;
		else {

			OperationStatus<Boolean> user = null;
			user = configureAdminUser(data);
			return user;
		}
	}

    /**
     * Configures the admin user by creating a new user with administrative privileges and saving the user and licence information.
     * 
     * @param data The FastInstallationSetupData containing user credentials and licence information.
     * @return an OperationStatus indicating the success or failure of the admin user configuration.
     */
	private OperationStatus<Boolean> configureAdminUser(FastInstallationSetupData data) {
		OperationStatus<Boolean> status = OperationStatus.of(false);
		status.getMessages().clear();
		try {
			User user = new User();
			user.setUsername(data.getUsername());
			// Encrypting the user's password
			user.setPassword(cryptService.crypt(data.getPassword()));
			user.setEmailVerified(true);
			user.setProvider(AuthProvider.local);
			user.setProviderId("local-jwt");
			user.setRoles(List.of(GeboAISecurityConfig.ADMIN_ROLE, GeboAISecurityConfig.USER_ROLE));
			userRepository.insert(user);
			
			GeboLicence geboLicence = new GeboLicence();
			// Encrypting the licence agreement
			geboLicence.setAgreedLicence(cryptService.crypt(data.getLicenceAgreement()));
			geboLicence.setLicenceType(GeboLicenceType.LIMITED_TRIAL);
			geboLicence.setCode("SignedLicence");
			// Encrypting the username for the licence
			geboLicence.setSignerUser(cryptService.crypt(user.getUsername()));
			licenceRepository.save(geboLicence);
			
			status = OperationStatus.of(true);
			status.getMessages().clear();
			status.getMessages().add(GUserMessage.successMessage("Admin user created !",
					"Admin user " + data.getUsername() + " created successfully"));

		} catch (GeboCryptSecretException e) {
			status.getMessages().add(GUserMessage.errorMessage("Problems in the crypting layer", e));
		} catch (Throwable th) {
			status.getMessages().add(GUserMessage.errorMessage("Problems saving", th));
		}
		return status;
	}

    /**
     * Checks if an administrator user is present in the user repository.
     * 
     * @return an OperationStatus indicating whether an admin user is present or not.
     */
	private OperationStatus<Boolean> checkAdminUserPresence() {
		OperationStatus<Boolean> value = null;
		if (userRepository.findByRoles(GeboAISecurityConfig.ADMIN_ROLE).isEmpty()) {
			value = OperationStatus.of(false);
			value.getMessages().clear();
			value.getMessages().add(GUserMessage.warnMessage("Administrator user not set",
					"An user administrator is not yet present in this installation"));
		} else {
			value = OperationStatus.of(true);
			value.getMessages().clear();
			value.getMessages().add(GUserMessage.successMessage("Administrator user set",
					"An user administrator is present in this installation"));
		}
		return value;
	}
}