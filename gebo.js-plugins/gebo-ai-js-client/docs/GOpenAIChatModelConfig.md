# GeboAiClient.GOpenAIChatModelConfig

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**code** | **String** |  | [optional] 
**description** | **String** |  | [optional] 
**userModified** | **String** |  | [optional] 
**userCreated** | **String** |  | [optional] 
**dateModified** | **Date** |  | [optional] 
**dateCreated** | **Date** |  | [optional] 
**modelTypeCode** | **String** |  | [optional] 
**defaultModel** | **Boolean** |  | [optional] 
**apiSecretCode** | **String** |  | [optional] 
**choosedModel** | [**GOpenAIChatModelChoice**](GOpenAIChatModelChoice.md) |  | [optional] 
**baseUrl** | **String** |  | [optional] 
**contextLength** | **Number** |  | [optional] 
**topP** | **Number** |  | [optional] 
**accessibleGroups** | **[String]** |  | [optional] 
**accessibleUsers** | **[String]** |  | [optional] 
**accessibleToAll** | **Boolean** |  | [optional] 
**enabledFunctions** | **[String]** |  | [optional] 
**temperature** | **Number** |  | [optional] 
**forUses** | **[String]** |  | [optional] 
**features** | **[String]** |  | [optional] 
**thinking** | **String** |  | [optional] 
**maxGeneratedTokens** | **Number** |  | [optional] 

<a name="[ForUsesEnum]"></a>
## Enum: [ForUsesEnum]

* `CHAT` (value: `"CHAT"`)
* `INTERNAL_SERVICES` (value: `"INTERNAL_SERVICES"`)


<a name="[FeaturesEnum]"></a>
## Enum: [FeaturesEnum]

* `CHAT` (value: `"CHAT"`)
* `REASONING` (value: `"REASONING"`)
* `STRUCTURED_OUTPUT` (value: `"STRUCTURED_OUTPUT"`)
* `MULTIMEDIA` (value: `"MULTIMEDIA"`)
* `FUNCTION_CALLING` (value: `"FUNCTION_CALLING"`)


<a name="ThinkingEnum"></a>
## Enum: ThinkingEnum

* `NO_THINKING` (value: `"NO_THINKING"`)
* `LOW_THINKING` (value: `"LOW_THINKING"`)
* `MEDIUM_THINKING` (value: `"MEDIUM_THINKING"`)
* `HIGH_THINKING` (value: `"HIGH_THINKING"`)
* `AUTO` (value: `"AUTO"`)

