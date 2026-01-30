package ai.gebo.llms.chat.abstraction.layer.model.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import ai.gebo.architecture.rag.support.layer.model.AIDocumentFragment;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.model.ExtractedDocumentMetaData;
import lombok.Data;

@Data
@JsonClassDescription("Summarized document")
public class CSSRelevantShrinkedDocument {

	@JsonPropertyDescription("uuid, auto generated")
	private String id = null;
	@JsonPropertyDescription("document reference code")
	private String documentReference = null;
	@JsonPropertyDescription("document name (file name normally)")
	private String documentName = null;
	@JsonPropertyDescription("document title")
	private String documentTitle = null;
	@JsonPropertyDescription("document url")
	private String documentUrl = null;
	@JsonPropertyDescription("document summarized content")
	private String summarizedContent = null;
	@JsonPropertyDescription("content relevancy vs last chat exchanged user/assistant messages")
	private Float relevancyRate = null;
	@JsonPropertyDescription("length in tokens")
	private Integer tokensSize = null;
	@JsonPropertyDescription("list of keywords relevant for  last chat exchanged user/assistant messages")
	private List<String> keywords = new ArrayList<String>();
	private Map<String, Object> metaData = new HashMap<String, Object>();

	public AIDocumentReferenceItem toAIDocumentReferenceItem() {
		Document document = new Document(id, summarizedContent, metaData);
		AIDocumentReferenceItem outdoc = new AIDocumentReferenceItem(ExtractedDocumentMetaData.of(metaData));
		AIDocumentFragment fragment = new AIDocumentFragment(document, ExtractedDocumentMetaData.of(metaData));
		fragment.setTokensSize(tokensSize != null ? tokensSize : 0);
		outdoc.getFragments().add(fragment);
		outdoc.recalculateSize();
		return outdoc;
	}
}