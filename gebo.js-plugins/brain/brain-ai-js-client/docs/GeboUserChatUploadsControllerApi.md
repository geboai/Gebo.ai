# BrainClient.GeboUserChatUploadsControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**chatSessionUpload**](GeboUserChatUploadsControllerApi.md#chatSessionUpload) | **POST** /api/users/GeboUserChatUploadsController/chatSessionUpload/{userSessionCode} | 
[**deleteSessionUploads**](GeboUserChatUploadsControllerApi.md#deleteSessionUploads) | **DELETE** /api/users/GeboUserChatUploadsController/deleteSessionUploads | 
[**serveContent**](GeboUserChatUploadsControllerApi.md#serveContent) | **GET** /api/users/GeboUserChatUploadsController/serveContent/{userSessionCode}/{uploadedContentId} | 

<a name="chatSessionUpload"></a>
# **chatSessionUpload**
> OperationStatusListUserUploadedContent chatSessionUpload(userSessionCode, opts)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboUserChatUploadsControllerApi();
let userSessionCode = null; // Object | 
let opts = { 
  'files': null // Object | 
};
apiInstance.chatSessionUpload(userSessionCode, opts).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userSessionCode** | [**Object**](.md)|  | 
 **files** | [**Object**](.md)|  | [optional] 

### Return type

[**OperationStatusListUserUploadedContent**](OperationStatusListUserUploadedContent.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

<a name="deleteSessionUploads"></a>
# **deleteSessionUploads**
> OperationStatusListUserUploadedContent deleteSessionUploads()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboUserChatUploadsControllerApi();
apiInstance.deleteSessionUploads().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**OperationStatusListUserUploadedContent**](OperationStatusListUserUploadedContent.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

<a name="serveContent"></a>
# **serveContent**
> serveContent(userSessionCode, uploadedContentId)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboUserChatUploadsControllerApi();
let userSessionCode = null; // Object | 
let uploadedContentId = null; // Object | 

apiInstance.serveContent(userSessionCode, uploadedContentId).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userSessionCode** | [**Object**](.md)|  | 
 **uploadedContentId** | [**Object**](.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

