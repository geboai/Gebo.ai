# BraveSearchConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGBraveSearchApiCredentials**](BraveSearchConfigurationControllerApi.md#deleteGBraveSearchApiCredentials) | **POST** /api/admin/BraveSearchConfigurationController/deleteGBraveSearchApiCredentials | 
[**fastInsertBraveSearchApiCredentials**](BraveSearchConfigurationControllerApi.md#fastInsertBraveSearchApiCredentials) | **POST** /api/admin/BraveSearchConfigurationController/fastInsertBraveSearchApiCredentials | 
[**getBraveSearchApiCredentials**](BraveSearchConfigurationControllerApi.md#getBraveSearchApiCredentials) | **GET** /api/admin/BraveSearchConfigurationController/getBraveSearchApiCredentials | 
[**getBraveSearchStatus**](BraveSearchConfigurationControllerApi.md#getBraveSearchStatus) | **GET** /api/admin/BraveSearchConfigurationController/getBraveSearchStatus | 
[**insertGBraveSearchApiCredentials**](BraveSearchConfigurationControllerApi.md#insertGBraveSearchApiCredentials) | **POST** /api/admin/BraveSearchConfigurationController/insertGBraveSearchApiCredentials | 
[**searchGBraveSearchApiCredentialsByCode**](BraveSearchConfigurationControllerApi.md#searchGBraveSearchApiCredentialsByCode) | **GET** /api/admin/BraveSearchConfigurationController/searchGBraveSearchApiCredentialsByCode | 
[**updateGBraveSearchApiCredentials**](BraveSearchConfigurationControllerApi.md#updateGBraveSearchApiCredentials) | **POST** /api/admin/BraveSearchConfigurationController/updateGBraveSearchApiCredentials | 

<a name="deleteGBraveSearchApiCredentials"></a>
# **deleteGBraveSearchApiCredentials**
> deleteGBraveSearchApiCredentials(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BraveSearchConfigurationControllerApi;


BraveSearchConfigurationControllerApi apiInstance = new BraveSearchConfigurationControllerApi();
GBraveSearchApiCredentials body = new GBraveSearchApiCredentials(); // GBraveSearchApiCredentials | 
try {
    apiInstance.deleteGBraveSearchApiCredentials(body);
} catch (ApiException e) {
    System.err.println("Exception when calling BraveSearchConfigurationControllerApi#deleteGBraveSearchApiCredentials");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBraveSearchApiCredentials**](GBraveSearchApiCredentials.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="fastInsertBraveSearchApiCredentials"></a>
# **fastInsertBraveSearchApiCredentials**
> GBraveSearchApiCredentials fastInsertBraveSearchApiCredentials(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BraveSearchConfigurationControllerApi;


BraveSearchConfigurationControllerApi apiInstance = new BraveSearchConfigurationControllerApi();
BraveSearchConfig body = new BraveSearchConfig(); // BraveSearchConfig | 
try {
    GBraveSearchApiCredentials result = apiInstance.fastInsertBraveSearchApiCredentials(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BraveSearchConfigurationControllerApi#fastInsertBraveSearchApiCredentials");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**BraveSearchConfig**](BraveSearchConfig.md)|  |

### Return type

[**GBraveSearchApiCredentials**](GBraveSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getBraveSearchApiCredentials"></a>
# **getBraveSearchApiCredentials**
> List&lt;GBraveSearchApiCredentials&gt; getBraveSearchApiCredentials()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BraveSearchConfigurationControllerApi;


BraveSearchConfigurationControllerApi apiInstance = new BraveSearchConfigurationControllerApi();
try {
    List<GBraveSearchApiCredentials> result = apiInstance.getBraveSearchApiCredentials();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BraveSearchConfigurationControllerApi#getBraveSearchApiCredentials");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GBraveSearchApiCredentials&gt;**](GBraveSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getBraveSearchStatus"></a>
# **getBraveSearchStatus**
> ComponentSetupStatus getBraveSearchStatus()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BraveSearchConfigurationControllerApi;


BraveSearchConfigurationControllerApi apiInstance = new BraveSearchConfigurationControllerApi();
try {
    ComponentSetupStatus result = apiInstance.getBraveSearchStatus();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BraveSearchConfigurationControllerApi#getBraveSearchStatus");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**ComponentSetupStatus**](ComponentSetupStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insertGBraveSearchApiCredentials"></a>
# **insertGBraveSearchApiCredentials**
> GBraveSearchApiCredentials insertGBraveSearchApiCredentials(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BraveSearchConfigurationControllerApi;


BraveSearchConfigurationControllerApi apiInstance = new BraveSearchConfigurationControllerApi();
GBraveSearchApiCredentials body = new GBraveSearchApiCredentials(); // GBraveSearchApiCredentials | 
try {
    GBraveSearchApiCredentials result = apiInstance.insertGBraveSearchApiCredentials(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BraveSearchConfigurationControllerApi#insertGBraveSearchApiCredentials");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBraveSearchApiCredentials**](GBraveSearchApiCredentials.md)|  |

### Return type

[**GBraveSearchApiCredentials**](GBraveSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="searchGBraveSearchApiCredentialsByCode"></a>
# **searchGBraveSearchApiCredentialsByCode**
> GBraveSearchApiCredentials searchGBraveSearchApiCredentialsByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BraveSearchConfigurationControllerApi;


BraveSearchConfigurationControllerApi apiInstance = new BraveSearchConfigurationControllerApi();
String code = "code_example"; // String | 
try {
    GBraveSearchApiCredentials result = apiInstance.searchGBraveSearchApiCredentialsByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BraveSearchConfigurationControllerApi#searchGBraveSearchApiCredentialsByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GBraveSearchApiCredentials**](GBraveSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateGBraveSearchApiCredentials"></a>
# **updateGBraveSearchApiCredentials**
> GBraveSearchApiCredentials updateGBraveSearchApiCredentials(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BraveSearchConfigurationControllerApi;


BraveSearchConfigurationControllerApi apiInstance = new BraveSearchConfigurationControllerApi();
GBraveSearchApiCredentials body = new GBraveSearchApiCredentials(); // GBraveSearchApiCredentials | 
try {
    GBraveSearchApiCredentials result = apiInstance.updateGBraveSearchApiCredentials(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BraveSearchConfigurationControllerApi#updateGBraveSearchApiCredentials");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBraveSearchApiCredentials**](GBraveSearchApiCredentials.md)|  |

### Return type

[**GBraveSearchApiCredentials**](GBraveSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

