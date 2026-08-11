# BrainClient.UserKnowledgeBaseBrowsingControllerApi

All URIs are relative to *http://localhost:13001/brain*

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
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.UserKnowledgeBaseBrowsingControllerApi();
let body = new BrainClient.BrowseParam(); // BrowseParam | 
let codes = null; // Object | 

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
 **codes** | [**Object**](.md)|  | 

### Return type

[**OperationStatusListPathInfo**](OperationStatusListPathInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getAccessibleRootKnowledgeBases"></a>
# **getAccessibleRootKnowledgeBases**
> Object getAccessibleRootKnowledgeBases()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.UserKnowledgeBaseBrowsingControllerApi();
apiInstance.getAccessibleRootKnowledgeBases().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

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
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.UserKnowledgeBaseBrowsingControllerApi();
let body = null; // Object | 
let codes = null; // Object | 

apiInstance.getKnowledgeBaseNavigationStatus(body, codes).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  | 
 **codes** | [**Object**](.md)|  | 

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
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.UserKnowledgeBaseBrowsingControllerApi();
let codes = null; // Object | 

apiInstance.getKnowledgeBaseRoots(codes).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **codes** | [**Object**](.md)|  | 

### Return type

[**OperationStatusListGVirtualFilesystemRoot**](OperationStatusListGVirtualFilesystemRoot.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getVisibleKnowledgeBaseByCodes"></a>
# **getVisibleKnowledgeBaseByCodes**
> Object getVisibleKnowledgeBaseByCodes(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.UserKnowledgeBaseBrowsingControllerApi();
let body = null; // Object | 

apiInstance.getVisibleKnowledgeBaseByCodes(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  | 

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

