# GeboAiClient.ProjectsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteProject**](ProjectsControllerApi.md#deleteProject) | **POST** /api/admin/ProjectsController/deleteProject | 
[**findChildProjects**](ProjectsControllerApi.md#findChildProjects) | **GET** /api/admin/ProjectsController/findChildProjects | 
[**findOtherKnowledgeBaseIncludableProjects**](ProjectsControllerApi.md#findOtherKnowledgeBaseIncludableProjects) | **GET** /api/admin/ProjectsController/findOtherKnowledgeBaseIncludableProjects | 
[**findProjectByCode**](ProjectsControllerApi.md#findProjectByCode) | **GET** /api/admin/ProjectsController/findProjectByCode | 
[**findRootProjects**](ProjectsControllerApi.md#findRootProjects) | **GET** /api/admin/ProjectsController/findRootProjects | 
[**getChildDocuments**](ProjectsControllerApi.md#getChildDocuments) | **POST** /api/admin/ProjectsController/getChildDocuments | 
[**getChildFolders**](ProjectsControllerApi.md#getChildFolders) | **POST** /api/admin/ProjectsController/getChildFolders | 
[**getProjects**](ProjectsControllerApi.md#getProjects) | **GET** /api/admin/ProjectsController/getProjects | 
[**getRootDocuments**](ProjectsControllerApi.md#getRootDocuments) | **POST** /api/admin/ProjectsController/getRootDocuments | 
[**getRootFolders**](ProjectsControllerApi.md#getRootFolders) | **POST** /api/admin/ProjectsController/getRootFolders | 
[**insertProject**](ProjectsControllerApi.md#insertProject) | **POST** /api/admin/ProjectsController/insertProject | 
[**searchProjects**](ProjectsControllerApi.md#searchProjects) | **POST** /api/admin/ProjectsController/searchProjects | 
[**searchProjectsByQbe**](ProjectsControllerApi.md#searchProjectsByQbe) | **POST** /api/admin/ProjectsController/searchProjectsByQbe | 
[**updateProject**](ProjectsControllerApi.md#updateProject) | **POST** /api/admin/ProjectsController/updateProject | 

<a name="deleteProject"></a>
# **deleteProject**
> deleteProject(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ProjectsControllerApi();
let body = new GeboAiClient.GProject(); // GProject | 

apiInstance.deleteProject(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GProject**](GProject.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="findChildProjects"></a>
# **findChildProjects**
> [GProject] findChildProjects(knowledgeBaseCode, parentProjectCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ProjectsControllerApi();
let knowledgeBaseCode = "knowledgeBaseCode_example"; // String | 
let parentProjectCode = "parentProjectCode_example"; // String | 

apiInstance.findChildProjects(knowledgeBaseCode, parentProjectCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **knowledgeBaseCode** | **String**|  | 
 **parentProjectCode** | **String**|  | 

### Return type

[**[GProject]**](GProject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findOtherKnowledgeBaseIncludableProjects"></a>
# **findOtherKnowledgeBaseIncludableProjects**
> [GProject] findOtherKnowledgeBaseIncludableProjects(knowledgeBaseCode, actualSelectedProjects)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ProjectsControllerApi();
let knowledgeBaseCode = "knowledgeBaseCode_example"; // String | 
let actualSelectedProjects = ["actualSelectedProjects_example"]; // [String] | 

apiInstance.findOtherKnowledgeBaseIncludableProjects(knowledgeBaseCode, actualSelectedProjects).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **knowledgeBaseCode** | **String**|  | 
 **actualSelectedProjects** | [**[String]**](String.md)|  | 

### Return type

[**[GProject]**](GProject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findProjectByCode"></a>
# **findProjectByCode**
> GProject findProjectByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ProjectsControllerApi();
let code = "code_example"; // String | 

apiInstance.findProjectByCode(code).then((data) => {
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

[**GProject**](GProject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findRootProjects"></a>
# **findRootProjects**
> [GProject] findRootProjects(knowledgeBaseCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ProjectsControllerApi();
let knowledgeBaseCode = "knowledgeBaseCode_example"; // String | 

apiInstance.findRootProjects(knowledgeBaseCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **knowledgeBaseCode** | **String**|  | 

### Return type

[**[GProject]**](GProject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getChildDocuments"></a>
# **getChildDocuments**
> [VDocumentInfo] getChildDocuments(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ProjectsControllerApi();
let body = new GeboAiClient.ChildVirtualFSParam(); // ChildVirtualFSParam | 

apiInstance.getChildDocuments(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ChildVirtualFSParam**](ChildVirtualFSParam.md)|  | 

### Return type

[**[VDocumentInfo]**](VDocumentInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getChildFolders"></a>
# **getChildFolders**
> [VFolderInfo] getChildFolders(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ProjectsControllerApi();
let body = new GeboAiClient.ChildVirtualFSParam(); // ChildVirtualFSParam | 

apiInstance.getChildFolders(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ChildVirtualFSParam**](ChildVirtualFSParam.md)|  | 

### Return type

[**[VFolderInfo]**](VFolderInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getProjects"></a>
# **getProjects**
> [GProject] getProjects()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ProjectsControllerApi();
apiInstance.getProjects().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GProject]**](GProject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getRootDocuments"></a>
# **getRootDocuments**
> [VDocumentInfo] getRootDocuments(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ProjectsControllerApi();
let body = new GeboAiClient.GObjectRefGProjectEndpoint(); // GObjectRefGProjectEndpoint | 

apiInstance.getRootDocuments(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GObjectRefGProjectEndpoint**](GObjectRefGProjectEndpoint.md)|  | 

### Return type

[**[VDocumentInfo]**](VDocumentInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getRootFolders"></a>
# **getRootFolders**
> [VFolderInfo] getRootFolders(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ProjectsControllerApi();
let body = new GeboAiClient.GObjectRefGProjectEndpoint(); // GObjectRefGProjectEndpoint | 

apiInstance.getRootFolders(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GObjectRefGProjectEndpoint**](GObjectRefGProjectEndpoint.md)|  | 

### Return type

[**[VFolderInfo]**](VFolderInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertProject"></a>
# **insertProject**
> GProject insertProject(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ProjectsControllerApi();
let body = new GeboAiClient.GProject(); // GProject | 

apiInstance.insertProject(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GProject**](GProject.md)|  | 

### Return type

[**GProject**](GProject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="searchProjects"></a>
# **searchProjects**
> [GProject] searchProjects(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ProjectsControllerApi();
let body = new GeboAiClient.ProjectsResearchFilter(); // ProjectsResearchFilter | 

apiInstance.searchProjects(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ProjectsResearchFilter**](ProjectsResearchFilter.md)|  | 

### Return type

[**[GProject]**](GProject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="searchProjectsByQbe"></a>
# **searchProjectsByQbe**
> [GProject] searchProjectsByQbe(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ProjectsControllerApi();
let body = new GeboAiClient.GProject(); // GProject | 

apiInstance.searchProjectsByQbe(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GProject**](GProject.md)|  | 

### Return type

[**[GProject]**](GProject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateProject"></a>
# **updateProject**
> GProject updateProject(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ProjectsControllerApi();
let body = new GeboAiClient.GProject(); // GProject | 

apiInstance.updateProject(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GProject**](GProject.md)|  | 

### Return type

[**GProject**](GProject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

