# GSharepointContentManagementSystem

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
**secretCode** | **String** |  | 
**sharepointVersion** | [**SharepointVersionEnum**](#SharepointVersionEnum) |  | 

<a name="List<UsedCapabilitiesEnum>"></a>
## Enum: List&lt;UsedCapabilitiesEnum&gt;
Name | Value
---- | -----
TICKETS_MANAGEMENT | &quot;TICKETS_MANAGEMENT&quot;
DOCUMENTS_MANAGEMENT | &quot;DOCUMENTS_MANAGEMENT&quot;
SOURCE_MANAGEMENT | &quot;SOURCE_MANAGEMENT&quot;
ARTIFACTS_REPOSITORY_MANAGEMENT | &quot;ARTIFACTS_REPOSITORY_MANAGEMENT&quot;

<a name="SharepointVersionEnum"></a>
## Enum: SharepointVersionEnum
Name | Value
---- | -----
CLOUD_VERSION | &quot;CLOUD_VERSION&quot;
ONPREMISE2019 | &quot;ONPREMISE2019&quot;
