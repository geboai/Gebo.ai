# WaitingConditionsAdminControllerApi

All URIs are relative to *http://localhost:13999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**abandon1**](WaitingConditionsAdminControllerApi.md#abandon1) | **POST** /api/admin/WaitingConditionsAdminController/abandon | 
[**allWaitingConditions**](WaitingConditionsAdminControllerApi.md#allWaitingConditions) | **GET** /api/admin/WaitingConditionsAdminController/allWaitingConditions | 
[**goOnWithout1**](WaitingConditionsAdminControllerApi.md#goOnWithout1) | **POST** /api/admin/WaitingConditionsAdminController/goOnWithout | 

<a name="abandon1"></a>
# **abandon1**
> WaitingConditionsActionAck abandon1(blockCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.WaitingConditionsAdminControllerApi;


WaitingConditionsAdminControllerApi apiInstance = new WaitingConditionsAdminControllerApi();
String blockCode = "blockCode_example"; // String | 
try {
    WaitingConditionsActionAck result = apiInstance.abandon1(blockCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WaitingConditionsAdminControllerApi#abandon1");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **blockCode** | **String**|  |

### Return type

[**WaitingConditionsActionAck**](WaitingConditionsActionAck.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="allWaitingConditions"></a>
# **allWaitingConditions**
> List&lt;WaitingBlockView&gt; allWaitingConditions()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.WaitingConditionsAdminControllerApi;


WaitingConditionsAdminControllerApi apiInstance = new WaitingConditionsAdminControllerApi();
try {
    List<WaitingBlockView> result = apiInstance.allWaitingConditions();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WaitingConditionsAdminControllerApi#allWaitingConditions");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;WaitingBlockView&gt;**](WaitingBlockView.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="goOnWithout1"></a>
# **goOnWithout1**
> WaitingConditionsActionAck goOnWithout1(blockCode, body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.WaitingConditionsAdminControllerApi;


WaitingConditionsAdminControllerApi apiInstance = new WaitingConditionsAdminControllerApi();
String blockCode = "blockCode_example"; // String | 
List<String> body = Arrays.asList("body_example"); // List<String> | 
try {
    WaitingConditionsActionAck result = apiInstance.goOnWithout1(blockCode, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WaitingConditionsAdminControllerApi#goOnWithout1");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **blockCode** | **String**|  |
 **body** | [**List&lt;String&gt;**](String.md)|  | [optional]

### Return type

[**WaitingConditionsActionAck**](WaitingConditionsActionAck.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

