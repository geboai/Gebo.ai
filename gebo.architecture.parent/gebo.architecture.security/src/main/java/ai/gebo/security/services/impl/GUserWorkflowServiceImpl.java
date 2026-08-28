package ai.gebo.security.services.impl;

import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.IGUserWorkflowMailService;
import ai.gebo.security.services.IGUserWorkflowService;
import ai.gebo.security.services.IGUsersAdminService;
import ai.gebo.security.services.SecurityAuditTaxonomy;
import ai.gebo.security.services.UserWorkflowException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Service

public class GUserWorkflowServiceImpl implements IGUserWorkflowService {
	private static final String ERROR_SENDING_EMAIL = "Error sending email";
	private static final Logger LOGGER = LoggerFactory.getLogger(GUserWorkflowServiceImpl.class);
	private static final String SECRET_ID_SEPARATOR = "|=|";
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
	private final IGSecurityAuditLoggerService securityAuditLoggerService;

	public GUserWorkflowServiceImpl(GeboUserWorkflowsConfig workflowsConfig,
			IGUserWorkflowMailService workflowMailService, UserRepository userRepository,
			IGUsersAdminService userAdminService, IGSecurityService securityService,
			IGeboSecretsAccessService secretAccessService, IGeboCryptingService cryptService,
			@Autowired(required = false) IGCustomUserWorkflowDiscriminationService userDiscriminationService,
			IGSecurityAuditLoggerService securityAuditLoggerService) {
		this.userRepository = userRepository;
		this.securityService = securityService;
		this.secretAccessService = secretAccessService;
		this.cryptService = cryptService;
		this.workflowsConfig = workflowsConfig;
		this.workflowMailService = workflowMailService;
		this.userAdminService = userAdminService;
		this.userDiscriminationService = userDiscriminationService;
		this.securityAuditLoggerService = securityAuditLoggerService;
	}

	@Override
	public UserWorkFlowStartResponse startUserWorkflow(String userName, UserWorkflowType type)
			throws UserWorkflowException, GeboCryptSecretException {
		UserWorkFlowStartResponse response = null;
		if (userDiscriminationService != null && !userDiscriminationService.canRunWorkflow(userName, type)) {
			response = new UserWorkFlowStartResponse(false, false, true);
			return response;
		}
		User user = userRepository.findByUsername(userName).orElse(null);
		if (user == null) {
			response = new UserWorkFlowStartResponse(false, false, true);
			return response;
		}
		if (user.getProvider() == null || user.getProvider() == AuthProvider.local) {
			switch (type) {
			case ACTIVATION: {
				if (!workflowsConfig.isActivationWorkflowEnabled())
					throw new UserWorkflowException(WORKFLOW_DISABLED);
				if (user.getDisabled() != null && user.getDisabled()) {
					UserWorkflowTicket ticket = generateAndSaveTicket(userName, type);
					try {
						workflowMailService.sendTicket(ticket);
						response = new UserWorkFlowStartResponse(true, true, false);
					} catch (Throwable th) {
						LOGGER.error(ERROR_SENDING_EMAIL, th);
						response = new UserWorkFlowStartResponse(true, false, false);
					}
				} else {
					response = new UserWorkFlowStartResponse(false, false, true);
				}
			}
				break;
			case FORGOT_PASSWORD: {
				if (!workflowsConfig.isForgotPasswordWorkflowEnabled())
					throw new UserWorkflowException(WORKFLOW_DISABLED);
				if (user.getDisabled() != null && user.getDisabled()) {
					response = new UserWorkFlowStartResponse(false, false, true);
				} else {
					UserWorkflowTicket ticket = generateAndSaveTicket(userName, type);
					try {
						workflowMailService.sendTicket(ticket);
						response = new UserWorkFlowStartResponse(true, true, false);
					} catch (Throwable th) {
						LOGGER.error(ERROR_SENDING_EMAIL, th);
						response = new UserWorkFlowStartResponse(true, false, false);
					}
				}
			}
				break;
			}
		} else {
			response = new UserWorkFlowStartResponse(false, false, true);
		}
		return response;

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
		buffer.append(SECRET_ID_SEPARATOR);
		buffer.append(storedId);
		UserWorkflowTicket ticket = new UserWorkflowTicket(type, cryptService.crypt(buffer.toString()),
				userName.toLowerCase().trim());
		return ticket;
	}

