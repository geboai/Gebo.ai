# JiraSearchServiceControllerApi

All URIs are relative to *http://localhost:13011/jira*

Method | HTTP request | Description
------------- | ------------- | -------------
[**restAggregate**](JiraSearchServiceControllerApi.md#restAggregate) | **POST** /api/users/JiraSearchServiceController/aggregate | 
[**restCreateCustomTemplateParamsMap**](JiraSearchServiceControllerApi.md#restCreateCustomTemplateParamsMap) | **POST** /api/users/JiraSearchServiceController/createCustomTemplateParamsMap | 
[**restExtractRelatedAnalisysReferences**](JiraSearchServiceControllerApi.md#restExtractRelatedAnalisysReferences) | **POST** /api/users/JiraSearchServiceController/extractRelatedAnalisysReferences | 
[**restFindSystemById**](JiraSearchServiceControllerApi.md#restFindSystemById) | **GET** /api/users/JiraSearchServiceController/findSystemById | 
[**restFindSystemBySearchResult**](JiraSearchServiceControllerApi.md#restFindSystemBySearchResult) | **POST** /api/users/JiraSearchServiceController/findSystemBySearchResult | 
[**restGetCachedCatalogues**](JiraSearchServiceControllerApi.md#restGetCachedCatalogues) | **GET** /api/users/JiraSearchServiceController/getCachedCatalogues | 
[**restGetCataloguesListSample**](JiraSearchServiceControllerApi.md#restGetCataloguesListSample) | **GET** /api/users/JiraSearchServiceController/getCataloguesListSample | 
[**restGetDescription**](JiraSearchServiceControllerApi.md#restGetDescription) | **GET** /api/users/JiraSearchServiceController/getDescription | 
[**restGetId**](JiraSearchServiceControllerApi.md#restGetId) | **GET** /api/users/JiraSearchServiceController/getId | 
[**restGetMessagingModuleId**](JiraSearchServiceControllerApi.md#restGetMessagingModuleId) | **GET** /api/users/JiraSearchServiceController/getMessagingModuleId | 
[**restGetNativePromptTemplateUseCode**](JiraSearchServiceControllerApi.md#restGetNativePromptTemplateUseCode) | **GET** /api/users/JiraSearchServiceController/getNativePromptTemplateUseCode | 
[**restGetProductId**](JiraSearchServiceControllerApi.md#restGetProductId) | **GET** /api/users/JiraSearchServiceController/getProductId | 
[**restGetQueriesGenerationPromptUseCode**](JiraSearchServiceControllerApi.md#restGetQueriesGenerationPromptUseCode) | **GET** /api/users/JiraSearchServiceController/getQueriesGenerationPromptUseCode | 
[**restGetSearchableSystems**](JiraSearchServiceControllerApi.md#restGetSearchableSystems) | **GET** /api/users/JiraSearchServiceController/getSearchableSystems | 
[**restIsEnabled**](JiraSearchServiceControllerApi.md#restIsEnabled) | **GET** /api/users/JiraSearchServiceController/isEnabled | 
[**restNativeSearch**](JiraSearchServiceControllerApi.md#restNativeSearch) | **POST** /api/users/JiraSearchServiceController/nativeSearch | 
[**restSearch**](JiraSearchServiceControllerApi.md#restSearch) | **POST** /api/users/JiraSearchServiceController/search | 

<a name="restAggregate"></a>
# **restAggregate**
> JiraResultsExtractionData restAggregate(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
AggregateRequestBodyJiraResultsExtractionData body = new AggregateRequestBodyJiraResultsExtractionData(); // AggregateRequestBodyJiraResultsExtractionData | 
try {
    JiraResultsExtractionData result = apiInstance.restAggregate(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restAggregate");
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

<a name="restCreateCustomTemplateParamsMap"></a>
# **restCreateCustomTemplateParamsMap**
> Object restCreateCustomTemplateParamsMap(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
CustomTemplateParamsRequestBody body = new CustomTemplateParamsRequestBody(); // CustomTemplateParamsRequestBody | 
try {
    Object result = apiInstance.restCreateCustomTemplateParamsMap(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restCreateCustomTemplateParamsMap");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**CustomTemplateParamsRequestBody**](CustomTemplateParamsRequestBody.md)|  |

### Return type

**Object**

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
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
JiraResultsExtractionData body = new JiraResultsExtractionData(); // JiraResultsExtractionData | 
Object systemId = null; // Object | 
try {
    SearchResultAnalisysOutcome result = apiInstance.restExtractRelatedAnalisysReferences(body, systemId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restExtractRelatedAnalisysReferences");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**JiraResultsExtractionData**](JiraResultsExtractionData.md)|  |
 **systemId** | [**Object**](.md)|  |

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
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
Object systemId = null; // Object | 
try {
    SearchableSystemMetaData result = apiInstance.restFindSystemById(systemId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restFindSystemById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **systemId** | [**Object**](.md)|  |

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
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
SearchResult body = new SearchResult(); // SearchResult | 
try {
    SearchableSystemMetaData result = apiInstance.restFindSystemBySearchResult(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restFindSystemBySearchResult");
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
> Object restGetCachedCatalogues(systemConfigurationCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
Object systemConfigurationCode = null; // Object | 
try {
    Object result = apiInstance.restGetCachedCatalogues(systemConfigurationCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restGetCachedCatalogues");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **systemConfigurationCode** | [**Object**](.md)|  | [optional]

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="restGetCataloguesListSample"></a>
# **restGetCataloguesListSample**
> Object restGetCataloguesListSample(configurationCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
Object configurationCode = null; // Object | 
try {
    Object result = apiInstance.restGetCataloguesListSample(configurationCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restGetCataloguesListSample");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **configurationCode** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="restGetDescription"></a>
# **restGetDescription**
> Object restGetDescription()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
try {
    Object result = apiInstance.restGetDescription();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restGetDescription");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="restGetId"></a>
# **restGetId**
> Object restGetId()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
try {
    Object result = apiInstance.restGetId();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restGetId");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="restGetMessagingModuleId"></a>
# **restGetMessagingModuleId**
> Object restGetMessagingModuleId()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
try {
    Object result = apiInstance.restGetMessagingModuleId();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restGetMessagingModuleId");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="restGetNativePromptTemplateUseCode"></a>
# **restGetNativePromptTemplateUseCode**
> Object restGetNativePromptTemplateUseCode()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
try {
    Object result = apiInstance.restGetNativePromptTemplateUseCode();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restGetNativePromptTemplateUseCode");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="restGetProductId"></a>
# **restGetProductId**
> Object restGetProductId()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
try {
    Object result = apiInstance.restGetProductId();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restGetProductId");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="restGetQueriesGenerationPromptUseCode"></a>
# **restGetQueriesGenerationPromptUseCode**
> Object restGetQueriesGenerationPromptUseCode()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
try {
    Object result = apiInstance.restGetQueriesGenerationPromptUseCode();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restGetQueriesGenerationPromptUseCode");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="restGetSearchableSystems"></a>
# **restGetSearchableSystems**
> Object restGetSearchableSystems()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
try {
    Object result = apiInstance.restGetSearchableSystems();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restGetSearchableSystems");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="restIsEnabled"></a>
# **restIsEnabled**
> Object restIsEnabled()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
try {
    Object result = apiInstance.restIsEnabled();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restIsEnabled");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="restNativeSearch"></a>
# **restNativeSearch**
> Object restNativeSearch(body, systemId, nEntryLimit)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
JiraIssuesSearchFilter body = new JiraIssuesSearchFilter(); // JiraIssuesSearchFilter | 
Object systemId = null; // Object | 
Object nEntryLimit = null; // Object | 
try {
    Object result = apiInstance.restNativeSearch(body, systemId, nEntryLimit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restNativeSearch");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**JiraIssuesSearchFilter**](JiraIssuesSearchFilter.md)|  |
 **systemId** | [**Object**](.md)|  |
 **nEntryLimit** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restSearch"></a>
# **restSearch**
> Object restSearch(body, systemId, nEntryLimit)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraSearchServiceControllerApi;


JiraSearchServiceControllerApi apiInstance = new JiraSearchServiceControllerApi();
SearchQuery body = new SearchQuery(); // SearchQuery | 
Object systemId = null; // Object | 
Object nEntryLimit = null; // Object | 
try {
    Object result = apiInstance.restSearch(body, systemId, nEntryLimit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSearchServiceControllerApi#restSearch");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SearchQuery**](SearchQuery.md)|  |
 **systemId** | [**Object**](.md)|  |
 **nEntryLimit** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

