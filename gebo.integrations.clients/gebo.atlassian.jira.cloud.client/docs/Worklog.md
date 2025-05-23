# Worklog

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**author** | **AllOfWorklogAuthor** | Details of the user who created the worklog. |  [optional]
**comment** | **Object** | A comment about the worklog in [Atlassian Document Format](https://developer.atlassian.com/cloud/jira/platform/apis/document/structure/). Optional when creating or updating a worklog. |  [optional]
**created** | [**Date**](Date.md) | The datetime on which the worklog was created. |  [optional]
**id** | **String** | The ID of the worklog record. |  [optional]
**issueId** | **String** | The ID of the issue this worklog is for. |  [optional]
**properties** | [**List&lt;EntityProperty&gt;**](EntityProperty.md) | Details of properties for the worklog. Optional when creating or updating a worklog. |  [optional]
**self** | **String** | The URL of the worklog item. |  [optional]
**started** | [**Date**](Date.md) | The datetime on which the worklog effort was started. Required when creating a worklog. Optional when updating a worklog. |  [optional]
**timeSpent** | **String** | The time spent working on the issue as days (\\#d), hours (\\#h), or minutes (\\#m or \\#). Required when creating a worklog if &#x60;timeSpentSeconds&#x60; isn&#x27;t provided. Optional when updating a worklog. Cannot be provided if &#x60;timeSpentSecond&#x60; is provided. |  [optional]
**timeSpentSeconds** | **Long** | The time in seconds spent working on the issue. Required when creating a worklog if &#x60;timeSpent&#x60; isn&#x27;t provided. Optional when updating a worklog. Cannot be provided if &#x60;timeSpent&#x60; is provided. |  [optional]
**updateAuthor** | **AllOfWorklogUpdateAuthor** | Details of the user who last updated the worklog. |  [optional]
**updated** | [**Date**](Date.md) | The datetime on which the worklog was last updated. |  [optional]
**visibility** | **AllOfWorklogVisibility** | Details about any restrictions in the visibility of the worklog. Optional when creating or updating a worklog. |  [optional]
