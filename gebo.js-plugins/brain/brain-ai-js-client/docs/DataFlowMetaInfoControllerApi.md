# BrainClient.DataFlowMetaInfoControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getLocalDataFlow**](DataFlowMetaInfoControllerApi.md#getLocalDataFlow) | **GET** /api/admin/DataFlowMetaInfoController/getLocalDataFlow | 

<a name="getLocalDataFlow"></a>
# **getLocalDataFlow**
> GDataFlowReport getLocalDataFlow()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.DataFlowMetaInfoControllerApi();
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

