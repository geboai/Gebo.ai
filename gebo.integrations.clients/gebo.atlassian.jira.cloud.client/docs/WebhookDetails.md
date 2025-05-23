# WebhookDetails

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**events** | [**List&lt;EventsEnum&gt;**](#List&lt;EventsEnum&gt;) | The Jira events that trigger the webhook. | 
**fieldIdsFilter** | **List&lt;String&gt;** | A list of field IDs. When the issue changelog contains any of the fields, the webhook &#x60;jira:issue_updated&#x60; is sent. If this parameter is not present, the app is notified about all field updates. |  [optional]
**issuePropertyKeysFilter** | **List&lt;String&gt;** | A list of issue property keys. A change of those issue properties triggers the &#x60;issue_property_set&#x60; or &#x60;issue_property_deleted&#x60; webhooks. If this parameter is not present, the app is notified about all issue property updates. |  [optional]
**jqlFilter** | **String** | The JQL filter that specifies which issues the webhook is sent for. Only a subset of JQL can be used. The supported elements are:   *  Fields: &#x60;issueKey&#x60;, &#x60;project&#x60;, &#x60;issuetype&#x60;, &#x60;status&#x60;, &#x60;assignee&#x60;, &#x60;reporter&#x60;, &#x60;issue.property&#x60;, and &#x60;cf[id]&#x60;. For custom fields (&#x60;cf[id]&#x60;), only the epic label custom field is supported.\&quot;.  *  Operators: &#x60;&#x3D;&#x60;, &#x60;!&#x3D;&#x60;, &#x60;IN&#x60;, and &#x60;NOT IN&#x60;. | 

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
