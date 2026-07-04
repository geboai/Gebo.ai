# GeboUserChatsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**changeChatDescription**](GeboUserChatsControllerApi.md#changeChatDescription) | **POST** /api/users/GeboUserChatsController/changeChatDescription | 
[**createCleanChatByChatProfileCode**](GeboUserChatsControllerApi.md#createCleanChatByChatProfileCode) | **GET** /api/users/GeboUserChatsController/createCleanChatByChatProfileCode | 
[**createCleanChatByModelCode**](GeboUserChatsControllerApi.md#createCleanChatByModelCode) | **GET** /api/users/GeboUserChatsController/createCleanChatByModelCode | 
[**deleteChat**](GeboUserChatsControllerApi.md#deleteChat) | **DELETE** /api/users/GeboUserChatsController/deleteChat | 
[**exportResponse2file**](GeboUserChatsControllerApi.md#exportResponse2file) | **GET** /api/users/GeboUserChatsController/exportResponse2file | 
[**getChatHistory**](GeboUserChatsControllerApi.md#getChatHistory) | **GET** /api/users/GeboUserChatsController/getChatHistory | 
[**getChatInfosByCode**](GeboUserChatsControllerApi.md#getChatInfosByCode) | **GET** /api/users/GeboUserChatsController/getChatInfosByCode | 
[**getChatInfosByQbe**](GeboUserChatsControllerApi.md#getChatInfosByQbe) | **POST** /api/users/GeboUserChatsController/getChatInfosByQbe | 
[**getMyChats**](GeboUserChatsControllerApi.md#getMyChats) | **GET** /api/users/GeboUserChatsController/getMyChats | 
[**getMyChatsPaged**](GeboUserChatsControllerApi.md#getMyChatsPaged) | **GET** /api/users/GeboUserChatsController/getMyChatsPaged | 
[**getUIConfig**](GeboUserChatsControllerApi.md#getUIConfig) | **GET** /api/users/GeboUserChatsController/getUIConfig | 
[**suggestChatDescription**](GeboUserChatsControllerApi.md#suggestChatDescription) | **GET** /api/users/GeboUserChatsController/suggestChatDescription | 

<a name="changeChatDescription"></a>
# **changeChatDescription**
> GLookupEntry changeChatDescription(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboUserChatsControllerApi;


GeboUserChatsControllerApi apiInstance = new GeboUserChatsControllerApi();
GLookupEntry body = new GLookupEntry(); // GLookupEntry | 
try {
    GLookupEntry result = apiInstance.changeChatDescription(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboUserChatsControllerApi#changeChatDescription");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GLookupEntry**](GLookupEntry.md)|  |

### Return type

[**GLookupEntry**](GLookupEntry.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createCleanChatByChatProfileCode"></a>
# **createCleanChatByChatProfileCode**
> GUserChatInfo createCleanChatByChatProfileCode(chatProfileCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboUserChatsControllerApi;


GeboUserChatsControllerApi apiInstance = new GeboUserChatsControllerApi();
String chatProfileCode = "chatProfileCode_example"; // String | 
try {
    GUserChatInfo result = apiInstance.createCleanChatByChatProfileCode(chatProfileCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboUserChatsControllerApi#createCleanChatByChatProfileCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **chatProfileCode** | **String**|  |

### Return type

[**GUserChatInfo**](GUserChatInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="createCleanChatByModelCode"></a>
# **createCleanChatByModelCode**
> GUserChatInfo createCleanChatByModelCode(modelCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboUserChatsControllerApi;


GeboUserChatsControllerApi apiInstance = new GeboUserChatsControllerApi();
String modelCode = "modelCode_example"; // String | 
try {
    GUserChatInfo result = apiInstance.createCleanChatByModelCode(modelCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboUserChatsControllerApi#createCleanChatByModelCode");
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

<a name="deleteChat"></a>
# **deleteChat**
> deleteChat(userChatContextCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboUserChatsControllerApi;


GeboUserChatsControllerApi apiInstance = new GeboUserChatsControllerApi();
String userChatContextCode = "userChatContextCode_example"; // String | 
try {
    apiInstance.deleteChat(userChatContextCode);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboUserChatsControllerApi#deleteChat");
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

<a name="exportResponse2file"></a>
# **exportResponse2file**
> exportResponse2file(userContextCode, responseId, format)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboUserChatsControllerApi;


GeboUserChatsControllerApi apiInstance = new GeboUserChatsControllerApi();
String userContextCode = "userContextCode_example"; // String | 
String responseId = "responseId_example"; // String | 
String format = "format_example"; // String | 
try {
    apiInstance.exportResponse2file(userContextCode, responseId, format);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboUserChatsControllerApi#exportResponse2file");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userContextCode** | **String**|  |
 **responseId** | **String**|  |
 **format** | **String**|  | [enum: EXCEL, WORD, PDF]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="getChatHistory"></a>
# **getChatHistory**
> UserChatHistory getChatHistory(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboUserChatsControllerApi;


GeboUserChatsControllerApi apiInstance = new GeboUserChatsControllerApi();
String code = "code_example"; // String | 
try {
    UserChatHistory result = apiInstance.getChatHistory(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboUserChatsControllerApi#getChatHistory");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**UserChatHistory**](UserChatHistory.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getChatInfosByCode"></a>
# **getChatInfosByCode**
> GUserChatInfo getChatInfosByCode(id)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboUserChatsControllerApi;


GeboUserChatsControllerApi apiInstance = new GeboUserChatsControllerApi();
String id = "id_example"; // String | 
try {
    GUserChatInfo result = apiInstance.getChatInfosByCode(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboUserChatsControllerApi#getChatInfosByCode");
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

<a name="getChatInfosByQbe"></a>
# **getChatInfosByQbe**
> PagedModelGUserChatInfo getChatInfosByQbe(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboUserChatsControllerApi;


GeboUserChatsControllerApi apiInstance = new GeboUserChatsControllerApi();
ChatInfosByQbeParam body = new ChatInfosByQbeParam(); // ChatInfosByQbeParam | 
try {
    PagedModelGUserChatInfo result = apiInstance.getChatInfosByQbe(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboUserChatsControllerApi#getChatInfosByQbe");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ChatInfosByQbeParam**](ChatInfosByQbeParam.md)|  |

### Return type

[**PagedModelGUserChatInfo**](PagedModelGUserChatInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMyChats"></a>
# **getMyChats**
> List&lt;GUserChatInfo&gt; getMyChats()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboUserChatsControllerApi;


GeboUserChatsControllerApi apiInstance = new GeboUserChatsControllerApi();
try {
    List<GUserChatInfo> result = apiInstance.getMyChats();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboUserChatsControllerApi#getMyChats");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GUserChatInfo&gt;**](GUserChatInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getMyChatsPaged"></a>
# **getMyChatsPaged**
> PagedModelGUserChatInfo getMyChatsPaged(page, pageSize)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboUserChatsControllerApi;


GeboUserChatsControllerApi apiInstance = new GeboUserChatsControllerApi();
Integer page = 56; // Integer | 
Integer pageSize = 56; // Integer | 
try {
    PagedModelGUserChatInfo result = apiInstance.getMyChatsPaged(page, pageSize);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboUserChatsControllerApi#getMyChatsPaged");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **page** | **Integer**|  |
 **pageSize** | **Integer**|  |

### Return type

[**PagedModelGUserChatInfo**](PagedModelGUserChatInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getUIConfig"></a>
# **getUIConfig**
> ChatUIOptions getUIConfig()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboUserChatsControllerApi;


GeboUserChatsControllerApi apiInstance = new GeboUserChatsControllerApi();
try {
    ChatUIOptions result = apiInstance.getUIConfig();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboUserChatsControllerApi#getUIConfig");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**ChatUIOptions**](ChatUIOptions.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="suggestChatDescription"></a>
# **suggestChatDescription**
> GUserChatInfo suggestChatDescription(userChatContextCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboUserChatsControllerApi;


GeboUserChatsControllerApi apiInstance = new GeboUserChatsControllerApi();
String userChatContextCode = "userChatContextCode_example"; // String | 
try {
    GUserChatInfo result = apiInstance.suggestChatDescription(userChatContextCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboUserChatsControllerApi#suggestChatDescription");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userChatContextCode** | **String**|  |

### Return type

[**GUserChatInfo**](GUserChatInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

