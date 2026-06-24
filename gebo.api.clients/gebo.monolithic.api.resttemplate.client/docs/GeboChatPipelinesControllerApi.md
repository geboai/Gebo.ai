# GeboChatPipelinesControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**executeChatPipeline**](GeboChatPipelinesControllerApi.md#executeChatPipeline) | **POST** /api/users/GeboChatPipelinesController/executeChatPipeline | 
[**executeDefaultChatPipeline**](GeboChatPipelinesControllerApi.md#executeDefaultChatPipeline) | **POST** /api/users/GeboChatPipelinesController/executeDefaultChatPipeline | 
[**getDefaultPersonalPipelinesChatMenu**](GeboChatPipelinesControllerApi.md#getDefaultPersonalPipelinesChatMenu) | **GET** /api/users/GeboChatPipelinesController/defaultPersonalPipelinesChatMenu | 
[**getPersonalPipelinesChatMenu**](GeboChatPipelinesControllerApi.md#getPersonalPipelinesChatMenu) | **GET** /api/users/GeboChatPipelinesController/personalPipelinesChatMenu | 
[**stopChatPipeline**](GeboChatPipelinesControllerApi.md#stopChatPipeline) | **GET** /api/users/GeboChatPipelinesController/stopChatPipeline | 
[**streamChatPipeline**](GeboChatPipelinesControllerApi.md#streamChatPipeline) | **POST** /api/users/GeboChatPipelinesController/streamChatPipeline | 
[**streamDefaultChatPipeline**](GeboChatPipelinesControllerApi.md#streamDefaultChatPipeline) | **POST** /api/users/GeboChatPipelinesController/streamDefaultChatPipeline | 

<a name="executeChatPipeline"></a>
# **executeChatPipeline**
> GeboChatResponse executeChatPipeline(body, pipelineCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboChatPipelinesControllerApi;


GeboChatPipelinesControllerApi apiInstance = new GeboChatPipelinesControllerApi();
PipelineRequestBody body = new PipelineRequestBody(); // PipelineRequestBody | 
String pipelineCode = "pipelineCode_example"; // String | 
try {
    GeboChatResponse result = apiInstance.executeChatPipeline(body, pipelineCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboChatPipelinesControllerApi#executeChatPipeline");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**PipelineRequestBody**](PipelineRequestBody.md)|  |
 **pipelineCode** | **String**|  | [optional]

### Return type

[**GeboChatResponse**](GeboChatResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="executeDefaultChatPipeline"></a>
# **executeDefaultChatPipeline**
> GeboChatResponse executeDefaultChatPipeline(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboChatPipelinesControllerApi;


GeboChatPipelinesControllerApi apiInstance = new GeboChatPipelinesControllerApi();
PipelineRequestBody body = new PipelineRequestBody(); // PipelineRequestBody | 
try {
    GeboChatResponse result = apiInstance.executeDefaultChatPipeline(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboChatPipelinesControllerApi#executeDefaultChatPipeline");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**PipelineRequestBody**](PipelineRequestBody.md)|  |

### Return type

[**GeboChatResponse**](GeboChatResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getDefaultPersonalPipelinesChatMenu"></a>
# **getDefaultPersonalPipelinesChatMenu**
> List&lt;PipelineChatMenu&gt; getDefaultPersonalPipelinesChatMenu(chatProfileCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboChatPipelinesControllerApi;


GeboChatPipelinesControllerApi apiInstance = new GeboChatPipelinesControllerApi();
String chatProfileCode = "chatProfileCode_example"; // String | 
try {
    List<PipelineChatMenu> result = apiInstance.getDefaultPersonalPipelinesChatMenu(chatProfileCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboChatPipelinesControllerApi#getDefaultPersonalPipelinesChatMenu");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **chatProfileCode** | **String**|  |

### Return type

[**List&lt;PipelineChatMenu&gt;**](PipelineChatMenu.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPersonalPipelinesChatMenu"></a>
# **getPersonalPipelinesChatMenu**
> List&lt;PipelineChatMenu&gt; getPersonalPipelinesChatMenu(chatProfileCode, pipelineCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboChatPipelinesControllerApi;


GeboChatPipelinesControllerApi apiInstance = new GeboChatPipelinesControllerApi();
String chatProfileCode = "chatProfileCode_example"; // String | 
String pipelineCode = "pipelineCode_example"; // String | 
try {
    List<PipelineChatMenu> result = apiInstance.getPersonalPipelinesChatMenu(chatProfileCode, pipelineCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboChatPipelinesControllerApi#getPersonalPipelinesChatMenu");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **chatProfileCode** | **String**|  |
 **pipelineCode** | **String**|  | [optional]

### Return type

[**List&lt;PipelineChatMenu&gt;**](PipelineChatMenu.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="stopChatPipeline"></a>
# **stopChatPipeline**
> stopChatPipeline(userChatContextCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboChatPipelinesControllerApi;


GeboChatPipelinesControllerApi apiInstance = new GeboChatPipelinesControllerApi();
String userChatContextCode = "userChatContextCode_example"; // String | 
try {
    apiInstance.stopChatPipeline(userChatContextCode);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboChatPipelinesControllerApi#stopChatPipeline");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userChatContextCode** | **String**|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="streamChatPipeline"></a>
# **streamChatPipeline**
> List&lt;GeboChatMessageEnvelope&gt; streamChatPipeline(body, pipelineCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboChatPipelinesControllerApi;


GeboChatPipelinesControllerApi apiInstance = new GeboChatPipelinesControllerApi();
PipelineRequestBody body = new PipelineRequestBody(); // PipelineRequestBody | 
String pipelineCode = "pipelineCode_example"; // String | 
try {
    List<GeboChatMessageEnvelope> result = apiInstance.streamChatPipeline(body, pipelineCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboChatPipelinesControllerApi#streamChatPipeline");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**PipelineRequestBody**](PipelineRequestBody.md)|  |
 **pipelineCode** | **String**|  | [optional]

### Return type

[**List&lt;GeboChatMessageEnvelope&gt;**](GeboChatMessageEnvelope.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: text/event-stream

<a name="streamDefaultChatPipeline"></a>
# **streamDefaultChatPipeline**
> List&lt;GeboChatMessageEnvelope&gt; streamDefaultChatPipeline(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboChatPipelinesControllerApi;


GeboChatPipelinesControllerApi apiInstance = new GeboChatPipelinesControllerApi();
PipelineRequestBody body = new PipelineRequestBody(); // PipelineRequestBody | 
try {
    List<GeboChatMessageEnvelope> result = apiInstance.streamDefaultChatPipeline(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboChatPipelinesControllerApi#streamDefaultChatPipeline");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**PipelineRequestBody**](PipelineRequestBody.md)|  |

### Return type

[**List&lt;GeboChatMessageEnvelope&gt;**](GeboChatMessageEnvelope.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: text/event-stream

