package ai.gebo.security.services.impl;

import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.crypting.services.IGeboCryptingService;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.security.config.GeboUserWorkflowsConfig;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.EditableUser;
import ai.gebo.security.model.User;
import ai.gebo.security.model.UserChangePasswordWithTicket;
import ai.gebo.security.model.UserWorkflowSecret;
import ai.gebo.security.model.UserWorkflowTicket;
import ai.gebo.security.model.UserWorkflowType;
import ai.gebo.security.repository.UserRepository;
import ai.gebo.security.services.IGCustomUserWorkflowDiscriminationService;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.IGUserWorkflowMailService;
import ai.gebo.security.services.IGUserWorkflowService;
import ai.gebo.security.services.IGUsersAdminService;
import ai.gebo.security.services.UserWorkflowException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Service

public class GUserWorkflowServiceImpl implements IGUserWorkflowService {
	private static final String CANNOT_RUN_THIS_WORKFLOW = "Cannot run this workflow";
	private static final String WRONG_STATE = "Wrong state";
	private static final String WRONG_EMAIL = "Wrong email";
	private static final String WRONG_TICKET = "Wrong ticket";
	private static final String TICKET_IS_MANDATORY = "Ticket is mandatory";
	private static final String USER_WORKFLOW_DESCRIPTION = "User workflow";
	private static final String SEPARATOR = "-";
	private static final String ONLY_JWT_MANAGED_USER_CAN_USE_WORKFLOWS = "Only JWT managed user can use workflows";
	private static final String USER_IS_DISABLED = "User is disabled";
	private static final String USER_IS_ENABLED = "User is enabled";
	private static final String WORKFLOW_DISABLED = "Workflow disabled";
	private static final String UNKNOWN_USER = "Unknown user";
	private final UserRepository userRepository;
	private final IGSecurityService securityService;
	private final IGeboSecretsAccessService secretAccessService;
	private final IGeboCryptingService cryptService;
	private final GeboUserWorkflowsConfig workflowsConfig;
	private final IGUserWorkflowMailService workflowMailService;
	private final IGUsersAdminService userAdminService;
	private final IGCustomUserWorkflowDiscriminationService userDiscriminationService;

	public GUserWorkflowServiceImpl(GeboUserWorkflowsConfig workflowsConfig,
			IGUserWorkflowMailService workflowMailService, UserRepository userRepository,
			IGUsersAdminService userAdminService, IGSecurityService securityService,
			IGeboSecretsAccessService secretAccessService, IGeboCryptingService cryptService,@Autowired(required = false)
			IGCustomUserWorkflowDiscriminationService userDiscriminationService) {
		this.userRepository = userRepository;
		this.securityService = securityService;
		this.secretAccessService = secretAccessService;
		this.cryptService = cryptService;
		this.workflowsConfig = workflowsConfig;
		this.workflowMailService = workflowMailService;
		this.userAdminService = userAdminService;
		this.userDiscriminationService = userDiscriminationService;
	}

	@Override
	public void startUserWorkflow(String userName, UserWorkflowType type)
			throws UserWorkflowException, GeboCryptSecretException {
		if (userDiscriminationService!=null && !userDiscriminationService.canRunWorkflow(userName, type)) {
			throw new UserWorkflowException(CANNOT_RUN_THIS_WORKFLOW);
		}
		User user = userRepository.findByUsername(userName).orElseThrow(() -> new UserWorkflowException(UNKNOWN_USER));
		if (user.getProvider() == null || user.getProvider() == AuthProvider.local) {
			switch (type) {
			case ACTIVATION: {
				if (!workflowsConfig.isActivationWorkflowEnabled())
					throw new UserWorkflowException(WORKFLOW_DISABLED);
				if (user.getDisabled() != null && user.getDisabled()) {
					UserWorkflowTicket ticket = generateAndSaveTicket(userName, type);
					workflowMailService.sendTicket(ticket);
				} else {
					throw new UserWorkflowException(USER_IS_ENABLED);
				}
			}
				break;
			case FORGOT_PASSWORD: {
				if (!workflowsConfig.isForgotPasswordWorkflowEnabled())
					throw new UserWorkflowException(WORKFLOW_DISABLED);
				if (user.getDisabled() != null && user.getDisabled()) {
					throw new UserWorkflowException(USER_IS_DISABLED);
				} else {
					UserWorkflowTicket ticket = generateAndSaveTicket(userName, type);
					workflowMailService.sendTicket(ticket);
				}
			}
				break;
			}
		} else
			throw new UserWorkflowException(ONLY_JWT_MANAGED_USER_CAN_USE_WORKFLOWS);

	}

