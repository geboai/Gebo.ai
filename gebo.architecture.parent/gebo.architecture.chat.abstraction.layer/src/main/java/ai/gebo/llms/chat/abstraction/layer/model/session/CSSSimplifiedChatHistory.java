package ai.gebo.llms.chat.abstraction.layer.model.session;

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