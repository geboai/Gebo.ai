# Oauth2ModuleStatusControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getStatus**](Oauth2ModuleStatusControllerApi.md#getStatus) | **GET** /api/admin/Oauth2ModuleStatusController | 

<a name="getStatus"></a>
# **getStatus**
> Oauth2ModuleStatus getStatus()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.Oauth2ModuleStatusControllerApi;


Oauth2ModuleStatusControllerApi apiInstance = new Oauth2ModuleStatusControllerApi();
try {
    Oauth2ModuleStatus result = apiInstance.getStatus();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling Oauth2ModuleStatusControllerApi#getStatus");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**Oauth2ModuleStatus**](Oauth2ModuleStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

