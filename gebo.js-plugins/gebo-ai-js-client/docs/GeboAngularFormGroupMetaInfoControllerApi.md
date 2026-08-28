# GeboAiClient.GeboAngularFormGroupMetaInfoControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**checkDeletableByGObjectRef**](GeboAngularFormGroupMetaInfoControllerApi.md#checkDeletableByGObjectRef) | **POST** /api/admin/AngularFormGroupController/checkDeletableByGObjectRef | 
[**checkDeletableBySimpleObjectRef**](GeboAngularFormGroupMetaInfoControllerApi.md#checkDeletableBySimpleObjectRef) | **POST** /api/admin/AngularFormGroupController/checkDeletableBySimpleGObjectRef | 
[**getFormGroupsMetaInfos**](GeboAngularFormGroupMetaInfoControllerApi.md#getFormGroupsMetaInfos) | **GET** /api/admin/AngularFormGroupController/getFormGroupsMetaInfos | 

<a name="checkDeletableByGObjectRef"></a>
# **checkDeletableByGObjectRef**
> DeletableStatus checkDeletableByGObjectRef(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAngularFormGroupMetaInfoControllerApi();
let body = new GeboAiClient.GObjectRef(); // GObjectRef | 

apiInstance.checkDeletableByGObjectRef(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GObjectRef**](GObjectRef.md)|  | 

### Return type

[**DeletableStatus**](DeletableStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="checkDeletableBySimpleObjectRef"></a>
# **checkDeletableBySimpleObjectRef**
> DeletableStatus checkDeletableBySimpleObjectRef(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAngularFormGroupMetaInfoControllerApi();
let body = new GeboAiClient.SimpleGObjectRef(); // SimpleGObjectRef | 

apiInstance.checkDeletableBySimpleObjectRef(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SimpleGObjectRef**](SimpleGObjectRef.md)|  | 

### Return type

[**DeletableStatus**](DeletableStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getFormGroupsMetaInfos"></a>
# **getFormGroupsMetaInfos**
> [FormGroupMetaInfo] getFormGroupsMetaInfos()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAngularFormGroupMetaInfoControllerApi();
apiInstance.getFormGroupsMetaInfos().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[FormGroupMetaInfo]**](FormGroupMetaInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

