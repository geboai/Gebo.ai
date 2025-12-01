# GeboUserChatsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**changeChatDescription**](GeboUserChatsControllerApi.md#changeChatDescription) | **POST** /api/users/GeboUserChatsController/changeChatDescription | 
[**deleteUserChats**](GeboUserChatsControllerApi.md#deleteUserChats) | **POST** /api/users/GeboUserChatsController/deleteUserChats | 
[**getChatHistory**](GeboUserChatsControllerApi.md#getChatHistory) | **GET** /api/users/GeboUserChatsController/getChatHistory | 
[**getChatInfosByCode**](GeboUserChatsControllerApi.md#getChatInfosByCode) | **GET** /api/users/GeboUserChatsController/getChatInfosByCode | 
[**getChatInfosByQbe**](GeboUserChatsControllerApi.md#getChatInfosByQbe) | **POST** /api/users/GeboUserChatsController/getChatInfosByQbe | 
[**getMyChats**](GeboUserChatsControllerApi.md#getMyChats) | **GET** /api/users/GeboUserChatsController/getMyChats | 
[**getMyChatsPaged**](GeboUserChatsControllerApi.md#getMyChatsPaged) | **GET** /api/users/GeboUserChatsController/getMyChatsPaged | 

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

<a name="deleteUserChats"></a>
# **deleteUserChats**
> deleteUserChats(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboUserChatsControllerApi;


GeboUserChatsControllerApi apiInstance = new GeboUserChatsControllerApi();
List<String> body = Arrays.asList("body_example"); // List<String> | 
try {
    apiInstance.deleteUserChats(body);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboUserChatsControllerApi#deleteUserChats");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**List&lt;String&gt;**](String.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
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
> PageGUserChatInfo getChatInfosByQbe(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboUserChatsControllerApi;


GeboUserChatsControllerApi apiInstance = new GeboUserChatsControllerApi();
ChatInfosByQbeParam body = new ChatInfosByQbeParam(); // ChatInfosByQbeParam | 
try {
    PageGUserChatInfo result = apiInstance.getChatInfosByQbe(body);
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

[**PageGUserChatInfo**](PageGUserChatInfo.md)

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
> PageGUserChatInfo getMyChatsPaged(page, pageSize)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboUserChatsControllerApi;


GeboUserChatsControllerApi apiInstance = new GeboUserChatsControllerApi();
Integer page = 56; // Integer | 
Integer pageSize = 56; // Integer | 
try {
    PageGUserChatInfo result = apiInstance.getMyChatsPaged(page, pageSize);
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

[**PageGUserChatInfo**](PageGUserChatInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

