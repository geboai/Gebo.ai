package ai.gebo.sharepoint.search.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import ai.gebo.sharepoint.search.api.SharePointContentAttributeFilter.TextMatchMode;
import lombok.Data;
@Data
@JsonClassDescription("Additional search related to the actual analyzed text")
public class SharepointAdditionalSearchFilter {

	@JsonPropertyDescription("Free-text terms/phrases (searched across content).")
    private List<String> textTerms = null;
    private TextMatchMode textTermsMatchMode = null;

    @JsonPropertyDescription("Title/name terms/phrases.")
    private List<String> titleTerms = null;
    private TextMatchMode titleTermsMatchMode = null;

}
