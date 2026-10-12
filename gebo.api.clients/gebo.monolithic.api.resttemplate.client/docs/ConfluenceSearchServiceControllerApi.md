# ConfluenceSearchServiceControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**restAggregateConfluence**](ConfluenceSearchServiceControllerApi.md#restAggregateConfluence) | **POST** /api/users/ConfluenceSearchServiceController/aggregate | 
[**restCreateCustomTemplateParamsMapConfluence**](ConfluenceSearchServiceControllerApi.md#restCreateCustomTemplateParamsMapConfluence) | **POST** /api/users/ConfluenceSearchServiceController/createCustomTemplateParamsMap | 
[**restExtractRelatedAnalisysReferencesConfluence**](ConfluenceSearchServiceControllerApi.md#restExtractRelatedAnalisysReferencesConfluence) | **POST** /api/users/ConfluenceSearchServiceController/extractRelatedAnalisysReferences | 
[**restFindSystemByIdConfluence**](ConfluenceSearchServiceControllerApi.md#restFindSystemByIdConfluence) | **GET** /api/users/ConfluenceSearchServiceController/findSystemById | 
[**restFindSystemBySearchResultConfluence**](ConfluenceSearchServiceControllerApi.md#restFindSystemBySearchResultConfluence) | **POST** /api/users/ConfluenceSearchServiceController/findSystemBySearchResult | 
[**restGetCachedCataloguesConfluence**](ConfluenceSearchServiceControllerApi.md#restGetCachedCataloguesConfluence) | **GET** /api/users/ConfluenceSearchServiceController/getCachedCatalogues | 
[**restGetCataloguesListSampleConfluence**](ConfluenceSearchServiceControllerApi.md#restGetCataloguesListSampleConfluence) | **GET** /api/users/ConfluenceSearchServiceController/getCataloguesListSample | 
[**restGetDescriptionConfluence**](ConfluenceSearchServiceControllerApi.md#restGetDescriptionConfluence) | **GET** /api/users/ConfluenceSearchServiceController/getDescription | 
[**restGetIdConfluence**](ConfluenceSearchServiceControllerApi.md#restGetIdConfluence) | **GET** /api/users/ConfluenceSearchServiceController/getId | 
[**restGetMessagingModuleIdConfluence**](ConfluenceSearchServiceControllerApi.md#restGetMessagingModuleIdConfluence) | **GET** /api/users/ConfluenceSearchServiceController/getMessagingModuleId | 
[**restGetNativePromptTemplateUseCodeConfluence**](ConfluenceSearchServiceControllerApi.md#restGetNativePromptTemplateUseCodeConfluence) | **GET** /api/users/ConfluenceSearchServiceController/getNativePromptTemplateUseCode | 
[**restGetProductIdConfluence**](ConfluenceSearchServiceControllerApi.md#restGetProductIdConfluence) | **GET** /api/users/ConfluenceSearchServiceController/getProductId | 
[**restGetQueriesGenerationPromptUseCodeConfluence**](ConfluenceSearchServiceControllerApi.md#restGetQueriesGenerationPromptUseCodeConfluence) | **GET** /api/users/ConfluenceSearchServiceController/getQueriesGenerationPromptUseCode | 
[**restGetSearchableSystemsConfluence**](ConfluenceSearchServiceControllerApi.md#restGetSearchableSystemsConfluence) | **GET** /api/users/ConfluenceSearchServiceController/getSearchableSystems | 
[**restIsEnabledConfluence**](ConfluenceSearchServiceControllerApi.md#restIsEnabledConfluence) | **GET** /api/users/ConfluenceSearchServiceController/isEnabled | 
[**restNativeSearchConfluence**](ConfluenceSearchServiceControllerApi.md#restNativeSearchConfluence) | **POST** /api/users/ConfluenceSearchServiceController/nativeSearch | 
[**restSearchConfluence**](ConfluenceSearchServiceControllerApi.md#restSearchConfluence) | **POST** /api/users/ConfluenceSearchServiceController/search | 

<a name="restAggregateConfluence"></a>
# **restAggregateConfluence**
> ConfluenceResultsExtractionData restAggregateConfluence(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
AggregateRequestBodyConfluenceResultsExtractionData body = new AggregateRequestBodyConfluenceResultsExtractionData(); // AggregateRequestBodyConfluenceResultsExtractionData | 
try {
    ConfluenceResultsExtractionData result = apiInstance.restAggregateConfluence(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restAggregateConfluence");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**AggregateRequestBodyConfluenceResultsExtractionData**](AggregateRequestBodyConfluenceResultsExtractionData.md)|  |

### Return type

[**ConfluenceResultsExtractionData**](ConfluenceResultsExtractionData.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restCreateCustomTemplateParamsMapConfluence"></a>
# **restCreateCustomTemplateParamsMapConfluence**
> Map&lt;String, Object&gt; restCreateCustomTemplateParamsMapConfluence(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
CustomTemplateParamsRequestBody body = new CustomTemplateParamsRequestBody(); // CustomTemplateParamsRequestBody | 
try {
    Map<String, Object> result = apiInstance.restCreateCustomTemplateParamsMapConfluence(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restCreateCustomTemplateParamsMapConfluence");
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

<a name="restExtractRelatedAnalisysReferencesConfluence"></a>
# **restExtractRelatedAnalisysReferencesConfluence**
> SearchResultAnalisysOutcome restExtractRelatedAnalisysReferencesConfluence(body, systemId)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
ConfluenceResultsExtractionData body = new ConfluenceResultsExtractionData(); // ConfluenceResultsExtractionData | 
String systemId = "systemId_example"; // String | 
try {
    SearchResultAnalisysOutcome result = apiInstance.restExtractRelatedAnalisysReferencesConfluence(body, systemId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restExtractRelatedAnalisysReferencesConfluence");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ConfluenceResultsExtractionData**](ConfluenceResultsExtractionData.md)|  |
 **systemId** | **String**|  |

### Return type

[**SearchResultAnalisysOutcome**](SearchResultAnalisysOutcome.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restFindSystemByIdConfluence"></a>
# **restFindSystemByIdConfluence**
> SearchableSystemMetaData restFindSystemByIdConfluence(systemId)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
String systemId = "systemId_example"; // String | 
try {
    SearchableSystemMetaData result = apiInstance.restFindSystemByIdConfluence(systemId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restFindSystemByIdConfluence");
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

<a name="restFindSystemBySearchResultConfluence"></a>
# **restFindSystemBySearchResultConfluence**
> SearchableSystemMetaData restFindSystemBySearchResultConfluence(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
SearchResult body = new SearchResult(); // SearchResult | 
try {
    SearchableSystemMetaData result = apiInstance.restFindSystemBySearchResultConfluence(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restFindSystemBySearchResultConfluence");
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

<a name="restGetCachedCataloguesConfluence"></a>
# **restGetCachedCataloguesConfluence**
> List&lt;CatalogueSample&gt; restGetCachedCataloguesConfluence(systemConfigurationCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
String systemConfigurationCode = "systemConfigurationCode_example"; // String | 
try {
    List<CatalogueSample> result = apiInstance.restGetCachedCataloguesConfluence(systemConfigurationCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restGetCachedCataloguesConfluence");
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

<a name="restGetCataloguesListSampleConfluence"></a>
# **restGetCataloguesListSampleConfluence**
> List&lt;CatalogueSample&gt; restGetCataloguesListSampleConfluence(configurationCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
String configurationCode = "configurationCode_example"; // String | 
try {
    List<CatalogueSample> result = apiInstance.restGetCataloguesListSampleConfluence(configurationCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restGetCataloguesListSampleConfluence");
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

<a name="restGetDescriptionConfluence"></a>
# **restGetDescriptionConfluence**
> String restGetDescriptionConfluence()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
try {
    String result = apiInstance.restGetDescriptionConfluence();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restGetDescriptionConfluence");
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

<a name="restGetIdConfluence"></a>
# **restGetIdConfluence**
> String restGetIdConfluence()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
try {
    String result = apiInstance.restGetIdConfluence();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restGetIdConfluence");
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

<a name="restGetMessagingModuleIdConfluence"></a>
# **restGetMessagingModuleIdConfluence**
> String restGetMessagingModuleIdConfluence()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
try {
    String result = apiInstance.restGetMessagingModuleIdConfluence();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restGetMessagingModuleIdConfluence");
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

<a name="restGetNativePromptTemplateUseCodeConfluence"></a>
# **restGetNativePromptTemplateUseCodeConfluence**
> String restGetNativePromptTemplateUseCodeConfluence()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
try {
    String result = apiInstance.restGetNativePromptTemplateUseCodeConfluence();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restGetNativePromptTemplateUseCodeConfluence");
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

<a name="restGetProductIdConfluence"></a>
# **restGetProductIdConfluence**
> String restGetProductIdConfluence()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
try {
    String result = apiInstance.restGetProductIdConfluence();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restGetProductIdConfluence");
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

<a name="restGetQueriesGenerationPromptUseCodeConfluence"></a>
# **restGetQueriesGenerationPromptUseCodeConfluence**
> String restGetQueriesGenerationPromptUseCodeConfluence()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
try {
    String result = apiInstance.restGetQueriesGenerationPromptUseCodeConfluence();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restGetQueriesGenerationPromptUseCodeConfluence");
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

<a name="restGetSearchableSystemsConfluence"></a>
# **restGetSearchableSystemsConfluence**
> List&lt;SearchableSystemMetaData&gt; restGetSearchableSystemsConfluence()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
try {
    List<SearchableSystemMetaData> result = apiInstance.restGetSearchableSystemsConfluence();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restGetSearchableSystemsConfluence");
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

<a name="restIsEnabledConfluence"></a>
# **restIsEnabledConfluence**
> Boolean restIsEnabledConfluence()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
try {
    Boolean result = apiInstance.restIsEnabledConfluence();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restIsEnabledConfluence");
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

<a name="restNativeSearchConfluence"></a>
# **restNativeSearchConfluence**
> List&lt;SearchResult&gt; restNativeSearchConfluence(body, systemId, nEntryLimit, connectTimeoutMillis, readTimeoutMillis, retries)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
ConfluenceContentSearchFilter body = new ConfluenceContentSearchFilter(); // ConfluenceContentSearchFilter | 
String systemId = "systemId_example"; // String | 
Integer nEntryLimit = 56; // Integer | 
Integer connectTimeoutMillis = 56; // Integer | 
Integer readTimeoutMillis = 56; // Integer | 
Integer retries = 56; // Integer | 
try {
    List<SearchResult> result = apiInstance.restNativeSearchConfluence(body, systemId, nEntryLimit, connectTimeoutMillis, readTimeoutMillis, retries);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restNativeSearchConfluence");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ConfluenceContentSearchFilter**](ConfluenceContentSearchFilter.md)|  |
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

<a name="restSearchConfluence"></a>
# **restSearchConfluence**
> List&lt;SearchResult&gt; restSearchConfluence(body, systemId, nEntryLimit, connectTimeoutMillis, readTimeoutMillis, retries)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
SearchQuery body = new SearchQuery(); // SearchQuery | 
String systemId = "systemId_example"; // String | 
Integer nEntryLimit = 56; // Integer | 
Integer connectTimeoutMillis = 56; // Integer | 
Integer readTimeoutMillis = 56; // Integer | 
Integer retries = 56; // Integer | 
try {
    List<SearchResult> result = apiInstance.restSearchConfluence(body, systemId, nEntryLimit, connectTimeoutMillis, readTimeoutMillis, retries);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restSearchConfluence");
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

