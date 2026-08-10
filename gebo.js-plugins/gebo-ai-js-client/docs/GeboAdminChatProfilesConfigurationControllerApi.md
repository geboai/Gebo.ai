# GeboAiClient.GeboAdminChatProfilesConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteChatProfile**](GeboAdminChatProfilesConfigurationControllerApi.md#deleteChatProfile) | **POST** /api/admin/GeboAdminChatProfilesConfigurationController/deleteChatProfile | 
[**findChatProfileConfigurationByCode**](GeboAdminChatProfilesConfigurationControllerApi.md#findChatProfileConfigurationByCode) | **GET** /api/admin/GeboAdminChatProfilesConfigurationController/findChatProfileConfigurationByCode | 
[**getAllChatProfileConfiguration**](GeboAdminChatProfilesConfigurationControllerApi.md#getAllChatProfileConfiguration) | **POST** /api/admin/GeboAdminChatProfilesConfigurationController/getAllChatProfileConfiguration | 
[**getChatProfileConfigurationByQbe**](GeboAdminChatProfilesConfigurationControllerApi.md#getChatProfileConfigurationByQbe) | **POST** /api/admin/GeboAdminChatProfilesConfigurationController/getChatProfileConfigurationByQbe | 
[**insertChatProfile**](GeboAdminChatProfilesConfigurationControllerApi.md#insertChatProfile) | **POST** /api/admin/GeboAdminChatProfilesConfigurationController/insertChatProfile | 
[**updateChatProfile**](GeboAdminChatProfilesConfigurationControllerApi.md#updateChatProfile) | **POST** /api/admin/GeboAdminChatProfilesConfigurationController/updateChatProfile | 

<a name="deleteChatProfile"></a>
# **deleteChatProfile**
> deleteChatProfile(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAdminChatProfilesConfigurationControllerApi();
let body = new GeboAiClient.GChatProfileConfiguration(); // GChatProfileConfiguration | 

apiInstance.deleteChatProfile(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GChatProfileConfiguration**](GChatProfileConfiguration.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="findChatProfileConfigurationByCode"></a>
# **findChatProfileConfigurationByCode**
> GChatProfileConfiguration findChatProfileConfigurationByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAdminChatProfilesConfigurationControllerApi();
let code = "code_example"; // String | 

apiInstance.findChatProfileConfigurationByCode(code).then((data) => {
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

[**GChatProfileConfiguration**](GChatProfileConfiguration.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAllChatProfileConfiguration"></a>
# **getAllChatProfileConfiguration**
> PagedModelGChatProfileConfiguration getAllChatProfileConfiguration(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAdminChatProfilesConfigurationControllerApi();
let body = new GeboAiClient.DataPage(); // DataPage | 

apiInstance.getAllChatProfileConfiguration(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**DataPage**](DataPage.md)|  | 

### Return type

[**PagedModelGChatProfileConfiguration**](PagedModelGChatProfileConfiguration.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getChatProfileConfigurationByQbe"></a>
# **getChatProfileConfigurationByQbe**
> PagedModelGChatProfileConfiguration getChatProfileConfigurationByQbe(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAdminChatProfilesConfigurationControllerApi();
let body = new GeboAiClient.ChatProfileConfigurationByQbeParam(); // ChatProfileConfigurationByQbeParam | 

apiInstance.getChatProfileConfigurationByQbe(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ChatProfileConfigurationByQbeParam**](ChatProfileConfigurationByQbeParam.md)|  | 

### Return type

[**PagedModelGChatProfileConfiguration**](PagedModelGChatProfileConfiguration.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertChatProfile"></a>
# **insertChatProfile**
> GChatProfileConfiguration insertChatProfile(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAdminChatProfilesConfigurationControllerApi();
let body = new GeboAiClient.GChatProfileConfiguration(); // GChatProfileConfiguration | 

apiInstance.insertChatProfile(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GChatProfileConfiguration**](GChatProfileConfiguration.md)|  | 

### Return type

[**GChatProfileConfiguration**](GChatProfileConfiguration.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateChatProfile"></a>
# **updateChatProfile**
> GChatProfileConfiguration updateChatProfile(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAdminChatProfilesConfigurationControllerApi();
let body = new GeboAiClient.GChatProfileConfiguration(); // GChatProfileConfiguration | 

apiInstance.updateChatProfile(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GChatProfileConfiguration**](GChatProfileConfiguration.md)|  | 

### Return type

[**GChatProfileConfiguration**](GChatProfileConfiguration.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

