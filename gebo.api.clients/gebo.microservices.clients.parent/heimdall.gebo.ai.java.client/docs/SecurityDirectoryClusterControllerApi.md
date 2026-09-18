# SecurityDirectoryClusterControllerApi

All URIs are relative to *http://localhost:13018/heimdall*

Method | HTTP request | Description
------------- | ------------- | -------------
[**checkPassword**](SecurityDirectoryClusterControllerApi.md#checkPassword) | **POST** /api/cluster/SecurityController/checkPassword | 
[**createUserIfNotExists**](SecurityDirectoryClusterControllerApi.md#createUserIfNotExists) | **POST** /api/cluster/SecurityController/createUserIfNotExists | 
[**findAllGroups**](SecurityDirectoryClusterControllerApi.md#findAllGroups) | **GET** /api/cluster/SecurityController/findAllGroups | 
[**findGroupsOfUser**](SecurityDirectoryClusterControllerApi.md#findGroupsOfUser) | **GET** /api/cluster/SecurityController/findGroupsOfUser | 
[**findUserByUsername**](SecurityDirectoryClusterControllerApi.md#findUserByUsername) | **GET** /api/cluster/SecurityController/findUserByUsername | 

<a name="checkPassword"></a>
# **checkPassword**
> Boolean checkPassword(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.SecurityDirectoryClusterControllerApi;


SecurityDirectoryClusterControllerApi apiInstance = new SecurityDirectoryClusterControllerApi();
CheckPasswordRequest body = new CheckPasswordRequest(); // CheckPasswordRequest | 
try {
    Boolean result = apiInstance.checkPassword(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SecurityDirectoryClusterControllerApi#checkPassword");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**CheckPasswordRequest**](CheckPasswordRequest.md)|  |

### Return type

**Boolean**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createUserIfNotExists"></a>
# **createUserIfNotExists**
> UserInfosImpl createUserIfNotExists(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.SecurityDirectoryClusterControllerApi;


SecurityDirectoryClusterControllerApi apiInstance = new SecurityDirectoryClusterControllerApi();
CreateUserIfNotExistsRequest body = new CreateUserIfNotExistsRequest(); // CreateUserIfNotExistsRequest | 
try {
    UserInfosImpl result = apiInstance.createUserIfNotExists(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SecurityDirectoryClusterControllerApi#createUserIfNotExists");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**CreateUserIfNotExistsRequest**](CreateUserIfNotExistsRequest.md)|  |

### Return type

[**UserInfosImpl**](UserInfosImpl.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findAllGroups"></a>
# **findAllGroups**
> List&lt;UsersGroup&gt; findAllGroups()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.SecurityDirectoryClusterControllerApi;


SecurityDirectoryClusterControllerApi apiInstance = new SecurityDirectoryClusterControllerApi();
try {
    List<UsersGroup> result = apiInstance.findAllGroups();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SecurityDirectoryClusterControllerApi#findAllGroups");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;UsersGroup&gt;**](UsersGroup.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findGroupsOfUser"></a>
# **findGroupsOfUser**
> List&lt;UsersGroup&gt; findGroupsOfUser(username)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.SecurityDirectoryClusterControllerApi;


SecurityDirectoryClusterControllerApi apiInstance = new SecurityDirectoryClusterControllerApi();
String username = "username_example"; // String | 
try {
    List<UsersGroup> result = apiInstance.findGroupsOfUser(username);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SecurityDirectoryClusterControllerApi#findGroupsOfUser");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **username** | **String**|  |

### Return type

[**List&lt;UsersGroup&gt;**](UsersGroup.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findUserByUsername"></a>
# **findUserByUsername**
> UserInfosImpl findUserByUsername(username)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.SecurityDirectoryClusterControllerApi;


SecurityDirectoryClusterControllerApi apiInstance = new SecurityDirectoryClusterControllerApi();
String username = "username_example"; // String | 
try {
    UserInfosImpl result = apiInstance.findUserByUsername(username);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SecurityDirectoryClusterControllerApi#findUserByUsername");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **username** | **String**|  |

### Return type

[**UserInfosImpl**](UserInfosImpl.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

