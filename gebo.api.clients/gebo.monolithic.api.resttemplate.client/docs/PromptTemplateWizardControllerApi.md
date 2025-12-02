# PromptTemplateWizardControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**generatePromptTemplate**](PromptTemplateWizardControllerApi.md#generatePromptTemplate) | **POST** /api/users/PromptTemplateWizardController/generatePromptTemplate | 
[**getTemplateWizardConfigs**](PromptTemplateWizardControllerApi.md#getTemplateWizardConfigs) | **GET** /api/users/PromptTemplateWizardController/getTemplateWizardConfigs | 

<a name="generatePromptTemplate"></a>
# **generatePromptTemplate**
> OperationStatusPromptTemplateResponse generatePromptTemplate(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.PromptTemplateWizardControllerApi;


PromptTemplateWizardControllerApi apiInstance = new PromptTemplateWizardControllerApi();
PromptTemplateParam body = new PromptTemplateParam(); // PromptTemplateParam | 
try {
    OperationStatusPromptTemplateResponse result = apiInstance.generatePromptTemplate(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PromptTemplateWizardControllerApi#generatePromptTemplate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**PromptTemplateParam**](PromptTemplateParam.md)|  |

### Return type

[**OperationStatusPromptTemplateResponse**](OperationStatusPromptTemplateResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getTemplateWizardConfigs"></a>
# **getTemplateWizardConfigs**
> PromptTemplateWizardConfigs getTemplateWizardConfigs()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.PromptTemplateWizardControllerApi;


PromptTemplateWizardControllerApi apiInstance = new PromptTemplateWizardControllerApi();
try {
    PromptTemplateWizardConfigs result = apiInstance.getTemplateWizardConfigs();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PromptTemplateWizardControllerApi#getTemplateWizardConfigs");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**PromptTemplateWizardConfigs**](PromptTemplateWizardConfigs.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

