package ai.gebo.llms.chat.abstraction.layer.model.session;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;

@Data
@JsonClassDescription("Summarized document")
public class CSSRelevantShrinkedDocument {
	@Data
	@JsonClassDescription("Relevant keyword contained in the file")
	public static class CSSContainedKeyword {
		@JsonPropertyDescription("extracted keyword")
		private String keyword = null;
		@JsonPropertyDescription("keyword relevancy vs last chat exchanged user/assistant messages")
		private Float relevancyRate = null;

	}
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
	private List<CSSContainedKeyword> keywords=new ArrayList<CSSContainedKeyword>();
}