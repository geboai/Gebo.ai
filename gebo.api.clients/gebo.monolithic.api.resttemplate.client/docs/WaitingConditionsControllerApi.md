# WaitingConditionsControllerApi

All URIs are relative to *http://localhost:13999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**abandon**](WaitingConditionsControllerApi.md#abandon) | **POST** /api/users/WaitingConditionsController/abandon | 
[**goOnWithout**](WaitingConditionsControllerApi.md#goOnWithout) | **POST** /api/users/WaitingConditionsController/goOnWithout | 
[**waitingConditions**](WaitingConditionsControllerApi.md#waitingConditions) | **GET** /api/users/WaitingConditionsController/waitingConditions | 

<a name="abandon"></a>
# **abandon**
> WaitingConditionsActionAck abandon(blockCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.WaitingConditionsControllerApi;


WaitingConditionsControllerApi apiInstance = new WaitingConditionsControllerApi();
String blockCode = "blockCode_example"; // String | 
try {
    WaitingConditionsActionAck result = apiInstance.abandon(blockCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WaitingConditionsControllerApi#abandon");
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

<a name="goOnWithout"></a>
# **goOnWithout**
> WaitingConditionsActionAck goOnWithout(blockCode, body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.WaitingConditionsControllerApi;


WaitingConditionsControllerApi apiInstance = new WaitingConditionsControllerApi();
String blockCode = "blockCode_example"; // String | 
List<String> body = Arrays.asList("body_example"); // List<String> | 
try {
    WaitingConditionsActionAck result = apiInstance.goOnWithout(blockCode, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WaitingConditionsControllerApi#goOnWithout");
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

<a name="waitingConditions"></a>
# **waitingConditions**
> List&lt;WaitingBlockView&gt; waitingConditions()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.WaitingConditionsControllerApi;


WaitingConditionsControllerApi apiInstance = new WaitingConditionsControllerApi();
try {
    List<WaitingBlockView> result = apiInstance.waitingConditions();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WaitingConditionsControllerApi#waitingConditions");
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

