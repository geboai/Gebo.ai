# WaitingConditionsAdminControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**adminAbandon**](WaitingConditionsAdminControllerApi.md#adminAbandon) | **POST** /api/admin/WaitingConditionsAdminController/abandon | 
[**adminGoOnWithout**](WaitingConditionsAdminControllerApi.md#adminGoOnWithout) | **POST** /api/admin/WaitingConditionsAdminController/goOnWithout | 
[**allWaitingConditions**](WaitingConditionsAdminControllerApi.md#allWaitingConditions) | **GET** /api/admin/WaitingConditionsAdminController/allWaitingConditions | 

<a name="adminAbandon"></a>
# **adminAbandon**
> WaitingConditionsActionAck adminAbandon(blockCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.WaitingConditionsAdminControllerApi;


WaitingConditionsAdminControllerApi apiInstance = new WaitingConditionsAdminControllerApi();
String blockCode = "blockCode_example"; // String | 
try {
    WaitingConditionsActionAck result = apiInstance.adminAbandon(blockCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WaitingConditionsAdminControllerApi#adminAbandon");
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

<a name="adminGoOnWithout"></a>
# **adminGoOnWithout**
> WaitingConditionsActionAck adminGoOnWithout(blockCode, body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.WaitingConditionsAdminControllerApi;


WaitingConditionsAdminControllerApi apiInstance = new WaitingConditionsAdminControllerApi();
String blockCode = "blockCode_example"; // String | 
List<String> body = Arrays.asList("body_example"); // List<String> | 
try {
    WaitingConditionsActionAck result = apiInstance.adminGoOnWithout(blockCode, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WaitingConditionsAdminControllerApi#adminGoOnWithout");
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

<a name="allWaitingConditions"></a>
# **allWaitingConditions**
> List&lt;WaitingBlockView&gt; allWaitingConditions()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.WaitingConditionsAdminControllerApi;


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

