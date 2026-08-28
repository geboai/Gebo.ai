# BrainClient.GeboAdminRagAutotuneControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getLatestComputedVectorStores**](GeboAdminRagAutotuneControllerApi.md#getLatestComputedVectorStores) | **GET** /api/admin/GeboAdminRagAutotuneController/getLatestComputedVectorStores | 

<a name="getLatestComputedVectorStores"></a>
# **getLatestComputedVectorStores**
> Object getLatestComputedVectorStores()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboAdminRagAutotuneControllerApi();
apiInstance.getLatestComputedVectorStores().then((data) => {
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

