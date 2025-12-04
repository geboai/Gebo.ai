# GeboFastInstallationSetupControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createSetup**](GeboFastInstallationSetupControllerApi.md#createSetup) | **POST** /public/GeboFastSetupController/createSetup | 
[**getInstallationStatus**](GeboFastInstallationSetupControllerApi.md#getInstallationStatus) | **GET** /public/GeboFastSetupController/getInstallationStatus | 

<a name="createSetup"></a>
# **createSetup**
> OperationStatusBoolean createSetup(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboFastInstallationSetupControllerApi;


GeboFastInstallationSetupControllerApi apiInstance = new GeboFastInstallationSetupControllerApi();
FastInstallationSetupData body = new FastInstallationSetupData(); // FastInstallationSetupData | 
try {
    OperationStatusBoolean result = apiInstance.createSetup(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboFastInstallationSetupControllerApi#createSetup");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**FastInstallationSetupData**](FastInstallationSetupData.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getInstallationStatus"></a>
# **getInstallationStatus**
> OperationStatusBoolean getInstallationStatus()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboFastInstallationSetupControllerApi;


GeboFastInstallationSetupControllerApi apiInstance = new GeboFastInstallationSetupControllerApi();
try {
    OperationStatusBoolean result = apiInstance.getInstallationStatus();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboFastInstallationSetupControllerApi#getInstallationStatus");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

