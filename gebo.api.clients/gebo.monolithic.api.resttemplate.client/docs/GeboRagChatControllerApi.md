# GeboRagChatControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createCleanRagChatByProfileCode**](GeboRagChatControllerApi.md#createCleanRagChatByProfileCode) | **GET** /api/users/GeboChatController/createCleanRagChatByProfileCode | 
[**getChatModelUserInfoByChatProfileCode**](GeboRagChatControllerApi.md#getChatModelUserInfoByChatProfileCode) | **GET** /api/users/GeboChatController/getChatModelUserInfoByChatProfileCode | 
[**getChatProfileModelMetaInfos**](GeboRagChatControllerApi.md#getChatProfileModelMetaInfos) | **GET** /api/users/GeboChatController/getChatProfileModelMetaInfos | 
[**getChatProfiles**](GeboRagChatControllerApi.md#getChatProfiles) | **GET** /api/users/GeboChatController/profiles | 
[**getProfileProviderModelCapabilities**](GeboRagChatControllerApi.md#getProfileProviderModelCapabilities) | **GET** /api/users/GeboChatController/getProfileProviderModelCapabilities | 
[**ragChat**](GeboRagChatControllerApi.md#ragChat) | **POST** /api/users/GeboChatController/ragChat | 
[**richRagChat**](GeboRagChatControllerApi.md#richRagChat) | **POST** /api/users/GeboChatController/richRagChat | 
[**streamRagResponse**](GeboRagChatControllerApi.md#streamRagResponse) | **POST** /api/users/GeboChatController/streamRagResponse | 
[**suggestRagChatDescription**](GeboRagChatControllerApi.md#suggestRagChatDescription) | **GET** /api/users/GeboChatController/suggestRagChatDescription | 

<a name="createCleanRagChatByProfileCode"></a>
# **createCleanRagChatByProfileCode**
> GUserChatInfo createCleanRagChatByProfileCode(profileCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboRagChatControllerApi;


GeboRagChatControllerApi apiInstance = new GeboRagChatControllerApi();
String profileCode = "profileCode_example"; // String | 
try {
    GUserChatInfo result = apiInstance.createCleanRagChatByProfileCode(profileCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboRagChatControllerApi#createCleanRagChatByProfileCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **profileCode** | **String**|  |

### Return type

[**GUserChatInfo**](GUserChatInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getChatModelUserInfoByChatProfileCode"></a>
# **getChatModelUserInfoByChatProfileCode**
> GeboChatUserInfo getChatModelUserInfoByChatProfileCode(chatProfileCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboRagChatControllerApi;


GeboRagChatControllerApi apiInstance = new GeboRagChatControllerApi();
String chatProfileCode = "chatProfileCode_example"; // String | 
try {
    GeboChatUserInfo result = apiInstance.getChatModelUserInfoByChatProfileCode(chatProfileCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboRagChatControllerApi#getChatModelUserInfoByChatProfileCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **chatProfileCode** | **String**|  |

### Return type

[**GeboChatUserInfo**](GeboChatUserInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getChatProfileModelMetaInfos"></a>
# **getChatProfileModelMetaInfos**
> GBaseChatModelChoice getChatProfileModelMetaInfos(chatProfileCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboRagChatControllerApi;


GeboRagChatControllerApi apiInstance = new GeboRagChatControllerApi();
String chatProfileCode = "chatProfileCode_example"; // String | 
try {
    GBaseChatModelChoice result = apiInstance.getChatProfileModelMetaInfos(chatProfileCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboRagChatControllerApi#getChatProfileModelMetaInfos");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **chatProfileCode** | **String**|  |

### Return type

[**GBaseChatModelChoice**](GBaseChatModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getChatProfiles"></a>
# **getChatProfiles**
> List&lt;GChatProfileConfiguration&gt; getChatProfiles()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboRagChatControllerApi;


GeboRagChatControllerApi apiInstance = new GeboRagChatControllerApi();
try {
    List<GChatProfileConfiguration> result = apiInstance.getChatProfiles();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboRagChatControllerApi#getChatProfiles");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GChatProfileConfiguration&gt;**](GChatProfileConfiguration.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="getProfileProviderModelCapabilities"></a>
# **getProfileProviderModelCapabilities**
> ModelProviderCapabilities getProfileProviderModelCapabilities(chatProfileCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboRagChatControllerApi;


GeboRagChatControllerApi apiInstance = new GeboRagChatControllerApi();
String chatProfileCode = "chatProfileCode_example"; // String | 
try {
    ModelProviderCapabilities result = apiInstance.getProfileProviderModelCapabilities(chatProfileCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboRagChatControllerApi#getProfileProviderModelCapabilities");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **chatProfileCode** | **String**|  |

### Return type

[**ModelProviderCapabilities**](ModelProviderCapabilities.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="ragChat"></a>
# **ragChat**
> GeboChatResponse ragChat(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboRagChatControllerApi;


GeboRagChatControllerApi apiInstance = new GeboRagChatControllerApi();
GeboChatRequest body = new GeboChatRequest(); // GeboChatRequest | 
try {
    GeboChatResponse result = apiInstance.ragChat(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboRagChatControllerApi#ragChat");
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

<a name="richRagChat"></a>
# **richRagChat**
> GeboTemplatedChatResponseRichResponse richRagChat(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboRagChatControllerApi;


GeboRagChatControllerApi apiInstance = new GeboRagChatControllerApi();
GeboChatRequest body = new GeboChatRequest(); // GeboChatRequest | 
try {
    GeboTemplatedChatResponseRichResponse result = apiInstance.richRagChat(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboRagChatControllerApi#richRagChat");
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

<a name="streamRagResponse"></a>
# **streamRagResponse**
> List&lt;ServerSentEventString&gt; streamRagResponse(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboRagChatControllerApi;


GeboRagChatControllerApi apiInstance = new GeboRagChatControllerApi();
GeboChatRequest body = new GeboChatRequest(); // GeboChatRequest | 
try {
    List<ServerSentEventString> result = apiInstance.streamRagResponse(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboRagChatControllerApi#streamRagResponse");
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

<a name="suggestRagChatDescription"></a>
# **suggestRagChatDescription**
> GUserChatInfo suggestRagChatDescription(id)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboRagChatControllerApi;


GeboRagChatControllerApi apiInstance = new GeboRagChatControllerApi();
String id = "id_example"; // String | 
try {
    GUserChatInfo result = apiInstance.suggestRagChatDescription(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboRagChatControllerApi#suggestRagChatDescription");
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

