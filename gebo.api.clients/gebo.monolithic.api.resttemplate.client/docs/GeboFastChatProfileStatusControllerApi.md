# GeboFastChatProfileStatusControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getChatProfilesSetupStatus**](GeboFastChatProfileStatusControllerApi.md#getChatProfilesSetupStatus) | **GET** /api/admin/GeboFastChatProfileStatusController/getChatProfilesSetupStatus | 

<a name="getChatProfilesSetupStatus"></a>
# **getChatProfilesSetupStatus**
> ComponentSetupStatus getChatProfilesSetupStatus()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboFastChatProfileStatusControllerApi;


GeboFastChatProfileStatusControllerApi apiInstance = new GeboFastChatProfileStatusControllerApi();
try {
    ComponentSetupStatus result = apiInstance.getChatProfilesSetupStatus();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboFastChatProfileStatusControllerApi#getChatProfilesSetupStatus");
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

