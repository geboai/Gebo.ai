# GoogleDriveSearchServiceControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**restAggregate2**](GoogleDriveSearchServiceControllerApi.md#restAggregate2) | **POST** /api/users/GoogleDriveSearchServiceController/aggregate | 
[**restExtractRelatedAnalisysReferences2**](GoogleDriveSearchServiceControllerApi.md#restExtractRelatedAnalisysReferences2) | **POST** /api/users/GoogleDriveSearchServiceController/extractRelatedAnalisysReferences | 
[**restFindSystemById2**](GoogleDriveSearchServiceControllerApi.md#restFindSystemById2) | **GET** /api/users/GoogleDriveSearchServiceController/findSystemById | 
[**restFindSystemBySearchResult2**](GoogleDriveSearchServiceControllerApi.md#restFindSystemBySearchResult2) | **POST** /api/users/GoogleDriveSearchServiceController/findSystemBySearchResult | 
[**restGetCachedCatalogues2**](GoogleDriveSearchServiceControllerApi.md#restGetCachedCatalogues2) | **GET** /api/users/GoogleDriveSearchServiceController/getCachedCatalogues | 
[**restGetCataloguesListSample2**](GoogleDriveSearchServiceControllerApi.md#restGetCataloguesListSample2) | **GET** /api/users/GoogleDriveSearchServiceController/getCataloguesListSample | 
[**restGetDescription2**](GoogleDriveSearchServiceControllerApi.md#restGetDescription2) | **GET** /api/users/GoogleDriveSearchServiceController/getDescription | 
[**restGetId2**](GoogleDriveSearchServiceControllerApi.md#restGetId2) | **GET** /api/users/GoogleDriveSearchServiceController/getId | 
[**restGetMessagingModuleId2**](GoogleDriveSearchServiceControllerApi.md#restGetMessagingModuleId2) | **GET** /api/users/GoogleDriveSearchServiceController/getMessagingModuleId | 
[**restGetProductId2**](GoogleDriveSearchServiceControllerApi.md#restGetProductId2) | **GET** /api/users/GoogleDriveSearchServiceController/getProductId | 
[**restGetQueriesGenerationPromptUseCode2**](GoogleDriveSearchServiceControllerApi.md#restGetQueriesGenerationPromptUseCode2) | **GET** /api/users/GoogleDriveSearchServiceController/getQueriesGenerationPromptUseCode | 
[**restGetSearchableSystems2**](GoogleDriveSearchServiceControllerApi.md#restGetSearchableSystems2) | **GET** /api/users/GoogleDriveSearchServiceController/getSearchableSystems | 
[**restIsEnabled2**](GoogleDriveSearchServiceControllerApi.md#restIsEnabled2) | **GET** /api/users/GoogleDriveSearchServiceController/isEnabled | 
[**restSearch2**](GoogleDriveSearchServiceControllerApi.md#restSearch2) | **POST** /api/users/GoogleDriveSearchServiceController/search | 

<a name="restAggregate2"></a>
# **restAggregate2**
> GoogleDriveResultsExtractionData restAggregate2(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
AggregateRequestBodyGoogleDriveResultsExtractionData body = new AggregateRequestBodyGoogleDriveResultsExtractionData(); // AggregateRequestBodyGoogleDriveResultsExtractionData | 
try {
    GoogleDriveResultsExtractionData result = apiInstance.restAggregate2(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restAggregate2");
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

<a name="restExtractRelatedAnalisysReferences2"></a>
# **restExtractRelatedAnalisysReferences2**
> SearchResultAnalisysOutcome restExtractRelatedAnalisysReferences2(body, systemId)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
GoogleDriveResultsExtractionData body = new GoogleDriveResultsExtractionData(); // GoogleDriveResultsExtractionData | 
String systemId = "systemId_example"; // String | 
try {
    SearchResultAnalisysOutcome result = apiInstance.restExtractRelatedAnalisysReferences2(body, systemId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restExtractRelatedAnalisysReferences2");
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

<a name="restFindSystemById2"></a>
# **restFindSystemById2**
> SearchableSystemMetaData restFindSystemById2(systemId)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
String systemId = "systemId_example"; // String | 
try {
    SearchableSystemMetaData result = apiInstance.restFindSystemById2(systemId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restFindSystemById2");
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

<a name="restFindSystemBySearchResult2"></a>
# **restFindSystemBySearchResult2**
> SearchableSystemMetaData restFindSystemBySearchResult2(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
SearchResult body = new SearchResult(); // SearchResult | 
try {
    SearchableSystemMetaData result = apiInstance.restFindSystemBySearchResult2(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restFindSystemBySearchResult2");
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

<a name="restGetCachedCatalogues2"></a>
# **restGetCachedCatalogues2**
> List&lt;CatalogueSample&gt; restGetCachedCatalogues2(systemConfigurationCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
String systemConfigurationCode = "systemConfigurationCode_example"; // String | 
try {
    List<CatalogueSample> result = apiInstance.restGetCachedCatalogues2(systemConfigurationCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restGetCachedCatalogues2");
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

<a name="restGetCataloguesListSample2"></a>
# **restGetCataloguesListSample2**
> List&lt;CatalogueSample&gt; restGetCataloguesListSample2(configurationCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
String configurationCode = "configurationCode_example"; // String | 
try {
    List<CatalogueSample> result = apiInstance.restGetCataloguesListSample2(configurationCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restGetCataloguesListSample2");
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

<a name="restGetDescription2"></a>
# **restGetDescription2**
> String restGetDescription2()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
try {
    String result = apiInstance.restGetDescription2();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restGetDescription2");
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

<a name="restGetId2"></a>
# **restGetId2**
> String restGetId2()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
try {
    String result = apiInstance.restGetId2();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restGetId2");
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

<a name="restGetMessagingModuleId2"></a>
# **restGetMessagingModuleId2**
> String restGetMessagingModuleId2()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
try {
    String result = apiInstance.restGetMessagingModuleId2();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restGetMessagingModuleId2");
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

<a name="restGetProductId2"></a>
# **restGetProductId2**
> String restGetProductId2()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
try {
    String result = apiInstance.restGetProductId2();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restGetProductId2");
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

<a name="restGetQueriesGenerationPromptUseCode2"></a>
# **restGetQueriesGenerationPromptUseCode2**
> String restGetQueriesGenerationPromptUseCode2()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
try {
    String result = apiInstance.restGetQueriesGenerationPromptUseCode2();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restGetQueriesGenerationPromptUseCode2");
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

<a name="restGetSearchableSystems2"></a>
# **restGetSearchableSystems2**
> List&lt;SearchableSystemMetaData&gt; restGetSearchableSystems2()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
try {
    List<SearchableSystemMetaData> result = apiInstance.restGetSearchableSystems2();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restGetSearchableSystems2");
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

<a name="restIsEnabled2"></a>
# **restIsEnabled2**
> Boolean restIsEnabled2()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
try {
    Boolean result = apiInstance.restIsEnabled2();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restIsEnabled2");
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

<a name="restSearch2"></a>
# **restSearch2**
> List&lt;SearchResult&gt; restSearch2(body, systemId, nEntryLimit)



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
    List<SearchResult> result = apiInstance.restSearch2(body, systemId, nEntryLimit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restSearch2");
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

