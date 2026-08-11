# GeboAiClient.UsersAdminControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**changeUserPassword**](UsersAdminControllerApi.md#changeUserPassword) | **POST** /api/admin/UsersAdminController/changeUserPassword | 
[**deleteGroup**](UsersAdminControllerApi.md#deleteGroup) | **POST** /api/admin/UsersAdminController/deleteGroup | 
[**deleteUser**](UsersAdminControllerApi.md#deleteUser) | **POST** /api/admin/UsersAdminController/deleteUser | 
[**findGroupByCode**](UsersAdminControllerApi.md#findGroupByCode) | **GET** /api/admin/UsersAdminController/findGroupByCode | 
[**findUserByQbe**](UsersAdminControllerApi.md#findUserByQbe) | **POST** /api/admin/UsersAdminController/findUserByQbe | 
[**findUserByUsername**](UsersAdminControllerApi.md#findUserByUsername) | **GET** /api/admin/UsersAdminController/findUserByUsername | 
[**findUsersGroupByQbe**](UsersAdminControllerApi.md#findUsersGroupByQbe) | **POST** /api/admin/UsersAdminController/findUsersGroupByQbe | 
[**getAllGroups**](UsersAdminControllerApi.md#getAllGroups) | **GET** /api/admin/UsersAdminController/getAllGroups | 
[**getAllUsers**](UsersAdminControllerApi.md#getAllUsers) | **GET** /api/admin/UsersAdminController/getAllUsers | 
[**insertGroup**](UsersAdminControllerApi.md#insertGroup) | **POST** /api/admin/UsersAdminController/insertGroup | 
[**insertUser**](UsersAdminControllerApi.md#insertUser) | **POST** /api/admin/UsersAdminController/insertUser | 
[**updateGroup**](UsersAdminControllerApi.md#updateGroup) | **POST** /api/admin/UsersAdminController/updateGroup | 
[**updateUser**](UsersAdminControllerApi.md#updateUser) | **POST** /api/admin/UsersAdminController/updateUser | 

<a name="changeUserPassword"></a>
# **changeUserPassword**
> GUserMessage changeUserPassword(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UsersAdminControllerApi();
let body = new GeboAiClient.ChangeUsernamePasswordData(); // ChangeUsernamePasswordData | 

apiInstance.changeUserPassword(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ChangeUsernamePasswordData**](ChangeUsernamePasswordData.md)|  | 

### Return type

[**GUserMessage**](GUserMessage.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteGroup"></a>
# **deleteGroup**
> deleteGroup(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UsersAdminControllerApi();
let body = new GeboAiClient.UsersGroup(); // UsersGroup | 

apiInstance.deleteGroup(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UsersGroup**](UsersGroup.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="deleteUser"></a>
# **deleteUser**
> deleteUser(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UsersAdminControllerApi();
let body = new GeboAiClient.EditableUser(); // EditableUser | 

apiInstance.deleteUser(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**EditableUser**](EditableUser.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="findGroupByCode"></a>
# **findGroupByCode**
> UsersGroup findGroupByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UsersAdminControllerApi();
let code = "code_example"; // String | 

apiInstance.findGroupByCode(code).then((data) => {
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

[**UsersGroup**](UsersGroup.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findUserByQbe"></a>
# **findUserByQbe**
> PagedModelUserInfos findUserByQbe(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UsersAdminControllerApi();
let body = new GeboAiClient.FindUserByQbeParam(); // FindUserByQbeParam | 

apiInstance.findUserByQbe(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**FindUserByQbeParam**](FindUserByQbeParam.md)|  | 

### Return type

[**PagedModelUserInfos**](PagedModelUserInfos.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findUserByUsername"></a>
# **findUserByUsername**
> EditableUser findUserByUsername(email)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UsersAdminControllerApi();
let email = "email_example"; // String | 

apiInstance.findUserByUsername(email).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **email** | **String**|  | 

### Return type

[**EditableUser**](EditableUser.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findUsersGroupByQbe"></a>
# **findUsersGroupByQbe**
> PagedModelUsersGroup findUsersGroupByQbe(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UsersAdminControllerApi();
let body = new GeboAiClient.FindUsersGroupParam(); // FindUsersGroupParam | 

apiInstance.findUsersGroupByQbe(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**FindUsersGroupParam**](FindUsersGroupParam.md)|  | 

### Return type

[**PagedModelUsersGroup**](PagedModelUsersGroup.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getAllGroups"></a>
# **getAllGroups**
> [UsersGroup] getAllGroups()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UsersAdminControllerApi();
apiInstance.getAllGroups().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[UsersGroup]**](UsersGroup.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAllUsers"></a>
# **getAllUsers**
> [UserInfos] getAllUsers()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UsersAdminControllerApi();
apiInstance.getAllUsers().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[UserInfos]**](UserInfos.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insertGroup"></a>
# **insertGroup**
> UsersGroup insertGroup(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UsersAdminControllerApi();
let body = new GeboAiClient.UsersGroup(); // UsersGroup | 

apiInstance.insertGroup(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UsersGroup**](UsersGroup.md)|  | 

### Return type

[**UsersGroup**](UsersGroup.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertUser"></a>
# **insertUser**
> EditableUser insertUser(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UsersAdminControllerApi();
let body = new GeboAiClient.InsertUserParam(); // InsertUserParam | 

apiInstance.insertUser(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**InsertUserParam**](InsertUserParam.md)|  | 

### Return type

[**EditableUser**](EditableUser.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateGroup"></a>
# **updateGroup**
> UsersGroup updateGroup(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UsersAdminControllerApi();
let body = new GeboAiClient.UsersGroup(); // UsersGroup | 

apiInstance.updateGroup(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UsersGroup**](UsersGroup.md)|  | 

### Return type

[**UsersGroup**](UsersGroup.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateUser"></a>
# **updateUser**
> EditableUser updateUser(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UsersAdminControllerApi();
let body = new GeboAiClient.EditableUser(); // EditableUser | 

apiInstance.updateUser(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**EditableUser**](EditableUser.md)|  | 

### Return type

[**EditableUser**](EditableUser.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

