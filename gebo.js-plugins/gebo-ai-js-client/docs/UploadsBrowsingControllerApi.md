# GeboAiClient.UploadsBrowsingControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**browseUploadsEndpointPath**](UploadsBrowsingControllerApi.md#browseUploadsEndpointPath) | **POST** /api/admin/UploadsBrowsingController/browseUploadsEndpointPath | 
[**getUploadsEndpointNavigationStatus**](UploadsBrowsingControllerApi.md#getUploadsEndpointNavigationStatus) | **POST** /api/admin/UploadsBrowsingController/getUploadsEndpointNavigationStatus | 
[**getUploadsEndpointRoots**](UploadsBrowsingControllerApi.md#getUploadsEndpointRoots) | **GET** /api/admin/UploadsBrowsingController/getUploadsEndpointRoots | 
[**serveUploadsEndpointFile**](UploadsBrowsingControllerApi.md#serveUploadsEndpointFile) | **GET** /api/admin/UploadsBrowsingController/serveUploadsEndpointFile | 

<a name="browseUploadsEndpointPath"></a>
# **browseUploadsEndpointPath**
> OperationStatusListPathInfo browseUploadsEndpointPath(body, endpointCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UploadsBrowsingControllerApi();
let body = new GeboAiClient.BrowseParam(); // BrowseParam | 
let endpointCode = "endpointCode_example"; // String | 

apiInstance.browseUploadsEndpointPath(body, endpointCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**BrowseParam**](BrowseParam.md)|  | 
 **endpointCode** | **String**|  | 

### Return type

[**OperationStatusListPathInfo**](OperationStatusListPathInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUploadsEndpointNavigationStatus"></a>
# **getUploadsEndpointNavigationStatus**
> OperationStatusListVirtualFilesystemNavigationTreeStatus getUploadsEndpointNavigationStatus(body, endpointCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UploadsBrowsingControllerApi();
let body = [new GeboAiClient.VFilesystemReference()]; // [VFilesystemReference] | 
let endpointCode = "endpointCode_example"; // String | 

apiInstance.getUploadsEndpointNavigationStatus(body, endpointCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**[VFilesystemReference]**](VFilesystemReference.md)|  | 
 **endpointCode** | **String**|  | 

### Return type

[**OperationStatusListVirtualFilesystemNavigationTreeStatus**](OperationStatusListVirtualFilesystemNavigationTreeStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUploadsEndpointRoots"></a>
# **getUploadsEndpointRoots**
> OperationStatusListGVirtualFilesystemRoot getUploadsEndpointRoots(endpointCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UploadsBrowsingControllerApi();
let endpointCode = "endpointCode_example"; // String | 

apiInstance.getUploadsEndpointRoots(endpointCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **endpointCode** | **String**|  | 

### Return type

[**OperationStatusListGVirtualFilesystemRoot**](OperationStatusListGVirtualFilesystemRoot.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="serveUploadsEndpointFile"></a>
# **serveUploadsEndpointFile**
> &#x27;Blob&#x27; serveUploadsEndpointFile(endpointCode, path)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UploadsBrowsingControllerApi();
let endpointCode = "endpointCode_example"; // String | 
let path = "path_example"; // String | 

apiInstance.serveUploadsEndpointFile(endpointCode, path).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **endpointCode** | **String**|  | 
 **path** | **String**|  | 

### Return type

**&#x27;Blob&#x27;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

