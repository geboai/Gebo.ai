# GeboAiClient.GoogleWorkspaceAccessHandshakeControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**googleWorkspaceRedirect**](GoogleWorkspaceAccessHandshakeControllerApi.md#googleWorkspaceRedirect) | **GET** /oauth2/google-workspace-redirect | 
[**startWorkspaceAccess**](GoogleWorkspaceAccessHandshakeControllerApi.md#startWorkspaceAccess) | **GET** /oauth2/start-workspace-access-go | 
[**tryGoogleWorkspaceAccess**](GoogleWorkspaceAccessHandshakeControllerApi.md#tryGoogleWorkspaceAccess) | **POST** /api/users/start-workspace-access | 

<a name="googleWorkspaceRedirect"></a>
# **googleWorkspaceRedirect**
> googleWorkspaceRedirect()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleWorkspaceAccessHandshakeControllerApi();
apiInstance.googleWorkspaceRedirect().then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="startWorkspaceAccess"></a>
# **startWorkspaceAccess**
> startWorkspaceAccess()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleWorkspaceAccessHandshakeControllerApi();
apiInstance.startWorkspaceAccess().then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="tryGoogleWorkspaceAccess"></a>
# **tryGoogleWorkspaceAccess**
> StartGooglWorkspaceAccessRespose tryGoogleWorkspaceAccess(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleWorkspaceAccessHandshakeControllerApi();
let body = new GeboAiClient.StartGooglWorkspaceAccessRequest(); // StartGooglWorkspaceAccessRequest | 

apiInstance.tryGoogleWorkspaceAccess(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**StartGooglWorkspaceAccessRequest**](StartGooglWorkspaceAccessRequest.md)|  | 

### Return type

[**StartGooglWorkspaceAccessRespose**](StartGooglWorkspaceAccessRespose.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

