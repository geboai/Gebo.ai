package ai.gebo.llms.abstraction.layer.model;

import ai.gebo.llms.abstraction.layer.model.IChatSessionEntry.ChatSessionEntryImpl.ChatSessionEntryImplBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public interface IChatSessionEntry {
	public String getUser();

	public String getAssistant();

	@Getter
	@AllArgsConstructor
	@Builder
	public class ChatSessionEntryImpl implements IChatSessionEntry {
		private final String user;
		private final String assistant;
	}

	public static ChatSessionEntryImplBuilder builder() {
		return ChatSessionEntryImpl.builder();
	}

}
