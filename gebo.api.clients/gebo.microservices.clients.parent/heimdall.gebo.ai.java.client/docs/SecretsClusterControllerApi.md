# SecretsClusterControllerApi

All URIs are relative to *http://localhost:13018/heimdall*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteSecret**](SecretsClusterControllerApi.md#deleteSecret) | **DELETE** /api/cluster/SecretsController/deleteSecret | 
[**getAllSecretsId**](SecretsClusterControllerApi.md#getAllSecretsId) | **GET** /api/cluster/SecretsController/getAllSecretsId | 
[**getSecretContentById**](SecretsClusterControllerApi.md#getSecretContentById) | **GET** /api/cluster/SecretsController/getSecretContentById | 
[**getSecretInfoByContextCode**](SecretsClusterControllerApi.md#getSecretInfoByContextCode) | **GET** /api/cluster/SecretsController/getSecretInfoByContextCode | 
[**getSecretInfoById**](SecretsClusterControllerApi.md#getSecretInfoById) | **GET** /api/cluster/SecretsController/getSecretInfoById | 
[**storeSecret**](SecretsClusterControllerApi.md#storeSecret) | **POST** /api/cluster/SecretsController/storeSecret | 
[**updateSecret**](SecretsClusterControllerApi.md#updateSecret) | **POST** /api/cluster/SecretsController/updateSecret | 

<a name="deleteSecret"></a>
# **deleteSecret**
> deleteSecret(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.SecretsClusterControllerApi;


SecretsClusterControllerApi apiInstance = new SecretsClusterControllerApi();
String code = "code_example"; // String | 
try {
    apiInstance.deleteSecret(code);
} catch (ApiException e) {
    System.err.println("Exception when calling SecretsClusterControllerApi#deleteSecret");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="getAllSecretsId"></a>
# **getAllSecretsId**
> List&lt;String&gt; getAllSecretsId()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.SecretsClusterControllerApi;


SecretsClusterControllerApi apiInstance = new SecretsClusterControllerApi();
try {
    List<String> result = apiInstance.getAllSecretsId();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SecretsClusterControllerApi#getAllSecretsId");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**List&lt;String&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSecretContentById"></a>
# **getSecretContentById**
> GeboSecretContentEnvelope getSecretContentById(id)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.SecretsClusterControllerApi;


SecretsClusterControllerApi apiInstance = new SecretsClusterControllerApi();
String id = "id_example"; // String | 
try {
    GeboSecretContentEnvelope result = apiInstance.getSecretContentById(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SecretsClusterControllerApi#getSecretContentById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |

### Return type

[**GeboSecretContentEnvelope**](GeboSecretContentEnvelope.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSecretInfoByContextCode"></a>
# **getSecretInfoByContextCode**
> List&lt;SecretInfo&gt; getSecretInfoByContextCode(contextCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.SecretsClusterControllerApi;


SecretsClusterControllerApi apiInstance = new SecretsClusterControllerApi();
String contextCode = "contextCode_example"; // String | 
try {
    List<SecretInfo> result = apiInstance.getSecretInfoByContextCode(contextCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SecretsClusterControllerApi#getSecretInfoByContextCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **contextCode** | **String**|  |

### Return type

[**List&lt;SecretInfo&gt;**](SecretInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSecretInfoById"></a>
# **getSecretInfoById**
> SecretInfo getSecretInfoById(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.SecretsClusterControllerApi;


SecretsClusterControllerApi apiInstance = new SecretsClusterControllerApi();
String code = "code_example"; // String | 
try {
    SecretInfo result = apiInstance.getSecretInfoById(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SecretsClusterControllerApi#getSecretInfoById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**SecretInfo**](SecretInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="storeSecret"></a>
# **storeSecret**
> String storeSecret(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.SecretsClusterControllerApi;


SecretsClusterControllerApi apiInstance = new SecretsClusterControllerApi();
GeboSecretStoreRequest body = new GeboSecretStoreRequest(); // GeboSecretStoreRequest | 
try {
    String result = apiInstance.storeSecret(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SecretsClusterControllerApi#storeSecret");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GeboSecretStoreRequest**](GeboSecretStoreRequest.md)|  |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: text/plain

<a name="updateSecret"></a>
# **updateSecret**
> updateSecret(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.SecretsClusterControllerApi;


SecretsClusterControllerApi apiInstance = new SecretsClusterControllerApi();
GeboSecretStoreRequest body = new GeboSecretStoreRequest(); // GeboSecretStoreRequest | 
try {
    apiInstance.updateSecret(body);
} catch (ApiException e) {
    System.err.println("Exception when calling SecretsClusterControllerApi#updateSecret");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GeboSecretStoreRequest**](GeboSecretStoreRequest.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

