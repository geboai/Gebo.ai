package ai.gebo.llms.chat.abstraction.layer.session.model;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CSSSimplefiedInteraction implements ITokensCountable, Cloneable {
	private String user = null;
	private Integer userTokenSize = null;
	private String assistant = null;
	private Integer assistantTokenSize = null;
	private String pipelineRoutingDecision = null;
	private Integer interactionIndex = null;
	private String requestId = null;
	private DeliverableIntent userIntent = null;
	/**
	 * The documents the answer rested on (without the fragments they were found by),
	 * null for the interactions saved before they were kept.
	 */
	private List<GResponseDocumentRef> documentsRef = null;

	/** The documents an answer rested on, as kept with the chat's history. */
	public static List<GResponseDocumentRef> keptDocuments(List<GResponseDocumentRef> answerDocuments) {
		if (answerDocuments == null || answerDocuments.isEmpty()) {
			return null;
		}
		final List<GResponseDocumentRef> kept = new ArrayList<>();
		for (GResponseDocumentRef ref : answerDocuments) {
			if (ref != null) {
				kept.add(ref.withoutReferences());
			}
		}
		return kept;
	}

	/**
	 * What follows the answer in the history the model is given: the documents it rested
	 * on, read then, empty when none.
	 */
	public String documentsNote() {
		if (documentsRef == null || documentsRef.isEmpty()) {
			return "";
		}
		final List<String> names = new ArrayList<>();
		for (GResponseDocumentRef ref : documentsRef) {
			final String name = ref.getName() != null ? ref.getName()
					: ref.getNestedSearchResult() != null && ref.getNestedSearchResult().getResultReference() != null
							? ref.getNestedSearchResult().getResultReference().getUri()
							: null;
			if (name != null && !names.contains(name)) {
				names.add(name);
			}
		}
		return names.isEmpty() ? "" : "\n\n" + DOCUMENTS_NOTE_HEAD + String.join("; ", names) + "]";
	}

	/** The head of the note naming the documents an answer rested on. */
	public static final String DOCUMENTS_NOTE_HEAD = "[Documents this answer rested on, read then: ";

	@Override
	public int getTokensSize() {

		return (userTokenSize != null ? userTokenSize : 0) + (assistantTokenSize != null ? assistantTokenSize : 0);
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("Exception cloning", e);
		}
	}
}
