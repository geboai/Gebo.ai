# Webhook

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**events** | [**List&lt;EventsEnum&gt;**](#List&lt;EventsEnum&gt;) | The Jira events that trigger the webhook. | 
**expirationDate** | **Long** | The date after which the webhook is no longer sent. Use [Extend webhook life](https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-webhooks/#api-rest-api-3-webhook-refresh-put) to extend the date. |  [optional]
**fieldIdsFilter** | **List&lt;String&gt;** | A list of field IDs. When the issue changelog contains any of the fields, the webhook &#x60;jira:issue_updated&#x60; is sent. If this parameter is not present, the app is notified about all field updates. |  [optional]
**id** | **Long** | The ID of the webhook. | 
**issuePropertyKeysFilter** | **List&lt;String&gt;** | A list of issue property keys. A change of those issue properties triggers the &#x60;issue_property_set&#x60; or &#x60;issue_property_deleted&#x60; webhooks. If this parameter is not present, the app is notified about all issue property updates. |  [optional]
**jqlFilter** | **String** | The JQL filter that specifies which issues the webhook is sent for. | 

<a name="List<EventsEnum>"></a>
## Enum: List&lt;EventsEnum&gt;
Name | Value
---- | -----
JIRA_ISSUE_CREATED | &quot;jira:issue_created&quot;
JIRA_ISSUE_UPDATED | &quot;jira:issue_updated&quot;
JIRA_ISSUE_DELETED | &quot;jira:issue_deleted&quot;
COMMENT_CREATED | &quot;comment_created&quot;
COMMENT_UPDATED | &quot;comment_updated&quot;
COMMENT_DELETED | &quot;comment_deleted&quot;
ISSUE_PROPERTY_SET | &quot;issue_property_set&quot;
ISSUE_PROPERTY_DELETED | &quot;issue_property_deleted&quot;
