# GJiraProjectEndpoint

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**code** | **String** |  |  [optional]
**description** | **String** |  |  [optional]
**userModified** | **String** |  |  [optional]
**userCreated** | **String** |  |  [optional]
**dateModified** | [**Date**](Date.md) |  |  [optional]
**dateCreated** | [**Date**](Date.md) |  |  [optional]
**parentProjectCode** | **String** |  |  [optional]
**readonly** | **Boolean** |  |  [optional]
**published** | **Boolean** |  |  [optional]
**synchPeriodically** | **Boolean** |  |  [optional]
**openZips** | **Boolean** |  |  [optional]
**buildSystemsRefs** | [**List&lt;BuildSystemRef&gt;**](BuildSystemRef.md) |  |  [optional]
**catalogingCriteria** | **String** |  |  [optional]
**programmedTables** | [**List&lt;ReindexingProgrammedTable&gt;**](ReindexingProgrammedTable.md) |  |  [optional]
**vectorizeOnlyExtensions** | **List&lt;String&gt;** |  |  [optional]
**synchroStrategy** | [**SynchroStrategyEnum**](#SynchroStrategyEnum) |  |  [optional]
**objectSpaceType** | [**ObjectSpaceTypeEnum**](#ObjectSpaceTypeEnum) |  |  [optional]
**paths** | [**List&lt;VFilesystemReference&gt;**](VFilesystemReference.md) |  |  [optional]
**jiraSystemCode** | **String** |  |  [optional]

<a name="SynchroStrategyEnum"></a>
## Enum: SynchroStrategyEnum
Name | Value
---- | -----
SIZE_AND_TIMESTAMP_AND_HASH_CHECK | &quot;SIZE_AND_TIMESTAMP_AND_HASH_CHECK&quot;
HASH_CHECK | &quot;HASH_CHECK&quot;

<a name="ObjectSpaceTypeEnum"></a>
## Enum: ObjectSpaceTypeEnum
Name | Value
---- | -----
COMPANY | &quot;COMPANY&quot;
USERSPACE | &quot;USERSPACE&quot;
