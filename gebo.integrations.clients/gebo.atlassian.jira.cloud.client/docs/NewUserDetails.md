# NewUserDetails

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**applicationKeys** | **List&lt;String&gt;** | Deprecated, do not use. |  [optional]
**displayName** | **String** | This property is no longer available. If the user has an Atlassian account, their display name is not changed. If the user does not have an Atlassian account, they are sent an email asking them set up an account. |  [optional]
**emailAddress** | **String** | The email address for the user. | 
**key** | **String** | This property is no longer available. See the [migration guide](https://developer.atlassian.com/cloud/jira/platform/deprecation-notice-user-privacy-api-migration-guide/) for details. |  [optional]
**name** | **String** | This property is no longer available. See the [migration guide](https://developer.atlassian.com/cloud/jira/platform/deprecation-notice-user-privacy-api-migration-guide/) for details. |  [optional]
**password** | **String** | This property is no longer available. If the user has an Atlassian account, their password is not changed. If the user does not have an Atlassian account, they are sent an email asking them set up an account. |  [optional]
**products** | **List&lt;String&gt;** | Products the new user has access to. Valid products are: jira-core, jira-servicedesk, jira-product-discovery, jira-software. To create a user without product access, set this field to be an empty array. | 
**self** | **String** | The URL of the user. |  [optional]
