# GeboAdminPromptUseInfoControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**findAll**](GeboAdminPromptUseInfoControllerApi.md#findAll) | **GET** /api/admin/GeboAdminPromptUseController/findAll | 
[**findByCode**](GeboAdminPromptUseInfoControllerApi.md#findByCode) | **GET** /api/admin/GeboAdminPromptUseController/findByCode | 
[**findByModule**](GeboAdminPromptUseInfoControllerApi.md#findByModule) | **GET** /api/admin/GeboAdminPromptUseController/findByModule | 

<a name="findAll"></a>
# **findAll**
> List&lt;GPromptUseInfo&gt; findAll()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAdminPromptUseInfoControllerApi;


GeboAdminPromptUseInfoControllerApi apiInstance = new GeboAdminPromptUseInfoControllerApi();
try {
    List<GPromptUseInfo> result = apiInstance.findAll();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminPromptUseInfoControllerApi#findAll");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GPromptUseInfo&gt;**](GPromptUseInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findByCode"></a>
# **findByCode**
> GPromptUseInfo findByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAdminPromptUseInfoControllerApi;


GeboAdminPromptUseInfoControllerApi apiInstance = new GeboAdminPromptUseInfoControllerApi();
String code = "code_example"; // String | 
try {
    GPromptUseInfo result = apiInstance.findByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminPromptUseInfoControllerApi#findByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GPromptUseInfo**](GPromptUseInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findByModule"></a>
# **findByModule**
> List&lt;GPromptUseInfo&gt; findByModule(module)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAdminPromptUseInfoControllerApi;


GeboAdminPromptUseInfoControllerApi apiInstance = new GeboAdminPromptUseInfoControllerApi();
String module = "module_example"; // String | 
try {
    List<GPromptUseInfo> result = apiInstance.findByModule(module);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminPromptUseInfoControllerApi#findByModule");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **module** | **String**|  |

### Return type

[**List&lt;GPromptUseInfo&gt;**](GPromptUseInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

