# IssueBulkDeletePayload

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**selectedIssueIdsOrKeys** | **List&lt;String&gt;** | List of issue IDs or keys which are to be bulk deleted. These IDs or keys can be from different projects and issue types. | 
**sendBulkNotification** | **Boolean** | A boolean value that indicates whether to send a bulk change notification when the issues are being deleted.  If &#x60;true&#x60;, dispatches a bulk notification email to users about the updates. |  [optional]
