package ai.gebo.atlassian.confluence.handler.search.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;

@Data
@JsonClassDescription("Filters on Confluence content attributes (CQL fields)")
public class ConfluenceContentAttributeFilter {
    @JsonClassDescription("Group of keywords search matching policy")
    public enum TextMatchMode { ANY, ALL }
    @JsonPropertyDescription("List of Confluence space keys to search in (CQL: space)")
    private List<String> spaceKeys = null;
    @JsonPropertyDescription("List of Confluence content types (e.g., page, blogpost, attachment, comment) (CQL: type)")
    private List<String> contentTypes = null;
    @JsonPropertyDescription("List of Confluence content IDs (numeric) (CQL: id)")
    private List<Long> contentIds = null;
    @JsonPropertyDescription("List of terms/phrases to search in the title (CQL: title ~ \"term\")")
    private List<String> titleTerms = null;
    private TextMatchMode titleTermsMatchMode = null;
    @JsonPropertyDescription("List of terms/phrases to search in the content body (CQL: text ~ \"term\")")
    private List<String> textTerms = null;
    private TextMatchMode textTermsMatchMode = null;
    @JsonPropertyDescription("List of labels (CQL: label)")
    private List<String> labels = null;
    private TextMatchMode labelsMatchMode = null;
    @JsonPropertyDescription("List of ancestor content IDs (pages under a page tree) (CQL: ancestor)")
    private List<Long> ancestorIds = null;
}