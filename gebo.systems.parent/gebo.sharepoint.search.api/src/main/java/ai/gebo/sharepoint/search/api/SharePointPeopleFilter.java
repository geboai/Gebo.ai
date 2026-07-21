package ai.gebo.sharepoint.search.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;

@Data
@JsonClassDescription("SharePoint people filter (mapped to managed properties; depends on tenant schema)")
public class SharePointPeopleFilter {

	@JsonPropertyDescription("Creators (UPN/email/display name depending on your schema/mapping).")
	private List<String> createdByList = null;

	@JsonPropertyDescription("Last modifiers (UPN/email/display name depending on your schema/mapping).")
	private List<String> lastModifiedByList = null;
}