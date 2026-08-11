# GeboAiClient.JiraBrowsingControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**browseJiraPath**](JiraBrowsingControllerApi.md#browseJiraPath) | **POST** /api/admin/JiraBrowsingController/browseJiraPath | 
[**getJiraNavigationStatus**](JiraBrowsingControllerApi.md#getJiraNavigationStatus) | **POST** /api/admin/JiraBrowsingController/getJiraNavigationStatus | 
[**getJiraRoots**](JiraBrowsingControllerApi.md#getJiraRoots) | **GET** /api/admin/JiraBrowsingController/getJiraRoots | 

<a name="browseJiraPath"></a>
# **browseJiraPath**
> OperationStatusListPathInfo browseJiraPath(body, systemCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraBrowsingControllerApi();
let body = new GeboAiClient.BrowseParam(); // BrowseParam | 
let systemCode = "systemCode_example"; // String | 

apiInstance.browseJiraPath(body, systemCode).then((data) => {
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

<a name="getJiraNavigationStatus"></a>
# **getJiraNavigationStatus**
> OperationStatusListVirtualFilesystemNavigationTreeStatus getJiraNavigationStatus(body, systemCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraBrowsingControllerApi();
let body = [new GeboAiClient.VFilesystemReference()]; // [VFilesystemReference] | 
let systemCode = "systemCode_example"; // String | 

apiInstance.getJiraNavigationStatus(body, systemCode).then((data) => {
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

<a name="getJiraRoots"></a>
# **getJiraRoots**
> OperationStatusListGVirtualFilesystemRoot getJiraRoots(systemCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraBrowsingControllerApi();
let systemCode = "systemCode_example"; // String | 

apiInstance.getJiraRoots(systemCode).then((data) => {
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

