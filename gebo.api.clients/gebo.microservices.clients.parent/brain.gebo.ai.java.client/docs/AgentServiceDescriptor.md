# AgentServiceDescriptor

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**serviceId** | **String** |  |  [optional]
**description** | **String** |  |  [optional]
**inputType** | **String** |  |  [optional]
**outputType** | **String** |  |  [optional]
**networkAgent** | **Boolean** |  |  [optional]
**routingAgent** | **Boolean** |  |  [optional]
**networkAdapter** | **Boolean** |  |  [optional]
**reactive** | **Boolean** |  |  [optional]
**adaptable** | **Boolean** |  |  [optional]
**messageSemantic** | [**MessageSemanticEnum**](#MessageSemanticEnum) |  |  [optional]
**kind** | [**KindEnum**](#KindEnum) |  |  [optional]

<a name="MessageSemanticEnum"></a>
## Enum: MessageSemanticEnum
Name | Value
---- | -----
EXECUTE_AND_SHARE_RESULT | &quot;EXECUTE_AND_SHARE_RESULT&quot;
RESPONSE | &quot;RESPONSE&quot;

<a name="KindEnum"></a>
## Enum: KindEnum
Name | Value
---- | -----
ROUTER | &quot;ROUTER&quot;
FORWARDER | &quot;FORWARDER&quot;
RESPONDER | &quot;RESPONDER&quot;
NETWORK_ADAPTER | &quot;NETWORK_ADAPTER&quot;
WRITER | &quot;WRITER&quot;
