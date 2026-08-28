# GeboAiClient.FileSystemsBrowsingControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**browseSharedFilesystemRootsPath**](FileSystemsBrowsingControllerApi.md#browseSharedFilesystemRootsPath) | **POST** /api/admin/FileSystemsBrowsingController/browseSharedFilesystemRootsPath | 
[**getSharedFilesystemNavigationStatus**](FileSystemsBrowsingControllerApi.md#getSharedFilesystemNavigationStatus) | **POST** /api/admin/FileSystemsBrowsingController/getSharedFilesystemNavigationStatus | 
[**getSharedFilesystemRoots**](FileSystemsBrowsingControllerApi.md#getSharedFilesystemRoots) | **GET** /api/admin/FileSystemsBrowsingController/getSharedFilesystemRoots | 

<a name="browseSharedFilesystemRootsPath"></a>
# **browseSharedFilesystemRootsPath**
> OperationStatusListPathInfo browseSharedFilesystemRootsPath(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FileSystemsBrowsingControllerApi();
let body = new GeboAiClient.BrowseParam(); // BrowseParam | 

apiInstance.browseSharedFilesystemRootsPath(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**BrowseParam**](BrowseParam.md)|  | 

### Return type

[**OperationStatusListPathInfo**](OperationStatusListPathInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getSharedFilesystemNavigationStatus"></a>
# **getSharedFilesystemNavigationStatus**
> OperationStatusListVirtualFilesystemNavigationTreeStatus getSharedFilesystemNavigationStatus(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FileSystemsBrowsingControllerApi();
let body = [new GeboAiClient.VFilesystemReference()]; // [VFilesystemReference] | 

apiInstance.getSharedFilesystemNavigationStatus(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**[VFilesystemReference]**](VFilesystemReference.md)|  | 

### Return type

[**OperationStatusListVirtualFilesystemNavigationTreeStatus**](OperationStatusListVirtualFilesystemNavigationTreeStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getSharedFilesystemRoots"></a>
# **getSharedFilesystemRoots**
> OperationStatusListGVirtualFilesystemRoot getSharedFilesystemRoots()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FileSystemsBrowsingControllerApi();
apiInstance.getSharedFilesystemRoots().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**OperationStatusListGVirtualFilesystemRoot**](OperationStatusListGVirtualFilesystemRoot.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

