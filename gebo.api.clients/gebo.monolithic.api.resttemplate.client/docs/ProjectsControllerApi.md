# ProjectsControllerApi

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
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ProjectsControllerApi;


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
> List&lt;GProject&gt; findChildProjects(knowledgeBaseCode, parentProjectCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ProjectsControllerApi;


ProjectsControllerApi apiInstance = new ProjectsControllerApi();
String knowledgeBaseCode = "knowledgeBaseCode_example"; // String | 
String parentProjectCode = "parentProjectCode_example"; // String | 
try {
    List<GProject> result = apiInstance.findChildProjects(knowledgeBaseCode, parentProjectCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectsControllerApi#findChildProjects");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **knowledgeBaseCode** | **String**|  |
 **parentProjectCode** | **String**|  |

### Return type

[**List&lt;GProject&gt;**](GProject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findOtherKnowledgeBaseIncludableProjects"></a>
# **findOtherKnowledgeBaseIncludableProjects**
> List&lt;GProject&gt; findOtherKnowledgeBaseIncludableProjects(knowledgeBaseCode, actualSelectedProjects)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ProjectsControllerApi;


ProjectsControllerApi apiInstance = new ProjectsControllerApi();
String knowledgeBaseCode = "knowledgeBaseCode_example"; // String | 
List<String> actualSelectedProjects = Arrays.asList("actualSelectedProjects_example"); // List<String> | 
try {
    List<GProject> result = apiInstance.findOtherKnowledgeBaseIncludableProjects(knowledgeBaseCode, actualSelectedProjects);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectsControllerApi#findOtherKnowledgeBaseIncludableProjects");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **knowledgeBaseCode** | **String**|  |
 **actualSelectedProjects** | [**List&lt;String&gt;**](String.md)|  |

### Return type

[**List&lt;GProject&gt;**](GProject.md)

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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ProjectsControllerApi;


ProjectsControllerApi apiInstance = new ProjectsControllerApi();
String code = "code_example"; // String | 
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
> List&lt;GProject&gt; findRootProjects(knowledgeBaseCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ProjectsControllerApi;


ProjectsControllerApi apiInstance = new ProjectsControllerApi();
String knowledgeBaseCode = "knowledgeBaseCode_example"; // String | 
try {
    List<GProject> result = apiInstance.findRootProjects(knowledgeBaseCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectsControllerApi#findRootProjects");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **knowledgeBaseCode** | **String**|  |

### Return type

[**List&lt;GProject&gt;**](GProject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getChildDocuments"></a>
# **getChildDocuments**
> List&lt;VDocumentInfo&gt; getChildDocuments(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ProjectsControllerApi;


ProjectsControllerApi apiInstance = new ProjectsControllerApi();
ChildVirtualFSParam body = new ChildVirtualFSParam(); // ChildVirtualFSParam | 
try {
    List<VDocumentInfo> result = apiInstance.getChildDocuments(body);
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

[**List&lt;VDocumentInfo&gt;**](VDocumentInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getChildFolders"></a>
# **getChildFolders**
> List&lt;VFolderInfo&gt; getChildFolders(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ProjectsControllerApi;


ProjectsControllerApi apiInstance = new ProjectsControllerApi();
ChildVirtualFSParam body = new ChildVirtualFSParam(); // ChildVirtualFSParam | 
try {
    List<VFolderInfo> result = apiInstance.getChildFolders(body);
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

[**List&lt;VFolderInfo&gt;**](VFolderInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getProjects"></a>
# **getProjects**
> List&lt;GProject&gt; getProjects()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ProjectsControllerApi;


ProjectsControllerApi apiInstance = new ProjectsControllerApi();
try {
    List<GProject> result = apiInstance.getProjects();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectsControllerApi#getProjects");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GProject&gt;**](GProject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getRootDocuments"></a>
# **getRootDocuments**
> List&lt;VDocumentInfo&gt; getRootDocuments(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ProjectsControllerApi;


ProjectsControllerApi apiInstance = new ProjectsControllerApi();
GObjectRefGProjectEndpoint body = new GObjectRefGProjectEndpoint(); // GObjectRefGProjectEndpoint | 
try {
    List<VDocumentInfo> result = apiInstance.getRootDocuments(body);
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

[**List&lt;VDocumentInfo&gt;**](VDocumentInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getRootFolders"></a>
# **getRootFolders**
> List&lt;VFolderInfo&gt; getRootFolders(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ProjectsControllerApi;


ProjectsControllerApi apiInstance = new ProjectsControllerApi();
GObjectRefGProjectEndpoint body = new GObjectRefGProjectEndpoint(); // GObjectRefGProjectEndpoint | 
try {
    List<VFolderInfo> result = apiInstance.getRootFolders(body);
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

[**List&lt;VFolderInfo&gt;**](VFolderInfo.md)

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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ProjectsControllerApi;


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
> List&lt;GProject&gt; searchProjects(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ProjectsControllerApi;


ProjectsControllerApi apiInstance = new ProjectsControllerApi();
ProjectsResearchFilter body = new ProjectsResearchFilter(); // ProjectsResearchFilter | 
try {
    List<GProject> result = apiInstance.searchProjects(body);
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

[**List&lt;GProject&gt;**](GProject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="searchProjectsByQbe"></a>
# **searchProjectsByQbe**
> List&lt;GProject&gt; searchProjectsByQbe(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ProjectsControllerApi;


ProjectsControllerApi apiInstance = new ProjectsControllerApi();
GProject body = new GProject(); // GProject | 
try {
    List<GProject> result = apiInstance.searchProjectsByQbe(body);
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

[**List&lt;GProject&gt;**](GProject.md)

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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ProjectsControllerApi;


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

