# LanguageResourcesControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAllResourcesByLanguage**](LanguageResourcesControllerApi.md#getAllResourcesByLanguage) | **GET** /api/users/LanguageResourcesController | 
[**getUIComponentLabels**](LanguageResourcesControllerApi.md#getUIComponentLabels) | **GET** /api/users/LanguageResourcesController/getUIComponentLabels | 
[**update**](LanguageResourcesControllerApi.md#update) | **POST** /api/users/LanguageResourcesController/update | 

<a name="getAllResourcesByLanguage"></a>
# **getAllResourcesByLanguage**
> List&lt;UIComponent&gt; getAllResourcesByLanguage(langCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.LanguageResourcesControllerApi;


LanguageResourcesControllerApi apiInstance = new LanguageResourcesControllerApi();
String langCode = "langCode_example"; // String | 
try {
    List<UIComponent> result = apiInstance.getAllResourcesByLanguage(langCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LanguageResourcesControllerApi#getAllResourcesByLanguage");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **langCode** | **String**|  |

### Return type

[**List&lt;UIComponent&gt;**](UIComponent.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getUIComponentLabels"></a>
# **getUIComponentLabels**
> UIComponent getUIComponentLabels(id, langCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.LanguageResourcesControllerApi;


LanguageResourcesControllerApi apiInstance = new LanguageResourcesControllerApi();
String id = "id_example"; // String | 
String langCode = "langCode_example"; // String | 
try {
    UIComponent result = apiInstance.getUIComponentLabels(id, langCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LanguageResourcesControllerApi#getUIComponentLabels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **langCode** | **String**|  |

### Return type

[**UIComponent**](UIComponent.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="update"></a>
# **update**
> UIComponent update(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.LanguageResourcesControllerApi;


LanguageResourcesControllerApi apiInstance = new LanguageResourcesControllerApi();
UIComponent body = new UIComponent(); // UIComponent | 
try {
    UIComponent result = apiInstance.update(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LanguageResourcesControllerApi#update");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UIComponent**](UIComponent.md)|  |

### Return type

[**UIComponent**](UIComponent.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

