package ai.gebo.security.model;

import java.util.Date;

import ai.gebo.model.base.GBaseObject;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GeneratedApiKey extends GBaseObject {
	@NotNull
	private String apiKey = null;
	@NotNull
	private Date expiration = null;
	@NotNull
	private String impersonatedUser = null;
}
