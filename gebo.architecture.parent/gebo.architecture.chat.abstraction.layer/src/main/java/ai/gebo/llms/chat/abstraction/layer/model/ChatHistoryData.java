package ai.gebo.llms.chat.abstraction.layer.model;

import java.util.List;

import lombok.Data;

@Data
public class ChatHistoryData {
	private GUserChatInteractionsConsolidationData consolidated = null;
	private List<ChatInteractions> interactions = null;
}
