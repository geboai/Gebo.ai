# BrainClient.BuildSystemsControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getBuildSystemConfigs**](BuildSystemsControllerApi.md#getBuildSystemConfigs) | **GET** /api/admin/BuildSystemsController/getBuildSystemConfigs | 
[**getBuildSystemTypes**](BuildSystemsControllerApi.md#getBuildSystemTypes) | **GET** /api/admin/BuildSystemsController/getBuildSystemTypes | 

<a name="getBuildSystemConfigs"></a>
# **getBuildSystemConfigs**
> Object getBuildSystemConfigs(buildSystemTypeCode)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.BuildSystemsControllerApi();
let buildSystemTypeCode = null; // Object | 

apiInstance.getBuildSystemConfigs(buildSystemTypeCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **buildSystemTypeCode** | [**Object**](.md)|  | 

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getBuildSystemTypes"></a>
# **getBuildSystemTypes**
> Object getBuildSystemTypes()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.BuildSystemsControllerApi();
apiInstance.getBuildSystemTypes().then((data) => {
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

