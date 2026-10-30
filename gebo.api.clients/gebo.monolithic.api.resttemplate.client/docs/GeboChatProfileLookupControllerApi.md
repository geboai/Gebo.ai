# GeboChatProfileLookupControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**findChatProfileConfigurationLookupByCode**](GeboChatProfileLookupControllerApi.md#findChatProfileConfigurationLookupByCode) | **GET** /api/users/GeboChatProfileLookupController/findChatProfileConfigurationLookupByCode | 
[**getAllChatProfileConfigurationLoookup**](GeboChatProfileLookupControllerApi.md#getAllChatProfileConfigurationLoookup) | **POST** /api/users/GeboChatProfileLookupController/getAllChatProfileConfigurationLoookup | 
[**getChatProfileConfigurationLookupByQbe**](GeboChatProfileLookupControllerApi.md#getChatProfileConfigurationLookupByQbe) | **POST** /api/users/GeboChatProfileLookupController/getChatProfileConfigurationLookupByQbe | 

<a name="findChatProfileConfigurationLookupByCode"></a>
# **findChatProfileConfigurationLookupByCode**
> GChatProfileConfiguration findChatProfileConfigurationLookupByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboChatProfileLookupControllerApi;


GeboChatProfileLookupControllerApi apiInstance = new GeboChatProfileLookupControllerApi();
String code = "code_example"; // String | 
try {
    GChatProfileConfiguration result = apiInstance.findChatProfileConfigurationLookupByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboChatProfileLookupControllerApi#findChatProfileConfigurationLookupByCode");
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

<a name="getAllChatProfileConfigurationLoookup"></a>
# **getAllChatProfileConfigurationLoookup**
> PagedModelGLookupEntry getAllChatProfileConfigurationLoookup(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboChatProfileLookupControllerApi;


GeboChatProfileLookupControllerApi apiInstance = new GeboChatProfileLookupControllerApi();
DataPage body = new DataPage(); // DataPage | 
try {
    PagedModelGLookupEntry result = apiInstance.getAllChatProfileConfigurationLoookup(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboChatProfileLookupControllerApi#getAllChatProfileConfigurationLoookup");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**DataPage**](DataPage.md)|  |

### Return type

[**PagedModelGLookupEntry**](PagedModelGLookupEntry.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getChatProfileConfigurationLookupByQbe"></a>
# **getChatProfileConfigurationLookupByQbe**
> PagedModelGLookupEntry getChatProfileConfigurationLookupByQbe(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboChatProfileLookupControllerApi;


GeboChatProfileLookupControllerApi apiInstance = new GeboChatProfileLookupControllerApi();
ChatProfileConfigurationLookupByQbeParam body = new ChatProfileConfigurationLookupByQbeParam(); // ChatProfileConfigurationLookupByQbeParam | 
try {
    PagedModelGLookupEntry result = apiInstance.getChatProfileConfigurationLookupByQbe(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboChatProfileLookupControllerApi#getChatProfileConfigurationLookupByQbe");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ChatProfileConfigurationLookupByQbeParam**](ChatProfileConfigurationLookupByQbeParam.md)|  |

### Return type

[**PagedModelGLookupEntry**](PagedModelGLookupEntry.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

