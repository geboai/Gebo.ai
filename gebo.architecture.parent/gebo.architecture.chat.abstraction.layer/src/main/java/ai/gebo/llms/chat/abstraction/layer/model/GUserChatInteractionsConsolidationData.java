package ai.gebo.llms.chat.abstraction.layer.model;

import ai.gebo.llms.chat.abstraction.layer.model.session.ITokensCountable;
import lombok.Data;

@Data
public class GUserChatInteractionsConsolidationData implements ITokensCountable {
	private String consolidationText = null;
	private Integer lastInteractionPointer = null;
	private int tokensSize = 0;
}