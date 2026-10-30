# SerpapiSearchConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGSerpapiSearchApiCredentials**](SerpapiSearchConfigurationControllerApi.md#deleteGSerpapiSearchApiCredentials) | **POST** /api/admin/SerpapiSearchConfigurationController/deleteGSerpapiSearchApiCredentials | 
[**fastInsertSerpapiSearchApiCredentials**](SerpapiSearchConfigurationControllerApi.md#fastInsertSerpapiSearchApiCredentials) | **POST** /api/admin/SerpapiSearchConfigurationController/fastInsertSerpapiSearchApiCredentials | 
[**getSerpapiSearchApiCredentials**](SerpapiSearchConfigurationControllerApi.md#getSerpapiSearchApiCredentials) | **GET** /api/admin/SerpapiSearchConfigurationController/getSerpapiSearchApiCredentials | 
[**getSerpapiSearchStatus**](SerpapiSearchConfigurationControllerApi.md#getSerpapiSearchStatus) | **GET** /api/admin/SerpapiSearchConfigurationController/getSerpapiSearchStatus | 
[**insertGSerpapiSearchApiCredentials**](SerpapiSearchConfigurationControllerApi.md#insertGSerpapiSearchApiCredentials) | **POST** /api/admin/SerpapiSearchConfigurationController/insertGSerpapiSearchApiCredentials | 
[**searchGSerpapiSearchApiCredentialsByCode**](SerpapiSearchConfigurationControllerApi.md#searchGSerpapiSearchApiCredentialsByCode) | **GET** /api/admin/SerpapiSearchConfigurationController/searchGSerpapiSearchApiCredentialsByCode | 
[**updateGSerpapiSearchApiCredentials**](SerpapiSearchConfigurationControllerApi.md#updateGSerpapiSearchApiCredentials) | **POST** /api/admin/SerpapiSearchConfigurationController/updateGSerpapiSearchApiCredentials | 

<a name="deleteGSerpapiSearchApiCredentials"></a>
# **deleteGSerpapiSearchApiCredentials**
> deleteGSerpapiSearchApiCredentials(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SerpapiSearchConfigurationControllerApi;


SerpapiSearchConfigurationControllerApi apiInstance = new SerpapiSearchConfigurationControllerApi();
GSerpapiSearchApiCredentials body = new GSerpapiSearchApiCredentials(); // GSerpapiSearchApiCredentials | 
try {
    apiInstance.deleteGSerpapiSearchApiCredentials(body);
} catch (ApiException e) {
    System.err.println("Exception when calling SerpapiSearchConfigurationControllerApi#deleteGSerpapiSearchApiCredentials");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GSerpapiSearchApiCredentials**](GSerpapiSearchApiCredentials.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="fastInsertSerpapiSearchApiCredentials"></a>
# **fastInsertSerpapiSearchApiCredentials**
> GSerpapiSearchApiCredentials fastInsertSerpapiSearchApiCredentials(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SerpapiSearchConfigurationControllerApi;


SerpapiSearchConfigurationControllerApi apiInstance = new SerpapiSearchConfigurationControllerApi();
SerpapiSearchConfig body = new SerpapiSearchConfig(); // SerpapiSearchConfig | 
try {
    GSerpapiSearchApiCredentials result = apiInstance.fastInsertSerpapiSearchApiCredentials(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SerpapiSearchConfigurationControllerApi#fastInsertSerpapiSearchApiCredentials");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SerpapiSearchConfig**](SerpapiSearchConfig.md)|  |

### Return type

[**GSerpapiSearchApiCredentials**](GSerpapiSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getSerpapiSearchApiCredentials"></a>
# **getSerpapiSearchApiCredentials**
> List&lt;GSerpapiSearchApiCredentials&gt; getSerpapiSearchApiCredentials()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SerpapiSearchConfigurationControllerApi;


SerpapiSearchConfigurationControllerApi apiInstance = new SerpapiSearchConfigurationControllerApi();
try {
    List<GSerpapiSearchApiCredentials> result = apiInstance.getSerpapiSearchApiCredentials();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SerpapiSearchConfigurationControllerApi#getSerpapiSearchApiCredentials");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GSerpapiSearchApiCredentials&gt;**](GSerpapiSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSerpapiSearchStatus"></a>
# **getSerpapiSearchStatus**
> ComponentSetupStatus getSerpapiSearchStatus()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SerpapiSearchConfigurationControllerApi;


SerpapiSearchConfigurationControllerApi apiInstance = new SerpapiSearchConfigurationControllerApi();
try {
    ComponentSetupStatus result = apiInstance.getSerpapiSearchStatus();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SerpapiSearchConfigurationControllerApi#getSerpapiSearchStatus");
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

<a name="insertGSerpapiSearchApiCredentials"></a>
# **insertGSerpapiSearchApiCredentials**
> GSerpapiSearchApiCredentials insertGSerpapiSearchApiCredentials(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SerpapiSearchConfigurationControllerApi;


SerpapiSearchConfigurationControllerApi apiInstance = new SerpapiSearchConfigurationControllerApi();
GSerpapiSearchApiCredentials body = new GSerpapiSearchApiCredentials(); // GSerpapiSearchApiCredentials | 
try {
    GSerpapiSearchApiCredentials result = apiInstance.insertGSerpapiSearchApiCredentials(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SerpapiSearchConfigurationControllerApi#insertGSerpapiSearchApiCredentials");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GSerpapiSearchApiCredentials**](GSerpapiSearchApiCredentials.md)|  |

### Return type

[**GSerpapiSearchApiCredentials**](GSerpapiSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="searchGSerpapiSearchApiCredentialsByCode"></a>
# **searchGSerpapiSearchApiCredentialsByCode**
> GSerpapiSearchApiCredentials searchGSerpapiSearchApiCredentialsByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SerpapiSearchConfigurationControllerApi;


SerpapiSearchConfigurationControllerApi apiInstance = new SerpapiSearchConfigurationControllerApi();
String code = "code_example"; // String | 
try {
    GSerpapiSearchApiCredentials result = apiInstance.searchGSerpapiSearchApiCredentialsByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SerpapiSearchConfigurationControllerApi#searchGSerpapiSearchApiCredentialsByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GSerpapiSearchApiCredentials**](GSerpapiSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateGSerpapiSearchApiCredentials"></a>
# **updateGSerpapiSearchApiCredentials**
> GSerpapiSearchApiCredentials updateGSerpapiSearchApiCredentials(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SerpapiSearchConfigurationControllerApi;


SerpapiSearchConfigurationControllerApi apiInstance = new SerpapiSearchConfigurationControllerApi();
GSerpapiSearchApiCredentials body = new GSerpapiSearchApiCredentials(); // GSerpapiSearchApiCredentials | 
try {
    GSerpapiSearchApiCredentials result = apiInstance.updateGSerpapiSearchApiCredentials(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SerpapiSearchConfigurationControllerApi#updateGSerpapiSearchApiCredentials");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GSerpapiSearchApiCredentials**](GSerpapiSearchApiCredentials.md)|  |

### Return type

[**GSerpapiSearchApiCredentials**](GSerpapiSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

