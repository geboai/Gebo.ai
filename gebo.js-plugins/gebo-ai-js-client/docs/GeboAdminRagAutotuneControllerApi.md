# GeboAiClient.GeboAdminRagAutotuneControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getLatestComputedVectorStores**](GeboAdminRagAutotuneControllerApi.md#getLatestComputedVectorStores) | **GET** /api/admin/GeboAdminRagAutotuneController/getLatestComputedVectorStores | 

<a name="getLatestComputedVectorStores"></a>
# **getLatestComputedVectorStores**
> [AutotuneVectorStoreInfo] getLatestComputedVectorStores()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAdminRagAutotuneControllerApi();
apiInstance.getLatestComputedVectorStores().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[AutotuneVectorStoreInfo]**](AutotuneVectorStoreInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

