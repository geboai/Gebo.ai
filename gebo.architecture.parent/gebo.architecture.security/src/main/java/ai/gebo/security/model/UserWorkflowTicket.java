package ai.gebo.security.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserWorkflowTicket {
	@NotNull
	private final UserWorkflowType type;
	@NotNull
	private final String ticket;
	@NotNull
	private final String email;
}