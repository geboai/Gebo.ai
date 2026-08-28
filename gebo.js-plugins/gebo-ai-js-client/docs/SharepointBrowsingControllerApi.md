# GeboAiClient.SharepointBrowsingControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**browseSharepointPath**](SharepointBrowsingControllerApi.md#browseSharepointPath) | **POST** /api/admin/SharepointBrowsingController/browseSharepointPath | 
[**getSharepointNavigationStatus**](SharepointBrowsingControllerApi.md#getSharepointNavigationStatus) | **POST** /api/admin/SharepointBrowsingController/getSharepointNavigationStatus | 
[**getSharepointRoots**](SharepointBrowsingControllerApi.md#getSharepointRoots) | **GET** /api/admin/SharepointBrowsingController/getSharepointRoots | 

<a name="browseSharepointPath"></a>
# **browseSharepointPath**
> OperationStatusListPathInfo browseSharepointPath(body, systemCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharepointBrowsingControllerApi();
let body = new GeboAiClient.BrowseParam(); // BrowseParam | 
let systemCode = "systemCode_example"; // String | 

apiInstance.browseSharepointPath(body, systemCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**BrowseParam**](BrowseParam.md)|  | 
 **systemCode** | **String**|  | 

### Return type

[**OperationStatusListPathInfo**](OperationStatusListPathInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getSharepointNavigationStatus"></a>
# **getSharepointNavigationStatus**
> OperationStatusListVirtualFilesystemNavigationTreeStatus getSharepointNavigationStatus(body, systemCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharepointBrowsingControllerApi();
let body = [new GeboAiClient.VFilesystemReference()]; // [VFilesystemReference] | 
let systemCode = "systemCode_example"; // String | 

apiInstance.getSharepointNavigationStatus(body, systemCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**[VFilesystemReference]**](VFilesystemReference.md)|  | 
 **systemCode** | **String**|  | 

### Return type

[**OperationStatusListVirtualFilesystemNavigationTreeStatus**](OperationStatusListVirtualFilesystemNavigationTreeStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getSharepointRoots"></a>
# **getSharepointRoots**
> OperationStatusListGVirtualFilesystemRoot getSharepointRoots(systemCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharepointBrowsingControllerApi();
let systemCode = "systemCode_example"; // String | 

apiInstance.getSharepointRoots(systemCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **systemCode** | **String**|  | 

### Return type

[**OperationStatusListGVirtualFilesystemRoot**](OperationStatusListGVirtualFilesystemRoot.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

