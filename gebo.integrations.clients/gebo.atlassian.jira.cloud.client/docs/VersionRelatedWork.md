# VersionRelatedWork

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**category** | **String** | The category of the related work | 
**issueId** | **Long** | The ID of the issue associated with the related work (if there is one). Cannot be updated via the Rest API. |  [optional]
**relatedWorkId** | **String** | The id of the related work. For the native release note related work item, this will be null, and Rest API does not support updating it. |  [optional]
**title** | **String** | The title of the related work |  [optional]
**url** | **String** | The URL of the related work. Will be null for the native release note related work item, but is otherwise required. |  [optional]
