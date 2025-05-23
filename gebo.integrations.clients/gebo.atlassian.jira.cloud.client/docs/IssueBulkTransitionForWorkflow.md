# IssueBulkTransitionForWorkflow

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**isTransitionsFiltered** | **Boolean** | Indicates whether all the transitions of this workflow are available in the transitions list or not. |  [optional]
**issues** | **List&lt;String&gt;** | List of issue keys from the request which are associated with this workflow. |  [optional]
**transitions** | [**List&lt;SimplifiedIssueTransition&gt;**](SimplifiedIssueTransition.md) | List of transitions available for issues from the request which are associated with this workflow.   **This list includes only those transitions that are common across the issues in this workflow and do not involve any additional field updates.**  |  [optional]
