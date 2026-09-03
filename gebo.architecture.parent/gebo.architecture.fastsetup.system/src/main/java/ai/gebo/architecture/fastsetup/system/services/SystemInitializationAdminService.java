package ai.gebo.architecture.fastsetup.system.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.fastsetup.system.configuration.SystemInitializationAdminConfiguration;
import ai.gebo.knlowledgebase.model.licence.GeboLicence;
import ai.gebo.knlowledgebase.model.licence.GeboLicence.GeboLicenceType;
import ai.gebo.knowledgebase.repositories.GeboLicenceRepository;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.User;
import ai.gebo.security.repository.UserRepository;
import ai.gebo.security.services.IGUserPasswordService;

@Component
@Scope("singleton")
public class SystemInitializationAdminService {
	private final SystemInitializationAdminConfiguration configuration;
	// The password is not a field of the user document any more - it is a secret filed
	// under "user:<username>". See IGUserPasswordService.
	private final IGUserPasswordService userPasswordService;
	private final UserRepository userRepository;
	private final GeboLicenceRepository licenceRepository;
	private final static Logger LOGGER = LoggerFactory.getLogger(SystemInitializationAdminService.class);

	public SystemInitializationAdminService(
			@Autowired(required = false) SystemInitializationAdminConfiguration configuration,
			IGUserPasswordService userPasswordService, UserRepository userRepository,
			GeboLicenceRepository licenceRepository) {
		this.configuration = configuration;
		this.userPasswordService = userPasswordService;
		this.userRepository = userRepository;
		this.licenceRepository = licenceRepository;
	}

	@Scheduled(initialDelay = 20000)
	public void onTick() {
		// if not yet configured interactiverly
		if (configuration != null && configuration.getAdminUsername() != null
				&& configuration.getAdminPassword() != null && configuration.getAdminUsername().contains("@")
				&& this.userRepository.count() == 0 && this.licenceRepository.count() == 0) {
			LOGGER.info("Creating system admin user");
			try {
				User user = new User();
				user.setProvider(AuthProvider.local);
				user.setUsername(configuration.getAdminUsername());
				user.setRoles(List.of("USER", "ADMIN"));
				user.setDisabled(false);
				// The secret first, then the row: a password secret with no user is inert,
				// whereas an admin row with no password would be an account nobody can sign in
				// as - and this is the only account there is at this point.
				userPasswordService.storePassword(user.getUsername(), configuration.getAdminPassword());
				userRepository.insert(user);
				GeboLicence geboLicence = new GeboLicence();
				geboLicence.setCode("ConfigLicence");
				geboLicence.setAgreedLicence("Silent registration");
				geboLicence.setLicenceType(GeboLicenceType.COMMON);
				geboLicence.setSignerUser(configuration.getAdminUsername());
				licenceRepository.insert(geboLicence);
			} catch (Throwable e) {
				LOGGER.error("Error in admin account creation", e);
			}
		}
	}

}
