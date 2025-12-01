package ai.gebo.llms.chat.abstraction.layer.model;

import java.util.List;

import lombok.Data;

@Data
public class ChatHistoryData {
	GUserChatConsolidationData consolidated = null;
	List<ChatInteractions> interactions = null;
}
