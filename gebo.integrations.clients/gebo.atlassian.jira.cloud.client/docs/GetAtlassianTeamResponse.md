# GetAtlassianTeamResponse

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**capacity** | **Double** | The capacity for the Atlassian team. |  [optional]
**id** | **String** | The Atlassian team ID. | 
**issueSourceId** | **Long** | The ID of the issue source for the Atlassian team. |  [optional]
**planningStyle** | [**PlanningStyleEnum**](#PlanningStyleEnum) | The planning style for the Atlassian team. This is \&quot;Scrum\&quot; or \&quot;Kanban\&quot;. | 
**sprintLength** | **Long** | The sprint length for the Atlassian team. |  [optional]

<a name="PlanningStyleEnum"></a>
## Enum: PlanningStyleEnum
Name | Value
---- | -----
SCRUM | &quot;Scrum&quot;
KANBAN | &quot;Kanban&quot;
