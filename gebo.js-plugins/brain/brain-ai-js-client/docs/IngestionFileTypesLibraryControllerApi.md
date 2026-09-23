# BrainClient.IngestionFileTypesLibraryControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAllFileTypes**](IngestionFileTypesLibraryControllerApi.md#getAllFileTypes) | **GET** /api/users/IngestionFileTypesLibraryController/getAllFileTypes | 
[**getIngestionFileTypeByExtension**](IngestionFileTypesLibraryControllerApi.md#getIngestionFileTypeByExtension) | **GET** /api/users/IngestionFileTypesLibraryController/getIngestionFileTypeByExtension | 
[**getIngestionReadingModules**](IngestionFileTypesLibraryControllerApi.md#getIngestionReadingModules) | **GET** /api/users/IngestionFileTypesLibraryController/getIngestionReadingModules | 

<a name="getAllFileTypes"></a>
# **getAllFileTypes**
> [IngestionFileType] getAllFileTypes()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.IngestionFileTypesLibraryControllerApi();
apiInstance.getAllFileTypes().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[IngestionFileType]**](IngestionFileType.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getIngestionFileTypeByExtension"></a>
# **getIngestionFileTypeByExtension**
> IngestionFileType getIngestionFileTypeByExtension(extension)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.IngestionFileTypesLibraryControllerApi();
let extension = "extension_example"; // String | 

apiInstance.getIngestionFileTypeByExtension(extension).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **extension** | **String**|  | 

### Return type

[**IngestionFileType**](IngestionFileType.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getIngestionReadingModules"></a>
# **getIngestionReadingModules**
> [IngestionHandlerConfig] getIngestionReadingModules()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.IngestionFileTypesLibraryControllerApi();
apiInstance.getIngestionReadingModules().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[IngestionHandlerConfig]**](IngestionHandlerConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

