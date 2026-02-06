package ai.gebo.llms.chat.abstraction.layer.model.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.model.ITokensCountable;
import lombok.Data;

@Data
public class CSSReferredContentList<T> implements ITokensCountable {
	public static class NestedArrayList<T> extends ArrayList<CSSInteractionReferredContent<T>> {
		@Override
		public CSSInteractionReferredContent<T> get(int index) {
			return super.get(index);
		}

		@Override
		public boolean add(CSSInteractionReferredContent<T> e) {
			return super.add(e);
		}
	}

	NestedArrayList<T> data = new NestedArrayList<T>();

	@Override
	public int getTokensSize() {

		return ITokensCountable.tokensSize(data);
	}

	public AIDocumentsSet toAIDocumentsSet() {
		Map<String, AIDocumentReferenceItem> map = new HashMap<String, AIDocumentReferenceItem>();
		List<AIDocumentReferenceItem> docs = this.data.stream().map(x -> x.getData()).toList();
		for (AIDocumentReferenceItem aiDocumentReferenceItem : docs) {
			map.put(aiDocumentReferenceItem.getCode(), aiDocumentReferenceItem);
		}
		return AIDocumentsSet.fromMap(map);
	}
};