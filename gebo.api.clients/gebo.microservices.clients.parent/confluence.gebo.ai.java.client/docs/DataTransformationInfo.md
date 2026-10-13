# DataTransformationInfo

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** |  | 
**description** | **String** |  | 
**transformationInfo** | [**DataTransformationMetaInfo**](DataTransformationMetaInfo.md) |  | 
**dataSourceId** | **String** |  | 
**dataDestinationId** | **String** |  | 
**carried** | [**CarriedEnum**](#CarriedEnum) |  |  [optional]
**section** | **String** |  |  [optional]

<a name="CarriedEnum"></a>
## Enum: CarriedEnum
Name | Value
---- | -----
CONTENT | &quot;CONTENT&quot;
PROCESSED | &quot;PROCESSED&quot;
REQUEST | &quot;REQUEST&quot;
