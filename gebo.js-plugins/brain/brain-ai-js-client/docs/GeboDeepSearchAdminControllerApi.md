# BrainClient.GeboDeepSearchAdminControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteDeepSearchConfig**](GeboDeepSearchAdminControllerApi.md#deleteDeepSearchConfig) | **DELETE** /api/admin/GeboDeepSearchAdminController/deleteDeepSearchConfig | 
[**getConfigurableDataSources**](GeboDeepSearchAdminControllerApi.md#getConfigurableDataSources) | **GET** /api/admin/GeboDeepSearchAdminController/getConfigurableDataSources | 
[**getDeepSeachConfigs**](GeboDeepSearchAdminControllerApi.md#getDeepSeachConfigs) | **GET** /api/admin/GeboDeepSearchAdminController/getDeepSeachConfigs | 
[**getDeepSearchDefaultConfig**](GeboDeepSearchAdminControllerApi.md#getDeepSearchDefaultConfig) | **GET** /api/admin/GeboDeepSearchAdminController/getDeepSearchDefaultConfig | 
[**getDeepSearchDefaultOrSystemConfig**](GeboDeepSearchAdminControllerApi.md#getDeepSearchDefaultOrSystemConfig) | **GET** /api/admin/GeboDeepSearchAdminController/getDeepSearchDefaultOrSystemConfig | 
[**getDeepSearchSystemConfig**](GeboDeepSearchAdminControllerApi.md#getDeepSearchSystemConfig) | **GET** /api/admin/GeboDeepSearchAdminController/getDeepSearchSystemConfig | 
[**insertDeepSearchConfig**](GeboDeepSearchAdminControllerApi.md#insertDeepSearchConfig) | **POST** /api/admin/GeboDeepSearchAdminController/insertDeepSearchConfig | 
[**updateDeepSearchConfig**](GeboDeepSearchAdminControllerApi.md#updateDeepSearchConfig) | **POST** /api/admin/GeboDeepSearchAdminController/updateDeepSearchConfig | 

<a name="deleteDeepSearchConfig"></a>
# **deleteDeepSearchConfig**
> deleteDeepSearchConfig(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboDeepSearchAdminControllerApi();
let body = new BrainClient.DeepSearchConfig(); // DeepSearchConfig | 

apiInstance.deleteDeepSearchConfig(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**DeepSearchConfig**](DeepSearchConfig.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="getConfigurableDataSources"></a>
# **getConfigurableDataSources**
> Object getConfigurableDataSources()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboDeepSearchAdminControllerApi();
apiInstance.getConfigurableDataSources().then((data) => {
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
 - **Accept**: */*

<a name="getDeepSeachConfigs"></a>
# **getDeepSeachConfigs**
> Object getDeepSeachConfigs()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboDeepSearchAdminControllerApi();
apiInstance.getDeepSeachConfigs().then((data) => {
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

<a name="getDeepSearchDefaultConfig"></a>
# **getDeepSearchDefaultConfig**
> DeepSearchConfig getDeepSearchDefaultConfig()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboDeepSearchAdminControllerApi();
apiInstance.getDeepSearchDefaultConfig().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**DeepSearchConfig**](DeepSearchConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getDeepSearchDefaultOrSystemConfig"></a>
# **getDeepSearchDefaultOrSystemConfig**
> DeepSearchConfig getDeepSearchDefaultOrSystemConfig()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboDeepSearchAdminControllerApi();
apiInstance.getDeepSearchDefaultOrSystemConfig().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**DeepSearchConfig**](DeepSearchConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getDeepSearchSystemConfig"></a>
# **getDeepSearchSystemConfig**
> DeepSearchConfig getDeepSearchSystemConfig()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboDeepSearchAdminControllerApi();
apiInstance.getDeepSearchSystemConfig().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**DeepSearchConfig**](DeepSearchConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insertDeepSearchConfig"></a>
# **insertDeepSearchConfig**
> DeepSearchConfig insertDeepSearchConfig(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboDeepSearchAdminControllerApi();
let body = new BrainClient.DeepSearchConfig(); // DeepSearchConfig | 

apiInstance.insertDeepSearchConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**DeepSearchConfig**](DeepSearchConfig.md)|  | 

### Return type

[**DeepSearchConfig**](DeepSearchConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateDeepSearchConfig"></a>
# **updateDeepSearchConfig**
> DeepSearchConfig updateDeepSearchConfig(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboDeepSearchAdminControllerApi();
let body = new BrainClient.DeepSearchConfig(); // DeepSearchConfig | 

apiInstance.updateDeepSearchConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**DeepSearchConfig**](DeepSearchConfig.md)|  | 

### Return type

[**DeepSearchConfig**](DeepSearchConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

