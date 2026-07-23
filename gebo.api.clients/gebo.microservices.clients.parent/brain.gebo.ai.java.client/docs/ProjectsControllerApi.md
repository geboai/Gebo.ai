# ProjectsControllerApi

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
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ProjectsControllerApi;


ProjectsControllerApi apiInstance = new ProjectsControllerApi();
GProject body = new GProject(); // GProject | 
try {
    apiInstance.deleteProject(body);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectsControllerApi#deleteProject");
    e.printStackTrace();
}
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
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ProjectsControllerApi;


ProjectsControllerApi apiInstance = new ProjectsControllerApi();
Object knowledgeBaseCode = null; // Object | 
Object parentProjectCode = null; // Object | 
try {
    Object result = apiInstance.findChildProjects(knowledgeBaseCode, parentProjectCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectsControllerApi#findChildProjects");
    e.printStackTrace();
}
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
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ProjectsControllerApi;


ProjectsControllerApi apiInstance = new ProjectsControllerApi();
Object knowledgeBaseCode = null; // Object | 
Object actualSelectedProjects = null; // Object | 
try {
    Object result = apiInstance.findOtherKnowledgeBaseIncludableProjects(knowledgeBaseCode, actualSelectedProjects);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectsControllerApi#findOtherKnowledgeBaseIncludableProjects");
    e.printStackTrace();
}
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
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ProjectsControllerApi;


ProjectsControllerApi apiInstance = new ProjectsControllerApi();
Object code = null; // Object | 
try {
    GProject result = apiInstance.findProjectByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectsControllerApi#findProjectByCode");
    e.printStackTrace();
}
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
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ProjectsControllerApi;


ProjectsControllerApi apiInstance = new ProjectsControllerApi();
Object knowledgeBaseCode = null; // Object | 
try {
    Object result = apiInstance.findRootProjects(knowledgeBaseCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectsControllerApi#findRootProjects");
    e.printStackTrace();
}
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
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ProjectsControllerApi;


ProjectsControllerApi apiInstance = new ProjectsControllerApi();
ChildVirtualFSParam body = new ChildVirtualFSParam(); // ChildVirtualFSParam | 
try {
    Object result = apiInstance.getChildDocuments(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectsControllerApi#getChildDocuments");
    e.printStackTrace();
}
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
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ProjectsControllerApi;


ProjectsControllerApi apiInstance = new ProjectsControllerApi();
ChildVirtualFSParam body = new ChildVirtualFSParam(); // ChildVirtualFSParam | 
try {
    Object result = apiInstance.getChildFolders(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectsControllerApi#getChildFolders");
    e.printStackTrace();
}
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
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ProjectsControllerApi;


ProjectsControllerApi apiInstance = new ProjectsControllerApi();
try {
    Object result = apiInstance.getProjects();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectsControllerApi#getProjects");
    e.printStackTrace();
}
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
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ProjectsControllerApi;


ProjectsControllerApi apiInstance = new ProjectsControllerApi();
GObjectRefGProjectEndpoint body = new GObjectRefGProjectEndpoint(); // GObjectRefGProjectEndpoint | 
try {
    Object result = apiInstance.getRootDocuments(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectsControllerApi#getRootDocuments");
    e.printStackTrace();
}
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
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ProjectsControllerApi;


ProjectsControllerApi apiInstance = new ProjectsControllerApi();
GObjectRefGProjectEndpoint body = new GObjectRefGProjectEndpoint(); // GObjectRefGProjectEndpoint | 
try {
    Object result = apiInstance.getRootFolders(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectsControllerApi#getRootFolders");
    e.printStackTrace();
}
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
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ProjectsControllerApi;


ProjectsControllerApi apiInstance = new ProjectsControllerApi();
GProject body = new GProject(); // GProject | 
try {
    GProject result = apiInstance.insertProject(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectsControllerApi#insertProject");
    e.printStackTrace();
}
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
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ProjectsControllerApi;


ProjectsControllerApi apiInstance = new ProjectsControllerApi();
ProjectsResearchFilter body = new ProjectsResearchFilter(); // ProjectsResearchFilter | 
try {
    Object result = apiInstance.searchProjects(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectsControllerApi#searchProjects");
    e.printStackTrace();
}
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
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ProjectsControllerApi;


ProjectsControllerApi apiInstance = new ProjectsControllerApi();
GProject body = new GProject(); // GProject | 
try {
    Object result = apiInstance.searchProjectsByQbe(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectsControllerApi#searchProjectsByQbe");
    e.printStackTrace();
}
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
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ProjectsControllerApi;


ProjectsControllerApi apiInstance = new ProjectsControllerApi();
GProject body = new GProject(); // GProject | 
try {
    GProject result = apiInstance.updateProject(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectsControllerApi#updateProject");
    e.printStackTrace();
}
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

