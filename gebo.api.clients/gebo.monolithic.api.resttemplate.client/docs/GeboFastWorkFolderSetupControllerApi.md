# GeboFastWorkFolderSetupControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**configureWorkDirectory**](GeboFastWorkFolderSetupControllerApi.md#configureWorkDirectory) | **POST** /api/admin/GeboFastWorkFolderSetupController/configureWorkDirectory | 
[**getWorkDirectorySetupEnabled**](GeboFastWorkFolderSetupControllerApi.md#getWorkDirectorySetupEnabled) | **GET** /api/admin/GeboFastWorkFolderSetupController/getWorkDirectorySetupEnabled | 
[**getWorkDirectorySetupStatus**](GeboFastWorkFolderSetupControllerApi.md#getWorkDirectorySetupStatus) | **GET** /api/admin/GeboFastWorkFolderSetupController/getWorkDirectorySetupStatus | 

<a name="configureWorkDirectory"></a>
# **configureWorkDirectory**
> OperationStatusWorkFolderSetupStatus configureWorkDirectory(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboFastWorkFolderSetupControllerApi;


GeboFastWorkFolderSetupControllerApi apiInstance = new GeboFastWorkFolderSetupControllerApi();
FastWorkDirectorySetupData body = new FastWorkDirectorySetupData(); // FastWorkDirectorySetupData | 
try {
    OperationStatusWorkFolderSetupStatus result = apiInstance.configureWorkDirectory(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboFastWorkFolderSetupControllerApi#configureWorkDirectory");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**FastWorkDirectorySetupData**](FastWorkDirectorySetupData.md)|  |

### Return type

[**OperationStatusWorkFolderSetupStatus**](OperationStatusWorkFolderSetupStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getWorkDirectorySetupEnabled"></a>
# **getWorkDirectorySetupEnabled**
> ComponentEnabledStatus getWorkDirectorySetupEnabled()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboFastWorkFolderSetupControllerApi;


GeboFastWorkFolderSetupControllerApi apiInstance = new GeboFastWorkFolderSetupControllerApi();
try {
    ComponentEnabledStatus result = apiInstance.getWorkDirectorySetupEnabled();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboFastWorkFolderSetupControllerApi#getWorkDirectorySetupEnabled");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**ComponentEnabledStatus**](ComponentEnabledStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getWorkDirectorySetupStatus"></a>
# **getWorkDirectorySetupStatus**
> WorkFolderSetupStatus getWorkDirectorySetupStatus()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboFastWorkFolderSetupControllerApi;


GeboFastWorkFolderSetupControllerApi apiInstance = new GeboFastWorkFolderSetupControllerApi();
try {
    WorkFolderSetupStatus result = apiInstance.getWorkDirectorySetupStatus();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboFastWorkFolderSetupControllerApi#getWorkDirectorySetupStatus");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**WorkFolderSetupStatus**](WorkFolderSetupStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

