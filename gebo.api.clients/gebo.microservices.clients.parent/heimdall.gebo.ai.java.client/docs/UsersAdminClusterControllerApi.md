# UsersAdminClusterControllerApi

All URIs are relative to *http://localhost:13018/heimdall*

Method | HTTP request | Description
------------- | ------------- | -------------
[**changePassword1**](UsersAdminClusterControllerApi.md#changePassword1) | **POST** /api/cluster/SecurityController/UsersAdmin/changePassword | 
[**createUserIfNotExists1**](UsersAdminClusterControllerApi.md#createUserIfNotExists1) | **POST** /api/cluster/SecurityController/UsersAdmin/createUserIfNotExists | 
[**deleteGroup**](UsersAdminClusterControllerApi.md#deleteGroup) | **POST** /api/cluster/SecurityController/UsersAdmin/deleteGroup | 
[**deleteUser**](UsersAdminClusterControllerApi.md#deleteUser) | **POST** /api/cluster/SecurityController/UsersAdmin/deleteUser | 
[**findGroupByCode**](UsersAdminClusterControllerApi.md#findGroupByCode) | **GET** /api/cluster/SecurityController/UsersAdmin/findGroupByCode | 
[**findUserByQbe**](UsersAdminClusterControllerApi.md#findUserByQbe) | **POST** /api/cluster/SecurityController/UsersAdmin/findUserByQbe | 
[**findUserByQbe1**](UsersAdminClusterControllerApi.md#findUserByQbe1) | **POST** /api/cluster/SecurityController/UsersAdmin/findEditableUserByQbe | 
[**findUserByUsername1**](UsersAdminClusterControllerApi.md#findUserByUsername1) | **GET** /api/cluster/SecurityController/UsersAdmin/findUserByUsername | 
[**findUsersGroupByQbe**](UsersAdminClusterControllerApi.md#findUsersGroupByQbe) | **POST** /api/cluster/SecurityController/UsersAdmin/findUsersGroupByQbe | 
[**getAllGroups**](UsersAdminClusterControllerApi.md#getAllGroups) | **GET** /api/cluster/SecurityController/UsersAdmin/getAllGroups | 
[**getAllUsers**](UsersAdminClusterControllerApi.md#getAllUsers) | **GET** /api/cluster/SecurityController/UsersAdmin/getAllUsers | 
[**insertGroup**](UsersAdminClusterControllerApi.md#insertGroup) | **POST** /api/cluster/SecurityController/UsersAdmin/insertGroup | 
[**insertUser**](UsersAdminClusterControllerApi.md#insertUser) | **POST** /api/cluster/SecurityController/UsersAdmin/insertUser | 
[**updateGroup**](UsersAdminClusterControllerApi.md#updateGroup) | **POST** /api/cluster/SecurityController/UsersAdmin/updateGroup | 
[**updateUser**](UsersAdminClusterControllerApi.md#updateUser) | **POST** /api/cluster/SecurityController/UsersAdmin/updateUser | 

<a name="changePassword1"></a>
# **changePassword1**
> changePassword1(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminClusterControllerApi;


UsersAdminClusterControllerApi apiInstance = new UsersAdminClusterControllerApi();
ChangePasswordRequest body = new ChangePasswordRequest(); // ChangePasswordRequest | 
try {
    apiInstance.changePassword1(body);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminClusterControllerApi#changePassword1");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ChangePasswordRequest**](ChangePasswordRequest.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="createUserIfNotExists1"></a>
# **createUserIfNotExists1**
> createUserIfNotExists1(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminClusterControllerApi;


UsersAdminClusterControllerApi apiInstance = new UsersAdminClusterControllerApi();
CreateUserIfNotExistsRequest body = new CreateUserIfNotExistsRequest(); // CreateUserIfNotExistsRequest | 
try {
    apiInstance.createUserIfNotExists1(body);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminClusterControllerApi#createUserIfNotExists1");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**CreateUserIfNotExistsRequest**](CreateUserIfNotExistsRequest.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="deleteGroup"></a>
# **deleteGroup**
> deleteGroup(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminClusterControllerApi;


UsersAdminClusterControllerApi apiInstance = new UsersAdminClusterControllerApi();
UsersGroup body = new UsersGroup(); // UsersGroup | 
try {
    apiInstance.deleteGroup(body);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminClusterControllerApi#deleteGroup");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UsersGroup**](UsersGroup.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="deleteUser"></a>
# **deleteUser**
> deleteUser(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminClusterControllerApi;


UsersAdminClusterControllerApi apiInstance = new UsersAdminClusterControllerApi();
EditableUser body = new EditableUser(); // EditableUser | 
try {
    apiInstance.deleteUser(body);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminClusterControllerApi#deleteUser");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**EditableUser**](EditableUser.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="findGroupByCode"></a>
# **findGroupByCode**
> UsersGroup findGroupByCode(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminClusterControllerApi;


UsersAdminClusterControllerApi apiInstance = new UsersAdminClusterControllerApi();
Object code = null; // Object | 
try {
    UsersGroup result = apiInstance.findGroupByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminClusterControllerApi#findGroupByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | [**Object**](.md)|  |

### Return type

[**UsersGroup**](UsersGroup.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findUserByQbe"></a>
# **findUserByQbe**
> PageResultUserInfos findUserByQbe(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminClusterControllerApi;


UsersAdminClusterControllerApi apiInstance = new UsersAdminClusterControllerApi();
UserQbeRequest body = new UserQbeRequest(); // UserQbeRequest | 
try {
    PageResultUserInfos result = apiInstance.findUserByQbe(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminClusterControllerApi#findUserByQbe");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UserQbeRequest**](UserQbeRequest.md)|  |

### Return type

[**PageResultUserInfos**](PageResultUserInfos.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findUserByQbe1"></a>
# **findUserByQbe1**
> PageResultUserInfos findUserByQbe1(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminClusterControllerApi;


UsersAdminClusterControllerApi apiInstance = new UsersAdminClusterControllerApi();
EditableUserQbeRequest body = new EditableUserQbeRequest(); // EditableUserQbeRequest | 
try {
    PageResultUserInfos result = apiInstance.findUserByQbe1(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminClusterControllerApi#findUserByQbe1");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**EditableUserQbeRequest**](EditableUserQbeRequest.md)|  |

### Return type

[**PageResultUserInfos**](PageResultUserInfos.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findUserByUsername1"></a>
# **findUserByUsername1**
> EditableUser findUserByUsername1(email)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminClusterControllerApi;


UsersAdminClusterControllerApi apiInstance = new UsersAdminClusterControllerApi();
Object email = null; // Object | 
try {
    EditableUser result = apiInstance.findUserByUsername1(email);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminClusterControllerApi#findUserByUsername1");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **email** | [**Object**](.md)|  |

### Return type

[**EditableUser**](EditableUser.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findUsersGroupByQbe"></a>
# **findUsersGroupByQbe**
> PageResultUsersGroup findUsersGroupByQbe(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminClusterControllerApi;


UsersAdminClusterControllerApi apiInstance = new UsersAdminClusterControllerApi();
GroupQbeRequest body = new GroupQbeRequest(); // GroupQbeRequest | 
try {
    PageResultUsersGroup result = apiInstance.findUsersGroupByQbe(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminClusterControllerApi#findUsersGroupByQbe");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GroupQbeRequest**](GroupQbeRequest.md)|  |

### Return type

[**PageResultUsersGroup**](PageResultUsersGroup.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getAllGroups"></a>
# **getAllGroups**
> Object getAllGroups()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminClusterControllerApi;


UsersAdminClusterControllerApi apiInstance = new UsersAdminClusterControllerApi();
try {
    Object result = apiInstance.getAllGroups();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminClusterControllerApi#getAllGroups");
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

<a name="getAllUsers"></a>
# **getAllUsers**
> Object getAllUsers()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminClusterControllerApi;


UsersAdminClusterControllerApi apiInstance = new UsersAdminClusterControllerApi();
try {
    Object result = apiInstance.getAllUsers();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminClusterControllerApi#getAllUsers");
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

<a name="insertGroup"></a>
# **insertGroup**
> UsersGroup insertGroup(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminClusterControllerApi;


UsersAdminClusterControllerApi apiInstance = new UsersAdminClusterControllerApi();
UsersGroup body = new UsersGroup(); // UsersGroup | 
try {
    UsersGroup result = apiInstance.insertGroup(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminClusterControllerApi#insertGroup");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UsersGroup**](UsersGroup.md)|  |

### Return type

[**UsersGroup**](UsersGroup.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertUser"></a>
# **insertUser**
> EditableUser insertUser(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminClusterControllerApi;


UsersAdminClusterControllerApi apiInstance = new UsersAdminClusterControllerApi();
InsertUserRequest body = new InsertUserRequest(); // InsertUserRequest | 
try {
    EditableUser result = apiInstance.insertUser(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminClusterControllerApi#insertUser");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**InsertUserRequest**](InsertUserRequest.md)|  |

### Return type

[**EditableUser**](EditableUser.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateGroup"></a>
# **updateGroup**
> UsersGroup updateGroup(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminClusterControllerApi;


UsersAdminClusterControllerApi apiInstance = new UsersAdminClusterControllerApi();
UsersGroup body = new UsersGroup(); // UsersGroup | 
try {
    UsersGroup result = apiInstance.updateGroup(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminClusterControllerApi#updateGroup");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UsersGroup**](UsersGroup.md)|  |

### Return type

[**UsersGroup**](UsersGroup.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateUser"></a>
# **updateUser**
> EditableUser updateUser(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminClusterControllerApi;


UsersAdminClusterControllerApi apiInstance = new UsersAdminClusterControllerApi();
EditableUser body = new EditableUser(); // EditableUser | 
try {
    EditableUser result = apiInstance.updateUser(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminClusterControllerApi#updateUser");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**EditableUser**](EditableUser.md)|  |

### Return type

[**EditableUser**](EditableUser.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

