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
		// Configuration changes that decide what the AI may do and for whom: the agents
		// and networks of agents, the external tools and agents they reach (MCP, A2A),
		// the chat profiles and knowledge bases users get, and the external search
		// sources. Each one can widen access or open a new outbound data flow, so they
		// are security events in their own right (see ConfigurationAuditSupport).
		public static final String AGENTS_CONFIGURATION = "agentsConfiguration";
		public static final String INTEROPERABILITY_CONFIGURATION = "interoperabilityConfiguration";
		public static final String KNOWLEDGE_CONFIGURATION = "knowledgeConfiguration";
		public static final String SEARCH_CONFIGURATION = "searchConfiguration";
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
		public static final String AGENTS_CONFIGURATION = EventType.AGENTS_CONFIGURATION;
		public static final String INTEROPERABILITY_CONFIGURATION = EventType.INTEROPERABILITY_CONFIGURATION;
		public static final String KNOWLEDGE_CONFIGURATION = EventType.KNOWLEDGE_CONFIGURATION;
		public static final String SEARCH_CONFIGURATION = EventType.SEARCH_CONFIGURATION;
	}

	public static class Action {
		// Session / token
		public static final String SESSION_TOKEN_RENEW = "sessionTokenRenew";
		public static final String SESSION_HEADER_DATA_COMPLETE = "sessionHeaderDataComplete";
		public static final String SESSION_LOGOUT = "sessionLogout";
		public static final String SESSION_REVOKE = "sessionRevoke";
		public static final String SESSION_REVOKE_ALL_OF_USER = "sessionRevokeAllOfUser";

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

		// Agents configuration (EventType.AGENTS_CONFIGURATION): the networks of agents
		// and the agents they are made of - their models, tools, prompts and who may use
		// them - and the network every chat of a pipeline type is handed to. A copy is
		// its own action: it creates a new, editable configuration out of another one.
		public static final String AGENTS_NETWORK_INSERT = "agentsNetworkInsert";
		public static final String AGENTS_NETWORK_UPDATE = "agentsNetworkUpdate";
		public static final String AGENTS_NETWORK_DELETE = "agentsNetworkDelete";
		public static final String AGENTS_NETWORK_COPY = "agentsNetworkCopy";
		public static final String AGENT_INSERT = "agentInsert";
		public static final String AGENT_UPDATE = "agentUpdate";
		public static final String AGENT_DELETE = "agentDelete";
		public static final String AGENT_COPY = "agentCopy";
		public static final String AGENTIC_CHAT_DEFAULT_NETWORK_SET = "agenticChatDefaultNetworkSet";
		public static final String AGENTIC_CHAT_DEFAULT_NETWORK_RESET = "agenticChatDefaultNetworkReset";

		// Interoperability configuration (EventType.INTEROPERABILITY_CONFIGURATION): the
		// external MCP servers whose tools the agents call, the MCP servers this
		// installation exposes (and their ACLs), the external A2A agents imported and
		// the agents/networks exported over A2A. Each one opens or closes a path to or
		// from another system.
		public static final String MCP_SERVER_INSERT = "mcpServerInsert";
		public static final String MCP_SERVER_UPDATE = "mcpServerUpdate";
		public static final String MCP_SERVER_DELETE = "mcpServerDelete";
		public static final String MCP_EXPOSED_SERVER_INSERT = "mcpExposedServerInsert";
		public static final String MCP_EXPOSED_SERVER_UPDATE = "mcpExposedServerUpdate";
		public static final String MCP_EXPOSED_SERVER_DELETE = "mcpExposedServerDelete";
		public static final String MCP_EXPOSED_SERVER_ACCESS_UPDATE = "mcpExposedServerAccessUpdate";
		public static final String A2A_REMOTE_AGENT_INSERT = "a2aRemoteAgentInsert";
		public static final String A2A_REMOTE_AGENT_UPDATE = "a2aRemoteAgentUpdate";
		public static final String A2A_REMOTE_AGENT_DELETE = "a2aRemoteAgentDelete";
		public static final String A2A_EXPORT_INSERT = "a2aExportInsert";
		public static final String A2A_EXPORT_UPDATE = "a2aExportUpdate";
		public static final String A2A_EXPORT_DELETE = "a2aExportDelete";

		// Knowledge configuration (EventType.KNOWLEDGE_CONFIGURATION): the chat
		// profiles users chat through and the knowledge bases and projects their
		// documents live in, with who may use each one.
		public static final String CHAT_PROFILE_INSERT = "chatProfileInsert";
		public static final String CHAT_PROFILE_UPDATE = "chatProfileUpdate";
		public static final String CHAT_PROFILE_DELETE = "chatProfileDelete";
		public static final String KNOWLEDGE_BASE_INSERT = "knowledgeBaseInsert";
		public static final String KNOWLEDGE_BASE_UPDATE = "knowledgeBaseUpdate";
		public static final String KNOWLEDGE_BASE_DELETE = "knowledgeBaseDelete";
		public static final String PROJECT_INSERT = "projectInsert";
		public static final String PROJECT_UPDATE = "projectUpdate";
		public static final String PROJECT_DELETE = "projectDelete";

		// Search configuration (EventType.SEARCH_CONFIGURATION): the web search
		// providers (Google, Tavily, Brave, SearXNG, SerpApi) and the deep search
		// configuration, which also decides who may search the external sources.
		public static final String WEB_SEARCH_CONFIG_INSERT = "webSearchConfigInsert";
		public static final String WEB_SEARCH_CONFIG_UPDATE = "webSearchConfigUpdate";
		public static final String WEB_SEARCH_CONFIG_DELETE = "webSearchConfigDelete";
		public static final String DEEP_SEARCH_CONFIG_INSERT = "deepSearchConfigInsert";
		public static final String DEEP_SEARCH_CONFIG_UPDATE = "deepSearchConfigUpdate";
		public static final String DEEP_SEARCH_CONFIG_DELETE = "deepSearchConfigDelete";
	}

	/**
	 * The resource types of the configuration events (SecurityEvent.resourceType).
	 */
	public static class ResourceType {
		public static final String AGENTS_NETWORK = "agentsNetwork";
		public static final String AGENT = "agent";
		public static final String AGENTIC_CHAT_DEFAULT_NETWORK = "agenticChatDefaultNetwork";
		public static final String MCP_SERVER = "mcpServer";
		public static final String MCP_EXPOSED_SERVER = "mcpExposedServer";
		public static final String A2A_REMOTE_AGENT = "a2aRemoteAgent";
		public static final String A2A_EXPORT = "a2aExport";
		public static final String CHAT_PROFILE = "chatProfile";
		public static final String KNOWLEDGE_BASE = "knowledgeBase";
		public static final String PROJECT = "project";
		public static final String WEB_SEARCH_CONFIG = "webSearchConfig";
		public static final String DEEP_SEARCH_CONFIG = "deepSearchConfig";
	}

	public static class Outcome {
		public static final String SUCCESS = "success";
		public static final String FAILURE = "failure";
		public static final String DENIED = "denied";
	}
}
