package ai.gebo.llms.chat.abstraction.layer.model.session;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.llms.chat.abstraction.layer.model.GUserChatInteractionsConsolidationData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CSSSimplifiedChatHistory implements ITokensCountable {
	private List<CSSSimplefiedInteraction> interactions = new ArrayList();
	private GUserChatInteractionsConsolidationData consolidation = null;

	@Override
	public int getTokensSize() {
		int tokens = 0;
		if (interactions != null) {
			for (CSSSimplefiedInteraction i : interactions) {
				tokens += i.getTokensSize();
			}
		}
		if (consolidation != null) {
			tokens += consolidation.getTokensSize();
		}
		return tokens;
	}
}