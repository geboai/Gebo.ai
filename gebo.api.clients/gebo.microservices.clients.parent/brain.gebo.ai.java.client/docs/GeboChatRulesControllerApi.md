# GeboChatRulesControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**checkRuleConflicts**](GeboChatRulesControllerApi.md#checkRuleConflicts) | **POST** /api/users/GeboChatRulesController/checkRuleConflicts | 
[**createRule**](GeboChatRulesControllerApi.md#createRule) | **POST** /api/users/GeboChatRulesController/createRule | 
[**deleteRule**](GeboChatRulesControllerApi.md#deleteRule) | **DELETE** /api/users/GeboChatRulesController/deleteRule | 
[**getChatRules**](GeboChatRulesControllerApi.md#getChatRules) | **GET** /api/users/GeboChatRulesController/getChatRules | 
[**getMyRules**](GeboChatRulesControllerApi.md#getMyRules) | **GET** /api/users/GeboChatRulesController/getMyRules | 
[**getSharedRulesAppliedToMe**](GeboChatRulesControllerApi.md#getSharedRulesAppliedToMe) | **GET** /api/users/GeboChatRulesController/getSharedRulesAppliedToMe | 
[**proposeRules**](GeboChatRulesControllerApi.md#proposeRules) | **GET** /api/users/GeboChatRulesController/proposeRules | 
[**updateRule**](GeboChatRulesControllerApi.md#updateRule) | **POST** /api/users/GeboChatRulesController/updateRule | 

<a name="checkRuleConflicts"></a>
# **checkRuleConflicts**
> List&lt;ChatRuleConflict&gt; checkRuleConflicts(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboChatRulesControllerApi;


GeboChatRulesControllerApi apiInstance = new GeboChatRulesControllerApi();
GChatRule body = new GChatRule(); // GChatRule | 
try {
    List<ChatRuleConflict> result = apiInstance.checkRuleConflicts(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboChatRulesControllerApi#checkRuleConflicts");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GChatRule**](GChatRule.md)|  |

### Return type

[**List&lt;ChatRuleConflict&gt;**](ChatRuleConflict.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createRule"></a>
# **createRule**
> GChatRule createRule(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboChatRulesControllerApi;


GeboChatRulesControllerApi apiInstance = new GeboChatRulesControllerApi();
GChatRule body = new GChatRule(); // GChatRule | 
try {
    GChatRule result = apiInstance.createRule(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboChatRulesControllerApi#createRule");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GChatRule**](GChatRule.md)|  |

### Return type

[**GChatRule**](GChatRule.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteRule"></a>
# **deleteRule**
> deleteRule(id)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboChatRulesControllerApi;


GeboChatRulesControllerApi apiInstance = new GeboChatRulesControllerApi();
String id = "id_example"; // String | 
try {
    apiInstance.deleteRule(id);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboChatRulesControllerApi#deleteRule");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="getChatRules"></a>
# **getChatRules**
> List&lt;GChatRule&gt; getChatRules(userChatContextCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboChatRulesControllerApi;


GeboChatRulesControllerApi apiInstance = new GeboChatRulesControllerApi();
String userChatContextCode = "userChatContextCode_example"; // String | 
try {
    List<GChatRule> result = apiInstance.getChatRules(userChatContextCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboChatRulesControllerApi#getChatRules");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userChatContextCode** | **String**|  |

### Return type

[**List&lt;GChatRule&gt;**](GChatRule.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getMyRules"></a>
# **getMyRules**
> List&lt;GChatRule&gt; getMyRules()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboChatRulesControllerApi;


GeboChatRulesControllerApi apiInstance = new GeboChatRulesControllerApi();
try {
    List<GChatRule> result = apiInstance.getMyRules();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboChatRulesControllerApi#getMyRules");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GChatRule&gt;**](GChatRule.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSharedRulesAppliedToMe"></a>
# **getSharedRulesAppliedToMe**
> List&lt;GChatRule&gt; getSharedRulesAppliedToMe()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboChatRulesControllerApi;


GeboChatRulesControllerApi apiInstance = new GeboChatRulesControllerApi();
try {
    List<GChatRule> result = apiInstance.getSharedRulesAppliedToMe();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboChatRulesControllerApi#getSharedRulesAppliedToMe");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GChatRule&gt;**](GChatRule.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="proposeRules"></a>
# **proposeRules**
> List&lt;String&gt; proposeRules(userChatContextCode, requestId)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboChatRulesControllerApi;


GeboChatRulesControllerApi apiInstance = new GeboChatRulesControllerApi();
String userChatContextCode = "userChatContextCode_example"; // String | 
String requestId = "requestId_example"; // String | 
try {
    List<String> result = apiInstance.proposeRules(userChatContextCode, requestId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboChatRulesControllerApi#proposeRules");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userChatContextCode** | **String**|  |
 **requestId** | **String**|  |

### Return type

**List&lt;String&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateRule"></a>
# **updateRule**
> GChatRule updateRule(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboChatRulesControllerApi;


GeboChatRulesControllerApi apiInstance = new GeboChatRulesControllerApi();
GChatRule body = new GChatRule(); // GChatRule | 
try {
    GChatRule result = apiInstance.updateRule(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboChatRulesControllerApi#updateRule");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GChatRule**](GChatRule.md)|  |

### Return type

[**GChatRule**](GChatRule.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

