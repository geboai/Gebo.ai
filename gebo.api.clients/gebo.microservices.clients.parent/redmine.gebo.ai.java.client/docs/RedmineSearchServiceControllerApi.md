# RedmineSearchServiceControllerApi

All URIs are relative to *http://localhost:13021/redmine*

Method | HTTP request | Description
------------- | ------------- | -------------
[**restAggregateRedmine**](RedmineSearchServiceControllerApi.md#restAggregateRedmine) | **POST** /api/users/RedmineSearchServiceController/aggregate | 
[**restCreateCustomTemplateParamsMapRedmine**](RedmineSearchServiceControllerApi.md#restCreateCustomTemplateParamsMapRedmine) | **POST** /api/users/RedmineSearchServiceController/createCustomTemplateParamsMap | 
[**restExtractRelatedAnalisysReferencesRedmine**](RedmineSearchServiceControllerApi.md#restExtractRelatedAnalisysReferencesRedmine) | **POST** /api/users/RedmineSearchServiceController/extractRelatedAnalisysReferences | 
[**restFindSystemByIdRedmine**](RedmineSearchServiceControllerApi.md#restFindSystemByIdRedmine) | **GET** /api/users/RedmineSearchServiceController/findSystemById | 
[**restFindSystemBySearchResultRedmine**](RedmineSearchServiceControllerApi.md#restFindSystemBySearchResultRedmine) | **POST** /api/users/RedmineSearchServiceController/findSystemBySearchResult | 
[**restGetCachedCataloguesRedmine**](RedmineSearchServiceControllerApi.md#restGetCachedCataloguesRedmine) | **GET** /api/users/RedmineSearchServiceController/getCachedCatalogues | 
[**restGetCataloguesListSampleRedmine**](RedmineSearchServiceControllerApi.md#restGetCataloguesListSampleRedmine) | **GET** /api/users/RedmineSearchServiceController/getCataloguesListSample | 
[**restGetDescriptionRedmine**](RedmineSearchServiceControllerApi.md#restGetDescriptionRedmine) | **GET** /api/users/RedmineSearchServiceController/getDescription | 
[**restGetIdRedmine**](RedmineSearchServiceControllerApi.md#restGetIdRedmine) | **GET** /api/users/RedmineSearchServiceController/getId | 
[**restGetMessagingModuleIdRedmine**](RedmineSearchServiceControllerApi.md#restGetMessagingModuleIdRedmine) | **GET** /api/users/RedmineSearchServiceController/getMessagingModuleId | 
[**restGetNativePromptTemplateUseCodeRedmine**](RedmineSearchServiceControllerApi.md#restGetNativePromptTemplateUseCodeRedmine) | **GET** /api/users/RedmineSearchServiceController/getNativePromptTemplateUseCode | 
[**restGetProductIdRedmine**](RedmineSearchServiceControllerApi.md#restGetProductIdRedmine) | **GET** /api/users/RedmineSearchServiceController/getProductId | 
[**restGetQueriesGenerationPromptUseCodeRedmine**](RedmineSearchServiceControllerApi.md#restGetQueriesGenerationPromptUseCodeRedmine) | **GET** /api/users/RedmineSearchServiceController/getQueriesGenerationPromptUseCode | 
[**restGetSearchableSystemsRedmine**](RedmineSearchServiceControllerApi.md#restGetSearchableSystemsRedmine) | **GET** /api/users/RedmineSearchServiceController/getSearchableSystems | 
[**restIsEnabledRedmine**](RedmineSearchServiceControllerApi.md#restIsEnabledRedmine) | **GET** /api/users/RedmineSearchServiceController/isEnabled | 
[**restNativeSearchRedmine**](RedmineSearchServiceControllerApi.md#restNativeSearchRedmine) | **POST** /api/users/RedmineSearchServiceController/nativeSearch | 
[**restSearchRedmine**](RedmineSearchServiceControllerApi.md#restSearchRedmine) | **POST** /api/users/RedmineSearchServiceController/search | 

<a name="restAggregateRedmine"></a>
# **restAggregateRedmine**
> RedmineResultsExtractionData restAggregateRedmine(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineSearchServiceControllerApi;


RedmineSearchServiceControllerApi apiInstance = new RedmineSearchServiceControllerApi();
AggregateRequestBodyRedmineResultsExtractionData body = new AggregateRequestBodyRedmineResultsExtractionData(); // AggregateRequestBodyRedmineResultsExtractionData | 
try {
    RedmineResultsExtractionData result = apiInstance.restAggregateRedmine(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineSearchServiceControllerApi#restAggregateRedmine");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**AggregateRequestBodyRedmineResultsExtractionData**](AggregateRequestBodyRedmineResultsExtractionData.md)|  |

### Return type

[**RedmineResultsExtractionData**](RedmineResultsExtractionData.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restCreateCustomTemplateParamsMapRedmine"></a>
# **restCreateCustomTemplateParamsMapRedmine**
> Map&lt;String, Object&gt; restCreateCustomTemplateParamsMapRedmine(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineSearchServiceControllerApi;


RedmineSearchServiceControllerApi apiInstance = new RedmineSearchServiceControllerApi();
CustomTemplateParamsRequestBody body = new CustomTemplateParamsRequestBody(); // CustomTemplateParamsRequestBody | 
try {
    Map<String, Object> result = apiInstance.restCreateCustomTemplateParamsMapRedmine(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineSearchServiceControllerApi#restCreateCustomTemplateParamsMapRedmine");
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

<a name="restExtractRelatedAnalisysReferencesRedmine"></a>
# **restExtractRelatedAnalisysReferencesRedmine**
> SearchResultAnalisysOutcome restExtractRelatedAnalisysReferencesRedmine(body, systemId)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineSearchServiceControllerApi;


RedmineSearchServiceControllerApi apiInstance = new RedmineSearchServiceControllerApi();
RedmineResultsExtractionData body = new RedmineResultsExtractionData(); // RedmineResultsExtractionData | 
String systemId = "systemId_example"; // String | 
try {
    SearchResultAnalisysOutcome result = apiInstance.restExtractRelatedAnalisysReferencesRedmine(body, systemId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineSearchServiceControllerApi#restExtractRelatedAnalisysReferencesRedmine");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**RedmineResultsExtractionData**](RedmineResultsExtractionData.md)|  |
 **systemId** | **String**|  |

### Return type

[**SearchResultAnalisysOutcome**](SearchResultAnalisysOutcome.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restFindSystemByIdRedmine"></a>
# **restFindSystemByIdRedmine**
> SearchableSystemMetaData restFindSystemByIdRedmine(systemId)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineSearchServiceControllerApi;


RedmineSearchServiceControllerApi apiInstance = new RedmineSearchServiceControllerApi();
String systemId = "systemId_example"; // String | 
try {
    SearchableSystemMetaData result = apiInstance.restFindSystemByIdRedmine(systemId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineSearchServiceControllerApi#restFindSystemByIdRedmine");
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

<a name="restFindSystemBySearchResultRedmine"></a>
# **restFindSystemBySearchResultRedmine**
> SearchableSystemMetaData restFindSystemBySearchResultRedmine(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineSearchServiceControllerApi;


RedmineSearchServiceControllerApi apiInstance = new RedmineSearchServiceControllerApi();
SearchResult body = new SearchResult(); // SearchResult | 
try {
    SearchableSystemMetaData result = apiInstance.restFindSystemBySearchResultRedmine(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineSearchServiceControllerApi#restFindSystemBySearchResultRedmine");
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

<a name="restGetCachedCataloguesRedmine"></a>
# **restGetCachedCataloguesRedmine**
> List&lt;CatalogueSample&gt; restGetCachedCataloguesRedmine(systemConfigurationCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineSearchServiceControllerApi;


RedmineSearchServiceControllerApi apiInstance = new RedmineSearchServiceControllerApi();
String systemConfigurationCode = "systemConfigurationCode_example"; // String | 
try {
    List<CatalogueSample> result = apiInstance.restGetCachedCataloguesRedmine(systemConfigurationCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineSearchServiceControllerApi#restGetCachedCataloguesRedmine");
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

<a name="restGetCataloguesListSampleRedmine"></a>
# **restGetCataloguesListSampleRedmine**
> List&lt;CatalogueSample&gt; restGetCataloguesListSampleRedmine(configurationCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineSearchServiceControllerApi;


RedmineSearchServiceControllerApi apiInstance = new RedmineSearchServiceControllerApi();
String configurationCode = "configurationCode_example"; // String | 
try {
    List<CatalogueSample> result = apiInstance.restGetCataloguesListSampleRedmine(configurationCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineSearchServiceControllerApi#restGetCataloguesListSampleRedmine");
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

<a name="restGetDescriptionRedmine"></a>
# **restGetDescriptionRedmine**
> String restGetDescriptionRedmine()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineSearchServiceControllerApi;


RedmineSearchServiceControllerApi apiInstance = new RedmineSearchServiceControllerApi();
try {
    String result = apiInstance.restGetDescriptionRedmine();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineSearchServiceControllerApi#restGetDescriptionRedmine");
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

<a name="restGetIdRedmine"></a>
# **restGetIdRedmine**
> String restGetIdRedmine()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineSearchServiceControllerApi;


RedmineSearchServiceControllerApi apiInstance = new RedmineSearchServiceControllerApi();
try {
    String result = apiInstance.restGetIdRedmine();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineSearchServiceControllerApi#restGetIdRedmine");
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

<a name="restGetMessagingModuleIdRedmine"></a>
# **restGetMessagingModuleIdRedmine**
> String restGetMessagingModuleIdRedmine()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineSearchServiceControllerApi;


RedmineSearchServiceControllerApi apiInstance = new RedmineSearchServiceControllerApi();
try {
    String result = apiInstance.restGetMessagingModuleIdRedmine();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineSearchServiceControllerApi#restGetMessagingModuleIdRedmine");
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

<a name="restGetNativePromptTemplateUseCodeRedmine"></a>
# **restGetNativePromptTemplateUseCodeRedmine**
> String restGetNativePromptTemplateUseCodeRedmine()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineSearchServiceControllerApi;


RedmineSearchServiceControllerApi apiInstance = new RedmineSearchServiceControllerApi();
try {
    String result = apiInstance.restGetNativePromptTemplateUseCodeRedmine();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineSearchServiceControllerApi#restGetNativePromptTemplateUseCodeRedmine");
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

<a name="restGetProductIdRedmine"></a>
# **restGetProductIdRedmine**
> String restGetProductIdRedmine()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineSearchServiceControllerApi;


RedmineSearchServiceControllerApi apiInstance = new RedmineSearchServiceControllerApi();
try {
    String result = apiInstance.restGetProductIdRedmine();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineSearchServiceControllerApi#restGetProductIdRedmine");
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

<a name="restGetQueriesGenerationPromptUseCodeRedmine"></a>
# **restGetQueriesGenerationPromptUseCodeRedmine**
> String restGetQueriesGenerationPromptUseCodeRedmine()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineSearchServiceControllerApi;


RedmineSearchServiceControllerApi apiInstance = new RedmineSearchServiceControllerApi();
try {
    String result = apiInstance.restGetQueriesGenerationPromptUseCodeRedmine();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineSearchServiceControllerApi#restGetQueriesGenerationPromptUseCodeRedmine");
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

<a name="restGetSearchableSystemsRedmine"></a>
# **restGetSearchableSystemsRedmine**
> List&lt;SearchableSystemMetaData&gt; restGetSearchableSystemsRedmine()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineSearchServiceControllerApi;


RedmineSearchServiceControllerApi apiInstance = new RedmineSearchServiceControllerApi();
try {
    List<SearchableSystemMetaData> result = apiInstance.restGetSearchableSystemsRedmine();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineSearchServiceControllerApi#restGetSearchableSystemsRedmine");
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

<a name="restIsEnabledRedmine"></a>
# **restIsEnabledRedmine**
> Boolean restIsEnabledRedmine()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineSearchServiceControllerApi;


RedmineSearchServiceControllerApi apiInstance = new RedmineSearchServiceControllerApi();
try {
    Boolean result = apiInstance.restIsEnabledRedmine();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineSearchServiceControllerApi#restIsEnabledRedmine");
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

<a name="restNativeSearchRedmine"></a>
# **restNativeSearchRedmine**
> List&lt;SearchResult&gt; restNativeSearchRedmine(body, systemId, nEntryLimit, connectTimeoutMillis, readTimeoutMillis, retries)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineSearchServiceControllerApi;


RedmineSearchServiceControllerApi apiInstance = new RedmineSearchServiceControllerApi();
RedmineIssuesSearchFilter body = new RedmineIssuesSearchFilter(); // RedmineIssuesSearchFilter | 
String systemId = "systemId_example"; // String | 
Integer nEntryLimit = 56; // Integer | 
Integer connectTimeoutMillis = 56; // Integer | 
Integer readTimeoutMillis = 56; // Integer | 
Integer retries = 56; // Integer | 
try {
    List<SearchResult> result = apiInstance.restNativeSearchRedmine(body, systemId, nEntryLimit, connectTimeoutMillis, readTimeoutMillis, retries);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineSearchServiceControllerApi#restNativeSearchRedmine");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**RedmineIssuesSearchFilter**](RedmineIssuesSearchFilter.md)|  |
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

<a name="restSearchRedmine"></a>
# **restSearchRedmine**
> List&lt;SearchResult&gt; restSearchRedmine(body, systemId, nEntryLimit, connectTimeoutMillis, readTimeoutMillis, retries)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineSearchServiceControllerApi;


RedmineSearchServiceControllerApi apiInstance = new RedmineSearchServiceControllerApi();
SearchQuery body = new SearchQuery(); // SearchQuery | 
String systemId = "systemId_example"; // String | 
Integer nEntryLimit = 56; // Integer | 
Integer connectTimeoutMillis = 56; // Integer | 
Integer readTimeoutMillis = 56; // Integer | 
Integer retries = 56; // Integer | 
try {
    List<SearchResult> result = apiInstance.restSearchRedmine(body, systemId, nEntryLimit, connectTimeoutMillis, readTimeoutMillis, retries);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineSearchServiceControllerApi#restSearchRedmine");
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

