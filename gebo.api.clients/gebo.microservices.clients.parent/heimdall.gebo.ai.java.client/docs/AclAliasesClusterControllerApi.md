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
> Integer addAcl(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.AclAliasesClusterControllerApi;


AclAliasesClusterControllerApi apiInstance = new AclAliasesClusterControllerApi();
GAclEntry body = new GAclEntry(); // GAclEntry | 
try {
    Integer result = apiInstance.addAcl(body);
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

**Integer**

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
Integer alias = 56; // Integer | 
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
 **alias** | **Integer**|  |

### Return type

[**GAclEntry**](GAclEntry.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findAlias"></a>
# **findAlias**
> Integer findAlias(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.AclAliasesClusterControllerApi;


AclAliasesClusterControllerApi apiInstance = new AclAliasesClusterControllerApi();
GAclEntry body = new GAclEntry(); // GAclEntry | 
try {
    Integer result = apiInstance.findAlias(body);
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

**Integer**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findAliasesByAclGrantedUniqueId"></a>
# **findAliasesByAclGrantedUniqueId**
> List&lt;Integer&gt; findAliasesByAclGrantedUniqueId(uniqueId)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.AclAliasesClusterControllerApi;


AclAliasesClusterControllerApi apiInstance = new AclAliasesClusterControllerApi();
String uniqueId = "uniqueId_example"; // String | 
try {
    List<Integer> result = apiInstance.findAliasesByAclGrantedUniqueId(uniqueId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AclAliasesClusterControllerApi#findAliasesByAclGrantedUniqueId");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **uniqueId** | **String**|  |

### Return type

**List&lt;Integer&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findAliasesByAclGrantedUniqueIdAndAclGrantType"></a>
# **findAliasesByAclGrantedUniqueIdAndAclGrantType**
> List&lt;Integer&gt; findAliasesByAclGrantedUniqueIdAndAclGrantType(uniqueId, grantType)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.AclAliasesClusterControllerApi;


AclAliasesClusterControllerApi apiInstance = new AclAliasesClusterControllerApi();
String uniqueId = "uniqueId_example"; // String | 
String grantType = "grantType_example"; // String | 
try {
    List<Integer> result = apiInstance.findAliasesByAclGrantedUniqueIdAndAclGrantType(uniqueId, grantType);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AclAliasesClusterControllerApi#findAliasesByAclGrantedUniqueIdAndAclGrantType");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **uniqueId** | **String**|  |
 **grantType** | **String**|  | [enum: READ, WRITE, EXECUTE]

### Return type

**List&lt;Integer&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findAliasesByAclGrantedUniqueIdIn"></a>
# **findAliasesByAclGrantedUniqueIdIn**
> List&lt;Integer&gt; findAliasesByAclGrantedUniqueIdIn(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.AclAliasesClusterControllerApi;


AclAliasesClusterControllerApi apiInstance = new AclAliasesClusterControllerApi();
List<String> body = Arrays.asList("body_example"); // List<String> | 
try {
    List<Integer> result = apiInstance.findAliasesByAclGrantedUniqueIdIn(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AclAliasesClusterControllerApi#findAliasesByAclGrantedUniqueIdIn");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**List&lt;String&gt;**](String.md)|  |

### Return type

**List&lt;Integer&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findAliasesByAclGrantedUniqueIdInAndAclGrantType"></a>
# **findAliasesByAclGrantedUniqueIdInAndAclGrantType**
> List&lt;Integer&gt; findAliasesByAclGrantedUniqueIdInAndAclGrantType(body, grantType)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.AclAliasesClusterControllerApi;


AclAliasesClusterControllerApi apiInstance = new AclAliasesClusterControllerApi();
List<String> body = Arrays.asList("body_example"); // List<String> | 
String grantType = "grantType_example"; // String | 
try {
    List<Integer> result = apiInstance.findAliasesByAclGrantedUniqueIdInAndAclGrantType(body, grantType);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AclAliasesClusterControllerApi#findAliasesByAclGrantedUniqueIdInAndAclGrantType");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**List&lt;String&gt;**](String.md)|  |
 **grantType** | **String**|  | [enum: READ, WRITE, EXECUTE]

### Return type

**List&lt;Integer&gt;**

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
Integer alias = 56; // Integer | 
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
 **alias** | **Integer**|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

