package ai.gebo.llms.agent.standard.services;

public class StandardAgentsNetworkEnvironmentEntries {

	public static final String KNOWLEDGE_BASES_CODE = "KNOWLEDGE_BASES_CODE";
	public static final String USER_INTENT = "USER_INTENT";
	/**
	 * Shared-session environment key holding a {@code Boolean}: whether the user asked
	 * to search, find, research, look up or verify, or named the sources to use (see
	 * the request understanding).
	 */
	public static final String SEARCH_REQUESTED = "SEARCH_REQUESTED";
	/**
	 * Shared-session environment key holding a {@code Boolean}: whether the user asked
	 * to answer without searching (from memory, from the conversation), see the request
	 * understanding.
	 */
	public static final String SEARCH_FORBIDDEN = "SEARCH_FORBIDDEN";
	/**
	 * Shared-session environment key holding a {@code String}: the English name of the
	 * language the user's message is written in (e.g. "English"), detected on the
	 * user's own text; absent when the detection is not trusted.
	 */
	public static final String USER_LANGUAGE = "USER_LANGUAGE";

	/**
	 * Shared-session environment key holding a {@code Map<String, GResponseDocumentRef>}
	 * of the rich document references the external-search agents produced, keyed by the
	 * source's {@code getCode()} (which the report writer's Documents are also keyed on via
	 * CONTENT_CODE). These refs are built upstream from the typed {@code SearchResult}, so
	 * they carry {@code nestedSearchResult} and the user can "chat with" the external
	 * result; the report writer prefers them over rebuilding a ref from the (SearchResult-less)
	 * Document.
	 */
	public static final String CHAT_WITH_DOC_REFS_BY_CODE = "CHAT_WITH_DOC_REFS_BY_CODE";

}
