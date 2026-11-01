# SessionsAdminControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getActiveSessionsCount**](SessionsAdminControllerApi.md#getActiveSessionsCount) | **GET** /api/admin/SessionsAdminController/getActiveSessionsCount | 
[**getSessionsPagedList**](SessionsAdminControllerApi.md#getSessionsPagedList) | **POST** /api/admin/SessionsAdminController/getSessionsPagedList | 
[**revokeSession**](SessionsAdminControllerApi.md#revokeSession) | **POST** /api/admin/SessionsAdminController/revokeSession | 
[**revokeUserSessions**](SessionsAdminControllerApi.md#revokeUserSessions) | **POST** /api/admin/SessionsAdminController/revokeUserSessions | 

<a name="getActiveSessionsCount"></a>
# **getActiveSessionsCount**
> Long getActiveSessionsCount(withinMinutes)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SessionsAdminControllerApi;


SessionsAdminControllerApi apiInstance = new SessionsAdminControllerApi();
Integer withinMinutes = 15; // Integer | 
try {
    Long result = apiInstance.getActiveSessionsCount(withinMinutes);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SessionsAdminControllerApi#getActiveSessionsCount");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **withinMinutes** | **Integer**|  | [optional] [default to 15]

### Return type

**Long**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSessionsPagedList"></a>
# **getSessionsPagedList**
> PagedModelGUserSession getSessionsPagedList(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SessionsAdminControllerApi;


SessionsAdminControllerApi apiInstance = new SessionsAdminControllerApi();
SessionsPageRequest body = new SessionsPageRequest(); // SessionsPageRequest | 
try {
    PagedModelGUserSession result = apiInstance.getSessionsPagedList(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SessionsAdminControllerApi#getSessionsPagedList");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SessionsPageRequest**](SessionsPageRequest.md)|  |

### Return type

[**PagedModelGUserSession**](PagedModelGUserSession.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="revokeSession"></a>
# **revokeSession**
> GUserSession revokeSession(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SessionsAdminControllerApi;


SessionsAdminControllerApi apiInstance = new SessionsAdminControllerApi();
String code = "code_example"; // String | 
try {
    GUserSession result = apiInstance.revokeSession(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SessionsAdminControllerApi#revokeSession");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GUserSession**](GUserSession.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="revokeUserSessions"></a>
# **revokeUserSessions**
> Integer revokeUserSessions(username)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SessionsAdminControllerApi;


SessionsAdminControllerApi apiInstance = new SessionsAdminControllerApi();
String username = "username_example"; // String | 
try {
    Integer result = apiInstance.revokeUserSessions(username);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SessionsAdminControllerApi#revokeUserSessions");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **username** | **String**|  |

### Return type

**Integer**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

