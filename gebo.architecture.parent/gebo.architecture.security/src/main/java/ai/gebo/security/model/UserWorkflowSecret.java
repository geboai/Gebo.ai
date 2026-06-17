package ai.gebo.security.model;

import java.util.Date;

import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboCustomSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserWorkflowSecret extends GeboCustomSecretContent {
	public static final String USER_WORKFLOW_CONTEXT = "USERS_WORKFLOWS";
	@NotNull
	private UserWorkflowType type;
	@NotNull
	private String ticket;
	@NotNull
	private String email;
	@NotNull
	private Date startValidity = null;
	@NotNull
	private Date endValidity = null;

}
