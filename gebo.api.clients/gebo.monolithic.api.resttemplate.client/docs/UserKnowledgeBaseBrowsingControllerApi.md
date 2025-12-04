# UserKnowledgeBaseBrowsingControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**browseKnowledgeBasePath**](UserKnowledgeBaseBrowsingControllerApi.md#browseKnowledgeBasePath) | **POST** /api/user/UserKnowledgeBaseBrowsingController/browseKnowledgeBasePath | 
[**getAccessibleRootKnowledgeBases**](UserKnowledgeBaseBrowsingControllerApi.md#getAccessibleRootKnowledgeBases) | **GET** /api/user/UserKnowledgeBaseBrowsingController/getAccessibleRootKnowledgeBases | 
[**getKnowledgeBaseNavigationStatus**](UserKnowledgeBaseBrowsingControllerApi.md#getKnowledgeBaseNavigationStatus) | **POST** /api/user/UserKnowledgeBaseBrowsingController/getKnowledgeBaseNavigationStatus | 
[**getKnowledgeBaseRoots**](UserKnowledgeBaseBrowsingControllerApi.md#getKnowledgeBaseRoots) | **GET** /api/user/UserKnowledgeBaseBrowsingController/getKnowledgeBaseRoots | 

<a name="browseKnowledgeBasePath"></a>
# **browseKnowledgeBasePath**
> OperationStatusListPathInfo browseKnowledgeBasePath(body, codes)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UserKnowledgeBaseBrowsingControllerApi;


UserKnowledgeBaseBrowsingControllerApi apiInstance = new UserKnowledgeBaseBrowsingControllerApi();
BrowseParam body = new BrowseParam(); // BrowseParam | 
List<String> codes = Arrays.asList("codes_example"); // List<String> | 
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
 **codes** | [**List&lt;String&gt;**](String.md)|  |

### Return type

[**OperationStatusListPathInfo**](OperationStatusListPathInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getAccessibleRootKnowledgeBases"></a>
# **getAccessibleRootKnowledgeBases**
> List&lt;GBaseObject&gt; getAccessibleRootKnowledgeBases()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UserKnowledgeBaseBrowsingControllerApi;


UserKnowledgeBaseBrowsingControllerApi apiInstance = new UserKnowledgeBaseBrowsingControllerApi();
try {
    List<GBaseObject> result = apiInstance.getAccessibleRootKnowledgeBases();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserKnowledgeBaseBrowsingControllerApi#getAccessibleRootKnowledgeBases");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GBaseObject&gt;**](GBaseObject.md)

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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UserKnowledgeBaseBrowsingControllerApi;


UserKnowledgeBaseBrowsingControllerApi apiInstance = new UserKnowledgeBaseBrowsingControllerApi();
List<VFilesystemReference> body = Arrays.asList(new VFilesystemReference()); // List<VFilesystemReference> | 
List<String> codes = Arrays.asList("codes_example"); // List<String> | 
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
 **body** | [**List&lt;VFilesystemReference&gt;**](VFilesystemReference.md)|  |
 **codes** | [**List&lt;String&gt;**](String.md)|  |

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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UserKnowledgeBaseBrowsingControllerApi;


UserKnowledgeBaseBrowsingControllerApi apiInstance = new UserKnowledgeBaseBrowsingControllerApi();
List<String> codes = Arrays.asList("codes_example"); // List<String> | 
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
 **codes** | [**List&lt;String&gt;**](String.md)|  |

### Return type

[**OperationStatusListGVirtualFilesystemRoot**](OperationStatusListGVirtualFilesystemRoot.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

