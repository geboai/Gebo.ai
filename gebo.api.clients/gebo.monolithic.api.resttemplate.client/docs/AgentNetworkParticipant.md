# AgentNetworkParticipant

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**agentConfigCode** | **String** |  | 
**agentContextualName** | **String** |  |  [optional]
**inputNode** | **Boolean** |  |  [optional]
**outputNode** | **Boolean** |  |  [optional]
**allowedToNotifyUser** | **Boolean** |  |  [optional]
**communicationPolicy** | [**CommunicationPolicyEnum**](#CommunicationPolicyEnum) |  | 
**communicationList** | **List&lt;String&gt;** |  |  [optional]
**maxInvocations** | **Integer** |  |  [optional]
**maxConsecutiveInvocations** | **Integer** |  |  [optional]
**canCallTools** | **Boolean** |  |  [optional]
**canCallOtherAgents** | **Boolean** |  |  [optional]
**networkAgentName** | **String** |  |  [optional]

<a name="CommunicationPolicyEnum"></a>
## Enum: CommunicationPolicyEnum
Name | Value
---- | -----
ALLOW_ALL | &quot;ALLOW_ALL&quot;
DENY_ALL | &quot;DENY_ALL&quot;
ALLOW_LIST | &quot;ALLOW_LIST&quot;
DENY_LIST | &quot;DENY_LIST&quot;
