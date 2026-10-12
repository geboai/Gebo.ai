# SecurityDirectoryClusterControllerApi

All URIs are relative to *http://localhost:13018/heimdall*

Method | HTTP request | Description
------------- | ------------- | -------------
[**infrastructureCheckPassword**](SecurityDirectoryClusterControllerApi.md#infrastructureCheckPassword) | **POST** /api/cluster/SecurityController/checkPassword | 
[**infrastructureCreateUserIfNotExists**](SecurityDirectoryClusterControllerApi.md#infrastructureCreateUserIfNotExists) | **POST** /api/cluster/SecurityController/createUserIfNotExists | 
[**infrastructureFindAllGroups**](SecurityDirectoryClusterControllerApi.md#infrastructureFindAllGroups) | **GET** /api/cluster/SecurityController/findAllGroups | 
[**infrastructureFindGroupsOfUser**](SecurityDirectoryClusterControllerApi.md#infrastructureFindGroupsOfUser) | **GET** /api/cluster/SecurityController/findGroupsOfUser | 
[**infrastructureFindUserByUsername**](SecurityDirectoryClusterControllerApi.md#infrastructureFindUserByUsername) | **GET** /api/cluster/SecurityController/findUserByUsername | 

<a name="infrastructureCheckPassword"></a>
# **infrastructureCheckPassword**
> Boolean infrastructureCheckPassword(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.SecurityDirectoryClusterControllerApi;


SecurityDirectoryClusterControllerApi apiInstance = new SecurityDirectoryClusterControllerApi();
CheckPasswordRequest body = new CheckPasswordRequest(); // CheckPasswordRequest | 
try {
    Boolean result = apiInstance.infrastructureCheckPassword(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SecurityDirectoryClusterControllerApi#infrastructureCheckPassword");
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

<a name="infrastructureCreateUserIfNotExists"></a>
# **infrastructureCreateUserIfNotExists**
> UserInfosImpl infrastructureCreateUserIfNotExists(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.SecurityDirectoryClusterControllerApi;


SecurityDirectoryClusterControllerApi apiInstance = new SecurityDirectoryClusterControllerApi();
CreateUserIfNotExistsRequest body = new CreateUserIfNotExistsRequest(); // CreateUserIfNotExistsRequest | 
try {
    UserInfosImpl result = apiInstance.infrastructureCreateUserIfNotExists(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SecurityDirectoryClusterControllerApi#infrastructureCreateUserIfNotExists");
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

<a name="infrastructureFindAllGroups"></a>
# **infrastructureFindAllGroups**
> List&lt;UsersGroup&gt; infrastructureFindAllGroups()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.SecurityDirectoryClusterControllerApi;


SecurityDirectoryClusterControllerApi apiInstance = new SecurityDirectoryClusterControllerApi();
try {
    List<UsersGroup> result = apiInstance.infrastructureFindAllGroups();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SecurityDirectoryClusterControllerApi#infrastructureFindAllGroups");
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

<a name="infrastructureFindGroupsOfUser"></a>
# **infrastructureFindGroupsOfUser**
> List&lt;UsersGroup&gt; infrastructureFindGroupsOfUser(username)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.SecurityDirectoryClusterControllerApi;


SecurityDirectoryClusterControllerApi apiInstance = new SecurityDirectoryClusterControllerApi();
String username = "username_example"; // String | 
try {
    List<UsersGroup> result = apiInstance.infrastructureFindGroupsOfUser(username);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SecurityDirectoryClusterControllerApi#infrastructureFindGroupsOfUser");
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

<a name="infrastructureFindUserByUsername"></a>
# **infrastructureFindUserByUsername**
> UserInfosImpl infrastructureFindUserByUsername(username)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.SecurityDirectoryClusterControllerApi;


SecurityDirectoryClusterControllerApi apiInstance = new SecurityDirectoryClusterControllerApi();
String username = "username_example"; // String | 
try {
    UserInfosImpl result = apiInstance.infrastructureFindUserByUsername(username);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SecurityDirectoryClusterControllerApi#infrastructureFindUserByUsername");
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

