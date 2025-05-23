# TransitionPayload

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**actions** | [**List&lt;RulePayload&gt;**](RulePayload.md) | The actions that are performed when the transition is made |  [optional]
**conditions** | [**ConditionGroupPayload**](ConditionGroupPayload.md) |  |  [optional]
**customIssueEventId** | **String** | Mechanism in Jira for triggering certain actions, like notifications, automations, etc. Unless a custom notification scheme is configure, it&#x27;s better not to provide any value here |  [optional]
**description** | **String** | The description of the transition |  [optional]
**from** | [**List&lt;FromLayoutPayload&gt;**](FromLayoutPayload.md) | The statuses that the transition can be made from |  [optional]
**id** | **Integer** | The id of the transition |  [optional]
**name** | **String** | The name of the transition |  [optional]
**properties** | **Map&lt;String, String&gt;** | The properties of the transition |  [optional]
**to** | [**ToLayoutPayload**](ToLayoutPayload.md) |  |  [optional]
**transitionScreen** | [**RulePayload**](RulePayload.md) |  |  [optional]
**triggers** | [**List&lt;RulePayload&gt;**](RulePayload.md) | The triggers that are performed when the transition is made |  [optional]
**type** | [**TypeEnum**](#TypeEnum) | The type of the transition |  [optional]
**validators** | [**List&lt;RulePayload&gt;**](RulePayload.md) | The validators that are performed when the transition is made |  [optional]

<a name="TypeEnum"></a>
## Enum: TypeEnum
Name | Value
---- | -----
GLOBAL | &quot;global&quot;
INITIAL | &quot;initial&quot;
DIRECTED | &quot;directed&quot;
