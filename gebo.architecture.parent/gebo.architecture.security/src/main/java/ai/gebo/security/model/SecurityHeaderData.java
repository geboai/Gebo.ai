package ai.gebo.security.model;

import ai.gebo.security.SecurityHeaderUtil;
import ai.gebo.security.SecurityHeaderUtil.XAuthType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SecurityHeaderData {
	private final String token;
	private final XAuthType authType;
	private final String authProviderId;
	private final String authTenantId;
	private final boolean empty;
}