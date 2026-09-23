# GeboAiClient.DataFlowMetaInfoControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getLocalDataFlow**](DataFlowMetaInfoControllerApi.md#getLocalDataFlow) | **GET** /api/admin/DataFlowMetaInfoController/getLocalDataFlow | 

<a name="getLocalDataFlow"></a>
# **getLocalDataFlow**
> GDataFlowReport getLocalDataFlow()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.DataFlowMetaInfoControllerApi();
apiInstance.getLocalDataFlow().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**GDataFlowReport**](GDataFlowReport.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

