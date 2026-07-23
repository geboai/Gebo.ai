package ai.gebo.security.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserChangePasswordWithTicket {
	@NotNull
	private String ticket = null;
	@NotNull
	private String email = null;
	@NotNull
	private String password = null;
	@NotNull
	private String confirmPassword = null;

}
