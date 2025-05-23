# WorkflowSearchResponse

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**isLast** | **Boolean** | Whether this is the last page. |  [optional]
**maxResults** | **Integer** | The maximum number of items that could be returned. |  [optional]
**nextPage** | **String** | If there is another page of results, the URL of the next page. |  [optional]
**self** | **String** | The URL of the page. |  [optional]
**startAt** | **Long** | The index of the first item returned. |  [optional]
**statuses** | [**List&lt;JiraWorkflowStatus&gt;**](JiraWorkflowStatus.md) | List of statuses. |  [optional]
**total** | **Long** | The number of items returned. |  [optional]
**values** | [**List&lt;JiraWorkflow&gt;**](JiraWorkflow.md) | List of workflows. |  [optional]
