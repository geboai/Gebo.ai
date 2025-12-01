# GeboChatControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**chat**](GeboChatControllerApi.md#chat) | **POST** /api/users/GeboDirectModelChatController/chat | 
[**createCleanChatByModelCode**](GeboChatControllerApi.md#createCleanChatByModelCode) | **GET** /api/users/GeboDirectModelChatController/createCleanChatByModelCode | 
[**getChatModelMetaInfos**](GeboChatControllerApi.md#getChatModelMetaInfos) | **GET** /api/users/GeboDirectModelChatController/getChatModelMetaInfos | 
[**getChatModelUserInfo**](GeboChatControllerApi.md#getChatModelUserInfo) | **GET** /api/users/GeboDirectModelChatController/getChatModelUserInfo | 
[**getProviderCapabilities**](GeboChatControllerApi.md#getProviderCapabilities) | **GET** /api/users/GeboDirectModelChatController/getProviderCapabilities | 
[**richChat**](GeboChatControllerApi.md#richChat) | **POST** /api/users/GeboDirectModelChatController/richChat | 
[**speechText**](GeboChatControllerApi.md#speechText) | **POST** /api/users/GeboDirectModelChatController/speechText | 
[**streamResponse**](GeboChatControllerApi.md#streamResponse) | **POST** /api/users/GeboDirectModelChatController/streamResponse | 
[**suggestChatDescription**](GeboChatControllerApi.md#suggestChatDescription) | **GET** /api/users/GeboDirectModelChatController/suggestChatDescription | 
[**transcriptText**](GeboChatControllerApi.md#transcriptText) | **POST** /api/users/GeboDirectModelChatController/transcriptText | 

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

<a name="createCleanChatByModelCode"></a>
# **createCleanChatByModelCode**
> GUserChatInfo createCleanChatByModelCode(modelCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboChatControllerApi;


GeboChatControllerApi apiInstance = new GeboChatControllerApi();
String modelCode = "modelCode_example"; // String | 
try {
    GUserChatInfo result = apiInstance.createCleanChatByModelCode(modelCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboChatControllerApi#createCleanChatByModelCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **modelCode** | **String**|  |

### Return type

[**GUserChatInfo**](GUserChatInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
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

<a name="richChat"></a>
# **richChat**
> GeboTemplatedChatResponseRichResponse richChat(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboChatControllerApi;


GeboChatControllerApi apiInstance = new GeboChatControllerApi();
GeboChatRequest body = new GeboChatRequest(); // GeboChatRequest | 
try {
    GeboTemplatedChatResponseRichResponse result = apiInstance.richChat(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboChatControllerApi#richChat");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GeboChatRequest**](GeboChatRequest.md)|  |

### Return type

[**GeboTemplatedChatResponseRichResponse**](GeboTemplatedChatResponseRichResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="speechText"></a>
# **speechText**
> File speechText(body, modelCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboChatControllerApi;


GeboChatControllerApi apiInstance = new GeboChatControllerApi();
SpeechRequest body = new SpeechRequest(); // SpeechRequest | 
String modelCode = "modelCode_example"; // String | 
try {
    File result = apiInstance.speechText(body, modelCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboChatControllerApi#speechText");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SpeechRequest**](SpeechRequest.md)|  |
 **modelCode** | **String**|  |

### Return type

[**File**](File.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/octet-stream

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

<a name="suggestChatDescription"></a>
# **suggestChatDescription**
> GUserChatInfo suggestChatDescription(id)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboChatControllerApi;


GeboChatControllerApi apiInstance = new GeboChatControllerApi();
String id = "id_example"; // String | 
try {
    GUserChatInfo result = apiInstance.suggestChatDescription(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboChatControllerApi#suggestChatDescription");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |

### Return type

[**GUserChatInfo**](GUserChatInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="transcriptText"></a>
# **transcriptText**
> TranscriptResponse transcriptText(modelCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboChatControllerApi;


GeboChatControllerApi apiInstance = new GeboChatControllerApi();
String modelCode = "modelCode_example"; // String | 
try {
    TranscriptResponse result = apiInstance.transcriptText(modelCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboChatControllerApi#transcriptText");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **modelCode** | **String**|  |

### Return type

[**TranscriptResponse**](TranscriptResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

