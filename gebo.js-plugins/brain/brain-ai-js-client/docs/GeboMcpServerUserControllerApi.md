# BrainClient.GeboMcpServerUserControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**findAccessibleMcpServerByCode**](GeboMcpServerUserControllerApi.md#findAccessibleMcpServerByCode) | **GET** /api/user/GeboMCPServerUserController/findAccessibleMcpServerByCode | 
[**getUsersCanAccessMcpServersList**](GeboMcpServerUserControllerApi.md#getUsersCanAccessMcpServersList) | **GET** /api/user/GeboMCPServerUserController/getUsersCanAccessMcpServersList | 
[**listAccessibleMcpServers**](GeboMcpServerUserControllerApi.md#listAccessibleMcpServers) | **GET** /api/user/GeboMCPServerUserController/listAccessibleMcpServers | 

<a name="findAccessibleMcpServerByCode"></a>
# **findAccessibleMcpServerByCode**
> UserAccessibleMcpServerView findAccessibleMcpServerByCode(code)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboMcpServerUserControllerApi();
let code = null; // Object | 

apiInstance.findAccessibleMcpServerByCode(code).then((data) => {
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

[**UserAccessibleMcpServerView**](UserAccessibleMcpServerView.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getUsersCanAccessMcpServersList"></a>
# **getUsersCanAccessMcpServersList**
> Object getUsersCanAccessMcpServersList()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboMcpServerUserControllerApi();
apiInstance.getUsersCanAccessMcpServersList().then((data) => {
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

<a name="listAccessibleMcpServers"></a>
# **listAccessibleMcpServers**
> Object listAccessibleMcpServers()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboMcpServerUserControllerApi();
apiInstance.listAccessibleMcpServers().then((data) => {
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

