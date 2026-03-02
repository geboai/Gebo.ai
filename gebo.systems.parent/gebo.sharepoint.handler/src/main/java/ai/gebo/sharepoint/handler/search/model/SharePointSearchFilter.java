package ai.gebo.sharepoint.handler.search.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;

@Data
@JsonClassDescription("SharePoint / OneDrive search filter (Microsoft Graph Search API query builder input)")
public class SharePointSearchFilter {

    @JsonPropertyDescription("Filters on SharePoint/OneDrive content attributes")
    private SharePointContentAttributeFilter contentAttributesFilter = new SharePointContentAttributeFilter();

    @JsonPropertyDescription("Filters on people participating in the item lifecycle (created/modified/by etc.)")
    private SharePointPeopleFilter peopleFilter = new SharePointPeopleFilter();
}