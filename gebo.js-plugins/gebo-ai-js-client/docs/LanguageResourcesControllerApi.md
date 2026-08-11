# GeboAiClient.LanguageResourcesControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAllResourcesByLanguage**](LanguageResourcesControllerApi.md#getAllResourcesByLanguage) | **GET** /api/users/LanguageResourcesController | 
[**getUIComponentLabels**](LanguageResourcesControllerApi.md#getUIComponentLabels) | **GET** /api/users/LanguageResourcesController/getUIComponentLabels | 
[**update**](LanguageResourcesControllerApi.md#update) | **POST** /api/users/LanguageResourcesController/update | 

<a name="getAllResourcesByLanguage"></a>
# **getAllResourcesByLanguage**
> [UIComponent] getAllResourcesByLanguage(langCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.LanguageResourcesControllerApi();
let langCode = "langCode_example"; // String | 

apiInstance.getAllResourcesByLanguage(langCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **langCode** | **String**|  | 

### Return type

[**[UIComponent]**](UIComponent.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getUIComponentLabels"></a>
# **getUIComponentLabels**
> UIComponent getUIComponentLabels(id, langCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.LanguageResourcesControllerApi();
let id = "id_example"; // String | 
let langCode = "langCode_example"; // String | 

apiInstance.getUIComponentLabels(id, langCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  | 
 **langCode** | **String**|  | 

### Return type

[**UIComponent**](UIComponent.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="update"></a>
# **update**
> UIComponent update(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.LanguageResourcesControllerApi();
let body = new GeboAiClient.UIComponent(); // UIComponent | 

apiInstance.update(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UIComponent**](UIComponent.md)|  | 

### Return type

[**UIComponent**](UIComponent.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

