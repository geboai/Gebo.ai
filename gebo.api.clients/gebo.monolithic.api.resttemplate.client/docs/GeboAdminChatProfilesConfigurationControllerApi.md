# GeboAdminChatProfilesConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteChatProfile**](GeboAdminChatProfilesConfigurationControllerApi.md#deleteChatProfile) | **POST** /api/admin/GeboAdminChatProfilesConfigurationController/deleteChatProfile | 
[**findChatProfileConfigurationByCode**](GeboAdminChatProfilesConfigurationControllerApi.md#findChatProfileConfigurationByCode) | **GET** /api/admin/GeboAdminChatProfilesConfigurationController/findChatProfileConfigurationByCode | 
[**getAllChatProfileConfiguration**](GeboAdminChatProfilesConfigurationControllerApi.md#getAllChatProfileConfiguration) | **POST** /api/admin/GeboAdminChatProfilesConfigurationController/getAllChatProfileConfiguration | 
[**getChatProfileConfigurationByQbe**](GeboAdminChatProfilesConfigurationControllerApi.md#getChatProfileConfigurationByQbe) | **POST** /api/admin/GeboAdminChatProfilesConfigurationController/getChatProfileConfigurationByQbe | 
[**insertChatProfile**](GeboAdminChatProfilesConfigurationControllerApi.md#insertChatProfile) | **POST** /api/admin/GeboAdminChatProfilesConfigurationController/insertChatProfile | 
[**updateChatProfile**](GeboAdminChatProfilesConfigurationControllerApi.md#updateChatProfile) | **POST** /api/admin/GeboAdminChatProfilesConfigurationController/updateChatProfile | 

<a name="deleteChatProfile"></a>
# **deleteChatProfile**
> deleteChatProfile(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAdminChatProfilesConfigurationControllerApi;


GeboAdminChatProfilesConfigurationControllerApi apiInstance = new GeboAdminChatProfilesConfigurationControllerApi();
GChatProfileConfiguration body = new GChatProfileConfiguration(); // GChatProfileConfiguration | 
try {
    apiInstance.deleteChatProfile(body);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminChatProfilesConfigurationControllerApi#deleteChatProfile");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GChatProfileConfiguration**](GChatProfileConfiguration.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="findChatProfileConfigurationByCode"></a>
# **findChatProfileConfigurationByCode**
> GChatProfileConfiguration findChatProfileConfigurationByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAdminChatProfilesConfigurationControllerApi;


GeboAdminChatProfilesConfigurationControllerApi apiInstance = new GeboAdminChatProfilesConfigurationControllerApi();
String code = "code_example"; // String | 
try {
    GChatProfileConfiguration result = apiInstance.findChatProfileConfigurationByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminChatProfilesConfigurationControllerApi#findChatProfileConfigurationByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GChatProfileConfiguration**](GChatProfileConfiguration.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAllChatProfileConfiguration"></a>
# **getAllChatProfileConfiguration**
> PageGChatProfileConfiguration getAllChatProfileConfiguration(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAdminChatProfilesConfigurationControllerApi;


GeboAdminChatProfilesConfigurationControllerApi apiInstance = new GeboAdminChatProfilesConfigurationControllerApi();
DataPage body = new DataPage(); // DataPage | 
try {
    PageGChatProfileConfiguration result = apiInstance.getAllChatProfileConfiguration(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminChatProfilesConfigurationControllerApi#getAllChatProfileConfiguration");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**DataPage**](DataPage.md)|  |

### Return type

[**PageGChatProfileConfiguration**](PageGChatProfileConfiguration.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getChatProfileConfigurationByQbe"></a>
# **getChatProfileConfigurationByQbe**
> PageGChatProfileConfiguration getChatProfileConfigurationByQbe(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAdminChatProfilesConfigurationControllerApi;


GeboAdminChatProfilesConfigurationControllerApi apiInstance = new GeboAdminChatProfilesConfigurationControllerApi();
ChatProfileConfigurationByQbeParam body = new ChatProfileConfigurationByQbeParam(); // ChatProfileConfigurationByQbeParam | 
try {
    PageGChatProfileConfiguration result = apiInstance.getChatProfileConfigurationByQbe(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminChatProfilesConfigurationControllerApi#getChatProfileConfigurationByQbe");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ChatProfileConfigurationByQbeParam**](ChatProfileConfigurationByQbeParam.md)|  |

### Return type

[**PageGChatProfileConfiguration**](PageGChatProfileConfiguration.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertChatProfile"></a>
# **insertChatProfile**
> GChatProfileConfiguration insertChatProfile(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAdminChatProfilesConfigurationControllerApi;


GeboAdminChatProfilesConfigurationControllerApi apiInstance = new GeboAdminChatProfilesConfigurationControllerApi();
GChatProfileConfiguration body = new GChatProfileConfiguration(); // GChatProfileConfiguration | 
try {
    GChatProfileConfiguration result = apiInstance.insertChatProfile(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminChatProfilesConfigurationControllerApi#insertChatProfile");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GChatProfileConfiguration**](GChatProfileConfiguration.md)|  |

### Return type

[**GChatProfileConfiguration**](GChatProfileConfiguration.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateChatProfile"></a>
# **updateChatProfile**
> GChatProfileConfiguration updateChatProfile(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAdminChatProfilesConfigurationControllerApi;


GeboAdminChatProfilesConfigurationControllerApi apiInstance = new GeboAdminChatProfilesConfigurationControllerApi();
GChatProfileConfiguration body = new GChatProfileConfiguration(); // GChatProfileConfiguration | 
try {
    GChatProfileConfiguration result = apiInstance.updateChatProfile(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminChatProfilesConfigurationControllerApi#updateChatProfile");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GChatProfileConfiguration**](GChatProfileConfiguration.md)|  |

### Return type

[**GChatProfileConfiguration**](GChatProfileConfiguration.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

