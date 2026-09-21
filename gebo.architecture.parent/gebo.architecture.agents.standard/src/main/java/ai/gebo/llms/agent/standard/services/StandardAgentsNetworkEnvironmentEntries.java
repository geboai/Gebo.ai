package ai.gebo.llms.agent.standard.services;

public class StandardAgentsNetworkEnvironmentEntries {

	public static final String KNOWLEDGE_BASES_CODE = "KNOWLEDGE_BASES_CODE";
	public static final String USER_INTENT = "USER_INTENT";

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
