# OpenProjectSearchServiceControllerApi

All URIs are relative to *http://localhost:13023/openproject*

Method | HTTP request | Description
------------- | ------------- | -------------
[**restAggregateOpenProject**](OpenProjectSearchServiceControllerApi.md#restAggregateOpenProject) | **POST** /api/users/OpenProjectSearchServiceController/aggregate | 
[**restCreateCustomTemplateParamsMapOpenProject**](OpenProjectSearchServiceControllerApi.md#restCreateCustomTemplateParamsMapOpenProject) | **POST** /api/users/OpenProjectSearchServiceController/createCustomTemplateParamsMap | 
[**restExtractRelatedAnalisysReferencesOpenProject**](OpenProjectSearchServiceControllerApi.md#restExtractRelatedAnalisysReferencesOpenProject) | **POST** /api/users/OpenProjectSearchServiceController/extractRelatedAnalisysReferences | 
[**restFindSystemByIdOpenProject**](OpenProjectSearchServiceControllerApi.md#restFindSystemByIdOpenProject) | **GET** /api/users/OpenProjectSearchServiceController/findSystemById | 
[**restFindSystemBySearchResultOpenProject**](OpenProjectSearchServiceControllerApi.md#restFindSystemBySearchResultOpenProject) | **POST** /api/users/OpenProjectSearchServiceController/findSystemBySearchResult | 
[**restGetCachedCataloguesOpenProject**](OpenProjectSearchServiceControllerApi.md#restGetCachedCataloguesOpenProject) | **GET** /api/users/OpenProjectSearchServiceController/getCachedCatalogues | 
[**restGetCataloguesListSampleOpenProject**](OpenProjectSearchServiceControllerApi.md#restGetCataloguesListSampleOpenProject) | **GET** /api/users/OpenProjectSearchServiceController/getCataloguesListSample | 
[**restGetDescriptionOpenProject**](OpenProjectSearchServiceControllerApi.md#restGetDescriptionOpenProject) | **GET** /api/users/OpenProjectSearchServiceController/getDescription | 
[**restGetIdOpenProject**](OpenProjectSearchServiceControllerApi.md#restGetIdOpenProject) | **GET** /api/users/OpenProjectSearchServiceController/getId | 
[**restGetMessagingModuleIdOpenProject**](OpenProjectSearchServiceControllerApi.md#restGetMessagingModuleIdOpenProject) | **GET** /api/users/OpenProjectSearchServiceController/getMessagingModuleId | 
[**restGetNativePromptTemplateUseCodeOpenProject**](OpenProjectSearchServiceControllerApi.md#restGetNativePromptTemplateUseCodeOpenProject) | **GET** /api/users/OpenProjectSearchServiceController/getNativePromptTemplateUseCode | 
[**restGetProductIdOpenProject**](OpenProjectSearchServiceControllerApi.md#restGetProductIdOpenProject) | **GET** /api/users/OpenProjectSearchServiceController/getProductId | 
[**restGetQueriesGenerationPromptUseCodeOpenProject**](OpenProjectSearchServiceControllerApi.md#restGetQueriesGenerationPromptUseCodeOpenProject) | **GET** /api/users/OpenProjectSearchServiceController/getQueriesGenerationPromptUseCode | 
[**restGetSearchableSystemsOpenProject**](OpenProjectSearchServiceControllerApi.md#restGetSearchableSystemsOpenProject) | **GET** /api/users/OpenProjectSearchServiceController/getSearchableSystems | 
[**restIsEnabledOpenProject**](OpenProjectSearchServiceControllerApi.md#restIsEnabledOpenProject) | **GET** /api/users/OpenProjectSearchServiceController/isEnabled | 
[**restNativeSearchOpenProject**](OpenProjectSearchServiceControllerApi.md#restNativeSearchOpenProject) | **POST** /api/users/OpenProjectSearchServiceController/nativeSearch | 
[**restSearchOpenProject**](OpenProjectSearchServiceControllerApi.md#restSearchOpenProject) | **POST** /api/users/OpenProjectSearchServiceController/search | 

<a name="restAggregateOpenProject"></a>
# **restAggregateOpenProject**
> OpenProjectResultsExtractionData restAggregateOpenProject(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectSearchServiceControllerApi;


OpenProjectSearchServiceControllerApi apiInstance = new OpenProjectSearchServiceControllerApi();
AggregateRequestBodyOpenProjectResultsExtractionData body = new AggregateRequestBodyOpenProjectResultsExtractionData(); // AggregateRequestBodyOpenProjectResultsExtractionData | 
try {
    OpenProjectResultsExtractionData result = apiInstance.restAggregateOpenProject(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectSearchServiceControllerApi#restAggregateOpenProject");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**AggregateRequestBodyOpenProjectResultsExtractionData**](AggregateRequestBodyOpenProjectResultsExtractionData.md)|  |

### Return type

[**OpenProjectResultsExtractionData**](OpenProjectResultsExtractionData.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restCreateCustomTemplateParamsMapOpenProject"></a>
# **restCreateCustomTemplateParamsMapOpenProject**
> Map&lt;String, Object&gt; restCreateCustomTemplateParamsMapOpenProject(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectSearchServiceControllerApi;


OpenProjectSearchServiceControllerApi apiInstance = new OpenProjectSearchServiceControllerApi();
CustomTemplateParamsRequestBody body = new CustomTemplateParamsRequestBody(); // CustomTemplateParamsRequestBody | 
try {
    Map<String, Object> result = apiInstance.restCreateCustomTemplateParamsMapOpenProject(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectSearchServiceControllerApi#restCreateCustomTemplateParamsMapOpenProject");
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

<a name="restExtractRelatedAnalisysReferencesOpenProject"></a>
# **restExtractRelatedAnalisysReferencesOpenProject**
> SearchResultAnalisysOutcome restExtractRelatedAnalisysReferencesOpenProject(body, systemId)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectSearchServiceControllerApi;


OpenProjectSearchServiceControllerApi apiInstance = new OpenProjectSearchServiceControllerApi();
OpenProjectResultsExtractionData body = new OpenProjectResultsExtractionData(); // OpenProjectResultsExtractionData | 
String systemId = "systemId_example"; // String | 
try {
    SearchResultAnalisysOutcome result = apiInstance.restExtractRelatedAnalisysReferencesOpenProject(body, systemId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectSearchServiceControllerApi#restExtractRelatedAnalisysReferencesOpenProject");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**OpenProjectResultsExtractionData**](OpenProjectResultsExtractionData.md)|  |
 **systemId** | **String**|  |

### Return type

[**SearchResultAnalisysOutcome**](SearchResultAnalisysOutcome.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restFindSystemByIdOpenProject"></a>
# **restFindSystemByIdOpenProject**
> SearchableSystemMetaData restFindSystemByIdOpenProject(systemId)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectSearchServiceControllerApi;


OpenProjectSearchServiceControllerApi apiInstance = new OpenProjectSearchServiceControllerApi();
String systemId = "systemId_example"; // String | 
try {
    SearchableSystemMetaData result = apiInstance.restFindSystemByIdOpenProject(systemId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectSearchServiceControllerApi#restFindSystemByIdOpenProject");
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

<a name="restFindSystemBySearchResultOpenProject"></a>
# **restFindSystemBySearchResultOpenProject**
> SearchableSystemMetaData restFindSystemBySearchResultOpenProject(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectSearchServiceControllerApi;


OpenProjectSearchServiceControllerApi apiInstance = new OpenProjectSearchServiceControllerApi();
SearchResult body = new SearchResult(); // SearchResult | 
try {
    SearchableSystemMetaData result = apiInstance.restFindSystemBySearchResultOpenProject(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectSearchServiceControllerApi#restFindSystemBySearchResultOpenProject");
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

<a name="restGetCachedCataloguesOpenProject"></a>
# **restGetCachedCataloguesOpenProject**
> List&lt;CatalogueSample&gt; restGetCachedCataloguesOpenProject(systemConfigurationCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectSearchServiceControllerApi;


OpenProjectSearchServiceControllerApi apiInstance = new OpenProjectSearchServiceControllerApi();
String systemConfigurationCode = "systemConfigurationCode_example"; // String | 
try {
    List<CatalogueSample> result = apiInstance.restGetCachedCataloguesOpenProject(systemConfigurationCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectSearchServiceControllerApi#restGetCachedCataloguesOpenProject");
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

<a name="restGetCataloguesListSampleOpenProject"></a>
# **restGetCataloguesListSampleOpenProject**
> List&lt;CatalogueSample&gt; restGetCataloguesListSampleOpenProject(configurationCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectSearchServiceControllerApi;


OpenProjectSearchServiceControllerApi apiInstance = new OpenProjectSearchServiceControllerApi();
String configurationCode = "configurationCode_example"; // String | 
try {
    List<CatalogueSample> result = apiInstance.restGetCataloguesListSampleOpenProject(configurationCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectSearchServiceControllerApi#restGetCataloguesListSampleOpenProject");
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

<a name="restGetDescriptionOpenProject"></a>
# **restGetDescriptionOpenProject**
> String restGetDescriptionOpenProject()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectSearchServiceControllerApi;


OpenProjectSearchServiceControllerApi apiInstance = new OpenProjectSearchServiceControllerApi();
try {
    String result = apiInstance.restGetDescriptionOpenProject();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectSearchServiceControllerApi#restGetDescriptionOpenProject");
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

<a name="restGetIdOpenProject"></a>
# **restGetIdOpenProject**
> String restGetIdOpenProject()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectSearchServiceControllerApi;


OpenProjectSearchServiceControllerApi apiInstance = new OpenProjectSearchServiceControllerApi();
try {
    String result = apiInstance.restGetIdOpenProject();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectSearchServiceControllerApi#restGetIdOpenProject");
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

<a name="restGetMessagingModuleIdOpenProject"></a>
# **restGetMessagingModuleIdOpenProject**
> String restGetMessagingModuleIdOpenProject()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectSearchServiceControllerApi;


OpenProjectSearchServiceControllerApi apiInstance = new OpenProjectSearchServiceControllerApi();
try {
    String result = apiInstance.restGetMessagingModuleIdOpenProject();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectSearchServiceControllerApi#restGetMessagingModuleIdOpenProject");
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

<a name="restGetNativePromptTemplateUseCodeOpenProject"></a>
# **restGetNativePromptTemplateUseCodeOpenProject**
> String restGetNativePromptTemplateUseCodeOpenProject()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectSearchServiceControllerApi;


OpenProjectSearchServiceControllerApi apiInstance = new OpenProjectSearchServiceControllerApi();
try {
    String result = apiInstance.restGetNativePromptTemplateUseCodeOpenProject();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectSearchServiceControllerApi#restGetNativePromptTemplateUseCodeOpenProject");
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

<a name="restGetProductIdOpenProject"></a>
# **restGetProductIdOpenProject**
> String restGetProductIdOpenProject()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectSearchServiceControllerApi;


OpenProjectSearchServiceControllerApi apiInstance = new OpenProjectSearchServiceControllerApi();
try {
    String result = apiInstance.restGetProductIdOpenProject();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectSearchServiceControllerApi#restGetProductIdOpenProject");
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

<a name="restGetQueriesGenerationPromptUseCodeOpenProject"></a>
# **restGetQueriesGenerationPromptUseCodeOpenProject**
> String restGetQueriesGenerationPromptUseCodeOpenProject()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectSearchServiceControllerApi;


OpenProjectSearchServiceControllerApi apiInstance = new OpenProjectSearchServiceControllerApi();
try {
    String result = apiInstance.restGetQueriesGenerationPromptUseCodeOpenProject();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectSearchServiceControllerApi#restGetQueriesGenerationPromptUseCodeOpenProject");
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

<a name="restGetSearchableSystemsOpenProject"></a>
# **restGetSearchableSystemsOpenProject**
> List&lt;SearchableSystemMetaData&gt; restGetSearchableSystemsOpenProject()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectSearchServiceControllerApi;


OpenProjectSearchServiceControllerApi apiInstance = new OpenProjectSearchServiceControllerApi();
try {
    List<SearchableSystemMetaData> result = apiInstance.restGetSearchableSystemsOpenProject();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectSearchServiceControllerApi#restGetSearchableSystemsOpenProject");
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

<a name="restIsEnabledOpenProject"></a>
# **restIsEnabledOpenProject**
> Boolean restIsEnabledOpenProject()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectSearchServiceControllerApi;


OpenProjectSearchServiceControllerApi apiInstance = new OpenProjectSearchServiceControllerApi();
try {
    Boolean result = apiInstance.restIsEnabledOpenProject();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectSearchServiceControllerApi#restIsEnabledOpenProject");
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

<a name="restNativeSearchOpenProject"></a>
# **restNativeSearchOpenProject**
> List&lt;SearchResult&gt; restNativeSearchOpenProject(body, systemId, nEntryLimit, connectTimeoutMillis, readTimeoutMillis, retries)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectSearchServiceControllerApi;


OpenProjectSearchServiceControllerApi apiInstance = new OpenProjectSearchServiceControllerApi();
OpenProjectWorkPackagesSearchFilter body = new OpenProjectWorkPackagesSearchFilter(); // OpenProjectWorkPackagesSearchFilter | 
String systemId = "systemId_example"; // String | 
Integer nEntryLimit = 56; // Integer | 
Integer connectTimeoutMillis = 56; // Integer | 
Integer readTimeoutMillis = 56; // Integer | 
Integer retries = 56; // Integer | 
try {
    List<SearchResult> result = apiInstance.restNativeSearchOpenProject(body, systemId, nEntryLimit, connectTimeoutMillis, readTimeoutMillis, retries);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectSearchServiceControllerApi#restNativeSearchOpenProject");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**OpenProjectWorkPackagesSearchFilter**](OpenProjectWorkPackagesSearchFilter.md)|  |
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

<a name="restSearchOpenProject"></a>
# **restSearchOpenProject**
> List&lt;SearchResult&gt; restSearchOpenProject(body, systemId, nEntryLimit, connectTimeoutMillis, readTimeoutMillis, retries)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectSearchServiceControllerApi;


OpenProjectSearchServiceControllerApi apiInstance = new OpenProjectSearchServiceControllerApi();
SearchQuery body = new SearchQuery(); // SearchQuery | 
String systemId = "systemId_example"; // String | 
Integer nEntryLimit = 56; // Integer | 
Integer connectTimeoutMillis = 56; // Integer | 
Integer readTimeoutMillis = 56; // Integer | 
Integer retries = 56; // Integer | 
try {
    List<SearchResult> result = apiInstance.restSearchOpenProject(body, systemId, nEntryLimit, connectTimeoutMillis, readTimeoutMillis, retries);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectSearchServiceControllerApi#restSearchOpenProject");
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

