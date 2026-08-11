# GeboAiClient.GeboDeepSearchAdminControllerApi

All URIs are relative to *http://localhost:12999*

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
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboDeepSearchAdminControllerApi();
let body = new GeboAiClient.DeepSearchConfig(); // DeepSearchConfig | 

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
> [GBaseObject] getConfigurableDataSources()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboDeepSearchAdminControllerApi();
apiInstance.getConfigurableDataSources().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GBaseObject]**](GBaseObject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="getDeepSeachConfigs"></a>
# **getDeepSeachConfigs**
> [DeepSearchConfig] getDeepSeachConfigs()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboDeepSearchAdminControllerApi();
apiInstance.getDeepSeachConfigs().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[DeepSearchConfig]**](DeepSearchConfig.md)

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
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboDeepSearchAdminControllerApi();
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
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboDeepSearchAdminControllerApi();
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
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboDeepSearchAdminControllerApi();
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
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboDeepSearchAdminControllerApi();
let body = new GeboAiClient.DeepSearchConfig(); // DeepSearchConfig | 

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
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboDeepSearchAdminControllerApi();
let body = new GeboAiClient.DeepSearchConfig(); // DeepSearchConfig | 

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

