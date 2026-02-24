package ai.gebo.atlassian.jira.handler.search.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;

@Data
@JsonClassDescription("Jira Issue filter over people participating the process")
public class JiraPeopleFilter {
	@JsonPropertyDescription("List of possible issues/tasks assignee")
	List<String> assigneesList = null;
	@JsonPropertyDescription("List of  possible  issues/tasks reporters")
	List<String> reportersList = null;
	@JsonPropertyDescription("List of  possible  issues/tasks creators")
	List<String> creatorsList = null;
}
