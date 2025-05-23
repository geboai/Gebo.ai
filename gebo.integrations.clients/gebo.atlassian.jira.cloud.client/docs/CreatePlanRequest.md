# CreatePlanRequest

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**crossProjectReleases** | [**List&lt;CreateCrossProjectReleaseRequest&gt;**](CreateCrossProjectReleaseRequest.md) | The cross-project releases to include in the plan. |  [optional]
**customFields** | [**List&lt;CreateCustomFieldRequest&gt;**](CreateCustomFieldRequest.md) | The custom fields for the plan. |  [optional]
**exclusionRules** | **AllOfCreatePlanRequestExclusionRules** | The exclusion rules for the plan. |  [optional]
**issueSources** | [**List&lt;CreateIssueSourceRequest&gt;**](CreateIssueSourceRequest.md) | The issue sources to include in the plan. | 
**leadAccountId** | **String** | The account ID of the plan lead. |  [optional]
**name** | **String** | The plan name. | 
**permissions** | [**List&lt;CreatePermissionRequest&gt;**](CreatePermissionRequest.md) | The permissions for the plan. |  [optional]
**scheduling** | **AllOfCreatePlanRequestScheduling** | The scheduling settings for the plan. | 
