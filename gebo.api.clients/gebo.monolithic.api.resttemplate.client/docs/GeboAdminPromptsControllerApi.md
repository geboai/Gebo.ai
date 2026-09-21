# GeboAdminPromptsControllerApi

All URIs are relative to *http://localhost:12998*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deletePromptConfig**](GeboAdminPromptsControllerApi.md#deletePromptConfig) | **POST** /api/admin/GeboAdminPromptsController/deletePromptConfig | 
[**findGPromptUseInfoByUseCode**](GeboAdminPromptsControllerApi.md#findGPromptUseInfoByUseCode) | **GET** /api/admin/GeboAdminPromptsController/findGPromptUseInfoByUseCode | 
[**findPromptConfigByCode**](GeboAdminPromptsControllerApi.md#findPromptConfigByCode) | **GET** /api/admin/GeboAdminPromptsController/findPromptConfigByCode | 
[**getAllPromptConfigsLightList**](GeboAdminPromptsControllerApi.md#getAllPromptConfigsLightList) | **GET** /api/admin/GeboAdminPromptsController/getAllPromptConfigsLightList | 
[**getPromptCategories**](GeboAdminPromptsControllerApi.md#getPromptCategories) | **GET** /api/admin/GeboAdminPromptsController/getPromptCategories | 
[**getPromptConfigByFilter**](GeboAdminPromptsControllerApi.md#getPromptConfigByFilter) | **POST** /api/admin/GeboAdminPromptsController/getPromptConfigByFilter | 
[**insertPromptConfig**](GeboAdminPromptsControllerApi.md#insertPromptConfig) | **POST** /api/admin/GeboAdminPromptsController/insertPromptConfig | 
[**isPromptTemplateEditingEnabled**](GeboAdminPromptsControllerApi.md#isPromptTemplateEditingEnabled) | **GET** /api/admin/GeboAdminPromptsController/isPromptTemplateEditingEnabled | 
[**updatePromptConfig**](GeboAdminPromptsControllerApi.md#updatePromptConfig) | **POST** /api/admin/GeboAdminPromptsController/updatePromptConfig | 

<a name="deletePromptConfig"></a>
# **deletePromptConfig**
> deletePromptConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAdminPromptsControllerApi;


GeboAdminPromptsControllerApi apiInstance = new GeboAdminPromptsControllerApi();
GPromptTemplateConfig body = new GPromptTemplateConfig(); // GPromptTemplateConfig | 
try {
    apiInstance.deletePromptConfig(body);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminPromptsControllerApi#deletePromptConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GPromptTemplateConfig**](GPromptTemplateConfig.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="findGPromptUseInfoByUseCode"></a>
# **findGPromptUseInfoByUseCode**
> GPromptUseInfo findGPromptUseInfoByUseCode(useCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAdminPromptsControllerApi;


GeboAdminPromptsControllerApi apiInstance = new GeboAdminPromptsControllerApi();
String useCode = "useCode_example"; // String | 
try {
    GPromptUseInfo result = apiInstance.findGPromptUseInfoByUseCode(useCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminPromptsControllerApi#findGPromptUseInfoByUseCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **useCode** | **String**|  |

### Return type

[**GPromptUseInfo**](GPromptUseInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findPromptConfigByCode"></a>
# **findPromptConfigByCode**
> GPromptTemplateConfig findPromptConfigByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAdminPromptsControllerApi;


GeboAdminPromptsControllerApi apiInstance = new GeboAdminPromptsControllerApi();
String code = "code_example"; // String | 
try {
    GPromptTemplateConfig result = apiInstance.findPromptConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminPromptsControllerApi#findPromptConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GPromptTemplateConfig**](GPromptTemplateConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAllPromptConfigsLightList"></a>
# **getAllPromptConfigsLightList**
> List&lt;GPromptTemplateLightView&gt; getAllPromptConfigsLightList()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAdminPromptsControllerApi;


GeboAdminPromptsControllerApi apiInstance = new GeboAdminPromptsControllerApi();
try {
    List<GPromptTemplateLightView> result = apiInstance.getAllPromptConfigsLightList();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminPromptsControllerApi#getAllPromptConfigsLightList");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GPromptTemplateLightView&gt;**](GPromptTemplateLightView.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPromptCategories"></a>
# **getPromptCategories**
> List&lt;String&gt; getPromptCategories()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAdminPromptsControllerApi;


GeboAdminPromptsControllerApi apiInstance = new GeboAdminPromptsControllerApi();
try {
    List<String> result = apiInstance.getPromptCategories();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminPromptsControllerApi#getPromptCategories");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**List&lt;String&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPromptConfigByFilter"></a>
# **getPromptConfigByFilter**
> GPromptTemplateConfig getPromptConfigByFilter(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAdminPromptsControllerApi;


GeboAdminPromptsControllerApi apiInstance = new GeboAdminPromptsControllerApi();
PromptFilter body = new PromptFilter(); // PromptFilter | 
try {
    GPromptTemplateConfig result = apiInstance.getPromptConfigByFilter(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminPromptsControllerApi#getPromptConfigByFilter");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**PromptFilter**](PromptFilter.md)|  |

### Return type

[**GPromptTemplateConfig**](GPromptTemplateConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertPromptConfig"></a>
# **insertPromptConfig**
> GPromptTemplateConfig insertPromptConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAdminPromptsControllerApi;


GeboAdminPromptsControllerApi apiInstance = new GeboAdminPromptsControllerApi();
GPromptTemplateConfig body = new GPromptTemplateConfig(); // GPromptTemplateConfig | 
try {
    GPromptTemplateConfig result = apiInstance.insertPromptConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminPromptsControllerApi#insertPromptConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GPromptTemplateConfig**](GPromptTemplateConfig.md)|  |

### Return type

[**GPromptTemplateConfig**](GPromptTemplateConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="isPromptTemplateEditingEnabled"></a>
# **isPromptTemplateEditingEnabled**
> Boolean isPromptTemplateEditingEnabled()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAdminPromptsControllerApi;


GeboAdminPromptsControllerApi apiInstance = new GeboAdminPromptsControllerApi();
try {
    Boolean result = apiInstance.isPromptTemplateEditingEnabled();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminPromptsControllerApi#isPromptTemplateEditingEnabled");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Boolean**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updatePromptConfig"></a>
# **updatePromptConfig**
> GPromptTemplateConfig updatePromptConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAdminPromptsControllerApi;


GeboAdminPromptsControllerApi apiInstance = new GeboAdminPromptsControllerApi();
GPromptTemplateConfig body = new GPromptTemplateConfig(); // GPromptTemplateConfig | 
try {
    GPromptTemplateConfig result = apiInstance.updatePromptConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminPromptsControllerApi#updatePromptConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GPromptTemplateConfig**](GPromptTemplateConfig.md)|  |

### Return type

[**GPromptTemplateConfig**](GPromptTemplateConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

