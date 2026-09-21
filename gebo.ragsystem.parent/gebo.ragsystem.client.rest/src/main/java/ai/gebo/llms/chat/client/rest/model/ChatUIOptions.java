package ai.gebo.llms.chat.client.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatUIOptions {
	public ChatUIOptions(ChatUIOptions cc) {
		this(cc.enablePureModelChat, cc.defaultUILanguage, cc.chatWithExternalFiles, cc.openChatAvailable);
	}

	private boolean enablePureModelChat = true;
	private String defaultUILanguage = null;
	/**
	 * Configuration property ({@code ai.gebo.chatui.chatWithExternalFiles}). When
	 * true, the user can "chat with" external-search results (a retrieved
	 * {@code GResponseDocumentRef} carrying a {@code nestedSearchResult}) as if they
	 * were internal knowledge-base documents: the UI shows the add-to-chat ("+")
	 * button on such results and the backend streams and ingests their content. When
	 * false, external results are still displayed but not chattable, and the backend
	 * silently skips any external ref it receives. Internal documents are always
	 * chattable regardless of this flag.
	 */
	private boolean chatWithExternalFiles = true;
	/**
	 * Computed at request time (not a configuration property): true when the
	 * open-chat pipeline is registered AND its network-of-agents streaming step is
	 * running (which in turn requires {@code ai.gebo.openchat.enabled} and
	 * {@code ai.gebo.agents.standard.enabled}). The UI shows the "Chat" (open-chat)
	 * entry only when this is true together with {@link #enablePureModelChat}.
	 */
	private boolean openChatAvailable = false;

}
