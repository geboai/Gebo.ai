# GeboAdminChatRulesControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**checkSharedRuleConflicts**](GeboAdminChatRulesControllerApi.md#checkSharedRuleConflicts) | **POST** /api/admin/GeboAdminChatRulesController/checkSharedRuleConflicts | 
[**createSharedRule**](GeboAdminChatRulesControllerApi.md#createSharedRule) | **POST** /api/admin/GeboAdminChatRulesController/createSharedRule | 
[**deleteSharedRule**](GeboAdminChatRulesControllerApi.md#deleteSharedRule) | **DELETE** /api/admin/GeboAdminChatRulesController/deleteSharedRule | 
[**getSharedRules**](GeboAdminChatRulesControllerApi.md#getSharedRules) | **GET** /api/admin/GeboAdminChatRulesController/getSharedRules | 
[**updateSharedRule**](GeboAdminChatRulesControllerApi.md#updateSharedRule) | **POST** /api/admin/GeboAdminChatRulesController/updateSharedRule | 

<a name="checkSharedRuleConflicts"></a>
# **checkSharedRuleConflicts**
> List&lt;ChatRuleConflict&gt; checkSharedRuleConflicts(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAdminChatRulesControllerApi;


GeboAdminChatRulesControllerApi apiInstance = new GeboAdminChatRulesControllerApi();
GChatRule body = new GChatRule(); // GChatRule | 
try {
    List<ChatRuleConflict> result = apiInstance.checkSharedRuleConflicts(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminChatRulesControllerApi#checkSharedRuleConflicts");
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

<a name="createSharedRule"></a>
# **createSharedRule**
> GChatRule createSharedRule(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAdminChatRulesControllerApi;


GeboAdminChatRulesControllerApi apiInstance = new GeboAdminChatRulesControllerApi();
GChatRule body = new GChatRule(); // GChatRule | 
try {
    GChatRule result = apiInstance.createSharedRule(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminChatRulesControllerApi#createSharedRule");
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

<a name="deleteSharedRule"></a>
# **deleteSharedRule**
> deleteSharedRule(id)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAdminChatRulesControllerApi;


GeboAdminChatRulesControllerApi apiInstance = new GeboAdminChatRulesControllerApi();
String id = "id_example"; // String | 
try {
    apiInstance.deleteSharedRule(id);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminChatRulesControllerApi#deleteSharedRule");
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

<a name="getSharedRules"></a>
# **getSharedRules**
> List&lt;GChatRule&gt; getSharedRules()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAdminChatRulesControllerApi;


GeboAdminChatRulesControllerApi apiInstance = new GeboAdminChatRulesControllerApi();
try {
    List<GChatRule> result = apiInstance.getSharedRules();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminChatRulesControllerApi#getSharedRules");
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

<a name="updateSharedRule"></a>
# **updateSharedRule**
> GChatRule updateSharedRule(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAdminChatRulesControllerApi;


GeboAdminChatRulesControllerApi apiInstance = new GeboAdminChatRulesControllerApi();
GChatRule body = new GChatRule(); // GChatRule | 
try {
    GChatRule result = apiInstance.updateSharedRule(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminChatRulesControllerApi#updateSharedRule");
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

