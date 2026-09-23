# BrainClient.GeboNeo4jModuleSetupControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getNeo4jModuleSetupConfig**](GeboNeo4jModuleSetupControllerApi.md#getNeo4jModuleSetupConfig) | **GET** /api/admin/GeboNeo4jModuleSetupController | 

<a name="getNeo4jModuleSetupConfig"></a>
# **getNeo4jModuleSetupConfig**
> GeboNeo4jModuleConfigDto getNeo4jModuleSetupConfig()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboNeo4jModuleSetupControllerApi();
apiInstance.getNeo4jModuleSetupConfig().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**GeboNeo4jModuleConfigDto**](GeboNeo4jModuleConfigDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

