# GeboChatPipelinesControllerApi

All URIs are relative to *http://localhost:13001*

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
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboChatPipelinesControllerApi;


GeboChatPipelinesControllerApi apiInstance = new GeboChatPipelinesControllerApi();
PipelineRequestBody body = new PipelineRequestBody(); // PipelineRequestBody | 
Object pipelineCode = null; // Object | 
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
 **pipelineCode** | [**Object**](.md)|  | [optional]

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
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboChatPipelinesControllerApi;


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
> Object getDefaultPersonalPipelinesChatMenu(chatProfileCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboChatPipelinesControllerApi;


GeboChatPipelinesControllerApi apiInstance = new GeboChatPipelinesControllerApi();
Object chatProfileCode = null; // Object | 
try {
    Object result = apiInstance.getDefaultPersonalPipelinesChatMenu(chatProfileCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboChatPipelinesControllerApi#getDefaultPersonalPipelinesChatMenu");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **chatProfileCode** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPersonalPipelinesChatMenu"></a>
# **getPersonalPipelinesChatMenu**
> Object getPersonalPipelinesChatMenu(chatProfileCode, pipelineCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboChatPipelinesControllerApi;


GeboChatPipelinesControllerApi apiInstance = new GeboChatPipelinesControllerApi();
Object chatProfileCode = null; // Object | 
Object pipelineCode = null; // Object | 
try {
    Object result = apiInstance.getPersonalPipelinesChatMenu(chatProfileCode, pipelineCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboChatPipelinesControllerApi#getPersonalPipelinesChatMenu");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **chatProfileCode** | [**Object**](.md)|  |
 **pipelineCode** | [**Object**](.md)|  | [optional]

### Return type

**Object**

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
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboChatPipelinesControllerApi;


GeboChatPipelinesControllerApi apiInstance = new GeboChatPipelinesControllerApi();
Object userChatContextCode = null; // Object | 
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
 **userChatContextCode** | [**Object**](.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="streamChatPipeline"></a>
# **streamChatPipeline**
> Object streamChatPipeline(body, pipelineCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboChatPipelinesControllerApi;


GeboChatPipelinesControllerApi apiInstance = new GeboChatPipelinesControllerApi();
PipelineRequestBody body = new PipelineRequestBody(); // PipelineRequestBody | 
Object pipelineCode = null; // Object | 
try {
    Object result = apiInstance.streamChatPipeline(body, pipelineCode);
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
 **pipelineCode** | [**Object**](.md)|  | [optional]

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: text/event-stream

<a name="streamDefaultChatPipeline"></a>
# **streamDefaultChatPipeline**
> Object streamDefaultChatPipeline(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboChatPipelinesControllerApi;


GeboChatPipelinesControllerApi apiInstance = new GeboChatPipelinesControllerApi();
PipelineRequestBody body = new PipelineRequestBody(); // PipelineRequestBody | 
try {
    Object result = apiInstance.streamDefaultChatPipeline(body);
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

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: text/event-stream

