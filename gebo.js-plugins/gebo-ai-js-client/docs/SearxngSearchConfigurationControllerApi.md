# GeboAiClient.SearxngSearchConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGSearxngSearchApiCredentials**](SearxngSearchConfigurationControllerApi.md#deleteGSearxngSearchApiCredentials) | **POST** /api/admin/SearxngSearchConfigurationController/deleteGSearxngSearchApiCredentials | 
[**fastInsertSearxngSearchApiCredentials**](SearxngSearchConfigurationControllerApi.md#fastInsertSearxngSearchApiCredentials) | **POST** /api/admin/SearxngSearchConfigurationController/fastInsertSearxngSearchApiCredentials | 
[**getSearxngSearchApiCredentials**](SearxngSearchConfigurationControllerApi.md#getSearxngSearchApiCredentials) | **GET** /api/admin/SearxngSearchConfigurationController/getSearxngSearchApiCredentials | 
[**getSearxngSearchStatus**](SearxngSearchConfigurationControllerApi.md#getSearxngSearchStatus) | **GET** /api/admin/SearxngSearchConfigurationController/getSearxngSearchStatus | 
[**insertGSearxngSearchApiCredentials**](SearxngSearchConfigurationControllerApi.md#insertGSearxngSearchApiCredentials) | **POST** /api/admin/SearxngSearchConfigurationController/insertGSearxngSearchApiCredentials | 
[**searchGSearxngSearchApiCredentialsByCode**](SearxngSearchConfigurationControllerApi.md#searchGSearxngSearchApiCredentialsByCode) | **GET** /api/admin/SearxngSearchConfigurationController/searchGSearxngSearchApiCredentialsByCode | 
[**updateGSearxngSearchApiCredentials**](SearxngSearchConfigurationControllerApi.md#updateGSearxngSearchApiCredentials) | **POST** /api/admin/SearxngSearchConfigurationController/updateGSearxngSearchApiCredentials | 

<a name="deleteGSearxngSearchApiCredentials"></a>
# **deleteGSearxngSearchApiCredentials**
> deleteGSearxngSearchApiCredentials(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SearxngSearchConfigurationControllerApi();
let body = new GeboAiClient.GSearxngSearchApiCredentials(); // GSearxngSearchApiCredentials | 

apiInstance.deleteGSearxngSearchApiCredentials(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GSearxngSearchApiCredentials**](GSearxngSearchApiCredentials.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="fastInsertSearxngSearchApiCredentials"></a>
# **fastInsertSearxngSearchApiCredentials**
> GSearxngSearchApiCredentials fastInsertSearxngSearchApiCredentials(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SearxngSearchConfigurationControllerApi();
let body = new GeboAiClient.SearxngSearchConfig(); // SearxngSearchConfig | 

apiInstance.fastInsertSearxngSearchApiCredentials(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SearxngSearchConfig**](SearxngSearchConfig.md)|  | 

### Return type

[**GSearxngSearchApiCredentials**](GSearxngSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getSearxngSearchApiCredentials"></a>
# **getSearxngSearchApiCredentials**
> [GSearxngSearchApiCredentials] getSearxngSearchApiCredentials()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SearxngSearchConfigurationControllerApi();
apiInstance.getSearxngSearchApiCredentials().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GSearxngSearchApiCredentials]**](GSearxngSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSearxngSearchStatus"></a>
# **getSearxngSearchStatus**
> ComponentSetupStatus getSearxngSearchStatus()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SearxngSearchConfigurationControllerApi();
apiInstance.getSearxngSearchStatus().then((data) => {
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

<a name="insertGSearxngSearchApiCredentials"></a>
# **insertGSearxngSearchApiCredentials**
> GSearxngSearchApiCredentials insertGSearxngSearchApiCredentials(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SearxngSearchConfigurationControllerApi();
let body = new GeboAiClient.GSearxngSearchApiCredentials(); // GSearxngSearchApiCredentials | 

apiInstance.insertGSearxngSearchApiCredentials(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GSearxngSearchApiCredentials**](GSearxngSearchApiCredentials.md)|  | 

### Return type

[**GSearxngSearchApiCredentials**](GSearxngSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="searchGSearxngSearchApiCredentialsByCode"></a>
# **searchGSearxngSearchApiCredentialsByCode**
> GSearxngSearchApiCredentials searchGSearxngSearchApiCredentialsByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SearxngSearchConfigurationControllerApi();
let code = "code_example"; // String | 

apiInstance.searchGSearxngSearchApiCredentialsByCode(code).then((data) => {
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

[**GSearxngSearchApiCredentials**](GSearxngSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateGSearxngSearchApiCredentials"></a>
# **updateGSearxngSearchApiCredentials**
> GSearxngSearchApiCredentials updateGSearxngSearchApiCredentials(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SearxngSearchConfigurationControllerApi();
let body = new GeboAiClient.GSearxngSearchApiCredentials(); // GSearxngSearchApiCredentials | 

apiInstance.updateGSearxngSearchApiCredentials(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GSearxngSearchApiCredentials**](GSearxngSearchApiCredentials.md)|  | 

### Return type

[**GSearxngSearchApiCredentials**](GSearxngSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

