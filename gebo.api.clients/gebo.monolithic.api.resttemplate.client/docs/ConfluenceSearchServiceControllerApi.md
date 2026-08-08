# ConfluenceSearchServiceControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**restAggregate3**](ConfluenceSearchServiceControllerApi.md#restAggregate3) | **POST** /api/users/ConfluenceSearchServiceController/aggregate | 
[**restCreateCustomTemplateParamsMap2**](ConfluenceSearchServiceControllerApi.md#restCreateCustomTemplateParamsMap2) | **POST** /api/users/ConfluenceSearchServiceController/createCustomTemplateParamsMap | 
[**restExtractRelatedAnalisysReferences3**](ConfluenceSearchServiceControllerApi.md#restExtractRelatedAnalisysReferences3) | **POST** /api/users/ConfluenceSearchServiceController/extractRelatedAnalisysReferences | 
[**restFindSystemById3**](ConfluenceSearchServiceControllerApi.md#restFindSystemById3) | **GET** /api/users/ConfluenceSearchServiceController/findSystemById | 
[**restFindSystemBySearchResult3**](ConfluenceSearchServiceControllerApi.md#restFindSystemBySearchResult3) | **POST** /api/users/ConfluenceSearchServiceController/findSystemBySearchResult | 
[**restGetCachedCatalogues3**](ConfluenceSearchServiceControllerApi.md#restGetCachedCatalogues3) | **GET** /api/users/ConfluenceSearchServiceController/getCachedCatalogues | 
[**restGetCataloguesListSample3**](ConfluenceSearchServiceControllerApi.md#restGetCataloguesListSample3) | **GET** /api/users/ConfluenceSearchServiceController/getCataloguesListSample | 
[**restGetDescription3**](ConfluenceSearchServiceControllerApi.md#restGetDescription3) | **GET** /api/users/ConfluenceSearchServiceController/getDescription | 
[**restGetId3**](ConfluenceSearchServiceControllerApi.md#restGetId3) | **GET** /api/users/ConfluenceSearchServiceController/getId | 
[**restGetMessagingModuleId3**](ConfluenceSearchServiceControllerApi.md#restGetMessagingModuleId3) | **GET** /api/users/ConfluenceSearchServiceController/getMessagingModuleId | 
[**restGetNativePromptTemplateUseCode2**](ConfluenceSearchServiceControllerApi.md#restGetNativePromptTemplateUseCode2) | **GET** /api/users/ConfluenceSearchServiceController/getNativePromptTemplateUseCode | 
[**restGetProductId3**](ConfluenceSearchServiceControllerApi.md#restGetProductId3) | **GET** /api/users/ConfluenceSearchServiceController/getProductId | 
[**restGetQueriesGenerationPromptUseCode3**](ConfluenceSearchServiceControllerApi.md#restGetQueriesGenerationPromptUseCode3) | **GET** /api/users/ConfluenceSearchServiceController/getQueriesGenerationPromptUseCode | 
[**restGetSearchableSystems3**](ConfluenceSearchServiceControllerApi.md#restGetSearchableSystems3) | **GET** /api/users/ConfluenceSearchServiceController/getSearchableSystems | 
[**restIsEnabled3**](ConfluenceSearchServiceControllerApi.md#restIsEnabled3) | **GET** /api/users/ConfluenceSearchServiceController/isEnabled | 
[**restNativeSearch2**](ConfluenceSearchServiceControllerApi.md#restNativeSearch2) | **POST** /api/users/ConfluenceSearchServiceController/nativeSearch | 
[**restSearch3**](ConfluenceSearchServiceControllerApi.md#restSearch3) | **POST** /api/users/ConfluenceSearchServiceController/search | 

<a name="restAggregate3"></a>
# **restAggregate3**
> ConfluenceResultsExtractionData restAggregate3(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
AggregateRequestBodyConfluenceResultsExtractionData body = new AggregateRequestBodyConfluenceResultsExtractionData(); // AggregateRequestBodyConfluenceResultsExtractionData | 
try {
    ConfluenceResultsExtractionData result = apiInstance.restAggregate3(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restAggregate3");
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

<a name="restCreateCustomTemplateParamsMap2"></a>
# **restCreateCustomTemplateParamsMap2**
> Map&lt;String, Object&gt; restCreateCustomTemplateParamsMap2(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
CustomTemplateParamsRequestBody body = new CustomTemplateParamsRequestBody(); // CustomTemplateParamsRequestBody | 
try {
    Map<String, Object> result = apiInstance.restCreateCustomTemplateParamsMap2(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restCreateCustomTemplateParamsMap2");
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

<a name="restExtractRelatedAnalisysReferences3"></a>
# **restExtractRelatedAnalisysReferences3**
> SearchResultAnalisysOutcome restExtractRelatedAnalisysReferences3(body, systemId)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
ConfluenceResultsExtractionData body = new ConfluenceResultsExtractionData(); // ConfluenceResultsExtractionData | 
String systemId = "systemId_example"; // String | 
try {
    SearchResultAnalisysOutcome result = apiInstance.restExtractRelatedAnalisysReferences3(body, systemId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restExtractRelatedAnalisysReferences3");
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

<a name="restFindSystemById3"></a>
# **restFindSystemById3**
> SearchableSystemMetaData restFindSystemById3(systemId)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
String systemId = "systemId_example"; // String | 
try {
    SearchableSystemMetaData result = apiInstance.restFindSystemById3(systemId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restFindSystemById3");
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

<a name="restFindSystemBySearchResult3"></a>
# **restFindSystemBySearchResult3**
> SearchableSystemMetaData restFindSystemBySearchResult3(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
SearchResult body = new SearchResult(); // SearchResult | 
try {
    SearchableSystemMetaData result = apiInstance.restFindSystemBySearchResult3(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restFindSystemBySearchResult3");
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

<a name="restGetCachedCatalogues3"></a>
# **restGetCachedCatalogues3**
> List&lt;CatalogueSample&gt; restGetCachedCatalogues3(systemConfigurationCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
String systemConfigurationCode = "systemConfigurationCode_example"; // String | 
try {
    List<CatalogueSample> result = apiInstance.restGetCachedCatalogues3(systemConfigurationCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restGetCachedCatalogues3");
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

<a name="restGetCataloguesListSample3"></a>
# **restGetCataloguesListSample3**
> List&lt;CatalogueSample&gt; restGetCataloguesListSample3(configurationCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
String configurationCode = "configurationCode_example"; // String | 
try {
    List<CatalogueSample> result = apiInstance.restGetCataloguesListSample3(configurationCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restGetCataloguesListSample3");
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

<a name="restGetDescription3"></a>
# **restGetDescription3**
> String restGetDescription3()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
try {
    String result = apiInstance.restGetDescription3();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restGetDescription3");
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

<a name="restGetId3"></a>
# **restGetId3**
> String restGetId3()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
try {
    String result = apiInstance.restGetId3();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restGetId3");
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

<a name="restGetMessagingModuleId3"></a>
# **restGetMessagingModuleId3**
> String restGetMessagingModuleId3()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
try {
    String result = apiInstance.restGetMessagingModuleId3();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restGetMessagingModuleId3");
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

<a name="restGetNativePromptTemplateUseCode2"></a>
# **restGetNativePromptTemplateUseCode2**
> String restGetNativePromptTemplateUseCode2()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
try {
    String result = apiInstance.restGetNativePromptTemplateUseCode2();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restGetNativePromptTemplateUseCode2");
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

<a name="restGetProductId3"></a>
# **restGetProductId3**
> String restGetProductId3()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
try {
    String result = apiInstance.restGetProductId3();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restGetProductId3");
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

<a name="restGetQueriesGenerationPromptUseCode3"></a>
# **restGetQueriesGenerationPromptUseCode3**
> String restGetQueriesGenerationPromptUseCode3()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
try {
    String result = apiInstance.restGetQueriesGenerationPromptUseCode3();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restGetQueriesGenerationPromptUseCode3");
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

<a name="restGetSearchableSystems3"></a>
# **restGetSearchableSystems3**
> List&lt;SearchableSystemMetaData&gt; restGetSearchableSystems3()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
try {
    List<SearchableSystemMetaData> result = apiInstance.restGetSearchableSystems3();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restGetSearchableSystems3");
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

<a name="restIsEnabled3"></a>
# **restIsEnabled3**
> Boolean restIsEnabled3()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
try {
    Boolean result = apiInstance.restIsEnabled3();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restIsEnabled3");
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

<a name="restNativeSearch2"></a>
# **restNativeSearch2**
> List&lt;SearchResult&gt; restNativeSearch2(body, systemId, nEntryLimit)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
ConfluenceContentSearchFilter body = new ConfluenceContentSearchFilter(); // ConfluenceContentSearchFilter | 
String systemId = "systemId_example"; // String | 
Integer nEntryLimit = 56; // Integer | 
try {
    List<SearchResult> result = apiInstance.restNativeSearch2(body, systemId, nEntryLimit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restNativeSearch2");
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

<a name="restSearch3"></a>
# **restSearch3**
> List&lt;SearchResult&gt; restSearch3(body, systemId, nEntryLimit)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSearchServiceControllerApi;


ConfluenceSearchServiceControllerApi apiInstance = new ConfluenceSearchServiceControllerApi();
SearchQuery body = new SearchQuery(); // SearchQuery | 
String systemId = "systemId_example"; // String | 
Integer nEntryLimit = 56; // Integer | 
try {
    List<SearchResult> result = apiInstance.restSearch3(body, systemId, nEntryLimit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSearchServiceControllerApi#restSearch3");
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

