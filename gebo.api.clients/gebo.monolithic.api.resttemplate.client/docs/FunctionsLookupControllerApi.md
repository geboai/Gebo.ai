# FunctionsLookupControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAllFunctions**](FunctionsLookupControllerApi.md#getAllFunctions) | **GET** /api/admin/FunctionsLookupController/getAllFunctions | 
[**getAllFunctionsTree**](FunctionsLookupControllerApi.md#getAllFunctionsTree) | **GET** /api/admin/FunctionsLookupController/getAllFunctionsTree | 

<a name="getAllFunctions"></a>
# **getAllFunctions**
> List&lt;GLookupEntry&gt; getAllFunctions()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.FunctionsLookupControllerApi;


FunctionsLookupControllerApi apiInstance = new FunctionsLookupControllerApi();
try {
    List<GLookupEntry> result = apiInstance.getAllFunctions();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FunctionsLookupControllerApi#getAllFunctions");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GLookupEntry&gt;**](GLookupEntry.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAllFunctionsTree"></a>
# **getAllFunctionsTree**
> List&lt;ToolCategoriesTree&gt; getAllFunctionsTree(ragContextFunctions)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.FunctionsLookupControllerApi;


FunctionsLookupControllerApi apiInstance = new FunctionsLookupControllerApi();
Boolean ragContextFunctions = true; // Boolean | 
try {
    List<ToolCategoriesTree> result = apiInstance.getAllFunctionsTree(ragContextFunctions);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FunctionsLookupControllerApi#getAllFunctionsTree");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ragContextFunctions** | **Boolean**|  | [optional]

### Return type

[**List&lt;ToolCategoriesTree&gt;**](ToolCategoriesTree.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

