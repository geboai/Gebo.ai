# GeboChatControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**chat**](GeboChatControllerApi.md#chat) | **POST** /api/users/GeboDirectModelChatController/chat | 
[**getChatModelMetaInfos**](GeboChatControllerApi.md#getChatModelMetaInfos) | **GET** /api/users/GeboDirectModelChatController/getChatModelMetaInfos | 
[**getChatModelUserInfo**](GeboChatControllerApi.md#getChatModelUserInfo) | **GET** /api/users/GeboDirectModelChatController/getChatModelUserInfo | 
[**getProviderCapabilities**](GeboChatControllerApi.md#getProviderCapabilities) | **GET** /api/users/GeboDirectModelChatController/getProviderCapabilities | 
[**getVisibleKnowledgeBases**](GeboChatControllerApi.md#getVisibleKnowledgeBases) | **GET** /api/users/GeboDirectModelChatController/getVisibleKnowledgeBases | 
[**streamResponse**](GeboChatControllerApi.md#streamResponse) | **POST** /api/users/GeboDirectModelChatController/streamResponse | 

<a name="chat"></a>
# **chat**
> GeboChatResponse chat(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboChatControllerApi;


GeboChatControllerApi apiInstance = new GeboChatControllerApi();
GeboChatRequest body = new GeboChatRequest(); // GeboChatRequest | 
try {
    GeboChatResponse result = apiInstance.chat(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboChatControllerApi#chat");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GeboChatRequest**](GeboChatRequest.md)|  |

### Return type

[**GeboChatResponse**](GeboChatResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getChatModelMetaInfos"></a>
# **getChatModelMetaInfos**
> GBaseChatModelChoice getChatModelMetaInfos(modelCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboChatControllerApi;


GeboChatControllerApi apiInstance = new GeboChatControllerApi();
String modelCode = "modelCode_example"; // String | 
try {
    GBaseChatModelChoice result = apiInstance.getChatModelMetaInfos(modelCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboChatControllerApi#getChatModelMetaInfos");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **modelCode** | **String**|  |

### Return type

[**GBaseChatModelChoice**](GBaseChatModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getChatModelUserInfo"></a>
# **getChatModelUserInfo**
> GeboChatUserInfo getChatModelUserInfo(modelCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboChatControllerApi;


GeboChatControllerApi apiInstance = new GeboChatControllerApi();
String modelCode = "modelCode_example"; // String | 
try {
    GeboChatUserInfo result = apiInstance.getChatModelUserInfo(modelCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboChatControllerApi#getChatModelUserInfo");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **modelCode** | **String**|  |

### Return type

[**GeboChatUserInfo**](GeboChatUserInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getProviderCapabilities"></a>
# **getProviderCapabilities**
> ModelProviderCapabilities getProviderCapabilities(modelCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboChatControllerApi;


GeboChatControllerApi apiInstance = new GeboChatControllerApi();
String modelCode = "modelCode_example"; // String | 
try {
    ModelProviderCapabilities result = apiInstance.getProviderCapabilities(modelCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboChatControllerApi#getProviderCapabilities");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **modelCode** | **String**|  |

### Return type

[**ModelProviderCapabilities**](ModelProviderCapabilities.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getVisibleKnowledgeBases"></a>
# **getVisibleKnowledgeBases**
> List&lt;GBaseObject&gt; getVisibleKnowledgeBases()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboChatControllerApi;


GeboChatControllerApi apiInstance = new GeboChatControllerApi();
try {
    List<GBaseObject> result = apiInstance.getVisibleKnowledgeBases();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboChatControllerApi#getVisibleKnowledgeBases");
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

<a name="streamResponse"></a>
# **streamResponse**
> List&lt;ServerSentEventString&gt; streamResponse(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboChatControllerApi;


GeboChatControllerApi apiInstance = new GeboChatControllerApi();
GeboChatRequest body = new GeboChatRequest(); // GeboChatRequest | 
try {
    List<ServerSentEventString> result = apiInstance.streamResponse(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboChatControllerApi#streamResponse");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GeboChatRequest**](GeboChatRequest.md)|  |

### Return type

[**List&lt;ServerSentEventString&gt;**](ServerSentEventString.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: text/event-stream

