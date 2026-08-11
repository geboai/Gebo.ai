# GeboAiClient.CompanySystemsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getContentSystem**](CompanySystemsControllerApi.md#getContentSystem) | **GET** /api/admin/CompanySystemsController/getContentSystem | 
[**getContentSystemType**](CompanySystemsControllerApi.md#getContentSystemType) | **GET** /api/admin/CompanySystemsController/getContentSystemType | 
[**getContentSystemTypes**](CompanySystemsControllerApi.md#getContentSystemTypes) | **GET** /api/admin/CompanySystemsController/getContentSystemTypes() | 
[**getContentSystems**](CompanySystemsControllerApi.md#getContentSystems) | **GET** /api/admin/CompanySystemsController/getContentSystems | 
[**getProjectEndpoint**](CompanySystemsControllerApi.md#getProjectEndpoint) | **GET** /api/admin/CompanySystemsController/getProjectEndpoint | 
[**getProjectEndpointByObjectRef**](CompanySystemsControllerApi.md#getProjectEndpointByObjectRef) | **POST** /api/admin/CompanySystemsController/getProjectEndpointByObjectRef | 
[**getProjectEndpointSystemInfos**](CompanySystemsControllerApi.md#getProjectEndpointSystemInfos) | **POST** /api/admin/CompanySystemsController/getProjectEndpointSystemInfos | 

<a name="getContentSystem"></a>
# **getContentSystem**
> GContentManagementSystem getContentSystem(systemTypeCode, systemCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.CompanySystemsControllerApi();
let systemTypeCode = "systemTypeCode_example"; // String | 
let systemCode = "systemCode_example"; // String | 

apiInstance.getContentSystem(systemTypeCode, systemCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **systemTypeCode** | **String**|  | 
 **systemCode** | **String**|  | 

### Return type

[**GContentManagementSystem**](GContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getContentSystemType"></a>
# **getContentSystemType**
> GContentManagementSystemType getContentSystemType(systemTypeCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.CompanySystemsControllerApi();
let systemTypeCode = "systemTypeCode_example"; // String | 

apiInstance.getContentSystemType(systemTypeCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **systemTypeCode** | **String**|  | 

### Return type

[**GContentManagementSystemType**](GContentManagementSystemType.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getContentSystemTypes"></a>
# **getContentSystemTypes**
> [GContentManagementSystemType] getContentSystemTypes()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.CompanySystemsControllerApi();
apiInstance.getContentSystemTypes().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GContentManagementSystemType]**](GContentManagementSystemType.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getContentSystems"></a>
# **getContentSystems**
> [GContentManagementSystem] getContentSystems()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.CompanySystemsControllerApi();
apiInstance.getContentSystems().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GContentManagementSystem]**](GContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getProjectEndpoint"></a>
# **getProjectEndpoint**
> GProjectEndpoint getProjectEndpoint(systemTypeCode, systemCode, projectEndpointCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.CompanySystemsControllerApi();
let systemTypeCode = "systemTypeCode_example"; // String | 
let systemCode = "systemCode_example"; // String | 
let projectEndpointCode = "projectEndpointCode_example"; // String | 

apiInstance.getProjectEndpoint(systemTypeCode, systemCode, projectEndpointCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **systemTypeCode** | **String**|  | 
 **systemCode** | **String**|  | 
 **projectEndpointCode** | **String**|  | 

### Return type

[**GProjectEndpoint**](GProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getProjectEndpointByObjectRef"></a>
# **getProjectEndpointByObjectRef**
> GProjectEndpoint getProjectEndpointByObjectRef(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.CompanySystemsControllerApi();
let body = new GeboAiClient.GObjectRefGProjectEndpoint(); // GObjectRefGProjectEndpoint | 

apiInstance.getProjectEndpointByObjectRef(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GObjectRefGProjectEndpoint**](GObjectRefGProjectEndpoint.md)|  | 

### Return type

[**GProjectEndpoint**](GProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getProjectEndpointSystemInfos"></a>
# **getProjectEndpointSystemInfos**
> SystemInfos getProjectEndpointSystemInfos(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.CompanySystemsControllerApi();
let body = new GeboAiClient.GObjectRefGProjectEndpoint(); // GObjectRefGProjectEndpoint | 

apiInstance.getProjectEndpointSystemInfos(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GObjectRefGProjectEndpoint**](GObjectRefGProjectEndpoint.md)|  | 

### Return type

[**SystemInfos**](SystemInfos.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

