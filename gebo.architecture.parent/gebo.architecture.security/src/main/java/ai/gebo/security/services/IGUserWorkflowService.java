package ai.gebo.security.services;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.security.model.UserChangePasswordWithTicket;
import ai.gebo.security.model.UserWorkflowTicket;
import ai.gebo.security.model.UserWorkflowType;

public interface IGUserWorkflowService {

	public void startUserWorkflow(String userName, UserWorkflowType type) throws UserWorkflowException, GeboCryptSecretException;

	public void userChangePasswordWithTicket(UserChangePasswordWithTicket data) throws UserWorkflowException, GeboCryptSecretException;
}
