# GeboAiClient.AgentNetworkParticipant

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**agentConfigCode** | **String** |  | 
**agentContextualName** | **String** |  | [optional] 
**inputNode** | **Boolean** |  | [optional] 
**outputNode** | **Boolean** |  | [optional] 
**allowedToNotifyUser** | **Boolean** |  | [optional] 
**communicationPolicy** | **String** |  | 
**communicationList** | **[String]** |  | [optional] 
**maxInvocations** | **Number** |  | [optional] 
**maxConsecutiveInvocations** | **Number** |  | [optional] 
**canCallTools** | **Boolean** |  | [optional] 
**canCallOtherAgents** | **Boolean** |  | [optional] 
**networkAgentName** | **String** |  | [optional] 

<a name="CommunicationPolicyEnum"></a>
## Enum: CommunicationPolicyEnum

* `ALLOW_ALL` (value: `"ALLOW_ALL"`)
* `DENY_ALL` (value: `"DENY_ALL"`)
* `ALLOW_LIST` (value: `"ALLOW_LIST"`)
* `DENY_LIST` (value: `"DENY_LIST"`)

