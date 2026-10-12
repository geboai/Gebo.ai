# BrainClient.SerpapiSearchConfigurationControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGSerpapiSearchApiCredentials**](SerpapiSearchConfigurationControllerApi.md#deleteGSerpapiSearchApiCredentials) | **POST** /api/admin/SerpapiSearchConfigurationController/deleteGSerpapiSearchApiCredentials | 
[**fastInsertSerpapiSearchApiCredentials**](SerpapiSearchConfigurationControllerApi.md#fastInsertSerpapiSearchApiCredentials) | **POST** /api/admin/SerpapiSearchConfigurationController/fastInsertSerpapiSearchApiCredentials | 
[**getSerpapiSearchApiCredentials**](SerpapiSearchConfigurationControllerApi.md#getSerpapiSearchApiCredentials) | **GET** /api/admin/SerpapiSearchConfigurationController/getSerpapiSearchApiCredentials | 
[**getSerpapiSearchStatus**](SerpapiSearchConfigurationControllerApi.md#getSerpapiSearchStatus) | **GET** /api/admin/SerpapiSearchConfigurationController/getSerpapiSearchStatus | 
[**insertGSerpapiSearchApiCredentials**](SerpapiSearchConfigurationControllerApi.md#insertGSerpapiSearchApiCredentials) | **POST** /api/admin/SerpapiSearchConfigurationController/insertGSerpapiSearchApiCredentials | 
[**searchGSerpapiSearchApiCredentialsByCode**](SerpapiSearchConfigurationControllerApi.md#searchGSerpapiSearchApiCredentialsByCode) | **GET** /api/admin/SerpapiSearchConfigurationController/searchGSerpapiSearchApiCredentialsByCode | 
[**updateGSerpapiSearchApiCredentials**](SerpapiSearchConfigurationControllerApi.md#updateGSerpapiSearchApiCredentials) | **POST** /api/admin/SerpapiSearchConfigurationController/updateGSerpapiSearchApiCredentials | 

<a name="deleteGSerpapiSearchApiCredentials"></a>
# **deleteGSerpapiSearchApiCredentials**
> deleteGSerpapiSearchApiCredentials(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.SerpapiSearchConfigurationControllerApi();
let body = new BrainClient.GSerpapiSearchApiCredentials(); // GSerpapiSearchApiCredentials | 

apiInstance.deleteGSerpapiSearchApiCredentials(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GSerpapiSearchApiCredentials**](GSerpapiSearchApiCredentials.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="fastInsertSerpapiSearchApiCredentials"></a>
# **fastInsertSerpapiSearchApiCredentials**
> GSerpapiSearchApiCredentials fastInsertSerpapiSearchApiCredentials(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.SerpapiSearchConfigurationControllerApi();
let body = new BrainClient.SerpapiSearchConfig(); // SerpapiSearchConfig | 

apiInstance.fastInsertSerpapiSearchApiCredentials(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SerpapiSearchConfig**](SerpapiSearchConfig.md)|  | 

### Return type

[**GSerpapiSearchApiCredentials**](GSerpapiSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getSerpapiSearchApiCredentials"></a>
# **getSerpapiSearchApiCredentials**
> [GSerpapiSearchApiCredentials] getSerpapiSearchApiCredentials()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.SerpapiSearchConfigurationControllerApi();
apiInstance.getSerpapiSearchApiCredentials().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GSerpapiSearchApiCredentials]**](GSerpapiSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSerpapiSearchStatus"></a>
# **getSerpapiSearchStatus**
> ComponentSetupStatus getSerpapiSearchStatus()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.SerpapiSearchConfigurationControllerApi();
apiInstance.getSerpapiSearchStatus().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**ComponentSetupStatus**](ComponentSetupStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insertGSerpapiSearchApiCredentials"></a>
# **insertGSerpapiSearchApiCredentials**
> GSerpapiSearchApiCredentials insertGSerpapiSearchApiCredentials(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.SerpapiSearchConfigurationControllerApi();
let body = new BrainClient.GSerpapiSearchApiCredentials(); // GSerpapiSearchApiCredentials | 

apiInstance.insertGSerpapiSearchApiCredentials(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GSerpapiSearchApiCredentials**](GSerpapiSearchApiCredentials.md)|  | 

### Return type

[**GSerpapiSearchApiCredentials**](GSerpapiSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="searchGSerpapiSearchApiCredentialsByCode"></a>
# **searchGSerpapiSearchApiCredentialsByCode**
> GSerpapiSearchApiCredentials searchGSerpapiSearchApiCredentialsByCode(code)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.SerpapiSearchConfigurationControllerApi();
let code = "code_example"; // String | 

apiInstance.searchGSerpapiSearchApiCredentialsByCode(code).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  | 

### Return type

[**GSerpapiSearchApiCredentials**](GSerpapiSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateGSerpapiSearchApiCredentials"></a>
# **updateGSerpapiSearchApiCredentials**
> GSerpapiSearchApiCredentials updateGSerpapiSearchApiCredentials(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.SerpapiSearchConfigurationControllerApi();
let body = new BrainClient.GSerpapiSearchApiCredentials(); // GSerpapiSearchApiCredentials | 

apiInstance.updateGSerpapiSearchApiCredentials(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GSerpapiSearchApiCredentials**](GSerpapiSearchApiCredentials.md)|  | 

### Return type

[**GSerpapiSearchApiCredentials**](GSerpapiSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

