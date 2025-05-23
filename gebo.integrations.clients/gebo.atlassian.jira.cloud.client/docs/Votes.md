# Votes

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**hasVoted** | **Boolean** | Whether the user making this request has voted on the issue. |  [optional]
**self** | **String** | The URL of these issue vote details. |  [optional]
**voters** | [**List&lt;User&gt;**](User.md) | List of the users who have voted on this issue. An empty list is returned when the calling user doesn&#x27;t have the *View voters and watchers* project permission. |  [optional]
**votes** | **Long** | The number of votes on the issue. |  [optional]
