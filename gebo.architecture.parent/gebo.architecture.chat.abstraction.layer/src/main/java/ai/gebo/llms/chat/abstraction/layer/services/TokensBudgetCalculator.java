package ai.gebo.llms.chat.abstraction.layer.services;

import java.util.List;

import org.springframework.ai.document.Document;

import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.model.DocumentMetaInfos;

public class TokensBudgetCalculator {

	public static boolean higherThanBudget(List<Document> docs, long tokensBudget) {
		long tokens = 0;
		for (Document document : docs) {
			tokens += ITokensCountable.tokensSize(document.getMetadata());
			if (document.getMetadata() != null
					&& document.getMetadata().get(DocumentMetaInfos.GEBO_TOKEN_LENGTH) instanceof Number length) {
				tokens += length.longValue();
			} else {
				tokens += ITokensCountable.stringsTokensSize(document.getText());
			}
		}
		return tokens >= tokensBudget;
	}

}
