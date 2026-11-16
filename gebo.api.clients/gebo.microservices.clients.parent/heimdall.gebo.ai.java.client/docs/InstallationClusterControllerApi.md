# InstallationClusterControllerApi

All URIs are relative to *http://localhost:13018/heimdall*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getInstallationId**](InstallationClusterControllerApi.md#getInstallationId) | **GET** /public/InstallationController/getInstallationId | 

<a name="getInstallationId"></a>
# **getInstallationId**
> Map&lt;String, String&gt; getInstallationId(geboAuthorization)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.InstallationClusterControllerApi;


InstallationClusterControllerApi apiInstance = new InstallationClusterControllerApi();
String geboAuthorization = "geboAuthorization_example"; // String | 
try {
    Map<String, String> result = apiInstance.getInstallationId(geboAuthorization);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling InstallationClusterControllerApi#getInstallationId");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **geboAuthorization** | **String**|  | [optional]

### Return type

**Map&lt;String, String&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

