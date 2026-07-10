# GDocumentReference

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**code** | **String** |  | 
**description** | **String** |  |  [optional]
**userModified** | **String** |  |  [optional]
**userCreated** | **String** |  |  [optional]
**dateModified** | [**Date**](Date.md) |  |  [optional]
**dateCreated** | [**Date**](Date.md) |  |  [optional]
**creationDate** | [**Date**](Date.md) |  |  [optional]
**modificationDate** | [**Date**](Date.md) |  |  [optional]
**version** | **String** |  |  [optional]
**parentVirtualFolderCode** | **String** |  |  [optional]
**absolutePath** | **String** |  |  [optional]
**parentProjectCode** | **String** |  |  [optional]
**rootKnowledgebaseCode** | **String** |  |  [optional]
**uri** | **String** |  |  [optional]
**relativePath** | **String** |  |  [optional]
**name** | **String** |  |  [optional]
**deleted** | **Boolean** |  |  [optional]
**messagingModuleId** | **String** |  |  [optional]
**projectEndpointReference** | [**GObjectRefGProjectEndpoint**](GObjectRefGProjectEndpoint.md) |  |  [optional]
**nestedInArchive** | **Boolean** |  |  [optional]
**absoluteArchivePath** | **String** |  |  [optional]
**archiveInternalPath** | **String** |  |  [optional]
**customMetaInfos** | **Map&lt;String, Object&gt;** |  |  [optional]
**lastesJobId** | **String** |  |  [optional]
**aclAliases** | **List&lt;Integer&gt;** |  |  [optional]
**extIntegrationCode** | **String** |  |  [optional]
**synchronizationUUID** | **String** |  |  [optional]
**extension** | **String** |  |  [optional]
**contentType** | **String** |  |  [optional]
**geboFileArchetypeId** | **String** |  |  [optional]
**fileSize** | **Long** |  |  [optional]
**unmanagedContentType** | **Boolean** |  |  [optional]
**referenceType** | [**ReferenceTypeEnum**](#ReferenceTypeEnum) |  |  [optional]
**skippedVectorizationContent** | **Boolean** |  |  [optional]
**artificiallyGeneratedContent** | **String** |  |  [optional]
**originComponent** | [**GeboComponentInfo**](GeboComponentInfo.md) |  | 
**attributesValues** | [**List&lt;GDocumentAttributeValue&gt;**](GDocumentAttributeValue.md) |  |  [optional]
**langCode** | **String** |  |  [optional]
**translationOfDocumentCode** | **String** |  |  [optional]
**categoryCodes** | **List&lt;String&gt;** |  |  [optional]
**publishedDate** | [**Date**](Date.md) |  |  [optional]
**author** | **String** |  |  [optional]

<a name="ReferenceTypeEnum"></a>
## Enum: ReferenceTypeEnum
Name | Value
---- | -----
FILE | &quot;FILE&quot;
WEB | &quot;WEB&quot;
