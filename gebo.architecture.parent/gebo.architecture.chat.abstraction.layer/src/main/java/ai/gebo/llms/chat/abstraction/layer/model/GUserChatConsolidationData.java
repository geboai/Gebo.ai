package ai.gebo.llms.chat.abstraction.layer.model;

import lombok.Data;

@Data
public class GUserChatConsolidationData {
	private String consolidationText = null;
	private Integer lastInteractionPointer = null;
	private int tokensSize = 0;
}