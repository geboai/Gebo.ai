# UsersAdminControllerApi

All URIs are relative to *http://localhost:13018/heimdall*

Method | HTTP request | Description
------------- | ------------- | -------------
[**changeUserPassword**](UsersAdminControllerApi.md#changeUserPassword) | **POST** /api/admin/UsersAdminController/changeUserPassword | 
[**deleteGroup**](UsersAdminControllerApi.md#deleteGroup) | **POST** /api/admin/UsersAdminController/deleteGroup | 
[**deleteUser**](UsersAdminControllerApi.md#deleteUser) | **POST** /api/admin/UsersAdminController/deleteUser | 
[**findGroupByCode**](UsersAdminControllerApi.md#findGroupByCode) | **GET** /api/admin/UsersAdminController/findGroupByCode | 
[**findUserByQbe**](UsersAdminControllerApi.md#findUserByQbe) | **POST** /api/admin/UsersAdminController/findUserByQbe | 
[**findUserByUsername1**](UsersAdminControllerApi.md#findUserByUsername1) | **GET** /api/admin/UsersAdminController/findUserByUsername | 
[**findUsersGroupByQbe**](UsersAdminControllerApi.md#findUsersGroupByQbe) | **POST** /api/admin/UsersAdminController/findUsersGroupByQbe | 
[**getAllGroups**](UsersAdminControllerApi.md#getAllGroups) | **GET** /api/admin/UsersAdminController/getAllGroups | 
[**getAllUsers**](UsersAdminControllerApi.md#getAllUsers) | **GET** /api/admin/UsersAdminController/getAllUsers | 
[**insertGroup**](UsersAdminControllerApi.md#insertGroup) | **POST** /api/admin/UsersAdminController/insertGroup | 
[**insertUser**](UsersAdminControllerApi.md#insertUser) | **POST** /api/admin/UsersAdminController/insertUser | 
[**updateGroup**](UsersAdminControllerApi.md#updateGroup) | **POST** /api/admin/UsersAdminController/updateGroup | 
[**updateUser**](UsersAdminControllerApi.md#updateUser) | **POST** /api/admin/UsersAdminController/updateUser | 

<a name="changeUserPassword"></a>
# **changeUserPassword**
> GUserMessage changeUserPassword(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminControllerApi;


UsersAdminControllerApi apiInstance = new UsersAdminControllerApi();
ChangeUsernamePasswordData body = new ChangeUsernamePasswordData(); // ChangeUsernamePasswordData | 
try {
    GUserMessage result = apiInstance.changeUserPassword(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminControllerApi#changeUserPassword");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ChangeUsernamePasswordData**](ChangeUsernamePasswordData.md)|  |

### Return type

[**GUserMessage**](GUserMessage.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteGroup"></a>
# **deleteGroup**
> deleteGroup(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminControllerApi;


UsersAdminControllerApi apiInstance = new UsersAdminControllerApi();
UsersGroup body = new UsersGroup(); // UsersGroup | 
try {
    apiInstance.deleteGroup(body);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminControllerApi#deleteGroup");
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
//import gebo.microservices.api.client.heimdall.api.UsersAdminControllerApi;


UsersAdminControllerApi apiInstance = new UsersAdminControllerApi();
EditableUser body = new EditableUser(); // EditableUser | 
try {
    apiInstance.deleteUser(body);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminControllerApi#deleteUser");
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
//import gebo.microservices.api.client.heimdall.api.UsersAdminControllerApi;


UsersAdminControllerApi apiInstance = new UsersAdminControllerApi();
Object code = null; // Object | 
try {
    UsersGroup result = apiInstance.findGroupByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminControllerApi#findGroupByCode");
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
> PageUserInfos findUserByQbe(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminControllerApi;


UsersAdminControllerApi apiInstance = new UsersAdminControllerApi();
FindUserByQbeParam body = new FindUserByQbeParam(); // FindUserByQbeParam | 
try {
    PageUserInfos result = apiInstance.findUserByQbe(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminControllerApi#findUserByQbe");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**FindUserByQbeParam**](FindUserByQbeParam.md)|  |

### Return type

[**PageUserInfos**](PageUserInfos.md)

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
//import gebo.microservices.api.client.heimdall.api.UsersAdminControllerApi;


UsersAdminControllerApi apiInstance = new UsersAdminControllerApi();
Object email = null; // Object | 
try {
    EditableUser result = apiInstance.findUserByUsername1(email);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminControllerApi#findUserByUsername1");
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
> PageUsersGroup findUsersGroupByQbe(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminControllerApi;


UsersAdminControllerApi apiInstance = new UsersAdminControllerApi();
FindUsersGroupParam body = new FindUsersGroupParam(); // FindUsersGroupParam | 
try {
    PageUsersGroup result = apiInstance.findUsersGroupByQbe(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminControllerApi#findUsersGroupByQbe");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**FindUsersGroupParam**](FindUsersGroupParam.md)|  |

### Return type

[**PageUsersGroup**](PageUsersGroup.md)

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
//import gebo.microservices.api.client.heimdall.api.UsersAdminControllerApi;


UsersAdminControllerApi apiInstance = new UsersAdminControllerApi();
try {
    Object result = apiInstance.getAllGroups();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminControllerApi#getAllGroups");
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
//import gebo.microservices.api.client.heimdall.api.UsersAdminControllerApi;


UsersAdminControllerApi apiInstance = new UsersAdminControllerApi();
try {
    Object result = apiInstance.getAllUsers();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminControllerApi#getAllUsers");
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
//import gebo.microservices.api.client.heimdall.api.UsersAdminControllerApi;


UsersAdminControllerApi apiInstance = new UsersAdminControllerApi();
UsersGroup body = new UsersGroup(); // UsersGroup | 
try {
    UsersGroup result = apiInstance.insertGroup(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminControllerApi#insertGroup");
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
//import gebo.microservices.api.client.heimdall.api.UsersAdminControllerApi;


UsersAdminControllerApi apiInstance = new UsersAdminControllerApi();
InsertUserParam body = new InsertUserParam(); // InsertUserParam | 
try {
    EditableUser result = apiInstance.insertUser(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminControllerApi#insertUser");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**InsertUserParam**](InsertUserParam.md)|  |

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
//import gebo.microservices.api.client.heimdall.api.UsersAdminControllerApi;


UsersAdminControllerApi apiInstance = new UsersAdminControllerApi();
UsersGroup body = new UsersGroup(); // UsersGroup | 
try {
    UsersGroup result = apiInstance.updateGroup(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminControllerApi#updateGroup");
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
//import gebo.microservices.api.client.heimdall.api.UsersAdminControllerApi;


UsersAdminControllerApi apiInstance = new UsersAdminControllerApi();
EditableUser body = new EditableUser(); // EditableUser | 
try {
    EditableUser result = apiInstance.updateUser(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminControllerApi#updateUser");
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

