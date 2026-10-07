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
	/**
	 * The names of the documents the tools only listed for the answer (not read) that it
	 * names, null when none.
	 */
	private List<String> listedDocumentNames = null;

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
	 * The names of the documents the answer rested on, read then, in their order and
	 * once each: what the model is told of them with the chat's earlier answers.
	 */
	public List<String> documentNames() {
		final List<String> names = new ArrayList<>();
		if (documentsRef == null) {
			return names;
		}
		for (GResponseDocumentRef ref : documentsRef) {
			final String name = ref.getName() != null ? ref.getName()
					: ref.getNestedSearchResult() != null && ref.getNestedSearchResult().getResultReference() != null
							? ref.getNestedSearchResult().getResultReference().getUri()
							: null;
			if (name != null && !names.contains(name)) {
				names.add(name);
			}
		}
		return names;
	}

	/**
	 * The size of what the model is told of the documents of this answer: the names of
	 * the ones it rested on and of the ones the tools only listed.
	 */
	public int documentsTokensSize() {
		final List<String> names = new ArrayList<>(documentNames());
		if (listedDocumentNames != null) {
			names.addAll(listedDocumentNames);
		}
		return names.isEmpty() ? 0 : ITokensCountable.tokensEstimator.estimate(String.join("; ", names));
	}

	/**
	 * Where a note naming the documents an answer rested on starts: the history gave the
	 * model such notes after the earlier answers, and models copied them into their own.
	 */
	public static final String DOCUMENTS_NOTE_START = "[Documents this answer rested on";
	/** A note: its line, up to its closing bracket or the line end. */
	private static final java.util.regex.Pattern DOCUMENTS_NOTE = java.util.regex.Pattern
			.compile("[ \\t]*(\\r?\\n)*[ \\t]*" + java.util.regex.Pattern.quote(DOCUMENTS_NOTE_START)
					+ "[^\\]\\r\\n]*\\]?");

	/**
	 * The text without the notes naming the documents an answer rested on: an answer
	 * holding one copied it, and neither the user nor the model is shown it again.
	 */
	public static String withoutDocumentsNotes(String text) {
		if (text == null || !text.contains(DOCUMENTS_NOTE_START)) {
			return text;
		}
		return DOCUMENTS_NOTE.matcher(text).replaceAll("");
	}

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
