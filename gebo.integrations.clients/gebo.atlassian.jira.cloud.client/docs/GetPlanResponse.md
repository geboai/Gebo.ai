# GetPlanResponse

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**crossProjectReleases** | [**List&lt;GetCrossProjectReleaseResponse&gt;**](GetCrossProjectReleaseResponse.md) | The cross-project releases included in the plan. |  [optional]
**customFields** | [**List&lt;GetCustomFieldResponse&gt;**](GetCustomFieldResponse.md) | The custom fields for the plan. |  [optional]
**exclusionRules** | **AllOfGetPlanResponseExclusionRules** | The exclusion rules for the plan. |  [optional]
**id** | **Long** | The plan ID. | 
**issueSources** | [**List&lt;GetIssueSourceResponse&gt;**](GetIssueSourceResponse.md) | The issue sources included in the plan. |  [optional]
**lastSaved** | **String** | The date when the plan was last saved in UTC. |  [optional]
**leadAccountId** | **String** | The account ID of the plan lead. |  [optional]
**name** | **String** | The plan name. |  [optional]
**permissions** | [**List&lt;GetPermissionResponse&gt;**](GetPermissionResponse.md) | The permissions for the plan. |  [optional]
**scheduling** | **AllOfGetPlanResponseScheduling** | The scheduling settings for the plan. | 
**status** | [**StatusEnum**](#StatusEnum) | The plan status. This is \&quot;Active\&quot;, \&quot;Trashed\&quot; or \&quot;Archived\&quot;. | 

<a name="StatusEnum"></a>
## Enum: StatusEnum
Name | Value
---- | -----
ACTIVE | &quot;Active&quot;
TRASHED | &quot;Trashed&quot;
ARCHIVED | &quot;Archived&quot;
