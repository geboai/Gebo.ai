# BrainClient.GoogleSearchConfigurationControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGGoogleSearchApiCredentials**](GoogleSearchConfigurationControllerApi.md#deleteGGoogleSearchApiCredentials) | **POST** /api/admin/GoogleSearchConfigurationController/deleteGGoogleSearchApiCredentials | 
[**fastInsertGoogleSearchApiCredentials**](GoogleSearchConfigurationControllerApi.md#fastInsertGoogleSearchApiCredentials) | **POST** /api/admin/GoogleSearchConfigurationController/fastInsertGoogleSearchApiCredentials | 
[**getGoogleSearchApiCredentials**](GoogleSearchConfigurationControllerApi.md#getGoogleSearchApiCredentials) | **GET** /api/admin/GoogleSearchConfigurationController/getGoogleSearchApiCredentials | 
[**getGoogleSearchStatus**](GoogleSearchConfigurationControllerApi.md#getGoogleSearchStatus) | **GET** /api/admin/GoogleSearchConfigurationController/getGoogleSearchStatus | 
[**insertGGoogleSearchApiCredentials**](GoogleSearchConfigurationControllerApi.md#insertGGoogleSearchApiCredentials) | **POST** /api/admin/GoogleSearchConfigurationController/insertGGoogleSearchApiCredentials | 
[**searchGGoogleSearchApiCredentialsByCode**](GoogleSearchConfigurationControllerApi.md#searchGGoogleSearchApiCredentialsByCode) | **GET** /api/admin/GoogleSearchConfigurationController/searchGGoogleSearchApiCredentialsByCode | 
[**updateGGoogleSearchApiCredentials**](GoogleSearchConfigurationControllerApi.md#updateGGoogleSearchApiCredentials) | **POST** /api/admin/GoogleSearchConfigurationController/updateGGoogleSearchApiCredentials | 

<a name="deleteGGoogleSearchApiCredentials"></a>
# **deleteGGoogleSearchApiCredentials**
> deleteGGoogleSearchApiCredentials(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GoogleSearchConfigurationControllerApi();
let body = new BrainClient.GGoogleSearchApiCredentials(); // GGoogleSearchApiCredentials | 

apiInstance.deleteGGoogleSearchApiCredentials(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleSearchApiCredentials**](GGoogleSearchApiCredentials.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="fastInsertGoogleSearchApiCredentials"></a>
# **fastInsertGoogleSearchApiCredentials**
> GGoogleSearchApiCredentials fastInsertGoogleSearchApiCredentials(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GoogleSearchConfigurationControllerApi();
let body = new BrainClient.GoogleSearchConfig(); // GoogleSearchConfig | 

apiInstance.fastInsertGoogleSearchApiCredentials(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GoogleSearchConfig**](GoogleSearchConfig.md)|  | 

### Return type

[**GGoogleSearchApiCredentials**](GGoogleSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getGoogleSearchApiCredentials"></a>
# **getGoogleSearchApiCredentials**
> Object getGoogleSearchApiCredentials()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GoogleSearchConfigurationControllerApi();
apiInstance.getGoogleSearchApiCredentials().then((data) => {
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

<a name="getGoogleSearchStatus"></a>
# **getGoogleSearchStatus**
> ComponentSetupStatus getGoogleSearchStatus()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GoogleSearchConfigurationControllerApi();
apiInstance.getGoogleSearchStatus().then((data) => {
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

<a name="insertGGoogleSearchApiCredentials"></a>
# **insertGGoogleSearchApiCredentials**
> GGoogleSearchApiCredentials insertGGoogleSearchApiCredentials(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GoogleSearchConfigurationControllerApi();
let body = new BrainClient.GGoogleSearchApiCredentials(); // GGoogleSearchApiCredentials | 

apiInstance.insertGGoogleSearchApiCredentials(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleSearchApiCredentials**](GGoogleSearchApiCredentials.md)|  | 

### Return type

[**GGoogleSearchApiCredentials**](GGoogleSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="searchGGoogleSearchApiCredentialsByCode"></a>
# **searchGGoogleSearchApiCredentialsByCode**
> GGoogleSearchApiCredentials searchGGoogleSearchApiCredentialsByCode(code)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GoogleSearchConfigurationControllerApi();
let code = null; // Object | 

apiInstance.searchGGoogleSearchApiCredentialsByCode(code).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | [**Object**](.md)|  | 

### Return type

[**GGoogleSearchApiCredentials**](GGoogleSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateGGoogleSearchApiCredentials"></a>
# **updateGGoogleSearchApiCredentials**
> GGoogleSearchApiCredentials updateGGoogleSearchApiCredentials(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GoogleSearchConfigurationControllerApi();
let body = new BrainClient.GGoogleSearchApiCredentials(); // GGoogleSearchApiCredentials | 

apiInstance.updateGGoogleSearchApiCredentials(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleSearchApiCredentials**](GGoogleSearchApiCredentials.md)|  | 

### Return type

[**GGoogleSearchApiCredentials**](GGoogleSearchApiCredentials.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

