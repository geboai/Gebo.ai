# GeboFastChatProfileStatusControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getChatProfilesSetupStatus**](GeboFastChatProfileStatusControllerApi.md#getChatProfilesSetupStatus) | **GET** /api/admin/GeboFastChatProfileStatusController/getChatProfilesSetupStatus | 

<a name="getChatProfilesSetupStatus"></a>
# **getChatProfilesSetupStatus**
> ComponentSetupStatus getChatProfilesSetupStatus()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboFastChatProfileStatusControllerApi;


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

