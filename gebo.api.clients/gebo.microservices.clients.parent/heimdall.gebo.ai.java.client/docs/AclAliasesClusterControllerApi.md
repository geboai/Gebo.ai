# AclAliasesClusterControllerApi

All URIs are relative to *http://localhost:13018/heimdall*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addAcl**](AclAliasesClusterControllerApi.md#addAcl) | **POST** /api/cluster/AclController/addAcl | 
[**findAcl**](AclAliasesClusterControllerApi.md#findAcl) | **GET** /api/cluster/AclController/findAcl | 
[**findAlias**](AclAliasesClusterControllerApi.md#findAlias) | **POST** /api/cluster/AclController/findAlias | 
[**findAliasesByAclGrantedUniqueId**](AclAliasesClusterControllerApi.md#findAliasesByAclGrantedUniqueId) | **GET** /api/cluster/AclController/findAliasesByAclGrantedUniqueId | 
[**findAliasesByAclGrantedUniqueIdAndAclGrantType**](AclAliasesClusterControllerApi.md#findAliasesByAclGrantedUniqueIdAndAclGrantType) | **GET** /api/cluster/AclController/findAliasesByAclGrantedUniqueIdAndAclGrantType | 
[**findAliasesByAclGrantedUniqueIdIn**](AclAliasesClusterControllerApi.md#findAliasesByAclGrantedUniqueIdIn) | **POST** /api/cluster/AclController/findAliasesByAclGrantedUniqueIdIn | 
[**findAliasesByAclGrantedUniqueIdInAndAclGrantType**](AclAliasesClusterControllerApi.md#findAliasesByAclGrantedUniqueIdInAndAclGrantType) | **POST** /api/cluster/AclController/findAliasesByAclGrantedUniqueIdInAndAclGrantType | 
[**removeAcl**](AclAliasesClusterControllerApi.md#removeAcl) | **DELETE** /api/cluster/AclController/removeAcl | 

<a name="addAcl"></a>
# **addAcl**
> Object addAcl(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.AclAliasesClusterControllerApi;


AclAliasesClusterControllerApi apiInstance = new AclAliasesClusterControllerApi();
GAclEntry body = new GAclEntry(); // GAclEntry | 
try {
    Object result = apiInstance.addAcl(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AclAliasesClusterControllerApi#addAcl");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAclEntry**](GAclEntry.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findAcl"></a>
# **findAcl**
> GAclEntry findAcl(alias)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.AclAliasesClusterControllerApi;


AclAliasesClusterControllerApi apiInstance = new AclAliasesClusterControllerApi();
Object alias = null; // Object | 
try {
    GAclEntry result = apiInstance.findAcl(alias);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AclAliasesClusterControllerApi#findAcl");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **alias** | [**Object**](.md)|  |

### Return type

[**GAclEntry**](GAclEntry.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findAlias"></a>
# **findAlias**
> Object findAlias(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.AclAliasesClusterControllerApi;


AclAliasesClusterControllerApi apiInstance = new AclAliasesClusterControllerApi();
GAclEntry body = new GAclEntry(); // GAclEntry | 
try {
    Object result = apiInstance.findAlias(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AclAliasesClusterControllerApi#findAlias");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAclEntry**](GAclEntry.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findAliasesByAclGrantedUniqueId"></a>
# **findAliasesByAclGrantedUniqueId**
> Object findAliasesByAclGrantedUniqueId(uniqueId)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.AclAliasesClusterControllerApi;


AclAliasesClusterControllerApi apiInstance = new AclAliasesClusterControllerApi();
Object uniqueId = null; // Object | 
try {
    Object result = apiInstance.findAliasesByAclGrantedUniqueId(uniqueId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AclAliasesClusterControllerApi#findAliasesByAclGrantedUniqueId");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **uniqueId** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findAliasesByAclGrantedUniqueIdAndAclGrantType"></a>
# **findAliasesByAclGrantedUniqueIdAndAclGrantType**
> Object findAliasesByAclGrantedUniqueIdAndAclGrantType(uniqueId, grantType)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.AclAliasesClusterControllerApi;


AclAliasesClusterControllerApi apiInstance = new AclAliasesClusterControllerApi();
Object uniqueId = null; // Object | 
Object grantType = null; // Object | 
try {
    Object result = apiInstance.findAliasesByAclGrantedUniqueIdAndAclGrantType(uniqueId, grantType);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AclAliasesClusterControllerApi#findAliasesByAclGrantedUniqueIdAndAclGrantType");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **uniqueId** | [**Object**](.md)|  |
 **grantType** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findAliasesByAclGrantedUniqueIdIn"></a>
# **findAliasesByAclGrantedUniqueIdIn**
> Object findAliasesByAclGrantedUniqueIdIn(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.AclAliasesClusterControllerApi;


AclAliasesClusterControllerApi apiInstance = new AclAliasesClusterControllerApi();
Object body = null; // Object | 
try {
    Object result = apiInstance.findAliasesByAclGrantedUniqueIdIn(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AclAliasesClusterControllerApi#findAliasesByAclGrantedUniqueIdIn");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findAliasesByAclGrantedUniqueIdInAndAclGrantType"></a>
# **findAliasesByAclGrantedUniqueIdInAndAclGrantType**
> Object findAliasesByAclGrantedUniqueIdInAndAclGrantType(body, grantType)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.AclAliasesClusterControllerApi;


AclAliasesClusterControllerApi apiInstance = new AclAliasesClusterControllerApi();
Object body = null; // Object | 
Object grantType = null; // Object | 
try {
    Object result = apiInstance.findAliasesByAclGrantedUniqueIdInAndAclGrantType(body, grantType);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AclAliasesClusterControllerApi#findAliasesByAclGrantedUniqueIdInAndAclGrantType");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  |
 **grantType** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="removeAcl"></a>
# **removeAcl**
> removeAcl(alias)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.AclAliasesClusterControllerApi;


AclAliasesClusterControllerApi apiInstance = new AclAliasesClusterControllerApi();
Object alias = null; // Object | 
try {
    apiInstance.removeAcl(alias);
} catch (ApiException e) {
    System.err.println("Exception when calling AclAliasesClusterControllerApi#removeAcl");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **alias** | [**Object**](.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

