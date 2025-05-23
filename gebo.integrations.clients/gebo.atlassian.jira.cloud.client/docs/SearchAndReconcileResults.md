# SearchAndReconcileResults

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**issues** | [**List&lt;IssueBean&gt;**](IssueBean.md) | The list of issues found by the search or reconsiliation. |  [optional]
**names** | **Map&lt;String, String&gt;** | The ID and name of each field in the search results. |  [optional]
**nextPageToken** | **String** | Continuation token to fetch the next page. If this result represents the last or the only page this token will be null. This token will expire in 7 days. |  [optional]
**schema** | [**Map&lt;String, JsonTypeBean&gt;**](JsonTypeBean.md) | The schema describing the field types in the search results. |  [optional]
