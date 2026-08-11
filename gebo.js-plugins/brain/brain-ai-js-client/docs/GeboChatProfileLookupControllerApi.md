# BrainClient.GeboChatProfileLookupControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**findChatProfileConfigurationLookupByCode**](GeboChatProfileLookupControllerApi.md#findChatProfileConfigurationLookupByCode) | **GET** /api/users/GeboChatProfileLookupController/findChatProfileConfigurationLookupByCode | 
[**getAllChatProfileConfigurationLoookup**](GeboChatProfileLookupControllerApi.md#getAllChatProfileConfigurationLoookup) | **POST** /api/users/GeboChatProfileLookupController/getAllChatProfileConfigurationLoookup | 
[**getChatProfileConfigurationLookupByQbe**](GeboChatProfileLookupControllerApi.md#getChatProfileConfigurationLookupByQbe) | **POST** /api/users/GeboChatProfileLookupController/getChatProfileConfigurationLookupByQbe | 

<a name="findChatProfileConfigurationLookupByCode"></a>
# **findChatProfileConfigurationLookupByCode**
> GChatProfileConfiguration findChatProfileConfigurationLookupByCode(code)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboChatProfileLookupControllerApi();
let code = null; // Object | 

apiInstance.findChatProfileConfigurationLookupByCode(code).then((data) => {
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

[**GChatProfileConfiguration**](GChatProfileConfiguration.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAllChatProfileConfigurationLoookup"></a>
# **getAllChatProfileConfigurationLoookup**
> PageGLookupEntry getAllChatProfileConfigurationLoookup(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboChatProfileLookupControllerApi();
let body = new BrainClient.DataPage(); // DataPage | 

apiInstance.getAllChatProfileConfigurationLoookup(body).then((data) => {
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

[**PageGLookupEntry**](PageGLookupEntry.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getChatProfileConfigurationLookupByQbe"></a>
# **getChatProfileConfigurationLookupByQbe**
> PageGLookupEntry getChatProfileConfigurationLookupByQbe(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboChatProfileLookupControllerApi();
let body = new BrainClient.ChatProfileConfigurationLookupByQbeParam(); // ChatProfileConfigurationLookupByQbeParam | 

apiInstance.getChatProfileConfigurationLookupByQbe(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ChatProfileConfigurationLookupByQbeParam**](ChatProfileConfigurationLookupByQbeParam.md)|  | 

### Return type

[**PageGLookupEntry**](PageGLookupEntry.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

