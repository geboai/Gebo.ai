# SecretsClusterControllerApi

All URIs are relative to *http://localhost:13018/heimdall*

Method | HTTP request | Description
------------- | ------------- | -------------
[**infrastructureDeleteSecret**](SecretsClusterControllerApi.md#infrastructureDeleteSecret) | **DELETE** /api/cluster/SecretsController/deleteSecret | 
[**infrastructureGetAllSecretsId**](SecretsClusterControllerApi.md#infrastructureGetAllSecretsId) | **GET** /api/cluster/SecretsController/getAllSecretsId | 
[**infrastructureGetSecretContentById**](SecretsClusterControllerApi.md#infrastructureGetSecretContentById) | **GET** /api/cluster/SecretsController/getSecretContentById | 
[**infrastructureGetSecretInfoByContextCode**](SecretsClusterControllerApi.md#infrastructureGetSecretInfoByContextCode) | **GET** /api/cluster/SecretsController/getSecretInfoByContextCode | 
[**infrastructureGetSecretInfoById**](SecretsClusterControllerApi.md#infrastructureGetSecretInfoById) | **GET** /api/cluster/SecretsController/getSecretInfoById | 
[**infrastructureStoreSecret**](SecretsClusterControllerApi.md#infrastructureStoreSecret) | **POST** /api/cluster/SecretsController/storeSecret | 
[**infrastructureUpdateSecret**](SecretsClusterControllerApi.md#infrastructureUpdateSecret) | **POST** /api/cluster/SecretsController/updateSecret | 

<a name="infrastructureDeleteSecret"></a>
# **infrastructureDeleteSecret**
> infrastructureDeleteSecret(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.SecretsClusterControllerApi;


SecretsClusterControllerApi apiInstance = new SecretsClusterControllerApi();
String code = "code_example"; // String | 
try {
    apiInstance.infrastructureDeleteSecret(code);
} catch (ApiException e) {
    System.err.println("Exception when calling SecretsClusterControllerApi#infrastructureDeleteSecret");
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

<a name="infrastructureGetAllSecretsId"></a>
# **infrastructureGetAllSecretsId**
> List&lt;String&gt; infrastructureGetAllSecretsId()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.SecretsClusterControllerApi;


SecretsClusterControllerApi apiInstance = new SecretsClusterControllerApi();
try {
    List<String> result = apiInstance.infrastructureGetAllSecretsId();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SecretsClusterControllerApi#infrastructureGetAllSecretsId");
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

<a name="infrastructureGetSecretContentById"></a>
# **infrastructureGetSecretContentById**
> GeboSecretContentEnvelope infrastructureGetSecretContentById(id)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.SecretsClusterControllerApi;


SecretsClusterControllerApi apiInstance = new SecretsClusterControllerApi();
String id = "id_example"; // String | 
try {
    GeboSecretContentEnvelope result = apiInstance.infrastructureGetSecretContentById(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SecretsClusterControllerApi#infrastructureGetSecretContentById");
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

<a name="infrastructureGetSecretInfoByContextCode"></a>
# **infrastructureGetSecretInfoByContextCode**
> List&lt;SecretInfo&gt; infrastructureGetSecretInfoByContextCode(contextCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.SecretsClusterControllerApi;


SecretsClusterControllerApi apiInstance = new SecretsClusterControllerApi();
String contextCode = "contextCode_example"; // String | 
try {
    List<SecretInfo> result = apiInstance.infrastructureGetSecretInfoByContextCode(contextCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SecretsClusterControllerApi#infrastructureGetSecretInfoByContextCode");
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

<a name="infrastructureGetSecretInfoById"></a>
# **infrastructureGetSecretInfoById**
> SecretInfo infrastructureGetSecretInfoById(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.SecretsClusterControllerApi;


SecretsClusterControllerApi apiInstance = new SecretsClusterControllerApi();
String code = "code_example"; // String | 
try {
    SecretInfo result = apiInstance.infrastructureGetSecretInfoById(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SecretsClusterControllerApi#infrastructureGetSecretInfoById");
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

<a name="infrastructureStoreSecret"></a>
# **infrastructureStoreSecret**
> String infrastructureStoreSecret(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.SecretsClusterControllerApi;


SecretsClusterControllerApi apiInstance = new SecretsClusterControllerApi();
GeboSecretStoreRequest body = new GeboSecretStoreRequest(); // GeboSecretStoreRequest | 
try {
    String result = apiInstance.infrastructureStoreSecret(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SecretsClusterControllerApi#infrastructureStoreSecret");
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

<a name="infrastructureUpdateSecret"></a>
# **infrastructureUpdateSecret**
> infrastructureUpdateSecret(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.SecretsClusterControllerApi;


SecretsClusterControllerApi apiInstance = new SecretsClusterControllerApi();
GeboSecretStoreRequest body = new GeboSecretStoreRequest(); // GeboSecretStoreRequest | 
try {
    apiInstance.infrastructureUpdateSecret(body);
} catch (ApiException e) {
    System.err.println("Exception when calling SecretsClusterControllerApi#infrastructureUpdateSecret");
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

