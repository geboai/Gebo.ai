# BrainClient.GeboFastLlmsSetupControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createLLMByAutoconfigure**](GeboFastLlmsSetupControllerApi.md#createLLMByAutoconfigure) | **POST** /api/admin/GeboFastLLMSSetupController/createLLMByAutoconfigure | 
[**createLLMCredentials**](GeboFastLlmsSetupControllerApi.md#createLLMCredentials) | **POST** /api/admin/GeboFastLLMSSetupController/createLLMCredentials | 
[**createLLMS**](GeboFastLlmsSetupControllerApi.md#createLLMS) | **POST** /api/admin/GeboFastLLMSSetupController/createLLMS | 
[**getActualLLMSConfiguration**](GeboFastLlmsSetupControllerApi.md#getActualLLMSConfiguration) | **GET** /api/admin/GeboFastLLMSSetupController/getActualLLMSConfiguration | 
[**getLLMSSetupStatus**](GeboFastLlmsSetupControllerApi.md#getLLMSSetupStatus) | **GET** /api/admin/GeboFastLLMSSetupController/getLLMSSetupStatus | 
[**verifyCredentialsAndDownloadModels**](GeboFastLlmsSetupControllerApi.md#verifyCredentialsAndDownloadModels) | **POST** /api/admin/GeboFastLLMSSetupController/verifyCredentialsAndDownloadModels | 
[**verifyVendorCredentialsAndDownloadModels**](GeboFastLlmsSetupControllerApi.md#verifyVendorCredentialsAndDownloadModels) | **POST** /api/admin/GeboFastLLMSSetupController/verifyVendorCredentialsAndDownloadModels | 

<a name="createLLMByAutoconfigure"></a>
# **createLLMByAutoconfigure**
> OperationStatusListGBaseModelConfig createLLMByAutoconfigure(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboFastLlmsSetupControllerApi();
let body = new BrainClient.LLMAutoconfigureCreationData(); // LLMAutoconfigureCreationData | 

apiInstance.createLLMByAutoconfigure(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**LLMAutoconfigureCreationData**](LLMAutoconfigureCreationData.md)|  | 

### Return type

[**OperationStatusListGBaseModelConfig**](OperationStatusListGBaseModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createLLMCredentials"></a>
# **createLLMCredentials**
> OperationStatusSecretInfo createLLMCredentials(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboFastLlmsSetupControllerApi();
let body = new BrainClient.LLMCredentialsCreationData(); // LLMCredentialsCreationData | 

apiInstance.createLLMCredentials(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**LLMCredentialsCreationData**](LLMCredentialsCreationData.md)|  | 

### Return type

[**OperationStatusSecretInfo**](OperationStatusSecretInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createLLMS"></a>
# **createLLMS**
> OperationStatusLLMSModelsCreationResult createLLMS(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboFastLlmsSetupControllerApi();
let body = null; // Object | 

apiInstance.createLLMS(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  | 

### Return type

[**OperationStatusLLMSModelsCreationResult**](OperationStatusLLMSModelsCreationResult.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getActualLLMSConfiguration"></a>
# **getActualLLMSConfiguration**
> LLMSSetupConfigurationData getActualLLMSConfiguration()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboFastLlmsSetupControllerApi();
apiInstance.getActualLLMSConfiguration().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**LLMSSetupConfigurationData**](LLMSSetupConfigurationData.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getLLMSSetupStatus"></a>
# **getLLMSSetupStatus**
> ComponentLLMSStatus getLLMSSetupStatus()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboFastLlmsSetupControllerApi();
apiInstance.getLLMSSetupStatus().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**ComponentLLMSStatus**](ComponentLLMSStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="verifyCredentialsAndDownloadModels"></a>
# **verifyCredentialsAndDownloadModels**
> OperationStatusListGBaseModelChoice verifyCredentialsAndDownloadModels(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboFastLlmsSetupControllerApi();
let body = new BrainClient.LLMModelsLookupParameter(); // LLMModelsLookupParameter | 

apiInstance.verifyCredentialsAndDownloadModels(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**LLMModelsLookupParameter**](LLMModelsLookupParameter.md)|  | 

### Return type

[**OperationStatusListGBaseModelChoice**](OperationStatusListGBaseModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="verifyVendorCredentialsAndDownloadModels"></a>
# **verifyVendorCredentialsAndDownloadModels**
> OperationStatusListGBaseModelChoice verifyVendorCredentialsAndDownloadModels(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboFastLlmsSetupControllerApi();
let body = new BrainClient.LLMCredentialsVerificationData(); // LLMCredentialsVerificationData | 

apiInstance.verifyVendorCredentialsAndDownloadModels(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**LLMCredentialsVerificationData**](LLMCredentialsVerificationData.md)|  | 

### Return type

[**OperationStatusListGBaseModelChoice**](OperationStatusListGBaseModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

