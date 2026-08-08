# JiraSearchServiceControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**restAggregate1**](JiraSearchServiceControllerApi.md#restAggregate1) | **POST** /api/users/JiraSearchServiceController/aggregate | 
[**restCreateCustomTemplateParamsMap1**](JiraSearchServiceControllerApi.md#restCreateCustomTemplateParamsMap1) | **POST** /api/users/JiraSearchServiceController/createCustomTemplateParamsMap | 
[**restExtractRelatedAnalisysReferences1**](JiraSearchServiceControllerApi.md#restExtractRelatedAnalisysReferences1) | **POST** /api/users/JiraSearchServiceController/extractRelatedAnalisysReferences | 
[**restFindSystemById1**](JiraSearchServiceControllerApi.md#restFindSystemById1) | **GET** /api/users/JiraSearchServiceController/findSystemById | 
[**restFindSystemBySearchResult1**](JiraSearchServiceControllerApi.md#restFindSystemBySearchResult1) | **POST** /api/users/JiraSearchServiceController/findSystemBySearchResult | 
[**restGetCachedCatalogues1**](JiraSearchServiceControllerApi.md#restGetCachedCatalogues1) | **GET** /api/users/JiraSearchServiceController/getCachedCatalogues | 
[**restGetCataloguesListSample1**](JiraSearchServiceControllerApi.md#restGetCataloguesListSample1) | **GET** /api/users/JiraSearchServiceController/getCataloguesListSample | 
[**restGetDescription1**](JiraSearchServiceControllerApi.md#restGetDescription1) | **GET** /api/users/JiraSearchServiceController/getDescription | 
[**restGetId1**](JiraSearchServiceControllerApi.md#restGetId1) | **GET** /api/users/JiraSearchServiceController/getId | 
[**restGetMessagingModuleId1**](JiraSearchServiceControllerApi.md#restGetMessagingModuleId1) | **GET** /api/users/JiraSearchServiceController/getMessagingModuleId | 
[**restGetNativePromptTemplateUseCode1**](JiraSearchServiceControllerApi.md#restGetNativePromptTemplateUseCode1) | **GET** /api/users/JiraSearchServiceController/getNativePromptTemplateUseCode | 
[**restGetProductId1**](JiraSearchServiceControllerApi.md#restGetProductId1) | **GET** /api/users/JiraSearchServiceController/getProductId | 
[**restGetQueriesGenerationPromptUseCode1**](JiraSearchServiceControllerApi.md#restGetQueriesGenerationPromptUseCode1) | **GET** /api/users/JiraSearchServiceController/getQueriesGenerationPromptUseCode | 
[**restGetSearchableSystems1**](JiraSearchServiceControllerApi.md#restGetSearchableSystems1) | **GET** /api/users/JiraSearchServiceController/getSearchableSystems | 
[**restIsEnabled1**](JiraSearchServiceControllerApi.md#restIsEnabled1) | **GET** /api/users/JiraSearchServiceController/isEnabled | 
[**restNativeSearch1**](JiraSearchServiceControllerApi.md#restNativeSearch1) | **POST** /api/users/JiraSearchServiceController/nativeSearch | 
[**restSearch1**](JiraSearchServiceControllerApi.md#restSearch1) | **POST** /api/users/JiraSearchServiceController/search | 

<a name="restAggregate1"></a>
# **restAggregate1**
> JiraResultsExtractionData restAggregate1(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
AggregateRequestBodyJiraResultsExtractionData body = new AggregateRequestBodyJiraResultsExtractionData(); // AggregateRequestBodyJiraResultsExtractionData | 
try {
    JiraResultsExtractionData result = apiInstance.restAggregate1(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restAggregate1");
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

<a name="restCreateCustomTemplateParamsMap1"></a>
# **restCreateCustomTemplateParamsMap1**
> Map&lt;String, Object&gt; restCreateCustomTemplateParamsMap1(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
CustomTemplateParamsRequestBody body = new CustomTemplateParamsRequestBody(); // CustomTemplateParamsRequestBody | 
try {
    Map<String, Object> result = apiInstance.restCreateCustomTemplateParamsMap1(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restCreateCustomTemplateParamsMap1");
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

<a name="restExtractRelatedAnalisysReferences1"></a>
# **restExtractRelatedAnalisysReferences1**
> SearchResultAnalisysOutcome restExtractRelatedAnalisysReferences1(body, systemId)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
JiraResultsExtractionData body = new JiraResultsExtractionData(); // JiraResultsExtractionData | 
String systemId = "systemId_example"; // String | 
try {
    SearchResultAnalisysOutcome result = apiInstance.restExtractRelatedAnalisysReferences1(body, systemId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restExtractRelatedAnalisysReferences1");
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

<a name="restFindSystemById1"></a>
# **restFindSystemById1**
> SearchableSystemMetaData restFindSystemById1(systemId)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
String systemId = "systemId_example"; // String | 
try {
    SearchableSystemMetaData result = apiInstance.restFindSystemById1(systemId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restFindSystemById1");
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

<a name="restFindSystemBySearchResult1"></a>
# **restFindSystemBySearchResult1**
> SearchableSystemMetaData restFindSystemBySearchResult1(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
SearchResult body = new SearchResult(); // SearchResult | 
try {
    SearchableSystemMetaData result = apiInstance.restFindSystemBySearchResult1(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restFindSystemBySearchResult1");
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

<a name="restGetCachedCatalogues1"></a>
# **restGetCachedCatalogues1**
> List&lt;CatalogueSample&gt; restGetCachedCatalogues1(systemConfigurationCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
String systemConfigurationCode = "systemConfigurationCode_example"; // String | 
try {
    List<CatalogueSample> result = apiInstance.restGetCachedCatalogues1(systemConfigurationCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restGetCachedCatalogues1");
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

<a name="restGetCataloguesListSample1"></a>
# **restGetCataloguesListSample1**
> List&lt;CatalogueSample&gt; restGetCataloguesListSample1(configurationCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
String configurationCode = "configurationCode_example"; // String | 
try {
    List<CatalogueSample> result = apiInstance.restGetCataloguesListSample1(configurationCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restGetCataloguesListSample1");
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

<a name="restGetDescription1"></a>
# **restGetDescription1**
> String restGetDescription1()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
try {
    String result = apiInstance.restGetDescription1();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restGetDescription1");
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

<a name="restGetId1"></a>
# **restGetId1**
> String restGetId1()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
try {
    String result = apiInstance.restGetId1();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restGetId1");
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

<a name="restGetMessagingModuleId1"></a>
# **restGetMessagingModuleId1**
> String restGetMessagingModuleId1()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
try {
    String result = apiInstance.restGetMessagingModuleId1();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restGetMessagingModuleId1");
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

<a name="restGetNativePromptTemplateUseCode1"></a>
# **restGetNativePromptTemplateUseCode1**
> String restGetNativePromptTemplateUseCode1()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
try {
    String result = apiInstance.restGetNativePromptTemplateUseCode1();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restGetNativePromptTemplateUseCode1");
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

<a name="restGetProductId1"></a>
# **restGetProductId1**
> String restGetProductId1()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
try {
    String result = apiInstance.restGetProductId1();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restGetProductId1");
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

<a name="restGetQueriesGenerationPromptUseCode1"></a>
# **restGetQueriesGenerationPromptUseCode1**
> String restGetQueriesGenerationPromptUseCode1()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
try {
    String result = apiInstance.restGetQueriesGenerationPromptUseCode1();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restGetQueriesGenerationPromptUseCode1");
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

<a name="restGetSearchableSystems1"></a>
# **restGetSearchableSystems1**
> List&lt;SearchableSystemMetaData&gt; restGetSearchableSystems1()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
try {
    List<SearchableSystemMetaData> result = apiInstance.restGetSearchableSystems1();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restGetSearchableSystems1");
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

<a name="restIsEnabled1"></a>
# **restIsEnabled1**
> Boolean restIsEnabled1()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
try {
    Boolean result = apiInstance.restIsEnabled1();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restIsEnabled1");
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

<a name="restNativeSearch1"></a>
# **restNativeSearch1**
> List&lt;SearchResult&gt; restNativeSearch1(body, systemId, nEntryLimit)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
JiraIssuesSearchFilter body = new JiraIssuesSearchFilter(); // JiraIssuesSearchFilter | 
String systemId = "systemId_example"; // String | 
Integer nEntryLimit = 56; // Integer | 
try {
    List<SearchResult> result = apiInstance.restNativeSearch1(body, systemId, nEntryLimit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restNativeSearch1");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**JiraIssuesSearchFilter**](JiraIssuesSearchFilter.md)|  |
 **systemId** | **String**|  |
 **nEntryLimit** | **Integer**|  |

### Return type

[**List&lt;SearchResult&gt;**](SearchResult.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restSearch1"></a>
# **restSearch1**
> List&lt;SearchResult&gt; restSearch1(body, systemId, nEntryLimit)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
SearchQuery body = new SearchQuery(); // SearchQuery | 
String systemId = "systemId_example"; // String | 
Integer nEntryLimit = 56; // Integer | 
try {
    List<SearchResult> result = apiInstance.restSearch1(body, systemId, nEntryLimit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restSearch1");
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

