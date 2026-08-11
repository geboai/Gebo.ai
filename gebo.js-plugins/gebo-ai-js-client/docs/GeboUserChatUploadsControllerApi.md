# GeboAiClient.GeboUserChatUploadsControllerApi

All URIs are relative to *http://localhost:12999*

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
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboUserChatUploadsControllerApi();
let userSessionCode = "userSessionCode_example"; // String | 
let opts = { 
  'files': ["QmFzZTY0IGV4YW1wbGU="] // [Blob] | 
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
 **userSessionCode** | **String**|  | 
 **files** | [**[Blob]**](Blob.md)|  | [optional] 

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
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboUserChatUploadsControllerApi();
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
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboUserChatUploadsControllerApi();
let userSessionCode = "userSessionCode_example"; // String | 
let uploadedContentId = "uploadedContentId_example"; // String | 

apiInstance.serveContent(userSessionCode, uploadedContentId).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userSessionCode** | **String**|  | 
 **uploadedContentId** | **String**|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

