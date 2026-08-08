# SharePointSearchServiceControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**restAggregate**](SharePointSearchServiceControllerApi.md#restAggregate) | **POST** /api/users/SharePointSearchServiceController/aggregate | 
[**restCreateCustomTemplateParamsMap**](SharePointSearchServiceControllerApi.md#restCreateCustomTemplateParamsMap) | **POST** /api/users/SharePointSearchServiceController/createCustomTemplateParamsMap | 
[**restExtractRelatedAnalisysReferences**](SharePointSearchServiceControllerApi.md#restExtractRelatedAnalisysReferences) | **POST** /api/users/SharePointSearchServiceController/extractRelatedAnalisysReferences | 
[**restFindSystemById**](SharePointSearchServiceControllerApi.md#restFindSystemById) | **GET** /api/users/SharePointSearchServiceController/findSystemById | 
[**restFindSystemBySearchResult**](SharePointSearchServiceControllerApi.md#restFindSystemBySearchResult) | **POST** /api/users/SharePointSearchServiceController/findSystemBySearchResult | 
[**restGetCachedCatalogues**](SharePointSearchServiceControllerApi.md#restGetCachedCatalogues) | **GET** /api/users/SharePointSearchServiceController/getCachedCatalogues | 
[**restGetCataloguesListSample**](SharePointSearchServiceControllerApi.md#restGetCataloguesListSample) | **GET** /api/users/SharePointSearchServiceController/getCataloguesListSample | 
[**restGetDescription**](SharePointSearchServiceControllerApi.md#restGetDescription) | **GET** /api/users/SharePointSearchServiceController/getDescription | 
[**restGetId**](SharePointSearchServiceControllerApi.md#restGetId) | **GET** /api/users/SharePointSearchServiceController/getId | 
[**restGetMessagingModuleId**](SharePointSearchServiceControllerApi.md#restGetMessagingModuleId) | **GET** /api/users/SharePointSearchServiceController/getMessagingModuleId | 
[**restGetNativePromptTemplateUseCode**](SharePointSearchServiceControllerApi.md#restGetNativePromptTemplateUseCode) | **GET** /api/users/SharePointSearchServiceController/getNativePromptTemplateUseCode | 
[**restGetProductId**](SharePointSearchServiceControllerApi.md#restGetProductId) | **GET** /api/users/SharePointSearchServiceController/getProductId | 
[**restGetQueriesGenerationPromptUseCode**](SharePointSearchServiceControllerApi.md#restGetQueriesGenerationPromptUseCode) | **GET** /api/users/SharePointSearchServiceController/getQueriesGenerationPromptUseCode | 
[**restGetSearchableSystems**](SharePointSearchServiceControllerApi.md#restGetSearchableSystems) | **GET** /api/users/SharePointSearchServiceController/getSearchableSystems | 
[**restIsEnabled**](SharePointSearchServiceControllerApi.md#restIsEnabled) | **GET** /api/users/SharePointSearchServiceController/isEnabled | 
[**restNativeSearch**](SharePointSearchServiceControllerApi.md#restNativeSearch) | **POST** /api/users/SharePointSearchServiceController/nativeSearch | 
[**restSearch**](SharePointSearchServiceControllerApi.md#restSearch) | **POST** /api/users/SharePointSearchServiceController/search | 

<a name="restAggregate"></a>
# **restAggregate**
> MicrosoftResultsExtractionData restAggregate(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharePointSearchServiceControllerApi;


SharePointSearchServiceControllerApi apiInstance = new SharePointSearchServiceControllerApi();
AggregateRequestBodyMicrosoftResultsExtractionData body = new AggregateRequestBodyMicrosoftResultsExtractionData(); // AggregateRequestBodyMicrosoftResultsExtractionData | 
try {
    MicrosoftResultsExtractionData result = apiInstance.restAggregate(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restAggregate");
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

<a name="restCreateCustomTemplateParamsMap"></a>
# **restCreateCustomTemplateParamsMap**
> Map&lt;String, Object&gt; restCreateCustomTemplateParamsMap(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharePointSearchServiceControllerApi;


SharePointSearchServiceControllerApi apiInstance = new SharePointSearchServiceControllerApi();
CustomTemplateParamsRequestBody body = new CustomTemplateParamsRequestBody(); // CustomTemplateParamsRequestBody | 
try {
    Map<String, Object> result = apiInstance.restCreateCustomTemplateParamsMap(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restCreateCustomTemplateParamsMap");
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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharePointSearchServiceControllerApi;


SharePointSearchServiceControllerApi apiInstance = new SharePointSearchServiceControllerApi();
MicrosoftResultsExtractionData body = new MicrosoftResultsExtractionData(); // MicrosoftResultsExtractionData | 
String systemId = "systemId_example"; // String | 
try {
    SearchResultAnalisysOutcome result = apiInstance.restExtractRelatedAnalisysReferences(body, systemId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restExtractRelatedAnalisysReferences");
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

<a name="restFindSystemById"></a>
# **restFindSystemById**
> SearchableSystemMetaData restFindSystemById(systemId)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharePointSearchServiceControllerApi;


SharePointSearchServiceControllerApi apiInstance = new SharePointSearchServiceControllerApi();
String systemId = "systemId_example"; // String | 
try {
    SearchableSystemMetaData result = apiInstance.restFindSystemById(systemId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restFindSystemById");
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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharePointSearchServiceControllerApi;


SharePointSearchServiceControllerApi apiInstance = new SharePointSearchServiceControllerApi();
SearchResult body = new SearchResult(); // SearchResult | 
try {
    SearchableSystemMetaData result = apiInstance.restFindSystemBySearchResult(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restFindSystemBySearchResult");
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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharePointSearchServiceControllerApi;


SharePointSearchServiceControllerApi apiInstance = new SharePointSearchServiceControllerApi();
String systemConfigurationCode = "systemConfigurationCode_example"; // String | 
try {
    List<CatalogueSample> result = apiInstance.restGetCachedCatalogues(systemConfigurationCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restGetCachedCatalogues");
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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharePointSearchServiceControllerApi;


SharePointSearchServiceControllerApi apiInstance = new SharePointSearchServiceControllerApi();
String configurationCode = "configurationCode_example"; // String | 
try {
    List<CatalogueSample> result = apiInstance.restGetCataloguesListSample(configurationCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restGetCataloguesListSample");
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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharePointSearchServiceControllerApi;


SharePointSearchServiceControllerApi apiInstance = new SharePointSearchServiceControllerApi();
try {
    String result = apiInstance.restGetDescription();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restGetDescription");
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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharePointSearchServiceControllerApi;


SharePointSearchServiceControllerApi apiInstance = new SharePointSearchServiceControllerApi();
try {
    String result = apiInstance.restGetId();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restGetId");
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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharePointSearchServiceControllerApi;


SharePointSearchServiceControllerApi apiInstance = new SharePointSearchServiceControllerApi();
try {
    String result = apiInstance.restGetMessagingModuleId();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restGetMessagingModuleId");
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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharePointSearchServiceControllerApi;


SharePointSearchServiceControllerApi apiInstance = new SharePointSearchServiceControllerApi();
try {
    String result = apiInstance.restGetNativePromptTemplateUseCode();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restGetNativePromptTemplateUseCode");
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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharePointSearchServiceControllerApi;


SharePointSearchServiceControllerApi apiInstance = new SharePointSearchServiceControllerApi();
try {
    String result = apiInstance.restGetProductId();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restGetProductId");
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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharePointSearchServiceControllerApi;


SharePointSearchServiceControllerApi apiInstance = new SharePointSearchServiceControllerApi();
try {
    String result = apiInstance.restGetQueriesGenerationPromptUseCode();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restGetQueriesGenerationPromptUseCode");
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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharePointSearchServiceControllerApi;


SharePointSearchServiceControllerApi apiInstance = new SharePointSearchServiceControllerApi();
try {
    List<SearchableSystemMetaData> result = apiInstance.restGetSearchableSystems();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restGetSearchableSystems");
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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharePointSearchServiceControllerApi;


SharePointSearchServiceControllerApi apiInstance = new SharePointSearchServiceControllerApi();
try {
    Boolean result = apiInstance.restIsEnabled();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restIsEnabled");
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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharePointSearchServiceControllerApi;


SharePointSearchServiceControllerApi apiInstance = new SharePointSearchServiceControllerApi();
SharePointSearchFilter body = new SharePointSearchFilter(); // SharePointSearchFilter | 
String systemId = "systemId_example"; // String | 
Integer nEntryLimit = 56; // Integer | 
try {
    List<SearchResult> result = apiInstance.restNativeSearch(body, systemId, nEntryLimit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restNativeSearch");
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

<a name="restSearch"></a>
# **restSearch**
> List&lt;SearchResult&gt; restSearch(body, systemId, nEntryLimit)



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
    List<SearchResult> result = apiInstance.restSearch(body, systemId, nEntryLimit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharePointSearchServiceControllerApi#restSearch");
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

