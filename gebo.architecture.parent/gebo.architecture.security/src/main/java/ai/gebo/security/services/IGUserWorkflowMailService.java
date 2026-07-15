package ai.gebo.security.services;

import ai.gebo.security.model.UserWorkflowTicket;

public interface IGUserWorkflowMailService {
	public void sendTicket(UserWorkflowTicket ticket);
}
