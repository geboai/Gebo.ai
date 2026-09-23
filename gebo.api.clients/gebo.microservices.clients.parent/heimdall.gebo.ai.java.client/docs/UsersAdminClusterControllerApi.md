# UsersAdminClusterControllerApi

All URIs are relative to *http://localhost:13018/heimdall*

Method | HTTP request | Description
------------- | ------------- | -------------
[**infrastructureChangePassword**](UsersAdminClusterControllerApi.md#infrastructureChangePassword) | **POST** /api/cluster/SecurityController/UsersAdmin/changePassword | 
[**infrastructureCreateUserIfNotExists1**](UsersAdminClusterControllerApi.md#infrastructureCreateUserIfNotExists1) | **POST** /api/cluster/SecurityController/UsersAdmin/createUserIfNotExists | 
[**infrastructureDeleteGroup**](UsersAdminClusterControllerApi.md#infrastructureDeleteGroup) | **POST** /api/cluster/SecurityController/UsersAdmin/deleteGroup | 
[**infrastructureDeleteUser**](UsersAdminClusterControllerApi.md#infrastructureDeleteUser) | **POST** /api/cluster/SecurityController/UsersAdmin/deleteUser | 
[**infrastructureFindGroupByCode**](UsersAdminClusterControllerApi.md#infrastructureFindGroupByCode) | **GET** /api/cluster/SecurityController/UsersAdmin/findGroupByCode | 
[**infrastructureFindUserByQbe**](UsersAdminClusterControllerApi.md#infrastructureFindUserByQbe) | **POST** /api/cluster/SecurityController/UsersAdmin/findUserByQbe | 
[**infrastructureFindUserByQbe1**](UsersAdminClusterControllerApi.md#infrastructureFindUserByQbe1) | **POST** /api/cluster/SecurityController/UsersAdmin/findEditableUserByQbe | 
[**infrastructureFindUserByUsername1**](UsersAdminClusterControllerApi.md#infrastructureFindUserByUsername1) | **GET** /api/cluster/SecurityController/UsersAdmin/findUserByUsername | 
[**infrastructureFindUsersGroupByQbe**](UsersAdminClusterControllerApi.md#infrastructureFindUsersGroupByQbe) | **POST** /api/cluster/SecurityController/UsersAdmin/findUsersGroupByQbe | 
[**infrastructureGetAllGroups**](UsersAdminClusterControllerApi.md#infrastructureGetAllGroups) | **GET** /api/cluster/SecurityController/UsersAdmin/getAllGroups | 
[**infrastructureGetAllUsers**](UsersAdminClusterControllerApi.md#infrastructureGetAllUsers) | **GET** /api/cluster/SecurityController/UsersAdmin/getAllUsers | 
[**infrastructureInsertGroup**](UsersAdminClusterControllerApi.md#infrastructureInsertGroup) | **POST** /api/cluster/SecurityController/UsersAdmin/insertGroup | 
[**infrastructureInsertUser**](UsersAdminClusterControllerApi.md#infrastructureInsertUser) | **POST** /api/cluster/SecurityController/UsersAdmin/insertUser | 
[**infrastructureUpdateGroup**](UsersAdminClusterControllerApi.md#infrastructureUpdateGroup) | **POST** /api/cluster/SecurityController/UsersAdmin/updateGroup | 
[**infrastructureUpdateUser**](UsersAdminClusterControllerApi.md#infrastructureUpdateUser) | **POST** /api/cluster/SecurityController/UsersAdmin/updateUser | 

<a name="infrastructureChangePassword"></a>
# **infrastructureChangePassword**
> infrastructureChangePassword(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminClusterControllerApi;


UsersAdminClusterControllerApi apiInstance = new UsersAdminClusterControllerApi();
ChangePasswordRequest body = new ChangePasswordRequest(); // ChangePasswordRequest | 
try {
    apiInstance.infrastructureChangePassword(body);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminClusterControllerApi#infrastructureChangePassword");
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

<a name="infrastructureCreateUserIfNotExists1"></a>
# **infrastructureCreateUserIfNotExists1**
> infrastructureCreateUserIfNotExists1(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminClusterControllerApi;


UsersAdminClusterControllerApi apiInstance = new UsersAdminClusterControllerApi();
CreateUserIfNotExistsRequest body = new CreateUserIfNotExistsRequest(); // CreateUserIfNotExistsRequest | 
try {
    apiInstance.infrastructureCreateUserIfNotExists1(body);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminClusterControllerApi#infrastructureCreateUserIfNotExists1");
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

<a name="infrastructureDeleteGroup"></a>
# **infrastructureDeleteGroup**
> infrastructureDeleteGroup(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminClusterControllerApi;


UsersAdminClusterControllerApi apiInstance = new UsersAdminClusterControllerApi();
UsersGroup body = new UsersGroup(); // UsersGroup | 
try {
    apiInstance.infrastructureDeleteGroup(body);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminClusterControllerApi#infrastructureDeleteGroup");
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

<a name="infrastructureDeleteUser"></a>
# **infrastructureDeleteUser**
> infrastructureDeleteUser(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminClusterControllerApi;


UsersAdminClusterControllerApi apiInstance = new UsersAdminClusterControllerApi();
EditableUser body = new EditableUser(); // EditableUser | 
try {
    apiInstance.infrastructureDeleteUser(body);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminClusterControllerApi#infrastructureDeleteUser");
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

<a name="infrastructureFindGroupByCode"></a>
# **infrastructureFindGroupByCode**
> UsersGroup infrastructureFindGroupByCode(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminClusterControllerApi;


UsersAdminClusterControllerApi apiInstance = new UsersAdminClusterControllerApi();
String code = "code_example"; // String | 
try {
    UsersGroup result = apiInstance.infrastructureFindGroupByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminClusterControllerApi#infrastructureFindGroupByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**UsersGroup**](UsersGroup.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="infrastructureFindUserByQbe"></a>
# **infrastructureFindUserByQbe**
> PageResultUserInfos infrastructureFindUserByQbe(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminClusterControllerApi;


UsersAdminClusterControllerApi apiInstance = new UsersAdminClusterControllerApi();
UserQbeRequest body = new UserQbeRequest(); // UserQbeRequest | 
try {
    PageResultUserInfos result = apiInstance.infrastructureFindUserByQbe(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminClusterControllerApi#infrastructureFindUserByQbe");
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

<a name="infrastructureFindUserByQbe1"></a>
# **infrastructureFindUserByQbe1**
> PageResultUserInfos infrastructureFindUserByQbe1(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminClusterControllerApi;


UsersAdminClusterControllerApi apiInstance = new UsersAdminClusterControllerApi();
EditableUserQbeRequest body = new EditableUserQbeRequest(); // EditableUserQbeRequest | 
try {
    PageResultUserInfos result = apiInstance.infrastructureFindUserByQbe1(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminClusterControllerApi#infrastructureFindUserByQbe1");
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

<a name="infrastructureFindUserByUsername1"></a>
# **infrastructureFindUserByUsername1**
> EditableUser infrastructureFindUserByUsername1(email)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminClusterControllerApi;


UsersAdminClusterControllerApi apiInstance = new UsersAdminClusterControllerApi();
String email = "email_example"; // String | 
try {
    EditableUser result = apiInstance.infrastructureFindUserByUsername1(email);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminClusterControllerApi#infrastructureFindUserByUsername1");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **email** | **String**|  |

### Return type

[**EditableUser**](EditableUser.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="infrastructureFindUsersGroupByQbe"></a>
# **infrastructureFindUsersGroupByQbe**
> PageResultUsersGroup infrastructureFindUsersGroupByQbe(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminClusterControllerApi;


UsersAdminClusterControllerApi apiInstance = new UsersAdminClusterControllerApi();
GroupQbeRequest body = new GroupQbeRequest(); // GroupQbeRequest | 
try {
    PageResultUsersGroup result = apiInstance.infrastructureFindUsersGroupByQbe(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminClusterControllerApi#infrastructureFindUsersGroupByQbe");
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

<a name="infrastructureGetAllGroups"></a>
# **infrastructureGetAllGroups**
> List&lt;UsersGroup&gt; infrastructureGetAllGroups()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminClusterControllerApi;


UsersAdminClusterControllerApi apiInstance = new UsersAdminClusterControllerApi();
try {
    List<UsersGroup> result = apiInstance.infrastructureGetAllGroups();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminClusterControllerApi#infrastructureGetAllGroups");
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

<a name="infrastructureGetAllUsers"></a>
# **infrastructureGetAllUsers**
> List&lt;UserInfos&gt; infrastructureGetAllUsers()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminClusterControllerApi;


UsersAdminClusterControllerApi apiInstance = new UsersAdminClusterControllerApi();
try {
    List<UserInfos> result = apiInstance.infrastructureGetAllUsers();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminClusterControllerApi#infrastructureGetAllUsers");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;UserInfos&gt;**](UserInfos.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="infrastructureInsertGroup"></a>
# **infrastructureInsertGroup**
> UsersGroup infrastructureInsertGroup(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminClusterControllerApi;


UsersAdminClusterControllerApi apiInstance = new UsersAdminClusterControllerApi();
UsersGroup body = new UsersGroup(); // UsersGroup | 
try {
    UsersGroup result = apiInstance.infrastructureInsertGroup(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminClusterControllerApi#infrastructureInsertGroup");
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

<a name="infrastructureInsertUser"></a>
# **infrastructureInsertUser**
> EditableUser infrastructureInsertUser(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminClusterControllerApi;


UsersAdminClusterControllerApi apiInstance = new UsersAdminClusterControllerApi();
InsertUserRequest body = new InsertUserRequest(); // InsertUserRequest | 
try {
    EditableUser result = apiInstance.infrastructureInsertUser(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminClusterControllerApi#infrastructureInsertUser");
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

<a name="infrastructureUpdateGroup"></a>
# **infrastructureUpdateGroup**
> UsersGroup infrastructureUpdateGroup(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminClusterControllerApi;


UsersAdminClusterControllerApi apiInstance = new UsersAdminClusterControllerApi();
UsersGroup body = new UsersGroup(); // UsersGroup | 
try {
    UsersGroup result = apiInstance.infrastructureUpdateGroup(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminClusterControllerApi#infrastructureUpdateGroup");
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

<a name="infrastructureUpdateUser"></a>
# **infrastructureUpdateUser**
> EditableUser infrastructureUpdateUser(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminClusterControllerApi;


UsersAdminClusterControllerApi apiInstance = new UsersAdminClusterControllerApi();
EditableUser body = new EditableUser(); // EditableUser | 
try {
    EditableUser result = apiInstance.infrastructureUpdateUser(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminClusterControllerApi#infrastructureUpdateUser");
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

