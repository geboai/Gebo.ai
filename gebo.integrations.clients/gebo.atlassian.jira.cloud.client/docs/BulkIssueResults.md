# BulkIssueResults

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**issueErrors** | [**List&lt;IssueError&gt;**](IssueError.md) | When Jira can&#x27;t return an issue enumerated in a request due to a retriable error or payload constraint, we&#x27;ll return the respective issue ID with a corresponding error message. This list is empty when there are no errors Issues which aren&#x27;t found or that the user doesn&#x27;t have permission to view won&#x27;t be returned in this list. |  [optional]
**issues** | [**List&lt;IssueBean&gt;**](IssueBean.md) | The list of issues. |  [optional]
