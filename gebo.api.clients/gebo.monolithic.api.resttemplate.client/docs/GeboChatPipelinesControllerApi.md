# GeboChatPipelinesControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**executeChatPipeline**](GeboChatPipelinesControllerApi.md#executeChatPipeline) | **POST** /api/users/GeboChatPipelinesController/executeChatPipeline | 
[**executeDefaultChatPipeline**](GeboChatPipelinesControllerApi.md#executeDefaultChatPipeline) | **POST** /api/users/GeboChatPipelinesController/executeDefaultChatPipeline | 
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
GeboChatRequest body = new GeboChatRequest(); // GeboChatRequest | 
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
 **body** | [**GeboChatRequest**](GeboChatRequest.md)|  |
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
GeboChatRequest body = new GeboChatRequest(); // GeboChatRequest | 
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
 **body** | [**GeboChatRequest**](GeboChatRequest.md)|  |

### Return type

[**GeboChatResponse**](GeboChatResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="streamChatPipeline"></a>
# **streamChatPipeline**
> List&lt;GeboChatMessageEnvelope&gt; streamChatPipeline(body, pipelineCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboChatPipelinesControllerApi;


GeboChatPipelinesControllerApi apiInstance = new GeboChatPipelinesControllerApi();
GeboChatRequest body = new GeboChatRequest(); // GeboChatRequest | 
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
 **body** | [**GeboChatRequest**](GeboChatRequest.md)|  |
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
GeboChatRequest body = new GeboChatRequest(); // GeboChatRequest | 
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
 **body** | [**GeboChatRequest**](GeboChatRequest.md)|  |

### Return type

[**List&lt;GeboChatMessageEnvelope&gt;**](GeboChatMessageEnvelope.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: text/event-stream

