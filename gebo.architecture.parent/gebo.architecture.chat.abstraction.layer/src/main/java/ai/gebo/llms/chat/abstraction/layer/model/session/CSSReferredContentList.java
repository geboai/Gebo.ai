package ai.gebo.llms.chat.abstraction.layer.model.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.model.ITokensCountable;

public class CSSReferredContentList<T> extends ArrayList<CSSInteractionReferredContent<T>> implements ITokensCountable {
	@Override
	public int getTokensSize() {

		return ITokensCountable.tokensSize(new ArrayList<ITokensCountable>(this));
	}

	

	public AIDocumentsSet toAIDocumentsSet() {
		Map<String, AIDocumentReferenceItem> map = new HashMap<String, AIDocumentReferenceItem>();
		List<AIDocumentReferenceItem> docs = this.stream().map(x -> x.getData()).toList();
		for (AIDocumentReferenceItem aiDocumentReferenceItem : docs) {
			map.put(aiDocumentReferenceItem.getCode(), aiDocumentReferenceItem);
		}
		return AIDocumentsSet.fromMap(map);
	}
};