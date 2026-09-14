# ConfluenceSearchServiceControllerApi

All URIs are relative to *http://localhost:13010/confluence*

Method | HTTP request | Description
------------- | ------------- | -------------
[**restAggregate**](ConfluenceSearchServiceControllerApi.md#restAggregate) | **POST** /api/users/ConfluenceSearchServiceController/aggregate | 
[**restCreateCustomTemplateParamsMap**](ConfluenceSearchServiceControllerApi.md#restCreateCustomTemplateParamsMap) | **POST** /api/users/ConfluenceSearchServiceController/createCustomTemplateParamsMap | 
[**restExtractRelatedAnalisysReferences**](ConfluenceSearchServiceControllerApi.md#restExtractRelatedAnalisysReferences) | **POST** /api/users/ConfluenceSearchServiceController/extractRelatedAnalisysReferences | 
[**restFindSystemById**](ConfluenceSearchServiceControllerApi.md#restFindSystemById) | **GET** /api/users/ConfluenceSearchServiceController/findSystemById | 
[**restFindSystemBySearchResult**](ConfluenceSearchServiceControllerApi.md#restFindSystemBySearchResult) | **POST** /api/users/ConfluenceSearchServiceController/findSystemBySearchResult | 
[**restGetCachedCatalogues**](ConfluenceSearchServiceControllerApi.md#restGetCachedCatalogues) | **GET** /api/users/ConfluenceSearchServiceController/getCachedCatalogues | 
[**restGetCataloguesListSample**](ConfluenceSearchServiceControllerApi.md#restGetCataloguesListSample) | **GET** /api/users/ConfluenceSearchServiceController/getCataloguesListSample | 
[**restGetDescription**](ConfluenceSearchServiceControllerApi.md#restGetDescription) | **GET** /api/users/ConfluenceSearchServiceController/getDescription | 
[**restGetId**](ConfluenceSearchServiceControllerApi.md#restGetId) | **GET** /api/users/ConfluenceSearchServiceController/getId | 
[**restGetMessagingModuleId**](ConfluenceSearchServiceControllerApi.md#restGetMessagingModuleId) | **GET** /api/users/ConfluenceSearchServiceController/getMessagingModuleId | 
[**restGetNativePromptTemplateUseCode**](ConfluenceSearchServiceControllerApi.md#restGetNativePromptTemplateUseCode) | **GET** /api/users/ConfluenceSearchServiceController/getNativePromptTemplateUseCode | 
[**restGetProductId**](ConfluenceSearchServiceControllerApi.md#restGetProductId) | **GET** /api/users/ConfluenceSearchServiceController/getProductId | 
[**restGetQueriesGenerationPromptUseCode**](ConfluenceSearchServiceControllerApi.md#restGetQueriesGenerationPromptUseCode) | **GET** /api/users/ConfluenceSearchServiceController/getQueriesGenerationPromptUseCode | 
[**restGetSearchableSystems**](ConfluenceSearchServiceControllerApi.md#restGetSearchableSystems) | **GET** /api/users/ConfluenceSearchServiceController/getSearchableSystems | 
[**restIsEnabled**](ConfluenceSearchServiceControllerApi.md#restIsEnabled) | **GET** /api/users/ConfluenceSearchServiceController/isEnabled | 
[**restNativeSearch**](ConfluenceSearchServiceControllerApi.md#restNativeSearch) | **POST** /api/users/ConfluenceSearchServiceController/nativeSearch | 
[**restSearch**](ConfluenceSearchServiceControllerApi.md#restSearch) | **POST** /api/users/ConfluenceSearchServiceController/search | 

<a name="restAggregate"></a>
# **restAggregate**
> ConfluenceResultsExtractionData restAggregate(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.confluence.invoker.ApiException;
//import gebo.microservices.api.client.confluence.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
AggregateRequestBodyConfluenceResultsExtractionData body = new AggregateRequestBodyConfluenceResultsExtractionData(); // AggregateRequestBodyConfluenceResultsExtractionData | 
try {
    ConfluenceResultsExtractionData result = apiInstance.restAggregate(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restAggregate");
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

<a name="restCreateCustomTemplateParamsMap"></a>
# **restCreateCustomTemplateParamsMap**
> Map&lt;String, Object&gt; restCreateCustomTemplateParamsMap(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.confluence.invoker.ApiException;
//import gebo.microservices.api.client.confluence.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
CustomTemplateParamsRequestBody body = new CustomTemplateParamsRequestBody(); // CustomTemplateParamsRequestBody | 
try {
    Map<String, Object> result = apiInstance.restCreateCustomTemplateParamsMap(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restCreateCustomTemplateParamsMap");
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

<a name="restExtractRelatedAnalisysReferences"></a>
# **restExtractRelatedAnalisysReferences**
> SearchResultAnalisysOutcome restExtractRelatedAnalisysReferences(body, systemId)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.confluence.invoker.ApiException;
//import gebo.microservices.api.client.confluence.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
ConfluenceResultsExtractionData body = new ConfluenceResultsExtractionData(); // ConfluenceResultsExtractionData | 
String systemId = "systemId_example"; // String | 
try {
    SearchResultAnalisysOutcome result = apiInstance.restExtractRelatedAnalisysReferences(body, systemId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restExtractRelatedAnalisysReferences");
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

<a name="restFindSystemById"></a>
# **restFindSystemById**
> SearchableSystemMetaData restFindSystemById(systemId)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.confluence.invoker.ApiException;
//import gebo.microservices.api.client.confluence.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
String systemId = "systemId_example"; // String | 
try {
    SearchableSystemMetaData result = apiInstance.restFindSystemById(systemId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restFindSystemById");
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

<a name="restFindSystemBySearchResult"></a>
# **restFindSystemBySearchResult**
> SearchableSystemMetaData restFindSystemBySearchResult(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.confluence.invoker.ApiException;
//import gebo.microservices.api.client.confluence.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
SearchResult body = new SearchResult(); // SearchResult | 
try {
    SearchableSystemMetaData result = apiInstance.restFindSystemBySearchResult(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restFindSystemBySearchResult");
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

<a name="restGetCachedCatalogues"></a>
# **restGetCachedCatalogues**
> List&lt;CatalogueSample&gt; restGetCachedCatalogues(systemConfigurationCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.confluence.invoker.ApiException;
//import gebo.microservices.api.client.confluence.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
String systemConfigurationCode = "systemConfigurationCode_example"; // String | 
try {
    List<CatalogueSample> result = apiInstance.restGetCachedCatalogues(systemConfigurationCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restGetCachedCatalogues");
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

<a name="restGetCataloguesListSample"></a>
# **restGetCataloguesListSample**
> List&lt;CatalogueSample&gt; restGetCataloguesListSample(configurationCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.confluence.invoker.ApiException;
//import gebo.microservices.api.client.confluence.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
String configurationCode = "configurationCode_example"; // String | 
try {
    List<CatalogueSample> result = apiInstance.restGetCataloguesListSample(configurationCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restGetCataloguesListSample");
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

<a name="restGetDescription"></a>
# **restGetDescription**
> String restGetDescription()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.confluence.invoker.ApiException;
//import gebo.microservices.api.client.confluence.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
try {
    String result = apiInstance.restGetDescription();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restGetDescription");
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

<a name="restGetId"></a>
# **restGetId**
> String restGetId()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.confluence.invoker.ApiException;
//import gebo.microservices.api.client.confluence.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
try {
    String result = apiInstance.restGetId();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restGetId");
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

<a name="restGetMessagingModuleId"></a>
# **restGetMessagingModuleId**
> String restGetMessagingModuleId()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.confluence.invoker.ApiException;
//import gebo.microservices.api.client.confluence.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
try {
    String result = apiInstance.restGetMessagingModuleId();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restGetMessagingModuleId");
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

<a name="restGetNativePromptTemplateUseCode"></a>
# **restGetNativePromptTemplateUseCode**
> String restGetNativePromptTemplateUseCode()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.confluence.invoker.ApiException;
//import gebo.microservices.api.client.confluence.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
try {
    String result = apiInstance.restGetNativePromptTemplateUseCode();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restGetNativePromptTemplateUseCode");
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

<a name="restGetProductId"></a>
# **restGetProductId**
> String restGetProductId()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.confluence.invoker.ApiException;
//import gebo.microservices.api.client.confluence.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
try {
    String result = apiInstance.restGetProductId();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restGetProductId");
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

<a name="restGetQueriesGenerationPromptUseCode"></a>
# **restGetQueriesGenerationPromptUseCode**
> String restGetQueriesGenerationPromptUseCode()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.confluence.invoker.ApiException;
//import gebo.microservices.api.client.confluence.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
try {
    String result = apiInstance.restGetQueriesGenerationPromptUseCode();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restGetQueriesGenerationPromptUseCode");
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

<a name="restGetSearchableSystems"></a>
# **restGetSearchableSystems**
> List&lt;SearchableSystemMetaData&gt; restGetSearchableSystems()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.confluence.invoker.ApiException;
//import gebo.microservices.api.client.confluence.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
try {
    List<SearchableSystemMetaData> result = apiInstance.restGetSearchableSystems();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restGetSearchableSystems");
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

<a name="restIsEnabled"></a>
# **restIsEnabled**
> Boolean restIsEnabled()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.confluence.invoker.ApiException;
//import gebo.microservices.api.client.confluence.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
try {
    Boolean result = apiInstance.restIsEnabled();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restIsEnabled");
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

<a name="restNativeSearch"></a>
# **restNativeSearch**
> List&lt;SearchResult&gt; restNativeSearch(body, systemId, nEntryLimit)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.confluence.invoker.ApiException;
//import gebo.microservices.api.client.confluence.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
ConfluenceContentSearchFilter body = new ConfluenceContentSearchFilter(); // ConfluenceContentSearchFilter | 
String systemId = "systemId_example"; // String | 
Integer nEntryLimit = 56; // Integer | 
try {
    List<SearchResult> result = apiInstance.restNativeSearch(body, systemId, nEntryLimit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restNativeSearch");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ConfluenceContentSearchFilter**](ConfluenceContentSearchFilter.md)|  |
 **systemId** | **String**|  |
 **nEntryLimit** | **Integer**|  |

### Return type

[**List&lt;SearchResult&gt;**](SearchResult.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restSearch"></a>
# **restSearch**
> List&lt;SearchResult&gt; restSearch(body, systemId, nEntryLimit)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.confluence.invoker.ApiException;
//import gebo.microservices.api.client.confluence.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
SearchQuery body = new SearchQuery(); // SearchQuery | 
String systemId = "systemId_example"; // String | 
Integer nEntryLimit = 56; // Integer | 
try {
    List<SearchResult> result = apiInstance.restSearch(body, systemId, nEntryLimit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restSearch");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SearchQuery**](SearchQuery.md)|  |
 **systemId** | **String**|  |
 **nEntryLimit** | **Integer**|  |

### Return type

[**List&lt;SearchResult&gt;**](SearchResult.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

