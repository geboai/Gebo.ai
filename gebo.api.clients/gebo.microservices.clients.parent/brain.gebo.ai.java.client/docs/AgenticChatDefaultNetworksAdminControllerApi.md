# AgenticChatDefaultNetworksAdminControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAgenticChatDefaultNetworks**](AgenticChatDefaultNetworksAdminControllerApi.md#getAgenticChatDefaultNetworks) | **GET** /api/admin/AgenticChatDefaultNetworksAdminController/getAgenticChatDefaultNetworks | 
[**getChoosableChatNetworksOfAgents**](AgenticChatDefaultNetworksAdminControllerApi.md#getChoosableChatNetworksOfAgents) | **GET** /api/admin/AgenticChatDefaultNetworksAdminController/getChoosableChatNetworksOfAgents | 
[**isAgenticChatNetworksEnabled**](AgenticChatDefaultNetworksAdminControllerApi.md#isAgenticChatNetworksEnabled) | **GET** /api/admin/AgenticChatDefaultNetworksAdminController/isAgenticChatNetworksEnabled | 
[**resetAgenticChatDefaultNetwork**](AgenticChatDefaultNetworksAdminControllerApi.md#resetAgenticChatDefaultNetwork) | **POST** /api/admin/AgenticChatDefaultNetworksAdminController/resetAgenticChatDefaultNetwork | 
[**setAgenticChatDefaultNetwork**](AgenticChatDefaultNetworksAdminControllerApi.md#setAgenticChatDefaultNetwork) | **POST** /api/admin/AgenticChatDefaultNetworksAdminController/setAgenticChatDefaultNetwork | 

<a name="getAgenticChatDefaultNetworks"></a>
# **getAgenticChatDefaultNetworks**
> List&lt;AgenticChatDefaultNetworkInfo&gt; getAgenticChatDefaultNetworks()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.AgenticChatDefaultNetworksAdminControllerApi;


AgenticChatDefaultNetworksAdminControllerApi apiInstance = new AgenticChatDefaultNetworksAdminControllerApi();
try {
    List<AgenticChatDefaultNetworkInfo> result = apiInstance.getAgenticChatDefaultNetworks();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AgenticChatDefaultNetworksAdminControllerApi#getAgenticChatDefaultNetworks");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;AgenticChatDefaultNetworkInfo&gt;**](AgenticChatDefaultNetworkInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getChoosableChatNetworksOfAgents"></a>
# **getChoosableChatNetworksOfAgents**
> List&lt;GAgentsNetwork&gt; getChoosableChatNetworksOfAgents(pipelineType)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.AgenticChatDefaultNetworksAdminControllerApi;


AgenticChatDefaultNetworksAdminControllerApi apiInstance = new AgenticChatDefaultNetworksAdminControllerApi();
String pipelineType = "pipelineType_example"; // String | 
try {
    List<GAgentsNetwork> result = apiInstance.getChoosableChatNetworksOfAgents(pipelineType);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AgenticChatDefaultNetworksAdminControllerApi#getChoosableChatNetworksOfAgents");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **pipelineType** | **String**|  | [enum: RAG_PIPELINE, PURE_CHAT_PIPELINE]

### Return type

[**List&lt;GAgentsNetwork&gt;**](GAgentsNetwork.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="isAgenticChatNetworksEnabled"></a>
# **isAgenticChatNetworksEnabled**
> Boolean isAgenticChatNetworksEnabled()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.AgenticChatDefaultNetworksAdminControllerApi;


AgenticChatDefaultNetworksAdminControllerApi apiInstance = new AgenticChatDefaultNetworksAdminControllerApi();
try {
    Boolean result = apiInstance.isAgenticChatNetworksEnabled();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AgenticChatDefaultNetworksAdminControllerApi#isAgenticChatNetworksEnabled");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Boolean**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="resetAgenticChatDefaultNetwork"></a>
# **resetAgenticChatDefaultNetwork**
> OperationStatusAgenticChatDefaultNetworkInfo resetAgenticChatDefaultNetwork(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.AgenticChatDefaultNetworksAdminControllerApi;


AgenticChatDefaultNetworksAdminControllerApi apiInstance = new AgenticChatDefaultNetworksAdminControllerApi();
AgenticChatDefaultNetworkRequest body = new AgenticChatDefaultNetworkRequest(); // AgenticChatDefaultNetworkRequest | 
try {
    OperationStatusAgenticChatDefaultNetworkInfo result = apiInstance.resetAgenticChatDefaultNetwork(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AgenticChatDefaultNetworksAdminControllerApi#resetAgenticChatDefaultNetwork");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**AgenticChatDefaultNetworkRequest**](AgenticChatDefaultNetworkRequest.md)|  |

### Return type

[**OperationStatusAgenticChatDefaultNetworkInfo**](OperationStatusAgenticChatDefaultNetworkInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="setAgenticChatDefaultNetwork"></a>
# **setAgenticChatDefaultNetwork**
> OperationStatusAgenticChatDefaultNetworkInfo setAgenticChatDefaultNetwork(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.AgenticChatDefaultNetworksAdminControllerApi;


AgenticChatDefaultNetworksAdminControllerApi apiInstance = new AgenticChatDefaultNetworksAdminControllerApi();
AgenticChatDefaultNetworkRequest body = new AgenticChatDefaultNetworkRequest(); // AgenticChatDefaultNetworkRequest | 
try {
    OperationStatusAgenticChatDefaultNetworkInfo result = apiInstance.setAgenticChatDefaultNetwork(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AgenticChatDefaultNetworksAdminControllerApi#setAgenticChatDefaultNetwork");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**AgenticChatDefaultNetworkRequest**](AgenticChatDefaultNetworkRequest.md)|  |

### Return type

[**OperationStatusAgenticChatDefaultNetworkInfo**](OperationStatusAgenticChatDefaultNetworkInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

