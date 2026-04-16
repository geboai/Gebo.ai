package ai.gebo.sharepoint.handler.impl.model;

import ai.gebo.architecture.search.model.BaseSearchResultsExtractionDataType;
import ai.gebo.sharepoint.handler.search.model.SharepointAdditionalSearchFilter;
import lombok.Data;

@Data
public class MicrosoftResultsExtractionData extends BaseSearchResultsExtractionDataType {

	private SharepointAdditionalSearchFilter additionalSharepointSearchIdeas = new SharepointAdditionalSearchFilter();

}
