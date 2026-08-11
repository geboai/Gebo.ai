# BrainClient.FunctionsLookupControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAllFunctions**](FunctionsLookupControllerApi.md#getAllFunctions) | **GET** /api/admin/FunctionsLookupController/getAllFunctions | 
[**getAllFunctionsTree**](FunctionsLookupControllerApi.md#getAllFunctionsTree) | **GET** /api/admin/FunctionsLookupController/getAllFunctionsTree | 
[**getAllLocalFunctions**](FunctionsLookupControllerApi.md#getAllLocalFunctions) | **GET** /api/admin/FunctionsLookupController/getAllLocalFunctions | 
[**getAllLocalFunctionsTree**](FunctionsLookupControllerApi.md#getAllLocalFunctionsTree) | **GET** /api/admin/FunctionsLookupController/getAllLocalFunctionsTree | 

<a name="getAllFunctions"></a>
# **getAllFunctions**
> Object getAllFunctions()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.FunctionsLookupControllerApi();
apiInstance.getAllFunctions().then((data) => {
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

<a name="getAllFunctionsTree"></a>
# **getAllFunctionsTree**
> Object getAllFunctionsTree(opts)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.FunctionsLookupControllerApi();
let opts = { 
  'ragContextFunctions': null // Object | 
};
apiInstance.getAllFunctionsTree(opts).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ragContextFunctions** | [**Object**](.md)|  | [optional] 

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAllLocalFunctions"></a>
# **getAllLocalFunctions**
> Object getAllLocalFunctions()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.FunctionsLookupControllerApi();
apiInstance.getAllLocalFunctions().then((data) => {
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

<a name="getAllLocalFunctionsTree"></a>
# **getAllLocalFunctionsTree**
> Object getAllLocalFunctionsTree(opts)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.FunctionsLookupControllerApi();
let opts = { 
  'ragContextFunctions': null // Object | 
};
apiInstance.getAllLocalFunctionsTree(opts).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ragContextFunctions** | [**Object**](.md)|  | [optional] 

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

