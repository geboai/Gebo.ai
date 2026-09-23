# GeboAiClient.ClientsTopologyProviderControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getClientsTopology**](ClientsTopologyProviderControllerApi.md#getClientsTopology) | **GET** /public/ClientsTopologyProviderController | 

<a name="getClientsTopology"></a>
# **getClientsTopology**
> GeboClientsTopologyInfo getClientsTopology()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ClientsTopologyProviderControllerApi();
apiInstance.getClientsTopology().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**GeboClientsTopologyInfo**](GeboClientsTopologyInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

