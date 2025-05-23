# TemplateApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createContentTemplate**](TemplateApi.md#createContentTemplate) | **POST** /wiki/rest/api/template | Create content template
[**getBlueprintTemplates**](TemplateApi.md#getBlueprintTemplates) | **GET** /wiki/rest/api/template/blueprint | Get blueprint templates
[**getContentTemplate**](TemplateApi.md#getContentTemplate) | **GET** /wiki/rest/api/template/{contentTemplateId} | Get content template
[**getContentTemplates**](TemplateApi.md#getContentTemplates) | **GET** /wiki/rest/api/template/page | Get content templates
[**removeTemplate**](TemplateApi.md#removeTemplate) | **DELETE** /wiki/rest/api/template/{contentTemplateId} | Remove template
[**updateContentTemplate**](TemplateApi.md#updateContentTemplate) | **PUT** /wiki/rest/api/template | Update content template

<a name="createContentTemplate"></a>
# **createContentTemplate**
> ContentTemplate createContentTemplate(body)

Create content template

Creates a new content template. Note, blueprint templates cannot be created via the REST API.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Admin&#x27; permission for the space to create a space template or &#x27;Confluence Administrator&#x27; global permission to create a global template.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.TemplateApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

TemplateApi apiInstance = new TemplateApi();
Map<String, Object> body = new Map(); // Map<String, Object> | The content template to be created.
The content body must be in 'storage' format.
try {
    ContentTemplate result = apiInstance.createContentTemplate(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TemplateApi#createContentTemplate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Map&lt;String, Object&gt;**](Map.md)| The content template to be created.
The content body must be in &#x27;storage&#x27; format. |

### Return type

[**ContentTemplate**](ContentTemplate.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getBlueprintTemplates"></a>
# **getBlueprintTemplates**
> BlueprintTemplateArray getBlueprintTemplates(spaceKey, start, limit, expand)

Get blueprint templates

Returns all templates provided by blueprints. Use this method to retrieve all global blueprint templates or all blueprint templates in a space.  Note, all global blueprints are inherited by each space. Space blueprints can be customised without affecting the global blueprints.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;View&#x27; permission for the space to view blueprints for the space and permission to access the Confluence site (&#x27;Can use&#x27; global permission) to view global blueprints.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.TemplateApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

TemplateApi apiInstance = new TemplateApi();
String spaceKey = "spaceKey_example"; // String | The key of the space to be queried for templates. If the `spaceKey` is not specified, global blueprint templates will be returned.
Integer start = 0; // Integer | The starting index of the returned templates.
Integer limit = 25; // Integer | The maximum number of templates to return per page. Note, this may be restricted by fixed system limits.
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the template to expand.  - `body` returns the content of the template in storage format.
try {
    BlueprintTemplateArray result = apiInstance.getBlueprintTemplates(spaceKey, start, limit, expand);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TemplateApi#getBlueprintTemplates");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **spaceKey** | **String**| The key of the space to be queried for templates. If the &#x60;spaceKey&#x60; is not specified, global blueprint templates will be returned. | [optional]
 **start** | **Integer**| The starting index of the returned templates. | [optional] [default to 0] [enum: 0]
 **limit** | **Integer**| The maximum number of templates to return per page. Note, this may be restricted by fixed system limits. | [optional] [default to 25] [enum: 0]
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the template to expand.  - &#x60;body&#x60; returns the content of the template in storage format. | [optional] [enum: body]

### Return type

[**BlueprintTemplateArray**](BlueprintTemplateArray.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getContentTemplate"></a>
# **getContentTemplate**
> ContentTemplate getContentTemplate(contentTemplateId)

Get content template

Returns a content template. This includes information about template, like the name, the space or blueprint that the template is in, the body of the template, and more.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;View&#x27; permission for the space to view space templates and permission to access the Confluence site (&#x27;Can use&#x27; global permission) to view global templates.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.TemplateApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

TemplateApi apiInstance = new TemplateApi();
String contentTemplateId = "contentTemplateId_example"; // String | The ID of the content template to be returned.
try {
    ContentTemplate result = apiInstance.getContentTemplate(contentTemplateId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TemplateApi#getContentTemplate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **contentTemplateId** | **String**| The ID of the content template to be returned. |

### Return type

[**ContentTemplate**](ContentTemplate.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getContentTemplates"></a>
# **getContentTemplates**
> ContentTemplateArray getContentTemplates(spaceKey, start, limit, expand)

Get content templates

Returns all content templates. Use this method to retrieve all global content templates or all content templates in a space.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;View&#x27; permission for the space to view space templates and permission to access the Confluence site (&#x27;Can use&#x27; global permission) to view global templates.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.TemplateApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

TemplateApi apiInstance = new TemplateApi();
String spaceKey = "spaceKey_example"; // String | The key of the space to be queried for templates. If the `spaceKey` is not specified, global templates will be returned.
Integer start = 0; // Integer | The starting index of the returned templates.
Integer limit = 25; // Integer | The maximum number of templates to return per page. Note, this may be restricted by fixed system limits.
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the template to expand.  - `body` returns the content of the template in storage format.
try {
    ContentTemplateArray result = apiInstance.getContentTemplates(spaceKey, start, limit, expand);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TemplateApi#getContentTemplates");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **spaceKey** | **String**| The key of the space to be queried for templates. If the &#x60;spaceKey&#x60; is not specified, global templates will be returned. | [optional]
 **start** | **Integer**| The starting index of the returned templates. | [optional] [default to 0] [enum: 0]
 **limit** | **Integer**| The maximum number of templates to return per page. Note, this may be restricted by fixed system limits. | [optional] [default to 25] [enum: 0]
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the template to expand.  - &#x60;body&#x60; returns the content of the template in storage format. | [optional] [enum: body]

### Return type

[**ContentTemplateArray**](ContentTemplateArray.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="removeTemplate"></a>
# **removeTemplate**
> removeTemplate(contentTemplateId)

Remove template

Deletes a template. This results in different actions depending on the type of template:  - If the template is a content template, it is deleted. - If the template is a modified space-level blueprint template, it reverts to the template inherited from the global-level blueprint template. - If the template is a modified global-level blueprint template, it reverts to the default global-level blueprint template.   Note, unmodified blueprint templates cannot be deleted.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**:         &#x27;Admin&#x27; permission for the space to delete a space template or &#x27;Confluence Administrator&#x27;         global permission to delete a global template.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.TemplateApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

TemplateApi apiInstance = new TemplateApi();
String contentTemplateId = "contentTemplateId_example"; // String | The ID of the template to be deleted.
try {
    apiInstance.removeTemplate(contentTemplateId);
} catch (ApiException e) {
    System.err.println("Exception when calling TemplateApi#removeTemplate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **contentTemplateId** | **String**| The ID of the template to be deleted. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="updateContentTemplate"></a>
# **updateContentTemplate**
> ContentTemplate updateContentTemplate(body)

Update content template

Updates a content template. Note, blueprint templates cannot be updated via the REST API.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Admin&#x27; permission for the space to update a space template or &#x27;Confluence Administrator&#x27; global permission to update a global template.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.TemplateApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

TemplateApi apiInstance = new TemplateApi();
Map<String, Object> body = new Map(); // Map<String, Object> | The updated content template.
try {
    ContentTemplate result = apiInstance.updateContentTemplate(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TemplateApi#updateContentTemplate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Map&lt;String, Object&gt;**](Map.md)| The updated content template. |

### Return type

[**ContentTemplate**](ContentTemplate.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

