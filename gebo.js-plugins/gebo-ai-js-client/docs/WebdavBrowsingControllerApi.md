# GeboAiClient.WebdavBrowsingControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**browseWebdavPath**](WebdavBrowsingControllerApi.md#browseWebdavPath) | **POST** /api/admin/WebdavBrowsingController/browseWebdavPath | 
[**getWebdavNavigationStatus**](WebdavBrowsingControllerApi.md#getWebdavNavigationStatus) | **POST** /api/admin/WebdavBrowsingController/getWebdavNavigationStatus | 
[**getWebdavRoots**](WebdavBrowsingControllerApi.md#getWebdavRoots) | **GET** /api/admin/WebdavBrowsingController/getWebdavRoots | 

<a name="browseWebdavPath"></a>
# **browseWebdavPath**
> OperationStatusListPathInfo browseWebdavPath(body, systemCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.WebdavBrowsingControllerApi();
let body = new GeboAiClient.BrowseParam(); // BrowseParam | 
let systemCode = "systemCode_example"; // String | 

apiInstance.browseWebdavPath(body, systemCode).then((data) => {
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

<a name="getWebdavNavigationStatus"></a>
# **getWebdavNavigationStatus**
> OperationStatusListVirtualFilesystemNavigationTreeStatus getWebdavNavigationStatus(body, systemCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.WebdavBrowsingControllerApi();
let body = [new GeboAiClient.VFilesystemReference()]; // [VFilesystemReference] | 
let systemCode = "systemCode_example"; // String | 

apiInstance.getWebdavNavigationStatus(body, systemCode).then((data) => {
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

<a name="getWebdavRoots"></a>
# **getWebdavRoots**
> OperationStatusListGVirtualFilesystemRoot getWebdavRoots(systemCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.WebdavBrowsingControllerApi();
let systemCode = "systemCode_example"; // String | 

apiInstance.getWebdavRoots(systemCode).then((data) => {
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

