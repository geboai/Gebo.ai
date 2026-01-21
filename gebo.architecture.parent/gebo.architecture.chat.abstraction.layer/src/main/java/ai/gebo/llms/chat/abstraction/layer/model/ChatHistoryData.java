package ai.gebo.llms.chat.abstraction.layer.model;

import java.util.List;

import lombok.Data;

@Data
public class ChatHistoryData {
	private GUserChatConsolidationData consolidated = null;
	private List<ChatInteractions> interactions = null;
}
