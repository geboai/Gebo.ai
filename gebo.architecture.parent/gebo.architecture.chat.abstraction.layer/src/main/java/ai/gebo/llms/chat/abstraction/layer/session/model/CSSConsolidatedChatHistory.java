package ai.gebo.llms.chat.abstraction.layer.session.model;

import ai.gebo.architecture.ai.model.ITokensCountable;
import lombok.Data;

@Data
public class CSSConsolidatedChatHistory implements ITokensCountable {
	private String consolidationText = null;
	private Integer lastInteractionPointer = null;
	private int consolidationTextTokenSize = 0;
	private CSSSimplifiedChatHistory latestEntries = new CSSSimplifiedChatHistory();
	// the files written by the requests the summary took the place of
	private java.util.List<CSSWrittenFile> earlierWrittenFiles = null;

	@Override
	public int getTokensSize() {

		return ITokensCountable.tokensSize(latestEntries) + consolidationTextTokenSize;
	}

}