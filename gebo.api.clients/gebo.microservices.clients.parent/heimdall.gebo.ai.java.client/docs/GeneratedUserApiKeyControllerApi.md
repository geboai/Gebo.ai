# GeneratedUserApiKeyControllerApi

All URIs are relative to *http://localhost:13018*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteUserGeneratedApiKey**](GeneratedUserApiKeyControllerApi.md#deleteUserGeneratedApiKey) | **POST** /api/users/GeneratedUserApiKeyController/deleteUserGeneratedApiKey | 
[**generateUserGeneratedApiKey**](GeneratedUserApiKeyControllerApi.md#generateUserGeneratedApiKey) | **POST** /api/users/GeneratedUserApiKeyController/generateUserGeneratedApiKey | 
[**getUserGeneratedApiKeyPagedList**](GeneratedUserApiKeyControllerApi.md#getUserGeneratedApiKeyPagedList) | **POST** /api/users/GeneratedUserApiKeyController/getUserGeneratedApiKeyPagedList | 
[**isUserGeneratedApiKeyGenerationAllowed**](GeneratedUserApiKeyControllerApi.md#isUserGeneratedApiKeyGenerationAllowed) | **GET** /api/users/GeneratedUserApiKeyController/isUserGeneratedApiKeyGenerationAllowed | 

<a name="deleteUserGeneratedApiKey"></a>
# **deleteUserGeneratedApiKey**
> deleteUserGeneratedApiKey(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.GeneratedUserApiKeyControllerApi;


GeneratedUserApiKeyControllerApi apiInstance = new GeneratedUserApiKeyControllerApi();
Object code = null; // Object | 
try {
    apiInstance.deleteUserGeneratedApiKey(code);
} catch (ApiException e) {
    System.err.println("Exception when calling GeneratedUserApiKeyControllerApi#deleteUserGeneratedApiKey");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | [**Object**](.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="generateUserGeneratedApiKey"></a>
# **generateUserGeneratedApiKey**
> GeneratedApiKey generateUserGeneratedApiKey(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.GeneratedUserApiKeyControllerApi;


GeneratedUserApiKeyControllerApi apiInstance = new GeneratedUserApiKeyControllerApi();
GenerateUserGeneratedApiKeyParam body = new GenerateUserGeneratedApiKeyParam(); // GenerateUserGeneratedApiKeyParam | 
try {
    GeneratedApiKey result = apiInstance.generateUserGeneratedApiKey(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeneratedUserApiKeyControllerApi#generateUserGeneratedApiKey");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenerateUserGeneratedApiKeyParam**](GenerateUserGeneratedApiKeyParam.md)|  |

### Return type

[**GeneratedApiKey**](GeneratedApiKey.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUserGeneratedApiKeyPagedList"></a>
# **getUserGeneratedApiKeyPagedList**
> PageGeneratedApiKeyInfo getUserGeneratedApiKeyPagedList(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.GeneratedUserApiKeyControllerApi;


GeneratedUserApiKeyControllerApi apiInstance = new GeneratedUserApiKeyControllerApi();
DataPage body = new DataPage(); // DataPage | 
try {
    PageGeneratedApiKeyInfo result = apiInstance.getUserGeneratedApiKeyPagedList(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeneratedUserApiKeyControllerApi#getUserGeneratedApiKeyPagedList");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**DataPage**](DataPage.md)|  |

### Return type

[**PageGeneratedApiKeyInfo**](PageGeneratedApiKeyInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="isUserGeneratedApiKeyGenerationAllowed"></a>
# **isUserGeneratedApiKeyGenerationAllowed**
> Object isUserGeneratedApiKeyGenerationAllowed()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.GeneratedUserApiKeyControllerApi;


GeneratedUserApiKeyControllerApi apiInstance = new GeneratedUserApiKeyControllerApi();
try {
    Object result = apiInstance.isUserGeneratedApiKeyGenerationAllowed();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeneratedUserApiKeyControllerApi#isUserGeneratedApiKeyGenerationAllowed");
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

