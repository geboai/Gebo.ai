# CreatePlanOnlyTeamRequest

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**capacity** | **Double** | The capacity for the plan-only team. |  [optional]
**issueSourceId** | **Long** | The ID of the issue source for the plan-only team. |  [optional]
**memberAccountIds** | **List&lt;String&gt;** | The account IDs of the plan-only team members. |  [optional]
**name** | **String** | The plan-only team name. | 
**planningStyle** | [**PlanningStyleEnum**](#PlanningStyleEnum) | The planning style for the plan-only team. This must be \&quot;Scrum\&quot; or \&quot;Kanban\&quot;. | 
**sprintLength** | **Long** | The sprint length for the plan-only team. |  [optional]

<a name="PlanningStyleEnum"></a>
## Enum: PlanningStyleEnum
Name | Value
---- | -----
SCRUM | &quot;Scrum&quot;
KANBAN | &quot;Kanban&quot;
