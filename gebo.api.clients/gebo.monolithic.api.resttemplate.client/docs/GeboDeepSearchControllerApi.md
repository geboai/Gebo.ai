# GeboDeepSearchControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**dataSourceDeepSearch**](GeboDeepSearchControllerApi.md#dataSourceDeepSearch) | **POST** /api/users/GeboDeepSearchController/dataSourceDeepSearch/{dataSourceCode} | 
[**deleteDeepSearch**](GeboDeepSearchControllerApi.md#deleteDeepSearch) | **DELETE** /api/users/GeboDeepSearchController/deleteDeepSearch | 
[**doDeepSearch**](GeboDeepSearchControllerApi.md#doDeepSearch) | **POST** /api/users/GeboDeepSearchController/doDeepSearch | 
[**getDeepSearchDataSources**](GeboDeepSearchControllerApi.md#getDeepSearchDataSources) | **GET** /api/users/GeboDeepSearchController/getDeepSearchDataSources | 
[**getDeepSearchDocumentsCount**](GeboDeepSearchControllerApi.md#getDeepSearchDocumentsCount) | **GET** /api/users/GeboDeepSearchController/getDeepSearchDocumentsCount | 
[**getDeepSearchUISettings**](GeboDeepSearchControllerApi.md#getDeepSearchUISettings) | **GET** /api/users/GeboDeepSearchController/getDeepSearchUISettings | 
[**getMyDeepSearchById**](GeboDeepSearchControllerApi.md#getMyDeepSearchById) | **GET** /api/users/GeboDeepSearchController/getMyDeepSearchById | 
[**getMyDeepSearchDataSourceDocumentResultsByRequestCode**](GeboDeepSearchControllerApi.md#getMyDeepSearchDataSourceDocumentResultsByRequestCode) | **GET** /api/users/GeboDeepSearchController/getMyDeepSearchDataSourceDocumentResultsByRequestCode | 
[**getMyDeepSearchDeepSearchDataSourceResponsesByRequestCode**](GeboDeepSearchControllerApi.md#getMyDeepSearchDeepSearchDataSourceResponsesByRequestCode) | **GET** /api/users/GeboDeepSearchController/getMyDeepSearchDeepSearchDataSourceResponsesByRequestCode | 
[**getMyDeepSearchResponseByRequestCode**](GeboDeepSearchControllerApi.md#getMyDeepSearchResponseByRequestCode) | **GET** /api/users/GeboDeepSearchController/getMyDeepSearchResponseByRequestCode | 
[**getMyDeepSearches**](GeboDeepSearchControllerApi.md#getMyDeepSearches) | **GET** /api/users/GeboDeepSearchController/getMyDeepSearches | 
[**getMyDeepSearchesPaged**](GeboDeepSearchControllerApi.md#getMyDeepSearchesPaged) | **GET** /api/users/GeboDeepSearchController/getMyDeepSearchesPaged | 
[**getMyDeepSearchesSteps**](GeboDeepSearchControllerApi.md#getMyDeepSearchesSteps) | **GET** /api/users/GeboDeepSearchController/getMyDeepSearchesSteps | 
[**getMyDeepSearchesStepsPaged**](GeboDeepSearchControllerApi.md#getMyDeepSearchesStepsPaged) | **GET** /api/users/GeboDeepSearchController/getMyDeepSearchesStepsPaged | 
[**internalKnowledgeBaseDeepSearch**](GeboDeepSearchControllerApi.md#internalKnowledgeBaseDeepSearch) | **POST** /api/users/GeboDeepSearchController/internalKnowledgeBaseDeepSearch | 
[**stopDeepSearch**](GeboDeepSearchControllerApi.md#stopDeepSearch) | **POST** /api/users/GeboDeepSearchController/stopDeepSearch | 
[**streamDeepSearch**](GeboDeepSearchControllerApi.md#streamDeepSearch) | **POST** /api/users/GeboDeepSearchController/streamDeepSearch | 
[**streamDeepSearchWithChatContext**](GeboDeepSearchControllerApi.md#streamDeepSearchWithChatContext) | **POST** /api/users/GeboDeepSearchController/streamDeepSearchWithChatContext | 

<a name="dataSourceDeepSearch"></a>
# **dataSourceDeepSearch**
> List&lt;ServerSentEventString&gt; dataSourceDeepSearch(body, dataSourceCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboDeepSearchControllerApi;


GeboDeepSearchControllerApi apiInstance = new GeboDeepSearchControllerApi();
GeboChatRequest body = new GeboChatRequest(); // GeboChatRequest | 
String dataSourceCode = "dataSourceCode_example"; // String | 
try {
    List<ServerSentEventString> result = apiInstance.dataSourceDeepSearch(body, dataSourceCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboDeepSearchControllerApi#dataSourceDeepSearch");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GeboChatRequest**](GeboChatRequest.md)|  |
 **dataSourceCode** | **String**|  |

### Return type

[**List&lt;ServerSentEventString&gt;**](ServerSentEventString.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: text/event-stream

<a name="deleteDeepSearch"></a>
# **deleteDeepSearch**
> deleteDeepSearch(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboDeepSearchControllerApi;


GeboDeepSearchControllerApi apiInstance = new GeboDeepSearchControllerApi();
DeepSearchRequest body = new DeepSearchRequest(); // DeepSearchRequest | 
try {
    apiInstance.deleteDeepSearch(body);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboDeepSearchControllerApi#deleteDeepSearch");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**DeepSearchRequest**](DeepSearchRequest.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="doDeepSearch"></a>
# **doDeepSearch**
> DeepSearchResponse doDeepSearch(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboDeepSearchControllerApi;


GeboDeepSearchControllerApi apiInstance = new GeboDeepSearchControllerApi();
DeepSearchRequest body = new DeepSearchRequest(); // DeepSearchRequest | 
try {
    DeepSearchResponse result = apiInstance.doDeepSearch(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboDeepSearchControllerApi#doDeepSearch");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**DeepSearchRequest**](DeepSearchRequest.md)|  |

### Return type

[**DeepSearchResponse**](DeepSearchResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getDeepSearchDataSources"></a>
# **getDeepSearchDataSources**
> List&lt;GBaseObject&gt; getDeepSearchDataSources()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboDeepSearchControllerApi;


GeboDeepSearchControllerApi apiInstance = new GeboDeepSearchControllerApi();
try {
    List<GBaseObject> result = apiInstance.getDeepSearchDataSources();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboDeepSearchControllerApi#getDeepSearchDataSources");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GBaseObject&gt;**](GBaseObject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getDeepSearchDocumentsCount"></a>
# **getDeepSearchDocumentsCount**
> Long getDeepSearchDocumentsCount(deepSearchCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboDeepSearchControllerApi;


GeboDeepSearchControllerApi apiInstance = new GeboDeepSearchControllerApi();
String deepSearchCode = "deepSearchCode_example"; // String | 
try {
    Long result = apiInstance.getDeepSearchDocumentsCount(deepSearchCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboDeepSearchControllerApi#getDeepSearchDocumentsCount");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **deepSearchCode** | **String**|  |

### Return type

**Long**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getDeepSearchUISettings"></a>
# **getDeepSearchUISettings**
> DeepSearchUISettings getDeepSearchUISettings()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboDeepSearchControllerApi;


GeboDeepSearchControllerApi apiInstance = new GeboDeepSearchControllerApi();
try {
    DeepSearchUISettings result = apiInstance.getDeepSearchUISettings();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboDeepSearchControllerApi#getDeepSearchUISettings");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**DeepSearchUISettings**](DeepSearchUISettings.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getMyDeepSearchById"></a>
# **getMyDeepSearchById**
> DeepSearchRequest getMyDeepSearchById(deepSearchCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboDeepSearchControllerApi;


GeboDeepSearchControllerApi apiInstance = new GeboDeepSearchControllerApi();
String deepSearchCode = "deepSearchCode_example"; // String | 
try {
    DeepSearchRequest result = apiInstance.getMyDeepSearchById(deepSearchCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboDeepSearchControllerApi#getMyDeepSearchById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **deepSearchCode** | **String**|  |

### Return type

[**DeepSearchRequest**](DeepSearchRequest.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getMyDeepSearchDataSourceDocumentResultsByRequestCode"></a>
# **getMyDeepSearchDataSourceDocumentResultsByRequestCode**
> List&lt;DeepSearchDataSourceDocumentResult&gt; getMyDeepSearchDataSourceDocumentResultsByRequestCode(deepSearchCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboDeepSearchControllerApi;


GeboDeepSearchControllerApi apiInstance = new GeboDeepSearchControllerApi();
String deepSearchCode = "deepSearchCode_example"; // String | 
try {
    List<DeepSearchDataSourceDocumentResult> result = apiInstance.getMyDeepSearchDataSourceDocumentResultsByRequestCode(deepSearchCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboDeepSearchControllerApi#getMyDeepSearchDataSourceDocumentResultsByRequestCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **deepSearchCode** | **String**|  |

### Return type

[**List&lt;DeepSearchDataSourceDocumentResult&gt;**](DeepSearchDataSourceDocumentResult.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getMyDeepSearchDeepSearchDataSourceResponsesByRequestCode"></a>
# **getMyDeepSearchDeepSearchDataSourceResponsesByRequestCode**
> List&lt;DeepSearchDataSourceResponse&gt; getMyDeepSearchDeepSearchDataSourceResponsesByRequestCode(deepSearchCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboDeepSearchControllerApi;


GeboDeepSearchControllerApi apiInstance = new GeboDeepSearchControllerApi();
String deepSearchCode = "deepSearchCode_example"; // String | 
try {
    List<DeepSearchDataSourceResponse> result = apiInstance.getMyDeepSearchDeepSearchDataSourceResponsesByRequestCode(deepSearchCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboDeepSearchControllerApi#getMyDeepSearchDeepSearchDataSourceResponsesByRequestCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **deepSearchCode** | **String**|  |

### Return type

[**List&lt;DeepSearchDataSourceResponse&gt;**](DeepSearchDataSourceResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getMyDeepSearchResponseByRequestCode"></a>
# **getMyDeepSearchResponseByRequestCode**
> DeepSearchResponse getMyDeepSearchResponseByRequestCode(deepSearchCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboDeepSearchControllerApi;


GeboDeepSearchControllerApi apiInstance = new GeboDeepSearchControllerApi();
String deepSearchCode = "deepSearchCode_example"; // String | 
try {
    DeepSearchResponse result = apiInstance.getMyDeepSearchResponseByRequestCode(deepSearchCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboDeepSearchControllerApi#getMyDeepSearchResponseByRequestCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **deepSearchCode** | **String**|  |

### Return type

[**DeepSearchResponse**](DeepSearchResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getMyDeepSearches"></a>
# **getMyDeepSearches**
> List&lt;DeepSearchRequest&gt; getMyDeepSearches()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboDeepSearchControllerApi;


GeboDeepSearchControllerApi apiInstance = new GeboDeepSearchControllerApi();
try {
    List<DeepSearchRequest> result = apiInstance.getMyDeepSearches();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboDeepSearchControllerApi#getMyDeepSearches");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;DeepSearchRequest&gt;**](DeepSearchRequest.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getMyDeepSearchesPaged"></a>
# **getMyDeepSearchesPaged**
> PageDeepSearchRequest getMyDeepSearchesPaged(page, pageSize)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboDeepSearchControllerApi;


GeboDeepSearchControllerApi apiInstance = new GeboDeepSearchControllerApi();
Integer page = 56; // Integer | 
Integer pageSize = 56; // Integer | 
try {
    PageDeepSearchRequest result = apiInstance.getMyDeepSearchesPaged(page, pageSize);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboDeepSearchControllerApi#getMyDeepSearchesPaged");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **page** | **Integer**|  |
 **pageSize** | **Integer**|  |

### Return type

[**PageDeepSearchRequest**](PageDeepSearchRequest.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getMyDeepSearchesSteps"></a>
# **getMyDeepSearchesSteps**
> List&lt;DeepSearchDocumentAnalisysResultStep&gt; getMyDeepSearchesSteps(deepSearchCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboDeepSearchControllerApi;


GeboDeepSearchControllerApi apiInstance = new GeboDeepSearchControllerApi();
String deepSearchCode = "deepSearchCode_example"; // String | 
try {
    List<DeepSearchDocumentAnalisysResultStep> result = apiInstance.getMyDeepSearchesSteps(deepSearchCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboDeepSearchControllerApi#getMyDeepSearchesSteps");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **deepSearchCode** | **String**|  |

### Return type

[**List&lt;DeepSearchDocumentAnalisysResultStep&gt;**](DeepSearchDocumentAnalisysResultStep.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getMyDeepSearchesStepsPaged"></a>
# **getMyDeepSearchesStepsPaged**
> PageDeepSearchDocumentAnalisysResultStep getMyDeepSearchesStepsPaged(deepSearchCode, page, pageSize)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboDeepSearchControllerApi;


GeboDeepSearchControllerApi apiInstance = new GeboDeepSearchControllerApi();
String deepSearchCode = "deepSearchCode_example"; // String | 
Integer page = 56; // Integer | 
Integer pageSize = 56; // Integer | 
try {
    PageDeepSearchDocumentAnalisysResultStep result = apiInstance.getMyDeepSearchesStepsPaged(deepSearchCode, page, pageSize);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboDeepSearchControllerApi#getMyDeepSearchesStepsPaged");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **deepSearchCode** | **String**|  |
 **page** | **Integer**|  |
 **pageSize** | **Integer**|  |

### Return type

[**PageDeepSearchDocumentAnalisysResultStep**](PageDeepSearchDocumentAnalisysResultStep.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="internalKnowledgeBaseDeepSearch"></a>
# **internalKnowledgeBaseDeepSearch**
> List&lt;ServerSentEventString&gt; internalKnowledgeBaseDeepSearch(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboDeepSearchControllerApi;


GeboDeepSearchControllerApi apiInstance = new GeboDeepSearchControllerApi();
GeboChatRequest body = new GeboChatRequest(); // GeboChatRequest | 
try {
    List<ServerSentEventString> result = apiInstance.internalKnowledgeBaseDeepSearch(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboDeepSearchControllerApi#internalKnowledgeBaseDeepSearch");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GeboChatRequest**](GeboChatRequest.md)|  |

### Return type

[**List&lt;ServerSentEventString&gt;**](ServerSentEventString.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: text/event-stream

<a name="stopDeepSearch"></a>
# **stopDeepSearch**
> stopDeepSearch(deepSearchCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboDeepSearchControllerApi;


GeboDeepSearchControllerApi apiInstance = new GeboDeepSearchControllerApi();
String deepSearchCode = "deepSearchCode_example"; // String | 
try {
    apiInstance.stopDeepSearch(deepSearchCode);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboDeepSearchControllerApi#stopDeepSearch");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **deepSearchCode** | **String**|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="streamDeepSearch"></a>
# **streamDeepSearch**
> List&lt;ServerSentEventString&gt; streamDeepSearch(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboDeepSearchControllerApi;


GeboDeepSearchControllerApi apiInstance = new GeboDeepSearchControllerApi();
DeepSearchRequest body = new DeepSearchRequest(); // DeepSearchRequest | 
try {
    List<ServerSentEventString> result = apiInstance.streamDeepSearch(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboDeepSearchControllerApi#streamDeepSearch");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**DeepSearchRequest**](DeepSearchRequest.md)|  |

### Return type

[**List&lt;ServerSentEventString&gt;**](ServerSentEventString.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: text/event-stream

<a name="streamDeepSearchWithChatContext"></a>
# **streamDeepSearchWithChatContext**
> List&lt;ServerSentEventString&gt; streamDeepSearchWithChatContext(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboDeepSearchControllerApi;


GeboDeepSearchControllerApi apiInstance = new GeboDeepSearchControllerApi();
GeboChatRequest body = new GeboChatRequest(); // GeboChatRequest | 
try {
    List<ServerSentEventString> result = apiInstance.streamDeepSearchWithChatContext(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboDeepSearchControllerApi#streamDeepSearchWithChatContext");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GeboChatRequest**](GeboChatRequest.md)|  |

### Return type

[**List&lt;ServerSentEventString&gt;**](ServerSentEventString.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: text/event-stream

