# UiTextResourcesControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getI18n**](UiTextResourcesControllerApi.md#getI18n) | **GET** /public/UITextResourcesController | 
[**getUiTextResourcesModule**](UiTextResourcesControllerApi.md#getUiTextResourcesModule) | **GET** /public/UITextResourcesController/getUiTextResourcesModule | 
[**updateUIExistingTexts**](UiTextResourcesControllerApi.md#updateUIExistingTexts) | **POST** /public/UITextResourcesController/updateUIExistingTexts | 

<a name="getI18n"></a>
# **getI18n**
> File getI18n()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UiTextResourcesControllerApi;


UiTextResourcesControllerApi apiInstance = new UiTextResourcesControllerApi();
try {
    File result = apiInstance.getI18n();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UiTextResourcesControllerApi#getI18n");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**File**](File.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="getUiTextResourcesModule"></a>
# **getUiTextResourcesModule**
> UiTextResourcesModule getUiTextResourcesModule()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UiTextResourcesControllerApi;


UiTextResourcesControllerApi apiInstance = new UiTextResourcesControllerApi();
try {
    UiTextResourcesModule result = apiInstance.getUiTextResourcesModule();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UiTextResourcesControllerApi#getUiTextResourcesModule");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**UiTextResourcesModule**](UiTextResourcesModule.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateUIExistingTexts"></a>
# **updateUIExistingTexts**
> updateUIExistingTexts(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UiTextResourcesControllerApi;


UiTextResourcesControllerApi apiInstance = new UiTextResourcesControllerApi();
List<UIExistingText> body = Arrays.asList(new UIExistingText()); // List<UIExistingText> | 
try {
    apiInstance.updateUIExistingTexts(body);
} catch (ApiException e) {
    System.err.println("Exception when calling UiTextResourcesControllerApi#updateUIExistingTexts");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**List&lt;UIExistingText&gt;**](UIExistingText.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

