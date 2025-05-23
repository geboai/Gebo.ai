# GetPlanResponseForPage

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** | The plan ID. | 
**issueSources** | [**List&lt;GetIssueSourceResponse&gt;**](GetIssueSourceResponse.md) | The issue sources included in the plan. |  [optional]
**name** | **String** | The plan name. | 
**scenarioId** | **String** | Default scenario ID. | 
**status** | [**StatusEnum**](#StatusEnum) | The plan status. This is \&quot;Active\&quot;, \&quot;Trashed\&quot; or \&quot;Archived\&quot;. | 

<a name="StatusEnum"></a>
## Enum: StatusEnum
Name | Value
---- | -----
ACTIVE | &quot;Active&quot;
TRASHED | &quot;Trashed&quot;
ARCHIVED | &quot;Archived&quot;
