# IssueBean

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**changelog** | **AllOfIssueBeanChangelog** | Details of changelogs associated with the issue. |  [optional]
**editmeta** | **AllOfIssueBeanEditmeta** | The metadata for the fields on the issue that can be amended. |  [optional]
**expand** | **String** | Expand options that include additional issue details in the response. |  [optional]
**fields** | **Map&lt;String, Object&gt;** |  |  [optional]
**fieldsToInclude** | [**IncludedFields**](IncludedFields.md) |  |  [optional]
**id** | **String** | The ID of the issue. |  [optional]
**key** | **String** | The key of the issue. |  [optional]
**names** | **Map&lt;String, String&gt;** | The ID and name of each field present on the issue. |  [optional]
**operations** | **AllOfIssueBeanOperations** | The operations that can be performed on the issue. |  [optional]
**properties** | **Map&lt;String, Object&gt;** | Details of the issue properties identified in the request. |  [optional]
**renderedFields** | **Map&lt;String, Object&gt;** | The rendered value of each field present on the issue. |  [optional]
**schema** | [**Map&lt;String, JsonTypeBean&gt;**](JsonTypeBean.md) | The schema describing each field present on the issue. |  [optional]
**self** | **String** | The URL of the issue details. |  [optional]
**transitions** | [**List&lt;IssueTransition&gt;**](IssueTransition.md) | The transitions that can be performed on the issue. |  [optional]
**versionedRepresentations** | [**Map&lt;String, Map&lt;String, Object&gt;&gt;**](Map.md) | The versions of each field on the issue. |  [optional]
