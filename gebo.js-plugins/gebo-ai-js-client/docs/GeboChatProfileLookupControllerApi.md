# GeboAiClient.GeboChatProfileLookupControllerApi

All URIs are relative to *http://localhost:12999*

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
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboChatProfileLookupControllerApi();
let code = "code_example"; // String | 

apiInstance.findChatProfileConfigurationLookupByCode(code).then((data) => {
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

<a name="getAllChatProfileConfigurationLoookup"></a>
# **getAllChatProfileConfigurationLoookup**
> PagedModelGLookupEntry getAllChatProfileConfigurationLoookup(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboChatProfileLookupControllerApi();
let body = new GeboAiClient.DataPage(); // DataPage | 

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

[**PagedModelGLookupEntry**](PagedModelGLookupEntry.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getChatProfileConfigurationLookupByQbe"></a>
# **getChatProfileConfigurationLookupByQbe**
> PagedModelGLookupEntry getChatProfileConfigurationLookupByQbe(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboChatProfileLookupControllerApi();
let body = new GeboAiClient.ChatProfileConfigurationLookupByQbeParam(); // ChatProfileConfigurationLookupByQbeParam | 

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

[**PagedModelGLookupEntry**](PagedModelGLookupEntry.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

