package ai.gebo.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Oauth2ModuleStatus {
	private final boolean oauth2UISetupEnabled;
	private final boolean oauth2Enabled;
}
