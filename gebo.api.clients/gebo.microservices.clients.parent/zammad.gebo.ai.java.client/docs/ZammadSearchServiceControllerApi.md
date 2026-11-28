# ZammadSearchServiceControllerApi

All URIs are relative to *http://localhost:13024/zammad*

Method | HTTP request | Description
------------- | ------------- | -------------
[**restAggregateZammad**](ZammadSearchServiceControllerApi.md#restAggregateZammad) | **POST** /api/users/ZammadSearchServiceController/aggregate | 
[**restCreateCustomTemplateParamsMapZammad**](ZammadSearchServiceControllerApi.md#restCreateCustomTemplateParamsMapZammad) | **POST** /api/users/ZammadSearchServiceController/createCustomTemplateParamsMap | 
[**restExtractRelatedAnalisysReferencesZammad**](ZammadSearchServiceControllerApi.md#restExtractRelatedAnalisysReferencesZammad) | **POST** /api/users/ZammadSearchServiceController/extractRelatedAnalisysReferences | 
[**restFindSystemByIdZammad**](ZammadSearchServiceControllerApi.md#restFindSystemByIdZammad) | **GET** /api/users/ZammadSearchServiceController/findSystemById | 
[**restFindSystemBySearchResultZammad**](ZammadSearchServiceControllerApi.md#restFindSystemBySearchResultZammad) | **POST** /api/users/ZammadSearchServiceController/findSystemBySearchResult | 
[**restGetCachedCataloguesZammad**](ZammadSearchServiceControllerApi.md#restGetCachedCataloguesZammad) | **GET** /api/users/ZammadSearchServiceController/getCachedCatalogues | 
[**restGetCataloguesListSampleZammad**](ZammadSearchServiceControllerApi.md#restGetCataloguesListSampleZammad) | **GET** /api/users/ZammadSearchServiceController/getCataloguesListSample | 
[**restGetDescriptionZammad**](ZammadSearchServiceControllerApi.md#restGetDescriptionZammad) | **GET** /api/users/ZammadSearchServiceController/getDescription | 
[**restGetIdZammad**](ZammadSearchServiceControllerApi.md#restGetIdZammad) | **GET** /api/users/ZammadSearchServiceController/getId | 
[**restGetMessagingModuleIdZammad**](ZammadSearchServiceControllerApi.md#restGetMessagingModuleIdZammad) | **GET** /api/users/ZammadSearchServiceController/getMessagingModuleId | 
[**restGetNativePromptTemplateUseCodeZammad**](ZammadSearchServiceControllerApi.md#restGetNativePromptTemplateUseCodeZammad) | **GET** /api/users/ZammadSearchServiceController/getNativePromptTemplateUseCode | 
[**restGetProductIdZammad**](ZammadSearchServiceControllerApi.md#restGetProductIdZammad) | **GET** /api/users/ZammadSearchServiceController/getProductId | 
[**restGetQueriesGenerationPromptUseCodeZammad**](ZammadSearchServiceControllerApi.md#restGetQueriesGenerationPromptUseCodeZammad) | **GET** /api/users/ZammadSearchServiceController/getQueriesGenerationPromptUseCode | 
[**restGetSearchableSystemsZammad**](ZammadSearchServiceControllerApi.md#restGetSearchableSystemsZammad) | **GET** /api/users/ZammadSearchServiceController/getSearchableSystems | 
[**restIsEnabledZammad**](ZammadSearchServiceControllerApi.md#restIsEnabledZammad) | **GET** /api/users/ZammadSearchServiceController/isEnabled | 
[**restNativeSearchZammad**](ZammadSearchServiceControllerApi.md#restNativeSearchZammad) | **POST** /api/users/ZammadSearchServiceController/nativeSearch | 
[**restSearchZammad**](ZammadSearchServiceControllerApi.md#restSearchZammad) | **POST** /api/users/ZammadSearchServiceController/search | 

<a name="restAggregateZammad"></a>
# **restAggregateZammad**
> ZammadResultsExtractionData restAggregateZammad(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadSearchServiceControllerApi;


ZammadSearchServiceControllerApi apiInstance = new ZammadSearchServiceControllerApi();
AggregateRequestBodyZammadResultsExtractionData body = new AggregateRequestBodyZammadResultsExtractionData(); // AggregateRequestBodyZammadResultsExtractionData | 
try {
    ZammadResultsExtractionData result = apiInstance.restAggregateZammad(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadSearchServiceControllerApi#restAggregateZammad");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**AggregateRequestBodyZammadResultsExtractionData**](AggregateRequestBodyZammadResultsExtractionData.md)|  |

### Return type

[**ZammadResultsExtractionData**](ZammadResultsExtractionData.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restCreateCustomTemplateParamsMapZammad"></a>
# **restCreateCustomTemplateParamsMapZammad**
> Map&lt;String, Object&gt; restCreateCustomTemplateParamsMapZammad(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadSearchServiceControllerApi;


ZammadSearchServiceControllerApi apiInstance = new ZammadSearchServiceControllerApi();
CustomTemplateParamsRequestBody body = new CustomTemplateParamsRequestBody(); // CustomTemplateParamsRequestBody | 
try {
    Map<String, Object> result = apiInstance.restCreateCustomTemplateParamsMapZammad(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadSearchServiceControllerApi#restCreateCustomTemplateParamsMapZammad");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**CustomTemplateParamsRequestBody**](CustomTemplateParamsRequestBody.md)|  |

### Return type

**Map&lt;String, Object&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restExtractRelatedAnalisysReferencesZammad"></a>
# **restExtractRelatedAnalisysReferencesZammad**
> SearchResultAnalisysOutcome restExtractRelatedAnalisysReferencesZammad(body, systemId)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadSearchServiceControllerApi;


ZammadSearchServiceControllerApi apiInstance = new ZammadSearchServiceControllerApi();
ZammadResultsExtractionData body = new ZammadResultsExtractionData(); // ZammadResultsExtractionData | 
String systemId = "systemId_example"; // String | 
try {
    SearchResultAnalisysOutcome result = apiInstance.restExtractRelatedAnalisysReferencesZammad(body, systemId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadSearchServiceControllerApi#restExtractRelatedAnalisysReferencesZammad");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ZammadResultsExtractionData**](ZammadResultsExtractionData.md)|  |
 **systemId** | **String**|  |

### Return type

[**SearchResultAnalisysOutcome**](SearchResultAnalisysOutcome.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restFindSystemByIdZammad"></a>
# **restFindSystemByIdZammad**
> SearchableSystemMetaData restFindSystemByIdZammad(systemId)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadSearchServiceControllerApi;


ZammadSearchServiceControllerApi apiInstance = new ZammadSearchServiceControllerApi();
String systemId = "systemId_example"; // String | 
try {
    SearchableSystemMetaData result = apiInstance.restFindSystemByIdZammad(systemId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadSearchServiceControllerApi#restFindSystemByIdZammad");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **systemId** | **String**|  |

### Return type

[**SearchableSystemMetaData**](SearchableSystemMetaData.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="restFindSystemBySearchResultZammad"></a>
# **restFindSystemBySearchResultZammad**
> SearchableSystemMetaData restFindSystemBySearchResultZammad(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadSearchServiceControllerApi;


ZammadSearchServiceControllerApi apiInstance = new ZammadSearchServiceControllerApi();
SearchResult body = new SearchResult(); // SearchResult | 
try {
    SearchableSystemMetaData result = apiInstance.restFindSystemBySearchResultZammad(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadSearchServiceControllerApi#restFindSystemBySearchResultZammad");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SearchResult**](SearchResult.md)|  |

### Return type

[**SearchableSystemMetaData**](SearchableSystemMetaData.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restGetCachedCataloguesZammad"></a>
# **restGetCachedCataloguesZammad**
> List&lt;CatalogueSample&gt; restGetCachedCataloguesZammad(systemConfigurationCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadSearchServiceControllerApi;


ZammadSearchServiceControllerApi apiInstance = new ZammadSearchServiceControllerApi();
String systemConfigurationCode = "systemConfigurationCode_example"; // String | 
try {
    List<CatalogueSample> result = apiInstance.restGetCachedCataloguesZammad(systemConfigurationCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadSearchServiceControllerApi#restGetCachedCataloguesZammad");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **systemConfigurationCode** | **String**|  | [optional]

### Return type

[**List&lt;CatalogueSample&gt;**](CatalogueSample.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="restGetCataloguesListSampleZammad"></a>
# **restGetCataloguesListSampleZammad**
> List&lt;CatalogueSample&gt; restGetCataloguesListSampleZammad(configurationCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadSearchServiceControllerApi;


ZammadSearchServiceControllerApi apiInstance = new ZammadSearchServiceControllerApi();
String configurationCode = "configurationCode_example"; // String | 
try {
    List<CatalogueSample> result = apiInstance.restGetCataloguesListSampleZammad(configurationCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadSearchServiceControllerApi#restGetCataloguesListSampleZammad");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **configurationCode** | **String**|  |

### Return type

[**List&lt;CatalogueSample&gt;**](CatalogueSample.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="restGetDescriptionZammad"></a>
# **restGetDescriptionZammad**
> String restGetDescriptionZammad()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadSearchServiceControllerApi;


ZammadSearchServiceControllerApi apiInstance = new ZammadSearchServiceControllerApi();
try {
    String result = apiInstance.restGetDescriptionZammad();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadSearchServiceControllerApi#restGetDescriptionZammad");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="restGetIdZammad"></a>
# **restGetIdZammad**
> String restGetIdZammad()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadSearchServiceControllerApi;


ZammadSearchServiceControllerApi apiInstance = new ZammadSearchServiceControllerApi();
try {
    String result = apiInstance.restGetIdZammad();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadSearchServiceControllerApi#restGetIdZammad");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="restGetMessagingModuleIdZammad"></a>
# **restGetMessagingModuleIdZammad**
> String restGetMessagingModuleIdZammad()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadSearchServiceControllerApi;


ZammadSearchServiceControllerApi apiInstance = new ZammadSearchServiceControllerApi();
try {
    String result = apiInstance.restGetMessagingModuleIdZammad();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadSearchServiceControllerApi#restGetMessagingModuleIdZammad");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="restGetNativePromptTemplateUseCodeZammad"></a>
# **restGetNativePromptTemplateUseCodeZammad**
> String restGetNativePromptTemplateUseCodeZammad()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadSearchServiceControllerApi;


ZammadSearchServiceControllerApi apiInstance = new ZammadSearchServiceControllerApi();
try {
    String result = apiInstance.restGetNativePromptTemplateUseCodeZammad();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadSearchServiceControllerApi#restGetNativePromptTemplateUseCodeZammad");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="restGetProductIdZammad"></a>
# **restGetProductIdZammad**
> String restGetProductIdZammad()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadSearchServiceControllerApi;


ZammadSearchServiceControllerApi apiInstance = new ZammadSearchServiceControllerApi();
try {
    String result = apiInstance.restGetProductIdZammad();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadSearchServiceControllerApi#restGetProductIdZammad");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="restGetQueriesGenerationPromptUseCodeZammad"></a>
# **restGetQueriesGenerationPromptUseCodeZammad**
> String restGetQueriesGenerationPromptUseCodeZammad()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadSearchServiceControllerApi;


ZammadSearchServiceControllerApi apiInstance = new ZammadSearchServiceControllerApi();
try {
    String result = apiInstance.restGetQueriesGenerationPromptUseCodeZammad();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadSearchServiceControllerApi#restGetQueriesGenerationPromptUseCodeZammad");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="restGetSearchableSystemsZammad"></a>
# **restGetSearchableSystemsZammad**
> List&lt;SearchableSystemMetaData&gt; restGetSearchableSystemsZammad()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadSearchServiceControllerApi;


ZammadSearchServiceControllerApi apiInstance = new ZammadSearchServiceControllerApi();
try {
    List<SearchableSystemMetaData> result = apiInstance.restGetSearchableSystemsZammad();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadSearchServiceControllerApi#restGetSearchableSystemsZammad");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;SearchableSystemMetaData&gt;**](SearchableSystemMetaData.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="restIsEnabledZammad"></a>
# **restIsEnabledZammad**
> Boolean restIsEnabledZammad()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadSearchServiceControllerApi;


ZammadSearchServiceControllerApi apiInstance = new ZammadSearchServiceControllerApi();
try {
    Boolean result = apiInstance.restIsEnabledZammad();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadSearchServiceControllerApi#restIsEnabledZammad");
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
 - **Accept**: */*

<a name="restNativeSearchZammad"></a>
# **restNativeSearchZammad**
> List&lt;SearchResult&gt; restNativeSearchZammad(body, systemId, nEntryLimit, connectTimeoutMillis, readTimeoutMillis, retries)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadSearchServiceControllerApi;


ZammadSearchServiceControllerApi apiInstance = new ZammadSearchServiceControllerApi();
ZammadTicketsSearchFilter body = new ZammadTicketsSearchFilter(); // ZammadTicketsSearchFilter | 
String systemId = "systemId_example"; // String | 
Integer nEntryLimit = 56; // Integer | 
Integer connectTimeoutMillis = 56; // Integer | 
Integer readTimeoutMillis = 56; // Integer | 
Integer retries = 56; // Integer | 
try {
    List<SearchResult> result = apiInstance.restNativeSearchZammad(body, systemId, nEntryLimit, connectTimeoutMillis, readTimeoutMillis, retries);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadSearchServiceControllerApi#restNativeSearchZammad");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ZammadTicketsSearchFilter**](ZammadTicketsSearchFilter.md)|  |
 **systemId** | **String**|  |
 **nEntryLimit** | **Integer**|  |
 **connectTimeoutMillis** | **Integer**|  | [optional]
 **readTimeoutMillis** | **Integer**|  | [optional]
 **retries** | **Integer**|  | [optional]

### Return type

[**List&lt;SearchResult&gt;**](SearchResult.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restSearchZammad"></a>
# **restSearchZammad**
> List&lt;SearchResult&gt; restSearchZammad(body, systemId, nEntryLimit, connectTimeoutMillis, readTimeoutMillis, retries)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadSearchServiceControllerApi;


ZammadSearchServiceControllerApi apiInstance = new ZammadSearchServiceControllerApi();
SearchQuery body = new SearchQuery(); // SearchQuery | 
String systemId = "systemId_example"; // String | 
Integer nEntryLimit = 56; // Integer | 
Integer connectTimeoutMillis = 56; // Integer | 
Integer readTimeoutMillis = 56; // Integer | 
Integer retries = 56; // Integer | 
try {
    List<SearchResult> result = apiInstance.restSearchZammad(body, systemId, nEntryLimit, connectTimeoutMillis, readTimeoutMillis, retries);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadSearchServiceControllerApi#restSearchZammad");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SearchQuery**](SearchQuery.md)|  |
 **systemId** | **String**|  |
 **nEntryLimit** | **Integer**|  |
 **connectTimeoutMillis** | **Integer**|  | [optional]
 **readTimeoutMillis** | **Integer**|  | [optional]
 **retries** | **Integer**|  | [optional]

### Return type

[**List&lt;SearchResult&gt;**](SearchResult.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

