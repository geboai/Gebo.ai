# GoogleDriveSearchServiceControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**restAggregateGoogleDrive**](GoogleDriveSearchServiceControllerApi.md#restAggregateGoogleDrive) | **POST** /api/users/GoogleDriveSearchServiceController/aggregate | 
[**restExtractRelatedAnalisysReferencesGoogleDrive**](GoogleDriveSearchServiceControllerApi.md#restExtractRelatedAnalisysReferencesGoogleDrive) | **POST** /api/users/GoogleDriveSearchServiceController/extractRelatedAnalisysReferences | 
[**restFindSystemByIdGoogleDrive**](GoogleDriveSearchServiceControllerApi.md#restFindSystemByIdGoogleDrive) | **GET** /api/users/GoogleDriveSearchServiceController/findSystemById | 
[**restFindSystemBySearchResultGoogleDrive**](GoogleDriveSearchServiceControllerApi.md#restFindSystemBySearchResultGoogleDrive) | **POST** /api/users/GoogleDriveSearchServiceController/findSystemBySearchResult | 
[**restGetCachedCataloguesGoogleDrive**](GoogleDriveSearchServiceControllerApi.md#restGetCachedCataloguesGoogleDrive) | **GET** /api/users/GoogleDriveSearchServiceController/getCachedCatalogues | 
[**restGetCataloguesListSampleGoogleDrive**](GoogleDriveSearchServiceControllerApi.md#restGetCataloguesListSampleGoogleDrive) | **GET** /api/users/GoogleDriveSearchServiceController/getCataloguesListSample | 
[**restGetDescriptionGoogleDrive**](GoogleDriveSearchServiceControllerApi.md#restGetDescriptionGoogleDrive) | **GET** /api/users/GoogleDriveSearchServiceController/getDescription | 
[**restGetIdGoogleDrive**](GoogleDriveSearchServiceControllerApi.md#restGetIdGoogleDrive) | **GET** /api/users/GoogleDriveSearchServiceController/getId | 
[**restGetMessagingModuleIdGoogleDrive**](GoogleDriveSearchServiceControllerApi.md#restGetMessagingModuleIdGoogleDrive) | **GET** /api/users/GoogleDriveSearchServiceController/getMessagingModuleId | 
[**restGetProductIdGoogleDrive**](GoogleDriveSearchServiceControllerApi.md#restGetProductIdGoogleDrive) | **GET** /api/users/GoogleDriveSearchServiceController/getProductId | 
[**restGetQueriesGenerationPromptUseCodeGoogleDrive**](GoogleDriveSearchServiceControllerApi.md#restGetQueriesGenerationPromptUseCodeGoogleDrive) | **GET** /api/users/GoogleDriveSearchServiceController/getQueriesGenerationPromptUseCode | 
[**restGetSearchableSystemsGoogleDrive**](GoogleDriveSearchServiceControllerApi.md#restGetSearchableSystemsGoogleDrive) | **GET** /api/users/GoogleDriveSearchServiceController/getSearchableSystems | 
[**restIsEnabledGoogleDrive**](GoogleDriveSearchServiceControllerApi.md#restIsEnabledGoogleDrive) | **GET** /api/users/GoogleDriveSearchServiceController/isEnabled | 
[**restSearchGoogleDrive**](GoogleDriveSearchServiceControllerApi.md#restSearchGoogleDrive) | **POST** /api/users/GoogleDriveSearchServiceController/search | 

<a name="restAggregateGoogleDrive"></a>
# **restAggregateGoogleDrive**
> GoogleDriveResultsExtractionData restAggregateGoogleDrive(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
AggregateRequestBodyGoogleDriveResultsExtractionData body = new AggregateRequestBodyGoogleDriveResultsExtractionData(); // AggregateRequestBodyGoogleDriveResultsExtractionData | 
try {
    GoogleDriveResultsExtractionData result = apiInstance.restAggregateGoogleDrive(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restAggregateGoogleDrive");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**AggregateRequestBodyGoogleDriveResultsExtractionData**](AggregateRequestBodyGoogleDriveResultsExtractionData.md)|  |

### Return type

[**GoogleDriveResultsExtractionData**](GoogleDriveResultsExtractionData.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restExtractRelatedAnalisysReferencesGoogleDrive"></a>
# **restExtractRelatedAnalisysReferencesGoogleDrive**
> SearchResultAnalisysOutcome restExtractRelatedAnalisysReferencesGoogleDrive(body, systemId)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
GoogleDriveResultsExtractionData body = new GoogleDriveResultsExtractionData(); // GoogleDriveResultsExtractionData | 
String systemId = "systemId_example"; // String | 
try {
    SearchResultAnalisysOutcome result = apiInstance.restExtractRelatedAnalisysReferencesGoogleDrive(body, systemId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restExtractRelatedAnalisysReferencesGoogleDrive");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GoogleDriveResultsExtractionData**](GoogleDriveResultsExtractionData.md)|  |
 **systemId** | **String**|  |

### Return type

[**SearchResultAnalisysOutcome**](SearchResultAnalisysOutcome.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restFindSystemByIdGoogleDrive"></a>
# **restFindSystemByIdGoogleDrive**
> SearchableSystemMetaData restFindSystemByIdGoogleDrive(systemId)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
String systemId = "systemId_example"; // String | 
try {
    SearchableSystemMetaData result = apiInstance.restFindSystemByIdGoogleDrive(systemId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restFindSystemByIdGoogleDrive");
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

<a name="restFindSystemBySearchResultGoogleDrive"></a>
# **restFindSystemBySearchResultGoogleDrive**
> SearchableSystemMetaData restFindSystemBySearchResultGoogleDrive(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
SearchResult body = new SearchResult(); // SearchResult | 
try {
    SearchableSystemMetaData result = apiInstance.restFindSystemBySearchResultGoogleDrive(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restFindSystemBySearchResultGoogleDrive");
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

<a name="restGetCachedCataloguesGoogleDrive"></a>
# **restGetCachedCataloguesGoogleDrive**
> List&lt;CatalogueSample&gt; restGetCachedCataloguesGoogleDrive(systemConfigurationCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
String systemConfigurationCode = "systemConfigurationCode_example"; // String | 
try {
    List<CatalogueSample> result = apiInstance.restGetCachedCataloguesGoogleDrive(systemConfigurationCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restGetCachedCataloguesGoogleDrive");
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

<a name="restGetCataloguesListSampleGoogleDrive"></a>
# **restGetCataloguesListSampleGoogleDrive**
> List&lt;CatalogueSample&gt; restGetCataloguesListSampleGoogleDrive(configurationCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
String configurationCode = "configurationCode_example"; // String | 
try {
    List<CatalogueSample> result = apiInstance.restGetCataloguesListSampleGoogleDrive(configurationCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restGetCataloguesListSampleGoogleDrive");
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

<a name="restGetDescriptionGoogleDrive"></a>
# **restGetDescriptionGoogleDrive**
> String restGetDescriptionGoogleDrive()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
try {
    String result = apiInstance.restGetDescriptionGoogleDrive();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restGetDescriptionGoogleDrive");
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

<a name="restGetIdGoogleDrive"></a>
# **restGetIdGoogleDrive**
> String restGetIdGoogleDrive()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
try {
    String result = apiInstance.restGetIdGoogleDrive();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restGetIdGoogleDrive");
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

<a name="restGetMessagingModuleIdGoogleDrive"></a>
# **restGetMessagingModuleIdGoogleDrive**
> String restGetMessagingModuleIdGoogleDrive()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
try {
    String result = apiInstance.restGetMessagingModuleIdGoogleDrive();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restGetMessagingModuleIdGoogleDrive");
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

<a name="restGetProductIdGoogleDrive"></a>
# **restGetProductIdGoogleDrive**
> String restGetProductIdGoogleDrive()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
try {
    String result = apiInstance.restGetProductIdGoogleDrive();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restGetProductIdGoogleDrive");
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

<a name="restGetQueriesGenerationPromptUseCodeGoogleDrive"></a>
# **restGetQueriesGenerationPromptUseCodeGoogleDrive**
> String restGetQueriesGenerationPromptUseCodeGoogleDrive()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
try {
    String result = apiInstance.restGetQueriesGenerationPromptUseCodeGoogleDrive();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restGetQueriesGenerationPromptUseCodeGoogleDrive");
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

<a name="restGetSearchableSystemsGoogleDrive"></a>
# **restGetSearchableSystemsGoogleDrive**
> List&lt;SearchableSystemMetaData&gt; restGetSearchableSystemsGoogleDrive()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
try {
    List<SearchableSystemMetaData> result = apiInstance.restGetSearchableSystemsGoogleDrive();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restGetSearchableSystemsGoogleDrive");
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

<a name="restIsEnabledGoogleDrive"></a>
# **restIsEnabledGoogleDrive**
> Boolean restIsEnabledGoogleDrive()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
try {
    Boolean result = apiInstance.restIsEnabledGoogleDrive();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restIsEnabledGoogleDrive");
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

<a name="restSearchGoogleDrive"></a>
# **restSearchGoogleDrive**
> List&lt;SearchResult&gt; restSearchGoogleDrive(body, systemId, nEntryLimit)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
SearchQuery body = new SearchQuery(); // SearchQuery | 
String systemId = "systemId_example"; // String | 
Integer nEntryLimit = 56; // Integer | 
try {
    List<SearchResult> result = apiInstance.restSearchGoogleDrive(body, systemId, nEntryLimit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restSearchGoogleDrive");
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

