# AssistantsApi

All URIs are relative to *https://api.regolo.ai*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addMessagesThreadsThreadIdMessagesPost**](AssistantsApi.md#addMessagesThreadsThreadIdMessagesPost) | **POST** /threads/{thread_id}/messages | Add Messages
[**addMessagesV1ThreadsThreadIdMessagesPost**](AssistantsApi.md#addMessagesV1ThreadsThreadIdMessagesPost) | **POST** /v1/threads/{thread_id}/messages | Add Messages
[**createAssistantAssistantsPost**](AssistantsApi.md#createAssistantAssistantsPost) | **POST** /assistants | Create Assistant
[**createAssistantV1AssistantsPost**](AssistantsApi.md#createAssistantV1AssistantsPost) | **POST** /v1/assistants | Create Assistant
[**createThreadsThreadsPost**](AssistantsApi.md#createThreadsThreadsPost) | **POST** /threads | Create Threads
[**createThreadsV1ThreadsPost**](AssistantsApi.md#createThreadsV1ThreadsPost) | **POST** /v1/threads | Create Threads
[**deleteAssistantAssistantsAssistantIdDelete**](AssistantsApi.md#deleteAssistantAssistantsAssistantIdDelete) | **DELETE** /assistants/{assistant_id} | Delete Assistant
[**deleteAssistantV1AssistantsAssistantIdDelete**](AssistantsApi.md#deleteAssistantV1AssistantsAssistantIdDelete) | **DELETE** /v1/assistants/{assistant_id} | Delete Assistant
[**getAssistantsAssistantsGet**](AssistantsApi.md#getAssistantsAssistantsGet) | **GET** /assistants | Get Assistants
[**getAssistantsV1AssistantsGet**](AssistantsApi.md#getAssistantsV1AssistantsGet) | **GET** /v1/assistants | Get Assistants
[**getMessagesThreadsThreadIdMessagesGet**](AssistantsApi.md#getMessagesThreadsThreadIdMessagesGet) | **GET** /threads/{thread_id}/messages | Get Messages
[**getMessagesV1ThreadsThreadIdMessagesGet**](AssistantsApi.md#getMessagesV1ThreadsThreadIdMessagesGet) | **GET** /v1/threads/{thread_id}/messages | Get Messages
[**getThreadThreadsThreadIdGet**](AssistantsApi.md#getThreadThreadsThreadIdGet) | **GET** /threads/{thread_id} | Get Thread
[**getThreadV1ThreadsThreadIdGet**](AssistantsApi.md#getThreadV1ThreadsThreadIdGet) | **GET** /v1/threads/{thread_id} | Get Thread
[**runThreadThreadsThreadIdRunsPost**](AssistantsApi.md#runThreadThreadsThreadIdRunsPost) | **POST** /threads/{thread_id}/runs | Run Thread
[**runThreadV1ThreadsThreadIdRunsPost**](AssistantsApi.md#runThreadV1ThreadsThreadIdRunsPost) | **POST** /v1/threads/{thread_id}/runs | Run Thread

<a name="addMessagesThreadsThreadIdMessagesPost"></a>
# **addMessagesThreadsThreadIdMessagesPost**
> Object addMessagesThreadsThreadIdMessagesPost(threadId)

Add Messages

Create a message.  API Reference - https://platform.openai.com/docs/api-reference/messages/createMessage

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.AssistantsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

AssistantsApi apiInstance = new AssistantsApi();
Object threadId = null; // Object | 
try {
    Object result = apiInstance.addMessagesThreadsThreadIdMessagesPost(threadId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AssistantsApi#addMessagesThreadsThreadIdMessagesPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **threadId** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

[APIKeyHeader](../README.md#APIKeyHeader)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="addMessagesV1ThreadsThreadIdMessagesPost"></a>
# **addMessagesV1ThreadsThreadIdMessagesPost**
> Object addMessagesV1ThreadsThreadIdMessagesPost(threadId)

Add Messages

Create a message.  API Reference - https://platform.openai.com/docs/api-reference/messages/createMessage

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.AssistantsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

AssistantsApi apiInstance = new AssistantsApi();
Object threadId = null; // Object | 
try {
    Object result = apiInstance.addMessagesV1ThreadsThreadIdMessagesPost(threadId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AssistantsApi#addMessagesV1ThreadsThreadIdMessagesPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **threadId** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

[APIKeyHeader](../README.md#APIKeyHeader)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="createAssistantAssistantsPost"></a>
# **createAssistantAssistantsPost**
> Object createAssistantAssistantsPost()

Create Assistant

Create assistant  API Reference docs - https://platform.openai.com/docs/api-reference/assistants/createAssistant

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.AssistantsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

AssistantsApi apiInstance = new AssistantsApi();
try {
    Object result = apiInstance.createAssistantAssistantsPost();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AssistantsApi#createAssistantAssistantsPost");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

[APIKeyHeader](../README.md#APIKeyHeader)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="createAssistantV1AssistantsPost"></a>
# **createAssistantV1AssistantsPost**
> Object createAssistantV1AssistantsPost()

Create Assistant

Create assistant  API Reference docs - https://platform.openai.com/docs/api-reference/assistants/createAssistant

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.AssistantsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

AssistantsApi apiInstance = new AssistantsApi();
try {
    Object result = apiInstance.createAssistantV1AssistantsPost();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AssistantsApi#createAssistantV1AssistantsPost");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

[APIKeyHeader](../README.md#APIKeyHeader)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="createThreadsThreadsPost"></a>
# **createThreadsThreadsPost**
> Object createThreadsThreadsPost()

Create Threads

Create a thread.  API Reference - https://platform.openai.com/docs/api-reference/threads/createThread

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.AssistantsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

AssistantsApi apiInstance = new AssistantsApi();
try {
    Object result = apiInstance.createThreadsThreadsPost();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AssistantsApi#createThreadsThreadsPost");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

[APIKeyHeader](../README.md#APIKeyHeader)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="createThreadsV1ThreadsPost"></a>
# **createThreadsV1ThreadsPost**
> Object createThreadsV1ThreadsPost()

Create Threads

Create a thread.  API Reference - https://platform.openai.com/docs/api-reference/threads/createThread

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.AssistantsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

AssistantsApi apiInstance = new AssistantsApi();
try {
    Object result = apiInstance.createThreadsV1ThreadsPost();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AssistantsApi#createThreadsV1ThreadsPost");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

[APIKeyHeader](../README.md#APIKeyHeader)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="deleteAssistantAssistantsAssistantIdDelete"></a>
# **deleteAssistantAssistantsAssistantIdDelete**
> Object deleteAssistantAssistantsAssistantIdDelete(assistantId)

Delete Assistant

Delete assistant  API Reference docs - https://platform.openai.com/docs/api-reference/assistants/createAssistant

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.AssistantsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

AssistantsApi apiInstance = new AssistantsApi();
Object assistantId = null; // Object | 
try {
    Object result = apiInstance.deleteAssistantAssistantsAssistantIdDelete(assistantId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AssistantsApi#deleteAssistantAssistantsAssistantIdDelete");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **assistantId** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

[APIKeyHeader](../README.md#APIKeyHeader)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="deleteAssistantV1AssistantsAssistantIdDelete"></a>
# **deleteAssistantV1AssistantsAssistantIdDelete**
> Object deleteAssistantV1AssistantsAssistantIdDelete(assistantId)

Delete Assistant

Delete assistant  API Reference docs - https://platform.openai.com/docs/api-reference/assistants/createAssistant

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.AssistantsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

AssistantsApi apiInstance = new AssistantsApi();
Object assistantId = null; // Object | 
try {
    Object result = apiInstance.deleteAssistantV1AssistantsAssistantIdDelete(assistantId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AssistantsApi#deleteAssistantV1AssistantsAssistantIdDelete");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **assistantId** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

[APIKeyHeader](../README.md#APIKeyHeader)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAssistantsAssistantsGet"></a>
# **getAssistantsAssistantsGet**
> Object getAssistantsAssistantsGet()

Get Assistants

Returns a list of assistants.  API Reference docs - https://platform.openai.com/docs/api-reference/assistants/listAssistants

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.AssistantsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

AssistantsApi apiInstance = new AssistantsApi();
try {
    Object result = apiInstance.getAssistantsAssistantsGet();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AssistantsApi#getAssistantsAssistantsGet");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

[APIKeyHeader](../README.md#APIKeyHeader)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAssistantsV1AssistantsGet"></a>
# **getAssistantsV1AssistantsGet**
> Object getAssistantsV1AssistantsGet()

Get Assistants

Returns a list of assistants.  API Reference docs - https://platform.openai.com/docs/api-reference/assistants/listAssistants

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.AssistantsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

AssistantsApi apiInstance = new AssistantsApi();
try {
    Object result = apiInstance.getAssistantsV1AssistantsGet();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AssistantsApi#getAssistantsV1AssistantsGet");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

[APIKeyHeader](../README.md#APIKeyHeader)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getMessagesThreadsThreadIdMessagesGet"></a>
# **getMessagesThreadsThreadIdMessagesGet**
> Object getMessagesThreadsThreadIdMessagesGet(threadId)

Get Messages

Returns a list of messages for a given thread.  API Reference - https://platform.openai.com/docs/api-reference/messages/listMessages

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.AssistantsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

AssistantsApi apiInstance = new AssistantsApi();
Object threadId = null; // Object | 
try {
    Object result = apiInstance.getMessagesThreadsThreadIdMessagesGet(threadId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AssistantsApi#getMessagesThreadsThreadIdMessagesGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **threadId** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

[APIKeyHeader](../README.md#APIKeyHeader)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getMessagesV1ThreadsThreadIdMessagesGet"></a>
# **getMessagesV1ThreadsThreadIdMessagesGet**
> Object getMessagesV1ThreadsThreadIdMessagesGet(threadId)

Get Messages

Returns a list of messages for a given thread.  API Reference - https://platform.openai.com/docs/api-reference/messages/listMessages

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.AssistantsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

AssistantsApi apiInstance = new AssistantsApi();
Object threadId = null; // Object | 
try {
    Object result = apiInstance.getMessagesV1ThreadsThreadIdMessagesGet(threadId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AssistantsApi#getMessagesV1ThreadsThreadIdMessagesGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **threadId** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

[APIKeyHeader](../README.md#APIKeyHeader)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getThreadThreadsThreadIdGet"></a>
# **getThreadThreadsThreadIdGet**
> Object getThreadThreadsThreadIdGet(threadId)

Get Thread

Retrieves a thread.  API Reference - https://platform.openai.com/docs/api-reference/threads/getThread

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.AssistantsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

AssistantsApi apiInstance = new AssistantsApi();
Object threadId = null; // Object | 
try {
    Object result = apiInstance.getThreadThreadsThreadIdGet(threadId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AssistantsApi#getThreadThreadsThreadIdGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **threadId** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

[APIKeyHeader](../README.md#APIKeyHeader)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getThreadV1ThreadsThreadIdGet"></a>
# **getThreadV1ThreadsThreadIdGet**
> Object getThreadV1ThreadsThreadIdGet(threadId)

Get Thread

Retrieves a thread.  API Reference - https://platform.openai.com/docs/api-reference/threads/getThread

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.AssistantsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

AssistantsApi apiInstance = new AssistantsApi();
Object threadId = null; // Object | 
try {
    Object result = apiInstance.getThreadV1ThreadsThreadIdGet(threadId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AssistantsApi#getThreadV1ThreadsThreadIdGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **threadId** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

[APIKeyHeader](../README.md#APIKeyHeader)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="runThreadThreadsThreadIdRunsPost"></a>
# **runThreadThreadsThreadIdRunsPost**
> Object runThreadThreadsThreadIdRunsPost(threadId)

Run Thread

Create a run.  API Reference: https://platform.openai.com/docs/api-reference/runs/createRun

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.AssistantsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

AssistantsApi apiInstance = new AssistantsApi();
Object threadId = null; // Object | 
try {
    Object result = apiInstance.runThreadThreadsThreadIdRunsPost(threadId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AssistantsApi#runThreadThreadsThreadIdRunsPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **threadId** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

[APIKeyHeader](../README.md#APIKeyHeader)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="runThreadV1ThreadsThreadIdRunsPost"></a>
# **runThreadV1ThreadsThreadIdRunsPost**
> Object runThreadV1ThreadsThreadIdRunsPost(threadId)

Run Thread

Create a run.  API Reference: https://platform.openai.com/docs/api-reference/runs/createRun

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.AssistantsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

AssistantsApi apiInstance = new AssistantsApi();
Object threadId = null; // Object | 
try {
    Object result = apiInstance.runThreadV1ThreadsThreadIdRunsPost(threadId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AssistantsApi#runThreadV1ThreadsThreadIdRunsPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **threadId** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

[APIKeyHeader](../README.md#APIKeyHeader)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

