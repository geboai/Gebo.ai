package ai.gebo.fastsetup.services;

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

import ai.gebo.crypting.services.IGeboCryptingService;
import ai.gebo.fastsetup.configuration.SystemInitializationAdminConfiguration;
import ai.gebo.knlowledgebase.model.licence.GeboLicence;
import ai.gebo.knlowledgebase.model.licence.GeboLicence.GeboLicenceType;
import ai.gebo.knowledgebase.repositories.GeboLicenceRepository;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.User;
import ai.gebo.security.repository.UserRepository;

@Component
@Scope("singleton")
public class SystemInitializationAdminService {
	private final SystemInitializationAdminConfiguration configuration;
	private final IGeboCryptingService cryptService;
	private final UserRepository userRepository;
	private final GeboLicenceRepository licenceRepository;
	private final static Logger LOGGER = LoggerFactory.getLogger(SystemInitializationAdminService.class);

	public SystemInitializationAdminService(
			@Autowired(required = false) SystemInitializationAdminConfiguration configuration,
			IGeboCryptingService cryptService, UserRepository userRepository, GeboLicenceRepository licenceRepository) {
		this.configuration = configuration;
		this.cryptService = cryptService;
		this.userRepository = userRepository;
		this.licenceRepository = licenceRepository;
	}

	@Scheduled(initialDelay = 10000)
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
				user.setPassword(this.cryptService.crypt(configuration.getAdminPassword()));
				user.setRoles(List.of("USER", "ADMIN"));
				user.setDisabled(false);
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
