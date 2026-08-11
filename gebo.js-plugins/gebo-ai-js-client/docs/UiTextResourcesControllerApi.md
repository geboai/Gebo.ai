# GeboAiClient.UiTextResourcesControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getI18n**](UiTextResourcesControllerApi.md#getI18n) | **GET** /public/UITextResourcesController | 
[**getUiTextResourcesModule**](UiTextResourcesControllerApi.md#getUiTextResourcesModule) | **GET** /public/UITextResourcesController/getUiTextResourcesModule | 
[**updateUIExistingTexts**](UiTextResourcesControllerApi.md#updateUIExistingTexts) | **POST** /public/UITextResourcesController/updateUIExistingTexts | 

<a name="getI18n"></a>
# **getI18n**
> &#x27;Blob&#x27; getI18n()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UiTextResourcesControllerApi();
apiInstance.getI18n().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

**&#x27;Blob&#x27;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="getUiTextResourcesModule"></a>
# **getUiTextResourcesModule**
> UiTextResourcesModule getUiTextResourcesModule()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UiTextResourcesControllerApi();
apiInstance.getUiTextResourcesModule().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**UiTextResourcesModule**](UiTextResourcesModule.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateUIExistingTexts"></a>
# **updateUIExistingTexts**
> updateUIExistingTexts(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UiTextResourcesControllerApi();
let body = [new GeboAiClient.UIExistingText()]; // [UIExistingText] | 

apiInstance.updateUIExistingTexts(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**[UIExistingText]**](UIExistingText.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

