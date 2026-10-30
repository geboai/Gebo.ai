# GeboUserKnowledgeBaseSemanticSearchControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**semanticSearch**](GeboUserKnowledgeBaseSemanticSearchControllerApi.md#semanticSearch) | **POST** /api/users/GeboUserKnowledgeBaseSemanticSearchController/semanticSearch | 

<a name="semanticSearch"></a>
# **semanticSearch**
> List&lt;String&gt; semanticSearch(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboUserKnowledgeBaseSemanticSearchControllerApi;


GeboUserKnowledgeBaseSemanticSearchControllerApi apiInstance = new GeboUserKnowledgeBaseSemanticSearchControllerApi();
SemanticQueryParam body = new SemanticQueryParam(); // SemanticQueryParam | 
try {
    List<String> result = apiInstance.semanticSearch(body);
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

**List&lt;String&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

