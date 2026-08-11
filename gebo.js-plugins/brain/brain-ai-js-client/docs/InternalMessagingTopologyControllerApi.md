# BrainClient.InternalMessagingTopologyControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getLocalTopology**](InternalMessagingTopologyControllerApi.md#getLocalTopology) | **GET** /api/admin/InternalMessagingTopologyController/getLocalTopology | 

<a name="getLocalTopology"></a>
# **getLocalTopology**
> Object getLocalTopology()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.InternalMessagingTopologyControllerApi();
apiInstance.getLocalTopology().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

