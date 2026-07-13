# FunctionsLookupControllerApi

All URIs are relative to *http://localhost:13001*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAllFunctions**](FunctionsLookupControllerApi.md#getAllFunctions) | **GET** /api/admin/FunctionsLookupController/getAllFunctions | 
[**getAllFunctionsTree**](FunctionsLookupControllerApi.md#getAllFunctionsTree) | **GET** /api/admin/FunctionsLookupController/getAllFunctionsTree | 
[**getAllLocalFunctions**](FunctionsLookupControllerApi.md#getAllLocalFunctions) | **GET** /api/admin/FunctionsLookupController/getAllLocalFunctions | 
[**getAllLocalFunctionsTree**](FunctionsLookupControllerApi.md#getAllLocalFunctionsTree) | **GET** /api/admin/FunctionsLookupController/getAllLocalFunctionsTree | 

<a name="getAllFunctions"></a>
# **getAllFunctions**
> Object getAllFunctions()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.FunctionsLookupControllerApi;


FunctionsLookupControllerApi apiInstance = new FunctionsLookupControllerApi();
try {
    Object result = apiInstance.getAllFunctions();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FunctionsLookupControllerApi#getAllFunctions");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAllFunctionsTree"></a>
# **getAllFunctionsTree**
> Object getAllFunctionsTree(ragContextFunctions)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.FunctionsLookupControllerApi;


FunctionsLookupControllerApi apiInstance = new FunctionsLookupControllerApi();
Object ragContextFunctions = null; // Object | 
try {
    Object result = apiInstance.getAllFunctionsTree(ragContextFunctions);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FunctionsLookupControllerApi#getAllFunctionsTree");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ragContextFunctions** | [**Object**](.md)|  | [optional]

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAllLocalFunctions"></a>
# **getAllLocalFunctions**
> Object getAllLocalFunctions()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.FunctionsLookupControllerApi;


FunctionsLookupControllerApi apiInstance = new FunctionsLookupControllerApi();
try {
    Object result = apiInstance.getAllLocalFunctions();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FunctionsLookupControllerApi#getAllLocalFunctions");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAllLocalFunctionsTree"></a>
# **getAllLocalFunctionsTree**
> Object getAllLocalFunctionsTree(ragContextFunctions)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.FunctionsLookupControllerApi;


FunctionsLookupControllerApi apiInstance = new FunctionsLookupControllerApi();
Object ragContextFunctions = null; // Object | 
try {
    Object result = apiInstance.getAllLocalFunctionsTree(ragContextFunctions);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FunctionsLookupControllerApi#getAllLocalFunctionsTree");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ragContextFunctions** | [**Object**](.md)|  | [optional]

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

