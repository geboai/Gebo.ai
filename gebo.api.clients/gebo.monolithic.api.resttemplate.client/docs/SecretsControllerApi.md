# SecretsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createCustomSecret**](SecretsControllerApi.md#createCustomSecret) | **POST** /api/admin/SecretsController/createCustomSecret | 
[**createGoogleJsonCredentialsSecret**](SecretsControllerApi.md#createGoogleJsonCredentialsSecret) | **POST** /api/admin/SecretsController/createGoogleJsonCredentialsSecret | 
[**createGoogleOauth2Secret**](SecretsControllerApi.md#createGoogleOauth2Secret) | **POST** /api/admin/SecretsController/createGoogleOauth2Secret | 
[**createOauth2StandardSecret**](SecretsControllerApi.md#createOauth2StandardSecret) | **POST** /api/admin/SecretsController/createOauth2StandardSecret | 
[**createSshKeySecret**](SecretsControllerApi.md#createSshKeySecret) | **POST** /api/admin/SecretsController/createSshKeySecret | 
[**createTokenSecret**](SecretsControllerApi.md#createTokenSecret) | **POST** /api/admin/SecretsController/createTokenSecret | 
[**createUsernamePasswordSecret**](SecretsControllerApi.md#createUsernamePasswordSecret) | **POST** /api/admin/SecretsController/createUsernamePasswordSecret | 
[**getSecretsByContextCode**](SecretsControllerApi.md#getSecretsByContextCode) | **GET** /api/admin/SecretsController/getSecretsByContextCode | 

<a name="createCustomSecret"></a>
# **createCustomSecret**
> SecretInfo createCustomSecret(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SecretsControllerApi;


SecretsControllerApi apiInstance = new SecretsControllerApi();
SecretWrapperGeboCustomSecretContent body = new SecretWrapperGeboCustomSecretContent(); // SecretWrapperGeboCustomSecretContent | 
try {
    SecretInfo result = apiInstance.createCustomSecret(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SecretsControllerApi#createCustomSecret");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SecretWrapperGeboCustomSecretContent**](SecretWrapperGeboCustomSecretContent.md)|  |

### Return type

[**SecretInfo**](SecretInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createGoogleJsonCredentialsSecret"></a>
# **createGoogleJsonCredentialsSecret**
> SecretInfo createGoogleJsonCredentialsSecret(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SecretsControllerApi;


SecretsControllerApi apiInstance = new SecretsControllerApi();
SecretWrapperGeboGoogleJsonSecretContent body = new SecretWrapperGeboGoogleJsonSecretContent(); // SecretWrapperGeboGoogleJsonSecretContent | 
try {
    SecretInfo result = apiInstance.createGoogleJsonCredentialsSecret(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SecretsControllerApi#createGoogleJsonCredentialsSecret");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SecretWrapperGeboGoogleJsonSecretContent**](SecretWrapperGeboGoogleJsonSecretContent.md)|  |

### Return type

[**SecretInfo**](SecretInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createGoogleOauth2Secret"></a>
# **createGoogleOauth2Secret**
> SecretInfo createGoogleOauth2Secret(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SecretsControllerApi;


SecretsControllerApi apiInstance = new SecretsControllerApi();
SecretWrapperGeboGoogleOauth2SecretContent body = new SecretWrapperGeboGoogleOauth2SecretContent(); // SecretWrapperGeboGoogleOauth2SecretContent | 
try {
    SecretInfo result = apiInstance.createGoogleOauth2Secret(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SecretsControllerApi#createGoogleOauth2Secret");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SecretWrapperGeboGoogleOauth2SecretContent**](SecretWrapperGeboGoogleOauth2SecretContent.md)|  |

### Return type

[**SecretInfo**](SecretInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createOauth2StandardSecret"></a>
# **createOauth2StandardSecret**
> SecretInfo createOauth2StandardSecret(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SecretsControllerApi;


SecretsControllerApi apiInstance = new SecretsControllerApi();
SecretWrapperGeboOauth2SecretContent body = new SecretWrapperGeboOauth2SecretContent(); // SecretWrapperGeboOauth2SecretContent | 
try {
    SecretInfo result = apiInstance.createOauth2StandardSecret(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SecretsControllerApi#createOauth2StandardSecret");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SecretWrapperGeboOauth2SecretContent**](SecretWrapperGeboOauth2SecretContent.md)|  |

### Return type

[**SecretInfo**](SecretInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createSshKeySecret"></a>
# **createSshKeySecret**
> SecretInfo createSshKeySecret(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SecretsControllerApi;


SecretsControllerApi apiInstance = new SecretsControllerApi();
SecretWrapperGeboSshKeySecretContent body = new SecretWrapperGeboSshKeySecretContent(); // SecretWrapperGeboSshKeySecretContent | 
try {
    SecretInfo result = apiInstance.createSshKeySecret(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SecretsControllerApi#createSshKeySecret");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SecretWrapperGeboSshKeySecretContent**](SecretWrapperGeboSshKeySecretContent.md)|  |

### Return type

[**SecretInfo**](SecretInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createTokenSecret"></a>
# **createTokenSecret**
> SecretInfo createTokenSecret(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SecretsControllerApi;


SecretsControllerApi apiInstance = new SecretsControllerApi();
SecretWrapperGeboTokenContent body = new SecretWrapperGeboTokenContent(); // SecretWrapperGeboTokenContent | 
try {
    SecretInfo result = apiInstance.createTokenSecret(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SecretsControllerApi#createTokenSecret");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SecretWrapperGeboTokenContent**](SecretWrapperGeboTokenContent.md)|  |

### Return type

[**SecretInfo**](SecretInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createUsernamePasswordSecret"></a>
# **createUsernamePasswordSecret**
> SecretInfo createUsernamePasswordSecret(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SecretsControllerApi;


SecretsControllerApi apiInstance = new SecretsControllerApi();
SecretWrapperGeboUsernamePasswordContent body = new SecretWrapperGeboUsernamePasswordContent(); // SecretWrapperGeboUsernamePasswordContent | 
try {
    SecretInfo result = apiInstance.createUsernamePasswordSecret(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SecretsControllerApi#createUsernamePasswordSecret");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SecretWrapperGeboUsernamePasswordContent**](SecretWrapperGeboUsernamePasswordContent.md)|  |

### Return type

[**SecretInfo**](SecretInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getSecretsByContextCode"></a>
# **getSecretsByContextCode**
> List&lt;SecretInfo&gt; getSecretsByContextCode(context)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SecretsControllerApi;


SecretsControllerApi apiInstance = new SecretsControllerApi();
String context = "context_example"; // String | 
try {
    List<SecretInfo> result = apiInstance.getSecretsByContextCode(context);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SecretsControllerApi#getSecretsByContextCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **context** | **String**|  |

### Return type

[**List&lt;SecretInfo&gt;**](SecretInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

