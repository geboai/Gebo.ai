# GConfluenceSystem

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**code** | **String** |  |  [optional]
**description** | **String** |  |  [optional]
**userModified** | **String** |  |  [optional]
**userCreated** | **String** |  |  [optional]
**dateModified** | [**Date**](Date.md) |  |  [optional]
**dateCreated** | [**Date**](Date.md) |  |  [optional]
**creationDate** | [**Date**](Date.md) |  |  [optional]
**modificationDate** | [**Date**](Date.md) |  |  [optional]
**version** | **String** |  |  [optional]
**contentManagementSystemType** | **String** |  |  [optional]
**readonly** | **Boolean** |  |  [optional]
**baseUri** | **String** |  |  [optional]
**usedCapabilities** | [**List&lt;UsedCapabilitiesEnum&gt;**](#List&lt;UsedCapabilitiesEnum&gt;) |  |  [optional]
**confluenceVersion** | [**ConfluenceVersionEnum**](#ConfluenceVersionEnum) |  |  [optional]
**secretCode** | **String** |  |  [optional]

<a name="List<UsedCapabilitiesEnum>"></a>
## Enum: List&lt;UsedCapabilitiesEnum&gt;
Name | Value
---- | -----
TICKETS_MANAGEMENT | &quot;TICKETS_MANAGEMENT&quot;
DOCUMENTS_MANAGEMENT | &quot;DOCUMENTS_MANAGEMENT&quot;
SOURCE_MANAGEMENT | &quot;SOURCE_MANAGEMENT&quot;
ARTIFACTS_REPOSITORY_MANAGEMENT | &quot;ARTIFACTS_REPOSITORY_MANAGEMENT&quot;

<a name="ConfluenceVersionEnum"></a>
## Enum: ConfluenceVersionEnum
Name | Value
---- | -----
ONPREMISE7X | &quot;ONPREMISE7X&quot;
CLOUD | &quot;CLOUD&quot;
