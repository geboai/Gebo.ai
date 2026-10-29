# GeboChatLibrariesControllerApi

All URIs are relative to *http://localhost:13999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**downloadChatLibraryResource**](GeboChatLibrariesControllerApi.md#downloadChatLibraryResource) | **GET** /api/users/GeboChatLibrariesController/downloadChatLibraryResource | 
[**getChatLibraryInstance**](GeboChatLibrariesControllerApi.md#getChatLibraryInstance) | **GET** /api/users/GeboChatLibrariesController/getChatLibraryInstance | 

<a name="downloadChatLibraryResource"></a>
# **downloadChatLibraryResource**
> downloadChatLibraryResource(chatCode, libraryTypeId, resourceId)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboChatLibrariesControllerApi;


GeboChatLibrariesControllerApi apiInstance = new GeboChatLibrariesControllerApi();
String chatCode = "chatCode_example"; // String | 
String libraryTypeId = "libraryTypeId_example"; // String | 
String resourceId = "resourceId_example"; // String | 
try {
    apiInstance.downloadChatLibraryResource(chatCode, libraryTypeId, resourceId);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboChatLibrariesControllerApi#downloadChatLibraryResource");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **chatCode** | **String**|  |
 **libraryTypeId** | **String**|  |
 **resourceId** | **String**|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="getChatLibraryInstance"></a>
# **getChatLibraryInstance**
> List&lt;AIToolsAndResourcesLibraryInstance&gt; getChatLibraryInstance(chatCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboChatLibrariesControllerApi;


GeboChatLibrariesControllerApi apiInstance = new GeboChatLibrariesControllerApi();
String chatCode = "chatCode_example"; // String | 
try {
    List<AIToolsAndResourcesLibraryInstance> result = apiInstance.getChatLibraryInstance(chatCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboChatLibrariesControllerApi#getChatLibraryInstance");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **chatCode** | **String**|  |

### Return type

[**List&lt;AIToolsAndResourcesLibraryInstance&gt;**](AIToolsAndResourcesLibraryInstance.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

