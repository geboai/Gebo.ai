# GeneratedUserApiKeyControllerApi

All URIs are relative to *http://localhost:12999*

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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeneratedUserApiKeyControllerApi;


GeneratedUserApiKeyControllerApi apiInstance = new GeneratedUserApiKeyControllerApi();
String code = "code_example"; // String | 
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
 **code** | **String**|  |

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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeneratedUserApiKeyControllerApi;


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
> PagedModelGeneratedApiKeyInfo getUserGeneratedApiKeyPagedList(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeneratedUserApiKeyControllerApi;


GeneratedUserApiKeyControllerApi apiInstance = new GeneratedUserApiKeyControllerApi();
DataPage body = new DataPage(); // DataPage | 
try {
    PagedModelGeneratedApiKeyInfo result = apiInstance.getUserGeneratedApiKeyPagedList(body);
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

[**PagedModelGeneratedApiKeyInfo**](PagedModelGeneratedApiKeyInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="isUserGeneratedApiKeyGenerationAllowed"></a>
# **isUserGeneratedApiKeyGenerationAllowed**
> Boolean isUserGeneratedApiKeyGenerationAllowed()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeneratedUserApiKeyControllerApi;


GeneratedUserApiKeyControllerApi apiInstance = new GeneratedUserApiKeyControllerApi();
try {
    Boolean result = apiInstance.isUserGeneratedApiKeyGenerationAllowed();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeneratedUserApiKeyControllerApi#isUserGeneratedApiKeyGenerationAllowed");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Boolean**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

