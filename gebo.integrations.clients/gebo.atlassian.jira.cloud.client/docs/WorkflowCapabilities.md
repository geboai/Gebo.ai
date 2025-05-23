# WorkflowCapabilities

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**connectRules** | [**List&lt;AvailableWorkflowConnectRule&gt;**](AvailableWorkflowConnectRule.md) | The Connect provided ecosystem rules available. |  [optional]
**editorScope** | [**EditorScopeEnum**](#EditorScopeEnum) | The scope of the workflow capabilities. &#x60;GLOBAL&#x60; for company-managed projects and &#x60;PROJECT&#x60; for team-managed projects. |  [optional]
**forgeRules** | [**List&lt;AvailableWorkflowForgeRule&gt;**](AvailableWorkflowForgeRule.md) | The Forge provided ecosystem rules available. |  [optional]
**projectTypes** | [**List&lt;ProjectTypesEnum&gt;**](#List&lt;ProjectTypesEnum&gt;) | The types of projects that this capability set is available for. |  [optional]
**systemRules** | [**List&lt;AvailableWorkflowSystemRule&gt;**](AvailableWorkflowSystemRule.md) | The Atlassian provided system rules available. |  [optional]
**triggerRules** | [**List&lt;AvailableWorkflowTriggers&gt;**](AvailableWorkflowTriggers.md) | The trigger rules available. |  [optional]

<a name="EditorScopeEnum"></a>
## Enum: EditorScopeEnum
Name | Value
---- | -----
PROJECT | &quot;PROJECT&quot;
GLOBAL | &quot;GLOBAL&quot;

<a name="List<ProjectTypesEnum>"></a>
## Enum: List&lt;ProjectTypesEnum&gt;
Name | Value
---- | -----
SOFTWARE | &quot;software&quot;
SERVICE_DESK | &quot;service_desk&quot;
PRODUCT_DISCOVERY | &quot;product_discovery&quot;
BUSINESS | &quot;business&quot;
UNKNOWN | &quot;unknown&quot;
