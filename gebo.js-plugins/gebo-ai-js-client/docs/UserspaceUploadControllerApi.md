# GeboAiClient.UserspaceUploadControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**upload**](UserspaceUploadControllerApi.md#upload) | **POST** /api/user/UserspaceUploadController/upload/{userspaceFolderCode} | 

<a name="upload"></a>
# **upload**
> upload(userspaceFolderCode, opts)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UserspaceUploadControllerApi();
let userspaceFolderCode = "userspaceFolderCode_example"; // String | 
let opts = { 
  'files': ["QmFzZTY0IGV4YW1wbGU="] // [Blob] | 
};
apiInstance.upload(userspaceFolderCode, opts).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userspaceFolderCode** | **String**|  | 
 **files** | [**[Blob]**](Blob.md)|  | [optional] 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: Not defined

