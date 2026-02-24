package ai.gebo.atlassian.confluence.handler.search.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;

@Data
@JsonClassDescription("Confluence content filter over people participating the process")
public class ConfluencePeopleFilter {

    @JsonPropertyDescription("List of creators (accountId, public name, or full name depending on visibility) (CQL: creator)")
    private List<String> creatorsList = null;

    @JsonPropertyDescription("List of contributors (accountId/public name/full name depending on visibility) (CQL: contributor)")
    private List<String> contributorsList = null;

    @JsonPropertyDescription("List of mentioned users (accountId/public name/full name depending on visibility) (CQL: mention)")
    private List<String> mentionsList = null;

    @JsonPropertyDescription("List of owners (accountId/public name/full name depending on visibility) (CQL: owner)")
    private List<String> ownersList = null;
}