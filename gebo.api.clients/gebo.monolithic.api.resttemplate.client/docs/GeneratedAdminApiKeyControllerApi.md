# GeneratedAdminApiKeyControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteAdminGeneratedApiKey**](GeneratedAdminApiKeyControllerApi.md#deleteAdminGeneratedApiKey) | **POST** /api/admin/GeneratedAdminApiKeyController/deleteAdminGeneratedApiKey | 
[**generateAdminGeneratedApiKey**](GeneratedAdminApiKeyControllerApi.md#generateAdminGeneratedApiKey) | **POST** /api/admin/GeneratedAdminApiKeyController/generateAdminGeneratedApiKey | 
[**getAdminGeneratedApiKeyPagedList**](GeneratedAdminApiKeyControllerApi.md#getAdminGeneratedApiKeyPagedList) | **POST** /api/admin/GeneratedAdminApiKeyController/getAdminGeneratedApiKeyPagedList | 
[**isAdminGeneratedApiKeyGenerationAllowed**](GeneratedAdminApiKeyControllerApi.md#isAdminGeneratedApiKeyGenerationAllowed) | **GET** /api/admin/GeneratedAdminApiKeyController/isAdminGeneratedApiKeyGenerationAllowed | 

<a name="deleteAdminGeneratedApiKey"></a>
# **deleteAdminGeneratedApiKey**
> deleteAdminGeneratedApiKey(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeneratedAdminApiKeyControllerApi;


GeneratedAdminApiKeyControllerApi apiInstance = new GeneratedAdminApiKeyControllerApi();
String code = "code_example"; // String | 
try {
    apiInstance.deleteAdminGeneratedApiKey(code);
} catch (ApiException e) {
    System.err.println("Exception when calling GeneratedAdminApiKeyControllerApi#deleteAdminGeneratedApiKey");
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

<a name="generateAdminGeneratedApiKey"></a>
# **generateAdminGeneratedApiKey**
> GeneratedApiKey generateAdminGeneratedApiKey(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeneratedAdminApiKeyControllerApi;


GeneratedAdminApiKeyControllerApi apiInstance = new GeneratedAdminApiKeyControllerApi();
GenerateAdminGeneratedApiKeyParam body = new GenerateAdminGeneratedApiKeyParam(); // GenerateAdminGeneratedApiKeyParam | 
try {
    GeneratedApiKey result = apiInstance.generateAdminGeneratedApiKey(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeneratedAdminApiKeyControllerApi#generateAdminGeneratedApiKey");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenerateAdminGeneratedApiKeyParam**](GenerateAdminGeneratedApiKeyParam.md)|  |

### Return type

[**GeneratedApiKey**](GeneratedApiKey.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getAdminGeneratedApiKeyPagedList"></a>
# **getAdminGeneratedApiKeyPagedList**
> PagedModelGeneratedApiKeyInfo getAdminGeneratedApiKeyPagedList(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeneratedAdminApiKeyControllerApi;


GeneratedAdminApiKeyControllerApi apiInstance = new GeneratedAdminApiKeyControllerApi();
DataPage body = new DataPage(); // DataPage | 
try {
    PagedModelGeneratedApiKeyInfo result = apiInstance.getAdminGeneratedApiKeyPagedList(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeneratedAdminApiKeyControllerApi#getAdminGeneratedApiKeyPagedList");
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

<a name="isAdminGeneratedApiKeyGenerationAllowed"></a>
# **isAdminGeneratedApiKeyGenerationAllowed**
> Boolean isAdminGeneratedApiKeyGenerationAllowed()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeneratedAdminApiKeyControllerApi;


GeneratedAdminApiKeyControllerApi apiInstance = new GeneratedAdminApiKeyControllerApi();
try {
    Boolean result = apiInstance.isAdminGeneratedApiKeyGenerationAllowed();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeneratedAdminApiKeyControllerApi#isAdminGeneratedApiKeyGenerationAllowed");
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

