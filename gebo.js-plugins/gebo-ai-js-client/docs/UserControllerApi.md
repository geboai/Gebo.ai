# GeboAiClient.UserControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**changePassword**](UserControllerApi.md#changePassword) | **POST** /api/users/ActualUserController/changePassword | 
[**getCurrentUser**](UserControllerApi.md#getCurrentUser) | **GET** /api/users/ActualUserController/me | 
[**getMyGroups**](UserControllerApi.md#getMyGroups) | **GET** /api/users/ActualUserController/getMyGroups | 

<a name="changePassword"></a>
# **changePassword**
> ChangePasswordResponse changePassword(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UserControllerApi();
let body = new GeboAiClient.ChangePasswordParam(); // ChangePasswordParam | 

apiInstance.changePassword(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ChangePasswordParam**](ChangePasswordParam.md)|  | 

### Return type

[**ChangePasswordResponse**](ChangePasswordResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getCurrentUser"></a>
# **getCurrentUser**
> UserInfo getCurrentUser()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UserControllerApi();
apiInstance.getCurrentUser().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**UserInfo**](UserInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getMyGroups"></a>
# **getMyGroups**
> [GroupInfo] getMyGroups()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UserControllerApi();
apiInstance.getMyGroups().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GroupInfo]**](GroupInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

