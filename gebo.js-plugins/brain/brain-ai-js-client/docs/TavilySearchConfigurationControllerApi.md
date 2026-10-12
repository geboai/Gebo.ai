# BrainClient.TavilySearchConfigurationControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGTavilySearchApiCredentials**](TavilySearchConfigurationControllerApi.md#deleteGTavilySearchApiCredentials) | **POST** /api/admin/TavilySearchConfigurationController/deleteGTavilySearchApiCredentials | 
[**fastInsertTavilySearchApiCredentials**](TavilySearchConfigurationControllerApi.md#fastInsertTavilySearchApiCredentials) | **POST** /api/admin/TavilySearchConfigurationController/fastInsertTavilySearchApiCredentials | 
[**getTavilySearchApiCredentials**](TavilySearchConfigurationControllerApi.md#getTavilySearchApiCredentials) | **GET** /api/admin/TavilySearchConfigurationController/getTavilySearchApiCredentials | 
[**getTavilySearchStatus**](TavilySearchConfigurationControllerApi.md#getTavilySearchStatus) | **GET** /api/admin/TavilySearchConfigurationController/getTavilySearchStatus | 
[**insertGTavilySearchApiCredentials**](TavilySearchConfigurationControllerApi.md#insertGTavilySearchApiCredentials) | **POST** /api/admin/TavilySearchConfigurationController/insertGTavilySearchApiCredentials | 
[**searchGTavilySearchApiCredentialsByCode**](TavilySearchConfigurationControllerApi.md#searchGTavilySearchApiCredentialsByCode) | **GET** /api/admin/TavilySearchConfigurationController/searchGTavilySearchApiCredentialsByCode | 
[**updateGTavilySearchApiCredentials**](TavilySearchConfigurationControllerApi.md#updateGTavilySearchApiCredentials) | **POST** /api/admin/TavilySearchConfigurationController/updateGTavilySearchApiCredentials | 

<a name="deleteGTavilySearchApiCredentials"></a>
# **deleteGTavilySearchApiCredentials**
> deleteGTavilySearchApiCredentials(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.TavilySearchConfigurationControllerApi();
let body = new BrainClient.GTavilySearchApiCredentials(); // GTavilySearchApiCredentials | 

apiInstance.deleteGTavilySearchApiCredentials(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GTavilySearchApiCredentials**](GTavilySearchApiCredentials.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="fastInsertTavilySearchApiCredentials"></a>
# **fastInsertTavilySearchApiCredentials**
> GTavilySearchApiCredentials fastInsertTavilySearchApiCredentials(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.TavilySearchConfigurationControllerApi();
let body = new BrainClient.TavilySearchConfig(); // TavilySearchConfig | 

apiInstance.fastInsertTavilySearchApiCredentials(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**TavilySearchConfig**](TavilySearchConfig.md)|  | 

### Return type

[**GTavilySearchApiCredentials**](GTavilySearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getTavilySearchApiCredentials"></a>
# **getTavilySearchApiCredentials**
> [GTavilySearchApiCredentials] getTavilySearchApiCredentials()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.TavilySearchConfigurationControllerApi();
apiInstance.getTavilySearchApiCredentials().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GTavilySearchApiCredentials]**](GTavilySearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getTavilySearchStatus"></a>
# **getTavilySearchStatus**
> ComponentSetupStatus getTavilySearchStatus()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.TavilySearchConfigurationControllerApi();
apiInstance.getTavilySearchStatus().then((data) => {
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

<a name="insertGTavilySearchApiCredentials"></a>
# **insertGTavilySearchApiCredentials**
> GTavilySearchApiCredentials insertGTavilySearchApiCredentials(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.TavilySearchConfigurationControllerApi();
let body = new BrainClient.GTavilySearchApiCredentials(); // GTavilySearchApiCredentials | 

apiInstance.insertGTavilySearchApiCredentials(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GTavilySearchApiCredentials**](GTavilySearchApiCredentials.md)|  | 

### Return type

[**GTavilySearchApiCredentials**](GTavilySearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="searchGTavilySearchApiCredentialsByCode"></a>
# **searchGTavilySearchApiCredentialsByCode**
> GTavilySearchApiCredentials searchGTavilySearchApiCredentialsByCode(code)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.TavilySearchConfigurationControllerApi();
let code = "code_example"; // String | 

apiInstance.searchGTavilySearchApiCredentialsByCode(code).then((data) => {
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

[**GTavilySearchApiCredentials**](GTavilySearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateGTavilySearchApiCredentials"></a>
# **updateGTavilySearchApiCredentials**
> GTavilySearchApiCredentials updateGTavilySearchApiCredentials(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.TavilySearchConfigurationControllerApi();
let body = new BrainClient.GTavilySearchApiCredentials(); // GTavilySearchApiCredentials | 

apiInstance.updateGTavilySearchApiCredentials(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GTavilySearchApiCredentials**](GTavilySearchApiCredentials.md)|  | 

### Return type

[**GTavilySearchApiCredentials**](GTavilySearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

