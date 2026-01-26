package ai.gebo.llms.chat.pipelines.model.defaultrouting;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;

@Data
@JsonClassDescription("Json node carriying query rewritings according to the user question for rag searches")
public class SearchRewritings {
	@JsonPropertyDescription("Semantic searched rewritten sencences related to user question")
	private List<String> rewrittenSemanticSearchSentences = new ArrayList<String>();
	@JsonPropertyDescription("full text searched rewritten texts related to user question")
	private List<String> rewrittenFullTextSearchSentences = new ArrayList<String>();

}
