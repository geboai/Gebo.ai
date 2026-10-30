# UserControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**changePassword**](UserControllerApi.md#changePassword) | **POST** /api/users/ActualUserController/changePassword | 
[**getCurrentUser**](UserControllerApi.md#getCurrentUser) | **GET** /api/users/ActualUserController/me | 
[**getMyGroups**](UserControllerApi.md#getMyGroups) | **GET** /api/users/ActualUserController/getMyGroups | 

<a name="changePassword"></a>
# **changePassword**
> ChangePasswordResponse changePassword(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UserControllerApi;


UserControllerApi apiInstance = new UserControllerApi();
ChangePasswordParam body = new ChangePasswordParam(); // ChangePasswordParam | 
try {
    ChangePasswordResponse result = apiInstance.changePassword(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserControllerApi#changePassword");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ChangePasswordParam**](ChangePasswordParam.md)|  |

### Return type

[**ChangePasswordResponse**](ChangePasswordResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getCurrentUser"></a>
# **getCurrentUser**
> UserInfo getCurrentUser()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UserControllerApi;


UserControllerApi apiInstance = new UserControllerApi();
try {
    UserInfo result = apiInstance.getCurrentUser();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserControllerApi#getCurrentUser");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**UserInfo**](UserInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getMyGroups"></a>
# **getMyGroups**
> List&lt;GroupInfo&gt; getMyGroups()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UserControllerApi;


UserControllerApi apiInstance = new UserControllerApi();
try {
    List<GroupInfo> result = apiInstance.getMyGroups();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserControllerApi#getMyGroups");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GroupInfo&gt;**](GroupInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

