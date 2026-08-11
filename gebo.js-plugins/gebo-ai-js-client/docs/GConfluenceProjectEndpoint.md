# GeboAiClient.GConfluenceProjectEndpoint

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**code** | **String** |  | [optional] 
**description** | **String** |  | [optional] 
**userModified** | **String** |  | [optional] 
**userCreated** | **String** |  | [optional] 
**dateModified** | **Date** |  | [optional] 
**dateCreated** | **Date** |  | [optional] 
**parentProjectCode** | **String** |  | [optional] 
**readonly** | **Boolean** |  | [optional] 
**published** | **Boolean** |  | [optional] 
**synchPeriodically** | **Boolean** |  | [optional] 
**openZips** | **Boolean** |  | [optional] 
**buildSystemsRefs** | [**[BuildSystemRef]**](BuildSystemRef.md) |  | [optional] 
**catalogingCriteria** | **String** |  | [optional] 
**programmedTables** | [**[ReindexingProgrammedTable]**](ReindexingProgrammedTable.md) |  | [optional] 
**vectorizeOnlyExtensions** | **[String]** |  | [optional] 
**synchroStrategy** | **String** |  | [optional] 
**objectSpaceType** | **String** |  | [optional] 
**aclAliases** | **[Number]** |  | [optional] 
**paths** | [**[VFilesystemReference]**](VFilesystemReference.md) |  | [optional] 
**confluenceSystemCode** | **String** |  | [optional] 
**extractedFormat** | **String** |  | [optional] 
**extractAndSaveContents** | **Boolean** |  | [optional] 
**confluenceVersion** | **String** |  | [optional] 

<a name="SynchroStrategyEnum"></a>
## Enum: SynchroStrategyEnum

* `SIZE_AND_TIMESTAMP_AND_HASH_CHECK` (value: `"SIZE_AND_TIMESTAMP_AND_HASH_CHECK"`)
* `HASH_CHECK` (value: `"HASH_CHECK"`)


<a name="ObjectSpaceTypeEnum"></a>
## Enum: ObjectSpaceTypeEnum

* `COMPANY` (value: `"COMPANY"`)
* `USERSPACE` (value: `"USERSPACE"`)


<a name="ExtractedFormatEnum"></a>
## Enum: ExtractedFormatEnum

* `HTML` (value: `"HTML"`)
* `WORD` (value: `"WORD"`)
* `PDF` (value: `"PDF"`)


<a name="ConfluenceVersionEnum"></a>
## Enum: ConfluenceVersionEnum

* `oNPREMISE7X` (value: `"ONPREMISE7X"`)
* `CLOUD` (value: `"CLOUD"`)

