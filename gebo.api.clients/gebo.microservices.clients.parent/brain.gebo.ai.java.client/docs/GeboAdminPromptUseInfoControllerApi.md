# GeboAdminPromptUseInfoControllerApi

All URIs are relative to *http://localhost:13001*

Method | HTTP request | Description
------------- | ------------- | -------------
[**findAll**](GeboAdminPromptUseInfoControllerApi.md#findAll) | **GET** /api/admin/GeboAdminPromptUseController/findAll | 
[**findByCode**](GeboAdminPromptUseInfoControllerApi.md#findByCode) | **GET** /api/admin/GeboAdminPromptUseController/findByCode | 
[**findByModule**](GeboAdminPromptUseInfoControllerApi.md#findByModule) | **GET** /api/admin/GeboAdminPromptUseController/findByModule | 

<a name="findAll"></a>
# **findAll**
> Object findAll()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboAdminPromptUseInfoControllerApi;


GeboAdminPromptUseInfoControllerApi apiInstance = new GeboAdminPromptUseInfoControllerApi();
try {
    Object result = apiInstance.findAll();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminPromptUseInfoControllerApi#findAll");
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

<a name="findByCode"></a>
# **findByCode**
> GPromptUseInfo findByCode(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboAdminPromptUseInfoControllerApi;


GeboAdminPromptUseInfoControllerApi apiInstance = new GeboAdminPromptUseInfoControllerApi();
Object code = null; // Object | 
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
 **code** | [**Object**](.md)|  |

### Return type

[**GPromptUseInfo**](GPromptUseInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findByModule"></a>
# **findByModule**
> Object findByModule(module)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboAdminPromptUseInfoControllerApi;


GeboAdminPromptUseInfoControllerApi apiInstance = new GeboAdminPromptUseInfoControllerApi();
Object module = null; // Object | 
try {
    Object result = apiInstance.findByModule(module);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminPromptUseInfoControllerApi#findByModule");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **module** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

