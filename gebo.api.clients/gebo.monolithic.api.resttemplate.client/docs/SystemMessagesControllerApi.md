# SystemMessagesControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**dismissSystemMessage**](SystemMessagesControllerApi.md#dismissSystemMessage) | **POST** /api/users/SystemMessagesController/dismissSystemMessage | 
[**getMySystemMessages**](SystemMessagesControllerApi.md#getMySystemMessages) | **GET** /api/users/SystemMessagesController/getMySystemMessages | 

<a name="dismissSystemMessage"></a>
# **dismissSystemMessage**
> OperationStatusBoolean dismissSystemMessage(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SystemMessagesControllerApi;


SystemMessagesControllerApi apiInstance = new SystemMessagesControllerApi();
DismissSystemMessageRequest body = new DismissSystemMessageRequest(); // DismissSystemMessageRequest | 
try {
    OperationStatusBoolean result = apiInstance.dismissSystemMessage(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SystemMessagesControllerApi#dismissSystemMessage");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**DismissSystemMessageRequest**](DismissSystemMessageRequest.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMySystemMessages"></a>
# **getMySystemMessages**
> List&lt;GSystemMessage&gt; getMySystemMessages()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SystemMessagesControllerApi;


SystemMessagesControllerApi apiInstance = new SystemMessagesControllerApi();
try {
    List<GSystemMessage> result = apiInstance.getMySystemMessages();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SystemMessagesControllerApi#getMySystemMessages");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GSystemMessage&gt;**](GSystemMessage.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

