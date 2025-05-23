# IssueTypeProjectCreatePayload

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**issueTypeHierarchy** | [**List&lt;IssueTypeHierarchyPayload&gt;**](IssueTypeHierarchyPayload.md) | Defines the issue type hierarhy to be created and used during this project creation. This will only add new levels if there isn&#x27;t an existing level |  [optional]
**issueTypeScheme** | [**IssueTypeSchemePayload**](IssueTypeSchemePayload.md) |  |  [optional]
**issueTypes** | [**List&lt;IssueTypePayload&gt;**](IssueTypePayload.md) | Only needed if you want to create issue types, you can otherwise use the ids of issue types in the scheme configuration |  [optional]
