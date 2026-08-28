# GeboAiClient.FunctionsLookupControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAllFunctions**](FunctionsLookupControllerApi.md#getAllFunctions) | **GET** /api/admin/FunctionsLookupController/getAllFunctions | 
[**getAllFunctionsTree**](FunctionsLookupControllerApi.md#getAllFunctionsTree) | **GET** /api/admin/FunctionsLookupController/getAllFunctionsTree | 
[**getAllLocalFunctions**](FunctionsLookupControllerApi.md#getAllLocalFunctions) | **GET** /api/admin/FunctionsLookupController/getAllLocalFunctions | 
[**getAllLocalFunctionsTree**](FunctionsLookupControllerApi.md#getAllLocalFunctionsTree) | **GET** /api/admin/FunctionsLookupController/getAllLocalFunctionsTree | 

<a name="getAllFunctions"></a>
# **getAllFunctions**
> [GLookupEntry] getAllFunctions()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FunctionsLookupControllerApi();
apiInstance.getAllFunctions().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GLookupEntry]**](GLookupEntry.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAllFunctionsTree"></a>
# **getAllFunctionsTree**
> [ToolCategoriesTree] getAllFunctionsTree(opts)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FunctionsLookupControllerApi();
let opts = { 
  'ragContextFunctions': true // Boolean | 
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
 **ragContextFunctions** | **Boolean**|  | [optional] 

### Return type

[**[ToolCategoriesTree]**](ToolCategoriesTree.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAllLocalFunctions"></a>
# **getAllLocalFunctions**
> [GLookupEntry] getAllLocalFunctions()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FunctionsLookupControllerApi();
apiInstance.getAllLocalFunctions().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GLookupEntry]**](GLookupEntry.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAllLocalFunctionsTree"></a>
# **getAllLocalFunctionsTree**
> [ToolCategoriesTree] getAllLocalFunctionsTree(opts)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FunctionsLookupControllerApi();
let opts = { 
  'ragContextFunctions': true // Boolean | 
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
 **ragContextFunctions** | **Boolean**|  | [optional] 

### Return type

[**[ToolCategoriesTree]**](ToolCategoriesTree.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

