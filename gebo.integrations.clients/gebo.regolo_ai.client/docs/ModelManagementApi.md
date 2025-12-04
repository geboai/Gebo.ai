# ModelManagementApi

All URIs are relative to *https://api.regolo.ai*

Method | HTTP request | Description
------------- | ------------- | -------------
[**modelInfoV1ModelInfoGet**](ModelManagementApi.md#modelInfoV1ModelInfoGet) | **GET** /model/info | Model Info V1
[**modelInfoV1V1ModelInfoGet**](ModelManagementApi.md#modelInfoV1V1ModelInfoGet) | **GET** /v1/model/info | Model Info V1
[**modelListModelsGet**](ModelManagementApi.md#modelListModelsGet) | **GET** /models | Model List
[**modelListV1ModelsGet**](ModelManagementApi.md#modelListV1ModelsGet) | **GET** /v1/models | Model List

<a name="modelInfoV1ModelInfoGet"></a>
# **modelInfoV1ModelInfoGet**
> Object modelInfoV1ModelInfoGet(regoloModelId)

Model Info V1

Provides more info about each model in /models  Parameters:     regolo_model_id: Optional[str] &#x3D; None      - When regolo_model_id is passed, it will return the info for that specific model     - When regolo_model_id is not passed, it will return the info for all models  Returns:     Returns a dictionary containing information about each model.  Example Response: &#x60;&#x60;&#x60;json {     \&quot;data\&quot;: [                 {                     \&quot;model_name\&quot;: \&quot;fake-openai-endpoint\&quot;,                     \&quot;litellm_params\&quot;: {                         \&quot;api_base\&quot;: \&quot;https://exampleopenaiendpoint-production.up.railway.app/\&quot;,                         \&quot;model\&quot;: \&quot;openai/fake\&quot;                     },                     \&quot;model_info\&quot;: {                         \&quot;id\&quot;: \&quot;112f74fab24a7a5245d2ced3536dd8f5f9192c57ee6e332af0f0512e08bed5af\&quot;,                         \&quot;db_model\&quot;: false                     }                 }             ] }  &#x60;&#x60;&#x60;

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.ModelManagementApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

ModelManagementApi apiInstance = new ModelManagementApi();
Object regoloModelId = null; // Object | 
try {
    Object result = apiInstance.modelInfoV1ModelInfoGet(regoloModelId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ModelManagementApi#modelInfoV1ModelInfoGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **regoloModelId** | [**Object**](.md)|  | [optional]

### Return type

**Object**

### Authorization

[APIKeyHeader](../README.md#APIKeyHeader)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="modelInfoV1V1ModelInfoGet"></a>
# **modelInfoV1V1ModelInfoGet**
> Object modelInfoV1V1ModelInfoGet(regoloModelId)

Model Info V1

Provides more info about each model in /models  Parameters:     regolo_model_id: Optional[str] &#x3D; None      - When regolo_model_id is passed, it will return the info for that specific model     - When regolo_model_id is not passed, it will return the info for all models  Returns:     Returns a dictionary containing information about each model.  Example Response: &#x60;&#x60;&#x60;json {     \&quot;data\&quot;: [                 {                     \&quot;model_name\&quot;: \&quot;fake-openai-endpoint\&quot;,                     \&quot;litellm_params\&quot;: {                         \&quot;api_base\&quot;: \&quot;https://exampleopenaiendpoint-production.up.railway.app/\&quot;,                         \&quot;model\&quot;: \&quot;openai/fake\&quot;                     },                     \&quot;model_info\&quot;: {                         \&quot;id\&quot;: \&quot;112f74fab24a7a5245d2ced3536dd8f5f9192c57ee6e332af0f0512e08bed5af\&quot;,                         \&quot;db_model\&quot;: false                     }                 }             ] }  &#x60;&#x60;&#x60;

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.ModelManagementApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

ModelManagementApi apiInstance = new ModelManagementApi();
Object regoloModelId = null; // Object | 
try {
    Object result = apiInstance.modelInfoV1V1ModelInfoGet(regoloModelId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ModelManagementApi#modelInfoV1V1ModelInfoGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **regoloModelId** | [**Object**](.md)|  | [optional]

### Return type

**Object**

### Authorization

[APIKeyHeader](../README.md#APIKeyHeader)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="modelListModelsGet"></a>
# **modelListModelsGet**
> Object modelListModelsGet(returnWildcardRoutes)

Model List

Use &#x60;/model/info&#x60; - to get detailed model information, example - pricing, mode, etc.  This is just for compatibility with openai projects like aider.

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.ModelManagementApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

ModelManagementApi apiInstance = new ModelManagementApi();
Object returnWildcardRoutes = false; // Object | 
try {
    Object result = apiInstance.modelListModelsGet(returnWildcardRoutes);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ModelManagementApi#modelListModelsGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **returnWildcardRoutes** | [**Object**](.md)|  | [optional] [default to false]

### Return type

**Object**

### Authorization

[APIKeyHeader](../README.md#APIKeyHeader)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="modelListV1ModelsGet"></a>
# **modelListV1ModelsGet**
> Object modelListV1ModelsGet(returnWildcardRoutes)

Model List

Use &#x60;/model/info&#x60; - to get detailed model information, example - pricing, mode, etc.  This is just for compatibility with openai projects like aider.

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.ModelManagementApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

ModelManagementApi apiInstance = new ModelManagementApi();
Object returnWildcardRoutes = false; // Object | 
try {
    Object result = apiInstance.modelListV1ModelsGet(returnWildcardRoutes);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ModelManagementApi#modelListV1ModelsGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **returnWildcardRoutes** | [**Object**](.md)|  | [optional] [default to false]

### Return type

**Object**

### Authorization

[APIKeyHeader](../README.md#APIKeyHeader)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

