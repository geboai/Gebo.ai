# GResponseDocumentRef

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**referenceType** | [**ReferenceTypeEnum**](#ReferenceTypeEnum) |  |  [optional]
**uuid** | **String** |  |  [optional]
**documentCode** | **String** |  |  [optional]
**description** | **String** |  |  [optional]
**contentType** | **String** |  |  [optional]
**extension** | **String** |  |  [optional]
**knowledgeBaseCode** | **String** |  |  [optional]
**projectCode** | **String** |  |  [optional]
**geboTreatAs** | **String** |  |  [optional]
**geboFileTypeDescription** | **String** |  |  [optional]
**geboFileTypeId** | **String** |  |  [optional]
**name** | **String** |  |  [optional]
**loadPercentage** | **Double** |  |  [optional]
**references** | [**List&lt;DocInternalRef&gt;**](DocInternalRef.md) |  |  [optional]
**ntokensRelevant** | **Long** |  |  [optional]
**nbytesRelevant** | **Long** |  |  [optional]
**ntotalContentTokens** | **Long** |  |  [optional]
**shortCode** | **String** |  |  [optional]

<a name="ReferenceTypeEnum"></a>
## Enum: ReferenceTypeEnum
Name | Value
---- | -----
FILE | &quot;FILE&quot;
WEB | &quot;WEB&quot;
