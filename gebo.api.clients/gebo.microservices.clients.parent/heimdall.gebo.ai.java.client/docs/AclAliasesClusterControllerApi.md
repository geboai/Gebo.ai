# AclAliasesClusterControllerApi

All URIs are relative to *http://localhost:13018/heimdall*

Method | HTTP request | Description
------------- | ------------- | -------------
[**infrastructureAddAcl**](AclAliasesClusterControllerApi.md#infrastructureAddAcl) | **POST** /api/cluster/AclController/addAcl | 
[**infrastructureFindAcl**](AclAliasesClusterControllerApi.md#infrastructureFindAcl) | **GET** /api/cluster/AclController/findAcl | 
[**infrastructureFindAlias**](AclAliasesClusterControllerApi.md#infrastructureFindAlias) | **POST** /api/cluster/AclController/findAlias | 
[**infrastructureFindAliasesByAclGrantedUniqueId**](AclAliasesClusterControllerApi.md#infrastructureFindAliasesByAclGrantedUniqueId) | **GET** /api/cluster/AclController/findAliasesByAclGrantedUniqueId | 
[**infrastructureFindAliasesByAclGrantedUniqueIdAndAclGrantType**](AclAliasesClusterControllerApi.md#infrastructureFindAliasesByAclGrantedUniqueIdAndAclGrantType) | **GET** /api/cluster/AclController/findAliasesByAclGrantedUniqueIdAndAclGrantType | 
[**infrastructureFindAliasesByAclGrantedUniqueIdIn**](AclAliasesClusterControllerApi.md#infrastructureFindAliasesByAclGrantedUniqueIdIn) | **POST** /api/cluster/AclController/findAliasesByAclGrantedUniqueIdIn | 
[**infrastructureFindAliasesByAclGrantedUniqueIdInAndAclGrantType**](AclAliasesClusterControllerApi.md#infrastructureFindAliasesByAclGrantedUniqueIdInAndAclGrantType) | **POST** /api/cluster/AclController/findAliasesByAclGrantedUniqueIdInAndAclGrantType | 
[**infrastructureRemoveAcl**](AclAliasesClusterControllerApi.md#infrastructureRemoveAcl) | **DELETE** /api/cluster/AclController/removeAcl | 

<a name="infrastructureAddAcl"></a>
# **infrastructureAddAcl**
> Integer infrastructureAddAcl(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.AclAliasesClusterControllerApi;


AclAliasesClusterControllerApi apiInstance = new AclAliasesClusterControllerApi();
GAclEntry body = new GAclEntry(); // GAclEntry | 
try {
    Integer result = apiInstance.infrastructureAddAcl(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AclAliasesClusterControllerApi#infrastructureAddAcl");
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

<a name="infrastructureFindAcl"></a>
# **infrastructureFindAcl**
> GAclEntry infrastructureFindAcl(alias)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.AclAliasesClusterControllerApi;


AclAliasesClusterControllerApi apiInstance = new AclAliasesClusterControllerApi();
Integer alias = 56; // Integer | 
try {
    GAclEntry result = apiInstance.infrastructureFindAcl(alias);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AclAliasesClusterControllerApi#infrastructureFindAcl");
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

<a name="infrastructureFindAlias"></a>
# **infrastructureFindAlias**
> Integer infrastructureFindAlias(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.AclAliasesClusterControllerApi;


AclAliasesClusterControllerApi apiInstance = new AclAliasesClusterControllerApi();
GAclEntry body = new GAclEntry(); // GAclEntry | 
try {
    Integer result = apiInstance.infrastructureFindAlias(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AclAliasesClusterControllerApi#infrastructureFindAlias");
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

<a name="infrastructureFindAliasesByAclGrantedUniqueId"></a>
# **infrastructureFindAliasesByAclGrantedUniqueId**
> List&lt;Integer&gt; infrastructureFindAliasesByAclGrantedUniqueId(uniqueId)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.AclAliasesClusterControllerApi;


AclAliasesClusterControllerApi apiInstance = new AclAliasesClusterControllerApi();
String uniqueId = "uniqueId_example"; // String | 
try {
    List<Integer> result = apiInstance.infrastructureFindAliasesByAclGrantedUniqueId(uniqueId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AclAliasesClusterControllerApi#infrastructureFindAliasesByAclGrantedUniqueId");
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

<a name="infrastructureFindAliasesByAclGrantedUniqueIdAndAclGrantType"></a>
# **infrastructureFindAliasesByAclGrantedUniqueIdAndAclGrantType**
> List&lt;Integer&gt; infrastructureFindAliasesByAclGrantedUniqueIdAndAclGrantType(uniqueId, grantType)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.AclAliasesClusterControllerApi;


AclAliasesClusterControllerApi apiInstance = new AclAliasesClusterControllerApi();
String uniqueId = "uniqueId_example"; // String | 
String grantType = "grantType_example"; // String | 
try {
    List<Integer> result = apiInstance.infrastructureFindAliasesByAclGrantedUniqueIdAndAclGrantType(uniqueId, grantType);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AclAliasesClusterControllerApi#infrastructureFindAliasesByAclGrantedUniqueIdAndAclGrantType");
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

<a name="infrastructureFindAliasesByAclGrantedUniqueIdIn"></a>
# **infrastructureFindAliasesByAclGrantedUniqueIdIn**
> List&lt;Integer&gt; infrastructureFindAliasesByAclGrantedUniqueIdIn(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.AclAliasesClusterControllerApi;


AclAliasesClusterControllerApi apiInstance = new AclAliasesClusterControllerApi();
List<String> body = Arrays.asList("body_example"); // List<String> | 
try {
    List<Integer> result = apiInstance.infrastructureFindAliasesByAclGrantedUniqueIdIn(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AclAliasesClusterControllerApi#infrastructureFindAliasesByAclGrantedUniqueIdIn");
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

<a name="infrastructureFindAliasesByAclGrantedUniqueIdInAndAclGrantType"></a>
# **infrastructureFindAliasesByAclGrantedUniqueIdInAndAclGrantType**
> List&lt;Integer&gt; infrastructureFindAliasesByAclGrantedUniqueIdInAndAclGrantType(body, grantType)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.AclAliasesClusterControllerApi;


AclAliasesClusterControllerApi apiInstance = new AclAliasesClusterControllerApi();
List<String> body = Arrays.asList("body_example"); // List<String> | 
String grantType = "grantType_example"; // String | 
try {
    List<Integer> result = apiInstance.infrastructureFindAliasesByAclGrantedUniqueIdInAndAclGrantType(body, grantType);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AclAliasesClusterControllerApi#infrastructureFindAliasesByAclGrantedUniqueIdInAndAclGrantType");
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

<a name="infrastructureRemoveAcl"></a>
# **infrastructureRemoveAcl**
> infrastructureRemoveAcl(alias)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.AclAliasesClusterControllerApi;


AclAliasesClusterControllerApi apiInstance = new AclAliasesClusterControllerApi();
Integer alias = 56; // Integer | 
try {
    apiInstance.infrastructureRemoveAcl(alias);
} catch (ApiException e) {
    System.err.println("Exception when calling AclAliasesClusterControllerApi#infrastructureRemoveAcl");
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

