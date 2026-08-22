package ai.gebo.security.services;

/*****************************************************************************
 * Shared vocabulary for IGSecurityAuditLoggerService.SecurityEvent's
 * eventType/category/action/outcome fields, so events stay queryable/filterable
 * consistently across every instrumented component.
 */
public class SecurityAuditTaxonomy {

	public static class EventType {
		public static final String SESSION_MANAGEMENT = "sessionManagement";
		public static final String AUTHENTICATION = "authentication";
		public static final String LLM_CONFIGURATION = "llmConfiguration";
		public static final String LLM_INVOCATION = "llmInvocation";
		public static final String INTEGRATION_CONFIGURATION = "integrationConfiguration";
		public static final String INTEGRATION_DATA_ACCESS = "integrationDataAccess";
		public static final String SECRET_MANAGEMENT = "secretManagement";
		public static final String API_KEY_MANAGEMENT = "apiKeyManagement";
		public static final String USER_ADMINISTRATION = "userAdministration";
	}

	public static class Category {
		public static final String SESSION_MANAGEMENT = EventType.SESSION_MANAGEMENT;
		public static final String AUTHENTICATION = EventType.AUTHENTICATION;
		public static final String LLM_CONFIGURATION = EventType.LLM_CONFIGURATION;
		public static final String LLM_INVOCATION = EventType.LLM_INVOCATION;
		public static final String INTEGRATION_CONFIGURATION = EventType.INTEGRATION_CONFIGURATION;
		public static final String INTEGRATION_DATA_ACCESS = EventType.INTEGRATION_DATA_ACCESS;
		public static final String SECRET_MANAGEMENT = EventType.SECRET_MANAGEMENT;
		public static final String API_KEY_MANAGEMENT = EventType.API_KEY_MANAGEMENT;
		public static final String USER_ADMINISTRATION = EventType.USER_ADMINISTRATION;
	}

	public static class Action {
		// Session / token
		public static final String SESSION_TOKEN_RENEW = "sessionTokenRenew";

		// Authentication
		public static final String AUTH_LOGIN_LOCAL = "authLoginLocal";
		public static final String AUTH_LOGIN_LOCAL_FAILURE = "authLoginLocalFailure";
		public static final String AUTH_LOGIN_OAUTH2 = "authLoginOauth2";
		public static final String AUTH_LOGIN_OAUTH2_FAILURE = "authLoginOauth2Failure";
		public static final String AUTH_UNAUTHORIZED_ACCESS = "authUnauthorizedAccess";

		// LLM configuration
		public static final String LLM_CONFIG_INSERT = "llmConfigInsert";
		public static final String LLM_CONFIG_UPDATE = "llmConfigUpdate";
		public static final String LLM_CONFIG_DELETE = "llmConfigDelete";
		public static final String LLM_CREDENTIALS_CREATE = "llmCredentialsCreate";
		public static final String LLM_AUTOCONFIGURE = "llmAutoconfigure";
		public static final String LLM_BULK_SETUP = "llmBulkSetup";
		public static final String LLM_VERIFY_CREDENTIALS = "llmVerifyCredentials";

		// LLM invocation
		public static final String LLM_INVOKE_CHAT = "llmInvokeChat";
		public static final String LLM_INVOKE_TTS = "llmInvokeTts";
		public static final String LLM_INVOKE_TRANSCRIPT = "llmInvokeTranscript";
		public static final String LLM_INVOKE_RANK = "llmInvokeRank";

		// 3rd-party integration settings
		public static final String INTEGRATION_SYSTEM_INSERT = "integrationSystemInsert";
		public static final String INTEGRATION_SYSTEM_UPDATE = "integrationSystemUpdate";
		public static final String INTEGRATION_SYSTEM_DELETE = "integrationSystemDelete";
		public static final String INTEGRATION_ENDPOINT_INSERT = "integrationEndpointInsert";
		public static final String INTEGRATION_ENDPOINT_UPDATE = "integrationEndpointUpdate";
		public static final String INTEGRATION_ENDPOINT_DELETE = "integrationEndpointDelete";
		public static final String INTEGRATION_ENDPOINT_PUBLISH = "integrationEndpointPublish";

		// 3rd-party content lifecycle
		public static final String INTEGRATION_CONTENT_UPLOAD = "integrationContentUpload";
		public static final String INTEGRATION_CONTENT_DELETE = "integrationContentDelete";

		// 3rd-party data interaction
		public static final String INTEGRATION_DATA_CONSUME = "integrationDataConsume";
		public static final String INTEGRATION_DATA_READ = "integrationDataRead";
		public static final String INTEGRATION_DATA_STREAM = "integrationDataStream";

		// OAuth2 client configuration
		public static final String OAUTH2_CLIENT_CONFIG_INSERT = "oauth2ClientConfigInsert";
		public static final String OAUTH2_CLIENT_CONFIG_UPDATE = "oauth2ClientConfigUpdate";
		public static final String OAUTH2_CLIENT_CONFIG_DELETE = "oauth2ClientConfigDelete";

		// Secrets
		public static final String SECRET_CREATE = "secretCreate";
		public static final String SECRET_UPDATE = "secretUpdate";
		public static final String SECRET_DELETE = "secretDelete";
		public static final String SECRET_STORAGE_MIGRATE = "secretStorageMigrate";

		// API keys
		public static final String APIKEY_GENERATE_SELF = "apiKeyGenerateSelf";
		public static final String APIKEY_GENERATE_ADMIN = "apiKeyGenerateAdmin";
		public static final String APIKEY_DELETE = "apiKeyDelete";

		// Password management
		public static final String PASSWORD_CHANGE_SELF = "passwordChangeSelf";
		public static final String PASSWORD_CHANGE_ADMIN = "passwordChangeAdmin";
		public static final String PASSWORD_RESET_TICKET = "passwordResetTicket";

		// User / group administration
		public static final String USER_INSERT = "userInsert";
		public static final String USER_UPDATE = "userUpdate";
		public static final String USER_DELETE = "userDelete";
		public static final String GROUP_INSERT = "groupInsert";
		public static final String GROUP_UPDATE = "groupUpdate";
		public static final String GROUP_DELETE = "groupDelete";
	}

	public static class Outcome {
		public static final String SUCCESS = "success";
		public static final String FAILURE = "failure";
		public static final String DENIED = "denied";
	}
}
