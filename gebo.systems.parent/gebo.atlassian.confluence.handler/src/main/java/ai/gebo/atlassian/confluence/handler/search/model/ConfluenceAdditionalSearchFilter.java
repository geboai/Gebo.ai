package ai.gebo.atlassian.confluence.handler.search.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import ai.gebo.atlassian.confluence.handler.search.model.ConfluenceContentAttributeFilter.TextMatchMode;
import lombok.Data;

@Data
@JsonClassDescription("Additional search related to the actual analyzed text")
public class ConfluenceAdditionalSearchFilter {

	@JsonPropertyDescription("List of terms/phrases to search in the title (CQL: title ~ \"term\")")
	private List<String> titleTerms = null;
	private TextMatchMode titleTermsMatchMode = null;
	@JsonPropertyDescription("List of terms/phrases to search in the content body (CQL: text ~ \"term\")")
	private List<String> textTerms = null;
	private TextMatchMode textTermsMatchMode = null;
	@JsonPropertyDescription("List of labels (CQL: label)")
	private List<String> labels = null;
	private TextMatchMode labelsMatchMode = null;

}
