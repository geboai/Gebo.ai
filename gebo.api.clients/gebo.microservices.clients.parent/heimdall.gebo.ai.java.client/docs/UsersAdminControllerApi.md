# UsersAdminControllerApi

All URIs are relative to *http://localhost:13018/heimdall*

Method | HTTP request | Description
------------- | ------------- | -------------
[**changeUserPassword**](UsersAdminControllerApi.md#changeUserPassword) | **POST** /api/admin/UsersAdminController/changeUserPassword | 
[**deleteGroup1**](UsersAdminControllerApi.md#deleteGroup1) | **POST** /api/admin/UsersAdminController/deleteGroup | 
[**deleteUser1**](UsersAdminControllerApi.md#deleteUser1) | **POST** /api/admin/UsersAdminController/deleteUser | 
[**findGroupByCode1**](UsersAdminControllerApi.md#findGroupByCode1) | **GET** /api/admin/UsersAdminController/findGroupByCode | 
[**findUserByQbe2**](UsersAdminControllerApi.md#findUserByQbe2) | **POST** /api/admin/UsersAdminController/findUserByQbe | 
[**findUserByUsername2**](UsersAdminControllerApi.md#findUserByUsername2) | **GET** /api/admin/UsersAdminController/findUserByUsername | 
[**findUsersGroupByQbe1**](UsersAdminControllerApi.md#findUsersGroupByQbe1) | **POST** /api/admin/UsersAdminController/findUsersGroupByQbe | 
[**getAllGroups1**](UsersAdminControllerApi.md#getAllGroups1) | **GET** /api/admin/UsersAdminController/getAllGroups | 
[**getAllUsers1**](UsersAdminControllerApi.md#getAllUsers1) | **GET** /api/admin/UsersAdminController/getAllUsers | 
[**insertGroup1**](UsersAdminControllerApi.md#insertGroup1) | **POST** /api/admin/UsersAdminController/insertGroup | 
[**insertUser1**](UsersAdminControllerApi.md#insertUser1) | **POST** /api/admin/UsersAdminController/insertUser | 
[**updateGroup1**](UsersAdminControllerApi.md#updateGroup1) | **POST** /api/admin/UsersAdminController/updateGroup | 
[**updateUser1**](UsersAdminControllerApi.md#updateUser1) | **POST** /api/admin/UsersAdminController/updateUser | 

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

<a name="deleteGroup1"></a>
# **deleteGroup1**
> deleteGroup1(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminControllerApi;


UsersAdminControllerApi apiInstance = new UsersAdminControllerApi();
UsersGroup body = new UsersGroup(); // UsersGroup | 
try {
    apiInstance.deleteGroup1(body);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminControllerApi#deleteGroup1");
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

<a name="deleteUser1"></a>
# **deleteUser1**
> deleteUser1(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminControllerApi;


UsersAdminControllerApi apiInstance = new UsersAdminControllerApi();
EditableUser body = new EditableUser(); // EditableUser | 
try {
    apiInstance.deleteUser1(body);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminControllerApi#deleteUser1");
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

<a name="findGroupByCode1"></a>
# **findGroupByCode1**
> UsersGroup findGroupByCode1(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminControllerApi;


UsersAdminControllerApi apiInstance = new UsersAdminControllerApi();
Object code = null; // Object | 
try {
    UsersGroup result = apiInstance.findGroupByCode1(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminControllerApi#findGroupByCode1");
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

<a name="findUserByQbe2"></a>
# **findUserByQbe2**
> PageUserInfos findUserByQbe2(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminControllerApi;


UsersAdminControllerApi apiInstance = new UsersAdminControllerApi();
FindUserByQbeParam body = new FindUserByQbeParam(); // FindUserByQbeParam | 
try {
    PageUserInfos result = apiInstance.findUserByQbe2(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminControllerApi#findUserByQbe2");
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

<a name="findUserByUsername2"></a>
# **findUserByUsername2**
> EditableUser findUserByUsername2(email)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminControllerApi;


UsersAdminControllerApi apiInstance = new UsersAdminControllerApi();
Object email = null; // Object | 
try {
    EditableUser result = apiInstance.findUserByUsername2(email);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminControllerApi#findUserByUsername2");
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

<a name="findUsersGroupByQbe1"></a>
# **findUsersGroupByQbe1**
> PageUsersGroup findUsersGroupByQbe1(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminControllerApi;


UsersAdminControllerApi apiInstance = new UsersAdminControllerApi();
FindUsersGroupParam body = new FindUsersGroupParam(); // FindUsersGroupParam | 
try {
    PageUsersGroup result = apiInstance.findUsersGroupByQbe1(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminControllerApi#findUsersGroupByQbe1");
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

<a name="getAllGroups1"></a>
# **getAllGroups1**
> Object getAllGroups1()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminControllerApi;


UsersAdminControllerApi apiInstance = new UsersAdminControllerApi();
try {
    Object result = apiInstance.getAllGroups1();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminControllerApi#getAllGroups1");
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

<a name="getAllUsers1"></a>
# **getAllUsers1**
> Object getAllUsers1()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminControllerApi;


UsersAdminControllerApi apiInstance = new UsersAdminControllerApi();
try {
    Object result = apiInstance.getAllUsers1();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminControllerApi#getAllUsers1");
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

<a name="insertGroup1"></a>
# **insertGroup1**
> UsersGroup insertGroup1(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminControllerApi;


UsersAdminControllerApi apiInstance = new UsersAdminControllerApi();
UsersGroup body = new UsersGroup(); // UsersGroup | 
try {
    UsersGroup result = apiInstance.insertGroup1(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminControllerApi#insertGroup1");
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

<a name="insertUser1"></a>
# **insertUser1**
> EditableUser insertUser1(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminControllerApi;


UsersAdminControllerApi apiInstance = new UsersAdminControllerApi();
InsertUserParam body = new InsertUserParam(); // InsertUserParam | 
try {
    EditableUser result = apiInstance.insertUser1(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminControllerApi#insertUser1");
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

<a name="updateGroup1"></a>
# **updateGroup1**
> UsersGroup updateGroup1(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminControllerApi;


UsersAdminControllerApi apiInstance = new UsersAdminControllerApi();
UsersGroup body = new UsersGroup(); // UsersGroup | 
try {
    UsersGroup result = apiInstance.updateGroup1(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminControllerApi#updateGroup1");
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

<a name="updateUser1"></a>
# **updateUser1**
> EditableUser updateUser1(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.UsersAdminControllerApi;


UsersAdminControllerApi apiInstance = new UsersAdminControllerApi();
EditableUser body = new EditableUser(); // EditableUser | 
try {
    EditableUser result = apiInstance.updateUser1(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminControllerApi#updateUser1");
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