	private static final int RANDOM_CYCLES = 20;

	private UserWorkflowTicket generateAndSaveTicket(String userName, UserWorkflowType type)
			throws GeboCryptSecretException {
		StringBuffer buffer = new StringBuffer();
		buffer.append(type.name());
		buffer.append(SEPARATOR);
		buffer.append(userName.toLowerCase().trim());
		for (int i = 0; i < RANDOM_CYCLES; i++) {
			buffer.append(SEPARATOR);
			long p = Math.round(Math.random() * 100000.0);
			buffer.append(p);

		}
		GregorianCalendar gregorian = new GregorianCalendar();
		Date now = gregorian.getTime();
		gregorian.add(GregorianCalendar.MILLISECOND, workflowsConfig.getTicketValidityTimeoutMS());
		Date end = gregorian.getTime();
		UserWorkflowSecret secret = new UserWorkflowSecret(type, buffer.toString(), userName.toLowerCase().trim(), now,
				end);
		String storedId = secretAccessService.storeSecret(secret, USER_WORKFLOW_DESCRIPTION,
				UserWorkflowSecret.USER_WORKFLOW_CONTEXT);
		buffer.append(SEPARATOR);
		buffer.append(storedId);
		UserWorkflowTicket ticket = new UserWorkflowTicket(type, cryptService.crypt(buffer.toString()),
				userName.toLowerCase().trim());
		return ticket;
	}

	@Override
	public void userChangePasswordWithTicket(@Valid @NotNull UserChangePasswordWithTicket data)
			throws UserWorkflowException, GeboCryptSecretException {
		UserWorkflowSecret originalSecret = loadAndVerifyTicket(data);
		EditableUser user = userAdminService.findUserByUsername(data.getEmail().trim().toLowerCase());
		if (user == null)
			throw new UserWorkflowException(WRONG_EMAIL);
		switch (originalSecret.getType()) {
		case ACTIVATION: {
			if (user.getDisabled() != null && user.getDisabled()) {
				user.setDisabled(false);
				userAdminService.updateUser(user);
				userAdminService.changePassword(user.getUsername(), data.getPassword());
				return;
			}
		}
			break;
		case FORGOT_PASSWORD: {
			if (user.getDisabled() == null || !user.getDisabled()) {
				userAdminService.changePassword(user.getUsername(), data.getPassword());
				return;
			}
		}
			break;
		}
		throw new UserWorkflowException(WRONG_STATE);
	}

	private UserWorkflowSecret loadAndVerifyTicket(@Valid @NotNull UserChangePasswordWithTicket data)
			throws UserWorkflowException, GeboCryptSecretException {
		if (data == null || data.getTicket() == null || data.getTicket().trim().isEmpty())
			throw new UserWorkflowException(TICKET_IS_MANDATORY);
		String originalTicket = cryptService.decrypt(data.getTicket().trim());
		int storeIdOffset = originalTicket.lastIndexOf(SEPARATOR);

		if (storeIdOffset >= 0) {
			String matchingSavedTicket = originalTicket.substring(0, storeIdOffset);
			storeIdOffset++;
			String storeId = originalTicket.substring(storeIdOffset);
			UserWorkflowSecret secret = secretAccessService.getCustomSecretContentById(storeId,
					UserWorkflowSecret.class);
			if (!secret.getTicket().equals(matchingSavedTicket))
				throw new UserWorkflowException(WRONG_TICKET);
			if (data.getEmail() != null && data.getEmail().trim().length() > 0 && data.getEmail().contains("@")) {
				if (!data.getEmail().trim().toLowerCase().equals(secret.getEmail().trim().toLowerCase())) {
					throw new UserWorkflowException(WRONG_EMAIL);
				}
				return secret;
			} else
				throw new UserWorkflowException(WRONG_EMAIL);
		} else
			throw new UserWorkflowException(WRONG_TICKET);

	}

}
