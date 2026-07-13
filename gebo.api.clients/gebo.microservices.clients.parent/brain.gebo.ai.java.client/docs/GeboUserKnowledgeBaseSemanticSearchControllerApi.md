# GeboUserKnowledgeBaseSemanticSearchControllerApi

All URIs are relative to *http://localhost:13001*

Method | HTTP request | Description
------------- | ------------- | -------------
[**semanticSearch**](GeboUserKnowledgeBaseSemanticSearchControllerApi.md#semanticSearch) | **POST** /api/users/GeboUserKnowledgeBaseSemanticSearchController/semanticSearch | 

<a name="semanticSearch"></a>
# **semanticSearch**
> Object semanticSearch(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboUserKnowledgeBaseSemanticSearchControllerApi;


GeboUserKnowledgeBaseSemanticSearchControllerApi apiInstance = new GeboUserKnowledgeBaseSemanticSearchControllerApi();
SemanticQueryParam body = new SemanticQueryParam(); // SemanticQueryParam | 
try {
    Object result = apiInstance.semanticSearch(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboUserKnowledgeBaseSemanticSearchControllerApi#semanticSearch");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SemanticQueryParam**](SemanticQueryParam.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

