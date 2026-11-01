# SearxngSearchConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGSearxngSearchApiCredentials**](SearxngSearchConfigurationControllerApi.md#deleteGSearxngSearchApiCredentials) | **POST** /api/admin/SearxngSearchConfigurationController/deleteGSearxngSearchApiCredentials | 
[**fastInsertSearxngSearchApiCredentials**](SearxngSearchConfigurationControllerApi.md#fastInsertSearxngSearchApiCredentials) | **POST** /api/admin/SearxngSearchConfigurationController/fastInsertSearxngSearchApiCredentials | 
[**getSearxngSearchApiCredentials**](SearxngSearchConfigurationControllerApi.md#getSearxngSearchApiCredentials) | **GET** /api/admin/SearxngSearchConfigurationController/getSearxngSearchApiCredentials | 
[**getSearxngSearchStatus**](SearxngSearchConfigurationControllerApi.md#getSearxngSearchStatus) | **GET** /api/admin/SearxngSearchConfigurationController/getSearxngSearchStatus | 
[**insertGSearxngSearchApiCredentials**](SearxngSearchConfigurationControllerApi.md#insertGSearxngSearchApiCredentials) | **POST** /api/admin/SearxngSearchConfigurationController/insertGSearxngSearchApiCredentials | 
[**searchGSearxngSearchApiCredentialsByCode**](SearxngSearchConfigurationControllerApi.md#searchGSearxngSearchApiCredentialsByCode) | **GET** /api/admin/SearxngSearchConfigurationController/searchGSearxngSearchApiCredentialsByCode | 
[**updateGSearxngSearchApiCredentials**](SearxngSearchConfigurationControllerApi.md#updateGSearxngSearchApiCredentials) | **POST** /api/admin/SearxngSearchConfigurationController/updateGSearxngSearchApiCredentials | 

<a name="deleteGSearxngSearchApiCredentials"></a>
# **deleteGSearxngSearchApiCredentials**
> deleteGSearxngSearchApiCredentials(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SearxngSearchConfigurationControllerApi;


SearxngSearchConfigurationControllerApi apiInstance = new SearxngSearchConfigurationControllerApi();
GSearxngSearchApiCredentials body = new GSearxngSearchApiCredentials(); // GSearxngSearchApiCredentials | 
try {
    apiInstance.deleteGSearxngSearchApiCredentials(body);
} catch (ApiException e) {
    System.err.println("Exception when calling SearxngSearchConfigurationControllerApi#deleteGSearxngSearchApiCredentials");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GSearxngSearchApiCredentials**](GSearxngSearchApiCredentials.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="fastInsertSearxngSearchApiCredentials"></a>
# **fastInsertSearxngSearchApiCredentials**
> GSearxngSearchApiCredentials fastInsertSearxngSearchApiCredentials(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SearxngSearchConfigurationControllerApi;


SearxngSearchConfigurationControllerApi apiInstance = new SearxngSearchConfigurationControllerApi();
SearxngSearchConfig body = new SearxngSearchConfig(); // SearxngSearchConfig | 
try {
    GSearxngSearchApiCredentials result = apiInstance.fastInsertSearxngSearchApiCredentials(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SearxngSearchConfigurationControllerApi#fastInsertSearxngSearchApiCredentials");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SearxngSearchConfig**](SearxngSearchConfig.md)|  |

### Return type

[**GSearxngSearchApiCredentials**](GSearxngSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getSearxngSearchApiCredentials"></a>
# **getSearxngSearchApiCredentials**
> List&lt;GSearxngSearchApiCredentials&gt; getSearxngSearchApiCredentials()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SearxngSearchConfigurationControllerApi;


SearxngSearchConfigurationControllerApi apiInstance = new SearxngSearchConfigurationControllerApi();
try {
    List<GSearxngSearchApiCredentials> result = apiInstance.getSearxngSearchApiCredentials();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SearxngSearchConfigurationControllerApi#getSearxngSearchApiCredentials");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GSearxngSearchApiCredentials&gt;**](GSearxngSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSearxngSearchStatus"></a>
# **getSearxngSearchStatus**
> ComponentSetupStatus getSearxngSearchStatus()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SearxngSearchConfigurationControllerApi;


SearxngSearchConfigurationControllerApi apiInstance = new SearxngSearchConfigurationControllerApi();
try {
    ComponentSetupStatus result = apiInstance.getSearxngSearchStatus();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SearxngSearchConfigurationControllerApi#getSearxngSearchStatus");
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

<a name="insertGSearxngSearchApiCredentials"></a>
# **insertGSearxngSearchApiCredentials**
> GSearxngSearchApiCredentials insertGSearxngSearchApiCredentials(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SearxngSearchConfigurationControllerApi;


SearxngSearchConfigurationControllerApi apiInstance = new SearxngSearchConfigurationControllerApi();
GSearxngSearchApiCredentials body = new GSearxngSearchApiCredentials(); // GSearxngSearchApiCredentials | 
try {
    GSearxngSearchApiCredentials result = apiInstance.insertGSearxngSearchApiCredentials(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SearxngSearchConfigurationControllerApi#insertGSearxngSearchApiCredentials");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GSearxngSearchApiCredentials**](GSearxngSearchApiCredentials.md)|  |

### Return type

[**GSearxngSearchApiCredentials**](GSearxngSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="searchGSearxngSearchApiCredentialsByCode"></a>
# **searchGSearxngSearchApiCredentialsByCode**
> GSearxngSearchApiCredentials searchGSearxngSearchApiCredentialsByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SearxngSearchConfigurationControllerApi;


SearxngSearchConfigurationControllerApi apiInstance = new SearxngSearchConfigurationControllerApi();
String code = "code_example"; // String | 
try {
    GSearxngSearchApiCredentials result = apiInstance.searchGSearxngSearchApiCredentialsByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SearxngSearchConfigurationControllerApi#searchGSearxngSearchApiCredentialsByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GSearxngSearchApiCredentials**](GSearxngSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateGSearxngSearchApiCredentials"></a>
# **updateGSearxngSearchApiCredentials**
> GSearxngSearchApiCredentials updateGSearxngSearchApiCredentials(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SearxngSearchConfigurationControllerApi;


SearxngSearchConfigurationControllerApi apiInstance = new SearxngSearchConfigurationControllerApi();
GSearxngSearchApiCredentials body = new GSearxngSearchApiCredentials(); // GSearxngSearchApiCredentials | 
try {
    GSearxngSearchApiCredentials result = apiInstance.updateGSearxngSearchApiCredentials(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SearxngSearchConfigurationControllerApi#updateGSearxngSearchApiCredentials");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GSearxngSearchApiCredentials**](GSearxngSearchApiCredentials.md)|  |

### Return type

[**GSearxngSearchApiCredentials**](GSearxngSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

