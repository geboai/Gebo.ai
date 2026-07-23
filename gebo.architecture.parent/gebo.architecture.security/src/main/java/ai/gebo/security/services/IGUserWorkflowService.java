package ai.gebo.security.services;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.security.model.UserChangePasswordWithTicket;
import ai.gebo.security.model.UserWorkflowTicket;
import ai.gebo.security.model.UserWorkflowType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public interface IGUserWorkflowService {
	@Getter
	@AllArgsConstructor
	public static class UserWorkFlowStartResponse {
		private final boolean ok;
		private final boolean mailSent;
		private final boolean invalidAccountState;		
	}
	public UserWorkFlowStartResponse startUserWorkflow(String userName, UserWorkflowType type) throws UserWorkflowException, GeboCryptSecretException;
	@Getter
	@AllArgsConstructor
	public static class UserWorkFlowChangePasswordResponse {
		private final boolean ok;
		private final boolean invalidAccountState;
		private final boolean invalidToken;
		private final boolean timeoutReached;
	}
	public UserWorkFlowChangePasswordResponse userChangePasswordWithTicket(UserChangePasswordWithTicket data) throws UserWorkflowException, GeboCryptSecretException;
}
