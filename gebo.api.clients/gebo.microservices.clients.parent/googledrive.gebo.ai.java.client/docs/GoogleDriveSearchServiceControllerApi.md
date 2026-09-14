# GoogleDriveSearchServiceControllerApi

All URIs are relative to *http://localhost:13013/googledrive*

Method | HTTP request | Description
------------- | ------------- | -------------
[**restAggregate**](GoogleDriveSearchServiceControllerApi.md#restAggregate) | **POST** /api/users/GoogleDriveSearchServiceController/aggregate | 
[**restExtractRelatedAnalisysReferences**](GoogleDriveSearchServiceControllerApi.md#restExtractRelatedAnalisysReferences) | **POST** /api/users/GoogleDriveSearchServiceController/extractRelatedAnalisysReferences | 
[**restFindSystemById**](GoogleDriveSearchServiceControllerApi.md#restFindSystemById) | **GET** /api/users/GoogleDriveSearchServiceController/findSystemById | 
[**restFindSystemBySearchResult**](GoogleDriveSearchServiceControllerApi.md#restFindSystemBySearchResult) | **POST** /api/users/GoogleDriveSearchServiceController/findSystemBySearchResult | 
[**restGetCachedCatalogues**](GoogleDriveSearchServiceControllerApi.md#restGetCachedCatalogues) | **GET** /api/users/GoogleDriveSearchServiceController/getCachedCatalogues | 
[**restGetCataloguesListSample**](GoogleDriveSearchServiceControllerApi.md#restGetCataloguesListSample) | **GET** /api/users/GoogleDriveSearchServiceController/getCataloguesListSample | 
[**restGetDescription**](GoogleDriveSearchServiceControllerApi.md#restGetDescription) | **GET** /api/users/GoogleDriveSearchServiceController/getDescription | 
[**restGetId**](GoogleDriveSearchServiceControllerApi.md#restGetId) | **GET** /api/users/GoogleDriveSearchServiceController/getId | 
[**restGetMessagingModuleId**](GoogleDriveSearchServiceControllerApi.md#restGetMessagingModuleId) | **GET** /api/users/GoogleDriveSearchServiceController/getMessagingModuleId | 
[**restGetProductId**](GoogleDriveSearchServiceControllerApi.md#restGetProductId) | **GET** /api/users/GoogleDriveSearchServiceController/getProductId | 
[**restGetQueriesGenerationPromptUseCode**](GoogleDriveSearchServiceControllerApi.md#restGetQueriesGenerationPromptUseCode) | **GET** /api/users/GoogleDriveSearchServiceController/getQueriesGenerationPromptUseCode | 
[**restGetSearchableSystems**](GoogleDriveSearchServiceControllerApi.md#restGetSearchableSystems) | **GET** /api/users/GoogleDriveSearchServiceController/getSearchableSystems | 
[**restIsEnabled**](GoogleDriveSearchServiceControllerApi.md#restIsEnabled) | **GET** /api/users/GoogleDriveSearchServiceController/isEnabled | 
[**restSearch**](GoogleDriveSearchServiceControllerApi.md#restSearch) | **POST** /api/users/GoogleDriveSearchServiceController/search | 

<a name="restAggregate"></a>
# **restAggregate**
> GoogleDriveResultsExtractionData restAggregate(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.googledrive.invoker.ApiException;
//import gebo.microservices.api.client.googledrive.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
AggregateRequestBodyGoogleDriveResultsExtractionData body = new AggregateRequestBodyGoogleDriveResultsExtractionData(); // AggregateRequestBodyGoogleDriveResultsExtractionData | 
try {
    GoogleDriveResultsExtractionData result = apiInstance.restAggregate(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restAggregate");
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

<a name="restExtractRelatedAnalisysReferences"></a>
# **restExtractRelatedAnalisysReferences**
> SearchResultAnalisysOutcome restExtractRelatedAnalisysReferences(body, systemId)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.googledrive.invoker.ApiException;
//import gebo.microservices.api.client.googledrive.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
GoogleDriveResultsExtractionData body = new GoogleDriveResultsExtractionData(); // GoogleDriveResultsExtractionData | 
String systemId = "systemId_example"; // String | 
try {
    SearchResultAnalisysOutcome result = apiInstance.restExtractRelatedAnalisysReferences(body, systemId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restExtractRelatedAnalisysReferences");
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

<a name="restFindSystemById"></a>
# **restFindSystemById**
> SearchableSystemMetaData restFindSystemById(systemId)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.googledrive.invoker.ApiException;
//import gebo.microservices.api.client.googledrive.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
String systemId = "systemId_example"; // String | 
try {
    SearchableSystemMetaData result = apiInstance.restFindSystemById(systemId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restFindSystemById");
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
//import gebo.microservices.api.client.googledrive.invoker.ApiException;
//import gebo.microservices.api.client.googledrive.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
SearchResult body = new SearchResult(); // SearchResult | 
try {
    SearchableSystemMetaData result = apiInstance.restFindSystemBySearchResult(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restFindSystemBySearchResult");
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
//import gebo.microservices.api.client.googledrive.invoker.ApiException;
//import gebo.microservices.api.client.googledrive.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
String systemConfigurationCode = "systemConfigurationCode_example"; // String | 
try {
    List<CatalogueSample> result = apiInstance.restGetCachedCatalogues(systemConfigurationCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restGetCachedCatalogues");
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
//import gebo.microservices.api.client.googledrive.invoker.ApiException;
//import gebo.microservices.api.client.googledrive.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
String configurationCode = "configurationCode_example"; // String | 
try {
    List<CatalogueSample> result = apiInstance.restGetCataloguesListSample(configurationCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restGetCataloguesListSample");
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
//import gebo.microservices.api.client.googledrive.invoker.ApiException;
//import gebo.microservices.api.client.googledrive.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
try {
    String result = apiInstance.restGetDescription();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restGetDescription");
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
//import gebo.microservices.api.client.googledrive.invoker.ApiException;
//import gebo.microservices.api.client.googledrive.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
try {
    String result = apiInstance.restGetId();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restGetId");
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
//import gebo.microservices.api.client.googledrive.invoker.ApiException;
//import gebo.microservices.api.client.googledrive.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
try {
    String result = apiInstance.restGetMessagingModuleId();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restGetMessagingModuleId");
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
//import gebo.microservices.api.client.googledrive.invoker.ApiException;
//import gebo.microservices.api.client.googledrive.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
try {
    String result = apiInstance.restGetProductId();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restGetProductId");
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
//import gebo.microservices.api.client.googledrive.invoker.ApiException;
//import gebo.microservices.api.client.googledrive.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
try {
    String result = apiInstance.restGetQueriesGenerationPromptUseCode();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restGetQueriesGenerationPromptUseCode");
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
//import gebo.microservices.api.client.googledrive.invoker.ApiException;
//import gebo.microservices.api.client.googledrive.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
try {
    List<SearchableSystemMetaData> result = apiInstance.restGetSearchableSystems();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restGetSearchableSystems");
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
//import gebo.microservices.api.client.googledrive.invoker.ApiException;
//import gebo.microservices.api.client.googledrive.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
try {
    Boolean result = apiInstance.restIsEnabled();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restIsEnabled");
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

<a name="restSearch"></a>
# **restSearch**
> List&lt;SearchResult&gt; restSearch(body, systemId, nEntryLimit)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.googledrive.invoker.ApiException;
//import gebo.microservices.api.client.googledrive.api.GoogleDriveSearchServiceControllerApi;


GoogleDriveSearchServiceControllerApi apiInstance = new GoogleDriveSearchServiceControllerApi();
SearchQuery body = new SearchQuery(); // SearchQuery | 
String systemId = "systemId_example"; // String | 
Integer nEntryLimit = 56; // Integer | 
try {
    List<SearchResult> result = apiInstance.restSearch(body, systemId, nEntryLimit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSearchServiceControllerApi#restSearch");
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

