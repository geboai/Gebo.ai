package ai.gebo.llms.chat.abstraction.layer.session.model;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.architecture.rag.support.layer.model.ITokensCountable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CSSSimplifiedChatHistory implements ITokensCountable {
	private List<CSSSimplefiedInteraction> interactions = new ArrayList();

	public CSSSimplifiedChatHistory(CSSSimplifiedChatHistory original) {
		this.interactions = new ArrayList<CSSSimplefiedInteraction>(original.interactions);
	}

	@Override
	public int getTokensSize() {
		int tokens = 0;
		if (interactions != null) {
			for (CSSSimplefiedInteraction i : interactions) {
				tokens += i.getTokensSize();
			}
		}

		return tokens;
	}
}