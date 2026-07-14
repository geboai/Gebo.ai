package ai.gebo.security.services;

import ai.gebo.security.model.UserWorkflowType;

public interface IGCustomUserWorkflowDiscriminationService {
	public boolean canRunWorkflow(String email, UserWorkflowType type);
}
