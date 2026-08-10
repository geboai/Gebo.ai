# GeboAiClient.GeboUserKnowledgeBaseSemanticSearchControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**semanticSearch**](GeboUserKnowledgeBaseSemanticSearchControllerApi.md#semanticSearch) | **POST** /api/users/GeboUserKnowledgeBaseSemanticSearchController/semanticSearch | 

<a name="semanticSearch"></a>
# **semanticSearch**
> [&#x27;String&#x27;] semanticSearch(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboUserKnowledgeBaseSemanticSearchControllerApi();
let body = new GeboAiClient.SemanticQueryParam(); // SemanticQueryParam | 

apiInstance.semanticSearch(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SemanticQueryParam**](SemanticQueryParam.md)|  | 

### Return type

**[&#x27;String&#x27;]**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

