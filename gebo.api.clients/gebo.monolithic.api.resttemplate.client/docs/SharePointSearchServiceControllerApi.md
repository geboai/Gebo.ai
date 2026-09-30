# SharePointSearchServiceControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**restAggregateSharePoint**](SharePointSearchServiceControllerApi.md#restAggregateSharePoint) | **POST** /api/users/SharePointSearchServiceController/aggregate | 
[**restCreateCustomTemplateParamsMapSharePoint**](SharePointSearchServiceControllerApi.md#restCreateCustomTemplateParamsMapSharePoint) | **POST** /api/users/SharePointSearchServiceController/createCustomTemplateParamsMap | 
[**restExtractRelatedAnalisysReferencesSharePoint**](SharePointSearchServiceControllerApi.md#restExtractRelatedAnalisysReferencesSharePoint) | **POST** /api/users/SharePointSearchServiceController/extractRelatedAnalisysReferences | 
[**restFindSystemByIdSharePoint**](SharePointSearchServiceControllerApi.md#restFindSystemByIdSharePoint) | **GET** /api/users/SharePointSearchServiceController/findSystemById | 
[**restFindSystemBySearchResultSharePoint**](SharePointSearchServiceControllerApi.md#restFindSystemBySearchResultSharePoint) | **POST** /api/users/SharePointSearchServiceController/findSystemBySearchResult | 
[**restGetCachedCataloguesSharePoint**](SharePointSearchServiceControllerApi.md#restGetCachedCataloguesSharePoint) | **GET** /api/users/SharePointSearchServiceController/getCachedCatalogues | 
[**restGetCataloguesListSampleSharePoint**](SharePointSearchServiceControllerApi.md#restGetCataloguesListSampleSharePoint) | **GET** /api/users/SharePointSearchServiceController/getCataloguesListSample | 
[**restGetDescriptionSharePoint**](SharePointSearchServiceControllerApi.md#restGetDescriptionSharePoint) | **GET** /api/users/SharePointSearchServiceController/getDescription | 
[**restGetIdSharePoint**](SharePointSearchServiceControllerApi.md#restGetIdSharePoint) | **GET** /api/users/SharePointSearchServiceController/getId | 
[**restGetMessagingModuleIdSharePoint**](SharePointSearchServiceControllerApi.md#restGetMessagingModuleIdSharePoint) | **GET** /api/users/SharePointSearchServiceController/getMessagingModuleId | 
[**restGetNativePromptTemplateUseCodeSharePoint**](SharePointSearchServiceControllerApi.md#restGetNativePromptTemplateUseCodeSharePoint) | **GET** /api/users/SharePointSearchServiceController/getNativePromptTemplateUseCode | 
[**restGetProductIdSharePoint**](SharePointSearchServiceControllerApi.md#restGetProductIdSharePoint) | **GET** /api/users/SharePointSearchServiceController/getProductId | 
[**restGetQueriesGenerationPromptUseCodeSharePoint**](SharePointSearchServiceControllerApi.md#restGetQueriesGenerationPromptUseCodeSharePoint) | **GET** /api/users/SharePointSearchServiceController/getQueriesGenerationPromptUseCode | 
[**restGetSearchableSystemsSharePoint**](SharePointSearchServiceControllerApi.md#restGetSearchableSystemsSharePoint) | **GET** /api/users/SharePointSearchServiceController/getSearchableSystems | 
[**restIsEnabledSharePoint**](SharePointSearchServiceControllerApi.md#restIsEnabledSharePoint) | **GET** /api/users/SharePointSearchServiceController/isEnabled | 
[**restNativeSearchSharePoint**](SharePointSearchServiceControllerApi.md#restNativeSearchSharePoint) | **POST** /api/users/SharePointSearchServiceController/nativeSearch | 
[**restSearchSharePoint**](SharePointSearchServiceControllerApi.md#restSearchSharePoint) | **POST** /api/users/SharePointSearchServiceController/search | 

<a name="restAggregateSharePoint"></a>
# **restAggregateSharePoint**
> MicrosoftResultsExtractionData restAggregateSharePoint(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharePointSearchServiceControllerApi;


SharePointSearchServiceControllerApi apiInstance = new SharePointSearchServiceControllerApi();
AggregateRequestBodyMicrosoftResultsExtractionData body = new AggregateRequestBodyMicrosoftResultsExtractionData(); // AggregateRequestBodyMicrosoftResultsExtractionData | 
try {
    MicrosoftResultsExtractionData result = apiInstance.restAggregateSharePoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restAggregateSharePoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**AggregateRequestBodyMicrosoftResultsExtractionData**](AggregateRequestBodyMicrosoftResultsExtractionData.md)|  |

### Return type

[**MicrosoftResultsExtractionData**](MicrosoftResultsExtractionData.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restCreateCustomTemplateParamsMapSharePoint"></a>
# **restCreateCustomTemplateParamsMapSharePoint**
> Map&lt;String, Object&gt; restCreateCustomTemplateParamsMapSharePoint(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharePointSearchServiceControllerApi;


SharePointSearchServiceControllerApi apiInstance = new SharePointSearchServiceControllerApi();
CustomTemplateParamsRequestBody body = new CustomTemplateParamsRequestBody(); // CustomTemplateParamsRequestBody | 
try {
    Map<String, Object> result = apiInstance.restCreateCustomTemplateParamsMapSharePoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restCreateCustomTemplateParamsMapSharePoint");
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

<a name="restExtractRelatedAnalisysReferencesSharePoint"></a>
# **restExtractRelatedAnalisysReferencesSharePoint**
> SearchResultAnalisysOutcome restExtractRelatedAnalisysReferencesSharePoint(body, systemId)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharePointSearchServiceControllerApi;


SharePointSearchServiceControllerApi apiInstance = new SharePointSearchServiceControllerApi();
MicrosoftResultsExtractionData body = new MicrosoftResultsExtractionData(); // MicrosoftResultsExtractionData | 
String systemId = "systemId_example"; // String | 
try {
    SearchResultAnalisysOutcome result = apiInstance.restExtractRelatedAnalisysReferencesSharePoint(body, systemId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restExtractRelatedAnalisysReferencesSharePoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**MicrosoftResultsExtractionData**](MicrosoftResultsExtractionData.md)|  |
 **systemId** | **String**|  |

### Return type

[**SearchResultAnalisysOutcome**](SearchResultAnalisysOutcome.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restFindSystemByIdSharePoint"></a>
# **restFindSystemByIdSharePoint**
> SearchableSystemMetaData restFindSystemByIdSharePoint(systemId)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharePointSearchServiceControllerApi;


SharePointSearchServiceControllerApi apiInstance = new SharePointSearchServiceControllerApi();
String systemId = "systemId_example"; // String | 
try {
    SearchableSystemMetaData result = apiInstance.restFindSystemByIdSharePoint(systemId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restFindSystemByIdSharePoint");
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

<a name="restFindSystemBySearchResultSharePoint"></a>
# **restFindSystemBySearchResultSharePoint**
> SearchableSystemMetaData restFindSystemBySearchResultSharePoint(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharePointSearchServiceControllerApi;


SharePointSearchServiceControllerApi apiInstance = new SharePointSearchServiceControllerApi();
SearchResult body = new SearchResult(); // SearchResult | 
try {
    SearchableSystemMetaData result = apiInstance.restFindSystemBySearchResultSharePoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restFindSystemBySearchResultSharePoint");
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

<a name="restGetCachedCataloguesSharePoint"></a>
# **restGetCachedCataloguesSharePoint**
> List&lt;CatalogueSample&gt; restGetCachedCataloguesSharePoint(systemConfigurationCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharePointSearchServiceControllerApi;


SharePointSearchServiceControllerApi apiInstance = new SharePointSearchServiceControllerApi();
String systemConfigurationCode = "systemConfigurationCode_example"; // String | 
try {
    List<CatalogueSample> result = apiInstance.restGetCachedCataloguesSharePoint(systemConfigurationCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restGetCachedCataloguesSharePoint");
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

<a name="restGetCataloguesListSampleSharePoint"></a>
# **restGetCataloguesListSampleSharePoint**
> List&lt;CatalogueSample&gt; restGetCataloguesListSampleSharePoint(configurationCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharePointSearchServiceControllerApi;


SharePointSearchServiceControllerApi apiInstance = new SharePointSearchServiceControllerApi();
String configurationCode = "configurationCode_example"; // String | 
try {
    List<CatalogueSample> result = apiInstance.restGetCataloguesListSampleSharePoint(configurationCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restGetCataloguesListSampleSharePoint");
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

<a name="restGetDescriptionSharePoint"></a>
# **restGetDescriptionSharePoint**
> String restGetDescriptionSharePoint()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharePointSearchServiceControllerApi;


SharePointSearchServiceControllerApi apiInstance = new SharePointSearchServiceControllerApi();
try {
    String result = apiInstance.restGetDescriptionSharePoint();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restGetDescriptionSharePoint");
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

<a name="restGetIdSharePoint"></a>
# **restGetIdSharePoint**
> String restGetIdSharePoint()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharePointSearchServiceControllerApi;


SharePointSearchServiceControllerApi apiInstance = new SharePointSearchServiceControllerApi();
try {
    String result = apiInstance.restGetIdSharePoint();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restGetIdSharePoint");
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

<a name="restGetMessagingModuleIdSharePoint"></a>
# **restGetMessagingModuleIdSharePoint**
> String restGetMessagingModuleIdSharePoint()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharePointSearchServiceControllerApi;


SharePointSearchServiceControllerApi apiInstance = new SharePointSearchServiceControllerApi();
try {
    String result = apiInstance.restGetMessagingModuleIdSharePoint();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restGetMessagingModuleIdSharePoint");
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

<a name="restGetNativePromptTemplateUseCodeSharePoint"></a>
# **restGetNativePromptTemplateUseCodeSharePoint**
> String restGetNativePromptTemplateUseCodeSharePoint()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharePointSearchServiceControllerApi;


SharePointSearchServiceControllerApi apiInstance = new SharePointSearchServiceControllerApi();
try {
    String result = apiInstance.restGetNativePromptTemplateUseCodeSharePoint();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restGetNativePromptTemplateUseCodeSharePoint");
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

<a name="restGetProductIdSharePoint"></a>
# **restGetProductIdSharePoint**
> String restGetProductIdSharePoint()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharePointSearchServiceControllerApi;


SharePointSearchServiceControllerApi apiInstance = new SharePointSearchServiceControllerApi();
try {
    String result = apiInstance.restGetProductIdSharePoint();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restGetProductIdSharePoint");
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

<a name="restGetQueriesGenerationPromptUseCodeSharePoint"></a>
# **restGetQueriesGenerationPromptUseCodeSharePoint**
> String restGetQueriesGenerationPromptUseCodeSharePoint()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharePointSearchServiceControllerApi;


SharePointSearchServiceControllerApi apiInstance = new SharePointSearchServiceControllerApi();
try {
    String result = apiInstance.restGetQueriesGenerationPromptUseCodeSharePoint();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restGetQueriesGenerationPromptUseCodeSharePoint");
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

<a name="restGetSearchableSystemsSharePoint"></a>
# **restGetSearchableSystemsSharePoint**
> List&lt;SearchableSystemMetaData&gt; restGetSearchableSystemsSharePoint()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharePointSearchServiceControllerApi;


SharePointSearchServiceControllerApi apiInstance = new SharePointSearchServiceControllerApi();
try {
    List<SearchableSystemMetaData> result = apiInstance.restGetSearchableSystemsSharePoint();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restGetSearchableSystemsSharePoint");
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

<a name="restIsEnabledSharePoint"></a>
# **restIsEnabledSharePoint**
> Boolean restIsEnabledSharePoint()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharePointSearchServiceControllerApi;


SharePointSearchServiceControllerApi apiInstance = new SharePointSearchServiceControllerApi();
try {
    Boolean result = apiInstance.restIsEnabledSharePoint();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restIsEnabledSharePoint");
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

<a name="restNativeSearchSharePoint"></a>
# **restNativeSearchSharePoint**
> List&lt;SearchResult&gt; restNativeSearchSharePoint(body, systemId, nEntryLimit)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharePointSearchServiceControllerApi;


SharePointSearchServiceControllerApi apiInstance = new SharePointSearchServiceControllerApi();
SharePointSearchFilter body = new SharePointSearchFilter(); // SharePointSearchFilter | 
String systemId = "systemId_example"; // String | 
Integer nEntryLimit = 56; // Integer | 
try {
    List<SearchResult> result = apiInstance.restNativeSearchSharePoint(body, systemId, nEntryLimit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restNativeSearchSharePoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SharePointSearchFilter**](SharePointSearchFilter.md)|  |
 **systemId** | **String**|  |
 **nEntryLimit** | **Integer**|  |

### Return type

[**List&lt;SearchResult&gt;**](SearchResult.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restSearchSharePoint"></a>
# **restSearchSharePoint**
> List&lt;SearchResult&gt; restSearchSharePoint(body, systemId, nEntryLimit)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharePointSearchServiceControllerApi;


SharePointSearchServiceControllerApi apiInstance = new SharePointSearchServiceControllerApi();
SearchQuery body = new SearchQuery(); // SearchQuery | 
String systemId = "systemId_example"; // String | 
Integer nEntryLimit = 56; // Integer | 
try {
    List<SearchResult> result = apiInstance.restSearchSharePoint(body, systemId, nEntryLimit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restSearchSharePoint");
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

