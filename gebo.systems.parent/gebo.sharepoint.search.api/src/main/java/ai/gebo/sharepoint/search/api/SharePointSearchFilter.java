package ai.gebo.sharepoint.search.api;

import java.util.List;

import org.springframework.web.reactive.result.method.RequestMappingInfo.BuilderConfiguration;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import ai.gebo.architecture.search.service.INativeQueryObject;
import ai.gebo.architecture.search.service.KeywordListBuilder;
import lombok.Data;

@Data
@JsonClassDescription("SharePoint / OneDrive search filter (Microsoft Graph Search API query builder input)")
public class SharePointSearchFilter  implements INativeQueryObject{

    @JsonPropertyDescription("Filters on SharePoint/OneDrive content attributes")
    private SharePointContentAttributeFilter contentAttributesFilter = new SharePointContentAttributeFilter();

    @JsonPropertyDescription("Filters on people participating in the item lifecycle (created/modified/by etc.)")
    private SharePointPeopleFilter peopleFilter = new SharePointPeopleFilter();

	@Override
	public List<String> relevantKeywords() {
		KeywordListBuilder builder=new KeywordListBuilder();
		builder.addKeywordsProvider(()->{return contentAttributesFilter.getTextTerms();});
		builder.addKeywordsProvider(()->{return contentAttributesFilter.getTitleTerms();});
		return builder.getKeywords();
	}
}