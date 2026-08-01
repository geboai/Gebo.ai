# SecurityDirectoryClusterControllerApi

All URIs are relative to *http://localhost:13018/heimdall*

Method | HTTP request | Description
------------- | ------------- | -------------
[**checkPassword**](SecurityDirectoryClusterControllerApi.md#checkPassword) | **POST** /api/cluster/SecurityController/checkPassword | 
[**findAllGroups**](SecurityDirectoryClusterControllerApi.md#findAllGroups) | **GET** /api/cluster/SecurityController/findAllGroups | 
[**findGroupsOfUser**](SecurityDirectoryClusterControllerApi.md#findGroupsOfUser) | **GET** /api/cluster/SecurityController/findGroupsOfUser | 
[**findUserByUsername**](SecurityDirectoryClusterControllerApi.md#findUserByUsername) | **GET** /api/cluster/SecurityController/findUserByUsername | 

<a name="checkPassword"></a>
# **checkPassword**
> Object checkPassword(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.SecurityDirectoryClusterControllerApi;


SecurityDirectoryClusterControllerApi apiInstance = new SecurityDirectoryClusterControllerApi();
CheckPasswordRequest body = new CheckPasswordRequest(); // CheckPasswordRequest | 
try {
    Object result = apiInstance.checkPassword(body);
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

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findAllGroups"></a>
# **findAllGroups**
> Object findAllGroups()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.SecurityDirectoryClusterControllerApi;


SecurityDirectoryClusterControllerApi apiInstance = new SecurityDirectoryClusterControllerApi();
try {
    Object result = apiInstance.findAllGroups();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SecurityDirectoryClusterControllerApi#findAllGroups");
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
 - **Accept**: application/json

<a name="findGroupsOfUser"></a>
# **findGroupsOfUser**
> Object findGroupsOfUser(username)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.SecurityDirectoryClusterControllerApi;


SecurityDirectoryClusterControllerApi apiInstance = new SecurityDirectoryClusterControllerApi();
Object username = null; // Object | 
try {
    Object result = apiInstance.findGroupsOfUser(username);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SecurityDirectoryClusterControllerApi#findGroupsOfUser");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **username** | [**Object**](.md)|  |

### Return type

**Object**

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
Object username = null; // Object | 
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
 **username** | [**Object**](.md)|  |

### Return type

[**UserInfosImpl**](UserInfosImpl.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

