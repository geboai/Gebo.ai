# JiraSearchServiceControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**restAggregateJira**](JiraSearchServiceControllerApi.md#restAggregateJira) | **POST** /api/users/JiraSearchServiceController/aggregate | 
[**restCreateCustomTemplateParamsMapJira**](JiraSearchServiceControllerApi.md#restCreateCustomTemplateParamsMapJira) | **POST** /api/users/JiraSearchServiceController/createCustomTemplateParamsMap | 
[**restExtractRelatedAnalisysReferencesJira**](JiraSearchServiceControllerApi.md#restExtractRelatedAnalisysReferencesJira) | **POST** /api/users/JiraSearchServiceController/extractRelatedAnalisysReferences | 
[**restFindSystemByIdJira**](JiraSearchServiceControllerApi.md#restFindSystemByIdJira) | **GET** /api/users/JiraSearchServiceController/findSystemById | 
[**restFindSystemBySearchResultJira**](JiraSearchServiceControllerApi.md#restFindSystemBySearchResultJira) | **POST** /api/users/JiraSearchServiceController/findSystemBySearchResult | 
[**restGetCachedCataloguesJira**](JiraSearchServiceControllerApi.md#restGetCachedCataloguesJira) | **GET** /api/users/JiraSearchServiceController/getCachedCatalogues | 
[**restGetCataloguesListSampleJira**](JiraSearchServiceControllerApi.md#restGetCataloguesListSampleJira) | **GET** /api/users/JiraSearchServiceController/getCataloguesListSample | 
[**restGetDescriptionJira**](JiraSearchServiceControllerApi.md#restGetDescriptionJira) | **GET** /api/users/JiraSearchServiceController/getDescription | 
[**restGetIdJira**](JiraSearchServiceControllerApi.md#restGetIdJira) | **GET** /api/users/JiraSearchServiceController/getId | 
[**restGetMessagingModuleIdJira**](JiraSearchServiceControllerApi.md#restGetMessagingModuleIdJira) | **GET** /api/users/JiraSearchServiceController/getMessagingModuleId | 
[**restGetNativePromptTemplateUseCodeJira**](JiraSearchServiceControllerApi.md#restGetNativePromptTemplateUseCodeJira) | **GET** /api/users/JiraSearchServiceController/getNativePromptTemplateUseCode | 
[**restGetProductIdJira**](JiraSearchServiceControllerApi.md#restGetProductIdJira) | **GET** /api/users/JiraSearchServiceController/getProductId | 
[**restGetQueriesGenerationPromptUseCodeJira**](JiraSearchServiceControllerApi.md#restGetQueriesGenerationPromptUseCodeJira) | **GET** /api/users/JiraSearchServiceController/getQueriesGenerationPromptUseCode | 
[**restGetSearchableSystemsJira**](JiraSearchServiceControllerApi.md#restGetSearchableSystemsJira) | **GET** /api/users/JiraSearchServiceController/getSearchableSystems | 
[**restIsEnabledJira**](JiraSearchServiceControllerApi.md#restIsEnabledJira) | **GET** /api/users/JiraSearchServiceController/isEnabled | 
[**restNativeSearchJira**](JiraSearchServiceControllerApi.md#restNativeSearchJira) | **POST** /api/users/JiraSearchServiceController/nativeSearch | 
[**restSearchJira**](JiraSearchServiceControllerApi.md#restSearchJira) | **POST** /api/users/JiraSearchServiceController/search | 

<a name="restAggregateJira"></a>
# **restAggregateJira**
> JiraResultsExtractionData restAggregateJira(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
AggregateRequestBodyJiraResultsExtractionData body = new AggregateRequestBodyJiraResultsExtractionData(); // AggregateRequestBodyJiraResultsExtractionData | 
try {
    JiraResultsExtractionData result = apiInstance.restAggregateJira(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restAggregateJira");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**AggregateRequestBodyJiraResultsExtractionData**](AggregateRequestBodyJiraResultsExtractionData.md)|  |

### Return type

[**JiraResultsExtractionData**](JiraResultsExtractionData.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restCreateCustomTemplateParamsMapJira"></a>
# **restCreateCustomTemplateParamsMapJira**
> Map&lt;String, Object&gt; restCreateCustomTemplateParamsMapJira(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
CustomTemplateParamsRequestBody body = new CustomTemplateParamsRequestBody(); // CustomTemplateParamsRequestBody | 
try {
    Map<String, Object> result = apiInstance.restCreateCustomTemplateParamsMapJira(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restCreateCustomTemplateParamsMapJira");
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

<a name="restExtractRelatedAnalisysReferencesJira"></a>
# **restExtractRelatedAnalisysReferencesJira**
> SearchResultAnalisysOutcome restExtractRelatedAnalisysReferencesJira(body, systemId)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
JiraResultsExtractionData body = new JiraResultsExtractionData(); // JiraResultsExtractionData | 
String systemId = "systemId_example"; // String | 
try {
    SearchResultAnalisysOutcome result = apiInstance.restExtractRelatedAnalisysReferencesJira(body, systemId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restExtractRelatedAnalisysReferencesJira");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**JiraResultsExtractionData**](JiraResultsExtractionData.md)|  |
 **systemId** | **String**|  |

### Return type

[**SearchResultAnalisysOutcome**](SearchResultAnalisysOutcome.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restFindSystemByIdJira"></a>
# **restFindSystemByIdJira**
> SearchableSystemMetaData restFindSystemByIdJira(systemId)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
String systemId = "systemId_example"; // String | 
try {
    SearchableSystemMetaData result = apiInstance.restFindSystemByIdJira(systemId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restFindSystemByIdJira");
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

<a name="restFindSystemBySearchResultJira"></a>
# **restFindSystemBySearchResultJira**
> SearchableSystemMetaData restFindSystemBySearchResultJira(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
SearchResult body = new SearchResult(); // SearchResult | 
try {
    SearchableSystemMetaData result = apiInstance.restFindSystemBySearchResultJira(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restFindSystemBySearchResultJira");
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

<a name="restGetCachedCataloguesJira"></a>
# **restGetCachedCataloguesJira**
> List&lt;CatalogueSample&gt; restGetCachedCataloguesJira(systemConfigurationCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
String systemConfigurationCode = "systemConfigurationCode_example"; // String | 
try {
    List<CatalogueSample> result = apiInstance.restGetCachedCataloguesJira(systemConfigurationCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restGetCachedCataloguesJira");
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

<a name="restGetCataloguesListSampleJira"></a>
# **restGetCataloguesListSampleJira**
> List&lt;CatalogueSample&gt; restGetCataloguesListSampleJira(configurationCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
String configurationCode = "configurationCode_example"; // String | 
try {
    List<CatalogueSample> result = apiInstance.restGetCataloguesListSampleJira(configurationCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restGetCataloguesListSampleJira");
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

<a name="restGetDescriptionJira"></a>
# **restGetDescriptionJira**
> String restGetDescriptionJira()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
try {
    String result = apiInstance.restGetDescriptionJira();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restGetDescriptionJira");
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

<a name="restGetIdJira"></a>
# **restGetIdJira**
> String restGetIdJira()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
try {
    String result = apiInstance.restGetIdJira();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restGetIdJira");
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

<a name="restGetMessagingModuleIdJira"></a>
# **restGetMessagingModuleIdJira**
> String restGetMessagingModuleIdJira()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
try {
    String result = apiInstance.restGetMessagingModuleIdJira();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restGetMessagingModuleIdJira");
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

<a name="restGetNativePromptTemplateUseCodeJira"></a>
# **restGetNativePromptTemplateUseCodeJira**
> String restGetNativePromptTemplateUseCodeJira()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
try {
    String result = apiInstance.restGetNativePromptTemplateUseCodeJira();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restGetNativePromptTemplateUseCodeJira");
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

<a name="restGetProductIdJira"></a>
# **restGetProductIdJira**
> String restGetProductIdJira()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
try {
    String result = apiInstance.restGetProductIdJira();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restGetProductIdJira");
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

<a name="restGetQueriesGenerationPromptUseCodeJira"></a>
# **restGetQueriesGenerationPromptUseCodeJira**
> String restGetQueriesGenerationPromptUseCodeJira()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
try {
    String result = apiInstance.restGetQueriesGenerationPromptUseCodeJira();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restGetQueriesGenerationPromptUseCodeJira");
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

<a name="restGetSearchableSystemsJira"></a>
# **restGetSearchableSystemsJira**
> List&lt;SearchableSystemMetaData&gt; restGetSearchableSystemsJira()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
try {
    List<SearchableSystemMetaData> result = apiInstance.restGetSearchableSystemsJira();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restGetSearchableSystemsJira");
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

<a name="restIsEnabledJira"></a>
# **restIsEnabledJira**
> Boolean restIsEnabledJira()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
try {
    Boolean result = apiInstance.restIsEnabledJira();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restIsEnabledJira");
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

<a name="restNativeSearchJira"></a>
# **restNativeSearchJira**
> List&lt;SearchResult&gt; restNativeSearchJira(body, systemId, nEntryLimit, connectTimeoutMillis, readTimeoutMillis, retries)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
JiraIssuesSearchFilter body = new JiraIssuesSearchFilter(); // JiraIssuesSearchFilter | 
String systemId = "systemId_example"; // String | 
Integer nEntryLimit = 56; // Integer | 
Integer connectTimeoutMillis = 56; // Integer | 
Integer readTimeoutMillis = 56; // Integer | 
Integer retries = 56; // Integer | 
try {
    List<SearchResult> result = apiInstance.restNativeSearchJira(body, systemId, nEntryLimit, connectTimeoutMillis, readTimeoutMillis, retries);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restNativeSearchJira");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**JiraIssuesSearchFilter**](JiraIssuesSearchFilter.md)|  |
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

<a name="restSearchJira"></a>
# **restSearchJira**
> List&lt;SearchResult&gt; restSearchJira(body, systemId, nEntryLimit, connectTimeoutMillis, readTimeoutMillis, retries)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
SearchQuery body = new SearchQuery(); // SearchQuery | 
String systemId = "systemId_example"; // String | 
Integer nEntryLimit = 56; // Integer | 
Integer connectTimeoutMillis = 56; // Integer | 
Integer readTimeoutMillis = 56; // Integer | 
Integer retries = 56; // Integer | 
try {
    List<SearchResult> result = apiInstance.restSearchJira(body, systemId, nEntryLimit, connectTimeoutMillis, readTimeoutMillis, retries);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restSearchJira");
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

