package ai.gebo.sharepoint.search.api;

import ai.gebo.architecture.search.model.BaseSearchResultsExtractionDataType;
import ai.gebo.sharepoint.search.api.SharepointAdditionalSearchFilter;
import lombok.Data;

@Data
public class MicrosoftResultsExtractionData extends BaseSearchResultsExtractionDataType {

	private SharepointAdditionalSearchFilter additionalSharepointSearchIdeas = new SharepointAdditionalSearchFilter();

}
