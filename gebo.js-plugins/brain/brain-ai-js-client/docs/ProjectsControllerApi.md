# BrainClient.ProjectsControllerApi

All URIs are relative to *http://localhost:13001/brain*

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
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.ProjectsControllerApi();
let body = new BrainClient.GProject(); // GProject | 

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
> Object findChildProjects(knowledgeBaseCode, parentProjectCode)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.ProjectsControllerApi();
let knowledgeBaseCode = null; // Object | 
let parentProjectCode = null; // Object | 

apiInstance.findChildProjects(knowledgeBaseCode, parentProjectCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **knowledgeBaseCode** | [**Object**](.md)|  | 
 **parentProjectCode** | [**Object**](.md)|  | 

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findOtherKnowledgeBaseIncludableProjects"></a>
# **findOtherKnowledgeBaseIncludableProjects**
> Object findOtherKnowledgeBaseIncludableProjects(knowledgeBaseCode, actualSelectedProjects)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.ProjectsControllerApi();
let knowledgeBaseCode = null; // Object | 
let actualSelectedProjects = null; // Object | 

apiInstance.findOtherKnowledgeBaseIncludableProjects(knowledgeBaseCode, actualSelectedProjects).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **knowledgeBaseCode** | [**Object**](.md)|  | 
 **actualSelectedProjects** | [**Object**](.md)|  | 

### Return type

**Object**

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
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.ProjectsControllerApi();
let code = null; // Object | 

apiInstance.findProjectByCode(code).then((data) => {
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

[**GProject**](GProject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findRootProjects"></a>
# **findRootProjects**
> Object findRootProjects(knowledgeBaseCode)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.ProjectsControllerApi();
let knowledgeBaseCode = null; // Object | 

apiInstance.findRootProjects(knowledgeBaseCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **knowledgeBaseCode** | [**Object**](.md)|  | 

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getChildDocuments"></a>
# **getChildDocuments**
> Object getChildDocuments(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.ProjectsControllerApi();
let body = new BrainClient.ChildVirtualFSParam(); // ChildVirtualFSParam | 

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

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getChildFolders"></a>
# **getChildFolders**
> Object getChildFolders(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.ProjectsControllerApi();
let body = new BrainClient.ChildVirtualFSParam(); // ChildVirtualFSParam | 

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

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getProjects"></a>
# **getProjects**
> Object getProjects()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.ProjectsControllerApi();
apiInstance.getProjects().then((data) => {
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

<a name="getRootDocuments"></a>
# **getRootDocuments**
> Object getRootDocuments(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.ProjectsControllerApi();
let body = new BrainClient.GObjectRefGProjectEndpoint(); // GObjectRefGProjectEndpoint | 

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

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getRootFolders"></a>
# **getRootFolders**
> Object getRootFolders(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.ProjectsControllerApi();
let body = new BrainClient.GObjectRefGProjectEndpoint(); // GObjectRefGProjectEndpoint | 

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

**Object**

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
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.ProjectsControllerApi();
let body = new BrainClient.GProject(); // GProject | 

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
> Object searchProjects(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.ProjectsControllerApi();
let body = new BrainClient.ProjectsResearchFilter(); // ProjectsResearchFilter | 

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

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="searchProjectsByQbe"></a>
# **searchProjectsByQbe**
> Object searchProjectsByQbe(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.ProjectsControllerApi();
let body = new BrainClient.GProject(); // GProject | 

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

**Object**

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
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.ProjectsControllerApi();
let body = new BrainClient.GProject(); // GProject | 

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

