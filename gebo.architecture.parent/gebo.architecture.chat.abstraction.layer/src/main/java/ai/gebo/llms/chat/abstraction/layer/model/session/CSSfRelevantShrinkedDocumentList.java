package ai.gebo.llms.chat.abstraction.layer.model.session;

import java.util.ArrayList;
import java.util.Collection;

import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.model.ITokensCountable;

public class CSSfRelevantShrinkedDocumentList extends ArrayList<CSSRelevantShrinkedDocument>
		implements ITokensCountable {

	public CSSfRelevantShrinkedDocumentList() {

	}

	public CSSfRelevantShrinkedDocumentList(int initialCapacity) {
		super(initialCapacity);

	}

	public CSSfRelevantShrinkedDocumentList(Collection<? extends CSSRelevantShrinkedDocument> c) {
		super(c);

	}

	public int getTokensSize() {
		int tokens = 0;
		for (CSSRelevantShrinkedDocument x : this) {
			if (x.getTokensSize() != null) {
				tokens += x.getTokensSize().intValue();
			}
		}
		return tokens;
	}

	public AIDocumentsSet toAIDocumentsSet(float relevencyFilter) {
		AIDocumentsSet outset = new AIDocumentsSet();
		for (CSSRelevantShrinkedDocument doc : this) {
			if (doc.getRelevancyRate() == null || doc.getRelevancyRate().floatValue() >= relevencyFilter)
				outset.getDocumentItems().add(doc.toAIDocumentReferenceItem());
		}
		return outset;
	}

}
