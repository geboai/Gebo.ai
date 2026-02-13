package ai.gebo.llms.chat.abstraction.layer.session.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.model.ITokensCountable;
import lombok.Data;

@Data
public class CSSReferredContentList<T> implements ITokensCountable {
	public static class NestedArrayList<T> implements ITokensCountable {
		private ArrayList<CSSInteractionReferredContent<T>> container = new ArrayList();

		public CSSInteractionReferredContent<T> get(int index) {
			return container.get(index);
		}

		public boolean add(CSSInteractionReferredContent<T> e) {
			return container.add(e);
		}

		public Stream<CSSInteractionReferredContent<T>> stream() {
			return container.stream();
		}

		@Override
		public int getTokensSize() {

			return ITokensCountable.tokensSize(container);
		}

		public boolean isEmpty() {

			return container.isEmpty();
		}

		public void remove(int i) {
			container.remove(i);

		}

		public int size() {
			
			return container.size();
		}
	}

	private NestedArrayList<T> data = new NestedArrayList<T>();

	@Override
	public int getTokensSize() {

		return ITokensCountable.tokensSize(data);
	}

	public AIDocumentsSet toAIDocumentsSet() {
		Map<String, AIDocumentReferenceItem> map = new HashMap<String, AIDocumentReferenceItem>();
		List<AIDocumentReferenceItem> docs = this.data.stream().map(x -> x.getAiDocument()).toList();
		for (AIDocumentReferenceItem aiDocumentReferenceItem : docs) {
			map.put(aiDocumentReferenceItem.getCode(), aiDocumentReferenceItem);
		}
		return AIDocumentsSet.fromMap(map);
	}

	
};