# IssueTransition

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**expand** | **String** | Expand options that include additional transition details in the response. |  [optional]
**fields** | [**Map&lt;String, FieldMetadata&gt;**](FieldMetadata.md) | Details of the fields associated with the issue transition screen. Use this information to populate &#x60;fields&#x60; and &#x60;update&#x60; in a transition request. |  [optional]
**hasScreen** | **Boolean** | Whether there is a screen associated with the issue transition. |  [optional]
**id** | **String** | The ID of the issue transition. Required when specifying a transition to undertake. |  [optional]
**isAvailable** | **Boolean** | Whether the transition is available to be performed. |  [optional]
**isConditional** | **Boolean** | Whether the issue has to meet criteria before the issue transition is applied. |  [optional]
**isGlobal** | **Boolean** | Whether the issue transition is global, that is, the transition is applied to issues regardless of their status. |  [optional]
**isInitial** | **Boolean** | Whether this is the initial issue transition for the workflow. |  [optional]
**looped** | **Boolean** |  |  [optional]
**name** | **String** | The name of the issue transition. |  [optional]
**to** | **AllOfIssueTransitionTo** | Details of the issue status after the transition. |  [optional]
