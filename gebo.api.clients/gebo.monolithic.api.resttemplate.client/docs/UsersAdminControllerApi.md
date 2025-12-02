# UsersAdminControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGroup**](UsersAdminControllerApi.md#deleteGroup) | **POST** /api/admin/UsersAdminController/deleteGroup | 
[**deleteUser**](UsersAdminControllerApi.md#deleteUser) | **POST** /api/admin/UsersAdminController/deleteUser | 
[**findGroupByCode**](UsersAdminControllerApi.md#findGroupByCode) | **GET** /api/admin/UsersAdminController/findGroupByCode | 
[**findUserByQbe**](UsersAdminControllerApi.md#findUserByQbe) | **POST** /api/admin/UsersAdminController/findUserByQbe | 
[**findUserByUsername**](UsersAdminControllerApi.md#findUserByUsername) | **GET** /api/admin/UsersAdminController/findUserByUsername | 
[**findUsersGroupByQbe**](UsersAdminControllerApi.md#findUsersGroupByQbe) | **POST** /api/admin/UsersAdminController/findUsersGroupByQbe | 
[**getAllGroups**](UsersAdminControllerApi.md#getAllGroups) | **GET** /api/admin/UsersAdminController/getAllGroups | 
[**getAllUsers**](UsersAdminControllerApi.md#getAllUsers) | **GET** /api/admin/UsersAdminController/getAllUsers | 
[**insertGroup**](UsersAdminControllerApi.md#insertGroup) | **POST** /api/admin/UsersAdminController/insertGroup | 
[**insertUser**](UsersAdminControllerApi.md#insertUser) | **POST** /api/admin/UsersAdminController/insertUser | 
[**updateGroup**](UsersAdminControllerApi.md#updateGroup) | **POST** /api/admin/UsersAdminController/updateGroup | 
[**updateUser**](UsersAdminControllerApi.md#updateUser) | **POST** /api/admin/UsersAdminController/updateUser | 

<a name="deleteGroup"></a>
# **deleteGroup**
> deleteGroup(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UsersAdminControllerApi;


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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UsersAdminControllerApi;


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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UsersAdminControllerApi;


UsersAdminControllerApi apiInstance = new UsersAdminControllerApi();
String code = "code_example"; // String | 
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
 **code** | **String**|  |

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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UsersAdminControllerApi;


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

<a name="findUserByUsername"></a>
# **findUserByUsername**
> EditableUser findUserByUsername(email)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UsersAdminControllerApi;


UsersAdminControllerApi apiInstance = new UsersAdminControllerApi();
String email = "email_example"; // String | 
try {
    EditableUser result = apiInstance.findUserByUsername(email);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminControllerApi#findUserByUsername");
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

<a name="findUsersGroupByQbe"></a>
# **findUsersGroupByQbe**
> PageUsersGroup findUsersGroupByQbe(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UsersAdminControllerApi;


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
> List&lt;UsersGroup&gt; getAllGroups()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UsersAdminControllerApi;


UsersAdminControllerApi apiInstance = new UsersAdminControllerApi();
try {
    List<UsersGroup> result = apiInstance.getAllGroups();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminControllerApi#getAllGroups");
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

<a name="getAllUsers"></a>
# **getAllUsers**
> List&lt;UserInfos&gt; getAllUsers()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UsersAdminControllerApi;


UsersAdminControllerApi apiInstance = new UsersAdminControllerApi();
try {
    List<UserInfos> result = apiInstance.getAllUsers();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsersAdminControllerApi#getAllUsers");
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

<a name="insertGroup"></a>
# **insertGroup**
> UsersGroup insertGroup(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UsersAdminControllerApi;


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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UsersAdminControllerApi;


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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UsersAdminControllerApi;


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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UsersAdminControllerApi;


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

