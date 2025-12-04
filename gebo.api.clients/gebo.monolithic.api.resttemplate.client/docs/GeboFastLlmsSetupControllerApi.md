# GeboFastLlmsSetupControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createLLMCredentials**](GeboFastLlmsSetupControllerApi.md#createLLMCredentials) | **POST** /api/admin/GeboFastLLMSSetupController/createLLMCredentials | 
[**createLLMS**](GeboFastLlmsSetupControllerApi.md#createLLMS) | **POST** /api/admin/GeboFastLLMSSetupController/createLLMS | 
[**getActualLLMSConfiguration**](GeboFastLlmsSetupControllerApi.md#getActualLLMSConfiguration) | **GET** /api/admin/GeboFastLLMSSetupController/getActualLLMSConfiguration | 
[**getLLMSSetupStatus**](GeboFastLlmsSetupControllerApi.md#getLLMSSetupStatus) | **GET** /api/admin/GeboFastLLMSSetupController/getLLMSSetupStatus | 
[**verifyCredentialsAndDownloadModels**](GeboFastLlmsSetupControllerApi.md#verifyCredentialsAndDownloadModels) | **POST** /api/admin/GeboFastLLMSSetupController/verifyCredentialsAndDownloadModels | 

<a name="createLLMCredentials"></a>
# **createLLMCredentials**
> OperationStatusSecretInfo createLLMCredentials(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboFastLlmsSetupControllerApi;


GeboFastLlmsSetupControllerApi apiInstance = new GeboFastLlmsSetupControllerApi();
LLMCredentialsCreationData body = new LLMCredentialsCreationData(); // LLMCredentialsCreationData | 
try {
    OperationStatusSecretInfo result = apiInstance.createLLMCredentials(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboFastLlmsSetupControllerApi#createLLMCredentials");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**LLMCredentialsCreationData**](LLMCredentialsCreationData.md)|  |

### Return type

[**OperationStatusSecretInfo**](OperationStatusSecretInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createLLMS"></a>
# **createLLMS**
> OperationStatusList createLLMS(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboFastLlmsSetupControllerApi;


GeboFastLlmsSetupControllerApi apiInstance = new GeboFastLlmsSetupControllerApi();
List<LLMCreateModelData> body = Arrays.asList(new LLMCreateModelData()); // List<LLMCreateModelData> | 
try {
    OperationStatusList result = apiInstance.createLLMS(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboFastLlmsSetupControllerApi#createLLMS");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**List&lt;LLMCreateModelData&gt;**](LLMCreateModelData.md)|  |

### Return type

[**OperationStatusList**](OperationStatusList.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getActualLLMSConfiguration"></a>
# **getActualLLMSConfiguration**
> LLMSSetupConfigurationData getActualLLMSConfiguration()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboFastLlmsSetupControllerApi;


GeboFastLlmsSetupControllerApi apiInstance = new GeboFastLlmsSetupControllerApi();
try {
    LLMSSetupConfigurationData result = apiInstance.getActualLLMSConfiguration();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboFastLlmsSetupControllerApi#getActualLLMSConfiguration");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**LLMSSetupConfigurationData**](LLMSSetupConfigurationData.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getLLMSSetupStatus"></a>
# **getLLMSSetupStatus**
> ComponentLLMSStatus getLLMSSetupStatus()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboFastLlmsSetupControllerApi;


GeboFastLlmsSetupControllerApi apiInstance = new GeboFastLlmsSetupControllerApi();
try {
    ComponentLLMSStatus result = apiInstance.getLLMSSetupStatus();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboFastLlmsSetupControllerApi#getLLMSSetupStatus");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**ComponentLLMSStatus**](ComponentLLMSStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="verifyCredentialsAndDownloadModels"></a>
# **verifyCredentialsAndDownloadModels**
> OperationStatusListGBaseModelChoice verifyCredentialsAndDownloadModels(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboFastLlmsSetupControllerApi;


GeboFastLlmsSetupControllerApi apiInstance = new GeboFastLlmsSetupControllerApi();
LLMModelsLookupParameter body = new LLMModelsLookupParameter(); // LLMModelsLookupParameter | 
try {
    OperationStatusListGBaseModelChoice result = apiInstance.verifyCredentialsAndDownloadModels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboFastLlmsSetupControllerApi#verifyCredentialsAndDownloadModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**LLMModelsLookupParameter**](LLMModelsLookupParameter.md)|  |

### Return type

[**OperationStatusListGBaseModelChoice**](OperationStatusListGBaseModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

