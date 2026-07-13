# DocumentsChunkServiceControllerApi

All URIs are relative to *http://localhost:13004*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createChunkingSession**](DocumentsChunkServiceControllerApi.md#createChunkingSession) | **POST** /api/DocumentsChunkServiceController/createChunkingSession | 
[**disposeChunkingSession**](DocumentsChunkServiceControllerApi.md#disposeChunkingSession) | **POST** /api/DocumentsChunkServiceController/disposeChunkingSession | 
[**getCachedChunkSet**](DocumentsChunkServiceControllerApi.md#getCachedChunkSet) | **POST** /api/DocumentsChunkServiceController/getCachedChunkSet | 
[**getChunkSet**](DocumentsChunkServiceControllerApi.md#getChunkSet) | **POST** /api/DocumentsChunkServiceController/getChunkSet | 
[**getNextChunkSet**](DocumentsChunkServiceControllerApi.md#getNextChunkSet) | **POST** /api/DocumentsChunkServiceController/getNextChunkSet | 
[**prepareChunks**](DocumentsChunkServiceControllerApi.md#prepareChunks) | **POST** /api/DocumentsChunkServiceController/prepareChunks | 
[**retrieveChunkingSession**](DocumentsChunkServiceControllerApi.md#retrieveChunkingSession) | **GET** /api/DocumentsChunkServiceController/retrieveChunkingSession | 
[**streamChunks**](DocumentsChunkServiceControllerApi.md#streamChunks) | **POST** /api/DocumentsChunkServiceController/streamChunks | 
[**streamChunksBatch**](DocumentsChunkServiceControllerApi.md#streamChunksBatch) | **POST** /api/DocumentsChunkServiceController/streamChunksBatch | 
[**streamChunksReactive**](DocumentsChunkServiceControllerApi.md#streamChunksReactive) | **POST** /api/DocumentsChunkServiceController/streamChunksReactive | 

<a name="createChunkingSession"></a>
# **createChunkingSession**
> Object createChunkingSession(reference)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.chunker.invoker.ApiException;
//import gebo.microservices.api.client.chunker.api.DocumentsChunkServiceControllerApi;


DocumentsChunkServiceControllerApi apiInstance = new DocumentsChunkServiceControllerApi();
Object reference = null; // Object | 
try {
    Object result = apiInstance.createChunkingSession(reference);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DocumentsChunkServiceControllerApi#createChunkingSession");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **reference** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="disposeChunkingSession"></a>
# **disposeChunkingSession**
> disposeChunkingSession(chunkSessionId)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.chunker.invoker.ApiException;
//import gebo.microservices.api.client.chunker.api.DocumentsChunkServiceControllerApi;


DocumentsChunkServiceControllerApi apiInstance = new DocumentsChunkServiceControllerApi();
Object chunkSessionId = null; // Object | 
try {
    apiInstance.disposeChunkingSession(chunkSessionId);
} catch (ApiException e) {
    System.err.println("Exception when calling DocumentsChunkServiceControllerApi#disposeChunkingSession");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **chunkSessionId** | [**Object**](.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="getCachedChunkSet"></a>
# **getCachedChunkSet**
> DocumentChunkingResponse getCachedChunkSet(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.chunker.invoker.ApiException;
//import gebo.microservices.api.client.chunker.api.DocumentsChunkServiceControllerApi;


DocumentsChunkServiceControllerApi apiInstance = new DocumentsChunkServiceControllerApi();
GetCachedChunkSetRequest body = new GetCachedChunkSetRequest(); // GetCachedChunkSetRequest | 
try {
    DocumentChunkingResponse result = apiInstance.getCachedChunkSet(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DocumentsChunkServiceControllerApi#getCachedChunkSet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GetCachedChunkSetRequest**](GetCachedChunkSetRequest.md)|  |

### Return type

[**DocumentChunkingResponse**](DocumentChunkingResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getChunkSet"></a>
# **getChunkSet**
> DocumentChunkingResponse getChunkSet(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.chunker.invoker.ApiException;
//import gebo.microservices.api.client.chunker.api.DocumentsChunkServiceControllerApi;


DocumentsChunkServiceControllerApi apiInstance = new DocumentsChunkServiceControllerApi();
GetChunkSetRequest body = new GetChunkSetRequest(); // GetChunkSetRequest | 
try {
    DocumentChunkingResponse result = apiInstance.getChunkSet(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DocumentsChunkServiceControllerApi#getChunkSet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GetChunkSetRequest**](GetChunkSetRequest.md)|  |

### Return type

[**DocumentChunkingResponse**](DocumentChunkingResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getNextChunkSet"></a>
# **getNextChunkSet**
> DocumentChunkingResponse getNextChunkSet(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.chunker.invoker.ApiException;
//import gebo.microservices.api.client.chunker.api.DocumentsChunkServiceControllerApi;


DocumentsChunkServiceControllerApi apiInstance = new DocumentsChunkServiceControllerApi();
GetNextChunkSetRequest body = new GetNextChunkSetRequest(); // GetNextChunkSetRequest | 
try {
    DocumentChunkingResponse result = apiInstance.getNextChunkSet(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DocumentsChunkServiceControllerApi#getNextChunkSet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GetNextChunkSetRequest**](GetNextChunkSetRequest.md)|  |

### Return type

[**DocumentChunkingResponse**](DocumentChunkingResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="prepareChunks"></a>
# **prepareChunks**
> DocumentChunkingResponse prepareChunks(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.chunker.invoker.ApiException;
//import gebo.microservices.api.client.chunker.api.DocumentsChunkServiceControllerApi;


DocumentsChunkServiceControllerApi apiInstance = new DocumentsChunkServiceControllerApi();
PrepareChunksRequest body = new PrepareChunksRequest(); // PrepareChunksRequest | 
try {
    DocumentChunkingResponse result = apiInstance.prepareChunks(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DocumentsChunkServiceControllerApi#prepareChunks");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**PrepareChunksRequest**](PrepareChunksRequest.md)|  |

### Return type

[**DocumentChunkingResponse**](DocumentChunkingResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="retrieveChunkingSession"></a>
# **retrieveChunkingSession**
> Object retrieveChunkingSession(reference)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.chunker.invoker.ApiException;
//import gebo.microservices.api.client.chunker.api.DocumentsChunkServiceControllerApi;


DocumentsChunkServiceControllerApi apiInstance = new DocumentsChunkServiceControllerApi();
Object reference = null; // Object | 
try {
    Object result = apiInstance.retrieveChunkingSession(reference);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DocumentsChunkServiceControllerApi#retrieveChunkingSession");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **reference** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="streamChunks"></a>
# **streamChunks**
> Object streamChunks(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.chunker.invoker.ApiException;
//import gebo.microservices.api.client.chunker.api.DocumentsChunkServiceControllerApi;


DocumentsChunkServiceControllerApi apiInstance = new DocumentsChunkServiceControllerApi();
StreamChunksRequest body = new StreamChunksRequest(); // StreamChunksRequest | 
try {
    Object result = apiInstance.streamChunks(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DocumentsChunkServiceControllerApi#streamChunks");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**StreamChunksRequest**](StreamChunksRequest.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/x-ndjson

<a name="streamChunksBatch"></a>
# **streamChunksBatch**
> Object streamChunksBatch(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.chunker.invoker.ApiException;
//import gebo.microservices.api.client.chunker.api.DocumentsChunkServiceControllerApi;


DocumentsChunkServiceControllerApi apiInstance = new DocumentsChunkServiceControllerApi();
StreamChunksBatchRequest body = new StreamChunksBatchRequest(); // StreamChunksBatchRequest | 
try {
    Object result = apiInstance.streamChunksBatch(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DocumentsChunkServiceControllerApi#streamChunksBatch");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**StreamChunksBatchRequest**](StreamChunksBatchRequest.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/x-ndjson

<a name="streamChunksReactive"></a>
# **streamChunksReactive**
> Object streamChunksReactive(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.chunker.invoker.ApiException;
//import gebo.microservices.api.client.chunker.api.DocumentsChunkServiceControllerApi;


DocumentsChunkServiceControllerApi apiInstance = new DocumentsChunkServiceControllerApi();
StreamChunksReactiveRequest body = new StreamChunksReactiveRequest(); // StreamChunksReactiveRequest | 
try {
    Object result = apiInstance.streamChunksReactive(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DocumentsChunkServiceControllerApi#streamChunksReactive");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**StreamChunksReactiveRequest**](StreamChunksReactiveRequest.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/x-ndjson

