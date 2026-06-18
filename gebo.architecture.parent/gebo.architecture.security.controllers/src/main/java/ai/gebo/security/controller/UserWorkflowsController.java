package ai.gebo.security.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.security.config.GeboUserWorkflowsConfig;
import ai.gebo.security.model.UserChangePasswordWithTicket;
import ai.gebo.security.model.UserWorkflowType;
import ai.gebo.security.services.IGUserWorkflowService;
import ai.gebo.security.services.IGUserWorkflowService.UserWorkFlowChangePasswordResponse;
import ai.gebo.security.services.IGUserWorkflowService.UserWorkFlowStartResponse;
import ai.gebo.security.services.UserWorkflowException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
@RequestMapping("/public/UserWorkflowsController")
@AllArgsConstructor
public class UserWorkflowsController {
	private final GeboUserWorkflowsConfig config;
	private final IGUserWorkflowService userWorkflowService;
	private final static String WORKFLOW_NOT_CONFIGURED = "User workflows are not configured";

	@Data
	@AllArgsConstructor
	public static class UserWorkflows {
		private boolean activationWorkflowEnabled = false;
		private boolean forgotPasswordWorkflowEnabled = false;
	}

	@GetMapping(value = "getUserWorkflowsConfig", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserWorkflows getUserWorkflowsConfig() {
		return new UserWorkflows(config.isActivationWorkflowEnabled(), config.isForgotPasswordWorkflowEnabled());
	}

	@PostMapping(value = "userChangePasswordWithTicket", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public UserWorkFlowChangePasswordResponse userChangePasswordWithTicket(
			@RequestBody @NotNull @Valid UserChangePasswordWithTicket ticket)
			throws UserWorkflowException, GeboCryptSecretException {
		if (config.isActivationWorkflowEnabled() || config.isForgotPasswordWorkflowEnabled()) {
			return userWorkflowService.userChangePasswordWithTicket(ticket);
		} else
			throw new IllegalStateException(WORKFLOW_NOT_CONFIGURED);
	}

	@Data
	public static class StartWorkflowData {
		@NotNull
		private String email;
		@NotNull
		private UserWorkflowType type;
	}

	@PostMapping(value = "startUserWorkflow", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public UserWorkFlowStartResponse startUserWorkflow(@RequestBody @Valid @NotNull StartWorkflowData data)
			throws UserWorkflowException, GeboCryptSecretException {
		if ((data.getType() == UserWorkflowType.ACTIVATION && config.isActivationWorkflowEnabled())
				|| (data.getType() == UserWorkflowType.FORGOT_PASSWORD && config.isForgotPasswordWorkflowEnabled())) {
			return userWorkflowService.startUserWorkflow(data.getEmail(), data.getType());
		} else
			throw new IllegalStateException(WORKFLOW_NOT_CONFIGURED);
	}
}
