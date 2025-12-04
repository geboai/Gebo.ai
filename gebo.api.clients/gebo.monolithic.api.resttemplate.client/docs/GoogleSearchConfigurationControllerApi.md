# GoogleSearchConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGGoogleSearchApiCredentials**](GoogleSearchConfigurationControllerApi.md#deleteGGoogleSearchApiCredentials) | **POST** /api/admin/GoogleSearchConfigurationController/deleteGGoogleSearchApiCredentials | 
[**insertGGoogleSearchApiCredentials**](GoogleSearchConfigurationControllerApi.md#insertGGoogleSearchApiCredentials) | **POST** /api/admin/GoogleSearchConfigurationController/insertGGoogleSearchApiCredentials | 
[**searchGGoogleSearchApiCredentialsByCode**](GoogleSearchConfigurationControllerApi.md#searchGGoogleSearchApiCredentialsByCode) | **GET** /api/admin/GoogleSearchConfigurationController/searchGGoogleSearchApiCredentialsByCode | 
[**updateGGoogleSearchApiCredentials**](GoogleSearchConfigurationControllerApi.md#updateGGoogleSearchApiCredentials) | **POST** /api/admin/GoogleSearchConfigurationController/updateGGoogleSearchApiCredentials | 

<a name="deleteGGoogleSearchApiCredentials"></a>
# **deleteGGoogleSearchApiCredentials**
> deleteGGoogleSearchApiCredentials(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleSearchConfigurationControllerApi;


GoogleSearchConfigurationControllerApi apiInstance = new GoogleSearchConfigurationControllerApi();
GGoogleSearchApiCredentials body = new GGoogleSearchApiCredentials(); // GGoogleSearchApiCredentials | 
try {
    apiInstance.deleteGGoogleSearchApiCredentials(body);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleSearchConfigurationControllerApi#deleteGGoogleSearchApiCredentials");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleSearchApiCredentials**](GGoogleSearchApiCredentials.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="insertGGoogleSearchApiCredentials"></a>
# **insertGGoogleSearchApiCredentials**
> GGoogleSearchApiCredentials insertGGoogleSearchApiCredentials(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleSearchConfigurationControllerApi;


GoogleSearchConfigurationControllerApi apiInstance = new GoogleSearchConfigurationControllerApi();
GGoogleSearchApiCredentials body = new GGoogleSearchApiCredentials(); // GGoogleSearchApiCredentials | 
try {
    GGoogleSearchApiCredentials result = apiInstance.insertGGoogleSearchApiCredentials(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleSearchConfigurationControllerApi#insertGGoogleSearchApiCredentials");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleSearchApiCredentials**](GGoogleSearchApiCredentials.md)|  |

### Return type

[**GGoogleSearchApiCredentials**](GGoogleSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="searchGGoogleSearchApiCredentialsByCode"></a>
# **searchGGoogleSearchApiCredentialsByCode**
> GGoogleSearchApiCredentials searchGGoogleSearchApiCredentialsByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleSearchConfigurationControllerApi;


GoogleSearchConfigurationControllerApi apiInstance = new GoogleSearchConfigurationControllerApi();
String code = "code_example"; // String | 
try {
    GGoogleSearchApiCredentials result = apiInstance.searchGGoogleSearchApiCredentialsByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleSearchConfigurationControllerApi#searchGGoogleSearchApiCredentialsByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GGoogleSearchApiCredentials**](GGoogleSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateGGoogleSearchApiCredentials"></a>
# **updateGGoogleSearchApiCredentials**
> GGoogleSearchApiCredentials updateGGoogleSearchApiCredentials(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleSearchConfigurationControllerApi;


GoogleSearchConfigurationControllerApi apiInstance = new GoogleSearchConfigurationControllerApi();
GGoogleSearchApiCredentials body = new GGoogleSearchApiCredentials(); // GGoogleSearchApiCredentials | 
try {
    GGoogleSearchApiCredentials result = apiInstance.updateGGoogleSearchApiCredentials(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleSearchConfigurationControllerApi#updateGGoogleSearchApiCredentials");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleSearchApiCredentials**](GGoogleSearchApiCredentials.md)|  |

### Return type

[**GGoogleSearchApiCredentials**](GGoogleSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

