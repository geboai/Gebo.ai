# DecisionModelsControllerApi

All URIs are relative to *http://localhost:13999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getDecisionModelTypes**](DecisionModelsControllerApi.md#getDecisionModelTypes) | **GET** /api/admin/DecisionModelsController/getDecisionModelTypes | 
[**getRuntimeConfiguredDecisionModels**](DecisionModelsControllerApi.md#getRuntimeConfiguredDecisionModels) | **GET** /api/admin/DecisionModelsController/getRuntimeConfiguredDecisionModels | 

<a name="getDecisionModelTypes"></a>
# **getDecisionModelTypes**
> List&lt;GDecisionModelType&gt; getDecisionModelTypes()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.DecisionModelsControllerApi;


DecisionModelsControllerApi apiInstance = new DecisionModelsControllerApi();
try {
    List<GDecisionModelType> result = apiInstance.getDecisionModelTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DecisionModelsControllerApi#getDecisionModelTypes");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GDecisionModelType&gt;**](GDecisionModelType.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getRuntimeConfiguredDecisionModels"></a>
# **getRuntimeConfiguredDecisionModels**
> List&lt;ConfigurationEntryGBaseDecisionModelConfig&gt; getRuntimeConfiguredDecisionModels(modelTypeCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.DecisionModelsControllerApi;


DecisionModelsControllerApi apiInstance = new DecisionModelsControllerApi();
String modelTypeCode = "modelTypeCode_example"; // String | 
try {
    List<ConfigurationEntryGBaseDecisionModelConfig> result = apiInstance.getRuntimeConfiguredDecisionModels(modelTypeCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DecisionModelsControllerApi#getRuntimeConfiguredDecisionModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **modelTypeCode** | **String**|  | [optional]

### Return type

[**List&lt;ConfigurationEntryGBaseDecisionModelConfig&gt;**](ConfigurationEntryGBaseDecisionModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

