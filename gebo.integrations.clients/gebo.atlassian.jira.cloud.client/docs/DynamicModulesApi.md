# DynamicModulesApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**dynamicModulesResourceGetModulesGet**](DynamicModulesApi.md#dynamicModulesResourceGetModulesGet) | **GET** /rest/atlassian-connect/1/app/module/dynamic | Get modules
[**dynamicModulesResourceRegisterModulesPost**](DynamicModulesApi.md#dynamicModulesResourceRegisterModulesPost) | **POST** /rest/atlassian-connect/1/app/module/dynamic | Register modules
[**dynamicModulesResourceRemoveModulesDelete**](DynamicModulesApi.md#dynamicModulesResourceRemoveModulesDelete) | **DELETE** /rest/atlassian-connect/1/app/module/dynamic | Remove modules

<a name="dynamicModulesResourceGetModulesGet"></a>
# **dynamicModulesResourceGetModulesGet**
> ConnectModules dynamicModulesResourceGetModulesGet()

Get modules

Returns all modules registered dynamically by the calling app.  **[Permissions](#permissions) required:** Only Connect apps can make this request.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.api.DynamicModulesApi;


DynamicModulesApi apiInstance = new DynamicModulesApi();
try {
    ConnectModules result = apiInstance.dynamicModulesResourceGetModulesGet();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DynamicModulesApi#dynamicModulesResourceGetModulesGet");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**ConnectModules**](ConnectModules.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="dynamicModulesResourceRegisterModulesPost"></a>
# **dynamicModulesResourceRegisterModulesPost**
> dynamicModulesResourceRegisterModulesPost(body)

Register modules

Registers a list of modules.  **[Permissions](#permissions) required:** Only Connect apps can make this request.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.api.DynamicModulesApi;


DynamicModulesApi apiInstance = new DynamicModulesApi();
ConnectModules body = new ConnectModules(); // ConnectModules | 
try {
    apiInstance.dynamicModulesResourceRegisterModulesPost(body);
} catch (ApiException e) {
    System.err.println("Exception when calling DynamicModulesApi#dynamicModulesResourceRegisterModulesPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ConnectModules**](ConnectModules.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="dynamicModulesResourceRemoveModulesDelete"></a>
# **dynamicModulesResourceRemoveModulesDelete**
> dynamicModulesResourceRemoveModulesDelete(moduleKey)

Remove modules

Remove all or a list of modules registered by the calling app.  **[Permissions](#permissions) required:** Only Connect apps can make this request.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.api.DynamicModulesApi;


DynamicModulesApi apiInstance = new DynamicModulesApi();
List<String> moduleKey = Arrays.asList("moduleKey_example"); // List<String> | The key of the module to remove. To include multiple module keys, provide multiple copies of this parameter. For example, `moduleKey=dynamic-attachment-entity-property&moduleKey=dynamic-select-field`. Nonexistent keys are ignored.
try {
    apiInstance.dynamicModulesResourceRemoveModulesDelete(moduleKey);
} catch (ApiException e) {
    System.err.println("Exception when calling DynamicModulesApi#dynamicModulesResourceRemoveModulesDelete");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **moduleKey** | [**List&lt;String&gt;**](String.md)| The key of the module to remove. To include multiple module keys, provide multiple copies of this parameter. For example, &#x60;moduleKey&#x3D;dynamic-attachment-entity-property&amp;moduleKey&#x3D;dynamic-select-field&#x60;. Nonexistent keys are ignored. | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

