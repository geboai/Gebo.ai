package ai.gebo.llms.chat.pipelines.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;

@Data
@JsonClassDescription("Json node carriying full text/semantic searches suggestions according to the user question for rag searches")
public class SearchesSuggestions {
	@JsonPropertyDescription("Semantic searched  sencences related to user question")
	private List<String> rewrittenSemanticSearchSentences = new ArrayList<String>();
	@JsonPropertyDescription("full text searched  texts related to user question")
	private List<String> rewrittenFullTextSearchSentences = new ArrayList<String>();
	private List<String> suggestedDocuments=new ArrayList<String>();

}
