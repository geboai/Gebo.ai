# GeboAiClient.DocumentContentStreamerControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**streamDocumentReference**](DocumentContentStreamerControllerApi.md#streamDocumentReference) | **POST** /api/users/DocumentContentStreamerController/streamDocumentReference | 
[**streamSearchResult**](DocumentContentStreamerControllerApi.md#streamSearchResult) | **POST** /api/users/DocumentContentStreamerController/streamSearchResult | 

<a name="streamDocumentReference"></a>
# **streamDocumentReference**
> &#x27;Blob&#x27; streamDocumentReference(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.DocumentContentStreamerControllerApi();
let body = new GeboAiClient.GDocumentReferenceStreamRequest(); // GDocumentReferenceStreamRequest | 

apiInstance.streamDocumentReference(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GDocumentReferenceStreamRequest**](GDocumentReferenceStreamRequest.md)|  | 

### Return type

**&#x27;Blob&#x27;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/octet-stream

<a name="streamSearchResult"></a>
# **streamSearchResult**
> &#x27;Blob&#x27; streamSearchResult(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.DocumentContentStreamerControllerApi();
let body = new GeboAiClient.SearchResultStreamRequest(); // SearchResultStreamRequest | 

apiInstance.streamSearchResult(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SearchResultStreamRequest**](SearchResultStreamRequest.md)|  | 

### Return type

**&#x27;Blob&#x27;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/octet-stream

