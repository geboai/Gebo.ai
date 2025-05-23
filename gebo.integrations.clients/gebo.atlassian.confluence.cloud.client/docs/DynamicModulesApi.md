# DynamicModulesApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getModules**](DynamicModulesApi.md#getModules) | **GET** /atlassian-connect/1/app/module/dynamic | Get modules
[**registerModules**](DynamicModulesApi.md#registerModules) | **POST** /atlassian-connect/1/app/module/dynamic | Register modules
[**removeModules**](DynamicModulesApi.md#removeModules) | **DELETE** /atlassian-connect/1/app/module/dynamic | Remove modules

<a name="getModules"></a>
# **getModules**
> ConnectModules getModules()

Get modules

Returns all modules registered dynamically by the calling app.  **[Permissions](#permissions) required:** Only Connect apps can make this request.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.DynamicModulesApi;


DynamicModulesApi apiInstance = new DynamicModulesApi();
try {
    ConnectModules result = apiInstance.getModules();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DynamicModulesApi#getModules");
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
 - **Accept**: */*, message

<a name="registerModules"></a>
# **registerModules**
> registerModules(body)

Register modules

Registers a list of modules. For the list of modules that support dynamic registration, see [Dynamic modules](https://developer.atlassian.com/cloud/confluence/dynamic-modules/).  **[Permissions](#permissions) required:** Only Connect apps can make this request.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.DynamicModulesApi;


DynamicModulesApi apiInstance = new DynamicModulesApi();
ConnectModules body = new ConnectModules(); // ConnectModules | 
try {
    apiInstance.registerModules(body);
} catch (ApiException e) {
    System.err.println("Exception when calling DynamicModulesApi#registerModules");
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

 - **Content-Type**: */*
 - **Accept**: */*, message

<a name="removeModules"></a>
# **removeModules**
> removeModules(moduleKey)

Remove modules

Remove all or a list of modules registered by the calling app.  **[Permissions](#permissions) required:** Only Connect apps can make this request.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.DynamicModulesApi;


DynamicModulesApi apiInstance = new DynamicModulesApi();
List<String> moduleKey = Arrays.asList("moduleKey_example"); // List<String> | The key of the module to remove. To include multiple module keys, provide multiple copies of this parameter. For example, `moduleKey=dynamic-attachment-entity-property&moduleKey=dynamic-select-field`. Nonexistent keys are ignored.
try {
    apiInstance.removeModules(moduleKey);
} catch (ApiException e) {
    System.err.println("Exception when calling DynamicModulesApi#removeModules");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **moduleKey** | [**List&lt;String&gt;**](String.md)| The key of the module to remove. To include multiple module keys, provide multiple copies of this parameter. For example, &#x60;moduleKey&#x3D;dynamic-attachment-entity-property&amp;moduleKey&#x3D;dynamic-select-field&#x60;. Nonexistent keys are ignored. |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*, message

