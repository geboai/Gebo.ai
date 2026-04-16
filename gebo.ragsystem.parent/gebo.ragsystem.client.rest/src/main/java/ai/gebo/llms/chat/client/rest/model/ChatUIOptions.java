package ai.gebo.llms.chat.client.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatUIOptions {
	public ChatUIOptions(ChatUIOptions cc) {
		this(cc.enablePureModelChat, cc.defaultUILanguage);
	}

	private boolean enablePureModelChat = false;
	private String defaultUILanguage = null;

}
