# GeboAiClient.GenericalPublisherControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**publishCentralizedEndpoint**](GenericalPublisherControllerApi.md#publishCentralizedEndpoint) | **POST** /api/admin/GenericalPublisherController/publishCentralizedEndpoint | 

<a name="publishCentralizedEndpoint"></a>
# **publishCentralizedEndpoint**
> OperationStatusGJobStatus publishCentralizedEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericalPublisherControllerApi();
let body = new GeboAiClient.GCentralizedProjectEndpoint(); // GCentralizedProjectEndpoint | 

apiInstance.publishCentralizedEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GCentralizedProjectEndpoint**](GCentralizedProjectEndpoint.md)|  | 

### Return type

[**OperationStatusGJobStatus**](OperationStatusGJobStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

