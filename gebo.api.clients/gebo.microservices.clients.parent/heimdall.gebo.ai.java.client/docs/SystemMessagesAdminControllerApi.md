# SystemMessagesAdminControllerApi

All URIs are relative to *http://localhost:13018/heimdall*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAllSystemMessages**](SystemMessagesAdminControllerApi.md#getAllSystemMessages) | **GET** /api/admin/SystemMessagesAdminController/getAllSystemMessages | 

<a name="getAllSystemMessages"></a>
# **getAllSystemMessages**
> List&lt;GSystemMessage&gt; getAllSystemMessages()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.SystemMessagesAdminControllerApi;


SystemMessagesAdminControllerApi apiInstance = new SystemMessagesAdminControllerApi();
try {
    List<GSystemMessage> result = apiInstance.getAllSystemMessages();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SystemMessagesAdminControllerApi#getAllSystemMessages");
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

