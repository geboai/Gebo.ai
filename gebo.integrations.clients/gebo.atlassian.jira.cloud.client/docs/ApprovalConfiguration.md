# ApprovalConfiguration

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**active** | [**ActiveEnum**](#ActiveEnum) | Whether the approval configuration is active. | 
**conditionType** | [**ConditionTypeEnum**](#ConditionTypeEnum) | How the required approval count is calculated. It may be configured to require a specific number of approvals, or approval by a percentage of approvers. If the approvers source field is Approver groups, you can configure how many approvals per group are required for the request to be approved. The number will be the same across all groups. | 
**conditionValue** | **String** | The number or percentage of approvals required for a request to be approved. If &#x60;conditionType&#x60; is &#x60;number&#x60;, the value must be 20 or less. If &#x60;conditionType&#x60; is &#x60;percent&#x60;, the value must be 100 or less. | 
**exclude** | [**List&lt;ExcludeEnum&gt;**](#List&lt;ExcludeEnum&gt;) | A list of roles that should be excluded as possible approvers. |  [optional]
**fieldId** | **String** | The custom field ID of the \&quot;Approvers\&quot; or \&quot;Approver Groups\&quot; field. | 
**prePopulatedFieldId** | **String** | The custom field ID of the field used to pre-populate the Approver field. Only supports the \&quot;Affected Services\&quot; field. |  [optional]
**transitionApproved** | **String** | The numeric ID of the transition to be executed if the request is approved. | 
**transitionRejected** | **String** | The numeric ID of the transition to be executed if the request is declined. | 

<a name="ActiveEnum"></a>
## Enum: ActiveEnum
Name | Value
---- | -----
TRUE | &quot;true&quot;
FALSE | &quot;false&quot;

<a name="ConditionTypeEnum"></a>
## Enum: ConditionTypeEnum
Name | Value
---- | -----
NUMBER | &quot;number&quot;
PERCENT | &quot;percent&quot;
NUMBERPERPRINCIPAL | &quot;numberPerPrincipal&quot;

<a name="List<ExcludeEnum>"></a>
## Enum: List&lt;ExcludeEnum&gt;
Name | Value
---- | -----
ASSIGNEE | &quot;assignee&quot;
REPORTER | &quot;reporter&quot;
