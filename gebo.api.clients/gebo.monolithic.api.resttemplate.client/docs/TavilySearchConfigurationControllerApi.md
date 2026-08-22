# TavilySearchConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGTavilySearchApiCredentials**](TavilySearchConfigurationControllerApi.md#deleteGTavilySearchApiCredentials) | **POST** /api/admin/TavilySearchConfigurationController/deleteGTavilySearchApiCredentials | 
[**fastInsertTavilySearchApiCredentials**](TavilySearchConfigurationControllerApi.md#fastInsertTavilySearchApiCredentials) | **POST** /api/admin/TavilySearchConfigurationController/fastInsertTavilySearchApiCredentials | 
[**getTavilySearchApiCredentials**](TavilySearchConfigurationControllerApi.md#getTavilySearchApiCredentials) | **GET** /api/admin/TavilySearchConfigurationController/getTavilySearchApiCredentials | 
[**getTavilySearchStatus**](TavilySearchConfigurationControllerApi.md#getTavilySearchStatus) | **GET** /api/admin/TavilySearchConfigurationController/getTavilySearchStatus | 
[**insertGTavilySearchApiCredentials**](TavilySearchConfigurationControllerApi.md#insertGTavilySearchApiCredentials) | **POST** /api/admin/TavilySearchConfigurationController/insertGTavilySearchApiCredentials | 
[**searchGTavilySearchApiCredentialsByCode**](TavilySearchConfigurationControllerApi.md#searchGTavilySearchApiCredentialsByCode) | **GET** /api/admin/TavilySearchConfigurationController/searchGTavilySearchApiCredentialsByCode | 
[**updateGTavilySearchApiCredentials**](TavilySearchConfigurationControllerApi.md#updateGTavilySearchApiCredentials) | **POST** /api/admin/TavilySearchConfigurationController/updateGTavilySearchApiCredentials | 

<a name="deleteGTavilySearchApiCredentials"></a>
# **deleteGTavilySearchApiCredentials**
> deleteGTavilySearchApiCredentials(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.TavilySearchConfigurationControllerApi;


TavilySearchConfigurationControllerApi apiInstance = new TavilySearchConfigurationControllerApi();
GTavilySearchApiCredentials body = new GTavilySearchApiCredentials(); // GTavilySearchApiCredentials | 
try {
    apiInstance.deleteGTavilySearchApiCredentials(body);
} catch (ApiException e) {
    System.err.println("Exception when calling TavilySearchConfigurationControllerApi#deleteGTavilySearchApiCredentials");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GTavilySearchApiCredentials**](GTavilySearchApiCredentials.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="fastInsertTavilySearchApiCredentials"></a>
# **fastInsertTavilySearchApiCredentials**
> GTavilySearchApiCredentials fastInsertTavilySearchApiCredentials(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.TavilySearchConfigurationControllerApi;


TavilySearchConfigurationControllerApi apiInstance = new TavilySearchConfigurationControllerApi();
TavilySearchConfig body = new TavilySearchConfig(); // TavilySearchConfig | 
try {
    GTavilySearchApiCredentials result = apiInstance.fastInsertTavilySearchApiCredentials(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TavilySearchConfigurationControllerApi#fastInsertTavilySearchApiCredentials");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**TavilySearchConfig**](TavilySearchConfig.md)|  |

### Return type

[**GTavilySearchApiCredentials**](GTavilySearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getTavilySearchApiCredentials"></a>
# **getTavilySearchApiCredentials**
> List&lt;GTavilySearchApiCredentials&gt; getTavilySearchApiCredentials()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.TavilySearchConfigurationControllerApi;


TavilySearchConfigurationControllerApi apiInstance = new TavilySearchConfigurationControllerApi();
try {
    List<GTavilySearchApiCredentials> result = apiInstance.getTavilySearchApiCredentials();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TavilySearchConfigurationControllerApi#getTavilySearchApiCredentials");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GTavilySearchApiCredentials&gt;**](GTavilySearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getTavilySearchStatus"></a>
# **getTavilySearchStatus**
> ComponentSetupStatus getTavilySearchStatus()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.TavilySearchConfigurationControllerApi;


TavilySearchConfigurationControllerApi apiInstance = new TavilySearchConfigurationControllerApi();
try {
    ComponentSetupStatus result = apiInstance.getTavilySearchStatus();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TavilySearchConfigurationControllerApi#getTavilySearchStatus");
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

<a name="insertGTavilySearchApiCredentials"></a>
# **insertGTavilySearchApiCredentials**
> GTavilySearchApiCredentials insertGTavilySearchApiCredentials(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.TavilySearchConfigurationControllerApi;


TavilySearchConfigurationControllerApi apiInstance = new TavilySearchConfigurationControllerApi();
GTavilySearchApiCredentials body = new GTavilySearchApiCredentials(); // GTavilySearchApiCredentials | 
try {
    GTavilySearchApiCredentials result = apiInstance.insertGTavilySearchApiCredentials(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TavilySearchConfigurationControllerApi#insertGTavilySearchApiCredentials");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GTavilySearchApiCredentials**](GTavilySearchApiCredentials.md)|  |

### Return type

[**GTavilySearchApiCredentials**](GTavilySearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="searchGTavilySearchApiCredentialsByCode"></a>
# **searchGTavilySearchApiCredentialsByCode**
> GTavilySearchApiCredentials searchGTavilySearchApiCredentialsByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.TavilySearchConfigurationControllerApi;


TavilySearchConfigurationControllerApi apiInstance = new TavilySearchConfigurationControllerApi();
String code = "code_example"; // String | 
try {
    GTavilySearchApiCredentials result = apiInstance.searchGTavilySearchApiCredentialsByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TavilySearchConfigurationControllerApi#searchGTavilySearchApiCredentialsByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GTavilySearchApiCredentials**](GTavilySearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateGTavilySearchApiCredentials"></a>
# **updateGTavilySearchApiCredentials**
> GTavilySearchApiCredentials updateGTavilySearchApiCredentials(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.TavilySearchConfigurationControllerApi;


TavilySearchConfigurationControllerApi apiInstance = new TavilySearchConfigurationControllerApi();
GTavilySearchApiCredentials body = new GTavilySearchApiCredentials(); // GTavilySearchApiCredentials | 
try {
    GTavilySearchApiCredentials result = apiInstance.updateGTavilySearchApiCredentials(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TavilySearchConfigurationControllerApi#updateGTavilySearchApiCredentials");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GTavilySearchApiCredentials**](GTavilySearchApiCredentials.md)|  |

### Return type

[**GTavilySearchApiCredentials**](GTavilySearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

