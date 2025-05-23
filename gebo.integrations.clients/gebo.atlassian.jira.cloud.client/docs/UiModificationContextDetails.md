# UiModificationContextDetails

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** | The ID of the UI modification context. |  [optional]
**isAvailable** | **Boolean** | Whether a context is available. For example, when a project is deleted the context becomes unavailable. |  [optional]
**issueTypeId** | **String** | The issue type ID of the context. Null is treated as a wildcard, meaning the UI modification will be applied to all issue types. Each UI modification context can have a maximum of one wildcard. |  [optional]
**projectId** | **String** | The project ID of the context. Null is treated as a wildcard, meaning the UI modification will be applied to all projects. Each UI modification context can have a maximum of one wildcard. |  [optional]
**viewType** | [**ViewTypeEnum**](#ViewTypeEnum) | The view type of the context. Only &#x60;GIC&#x60;(Global Issue Create), &#x60;IssueView&#x60; and &#x60;IssueTransition&#x60; are supported. Null is treated as a wildcard, meaning the UI modification will be applied to all view types. Each UI modification context can have a maximum of one wildcard. |  [optional]

<a name="ViewTypeEnum"></a>
## Enum: ViewTypeEnum
Name | Value
---- | -----
GIC | &quot;GIC&quot;
ISSUEVIEW | &quot;IssueView&quot;
ISSUETRANSITION | &quot;IssueTransition&quot;
