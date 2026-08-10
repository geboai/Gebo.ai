# GeboAiClient.GUploadsProjectEndpoint

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
**uploadHandshakeCode** | **String** |  | [optional] 
**uploadedContents** | **[String]** |  | [optional] 

<a name="SynchroStrategyEnum"></a>
## Enum: SynchroStrategyEnum

* `SIZE_AND_TIMESTAMP_AND_HASH_CHECK` (value: `"SIZE_AND_TIMESTAMP_AND_HASH_CHECK"`)
* `HASH_CHECK` (value: `"HASH_CHECK"`)


<a name="ObjectSpaceTypeEnum"></a>
## Enum: ObjectSpaceTypeEnum

* `COMPANY` (value: `"COMPANY"`)
* `USERSPACE` (value: `"USERSPACE"`)

