# UserKnowledgeBaseBrowsingControllerApi

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
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.UserKnowledgeBaseBrowsingControllerApi;


UserKnowledgeBaseBrowsingControllerApi apiInstance = new UserKnowledgeBaseBrowsingControllerApi();
BrowseParam body = new BrowseParam(); // BrowseParam | 
Object codes = null; // Object | 
try {
    OperationStatusListPathInfo result = apiInstance.browseKnowledgeBasePath(body, codes);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserKnowledgeBaseBrowsingControllerApi#browseKnowledgeBasePath");
    e.printStackTrace();
}
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
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.UserKnowledgeBaseBrowsingControllerApi;


UserKnowledgeBaseBrowsingControllerApi apiInstance = new UserKnowledgeBaseBrowsingControllerApi();
try {
    Object result = apiInstance.getAccessibleRootKnowledgeBases();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserKnowledgeBaseBrowsingControllerApi#getAccessibleRootKnowledgeBases");
    e.printStackTrace();
}
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
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.UserKnowledgeBaseBrowsingControllerApi;


UserKnowledgeBaseBrowsingControllerApi apiInstance = new UserKnowledgeBaseBrowsingControllerApi();
Object body = null; // Object | 
Object codes = null; // Object | 
try {
    OperationStatusListVirtualFilesystemNavigationTreeStatus result = apiInstance.getKnowledgeBaseNavigationStatus(body, codes);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserKnowledgeBaseBrowsingControllerApi#getKnowledgeBaseNavigationStatus");
    e.printStackTrace();
}
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
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.UserKnowledgeBaseBrowsingControllerApi;


UserKnowledgeBaseBrowsingControllerApi apiInstance = new UserKnowledgeBaseBrowsingControllerApi();
Object codes = null; // Object | 
try {
    OperationStatusListGVirtualFilesystemRoot result = apiInstance.getKnowledgeBaseRoots(codes);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserKnowledgeBaseBrowsingControllerApi#getKnowledgeBaseRoots");
    e.printStackTrace();
}
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
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.UserKnowledgeBaseBrowsingControllerApi;


UserKnowledgeBaseBrowsingControllerApi apiInstance = new UserKnowledgeBaseBrowsingControllerApi();
Object body = null; // Object | 
try {
    Object result = apiInstance.getVisibleKnowledgeBaseByCodes(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserKnowledgeBaseBrowsingControllerApi#getVisibleKnowledgeBaseByCodes");
    e.printStackTrace();
}
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

