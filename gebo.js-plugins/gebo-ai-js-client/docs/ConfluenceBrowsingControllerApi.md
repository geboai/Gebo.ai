# GeboAiClient.ConfluenceBrowsingControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**browseConfluencePath**](ConfluenceBrowsingControllerApi.md#browseConfluencePath) | **POST** /api/admin/ConfluenceBrowsingController/browseConfluencePath | 
[**getConfluenceNavigationStatus**](ConfluenceBrowsingControllerApi.md#getConfluenceNavigationStatus) | **POST** /api/admin/ConfluenceBrowsingController/getConfluenceNavigationStatus | 
[**getConfluenceRoots**](ConfluenceBrowsingControllerApi.md#getConfluenceRoots) | **GET** /api/admin/ConfluenceBrowsingController/getConfluenceRoots | 

<a name="browseConfluencePath"></a>
# **browseConfluencePath**
> OperationStatusListPathInfo browseConfluencePath(body, systemCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceBrowsingControllerApi();
let body = new GeboAiClient.BrowseParam(); // BrowseParam | 
let systemCode = "systemCode_example"; // String | 

apiInstance.browseConfluencePath(body, systemCode).then((data) => {
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

<a name="getConfluenceNavigationStatus"></a>
# **getConfluenceNavigationStatus**
> OperationStatusListVirtualFilesystemNavigationTreeStatus getConfluenceNavigationStatus(body, systemCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceBrowsingControllerApi();
let body = [new GeboAiClient.VFilesystemReference()]; // [VFilesystemReference] | 
let systemCode = "systemCode_example"; // String | 

apiInstance.getConfluenceNavigationStatus(body, systemCode).then((data) => {
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

<a name="getConfluenceRoots"></a>
# **getConfluenceRoots**
> OperationStatusListGVirtualFilesystemRoot getConfluenceRoots(systemCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceBrowsingControllerApi();
let systemCode = "systemCode_example"; // String | 

apiInstance.getConfluenceRoots(systemCode).then((data) => {
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

