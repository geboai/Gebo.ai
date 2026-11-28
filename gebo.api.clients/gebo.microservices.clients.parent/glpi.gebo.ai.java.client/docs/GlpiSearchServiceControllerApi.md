# GlpiSearchServiceControllerApi

All URIs are relative to *http://localhost:13022/glpi*

Method | HTTP request | Description
------------- | ------------- | -------------
[**restAggregateGlpi**](GlpiSearchServiceControllerApi.md#restAggregateGlpi) | **POST** /api/users/GlpiSearchServiceController/aggregate | 
[**restCreateCustomTemplateParamsMapGlpi**](GlpiSearchServiceControllerApi.md#restCreateCustomTemplateParamsMapGlpi) | **POST** /api/users/GlpiSearchServiceController/createCustomTemplateParamsMap | 
[**restExtractRelatedAnalisysReferencesGlpi**](GlpiSearchServiceControllerApi.md#restExtractRelatedAnalisysReferencesGlpi) | **POST** /api/users/GlpiSearchServiceController/extractRelatedAnalisysReferences | 
[**restFindSystemByIdGlpi**](GlpiSearchServiceControllerApi.md#restFindSystemByIdGlpi) | **GET** /api/users/GlpiSearchServiceController/findSystemById | 
[**restFindSystemBySearchResultGlpi**](GlpiSearchServiceControllerApi.md#restFindSystemBySearchResultGlpi) | **POST** /api/users/GlpiSearchServiceController/findSystemBySearchResult | 
[**restGetCachedCataloguesGlpi**](GlpiSearchServiceControllerApi.md#restGetCachedCataloguesGlpi) | **GET** /api/users/GlpiSearchServiceController/getCachedCatalogues | 
[**restGetCataloguesListSampleGlpi**](GlpiSearchServiceControllerApi.md#restGetCataloguesListSampleGlpi) | **GET** /api/users/GlpiSearchServiceController/getCataloguesListSample | 
[**restGetDescriptionGlpi**](GlpiSearchServiceControllerApi.md#restGetDescriptionGlpi) | **GET** /api/users/GlpiSearchServiceController/getDescription | 
[**restGetIdGlpi**](GlpiSearchServiceControllerApi.md#restGetIdGlpi) | **GET** /api/users/GlpiSearchServiceController/getId | 
[**restGetMessagingModuleIdGlpi**](GlpiSearchServiceControllerApi.md#restGetMessagingModuleIdGlpi) | **GET** /api/users/GlpiSearchServiceController/getMessagingModuleId | 
[**restGetNativePromptTemplateUseCodeGlpi**](GlpiSearchServiceControllerApi.md#restGetNativePromptTemplateUseCodeGlpi) | **GET** /api/users/GlpiSearchServiceController/getNativePromptTemplateUseCode | 
[**restGetProductIdGlpi**](GlpiSearchServiceControllerApi.md#restGetProductIdGlpi) | **GET** /api/users/GlpiSearchServiceController/getProductId | 
[**restGetQueriesGenerationPromptUseCodeGlpi**](GlpiSearchServiceControllerApi.md#restGetQueriesGenerationPromptUseCodeGlpi) | **GET** /api/users/GlpiSearchServiceController/getQueriesGenerationPromptUseCode | 
[**restGetSearchableSystemsGlpi**](GlpiSearchServiceControllerApi.md#restGetSearchableSystemsGlpi) | **GET** /api/users/GlpiSearchServiceController/getSearchableSystems | 
[**restIsEnabledGlpi**](GlpiSearchServiceControllerApi.md#restIsEnabledGlpi) | **GET** /api/users/GlpiSearchServiceController/isEnabled | 
[**restNativeSearchGlpi**](GlpiSearchServiceControllerApi.md#restNativeSearchGlpi) | **POST** /api/users/GlpiSearchServiceController/nativeSearch | 
[**restSearchGlpi**](GlpiSearchServiceControllerApi.md#restSearchGlpi) | **POST** /api/users/GlpiSearchServiceController/search | 

<a name="restAggregateGlpi"></a>
# **restAggregateGlpi**
> GlpiResultsExtractionData restAggregateGlpi(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.glpi.invoker.ApiException;
//import gebo.microservices.api.client.glpi.api.GlpiSearchServiceControllerApi;


GlpiSearchServiceControllerApi apiInstance = new GlpiSearchServiceControllerApi();
AggregateRequestBodyGlpiResultsExtractionData body = new AggregateRequestBodyGlpiResultsExtractionData(); // AggregateRequestBodyGlpiResultsExtractionData | 
try {
    GlpiResultsExtractionData result = apiInstance.restAggregateGlpi(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiSearchServiceControllerApi#restAggregateGlpi");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**AggregateRequestBodyGlpiResultsExtractionData**](AggregateRequestBodyGlpiResultsExtractionData.md)|  |

### Return type

[**GlpiResultsExtractionData**](GlpiResultsExtractionData.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restCreateCustomTemplateParamsMapGlpi"></a>
# **restCreateCustomTemplateParamsMapGlpi**
> Map&lt;String, Object&gt; restCreateCustomTemplateParamsMapGlpi(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.glpi.invoker.ApiException;
//import gebo.microservices.api.client.glpi.api.GlpiSearchServiceControllerApi;


GlpiSearchServiceControllerApi apiInstance = new GlpiSearchServiceControllerApi();
CustomTemplateParamsRequestBody body = new CustomTemplateParamsRequestBody(); // CustomTemplateParamsRequestBody | 
try {
    Map<String, Object> result = apiInstance.restCreateCustomTemplateParamsMapGlpi(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiSearchServiceControllerApi#restCreateCustomTemplateParamsMapGlpi");
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

<a name="restExtractRelatedAnalisysReferencesGlpi"></a>
# **restExtractRelatedAnalisysReferencesGlpi**
> SearchResultAnalisysOutcome restExtractRelatedAnalisysReferencesGlpi(body, systemId)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.glpi.invoker.ApiException;
//import gebo.microservices.api.client.glpi.api.GlpiSearchServiceControllerApi;


GlpiSearchServiceControllerApi apiInstance = new GlpiSearchServiceControllerApi();
GlpiResultsExtractionData body = new GlpiResultsExtractionData(); // GlpiResultsExtractionData | 
String systemId = "systemId_example"; // String | 
try {
    SearchResultAnalisysOutcome result = apiInstance.restExtractRelatedAnalisysReferencesGlpi(body, systemId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiSearchServiceControllerApi#restExtractRelatedAnalisysReferencesGlpi");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GlpiResultsExtractionData**](GlpiResultsExtractionData.md)|  |
 **systemId** | **String**|  |

### Return type

[**SearchResultAnalisysOutcome**](SearchResultAnalisysOutcome.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restFindSystemByIdGlpi"></a>
# **restFindSystemByIdGlpi**
> SearchableSystemMetaData restFindSystemByIdGlpi(systemId)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.glpi.invoker.ApiException;
//import gebo.microservices.api.client.glpi.api.GlpiSearchServiceControllerApi;


GlpiSearchServiceControllerApi apiInstance = new GlpiSearchServiceControllerApi();
String systemId = "systemId_example"; // String | 
try {
    SearchableSystemMetaData result = apiInstance.restFindSystemByIdGlpi(systemId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiSearchServiceControllerApi#restFindSystemByIdGlpi");
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

<a name="restFindSystemBySearchResultGlpi"></a>
# **restFindSystemBySearchResultGlpi**
> SearchableSystemMetaData restFindSystemBySearchResultGlpi(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.glpi.invoker.ApiException;
//import gebo.microservices.api.client.glpi.api.GlpiSearchServiceControllerApi;


GlpiSearchServiceControllerApi apiInstance = new GlpiSearchServiceControllerApi();
SearchResult body = new SearchResult(); // SearchResult | 
try {
    SearchableSystemMetaData result = apiInstance.restFindSystemBySearchResultGlpi(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiSearchServiceControllerApi#restFindSystemBySearchResultGlpi");
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

<a name="restGetCachedCataloguesGlpi"></a>
# **restGetCachedCataloguesGlpi**
> List&lt;CatalogueSample&gt; restGetCachedCataloguesGlpi(systemConfigurationCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.glpi.invoker.ApiException;
//import gebo.microservices.api.client.glpi.api.GlpiSearchServiceControllerApi;


GlpiSearchServiceControllerApi apiInstance = new GlpiSearchServiceControllerApi();
String systemConfigurationCode = "systemConfigurationCode_example"; // String | 
try {
    List<CatalogueSample> result = apiInstance.restGetCachedCataloguesGlpi(systemConfigurationCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiSearchServiceControllerApi#restGetCachedCataloguesGlpi");
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

<a name="restGetCataloguesListSampleGlpi"></a>
# **restGetCataloguesListSampleGlpi**
> List&lt;CatalogueSample&gt; restGetCataloguesListSampleGlpi(configurationCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.glpi.invoker.ApiException;
//import gebo.microservices.api.client.glpi.api.GlpiSearchServiceControllerApi;


GlpiSearchServiceControllerApi apiInstance = new GlpiSearchServiceControllerApi();
String configurationCode = "configurationCode_example"; // String | 
try {
    List<CatalogueSample> result = apiInstance.restGetCataloguesListSampleGlpi(configurationCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiSearchServiceControllerApi#restGetCataloguesListSampleGlpi");
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

<a name="restGetDescriptionGlpi"></a>
# **restGetDescriptionGlpi**
> String restGetDescriptionGlpi()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.glpi.invoker.ApiException;
//import gebo.microservices.api.client.glpi.api.GlpiSearchServiceControllerApi;


GlpiSearchServiceControllerApi apiInstance = new GlpiSearchServiceControllerApi();
try {
    String result = apiInstance.restGetDescriptionGlpi();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiSearchServiceControllerApi#restGetDescriptionGlpi");
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

<a name="restGetIdGlpi"></a>
# **restGetIdGlpi**
> String restGetIdGlpi()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.glpi.invoker.ApiException;
//import gebo.microservices.api.client.glpi.api.GlpiSearchServiceControllerApi;


GlpiSearchServiceControllerApi apiInstance = new GlpiSearchServiceControllerApi();
try {
    String result = apiInstance.restGetIdGlpi();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiSearchServiceControllerApi#restGetIdGlpi");
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

<a name="restGetMessagingModuleIdGlpi"></a>
# **restGetMessagingModuleIdGlpi**
> String restGetMessagingModuleIdGlpi()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.glpi.invoker.ApiException;
//import gebo.microservices.api.client.glpi.api.GlpiSearchServiceControllerApi;


GlpiSearchServiceControllerApi apiInstance = new GlpiSearchServiceControllerApi();
try {
    String result = apiInstance.restGetMessagingModuleIdGlpi();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiSearchServiceControllerApi#restGetMessagingModuleIdGlpi");
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

<a name="restGetNativePromptTemplateUseCodeGlpi"></a>
# **restGetNativePromptTemplateUseCodeGlpi**
> String restGetNativePromptTemplateUseCodeGlpi()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.glpi.invoker.ApiException;
//import gebo.microservices.api.client.glpi.api.GlpiSearchServiceControllerApi;


GlpiSearchServiceControllerApi apiInstance = new GlpiSearchServiceControllerApi();
try {
    String result = apiInstance.restGetNativePromptTemplateUseCodeGlpi();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiSearchServiceControllerApi#restGetNativePromptTemplateUseCodeGlpi");
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

<a name="restGetProductIdGlpi"></a>
# **restGetProductIdGlpi**
> String restGetProductIdGlpi()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.glpi.invoker.ApiException;
//import gebo.microservices.api.client.glpi.api.GlpiSearchServiceControllerApi;


GlpiSearchServiceControllerApi apiInstance = new GlpiSearchServiceControllerApi();
try {
    String result = apiInstance.restGetProductIdGlpi();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiSearchServiceControllerApi#restGetProductIdGlpi");
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

<a name="restGetQueriesGenerationPromptUseCodeGlpi"></a>
# **restGetQueriesGenerationPromptUseCodeGlpi**
> String restGetQueriesGenerationPromptUseCodeGlpi()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.glpi.invoker.ApiException;
//import gebo.microservices.api.client.glpi.api.GlpiSearchServiceControllerApi;


GlpiSearchServiceControllerApi apiInstance = new GlpiSearchServiceControllerApi();
try {
    String result = apiInstance.restGetQueriesGenerationPromptUseCodeGlpi();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiSearchServiceControllerApi#restGetQueriesGenerationPromptUseCodeGlpi");
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

<a name="restGetSearchableSystemsGlpi"></a>
# **restGetSearchableSystemsGlpi**
> List&lt;SearchableSystemMetaData&gt; restGetSearchableSystemsGlpi()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.glpi.invoker.ApiException;
//import gebo.microservices.api.client.glpi.api.GlpiSearchServiceControllerApi;


GlpiSearchServiceControllerApi apiInstance = new GlpiSearchServiceControllerApi();
try {
    List<SearchableSystemMetaData> result = apiInstance.restGetSearchableSystemsGlpi();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiSearchServiceControllerApi#restGetSearchableSystemsGlpi");
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

<a name="restIsEnabledGlpi"></a>
# **restIsEnabledGlpi**
> Boolean restIsEnabledGlpi()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.glpi.invoker.ApiException;
//import gebo.microservices.api.client.glpi.api.GlpiSearchServiceControllerApi;


GlpiSearchServiceControllerApi apiInstance = new GlpiSearchServiceControllerApi();
try {
    Boolean result = apiInstance.restIsEnabledGlpi();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiSearchServiceControllerApi#restIsEnabledGlpi");
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

<a name="restNativeSearchGlpi"></a>
# **restNativeSearchGlpi**
> List&lt;SearchResult&gt; restNativeSearchGlpi(body, systemId, nEntryLimit, connectTimeoutMillis, readTimeoutMillis, retries)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.glpi.invoker.ApiException;
//import gebo.microservices.api.client.glpi.api.GlpiSearchServiceControllerApi;


GlpiSearchServiceControllerApi apiInstance = new GlpiSearchServiceControllerApi();
GlpiSearchFilter body = new GlpiSearchFilter(); // GlpiSearchFilter | 
String systemId = "systemId_example"; // String | 
Integer nEntryLimit = 56; // Integer | 
Integer connectTimeoutMillis = 56; // Integer | 
Integer readTimeoutMillis = 56; // Integer | 
Integer retries = 56; // Integer | 
try {
    List<SearchResult> result = apiInstance.restNativeSearchGlpi(body, systemId, nEntryLimit, connectTimeoutMillis, readTimeoutMillis, retries);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiSearchServiceControllerApi#restNativeSearchGlpi");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GlpiSearchFilter**](GlpiSearchFilter.md)|  |
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

<a name="restSearchGlpi"></a>
# **restSearchGlpi**
> List&lt;SearchResult&gt; restSearchGlpi(body, systemId, nEntryLimit, connectTimeoutMillis, readTimeoutMillis, retries)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.glpi.invoker.ApiException;
//import gebo.microservices.api.client.glpi.api.GlpiSearchServiceControllerApi;


GlpiSearchServiceControllerApi apiInstance = new GlpiSearchServiceControllerApi();
SearchQuery body = new SearchQuery(); // SearchQuery | 
String systemId = "systemId_example"; // String | 
Integer nEntryLimit = 56; // Integer | 
Integer connectTimeoutMillis = 56; // Integer | 
Integer readTimeoutMillis = 56; // Integer | 
Integer retries = 56; // Integer | 
try {
    List<SearchResult> result = apiInstance.restSearchGlpi(body, systemId, nEntryLimit, connectTimeoutMillis, readTimeoutMillis, retries);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiSearchServiceControllerApi#restSearchGlpi");
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

