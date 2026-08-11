# GeboAiClient.UserKnowledgeBaseBrowsingControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**browseKnowledgeBasePath**](UserKnowledgeBaseBrowsingControllerApi.md#browseKnowledgeBasePath) | **POST** /api/user/UserKnowledgeBaseBrowsingController/browseKnowledgeBasePath | 
[**getAccessibleRootKnowledgeBases**](UserKnowledgeBaseBrowsingControllerApi.md#getAccessibleRootKnowledgeBases) | **GET** /api/user/UserKnowledgeBaseBrowsingController/getAccessibleRootKnowledgeBases | 
[**getKnowledgeBaseNavigationStatus**](UserKnowledgeBaseBrowsingControllerApi.md#getKnowledgeBaseNavigationStatus) | **POST** /api/user/UserKnowledgeBaseBrowsingController/getKnowledgeBaseNavigationStatus | 
[**getKnowledgeBaseRoots**](UserKnowledgeBaseBrowsingControllerApi.md#getKnowledgeBaseRoots) | **GET** /api/user/UserKnowledgeBaseBrowsingController/getKnowledgeBaseRoots | 
[**getVisibleKnowledgeBaseByCodes**](UserKnowledgeBaseBrowsingControllerApi.md#getVisibleKnowledgeBaseByCodes) | **POST** /api/user/UserKnowledgeBaseBrowsingController/getVisibleKnowledgeBaseByCodes | 

<a name="browseKnowledgeBasePath"></a>
# **browseKnowledgeBasePath**
> OperationStatusListPathInfo browseKnowledgeBasePath(body, codes)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UserKnowledgeBaseBrowsingControllerApi();
let body = new GeboAiClient.BrowseParam(); // BrowseParam | 
let codes = ["codes_example"]; // [String] | 

apiInstance.browseKnowledgeBasePath(body, codes).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**BrowseParam**](BrowseParam.md)|  | 
 **codes** | [**[String]**](String.md)|  | 

### Return type

[**OperationStatusListPathInfo**](OperationStatusListPathInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getAccessibleRootKnowledgeBases"></a>
# **getAccessibleRootKnowledgeBases**
> [GBaseObject] getAccessibleRootKnowledgeBases()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UserKnowledgeBaseBrowsingControllerApi();
apiInstance.getAccessibleRootKnowledgeBases().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GBaseObject]**](GBaseObject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getKnowledgeBaseNavigationStatus"></a>
# **getKnowledgeBaseNavigationStatus**
> OperationStatusListVirtualFilesystemNavigationTreeStatus getKnowledgeBaseNavigationStatus(body, codes)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UserKnowledgeBaseBrowsingControllerApi();
let body = [new GeboAiClient.VFilesystemReference()]; // [VFilesystemReference] | 
let codes = ["codes_example"]; // [String] | 

apiInstance.getKnowledgeBaseNavigationStatus(body, codes).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**[VFilesystemReference]**](VFilesystemReference.md)|  | 
 **codes** | [**[String]**](String.md)|  | 

### Return type

[**OperationStatusListVirtualFilesystemNavigationTreeStatus**](OperationStatusListVirtualFilesystemNavigationTreeStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getKnowledgeBaseRoots"></a>
# **getKnowledgeBaseRoots**
> OperationStatusListGVirtualFilesystemRoot getKnowledgeBaseRoots(codes)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UserKnowledgeBaseBrowsingControllerApi();
let codes = ["codes_example"]; // [String] | 

apiInstance.getKnowledgeBaseRoots(codes).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **codes** | [**[String]**](String.md)|  | 

### Return type

[**OperationStatusListGVirtualFilesystemRoot**](OperationStatusListGVirtualFilesystemRoot.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getVisibleKnowledgeBaseByCodes"></a>
# **getVisibleKnowledgeBaseByCodes**
> [GKnowledgeBase] getVisibleKnowledgeBaseByCodes(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UserKnowledgeBaseBrowsingControllerApi();
let body = ["body_example"]; // [String] | 

apiInstance.getVisibleKnowledgeBaseByCodes(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**[String]**](String.md)|  | 

### Return type

[**[GKnowledgeBase]**](GKnowledgeBase.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

