# GeboRagChatControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getChatModelUserInfoByChatProfileCode**](GeboRagChatControllerApi.md#getChatModelUserInfoByChatProfileCode) | **GET** /api/users/GeboChatController/getChatModelUserInfoByChatProfileCode | 
[**getChatProfileModelMetaInfos**](GeboRagChatControllerApi.md#getChatProfileModelMetaInfos) | **GET** /api/users/GeboChatController/getChatProfileModelMetaInfos | 
[**getChatProfiles**](GeboRagChatControllerApi.md#getChatProfiles) | **GET** /api/users/GeboChatController/profiles | 
[**getProfileProviderModelCapabilities**](GeboRagChatControllerApi.md#getProfileProviderModelCapabilities) | **GET** /api/users/GeboChatController/getProfileProviderModelCapabilities | 
[**getVisibleKnowledgeBasesByProfileCode**](GeboRagChatControllerApi.md#getVisibleKnowledgeBasesByProfileCode) | **GET** /api/users/GeboChatController/getVisibleKnowledgeBasesByProfileCode | 
[**ragChat**](GeboRagChatControllerApi.md#ragChat) | **POST** /api/users/GeboChatController/ragChat | 
[**streamRagResponse**](GeboRagChatControllerApi.md#streamRagResponse) | **POST** /api/users/GeboChatController/streamRagResponse | 

<a name="getChatModelUserInfoByChatProfileCode"></a>
# **getChatModelUserInfoByChatProfileCode**
> GeboChatUserInfo getChatModelUserInfoByChatProfileCode(chatProfileCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboRagChatControllerApi;


GeboRagChatControllerApi apiInstance = new GeboRagChatControllerApi();
Object chatProfileCode = null; // Object | 
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
 **chatProfileCode** | [**Object**](.md)|  |

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
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboRagChatControllerApi;


GeboRagChatControllerApi apiInstance = new GeboRagChatControllerApi();
Object chatProfileCode = null; // Object | 
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
 **chatProfileCode** | [**Object**](.md)|  |

### Return type

[**GBaseChatModelChoice**](GBaseChatModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getChatProfiles"></a>
# **getChatProfiles**
> Object getChatProfiles()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboRagChatControllerApi;


GeboRagChatControllerApi apiInstance = new GeboRagChatControllerApi();
try {
    Object result = apiInstance.getChatProfiles();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboRagChatControllerApi#getChatProfiles");
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
 - **Accept**: */*

<a name="getProfileProviderModelCapabilities"></a>
# **getProfileProviderModelCapabilities**
> ModelProviderCapabilities getProfileProviderModelCapabilities(chatProfileCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboRagChatControllerApi;


GeboRagChatControllerApi apiInstance = new GeboRagChatControllerApi();
Object chatProfileCode = null; // Object | 
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
 **chatProfileCode** | [**Object**](.md)|  |

### Return type

[**ModelProviderCapabilities**](ModelProviderCapabilities.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getVisibleKnowledgeBasesByProfileCode"></a>
# **getVisibleKnowledgeBasesByProfileCode**
> Object getVisibleKnowledgeBasesByProfileCode(profileCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboRagChatControllerApi;


GeboRagChatControllerApi apiInstance = new GeboRagChatControllerApi();
Object profileCode = null; // Object | 
try {
    Object result = apiInstance.getVisibleKnowledgeBasesByProfileCode(profileCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboRagChatControllerApi#getVisibleKnowledgeBasesByProfileCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **profileCode** | [**Object**](.md)|  |

### Return type

**Object**

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
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboRagChatControllerApi;


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

<a name="streamRagResponse"></a>
# **streamRagResponse**
> Object streamRagResponse(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboRagChatControllerApi;


GeboRagChatControllerApi apiInstance = new GeboRagChatControllerApi();
GeboChatRequest body = new GeboChatRequest(); // GeboChatRequest | 
try {
    Object result = apiInstance.streamRagResponse(body);
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

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: text/event-stream

