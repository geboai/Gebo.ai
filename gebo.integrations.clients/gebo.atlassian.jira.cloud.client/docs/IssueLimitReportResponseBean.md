# IssueLimitReportResponseBean

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**issuesApproachingLimit** | [**Map&lt;String, Map&lt;String, Long&gt;&gt;**](Map.md) | A list of ids of issues approaching the limit and their field count |  [optional]
**issuesBreachingLimit** | [**Map&lt;String, Map&lt;String, Long&gt;&gt;**](Map.md) | A list of ids of issues breaching the limit and their field count |  [optional]
**limits** | **Map&lt;String, Integer&gt;** | The fields and their defined limits |  [optional]
