# GeboAiClient.FileUploadControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getHandShakeCode**](FileUploadControllerApi.md#getHandShakeCode) | **GET** /api/admin/FileUploadController/getHandShakeCode | 
[**upload1**](FileUploadControllerApi.md#upload1) | **POST** /api/admin/FileUploadController/upload/{handShakeCode} | 

<a name="getHandShakeCode"></a>
# **getHandShakeCode**
> HandShakeToken getHandShakeCode()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FileUploadControllerApi();
apiInstance.getHandShakeCode().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**HandShakeToken**](HandShakeToken.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="upload1"></a>
# **upload1**
> upload1(handShakeCode, opts)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FileUploadControllerApi();
let handShakeCode = "handShakeCode_example"; // String | 
let opts = { 
  'files': ["QmFzZTY0IGV4YW1wbGU="] // [Blob] | 
};
apiInstance.upload1(handShakeCode, opts).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **handShakeCode** | **String**|  | 
 **files** | [**[Blob]**](Blob.md)|  | [optional] 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: Not defined

