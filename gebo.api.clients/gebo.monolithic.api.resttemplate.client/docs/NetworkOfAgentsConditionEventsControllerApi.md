# NetworkOfAgentsConditionEventsControllerApi

All URIs are relative to *http://localhost:13999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**conditionEventStatus**](NetworkOfAgentsConditionEventsControllerApi.md#conditionEventStatus) | **GET** /api/users/NetworkOfAgentsConditionEventsController/conditionEventStatus | 
[**deliverConditionEvent**](NetworkOfAgentsConditionEventsControllerApi.md#deliverConditionEvent) | **POST** /api/users/NetworkOfAgentsConditionEventsController/deliverConditionEvent | 

<a name="conditionEventStatus"></a>
# **conditionEventStatus**
> ConditionEventView conditionEventStatus(correlationId, sessionCode, conditionId)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.NetworkOfAgentsConditionEventsControllerApi;


NetworkOfAgentsConditionEventsControllerApi apiInstance = new NetworkOfAgentsConditionEventsControllerApi();
String correlationId = "correlationId_example"; // String | 
String sessionCode = "sessionCode_example"; // String | 
String conditionId = "conditionId_example"; // String | 
try {
    ConditionEventView result = apiInstance.conditionEventStatus(correlationId, sessionCode, conditionId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling NetworkOfAgentsConditionEventsControllerApi#conditionEventStatus");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **correlationId** | **String**|  | [optional]
 **sessionCode** | **String**|  | [optional]
 **conditionId** | **String**|  | [optional]

### Return type

[**ConditionEventView**](ConditionEventView.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="deliverConditionEvent"></a>
# **deliverConditionEvent**
> ConditionEventAck deliverConditionEvent(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.NetworkOfAgentsConditionEventsControllerApi;


NetworkOfAgentsConditionEventsControllerApi apiInstance = new NetworkOfAgentsConditionEventsControllerApi();
ConditionEvent body = new ConditionEvent(); // ConditionEvent | 
try {
    ConditionEventAck result = apiInstance.deliverConditionEvent(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling NetworkOfAgentsConditionEventsControllerApi#deliverConditionEvent");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ConditionEvent**](ConditionEvent.md)|  |

### Return type

[**ConditionEventAck**](ConditionEventAck.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

