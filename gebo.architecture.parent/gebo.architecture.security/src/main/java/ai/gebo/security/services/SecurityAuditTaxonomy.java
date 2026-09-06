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
		public static final String SESSION_HEADER_DATA_COMPLETE = "sessionHeaderDataComplete";

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
		public static final String LLM_INVOKE_RANK_FILTER = "llmInvokeRankFilter";

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

		// Password *secret store* lifecycle - the tier below the three actions above.
		// Those record the caller's intent ("an admin changed someone's password", "a
		// user changed their own", "a reset ticket was redeemed"); these record what
		// actually happened to the stored credential, wherever the write came from -
		// including paths that have no controller at all (installation bootstrap, OAuth2
		// auto-provisioning, user deletion). Emitted by IGUserPasswordService's
		// implementation.
		public static final String PASSWORD_SECRET_STORE = "passwordSecretStore";
		public static final String PASSWORD_SECRET_DELETE = "passwordSecretDelete";
		// Only ever logged with outcome FAILURE: the secret store could not be read
		// while verifying a password, which IGUserPasswordService.matches() reports to
		// its caller as a plain mismatch. Without this the infrastructure fault is
		// indistinguishable, in the security log, from a wrong password.
		public static final String PASSWORD_SECRET_READ = "passwordSecretRead";

		// User / group administration
		public static final String USER_INSERT = "userInsert";
		public static final String USER_UPDATE = "userUpdate";
		public static final String USER_DELETE = "userDelete";
		public static final String GROUP_INSERT = "groupInsert";
		public static final String GROUP_UPDATE = "groupUpdate";
		public static final String GROUP_DELETE = "groupDelete";

		// OAuth2/resource-server user auto-provisioning and sync (distinct from
		// USER_INSERT/USER_UPDATE, which are admin-UI-initiated): fires wherever an
		// unknown validated identity is auto-created or an existing one is synced,
		// whether decided locally (heimdall/monolith) or requested by a peer
		// microservice over the security cluster REST surface.
		//
		// These are the USER STORE's actions - "the store was asked to create this
		// identity if it does not exist". The *decision* to ask, taken up in the OAuth2
		// handler chain, is the pair below: one login produces both, and they say
		// different things.
		public static final String USER_AUTO_PROVISION = "userAutoProvision";
		public static final String USER_SYNC = "userSync";

		// The OAuth2/OIDC handler chain's own decision about an external identity,
		// raised by GOAuth2UserService and its reactive twin (interactive oauth2Login),
		// GJwtAuthenticationConverter (bearer JWT) and
		// GOauth2ResourceServerUserProvisioner (bearer JWT/opaque, servlet + reactive).
		//
		// Deliberately NOT the same action as USER_AUTO_PROVISION/USER_SYNC above, which
		// they can cause: these carry what only the chain knows - the provider, the
		// issuer or client registration, the login policy in force, the sync handler that
		// ran - and, crucially, they are the ONLY events raised on the paths where no
		// store write happens at all: an identity refused by the policy (DENIED), or a
		// provisioning attempt that failed and was swallowed so the request could fail to
		// authenticate exactly as it would have anyway.
		public static final String OAUTH2_IDENTITY_PROVISION = "oauth2IdentityProvision";
		public static final String OAUTH2_IDENTITY_SYNC = "oauth2IdentitySync";
	}

	public static class Outcome {
		public static final String SUCCESS = "success";
		public static final String FAILURE = "failure";
		public static final String DENIED = "denied";
	}
}