	@Override
	public UserWorkFlowChangePasswordResponse userChangePasswordWithTicket(
			@Valid @NotNull UserChangePasswordWithTicket data) throws UserWorkflowException, GeboCryptSecretException {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		event.setEventType(SecurityAuditTaxonomy.EventType.USER_ADMINISTRATION);
		event.setCategory(SecurityAuditTaxonomy.Category.USER_ADMINISTRATION);
		event.setAction(SecurityAuditTaxonomy.Action.PASSWORD_RESET_TICKET);
		event.setResourceId(data != null ? data.getEmail() : null);
		try {
			UserWorkFlowChangePasswordResponse outState = null;
			LoadAndVerifyOutput loaded = loadAndVerifyTicket(data);
			if (loaded.getOutState() != null) {
				event.setOutcome(SecurityAuditTaxonomy.Outcome.FAILURE);
				securityAuditLoggerService.log(event);
				return loaded.getOutState();
			}
			UserWorkflowSecret originalSecret = loaded.getSecret();
			EditableUser user = userAdminService.findUserByUsername(data.getEmail().trim().toLowerCase());
			if (user != null) {
				switch (originalSecret.getType()) {
				case ACTIVATION: {
					if (user.getDisabled() != null && user.getDisabled()) {
						user.setDisabled(false);
						userAdminService.updateUser(user);
						userAdminService.changePassword(user.getUsername(), data.getPassword());
						outState = new UserWorkFlowChangePasswordResponse(true, false, false, false);
					}
				}
					break;
				case FORGOT_PASSWORD: {
					if (user.getDisabled() == null || !user.getDisabled()) {
						userAdminService.changePassword(user.getUsername(), data.getPassword());
						outState = new UserWorkFlowChangePasswordResponse(true, false, false, false);
					}
				}
					break;
				}

			} else {
				outState = new UserWorkFlowChangePasswordResponse(false, true, false, false);
			}
			event.setOutcome(outState != null && outState.isOk() ? SecurityAuditTaxonomy.Outcome.SUCCESS
					: SecurityAuditTaxonomy.Outcome.FAILURE);
			securityAuditLoggerService.log(event);
			return outState;
		} catch (RuntimeException | UserWorkflowException | GeboCryptSecretException e) {
			event.setOutcome(SecurityAuditTaxonomy.Outcome.FAILURE);
			securityAuditLoggerService.log(event);
			throw e;
		}
	}

	@AllArgsConstructor
	@Getter
	static class LoadAndVerifyOutput {
		private final UserWorkflowSecret secret;
		private final UserWorkFlowChangePasswordResponse outState;
	}

	private LoadAndVerifyOutput loadAndVerifyTicket(@Valid @NotNull UserChangePasswordWithTicket data)
			throws UserWorkflowException, GeboCryptSecretException {
		if (data == null || data.getTicket() == null || data.getTicket().trim().isEmpty())
			throw new UserWorkflowException(TICKET_IS_MANDATORY);
		String originalTicket = cryptService.decrypt(data.getTicket().trim());
		int storeIdOffset = originalTicket.lastIndexOf(SECRET_ID_SEPARATOR);

		if (storeIdOffset >= 0) {
			String matchingSavedTicket = originalTicket.substring(0, storeIdOffset);
			String storeId = originalTicket.substring(storeIdOffset + SECRET_ID_SEPARATOR.length());
			UserWorkflowSecret secret = secretAccessService.getCustomSecretContentById(storeId,
					UserWorkflowSecret.class);
			if (!secret.getTicket().equals(matchingSavedTicket)) {
				return new LoadAndVerifyOutput(null, new UserWorkFlowChangePasswordResponse(false, false, true, false));
			}

			if (data.getEmail() != null && data.getEmail().trim().length() > 0 && data.getEmail().contains("@")) {
				if (!data.getEmail().trim().toLowerCase().equals(secret.getEmail().trim().toLowerCase())) {
					return new LoadAndVerifyOutput(null,
							new UserWorkFlowChangePasswordResponse(false, true, false, false));
				}
				if (secret.getEndValidity() == null || secret.getEndValidity().before(new Date())) {
					return new LoadAndVerifyOutput(null,
							new UserWorkFlowChangePasswordResponse(false, false, false, true));
				}
				return new LoadAndVerifyOutput(secret, null);
			} else {
				return new LoadAndVerifyOutput(null, new UserWorkFlowChangePasswordResponse(false, true, false, false));
			}

		} else {
			return new LoadAndVerifyOutput(null, new UserWorkFlowChangePasswordResponse(false, false, true, false));
		}

	}

}
