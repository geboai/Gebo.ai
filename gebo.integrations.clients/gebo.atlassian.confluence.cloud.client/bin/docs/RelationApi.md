# RelationApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createRelationship**](RelationApi.md#createRelationship) | **PUT** /wiki/rest/api/relation/{relationName}/from/{sourceType}/{sourceKey}/to/{targetType}/{targetKey} | Create relationship
[**deleteRelationship**](RelationApi.md#deleteRelationship) | **DELETE** /wiki/rest/api/relation/{relationName}/from/{sourceType}/{sourceKey}/to/{targetType}/{targetKey} | Delete relationship
[**findSourcesForTarget**](RelationApi.md#findSourcesForTarget) | **GET** /wiki/rest/api/relation/{relationName}/to/{targetType}/{targetKey}/from/{sourceType} | Find source entities related to a target entity
[**findTargetFromSource**](RelationApi.md#findTargetFromSource) | **GET** /wiki/rest/api/relation/{relationName}/from/{sourceType}/{sourceKey}/to/{targetType} | Find target entities related to a source entity
[**getRelationship**](RelationApi.md#getRelationship) | **GET** /wiki/rest/api/relation/{relationName}/from/{sourceType}/{sourceKey}/to/{targetType}/{targetKey} | Find relationship from source to target

<a name="createRelationship"></a>
# **createRelationship**
> Relation createRelationship(relationName, sourceType, sourceKey, targetType, targetKey, sourceStatus, targetStatus, sourceVersion, targetVersion)

Create relationship

Creates a relationship between two entities (user, space, content). The &#x27;favourite&#x27; relationship is supported by default, but you can use this method to create any type of relationship between two entities.  For example, the following method creates a &#x27;sibling&#x27; relationship between two pieces of content: &#x60;PUT /wiki/rest/api/relation/sibling/from/content/123/to/content/456&#x60;  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission).

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.RelationApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

RelationApi apiInstance = new RelationApi();
String relationName = "relationName_example"; // String | The name of the relationship. This method supports the 'favourite' (i.e. 'save for later') relationship. You can also specify any other value for this parameter to create a custom relationship type.
String sourceType = "sourceType_example"; // String | The source entity type of the relationship. This must be 'user', if the `relationName` is 'favourite'.
String sourceKey = "sourceKey_example"; // String | - The identifier for the source entity:  - If `sourceType` is 'user', then specify either 'current' (logged-in   user) or the user key. - If `sourceType` is 'content', then specify the content ID. - If `sourceType` is 'space', then specify the space key.
String targetType = "targetType_example"; // String | The target entity type of the relationship. This must be 'space' or 'content', if the `relationName` is 'favourite'.
String targetKey = "targetKey_example"; // String | - The identifier for the target entity:  - If `sourceType` is 'user', then specify either 'current' (logged-in   user) or the user key. - If `sourceType` is 'content', then specify the content ID. - If `sourceType` is 'space', then specify the space key.
String sourceStatus = "sourceStatus_example"; // String | The status of the source. This parameter is only used when the `sourceType` is 'content'.
String targetStatus = "targetStatus_example"; // String | The status of the target. This parameter is only used when the `targetType` is 'content'.
Integer sourceVersion = 56; // Integer | The version of the source. This parameter is only used when the `sourceType` is 'content' and the `sourceStatus` is 'historical'.
Integer targetVersion = 56; // Integer | The version of the target. This parameter is only used when the `targetType` is 'content' and the `targetStatus` is 'historical'.
try {
    Relation result = apiInstance.createRelationship(relationName, sourceType, sourceKey, targetType, targetKey, sourceStatus, targetStatus, sourceVersion, targetVersion);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RelationApi#createRelationship");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **relationName** | **String**| The name of the relationship. This method supports the &#x27;favourite&#x27; (i.e. &#x27;save for later&#x27;) relationship. You can also specify any other value for this parameter to create a custom relationship type. |
 **sourceType** | **String**| The source entity type of the relationship. This must be &#x27;user&#x27;, if the &#x60;relationName&#x60; is &#x27;favourite&#x27;. | [enum: user, content, space]
 **sourceKey** | **String**| - The identifier for the source entity:  - If &#x60;sourceType&#x60; is &#x27;user&#x27;, then specify either &#x27;current&#x27; (logged-in   user) or the user key. - If &#x60;sourceType&#x60; is &#x27;content&#x27;, then specify the content ID. - If &#x60;sourceType&#x60; is &#x27;space&#x27;, then specify the space key. |
 **targetType** | **String**| The target entity type of the relationship. This must be &#x27;space&#x27; or &#x27;content&#x27;, if the &#x60;relationName&#x60; is &#x27;favourite&#x27;. | [enum: user, content, space]
 **targetKey** | **String**| - The identifier for the target entity:  - If &#x60;sourceType&#x60; is &#x27;user&#x27;, then specify either &#x27;current&#x27; (logged-in   user) or the user key. - If &#x60;sourceType&#x60; is &#x27;content&#x27;, then specify the content ID. - If &#x60;sourceType&#x60; is &#x27;space&#x27;, then specify the space key. |
 **sourceStatus** | **String**| The status of the source. This parameter is only used when the &#x60;sourceType&#x60; is &#x27;content&#x27;. | [optional]
 **targetStatus** | **String**| The status of the target. This parameter is only used when the &#x60;targetType&#x60; is &#x27;content&#x27;. | [optional]
 **sourceVersion** | **Integer**| The version of the source. This parameter is only used when the &#x60;sourceType&#x60; is &#x27;content&#x27; and the &#x60;sourceStatus&#x60; is &#x27;historical&#x27;. | [optional]
 **targetVersion** | **Integer**| The version of the target. This parameter is only used when the &#x60;targetType&#x60; is &#x27;content&#x27; and the &#x60;targetStatus&#x60; is &#x27;historical&#x27;. | [optional]

### Return type

[**Relation**](Relation.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="deleteRelationship"></a>
# **deleteRelationship**
> deleteRelationship(relationName, sourceType, sourceKey, targetType, targetKey, sourceStatus, targetStatus, sourceVersion, targetVersion)

Delete relationship

Deletes a relationship between two entities (user, space, content).  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission). For favourite relationships, the current user can only delete their own favourite relationships. A space administrator can delete favourite relationships for any user.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.RelationApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

RelationApi apiInstance = new RelationApi();
String relationName = "relationName_example"; // String | The name of the relationship.
String sourceType = "sourceType_example"; // String | The source entity type of the relationship. This must be 'user', if the `relationName` is 'favourite'.
String sourceKey = "sourceKey_example"; // String | - The identifier for the source entity:  - If `sourceType` is 'user', then specify either 'current' (logged-in   user) or the user key. - If `sourceType` is 'content', then specify the content ID. - If `sourceType` is 'space', then specify the space key.
String targetType = "targetType_example"; // String | The target entity type of the relationship. This must be 'space' or 'content', if the `relationName` is 'favourite'.
String targetKey = "targetKey_example"; // String | - The identifier for the target entity:  - If `sourceType` is 'user', then specify either 'current' (logged-in   user) or the user key. - If `sourceType` is 'content', then specify the content ID. - If `sourceType` is 'space', then specify the space key.
String sourceStatus = "sourceStatus_example"; // String | The status of the source. This parameter is only used when the `sourceType` is 'content'.
String targetStatus = "targetStatus_example"; // String | The status of the target. This parameter is only used when the `targetType` is 'content'.
Integer sourceVersion = 56; // Integer | The version of the source. This parameter is only used when the `sourceType` is 'content' and the `sourceStatus` is 'historical'.
Integer targetVersion = 56; // Integer | The version of the target. This parameter is only used when the `targetType` is 'content' and the `targetStatus` is 'historical'.
try {
    apiInstance.deleteRelationship(relationName, sourceType, sourceKey, targetType, targetKey, sourceStatus, targetStatus, sourceVersion, targetVersion);
} catch (ApiException e) {
    System.err.println("Exception when calling RelationApi#deleteRelationship");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **relationName** | **String**| The name of the relationship. |
 **sourceType** | **String**| The source entity type of the relationship. This must be &#x27;user&#x27;, if the &#x60;relationName&#x60; is &#x27;favourite&#x27;. | [enum: user, content, space]
 **sourceKey** | **String**| - The identifier for the source entity:  - If &#x60;sourceType&#x60; is &#x27;user&#x27;, then specify either &#x27;current&#x27; (logged-in   user) or the user key. - If &#x60;sourceType&#x60; is &#x27;content&#x27;, then specify the content ID. - If &#x60;sourceType&#x60; is &#x27;space&#x27;, then specify the space key. |
 **targetType** | **String**| The target entity type of the relationship. This must be &#x27;space&#x27; or &#x27;content&#x27;, if the &#x60;relationName&#x60; is &#x27;favourite&#x27;. | [enum: user, content, space]
 **targetKey** | **String**| - The identifier for the target entity:  - If &#x60;sourceType&#x60; is &#x27;user&#x27;, then specify either &#x27;current&#x27; (logged-in   user) or the user key. - If &#x60;sourceType&#x60; is &#x27;content&#x27;, then specify the content ID. - If &#x60;sourceType&#x60; is &#x27;space&#x27;, then specify the space key. |
 **sourceStatus** | **String**| The status of the source. This parameter is only used when the &#x60;sourceType&#x60; is &#x27;content&#x27;. | [optional]
 **targetStatus** | **String**| The status of the target. This parameter is only used when the &#x60;targetType&#x60; is &#x27;content&#x27;. | [optional]
 **sourceVersion** | **Integer**| The version of the source. This parameter is only used when the &#x60;sourceType&#x60; is &#x27;content&#x27; and the &#x60;sourceStatus&#x60; is &#x27;historical&#x27;. | [optional]
 **targetVersion** | **Integer**| The version of the target. This parameter is only used when the &#x60;targetType&#x60; is &#x27;content&#x27; and the &#x60;targetStatus&#x60; is &#x27;historical&#x27;. | [optional]

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="findSourcesForTarget"></a>
# **findSourcesForTarget**
> RelationArray findSourcesForTarget(relationName, sourceType, targetType, targetKey, sourceStatus, targetStatus, sourceVersion, targetVersion, expand, start, limit)

Find source entities related to a target entity

Returns all target entities that have a particular relationship to the source entity. Note, relationships are one way.  For example, the following method finds all users that have a &#x27;collaborator&#x27; relationship to a piece of content with an ID of &#x27;1234&#x27;: &#x60;GET /wiki/rest/api/relation/collaborator/to/content/1234/from/user&#x60; Note, &#x27;collaborator&#x27; is an example custom relationship type.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view both the target entity and source entity.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.RelationApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

RelationApi apiInstance = new RelationApi();
String relationName = "relationName_example"; // String | The name of the relationship. This method supports relationships created via [Create relationship](#api-wiki-rest-api-relation-relationname-from-sourcetype-sourcekey-to-targettype-targetkey-put). Note, this method does not support 'like' or 'favourite' relationships.
String sourceType = "sourceType_example"; // String | The source entity type of the relationship.
String targetType = "targetType_example"; // String | The target entity type of the relationship.
String targetKey = "targetKey_example"; // String | The identifier for the target entity:  - If `targetType` is `user`, then specify either `current` (logged-in user), the user key of the user, or the account ID of the user. Note that the user key has been deprecated in favor of the account ID for this parameter. See the [migration guide](https://developer.atlassian.com/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details. - If `targetType` is 'content', then specify the content ID. - If `targetType` is 'space', then specify the space key.
String sourceStatus = "sourceStatus_example"; // String | The status of the source. This parameter is only used when the `sourceType` is 'content'.
String targetStatus = "targetStatus_example"; // String | The status of the target. This parameter is only used when the `targetType` is 'content'.
Integer sourceVersion = 56; // Integer | The version of the source. This parameter is only used when the `sourceType` is 'content' and the `sourceStatus` is 'historical'.
Integer targetVersion = 56; // Integer | The version of the target. This parameter is only used when the `targetType` is 'content' and the `targetStatus` is 'historical'.
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the response object to expand.  - `relationData` returns information about the relationship, such as who created it and when it was created. - `source` returns the source entity. - `target` returns the target entity.
Integer start = 0; // Integer | The starting index of the returned relationships.
Integer limit = 25; // Integer | The maximum number of relationships to return per page. Note, this may be restricted by fixed system limits.
try {
    RelationArray result = apiInstance.findSourcesForTarget(relationName, sourceType, targetType, targetKey, sourceStatus, targetStatus, sourceVersion, targetVersion, expand, start, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RelationApi#findSourcesForTarget");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **relationName** | **String**| The name of the relationship. This method supports relationships created via [Create relationship](#api-wiki-rest-api-relation-relationname-from-sourcetype-sourcekey-to-targettype-targetkey-put). Note, this method does not support &#x27;like&#x27; or &#x27;favourite&#x27; relationships. |
 **sourceType** | **String**| The source entity type of the relationship. | [enum: user, content, space]
 **targetType** | **String**| The target entity type of the relationship. | [enum: user, content, space]
 **targetKey** | **String**| The identifier for the target entity:  - If &#x60;targetType&#x60; is &#x60;user&#x60;, then specify either &#x60;current&#x60; (logged-in user), the user key of the user, or the account ID of the user. Note that the user key has been deprecated in favor of the account ID for this parameter. See the [migration guide](https://developer.atlassian.com/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details. - If &#x60;targetType&#x60; is &#x27;content&#x27;, then specify the content ID. - If &#x60;targetType&#x60; is &#x27;space&#x27;, then specify the space key. |
 **sourceStatus** | **String**| The status of the source. This parameter is only used when the &#x60;sourceType&#x60; is &#x27;content&#x27;. | [optional]
 **targetStatus** | **String**| The status of the target. This parameter is only used when the &#x60;targetType&#x60; is &#x27;content&#x27;. | [optional]
 **sourceVersion** | **Integer**| The version of the source. This parameter is only used when the &#x60;sourceType&#x60; is &#x27;content&#x27; and the &#x60;sourceStatus&#x60; is &#x27;historical&#x27;. | [optional]
 **targetVersion** | **Integer**| The version of the target. This parameter is only used when the &#x60;targetType&#x60; is &#x27;content&#x27; and the &#x60;targetStatus&#x60; is &#x27;historical&#x27;. | [optional]
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the response object to expand.  - &#x60;relationData&#x60; returns information about the relationship, such as who created it and when it was created. - &#x60;source&#x60; returns the source entity. - &#x60;target&#x60; returns the target entity. | [optional] [enum: relationData, source, target]
 **start** | **Integer**| The starting index of the returned relationships. | [optional] [default to 0] [enum: 0]
 **limit** | **Integer**| The maximum number of relationships to return per page. Note, this may be restricted by fixed system limits. | [optional] [default to 25] [enum: 0]

### Return type

[**RelationArray**](RelationArray.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findTargetFromSource"></a>
# **findTargetFromSource**
> RelationArray findTargetFromSource(relationName, sourceType, sourceKey, targetType, sourceStatus, targetStatus, sourceVersion, targetVersion, expand, start, limit)

Find target entities related to a source entity

Returns all target entities that have a particular relationship to the source entity. Note, relationships are one way.  For example, the following method finds all content that the current user has an &#x27;ignore&#x27; relationship with: &#x60;GET /wiki/rest/api/relation/ignore/from/user/current/to/content&#x60; Note, &#x27;ignore&#x27; is an example custom relationship type.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view both the target entity and source entity.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.RelationApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

RelationApi apiInstance = new RelationApi();
String relationName = "relationName_example"; // String | The name of the relationship. This method supports relationships created via [Create relationship](#api-wiki-rest-api-relation-relationname-from-sourcetype-sourcekey-to-targettype-targetkey-put). Note, this method does not support 'like' or 'favourite' relationships.
String sourceType = "sourceType_example"; // String | The source entity type of the relationship.
String sourceKey = "sourceKey_example"; // String | The identifier for the source entity:  - If `sourceType` is `user`, then specify either `current` (logged-in user), the user key of the user, or the account ID of the user. Note that the user key has been deprecated in favor of the account ID for this parameter. See the [migration guide](https://developer.atlassian.com/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details. - If `sourceType` is 'content', then specify the content ID. - If `sourceType` is 'space', then specify the space key.
String targetType = "targetType_example"; // String | The target entity type of the relationship.
String sourceStatus = "sourceStatus_example"; // String | The status of the source. This parameter is only used when the `sourceType` is 'content'.
String targetStatus = "targetStatus_example"; // String | The status of the target. This parameter is only used when the `targetType` is 'content'.
Integer sourceVersion = 56; // Integer | The version of the source. This parameter is only used when the `sourceType` is 'content' and the `sourceStatus` is 'historical'.
Integer targetVersion = 56; // Integer | The version of the target. This parameter is only used when the `targetType` is 'content' and the `targetStatus` is 'historical'.
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the response object to expand.  - `relationData` returns information about the relationship, such as who created it and when it was created. - `source` returns the source entity. - `target` returns the target entity.
Integer start = 0; // Integer | The starting index of the returned relationships.
Integer limit = 25; // Integer | The maximum number of relationships to return per page. Note, this may be restricted by fixed system limits.
try {
    RelationArray result = apiInstance.findTargetFromSource(relationName, sourceType, sourceKey, targetType, sourceStatus, targetStatus, sourceVersion, targetVersion, expand, start, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RelationApi#findTargetFromSource");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **relationName** | **String**| The name of the relationship. This method supports relationships created via [Create relationship](#api-wiki-rest-api-relation-relationname-from-sourcetype-sourcekey-to-targettype-targetkey-put). Note, this method does not support &#x27;like&#x27; or &#x27;favourite&#x27; relationships. |
 **sourceType** | **String**| The source entity type of the relationship. | [enum: user, content, space]
 **sourceKey** | **String**| The identifier for the source entity:  - If &#x60;sourceType&#x60; is &#x60;user&#x60;, then specify either &#x60;current&#x60; (logged-in user), the user key of the user, or the account ID of the user. Note that the user key has been deprecated in favor of the account ID for this parameter. See the [migration guide](https://developer.atlassian.com/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details. - If &#x60;sourceType&#x60; is &#x27;content&#x27;, then specify the content ID. - If &#x60;sourceType&#x60; is &#x27;space&#x27;, then specify the space key. |
 **targetType** | **String**| The target entity type of the relationship. | [enum: user, content, space]
 **sourceStatus** | **String**| The status of the source. This parameter is only used when the &#x60;sourceType&#x60; is &#x27;content&#x27;. | [optional]
 **targetStatus** | **String**| The status of the target. This parameter is only used when the &#x60;targetType&#x60; is &#x27;content&#x27;. | [optional]
 **sourceVersion** | **Integer**| The version of the source. This parameter is only used when the &#x60;sourceType&#x60; is &#x27;content&#x27; and the &#x60;sourceStatus&#x60; is &#x27;historical&#x27;. | [optional]
 **targetVersion** | **Integer**| The version of the target. This parameter is only used when the &#x60;targetType&#x60; is &#x27;content&#x27; and the &#x60;targetStatus&#x60; is &#x27;historical&#x27;. | [optional]
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the response object to expand.  - &#x60;relationData&#x60; returns information about the relationship, such as who created it and when it was created. - &#x60;source&#x60; returns the source entity. - &#x60;target&#x60; returns the target entity. | [optional] [enum: relationData, source, target]
 **start** | **Integer**| The starting index of the returned relationships. | [optional] [default to 0] [enum: 0]
 **limit** | **Integer**| The maximum number of relationships to return per page. Note, this may be restricted by fixed system limits. | [optional] [default to 25] [enum: 0]

### Return type

[**RelationArray**](RelationArray.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getRelationship"></a>
# **getRelationship**
> Relation getRelationship(relationName, sourceType, sourceKey, targetType, targetKey, sourceStatus, targetStatus, sourceVersion, targetVersion, expand)

Find relationship from source to target

Find whether a particular type of relationship exists from a source entity to a target entity. Note, relationships are one way.  For example, you can use this method to find whether the current user has selected a particular page as a favorite (i.e. &#x27;save for later&#x27;): &#x60;GET /wiki/rest/api/relation/favourite/from/user/current/to/content/123&#x60;  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view both the target entity and source entity.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.RelationApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

RelationApi apiInstance = new RelationApi();
String relationName = "relationName_example"; // String | The name of the relationship. This method supports the 'favourite' (i.e. 'save for later') relationship as well as any other relationship types created via [Create relationship](#api-wiki-rest-api-relation-relationname-from-sourcetype-sourcekey-to-targettype-targetkey-put).
String sourceType = "sourceType_example"; // String | The source entity type of the relationship. This must be 'user', if the `relationName` is 'favourite'.
String sourceKey = "sourceKey_example"; // String | - The identifier for the source entity:  - If `sourceType` is `user`, then specify either `current` (logged-in user), the user key of the user, or the account ID of the user. Note that the user key has been deprecated in favor of the account ID for this parameter. See the [migration guide](https://developer.atlassian.com/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details. - If `sourceType` is 'content', then specify the content ID. - If `sourceType` is 'space', then specify the space key.
String targetType = "targetType_example"; // String | The target entity type of the relationship. This must be 'space' or 'content', if the `relationName` is 'favourite'.
String targetKey = "targetKey_example"; // String | The identifier for the target entity:  - If `targetType` is `user`, then specify either `current` (logged-in user), the user key of the user, or the account ID of the user. Note that the user key has been deprecated in favor of the account ID for this parameter. See the [migration guide](https://developer.atlassian.com/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details. - If `targetType` is 'content', then specify the content ID. - If `targetType` is 'space', then specify the space key.
String sourceStatus = "sourceStatus_example"; // String | The status of the source. This parameter is only used when the `sourceType` is 'content'.
String targetStatus = "targetStatus_example"; // String | The status of the target. This parameter is only used when the `targetType` is 'content'.
Integer sourceVersion = 56; // Integer | The version of the source. This parameter is only used when the `sourceType` is 'content' and the `sourceStatus` is 'historical'.
Integer targetVersion = 56; // Integer | The version of the target. This parameter is only used when the `targetType` is 'content' and the `targetStatus` is 'historical'.
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the response object to expand.  - `relationData` returns information about the relationship, such as who created it and when it was created. - `source` returns the source entity. - `target` returns the target entity.
try {
    Relation result = apiInstance.getRelationship(relationName, sourceType, sourceKey, targetType, targetKey, sourceStatus, targetStatus, sourceVersion, targetVersion, expand);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RelationApi#getRelationship");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **relationName** | **String**| The name of the relationship. This method supports the &#x27;favourite&#x27; (i.e. &#x27;save for later&#x27;) relationship as well as any other relationship types created via [Create relationship](#api-wiki-rest-api-relation-relationname-from-sourcetype-sourcekey-to-targettype-targetkey-put). |
 **sourceType** | **String**| The source entity type of the relationship. This must be &#x27;user&#x27;, if the &#x60;relationName&#x60; is &#x27;favourite&#x27;. | [enum: user, content, space]
 **sourceKey** | **String**| - The identifier for the source entity:  - If &#x60;sourceType&#x60; is &#x60;user&#x60;, then specify either &#x60;current&#x60; (logged-in user), the user key of the user, or the account ID of the user. Note that the user key has been deprecated in favor of the account ID for this parameter. See the [migration guide](https://developer.atlassian.com/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details. - If &#x60;sourceType&#x60; is &#x27;content&#x27;, then specify the content ID. - If &#x60;sourceType&#x60; is &#x27;space&#x27;, then specify the space key. |
 **targetType** | **String**| The target entity type of the relationship. This must be &#x27;space&#x27; or &#x27;content&#x27;, if the &#x60;relationName&#x60; is &#x27;favourite&#x27;. | [enum: user, content, space]
 **targetKey** | **String**| The identifier for the target entity:  - If &#x60;targetType&#x60; is &#x60;user&#x60;, then specify either &#x60;current&#x60; (logged-in user), the user key of the user, or the account ID of the user. Note that the user key has been deprecated in favor of the account ID for this parameter. See the [migration guide](https://developer.atlassian.com/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details. - If &#x60;targetType&#x60; is &#x27;content&#x27;, then specify the content ID. - If &#x60;targetType&#x60; is &#x27;space&#x27;, then specify the space key. |
 **sourceStatus** | **String**| The status of the source. This parameter is only used when the &#x60;sourceType&#x60; is &#x27;content&#x27;. | [optional]
 **targetStatus** | **String**| The status of the target. This parameter is only used when the &#x60;targetType&#x60; is &#x27;content&#x27;. | [optional]
 **sourceVersion** | **Integer**| The version of the source. This parameter is only used when the &#x60;sourceType&#x60; is &#x27;content&#x27; and the &#x60;sourceStatus&#x60; is &#x27;historical&#x27;. | [optional]
 **targetVersion** | **Integer**| The version of the target. This parameter is only used when the &#x60;targetType&#x60; is &#x27;content&#x27; and the &#x60;targetStatus&#x60; is &#x27;historical&#x27;. | [optional]
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the response object to expand.  - &#x60;relationData&#x60; returns information about the relationship, such as who created it and when it was created. - &#x60;source&#x60; returns the source entity. - &#x60;target&#x60; returns the target entity. | [optional] [enum: relationData, source, target]

### Return type

[**Relation**](Relation.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

