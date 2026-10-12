# BrainClient.BraveSearchConfigurationControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGBraveSearchApiCredentials**](BraveSearchConfigurationControllerApi.md#deleteGBraveSearchApiCredentials) | **POST** /api/admin/BraveSearchConfigurationController/deleteGBraveSearchApiCredentials | 
[**fastInsertBraveSearchApiCredentials**](BraveSearchConfigurationControllerApi.md#fastInsertBraveSearchApiCredentials) | **POST** /api/admin/BraveSearchConfigurationController/fastInsertBraveSearchApiCredentials | 
[**getBraveSearchApiCredentials**](BraveSearchConfigurationControllerApi.md#getBraveSearchApiCredentials) | **GET** /api/admin/BraveSearchConfigurationController/getBraveSearchApiCredentials | 
[**getBraveSearchStatus**](BraveSearchConfigurationControllerApi.md#getBraveSearchStatus) | **GET** /api/admin/BraveSearchConfigurationController/getBraveSearchStatus | 
[**insertGBraveSearchApiCredentials**](BraveSearchConfigurationControllerApi.md#insertGBraveSearchApiCredentials) | **POST** /api/admin/BraveSearchConfigurationController/insertGBraveSearchApiCredentials | 
[**searchGBraveSearchApiCredentialsByCode**](BraveSearchConfigurationControllerApi.md#searchGBraveSearchApiCredentialsByCode) | **GET** /api/admin/BraveSearchConfigurationController/searchGBraveSearchApiCredentialsByCode | 
[**updateGBraveSearchApiCredentials**](BraveSearchConfigurationControllerApi.md#updateGBraveSearchApiCredentials) | **POST** /api/admin/BraveSearchConfigurationController/updateGBraveSearchApiCredentials | 

<a name="deleteGBraveSearchApiCredentials"></a>
# **deleteGBraveSearchApiCredentials**
> deleteGBraveSearchApiCredentials(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.BraveSearchConfigurationControllerApi();
let body = new BrainClient.GBraveSearchApiCredentials(); // GBraveSearchApiCredentials | 

apiInstance.deleteGBraveSearchApiCredentials(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBraveSearchApiCredentials**](GBraveSearchApiCredentials.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="fastInsertBraveSearchApiCredentials"></a>
# **fastInsertBraveSearchApiCredentials**
> GBraveSearchApiCredentials fastInsertBraveSearchApiCredentials(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.BraveSearchConfigurationControllerApi();
let body = new BrainClient.BraveSearchConfig(); // BraveSearchConfig | 

apiInstance.fastInsertBraveSearchApiCredentials(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**BraveSearchConfig**](BraveSearchConfig.md)|  | 

### Return type

[**GBraveSearchApiCredentials**](GBraveSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getBraveSearchApiCredentials"></a>
# **getBraveSearchApiCredentials**
> [GBraveSearchApiCredentials] getBraveSearchApiCredentials()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.BraveSearchConfigurationControllerApi();
apiInstance.getBraveSearchApiCredentials().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GBraveSearchApiCredentials]**](GBraveSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getBraveSearchStatus"></a>
# **getBraveSearchStatus**
> ComponentSetupStatus getBraveSearchStatus()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.BraveSearchConfigurationControllerApi();
apiInstance.getBraveSearchStatus().then((data) => {
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

<a name="insertGBraveSearchApiCredentials"></a>
# **insertGBraveSearchApiCredentials**
> GBraveSearchApiCredentials insertGBraveSearchApiCredentials(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.BraveSearchConfigurationControllerApi();
let body = new BrainClient.GBraveSearchApiCredentials(); // GBraveSearchApiCredentials | 

apiInstance.insertGBraveSearchApiCredentials(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBraveSearchApiCredentials**](GBraveSearchApiCredentials.md)|  | 

### Return type

[**GBraveSearchApiCredentials**](GBraveSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="searchGBraveSearchApiCredentialsByCode"></a>
# **searchGBraveSearchApiCredentialsByCode**
> GBraveSearchApiCredentials searchGBraveSearchApiCredentialsByCode(code)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.BraveSearchConfigurationControllerApi();
let code = "code_example"; // String | 

apiInstance.searchGBraveSearchApiCredentialsByCode(code).then((data) => {
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

[**GBraveSearchApiCredentials**](GBraveSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateGBraveSearchApiCredentials"></a>
# **updateGBraveSearchApiCredentials**
> GBraveSearchApiCredentials updateGBraveSearchApiCredentials(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.BraveSearchConfigurationControllerApi();
let body = new BrainClient.GBraveSearchApiCredentials(); // GBraveSearchApiCredentials | 

apiInstance.updateGBraveSearchApiCredentials(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBraveSearchApiCredentials**](GBraveSearchApiCredentials.md)|  | 

### Return type

[**GBraveSearchApiCredentials**](GBraveSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

