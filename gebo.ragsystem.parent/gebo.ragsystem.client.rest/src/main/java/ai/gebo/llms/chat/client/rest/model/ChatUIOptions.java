package ai.gebo.llms.chat.client.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatUIOptions {
	public ChatUIOptions(ChatUIOptions cc) {
		this(cc.enablePureModelChat, cc.defaultUILanguage, cc.openChatAvailable);
	}

	private boolean enablePureModelChat = true;
	private String defaultUILanguage = null;
	/**
	 * Computed at request time (not a configuration property): true when the
	 * open-chat pipeline is registered AND its network-of-agents streaming step is
	 * running (which in turn requires {@code ai.gebo.openchat.enabled} and
	 * {@code ai.gebo.agents.standard.enabled}). The UI shows the "Chat" (open-chat)
	 * entry only when this is true together with {@link #enablePureModelChat}.
	 */
	private boolean openChatAvailable = false;

}
