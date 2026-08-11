# BrainClient.DocumentContentStreamerControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**streamDocumentReference**](DocumentContentStreamerControllerApi.md#streamDocumentReference) | **POST** /api/users/DocumentContentStreamerController/streamDocumentReference | 
[**streamSearchResult**](DocumentContentStreamerControllerApi.md#streamSearchResult) | **POST** /api/users/DocumentContentStreamerController/streamSearchResult | 

<a name="streamDocumentReference"></a>
# **streamDocumentReference**
> Object streamDocumentReference(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.DocumentContentStreamerControllerApi();
let body = new BrainClient.GDocumentReferenceStreamRequest(); // GDocumentReferenceStreamRequest | 

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

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/octet-stream

<a name="streamSearchResult"></a>
# **streamSearchResult**
> Object streamSearchResult(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.DocumentContentStreamerControllerApi();
let body = new BrainClient.SearchResultStreamRequest(); // SearchResultStreamRequest | 

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

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/octet-stream

