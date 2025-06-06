package ai.gebo.security.model.oauth2;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Document
@Data
public class Oauth2InitializationInfo {
	@Id
	@NotNull
	String loginId = null;
	@NotNull
	String loginRelativeUrl = null;
	@NotNull
	String registrationId = null;
	@NotNull
	String remoteHost = null;
	String principalName = null;
	String oauth2Token = null;
	Date creationTimestamp = null;
	Date loginFinishedTimestamp = null;
}